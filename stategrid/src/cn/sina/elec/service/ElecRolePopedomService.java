package cn.sina.elec.service;

public interface ElecRolePopedomService {
	public static final String ELEC_SERVICE = "cn.sina.elec.service.impl.ElecRolePopedomServiceImp";

	boolean queryRecord(String roleID, String pid, String mid);
}
