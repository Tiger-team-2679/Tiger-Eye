package org.team2679.TigerEye.core;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import org.team2679.TigerEye.core.simulation.DriverStationCommunication;
import org.team2679.TigerEye.lib.log.Logger;
import org.team2679.TigerEye.lib.util.Timer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Bootstrap {

    // get the logger for the tiger eye
    private static Logger tigerLogger;

    // Is this is simulation? "You are in a simulation, inside another simulation!"
    private static boolean isSimulation;

    private static File tigerHome = new File("/home/lvuser/tiger/");

    private static long startTimeNS;
    private static long startTimeMS;

    public static void main(String args[]){
        Timer.start("Bootstrap");
        startTimeNS = Timer.getCurrentTimeNano();
        startTimeMS = Timer.getCurrentTimeMillis();

        // TODO check if the loading time is normal

        //TODO get environment arguments to check if simulation
        isSimulation = HALUtil.getHALRuntimeType() != 0;

        if(isSimulation){
            tigerHome = new File("tiger/").getAbsoluteFile();
            // do the stuff we need in the simulation
        }
        tigerHome.mkdirs();

        // loading the logger utilities and crash handler
        // TODO complete implementation
        SysOutFileWriter.init();
        tigerLogger = new Logger("Tiger");

        System.out.println(getSplash());
        System.out.println("Tiger Loaded On OS: " + getOS());
        System.out.println("Hardware Provider: " + getHardwareProvider());

        // TODO load all the modules here (pre init)

        // TODO initialize the robot
        if(isSimulation){
            DriverStationCommunication.init();
        }
        Timer.stop("Bootstrap");

        runRobot();
    }

    private static void runRobot(){
        if (!HAL.initialize(500, 0)) {
            throw new IllegalStateException("Failed to initialize. Terminating");
        }
// Call a CameraServer JNI function to force OpenCV native library loading
        // Needed because all the OpenCV JNI functions don't have built in loading
        if(!isSimulation) {
            CameraServerJNI.enumerateSinks();
        }
        HAL.report(FRCNetComm.tResourceType.kResourceType_Language, FRCNetComm.tInstances.kLanguage_Java);

        Tiger robot;
        try {
            robot = new Tiger();
        } catch (Throwable throwable) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                throwable = cause;
            }
            String robotName = "Unknown";
            StackTraceElement[] elements = throwable.getStackTrace();
            if (elements.length > 0) {
                robotName = elements[0].getClassName();
            }
            DriverStation.reportError("Unhandled exception instantiating robot " + robotName + " "
                    + throwable.toString(), elements);
            DriverStation.reportWarning("Robots should not quit, but yours did!", false);
            DriverStation.reportError("Could not instantiate robot " + robotName + "!", false);
            System.exit(1);
            return;
        }

        if(!isSimulation) {
            try {
                final File file = new File("/tmp/frc_versions/FRC_Lib_Version.ini");

                if (file.exists()) {
                    file.delete();
                }

                file.createNewFile();

                try (OutputStream output = Files.newOutputStream(file.toPath())) {
                    output.write("Java ".getBytes(StandardCharsets.UTF_8));
                    output.write(WPILibVersion.Version.getBytes(StandardCharsets.UTF_8));
                }

            } catch (IOException ex) {
                DriverStation.reportError("Could not write FRC_Lib_Version.ini: " + ex.toString(),
                        ex.getStackTrace());
            }
        }

        boolean errorOnExit = false;
        try {
            robot.startCompetition();
        } catch (Throwable throwable) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                throwable = cause;
            }
            DriverStation.reportError("Unhandled exception: " + throwable.toString(),
                    throwable.getStackTrace());
            errorOnExit = true;
        } finally {
            // startCompetition never returns unless exception occurs....
            DriverStation.reportWarning("Robots should not quit, but yours did!", false);
            if (errorOnExit) {
                DriverStation.reportError(
                        "The startCompetition() method (or methods called by it) should have "
                                + "handled the exception above.", false);
            } else {
                DriverStation.reportError("Unexpected return from startCompetition() method.", false);
            }
        }
        System.exit(1);
    }

    private static String getSplash(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(Bootstrap.class.getResourceAsStream("/ascii/splash.txt")))) {
            String ln;
            String total = "";
            while ((ln = reader.readLine()) != null) {
                total += ln + "\n";
            }
            return total;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getOS(){
        return System.getProperty("os.name");
    }

    public static String getHardwareProvider(){
        if(isSimulation){
            return "Simulation";
        }
        else {
            return "Roborio";
        }
    }

    public static Logger getTigerLogger() {
        return tigerLogger;
    }

    public static File getTigerHome() {
        return tigerHome;
    }

    public static long getStartTimeNS(){
        return startTimeNS;
    }

    public static long getStartTimeMS() {
        return startTimeMS;
    }
}
