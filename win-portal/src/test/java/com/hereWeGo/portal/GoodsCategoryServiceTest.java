package com.hereWeGo.portal;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.hereWeGo.common.utils.JsonUtil;
import com.hereWeGo.rpc.pojo.GoodsCategory;
import com.hereWeGo.rpc.service.GoodsCategoryService;
import com.hereWeGo.rpc.vo.GoodsCategoryVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GoodsCategoryServiceTest {
    @Reference(interfaceClass = GoodsCategoryService.class)
    private GoodsCategoryService goodsCategoryService;
    @Test
    public void testSelectCategoryListForView(){
        List<GoodsCategoryVo> list = goodsCategoryService.selectCategoryListForView();
        System.out.println(JsonUtil.object2JsonStr(list));
    }
}
