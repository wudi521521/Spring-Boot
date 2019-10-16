package com.rabbit.wudi.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

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

    /**
     * 实现一个监听器用于监听Broker端给我们返回的确认请求
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback(){

        /**
         * 确认模式
         * @param correlationData
         * @param ack  true是自动应答，false是手动应答
         * @param cause 异常
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData"+correlationData);
            System.err.println("*****ack*****"+ack);
            System.err.println("*******cause*******"+cause);
            if (!ack){
                System.out.println("exception deal with ");
            }
            //发送成功已经到到了Broker服务器
        }
    };

    /**
     * 返回模式(路由失败会进入此方法中)
     */
     final RabbitTemplate.ReturnCallback returnCallback =    new RabbitTemplate.ReturnCallback(){
         @Override
         public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
             System.err.println("exchange:"+exchange+"routingKey:"+routingKey);
             //交换器没有路由成功会在该方法进行一个回调处理
         }
     };

    public void send(Object message, Map<String,Object> propterties) throws Exception{
        MessageHeaders mhs = new MessageHeaders(propterties);
        Message msg = MessageBuilder.createMessage(message,mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //设置全局id,否者confirm返回的数据为null
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId((new Date()).toString());//id+时间戳全局唯一(实际消息的id)

        //发送数据
        //交换器没有和routingKey 绑定到一块
        //rabbitTemplate.convertAndSend("exchange-1","springboot.hello",msg);
        //交换器和routingKey 绑定到一块
        rabbitTemplate.convertAndSend("topic001","spring.#",msg,correlationData);
    }
}
