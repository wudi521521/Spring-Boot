package com.rabbit.wudi.controller;


import com.rabbit.wudi.producer.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dillon Wu
 * @Title: ProducerController
 * @Description: TODO
 * @date 2019/10/16 9:44
 */

@RestController
@RequestMapping("/template")
public class ProducerController {

    @Autowired
    private RabbitSender rabbitSender;

    /**
     * spring boot 发送者
     * @throws Exception
     */
    @RequestMapping("/send1")
    public void testSend() throws Exception{
        Map<String,Object> properties = new HashMap<>();
        properties.put("number",12345);
        properties.put("date",new Date());
        rabbitSender.send("Hello AMQP For Spring Boot",properties);
    }
}
