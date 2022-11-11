package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Lift {
    private OpMode opMode = null;
    private Hardware hardware = null;

    private DcMotor liftMotor = null;
    private TouchSensor liftSensor = null;

    public Lift(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {
    }

    public void doInitLoop() {

    }

    public double getCurrentPow(){
        return liftMotor.getPower();
    }

    public int getCurrentPos(){
        return liftMotor.getCurrentPosition();
    }

    public void goLift(double liftPower){}

    public void setPosition(){}
    public void findHome(){}
    public void backOffHome(){}
}
