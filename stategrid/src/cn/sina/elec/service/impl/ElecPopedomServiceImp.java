package cn.sina.elec.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.sina.elec.dao.ElecPopedomDao;
import cn.sina.elec.service.ElecPopedomService;

@Service(ElecPopedomService.ELEC_SERVICE)
@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=true)
public class ElecPopedomServiceImp implements ElecPopedomService{

	@Resource(name=ElecPopedomDao.DAO_IMP)
	ElecPopedomDao popedomDao;
}
