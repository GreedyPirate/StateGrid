package cn.sina.elec.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sina.elec.domain.ElecCommonMsg;
import cn.sina.elec.service.ElecCommonMsgService;
import cn.sina.elec.utils.StackUtils;
/**
 * 
 * @author yj
 * @date 2015-11-16 下午1:09:31
 */
@SuppressWarnings("serial")
@Controller("elecCommonMsgAction")
@Scope("prototype")
public class ElecCommonMsgAction extends BaseAction<ElecCommonMsg> {
	ElecCommonMsg elecCommonMsg = this.getModel();
	
	@Resource(name=ElecCommonMsgService.ELEC_SERVICE)
	ElecCommonMsgService commonMsgService;
	
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-18
	 * @Description: 点击运行监控,显示监控的主页面
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String home(){
		ElecCommonMsg commonMsg = commonMsgService.getCommonMsg();
		StackUtils.pushOnTop(commonMsg);
		return "home";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-18
	 * @Description: 保存或更新提交的监控信息.如果数据库中没有信息则提交,有则更新
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String save(){
		commonMsgService.saveCommonMsg(elecCommonMsg);
		return "save";
	}
	
	 /**
     * 
     * @Author: 杨杰
     * @CreateDate: 2015-11-19
     * @Description: 
     * @Version: V1.0.0
     * @Params: 
     * @Return: String
     */
    public String actingView(){
    	//查询运行监控的数据
		//1：查询数据库运行监控表的数据，返回惟一ElecCommonMsg
		ElecCommonMsg commonMsg = commonMsgService.getCommonMsg();
		//2：将ElecCommonMsg对象压入栈顶，支持表单回显
		StackUtils.pushOnTop(commonMsg);
    	return "actingView";
    }
}
