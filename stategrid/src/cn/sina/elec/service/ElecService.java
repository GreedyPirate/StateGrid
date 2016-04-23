package cn.sina.elec.service;

import cn.sina.elec.domain.Elec;

import java.util.List;

public interface ElecService {
	public static final String ELEC_SERVICE = "cn.sina.elec.service.impl.ElecServiceImp";

	public void saveElec(Elec elec);

	List<Elec> conditionalQuery(Elec elec);
}
