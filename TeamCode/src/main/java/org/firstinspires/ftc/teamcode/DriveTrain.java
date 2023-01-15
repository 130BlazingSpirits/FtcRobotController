package org.firstinspires.ftc.teamcode;

import android.widget.Switch;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gyroscope;

import java.util.Set;

public class DriveTrain {
    private OpMode opMode = null;
    private Hardware hardware = null;
    public boolean isMecanum = false;
    public boolean isTank = false;

    private Gyroscope imu = null;
    private DcMotorEx motorLFront = null;
    private DcMotorEx motorLBack = null;
    private DcMotorEx motorRFront = null;
    private DcMotorEx motorRBack = null;

    private static final int MAXSPEED = 950; // encoders per sec
    //private static final double FRONTPOWERREDUCTION = 0.80;
    //private static final double BACKPOWERINCREASE = 0.7;
//    private static final double MAXPOWER = 0.7;
    private static final double FRONTPOWERREDUCTION = 1.0;
    private static final double BACKPOWERINCREASE = 1.0;
    private static final double MAXPOWER = 1.0;
    private double gasPedalPower = 1.0;
    // *WARNING* SET TO FALSE BEFORE TESTING MAX VELOCITY OF MOTOR
    private boolean useSetVelocity = true; // true is for setVelocity method while false is for setPower method.
    public static final int MAX_LINEAR_VELOCITY = (int) (1922 * 0.93); //Encoders/second | Tested max velocity was 1922 1/15/23 useSetVelocity should be set to false when testing
    private static final int MAX_ANGULAR_VELOCITY = (int) (2213 * 0.93); //Encoders/second | Tested max velocity was 2213 1/15/23 useSetVelocity should be set to false when testing
    private static final int ACCELERATION_RATE = 2400; //Encoders/second^2
    private static final int DECELERATION_RATE = -4800; //Encoders/second^2
    public static final double FORWARD_DISTANCE_PER_PULSE = 25.75/1000; //found through rigorous testing and throwing what looks good together
    public static final double STRAFE_DISTANCE_PER_PULSE = 16.0/1000; //ibid
    public static final double DEGREE_PER_PULSE = 371.0/2000; //ibid
    private static       double motorLFrontVelocitySetpoint = 0;
    private static       double motorLBackVelocitySetpoint = 0;
    private static       double motorRFrontVelocitySetpoint = 0;
    private static       double motorRBackVelocitySetpoint = 0;
    private static       double motorLFrontVelocityReqSetpoint = 0;
    private static       double motorLBackVelocityReqSetpoint = 0;
    private static       double motorRFrontVelocityReqSetpoint = 0;
    private static       double motorRBackVelocityReqSetpoint = 0;
    private static final int DRIVE_MODE_POWER = 0;
    private static final int DRIVE_MODE_SPEED = 1;
    private static final int DRIVE_MODE_POSITION = 2;
    private static       int driveMode = DRIVE_MODE_POWER;

    public DriveTrain(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
    }

    public void init() {
        imu = hardware.imu;
        motorLFront = hardware.motorLFront;
        motorLBack = hardware.motorLBack;
        motorRFront = hardware.motorRFront;
        motorRBack = hardware.motorRBack;
    }

    public void loop(){
        switch(driveMode){
            case DRIVE_MODE_POWER:
                break;

            case DRIVE_MODE_SPEED:
                double motLFCurVel = motorLFront.getVelocity();
                double motLBCurVel = motorLBack.getVelocity();
                double motRFCurVel = motorRFront.getVelocity();
                double motRBCurVel = motorRBack.getVelocity();

                //Set motourLeftFrontCourrentVElocity
                /*if(motorLFrontVelocitySetpoint > motLFCurVel){
                    motorLFrontVelocityReqSetpoint = Math.min(motLFCurVel+ACCELERATION_RATE*hardware.getDeltaTime(), motorLFrontVelocitySetpoint);
                }
                else if(motorLFrontVelocitySetpoint < motLFCurVel){
                    motorLFrontVelocityReqSetpoint = Math.max(motLFCurVel+DECELERATION_RATE*hardware.getDeltaTime(), motorLFrontVelocitySetpoint);
                }
                motorLFront.setVelocity(motorLFrontVelocityReqSetpoint);*/

                //Set motourLeftBackCourrentVElocity
                if(motorLBackVelocitySetpoint > motLBCurVel){
                    motorLBackVelocityReqSetpoint = Math.min(motLBCurVel+ACCELERATION_RATE*hardware.getDeltaTime(), motorLBackVelocitySetpoint);
                }
                else if(motorLBackVelocitySetpoint < motLBCurVel){
                    motorLBackVelocityReqSetpoint = Math.max(motLBCurVel+DECELERATION_RATE*hardware.getDeltaTime(), motorLBackVelocitySetpoint);
                }
                motorLBack.setVelocity(motorLBackVelocityReqSetpoint);

                //Set motourRightFrontCourrentVElocity
                /*if(motorRFrontVelocitySetpoint > motRFCurVel){
                    motorRFrontVelocityReqSetpoint = Math.min(motRFCurVel+ACCELERATION_RATE*hardware.getDeltaTime(), motorRFrontVelocitySetpoint);
                }
                else if(motorRFrontVelocitySetpoint < motRFCurVel){
                    motorRFrontVelocityReqSetpoint = Math.max(motRFCurVel+DECELERATION_RATE*hardware.getDeltaTime(), motorRFrontVelocitySetpoint);
                }
                motorRFront.setVelocity(motorRFrontVelocityReqSetpoint);*/

                //Set motourRightBackCourrentVElocity
                if(motorRBackVelocitySetpoint > motRBCurVel){
                    motorRBackVelocityReqSetpoint = Math.min(motRBCurVel+ACCELERATION_RATE*hardware.getDeltaTime(), motorRBackVelocitySetpoint);
                }
                else if(motorRBackVelocitySetpoint < motRBCurVel){
                    motorRBackVelocityReqSetpoint = Math.max(motRBCurVel+DECELERATION_RATE*hardware.getDeltaTime(), motorRBackVelocitySetpoint);
                }
                motorRBack.setVelocity(motorRBackVelocityReqSetpoint);

                break;

            case DRIVE_MODE_POSITION:
                break;
        }
    }

    public void stop(){
        motorLFront.setVelocity(0.0);
        motorLBack.setVelocity(0.0);
        motorRFront.setVelocity(0.0);
        motorRBack.setVelocity(0.0);

        motorLFront.setPower(0.0);
        motorLBack.setPower(0.0);
        motorRFront.setPower(0.0);
        motorRBack.setPower(0.0);

        motorLFront.setMotorDisable();
        motorLBack.setMotorDisable();
        motorRFront.setMotorDisable();
        motorRBack.setMotorDisable();
    }

    public void goLeft(double power){
        driveMode = DRIVE_MODE_POWER;
        power = Math.max(power, 0.9);
        motorLFront.setVelocity(-1.0*power*gasPedalPower*FRONTPOWERREDUCTION*1800);
        motorLBack.setVelocity(power*gasPedalPower*BACKPOWERINCREASE*1800);
        motorRFront.setVelocity(power*gasPedalPower*FRONTPOWERREDUCTION*1800);
        motorRBack.setVelocity(-1.0*gasPedalPower*power*BACKPOWERINCREASE*1800);
        /*
        motorLFront.setPower(power*FRONTPOWERREDUCTION);
        motorLBack.setPower(-1.0*power*BACKPOWERINCREASE);
        motorRFront.setPower(-1.0*power*FRONTPOWERREDUCTION);
        motorRBack.setPower(power*BACKPOWERINCREASE);*/
    }

    public void goRight(double power){
        driveMode = DRIVE_MODE_POWER;
        power = Math.max(power, 0.9);
        motorLFront.setVelocity(power*gasPedalPower*FRONTPOWERREDUCTION*1800);
        motorLBack.setVelocity(-1.0*power*gasPedalPower*BACKPOWERINCREASE*1800);
        motorRFront.setVelocity(-1.0*power*gasPedalPower*FRONTPOWERREDUCTION*1800);
        motorRBack.setVelocity(power*gasPedalPower*BACKPOWERINCREASE*1800);
        /*
        motorLFront.setPower(-1.0*power*FRONTPOWERREDUCTION);
        motorLBack.setPower(power*BACKPOWERINCREASE);
        motorRFront.setPower(power*FRONTPOWERREDUCTION);
        motorRBack.setPower(-1.0*power*BACKPOWERINCREASE);*/
    }

    public void rotateClockwise(double angle){}
    public void rotateCounterClockwise(double angle){}

    public void goTankDrive(double leftPower, double rightPower){
        driveMode = DRIVE_MODE_POWER;
        if(useSetVelocity){
            {
                if(Math.abs(leftPower - rightPower) < 0.02){
                    motorLFront.setVelocity(leftPower*gasPedalPower*MAX_LINEAR_VELOCITY);
                    motorLBack.setVelocity(leftPower*gasPedalPower*MAX_LINEAR_VELOCITY);
                    motorRFront.setVelocity(rightPower*gasPedalPower*MAX_LINEAR_VELOCITY);
                    motorRBack.setVelocity(rightPower*gasPedalPower*MAX_LINEAR_VELOCITY);
                }else{
                    motorLFront.setVelocity(leftPower*gasPedalPower*MAX_ANGULAR_VELOCITY);
                    motorLBack.setVelocity(leftPower*gasPedalPower*MAX_ANGULAR_VELOCITY);
                    motorRFront.setVelocity(rightPower*gasPedalPower*MAX_ANGULAR_VELOCITY);
                    motorRBack.setVelocity(rightPower*gasPedalPower*MAX_ANGULAR_VELOCITY);
                }
            }
        }else{
            motorLFront.setPower(leftPower*gasPedalPower*MAXPOWER);
            motorLBack.setPower(leftPower*gasPedalPower*MAXPOWER);
            motorRFront.setPower(rightPower*gasPedalPower*MAXPOWER);
            motorRBack.setPower(rightPower*gasPedalPower*MAXPOWER);
        }
    }

    public void goSpeedLeft(double speed){
        driveMode = DRIVE_MODE_SPEED;
        /*motorLFront.setPower(power*MAXPOWER*FRONTPOWERREDUCTION);
        motorLBack.setPower(-1.0*power*MAXPOWER*BACKPOWERINCREASE);
        motorRFront.setPower(-1.0*power*MAXPOWER*FRONTPOWERREDUCTION);
        motorRBack.setPower(power*MAXPOWER*BACKPOWERINCREASE);*/
    }

    public void goSpeedRight(double speed){
        driveMode = DRIVE_MODE_SPEED;
        /*motorLFront.setPower(-1.0*power*MAXPOWER*FRONTPOWERREDUCTION);
        motorLBack.setPower(power*MAXPOWER*BACKPOWERINCREASE);
        motorRFront.setPower(power*MAXPOWER*FRONTPOWERREDUCTION);
        motorRBack.setPower(-1.0*power*MAXPOWER*BACKPOWERINCREASE);*/
    }

    public void goSpeedTankDrive(double leftSpeed, double rightSpeed){
        driveMode = DRIVE_MODE_SPEED;
        if(Math.abs(leftSpeed) < MAX_LINEAR_VELOCITY) {
            motorLFrontVelocitySetpoint = leftSpeed;
            motorLBackVelocitySetpoint = leftSpeed;
        }
        else{
            opMode.telemetry.addData("ErrorL:", "Left set linear velocity exceeds max");
        }

        if(Math.abs(rightSpeed) < MAX_LINEAR_VELOCITY) {
            motorRFrontVelocitySetpoint = rightSpeed;
            motorRBackVelocitySetpoint = rightSpeed;
        }
        else{
            opMode.telemetry.addData("ErrorR:", "Right set linear velocity exceeds max");
        }
    }

    public double getMotorLFrontVelocitySetpoint(){
        return motorLFrontVelocitySetpoint;
    }
    public double getMotorLBackVelocitySetpoint(){
        return motorLBackVelocitySetpoint;
    }
    public double getMotorRFrontVelocitySetpoint(){
        return motorRFrontVelocitySetpoint;
    }
    public double getMotorRBackVelocitySetpoint(){
        return motorRBackVelocitySetpoint;
    }

    public double getMotorLFrontVelocityReqSetpoint(){
        return motorLFrontVelocityReqSetpoint;
    }
    public double getMotorLBackVelocityReqSetpoint(){
        return motorLBackVelocityReqSetpoint;
    }
    public double getMotorRFrontVelocityReqSetpoint(){
        return motorRFrontVelocityReqSetpoint;
    }
    public double getMotorRBackVelocityReqSetpoint(){
        return motorRBackVelocityReqSetpoint;
    }

    public void setPowerMode(){
        motorLFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setSpeedMode(){
        motorLFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setPositionMode(){
        motorLFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void stopMotors(){
        hardware.motorLFront.setPower(0.0);
        hardware.motorLBack.setPower(0.0);
        hardware.motorRFront.setPower(0.0);
        hardware.motorRBack.setPower(0.0);
    }

    public double getGasPedalPower() {
        return gasPedalPower;
    }

    public void setGasPedalPower(double gasPedalPower) {
        this.gasPedalPower = gasPedalPower;
    }


    public void tileTurnCW() {}
    public void tileTurnCCW() {}
    public void forwardTile() {}
    public void reverseTile() {}
}
