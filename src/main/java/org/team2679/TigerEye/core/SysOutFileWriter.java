package org.team2679.TigerEye.core;

import org.team2679.TigerEye.lib.log.ColorlessOutputStream;
import org.team2679.TigerEye.lib.log.PrintSplitStream;
import org.team2679.TigerEye.lib.log.SplitStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * redirects all of the system.out calls to a file and the standard streams
 */
public class SysOutFileWriter {

    private static boolean initiated = false;
    private static File logDir;
    private static File outFile;
    private static FileOutputStream outFileStream;
    private static SplitStream splitStream;

    /**
     * Initiate the splitter
     */
    public static void init()
    {
        try {
            if (!initiated) {
                initiated = true;
                // get the home directory and create log directory
                logDir = new File(TigerEye.homeDirectory, "log");
                logDir.mkdirs();
                // create the new log file
                outFile = new File(logDir, "output.txt");
                // delete old log file
                outFile.delete();
                if (outFile.exists()) {
                    outFile.delete();
                }
                outFile.createNewFile();
                outFileStream = new FileOutputStream(outFile);
                splitStream = new SplitStream(System.out, new ColorlessOutputStream(outFileStream));

                System.setOut(new PrintSplitStream(splitStream));
            }
        }
        catch (Exception e){
            System.err.println("System.Out File Writer failed...");
            e.printStackTrace();
        }
    }

    /**
     * get the file reader of the output file
     * @return the file reader
     * @throws FileNotFoundException
     */
    public FileReader getFileReader() throws FileNotFoundException {
        return new FileReader(outFile);
    }
}
