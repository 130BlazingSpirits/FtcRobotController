package org.firstinspires.ftc.teamcode;

import org.openftc.easyopencv.OpenCvCameraRotation;

public class RCProcessFrame extends RobCommand{
    public               double delayTime = 0;
    public               double endTime = 0;

    public RCProcessFrame(Hardware hardware, double delayTime){
        this.hardware = hardware;
        this.delayTime = delayTime;
    }

    public void run(){
        hardware.webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPSIDE_DOWN);
        hardware.logMessage(false,"RCProcessFrame","Processing Frame");
        endTime = hardware.getCurrentTime() + delayTime;
    }

    public boolean isComplete(){
        if (hardware.getCurrentTime() > endTime){
            hardware.logMessage(false,"RCProcessFrame","Frame Processed");
            hardware.webcam.stopStreaming();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "RCWait{" +
                "delayTime=" + delayTime +
                ", endTime=" + endTime +
                '}';
    }
}
