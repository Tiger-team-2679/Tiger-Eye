package org.team2679.TigerEye.lib.log;

import java.util.Date;

/**
 * Interface used to handle every message made from all the loggers
 *
 * @author  SlowL0ris
 */
public interface LogHandler {

    /**
     * This function will be called every time someone is logging info
     * at one of the loggers
     * @param message the logged message
     * @param level the message's level
     * @param formatted the formatted message
     * @param logger the logger used
     */
    void onLog(String message, LOG_LEVEL level, String formatted, Logger logger);
}
