package org.firstinspires.ftc.teamcode;

public class RCLiftElevator extends RobCommand{
    public static final int LEVEL_ZERO = 0;
    public static final int LEVEL_ONE = 1138;
    public static final int LEVEL_TWO = 1600;
    public              int level = 0;
    public               double power = 1.0;
    public               int targPos = 0;

    public RCLiftElevator(Hardware hardware, int level, double power){
        this.hardware = hardware;
        this.power = power;
        this.level = level;
    }

    public void run(){
        //int currPos = hardware.elevatorMotor.getCurrentPosition();

        if(level == 10){
            level = ((AutoOne)hardware.opMode).scoringPieceAt;
        }
        switch (level) {
            case 0:
                targPos = LEVEL_ZERO;
                break;
            case 1:
                targPos = LEVEL_ONE;
                break;
            case 2:
                targPos = LEVEL_TWO;
                break;
        }

        hardware.elevatorMotor.setTargetPosition(targPos);
        hardware.elevatorMotor.setPower(power);
    }

    public boolean isComplete(){
        hardware.opMode.telemetry.addData("ElevatorPos", level);
        if (Math.abs(hardware.elevatorMotor.getCurrentPosition() - targPos) < 7){
             return true;
        }
        return false;
    }
}
