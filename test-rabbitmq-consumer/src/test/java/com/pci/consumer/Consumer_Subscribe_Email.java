package com.pci.consumer;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_Subscribe_Email {

    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建连接
        Connection connection = null;
        Channel channel = null;
        try {
            /**
             * 1、创建连接工厂
             */
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");
            connection = factory.newConnection();

            /**
             * 2、创建队列
             */
            channel = connection.createChannel();

            /**
             * 3、声明交换机  String exchange, String type
             */
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);

            /**
             * 4、声明队列
             * String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
             */
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);

            /**
             * 5、绑定队列和交换机
             * String queue, String exchange, String routingKey
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_FANOUT_INFORM,"");

            /**
             * 6、定义消费方法
             */
            DefaultConsumer consumer = new DefaultConsumer(channel){

                @Autowired
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    // 消息id
                    long deliveryTag = envelope.getDeliveryTag();
                    //交换机
                    String exchange = envelope.getExchange();
                    // 路由key
                    String routingKey = envelope.getRoutingKey();
                    String str = new String(body,"utf-8");
                    System.out.println("exchange : " +exchange+",routingKey: " + routingKey+",deliveryTag: " + deliveryTag
                            +", receive message :" + str);
                }
            };
            /**
             * 7、监听队列，消费方法
             * String queue, boolean autoAck, Consumer callback
             */
            channel.basicConsume(QUEUE_INFORM_EMAIL,true,consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
