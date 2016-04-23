package cn.sina.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.sina.elec.dao.ElecSystemDDLDao;
import cn.sina.elec.domain.ElecSystemDDL;
import cn.sina.elec.service.ElecSystemDDLService;

@Service(ElecSystemDDLService.ELEC_SERVICE)
@Transactional(readOnly=true)
public class ElecSystemDDLServiceImp implements ElecSystemDDLService{
	
	@Resource(name=ElecSystemDDLDao.DAO_IMP)
	ElecSystemDDLDao elecSystemDDLDao;

	@Override
	public List<ElecSystemDDL> queryDictionaryType() {
		return elecSystemDDLDao.queryDictionaryType();
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 通过指定的keyword按ddlCode升序查询,获取该keyword下所有数据项
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	@Override
	public List<ElecSystemDDL> getTypeValue(String keyword) {
		String condition = "";
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotBlank(keyword)){
			condition = " and o.keyword=?";
			params.add(keyword);
		}
		//这里依然要有序
		Map<String,String> orderBy = new LinkedHashMap<String,String>();
		orderBy.put("o.ddlCode", "asc");
		//使用二级缓存来加载
		return elecSystemDDLDao.queryWithCache(condition, params.toArray(), orderBy);
		//return elecSystemDDLDao.conditionalQuery(condition, params.toArray(), orderBy);
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 保存数据项
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveDictionary(ElecSystemDDL elecSystemDDL) {
		//1：获取页面传递的参数
		//数据类型
		String keyword = elecSystemDDL.getKeywordname();
		//业务标识
		String typeflag = elecSystemDDL.getTypeflag();
		//数据项的值（数组）
		String itemnames [] = elecSystemDDL.getItemname();
		//2：获取判断业务逻辑的标识（new和add）
		// 如果typeflag==new：新增一种新的数据类型
		if(typeflag!=null && typeflag.equals("new")){
			//* 遍历页面传递过来的数据项的名称，组织PO对象，执行保存
			this.saveDDL(keyword,itemnames);
		}
		// 如果typeflag==add：在已有的数据类型基础上进行编辑和修改
		else{
			//* 使用数据类型，查询该数据类型对应的list，删除list
			List<ElecSystemDDL> list = this.getTypeValue(keyword);
			elecSystemDDLDao.deleteByCollection(list);
		    //* 遍历页面传递过来的数据项的名称，组织PO对象，执行保存
			this.saveDDL(keyword, itemnames);
		}
		    
		
	}

	//* 遍历页面传递过来的数据项的名称，组织PO对象，执行保存
	private void saveDDL(String keyword, String[] itemnames) {
		if(itemnames!=null && itemnames.length>0){
			for(int i=0;i<itemnames.length;i++){
				//组织PO对象，执行保存
				ElecSystemDDL systemDDL = new ElecSystemDDL();
				systemDDL.setKeyword(keyword);
				systemDDL.setDdlCode(i+1);
				systemDDL.setDdlName(itemnames[i]);
				elecSystemDDLDao.save(systemDDL);
			}
		}
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
	public String getDdlName(String keyword, String ddlCode) {
		return elecSystemDDLDao.convertByKeywordAndDdlCode(keyword, ddlCode);
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 根据keyword和ddlName来获取对应的ddlCode
	 * 		用于jxl的导入,数据中是ddlName,数据库中存的是ddlCode
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	@Override
	public String getDdlCode(String keyword, String ddlName) {
		return elecSystemDDLDao.convertByKeywordAndDdlName(keyword, ddlName);
	}
}
