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
                hardware.logMessage(false,"RCClaw","Claw Set To open Position");
                break;
            case CMD_GRIP:
                hardware.claw.grip();
                hardware.logMessage(false,"RCClaw","Claw Set To grip Position");
                break;
            case CMD_STOW:
                hardware.claw.stow();
                hardware.logMessage(false,"RCClaw","Claw Set To stow Position");
                break;
            case CMD_DROP:
                hardware.robo130.dropCone();
                hardware.logMessage(false,"RCClaw","Claw Set To drop Position");
                break;
        }
    }

    public boolean isComplete() {
        if (skipWait) {
            hardware.logMessage(false,"RCClaw","Command Complete, skipped wait");
            return true;
        }
        if((hardware.getCurrentTime() - startTime) > 0.35 ) //XXX | change number later on, just random number for now
        {
            hardware.logMessage(false,"RCClaw","Command Complete, after wait");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "RCClaw{" +
                ", clawCMD=" + clawCMD +
                ", skipWait=" + skipWait +
                ", startTime=" + startTime +
                '}';
    }
}