package com.lac.component.controller;

import com.lac.component.rabbit.MsgProducer;
import com.lac.component.rabbit.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class DemoController {



    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitConfig rabbitConfig;
    @GetMapping("/")
    public String getHello() {
        return "hello";
    }
    @GetMapping("/user/{string}")
    public String test(@PathVariable String string) {
        return "Hello Nacos :" + string;
    }
    @GetMapping("/danbing2226/{string}")
    public String test1(@PathVariable String string) {
        return "灰色天空 :" + string;
    }
    @GetMapping("/xiawanan/{str}")
    public String test2(@PathVariable String str) {
        return "夏婉安的歌曲:"+str;
    }
    @GetMapping("/huisetiankong/{str}")
    public String test3(@PathVariable String str) {
        return "听了无数遍:"+str;
    }

    @GetMapping("/rabbit")
    public  String send() throws Exception{

        String goodsId = "goods1";
        Random r = new Random(1);
        int i = r.nextInt(100);
            MsgProducer producer = new MsgProducer(rabbitConfig.rabbitTemplate());
            System.out.println(redisTemplate.opsForValue().toString());
            Integer count = (Integer) redisTemplate.opsForValue().get(goodsId);
            if(count == 0){
                System.out.println("没库存了");
                return "没库存了";
            }
            long kucun = redisTemplate.opsForValue().decrement(goodsId,i);
            if(kucun <0 ){
                count = (Integer) redisTemplate.opsForValue().get(goodsId);
                if(count != 0 && count < Integer.valueOf(i)){
                    redisTemplate.opsForValue().increment(goodsId,i);
                    System.out.println("买多了再把库存还原");
                    return "买多了再把库存还原";
                }else if(count == 0){
                    redisTemplate.opsForValue().set(goodsId,0);
                    return "库存卖完了";
                }
                System.out.println("redis库存:"+ redisTemplate.opsForValue().get(goodsId));
            }
            producer.sendMsg("goods1",String.valueOf(i));
      return "下单成功";
    }

}
