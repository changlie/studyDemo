package com.changlie;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class s05Selector {

    // 1 2 4 8 16 32 64
    public static void main(String[] args) throws Exception {
        System.out.println(3 & 7);
        System.out.println(2 & 4);
        System.out.println(1 & 5);
        System.out.println("& operate ------ end.");

//        selectorTest();

        int a = 1;
        int b = 2;
        int c = 4;
        int d = 8;
        int e = 16;
        System.out.println(a | b);
        System.out.println(b | c);
        System.out.println(a | b | c);
        System.out.println(a | b | c | d);
        System.out.println(a | b | c | d | e);

        int accept = SelectionKey.OP_ACCEPT;


        System.out.println("------------add: ");
        System.out.println(1 << 0);
        System.out.println(1 << 2);
        System.out.println(1 << 3);
        System.out.println(1 << 4);
        System.out.println("-------------minus:");
        System.out.println(16 >> 3);
        System.out.println(16 >> 2);
        System.out.println(16 >> 1);


    }

    private static void selectorTest() throws Exception {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("http://jenkov.com", 80));


        Selector selector = Selector.open();

        channel.configureBlocking(false);

        // 监听 一个 或 多个事件
        int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

        SelectionKey selectionKey = channel.register(selector, interestSet);


        selectionKey.attach("abc");

        while (true) {

            int readyChannels = selector.select();

            if (readyChannels == 0) continue;


            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            // todo handle mothed 1
            for (SelectionKey skey : selectedKeys) {

                if (skey.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.

                } else if (skey.isConnectable()) {
                    // a connection was established with a remote server.

                } else if (skey.isReadable()) {
                    // a channel is ready for reading

                } else if (skey.isWritable()) {
                    // a channel is ready for writing
                }

                selectedKeys.remove(skey);
            }

            // todo handle method 2
//            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
//            while(keyIterator.hasNext()) {
//                SelectionKey key = keyIterator.next();
//
//                if(key.isAcceptable()) {
//                    // a connection was accepted by a ServerSocketChannel.
//
//                } else if (key.isConnectable()) {
//                    // a connection was established with a remote server.
//
//                } else if (key.isReadable()) {
//                    // a channel is ready for reading
//
//                } else if (key.isWritable()) {
//                    // a channel is ready for writing
//                }
//
//                keyIterator.remove();
//            }
        }
    }


}
