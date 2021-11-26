package com.hereWeGo.portal.controller;

import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.portal.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 验证码Controller
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
public class CaptchaController {


	@Autowired
	private CaptchaService captchaService;

	/**
	 * 校验验证码
	 * @param ticket
	 * @param randStr
	 * @return
	 */
	@RequestMapping("getCaptcha")
	@ResponseBody
	public BaseResult getCaptcha(String ticket,String randStr){
		return captchaService.captcha(ticket,randStr);
	}

}