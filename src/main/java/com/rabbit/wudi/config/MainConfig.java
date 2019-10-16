package com.rabbit.wudi.config;



import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;


import java.util.Map;

/**
 * @author Dillon Wu
 * @Title: MainConfig
 * @Description:
 * @date 2019/10/15 20:11
 */
@Configuration
@ComponentScan({"com.rabbit.wudi.*"})
public class MainConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object message, Map<String,Object> propterties) throws Exception{
        MessageHeaders mhs = new MessageHeaders(propterties);
        Message msg = MessageBuilder.createMessage(message,mhs);
        //发送数据
        rabbitTemplate.convertAndSend("exchange-1","springboot.hello",msg);
    }
}
