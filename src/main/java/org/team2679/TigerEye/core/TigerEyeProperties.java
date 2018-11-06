package org.team2679.TigerEye.core;

import org.team2679.TigerEye.lib.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TigerEyeProperties {

    private final static String FILE_NAME = "/tiger.properties";
    private static Properties prop;

    static{
        Logger logger = new Logger("properties-loader");
        prop = new Properties();
        InputStream in = TigerEyeProperties.class.getResourceAsStream(FILE_NAME);
        try {
            prop.load(in);
            in.close();
        } catch (IOException e) {
            logger.error("properties loader -> couldn't load properties file");
        }
    }

    public static String getProperty(String key){
        return prop.getProperty(key);
    }

    public static final String MAIN_CLASS = getProperty("setup_class");
}
