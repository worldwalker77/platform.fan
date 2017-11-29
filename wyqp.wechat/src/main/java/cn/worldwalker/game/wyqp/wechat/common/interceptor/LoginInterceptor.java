package cn.worldwalker.game.wyqp.wechat.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.worldwalker.game.wyqp.wechat.common.utils.RequestUtil;
import cn.worldwalker.game.wyqp.wechat.common.utils.redis.JedisTemplate;

public class LoginInterceptor  extends HandlerInterceptorAdapter {
	
	@Autowired
	private JedisTemplate jedisTemplate;
	
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
		if (!RequestUtil.isUserSessionExist()) {
			String uri = httpServletRequest.getRequestURI();
			String queryString = httpServletRequest.getQueryString();
			httpServletResponse.sendRedirect("/login/index?redirectUrl=" + uri + "?" + queryString);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handle, Exception e) {
		
	}
}
