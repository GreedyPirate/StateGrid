package cn.sina.elec.service;

import java.util.List;

import cn.sina.elec.domain.ElecSystemDDL;

public interface ElecSystemDDLService {
	public static final String ELEC_SERVICE = "cn.sina.elec.service.impl.ElecSystemDDLServiceImp";

	List<ElecSystemDDL> queryDictionaryType();

	List<ElecSystemDDL> getTypeValue(String keyword);

	void saveDictionary(ElecSystemDDL systemDDL);

	String getDdlName(String keyword, String ddlCode);

	String getDdlCode(String keyword, String ddlName);

}
