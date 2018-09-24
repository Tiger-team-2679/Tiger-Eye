package org.team2679.TigerEye.lib.log;

import org.team2679.TigerEye.lib.util.ConsoleColors;

/**
 * Types of different logged notification levels
 */
enum LOG_LEVEL {
    RAW,
    DEBUG(ConsoleColors.COLOR.GREEN),
    INFO(ConsoleColors.COLOR.BLUE),
    WARNING(ConsoleColors.COLOR.YELLOW),
    ERROR(ConsoleColors.COLOR.RED),
    FATAL(ConsoleColors.COLOR.PURPLE);

    private ConsoleColors.COLOR color = null;

    LOG_LEVEL(ConsoleColors.COLOR color){
        this.color = color;
    }

    LOG_LEVEL(){

    }

    /**
     * getting the level's name
     * @return level's name
     */
    public String getName(){
        return this.toString().toUpperCase();
    }

    /**
     * returns the level's color
     * @return level's color
     */
    public ConsoleColors.COLOR getColor(){
        return color;
    }
}
