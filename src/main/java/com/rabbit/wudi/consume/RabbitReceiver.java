package com.rabbit.wudi.consume;

import com.rabbit.wudi.entity.Order;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author Dillon Wu
 * @Title: RabbitReceiver
 * @Description: RabbitReceiver rabbit接收端,
 * @date 2019/10/17 15:59
 */

@Component
public class RabbitReceiver {

    @Value("${spring.rabbitmq.listener.order.exchange.name}")
    private String exchangeOrder;

    @Value("${spring.rabbitmq.listener.order.key}")
    private String orderKey;


    /**
     * Rabbit 接收端接受一般数据处理
     * @param message
     * @param channel
     * @throws Exception
     */
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

    /**
     * Rabbit 接受端接收Oder实体
     * @param order 实体
     * @param channel 管道
     * @param headers headers数据
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value ="${spring.rabbitmq.listener.order.queue.name}",
                    durable = "${spring.rabbitmq.listener.order.queue.durable}"
            ),
            exchange=@Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                    type = "${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"
            ),
            key = "${spring.rabbitmq.listener.order.key}"
    )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, Channel channel, @Headers Map<String,Object> headers) throws Exception{
        System.out.println("消费端------order-----数据打印:"+order);
        //ack
        Long deliveryTag= (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag,false);
    }
}
