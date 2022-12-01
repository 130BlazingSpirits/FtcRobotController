package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class OpMode2223 extends OpMode {
    private Hardware hardware = new Hardware();

    private static final double STRAFE_POWER = 0.50;
    private double prevLPower = 0.0;
    private double prevRPower = 0.0;
    private boolean isAccelDriveMode = false;
    private boolean gamepad1RightTriggerPreviouslyPressed = false;

    int targetPosition = 0;

    @Override
    public void init() {

        hardware.init(hardwareMap,this);
        hardware.driveTrain.setSpeedMode();
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
            float game1RightY = hardware.gamepad1_current_right_stick_y;
            double deltaExtension;
            double rightTriggerPosition = gamepad2.right_trigger;
            double leftTriggerPosition = gamepad2.left_trigger;
            double servoPower = 0;


            hardware.updateValues();

            //Testing
            if(hardware.gamepad1_current_a & !hardware.gamepad1_previous_a){
                hardware.lift.calibrateLift();
            }

            if(hardware.gamepad1_current_left_stick_y > 0.03) {
                targetPosition = hardware.lift.getCurrentPos() + 250;
            }else if(hardware.gamepad1_current_left_stick_y < -0.03) {
                targetPosition = hardware.lift.getCurrentPos() - 250;
            }
            hardware.lift.setPosition(targetPosition);

            if(hardware.gamepad1_current_dpad_left && !hardware.gamepad1_previous_dpad_left){
                isAccelDriveMode = false;
            }
            else if(hardware.gamepad1_current_dpad_right && !hardware.gamepad1_previous_dpad_right){
                isAccelDriveMode = false;
            }

            if (hardware.driveTrain.isMecanum && hardware.gamepad1_current_left_bumper && !hardware.gamepad1_previous_left_bumper) {
                // Sliiide to the left!
                hardware.driveTrain.goLeft(STRAFE_POWER); //TODO SWITCH THIS WITH BELOW ASAP!!!!!!
                //hardware.driveTrain.goSpeedLeft(STRAFE_POWER);
            }
            else if (hardware.driveTrain.isMecanum && hardware.gamepad1_current_right_bumper && !hardware.gamepad1_previous_right_bumper) {
                // Sliiide to the right!
                hardware.driveTrain.goRight(STRAFE_POWER); //TODO SWITCH THIS WITH ABOVE ASAP!!!!!!
                //hardware.driveTrain.goSpeedRight(STRAFE_POWER);
            }
            /*else if (((hardware.gamepad1_previous_right_bumper) && (!hardware.gamepad1_current_right_bumper))
                    || (((hardware.gamepad1_previous_left_bumper) && (!hardware.gamepad1_current_left_bumper)))){
                hardware.driveTrain.stopMotors();
            }*/
            else if (!hardware.gamepad1_current_left_bumper && !hardware.gamepad1_current_right_bumper){
                // Tank drive! *doomph! doomph!
                //targetLPower = Math.pow(-game1LeftY,3)/Math.abs(game1LeftY);
                //targetRPower = Math.pow(-game1RightY, 3)/Math.abs(game1RightY);
                if(hardware.driveTrain.isTank)
                {
                    desiredLPower = (Math.pow(-game1LeftY,3)/Math.abs(game1LeftY))*0.7;
                    desiredRPower = (Math.pow(-game1RightY, 3)/Math.abs(game1RightY))*0.7;
                }else{
                    desiredLPower = (Math.pow(-game1RightY,3)/Math.abs(game1RightY))*0.7;
                    desiredRPower = (Math.pow(-game1RightY, 3)/Math.abs(game1RightY))*0.7;
                }

                if(Math.abs(desiredLPower) < 0.02) {
                    targetLPower = 0.0;
                }
                else {
                    targetLPower = desiredLPower;
                }

                if(Math.abs(desiredRPower) < 0.02) {
                    targetRPower = 0.0;
                }
                else {
                    targetRPower = desiredRPower;
                }

                if(isAccelDriveMode) {
                    hardware.driveTrain.goSpeedTankDrive(targetLPower * DriveTrain.MAX_LINEAR_VELOCITY,
                            targetRPower * DriveTrain.MAX_LINEAR_VELOCITY);
                }
                else {
                    hardware.driveTrain.goTankDrive(targetLPower, targetRPower);
                }
            }

            if(gamepad2.dpad_left) {

            }

            //Claw Controls
            if(hardware.gamepad2_current_dpad_up && !hardware.gamepad2_previous_dpad_up){
                hardware.claw.grip();
            }
            else if(hardware.gamepad2_current_dpad_down && !hardware.gamepad2_previous_dpad_down){
                hardware.claw.open();
            }

            //Elevator Motor
            /*desiredLiftPower = Math.pow(-game2LeftY,3)/Math.abs(game2LeftY);
            if(Math.abs(desiredLiftPower) < 0.02) {
                targetLiftPower = 0.0;
            }
            else {
                targetLiftPower = desiredLiftPower;
            }
            if(hardware.ernie.transferState == 0){
                hardware.elevator.goElevatorLift(targetLiftPower);
            }
             */

//            if(gamepad1.right_trigger > 0.10 && !gamepad1RightTriggerPreviouslyPressed){
//                gamepad1RightTriggerPreviouslyPressed = true;
//            }
//            else if (gamepad1.right_trigger < 0.05){
//                gamepad1RightTriggerPreviouslyPressed = false;
//            }

            // Gas Pedal
            if(hardware.gamepad1_current_left_trigger < 0.05 && hardware.gamepad1_current_right_trigger < 0.05) {
                hardware.driveTrain.setGasPedalPower(1.0);
            }
            else if(hardware.gamepad1_current_left_trigger  > 0.5){
                hardware.driveTrain.setGasPedalPower(Math.max(1.0 - hardware.gamepad1_current_left_trigger, 0.15));
            }else if(hardware.gamepad1_current_right_trigger > 0.5){
                hardware.driveTrain.setGasPedalPower(Math.max(1.0 - hardware.gamepad1_current_right_trigger, 0.6));
            }

            if(hardware.gamepad1_current_a && !hardware.gamepad1_previous_a){

            }
            else if(hardware.gamepad1_current_b && !hardware.gamepad1_previous_b){

            }

            if(Math.abs(gamepad2.left_stick_y) > 0.05){

            }

            if(Math.abs(gamepad2.right_stick_y) > 0.05){

            }

//            if(hardware.gamepad2_current_dpad_left && !hardware.gamepad2_previous_dpad_left) {
//                hardware.ernie.transferBlock();
//            }

            if(hardware.gamepad1_current_dpad_up){

            }
            else if(hardware.gamepad1_current_dpad_down){

            }
            else{

            }

            if(hardware.gamepad1_current_x && !hardware.gamepad1_previous_x){
            }

            hardware.loop();

            prevLPower = targetLPower;
            prevRPower = targetRPower;

            telemetry.addData("Lift Home isPressed", hardware.liftHome.isPressed());
            telemetry.addData("Delta Time", hardware.getDeltaTime());
//            telemetry.addData("is red", hardware.ernie.isRedTeam);
            telemetry.addData("Status","Running");
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
        hardware.updateValues();

        hardware.logMessage(false, "MyFirstJava", "Start Button Pressed");
        super.start();
        hardware.start();
    }


}