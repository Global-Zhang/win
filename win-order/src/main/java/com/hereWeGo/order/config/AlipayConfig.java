package com.hereWeGo.order.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000118654536";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDOStH2icgSp0xaX/nBzHer1fhPVy58EF56K6sIjsLi/zVpCAvtkxKoNjNyTFXDUO2BtRTu9ZsTDWmu4EUPQmQImgRdI3/bVm+u+4s0FuXendSlODhmfEbwH8JkLQpzzW6uLZ3n4WAap6PUQUXWc7fm70V02dgubs+NbYhn1bpjSomO0SF3SVOeuftsPpAxiCRUhLrpXpnBr7PLc0u7xiSRZQslXobvYZ22IjdL2yz8mAIthwayG61cZ9peoneAsbojROEBM2aZZ6NFUkH85DylqT7oBmulFKCFMiOUyStMG1PlopBxvC4D6SyjrCu/Ybg+C/lSvQ87fJNby7Ee6ul9AgMBAAECggEAQ1U6iPzZ1/TRQCJZc/8YBHdXkBYFryrsm3XLYoSuacchGMIumoHJsivWyPTPfWvZyd9gEMsLrcFQAGtlPyDNY91ZlUjz/5SL6ZJjEkJzDyF2+eKKzjC8Or4zBd2JrtibyrBK/Reb9bdICwyvDdtBaFgq1IQSQr2aczAQOMwTBTsFRgDScEG/2Gtj9ZYS/2GcLb4RDMf/drbnE/0BA7E3lnrFRNLOdaIEa5iLEuiXW3U4vPfgEtmTm57OM74YT8iA9pKl+9J84haJZ9Gx1YQRu7UiSSGPgFtmF5s2/ZSk7rKb7amJPWsz57w39Qx85YJMSiQpwfRZYqrMNuuLSP4KgQKBgQD9VWLTcMoGlP4L4dXHlO1gZXfMGGVDGm9W6/iBzVB4ZHpqlC+YBGZ4itKTfd2o1VGW8ahai/syIlOs4OvjnHCd9M2cxJw/8wJxpyGbx8iFq3NuEHesi95dDKilu+WGBFyKNG0d3V8Gz3GpgM59ypFjjXnGlumcxM2WpS0msg0IXQKBgQDQdq4fe3Rx7qkVBgv0n5C0sdtjqIXS5JfcWorA7GkRONHt0rqwJ37oi7LwfOnZplQkt9v6rxa/BXCtg7GG1hxcR4HBaaSbLA6T+c0bQrFU0kaYMiqhKAqENCyXb77vSLtq3oljzM9bRuVCYERBQRG/l2vwVfS/wCB3QOV7hOTToQKBgQCK4jnYw1fufdY8+2VxEmOE2nvvx1wUirSJibpmpwG7zxIRxgXFqHvpD+pb88AGpFQjlX5/jYh8Nrd66qBH22ceIaxrippCsN4hIshZGBEFvzX8HEkPrmqMxrjai4VMkK2PEyjTG57SI/4jODt8WQaV9mTRi8CtMnOnaAy1zx03/QKBgQCPeO70CfKlqz1Bk+zMirl8M2CsIUMhV2TRKpuOlebmDHYnOT51mGk2wVcBPudvfZMxxjynNrPv+l2HfjjDdT8Fn81FAWFhzpul6mnolikYGLSvcO94BOff/0ylWSpO437h2ZEzV7Vqu8tzuG+hfZgqhZurSG1vP1rY2JhYP6mBoQKBgB6VCJyEQZWc8I3obRZ7fL2eDDcPyPNTucDgS/6FQMKlyaK43iiUCTyxPhJuSFsPStJxaRAZV6puAiAm7t62+ulPB4qL3vDCmVyOZwJvqAjQMbKJPYbBt1fz7nGNT1n6pM6Q+0HlHEGTrCKkj1X0SIGB3mxPFtqRNu38S4mFBKbb";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgKtayer4jyWcIKC3oCQupb0cGSG3M/5QARU6ZulZH8kMhf31/ljfmFLM6pZ5yGm9GEu2zF2G5fmld+Hkf6iULdQhqk51TYQ26yZ3UmNc90Oqh2g9J0VGj/ruiu8+eJ3fwXE1gykcuxYmgXHdFPZySDpbpTX6z0ULCjEvUkSfs/fJii3fpjWLwm2yigiTs0FjrgsnT3RJPb2S6NrrNjsnSW5pc1i7pYL4pDmYW9A0Wg03ZOaH2dq5qazR1uqPOmehaEfXrlZk4wf9g314Cs0xYje2TlTdc1PIgYZ+w6Hmv+AtGfPg71/kU39vupfggi8p27+t7wVWI2ZBH2zZbMJYvwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	//添加内网穿透后，免费网址不一定时更换，注意检查
	public static String notify_url = "http://vje3nu.natappfree.cc/win-portal/order/callback";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，这是内网测试路径
	//public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_uri.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:9098/win-order/order/myOrder";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}