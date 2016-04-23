package cn.sina.elec.dao;

import java.util.List;

import cn.sina.elec.domain.ElecSystemDDL;

public interface ElecSystemDDLDao extends CommonDao<ElecSystemDDL>{
	public static final String DAO_IMP = "cn.sina.elec.dao.imp.ElecSystemDDLDaoImp";

	List<ElecSystemDDL> queryDictionaryType();

	String convertByKeywordAndDdlCode(String keyword, String ddlCode);

	String convertByKeywordAndDdlName(String keyword, String ddlName);


}
