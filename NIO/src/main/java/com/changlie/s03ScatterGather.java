package com.changlie;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

public class s03ScatterGather {
    public static void main(String[] args) throws Exception {
        scatterDemo();
//        gatherDemo();
    }

    /**
     * 从 多个Buffer  写数据到  一个Channel
     */
    private static void gatherDemo() throws Exception {
        String filepath = "/home/changlie/nio-scatter-gather.txt";
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);

        // init buffer
//        String charset = "utf-8";
        header.put("http /1.1  200 OK\n".getBytes());
        header.put("http://www.chenlh.cn/scatter.html\n\n".getBytes());
        body.put("name=tome&age=199&addr=ShenZhenChina\n".getBytes());
        body.put("ServerSocketChannel end.".getBytes());

        header.flip();
        body.flip();
        ByteBuffer[] seq = {header, body};

        RandomAccessFile aFile     = new RandomAccessFile(filepath, "rw");
        FileChannel channel = aFile.getChannel();
        channel.write(seq);
        channel.close();
    }

    /**
     * 从 一个Channel 中 读数据到 多个Buffer
     * @throws Exception
     */
    private static void scatterDemo() throws Exception {
        String filepath = "/home/changlie/nio-scatter-gather.txt";
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);


        ByteBuffer[] seq = {header, body};

        RandomAccessFile aFile  = new RandomAccessFile(filepath, "rw");
        FileChannel channel = aFile.getChannel();
        channel.read(seq);
        System.out.println("header: ---------------------------");
        header.flip();
        byte[] headBytes = new byte[header.limit()];
        header.get(headBytes);
        System.out.println(new String(headBytes));

        System.out.println("body: ---------------------------");
        body.flip();
        byte[] bodyBytes = new byte[body.limit()];
        body.get(bodyBytes);
        System.out.println(new String(bodyBytes));

        channel.close();
    }
}
