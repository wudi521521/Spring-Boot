package com.rabbit.wudi.consume;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


/**
 * @author Dillon Wu
 * @Title: RabbitReceiver
 * @Description: RabbitReceiver rabbit接收端,
 * @date 2019/10/17 15:59
 */
@Component
public class RabbitReceiver {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value ="boot-1",
                    durable = "true"
            ),
            exchange=@Exchange(value = "exchange-boot-1",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"
            ),
            key = "springboot.*"
            )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception{
        System.out.println("消费端-----------数据打印:"+message.getPayload());
        //ack
        Long deliveryTag= (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag,false);
    }
}
