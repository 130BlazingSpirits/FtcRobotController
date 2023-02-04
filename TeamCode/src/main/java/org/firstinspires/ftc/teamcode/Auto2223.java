package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Auto2223", group = "auto")

public class Auto2223 extends OpMode {
    private Hardware hardware = new Hardware();
    private RobotConfiguration robotConfiguration = null;
    private boolean isRed = false;
    private boolean isLeftStartingPos = false;
    private int conePlacement = 0;

    private boolean commandsGrabbed = false;

    public double timeout = 0.0;

    @Override
    public void init() {
        hardware.init(hardwareMap, this);

        robotConfiguration = new RobotConfiguration();
        robotConfiguration.readConfig();
        isRed = robotConfiguration.isRed;
        isLeftStartingPos = robotConfiguration.isLeftStartPos;

        telemetry.addLine("Configuration Fetched");
        telemetry.addData("Is Red?? ", isRed);
        telemetry.addData("Is Left Position? ", isLeftStartingPos);
        telemetry.update();

        hardware.lift.calibrateLift();
        hardware.claw.stow();

        hardware.webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPSIDE_DOWN);
    }

    @Override
    public void init_loop() {
        hardware.updateValues();

        super.init_loop();

        hardware.init_loop();
    }

    @Override
    public void loop() {
        hardware.updateValues();
        hardware.loop();

        while(!commandsGrabbed){
            if(Math.abs(time - timeout) > 8.0){
                //skip camera
                commandsGrabbed = true;
                hardware.webcam.stopStreaming();
                hardware.lift.calibrateLift();
                if(isLeftStartingPos){      //Left Position
                    hardware.robo130.addCommand(new RCWait(hardware,2.0));//Wait for lift to nationalize
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,1,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCClaw(hardware, 1, false)); //grip
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,800,1,false));//go up
                    hardware.robo130.addCommand(new RCDriveForward(hardware, 48.0 + 3.5, 0.5 ));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-90.0, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware, Lift.HIGH_JUCTION_POSITION, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,45,0.3));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, 7.5, 0.2 ));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCClaw(hardware,3,false));
                    hardware.robo130.addCommand(new RCWait(hardware,1));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, -7.5, 0.2 ));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-45,0.3));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, -2, 0.2 ));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,0.5,false));//go up
                }else{      //Right Position
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,1,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCClaw(hardware, 1, false)); //grip
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,800,1,false));//go up
                    hardware.robo130.addCommand(new RCDriveForward(hardware, 48.0 + 3.5, 0.5 ));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,90.0, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware, Lift.HIGH_JUCTION_POSITION, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-45,0.3));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, 7.5, 0.2 ));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCClaw(hardware,3,false));
                    hardware.robo130.addCommand(new RCWait(hardware,1));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, -7.5, 0.2 ));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,45,0.3));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, -2, 0.2 ));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,0.5,false));//go up
                }

            }
            else if(hardware.webcamPipeline.isFrameSelected()){
                conePlacement = hardware.webcamPipeline.getConePosition();
                if(isLeftStartingPos){      //Left Position
                    hardware.robo130.addCommand(new RCWait(hardware,2.0));//Wait for lift to nationalize
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,1,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCClaw(hardware, 1, false)); //grip
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,800,1,false));//go up
                    hardware.robo130.addCommand(new RCDriveForward(hardware, 48.0 + 3.5, 0.5 ));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-90.0, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware, Lift.HIGH_JUCTION_POSITION, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,45,0.3));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, 7.5, 0.2 ));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCClaw(hardware,3,false));
                    hardware.robo130.addCommand(new RCWait(hardware,1));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, -7.5, 0.2 ));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-45,0.3));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, -2, 0.2 ));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,0.5,false));//go up
                }else{      //Right Position
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,1,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCClaw(hardware, 1, false)); //grip
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,800,1,false));//go up
                    hardware.robo130.addCommand(new RCDriveForward(hardware, 48.0 + 3.5, 0.5 ));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,90.0, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware, Lift.HIGH_JUCTION_POSITION, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-45,0.3));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, 7.5, 0.2 ));
                    hardware.robo130.addCommand(new RCWait(hardware,0.3));
                    hardware.robo130.addCommand(new RCClaw(hardware,3,false));
                    hardware.robo130.addCommand(new RCWait(hardware,1));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, -7.5, 0.2 ));
                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,45,0.3));
                    hardware.robo130.addCommand(new RCDriveForward(hardware, -2, 0.2 ));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,0.5,false));//go up
                }
                if(!isLeftStartingPos && conePlacement == 1){
                    hardware.robo130.addCommand(new RCDriveForward(hardware,24,0.5));
                }
                else if(!isLeftStartingPos && conePlacement == 3){
                    hardware.robo130.addCommand(new RCDriveForward(hardware,-24,0.5));
                }
                else if(isLeftStartingPos && conePlacement == 1){
                    hardware.robo130.addCommand(new RCDriveForward(hardware,-24,0.5));
                }
                else if(isLeftStartingPos && conePlacement == 3){
                    hardware.robo130.addCommand(new RCDriveForward(hardware,24,0.5));
                }
                commandsGrabbed = true;
                hardware.webcam.stopStreaming();
                hardware.lift.calibrateLift();
            }
        }

        hardware.robo130.processCommands();
        telemetry.addData("Commands: ", hardware.robo130.getNumCommands());
        telemetry.addData("Current Command: ", hardware.robo130.getCurrentCommandIndex());
        telemetry.addData("Next Command: ", hardware.robo130.getNextCommandIndex());
        telemetry.addData("Status", "Running");
        telemetry.update();
    }

    public void start(){
        hardware.claw.open();
        hardware.updateValues();
        hardware.logMessage(false, "Auto2223", "Start Button Pressed");
        super.start();
        hardware.start();
        hardware.claw.grip();
        hardware.lift.goToLow();
        timeout = time;
    }

    public void stop() {
        hardware.updateValues();

        hardware.logMessage(false, "Auto2223", "Stop Button Pressed");
        hardware.stop();
        super.stop();
    }
}
