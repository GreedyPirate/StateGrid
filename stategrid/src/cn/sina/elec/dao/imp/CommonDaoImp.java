package cn.sina.elec.dao.imp;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.sina.elec.dao.CommonDao;
import cn.sina.elec.utils.GenericUtils;
import cn.sina.elec.utils.page.PageInfo;

public class CommonDaoImp<T> extends HibernateDaoSupport implements CommonDao<T>{

    @SuppressWarnings("rawtypes")
	private Class klass = GenericUtils.getActualType(this.getClass());
    /**
     * 因为继承了HibernateDaoSupport,所以要注入SessionFactory
     *
     * @param sessionFactory
     */
    @Resource(name="sessionFactory")
    public void setFactory(SessionFactory sessionFactory){
        this.setSessionFactory(sessionFactory);
    }
    
    @Override
    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    /**
     * 保存一个集合
     */
    @Override
	public void saveList(List<T> List) {
		this.getHibernateTemplate().saveOrUpdateAll(List);
	}
    
    @Override
    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    /**
     * 设计成Serializable的原因是:string和number都实现了该接口
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    public T findById(Serializable id) {
        return (T)this.getHibernateTemplate().get(klass,id);
    }

    @Override
    public void deleteById(Serializable id) {
        Object entity = this.findById(id);
        this.getHibernateTemplate().delete(entity);
    }


    @Override
    public void deleteByIds(Serializable... ids) {
        //删除一组id:遍历删除即可
        for (int i = 0; i < ids.length; i++) {
            //先查找
            Object entity = this.findById(ids[i]);
            //再删除
            this.getHibernateTemplate().delete(entity);
        }
    }

    @Override
    public void deleteByCollection(List<T> list) {
        this.getHibernateTemplate().deleteAll(list);
    }

    /**
     * 按条件查询,想要的sql格式为:
     *      select * from
     *          elec_text
     *      where
     *          1=1 and textName like %xx% and textRemark like %xx%
     *       orderby
     *          textName ASC,textRemark DESC
     * @param condition
     * @param params
     * @param orderBy
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<T> conditionalQuery(String condition, final Object[] params, Map<String, String> orderBy) {
        StringBuilder hql = new StringBuilder("from "+klass.getSimpleName()+" o where 1=1");
        hql.append(condition);
        String orderHql = this.getOrderHql(orderBy);
        hql.append(orderHql);
        final String finalHql = hql.toString();
        //下面的操作就要得到session了,有三种方案...
        /*方案一:
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        Query query = session.createQuery(finalHql);
        if(params!=null && params.length>0){
            for (int i = 0; i < params.length; i++) {
                //按需要填充参数
                query.setParameter(i,params[i]);
            }
        }*/
        /*方案二:
         * List<T> list = this.getHibernateTemplate().find(finalHql, params);
         */
        /**
         * 方案三
         * */
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						//设置参数
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
		});
        return list;
    }

    /**
     * 获得排序的sql
     * @param orderBy
     * @return
     */
    private String getOrderHql(Map<String, String> orderBy) {
        StringBuilder orderHql = new StringBuilder();
        //遍历集合都要先判断
        if(orderBy!=null && orderBy.size()>0){
            orderHql.append(" ORDER BY ");
            //遍历map
            for(Map.Entry<String,String> entry:orderBy.entrySet()){
                orderHql.append(entry.getKey()+" "+entry.getValue()+",");
            }
            //删除最后一个逗号
            orderHql.deleteCharAt(orderHql.length()-1);
        }
        return orderHql.toString();
    }

    /**
     * 使用二级缓存做查询
     */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryWithCache(String condition, final Object[] params,
			Map<String, String> orderBy) {
		StringBuilder hql = new StringBuilder("from "+klass.getSimpleName()+" o where 1=1");
        hql.append(condition);
        String orderHql = this.getOrderHql(orderBy);
        hql.append(orderHql);
        final String finalHql = hql.toString();
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						//设置参数
						query.setParameter(i, params[i]);
					}
				}
				query.setCacheable(true);
				return query.list();
			}
		});
        return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> sqlQueryWithPaging(final String condition, final Object[] params,
			Map<String, String> orderBy, final PageInfo pageInfo) {
		
		StringBuilder hql = new StringBuilder("from "+klass.getSimpleName()+" o where 1=1");
        hql.append(condition);
        String orderHql = this.getOrderHql(orderBy);
        hql.append(orderHql);
        final String finalHql = hql.toString();
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						//设置参数
						query.setParameter(i, params[i]);
					}
				}
				//--- 添加分页 2015年12月4日 begin -----
				pageInfo.setTotalResult(query.list().size());//设置总的记录数
				query.setFirstResult(pageInfo.getBeginResult());//从第几条数据开始检索
				query.setMaxResults(pageInfo.getPageSize());//当前页的大小,也就是pagesize
				//------- 添加分页  end --------------
				return query.list();
			}
		});
        return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> excelQueryByField(String condition, final Object[] params,
			Map<String, String> orderBy, String field) {
		StringBuilder hql = new StringBuilder("select "+field+" from "+klass.getSimpleName()+" o where 1=1");
        hql.append(condition);
        String orderHql = this.getOrderHql(orderBy);
        hql.append(orderHql);
        final String finalHql = hql.toString();
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						//设置参数
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
		});
        return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> nativeSql(final String condition) {
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(condition);
				return query.list();
			}
		});
		return list;
	}
}
