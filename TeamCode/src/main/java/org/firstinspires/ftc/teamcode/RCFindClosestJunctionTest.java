package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="RCFindClosestJunction Test", group="tests")

public class RCFindClosestJunctionTest extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Hardware hardware = new Hardware();
    private CVLocateClosestJunction closestJunctionPipeline = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");
        hardware.init(hardwareMap,this);
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

        closestJunctionPipeline = new CVLocateClosestJunction(telemetry,this);

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

        if(hardware.gamepad1_current_a && !hardware.gamepad1_previous_a){
            hardware.robo130.addCommand(new RCFindClosestJunction(hardware, closestJunctionPipeline,15.0));
        }

        hardware.robo130.processCommands();

        hardware.loop();

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("left", closestJunctionPipeline.left);
        telemetry.addData("right", closestJunctionPipeline.right);
        telemetry.addData("center", closestJunctionPipeline.center);
        telemetry.addData("width", closestJunctionPipeline.width);
        telemetry.addData("estimated distance", closestJunctionPipeline.estimatedDistance);
        telemetry.addData("estimated angle", closestJunctionPipeline.estimatedAngle);
        telemetry.addData("desired center location", closestJunctionPipeline.desiredCenterLocation);
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
