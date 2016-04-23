package cn.sina.elec.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.sina.elec.dao.ElecExportFieldsDao;
import cn.sina.elec.domain.ElecExportFields;

@Service(ElecExportFieldsService.ELEC_SERVICE)
@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=true)
public class ElecExportFieldsServiceImp implements ElecExportFieldsService{

	@Resource(name=ElecExportFieldsDao.DAO_IMP)
	ElecExportFieldsDao exportFieldsDao;

	@Override
	public ElecExportFields searchFieldsByID(String belongto) {
		ElecExportFields elecExportFields = exportFieldsDao.findById(belongto);
		return elecExportFields;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveFields(ElecExportFields exportFields) {
		exportFieldsDao.update(exportFields);
	}
}
