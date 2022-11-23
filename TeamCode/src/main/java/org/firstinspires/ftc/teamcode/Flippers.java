package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Flippers {
    private OpMode opMode = null;
    private Hardware hardware = null;

    private Servo flipperLeft = null;
    private Servo flipperRight = null;

    private final double FLIPPOS = 0;
    public final double HOMEPOS = 0 ;

    public Flippers(OpMode opMode, Hardware hardware){
        this.opMode = opMode;
        this.hardware = hardware;
    };

    public void init() {

    }

    public void doInitLoop() {

    }

    public void leftFlip(){
        double startTime = opMode.time;
        flipperLeft.setPosition(FLIPPOS);
        while(startTime - 3 < opMode.time) {}
        flipperLeft.setPosition(HOMEPOS);
    };

    public void rightFlip(){
        double startTime = opMode.time;
        flipperRight.setPosition(FLIPPOS);
        while(startTime - 3 < opMode.time) {}
        flipperRight.setPosition(HOMEPOS);
    };
}
