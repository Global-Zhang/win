<!-- 设置项目根路径全局变量 -->
<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <#include "head.ftl">
    <style>#imgVerify {
            width: 120px;
            margin: 0 auto;
            text-align: center;
            display: block;
        }    </style>
    <script>
        function detectBrowser() {
            var browser = navigator.appName
            if (navigator.userAgent.indexOf("MSIE") > 0) {
                var b_version = navigator.appVersion
                var version = b_version.split(";");
                var trim_Version = version[1].replace(/[ ]/g, "");
                if ((browser == "Netscape" || browser == "Microsoft Internet Explorer")) {
                    if (trim_Version == 'MSIE8.0' || trim_Version == 'MSIE7.0' || trim_Version == 'MSIE6.0') {
                        alert('请使用IE9.0版本以上进行访问');
                        return;
                    }
                }
            }
        }

        detectBrowser();
    </script>
    <meta name="__hash__" content="35a35d71936253d091570f5dcdf3efda_36195b7c33bcc5ab73f67451e5438f65"/>
</head>
<body class="login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="#"><b>ego</b></a>
    </div>
    <div class="login-box-body">
        <p class="login-box-msg">管理后台</p>
        <div class="form-group has-feedback">
            <input type="text" name="username" id="username" class="form-control" placeholder="账号"/>
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="password" name="password" class="form-control" id="password" placeholder="密码"/>
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <opinioncontrol realtime="true" opinion_name="vertify_code" default="true">
                <div class="row" style="padding-right: 65px;">
                    <div class="col-xs-8">
                        <input style="width: 135px" type="text" name="vertify" id="vertify" class="form-control"
                               placeholder="验证码"/>
                    </div>
                    <div class="col-xs-4">
                        <img id="imgVerify" style="cursor:pointer;" src="${ctx}/image/getKaptchaImage"
                             alt="点击更换" title="点击更换"/>
                    </div>
                </div>
            </opinioncontrol>
        </div>
        <!--
        <div class="row">
          <div class="col-xs-8">
            <div class="checkbox icheck">
              <label><input type="checkbox"> 记住密码  </label>
            </div>
          </div>
          <div class="col-xs-4">
            <div class="checkbox icheck">
              <label><a href="#">找回密码</a></label>
            </div>
          </div>
        </div> -->
        <div class="form-group">
            <button type="button" class="btn btn-primary btn-block btn-flat" onclick="userLogin()">立即登陆</button>
        </div>
    </div>

    <div class="margin text-center">
        <div class="copyright">
            2014-2016 &copy; <a href="http://www.ego.cn">ego v1.3.3</a>
            <br/>
            <a href="http://www.ego.cn">北京506网络有限公司</a>出品
        </div>
    </div>
</div><!-- /.login-box -->
<script>
    $(function () {
        getKaptchaImage();
    });

    //点击更换验证码
    function getKaptchaImage() {
        $("#imgVerify").on("click", function () {
            var url = "${ctx}" + "/image/getKaptchaImage?time=" + new Date();
            $(this).attr("src", url);
        });
    }


    // 用户登录
    function userLogin() {
        // 必须输入用户名、密码、验证码
        var username = $("#username").val();
        var password = $("#password").val();
        var vertify = $("#vertify").val();

        if (undefined == username || "" == username) {
            layer.alert("请输入用户名！");
            return;
        }

        if (undefined == password || "" == password) {
            layer.alert("请输入密码！");
            return;
        }

        if (undefined == vertify || "" == vertify) {
            layer.alert("请输入验证码！");
            return;
        }

        $.ajax({
            url: "${ctx}/user/login",
            type: "POST",
            data: {
                userName: username,
                password: password,
                vertify: vertify
            },
            dataType: "JSON",
            success: function (result) {
                if (200 == result.code) {
                    location.href = "${ctx}/index";
                } else {
                    // 模拟点击事件行为
                    $("#imgVerify").trigger("click");
                    layer.alert(result.message);
                }
            },
            error: function () {
                layer.alert("亲，系统正在升级中，请稍后再试！");
            }
        });
    }

    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) +
            $(window).scrollTop()) - 30 + "px");
        this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) +
            $(window).scrollLeft()) + "px");
        return this;
    }
</script>
</body>
</html>