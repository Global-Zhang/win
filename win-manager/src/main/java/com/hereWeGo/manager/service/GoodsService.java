package com.hereWeGo.manager.service;

import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.manager.pojo.Goods;
import com.hereWeGo.manager.pojo.GoodsExample;

import java.util.List;

public interface GoodsService {
    List<Goods> selectGoodsList();
    BaseResult saveGoods(Goods goods);
    //商品列表-分页查询
    BaseResult selectGoodsListByPage(Goods goods,Integer pageNum,Integer pageSize);

}
