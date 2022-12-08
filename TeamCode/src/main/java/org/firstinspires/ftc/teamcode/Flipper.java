package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Flipper {
    private OpMode opMode = null;
    private Hardware hardware = null;

    private Servo flipper = null;
    private double minPos = 0;
    private double maxPos = 0;
    public String name = "";
    public double currentPos = 0;

    private final double FLIPPOS = 0;
    public final double HOMEPOS = 0 ;

    public Flipper(OpMode opMode, Hardware hardware, Servo flipper, double min, double max, String name){
        this.opMode = opMode;
        this.hardware = hardware;
        this.flipper = flipper;
        minPos = min;
        maxPos = max;
        this.name = name;
    }

    public void init() {

    }

    public void doInitLoop() {

    }

    public double getCurrentPosition(){return currentPos;}

    public void setPosition(double position){
        currentPos = position;
        flipper.setPosition(currentPos);
    }

    public void flip(){
        double startTime = opMode.time;
        setPosition(FLIPPOS);
        //Wait
        setPosition(HOMEPOS);
    }
}
