package org.team2679.TigerEye.lib.util;

public class ConsoleColors {

    public static String colorize(String s, COLOR color) {
        if(color != null) {
            return color.getColorCode() + s + COLOR.RESET.getColorCode();
        }
        return s;
    }

    public static String reset(String s) {
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    public enum COLOR {
        RESET("\033[0m"),
        // Regular Colors
        BLACK("\033[0;30m"),   // BLACK
        RED("\033[0;31m"),     // RED
        GREEN("\033[0;32m"),   // GREEN
        YELLOW("\033[0;33m"),  // YELLOW
        BLUE("\033[0;34m"),    // BLUE
        PURPLE("\033[0;35m"),  // PURPLE
        CYAN("\033[0;36m"),    // CYAN
        WHITE("\033[0;37m"),  // WHITE

        // Bold
        BLACK_BOLD ("\033[1;30m"),  // BLACK
        RED_BOLD ("\033[1;31m"),    // RED
        GREEN_BOLD ("\033[1;32m"),  // GREEN
        YELLOW_BOLD ("\033[1;33m"), // YELLOW
        BLUE_BOLD ("\033[1;34m"),   // BLUE
        PURPLE_BOLD ("\033[1;35m"), // PURPLE
        CYAN_BOLD ("\033[1;36m"),   // CYAN
        WHITE_BOLD ("\033[1;37m"),  // WHITE

        // High Intensity
        BLACK_BRIGHT ("\033[0;90m"),  // BLACK
        RED_BRIGHT ("\033[0;91m"),    // RED
        GREEN_BRIGHT ("\033[0;92m"),  // GREEN
        YELLOW_BRIGHT ("\033[0;93m"), // YELLOW
        BLUE_BRIGHT ("\033[0;94m"),   // BLUE
        PURPLE_BRIGHT ("\033[0;95m"), // PURPLE
        CYAN_BRIGHT ("\033[0;96m"),   // CYAN
        WHITE_BRIGHT ("\033[0;97m"),  // WHITE

        // Bold High Intensity
        BLACK_BOLD_BRIGHT ("\033[1;90m"), // BLACK
        RED_BOLD_BRIGHT ("\033[1;91m"),   // RED
        GREEN_BOLD_BRIGHT ("\033[1;92m"), // GREEN
        YELLOW_BOLD_BRIGHT ("\033[1;93m"),// YELLOW
        BLUE_BOLD_BRIGHT ("\033[1;94m"),  // BLUE
        PURPLE_BOLD_BRIGHT ("\033[1;95m"),// PURPLE
        CYAN_BOLD_BRIGHT ("\033[1;96m"),  // CYAN
        WHITE_BOLD_BRIGHT ("\033[1;97m"); // WHITE

        private String color;

        COLOR(String code) {
            this.color = code;
        }

        /**
         * getting the color's name
         * @return color name
         */
        private String getColorName(){
            return this.toString().toUpperCase();
        }

        /**
         * getting the color's code, the code is a string with the ascii letters
         * used by the ANSI method
         * @return the color ansi code
         */
        private String getColorCode(){
            return this.color;
        }
    }
}