package org.firstinspires.ftc.teamcode;

public class RCTurnCounterClockwise extends RCDriveCommand{
    public               double rotateBy = 0;
    public               double power = 1.0;
    public               int targPosLF = 0;
    public               int targPosLB = 0;
    public               int targPosRF = 0;
    public               int targPosRB = 0;

    private boolean isRunning = false;
    private boolean isMoveComplete = false;

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
        hardware.logMessage(false,"RCTurnCounterClockwise","Command Ran, set to rotate by " + rotateBy);

        isRunning = true;

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

    public boolean isComplete(){
//        hardware.opMode.telemetry.addData("isComplete tarLF", targPosLF);
//        hardware.opMode.telemetry.addData("isComplete tarLB", targPosLB);
//        hardware.opMode.telemetry.addData("isComplete tarRF", targPosRF);
//        hardware.opMode.telemetry.addData("isComplete tarRB", targPosRB);
        if (Math.abs(hardware.motorLFront.getCurrentPosition() - targPosLF) < 15
//                && Math.abs(hardware.motorLBack.getCurrentPosition() - targPosLB) < 7
//                && Math.abs(hardware.motorRFront.getCurrentPosition() - targPosRF) < 7
/*                && Math.abs(hardware.motorRBack.getCurrentPosition() - targPosRB) < 7*/){
            hardware.logMessage(false,"RCTurnCounterClockwise","Command Complete, at requested position");

            isRunning = true;
            isMoveComplete = false;
            return true;
        }
        return false;
    }
    public void updateRotateBy(double incremintalRotation) {
        hardware.logMessage(false,"RCTurnCounterClockwise","Command Updated By " + incremintalRotation);

        rotateBy += incremintalRotation;
        if (isRunning) {
            targPosLF += (int) (incremintalRotation / DriveTrain.DEGREE_PER_PULSE);
            targPosLB += (int) (incremintalRotation / DriveTrain.DEGREE_PER_PULSE);
            targPosRF += (int) (incremintalRotation / DriveTrain.DEGREE_PER_PULSE);
            targPosRB += (int) (incremintalRotation / DriveTrain.DEGREE_PER_PULSE);

            hardware.motorLFront.setTargetPosition(targPosLF);
            hardware.motorLBack.setTargetPosition(targPosLB);
            hardware.motorRFront.setTargetPosition(targPosRF);
            hardware.motorRBack.setTargetPosition(targPosRB);
        }
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    @Override
    public String toString() {
        return "RCTurnCounterClockwise{" +
                "rotateBy=" + rotateBy +
                ", power=" + power +
                ", targPosLF=" + targPosLF +
                ", targPosLB=" + targPosLB +
                ", targPosRF=" + targPosRF +
                ", targPosRB=" + targPosRB +
                ", isRunning=" + isRunning +
                ", isMoveComplete=" + isMoveComplete +
                '}';
    }
}
