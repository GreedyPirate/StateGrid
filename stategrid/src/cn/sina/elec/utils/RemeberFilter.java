package cn.sina.elec.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sina.elec.domain.ElecUser;
/**
 * 此过滤器还用于过滤没用session的用户,什么意思呢？
 * 	比如用户已经登录了,并且进行了一系列的活动,但是这时服务器关闭了 ,所有session被销毁,然后服务器启动
 * 这时,用户没有session,但是依然能进行正常的访问,这时我们要过滤的,在init方法中,我们还有让一些操作不需要session
 * 比如登录,登录时本来就没有session
 */
public class RemeberFilter implements Filter {
	//用于存放没有session也要放行的访问
	private List<String> pass = new ArrayList<String>();

	
	public void init(FilterConfig fConfig) throws ServletException {
		/*
		 * 以下都是不需要session的操作
		 */
		pass.add("/index.jsp");//首页,WebRoot下的
		pass.add("/image.jsp");//验证码
		pass.add("/system/elecMenuAction_menuHome.do");//加载用户主页
		pass.add("/error.jsp");//跳转错误页面
		pass.add("/system/elecMenuAction_logout.do");//重新登录
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		//获取访问的servlet的路径,也就是项目名后面的路径
		String path = servletRequest.getServletPath();
		//在跳转到index.jsp页面之前，先获取Cookie，传值的方式给index.jsp
		this.remeberMe(servletRequest,path);
		
		/*
		 * 粗粒度权限控制
		 */
		//如果访问路径是需要被放行的路径
		if(pass.contains(path)){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		//如果用户用session,放行
		ElecUser elecUser = (ElecUser) servletRequest.getSession().getAttribute("global_user");
		if(elecUser!=null){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		//剩下的都是不符合条件的,都重定向到登录首页
		servletResponse.sendRedirect(servletRequest.getContextPath()+"/error.jsp");
	}

	private void remeberMe(HttpServletRequest request, String path) {
		//Cookie只转发到day20-StateGrid/index.jsp
		if(path!=null && path.equals("/index.jsp")){
			Cookie[] cookies = request.getCookies();
			String name = null;
			String password = null;
			String checked = null;
			if(cookies!=null){
				for (Cookie cookie : cookies) {
					String cookiedName = cookie.getName();
					if("name".equals(cookiedName)){
						name = cookie.getValue();
						try {
							name = URLDecoder.decode(name, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						checked = "checked";//让复选框选中,比如打开登录页面时,记住我是默认选中的
					}else if("password".equals(cookiedName)){
						password = cookie.getValue();
					}
				}
			}
			request.setAttribute("name", name);
			request.setAttribute("password", password);
			request.setAttribute("checked", checked);
		}
	}


	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

}
