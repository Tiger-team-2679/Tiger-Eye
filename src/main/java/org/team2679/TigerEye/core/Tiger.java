package org.team2679.TigerEye.core;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.hal.HAL;
import org.team2679.TigerEye.core.simulation.DriverStationCommunication;
import org.team2679.TigerEye.lib.log.Logger;
import org.team2679.TigerEye.lib.util.Timer;

import java.io.File;

/**
 * the boorstrap class of the robot, THis class is supposed to be called by
 * the deploy script
 *
 * @author SlowL0ris
 */
public class Tiger extends RobotBase {

    public static Logger log() {
        return Bootstrap.getTigerLogger();
    }

    @Override
    public void startCompetition() {
        Logger logger = new Logger("Tiger Eye");


        StateTracker.init();
    }

    public static void main(String args[]){
        SysOutFileWriter.init();

        logger = new Logger("Tiger Eye");

        HAL.initialize(500, 0);
        Tiger tigerEye = new Tiger();
        startTimeNS = Timer.getCurrentTimeNano();

        // TODO find a not ugly way to load all modules and system

        if(RobotBase.isSimulation()){
            DriverStationCommunication.init();
        }

        // run this at the end of everything
        RobotBase.startRobot(Tiger::new);
    }

    public static long getStartTimeNS() {
        return startTimeNS;
    }

    public static Logger getLogger() {
        return logger;
    }
}
