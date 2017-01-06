package cn.sina.elec.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sina.elec.dao.ElecUserDao;
import cn.sina.elec.domain.ElecUser;
import cn.sina.elec.utils.page.PageInfo;

@Repository(ElecUserDao.DAO_IMP)
public class ElecUserDaoImp extends CommonDaoImp<ElecUser> implements ElecUserDao{

	/**
	 * 直接用sql的左外连接查询ddlName,联合查询没必要封装到底层
	 * SELECT u.`UserID`,u.`UserName`,u.`LogonName`,s.`ddlName`,u.`ContactTel`,u.`OnDutyDate` 
	 * FROM elec_user u 
	 * LEFT JOIN elec_systemddl s 
	 * ON  u.`SexID` = s.`seqID`
	 */
	/**使用sql语句的联合查询，查询用户表，关联数据字典表*/
	public List<ElecUser> useSQLQuery(String condition,
			final Object[] params, Map<String, String> orderby) {
		//sql语句
		String sql = "SELECT o.userID,o.logonName,o.userName,a.ddlName,o.contactTel,o.onDutyDate,b.ddlName " +
					 " FROM elec_user o " + 
					 " INNER JOIN elec_systemddl a ON o.sexID = a.ddlCode AND a.keyword = '性别' " +
					 " INNER JOIN elec_systemddl b ON o.postID= b.ddlCode AND b.keyword = '职位' " +
					 " WHERE 1=1";
		//将Map集合中存放的字段排序，组织成ORDER BY o.textDate ASC,o.textName DESC
		String orderbyCondition = this.orderbySql(orderby);
		//添加查询条件
		final String finalSql = sql + condition + orderbyCondition;

		@SuppressWarnings("unchecked")
		List<Object[]> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				//当投影查询的字段用到相同名称的字段，为了区分字段，使用标量查询
				Query query = session.createSQLQuery(finalSql)
						.addScalar("o.userID")
						.addScalar("o.logonName")
						.addScalar("o.userName")
						.addScalar("a.ddlName")
						.addScalar("o.contactTel")
						.addScalar("o.onDutyDate")
						.addScalar("b.ddlName");
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
			
		});
		
		//将List<Object[]>转换成List<ElecUser>
		List<ElecUser> userList = new ArrayList<ElecUser>();
		if(list!=null && list.size()>0){
			for(Object [] o:list){
				ElecUser elecUser = new ElecUser();
				//o.userID,o.logonName,o.userName,a.ddlName,o.contactTel,o.onDutyDate,b.ddlName
				elecUser.setUserID(o[0].toString());
				elecUser.setLogonName(o[1].toString());
				elecUser.setUserName(o[2].toString());
				elecUser.setSexID(o[3].toString());
				elecUser.setContactTel(o[4].toString());
				elecUser.setOnDutyDate((Date)o[5]);
				elecUser.setPostID(o[6].toString());
				userList.add(elecUser);
			}
		}
		return userList;
	}

	/**将Map集合中存放的字段排序，组织成
	 * ORDER BY o.textDate ASC,o.textName DESC*/
	private String orderbySql(Map<String, String> orderby) {
		StringBuffer buffer = new StringBuffer("");
		if(orderby!=null && orderby.size()>0){
			buffer.append(" ORDER BY ");
			for(Map.Entry<String, String> map:orderby.entrySet()){
				buffer.append(map.getKey()+" "+map.getValue()+",");
			}
			//在循环后，删除最后一个逗号
			buffer.deleteCharAt(buffer.length()-1);
		}
		return buffer.toString();
	}
	/**
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-6
	 * @Description: 查询各单位人数
	 * 		SELECT s.keyword,s.ddlName,COUNT(s.ddlCode) FROM elec_user u
			INNER JOIN elec_systemddl s ON u.JctID = s.ddlCode AND s.keyword='所属单位'
			WHERE u.IsDuty=1
			GROUP BY s.ddlName
			ORDER BY s.ddlCode
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> showUserview(String keyword, String ddlCode) {
		final String sql = "SELECT s.keyword,s.ddlName,COUNT(s.ddlCode) FROM elec_user u "+
			"INNER JOIN elec_systemddl s ON u."+ddlCode+" = s.ddlCode AND s.keyword='"+keyword+"' "+
			"WHERE u.IsDuty=1 "+
			"GROUP BY s.ddlName "+
			"ORDER BY s.ddlCode";
		List<Object[]> list = this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				//这里没用到标量查询,因为没有查询重名的字段
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
		return list;
	}

	@Override
	public List<ElecUser> sqlQueryWithPaging(String condition, final Object[] params,
			Map<String, String> orderBy, final PageInfo pageInfo) {
		//sql语句
		String sql = "SELECT o.userID,o.logonName,o.userName,a.ddlName,o.contactTel,o.onDutyDate,b.ddlName " +
					 " FROM elec_user o " + 
					 " INNER JOIN elec_systemddl a ON o.sexID = a.ddlCode AND a.keyword = '性别' " +
					 " INNER JOIN elec_systemddl b ON o.postID= b.ddlCode AND b.keyword = '职位' " +
					 " WHERE 1=1";
		//将Map集合中存放的字段排序，组织成ORDER BY o.textDate ASC,o.textName DESC
		String orderbyCondition = this.orderbySql(orderBy);
		//添加查询条件
		final String finalSql = sql + condition + orderbyCondition;

		@SuppressWarnings("unchecked")
		List<Object[]> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				//当投影查询的字段用到相同名称的字段，为了区分字段，使用标量查询
				Query query = session.createSQLQuery(finalSql)
						.addScalar("o.userID")
						.addScalar("o.logonName")
						.addScalar("o.userName")
						.addScalar("a.ddlName")
						.addScalar("o.contactTel")
						.addScalar("o.onDutyDate")
						.addScalar("b.ddlName");
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						query.setParameter(i, params[i]);
						//--- 添加分页 2017年1月6日 begin -----
						pageInfo.setTotalResult(query.list().size());//设置总的记录数
						query.setFirstResult(pageInfo.getBeginResult());//从第几条数据开始检索
						query.setMaxResults(pageInfo.getPageSize());//当前页的大小,也就是pagesize
						//------- 添加分页  end --------------
					}
				}
				return query.list();
			}
		});
		
		//将List<Object[]>转换成List<ElecUser>
		List<ElecUser> userList = new ArrayList<ElecUser>();
		if(list!=null && list.size()>0){
			for(Object [] o:list){
				ElecUser elecUser = new ElecUser();
				//o.userID,o.logonName,o.userName,a.ddlName,o.contactTel,o.onDutyDate,b.ddlName
				elecUser.setUserID(o[0].toString());
				elecUser.setLogonName(o[1].toString());
				elecUser.setUserName(o[2].toString());
				elecUser.setSexID(o[3].toString());
				elecUser.setContactTel(o[4].toString());
				elecUser.setOnDutyDate((Date)o[5]);
				elecUser.setPostID(o[6].toString());
				userList.add(elecUser);
			}
		}
		return userList;
	}

	
}
