package stephen.util;

import javax.servlet.http.HttpServletRequest;

//验证码工具类
public class VerifyCodeUtil {

	/**
	 * 验证验证码的正确性
	 * @param request
	 * @return
	 */
	public static boolean checkVerifyCode(HttpServletRequest request){
		//得到生成的验证码
		String verifyCodeGenerated = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		System.out.println("期望的验证码：" + verifyCodeGenerated);
		//得到输入的验证码
		String verifyCodeInput = HttpServletRequestUtil.getString(request, "verifyCodeInput");
		System.out.println("输入的验证码：" + verifyCodeInput);
		//判断是否相同
		if(verifyCodeGenerated.equals(verifyCodeInput)){
			return true ;
		}
		return false ;
	}
	
}
