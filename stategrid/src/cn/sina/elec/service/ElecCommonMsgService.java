package cn.sina.elec.service;

import cn.sina.elec.domain.ElecCommonMsg;

public interface ElecCommonMsgService {
	public static final String ELEC_SERVICE = "cn.sina.elec.service.impl.ElecCommonMsgServiceImp";

	ElecCommonMsg getCommonMsg();

	void saveCommonMsg(ElecCommonMsg elecCommonMsg);
}
