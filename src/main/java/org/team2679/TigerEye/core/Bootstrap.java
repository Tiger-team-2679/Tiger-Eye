package org.team2679.TigerEye.core;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import org.team2679.TigerEye.lib.log.Logger;
import org.team2679.TigerEye.lib.util.Timer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Supplier;

class Bootstrap {

    // get the logger for the tiger eye
    private static Logger tigerLogger;

    // Is this is simulation? "You are in a simulation, inside another simulation!"
    private static boolean isSimulation;

    private static File tigerHome = new File("/home/lvuser/tiger/");

    private static long startTimeNS;
    private static long startTimeMS;

    public static void main(String args[]){
        try {
            Timer.start("Bootstrap");
            startTimeNS = Timer.getCurrentTimeNano();
            startTimeMS = Timer.getCurrentTimeMillis();

            isSimulation = !RobotBase.isReal();

            if (isSimulation) {
                tigerHome = new File("tiger/").getAbsoluteFile();
                // do the stuff we need in the simulation
            }
            tigerHome.mkdirs();

            SysOutFileWriter.init();

            tigerLogger = new Logger("Tiger");

            System.out.println(getSplash());
            System.out.println("Tiger Loaded On OS: " + getOS());
            System.out.println("Hardware Provider: " + getHardwareProvider());

            Timer.stop("Bootstrap");

            runRobot(Tiger::new);
        }
        catch(Exception e) {
            System.err.println("Error in bootstrap, Something went really really wrong.");
        }
    }

    /**
     * Starting point for the robot.
     */
    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidCatchingThrowable",
            "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    private static <T extends Tiger> void runRobot(Supplier<T> robotSupplier) {
        if (!HAL.initialize(500, 0)) {
            throw new IllegalStateException("Failed to initialize. Terminating");
        }

        // Call a CameraServer JNI function to force OpenCV native library loading
        // Needed because all the OpenCV JNI functions don't have built in loading
        CameraServerJNI.enumerateSinks();

        HAL.report(FRCNetComm.tResourceType.kResourceType_Language, FRCNetComm.tInstances.kLanguage_Java);

        T robot = robotSupplier.get();

        if (RobotBase.isReal()) {
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

        robot.startCompetition();
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
