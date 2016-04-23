/**
 * IWebSystemDDLServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package cn.sina.elec.webservice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.sina.elec.dao.ElecSystemDDLDao;
import cn.sina.elec.domain.ElecSystemDDL;
/**
 * IWebSystemDDLServiceSkeleton java skeleton for the axisService
 */
public class IWebSystemDDLServiceSkeleton {

	/**
	 * Auto generated method signature
	 * 
	 * @param getSystemDDL
	 * @return getSystemDDLResponse
	 */

	public cn.sina.elec.webservice.GetSystemDDLResponse getSystemDDL(
			cn.sina.elec.webservice.GetSystemDDL getSystemDDL) {
		String keyword = getSystemDDL.getArgs0();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ElecSystemDDLDao systemDDLDao = (ElecSystemDDLDao) context.getBean(ElecSystemDDLDao.DAO_IMP);
		
		//根据keyword查询
		String condition = " and o.keyword = ?";
		Object[] params = {keyword};
		Map<String,String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("o.ddlCode", "asc");
		List<ElecSystemDDL> systemDDLs = systemDDLDao.conditionalQuery(condition, params, orderBy);
		
		/*
		 * 接下来是一个重点部分,我们查出来的是持久类的list,但是axis生成的是domain/xsd/ElecSystemDDL
		 * 所以需要一个转换
		 */
		//	cn.sina.elec.domain.xsd.ElecSystemDDL类名冲突了,必须这样写
		cn.sina.elec.domain.xsd.ElecSystemDDL [] systemDDLs2 = new cn.sina.elec.domain.xsd.ElecSystemDDL[systemDDLs.size()];
		if(systemDDLs!=null && systemDDLs.size()>0){
			for (int i = 0; i < systemDDLs.size(); i++) {
				cn.sina.elec.domain.xsd.ElecSystemDDL axisDDl = new cn.sina.elec.domain.xsd.ElecSystemDDL();
				try {
					BeanUtils.copyProperties(axisDDl, systemDDLs.get(i));
					systemDDLs2[i] = axisDDl;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		GetSystemDDLResponse response = new GetSystemDDLResponse();
		response.set_return(systemDDLs2);
		return response;
	}
}
