package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class CVCaptureImage extends OpenCvPipeline {
    public Telemetry telemetry;
    public OpMode opMode;
    boolean viewportPaused;

    String path = Environment.getExternalStorageDirectory().getPath() + "/FIRST/IMAGES/ImageCapture/";

    Mat mat = new Mat();
    boolean firstRun = true;

    public CVCaptureImage(Telemetry telemetry, OpMode opMode) {
        this.telemetry = telemetry;
        this.opMode = opMode;
    }

    @Override
    public Mat processFrame(Mat input) {
        if (firstRun) {
            firstRun = false;
            saveMatToDiskFullPath(input, path + "Image" + opMode.time + ".jpg");
        }

        mat = input;
//    saveMatToDiskFullPath(input, path + "Image" + hardware.getCurrentTime() + ".jpg");
        return input;
    }

    public void saveImage(){
        saveMatToDiskFullPath(mat, path + "Image" + opMode.time + ".jpg");
    }
}
