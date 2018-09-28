/*----------------------------------------------------------------------------*/
/* Copyright (c) 2016-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import org.team2679.TigerEye.core.simulation.SimulationData;
import org.team2679.TigerEye.lib.state.ROBOT_STATE;

import java.nio.ByteBuffer;

/**
 * JNI Wrapper for HAL<br>.
 */
@SuppressWarnings({"AbbreviationAsWordInName", "MethodName", "PMD.TooManyMethods"})
public final class HAL extends JNIWrapper {
    public static void waitForDSData(){
        while (!SimulationData.is_new_data_available){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SimulationData.is_new_data_available = false;
    }

    public static boolean initialize(int timeout, int mode) { return false; };

    public static void observeUserProgramStarting() {  };

    public static void observeUserProgramDisabled() {  };

    public static void observeUserProgramAutonomous() {  };

    public static void observeUserProgramTeleop() {  };

    public static void observeUserProgramTest() {  };

    public static void report(int resource, int instanceNumber) {
        report(resource, instanceNumber, 0, "");
    }

    public static void report(int resource, int instanceNumber, int context) {
        report(resource, instanceNumber, context, "");
    }

    /**
     * Report the usage of a resource of interest. <br>
     *
     * <p>Original signature: <code>uint32_t report(tResourceType, uint8_t, uint8_t, const
     * char*)</code>
     *
     * @param resource       one of the values in the tResourceType above (max value 51). <br>
     * @param instanceNumber an index that identifies the resource instance. <br>
     * @param context        an optional additional context number for some cases (such as module
     *                       number). Set to 0 to omit. <br>
     * @param feature        a string to be included describing features in use on a specific
     *                       resource. Setting the same resource more than once allows you to change
     *                       the feature string.
     */
    public static int report(int resource, int instanceNumber, int context, String feature){
        return 0;
    };

    public static int nativeGetControlWord() {
        return 0;
    };

    @SuppressWarnings("JavadocMethod")
    public static void getControlWord(ControlWord controlWord) {
        // TODO simulate the parts about the emergency stop, the fms, and ds attached
        ROBOT_STATE s = SimulationData.currentState;
        controlWord.update(s != ROBOT_STATE.DISABLED, s == ROBOT_STATE.AUTONOMOUS, s == ROBOT_STATE.TEST,
                false, false, true);
    }

    private static int nativeGetAllianceStation(){
        return SimulationData.alliance_station;
    };

    @SuppressWarnings("JavadocMethod")
    public static AllianceStationID getAllianceStation() {
        switch (nativeGetAllianceStation()) {
            case 0:
                return AllianceStationID.Red1;
            case 1:
                return AllianceStationID.Red2;
            case 2:
                return AllianceStationID.Red3;
            case 3:
                return AllianceStationID.Blue1;
            case 4:
                return AllianceStationID.Blue2;
            case 5:
                return AllianceStationID.Blue3;
            default:
                return null;
        }
    }

    @SuppressWarnings("JavadocMethod")
    public static native boolean isNewControlData();

    @SuppressWarnings("JavadocMethod")
    public static native void releaseDSMutex();

    @SuppressWarnings("JavadocMethod")
    public static native boolean waitForDSDataTimeout(double timeout);

    public static int kMaxJoystickAxes = 12;
    public static int kMaxJoystickPOVs = 12;

    public static short getJoystickAxes(byte joystickNum, float[] axesArray){
        // TODO implement
        return 0;
    };

    public static short getJoystickPOVs(byte joystickNum, short[] povsArray){
        // TODO implement
        return 0;
    };

    public static int getJoystickButtons(byte joystickNum, ByteBuffer count){
        // TODO implement
        return 0;
    };

    public static int setJoystickOutputs(byte joystickNum, int outputs, short leftRumble,
                                                short rightRumble){
        // TODO implement
        return 0;
    };

    public static int getJoystickIsXbox(byte joystickNum){
        // TODO implement
        return 0;
    };

    public static int getJoystickType(byte joystickNum){
        // TODO implement
        return 0;
    };

    public static String getJoystickName(byte joystickNum){
        // TODO implement
        return "None";
    };

    public static int getJoystickAxisType(byte joystickNum, byte axis){
        // TODO implement
        return 0;
    };

    public static double getMatchTime(){
        // TODO implement
        return 120;
    };

    public static boolean getSystemActive(){
        return true;
    };

    public static boolean getBrownedOut(){
        return false;
    };

    public static int getMatchInfo(MatchInfoData info){
        // TODO implement
        return 0;
    };

    public static int sendError(boolean isError, int errorCode, boolean isLVCode,
                                       String details, String location, String callStack,
                                       boolean printMsg){
        return 0;
    }

    public static int getPortWithModule(byte module, byte channel){
        // TODO implement
        return 0;
    };

    public static int getPort(byte channel){
        // TODO implement
        return 0;
    };

    private HAL() {

    }
}