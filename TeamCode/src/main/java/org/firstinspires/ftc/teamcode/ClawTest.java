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
import com.qualcomm.robotcore.hardware.Servo;
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

@TeleOp(name="ClawTest", group="tests")
//@Disabled
public class ClawTest extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Hardware hardware = new Hardware();
    private double clawPosition = 0.5;

    private Servo clawServo = null;

    private double positionIncrement = .1;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");
        hardware.init(hardwareMap,this);

        clawServo = hardware.clawServo;

        telemetry.addData("Claw Servo Position", clawPosition);
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

        if(hardware.gamepad1_current_x){positionIncrement = 0.001;}
        if(hardware.gamepad1_current_y){positionIncrement = .01;}
        if(hardware.gamepad1_current_b){positionIncrement = .1;}

//        if(hardware.gamepad1_current_dpad_up & !hardware.gamepad1_previous_dpad_up){
//            //clawPosition = Math.min(Math.max((clawPosition + positionIncrement),0.0),1.0);
//            clawPosition = clawPosition + positionIncrement;
//        }
//        else if(hardware.gamepad1_current_dpad_up & !hardware.gamepad1_previous_dpad_down) {
//            //clawPosition = Math.min(Math.max((clawPosition - positionIncrement),0.0),1.0);
//            clawPosition = clawPosition - positionIncrement;
//        }


        if(gamepad1.dpad_up){
            //clawPosition = Math.min(Math.max((clawPosition + positionIncrement),0.0),1.0);
            clawPosition = clawPosition + positionIncrement;
        }
        else if(gamepad1.dpad_down) {
            //clawPosition = Math.min(Math.max((clawPosition - positionIncrement),0.0),1.0);
            clawPosition = clawPosition - positionIncrement;
        }
        hardware.claw.setPosition(clawPosition);

        hardware.loop();

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Claw Servo Position", clawPosition);
        telemetry.addData("Claw Servo Hardware Position", clawServo.getPosition());
        telemetry.addData("Claw Servo increment", positionIncrement);
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
//NOT WORKING PROPERLY, CLAW INCREMENTS WHEN USING SET POSITION ARE NOT CORRECT