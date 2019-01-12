package org.team2679.TigerEye.lib.log;

import org.team2679.TigerEye.lib.util.ConsoleColors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * a logger used to log messages from all the modules and systems
 * to be used by all the systems and modules
 *
 * @author  SlowL0ris
 */
public class Logger {

    private static final Object printLock = new Object();
    private String name;
    private static CopyOnWriteArrayList<LogHandler> handlers = new CopyOnWriteArrayList<>();
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy-hh:mm:ss");

    /**
     * returns a time formatted like this [dd/MM/yy-hh:mm:ss]
     * @return the current time in the log format
     */
    private static String getTime() {
        return dateFormat.format(new Date());
    }

    /**
     * Creates a logger given a name
     * @param name the name of the logger to be created
     */
    public Logger(String name) {
        this.name = name;
    }

    /**
     * returns the logger's name
     * @return the name of the logger
     */
    public String getName(){
        return this.name;
    }

    /**
     * formats the log message and send it to all the handlers
     * @param message the message to log
     * @param level the level of the message
     */
    private void log(String message, LOG_LEVEL level){
        log(message, null, level);
    }

    /**
     * formats the log message with an exception and send it to all the handlers
     * @param message the message to log
     * @param e the exception to log (if null, no exception will be printed)
     * @param level the level of the message
     */
    private void log(String message, Exception e, LOG_LEVEL level){
        StringBuilder builder = new StringBuilder();
        if(!level.equals(LOG_LEVEL.RAW)) {
            builder.append(getTagString(level));
        }

        builder.append(message);
        String fm = builder.toString();

        // send out the logged info the Splitter
        synchronized(printLock) {
            System.out.println(fm);
            if (e != null) {
                e.printStackTrace();
            }
        }

        for (LogHandler handler : handlers) {
            handler.onLog(message,e, level, fm,this);
        }
    }

    /**
     * returns a formatted string of all the tags including the level's one
     * with color, threads id and logger's name
     * @param level the log level
     * @return the formatted tag string
     */
    private String getTagString(LOG_LEVEL level){
        StringBuilder builder = new StringBuilder();
        builder.append("[" + getTime() + "] ");
        builder.append("[" + this.getName() + "] ");
        builder.append("[" + Thread.currentThread().getName() + "] ");
        builder.append("[" + ConsoleColors.colorize(level.getName(), level.getColor()) + "] ");

        return builder.toString();
    }

    /**
     * log a debug message given a name
     * @param message the message to log
     */
    public void debug(String message){
        this.log(message, LOG_LEVEL.DEBUG);
    }

    /**
     * log an info message given a name
     * @param message the message to log
     */
    public void info(String message){
        this.log(message, LOG_LEVEL.INFO);
    }

    /**
     * log a warning message given a name
     * @param message the message to log
     */
    public void warning(String message){
        this.log(message, LOG_LEVEL.WARNING );
    }

    /**
     * log an error message given a name
     * @param message the message to log
     */
    public void error(String message){
        this.log(message, LOG_LEVEL.ERROR);
    }

    /**
     * log an exception as an error
     * @param message the message to log
     */
    public void error(String message, Exception e){
        this.log(message,e, LOG_LEVEL.ERROR);
    }

    /**
     * log a fatal message given a name
     * @param message the message to log
     */
    public void fatal(String message){
        this.log(message, LOG_LEVEL.FATAL);
    }


    /**
     * log an exception as a fatal exception
     * @param message the message to log
     */
    public void fatal(String message,Exception e){
        this.log(message,e, LOG_LEVEL.FATAL);
    }

    /**
     * register a log handler
     * @param handler the message to log
     */
    public static void registerHandler(LogHandler handler){
        handlers.add(handler);
    }

    /**
     * remove a log handler
     * @param handler the message to log
     */
    public static void removeHandler(LogHandler handler){
        handlers.remove(handler);
    }
}
