package org.team2679.TigerEye.lib.state;

/**
 * The robot various states, used by the StateTracker
 * {@link org.team2679.TigerEye.core.StateTracker} Class
 *
 * @author  SlowL0ris
 */
public enum  ROBOT_STATE {
    NONE,
    DISABLED,
    TELEOP,
    AUTONOMOUS,
    TEST;

    /**
     * get the state's name
     * @return the name
     */
    public String getName(){
        return this.toString().toUpperCase();
    }
}
