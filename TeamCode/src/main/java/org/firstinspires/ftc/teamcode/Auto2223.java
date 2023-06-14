package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Auto2223", group = "auto")

public class Auto2223 extends OpMode {
    private Hardware hardware = new Hardware();
    private RobotConfiguration robotConfiguration = null;
    private boolean isRed = false;
    private boolean isLeftStartingPos = false;
    private int conePlacement = 0;
    private boolean firstRun = true;
    private boolean secondRun = true;

    private TrajectorySequence lastSequence = null;
    private boolean commandsGrabbed = false;

    public double startTime = 0.0;

    public static final Pose2d RED_RIGHT_STARTPOS = new Pose2d(36,-63.5,90);
    public static final Pose2d RED_LEFT_STARTPOS = new Pose2d(-36,-63.5,90);
    public static final Pose2d BLUE_RIGHT_STARTPOS = new Pose2d(-36,63.5,-90);
    public static final Pose2d BLUE_LEFT_STARTPOS = new Pose2d(36,63.5,-90);

    private Pose2d startPose = null;

    @Override
    public void init() {
        System.gc();

        hardware.init(hardwareMap, this);

        robotConfiguration = new RobotConfiguration();
        robotConfiguration.readConfig();
        isRed = robotConfiguration.isRed;
        isLeftStartingPos = robotConfiguration.isLeftStartPos;

        telemetry.addLine("Configuration Fetched");
        telemetry.addData("Is Red?? ", isRed);
        telemetry.addData("Is Left Position? ", isLeftStartingPos);
        telemetry.update();

        //Set Starting Position
        startPose = isRed ? (isLeftStartingPos ? RED_LEFT_STARTPOS : RED_RIGHT_STARTPOS) : (isLeftStartingPos ? BLUE_LEFT_STARTPOS : BLUE_RIGHT_STARTPOS);

        hardware.lift.calibrateLift();
        hardware.claw.stow();

        hardware.webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                @Override
                public void onOpened() {
                    hardware.webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
                }

                @Override
                public void onError(int errorCode) {

                }

            });
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

        startPose = BLUE_RIGHT_STARTPOS;
        isLeftStartingPos = false;

        if(!commandsGrabbed){
            hardware.drive.setPoseEstimate(startPose);
            if(!hardware.webcamPipeline.isFrameSelected()){
                //skip camera
                if(isLeftStartingPos){      //Left Position
                    hardware.robo130.addCommand(new RCWait(hardware,2.0));//Wait for lift to nationalize
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,1,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCClaw(hardware, 1, false)); //grip
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,800,1,false));//go up

//                    hardware.robo130.addCommand(new RCDriveForward(hardware, 48.0 + 3.5, 0.5 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-90.0, 0.6));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(startPose)
                            .forward(51.5)
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/2.0))
                            .turn(Math.toRadians(-90))
                            .resetAccelConstraint()
                            .build()
                    ));

                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware, Lift.HIGH_JUCTION_POSITION, 0.6));

//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,45,0.3));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, 7.5, 0.2 ));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/2.0))
                            .turn(Math.toRadians(45.0))
                            .forward(7.5)
                            .resetAccelConstraint()
                            .build()
                    ));



                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCClaw(hardware,3,false));

//                    hardware.robo130.addCommand(new RCWait(hardware,1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, -7.5, 0.2 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-45,0.3));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, -2, 0.2 ));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/5.0))
                            .back(7.5)
                            .turn(Math.toRadians(-45))
                            .back(2)
                            .resetAccelConstraint()
                            .build()
                    ));

                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,0.5,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                }else{      //Right Position
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,1,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCClaw(hardware, 1, false)); //grip
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,800,1,false));//go up

//                    hardware.robo130.addCommand(new RCDriveForward(hardware, 48.0 + 3.5, 0.5 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,90.0, 0.6));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(startPose)
                            .forward(51.5)
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/2.0))
                            .turn(Math.toRadians(90))
                            .resetAccelConstraint()                            .build()
                    ));

                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware, Lift.HIGH_JUCTION_POSITION, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));

//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-45,0.3));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, 7.5, 0.2 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/2.0))
                            .turn(Math.toRadians(-45.0))
                            .forward(3)
                            .resetAccelConstraint()
                            .build()
                    ));

                    hardware.robo130.addCommand(new RCClaw(hardware,3,false));

//                    hardware.robo130.addCommand(new RCWait(hardware,1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, -7.5, 0.2 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,45,0.3));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, -2, 0.2 ));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/5.0))
                            .back(7.5)
                            .turn(Math.toRadians(45))
                            .back(2)
                            .resetAccelConstraint()
                            .build()
                    ));

                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,0.5,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                }
                hardware.lift.goMin();
                commandsGrabbed = true;
            }
            else if(hardware.webcamPipeline.isFrameSelected()){
                conePlacement = hardware.webcamPipeline.getConePosition();
                if(isLeftStartingPos){      //Left Position
                    hardware.robo130.addCommand(new RCWait(hardware,2.0));//Wait for lift to nationalize
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,1,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCClaw(hardware, 1, false)); //grip
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,800,1,false));//go up

//                    hardware.robo130.addCommand(new RCDriveForward(hardware, 48.0 + 3.5, 0.5 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-90.0, 0.6));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(startPose)
                            .forward(51.5)
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/2.0))
                            .turn(Math.toRadians(-90))
                            .resetAccelConstraint()
                            .build()
                    ));

                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware, Lift.HIGH_JUCTION_POSITION, 0.6));

//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,45,0.3));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, 7.5, 0.2 ));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/2.0))
                            .turn(Math.toRadians(45.0))
                            .forward(7.5)
                            .resetAccelConstraint()
                            .build()
                    ));

                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCClaw(hardware,3,false));

//                    hardware.robo130.addCommand(new RCWait(hardware,1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, -7.5, 0.2 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-45,0.3));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, -2, 0.2 ));
//
                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/5.0))
                            .back(7.5)
                            .turn(Math.toRadians(-45))
                            .back(2)
                            .resetAccelConstraint()
                            .build()
                    ));
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,0.5,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));

                }else{      //Right Position
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,1,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCClaw(hardware, 1, false)); //grip
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,800,1,false));//go up

//                    hardware.robo130.addCommand(new RCDriveForward(hardware, 48.0 + 3.5, 0.5 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,90.0, 0.6));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(startPose)
                            .forward(51.5)
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/2.0))
                            .turn(Math.toRadians(90))
                            .resetAccelConstraint()
                            .build()
                    ));

                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware, Lift.HIGH_JUCTION_POSITION, 0.6));
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));

//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,-45,0.3));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, 7.5, 0.2 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/2.0))
                            .turn(Math.toRadians(-45.0))
                            .forward(3)
                            .resetAccelConstraint()
                            .build()
                    ));

                    hardware.robo130.addCommand(new RCClaw(hardware,3,false));

//                    hardware.robo130.addCommand(new RCWait(hardware,1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, -7.5, 0.2 ));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCTurnCounterClockwise(hardware,45,0.3));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware, -2, 0.2 ));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/5.0))
                            .back(7.5)
                            .turn(Math.toRadians(5))
                            .back(2)
                            .resetAccelConstraint()
                            .build()
                    ));

                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                    hardware.robo130.addCommand(new RCLiftGoToPosition(hardware,100,0.5,false));//go up
                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
                }
                if((!isLeftStartingPos && conePlacement == 1) || (isLeftStartingPos && conePlacement == 3)){
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware,24,0.5));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/5.0))
                            .forward(24)                            .resetAccelConstraint()
                            .build()
                    ));
                }
                else if((!isLeftStartingPos && conePlacement == 3) || (isLeftStartingPos && conePlacement == 1)){
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));
//                    hardware.robo130.addCommand(new RCDriveForward(hardware,-24,0.5));
//                    hardware.robo130.addCommand(new RCWait(hardware,0.1));

                    hardware.robo130.addCommand(new RCRoadrunner(hardware, hardware.drive.trajectorySequenceBuilder(RCRoadrunner.getPreviousEndPoint())
                            .setAccelConstraint(hardware.drive.getAccelerationConstraint(DriveConstants.MAX_ACCEL/5.0))
                            .back(24)                            .resetAccelConstraint()
                            .build()
                    ));
                }

                hardware.lift.goMin();
                commandsGrabbed = true;
            }
        }

        if(commandsGrabbed){
            hardware.robo130.processCommands();
        }

        telemetry.addData("Cone Position: ",conePlacement);
        telemetry.addData("Commands: ", hardware.robo130.getNumCommands());
        telemetry.addData("Current Command: ", hardware.robo130.getCurrentCommandIndex());
        telemetry.addData("Next Command: ", hardware.robo130.getNextCommandIndex());
        telemetry.addData("Status", "Running");
        telemetry.update();
    }

    public void start(){
        hardware.webcam.stopStreaming();
        hardware.claw.open();
        hardware.updateValues();
        hardware.logMessage(false, "Auto2223", "Start Button Pressed");
        super.start();
        hardware.start();
        hardware.claw.grip();
    }

    public void stop() {
        hardware.updateValues();
        hardware.logMessage(false, "Auto2223", "Stop Button Pressed");
        hardware.stop();
        super.stop();
    }
}
