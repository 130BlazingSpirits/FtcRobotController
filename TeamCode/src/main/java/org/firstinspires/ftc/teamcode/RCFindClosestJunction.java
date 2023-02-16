package org.firstinspires.ftc.teamcode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

public class RCFindClosestJunction extends RobCommand{
    private final int FINDCLOSEJUNCTION = 0;
    private final int ROTATETOJUNCTION = 1;
    private final int DRIVETOJUNCTION = 2;
    private int state = FINDCLOSEJUNCTION;

    private CVLocateClosestJunction pipeline = null;
    private double startTime = 0.0;
    private double timeout = 0.0;

    public RCFindClosestJunction(Hardware hardware, CVLocateClosestJunction pipeline, double timeout){
        this.hardware = hardware;
        this.pipeline = pipeline;
        this.timeout = timeout;
    }

    public void run(){
        startTime = hardware.getCurrentTime();

        hardware.webcam.setPipeline(pipeline);
        hardware.webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                hardware.webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
            }
        });
    }

    @Override
    public boolean isComplete() {

        if(hardware.getCurrentTime() - startTime > timeout){
            hardware.webcam.stopStreaming();
            return true;
        }

        switch(state){
            case FINDCLOSEJUNCTION:
                
                break;

            case ROTATETOJUNCTION:

                break;

            case DRIVETOJUNCTION:

                break;
        }

        return false;
    }
}
