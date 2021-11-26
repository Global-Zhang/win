package com.hereWeGo.order.service;

import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.order.pojo.Order;
import com.hereWeGo.rpc.vo.CartResult;

public interface OrderService {
    /*
    * 生成订单
    * */
    BaseResult orderSave(Admin admin, CartResult cartResult);

    Order selectOrderByOrderSn(String orderSn);
}
