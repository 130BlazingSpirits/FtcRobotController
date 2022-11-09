/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="DriveForwardTest", group="tests")
//@Disabled
public class DriveForwardTest extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Hardware hardware = new Hardware();
    private static int forwardPosition = 1000;
    private static double motourPower = 1.0;

    private DcMotor motorLFront = null;
    private DcMotor motorLBack = null;
    private DcMotor motorRFront = null;
    private DcMotor motorRBack = null;


    private Boolean prevDpadUp = false;
    private Boolean prevDpadDown = false;
    private Boolean prevDpadLeft = false;
    private Boolean prevDpadRight = false;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");
        hardware.init(hardwareMap,this);

        motorLFront = hardware.motorLFront;
        motorLBack = hardware.motorLBack;
        motorRFront = hardware.motorRFront;
        motorRBack = hardware.motorRBack;

        motorLFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLFront.setTargetPosition(0);
        motorLBack.setTargetPosition(0);
        motorRFront.setTargetPosition(0);
        motorRBack.setTargetPosition(0);

        motorLFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorLFront.setPower(0.0);
        motorLBack.setPower(0.0);
        motorRFront.setPower(0.0);
        motorRBack.setPower(0.0);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Left Front mode", motorLFront.getMode().name());
        telemetry.addData("Left Back mode", motorLBack.getMode().name());
        telemetry.addData("Right Front mode", motorRFront.getMode().name());
        telemetry.addData("Right Back mode", motorRBack.getMode().name());


    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        Boolean currentDpadUp = gamepad1.dpad_up;
        Boolean currentDpadDown = gamepad1.dpad_down;
        Boolean currentDpadLeft = gamepad1.dpad_left;
        Boolean currentDpadRight = gamepad1.dpad_right;

        motorLFront.setTargetPosition(forwardPosition);
        motorLBack.setTargetPosition(forwardPosition);
        motorRFront.setTargetPosition(forwardPosition);
        motorRBack.setTargetPosition(forwardPosition);

        motorLFront.setPower(motourPower);
        motorLBack.setPower(motourPower);
        motorRFront.setPower(motourPower);
        motorRBack.setPower(motourPower);


        if(gamepad1.dpad_up && !prevDpadUp){

        }
        else if(gamepad1.dpad_down && !prevDpadDown){

        }
        else if(gamepad1.dpad_right && !prevDpadRight){

        }
        else if(gamepad1.dpad_left && !prevDpadLeft){

        }
        else if(gamepad1.left_bumper){

        }
        else if(gamepad1.x || gamepad1.b){

        }
        else if(gamepad1.y){

        }
        else if(gamepad1.a){

        }




        prevDpadDown = currentDpadDown;
        prevDpadUp = currentDpadUp;
        prevDpadLeft = currentDpadLeft;
        prevDpadRight = currentDpadRight;

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Forward Position", forwardPosition);
        telemetry.addData("LFrontCurrPos",motorLFront.getCurrentPosition());
        telemetry.addData("LBackCurrPos",motorLBack.getCurrentPosition());
        telemetry.addData("RFrontCurrPos",motorRFront.getCurrentPosition());
        telemetry.addData("RBackCurrPos",motorRBack.getCurrentPosition());

        telemetry.addData("Left Front mode", motorLFront.getMode().name());
        telemetry.addData("Left Back mode", motorLBack.getMode().name());
        telemetry.addData("Right Front mode", motorRFront.getMode().name());
        telemetry.addData("Right Back mode", motorRBack.getMode().name());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
