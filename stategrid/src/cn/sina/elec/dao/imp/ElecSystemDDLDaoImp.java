package cn.sina.elec.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sina.elec.dao.ElecSystemDDLDao;
import cn.sina.elec.domain.ElecSystemDDL;

@Repository(ElecSystemDDLDao.DAO_IMP)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ElecSystemDDLDaoImp extends CommonDaoImp<ElecSystemDDL> implements ElecSystemDDLDao{

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 查询所有的keyword(不重复)
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	@Override
	public List<ElecSystemDDL> queryDictionaryType() {
		/**
		 * 以前的做法还要将keyword封装到对象中,能不能直接查出来List<ElecSystemDDL>？
		 * 1.要有相应的构造方法
		 * public ElecSystemDDL(String keyword) {
				this.keyword = keyword;
			}
		 */
		String hql = "select distinct new cn.sina.elec.domain.ElecSystemDDL(d.keyword) from ElecSystemDDL d";
		//看看返回值是什么？
		List<ElecSystemDDL> typeList = new ArrayList<ElecSystemDDL>();
		typeList = this.getHibernateTemplate().find(hql);
		return typeList;
		//查询出所有不重复的类型字段
	/**	
		String hql = "select distinct keyword from ElecSystemDDL";
	 * //查询出来的是Object类型的list
		List<Object> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			for (Object object : list) {
				ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
				elecSystemDDL.setKeyword(object.toString());
				typeList.add(elecSystemDDL);
			}
		}*/
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 根据keyword和ddlCode来获取对应的ddlName
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	@Override
	public String convertByKeywordAndDdlCode(final String keyword, final String ddlCode) {
		final String hql = "select e.ddlName from ElecSystemDDL e where e.keyword = ? and e.ddlCode = ?";
		List<Object> list = (List<Object>) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter(0, keyword);
				/**
				 * java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Integer
				 * 因为ddlCode是Integer类型的
				 */
				query.setParameter(1, Integer.parseInt(ddlCode));
				query.setCacheable(true);
				return query.list();
			}
		});
		String ddlName = "";
		if(list!=null && list.size() > 0){
			ddlName = list.get(0).toString();
		}
		return ddlName;
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 根据keyword和ddlName来获取对应的ddlCode
	 * 		用于excel的导入
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	@Override
	public String convertByKeywordAndDdlName(final String keyword, final String ddlName) {
		final String hql = "select e.ddlCode from ElecSystemDDL e where e.keyword = ? and e.ddlName = ?";
		List<Object> list = (List<Object>) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter(0, keyword);
				query.setParameter(1, ddlName);
				query.setCacheable(true);
				return query.list();
			}
		});
		String ddlCode = "";
		if(list!=null && list.size() > 0){
			ddlCode = list.get(0).toString();
		}
		return ddlCode;
	}

}
