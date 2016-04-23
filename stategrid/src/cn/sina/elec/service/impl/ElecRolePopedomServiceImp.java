package cn.sina.elec.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.sina.elec.dao.ElecRolePopedomDao;
import cn.sina.elec.domain.ElecRolePopedom;
import cn.sina.elec.service.ElecRolePopedomService;

@Service(ElecRolePopedomService.ELEC_SERVICE)
@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=true)
public class ElecRolePopedomServiceImp implements ElecRolePopedomService{
	
	@Resource(name=ElecRolePopedomDao.DAO_IMP)
	ElecRolePopedomDao rolePopedomDao;

	@Override
	public boolean queryRecord(String roleID, String pid, String mid) {
		String condition = " and roleID = ? and pid = ? and mid = ?";
		Object [] params = {roleID,pid,mid};
		List<ElecRolePopedom> popedom = rolePopedomDao.conditionalQuery(condition, params, null);
		if(popedom!=null && popedom.size()>0){
			return true;
		}
		return false;
	}
}
