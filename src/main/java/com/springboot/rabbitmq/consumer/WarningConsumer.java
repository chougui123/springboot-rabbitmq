package com.springboot.rabbitmq.consumer;

import com.springboot.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarningConsumer {

    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void warnMessage(Message message){
        String msg = new String(message.getBody());
        log.info("收到告警消息：{}",msg);
    }
}
