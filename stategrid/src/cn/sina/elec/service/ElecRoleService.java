package cn.sina.elec.service;

import java.util.Hashtable;
import java.util.List;

import cn.sina.elec.domain.ElecPopedom;
import cn.sina.elec.domain.ElecRole;
import cn.sina.elec.domain.ElecUser;


public interface ElecRoleService {
	public static final String ELEC_SERVICE = "cn.sina.elec.service.impl.ElecRoleServiceImp";

	List<ElecRole> getAllRoles();

	List<ElecPopedom> getAllPopedom();

	List<ElecPopedom> getPopedomsByID(String roleID);

	List<ElecUser> getUsersByID(String roleID);

	void saveRole(ElecRole elecRole);

	String getPopedomsByRole(Hashtable<String, String> roles);

	List<ElecPopedom> loadPopedoms(String popedoms);
}
