package com.lac.component.rabbit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lac.component.model.Goods;
import com.lac.component.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private GoodsService goodsService;
    //   @RabbitHandler
//    public void process(String content) {
//       logger.info("处理器one接收处理队列A当中的消息：" +content);
//   }

    @RabbitHandler
    public void process(Map mp){
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
        System.out.println("更新成的件数"+String.valueOf(allCount-reduce));
       int successFlag =  this.goodsService.updateGoods("goods1",allCount-reduce);
       System.out.println(successFlag+"更新成功");

    }
}
