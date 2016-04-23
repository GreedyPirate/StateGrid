package cn.sina.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sina.elec.domain.ElecPopedom;
import cn.sina.elec.domain.ElecRole;
import cn.sina.elec.domain.ElecUser;
import cn.sina.elec.service.ElecRoleService;

@SuppressWarnings("serial")
@Controller("elecRoleAction")
@Scope("prototype")
public class ElecRoleAction extends BaseAction<ElecRole>{
	
	@Resource(name=ElecRoleService.ELEC_SERVICE)
	ElecRoleService elecRoleService;
	
	ElecRole elecRole = this.getModel();
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-24
	 * @Description: 跳转到角色管理主页
	 * 		先传递的参数:所有的角色名称,所有的权限名称(分为父子节点)
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String home(){
		List<ElecRole> roleList = elecRoleService.getAllRoles();
		request.setAttribute("roleList",roleList);
		List<ElecPopedom> popedomList = elecRoleService.getAllPopedom();
		request.setAttribute("popedomList",popedomList);
		return "home";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-24
	 * @Description: 跳转到角色权限编辑页面
	 * 			附带操作:根据选择的角色查询出对应的权限(匹配)
	 * 				       根据选择的角色查询出对应的用户(匹配)	
	 * 			匹配是因为页面上要根据选中的角色,让对应的复选框选中
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String edit(){
		String roleID = elecRole.getRoleID();
		//1.根据选择的角色查询出对应的权限(匹配)
		List<ElecPopedom> popedomList = elecRoleService.getPopedomsByID(roleID);
		request.setAttribute("popedomList", popedomList);//只发送该角色对应的权限
		//2.根据选择的角色查询出对应的用户(匹配)
		List<ElecUser> userList = elecRoleService.getUsersByID(roleID);
		request.setAttribute("userList", userList);
		return "edit";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-25
	 * @Description: 保存角色
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String save(){
		elecRoleService.saveRole(elecRole);
		return "save";
	}
}
