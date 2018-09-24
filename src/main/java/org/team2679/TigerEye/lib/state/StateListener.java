package org.team2679.TigerEye.lib.state;

/**
 * A class used as a listener by the StateTracker
 * {@link org.team2679.TigerEye.core.StateTracker}
 *
 * @author  SlowL0ris
 */
public interface StateListener {

    void onStateChange(ROBOT_STATE state, ROBOT_STATE oldState);
}
