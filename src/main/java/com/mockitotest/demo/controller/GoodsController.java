package com.mockitotest.demo.controller;

import com.mockitotest.demo.constant.ResponseCode;
import com.mockitotest.demo.pojo.Goods;
import com.mockitotest.demo.result.RestResult;
import com.mockitotest.demo.service.GoodsService;

import javax.annotation.Resource;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    //商品详情 参数:商品id
    @GetMapping("/one")
    @ResponseBody
    public RestResult goodsInfo(@RequestParam(value="goodsid",required = true,defaultValue = "0") Long goodsId) {
        Goods goods = goodsService.getOneGoodsById(goodsId);
        if (goods == null) {
            return RestResult.error(ResponseCode.GOODS_NOT_EXIST);
        } else {
            return RestResult.success(goods);
        }
    }


}

