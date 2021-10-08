package com.springboot.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class DelayedQueueConfig {

    public static final String DELAYED_QUEUE_NAME="delayed.queue";
    public static final String DELAYED_EXCHANGE_NAME="delayed.exchange";
    public static final String DELAYED_ROUTING_KEY="delayed.routingkey";

    @Bean
    public CustomExchange delayedExchange(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-delayed-type","direct");
        return new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message",true,false,map);
    }

    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE_NAME);
    }

    @Bean
    public Binding bindDelayedQueueToExchange(@Qualifier("delayedQueue") Queue delayedQueue,@Qualifier("delayedExchange") CustomExchange delayedExchange){

        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
