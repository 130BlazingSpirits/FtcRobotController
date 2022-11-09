package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name="AutoOne", group="auto")
public class AutoOne extends OpMode {
    //Positions
    private              String HOLD = "hold";
    private              String DROP = "drop";
    private              int leftPos = 0;
    private              int midPos = 1;

    public               int currentRobotCommand = -1;
    public               int scoringPieceAt = 2; //0 is left, 1 is centre, 2 is right.
    private              int midLayerLevel = 0; //Middle layer position
    private              int topLayerLevel = 0; //Top layer position
    public               int autoState = 0;
    private static final int LEFT_SPACE_CHECK = 1;
    private static final int CENTRE_SPACE_CHECK = 2;
    private static final int GET_TO_HUB = 3;
    private static final int PLACE_ELEMENT = 4;
    private static final int GO_TO_CAROUSEL = 5;
    private static final int SPIN_CAROUSEL = 6;
    private static final int PARK_IN_TEAM_DEPOT = 7;
    public boolean isRed=true;
    public boolean atCarousel=true;
    public double warehouseDelay=13.0;
    private Hardware hardware = new Hardware();
    private double currentTime = this.time;
    public List<RobCommand> robotCommands = new ArrayList<RobCommand>();

    //TODO: check leftmost space. Check centre space. Go to scoring hub. Go to carousel. Spin at carousel. Park in personal hub.

    private static final double STRAFE_POWER = 1.0;

    @Override
    public void init() {
        hardware.init(hardwareMap,this);


/*
        hardware.motorLFront.setMotorEnable();
        hardware.motorLBack.setMotorEnable();
        hardware.motorRFront.setMotorEnable();
        hardware.motorRBack.setMotorEnable();
        hardware.elevatorMotor.setMotorEnable();

        hardware.motorLFront.setPower(0.0);
        hardware.motorLBack.setPower(0.0);
        hardware.motorRFront.setPower(0.0);
        hardware.motorRBack.setPower(0.0);
        hardware.elevatorMotor.setPower(0.0);

        hardware.motorLFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.motorLBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.motorRFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.motorRBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        currentRobotCommand = -1;*/
/*
        //Red Warehouse Side
        robotCommands.add(new RCCabin(hardware, HOLD)); //keep it from falling
        robotCommands.add(new RCDriveForward(hardware, -13, 1));
        robotCommands.add(new RCWait(hardware, 0.1));
        robotCommands.add(new RCStrafeRight(hardware,-3.5, 0.5));
        robotCommands.add(new RCWait(hardware, 0.15));
        robotCommands.add(new RCCheckElement(hardware, leftPos));
        robotCommands.add(new RCStrafeRight(hardware, -8.375, 0.5));
        robotCommands.add(new RCWait(hardware, 0.15)); //check left and centre zones
        robotCommands.add(new RCCheckElement(hardware, midPos));
        robotCommands.add(new RCStrafeRight(hardware, 17, 0.5)); //go to hub
//        robotCommands.add(new RCCabin(hardware, HOLD)); //keep it from falling
//        robotCommands.add(new RCDriveForward(hardware, -13, 1));
//        robotCommands.add(new RCWait(hardware, 0.15)); //check left and centre zones
//        robotCommands.add(new RCCheckElement(hardware, midPos));
//        robotCommands.add(new RCStrafeRight(hardware,8.375, 0.5));
//        robotCommands.add(new RCWait(hardware, 0.15));
//        robotCommands.add(new RCCheckElement(hardware, leftPos));
//        robotCommands.add(new RCStrafeRight(hardware, 11.625, 0.5)); //go to hub
        robotCommands.add(new RCWait(hardware, 0.1));
        robotCommands.add(new RCDriveForwardUntil(hardware, -12,.25, 0.5, 2.0));
        robotCommands.add(new RCLiftElevator(hardware, 10, 1.0));
        robotCommands.add(new RCCabin(hardware, DROP));
        robotCommands.add(new RCWait(hardware, 0.4));
        robotCommands.add(new RCCabin(hardware, HOLD));
        robotCommands.add(new RCWait(hardware, 0.75));
        robotCommands.add(new RCDriveForward(hardware, 8.5, 1.0));
        robotCommands.add(new RCWait(hardware, 0.1));
        robotCommands.add(new RCTurnCounterClockwise(hardware, 90, 0.5));
        robotCommands.add(new RCWait(hardware, 0.1));
        robotCommands.add(new RCDriveForward(hardware, 71, 1.0));
*/
        isRed = true;
        atCarousel = true;
        warehouseDelay = 13.0;

        warehouseDelay = Math.min(13.0, Math.max(.150, warehouseDelay));

        if(isRed){
            if(atCarousel){
                // red carousel
                telemetry.addData("Going to run", "red carousel");

            }
            else{
                // red warehouse
                telemetry.addData("Going to run", "red warehouse");
            }
        }
        else{
            if(atCarousel){
                // blue carousel
                telemetry.addData("Going to run", "blue carousel");
            }
            else{
                // blue warehouse
                telemetry.addData("Going to run", "blue warehouse");
            }
        }

        telemetry.addData("isRed", isRed);
        telemetry.addData("atCarousel", atCarousel);
        telemetry.addData("warehouseDelay", warehouseDelay);

/*
        if(isRed) { //red
            //Red Carousel Side
            robotCommands.add(new RCCabin(hardware, HOLD)); //keep it from falling
            robotCommands.add(new RCDriveForward(hardware, -13, 1)); //
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCStrafeRight(hardware, -4.75, 0.5));
            robotCommands.add(new RCWait(hardware, 0.15));
            robotCommands.add(new RCCheckElement(hardware, leftPos));
            robotCommands.add(new RCStrafeRight(hardware, -8.375, 0.5));
            robotCommands.add(new RCWait(hardware, 0.15)); //check left and centre zones
            robotCommands.add(new RCCheckElement(hardware, midPos));
            robotCommands.add(new RCStrafeRight(hardware, -21, 0.5)); //go to hub
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForwardUntil(hardware, -12, .25, 0.5, 2.0));
            robotCommands.add(new RCLiftElevator(hardware, 10, 1.0));
            robotCommands.add(new RCCabin(hardware, DROP));
            robotCommands.add(new RCWait(hardware, 0.4));
            robotCommands.add(new RCCabin(hardware, HOLD));
            robotCommands.add(new RCWait(hardware, 0.75));
            robotCommands.add(new RCDriveForward(hardware, 9, 1.0));
            robotCommands.add(new RCWait(hardware, 0.10));
            robotCommands.add(new RCTurnCounterClockwise(hardware, -90, 1.0));
            robotCommands.add(new RCWait(hardware, 0.1));
            //robotCommands.add(new RCStrafeRight(hardware, -9, 0.5));
            robotCommands.add(new RCStrafeRight(hardware, -17, 0.4));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCStrafeRight(hardware, 6, 0.5));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForward(hardware, 47, 1.0));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForward(hardware, 5, 0.25));
            robotCommands.add(new RCTurntable(hardware, 3, -0.25));
            robotCommands.add(new RCStrafeRight(hardware, 16, 0.5));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForward(hardware, 9, 0.2));
        }
        else if (isRed){


            //Blue Carousel Side
            robotCommands.add(new RCCabin(hardware, HOLD)); //keep it from falling
            robotCommands.add(new RCDriveForward(hardware, -14, 1)); //
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCStrafeRight(hardware, 3.5, 0.5));
            robotCommands.add(new RCWait(hardware, 0.15));
            robotCommands.add(new RCCheckElement(hardware, leftPos));
            robotCommands.add(new RCStrafeRight(hardware, 8.375, 0.5));
            robotCommands.add(new RCWait(hardware, 0.15)); //check left and centre zones
            robotCommands.add(new RCCheckElement(hardware, midPos));
            robotCommands.add(new RCStrafeRight(hardware, 19, 0.5)); //go to hub
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForwardUntil(hardware, -15, .25, 0.5, 2.0));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCLiftElevator(hardware, 10, 1.0));
            robotCommands.add(new RCCabin(hardware, DROP));
            robotCommands.add(new RCWait(hardware, 0.4));
            robotCommands.add(new RCCabin(hardware, HOLD));
            robotCommands.add(new RCWait(hardware, 0.75));
            robotCommands.add(new RCDriveForward(hardware, 33, 0.4));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForward(hardware, -6, 0.5));
            robotCommands.add(new RCWait(hardware, 0.10));
            robotCommands.add(new RCStrafeRight(hardware, -26, 1.0));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForward(hardware, 10, 0.4));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForward(hardware, -15, 0.5));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCStrafeRight(hardware, -14, 0.5));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForward(hardware, 8, 0.5));
            robotCommands.add(new RCTurntable(hardware,6, 0.25));
            robotCommands.add(new RCWait(hardware, 0.1));
            robotCommands.add(new RCDriveForward(hardware, -20, 0.7));
        }
    */

    }

    @Override
    public void init_loop() {
        hardware.updateValues();

        super.init_loop();
     //   hardware.lift.doInitLoop();

        hardware.init_loop();

        if(hardware.gamepad2_current_x || hardware.gamepad1_current_x){
            isRed = false;
            }
        else if(hardware.gamepad2_current_b || hardware.gamepad1_current_b){
            isRed = true;
            }

        if(hardware.gamepad2_current_y || hardware.gamepad1_current_y){
            atCarousel = true;
        }
        else if(hardware.gamepad2_current_a || hardware.gamepad1_current_a){
            atCarousel = false;
        }

        if(hardware.gamepad2_current_dpad_up || hardware.gamepad1_current_dpad_up){
            warehouseDelay = 13.0;
        }
        else if(hardware.gamepad2_current_dpad_down || hardware.gamepad1_current_dpad_down){
            warehouseDelay = 0;
        }

        telemetry.addLine("Which team? RED or BLUE button.");
        telemetry.addLine("Which position? Yarousel or wArehouse.");
        telemetry.addLine("What delay? All the way UP or all the way DOWN.");
        if(isRed){
            telemetry.addData("Team Color", "Red");
        }
        else {
            telemetry.addData("Team Color", "Blue");
        }

        if(atCarousel){
            telemetry.addData("Starting at", "Carousel");
        }
        else {
            telemetry.addData("Starting at", "Warehouse");
            telemetry.addData("Warehouse Delay", warehouseDelay);
        }
    }

    @Override
    public void loop() {
        hardware.updateValues();
        if(currentRobotCommand == -1){
            currentRobotCommand = 0;
            robotCommands.get(currentRobotCommand).run();
        }
        else if(currentRobotCommand < robotCommands.size()){
            if(robotCommands.get(currentRobotCommand).isComplete()){
                currentRobotCommand++;
                if(currentRobotCommand < robotCommands.size()) {
                    robotCommands.get(currentRobotCommand).run();
                }
            }
        }
        //telemetry.addData("Status","Running");
        telemetry.addData("isRed?", isRed);
        telemetry.addData("Scoring Piece Position", scoringPieceAt);
        telemetry.addData("distance", hardware.rearDistance.getDistance(DistanceUnit.INCH));
        telemetry.update();
    }

    @Override
    public void stop() {
        hardware.updateValues();

        hardware.logMessage(false, "MyFirstJava", "Stop Button Pressed");
        hardware.stop();
        super.stop();
    }

    @Override
    public void start() {
        //hardware.updateValues();

        super.start();
        hardware.start();

        hardware.motorLFront.setMotorEnable();
        hardware.motorLBack.setMotorEnable();
        hardware.motorRFront.setMotorEnable();
        hardware.motorRBack.setMotorEnable();
        hardware.elevatorMotor.setMotorEnable();

        hardware.motorLFront.setPower(0.0);
        hardware.motorLBack.setPower(0.0);
        hardware.motorRFront.setPower(0.0);
        hardware.motorRBack.setPower(0.0);
        hardware.elevatorMotor.setPower(0.0);

        hardware.motorLFront.setTargetPosition(0);
        hardware.motorLBack.setTargetPosition(0);
        hardware.motorRFront.setTargetPosition(0);
        hardware.motorRBack.setTargetPosition(0);
        hardware.elevatorMotor.setTargetPosition(0);

        hardware.motorLFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.motorLBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.motorRFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.motorRBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        currentRobotCommand = -1;

        //isRed = false;
        //atCarousel = true;
//        warehouseDelay = 13.0;

        warehouseDelay = Math.min(13.0, Math.max(.150, warehouseDelay));

        if(isRed){
            if(atCarousel){
                // red carousel
                telemetry.addData("Going to run", "red carousel");
                robotCommands.add(new RCCabin(hardware, HOLD)); //keep it from falling
                robotCommands.add(new RCWait(hardware, 2));
                robotCommands.add(new RCDriveForward(hardware, -13, 1)); //
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCStrafeRight(hardware, -3.75, 0.5));
                robotCommands.add(new RCWait(hardware, 0.15));
                robotCommands.add(new RCCheckElement(hardware, leftPos));
                robotCommands.add(new RCStrafeRight(hardware, -8.375, 0.5));
                robotCommands.add(new RCWait(hardware, 0.15)); //check left and centre zones
                robotCommands.add(new RCCheckElement(hardware, midPos));
                robotCommands.add(new RCStrafeRight(hardware, -20, 0.5)); //go to hub
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForwardUntil(hardware, -8, .25, 0.5, 2.0));
                robotCommands.add(new RCLiftElevator(hardware, 10, 1.0));
                robotCommands.add(new RCCabin(hardware, DROP));
                robotCommands.add(new RCWait(hardware, 0.35));
                robotCommands.add(new RCCabin(hardware, HOLD));
                robotCommands.add(new RCWait(hardware, 0.75));
                robotCommands.add(new RCDriveForward(hardware, 9, 1.0));
                robotCommands.add(new RCWait(hardware, 0.10));
                robotCommands.add(new RCTurnCounterClockwise(hardware, -90, 0.5));
                robotCommands.add(new RCWait(hardware, 0.15));
                //robotCommands.add(new RCStrafeRight(hardware, -9, 0.5));
                robotCommands.add(new RCStrafeRight(hardware, -19, 0.4));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCStrafeRight(hardware, 7, 0.5));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, 47, 0.5));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, 5, 0.25));
                robotCommands.add(new RCTurntable(hardware, 3, -0.25));
                robotCommands.add(new RCStrafeRight(hardware, 19, 0.5));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, 6, 0.2));
                robotCommands.add(new RCLiftElevator(hardware, 0, 1.0));
                }
            else{
                // red warehouse
                telemetry.addData("Going to run", "red warehouse");
                robotCommands.add(new RCCabin(hardware, HOLD)); //keep it from falling
                robotCommands.add(new RCDriveForward(hardware, -13, 1));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCStrafeRight(hardware,-3.5, 0.5));
                robotCommands.add(new RCWait(hardware, .15));
                robotCommands.add(new RCCheckElement(hardware, leftPos));
                robotCommands.add(new RCStrafeRight(hardware, -8.375, 0.5));
                robotCommands.add(new RCWait(hardware, warehouseDelay)); //check left and centre zones
                robotCommands.add(new RCCheckElement(hardware, midPos));
                robotCommands.add(new RCStrafeRight(hardware, 19, 0.5)); //go to hub
//        robotCommands.add(new RCCabin(hardware, HOLD)); //keep it from falling
//        robotCommands.add(new RCDriveForward(hardware, -13, 1));
//        robotCommands.add(new RCWait(hardware, 0.15)); //check left and centre zones
//        robotCommands.add(new RCCheckElement(hardware, midPos));
//        robotCommands.add(new RCStrafeRight(hardware,8.375, 0.5));
//        robotCommands.add(new RCWait(hardware, 0.15));
//        robotCommands.add(new RCCheckElement(hardware, leftPos));
//        robotCommands.add(new RCStrafeRight(hardware, 11.625, 0.5)); //go to hub
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForwardUntil(hardware, -12,.25, 0.5, 2.0));
                robotCommands.add(new RCLiftElevator(hardware, 10, 1.0));
                robotCommands.add(new RCCabin(hardware, DROP));
                robotCommands.add(new RCWait(hardware, 0.35));
                robotCommands.add(new RCCabin(hardware, HOLD));
                robotCommands.add(new RCWait(hardware, 0.75));
                robotCommands.add(new RCDriveForward(hardware, 8.5, 1.0));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCTurnCounterClockwise(hardware, 90, 0.5));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, 71, 1.0));
                robotCommands.add(new RCLiftElevator(hardware, 0, 1.0));
            }
        }
        else{
            if(atCarousel){
                // blue carousel
                telemetry.addData("Going to run", "blue carousel");
                robotCommands.add(new RCCabin(hardware, HOLD)); //keep it from falling
                robotCommands.add(new RCDriveForward(hardware, -14, .5)); //
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCStrafeRight(hardware, 3.5, 0.5));
                robotCommands.add(new RCWait(hardware, 0.15));
                robotCommands.add(new RCCheckElement(hardware, leftPos));
                robotCommands.add(new RCStrafeRight(hardware, 7.375, 0.5));
                robotCommands.add(new RCWait(hardware, 0.15)); //check left and centre zones
                robotCommands.add(new RCCheckElement(hardware, midPos));
                robotCommands.add(new RCStrafeRight(hardware, 21, 0.5)); //go to hub
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForwardUntil(hardware, -15, .25, 0.5, 2.0));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCLiftElevator(hardware, 10, 1.0));
                robotCommands.add(new RCCabin(hardware, DROP));
                robotCommands.add(new RCWait(hardware, 0.35));
                robotCommands.add(new RCCabin(hardware, HOLD));
                robotCommands.add(new RCWait(hardware, 0.75));
                robotCommands.add(new RCDriveForward(hardware, 33, 0.3));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, -5, 0.5));
                robotCommands.add(new RCWait(hardware, 0.10));
                robotCommands.add(new RCStrafeRight(hardware, -26, 0.5));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, 10, 0.3));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, -15, 0.5));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCStrafeRight(hardware, -14, 0.5));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, 8, 0.5));
                robotCommands.add(new RCTurntable(hardware,5, 0.25));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, -20, 0.5));
                robotCommands.add(new RCStrafeRight(hardware, -4, 0.5));
                robotCommands.add(new RCLiftElevator(hardware, 0, 1.0));
                }
            else{
                // blue warehouse
                telemetry.addData("Going to run", "blue warehouse");
                robotCommands.add(new RCCabin(hardware, HOLD)); //keep it from falling
                robotCommands.add(new RCDriveForward(hardware, -13, 1));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCStrafeRight(hardware,3.5, 0.5));
                robotCommands.add(new RCWait(hardware, .15));
                robotCommands.add(new RCCheckElement(hardware, leftPos));
                robotCommands.add(new RCStrafeRight(hardware, 8.375, 0.5));
                robotCommands.add(new RCWait(hardware, warehouseDelay)); //check left and centre zones
                robotCommands.add(new RCCheckElement(hardware, midPos));
                robotCommands.add(new RCStrafeRight(hardware, -21, 0.5)); //go to hub
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForwardUntil(hardware, -12,.25, 0.5, 2.0));
                robotCommands.add(new RCLiftElevator(hardware, 10, 1.0));
                robotCommands.add(new RCCabin(hardware, DROP));
                robotCommands.add(new RCWait(hardware, 0.4));
                robotCommands.add(new RCCabin(hardware, HOLD));
                robotCommands.add(new RCWait(hardware, 0.75));
                robotCommands.add(new RCDriveForward(hardware, 10, 1.0));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCTurnCounterClockwise(hardware, -90, 0.5));
                robotCommands.add(new RCWait(hardware, 0.1));
                robotCommands.add(new RCDriveForward(hardware, 71, 1.0));
                robotCommands.add(new RCLiftElevator(hardware, 0, 1.0));
            }
        }

        telemetry.addData("isRed", isRed);
        telemetry.addData("atCarousel", atCarousel);
        telemetry.addData("warehouseDelay", warehouseDelay);

    }
}