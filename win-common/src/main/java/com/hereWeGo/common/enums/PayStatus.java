package com.hereWeGo.common.enums;

/**
 * @see : 支付状态枚举类
 * @author : zhoubin

 */
public enum PayStatus {
    // 0未支付 1已支付 2部分支付
    no_pay((byte) 0, "未支付"),
    has_payed((byte) 1, "已支付"),
    part_pay((byte) 2, "部分支付");

    // 状态
    private Byte status;
    // 描述
    private String message;

    // 自定义构造器
    private PayStatus(Byte status, String message) {
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