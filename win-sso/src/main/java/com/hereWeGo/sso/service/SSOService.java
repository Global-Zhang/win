package com.hereWeGo.sso.service;

import com.hereWeGo.common.pojo.Admin;

public interface SSOService {
    /*
    * 登录方法-返回票据
    * */
    String login(Admin admin);

    /*
    * 验证票据-返回用户信息
    * */
    Admin validate(String ticket);

    /*
    * 用户退出
    * */
    void logout(String ticket);
}
