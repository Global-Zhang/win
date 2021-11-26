package com.hereWeGo.order.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//搜索
@Controller
@RequestMapping("search")
public class SearchController {

    /*
    * 跳转到搜索页面
    * */
    @RequestMapping("index")
    public String index(HttpServletRequest request, String searchStr, Model model){
        try {
            //对输入的内容进行编码，防止乱码（跨域搜索）
            searchStr = URLEncoder.encode(searchStr,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:"+request.getSession()
                                  .getServletContext()
                                  .getAttribute("portalUrl")
                                  + "search/index?searchStr="
                                  + searchStr;
    }
}
