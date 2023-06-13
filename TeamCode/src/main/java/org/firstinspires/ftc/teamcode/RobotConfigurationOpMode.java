package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Robot Configuration", group="A")

public class RobotConfigurationOpMode extends LinearOpMode {
    private boolean isRed = false;
    private boolean isLeftStartPos = false;

    private RobotConfiguration roboConfig = null;

    @Override
    public void runOpMode(){
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("Team Colour? RED (left) or BLUE (right) button.");
            telemetry.update();
            while(true){
                if(gamepad1.x || gamepad2.x){
                    isRed = false;
                    break;
                }
                if(gamepad1.b || gamepad2.b){
                    isRed = true;
                    break;
                }
            }
            telemetry.addLine("Starting Position: X for left, B for right");
            telemetry.update();
            while(true) {
                if (gamepad1.x || gamepad2.x) {
                    isLeftStartPos = true;
                    break;
                }
                if (gamepad1.b || gamepad2.b) {
                    isLeftStartPos = false;
                    break;
                }
            }
            break;
        }

        roboConfig = new RobotConfiguration(isRed,isLeftStartPos);
        roboConfig.saveConfig();

        while (opModeIsActive())
        {
            telemetry.addLine("Configuration Complete");
            telemetry.addData("Is Red?? ", isRed);
            telemetry.addData("Is Left Position? ", isLeftStartPos);
            telemetry.update();
        }
    }
}
