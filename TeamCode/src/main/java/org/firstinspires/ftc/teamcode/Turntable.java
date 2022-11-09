package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Turntable {
    private OpMode opMode = null;
    private Hardware hardware = null;
    private DcMotor turntableMotor = null;

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timeout = new ElapsedTime();
    private static final int SPIN_MODE_POWER = 0;
    private static       int spinMode = SPIN_MODE_POWER;

    public Turntable(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {

        opMode.telemetry.addData("DJ Status", "Initializing");
        turntableMotor = hardware.turnTable;
        runtime.reset();
        timeout.reset();

        // Tell the driver that initialization is complete.
        opMode.telemetry.addData("DJ Status", "Initialized");
        opMode.telemetry.update();
    }

    public void doInitLoop() {

        opMode.telemetry.addData("DJ Status", "Starting. Downloading sick beats");
        opMode.telemetry.addData("DJ Status", "Sick Beats Queued!");
        opMode.telemetry.update();
    }

    public void goTurnSpin(double turnPower){
        spinMode = SPIN_MODE_POWER;
        turntableMotor.setPower(turnPower);
    }
}