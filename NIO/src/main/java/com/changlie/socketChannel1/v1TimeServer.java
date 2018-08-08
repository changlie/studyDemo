package com.changlie.socketChannel1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class v1TimeServer {

    public static void main(String[] args) {
        int port = 8000;

        new MultiplexerTimeServer(port).start();
    }

    static class MultiplexerTimeServer extends Thread{
        Selector selector ;
        ServerSocketChannel server;
        volatile boolean stop;



        public MultiplexerTimeServer(int port) {
            try{
                selector = Selector.open();
                server = ServerSocketChannel.open();
                server.configureBlocking(false);
                server.bind(new InetSocketAddress(port));
                server.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("The time server is start in port: "+ port);

            }catch (Exception e){
                System.err.println("初始化失败！");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (!stop){
                try{
                    selector.select(100);

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();

                        handleRequest(key);

                        iterator.remove();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        private void handleRequest(SelectionKey key) throws IOException {
            if(!key.isValid()) return;

            if(key.isAcceptable()){
                // 处理　新接入的请求消息
                ServerSocketChannel scc = (ServerSocketChannel) key.channel();
                SocketChannel sc = scc.accept();

                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ, System.currentTimeMillis());
            }else if(key.isReadable()){
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuf = ByteBuffer.allocate(1024*1024);

                int readQty = sc.read(readBuf);
                if(readQty>0){
                    String body = getBody(readBuf);

                    System.out.println("The time server receive order: "+ body);
                    String responseMsg = "now".equalsIgnoreCase(body) ? new Date().toLocaleString() : " bad order";
                    returnMsg(sc, responseMsg);
                }else{
                    sc.close();
                }
            }else if(key.isWritable()){
                System.out.println(key.attachment()+" writable!!!!! ");
            }

        }

        private String getBody(ByteBuffer readBuf) throws UnsupportedEncodingException {
            readBuf.flip();
            byte[] bytes = new byte[readBuf.limit()];
            readBuf.get(bytes);
            String body = new String(bytes, "utf-8");
            return body;
        }

        private void returnMsg(SocketChannel sc, String responseMsg) throws IOException {
            byte[] bytes = responseMsg.getBytes();
            ByteBuffer writeBuf = ByteBuffer.allocate(bytes.length);
            writeBuf.put(bytes);
            writeBuf.flip();
            sc.write(writeBuf);
        }

        public void exit(){
            this.stop = true;
        }
    }
}
