package com.changlie.asyncSocketChannel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;

        AsyncTimeServerHandler timeHandler = new AsyncTimeServerHandler(port);

        new Thread(timeHandler, "AIO-time-server").start();
    }
}

class AsyncTimeServerHandler implements  Runnable{
    CountDownLatch latch;
    AsynchronousServerSocketChannel serverSocketChannel;

    public AsyncTimeServerHandler(int port) {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port: "+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            latch = new CountDownLatch(1);
            serverSocketChannel.accept(this, new AcceptCompletionHandler());
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}

class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {


    @Override
    public void completed(AsynchronousSocketChannel socketChannel, AsyncTimeServerHandler attachment) {
        // 为下一个请求做准备
        attachment.serverSocketChannel.accept(attachment, this);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buf) {
                try {
                    buf.flip();
                    byte[] bytes = new byte[buf.limit()];
                    buf.get(bytes);
                    String body = new String(bytes, "utf-8");
                    System.out.println("receive: "+body);

                    String responseResult = "now".equalsIgnoreCase(body) ? new Date().toLocaleString() : "bad order";

                    doResponse(socketChannel, responseResult);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
            }
        });
    }

    void doResponse(AsynchronousSocketChannel channel, String msg){
        byte[] bytes = msg.getBytes();
        ByteBuffer writeBuf = ByteBuffer.allocate(bytes.length);
        writeBuf.put(bytes);
        writeBuf.flip();
        channel.write(writeBuf, writeBuf, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buf) {
                if(buf.hasRemaining())
                   channel.write(writeBuf, writeBuf, this);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
