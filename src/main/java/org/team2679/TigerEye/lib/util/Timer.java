package org.team2679.TigerEye.lib.util;

import org.team2679.TigerEye.lib.log.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * A timer used to measure the time of an action
 * also has the ability to log the results and help with debugging
 *
 * @author  SlowL0ris
 */
public class Timer {

    private static ConcurrentHashMap<String, Long> timed = new ConcurrentHashMap<String, Long>();
    private static Logger timerLogger = new Logger("Timer");

    /**
     * creates a new timer for a task with the given name
     * @param name the unique name of the task
     *
     */
    public static void start(String name){
        if(!timed.containsKey(name)) {
            timed.put(name, getCurrentTimeNano());
        }
        else {
            timerLogger.debug("Timer -> can't store two tasks with the same name");
        }
    }

    /**
     * stop the timer for the task with the given name, and logs the result
     * @param name the unique name of the task
     *
     */
    public static void stop(String name){
        if(timed.containsKey(name)) {
            timerLogger.debug(name + " finished in: " +
                    (getCurrentTimeNano() - timed.get(name))/1000000 + "MS");
            timed.remove(name);
        }
        else {
            timerLogger.debug("Timer -> a task with that name doesn't exist");
        }
    }

    /**
     * returns the most accurate time the system can get in nano
     * seconds
     * @return time in nano seconds
     */
    public static long getCurrentTimeNano(){
        return System.nanoTime();
    }

    /**
     * Alternative to System.currentTimeMillis because we can't find
     * the methods implementation so this is the most accurate
     * implementation known
     * @return time in milli seconds
     */
    public static long getCurrentTimeMillis(){
        return System.nanoTime() / 1000000;
    }

    /**
     * returns the nano time converted to seconds
     * @return time in seconds
     */
    public static long getCurrentTimeSeconds(){
        return System.nanoTime() / 1000000000;
    }
}
