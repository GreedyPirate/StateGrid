package cn.sina.elec.dao.imp;

import org.springframework.stereotype.Repository;

import cn.sina.elec.dao.ElecRoleDao;
import cn.sina.elec.domain.ElecRole;

@Repository(ElecRoleDao.DAO_IMP)
public class ElecRoleDaoImp extends CommonDaoImp<ElecRole> implements ElecRoleDao{

}
