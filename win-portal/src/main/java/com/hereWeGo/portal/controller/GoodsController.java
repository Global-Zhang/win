package com.hereWeGo.portal.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.hereWeGo.rpc.pojo.GoodsCategory;
import com.hereWeGo.rpc.service.GoodsCategoryService;
import com.hereWeGo.rpc.vo.GoodsCategoryVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("goodsCategory")
public class GoodsController {

    //通过dubbo暴露，调用rpc的服务 com.hereWeGo.rpc.service.GoodsCategoryService
    @Reference(interfaceClass = GoodsCategoryService.class)
    private GoodsCategoryService goodsCategoryService;

    //查询商品分类列表
    @RequestMapping("list")
    @ResponseBody
    public List<GoodsCategoryVo> categoryVoList(){
        return goodsCategoryService.selectCategoryListForView();
    }
}
