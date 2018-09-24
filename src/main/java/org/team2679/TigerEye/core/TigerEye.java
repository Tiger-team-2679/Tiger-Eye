package org.team2679.TigerEye.core;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SampleRobot;
import org.team2679.TigerEye.core.loader.RobotLoader;
import org.team2679.TigerEye.core.loops.Notifier;
import org.team2679.TigerEye.core.loops.NotifierListener;
import org.team2679.TigerEye.lib.log.Logger;
import org.team2679.TigerEye.lib.util.ConsoleColors;

import java.io.File;

/**
 * the boorstrap class of the robot, THis class is supposed to be called by
 * the deploy script
 *
 * @author SlowL0ris
 */
public class TigerEye extends RobotBase  {

    public static File homeDirectory = new File("/home/slowl0ris/Desktop/Tiger Eye/");

    @Override
    public void startCompetition() {

    }

    public static void main(String args[]){
        Logger logger = new Logger("Tiger Eye");

        SysOutFileWriter.init();
        RobotLoader.loadModules();

        logger.fatal("crashing!!!!!!!");
        logger.debug(ConsoleColors.colorize("hello kids, debug message here", ConsoleColors.COLOR.BLUE_BOLD_BRIGHT));

        Notifier notifier = new Notifier("test", 100);
        notifier.registerListener(new NotifierListener() {
            @Override
            public void onUpdate() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        notifier.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifier.stop();
    }

}
