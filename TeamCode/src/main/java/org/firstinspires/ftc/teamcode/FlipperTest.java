package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="FlipperTest", group="tests")

public class FlipperTest extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Hardware hardware = new Hardware();
    private double flipperPosition = 0.5;
    private Flipper selectedFlipper = null;

    private double positionIncrement = .1;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");
        hardware.init(hardwareMap,this);

        hardware.leftFlipper.setCalibrationRange();
        hardware.rightFlipper.setCalibrationRange();
        hardware.leftFlipper.setPosition(0.5);
        hardware.rightFlipper.setPosition(0.5);
        hardware.claw.open();

        selectedFlipper = hardware.leftFlipper;

        telemetry.addData("Claw Servo Position", flipperPosition);
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        hardware.updateValues();

        super.init_loop();

        hardware.init_loop();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        hardware.updateValues();

        hardware.logMessage(false, "MyFirstJava", "Start Button Pressed");
        super.start();
        hardware.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        hardware.updateValues();

        //Select Flippers
        if (hardware.gamepad1_current_left_bumper) {
            flipperPosition = 0.5;
            selectedFlipper = hardware.leftFlipper;
        }
        if (hardware.gamepad1_current_right_bumper) {
            flipperPosition = 0.5;
            selectedFlipper = hardware.rightFlipper;
        }

        if(hardware.gamepad1_current_x){positionIncrement = 0.001;}
        if(hardware.gamepad1_current_y){positionIncrement = .01;}
        if(hardware.gamepad1_current_b){positionIncrement = .1;}

        if(gamepad1.dpad_up){
            flipperPosition = flipperPosition + positionIncrement;
        }
        else if(gamepad1.dpad_down) {
            flipperPosition = flipperPosition - positionIncrement;
        }

        if(selectedFlipper != null){
            selectedFlipper.setPosition(flipperPosition);
        }

        hardware.loop();

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Flipper Servo Position", selectedFlipper.getCurrentPosition());
        telemetry.addData("Flipper Servo increment", positionIncrement);
        telemetry.addData("Flipper Selected: ", selectedFlipper.name);
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        hardware.updateValues();

        hardware.logMessage(false, "MyFirstJava", "Stop Button Pressed");
        hardware.stop();
        super.stop();
    }
}
