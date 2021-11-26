<!-- 设置项目根路径全局变量 -->
<#assign ctx=request.contextPath/>
<div id="s_cart">
    <ul>
        <li class="nums">
            <a href="javascript:void(0);">购物车： <span id="s_cart_nums1">0</span> 件</a>
            <a href="javascript:void(0);" class="btn" id="s_cart_nums2"></a>
        </li>
        <li class="checkout">
            <a href="javascript:void(0);">去结算&gt;&gt;</a>
        </li>
    </ul>
</div>
<script type="text/javascript">
    $(function () {
        // 获取购物车数量
        getCartNum();
    });

    // 获取购物车数量
    function getCartNum() {
        $.ajax({
            url: "${ctx}/cart/getCartNum",
            type: "POST",
            dataType: "JSON",
            success: function (result) {
                $("#s_cart_nums1").text(result);
            },
            error: function (result) {
                layer.alert("亲，系统正在升级中，请稍后再试！");
            }
        });
    }
</script>