package com.pci.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String EMAIL_ROUTINGKEY = "info.#.email.#";
    public static final String SMS_ROUTINGKEY = "info.#.sms.#";
    public static final String EXCHANGE_TOPIC_INFORM = "exchange_topic_inform";

    // 声明交换机
    @Bean(EXCHANGE_TOPIC_INFORM)
    public Exchange EXCHANGE_TOPIC_INFORM(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPIC_INFORM).durable(true).build();
    }

    // 声明队列
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL(){
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    // 声明队列
    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS(){
        return new Queue(QUEUE_INFORM_SMS);
    }

    // 绑定交换机和队列
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL)Queue queue,
                                              @Qualifier(EXCHANGE_TOPIC_INFORM)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(EMAIL_ROUTINGKEY).noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_INFORM_SMS)Queue queue,
                                            @Qualifier(EXCHANGE_TOPIC_INFORM)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(SMS_ROUTINGKEY).noargs();
    }


}
