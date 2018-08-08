package com.changlie.rabbitmq;

import com.rabbitmq.client.*;

import java.util.UUID;
import com.rabbitmq.client.AMQP.BasicProperties;

public class v6RPCClient {


    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;
    private QueueingConsumer consumer;

    public v6RPCClient() throws Exception {
        ConnectionFactory factory =  getFactory();


        connection = factory.newConnection();
        channel = connection.createChannel();

        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(replyQueueName, true, consumer);
    }

    public String call(String message) throws Exception {
        String response = null;
        String corrId = UUID.randomUUID().toString();

        BasicProperties props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response = new String(delivery.getBody(), "UTF-8");
                break;
            }
        }

        return response;
    }

    public void close() throws Exception {
        connection.close();
    }


    static ConnectionFactory getFactory(){
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
//		设置RabbitMQ地址
        factory.setHost("192.168.0.102");
        factory.setPort(5673);
        return factory;
    }

    public static void main(String[] argv) {
        v6RPCClient fibonacciRpc = null;
        String response = null;
        try {
            fibonacciRpc = new v6RPCClient();

            String num = "9";
            System.out.println("RPCClient [x] Requesting fib("+num+")");
            response = fibonacciRpc.call(num);
            System.out.println("RPCClient [.] Got '" + response + "'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fibonacciRpc != null) {
                try {
                    fibonacciRpc.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

}
