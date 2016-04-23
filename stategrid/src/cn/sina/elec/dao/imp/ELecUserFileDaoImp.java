package cn.sina.elec.dao.imp;

import org.springframework.stereotype.Repository;

import cn.sina.elec.dao.ELecUserFileDao;
import cn.sina.elec.domain.ELecUserFile;

@Repository(ELecUserFileDao.DAO_IMP)
public class ELecUserFileDaoImp extends CommonDaoImp<ELecUserFile> implements ELecUserFileDao{

}
