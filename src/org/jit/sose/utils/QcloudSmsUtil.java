package org.jit.sose.utils;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;



/**
 * @author dylan
 *  通过腾讯云 指定模板发送短信
 *  腾讯云短信API  https://cloud.tencent.com/document/product/382/5976
 */
public class QcloudSmsUtil {


        // 短信应用SDK AppID
        int appid = 1400090967; // 1400开头

        // 短信应用SDK AppKey
        String appkey = "e3aaa9bb1b15d508ddb478b62143d9fa==";

        // 需要发送短信的手机号码
//        String[] phoneNumbers = {"21212313123", "12345678902", "12345678903"};
//        String[] phoneNumbers = {"15996359676"};
        
        // 短信模板ID，需要在短信应用中申请
        // NOTE: 这里的模板ID`118951`只是一个示例，
        // 真实的模板ID需要在短信控制台中申请
//        int templateId = 118951;

        // 签名
        // NOTE: 这里的签名"腾讯云"只是一个示例，
        // 真实的签名需要在短信控制台中申请，另外
        // 签名参数使用的是`签名内容`，而不是`签名ID`
        String smsSign = "WhiteLie";

        // 验证码
    	private String code = "";
    	// 验证码长度
    	private static int codeLength = 6;
        
    	/**
    	 * @param phoneNumbers 不带国家码的手机号列表
    	 * @param msgFailureTime 验证码失效时间   单位:分钟
    	 * @param templateId   模板Id
    	 * @return 返回错误码
    	 */
    	// 指定模板ID单发短信
        public int sendQcloudSms(String[] phoneNumbers,String msgFailureTime,int templateId,HttpServletRequest request){
        	//自定义验证码
        	for (int i = 0; i < codeLength; i++) {
    			code +=(int)(Math.random() * 9+1);
    		}
            try {
            	System.out.println("验证码:"+code);
            	System.out.println("手机号码:"+phoneNumbers[0]);
            	//params 为短信模板中{1}{2}的具体内容
                String[] params = {code,msgFailureTime};
                SmsSingleSender msender = new SmsSingleSender(appid, appkey);
                SmsSingleSenderResult result =  msender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
                System.out.println(result);
                //将生成验证码放入session中
        		request.getSession().setAttribute("createMsgCode", code);
        		//将验证码有效时间放入session中
        		request.getSession().setAttribute("qMsgFailureTime", msgFailureTime);
        		//获取错误码
                int a=result.result;
                if(a==0){
                	System.out.println("腾讯云短信验证码发送成功！");
                }else {
    				System.out.println("腾讯云短信验证码发送失败！");
    			}
                return a;
            } catch (HTTPException e) {
                // HTTP响应码错误
                e.printStackTrace();
                return -1;
            } catch (JSONException e) {
                // json解析错误
                e.printStackTrace();
                return -2;
            } catch (IOException e) {
                // 网络IO错误
                e.printStackTrace();
                return -3;
            }
        }
    
}