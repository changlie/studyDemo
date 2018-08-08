package com.changlie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 两个 channel 之间 传递 数据
 */
public class s04ChannelToChannel {
    public static void main(String[] args) throws Exception {
        String fileRoot = "/home/changlie";

        RandomAccessFile fromFile = new RandomAccessFile(
                Paths.get(fileRoot, "nio-fromFile.txt").toString()
                , "rw");
        RandomAccessFile toFile = new RandomAccessFile(
                Paths.get(fileRoot, "nio-toFile.txt").toString()
                , "rw");

//        transferToTest(fromFile, toFile);
        transferFromTest(fromFile, toFile);

        fromFile.close();
        toFile.close();

    }

    private static void transferFromTest(RandomAccessFile fromFile, RandomAccessFile toFile) throws IOException {
        FileChannel fromChannel = fromFile.getChannel();
        FileChannel toChannel = toFile.getChannel();

        toChannel.transferFrom(fromChannel, 0 , fromChannel.size());
    }

    private static void transferToTest(RandomAccessFile fromFile, RandomAccessFile toFile) throws IOException {

        FileChannel fromChannel = fromFile.getChannel();
        FileChannel toChannel = toFile.getChannel();

        fromChannel.transferTo(0, fromChannel.size(), toChannel);
    }
}
