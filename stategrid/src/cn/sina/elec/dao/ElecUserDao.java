package cn.sina.elec.dao;

import java.util.List;
import java.util.Map;

import cn.sina.elec.domain.ElecUser;

public interface ElecUserDao extends CommonDao<ElecUser>{
	public static final String DAO_IMP = "cn.sina.elec.dao.imp.ElecUserDaoImp";

	List<ElecUser> useSQLQuery(String string, Object[] array,
			Map<String, String> orderBy);

	List<Object[]> showUserview(String ddlCode, String ddlCode2);

}
