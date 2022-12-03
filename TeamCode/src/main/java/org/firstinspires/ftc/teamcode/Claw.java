package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Claw {
    private OpMode opMode = null;
    private Hardware hardware = null;

    private Servo clawServo = null;

    private static final double OPENPOS = 0.5;
    private static final double GRIPPOS = 0.35;
    private static final double STOWPOS = 0.78;

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timeout = new ElapsedTime();

    public Claw(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {
        clawServo = hardware.clawServo;
        runtime.reset();
        timeout.reset();

        // Tell the driver that initialization is complete.
        opMode.telemetry.addData("Claw Status", "Initialized");
        opMode.telemetry.update();
    }

    public void doInitLoop() {

        opMode.telemetry.addData("Claw Status", "Starting. doing init loop");
    }

    public void open() {
        setPosition(OPENPOS);
        hardware.logMessage(false, "Claw", "open");
    }

    public void grip() {
        setPosition(GRIPPOS);
        hardware.logMessage(false, "Claw", "grip");
    }

    public void stow(){
        setPosition(STOWPOS);
        hardware.logMessage(false, "Claw", "stow");
    }

    public void setPosition(double pos){
//        clawServo.setPosition(Math.min(Math.max(pos, 0.0),1.0));
        clawServo.setPosition(pos);
    }

    public double getCurrentPos(){
        return clawServo.getPosition();
    }
}