package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class RCDriveForward extends RCDriveCommand{
    public               double moveBy = 0.0;
    public               double power = 1.0;
    public               int targPosLF = 0;
    public               int targPosLB = 0;
    public               int targPosRF = 0;
    public               int targPosRB = 0;

    public RCDriveForward(Hardware hardware, double moveBy){
        this.hardware = hardware;
        this.moveBy = moveBy;
    }

    public RCDriveForward(Hardware hardware, double moveBy, double power){
        this.hardware = hardware;
        this.moveBy = moveBy;
        this.power = power;
    }

    public void run(){
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

    public boolean isComplete(){
        /*hardware.opMode.telemetry.addData("isComplete tarLF", targPosLF);
        hardware.opMode.telemetry.addData("isComplete tarLB", targPosLB);
        hardware.opMode.telemetry.addData("isComplete tarRF", targPosRF);
        hardware.opMode.telemetry.addData("isComplete tarRB", targPosRB);*/
        if (Math.abs(hardware.motorLFront.getCurrentPosition() - targPosLF) < 15
                /*&& Math.abs(hardware.motorLBack.getCurrentPosition() - targPosLB) < 7
                && Math.abs(hardware.motorRFront.getCurrentPosition() - targPosRF) < 7
                && Math.abs(hardware.motorRBack.getCurrentPosition() - targPosRB) < 7*/){
            return true;
        }
        return false;
    }
}