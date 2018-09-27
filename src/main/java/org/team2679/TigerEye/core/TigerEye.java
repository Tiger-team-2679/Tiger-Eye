package org.team2679.TigerEye.core;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.hal.HAL;
import org.team2679.TigerEye.core.loader.RobotLoader;
import org.team2679.TigerEye.lib.log.Logger;

import java.io.File;

/**
 * the boorstrap class of the robot, THis class is supposed to be called by
 * the deploy script
 *
 * @author SlowL0ris
 */
public class TigerEye extends RobotBase {

    public static File homeDirectory = new File("/home/slowl0ris/Desktop/Tiger Eye/");

    @Override
    public void startCompetition() {
        Logger logger = new Logger("Tiger Eye");

        SysOutFileWriter.init();
        RobotLoader.loadModules();
    }

    public static void main(String args[]){
        HAL.initialize(500, 0);
        TigerEye tigerEye = new TigerEye();
        tigerEye.startCompetition();
    }
}
