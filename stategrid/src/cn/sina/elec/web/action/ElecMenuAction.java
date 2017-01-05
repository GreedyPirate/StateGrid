package cn.sina.elec.web.action;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sina.elec.domain.ElecCommonMsg;
import cn.sina.elec.domain.ElecPopedom;
import cn.sina.elec.domain.ElecRole;
import cn.sina.elec.domain.ElecUser;
import cn.sina.elec.service.ElecCommonMsgService;
import cn.sina.elec.service.ElecRoleService;
import cn.sina.elec.service.ElecUserService;
import cn.sina.elec.utils.CookieUtils;
import cn.sina.elec.utils.MD5keyBean;
import cn.sina.elec.utils.StackUtils;
import cn.sina.elec.web.action.login.Menu;
/**
 * Created by yj on 2015/11/15.
 */
@Controller("elecMenuAction")
@Scope("prototype")
public class ElecMenuAction extends BaseAction<Menu> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Menu menu = this.getModel();
	
	@Resource(name=ElecCommonMsgService.ELEC_SERVICE)
	ElecCommonMsgService commonMsgService;

	@Resource(name=ElecUserService.ELEC_SERVICE)
	ElecUserService elecUserService;
	
	@Resource(name=ElecRoleService.ELEC_SERVICE)
	ElecRoleService roleService;
    /**
     * @author: 杨杰
     * @date: 2015/11/16 18:13
     * @methodParameters:
     * @description: 校验并跳转到主页面
     * 		校验分为三部分:
     * 			1.用户名密码的校验(包括验证码)
     * 			2.用户是否有角色的校验,如果分配了角色，将角色的信息存放起来
     * 			3.用户角色是否有权限的校验,如果分配了权限，将权限的信息存放起来（aa）
     * 			4."记住我"功能的实现
     * @version: V1.0
     * @return: String
     */
    public String menuHome() {
    	//------------1.用户名密码的校验  begin -------------
    	//获取到登录名和密码
    	String name = menu.getName();
    	String password = menu.getPassword();
    	//根据登录名查询出用户
    	ElecUser elecUser = elecUserService.getUserByLogonName(name);
    	if(elecUser==null){
    		this.addActionError("用户名不存在");
    		return "loginError";
    	}
    	if(StringUtils.isBlank(password)){
    		this.addActionError("密码不能为空");
    		return "loginError";
    	}else{
    		//进行密码校验(md5加密)
    		MD5keyBean bean = new MD5keyBean();
    		//password = bean.getkeyBeanofStr(password); 测试方便一点
    		if(!password.equals(elecUser.getLogonPwd())){
    			this.addActionError("密码输入错误");
    			return "loginError";
    		}
    	}
    	//验证码
    	//获取用户输入的验证码
		String inputCode = menu.getCheckNumber();
		//获取图片生产的验证码
		String imageCode = (String) request.getSession().getAttribute("CHECK_NUMBER_KEY");
		
		boolean code = inputCode.equalsIgnoreCase(imageCode);
    	if(!code){
    		this.addActionError("验证码输入错误");
			return "loginError";
    	}
    	HttpSession session =this.request.getSession();
    	session.setAttribute("global_user", elecUser);
    	//------------------end-------------------------
    	
    	//---------------2.用户是否有角色的校验  begin -------------
    	Hashtable<String, String> roles = new Hashtable<String, String>();
    	Set<ElecRole> elecRoles = elecUser.getElecRoles();
    	/*ElecRole e = new ElecRole();
    	e.setRoleID("1");
    	e.setRoleName("系统管理员");
    	elecRoles.add(e);*/
    	if(elecRoles==null || elecRoles.size()<1){
    		this.addActionError("该用户没有角色信息,请练习管理员");
			return "loginError";
    	}else{
    		for (ElecRole elecRole : elecRoles) {
    			roles.put(elecRole.getRoleID(), elecRole.getRoleName());
			}
    	}
    	session.setAttribute("global_role", roles);
    	//------------------end-------------------------
    	
    	//------------ begin  3.用户角色是否有权限的校验,如果分配了权限，将权限的信息存放起来（aa）-------
    	//根据用户角色去查询该角色拥有的权限,并拼接成一个字符串
    	String popedoms = roleService.getPopedomsByRole(roles);
    	if(StringUtils.isBlank(popedoms)){
    		this.addActionError("该用户角色没有初始化权限,请练习管理员");
			return "loginError";
    	}
    	session.setAttribute("global_popedom", popedoms);
    	//------------------end-------------------------
    	
    	//-------------4. 记住用户名密码功能 begin----------------
    	CookieUtils.remeberMe(menu,request,response);
    	//------------------end-------------------------
    	return "menuHome";
    }
    
    /**
     * 
     * @Author: 杨杰
     * @CreateDate: 2015-11-27
     * @Description: 根据用户的权限,动态的加载左侧菜单(zTree),已json的形式传值到页面
     * @Version: V1.0.0
     * @Params: 
     * @Return: String
     */
    public String loadMenu(){
    	String popedoms = (String) request.getSession().getAttribute("global_popedom");
    	//加载出当前用户有哪些权限
    	List<ElecPopedom> userPopedoms = roleService.loadPopedoms(popedoms);
    	//以json的形式返回,放到栈顶就行
    	StackUtils.pushOnTop(userPopedoms);
    	
    	//根据角色来跳转到不同的用户管理首页:系统管理员跳转首页,非系统管理员只能看到自己的编辑页面
    	@SuppressWarnings("unchecked")
		Hashtable<String, String> roles = (Hashtable<String, String>) request.getSession().getAttribute("global_role");
    	ElecUser user = (ElecUser) request.getSession().getAttribute("global_user");
    	//如果不是系统管理员(1为系统管理员)
    	if(!roles.containsKey("1")){//containsKey...
    		if(roles!=null && roles.size()>0){
    			for (ElecPopedom elecPopedom : userPopedoms) {
					String mid = elecPopedom.getMid();
					String pid = elecPopedom.getPid();
					//改变用户管理的URL
					if("an".equals(mid) && "am".equals(pid)){
						//不是系统管理员就跳转到用户编辑页面,elecUserAction_edit需要UserID,从session中获取User
						elecPopedom.setUrl("../system/elecUserAction_edit.do?userID="
											+user.getUserID()+"&roleFlag=1");
					}
				}
    		}
    	}
    	return "loadMenu";
    }

    /**
     * @author: 杨杰
     * @date: 2015/11/16 18:20
     * @methodParameters:
     * @description: 显示title.jsp
     * @version: V1.0
     * @return:
     */
    public String title() {
        return "title";
    }

    /**
     * @author: 杨杰
     * @date: 2015/11/16 18:38
     * @methodParameters:
     * @description: 显示left.jsp
     * @version: V1.0
     * @return:
     */
    public String left() {
        return "left";
    }

    /**
     * @author: 杨杰
     * @date: 2015/11/16 18:38
     * @methodParameters:
     * @description: 显示change.jsp
     * @version: V1.0
     * @return:
     */
    public String change() {
        return "change";
    }

    /**
     * @author: 杨杰
     * @date: 2015/11/16 18:39
     * @methodParameters:
     * @description: 显示loading.jsp
     * @version: V1.0
     * @return:
     */
    public String loading() {
    	//查询运行监控的数据
		//1：查询数据库运行监控表的数据，返回惟一ElecCommonMsg
		ElecCommonMsg commonMsg = commonMsgService.getCommonMsg();
		//2：将ElecCommonMsg对象压入栈顶，支持表单回显
		StackUtils.pushOnTop(commonMsg);
        return "loading";
    }

    /**
     * @author: 杨杰
     * @Date: 2015/11/17 12:41
     * @methodParameters:
     * @description: 重新登录
     * @version: V1.0
     * @return:
     */
    public String logout(){
        request.getSession().invalidate();
        return "logout";
    }
    /**
     * @author: 杨杰
     * @Date: 2015/11/16 22:11
     * @methodParameters:
     * @description: 显示loading页面的两个IFrame,用于在主页显示信息
     * @version: V1.0
     * @return:
     */
    public String alermStation(){
    	//查询出监控信息
    	ElecCommonMsg elecCommonMsg = commonMsgService.getCommonMsg();
    	StackUtils.pushOnTop(elecCommonMsg);
        return "alermStation";
    }
    /**
     * @author: 杨杰
     * @Date: 2015/11/16 22:12
     * @methodParameters:
     * @description: 显示loading页面的两个IFrame,用于在主页显示信息
     * @version: V1.0
     * @return:
     */
    public String alermDevice(){
    	//查询出监控信息
    	ElecCommonMsg elecCommonMsg = commonMsgService.getCommonMsg();
    	StackUtils.pushOnTop(elecCommonMsg);
        return "alermDevice";
    }
}
