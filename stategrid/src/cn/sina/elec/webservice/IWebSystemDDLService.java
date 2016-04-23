package cn.sina.elec.webservice;

import java.util.List;

import cn.sina.elec.domain.ElecSystemDDL;

public interface IWebSystemDDLService {
	public List<ElecSystemDDL> getSystemDDL(String keyword);
}
