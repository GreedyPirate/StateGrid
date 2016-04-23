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

import cn.sina.elec.dao.ElecFileUploadDao;
import cn.sina.elec.domain.ElecFileUpload;

@Repository(ElecFileUploadDao.DAO_IMP)
public class ElecFileUploadDaoImp extends CommonDaoImp<ElecFileUpload> implements ElecFileUploadDao{

	/**
	 * 根据projID和belongTo查询资料图纸
	 * SQL:
	 * 		SELECT o.seqID,a.ddlName,b.ddlName,o.FileName,o.FileURL,o.progressTime,o.comment FROM elec_fileupload o 
			INNER JOIN elec_systemddl a ON o.projID = a.ddlCode AND a.keyword='所属单位' 
			INNER JOIN elec_systemddl b ON o.belongTo = b.ddlCode AND b.keyword='图纸类别' 
			WHERE 1=1
			AND o.projId='1'
			AND o.belongTo='1'
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ElecFileUpload> sqlQuery(String condition, final Object[] params,
			Map<String, String> orderBy) {
		String sql = "SELECT o.seqID,a.ddlName,b.ddlName,o.FileName,o.FileURL,o.progressTime,o.comment FROM elec_fileupload o "+ 
			"INNER JOIN elec_systemddl a ON o.projID = a.ddlCode AND a.keyword='所属单位' "+
			"INNER JOIN elec_systemddl b ON o.belongTo = b.ddlCode AND b.keyword='图纸类别' "+
			"WHERE 1=1";
		//添加条件
		final String finalSql = sql + condition;
		//获取排序
		String orderStr = this.getOrderHql(orderBy);
		List<Object[]> sqlList = this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				//执行sql是createSQLQuery方法
				Query query = session.createSQLQuery(finalSql)
								.addScalar("o.seqID")//添加标量,否则a.ddlName会被b.ddlName覆盖
								.addScalar("a.ddlName")
								.addScalar("b.ddlName")
								.addScalar("o.FileName")
								.addScalar("o.FileURL")
								.addScalar("o.progressTime")
								.addScalar("o.comment");
				if(params!=null && params.length>0){
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
		
		});
		//接下来要要将查询到的字段转化为一个ElecFileUpload对象
		List<ElecFileUpload> list = new ArrayList<ElecFileUpload>();
		if(sqlList!=null && sqlList.size()>0){
			ElecFileUpload fileUpload = null;
			Object[] query = null;
			for (int i = 0; i < sqlList.size(); i++) {
				query = sqlList.get(i);
				fileUpload = new ElecFileUpload();
				fileUpload.setSeqID(Integer.parseInt(query[0].toString()));
				fileUpload.setProjId(query[1].toString());
				fileUpload.setBelongTo(query[2].toString());
				fileUpload.setFileName(query[3].toString());
				fileUpload.setFileURL(query[4].toString());
				fileUpload.setProgressTime(query[5].toString());
				fileUpload.setComment(query[6].toString());
				list.add(fileUpload);
			}
		}
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
}
