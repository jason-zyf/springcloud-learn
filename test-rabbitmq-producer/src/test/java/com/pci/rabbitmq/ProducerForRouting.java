package com.pci.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerForRouting {

    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";

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
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.DIRECT);

            // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            // String queue, String exchange, String routingKey
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_FANOUT_INFORM,QUEUE_INFORM_EMAIL);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,QUEUE_INFORM_SMS);

            // 发送消息到sms队列
            for (int i = 0;i < 10;i++ ){
                String message = "inform to sms : " + i + "次消息";
                // String exchange, String routingKey, BasicProperties props, byte[] body
                channel.basicPublish(EXCHANGE_FANOUT_INFORM,QUEUE_INFORM_SMS,null,message.getBytes());
                System.out.println("send inform to sms : " + i + "次消息");
            }
            // 发送消息到email队列 (根据routingkey)
            for (int i = 0;i < 10;i++ ){
                String message = "inform to email : " + i + "次消息";
                // String exchange, String routingKey, BasicProperties props, byte[] body
                channel.basicPublish(EXCHANGE_FANOUT_INFORM,QUEUE_INFORM_EMAIL,null,message.getBytes());
                System.out.println("send inform to email : " + i + "次消息");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(channel != null){
                channel.close();
            }
            if(connection != null){
                connection.close();
            }
        }
    }

}
