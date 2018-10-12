package org.team2679.TigerEye.core;

import com.ctre.phoenix.CTREJNIWrapper;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.hal.HAL;
import org.team2679.TigerEye.core.thread.Notifier;
import org.team2679.TigerEye.core.thread.NotifierListener;
import org.team2679.TigerEye.lib.log.Logger;

/**
 * the setup class of the robot
 *
 * @author SlowL0ris
 */
public class Tiger extends RobotBase {

    public static Logger log() {
        return Bootstrap.getTigerLogger();
    }

    private static Notifier main_notifier_100ms;
    private static Notifier main_notifier_50ms;
    private static Notifier main_notifier_20ms;

    @Override
    public void startCompetition() {
        try {
            // TODO write the pre init code
            HAL.observeUserProgramStarting();
            // TODO write the init code

            main_notifier_20ms = new Notifier("main_notifier_20ms", 20);
            main_notifier_50ms = new Notifier("main_notifier_50ms", 50);
            main_notifier_100ms = new Notifier("main_notifier_100ms", 100);

            main_notifier_20ms.start();
            main_notifier_50ms.start();
            main_notifier_100ms.start();

            StateTracker.init();
        }
        catch (Exception e){
            // TODO inform the crash tracker
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

    public static Notifier get_main_notifier_20ms() {
        return main_notifier_20ms;
    }

    public static Notifier get_main_notifier_50ms() {
        return main_notifier_50ms;
    }

    public static Notifier get_main_notifier_100ms() {
        return main_notifier_100ms;
    }
}
