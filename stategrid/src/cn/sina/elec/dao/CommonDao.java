package cn.sina.elec.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.sina.elec.utils.page.PageInfo;

public interface CommonDao<T> {
	void save(T entity);
    
    void saveList(List<T> List);
    
    void update(T entity);

    T findById(Serializable id);

    void deleteById(Serializable id);

    void deleteByIds(Serializable... ids);

    void deleteByCollection(List<T> list);

    //普通的条件查询
    List<T> conditionalQuery(String condition, Object[] params, Map<String, String> orderBy);
    
    //采用二级缓存的查询
    List<T> queryWithCache(String condition, Object[] params,Map<String, String> orderBy);
    
    //带有分页的查询
    List<T> sqlQueryWithPaging(String condition, Object[] params,Map<String, String> orderBy, PageInfo pageInfo);
    
    //使用指定的字段来查询
	List<T> excelQueryByField(String condition, Object[] params,Map<String, String> orderBy, String field);

	//使用原生Sql查询
	List<T> nativeSql(String condition);
}
