package com.rabbit.wudi.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Dillon Wu
 * @Title: RabbitSender
 * @Description: 消息发送者
 * @date 2019/10/15 20:13
 */
@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
}
