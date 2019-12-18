package com.pci.consumer;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_Subscribe_Sms {

    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";
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

            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);

            // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            // String queue, String exchange, String routingKey
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,"");

            DefaultConsumer consumer = new DefaultConsumer(channel){

                @Autowired
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    long deliveryTag = envelope.getDeliveryTag();
                    String exchange = envelope.getExchange();
                    String routingKey = envelope.getRoutingKey();
                    String str = new String(body,"utf-8");
                    System.out.println("exchange : " +exchange+",routingKey: " + routingKey+",deliveryTag: " + deliveryTag +", receive message: "+ str);
                }
            };

            //String queue, boolean autoAck, Consumer callback
            channel.basicConsume(QUEUE_INFORM_SMS,true,consumer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

}
