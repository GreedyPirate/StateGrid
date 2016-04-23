package cn.sina.elec.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * #角色信息表
	CREATE TABLE Elec_Role(
		roleID VARCHAR(32)  NOT NULL primary key,   #角色ID
		roleName VARCHAR(500)                    	#角色名称
	)
 * @author yj
 * @date 2015-11-24 下午3:48:10
 */
@SuppressWarnings("serial")
public class ElecRole implements Serializable{
	private String roleID;
	private String roleName;
	private Set<ElecUser> elecUsers = new HashSet<ElecUser>();
	
	//-----------------非持久化字段------------------------
	
	/**
	 * 用于保存角色对应的权限的字段
	 * @see cn.sina.elec.service.impl.ElecRoleServiceImp.saveRole(ElecRole)
	 */
	private String selectoper[];
	
	/**
	 * 用于保存角色对应的用户的字段
	 * @see cn.sina.elec.service.impl.ElecRoleServiceImp.saveRole(ElecRole)
	 */
	private String selectuser;
	
	
	
	public String getSelectuser() {
		return selectuser;
	}
	public void setSelectuser(String selectuser) {
		this.selectuser = selectuser;
	}
	public String[] getSelectoper() {
		return selectoper;
	}
	public void setSelectoper(String[] selectoper) {
		this.selectoper = selectoper;
	}
	public Set<ElecUser> getElecUsers() {
		return elecUsers;
	}
	public void setElecUsers(Set<ElecUser> elecUsers) {
		this.elecUsers = elecUsers;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
