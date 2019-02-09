package org.team2679.TigerEye.lib.log;

import java.io.IOException;
import java.io.PrintStream;

/**
 * a class used to convert the Splitstream {@link SplitStream} to a split
 * print stream, to be used for example as a replacement for the
 * System.out print stream
 *
 * @author  SlowL0ris
 */
public class PrintSplitStream extends PrintStream {

    SplitStream stream;

    public PrintSplitStream(SplitStream out) {
        super(out);
        this.stream = out;
    }

    /**
     * overrides the print function and moves the input the the splitter
     * to be handled
     * @param s
     */
    @Override
    public void print(String s) {
        try {
            this.stream.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
