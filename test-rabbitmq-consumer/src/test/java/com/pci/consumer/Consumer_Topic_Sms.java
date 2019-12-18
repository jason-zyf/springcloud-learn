package com.pci.consumer;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class Consumer_Topic_Sms {
    private static final String EXCHANGE_TOPIC_INFORM = "exchange_topic_inform";
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EMAIL_ROUTINGKEY = "info.#.email.#";
    private static final String SMS_ROUTINGKEY = "info.#.sms.#";

    public static void main(String[] args) {

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

            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);

            /**
             * 声明队列
             * String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
             */
//            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            //绑定email通知队列
//            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_TOPIC_INFORM,EMAIL_ROUTINGKEY);
            //绑定sms通知队列
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_TOPIC_INFORM,SMS_ROUTINGKEY);

            // 定义消费方法
            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Autowired
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    long deliveryTag = envelope.getDeliveryTag();
                    String exchange = envelope.getExchange();
                    String routingKey = envelope.getRoutingKey();
                    String str = new String(body,"utf-8");
                    System.out.println("deliveryTag: "+deliveryTag+",exchange: "+exchange+",routingKey: "+routingKey+",message: "+str);
                }
            };

            // String queue, boolean autoAck, Consumer callback
//            channel.basicConsume(QUEUE_INFORM_EMAIL,true,consumer);
            channel.basicConsume(QUEUE_INFORM_SMS,true,consumer);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
