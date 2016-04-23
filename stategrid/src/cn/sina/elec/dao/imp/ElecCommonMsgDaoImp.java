package cn.sina.elec.dao.imp;

import org.springframework.stereotype.Repository;

import cn.sina.elec.dao.ElecCommonMsgDao;
import cn.sina.elec.domain.ElecCommonMsg;

@Repository(ElecCommonMsgDao.DAO_IMP)
public class ElecCommonMsgDaoImp extends CommonDaoImp<ElecCommonMsg> implements ElecCommonMsgDao{

}
