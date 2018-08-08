package com.changlie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class s06FileChannel {
    public static void main(String[] args) throws Exception {

//        positionDemo();

        truncateDemo();

//        RandomAccessFile f = new RandomAccessFile("/home/changlie/nio-data.txt", "rw");
//        FileChannel channel1 = f.getChannel();

    }

    private static void  positionDemo() throws Exception {
        // 获取 filechannel
        FileInputStream fis = new FileInputStream("/home/changlie/nio-data.txt");
        FileChannel inChannel = fis.getChannel();

        ByteBuffer  byteBuffer = ByteBuffer.allocate(16);
        for (int i = 0; i < 3; i++) {
            int read = inChannel.read(byteBuffer);
            System.out.println("read qty: "+read);
            byte[] bytes = new byte[read];
            int j = 0;
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()){
                bytes[j] = byteBuffer.get();
                j++;
            }
            System.out.println("read resut: "+new String(bytes));
            byteBuffer.clear();

            // todo 获取  游标  位置。
            long position = inChannel.position();
            System.out.println("position: "+position);
            System.out.println("--------------------------------------line");
        }

        fis.close();
    }

    private static void truncateDemo() throws Exception {
        // 获取 filechannel
        FileInputStream fis = new FileInputStream("/home/changlie/nio-data.txt");
        FileChannel inChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("/home/changlie/nio-data1.txt");
        FileChannel outChannel = fos.getChannel();


        //todo 截短功能,只能用于
        inChannel.transferTo(0 , inChannel.size(), outChannel);
        outChannel.truncate(64);

        outChannel.force(true);

        fis.close();
        fos.close();
    }


}
