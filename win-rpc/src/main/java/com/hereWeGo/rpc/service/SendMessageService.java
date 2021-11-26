package com.hereWeGo.rpc.service;

import com.hereWeGo.common.result.BaseResult;

/**
 * 短信发送服务
 */
public interface SendMessageService {

	/**
	 * 发送短信
	 * @param phoneNum
	 * @return
	 */
	BaseResult sendMessage(String phoneNum);
}
