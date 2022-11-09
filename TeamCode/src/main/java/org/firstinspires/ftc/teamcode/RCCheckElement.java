package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RCCheckElement extends RobCommand{
    int place = -1;
    boolean done = false;

    public RCCheckElement(Hardware hardware, int place){
        this.hardware = hardware;
        this.place = place;
    }

    /*public void run(){
        if(hardware.rearDistance.getDistance(DistanceUnit.INCH) < 4.0) { //if the distance sensor reads out between 1 and 4 inches
            ((AutoOne)hardware.opMode).scoringPieceAt = place;
        }
        done = true;
    }

    public boolean isComplete(){
        hardware.opMode.telemetry.addData("isComplete checkElement", place);

        if (done){
            return true;
        }
        return false;*/

    public void run(){
      }

    public boolean isComplete(){
        //hardware.opMode.telemetry.addData("isComplete checkElement", place);
        if (hardware.rearDistance.getDistance(DistanceUnit.INCH) < 7.0){
            ((AutoOne)hardware.opMode).scoringPieceAt = place;
        }
        return true;
    }
}
