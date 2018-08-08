package com.changlie.asyncSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TimeClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8080;
        new Thread(new AsyncTimeClientHandler(host, port), "AIO-time-client").start();
        System.out.println("place1 current thread name: "+Thread.currentThread().getName());
    }

}

class AsyncTimeClientHandler implements Runnable{

    AsynchronousSocketChannel client;

    public AsyncTimeClientHandler(String host, int port) {
        try {
            client = AsynchronousSocketChannel.open();
            client.connect(new InetSocketAddress(host, port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("place22 current thread name: "+Thread.currentThread().getName());
        byte[] bytes = "now".getBytes();
        ByteBuffer buf = ByteBuffer.allocate(bytes.length);
        buf.put(bytes);
        buf.flip();
        client.write(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buf1) {
                System.out.println("place3301 current thread name: "+Thread.currentThread().getName());
                if(buf.hasRemaining()) client.write(buf1, buf1, this);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("place3302 current thread name: "+Thread.currentThread().getName());
                exc.printStackTrace();
            }
        });

        ByteBuffer readBuf = ByteBuffer.allocate(1024);
        Future<Integer> future = client.read(readBuf);

        try {
            Integer integer = future.get();
            System.out.println("result byte len: "+integer);
            readBuf.flip();
            byte[] resultBytes = new byte[integer];
            readBuf.get(resultBytes);
            System.out.println("current time: "+ new String(resultBytes));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}