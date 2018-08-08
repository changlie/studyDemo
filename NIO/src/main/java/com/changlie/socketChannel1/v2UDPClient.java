package com.changlie.socketChannel1;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class v2UDPClient     {
    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8089;

        DatagramChannel channel = DatagramChannel.open();
        InetSocketAddress address = new InetSocketAddress(host, port);
        channel.connect(address);

        for (int i = 0; i < 10; i++) {
            String newData = "New String to write to file..."
                    + new Date().toLocaleString();

            sendData(channel, newData);

            TimeUnit.SECONDS.sleep(1);
        }

    }

    private static void sendData(DatagramChannel channel, String newData) throws Exception {
        ByteBuffer buf = ByteBuffer.allocate(128);
        byte[] bytes = newData.getBytes();
        buf.clear();
        buf.put(bytes);
        buf.flip();

        int bytesSent = channel.write(buf);
        if(bytes.length==bytesSent){
            System.out.println("数据发送成功！");
        }
    }
}
