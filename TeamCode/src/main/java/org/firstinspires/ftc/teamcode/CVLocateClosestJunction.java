package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class CVLocateClosestJunction extends OpenCvPipeline {
    public Telemetry telemetry;
    public OpMode opMode;
    public Hardware hardware;

    private Mat hsvImage = new Mat();
    private Mat croppedIMG = new Mat();
    private Mat mask = new Mat();
    private Mat finalImg = new Mat();

    public CVLocateClosestJunction(Telemetry telemetry, OpMode opMode, Hardware hardware) {
        this.telemetry = telemetry;
        this.opMode = opMode;
        this.hardware = hardware;
    }

    @Override
    public Mat processFrame(Mat input) {
        //Convert To HSV
        Imgproc.cvtColor(input, input, Imgproc.COLOR_BGRA2BGR);
        croppedIMG = input.submat(0, 20, 0, 1279);
        Imgproc.cvtColor(croppedIMG, hsvImage, Imgproc.COLOR_BGR2HSV);

        //Mask Out Yellow
        Scalar yellowLowHSV = new Scalar(94, 155, 50);
        Scalar yellowHighHSV = new Scalar(104, 255, 255);
        Core.inRange(hsvImage, yellowLowHSV, yellowHighHSV, mask);

        //Finding Contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchey = new Mat();
        Imgproc.findContours(mask, contours, hierarchey, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        //Drawing Contours
        Scalar color = new Scalar(0, 0, 255);
        Imgproc.drawContours(mask, contours, -1, color, 2, Imgproc.LINE_8, hierarchey, 2, new Point() ) ;

//        double maxVal = 0;
//        int maxValIdx = 0;
//        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++)
//        {
//            double contourArea = Imgproc.contourArea(contours.get(contourIdx));
//            if (maxVal < contourArea)
//            {
//                maxVal = contourArea;
//                maxValIdx = contourIdx;
//            }
//        }
//
//        Imgproc.drawContours(finalImg, contours, maxValIdx, new Scalar(0,255,0), 15);

        return mask;
    }
}
