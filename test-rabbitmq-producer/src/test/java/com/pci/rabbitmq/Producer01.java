package com.pci.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeoutException;

public class Producer01 {

    private static final String QUEUE = "helloworld";  // 声明队列名称

    public static void main(String[] args) throws TimeoutException, IOException {

        Connection connection = null;
        Channel channel = null;
        try {
            // 创建一个连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置工厂属性
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");   // rabbitmq默认的虚拟机名为"/",虚拟机相当于一个独立的mq服务器

            // 创建一个连接
            connection = factory.newConnection();
            // 创建一个通道
            channel = connection.createChannel();
            /**
             * 声明队列  String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
             * 声明队列，如果Rabbit中没有此队列将自动创建
             * queue 队列名称
             * durable 是否持久化
             * exclusive 队列是否独占此连接
             * autoDelete 队列不再使用时是否自动删除此队列
             * arguments 队列参数
             */
            channel.queueDeclare(QUEUE,true,false,false,null);
            // 设置要发送的消息
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = dateformat.format(System.currentTimeMillis());
            String message = "helloword PCI ，发送时间：" + dateStr;
            /**
             * 发布消息  String exchange, String routingKey, BasicProperties props, byte[] body
             * exchange：Exchange的名称，如果没有指定，则使用Default Exchange
             * routingKey:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * props:消息包含的属性
             * body：消息体
             * 这里没有指定交换机，使用默认的交换机，默认的交换机，routingKey等于队列名称
             */
            channel.basicPublish("",QUEUE,null,message.getBytes());

            System.out.println("send message is: " + message);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭连接 ,先关闭队列，再关闭连接
            if(channel != null){
                channel.close();
            }
            if(connection != null){
                connection.close();
            }
        }

    }


}
