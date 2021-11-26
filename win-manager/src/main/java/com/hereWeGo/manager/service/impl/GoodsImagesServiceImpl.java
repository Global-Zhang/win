package com.hereWeGo.manager.service.impl;

import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.manager.mapper.GoodsImagesMapper;
import com.hereWeGo.manager.pojo.GoodsImages;
import com.hereWeGo.manager.service.GoodsImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsImagesServiceImpl implements GoodsImagesService {
    @Autowired
    private GoodsImagesMapper goodsImagesMapper;
    @Override
    public BaseResult saveGoodsImages(GoodsImages goodsImages) {
        int result = goodsImagesMapper.insertSelective(goodsImages);
        return result>0 ? BaseResult.success() : BaseResult.error();
    }
}
