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

    public               double power = 0.5; //50% Speed
    public               int targPosLF = 0;
    public               int targPosLB = 0;
    public               int targPosRF = 0;
    public               int targPosRB = 0;

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
        hardware.logMessage(false,"RCFindClosestJunction","Starting Check For Junctions");
    }

    @Override
    public boolean isComplete() {

        if(hardware.getCurrentTime() - startTime > timeout){
            hardware.webcam.stopStreaming();
            return true;
        }

        switch(state){
            case FINDCLOSEJUNCTION:
                    if(pipeline.estimatedAngle >-999 )
                    {
                        hardware.logMessage(false,"RCFindClosestJunction","Junction Found");
                        state = ROTATETOJUNCTION;
                        runRotate(-1 * pipeline.estimatedAngle);
                    }
                break;

            case ROTATETOJUNCTION:
                if (Math.abs(hardware.motorLFront.getCurrentPosition() - targPosLF) < 15){
                    hardware.logMessage(false,"RCFindClosestJunction","Rotate Complete, at requested position");
                    if(pipeline.estimatedAngle > 15){ //Check if angle is close
                        hardware.logMessage(false,"RCFindClosestJunction","Driving To Junction");
                        state = DRIVETOJUNCTION;
                        runDrive(pipeline.estimatedDistance - 5.5);
                    }
                    else{ //Wrong Angle
                        state = FINDCLOSEJUNCTION;
                    }
                }
                break;

            case DRIVETOJUNCTION:
                if (Math.abs(hardware.motorLFront.getCurrentPosition() - targPosLF) < 15){
                    hardware.logMessage(false,"RCFindClosestJunction","Command Complete, at requested position");
                    return true;
                }
                break;
        }
        return false;
    }
    public void runRotate(double rotateBy){
        hardware.logMessage(false,"RCFindClosestJunction","Starting Rotation" + rotateBy);

        int currPosLF = hardware.motorLFront.getCurrentPosition();
        int currPosLB = hardware.motorLBack.getCurrentPosition();
        int currPosRF = hardware.motorRFront.getCurrentPosition();
        int currPosRB = hardware.motorRBack.getCurrentPosition();

        targPosLF = currPosLF - (int)(rotateBy / DriveTrain.DEGREE_PER_PULSE);
        targPosLB = currPosLB - (int)(rotateBy / DriveTrain.DEGREE_PER_PULSE);
        targPosRF = currPosRF + (int)(rotateBy / DriveTrain.DEGREE_PER_PULSE);
        targPosRB = currPosRB + (int)(rotateBy / DriveTrain.DEGREE_PER_PULSE);

        hardware.motorLFront.setTargetPosition(targPosLF);
        hardware.motorLBack.setTargetPosition(targPosLB);
        hardware.motorRFront.setTargetPosition(targPosRF);
        hardware.motorRBack.setTargetPosition(targPosRB);

        if(hardware.driveTrain.useSetVelocity){
            hardware.motorLFront.setVelocity(power*DriveTrain.MAX_ANGULAR_VELOCITY);
            hardware.motorLBack.setVelocity(power*DriveTrain.MAX_ANGULAR_VELOCITY);
            hardware.motorRFront.setVelocity(power*DriveTrain.MAX_ANGULAR_VELOCITY);
            hardware.motorRBack.setVelocity(power*DriveTrain.MAX_ANGULAR_VELOCITY);
        }
        else{
            hardware.motorLFront.setPower(power);
            hardware.motorLBack.setPower(power);
            hardware.motorRFront.setPower(power);
            hardware.motorRBack.setPower(power);
        }
    }

    public void runDrive(double moveBy){
        hardware.logMessage(false,"RCFindClosestJunction","Starting Drive Forward " + moveBy);

        int currPosLF = hardware.motorLFront.getCurrentPosition();
        int currPosLB = hardware.motorLBack.getCurrentPosition();
        int currPosRF = hardware.motorRFront.getCurrentPosition();
        int currPosRB = hardware.motorRBack.getCurrentPosition();

        targPosLF = currPosLF + (int)(moveBy / DriveTrain.FORWARD_DISTANCE_PER_PULSE);
        targPosLB = currPosLB + (int)(moveBy / DriveTrain.FORWARD_DISTANCE_PER_PULSE);
        targPosRF = currPosRF + (int)(moveBy / DriveTrain.FORWARD_DISTANCE_PER_PULSE);
        targPosRB = currPosRB + (int)(moveBy / DriveTrain.FORWARD_DISTANCE_PER_PULSE);

        hardware.motorLFront.setTargetPosition(targPosLF);
        hardware.motorLBack.setTargetPosition(targPosLB);
        hardware.motorRFront.setTargetPosition(targPosRF);
        hardware.motorRBack.setTargetPosition(targPosRB);

        if(hardware.driveTrain.useSetVelocity){
            hardware.motorLFront.setVelocity(power*DriveTrain.MAX_LINEAR_VELOCITY);
            hardware.motorLBack.setVelocity(power*DriveTrain.MAX_LINEAR_VELOCITY);
            hardware.motorRFront.setVelocity(power*DriveTrain.MAX_LINEAR_VELOCITY);
            hardware.motorRBack.setVelocity(power*DriveTrain.MAX_LINEAR_VELOCITY);
        }
        else{
            hardware.motorLFront.setPower(power);
            hardware.motorLBack.setPower(power);
            hardware.motorRFront.setPower(power);
            hardware.motorRBack.setPower(power);
        }
    }
}
