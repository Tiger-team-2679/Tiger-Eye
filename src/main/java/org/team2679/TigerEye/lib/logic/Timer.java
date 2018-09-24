package org.team2679.TigerEye.lib.logic;

/**
 * A timer used to measure the time of an action
 * also has the ability to log the results and help with debugging
 */
public class Timer {

    private boolean isFinished;
    private boolean isStarted;

    private double startTime;
    private double endTime;

    /**
     * starts the time
     */
    public void start(){
        if(!isStarted) {
            this.startTime = System.currentTimeMillis();
            this.endTime = 0;
            isStarted = true;
        }
    }

    /**
     * stops the timer
     */
    public void stop(){
        if(!isFinished) {
            this.endTime = System.currentTimeMillis();
            isFinished = true;
        }
    }

    /**
     * this function will reset the, guess what, time!
     */
    public void reset(){
        this.startTime = 0;
        this.endTime = 0;
        this.isFinished = false;
        this.isStarted = false;
    }

    /**
     * function to return the timer's elapsed time
     * @return the timer elapsed time
     */
    public double getElapseTime(){
        if(isFinished){
            return endTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }

    /**
     * log the timed results in the logger
     * @param name the name the timer will be logged after
     */
    public void logTimer(String name){
        // TODO implement the logger call
    }

    /**
     * returns the most accurate time the system can get in nano
     * seconds
     * @return time in nano seconds
     */
    public static double getCurrentTimeNano(){
        return System.nanoTime();
    }

    /**
     * Alternative to System.currentTimeMillis because we can't find
     * the methods implementation so this is the most accurate
     * implementation known
     * @return time in milli seconds
     */
    public static double getCurrentTimeMillis(){
        return System.nanoTime() / 1000000.0;
    }

    /**
     * returns the nano time converted to seconds
     * @return time in seconds
     */
    public static double getCurrentTimeSeconds(){
        return System.nanoTime() / 1000000000.0;
    }
}
