package cn.sina.elec.dao.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sina.elec.dao.ElecRolePopedomDao;
import cn.sina.elec.domain.ElecRolePopedom;

@Repository(ElecRolePopedomDao.DAO_IMP)
public class ElecRolePopedomDaoImp extends CommonDaoImp<ElecRolePopedom> implements ElecRolePopedomDao{

	/**
	 * SELECT DISTINCT o.mid FROM elec_role_popedom o WHERE 1=1 AND o.roleID IN ('1','2');
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getPopedomsByRoleID(String roleIDs) {
		String hql = "SELECT DISTINCT o.mid FROM ElecRolePopedom o WHERE o.roleID IN ("+roleIDs+")";
		return this.getHibernateTemplate().find(hql);
	}

}
