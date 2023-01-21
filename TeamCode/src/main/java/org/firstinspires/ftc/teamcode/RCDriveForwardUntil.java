package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RCDriveForwardUntil extends RCDriveCommand{
    public               double moveBy = 0.0;
    public               double moveStartSplit = 0.0;
    public               double power = 1.0;
    public               double timeOut = 0.0;
    public               double targetSensorDist = 0.0;
    public               int targPosLF = 0;
    public               int targPosLB = 0;
    public               int targPosRF = 0;
    public               int targPosRB = 0;

    public RCDriveForwardUntil(Hardware hardware, double moveBy, double power, double targetSensorDist, double timeOut){
        this.hardware = hardware;
        this.moveBy = moveBy;
        this.power = power;
        this.targetSensorDist = targetSensorDist;
        this.timeOut = timeOut;
    }

    public void run(){
        moveStartSplit = hardware.getCurrentTime();
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

        hardware.motorLFront.setPower(power);
        hardware.motorLBack.setPower(power);
        hardware.motorRFront.setPower(power);
        hardware.motorRBack.setPower(power);

    }

    public boolean isComplete(){
//        if ((hardware.rearDistance.getDistance(DistanceUnit.INCH) < targetSensorDist) || (hardware.getCurrentTime() - moveStartSplit > timeOut)){
//            return true;
//        }
        return false;
    }
}