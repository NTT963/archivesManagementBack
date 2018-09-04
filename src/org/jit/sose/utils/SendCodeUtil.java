package org.jit.sose.utils;
import javax.servlet.http.HttpServletRequest;
public class SendCodeUtil {
	// 用户名
	private static String Uid = "dylan1172";
	// 接口安全秘钥
	private static String Key = "1172474142=111";
	// 验证码
	private String code = "";
	// 验证码长度
	private static int codeLength = 6;
	// 短信内容
	private String smsText;
	/**
	 * 调用HttpClientUtil工具类
	 * 
	 * @return
	 */
	public int sendSmsText(String phone, HttpServletRequest request) {
		HttpClientUtil client = HttpClientUtil.getInstance();
		// UTF发送
		/**
		 * Uid：用户名 Key：密钥 smsText：要发送的信息 phone：目标手机号
		 */
		for (int i = 0; i < codeLength; i++) {
			code +=(int)(Math.random() * 9+1);
		}
		
		smsText = "验证码:"+code;
		System.out.println("手机号码:"+phone);
		System.out.println(smsText);
		int result = 0;
		result = client.sendMsgUtf8(Uid, Key, smsText, phone);
		//将生成验证码放入session中
		request.getSession().setAttribute("createMsgCode", code);
		if (result > 0) {
			System.out.println("UTF8成功发送条数==" + result);
		} else {
			System.out.println(client.getErrorMsg(result));
		}
		return result;
	}
}
