package org.team2679.TigerEye.core.loader;


import org.team2679.TigerEye.lib.module.EyeModule;
import org.team2679.TigerEye.lib.logic.Timer;

import java.util.List;

/**
 * An executor to initiate the robot and user code
 */
public class RobotLoader {

    private static List<Class<?>> classes;

    /**
     * find all the modules imported before the loading
     */
    public static void loadModules(){
        Timer moduleSearchTimer = new Timer();
        moduleSearchTimer.start();
        classes = ClasspathScannerBridge.scan_for_subclass("*", EyeModule.class);
        moduleSearchTimer.stop();
        for (int i = 0; i < classes.size(); i++) {
            if(!classes.get(0).getPackage().getName().contains("TigerEye.lib.module")){
                classes.remove(classes.get(i));
            }
        }
        moduleSearchTimer.logTimer("module Loader");
        preinit();
    }

    /**
     * function to execute all of the modules' preinit scripts
     */
    private static void preinit(){
        for (Class<?> aClass : classes) {
            System.out.println(aClass.getPackage().getName().contains("TigerEye.lib.module"));
        }
        init();
    }

    /**
     * function to execute all of the modules' init scripts
     */
    private static void init(){

    }
}
