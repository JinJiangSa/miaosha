package com.lac.component.service;

import com.lac.component.model.Goods;

import java.util.List;


public interface GoodsService {
    List<Goods> selectGoods();
    int updateGoods(String id, Integer count);
}
