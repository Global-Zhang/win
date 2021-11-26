package com.hereWeGo.order.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台门户全局拦截器
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class OrderCommonInterceptor implements HandlerInterceptor {


	@Value("${win.portal.url}")
	private String winPortalUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//获取application对象
		ServletContext servletContext = request.getSession().getServletContext();
		String orderUrl = (String) servletContext.getAttribute("portalUrl");
		if (StringUtils.isEmpty(orderUrl)){
			servletContext.setAttribute("portalUrl",winPortalUrl);
		}
		return true;
	}
}