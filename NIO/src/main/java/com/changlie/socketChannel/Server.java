package com.changlie.socketChannel;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {

    private int tcpPort = 0;
    private IMessageReaderFactory messageReaderFactory = null;
    private IMessageProcessor     messageProcessor = null;

    public Server(int tcpPort, IMessageReaderFactory messageReaderFactory, IMessageProcessor messageProcessor) {
        this.tcpPort = tcpPort;
        this.messageReaderFactory = messageReaderFactory;
        this.messageProcessor = messageProcessor;
    }



    public void start() throws IOException {

        //请求队列
        ArrayBlockingQueue socketQueue = new ArrayBlockingQueue(1024); //move 1024 to ServerConfig

        // 启动用于接收请求的线程
        SocketAccepter  socketAccepter  = new SocketAccepter(tcpPort, socketQueue);
        new Thread(socketAccepter).start();

        // 启动用于处理请求的线程
        SocketProcessor socketProcessor = new SocketProcessor(socketQueue,  this.messageReaderFactory, this.messageProcessor);
        new Thread(socketProcessor).start();
    }
}
