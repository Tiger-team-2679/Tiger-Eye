package org.team2679.TigerEye.core.loader;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.util.List;

public class ClasspathScannerBridge {

    /**
     * searching a given path for all subclasses of a given class
     * @param path to scan
     * @param parent to be the model
     * @return list of all subclasses found
     */
    public static List<Class<?>> scan_for_subclass(String path, Class parent){
        if(path.isEmpty()){
            path = "*";
        }
        try (ScanResult scanResult =
                     new ClassGraph()
                             .enableAllInfo()
                             .whitelistPackages(path)
                             .scan()) {
            ClassInfoList controlClasses = scanResult.getSubclasses(parent.getName());
            return controlClasses.loadClasses();
        }
    }
}
