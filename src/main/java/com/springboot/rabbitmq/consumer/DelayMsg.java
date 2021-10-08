package com.springboot.rabbitmq.consumer;

import com.springboot.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DelayMsg {

    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayMsg(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{}，接收到延迟队列的消息：{}",new Date().toString(),msg);
    }
}
