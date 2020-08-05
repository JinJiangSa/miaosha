package com.lac.component.dao;

import com.lac.component.model.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsDao {
    List<Goods> selectGoods();
    int updateGoods(@Param("id")String id, @Param("count")Integer count);
}
