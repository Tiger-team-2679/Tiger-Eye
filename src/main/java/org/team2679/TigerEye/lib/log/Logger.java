package org.team2679.TigerEye.lib.log;

import org.team2679.TigerEye.lib.util.ConsoleColors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * a logger used to log messages from all the modules and systems
 * to be used by all the systems and modules
 */
public class Logger {

    private String name;
    private static CopyOnWriteArrayList<LogHandler> handlers = new CopyOnWriteArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy-hh:mm:ss");

    /**
     * returns a time formatted like this [dd/MM/yy-hh:mm:ss]
     * @return
     */
    String getTime() {
        return dateFormat.format(new Date());
    }

    /**
     * Creates a logger given a name
     * @param name
     */
    public Logger(String name) {
        this.name = name;
    }

    /**
     * returns the logger's name
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * formats the log message and send it to all the handlers
     * @param message
     * @param level
     */
    private void log(String message, LOG_LEVEL level){
        StringBuilder builder = new StringBuilder();
        if(!level.equals(LOG_LEVEL.RAW)) {
            builder.append(getTagString(level));
        }

        builder.append(message);
        String fm = builder.toString();

        // send out the logged info the Splitter
        System.out.println(fm);

        for (LogHandler handler : handlers) {
            handler.onLog(message, level, fm,this);
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
     * @param message
     */
    public void debug(String message){
        this.log(message, LOG_LEVEL.DEBUG);
    }

    /**
     * log an info message given a name
     * @param message
     */
    public void info(String message){
        this.log(message, LOG_LEVEL.INFO);
    }

    /**
     * log a warning message given a name
     * @param message
     */
    public void warning(String message){
        this.log(message, LOG_LEVEL.WARNING );
    }

    /**
     * log an error message given a name
     * @param message
     */
    public void error(String message){
        this.log(message, LOG_LEVEL.ERROR);
    }

    /**
     * log a fatal message given a name
     * @param message
     */
    public void fatal(String message){
        this.log(message, LOG_LEVEL.FATAL);
    }

    /**
     * register a log handler
     * @param handler
     */
    public static void registerHandler(LogHandler handler){
        handlers.add(handler);
    }

    /**
     * remove a log handler
     * @param handler
     */
    public static void removeHandler(LogHandler handler){
        handlers.remove(handler);
    }
}
