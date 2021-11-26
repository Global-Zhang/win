package com.hereWeGo.rpc.service;

import com.hereWeGo.common.result.ShopPageInfo;
import com.hereWeGo.rpc.vo.GoodsVo;

import java.util.List;

/*
* 搜索服务
* */
public interface SearchService {

    //商品页返回对象，用于分页，替代elasticsearch返回数据时，pageHelper无法处理的情况
    ShopPageInfo<GoodsVo> doSearch(String searchString, Integer pageNumber, Integer pageSize);
}
