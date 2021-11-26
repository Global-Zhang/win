package com.hereWeGo.rpc.service;

import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.rpc.vo.CartResult;
import com.hereWeGo.rpc.vo.CartVo;

/*
* 购物车服务
* */
public interface CartService {

    /*
    * 加入购物车
    * */
    BaseResult addCart(CartVo cartVo, Admin admin);


    /**
     * 查询购物车数量
     * */
    Integer getCartNum(Admin admin);

    /*
    * 获取购物车列表
    * */
    CartResult getCartList(Admin admin);

    /*
    * 清除购物车信息
    * */
    BaseResult clearCart(Admin admin);
}
