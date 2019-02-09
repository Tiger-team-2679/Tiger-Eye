package org.team2679.TigerEye.core;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.RobotBase;
import org.team2679.TigerEye.lib.thread.Notifier;
import org.team2679.TigerEye.lib.exceptions.SetupClassNotFoundException;
import org.team2679.TigerEye.lib.log.Logger;

/**
 * the setup class of the robot
 *
 * @author SlowL0ris
 */
public class Tiger {

    public synchronized static Logger log() {
        return Bootstrap.getTigerLogger();
    }

    private static Notifier main_notifier_100ms;
    private static Notifier main_notifier_50ms;
    private static Notifier main_notifier_20ms;

    void startCompetition() {
        try {
            String setupClassLocation = TigerEyeProperties.getProperty(TigerEyeProperties.SETUP_CLASS_KEY);
            Setup setup = null;
            try {
                Class cls = java.lang.ClassLoader.getSystemClassLoader().loadClass(setupClassLocation);
                Object object = cls.getDeclaredConstructor().newInstance();
                setup = (Setup) object;
            }
            catch (Exception e){
                throw new SetupClassNotFoundException(setupClassLocation);
            }

            main_notifier_20ms = new Notifier("main_notifier_20ms", 20);
            main_notifier_50ms = new Notifier("main_notifier_50ms", 50);
            main_notifier_100ms = new Notifier("main_notifier_100ms", 100);

            main_notifier_20ms.start();
            main_notifier_50ms.start();
            main_notifier_100ms.start();

            setup.preinit();
            HAL.observeUserProgramStarting();
            setup.init();

            StateTracker.init();
        }
        catch (Exception e){
            log().fatal("A fatal exception made the code crash",e);
            shutdownCrash();
        }
    }

    public void shutdownSafely() {
        log().info("Robot Shutting Down...");
        System.exit(0);
    }

    public void shutdownCrash() {
        log().info("Robot Error Detected... Shutting Down...");
        System.exit(-1);
    }

    public static synchronized Notifier get_main_notifier_20ms() {
        return main_notifier_20ms;
    }

    public static synchronized Notifier get_main_notifier_50ms() {
        return main_notifier_50ms;
    }

    public static synchronized Notifier get_main_notifier_100ms() {
        return main_notifier_100ms;
    }
}
