package org.firstinspires.ftc.teamcode;

public class RCClaw extends RobCommand{
    public static final int CMD_OPEN = 0;
    public static final int CMD_GRIP = 1;
    public static final int CMD_STOW = 2;
    public static final int CMD_DROP = 3;
    private Hardware hardware = null;
    private int clawCMD = 0;
    private boolean skipWait = false;

    private double startTime = 0.0;

    public RCClaw(Hardware hardware, int clawCMD, boolean skipWait){
        this.hardware = hardware;
        this.clawCMD = clawCMD;
        this.skipWait = skipWait;
    }

    public void run(){
        startTime = hardware.getCurrentTime();
        switch(clawCMD){
            case CMD_OPEN:
                hardware.claw.open();
                break;
            case CMD_GRIP:
                hardware.claw.grip();
                break;
            case CMD_STOW:
                hardware.claw.stow();
                break;
            case CMD_DROP:
                hardware.robo130.dropCone();
                break;
        }
    }

    public boolean isComplete() {
        if (skipWait) {
            return true;
        }
        if((hardware.getCurrentTime() - startTime) > 0.2 ) //XXX | change number later on, just random number for now
        {
            return true;
        }
        return false;
    }
}