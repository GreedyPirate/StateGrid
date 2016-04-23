package cn.sina.elec.service.impl;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.sina.elec.dao.ElecPopedomDao;
import cn.sina.elec.dao.ElecRoleDao;
import cn.sina.elec.dao.ElecRolePopedomDao;
import cn.sina.elec.dao.ElecUserDao;
import cn.sina.elec.domain.ElecPopedom;
import cn.sina.elec.domain.ElecRole;
import cn.sina.elec.domain.ElecRolePopedom;
import cn.sina.elec.domain.ElecUser;
import cn.sina.elec.service.ElecRoleService;

@Service(ElecRoleService.ELEC_SERVICE)
@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=true)
public class ElecRoleServiceImp implements ElecRoleService{

	@Resource(name=ElecRoleDao.DAO_IMP)
	ElecRoleDao elecRoleDao;
	@Resource(name=ElecUserDao.DAO_IMP)
	ElecUserDao elecUserDao;
	@Resource(name=ElecPopedomDao.DAO_IMP)
	ElecPopedomDao elecPopedomDao;
	@Resource(name=ElecRolePopedomDao.DAO_IMP)
	ElecRolePopedomDao rolePopedomDao;
	
	
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-24
	 * @Description: 查询出所有的角色名称
	 * 		select * from elec_role o order by o.roleID
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	@Override
	public List<ElecRole> getAllRoles() {
		Map<String,String> orderBy = new LinkedHashMap<String,String>();
		orderBy.put("o.roleID", "asc");
		return elecRoleDao.conditionalQuery("", null, orderBy);
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-24
	 * @Description: 获取所有的权限
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	@Override
	public List<ElecPopedom> getAllPopedom() {
		String condition = " and pid=?";
		Object[] params = {"0"};
		Map<String,String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("mid", "asc");
		//先查出所有的父节点
		List<ElecPopedom> parentNodes = elecPopedomDao.conditionalQuery(condition, params, orderBy);
		if(parentNodes!=null && parentNodes.size()>0){
			for (ElecPopedom elecPopedom : parentNodes) {
				//查出该父节点下所有的子节点
				String pid = elecPopedom.getMid();
				condition = " and pid=?";
				Object[] params2 = {pid}; 
				orderBy = new LinkedHashMap<String, String>();
				orderBy.put("mid", "asc");
				List<ElecPopedom> subNodes = elecPopedomDao.conditionalQuery(condition, params2, orderBy);
				//设置每个父节点的子节点
				elecPopedom.setSubNodes(subNodes);
			}
		}
		return parentNodes;
	}
	
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-24
	 * @Description: 根据选择的角色查询出对应的权限(匹配)
	 * 		思路分析:
	 * 			1.先查出来所有的权限p1
	 * 			2.再查出这个角色对应的权限p2
	 * 			3.如果p2某一个权限和p1中的某一个权限对应,就在p1中设置一个标志位,带到页面显示
	 * 				具体实现步骤:
	 * 					3.1 把查询出来的角色权限p2的mid拼接成一个字符串midStr
	 * 					3.2 然后去对应的权限表里找,只要midStr包含了权限表中的mid,说明角色有该权限,然后将对应的权限设置标志位
	 * @Version: V1.0.0
	 * @Params: roleID
	 * @Return: List<ElecPopedom>
	 */
	@Override
	public List<ElecPopedom> getPopedomsByID(String roleID) {
		//1.先查出来所有的权限p1
		List<ElecPopedom> popedomList = this.getAllPopedom();
		//2.再查出这个角色对应的权限p2
		String condition = " and roleID=?";
		Object[] params = {roleID};
		List<ElecRolePopedom> rolePopedoms = rolePopedomDao.conditionalQuery(condition, params, null);
		
		//3.1 把查询出来的角色权限p2的mid拼接成一个字符串midStr
		String midStr = null;
		if(rolePopedoms!=null && rolePopedoms.size()>0){
			Object[] midArr = new Object[rolePopedoms.size()];
			for (int i = 0; i < rolePopedoms.size(); i++) {
				midArr[i] = rolePopedoms.get(i).getMid();
			}
			midStr = StringUtils.join(midArr, "#");
		}
		
		//3.2 然后去对应的权限表里找,只要midStr包含了权限表中的mid,说明角色有该权限,然后将对应的权限设置标志位
		this.matchPopedom(midStr,popedomList);
		return popedomList;//我们要找的是角色对应的权限,不要乱
	}

	private void matchPopedom(String midStr, List<ElecPopedom> popedomList) {
		//匹配的过程就是给popedomList对应的权限加标志位
		if(popedomList!=null && popedomList.size()>0){
			for (ElecPopedom elecPopedom : popedomList) {
				String popedomMid = elecPopedom.getMid();
				if(midStr.contains(popedomMid)){
					elecPopedom.setRoleHave("1");
				}else{
					elecPopedom.setRoleHave("0");
				}
				//别忘了遍历子节点,虽然我觉得已经获得了对应的权限,但是页面要用
				List<ElecPopedom> subNodes = elecPopedom.getSubNodes();
				if(subNodes!=null && subNodes.size()>0){
					this.matchPopedom(midStr, subNodes);
				}
			}
		}
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-24
	 * @Description: 根据选择的角色查询出对应的用户(匹配)
	 * 		思路:真的是不看页面效果不行啊
	 * 			页面上要先显示所有的用户,然后让角色对应的用户选中
	 * @Version: V1.0.0
	 * @Params: roleID
	 * @Return: List<ElecUser>
	 */
	@Override
	public List<ElecUser> getUsersByID(String roleID) {
		//1.先查询出所有用户
		Map<String,String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("o.onDutyDate", "asc");//按入职时间升序
		List<ElecUser> userList = elecUserDao.conditionalQuery("", null, orderBy);
		//2.查询出角色
		ElecRole elecRole = elecRoleDao.findById(roleID);
		Set<ElecUser> userSet = elecRole.getElecUsers();
		//3.存放角色对应的所有用户ID
		List<String> userIDs = null;
		if(userSet!=null && userSet.size()>0){
			//用String可以,用List也可以
			userIDs = new ArrayList<String>();
			for (ElecUser elecUser : userSet) {
				userIDs.add(elecUser.getUserID());
			}
		}
		//4.匹配
		if(userList!=null && userList.size()>0){
			//遍历所有的用户
			for (ElecUser elecUser : userList) {
				if(userIDs.contains(elecUser.getUserID())){
					//匹配上就设置为1
					elecUser.setRoleUser("1");
				}else{
					elecUser.setRoleUser("2");
				}
			}
		}
		/*
		 * 提供另外一种方案,就是不用把userID放到一个List中,再通过contains的方式判断
		 * 可以直接遍历两个集合userList和userSet,比较 它们的userID,相同就设置为1
		 */
		return userList;
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-25
	 * @Description: 保存角色
	 * 		保存的策略是:先把角色在elec_role_popedom表里原来的信息都删了再保存
	 * @Version: V1.0.0
	 * @Params: elecRole
	 * @Return: 
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveRole(ElecRole elecRole) {
		//获取角色ID
		String roleID = elecRole.getRoleID();
		//获取权限ID数组
		String[] selectoper = elecRole.getSelectoper();
		//获取到用户ID数组
		String selectuser = elecRole.getSelectuser();
		
		//保存角色对应的权限
		this.savePopedom(roleID, selectoper);
		//保存角色对应的用户
		this.saveUser(roleID,selectuser);
	}
	/*
	 * 保存角色对应的权限
	 */
	private void savePopedom(String roleID, String[] selectoper) {
		String condition = " and roleID=?";
		Object[] params = {roleID};
		//查询出角色原理的role_popedom信息
		List<ElecRolePopedom> rolePopedomList = rolePopedomDao.conditionalQuery(condition, params, null);
		//再删除了
		rolePopedomDao.deleteByCollection(rolePopedomList);
		//最后再保存提交的
		if(selectoper!=null && selectoper.length>0){
			for (String ids : selectoper) {
				String[] popedom = ids.split("_");
				ElecRolePopedom elecRolePopedom = new ElecRolePopedom();
				elecRolePopedom.setRoleID(roleID);
				elecRolePopedom.setPid(popedom[0]);
				elecRolePopedom.setMid(popedom[1]);
				rolePopedomDao.save(elecRolePopedom);
			}
		}
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-26
	 * @Description: 保存角色对应的用户
	 * @point: 角色权限是我们自己手动创建的关联关系,对权限的保存(savePopedom)是通过先查询出所有,在删除,再保存的方式
	 * 			角色用户是交给了hibernate管理,那么保存角色对应的用户的操作 和 保存角色权限的操作哪里不一样？
	 *	可以看出: 角色用户的保存是直接通过集合快照的更新完成的,只需要建立新的关联关系即可
	 */
	private void saveUser(String roleID, String selectuser) {
		ElecRole role = elecRoleDao.findById(roleID);
		//1.获取对应的用户集合
		Set<ElecUser> users = role.getElecUsers();
		//2.删除原来的用户
		users.clear();
		//3.保存提交的用户
		if(StringUtils.isNotBlank(selectuser)){
			String[] userIDs = selectuser.split(", ");
			for (String userID : userIDs) {
				ElecUser user = new ElecUser();
				//建立关联关系
				user.setUserID(userID);
				//保存提交的用户
				users.add(user);
			}
		}
		/*
		 * 方案二
		 */
		//也可以自己新建一个Set集合,然后在role.setElecUsers(set);
	}

	/**
	 * 根据用户角色去查询该角色拥有的权限,并拼接成一个字符串
	 * 用用的sql语句:
	 * 		SELECT DISTINCT o.mid FROM elec_role_popedom o WHERE 1=1 AND o.roleID IN ('1','2');
	 */
	@Override
	public String getPopedomsByRole(Hashtable<String, String> roles) {
		//组织查询条件:'1','2'
		StringBuilder condition = new StringBuilder();
		if(roles!=null && roles.size()>0){
			for (Iterator<Entry<String,String>> it = roles.entrySet().iterator();it.hasNext();) {
				String roleId = it.next().getKey();
				condition.append("'").append(roleId).append("',");
			}
			condition.deleteCharAt(condition.length()-1);
		}
		//查询出角色对应的权限列表
		List<Object> popedomList = rolePopedomDao.getPopedomsByRoleID(condition.toString());
		StringBuilder popedoms = new StringBuilder();
		if(popedomList!=null && popedomList.size()>0){
			for (Object object : popedomList) {
				popedoms.append(object.toString()).append("#");
			}
			popedoms.deleteCharAt(popedoms.length()-1);
		}
		return popedoms.toString();
	}
	
	 /**
     * 
     * @Author: 杨杰
     * @CreateDate: 2015-11-27
     * @Description: 加载出当前用户有哪些权限
     * 		popedoms的形式:aa#ab#ac#....
     * 		查询用户权限的hql:isMenu=TRUE代表菜单权限
     * 			SELECT * FROM elec_popedom o WHERE 1=1 and o.isMenu=TRUE AND o.MID IN('aa','ac','ag')
     * @Version: V1.0.0
     * @Params: popedoms
     * @Return: List<ElecPopedom>
     * 
     * @Skill: aa#ab#ac-->'aa','ab','ac'
     * 		aa#ab#ac.replace("#","','")=aa','ab','ac==>'aa','ab','ac'
     */
	@Override
	public List<ElecPopedom> loadPopedoms(String popedoms) {
		String mids = popedoms.replace("#", "','");
		String condition = " and o.isMenu=? AND o.mid IN("+"'"+mids+"'"+")";
		Object[] params = {true};
		Map<String,String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("o.mid", "asc");
		List<ElecPopedom> userPopedoms = elecPopedomDao.conditionalQuery(condition, params, orderBy);
		return userPopedoms;
	}
	
}
