package cn.sina.elec.service;

import java.util.ArrayList;
import java.util.List;

import cn.sina.elec.domain.ElecSystemDDL;
import cn.sina.elec.domain.ElecUser;

public interface ElecUserService {
	public static final String ELEC_SERVICE = "cn.sina.elec.service.impl.ElecUserServiceImp";

	List<ElecUser> queryUser(ElecUser elecUser);

	String checkUser(String logonName);

	void saveUser(ElecUser elecUser);

	ElecUser searchUserByID(String userID);

	List<ElecSystemDDL> getDdlList(String keyword);

	void deleteUser(ElecUser elecUser);

	ElecUser getUserByLogonName(String name);

	ArrayList<String> getExcelFieldName();

	ArrayList<ArrayList<String>> getExcelData(ElecUser elecUser);

	void imporExcel(List<ElecUser> userList);

	List<Object[]> showUserview(String string, String string2);

}
