package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
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

    private Rect maxContourRect = new Rect();
    private Mat contourImg = new Mat();

    public int left = 0;
    public int right = 0;
    public int center = 0;
    public int width = 0;
    public double estimatedDistance = 0.0;
    public double estimatedAngle = 0.0;
    public int desiredCenterLocation = 0;



    public CVLocateClosestJunction(Telemetry telemetry, OpMode opMode, Hardware hardware) {
        this.telemetry = telemetry;
        this.opMode = opMode;
        this.hardware = hardware;
    }
    public CVLocateClosestJunction(Telemetry telemetry, OpMode opMode) {
        this.telemetry = telemetry;
        this.opMode = opMode;
    }

    @Override
    public Mat processFrame(Mat input) {
        //Cleanup
        hsvImage.release();
        croppedIMG.release();
        mask.release();
        finalImg.release();
        contourImg.release();

        //Convert To HSV
        Imgproc.cvtColor(input, input, Imgproc.COLOR_BGRA2BGR);
        croppedIMG = input.submat(0, 20, 0, 1279);
        Imgproc.cvtColor(croppedIMG, hsvImage, Imgproc.COLOR_BGR2HSV);

        //Mask Out Yellow
        Scalar yellowLowHSV = new Scalar(94, 155, 50);
        Scalar yellowHighHSV = new Scalar(104, 255, 255);
        Core.inRange(hsvImage, yellowLowHSV, yellowHighHSV, mask);

        contourImg = new Mat(mask.size(), mask.type());

        //Finding Contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchey = new Mat();
        Imgproc.findContours(mask, contours, hierarchey, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        //Find Biggest Contour
        double maxVal = 0;
        int maxValIdx = -1;
        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++)
        {
            double contourArea = Imgproc.contourArea(contours.get(contourIdx));
            if (maxVal < contourArea)
            {
                maxVal = contourArea;
                maxValIdx = contourIdx;
            }
        }

        //check for contours in image and assign values if one exists
        if(maxValIdx > -1){
            maxContourRect = Imgproc.boundingRect(contours.get(maxValIdx));
            left = maxContourRect.x;
            right = maxContourRect.x + maxContourRect.width;
            width = maxContourRect.width;
            center = (left + right)/2;
            estimatedDistance = 0.0018732823 * (width*width) - 0.4939630727 * width + 38.6729349358;
            desiredCenterLocation = (int)(-0.0323989007 * (width*width) + 10.2385048305 * width + 180.1241663228);
            estimatedAngle = (double)(center - desiredCenterLocation)/1280 * -78.0;
        }
        else{
            left = -1;
            right = -1;
            width = -1;
            center = -1;
            estimatedDistance = -999;
            desiredCenterLocation = -1;
            estimatedAngle = -999;
        }

        return contourImg;
    }
}
