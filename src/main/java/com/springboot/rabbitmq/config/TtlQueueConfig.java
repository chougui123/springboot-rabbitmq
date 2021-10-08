package com.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class TtlQueueConfig {

    public static final String X_EXCHAGNE="X";
    public static final String Y_EXCHANGE="Y";
    public static final String QUEUE_A="QA";
    public static final String QUEUE_B="QB";
    public static final String QUEUE_C="QC";
    public static final String QUEUE_D="QD";


    @Bean("queueC")
    public Queue queueC(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",Y_EXCHANGE);
        map.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(map).build();
    }



    @Bean("xExchange")
    public DirectExchange xExchange(){
      return new DirectExchange(X_EXCHAGNE);
    }

    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_EXCHANGE);
    }

    @Bean("queueA")
    public Queue queueA(){
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("x-dead-letter-exchange",Y_EXCHANGE);
        map.put("x-dead-letter-routing-key","YD");
        map.put("x-message-ttl",10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(map).build();
    }
    @Bean("queueB")
    public Queue queueB(){
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("x-dead-letter-exchange",Y_EXCHANGE);
        map.put("x-dead-letter-routing-key","YD");
        map.put("x-message-ttl",40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(map).build();
    }
    @Bean("queueD")
    public Queue queueD(){
        return new Queue(QUEUE_D);
    }

    @Bean
    public Binding queueABindX(@Qualifier("queueA") Queue queueA,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBBindX(@Qualifier("queueB") Queue queueB,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueCBindX(@Qualifier("queueC") Queue queueC,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }

    @Bean
    public Binding queueDBindY(@Qualifier("queueD") Queue queueD,@Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }




}
