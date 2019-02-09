package org.team2679.TigerEye.lib.stream;

import org.team2679.TigerEye.lib.util.ConsoleColors;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * A output streamer used to remove ANSI colors {@link ConsoleColors}
 * from all given inputs
 *
 * @author  SlowL0ris
 */
public class ColorlessOutputStream extends PrintStream {
    OutputStream stream;

    public ColorlessOutputStream(OutputStream out) {
        super(out);
        this.stream = out;
    }

    /**
     * overrides the regular write method and removes all colors from the
     * input
     * @param bytes
     * @throws IOException
     */
    @Override
    public void write(byte[] bytes) throws IOException {
        this.stream.write(ConsoleColors.reset(new String(bytes)).getBytes());
    }
}
