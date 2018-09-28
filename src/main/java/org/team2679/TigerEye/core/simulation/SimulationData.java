package org.team2679.TigerEye.core.simulation;

import edu.wpi.first.wpilibj.DriverStation;
import org.team2679.TigerEye.lib.state.ROBOT_STATE;

public class SimulationData {

    public static ROBOT_STATE currentState = ROBOT_STATE.DISABLED;
    public static int alliance_station;
    public static boolean is_new_data_available = false;

}
