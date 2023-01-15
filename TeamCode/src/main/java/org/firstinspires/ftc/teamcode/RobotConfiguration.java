package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RobotConfiguration {
    public boolean isRed = false;
    public boolean isLeftStartPos = false;

    public RobotConfiguration(){}
    public RobotConfiguration(boolean isRed, boolean isLeftStartPos){
        this.isRed = isRed;
        this.isLeftStartPos = isLeftStartPos;
    }

    public void saveConfig(){
        FileWriter configFile = null;
        try{
            configFile = new FileWriter(Environment.getExternalStorageDirectory().getPath() + "/FIRST/RoboConfig2223.txt", false);

            if(isRed){
                configFile.write('R');
            }else{
                configFile.write('B');
            }

            if(isLeftStartPos){
                configFile.write('L');
            }else{
                configFile.write('R');
            }

            configFile.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readConfig(){
        FileReader configFile = null;
        try{
            configFile = new FileReader(Environment.getExternalStorageDirectory().getPath() + "/FIRST/RoboConfig2223.txt");
            char tempValue = ' ';

            //Read Team Color
            tempValue = (char) configFile.read();
            if (tempValue == 'R') {
                isRed = true;
            } else {
                isRed = false;
            }

            //Read Team Staring Position
            tempValue = (char) configFile.read();
            if (tempValue == 'R') {
                isLeftStartPos = false;
            } else {
                isLeftStartPos = true;
            }

            configFile.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
