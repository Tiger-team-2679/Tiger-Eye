package org.team2679.TigerEye.lib.exceptions;

public class PropertiesFileNotFoundException extends RuntimeException {
    public PropertiesFileNotFoundException(){
        super("Properties file not found");
    }
}
