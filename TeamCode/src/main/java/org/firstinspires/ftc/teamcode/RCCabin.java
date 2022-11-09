package org.firstinspires.ftc.teamcode;

import java.util.Locale;

public class RCCabin extends RobCommand{
    public               String position;

    public RCCabin(Hardware hardware, String position){
        this.hardware = hardware;
        this.position = position.toLowerCase();
    }

    public void run(){
        if(position.equals("drop"))
        {
            hardware.cabin.dropCargo();
        }
        else{ hardware.cabin.holdCargo(); }
    }

    public boolean isComplete(){
//        hardware.opMode.telemetry.addData("isComplete tarElevatorPos", position);

        if ((position.equals("drop") && hardware.cabin.getCurrentPos() == 0.43) || (position.equals("hold") && hardware.cabin.getCurrentPos() == 0.75)){
            return true;
        }
        return false;
    }
}
