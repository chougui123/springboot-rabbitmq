package com.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {

    public static final String CONFIRM_EXCHANGE_NAME="confirm_exchange";
    public static final String CONFIRM_QUEUE_NAME="confirm_queue";
    public static final String CONFIRM_ROUTING_KEY="key1";
    public static final String BACKUP_EXCHANGE_NAME="backup_exchange";
    public static final String BACKUP_QUEUE_NAME="backup_queue";
    public static final String WARNING_QUEUE_NAME="warning_queue";

    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME).build();
    }

    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Binding bindingExchangeQueue(@Qualifier("confirmExchange")DirectExchange exchange,@Qualifier("confirmQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean("backupExchange")
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    @Bean("backupQueue")
    public Queue backupQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    @Bean("warningQueue")
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    @Bean
    public Binding bindingBackupExchangeQueue(@Qualifier("backupExchange")FanoutExchange exchange,@Qualifier("backupQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindingWarningExchangeQueue(@Qualifier("backupExchange")FanoutExchange exchange,@Qualifier("warningQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange);
    }
}
