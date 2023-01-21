package org.firstinspires.ftc.teamcode;

import org.opencv.video.SparseOpticalFlow;

public class RCLiftGoToPosition extends RobCommand{
    private Hardware hardware = null;
    private int position = 0;
    private double power = 0.0;
    private boolean skipWait = false;

    public RCLiftGoToPosition(Hardware hardware, int position, double power){
        this.hardware = hardware;
        this.position = position;
        this.power = power;
    }
    public RCLiftGoToPosition(Hardware hardware, int position, double power,boolean skipWait){
        this.hardware = hardware;
        this.position = position;
        this.power = power;
        this.skipWait = skipWait;
    }

    public void run(){
        hardware.lift.setPosition(position);
    }

    public boolean isComplete(){
        if(skipWait){
            return true;
        }
        return Math.abs(hardware.lift.getCurrentPos() - position) < 7;
    }
}
