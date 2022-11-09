package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Cabin {
    private OpMode opMode = null;
    private Hardware hardware = null;

    private Servo cabinTilt = null;

    private static double HOLDPOS = 0.75; // 2000 us
    private static double DROPPOS = 0.43; // 1360 us
    private static double TRANSFERPOS = .916; //2332 us

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timeout = new ElapsedTime();

    public Cabin(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {
        cabinTilt = hardware.cabinTilt;
        if(cabinTilt == null){
            int error = 0/0;
        }

        opMode.telemetry.addData("Cabin Status", "Initializing");

        runtime.reset();
        timeout.reset();

        // Tell the driver that initialization is complete.
        opMode.telemetry.addData("Cabin Status", "Initialized");
        opMode.telemetry.update();
    }

    public void doInitLoop() {

        opMode.telemetry.addData("Cabin Status", "Starting. doing init loop");
                opMode.telemetry.addData("Cabin Status", "Done doing init loop");
        opMode.telemetry.update();
    }

    public void holdCargo() {
        cabinTilt.setPosition(HOLDPOS);
        hardware.logMessage(false, "Cabin", "holding");

    }

    public void dropCargo() {
        cabinTilt.setPosition(DROPPOS);
        hardware.logMessage(false, "Cabin", "empty");
    }

    public void transferPos() {
        cabinTilt.setPosition(TRANSFERPOS);
        hardware.logMessage(false, "Cabin", "holding");

    }

    public double getCurrentPos(){
        return cabinTilt.getPosition();
    }
}