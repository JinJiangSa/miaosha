package com.lac.component.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lac.component.model.Goods;
import com.lac.component.service.GoodsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class initController implements InitializingBean {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private GoodsService goodsService;
    @Override
    public void afterPropertiesSet() throws Exception {
        List<Goods> goodsList = this.goodsService.selectGoods();
        ObjectMapper mapper = new ObjectMapper();
        //！！！解决linkedHashmap转实体类的问题
        List<Goods> goods1 = mapper.convertValue(goodsList, new TypeReference<List<Goods>>(){});

        for(Goods a:goods1){
            redisTemplate.opsForValue().set(a.getGoodsId(),a.getGoodsCount());
            System.out.println(redisTemplate.opsForValue().get(a.getGoodsId()));
        }

    }
}
