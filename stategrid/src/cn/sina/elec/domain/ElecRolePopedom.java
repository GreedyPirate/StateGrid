package cn.sina.elec.domain;

import java.io.Serializable;
/**
 * #角色权限信息表
	CREATE TABLE Elec_Role_Popedom(
		roleID VARCHAR(32)  NOT NULL,      			#权限角色ID
		mid VARCHAR(32)   NOT NULL,          		#权限Code（主键ID）
		pid VARCHAR(32)  NOT NULL,     				#父级权限code，如果已经是根节点则为0
		PRIMARY KEY(roleID,MID,pid)                 #设置联合主键
	)
 * @author yj
 * @date 2015-11-24 下午3:41:28
 */
@SuppressWarnings("serial")
public class ElecRolePopedom implements Serializable{
	private String roleID;
	private String mid;
	private String pid;
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
}
