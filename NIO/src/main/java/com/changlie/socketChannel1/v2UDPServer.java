package com.changlie.socketChannel1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class v2UDPServer {

    public static void main(String[] args) throws Exception {
        int port = 8089;


        async(port);

//        syncReceive(port);
    }

    private static void async(int port) throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(port));
        channel.configureBlocking(false);

        Selector selector = Selector.open();

        int interestSet = SelectionKey.OP_READ;

        channel.register(selector, interestSet, ByteBuffer.allocate(1024));

        while (true){
            int select = selector.select(1000);

            System.out.println("selected: "+select);
            System.out.println("first now: "+new Date().toLocaleString());

            if(select<1) continue;

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                System.out.println("second now: "+new Date().toLocaleString());

                SelectionKey key = iterator.next();
                if(key.isReadable()){
                    System.out.println("read....");
                    handleReceive(key);
                }
                if(key.isAcceptable()){
                    System.out.println("acceptable...");
                }
                if(key.isConnectable()){
                    System.out.println("connectable...");
                }
                if(key.isValid()){
                    System.out.println("valid...");
                }

                iterator.remove();
            }
        }
    }



    private static void handleReceive(SelectionKey key) throws Exception {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.clear();
        channel.receive(buf);

        while (buf.hasRemaining()){
            buf.flip();
            byte[] bytes = new byte[buf.limit()];
            buf.get(bytes);

            System.out.println(new String(bytes));
            System.out.println("--------------------------");
        }
    }

    private static void syncReceive(int port) throws Exception {

        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(port));

        ByteBuffer buf = ByteBuffer.allocate(1024);
        while (true){
            channel.receive(buf);

            buf.flip();

            byte[] bytes = new byte[buf.limit()];
            buf.get(bytes);

            System.out.println("result: " + new String(bytes));
            buf.clear();
        }
    }
}
