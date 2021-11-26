package com.hereWeGo.common.enums;

/**
 * @see: 订单方式枚举类
 * @author: zhoubin
 */
public enum PromTypeStatus {
    // 0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠
    normal((byte) 0, "普通订单"),
    xsqg((byte) 1, " 限时抢购"),
    tg((byte) 2, "团购 "),
    cxyh((byte) 3, "促销优惠");

    // 状态
    private Byte status;
    // 描述
    private String message;

    // 自定义构造器
    private PromTypeStatus(Byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}