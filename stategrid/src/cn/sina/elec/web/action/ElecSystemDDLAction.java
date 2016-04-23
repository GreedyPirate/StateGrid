package cn.sina.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sina.elec.domain.ElecSystemDDL;
import cn.sina.elec.service.ElecSystemDDLService;

@SuppressWarnings("serial")
@Controller("elecSystemDDLAction")
@Scope(value="prototype")
public class ElecSystemDDLAction extends BaseAction<ElecSystemDDL>{
	
	@Resource(name=ElecSystemDDLService.ELEC_SERVICE)
	ElecSystemDDLService ddlService;
	
	ElecSystemDDL systemDDL = this.getModel();
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-19
	 * @Description: 跳转到数据监控的主页面,首先是通过zTree点击进入该页面的,所以要去zTree的超链接中修改
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String home(){
		List<ElecSystemDDL> typeList = ddlService.queryDictionaryType();
		request.setAttribute("typeList", typeList);
		return "home";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-20
	 * @Description: 根据下拉选的值,来获取到该类型下的所有选项
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String edit(){
		//获取下来选的值
		String keyword = systemDDL.getKeyword();
		List<ElecSystemDDL> valueList = ddlService.getTypeValue(keyword);
		request.setAttribute("valueList", valueList);
		return "edit";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-20
	 * @Description: 将新增的数据字典保存到数据库
	 * 			因为提交了时候多了3个参数,分别是:保存的类型(add/new),要保存的类型名称(keywordname)
	 * 			新增类型的值(items),所以ElecSystemDDL要多3个属性进行封装
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String save(){
		ddlService.saveDictionary(systemDDL);
		return "save";
	}
}
