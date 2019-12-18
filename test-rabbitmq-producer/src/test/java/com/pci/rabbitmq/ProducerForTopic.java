package com.pci.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerForTopic {

    private static final String EXCHANGE_TOPIC_INFORM = "exchange_topic_inform";
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EMAIL_ROUTINGKEY = "info.#.email.#";
    private static final String SMS_ROUTINGKEY = "info.#.sms.#";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");

            connection = factory.newConnection();
            channel = connection.createChannel();

            // String exchange, String type
            channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);

            // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);

            // String queue, String exchange, String routingKey
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_TOPIC_INFORM, EMAIL_ROUTINGKEY);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPIC_INFORM, SMS_ROUTINGKEY);

            // 发送消息到sms队列
            for (int i = 0; i < 5; i++) {
                String message = "inform to sms : " + i + "次消息";
                // String exchange, String routingKey, BasicProperties props, byte[] body
                channel.basicPublish(EXCHANGE_TOPIC_INFORM, "info.sms", null, message.getBytes());
                System.out.println("send inform to sms : " + i + "次消息");
            }
            // 发送消息到email队列 (根据routingkey)
            for (int i = 0; i < 5; i++) {
                String message = "inform to email : " + i + "次消息";
                // String exchange, String routingKey, BasicProperties props, byte[] body
                channel.basicPublish(EXCHANGE_TOPIC_INFORM, "info.email", null, message.getBytes());
                System.out.println("send inform to email : " + i + "次消息");
            }
            // 发送消息到email和sms队列 (根据routingkey)
            /*for (int i = 0; i < 5; i++) {
                String message = "inform to email : " + i + "次消息";
                // String exchange, String routingKey, BasicProperties props, byte[] body
                channel.basicPublish(EXCHANGE_TOPIC_INFORM, "info.sms.email", null, message.getBytes());
                System.out.println("send inform to email and sms : " + i + "次消息");
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

}
