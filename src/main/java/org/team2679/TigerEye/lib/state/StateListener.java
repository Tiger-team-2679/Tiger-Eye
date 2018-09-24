package org.team2679.TigerEye.lib.state;

public interface StateListener {

    void onStateChange(ROBOT_STATE state, ROBOT_STATE oldState);
}
