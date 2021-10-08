package com.springboot.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TtlMsg {

    @RabbitListener(queues = "QD")
    public void receiverMsg(Message message, Channel channel){
       String msg=new String(message.getBody());
       log.info("当前时间：{}，接收死信队列的消息为：{}",new Date().toString(),message);
    }
}
