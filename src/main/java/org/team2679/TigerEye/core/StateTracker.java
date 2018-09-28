package org.team2679.TigerEye.core;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.team2679.TigerEye.lib.log.Logger;
import org.team2679.TigerEye.lib.state.ROBOT_STATE;
import org.team2679.TigerEye.lib.state.StateListener;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * a class  used to be run at the end of the bootstrap to track the state
 * changes {@link ROBOT_STATE} operates like the base of the SampleRobot
 * works
 *
 * @author SlowLoris
 */
public class StateTracker {

    private static DriverStation m_ds = DriverStation.getInstance();

    private static ROBOT_STATE currentState = ROBOT_STATE.NONE;
    private static CopyOnWriteArrayList<StateListener> listeners = new CopyOnWriteArrayList<>();

    private static Logger stateTrackerLogger;

    /**
     * this function is used to initiate the state tracker
     * this is an an inifinite loop so make sure calling
     * it at the end of the setup
     */
    public static void init(){
        stateTrackerLogger = new Logger("StateTracker");
        // This is where the robot is ready
        HAL.observeUserProgramStarting();

        m_ds.waitForData();

        while (true) {
            /**
             * we chose to wait for the data from the ds rather than loop at a
             * given delay because we thought this will take less resources
             */
            m_ds.waitForData();
            if (m_ds.isDisabled()) {
                // Call DisabledInit() if we are now just entering disabled mode from either a different mode
                // or from power-on.
                if (currentState != ROBOT_STATE.DISABLED) {
                    LiveWindow.setEnabled(false);
                    callListeners(ROBOT_STATE.DISABLED, currentState);
                    currentState = ROBOT_STATE.DISABLED;
                    stateTrackerLogger.debug("StateTracker -> switched to DISABLED");
                }

                HAL.observeUserProgramDisabled();
                // TODO call disabled periodic
            } else if (m_ds.isAutonomous()) {
                // Call AutonomousInit() if we are now just entering autonomous mode from either a different
                // mode or from power-on.
                if (currentState != ROBOT_STATE.AUTONOMOUS) {
                    LiveWindow.setEnabled(false);
                    callListeners(ROBOT_STATE.AUTONOMOUS, currentState);
                    currentState = ROBOT_STATE.AUTONOMOUS;
                    stateTrackerLogger.debug("StateTracker -> switched to AUTONOMOUS");
                }

                HAL.observeUserProgramAutonomous();
                // TODO call autonomous periodic
            } else if (m_ds.isOperatorControl()) {
                if (currentState != ROBOT_STATE.TELEOP) {
                    LiveWindow.setEnabled(false);
                    callListeners(ROBOT_STATE.TELEOP, currentState);
                    currentState = ROBOT_STATE.TELEOP;
                    stateTrackerLogger.debug("StateTracker -> switched to TELEOP");
                }
                HAL.observeUserProgramTeleop();
                // TODO call teleop periodic
            } else {
                if (currentState != ROBOT_STATE.TEST) {
                    LiveWindow.setEnabled(false);
                    callListeners(ROBOT_STATE.TEST, currentState);
                    currentState = ROBOT_STATE.TEST;
                    stateTrackerLogger.debug("StateTracker -> switched to TEST");
                }
                HAL.observeUserProgramTest();
                // TODO call test periodic
            }
            LiveWindow.updateValues();
        }
    }

    /**
     * a private function used bu this call to call all listeners
     * @param newState the new state the robot is entering to
     * @param oldState the state the robot got out from
     */
    private static void callListeners(ROBOT_STATE newState, ROBOT_STATE oldState){
        for (StateListener listener : listeners) {
            listener.onStateChange(newState, oldState);
        }
    }

    /**
     * this is used to register an event change listener
     * @param listener
     */
    public static void registerListener(StateListener listener){
        listeners.add(listener);
    }

    /**
     * this function is used to remove a listener from the list
     * @param listener
     */
    public static void removeListener(StateListener listener){
        listeners.remove(listener);
    }

    /**
     * returns the current state determined by the state tracker loop
     * @return the current state
     */
    public static ROBOT_STATE getCurrentState(){
        return currentState;
    }
}
