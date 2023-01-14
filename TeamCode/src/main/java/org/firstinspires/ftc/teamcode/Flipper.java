package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Flipper {
    private OpMode opMode = null;
    private Hardware hardware = null;

    private Servo flipper = null;
    private double minPos = 0.0;    //LEFT MIN: 0.133   RIGHT MIN: 0.21
    private double maxPos = 1.0;    //LEFT MAX: 0.8     RIGHT MAX: 0.93
    public String name = "";
    public double currentPos = 0;
    public boolean needReverse = false;

    private final double FLIPPOS = 0;
    private final double HOMEPOS = 0 ;
    public static final double MANUAL_SPEED = .25;

    public Flipper(OpMode opMode, Hardware hardware, Servo flipper, double min, double max, String name, boolean needReverse){
        this.opMode = opMode;
        this.hardware = hardware;
        this.flipper = flipper;
        minPos = min;
        maxPos = max;
        this.name = name;
        this.needReverse = needReverse;
    }

    public void init() {

    }

    public void doInitLoop() {

    }

    public double getCurrentPosition() {
        return currentPos;
    }

    public void setPosition(double position) {
        opMode.telemetry.addData("Recieved Position: ", position);
        currentPos = (Math.min(Math.max(position, minPos), maxPos));
        opMode.telemetry.addData("Translated Position: ", currentPos);
        hardware.logMessage(false, name, "Recieved Position: " + position + " Translated Position: " + currentPos);
        flipper.setPosition(currentPos);
    }

    public void goMin(){
        setPosition(minPos);
    }
    public void goMax(){
        setPosition(maxPos);
    }

    public void flip(){
        double startTime = opMode.time;
        setPosition(FLIPPOS);

        setPosition(HOMEPOS);
    }
    public void setCalibrationRange(){
        minPos = 0.0;
        maxPos = 1.0;
    }

    public void goToStow(){
        if(needReverse){
            setPosition(maxPos);
        }
        else{
            setPosition(minPos);
        }
    }
}
