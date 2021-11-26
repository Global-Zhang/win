package com.hereWeGo.rpc.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hereWeGo.common.result.BaseResult;

import com.hereWeGo.rpc.service.SendMessageService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.stereotype.Component;

@Service
@Component
public class SendMessageServiceImpl implements SendMessageService {
	@Override
	public BaseResult sendMessage(String phoneNumber) {
		try{
			// 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
			// 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
			Credential cred = new Credential("AKID2tVrwkIZbOvBAsPfeMnysVrxmKbcMp2y", "h8IBoAXdS0GFxPaT5RWKsACxiGXXGiuw");
			// 实例化一个http选项，可选的，没有特殊需求可以跳过
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("sms.tencentcloudapi.com");
			// 实例化一个client选项，可选的，没有特殊需求可以跳过
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			// 实例化要请求产品的client对象,clientProfile是可选的
			SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);
			// 实例化一个请求对象,每个接口都会对应一个request对象
			SendSmsRequest req = new SendSmsRequest();
			String[] phoneNumberSet1 = {"86"+phoneNumber};
			req.setPhoneNumberSet(phoneNumberSet1);

			req.setSmsSdkAppid("1400600510");
			req.setSign("就是这个feel倍儿爽");
			req.setTemplateID("1215898");

			String[] templateParamSet1 = {"1"};
			req.setTemplateParamSet(templateParamSet1);

			// 返回的resp是一个SendSmsResponse的实例，与请求对象对应
			SendSmsResponse resp = client.SendSms(req);
			// 输出json格式的字符串回包
			System.out.println(SendSmsResponse.toJsonString(resp));

			if ("ok".equalsIgnoreCase(resp.getSendStatusSet()[0].getCode())){
				return BaseResult.success();
			}
		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
		}
		return BaseResult.error();
	}
}
