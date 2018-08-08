package com.changlie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class s01ChannelDemo {
    public static void main(String[] args) throws IOException {

        RandomAccessFile aFile = new RandomAccessFile("/home/changlie/nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        // First you read into a Buffer
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("\n---------Read " + bytesRead);
            // Then you flip it, make buffer ready for read
            buf.flip();

            // Then you read out of it
            while(buf.hasRemaining()){
                // read 1 byte at a time
                System.out.print((char) buf.get());
            }

            //make buffer ready for writing
            buf.clear();
            // read into a Buffer again
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
