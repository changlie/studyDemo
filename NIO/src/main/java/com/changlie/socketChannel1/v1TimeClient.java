package com.changlie.socketChannel1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class v1TimeClient {
    public static void main(String[] args) {
        int port = 8000;
        String host = "127.0.0.1";

        String[] orders = {"now", "year", "day", "now"};

        for (String order : orders) {
            new TimeClient(host, port, order).start();
        }
    }

    static class TimeClient extends Thread{
        Selector selector;
        SocketChannel client;
        volatile  boolean stop;
        String order;

        public TimeClient(String host, int port, String order) {
            this.order = order;

            try {
                selector = Selector.open();
                client = SocketChannel.open();
                client.configureBlocking(false);

                boolean isConnect = client.connect(new InetSocketAddress(host, port));
                if(isConnect){
                    client.register(selector, SelectionKey.OP_READ);
                    sendOrder();
                }else{
                    client.register(selector, SelectionKey.OP_CONNECT);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sendOrder() throws IOException {
//            byte[] bytes = "now".getBytes();
            byte[] bytes = order.getBytes();

            ByteBuffer writeBuf = ByteBuffer.allocate(bytes.length);
            writeBuf.put(bytes);
            writeBuf.flip();
            client.write(writeBuf);
            if(!writeBuf.hasRemaining()){
                System.out.println("order: "+order+" send successfully!");
            }
        }

        @Override
        public void run() {
            while (!stop){
                try {
                    selector.select(10);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();

                        handleReceive(key);

                        iterator.remove();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleReceive(SelectionKey key) throws IOException {
            if(!key.isValid()) return;

            SocketChannel sc = (SocketChannel) key.channel();
            if(key.isConnectable() && sc.finishConnect()){
                sc.register(selector, SelectionKey.OP_READ);
                sendOrder();
            }else if(key.isReadable()){
                ByteBuffer readBuf = ByteBuffer.allocate(1024);
                int readQty = sc.read(readBuf);
                if(readQty>0){
                    readBuf.flip();
                    byte[] bytes = new byte[readBuf.limit()];
                    readBuf.get(bytes);
                    String body = new String(bytes, "utf-8");
                    System.out.println("now is "+body);
                    this.stop = true;
                }else{
                    key.cancel();
                    sc.close();
                }
            }
        }
    }
}
