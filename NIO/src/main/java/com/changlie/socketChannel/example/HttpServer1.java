package com.changlie.socketChannel.example;


import com.changlie.socketChannel.IMessageProcessor;
import com.changlie.socketChannel.Message;
import com.changlie.socketChannel.Server;
import com.changlie.socketChannel.http.HttpMessageReaderFactory;

/**
 * Created by jjenkov on 19-10-2015.
 */
public class HttpServer1 {

    public static void main(String[] args) throws Exception {
        IMessageProcessor messageProcessor = getMessageProcessor();

        Server server = new Server(9999, new HttpMessageReaderFactory(), messageProcessor);

        server.start();
    }

    private static IMessageProcessor getMessageProcessor() throws Exception {

        String indexHtml = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Hello World!</body></html>";

        byte[] indexHtmlBytes = indexHtml.getBytes("UTF-8");

        IMessageProcessor messageProcessor =  (request, writeProxy) -> {
            System.out.println("Message Received from socket: " + request.socketId);

            Message response = writeProxy.getMessage();
            response.socketId = request.socketId;
            response.writeToMessage(indexHtmlBytes);

            writeProxy.enqueue(response);
        };

        return messageProcessor;
    }


}
