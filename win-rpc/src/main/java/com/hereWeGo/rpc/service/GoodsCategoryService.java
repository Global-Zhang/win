package com.hereWeGo.rpc.service;

import com.hereWeGo.rpc.pojo.GoodsCategory;
import com.hereWeGo.rpc.vo.GoodsCategoryVo;

import java.util.List;


public interface GoodsCategoryService {

    //商品分类列表
    List<GoodsCategoryVo> selectCategoryListForView();

}
