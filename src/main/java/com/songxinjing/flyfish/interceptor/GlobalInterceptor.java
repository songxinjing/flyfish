package com.songxinjing.flyfish.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.service.UserService;

/**
 * 全局拦截器
 * 
 * @author songxinjing
 * 
 */
@Component
public class GlobalInterceptor implements HandlerInterceptor {

	@Autowired
	UserService userService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelView) throws Exception {
		
		User loginUser = (User)request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		
		if(loginUser == null){
			loginUser = userService.find("0001");
			request.getSession().setAttribute(Constant.SESSION_LOGIN_USER, loginUser);
		}
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}
}
