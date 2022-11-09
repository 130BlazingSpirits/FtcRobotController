package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ErnieBot {
//    private OpMode opMode = null;
//    private Hardware hardware = null;
//    private ElapsedTime runtime = new ElapsedTime();
//    private ElapsedTime timeout = new ElapsedTime();
//
//    private              double transferTime = 0.0;
//    private static final int ARM_CLEAR_POS = 200;
//    private static final int ARM_TRANSFER_POS = 385;
//    private static final int ELEVATOR_TRANSFER_POS = 0;
//    private static final int TRANSFER_READY = 0;
//    public               int transferState = TRANSFER_READY;
//    private static final int WAIT_FOR_ELEV_TRANSFER_POS = 1;
//    private static final int WAIT_FOR_ARM_TRANSFER_POS = 2;
//    private static final int WAIT_FOR_BLOCK_DROP = 3;
//    private static final int WAIT_FOR_ARM_CLEAR = 4;
//    private static final int CONFIGURATION_ASK_TEAM_COLOUR = 1;
//    private static final int CONFIGURATION_ASK_STARTING_POSITION = 2;
//    private static final int CONFIGURATION_READY = 3;
//    public               int configurationState = CONFIGURATION_ASK_TEAM_COLOUR;
//    public boolean isRedTeam;
//    public double posFromCarousel; // Centre of robot distance in Feet
//
//    public ErnieBot(OpMode opMode, Hardware hardware) {
//        this.opMode = opMode;
//        this.hardware = hardware;
//    }
//
//    public void init(){
//        configurationState = CONFIGURATION_ASK_TEAM_COLOUR;
//        runtime.reset();
//        timeout.reset();
//    }
//
//    public void configure() {}
//
//    public void doConfigurationLoop(){
//        /*
//        switch (configurationState) {
//            case CONFIGURATION_ASK_TEAM_COLOUR:
//                opMode.telemetry.addLine("Team Colour? RED or BLUE button.");
//                opMode.telemetry.update();
//                if(hardware.gamepad2_current_x || hardware.gamepad1_current_x){
//                    isRedTeam = false;
//                    //((AutoOne)hardware.opMode).isRed = isRedTeam;
//                    configurationState = CONFIGURATION_ASK_STARTING_POSITION;}
//                else if(hardware.gamepad2_current_b || hardware.gamepad1_current_b){
//                    isRedTeam = true;
//                    //((AutoOne)hardware.opMode).isRed = isRedTeam;
//                    configurationState = CONFIGURATION_ASK_STARTING_POSITION;}
//                break;
//
//            case CONFIGURATION_ASK_STARTING_POSITION:
//                opMode.telemetry.addData("We Are Red?", isRedTeam);
//                opMode.telemetry.addLine("How many feet from Carousel? UP for +1 foot, DOWN for -1 foot");
//               if(hardware.gamepad1_current_dpad_up && !hardware.gamepad1_previous_dpad_up){
//                   posFromCarousel += 1.0;
////                   opMode.telemetry.addData("Parking Spot: ","We are " + posFromCarousel + " feet from the Carousel.");
////                   opMode.telemetry.update();
//               }
//                if (hardware.gamepad1_current_dpad_down && !hardware.gamepad1_previous_dpad_down) {
//                    posFromCarousel = posFromCarousel - 1.0;
//                }
//                if (hardware.gamepad1_current_a && !hardware.gamepad1_previous_a) {
//                    configurationState = CONFIGURATION_READY;
//                }
//                opMode.telemetry.addData("Parking Spot: ","We are " + posFromCarousel + " feet from the Carousel.");
//                opMode.telemetry.update();
//                break;
//
//            case CONFIGURATION_READY:
//
//                opMode.telemetry.addLine("Config Results:");
//                opMode.telemetry.addData("Team Colour is Red?", isRedTeam);
//                opMode.telemetry.addData("Robot is _ feet from carousel?", posFromCarousel);
//                opMode.telemetry.update();
//
//
//        }*/
//    }
//
//    public void doLoop() {
//
//        switch (transferState) {
//            case WAIT_FOR_ELEV_TRANSFER_POS:
//                if ((hardware.elevator.getCurrentPos() < (ELEVATOR_TRANSFER_POS + 100)) && ((opMode.time - transferTime) > 0.5)) {
//                    hardware.arm.setPosition(ARM_TRANSFER_POS, 0.6);
//                    transferTime = opMode.time;
//                    transferState = WAIT_FOR_ARM_TRANSFER_POS;
//                }
//                break;
//            case WAIT_FOR_ARM_TRANSFER_POS:
//                if ((hardware.arm.getCurrentPos() > (ARM_TRANSFER_POS - 20))) {
//                    hardware.claw.open();
//                    transferTime = opMode.time;
//                    transferState = WAIT_FOR_BLOCK_DROP;
//                }
//                break;
//            case WAIT_FOR_BLOCK_DROP:
//                if (opMode.time > transferTime + .35) {
//                    hardware.arm.setPosition(ARM_CLEAR_POS, 1.0);
//                    transferTime = opMode.time;
//                    transferState = WAIT_FOR_ARM_CLEAR;
//                }
//                break;
//            case WAIT_FOR_ARM_CLEAR:
//                if (hardware.arm.getCurrentPos() > ARM_CLEAR_POS + 20) {
//                    transferTime = opMode.time;
//                    transferState = TRANSFER_READY;
//                }
//        }
//    }
//
//    public void transferBlock(){
//        if(transferState == TRANSFER_READY){
//        hardware.claw.grip();
//        hardware.elevator.setPosition(ELEVATOR_TRANSFER_POS, 1.0);
//        hardware.cabin.transferPos();
//
//        hardware.arm.setPosition(ARM_CLEAR_POS, 0.6);
//
//        transferTime = opMode.time;
//        transferState = WAIT_FOR_ELEV_TRANSFER_POS;
//        }
//
//        opMode.telemetry.addData("transferState", transferState);
//    }
}