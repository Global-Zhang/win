package com.hereWeGo.manager.service;

import com.hereWeGo.manager.pojo.GoodsCategory;
import com.hereWeGo.manager.vo.GoodsCategoryVo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface GoodsCategoryService {
    //商品分类，新增分类
    List<GoodsCategory> selectCategoryTopList();
    //商品分类，级联查询
    List<GoodsCategory> selectCategoryByParentId(Short parentId);
    //商品分类，新增分类，保存分类
    int categorySave(GoodsCategory goodsCategory);
    //商品分类列表
    List<GoodsCategoryVo> selectCategoryListForView();

    GoodsCategory selectCategoryById( Short id);

    int updateByPrimaryKeySelective(GoodsCategory record);

    int deleteByPrimaryKey(Short id);

    List<GoodsCategory> selectCategoryList();
}
