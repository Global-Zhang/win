package com.hereWeGo.portal.service.Impl;

import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.portal.service.CaptchaService;
import org.springframework.stereotype.Service;

/**
 * 验证服务实现类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

	/**
	 * 校验验证码
	 * @param ticket
	 * @param randStr
	 * @return
	 */
	@Override
	public BaseResult captcha(String ticket, String randStr) {
		try{

			Credential cred = new Credential("自己注册", "自己注册");

			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("captcha.tencentcloudapi.com");

			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);

			CaptchaClient client = new CaptchaClient(cred, "ap-shanghai", clientProfile);

			String params = "{\"CaptchaType\":9,\"Ticket\":\""+ticket+"\",\"UserIp\":\"127.0.0.1\",\"Randstr\":\""
			+randStr+"\"," +
					"\"CaptchaAppId\":自己注册,\"AppSecretKey\":\"自己注册**\"}";
			DescribeCaptchaResultRequest req = DescribeCaptchaResultRequest.fromJsonString(params, DescribeCaptchaResultRequest.class);

			DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);

			System.out.println(DescribeCaptchaResultRequest.toJsonString(resp));
			if ("ok".equalsIgnoreCase(resp.getCaptchaMsg())){
				return BaseResult.success();
			}else{
				return BaseResult.error();
			}
		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
		}
		return null;
	}
}
