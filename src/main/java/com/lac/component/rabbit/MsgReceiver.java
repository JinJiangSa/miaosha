package com.lac.component.rabbit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lac.component.model.Goods;
import com.lac.component.service.GoodsService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;
    //   @RabbitHandler
//    public void process(String content) {
//       logger.info("处理器one接收处理队列A当中的消息：" +content);
//   }

    @RabbitHandler
    public void process(Map mp, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        List<Goods> goodsList = this.goodsService.selectGoods();
        ObjectMapper mapper = new ObjectMapper();
        Map hashMap = new HashMap<String,Integer>();
        //！！！解决linkedHashmap转实体类的问题
        List<Goods> goods1 = mapper.convertValue(goodsList, new TypeReference<List<Goods>>(){});

        for(Goods a:goods1){
            hashMap.put(a.getGoodsId(),a.getGoodsCount());
        }
        Integer allCount = (Integer)hashMap.get("goods1");

        String goodsId = (String) mp.get("goodsId");
        Integer reduce = (Integer) mp.get("reduce");
        System.out.println((String) mp.get("uid"));
        String uid = (String)redisTemplate.opsForValue().get((String) mp.get("uid"));
        if(uid == null){
            System.out.println("88888888888888"+uid);

            // int i = 1/0;
            System.out.println("更新成的件数"+String.valueOf(allCount-reduce));
            int successFlag =  this.goodsService.updateGoods("goods1",allCount-reduce);
            System.out.println(successFlag+"更新成功");
            redisTemplate.opsForValue().set((String) mp.get("uid"),(String) mp.get("uid"));
            // System.out.println(redisTemplate.opsForValue().get(a.getGoodsId()));
            // Long tag =  (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
            // 模拟rabbitmq消费时出现异常，mq会继续给消费者提供信息，这个时候不用再去消费这两条信息了。
            // int i = 1/0;
            // 确认应答模式
            channel.basicAck(tag,true);
        }else {
            System.out.println("消息已经被消费过了，不能再消费，保障幂等性问题");
        }


    }
}
