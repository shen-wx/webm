package com.plat.webm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.plat.webm.news.controller.UserController;
import com.plat.webm.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 登录验证token拦截器
 * 
 * @Description:TODO(
 * @author lilinpc
 * @date: 2019年7月23日 下午3:53:57
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private IUserService userService;

	/*
	 * 视图渲染之后的操作
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

	}

	/*
	 * 处理请求完成后视图渲染之前的处理操作
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

	}

	/*
	 * 进入controller层之前拦截请求
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Max-Age", "3600");

		//在这里打断点或打印出来url，访问swagger页面，看看请求的是什么路径，
		// 然后将路径放到ResourcesConfig类下面的 addInterceptors 里面
		String url = request.getRequestURI();
		System.out.println("-----------------"+url);

		UserController.checkToken(request,userService);

		return true;
	}

}