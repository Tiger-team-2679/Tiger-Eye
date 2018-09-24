package org.team2679.TigerEye.lib.log;

import org.team2679.TigerEye.core.SysOutFileWriter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A stream splitter used by the {@link SysOutFileWriter} in order to split the output streams
 */
public class SplitStream extends OutputStream {

    OutputStream[] outs;

    /**
     * create a stream splitter for the given streams
     * @param outs the streams
     */
    public SplitStream(OutputStream... outs){
        this.outs = outs;
    }

    /**
     * Flush all of the streams
     */
    @Override
    public void flush() throws IOException {
        for (OutputStream stream : outs) {
            stream.flush();
        }
    }

    /**
     * Close all of the streams
     */
    @Override
    public void close() throws IOException {
        for (OutputStream stream : outs) {
            stream.close();
        }
    }

    /**
     * write a single byte given in this method to all
     * output streams created for this splitter
     * @param i byte to write
     * @throws IOException
     */
    @Override
    public void write(int i) throws IOException {
        for (OutputStream stream : outs) {
            stream.write(i);
        }
    }


    /**
     * write a byte array given in this method to all
     * output streams created for this splitter
     * @param b byte array to write
     * @throws IOException
     */
    @Override
    public void write(byte b[]) throws IOException {
        for (OutputStream stream : outs)
            stream.write(b);
    }

    /**
     *  Write a byte array with the offset and length given in this method to all
     *  output streams created for this SplitStream
     * @param b byte array
     * @param off offset
     * @param len length
     * @throws IOException
     */
    @Override
    public void write(byte b[], int off, int len) throws IOException {
        for (OutputStream stream : outs) {
            stream.write(b, off, len);
        }
    }
}
