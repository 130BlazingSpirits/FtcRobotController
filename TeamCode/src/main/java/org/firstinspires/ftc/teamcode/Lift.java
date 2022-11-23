package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;
import java.util.List;

public class Lift {
    private OpMode opMode = null;
    private Hardware hardware = null;

    private DcMotor liftMotor = null;
    private TouchSensor liftSensor = null;

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timeout = new ElapsedTime();
    private static final int LIFTMAXHEIGHT = 1600;

    private double startTime = 0;
    private double liftPower = 1.0;
    private double liftHomingPower =- 0.5;

    //State Variables
    private static final int LIFTNOTHOMED = 0;
    private static final int LIFTFINDINGHOME = 1;
    private static final int LIFTBACKOFFHOME = 2;
    private static final int LIFTREADY = 3;
    private              int state = LIFTNOTHOMED;
    List<String> states = Arrays.asList("LIFTNOTHOMED", "LIFTFINDINGHOME", "LIFTBACKOFFHOME", "LIFTREADY");
    private static final double MAX_TIMEOUT = 5.0;

    public Lift(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {
        opMode.telemetry.addData("Lift Status", "Initializing");
        liftMotor = hardware.liftMotor;
        liftSensor = hardware.liftHome;
        runtime.reset();
        timeout.reset();

        // Let the driver know Initialization is complete
        opMode.telemetry.addData("Lift Status", "Initialized");
        opMode.telemetry.update();
    }

    //public void doInitLoop() {
    public void doLoop() {
        opMode.telemetry.addData("Lift Status", "Starting. Finding home...");
        opMode.telemetry.update();
        switch(state)
        {
            case LIFTNOTHOMED:
                break;

            case LIFTBACKOFFHOME:
                if(opMode.time - startTime >= 0.2){
                    findHome();
                }
                break;

            case LIFTFINDINGHOME:
                if(!liftSensor.isPressed()){
                    if(opMode.time - startTime >= MAX_TIMEOUT){
                        liftMotor.setPower(0);
                        opMode.telemetry.addLine("Lift could not find home position");
                    }
                    break;
                }
                else{
                    liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    setPosition(0, liftPower);
                    state = LIFTREADY;
                }
                break;
        }
        opMode.telemetry.addData("Lift State", states.get(state));
    }

    //Getters for power and position
    public double getCurrentPow(){
        return liftMotor.getPower();
    }
    public int getCurrentPos(){
        return liftMotor.getCurrentPosition();
    }

    public void goLift(int position){
        liftMotor.setTargetPosition(position);
        liftMotor.setPower(liftPower);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setPosition(int targetPos, double targetPow){
        liftMotor.setTargetPosition(Math.max(Math.min(targetPos, LIFTMAXHEIGHT), 0));
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(targetPow);
    }

    public void calibrateLift(){
        state = LIFTNOTHOMED;
        if(liftSensor.isPressed()){
            backOffHome();
        }else{
            findHome();
        }
    }

    public void findHome(){
        startTime = opMode.time;
        hardware.liftMotor.setMotorEnable();
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotor.setPower(liftHomingPower);
        state = LIFTFINDINGHOME;
    }
    public void backOffHome(){
        startTime = opMode.time;
        hardware.liftMotor.setMotorEnable();
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotor.setPower(liftPower*0.5);
        state = LIFTBACKOFFHOME;
    }
}