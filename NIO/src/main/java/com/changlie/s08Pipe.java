package com.changlie;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class s08Pipe {

    public static void main(String[] args) throws Exception {
        //初始化Pipe实例
        Pipe pipe = Pipe.open();

        // 获取写通道
        Pipe.SinkChannel skChannel = pipe.sink();

        String testData = "Test Data to Check java NIO Channels Pipe.";

        ByteBuffer buffer = ByteBuffer.allocate(512);
        buffer.clear();
        buffer.put(testData.getBytes());

        buffer.flip();
        //向写通道写入数据
        while(buffer.hasRemaining()) {
            skChannel.write(buffer);
        }
        //获得读取数据通道
        Pipe.SourceChannel sourceChannel = pipe.source();
        sourceChannel.configureBlocking(false);
        buffer = ByteBuffer.allocate(512);

        //将读取数据写到控制台
        while(sourceChannel.read(buffer) > 0){
            System.out.println("loop...");

            //flip方法将当前读取位置设置为0， limit设置为写入数据的size
            buffer.flip();

            while(buffer.hasRemaining()){
                char ch = (char) buffer.get();
                System.out.print(ch);
            }

            //clear方法将buffer的limit设置为其容量capacity， 将position设置为0
            buffer.clear();
        }

        System.out.println("it's over!");
    }

}
