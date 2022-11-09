package org.firstinspires.ftc.teamcode;

public class RCTurnCounterClockwise extends RobCommand{
    public               double rotateBy = 0;
    public               double power = 1.0;
    public               int targPosLF = 0;
    public               int targPosLB = 0;
    public               int targPosRF = 0;
    public               int targPosRB = 0;

    public RCTurnCounterClockwise(Hardware hardware, double rotateBy){
        this.hardware = hardware;
        this.rotateBy = rotateBy;
    }

    public RCTurnCounterClockwise(Hardware hardware, double rotateBy, double power){
        this.hardware = hardware;
        this.rotateBy = rotateBy;
        this.power = power;
    }

    public void run(){
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

        hardware.motorLFront.setPower(power);
        hardware.motorLBack.setPower(power);
        hardware.motorRFront.setPower(power);
        hardware.motorRBack.setPower(power);

    }

    public boolean isComplete(){
//        hardware.opMode.telemetry.addData("isComplete tarLF", targPosLF);
//        hardware.opMode.telemetry.addData("isComplete tarLB", targPosLB);
//        hardware.opMode.telemetry.addData("isComplete tarRF", targPosRF);
//        hardware.opMode.telemetry.addData("isComplete tarRB", targPosRB);
        if (Math.abs(hardware.motorLFront.getCurrentPosition() - targPosLF) < 7
//                && Math.abs(hardware.motorLBack.getCurrentPosition() - targPosLB) < 7
//                && Math.abs(hardware.motorRFront.getCurrentPosition() - targPosRF) < 7
/*                && Math.abs(hardware.motorRBack.getCurrentPosition() - targPosRB) < 7*/){
            return true;
        }
        return false;
    }
}
