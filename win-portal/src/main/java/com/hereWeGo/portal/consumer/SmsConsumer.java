package com.hereWeGo.portal.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hereWeGo.rpc.service.SendMessageService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 发送短信消费者
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
@RabbitListener(queues = "smsQueue")
public class SmsConsumer {

    @Reference(interfaceClass = SendMessageService.class)
    private SendMessageService sendMessageService;


    @RabbitHandler
    public void process(String phoneNum){
        System.out.println("接受消息"+phoneNum);
        sendMessageService.sendMessage(phoneNum);
    }
}