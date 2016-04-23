package cn.sina.elec.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.sina.elec.dao.ELecUserFileDao;
import cn.sina.elec.domain.ELecUserFile;
import cn.sina.elec.service.ELecUserFileService;

@Service(ELecUserFileService.ELEC_SERVICE)
@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=true)
public class ELecUserFileServiceImp implements ELecUserFileService{
	@Resource(name=ELecUserFileDao.DAO_IMP)
	ELecUserFileDao eLecUserFileDao;

	@Override
	public ELecUserFile getFielById(String fileID) {
		return eLecUserFileDao.findById(fileID);
	}
}
