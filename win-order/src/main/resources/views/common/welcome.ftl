<!-- 设置项目根路径全局变量 -->
<#assign ctx=request.contextPath/>
<div id="s_tbar">
    <div class="s_hd">
        <#if user??>
            <div class="tbar_lft">${user.userName}您好，欢迎来到shop商城！<a href="${ctx}/user/logout">退出</a></div>
        <#else>
            <div class="tbar_lft">您好，欢迎来到shop商城！<a href="${ctx}/login">请登录</a> | <a href="#">免费注册</a></div>
        </#if>
        <div class="tbar_rgt">
            <ul>
                <li class="first"><a href="#">我的订单</a></li>
                <li><a href="#">我的shop商城</a></li>
                <li><a href="#">帮助中心</a></li>
                <li><a href="#">联系客服</a></li>
                <li><a href="#">加入收藏</a></li>
                <li class="s_tel_str">服务热线：</li>
                <li class="s_tel">400-009-1906</li>
            </ul>
        </div>
    </div>
</div>