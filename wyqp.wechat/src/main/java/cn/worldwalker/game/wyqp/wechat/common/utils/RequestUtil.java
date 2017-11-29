package cn.worldwalker.game.wyqp.wechat.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.worldwalker.game.wyqp.wechat.common.utils.redis.JedisTemplate;
import cn.worldwalker.game.wyqp.wechat.domain.UserSession;

@Component
public class RequestUtil {
	
	private static JedisTemplate jedisTemplate;
	
	private static final String LOGIN_COOKIE_NAME = "logincookie";
	
	private static final Integer MAX_TIME = 60*60;
	
	private static ThreadLocal<HttpServletRequest> requsetThreadLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();
	
	private static ThreadLocal<UserSession> sessionThreadLocal = new ThreadLocal<UserSession>();
	
	public static void setRequset(HttpServletRequest request){
		requsetThreadLocal.set(request);
	}
	
	public static void setResponse(HttpServletResponse response){
		responseThreadLocal.set(response);
	}
	
	
	public static HttpServletRequest getHttpServletRequest(){
		return requsetThreadLocal.get();
	}
	
	public static HttpServletResponse getHttpServletResponse(){
		return responseThreadLocal.get();
	}
	
	public static void setUserSession(UserSession userSession){
		sessionThreadLocal.set(userSession);
	}
	public static UserSession getUserSession(){
		return sessionThreadLocal.get();
	}
	
	public static Integer getProxyId(){
		UserSession us = sessionThreadLocal.get();
		if (us == null) {
			return null;
		}
		return us.getProxyId();
	}
	
	/**
	 * 获取请求id，每个请求都会有一个id与之对应，再跨多个系统的复杂应用时，可以很方便的定位问题
	 * @return
	 */
	public static String getRequestId(){
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletRequest request = getHttpServletRequest();
		String requestId = String.valueOf(request.getAttribute("requestId"));
		return requestId;
	}
	
	/**
	 * 设置用户登录session
	 * @param frontUser
	 */
	public static void setUserSession(String token, UserSession userSession){
		/**session 方式实现**/
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		HttpServletRequest request = getHttpServletRequest();
//		HttpSession session = request.getSession();
//		String userSessionStr = JsonUtil.obj2json(userSession);
//		session.setAttribute("userSession", userSessionStr);
		
		/**redis cookie 方式实现**/
		jedisTemplate.setex(token, JsonUtil.toJson(userSession), MAX_TIME);
		addCookie(LOGIN_COOKIE_NAME, token, MAX_TIME);
	}
	
	/**
	 * 获取用户session
	 * @return
	 */
//	public static UserSession getUserSession(){
//		/**session 方式实现**/
////		HttpServletRequest request = getHttpServletRequest();
////		HttpSession session = request.getSession();
////		UserSession userSession = null;
////		Object sessionObject = session.getAttribute("userSession");
////		if (null != sessionObject) {
////			userSession = JsonUtil.json2pojo(String.valueOf(sessionObject), UserSession.class);
////		}
//		
//		String token = getCookieValue(LOGIN_COOKIE_NAME);
//		if (StringUtils.isEmpty(token)) {
//			return null;
//		}
//		String sessionStr = jedisTemplate.get(token);
//		if (StringUtils.isEmpty(sessionStr)) {
//			return null;
//		}
//		UserSession userSession = JsonUtil.json2pojo(sessionStr, UserSession.class);
//		return userSession;
//	}
	
	/**
	 * 判断用户session是否存在
	 * @return
	 */
	public static boolean isUserSessionExist(){
		/**session 方式实现*/
//		HttpServletRequest request = getHttpServletRequest();
//		HttpSession session = request.getSession();
//		Object sessionObject = session.getAttribute("userSession");
//		if (null != sessionObject) {
//			return true;
//		}
		String token = getCookieValue(LOGIN_COOKIE_NAME);
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		String sessionStr = jedisTemplate.get(token);
		if (StringUtils.isEmpty(sessionStr)) {
			return false;
		}
		/**redis token延期*/
		jedisTemplate.expire(token, MAX_TIME);
		
		/**session存入当前线程*/
		UserSession userSession = JsonUtil.toObject(sessionStr, UserSession.class);
		setUserSession(userSession);
		return true;
	}
	/**
	 * 添加cookie
	 * @param cookieName
	 * @param cookieValue
	 * @param maxAge
	 */
	public static void addCookie(String cookieName, String cookieValue, int maxAge){
		HttpServletResponse response = getHttpServletResponse();
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	/**
	 * 获取cookie值
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(String cookieName){
		HttpServletRequest request = getHttpServletRequest();
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for(Cookie cookie : cookies){
			if (cookieName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	@Autowired(required = true)
	public void setJedisTemplate(JedisTemplate jedisTemplate) {
		RequestUtil.jedisTemplate = jedisTemplate;
	}
	
}
