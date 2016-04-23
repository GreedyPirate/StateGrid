package cn.sina.elec.web.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sina.elec.domain.ElecExportFields;
import cn.sina.elec.service.ElecExportFieldsService;
import cn.sina.elec.utils.ListUtils;

@SuppressWarnings("serial")
@Controller("elecExportFieldsAction")
@Scope("prototype")
public class ElecExportFieldsAction extends BaseAction<ElecExportFields>{
	
	@Resource(name=ElecExportFieldsService.ELEC_SERVICE)
	ElecExportFieldsService exportFieldsService;
	ElecExportFields exportFields = this.getModel();
	
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-1
	 * @Description: 查询导出设置
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String setExportFields(){
		String belongto = exportFields.getBelongTo();
		ElecExportFields fields = exportFieldsService.searchFieldsByID(belongto);
		
		//这两个map一个存导出设置,一个存未导出设置,key-value:英文-中文
		//这必须是LinkedHashMap,不然导出设置顺序全乱了
		Map<String,String> exportMap = new LinkedHashMap<String, String>();
		Map<String,String> noexportMap = new LinkedHashMap<String, String>();
		
		if(fields!=null){
			List<String> elist = ListUtils.toList(fields.getExpFieldName(),"#");
			List<String> zlist = ListUtils.toList(fields.getExpNameList(),"#");
			List<String> noelist = ListUtils.toList(fields.getNoExpFieldName(),"#");
			List<String> nozlist = ListUtils.toList(fields.getNoExpNameList(),"#");
			if(zlist!=null && zlist.size()>0){
				for (int i = 0; i < zlist.size(); i++) {
					exportMap.put(elist.get(i), zlist.get(i));
				}
			}
			if(nozlist!=null && nozlist.size()>0){
				for (int i = 0; i < nozlist.size(); i++) {
					noexportMap.put(noelist.get(i), nozlist.get(i));
				}
			}
		}
		
		
		request.setAttribute("exportMap", exportMap);
		request.setAttribute("noexportMap", noexportMap);
		return "setExportFields";
	}
	
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-1
	 * @Description: 保存导出设置
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String saveSetExportExcel(){
		exportFieldsService.saveFields(exportFields);
		return "close";
	}
}
