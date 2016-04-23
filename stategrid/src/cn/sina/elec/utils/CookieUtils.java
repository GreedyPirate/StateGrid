package cn.sina.elec.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sina.elec.web.action.login.Menu;

public class CookieUtils {

	public static void remeberMe(Menu menu, HttpServletRequest request,
			HttpServletResponse response) {
			String name = menu.getName();
			//Cookie中不能存中文,用户名需要编码
			try {
				URLEncoder.encode(name, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String password = menu.getPassword();
			Cookie nameCookie = new Cookie("name", name);
			Cookie pwdCookie = new Cookie("password", password);
			String remeberMe = menu.getRemeberMe();
			/*
			 * 设置Cookie的访问路径,就是说访问哪个页面是要存放这个Cookie的:
			 * 		http://localhost:8080/day20-StateGrid/
			 */
			String url = request.getContextPath();
			nameCookie.setPath(url+"/");
			pwdCookie.setPath(url+"/");
			//用户点了"记住我"就存放Cookie,否则就清除Cookie
			if(remeberMe!=null && "yes".equals(remeberMe)){
				//设置声明周期为10天
				nameCookie.setMaxAge(60*60*24*10);
				pwdCookie.setMaxAge(60*60*24*10);
			}else{
				nameCookie.setMaxAge(0);
				pwdCookie.setMaxAge(0);
			}
			
			//在response中存放Cookie
			response.addCookie(nameCookie);
			response.addCookie(pwdCookie);
	}

	
}
