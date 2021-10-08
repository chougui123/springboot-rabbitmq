package com.springboot.rabbitmq.controller;

import com.springboot.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable("message") String message){
       log.info("当前时间:{}，发送消息给两个ttl队列:{}",new Date().toString(),message);
       rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s的队列："+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s的队列："+message);
    }

    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable("message") String message,@PathVariable("ttlTime") String ttlTime){
        log.info("当前时间：{}，发送过期时间为：{}ms的信息给队列QC:{}",new Date().toString(),ttlTime,message);
        rabbitTemplate.convertAndSend("X","XC",message,message1 -> {
            message1.getMessageProperties().setExpiration(ttlTime);
            return message1;
        });
    }

    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendDelayMsg(@PathVariable("message") String message,@PathVariable("delayTime") Integer delayTime){
        log.info("当前时间：{}，发送过期时间为：{}ms的信息给延迟队列:{}",new Date().toString(),delayTime,message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,DelayedQueueConfig.DELAYED_ROUTING_KEY,message, message1 -> {
            message1.getMessageProperties().setDelay(delayTime);
            return message1;
        });
    }

}
