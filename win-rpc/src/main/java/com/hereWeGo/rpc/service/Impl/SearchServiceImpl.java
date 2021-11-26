package com.hereWeGo.rpc.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.hereWeGo.common.result.ShopPageInfo;
import com.hereWeGo.rpc.service.SearchService;
import com.hereWeGo.rpc.vo.GoodsVo;
import io.micrometer.core.instrument.search.Search;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*搜素服务实现类*/

@Service(interfaceClass =  SearchService.class)
@Component
public class SearchServiceImpl implements SearchService {

    @SuppressWarnings("all")
    @Autowired
    private RestHighLevelClient client;
    //@SuppressWarnings("all")
    @Override
    public ShopPageInfo<GoodsVo> doSearch(String searchString, Integer pageNumber, Integer pageSize) {
        //构建分页对象
        ShopPageInfo<GoodsVo> shopPageInfo;
        try {
            //指定索引库
            SearchRequest searchRequest = new SearchRequest("shop");
            //构建查询对象
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //设置分页条件
            searchSourceBuilder.from((pageNumber-1)*pageSize).size(pageSize);
            //构建高亮对象
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            //设置高亮字段和样式
            highlightBuilder.field("goodsName")
                    .preTags("<span style='color:red'>")
                    .postTags("</span>");
            searchSourceBuilder.highlighter(highlightBuilder);
            //添加查询条件
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchString,"goodsName"));
            searchRequest.source(searchSourceBuilder);
            //执行请求
            List<GoodsVo> list = new ArrayList<>();
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            //总条数
            Long total = response.getHits().getTotalHits().value;
            if (0 > total){
                return null;
            }
            SearchHit[] hits = response.getHits().getHits();
            for(SearchHit hit:hits){
                Integer goodsId = Integer.valueOf((Integer) hit.getSourceAsMap().get("goodsId"));
                String goodsName = String.valueOf(hit.getSourceAsMap().get("goodsName"));
                String goodsNameHl = String.valueOf(hit.getHighlightFields().get("goodsName").fragments()[0]);
                BigDecimal marketPrice = new BigDecimal(String.valueOf(hit.getSourceAsMap().get("marketPrice")));
                String originalImg = String.valueOf(hit.getSourceAsMap().get("originalImg"));
                GoodsVo goodsVo = new GoodsVo(goodsId, goodsName, goodsNameHl, marketPrice, originalImg);
                list.add(goodsVo);
            }
            shopPageInfo = new ShopPageInfo<>(pageNumber, pageSize, total.intValue());
            shopPageInfo.setResult(list);
            return shopPageInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //处理数据
        return null;
    }
}
