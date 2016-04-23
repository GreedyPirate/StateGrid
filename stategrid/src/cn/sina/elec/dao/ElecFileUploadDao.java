package cn.sina.elec.dao;

import java.util.List;
import java.util.Map;

import cn.sina.elec.domain.ElecFileUpload;

public interface ElecFileUploadDao extends CommonDao<ElecFileUpload>{
	public static final String DAO_IMP = "cn.sina.elec.dao.imp.ElecFileUploadDaoImp";

	List<ElecFileUpload> sqlQuery(String condition, Object[] params, Map<String, String> orderBy);
}
