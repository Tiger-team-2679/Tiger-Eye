package org.team2679.TigerEye.core;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.HLUsageReporting;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.internal.HardwareHLUsageReporting;
import edu.wpi.first.wpilibj.internal.HardwareTimer;
import edu.wpi.first.wpilibj.util.WPILibVersion;
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
        startTimeNS = Timer.getCurrentTimeNano();
        startTimeMS = Timer.getCurrentTimeMillis();

        // TODO check if the loading time is normal

        //TODO get environment arguments to check if simulation
        isSimulation = true;

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
        runRobot();
    }

    private static void runRobot(){
        Tiger tiger = new Tiger();

        edu.wpi.first.wpilibj.Timer.SetImplementation(new HardwareTimer());
        HLUsageReporting.SetImplementation(new HardwareHLUsageReporting());
        RobotState.SetImplementation(DriverStation.getInstance());

        // TODO deal with the cscore lib
        //CameraServerJNI.enumerateSinks();

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

        tiger.startCompetition();
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
}
