package com.hereWeGo.manager.service.impl;


import com.hereWeGo.common.utils.CookieUtil;
import com.hereWeGo.manager.service.CookieService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* cookie服务实现类
* */
@Service
public class CookieServiceImpl implements CookieService {


    /*
    * 设置cookie
    * */
    @Override
    public Boolean setCookie(HttpServletRequest request, HttpServletResponse response, String ticket) {

        try{
            CookieUtil.setCookie(request,response,"userTicket",ticket);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /*
    * 获取cookie
    * */
    @Override
    public String getCookie(HttpServletRequest request) {
        return CookieUtil.getCookieValue(request,"userTicket");
    }

    /*
    * 删除cookie
    * */
    @Override
    public boolean deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        try {
            CookieUtil.deleteCookie(request,response,"userTicket");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
