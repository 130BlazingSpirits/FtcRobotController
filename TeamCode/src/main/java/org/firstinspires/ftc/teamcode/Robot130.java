package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Robot130 {
    private OpMode opMode = null;
    private Hardware hardware = null;

    //TIMING
//    private ElapsedTime runtime = new ElapsedTime();
//    private ElapsedTime timeout = new ElapsedTime();

    //CONFIGURATION STATE MACHINE VARIABLES
    public boolean isRedTeam;
    public String teamColor = "";
    private static final int CONFIGURATION_ASK_TEAM_COLOUR = 1;
    private static final int CONFIGURATION_READY = 2;
    public int configurationState = CONFIGURATION_ASK_TEAM_COLOUR;

    //MAIN STATE MACHINE VARIABLES
    private final int NOT_READY = 0;
    private final int READY = 1;
    public int loopState = NOT_READY;
    //DROP STATES
    private final int DROP_CONE_LIFT_MOVING_DOWN = 10;
    private final int DROP_CONE_DROPPING = 11;
    private final int DROP_CONE_LIFT_MOVING_UP = 12;

    String[] loopListValues = {"NOT_READY", "READY", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "DROP_CONE_LIFT_MOVING_DOWN", "DROP_CONE_DROPPING", "DROP_CONE_LIFT_MOVING_UP"};

    //OTHER
    private int dropPos = 0;
    private int preDropPos = 0;
    private double liftTimeout = 0.0;
    private double clawOpenTime = 0.0;


    public Robot130(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {
        configurationState = CONFIGURATION_ASK_TEAM_COLOUR;
//        runtime.reset();
//        timeout.reset();
    }

    public void doConfigurationLoop() {
        switch (configurationState) {
            case CONFIGURATION_ASK_TEAM_COLOUR:
                opMode.telemetry.addLine("Team Colour? RED or BLUE button.");
                opMode.telemetry.update();
                if (hardware.gamepad2_current_x || hardware.gamepad1_current_x) {
                    isRedTeam = false;
                    teamColor = "BLUE";
                    configurationState = CONFIGURATION_READY;
                } else if (hardware.gamepad2_current_b || hardware.gamepad1_current_b) {
                    isRedTeam = true;
                    teamColor = "RED";
                    configurationState = CONFIGURATION_READY;
                }
                break;

            case CONFIGURATION_READY:
                opMode.telemetry.addLine("Config Results:");
                opMode.telemetry.addData("Team Colour is: ", teamColor);
                opMode.telemetry.update();
        }
    }

    public void doLoop() {
        switch (loopState) {
            case NOT_READY:
                break;

            case READY:
                break;

            case DROP_CONE_LIFT_MOVING_DOWN:
                if (Math.abs(hardware.lift.getCurrentPos() - dropPos) < 10) {
                    hardware.claw.open();
                    clawOpenTime = opMode.time;
                    loopState = DROP_CONE_DROPPING;
                } else if (opMode.time - liftTimeout > 2.0) {
                    opMode.telemetry.addLine("Lift Timed Out going to position: " + dropPos + "currently at" + hardware.lift.getCurrentPos());
                    hardware.logMessage(true, "Lift", "Lift Timed Out getting to position" + dropPos + "current position is" + hardware.lift.getCurrentPos());
                }
                break;

            case DROP_CONE_DROPPING:
                if (opMode.time - clawOpenTime >= 0.5) {
                    hardware.lift.setPosition(preDropPos);
                    liftTimeout = opMode.time;
                    loopState = DROP_CONE_LIFT_MOVING_UP;
                }
                break;

            case DROP_CONE_LIFT_MOVING_UP:
                if (Math.abs(hardware.lift.getCurrentPos() - preDropPos) < 10) {
                    loopState = READY;
                } else if (opMode.time - liftTimeout > 2.0) {
                    opMode.telemetry.addLine("Lift Timed Out going to position " + preDropPos + "currently at" + hardware.lift.getCurrentPos());
                    hardware.logMessage(true, "Lift", "Lift Timed Out getting to position" + preDropPos + "current position is" + hardware.lift.getCurrentPos());
                }
                break;
        }
        opMode.telemetry.addLine("Robot State: " + loopListValues[loopState]);
        opMode.telemetry.update();
    }

    public void dropCone() {
        preDropPos = hardware.lift.getCurrentPos();
        dropPos = preDropPos - 250;
        hardware.lift.setPosition(dropPos);
        liftTimeout = opMode.time;
        loopState = DROP_CONE_LIFT_MOVING_DOWN;
    }
}
