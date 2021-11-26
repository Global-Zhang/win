package com.hereWeGo.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hereWeGo.common.result.ShopPageInfo;
import com.hereWeGo.rpc.service.SearchService;
import com.hereWeGo.rpc.vo.GoodsVo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("search")
public class SearchController {


    @Reference(interfaceClass = SearchService.class)
    private SearchService searchService;
    //跳转到搜索页

    @SuppressWarnings("all")
    @RequestMapping("index")
    public String index(String searchStr, Model model){
        model.addAttribute("searchStr",searchStr);
        return "search/doSearch";
    }

    @RequestMapping("searchGoods")
    //没注释的时候返回对象前端无法收到，显示searchStr无法接收数据
    @ResponseBody
    public ShopPageInfo<GoodsVo> searchGoods(String searchStr,Integer pageNum,Integer pageSize){
        return searchService.doSearch(searchStr,pageNum,pageSize);
    }

}
