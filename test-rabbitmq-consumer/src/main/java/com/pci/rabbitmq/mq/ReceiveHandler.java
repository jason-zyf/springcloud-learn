package com.pci.rabbitmq.mq;

import com.pci.rabbitmq.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveHandler {

    /*@RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void send_email(String msg,Message message,Channel channel){
        System.out.println("receive message is:"+msg);
    }

    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
    public void send_sms(String msg,Message message,Channel channel){
        System.out.println("receive message is:"+msg);
    }*/

    /**
     * 监听的队列可以是多个的
     * @param msg
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS,RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void send_sms(String msg,Message message,Channel channel){
        System.out.println("receive message is:"+msg);
    }


}
