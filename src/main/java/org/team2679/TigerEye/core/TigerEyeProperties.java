package org.team2679.TigerEye.core;

import org.team2679.TigerEye.lib.exceptions.PropertiesFileNotFoundException;
import org.team2679.TigerEye.lib.log.Logger;

import java.io.InputStream;
import java.util.Properties;

public class TigerEyeProperties {

    private final static String FILE_NAME = "/tiger.properties";
    private static Properties prop;

    private static void loadPropertiesFile() throws PropertiesFileNotFoundException {
        Logger logger = new Logger("properties-loader");
        prop = new Properties();
        InputStream in = TigerEyeProperties.class.getResourceAsStream(FILE_NAME);
        try {
            prop.load(in);
            in.close();
        } catch (Exception e) {
            throw new PropertiesFileNotFoundException();
        }
    }

    public static String getProperty(String key) throws PropertiesFileNotFoundException {
        if(prop == null){
            loadPropertiesFile();
        }
        return prop.getProperty(key);
    }

    public static final String SETUP_CLASS_KEY = "setup_class";
}
