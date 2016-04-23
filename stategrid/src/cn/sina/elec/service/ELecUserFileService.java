package cn.sina.elec.service;

import cn.sina.elec.domain.ELecUserFile;

public interface ELecUserFileService {
	public static final String ELEC_SERVICE = "cn.sina.elec.service.impl.ELecUserFileServiceImp";

	ELecUserFile getFielById(String fileID);
}
