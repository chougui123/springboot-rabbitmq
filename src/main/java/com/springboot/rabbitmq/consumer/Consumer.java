package com.springboot.rabbitmq.consumer;

import com.springboot.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiverMsg(Message message){
        String msg = new String(message.getBody());
        log.info("接收到确认消息：{}",msg);
    }
}
