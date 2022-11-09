package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Arm {
    private OpMode opMode = null;
    private Hardware hardware = null;
    private DcMotor armFlip = null;
    private Rev2mDistanceSensor distSense = null;

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timeout = new ElapsedTime();
    private static final int ARM_MAX_ROTATE = 410;
    private static final int ARM_MIN_ROTATE = -1080;
    private static final double MAX_POWER = 1.0;
    private static final int ARM_NOT_HOMED = 0;
    private              int state = ARM_NOT_HOMED;
    private static final int ARM_FINDING_HOME = 1;
    private static final int ARM_BACK_OFF_HOME = 2;
    private static final int ARM_READY = 3;
    private static final double MAX_TIMEOUT = 5.0;
    private static final double ARM_POWER = 1.0;
    private static final double ARM_BACKOFF_POWER = 0.15;
    private static final double ARM_FINDING_HOME_POWER = -0.20;
    private              double startTime = 0;
    private              int armPosition = 0;

    public Arm(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {
        state = ARM_NOT_HOMED;

        opMode.telemetry.addData("Arm Status", "Initializing");
        armFlip = hardware.armFlip;
        distSense = hardware.armDistanceSensor;
        runtime.reset();
        timeout.reset();

        // Tell the driver that initialization is complete.
        opMode.telemetry.addData("Arm Status", "Initialized");
        opMode.telemetry.update();
    }

    public void doInitLoop() {
        opMode.telemetry.addData("Arm Status", "Starting. Finding home...");
        opMode.telemetry.update();
        switch(state)
        {
            case ARM_NOT_HOMED:
                double distanceToClaw = distSense.getDistance(DistanceUnit.INCH);
                    if((distanceToClaw > 3 &&  distanceToClaw < 6)){
                    backOffHome();
                }else{
                    findHome();
                    opMode.telemetry.addData("Sensor Dist", distanceToClaw);
                    opMode.telemetry.update();
                }
                break;

            case ARM_BACK_OFF_HOME:
                if(opMode.time - startTime >= 0.25){
                    findHome();
                }
                break;

            case ARM_FINDING_HOME:
                double distanceToClawTwo = distSense.getDistance(DistanceUnit.INCH);
                opMode.telemetry.addData("Sensor Dist", distanceToClawTwo);
//                hardware.logMessage(false,"sensorDist",sensorDist);
                if((distanceToClawTwo < 4.0 ||  distanceToClawTwo > 6.0)){
                    if(opMode.time - startTime >= MAX_TIMEOUT){
                    }
                    break;
                }
                else{//At home
                    armFlip.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    setPosition(0, ARM_POWER);
                    state = ARM_READY;
                }
                break;
        }
        opMode.telemetry.addData("Arm State", state);
        opMode.telemetry.addData("Arm Status", "Done doing init loop");
        opMode.telemetry.update();
    }

    public void goArmLift(double liftPower){
        //elevatorMotor.setPower(liftPower);
        int targetPosition;
        if(liftPower > 0.03) {
            targetPosition = getCurrentPos() + 1500;
        }
        else if(liftPower < -0.03) {
            targetPosition = getCurrentPos() - 1500;
        }
        else {
            targetPosition = getCurrentPos();
        }
        setPosition(targetPosition, liftPower*MAX_POWER);
    }

    public double getCurrentPow(){
        return armFlip.getPower();
    }
    public int getCurrentPos(){
        return armFlip.getCurrentPosition();
    }

    public void setPosition(int targetPos, double targetPow) {
        armFlip.setTargetPosition(Math.max(Math.min(targetPos, ARM_MAX_ROTATE), ARM_MIN_ROTATE));
        armFlip.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armFlip.setPower(targetPow);//IDontThinkThisIsAGoodIdea
    }

    private void findHome(){
        startTime = opMode.time;
        hardware.armFlip.setMotorEnable();
        armFlip.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armFlip.setPower(ARM_FINDING_HOME_POWER);
        state = ARM_FINDING_HOME;
    }
    private void backOffHome(){
        startTime = opMode.time;
        hardware.armFlip.setMotorEnable();
        armFlip.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armFlip.setPower(ARM_BACKOFF_POWER);
        state = ARM_BACK_OFF_HOME;
    }
}