package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Elevator {
    /*
    private OpMode opMode = null;
    private Hardware hardware = null;

    private DcMotor elevatorMotor = null;

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timeout = new ElapsedTime();
    private static final int ELEVATORMAXHEIGHT = 1600;
    private              int state = ELEVATORNOTHOMED;
    private static final int ELEVATORNOTHOMED = 0;
    private static final int ELEVATORFINDINGHOME = 1;
    private static final int ELEVATORBACKOFFHOME = 2;
    private static final int ELEVATORREADY = 3;
    private static final double MAX_TIMEOUT = 5.0;
    private              double ELEVATOR_POWER = 1.0;
    private              double ELEVATOR_FINDING_HOME_POWER = -0.2;
    private              double startTime = 0;
    private              int elevatorPosition = 0;

    public Elevator(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {

        opMode.telemetry.addData("Arm Status", "Initializing");
        elevatorMotor = hardware.elevatorMotor;
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
            case ELEVATORNOTHOMED:
                if(hardware.liftLimit.isPressed()){
                    backOffHome();
                }else{
                    findHome();
                }
                break;

            case ELEVATORBACKOFFHOME:
                if(opMode.time - startTime >= 0.5){
                    findHome();
                }
                break;

            case ELEVATORFINDINGHOME:
                if(!hardware.liftLimit.isPressed()){
                    if(opMode.time - startTime >= MAX_TIMEOUT){
                    }
                    break;
                }
                else{//At home
                    elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    setPosition(0, ELEVATOR_POWER);
                    state = ELEVATORREADY;
                }
                break;
        }
        opMode.telemetry.addData("Claw Status", "Done doing init loop");
        opMode.telemetry.update();
    }

    public void goElevatorLift(double liftPower){
        //elevatorMotor.setPower(liftPower);
        int targetPosition;
        if(liftPower > 0.03) {
            targetPosition = getCurrentPos() + 250;
        }
        else if(liftPower < -0.03) {
            targetPosition = getCurrentPos() - 250;
        }
        else {
            targetPosition = getCurrentPos();
        }
        setPosition(targetPosition, liftPower);
    }

    public double getCurrentPow(){
        return elevatorMotor.getPower();
    }

    public int getCurrentPos(){
        return elevatorMotor.getCurrentPosition();
    }

    public void setPosition(int targetPos, double targetPow) {
        elevatorMotor.setTargetPosition(Math.max(Math.min(targetPos, ELEVATORMAXHEIGHT), 0));
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevatorMotor.setPower(targetPow);//IDontThinkThisIsAGoodIdea
    }

    private void findHome(){
        startTime = opMode.time;
        hardware.elevatorMotor.setMotorEnable();
        elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevatorMotor.setPower(ELEVATOR_FINDING_HOME_POWER);
        state = ELEVATORFINDINGHOME;
    }
    private void backOffHome(){
        startTime = opMode.time;
        hardware.elevatorMotor.setMotorEnable();
        elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevatorMotor.setPower(ELEVATOR_POWER*0.2);
        state = ELEVATORBACKOFFHOME;
    }
     */
}