package com.changlie.rabbitmq;

import com.rabbitmq.client.*;


public class v5TopicProducer {

    private static final String EXCHANGE_NAME = "topic_logs";

    static ConnectionFactory getFactory(){
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
//		设置RabbitMQ地址
        factory.setHost("192.168.0.102");
        factory.setPort(5673);
        return factory;
    }

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = getFactory();

            connection = factory.newConnection();
            channel = connection.createChannel();
//			声明一个匹配模式的交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            // 待发送的消息
            String[] routingKeys = new String[]{"quick.orange.rabbit",
                    "lazy.orange.elephant",
                    "quick.orange.fox",
                    "lazy.brown.fox",
                    "quick.brown.fox",
                    "quick.orange.male.rabbit",
                    "lazy.orange.male.rabbit"};
//			发送消息
            for (String severity : routingKeys) {
                String message = "From " + severity + " routingKey' s message!....xxxx";
                channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
                System.out.println("TopicSend [x] Sent '" + severity + "':'" + message + "'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }


}
