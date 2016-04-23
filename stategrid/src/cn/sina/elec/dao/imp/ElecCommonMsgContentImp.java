package cn.sina.elec.dao.imp;

import org.springframework.stereotype.Repository;

import cn.sina.elec.dao.ElecCommonMsgContentDao;
import cn.sina.elec.domain.ElecCommonMsgContent;

/*
 * Bean named 'cn.sina.elec.dao.imp.ElecCommonMsgDaoImp' must be of type [cn.sina.elec.dao.ElecCommonMsgContentDao], 
 * but was actually of type [cn.sina.elec.dao.imp.ElecCommonMsgDaoImp]
 * 
 */
@Repository(ElecCommonMsgContentDao.DAO_IMP)
public class ElecCommonMsgContentImp extends CommonDaoImp<ElecCommonMsgContent> implements ElecCommonMsgContentDao{
	
}
