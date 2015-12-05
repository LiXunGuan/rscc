package com.ruishengtech.rscc.crm.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ruishengtech.framework.core.ApplicationHelper;

/**
 * Created by yaoliceng on 14-3-21.
 */
public class MvcInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = Logger.getLogger(MvcInterceptor.class);
	
	private List<String> noFilters = new ArrayList<String>() {
		{
			add("/doLogin");
			add("/ubind");
			add("/assets");
			add("/public");
			add("/event");
			add("/forgetpwd");
			add("/yanzheng");
			add("/resultServlet");
			add("/checkLoginName");
			add("/user/reset_password");
			add("/user/dologin");
			add("/data/groupCall/getGroupCallData");// 三个群呼事件
			add("/data/groupCall/returnGroupCall");
			add("/data/groupCall/stopGroupCall");
			add("/newdata/groupCall/getGroupCallData");// 三个群呼事件
			add("/newdata/groupCall/returnGroupCall");
			add("/newdata/groupCall/stopGroupCall");
			add("/config");
			add("/reload");
			add("/version");
		}
	};

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object o) throws Exception {
		
//		int i = 1;
//		 Map<String, String[]> parmMap = request.getParameterMap();
//		 Iterator<String> iter = parmMap.keySet().iterator();
//		 while (iter.hasNext()) {
//             Object key = iter.next();
//             Object value = parmMap.get(key);
//             logger.info("第" + i + "个param---->{}-{}"+key+"-=-="+value);
//             i = i + 1;
//         }
		
//		logger.info(o+"---"+request.getMethod()+"---");
		
		String uri = request.getRequestURI().substring(
				ApplicationHelper.getContextPath().length());

		for (String noFilter : noFilters) {

			if (uri.equals("/")) {
				return true;
			}

			if (uri.startsWith(noFilter)) {
				return true;
			}
		}

		/**
		 * 用户没有登录
		 */
		if (request.getSession().getAttribute(SessionUtil.CURRENTUSER) == null) {

			PrintWriter out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
			// builder.append("alert(\"页面过期，请重新登录\");");
			builder.append("window.top.location.href=\"");
			builder.append(ApplicationHelper.getContextPath());
			builder.append("/\";</script>");
			out.print(builder.toString());
			out.close();
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse httpServletResponse, Object o, Exception e)
			throws Exception {

		if (e != null) {
			try {
				httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				httpServletResponse.getWriter().print(e.getMessage());
				httpServletResponse.getWriter().flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}
}
