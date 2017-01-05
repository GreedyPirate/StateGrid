package cn.sina.elec.utils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.sina.elec.domain.ElecUser;
import cn.sina.elec.service.ElecRolePopedomService;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class ExceptionLogInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation actioninvocation) throws Exception {
				//把自定义错误信息 放置到request中
				HttpServletRequest request = (HttpServletRequest) actioninvocation
								.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
				try {
					//获取请求的action对象
					Object action = actioninvocation.getAction();
					//获取请求的方法的名称
					String methodName = actioninvocation.getProxy().getMethod();
					//获取action中的方法的封装类(action中的方法没有参数)
					Method method = action.getClass().getMethod(methodName, null);
					// Action的返回值   
					String result = null; 
					
					//细颗粒度权限控制,用于判断用户的"角色"是否有访问的方法的权限
					boolean isAllowed = checkLimit(request,method);//request用于获取session中的global_user
					if(true){
						// 运行被拦截的Action,期间如果发生异常会被catch住   
						result = actioninvocation.invoke();
					}else{
						request.setAttribute("errorMsg", "对不起！您没有权限操作此功能！");
						result = "errorMsg";
					}
					
					return result;
				} catch (Exception e) {
					/**  
					 * 处理异常  
					 */
					String errorMsg = "出现错误信息，请查看日志！\n" + DateUtils.formattoString(new Date());
					/*//通过instanceof判断到底是什么异常类型   
					if (e instanceof RuntimeException) {
						//未知的运行时异常   
						RuntimeException re = (RuntimeException) e;
						//re.printStackTrace();
						errorMsg = re.getMessage().trim();
					}*/
					/**  
					 * 发送错误消息到页面  
					 */
					request.setAttribute("errorMsg", errorMsg);

					/**  
					 * log4j记录日志  
					 */
					Log log = LogFactory
							.getLog(actioninvocation.getAction().getClass());
					log.error(errorMsg, e);
					return "errorMsg";
				} 
	}

	private boolean checkLimit(HttpServletRequest request, Method method) {
		if(method==null){
			return false;
		}
		ElecUser user = (ElecUser) request.getSession().getAttribute("global_user");
		if(user==null){
			return false;
		}
		
		//判断方法上是否有注释
		boolean isAnnotated = method.isAnnotationPresent(PrevilegeAnnotation.class);
		//如果访问的方法上没有注释
		if(!isAnnotated){
			return false;
		}
		//获取注解
		PrevilegeAnnotation limit = method.getAnnotation(PrevilegeAnnotation.class);
		String pid = limit.pid();
		String mid = limit.mid();
		
		//要记住一个用户是有多个角色的,只要有一个角色有方法的权限,就可以访问
		Hashtable<String, String> roles = (Hashtable<String, String>) request.
								getSession().getAttribute("global_role");
		//获取WebApplicationContext
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(
									request.getSession().getServletContext());
		//获取Service
		ElecRolePopedomService service = (ElecRolePopedomService) context.getBean
									(ElecRolePopedomService.ELEC_SERVICE);
		boolean flag = false;
		if(roles!=null && roles.size()>0){
			//遍历角色
			for(Iterator<Entry<String, String>> it = roles.entrySet().iterator(); it.hasNext();){
				String roleID = it.next().getKey();
				flag = service.queryRecord(roleID,pid,mid);
				if(flag){
					break;//找到了
				}
			}
		}
		return false;
	}

}
