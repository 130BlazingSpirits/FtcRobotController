package org.firstinspires.ftc.teamcode;

public class RCDetectSignal extends RCDriveCommand{
    private boolean isRunning = false;
    private boolean isDetected = false;
    private int signalValue = 0;

    public RCDetectSignal(Hardware hardware){
        this.hardware = hardware;
    }

    public void run(){
        hardware.logMessage(false,"RCDetectSignal","Command Ran");

    }

    public boolean isComplete(){
        return false;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    @Override
    public String toString() {
        return "RCDetectSignal{" +
                "signalValue=" + signalValue +
                ", isRunning=" + isRunning +
                ", isMoveComplete=" + isDetected +
                '}';
    }
}