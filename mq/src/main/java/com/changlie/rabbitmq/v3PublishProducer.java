package com.changlie.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/***
 * 发布/订阅
 */
public class v3PublishProducer {

    private static final String EXCHANGE_NAME = "logs";

    static ConnectionFactory getFactory(){
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
//		设置RabbitMQ地址
        factory.setHost("192.168.0.102");
        factory.setPort(5673);
        return factory;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = getFactory();


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

//      分发消息
        for(int i = 0 ; i < 5; i++){
            String message = "sql curd! " + i*i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}
