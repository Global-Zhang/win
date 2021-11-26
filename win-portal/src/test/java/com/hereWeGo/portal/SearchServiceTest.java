package com.hereWeGo.portal;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hereWeGo.common.result.ShopPageInfo;
import com.hereWeGo.common.utils.JsonUtil;
import com.hereWeGo.rpc.service.SearchService;
import com.hereWeGo.rpc.vo.GoodsVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SearchServiceTest {
    @Reference(interfaceClass = SearchService.class)
    private SearchService searchService;

    @Test
    public void testSearch(){
        ShopPageInfo<GoodsVo> list = searchService.doSearch("中国移动联通电信", 1, 10);
        System.out.println(JsonUtil.object2JsonStr(list));
    }
}
