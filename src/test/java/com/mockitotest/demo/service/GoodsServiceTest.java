package com.mockitotest.demo.service;

import com.mockitotest.demo.mapper.GoodsMapper;
import com.mockitotest.demo.pojo.Goods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;

class GoodsServiceTest {

    @InjectMocks
    @Autowired
    GoodsService goodsService;

    @Mock
    GoodsMapper goodsMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("商品service:得到一件商品")
    void getOneGoodsById() {

        Goods goodsOne = new Goods();
        goodsOne.setGoodsId(13L);
        goodsOne.setGoodsName("商品名称");
        goodsOne.setSubject("商品描述");
        goodsOne.setPrice(new BigDecimal(100));
        goodsOne.setStock(10);

        doReturn(goodsOne).when(goodsMapper).selectOneGoods(13L);
        doReturn(null).when(goodsMapper).selectOneGoods(0L);

        Goods goodsRet = goodsService.getOneGoodsById(13L);
        assertThat(goodsRet.getGoodsId(), equalTo(13L));

        Goods goodsRet2 = goodsService.getOneGoodsById(0L);
        assertThat(goodsRet2, equalTo(null));
    }


    @Test
    @DisplayName("商品service:测试添加一件商品")
    void addOneGoods() {
        Goods goodsOne = new Goods();
        goodsOne.setGoodsId(13L);
        goodsOne.setGoodsName("商品名称");
        goodsOne.setSubject("商品描述");
        goodsOne.setPrice(new BigDecimal(100));
        goodsOne.setStock(10);

        Goods goodsTwo = new Goods();
        goodsTwo.setGoodsId(12L);
        goodsTwo.setGoodsName("商品名称");
        goodsTwo.setSubject("商品描述");
        goodsTwo.setPrice(new BigDecimal(100));
        goodsTwo.setStock(10);

        doReturn(1).when(goodsMapper).insertOneGoods(goodsOne);
        doReturn(0).when(goodsMapper).insertOneGoods(goodsTwo);

        Long goodsId = goodsService.addOneGoods(goodsOne);
        assertThat(goodsId, equalTo(13L));

        Long goodsIdFail = goodsService.addOneGoods(goodsTwo);;
        assertThat(goodsIdFail, equalTo(0L));

    }
}