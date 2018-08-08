package com.changlie.socketChannel1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(8989));

        while (true){

            server.configureBlocking(false);
            SocketChannel socketChannel = server.accept();

            socketChannel.configureBlocking(false);
        }




    }


    static class ChannelHandler extends Thread{
        Selector selector = null;
        {
            try {
                selector = selector = Selector.open();
            }catch (Exception e){
                System.err.println("selector init fail!");
                e.printStackTrace();
            }
        }


    }
}



