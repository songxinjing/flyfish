package com.songxinjing.flyfish.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.domain.User;

/**
 * 权限判断filter
 * 
 * @author songxinjing
 */
public class LoginFilter implements Filter {

	protected static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	/**
	 * 设置不需要被filter拦截的urls（url之间用逗号分隔）<br>
	 * 例：/version.jsp,/logout,/sso_logout<br>
	 * 配置：web.xml | SessionFilter | init-param | excludeUrl
	 */
	protected final static String EXCLUDE_URL = "excludeUrl";

	/**
	 * <b>excludeUrl</b> 要过滤的url路径
	 */
	private String excludeUrl = null;

	public void init(FilterConfig config) throws ServletException {
		String excludeUrl = config.getInitParameter(LoginFilter.EXCLUDE_URL);
		this.excludeUrl = excludeUrl;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
			throws IOException, ServletException {

		// make sure we've got an HTTP request
		if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
			throw new ServletException("SessionFilter protects only HTTP resources");
		}

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		// 如果当前请求的context在配置的excludeUrl的context名单内，则放过，不拦截
		if (isExcludeUrl(req, excludeUrl)) {
			fc.doFilter(request, response);
			return;
		}

		String reqUrl = req.getRequestURI();
		logger.info("访问URL：" + reqUrl);

		// 获取用户登录信息
		User user = (User) session.getAttribute(Constant.SESSION_LOGIN_USER);

		// 用户访问权限判断
		if (user == null) {
			logger.info("用户未登录！！！");
			reqUrl = reqUrl.replaceFirst(req.getContextPath(), "");
			if ("/".equals(reqUrl)) {
				reqUrl = "/index.html";
			}
			resp.sendRedirect(req.getContextPath() + "/login.html?reqUrl=" + reqUrl);
			return;
		}

		fc.doFilter(request, response);
	}

	/**
	 * 判断当前request url是否和excludUrl中设置的url匹配
	 * 
	 * @return 匹配 -> true<br>
	 *         不匹配 -> false
	 */
	private boolean isExcludeUrl(HttpServletRequest request, String excludeUrl) throws ServletException {
		if (excludeUrl != null && !excludeUrl.trim().equalsIgnoreCase("")) {
			String contentPath = request.getContextPath();
			String url = request.getRequestURI();
			String[] urlArr = excludeUrl.split(",");
			for (int i = 0; i < urlArr.length; i++) {
				String excUrl = contentPath + urlArr[i].toString();
				if (excUrl.indexOf("*") > 0) {
					int a = excUrl.indexOf("*");
					excUrl = excUrl.substring(0, a);
				}
				if (url.startsWith(excUrl)) {
					return true;
				}
			}
		}
		return false;
	}

	public void destroy() {
		// do nothing
	}

}
