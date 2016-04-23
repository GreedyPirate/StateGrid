package cn.sina.elec.service;

import java.util.List;

import cn.sina.elec.domain.ElecFileUpload;

public interface ElecFileUploadService {
	public static final String ELEC_SERVICE = "cn.sina.elec.service.impl.ElecFileUploadServiceImp";

	void saveFiles(ElecFileUpload elecFileUpload);

	List<ElecFileUpload> getUploadedByID(ElecFileUpload elecFileUpload);

	ElecFileUpload getFielById(Integer seqID);

	List<ElecFileUpload> retrieval(ElecFileUpload elecFileUpload);
}
