package com.hereWeGo.order.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhoubin 
 * @since 1.0.0
 */
public class Order implements Serializable {
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 订单状态
     */
    private Byte orderStatus;

    /**
     * 发货状态
     */
    private Byte shippingStatus;

    /**
     * 支付状态
     */
    private Byte payStatus;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 国家
     */
    private Integer country;

    /**
     * 省份
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 县区
     */
    private Integer district;

    /**
     * 乡镇
     */
    private Integer twon;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String zipcode;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮件
     */
    private String email;

    /**
     * 物流code
     */
    private String shippingCode;

    /**
     * 物流名称
     */
    private String shippingName;

    /**
     * 支付code
     */
    private String payCode;

    /**
     * 支付方式名称
     */
    private String payName;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 商品总价
     */
    private BigDecimal goodsPrice;

    /**
     * 邮费
     */
    private BigDecimal shippingPrice;

    /**
     * 使用余额
     */
    private BigDecimal userMoney;

    /**
     * 优惠券抵扣
     */
    private BigDecimal couponPrice;

    /**
     * 使用积分
     */
    private Integer integral;

    /**
     * 使用积分抵多少钱
     */
    private BigDecimal integralMoney;

    /**
     * 应付款金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单总价
     */
    private BigDecimal totalAmount;

    /**
     * 下单时间
     */
    private Integer addTime;

    /**
     * 最后新发货时间
     */
    private Integer shippingTime;

    /**
     * 收货确认时间
     */
    private Integer confirmTime;

    /**
     * 支付时间
     */
    private Integer payTime;

    /**
     * 活动id
     */
    private Short orderPromId;

    /**
     * 活动优惠金额
     */
    private BigDecimal orderPromAmount;

    /**
     * 价格调整
     */
    private BigDecimal discount;

    /**
     * 用户备注
     */
    private String userNote;

    /**
     * 管理员备注
     */
    private String adminNote;

    /**
     * 父单单号
     */
    private String parentSn;

    /**
     * 是否已分成0未分成1已分成
     */
    private Byte isDistribut;

    /**
     * t_order
     */
    private static final long serialVersionUID = 1L;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Byte getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(Byte shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public Integer getTwon() {
        return twon;
    }

    public void setTwon(Integer twon) {
        this.twon = twon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode == null ? null : shippingCode.trim();
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName == null ? null : shippingName.trim();
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode == null ? null : payCode.trim();
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public BigDecimal getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public BigDecimal getIntegralMoney() {
        return integralMoney;
    }

    public void setIntegralMoney(BigDecimal integralMoney) {
        this.integralMoney = integralMoney;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Integer shippingTime) {
        this.shippingTime = shippingTime;
    }

    public Integer getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Integer confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getPayTime() {
        return payTime;
    }

    public void setPayTime(Integer payTime) {
        this.payTime = payTime;
    }

    public Short getOrderPromId() {
        return orderPromId;
    }

    public void setOrderPromId(Short orderPromId) {
        this.orderPromId = orderPromId;
    }

    public BigDecimal getOrderPromAmount() {
        return orderPromAmount;
    }

    public void setOrderPromAmount(BigDecimal orderPromAmount) {
        this.orderPromAmount = orderPromAmount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote == null ? null : userNote.trim();
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote == null ? null : adminNote.trim();
    }

    public String getParentSn() {
        return parentSn;
    }

    public void setParentSn(String parentSn) {
        this.parentSn = parentSn == null ? null : parentSn.trim();
    }

    public Byte getIsDistribut() {
        return isDistribut;
    }

    public void setIsDistribut(Byte isDistribut) {
        this.isDistribut = isDistribut;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", userId=").append(userId);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", shippingStatus=").append(shippingStatus);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", consignee=").append(consignee);
        sb.append(", country=").append(country);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", twon=").append(twon);
        sb.append(", address=").append(address);
        sb.append(", zipcode=").append(zipcode);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", shippingCode=").append(shippingCode);
        sb.append(", shippingName=").append(shippingName);
        sb.append(", payCode=").append(payCode);
        sb.append(", payName=").append(payName);
        sb.append(", invoiceTitle=").append(invoiceTitle);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", shippingPrice=").append(shippingPrice);
        sb.append(", userMoney=").append(userMoney);
        sb.append(", couponPrice=").append(couponPrice);
        sb.append(", integral=").append(integral);
        sb.append(", integralMoney=").append(integralMoney);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", addTime=").append(addTime);
        sb.append(", shippingTime=").append(shippingTime);
        sb.append(", confirmTime=").append(confirmTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", orderPromId=").append(orderPromId);
        sb.append(", orderPromAmount=").append(orderPromAmount);
        sb.append(", discount=").append(discount);
        sb.append(", userNote=").append(userNote);
        sb.append(", adminNote=").append(adminNote);
        sb.append(", parentSn=").append(parentSn);
        sb.append(", isDistribut=").append(isDistribut);
        sb.append("]");
        return sb.toString();
    }
}