package com.hereWeGo.rpc.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车结果对象
 *
 * @author zhoubin
 * @since 1.0.0
 */
public class CartResult implements Serializable {
   private static final long serialVersionUID = -8936811376383018291L;
   // 购物车列表
   private List<CartVo> cartList;

   // 购物车总金额
   private BigDecimal totalPrice;

   public List<CartVo> getCartList() {
      return cartList;
   }

   public void setCartList(List<CartVo> cartList) {
      this.cartList = cartList;
   }

   public BigDecimal getTotalPrice() {
      return totalPrice;
   }

   public void setTotalPrice(BigDecimal totalPrice) {
      this.totalPrice = totalPrice;
   }

   @Override
   public String toString() {
      return "CartResult{" +
            "cartList=" + cartList +
            ", totalPrice=" + totalPrice +
            '}';
   }
}