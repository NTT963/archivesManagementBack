package org.jit.sose.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jit.sose.entity.Staff;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		//获取请求的URL
		String url=request.getRequestURI();
		//拦截器进行拦截
		System.out.println("拦截器进行拦截");
		if(url.indexOf("/login")>=0){
			System.out.println("登录页面拦截器");
			return true;
		}
		//获取session
		HttpSession session=request.getSession();
		Staff staff=(Staff) session.getAttribute("STAFF_SESSION");
		if(staff!=null){
			return true;
		}
		System.out.println("登录失败拦截器");
		return false;
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mav)
			throws Exception {
	}
}
