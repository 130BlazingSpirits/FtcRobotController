package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name="OpMode 2223", group="A")
public class OpMode2223 extends OpMode {
    private Hardware hardware = new Hardware();
    private MecanumDriveTrain drive = new MecanumDriveTrain(hardwareMap,hardware);
    private static final double STRAFE_POWER = 0.50;
    private double prevLPower = 0.0;
    private double prevRPower = 0.0;
    private boolean isAccelDriveMode = false;
    private Flipper selectedFlipper = null;
    private int liftTargetPosition = 0;
    private boolean liftManualMode = false;
    private int liftPreviousManualPosition = Lift.LIFT_MINPOS;
    private boolean flipperManualMode = false;
    private double flipperTargetPosition = 0;

    private RobotConfiguration robotConfiguration = null;

    private boolean isRed = false;
    private boolean isLeftStartingPos = false;

    private boolean isPreviousManualDrive = false;
    private boolean isCurrentManualDrive = false;

//    public boolean isManualDriveForward = false;
//    public boolean isManualDriveRotate = false;

    @Override
    public void init() {
        System.gc();

        hardware.init(hardwareMap, this);
        hardware.driveTrain.setSpeedMode();

        hardware.rightFlipper.goToStow();
        hardware.leftFlipper.goToStow();

        selectedFlipper = hardware.leftFlipper;

        robotConfiguration = new RobotConfiguration();
        robotConfiguration.readConfig();
        isRed = robotConfiguration.isRed;
        isLeftStartingPos = robotConfiguration.isLeftStartPos;

        telemetry.addLine("Configuration Fetched");
        telemetry.addData("Is Red?? ", isRed);
        telemetry.addData("Is Left Position? ", isLeftStartingPos);
        telemetry.update();
    }

    @Override
    public void init_loop() {
        hardware.updateValues();

        super.init_loop();

        hardware.init_loop();
    }

    @Override
    public void loop() {
        double targetLPower = 0.0;
        double targetRPower = 0.0;
        double desiredLPower = 0.0;
        double desiredRPower = 0.0;
        double targetLiftPower = 0.0;
        double desiredLiftPower = 0.0;
        double targetArmPower = 0.0;
        double desiredArmPower = 0.0;
        double targetSpinPower = 0.0;
        float game2LeftY = hardware.gamepad2_current_left_stick_y;
        float game2RightY = hardware.gamepad2_current_right_stick_y;
        float game1LeftY = hardware.gamepad1_current_left_stick_y;
        float game1LeftX = hardware.gamepad1_current_left_stick_x;
        float game1RightY = hardware.gamepad1_current_right_stick_y;
        float game1RightX = hardware.gamepad1_current_right_stick_x;
        double deltaExtension;
        double servoPower = 0;

        hardware.updateValues();
        //Flippers
        if (hardware.gamepad2_current_left_bumper) {
            selectedFlipper = hardware.leftFlipper;
            flipperManualMode = false;
        }
        if (hardware.gamepad2_current_right_bumper) {
            selectedFlipper = hardware.rightFlipper;
            flipperManualMode = false;
        }

        if (!flipperManualMode && Math.abs(hardware.gamepad2_current_left_stick_y) > 0.03) {
            flipperManualMode = true;
            flipperTargetPosition = selectedFlipper.getCurrentPosition() + ((selectedFlipper.needReverse ? 1 : -1) * hardware.gamepad2_current_left_stick_y * Flipper.MANUAL_SPEED * hardware.getDeltaTime());
            selectedFlipper.setPosition(flipperTargetPosition);
        } else if (flipperManualMode && Math.abs(hardware.gamepad2_current_left_stick_y) > 0.03) {
            flipperTargetPosition = selectedFlipper.getCurrentPosition() + ((selectedFlipper.needReverse ? 1 : -1) * hardware.gamepad2_current_left_stick_y * Flipper.MANUAL_SPEED * hardware.getDeltaTime());
            selectedFlipper.setPosition(flipperTargetPosition);
        } else if (flipperManualMode && Math.abs(hardware.gamepad2_current_left_stick_y) < 0.03) {
            flipperTargetPosition = selectedFlipper.getCurrentPosition();
            selectedFlipper.setPosition(flipperTargetPosition);
            flipperManualMode = false;
        }


        //Claw
        if (hardware.gamepad2_current_dpad_up && !hardware.gamepad2_previous_dpad_up) {
            hardware.claw.grip();
        } else if (hardware.gamepad2_current_dpad_down && !hardware.gamepad2_previous_dpad_down) {
            hardware.robo130.dropCone();
        } else if (hardware.gamepad2_current_dpad_left && !hardware.gamepad2_previous_dpad_left) {
            hardware.claw.open();
        } else if (hardware.gamepad2_current_dpad_right && !hardware.gamepad2_previous_dpad_right) {
            hardware.claw.stow();
        }

        //Lift  (!hardware.gamepad2_current_start is there because 'a' & 'b' needed protection from switching between gamepads)
        if (hardware.gamepad2_current_a && !hardware.gamepad2_previous_a && !hardware.gamepad2_current_start) {
            hardware.lift.goToGround();
        }
        if (hardware.gamepad2_current_x && !hardware.gamepad2_previous_x) {
            hardware.lift.goToLow();
        }
        if (hardware.gamepad2_current_y && !hardware.gamepad2_previous_y) {
            hardware.lift.goToMed();
        }
        if (hardware.gamepad2_current_b && !hardware.gamepad2_previous_b && !hardware.gamepad2_current_start) {
            hardware.lift.goToHigh();
        }
        if (hardware.gamepad2_current_right_stick_button && !hardware.gamepad2_previous_right_stick_button) {
            hardware.lift.calibrateLift();
        }
        //Lift Manual Controls
        if (!liftManualMode && Math.abs(hardware.gamepad2_current_right_stick_y) > 0.03) {
            liftManualMode = true;
            liftPreviousManualPosition = hardware.lift.getCurrentPos();
            liftTargetPosition = liftPreviousManualPosition + (int) (-1 * hardware.gamepad2_current_right_stick_y * Lift.LIFT_MANUAL_SPEED * hardware.getDeltaTime());
            liftPreviousManualPosition = liftTargetPosition;
            hardware.lift.setPosition(liftTargetPosition);
        } else if (liftManualMode && Math.abs(hardware.gamepad2_current_right_stick_y) > 0.03) {
            liftTargetPosition = liftPreviousManualPosition + (int) (-1 * hardware.gamepad2_current_right_stick_y * Lift.LIFT_MANUAL_SPEED * hardware.getDeltaTime());
            liftPreviousManualPosition = liftTargetPosition;
            hardware.lift.setPosition(liftTargetPosition);
        } else if (liftManualMode && Math.abs(hardware.gamepad2_current_right_stick_y) < 0.03) {
            liftPreviousManualPosition = hardware.lift.getCurrentPos();
            liftTargetPosition = liftPreviousManualPosition;
            hardware.lift.setPosition(liftTargetPosition);
            liftManualMode = false;
        }

        //Driving Controls
        if (hardware.gamepad1_current_dpad_up & !hardware.gamepad1_previous_dpad_up) {
            hardware.driveTrain.forwardTile();
        } else if (hardware.gamepad1_current_dpad_down & !hardware.gamepad1_previous_dpad_down) {
            hardware.driveTrain.reverseTile();
        } else if (hardware.gamepad1_current_left_bumper & !hardware.gamepad1_previous_left_bumper) {
            hardware.driveTrain.tileTurnCCW();
        } else if (hardware.gamepad1_current_right_bumper & !hardware.gamepad1_previous_right_bumper) {
            hardware.driveTrain.tileTurnCW();
        }

//        if (hardware.driveTrain.isMecanum && hardware.gamepad1_current_left_bumper && !hardware.gamepad1_previous_left_bumper) {
//            // Sliiide to the left!
//            hardware.driveTrain.goLeft(STRAFE_POWER); //TODO SWITCH THIS WITH BELOW ASAP!!!!!!
//            //hardware.driveTrain.goSpeedLeft(STRAFE_POWER);
//        } else if (hardware.driveTrain.isMecanum && hardware.gamepad1_current_right_bumper && !hardware.gamepad1_previous_right_bumper) {
//            // Sliiide to the right!
//            hardware.driveTrain.goRight(STRAFE_POWER); //TODO SWITCH THIS WITH ABOVE ASAP!!!!!!
//            //hardware.driveTrain.goSpeedRight(STRAFE_POWER);
//        }
        /*else if (((hardware.gamepad1_previous_right_bumper) && (!hardware.gamepad1_current_right_bumper))
                || (((hardware.gamepad1_previous_left_bumper) && (!hardware.gamepad1_current_left_bumper)))){
            hardware.driveTrain.stopMotors();
        }*/

//        else if (!hardware.gamepad1_current_left_bumper && !hardware.gamepad1_current_right_bumper) {
//            // Tank drive! *doomph! doomph!
//            //targetLPower = Math.pow(-game1LeftY,3)/Math.abs(game1LeftY);
//            //targetRPower = Math.pow(-game1RightY, 3)/Math.abs(game1RightY);
//            if (hardware.driveTrain.isTank) {
//                desiredLPower = (Math.pow(-game1LeftY, 3) / Math.abs(game1LeftY));
//                desiredRPower = (Math.pow(-game1RightY, 3) / Math.abs(game1RightY));
//            } else if ((Math.abs(game1RightX) < 0.02) && (Math.abs(game1LeftY) > 0.02)) { //non tank drive + no rotation
//                isCurrentManualDrive = true;
//                desiredLPower = (Math.pow(-game1LeftY, 3) / Math.abs(game1LeftY));
//                desiredRPower = (Math.pow(-game1LeftY, 3) / Math.abs(game1LeftY));
//            } else if (Math.abs(game1RightX) > .02 && Math.abs(game1LeftY) < .02) { //non tank drive + no forward
//                isCurrentManualDrive = true;
//                desiredLPower = (-1.0 * Math.pow(-game1RightX, 3) / Math.abs(game1RightX));
//                desiredRPower = (Math.pow(-game1RightX, 3) / Math.abs(game1RightX));
//            }
//
//            if (Math.abs(desiredLPower) < 0.02) {
//                targetLPower = 0.0;
//                isCurrentManualDrive = false;
//            } else {
//                targetLPower = desiredLPower;
//            }
//
//            if (Math.abs(desiredRPower) < 0.02) {
//                targetRPower = 0.0;
//                isCurrentManualDrive = false;
//            } else {
//                targetRPower = desiredRPower;
//            }
//
//            //hardware.driveTrain.goTankDrive(targetLPower, targetRPower);  //pre 2023 drive command using speed control
//            if(!(hardware.robo130.getCurrentCommand() instanceof RCDriveCommand)){
//                hardware.driveTrain.goTankPositionDrive(targetLPower, targetRPower, !(!isPreviousManualDrive && isCurrentManualDrive));
//            }
//            isPreviousManualDrive = isCurrentManualDrive;
//        }

//        if (gamepad2.dpad_left) {
//
//        }
//
//        // Gas Pedal
//        if (hardware.gamepad1_current_left_trigger < 0.05 && hardware.gamepad1_current_right_trigger < 0.05) {
//            hardware.driveTrain.setGasPedalPower(1.0);
//        } else if (hardware.gamepad1_current_left_trigger > 0.5) {
//            hardware.driveTrain.setGasPedalPower(Math.max(1.0 - hardware.gamepad1_current_left_trigger, 0.15));
//        } else if (hardware.gamepad1_current_right_trigger > 0.5) {
//            hardware.driveTrain.setGasPedalPower(Math.max(1.0 - hardware.gamepad1_current_right_trigger, 0.6));
//        }

        drive.setWeightedDrivePower(new Pose2d(hardware.gamepad1_current_left_stick_x, -hardware.gamepad1_current_left_stick_y, -hardware.gamepad1_current_right_stick_x));

        //Commands
        if(hardware.gamepad1_current_x && !hardware.gamepad1_previous_x){
            hardware.robo130.cancelFutureCommands(); //XXX LOOK AT THIS LATER, THIS WILL PROBABLY BREAK THE ROBOT IN THE FUTURE LOL
        }

        hardware.robo130.processCommands();

        hardware.loop();

        prevLPower = targetLPower;
        prevRPower = targetRPower;

        telemetry.addData("Lift Home isPressed", hardware.liftHomeButton.isPressed());
//        telemetry.addData("Front Distance", hardware.frontDistance.getDistance(DistanceUnit.INCH));
//        telemetry.addData("Rear Distance", hardware.rearDistance.getDistance(DistanceUnit.INCH));
        telemetry.addData("Delta Time", hardware.getDeltaTime());
        telemetry.addData("Selected Flipper: ", selectedFlipper.name);
        telemetry.addData("Commands: ", hardware.robo130.getNumCommands());
        telemetry.addData("Current Command: ", hardware.robo130.getCurrentCommandIndex());
        telemetry.addData("Next Command: ", hardware.robo130.getNextCommandIndex());
        telemetry.addData("Status", "Running");
        telemetry.update();
    }

    @Override
    public void stop() {
        hardware.updateValues();

        hardware.logMessage(false, "OpMode2223", "Stop Button Pressed");
        hardware.stop();
        super.stop();
    }

    @Override
    public void start() {
        hardware.updateValues();
        hardware.logMessage(false, "OpMode2223", "Start Button Pressed");
        super.start();
        hardware.start();
        hardware.claw.open();
        hardware.lift.calibrateLift();
    }
}