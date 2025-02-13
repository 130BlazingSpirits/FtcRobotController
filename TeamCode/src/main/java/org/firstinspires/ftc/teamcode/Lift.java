package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;
import java.util.List;

public class Lift {
    private OpMode opMode = null;
    private Hardware hardware = null;

    private DcMotorEx liftMotor = null;
    private TouchSensor liftSensor = null;

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timeout = new ElapsedTime();
    public static final int LIFT_MAXPOS = 3100; //(Hardware limit is 3175)
    public static final int LIFT_MINPOS = 20;
    public static final int LIFT_MAX_SPEED = 1870; //(Hardware Limit is 1871)
    public static final int LIFT_MANUAL_SPEED = (int) (LIFT_MAX_SPEED * .90);

    private double startTime = 0;
    private double liftPower = 1.0;
    private double liftPowerDownFactor = 0.65;
    private double liftHomingPower =- 0.5;
    private int previousTargetPos = 0;

    private static final int LIFTNOTHOMED = 0;
    private static final int LIFTFINDINGHOME = 1;
    private static final int LIFTBACKOFFHOME = 2;
    private static final int LIFTREADY = 3;
    private              int state = LIFTNOTHOMED;
    List<String> states = Arrays.asList("LIFTNOTHOMED", "LIFTFINDINGHOME", "LIFTBACKOFFHOME", "LIFTREADY");
    private static final double MAX_TIMEOUT = 5.0;

    public static final int GROUND_JUCTION_POSITION= 550;
    public static final int LOW_JUCTION_POSITION= 1500;
    public static final int MED_JUCTION_POSITION= 2300;
    public static final int HIGH_JUCTION_POSITION= 3100;

    public Lift(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {
        opMode.telemetry.addData("Lift Status", "Initializing");
        liftMotor = hardware.liftMotor;
        liftSensor = hardware.liftHomeButton;
        runtime.reset();
        timeout.reset();

//        calibrateLift();

        // Let the driver know Initialization is complete
        opMode.telemetry.addData("Lift Status", "Initialized");
        opMode.telemetry.update();
    }

    public void doInitLoop() {
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
                        hardware.logMessage(true, "Lift", "COULD NOT LOCATE HOME POSITION");
                    }
                    break;
                }
                else{
                    liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    setPosition(0, liftPower);
                    hardware.logMessage(false, "Lift","Lift State:  Ready");
                    state = LIFTREADY;
                }
                break;
        }
        opMode.telemetry.addData("Lift State", states.get(state));
    }
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
                        hardware.logMessage(true, "Lift", "COULD NOT LOCATE HOME POSITION");
                    }
                    break;
                }
                else{
                    liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    setPosition(0, liftPower);
                    hardware.logMessage(false, "Lift","Lift State:  Ready");
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

    //Lift Movement
    public void setPosition(int targetPos){
        this.setPosition(targetPos, liftPower);
    }

    public void setPosition(int targetPos, double targetPow){
        int tp = Math.max(Math.min(targetPos, LIFT_MAXPOS), LIFT_MINPOS);
        liftMotor.setTargetPosition(tp);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(targetPos < previousTargetPos) {liftMotor.setPower(targetPow * liftPowerDownFactor);}
        else{liftMotor.setPower(targetPow);}
        previousTargetPos = tp;
    }

    //GOTO MIN & MAX Positions
    public void goMin() {setPosition(LIFT_MINPOS);}
    public void goMax() {setPosition(LIFT_MAXPOS);}

    //GOTO Different Junction Positions
    public void goToGround(){setPosition(GROUND_JUCTION_POSITION);};
    public void goToLow(){setPosition(LOW_JUCTION_POSITION);};
    public void goToMed(){setPosition(MED_JUCTION_POSITION);};
    public void goToHigh(){setPosition(HIGH_JUCTION_POSITION);};

    public void calibrateLift(){
        hardware.logMessage(false, "Lift", "Starting to calibrate Lift");
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
        hardware.logMessage(false, "Lift","Lift State: Finding Home");
        state = LIFTFINDINGHOME;
    }
    public void backOffHome(){
        startTime = opMode.time;
        hardware.liftMotor.setMotorEnable();
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotor.setPower(liftPower*0.5);
        hardware.logMessage(false, "Lift","Lift State: Backing Off Home");
        state = LIFTBACKOFFHOME;
    }

    public void stop(){
        liftMotor.setPower(0.0);
        liftMotor.setVelocity(0.0);
        liftMotor.setMotorDisable();
    }
}