package com.changlie;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class v11AsynchronousFileChannel {



    private AsynchronousFileChannel getReadAsynchronousFileChannel() throws IOException {
        Path path = Paths.get("/home/changlie/nio-fromFile.txt");

        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        return fileChannel;
    }

    @Test
    public void readDataByFuture() throws Exception {

        AsynchronousFileChannel fileChannel = getReadAsynchronousFileChannel();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        Future<Integer> future = fileChannel.read(buf, 0);

        while (!future.isDone()) Thread.sleep(100);

        buf.flip();

        byte[] bytes = new byte[buf.limit()];
        ByteBuffer byteBuffer = buf.get(bytes);
        System.out.println("file content: \n" + new String(bytes));

        buf.clear();
    }



    @Test
    public void readDataByCompletionHandler() throws Exception {
        AsynchronousFileChannel fileChannel = getReadAsynchronousFileChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int position = 0;

        fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result = " + result);

                attachment.flip();

                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));

                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });

        Thread.sleep(3000);
    }


    byte[] getSourceData() throws Exception {
        AsynchronousFileChannel fileChannel = getReadAsynchronousFileChannel();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        Future<Integer> future = fileChannel.read(buf, 0);

        while (!future.isDone()) Thread.sleep(100);

        buf.flip();

        byte[] bytes = new byte[buf.limit()];
        ByteBuffer byteBuffer = buf.get(bytes);
        System.out.println("file content: \n" + new String(bytes));

        return bytes;
    }




    private AsynchronousFileChannel getWriteAsynchronousFileChannel() throws IOException {
        Path path = Paths.get("/home/changlie/test-write.txt");

        if(!Files.exists(path)){
            Files.createFile(path);
        }
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        return fileChannel;
    }


    @Test
    public void writeByFuture() throws IOException {
        AsynchronousFileChannel fileChannel = getWriteAsynchronousFileChannel();


        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;

        buffer.put("test data".getBytes());
        buffer.put("test data".getBytes());
        buffer.put("test data".getBytes());
        buffer.put("test data".getBytes());

        buffer.flip();

        Future<Integer> operation = fileChannel.write(buffer, position);
        fileChannel.force(false);
//        buffer.clear();

        while(!operation.isDone());

        System.out.println("Write done");
    }


    @Test
    public void writeByCompletionHandler() throws Exception {
        AsynchronousFileChannel fileChannel = getWriteAsynchronousFileChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;

        byte[] sourceData = getSourceData();

        buffer.put(sourceData);
        buffer.put("\n\ntest data".getBytes());
        buffer.flip();

        fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("bytes written: " + result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Write failed");
                exc.printStackTrace();
            }
        });

        Thread.sleep(3000);
    }

}
