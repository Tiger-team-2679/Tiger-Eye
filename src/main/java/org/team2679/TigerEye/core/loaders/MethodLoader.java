package org.team2679.TigerEye.core.loaders;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.team2679.TigerEye.lib.log.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class MethodLoader {
    public static void loadByAnnotation(Class<?> cls, Object... params){
        Logger methodloaderlogger = new Logger("method-loader");
        methodloaderlogger.info("searching for a setup function");
        Reflections ref = new Reflections("org.team2679", new MethodAnnotationsScanner());
        // located all methods annotated with Setup
        Set<Method> setupMethods = ref.getMethodsAnnotatedWith(Initiater.class);
        try {
            if (setupMethods.size() > 1) {
                methodloaderlogger.fatal("More than one " + cls.getTypeName() + " method was found in org.team2679");
            }
            else if (setupMethods.size() == 0) {
                methodloaderlogger.fatal("No " + cls.getTypeName() + " method was found in org.team2679");
            }
            else {
                setupMethods.forEach (it -> {
                    try {
                        methodloaderlogger.fatal(cls.getTypeName() + " method found, starting setup...");
                        it.invoke(params);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) { }
    }
}
