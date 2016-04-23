package cn.sina.elec.dao;

import java.util.List;

import cn.sina.elec.domain.ElecRolePopedom;

public interface ElecRolePopedomDao extends CommonDao<ElecRolePopedom>{
	public static final String DAO_IMP = "cn.sina.elec.dao.imp.ElecRolePopedomDaoImp";

	List<Object> getPopedomsByRoleID(String string);
}
