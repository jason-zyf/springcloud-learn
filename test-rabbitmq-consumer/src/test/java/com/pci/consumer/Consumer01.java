package com.pci.consumer;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer01 {

    private static final String QUEUE = "helloworld";  // 声明队列名称

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);

        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE,true,false,false,null);
        // 定义消费队列
        DefaultConsumer consumer = new DefaultConsumer(channel){
            /**
             * 消费者接受消息调用此方法
             * @param consumerTag  消费者的标签，在channel.basicConsume()去指定
             * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志(收到消息失败后是否需要重新发送)
             * @param properties
             * @param body   消费的方法
             * @throws IOException
             */
            @Autowired
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //交换机
                String exchange = envelope.getExchange();
                //路由key
                String routingKey = envelope.getRoutingKey();
                //消息id
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String msg = new String(body,"utf-8");
                System.out.println("receive message.. : " + msg);
            }
        };
        /**
         * 监听消费  String queue, boolean autoAck, Consumer callback
         * queue ： 队列名称
         * autoAck ： 是否自动回复，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动回复
         * callback ： 消费消息的方法，消费者接收到消息后调用此方法
         */
        channel.basicConsume(QUEUE,true,consumer);
    }
}
