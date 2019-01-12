package org.team2679.TigerEye.lib.exceptions;

public class SetupClassNotFoundException extends RuntimeException {
    public SetupClassNotFoundException(String attemptedLocation){
        super("Setup class at '" + attemptedLocation + "' was not found");
    }
}
