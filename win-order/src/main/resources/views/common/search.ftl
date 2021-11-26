<!-- 设置项目根路径全局变量 -->
<#assign ctx=request.contextPath/>
<div id="s_search">
    <form action="${ctx}/search/index" method="get">
        <input name="searchStr" type="text" class="search-input"/>
        <input name="" type="image" src="${ctx}/static/images/btn_search.jpg"/>
    </form>
</div>