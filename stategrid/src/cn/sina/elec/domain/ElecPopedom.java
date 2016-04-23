package cn.sina.elec.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * #权限信息表
	CREATE TABLE Elec_Popedom(
		MID VARCHAR(32)  NOT NULL,      			#权限Code（主键ID）
		pid VARCHAR(32)   NOT NULL,          		#父级权限code，如果已经是根节点则为0
		NAME VARCHAR(500)  NULL,     				#权限名称
		url VARCHAR(5000)  NULL,     				#权限在系统中执行访问地址的URL
		icon VARCHAR(5000)   NULL,           		#如果是菜单，则为显示图片的URL
		target VARCHAR(500)  NULL,   				#如果是菜单，链接执行的Frame区域名称
		isParent BOOLEAN NULL,       				#是否是父节点，父节点为true，子节点为false
		isMenu BOOLEAN NULL,          				#是否是系统菜单结构
		PRIMARY KEY(MID,pid)                        #设置联合主键
	)
 * @author yj
 * @date 2015-11-24 下午3:41:43
 */
@SuppressWarnings("serial")
public class ElecPopedom implements Serializable{
	private String mid;
	private String pid;
	private String name;
	private String url;
	private String icon;
	private String target;
	private boolean isParent;
	private boolean isMenu;
	
	
	
	
	//--------------------------- 非持久化字段----------------------------
	 
	/**
	 * 该父节点下的所有子节点
	 * @see cn.sina.elec.service.impl.ElecRoleServiceImp.getAllPopedom()
	 */
	private List<ElecPopedom> subNodes = new ArrayList<ElecPopedom>();
	
	/**
	 * 角色是否拥有该权限,有为1,没有为0
	 * @see cn.sina.elec.service.impl.ElecRoleServiceImp.getUsersByID(String)
	 */
	private String roleHave; 
	
	public String getRoleHave() {
		return roleHave;
	}
	public void setRoleHave(String roleHave) {
		this.roleHave = roleHave;
	}
	public List<ElecPopedom> getSubNodes() {
		return subNodes;
	}
	public void setSubNodes(List<ElecPopedom> subNodes) {
		this.subNodes = subNodes;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	public boolean getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(boolean isMenu) {
		this.isMenu = isMenu;
	}
	
	
}
