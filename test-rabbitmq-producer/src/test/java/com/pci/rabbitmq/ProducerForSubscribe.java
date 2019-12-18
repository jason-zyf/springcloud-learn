package com.pci.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerForSubscribe {

    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = null;
        Channel channel = null;
        try {
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");
            // 创建连接
            connection = factory.newConnection();
            // 创建通道
            channel = connection.createChannel();
            /**
             * 声明交换机   String exchange, String type
             * exchange : 交换机名字
             * type ： 交换机类型，fanout、topic、direct、headers
             */
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            /**
             * 声明队列
             * String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
             */
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);
            /**
             * 交换机和队列绑定
             * String queue, String exchange, String routingKey
             * queue  队列名称
             * exchange  交换机名称
             * routingKey  路由key
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_FANOUT_INFORM,"");
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,"");
            // 发送消息
            for(int i = 0;i < 10; i++){
                String message = "inform to user : " + i + "次消息";
                /**
                 * 向交换机发送消息 String exchange, String routingKey, BasicProperties props, byte[] body
                 * exchange 交换机名称
                 * routingKey 路由key
                 */
                channel.basicPublish(EXCHANGE_FANOUT_INFORM,"",null,message.getBytes());
                System.out.println("send message : " + i + "次消息");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
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
