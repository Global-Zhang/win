package com.hereWeGo.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieService {
    //设置cookie
    Boolean setCookie(HttpServletRequest request, HttpServletResponse response,String ticket);
    //获取cookie
    String getCookie(HttpServletRequest request);
    //删除cookie
    boolean deleteCookie(HttpServletRequest request,HttpServletResponse response);
}
