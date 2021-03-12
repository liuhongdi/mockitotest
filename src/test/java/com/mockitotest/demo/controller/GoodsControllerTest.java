package com.mockitotest.demo.controller;

import com.jayway.jsonpath.JsonPath;
import com.mockitotest.demo.pojo.Goods;
import com.mockitotest.demo.service.GoodsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@AutoConfigureMockMvc
@SpringBootTest
class GoodsControllerTest {

    @InjectMocks
    @Autowired
    private GoodsController goodsController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    GoodsService goodsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("商品controller:得到一件商品")
    void goodsInfo() throws Exception {
        Goods goodsOne = new Goods();
        goodsOne.setGoodsId(13L);
        goodsOne.setGoodsName("商品名称");
        goodsOne.setSubject("商品描述");
        goodsOne.setPrice(new BigDecimal(100));
        goodsOne.setStock(10);
        doReturn(goodsOne).when(goodsService).getOneGoodsById(any());

        MvcResult mvcResult = mockMvc.perform(get("/goods/one?goodsid=13")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(content);
        int code = JsonPath.parse(content).read("$.code");
        assertThat(code, equalTo(0));
        int goodsId = JsonPath.parse(content).read("$.data.goodsId");
        assertThat(goodsId, equalTo(13));
    }

    @Test
    @DisplayName("商品controller:得到一件商品:不存在")
    void goodsInfonull() throws Exception {
        doReturn(null).when(goodsService).getOneGoodsById(0L);
        MvcResult mvcResult = mockMvc.perform(get("/goods/one?goodsid=0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(content);
        int code = JsonPath.parse(content).read("$.code");
        assertThat(code, equalTo(800));
    }
}