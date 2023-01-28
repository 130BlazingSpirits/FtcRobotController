package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.Arrays;
import java.util.List;

public class SignalDetector extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();

    Mat purpleIMG = new Mat();
    Mat greenIMG = new Mat();
    Mat yellowIMG = new Mat();

    double numPurple = 0.0;
    double numGreen = 0.0;
    double numYellow = 0.0;

    public SignalDetector(Telemetry t) {telemetry = t;}

    public int conePlacement = 0;

    @Override
    public Mat processFrame(Mat input){
        Imgproc.cvtColor(input,mat,Imgproc.COLOR_BGR2HSV);

        //Purple
        Scalar purpleLowHSV = new Scalar(166,50,50);
        Scalar purpleHighHSV = new Scalar(186,255,255);
        Core.inRange(mat,purpleLowHSV,purpleHighHSV,purpleIMG);

        //Green
        Scalar greenLowHSV = new Scalar(166,50,50);
        Scalar greenHighHSV = new Scalar(186,255,255);
        Core.inRange(mat,greenLowHSV,greenHighHSV,purpleIMG);

        //Yellow
        Scalar yellowLowHSV = new Scalar(166,50,50);
        Scalar yellowHighHSV = new Scalar(186,255,255);
        Core.inRange(mat,yellowLowHSV,yellowHighHSV,purpleIMG);

        numPurple = Core.sumElems(purpleIMG).val[0];
        numGreen = Core.sumElems(greenIMG).val[0];
        numYellow = Core.sumElems(yellowIMG).val[0];

        purpleIMG.release();
        greenIMG.release();
        yellowIMG.release();

        telemetry.addData("Purple Value: ",numPurple);
        telemetry.addData("Green Value: ",numGreen);
        telemetry.addData("Yellow Value: ",numYellow);

        if((numGreen > numYellow) && (numGreen > numPurple)){
            conePlacement = 1;
            mat = greenIMG;
        }
        else if((numYellow > numGreen) && (numYellow > numPurple)){
            conePlacement = 2;
            mat = yellowIMG;
        }
        else if((numPurple > numGreen) && (numPurple > numYellow)){
            conePlacement = 3;
            mat = purpleIMG;
        }

        telemetry.addData("Cone Placement", conePlacement);
        telemetry.update();

        return mat;
    }

    public int getConePlacement() {
        return conePlacement;
    }
}
