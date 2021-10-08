package com.springboot.rabbitmq.controller;

import com.springboot.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable("message")String message){
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY,message,correlationData);
        log.info("发送确认消息：{}",message);

        CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY+"123",message,correlationData2);
        log.info("发送确认消息：{}",message);
    }
}
