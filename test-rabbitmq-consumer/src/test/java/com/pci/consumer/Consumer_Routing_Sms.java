package com.pci.consumer;


import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer_Routing_Sms {

    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";

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

            // String exchange, String type
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.DIRECT);

            // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            // String queue, String exchange, String routingKey
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,QUEUE_INFORM_SMS);

            DefaultConsumer consumer = new DefaultConsumer(channel){
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    long deliveryTag = envelope.getDeliveryTag();
                    String exchange = envelope.getExchange();
                    String routingKey = envelope.getRoutingKey();
                    String str = new String(body,"utf-8");
                    System.out.println("deliveryTag: "+deliveryTag+",exchange: "+exchange+",routingKey: "+routingKey+",message: " + str);
                }
            };
            // String queue, boolean autoAck, Consumer callback
            channel.basicConsume(QUEUE_INFORM_SMS,true,consumer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
