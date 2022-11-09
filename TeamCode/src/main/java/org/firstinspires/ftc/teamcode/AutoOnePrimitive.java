package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="AutoOnePrimitive", group="auto")
public class AutoOnePrimitive extends OpMode {
    private              int scoringPieceAt = 0; //0 is left, 1 is centre, 2 is right.
    private              int midLayerLevel = 0; //Middle layer position
    private              int topLayerLevel = 0; //Top layer position
    public               int autoState = 0;
    private static final int LEFT_SPACE_CHECK = 1;
    private static final int CENTRE_SPACE_CHECK = 2;
    private static final int GET_TO_HUB = 3;
    private static final int PLACE_ELEMENT = 4;
    private static final int GO_TO_CAROUSEL = 5;
    private static final int SPIN_CAROUSEL = 6;
    private static final int PARK_IN_TEAM_DEPOT = 7;
    private Hardware hardware = new Hardware();
    private double currentTime = this.time;

    //TODO: check leftmost space. Check centre space. Go to scoring hub. Go to carousel. Spin at carousel. Park in personal hub.

    private static final double STRAFE_POWER = 1.0;

    @Override
    public void init() {
        hardware.init(hardwareMap,this);

    }

    @Override
    public void init_loop() {
        hardware.updateValues();

        super.init_loop();
     //   hardware.lift.doInitLoop();

        hardware.init_loop();
    }

    @Override
    public void loop() {
        hardware.updateValues();
        while((currentTime - this.time)> 1.5)
        hardware.loop();
        switch (autoState) {
            case LEFT_SPACE_CHECK:
                //set cabin to hold pos
                //drive backward some distance
                if(hardware.rearDistance.getDistance(DistanceUnit.INCH) < 4.0) { //if the distance sensor reads out between 1 and 4 inches
                    scoringPieceAt = 0; //marker found at left
                    autoState = GET_TO_HUB;
                }
                else{
                    autoState = CENTRE_SPACE_CHECK;
                }
                break;
            case CENTRE_SPACE_CHECK:
                //slide left ~6 inches
                //check if the distance sensor reads out between 1 and 4 inches
                if(hardware.rearDistance.getDistance(DistanceUnit.INCH) < 4.0) { //if the distance sensor reads out between 1 and 4 inches
                    scoringPieceAt = 1; //marker found at centre
                }
                else{
                    scoringPieceAt = 2; //It's at right.
                }
                autoState = GET_TO_HUB;
                break;
            case GET_TO_HUB:
                //slide left 2 feet left
                //drive backward ~6 inches and/or until the distance sensor is under 1 inch
                autoState = PLACE_ELEMENT;
                break;
            case PLACE_ELEMENT:
                if(scoringPieceAt == 0){
                    hardware.cabin.dropCargo();
                }
                else if(scoringPieceAt == 1){
                    hardware.elevator.setPosition(midLayerLevel, 1.0);
                }
                else if(scoringPieceAt == 2){
                    hardware.elevator.setPosition(topLayerLevel, 1.0);
                }
                hardware.cabin.dropCargo();
                autoState = GO_TO_CAROUSEL;
                break;
            case GO_TO_CAROUSEL:
                //go back a little
                //turn 90 degrees to the right (-90)
                //slide left to wall
                //go forward the dead reckon position
                //go forward another 4 inches "just in case"
                currentTime = this.time;
                autoState = SPIN_CAROUSEL;
                break;
            case SPIN_CAROUSEL:
                if((currentTime - this.time) < 3){ //wait 3 seconds
                    hardware.turntable.goTurnSpin(-0.5); //Set turntable power to something like 0.5
                }
                else{
                    hardware.turntable.goTurnSpin(0.0); //Set turntable power to 0
                }
                autoState = PARK_IN_TEAM_DEPOT;
                break;
            case PARK_IN_TEAM_DEPOT:
                //slide right 4 inches
                //drive forward 30 inches "just in case"
                //slide leftwards 5 inches
                //slide rightwards into depot (dead reckon)
                break;
        }
        telemetry.addData("Status","Running");
        telemetry.addData("State", autoState);
        //telemetry.addData("Distance in Centimeteres", sensorColorRange.getDistance(DistanceUnit.CM)); //The current range is 2-4 inches (3/2 its real distance)
        telemetry.update();
    }

    @Override
    public void stop() {
        hardware.updateValues();

        hardware.logMessage(false, "MyFirstJava", "Stop Button Pressed");
        hardware.stop();
        super.stop();
    }

    @Override
    public void start() {
        hardware.updateValues();

        hardware.logMessage(false, "MyFirstJava", "Start Button Pressed");
        super.start();
        hardware.start();
    }
}