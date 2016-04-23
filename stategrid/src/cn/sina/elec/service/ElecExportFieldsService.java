package cn.sina.elec.service;

import cn.sina.elec.domain.ElecExportFields;

public interface ElecExportFieldsService {
	public static final String ELEC_SERVICE = "cn.sina.elec.service.ElecExportFieldsServiceImp";

	ElecExportFields searchFieldsByID(String belongto);

	void saveFields(ElecExportFields exportFields);

}
