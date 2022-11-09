package org.firstinspires.ftc.teamcode;

public class RCWait extends RobCommand{
    public               double delayTime = 0;
    public               double endTime = 0;

    public RCWait(Hardware hardware, double delayTime){
        this.hardware = hardware;
        this.delayTime = delayTime;
    }

    public void run(){
        endTime = hardware.getCurrentTime() + delayTime;
    }

    public boolean isComplete(){
       if (hardware.getCurrentTime() > endTime){
            return true;
        }
        return false;
    }
}