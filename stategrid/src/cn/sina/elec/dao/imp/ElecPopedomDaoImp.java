package cn.sina.elec.dao.imp;

import org.springframework.stereotype.Repository;

import cn.sina.elec.dao.ElecPopedomDao;
import cn.sina.elec.domain.ElecPopedom;

@Repository(ElecPopedomDao.DAO_IMP)
public class ElecPopedomDaoImp extends CommonDaoImp<ElecPopedom> implements ElecPopedomDao{

}
