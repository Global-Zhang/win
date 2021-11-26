package com.hereWeGo.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.order.config.AlipayConfig;
import com.hereWeGo.order.pojo.Order;
import com.hereWeGo.order.service.OrderService;
import com.hereWeGo.rpc.service.CartService;
import com.hereWeGo.rpc.vo.CartResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/*
* 与order系统搭配
* */
@Controller
@RequestMapping("order")
public class OrderController {

    @Reference(interfaceClass = CartService.class)
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    //跳转到预定的页面
    @RequestMapping("preOrder")
    public String preOrder(Model model, HttpServletRequest request){
        Admin admin = (Admin) request.getSession().getAttribute("user");
        model.addAttribute("cartResult", cartService.getCartList(admin));
        return "order/preOrder";
    }

    //跳转到订单提交页面
    @RequestMapping("submitOrder")
    public String submitOrder(Model model,HttpServletRequest request){
        Admin admin = (Admin) request.getSession().getAttribute("user");
        CartResult cartResult = cartService.getCartList(admin);
        model.addAttribute("cartResult", cartService.getCartList(admin));
        //存入订单信息
        BaseResult baseResult = orderService.orderSave(admin, cartResult);
        //总价
        model.addAttribute("totalPrice",cartResult.getTotalPrice());
        //订单编号
        model.addAttribute("orderSn", baseResult.getMessage());
        //清除购物车信息
        cartService.clearCart(admin);
        //页面跳转
        return "order/submitOrder";
    }

    @RequestMapping("payment")
    public String payment(HttpServletRequest request,Model model,String orderSn) {
        try {
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                    AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
                    AlipayConfig.sign_type);

            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            Order order = orderService.selectOrderByOrderSn(orderSn);

            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = orderSn;
            //付款金额，必填
            String total_amount = String.valueOf(order.getTotalAmount());
            //订单名称，必填
            String subject = "用户为"+order.getUserId()+"的订单";
            //商品描述，可空
            String body = "";

            alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"body\":\"" + body + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
            //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
            //		+ "\"total_amount\":\""+ total_amount +"\","
            //		+ "\"subject\":\""+ subject +"\","
            //		+ "\"body\":\""+ body +"\","
            //		+ "\"timeout_express\":\"10m\","
            //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            //输出
            System.out.println(result);
            model.addAttribute("result",result);
            return "order/payment";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 跳转到我的订单页面
    * 异步通知，貌似没实现。。。同步通知和异步通知都没实现跳转，老毛病了，时好时坏
    * */
    @RequestMapping("callback")
    public String callback(Model model){
        //支付宝硬性要求返回的参数
        model.addAttribute("result","success");
        System.out.println("异步通知成功！！！");
        return "order/callback";

    }

}
