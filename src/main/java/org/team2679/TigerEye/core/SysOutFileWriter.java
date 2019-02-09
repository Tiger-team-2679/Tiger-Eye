package org.team2679.TigerEye.core;

import org.team2679.TigerEye.lib.stream.ColorlessOutputStream;
import org.team2679.TigerEye.lib.stream.PrintSplitStream;
import org.team2679.TigerEye.lib.stream.SplitStream;

import java.io.*;

/**
 * redirects all of the system.out calls to a file and the standard streams
 *
 * @author  SlowL0ris
 */
class SysOutFileWriter {

    private static final String LOG_LOCATION = "log";
    private static final String OUT_FILE_NAME = "out.txt";
    private static final String ERROR_FILE_NAME = "err.txt";

    private static boolean initiated = false;
    private static File logDir;
    private static File outFile;
    private static File errFile;
    private static FileOutputStream outFileStream;
    private static FileOutputStream errFileStream;
    private static SplitStream bothFilesSplitStream;
    private static SplitStream outSplitStream;
    private static SplitStream errSplitStream;

    /**
     * Initiate the splitter
     */
    public static void init() throws IOException {
        if (!initiated) {
            initiated = true;
            // get the home directory and create log directory
            logDir = new File(Bootstrap.getTigerHome(), LOG_LOCATION);
            logDir.mkdirs();
            // create the new log file
            outFile = new File(logDir, OUT_FILE_NAME);
            errFile = new File(logDir, ERROR_FILE_NAME);
            outFileStream = new FileOutputStream(outFile);
            errFileStream = new FileOutputStream(errFile);

            bothFilesSplitStream = new SplitStream(new ColorlessOutputStream(errFileStream), new ColorlessOutputStream(outFileStream));

            outSplitStream = new SplitStream(System.out, new ColorlessOutputStream(outFileStream));
            errSplitStream = new SplitStream(System.err, bothFilesSplitStream);

            System.setOut(new PrintSplitStream(outSplitStream));
            System.setErr(new PrintStream(errSplitStream));
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
