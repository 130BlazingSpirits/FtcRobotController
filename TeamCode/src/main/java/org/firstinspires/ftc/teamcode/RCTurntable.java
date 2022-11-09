package org.firstinspires.ftc.teamcode;

public class RCTurntable extends RobCommand{
    public               double duration = 0.0;
    public               double endTime = 0.0;
    public               double power = 1.0;

    public RCTurntable(Hardware hardware, double duration, double power){
        this.hardware = hardware;
        this.duration = duration;
        this.power = power;
    }

    public void run(){
        endTime = hardware.getCurrentTime() + duration;
        hardware.turnTable.setPower(power);
    }

    public boolean isComplete(){
        if (hardware.getCurrentTime() > endTime){
            hardware.turnTable.setPower(0.0);
            return true;
        }
        return false;
    }
}
