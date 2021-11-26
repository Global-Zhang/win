package com.hereWeGo.portal.service;

import com.hereWeGo.common.result.BaseResult;

/**
 * 验证服务
 */
public interface CaptchaService {
	//校验验证码
	BaseResult captcha(String ticket, String randStr);
}
