package com.lac.component.service.impl;

import com.lac.component.dao.GoodsDao;
import com.lac.component.model.Goods;
import com.lac.component.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service(value = "GoodsService")
public class GoodsServiceImpl implements GoodsService, Serializable {

    @Autowired
    private GoodsDao goodsDao;
    @Override
    public List<Goods> selectGoods() {
        return goodsDao.selectGoods();
    }

    @Override
    public int updateGoods(String id, Integer count) {
        return goodsDao.updateGoods(id,count);
    }
}
