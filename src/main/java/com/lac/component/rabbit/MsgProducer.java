package com.lac.component.rabbit;

import com.lac.component.rabbit.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// import java.util.UUID;
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }
    public void sendMsg(String goodsId,String content){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //Fanout 就是我们熟悉的广播模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
        //rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE,content);
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        //rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A,RabbitConfig.ROUTINGKEY_A,content,correlationId);
        //传输对象
        Map mp = new HashMap(1024);
        mp.put("goodsId",goodsId);
        mp.put("reduce",Integer.valueOf(content));
        mp.put("uid",UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A,RabbitConfig.ROUTINGKEY_A,mp,correlationId);
        //rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A,RabbitConfig.ROUTINGKEY_A,user,correlationId);

    }
    /*
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("生产者消息被投递");
        } else {
            System.out.println("生产者消费投递失败:" + cause );
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("消息没有发送成功");
    }
}
