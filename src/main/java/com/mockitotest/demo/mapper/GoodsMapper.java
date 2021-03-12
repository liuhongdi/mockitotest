package com.mockitotest.demo.mapper;

import com.mockitotest.demo.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GoodsMapper {
    Goods selectOneGoods(Long goodsId);
    int insertOneGoods(Goods goods);
    int updateOneGoods(Goods goods);
}
