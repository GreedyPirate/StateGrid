package cn.sina.elec.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sina.elec.domain.ELecUserFile;
import cn.sina.elec.domain.ElecSystemDDL;
import cn.sina.elec.domain.ElecUser;
import cn.sina.elec.service.ELecUserFileService;
import cn.sina.elec.service.ElecSystemDDLService;
import cn.sina.elec.service.ElecUserService;
import cn.sina.elec.utils.DateUtils;
import cn.sina.elec.utils.MD5keyBean;
import cn.sina.elec.utils.StackUtils;
import cn.sina.elec.utils.chart.ChartUtils;
import cn.sina.elec.utils.excel.ExcelFileGenerator;
import cn.sina.elec.utils.excel.GenerateSqlFromExcel;

@SuppressWarnings("serial")
@Controller("elecUserAction")
@Scope("prototype")
public class ElecUserAction extends BaseAction<ElecUser>{

	@Resource(name=ElecUserService.ELEC_SERVICE)
	ElecUserService elecUserService;
	
	@Resource(name=ElecSystemDDLService.ELEC_SERVICE)
	ElecSystemDDLService ddlService;
	
	@Resource(name=ELecUserFileService.ELEC_SERVICE)
	ELecUserFileService fileService;
	
	ElecUser elecUser = this.getModel();
	
	/**
	 * 文件下载要在struts.xml中使用ognl
	 */
	private String contentType;
	private String contentDisposition;
	private long bufferSize = 1024*1024;
	
	public String getContentType() {
		return contentType;
	}
	public String getContentDisposition() {
		return contentDisposition;
	}
	public long getBufferSize() {
		return bufferSize;
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 跳转到用户管理主页.查询所属部门的数据字典,实现用户的按条件查询
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String home(){
		//跳转到主页,并且查询出所属部门,在下拉选中显示,要注入ElecSystemDDLService
		//还只能写"所属单位"....
		List<ElecSystemDDL> ddlList = ddlService.getTypeValue("所属单位");
		//用户的条件查询
		List<ElecUser> userList = elecUserService.queryUser(elecUser);
		request.setAttribute("ddlList", ddlList);
		request.setAttribute("userList", userList);
		//--- 添加分页 2015年12月4日 begin -----
		String initPage = request.getParameter("initPage");
		//如果是initPage="1"就说明不是从用户管理点进来的
		if(StringUtils.isNotBlank(initPage) && initPage.equals("1")){
			return "list";
		}
		//------- 添加分页  end --------------
		return "home";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 点击添加用户的转发动作,并且要查询出相应的数据字典转发过去
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String add(){
		this.getAddDdlName();
		return "add";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 获取所需的数据字典并转发:性别,是否在职,职位,所属单位
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: void
	 */
	private void getAddDdlName() {
		List<ElecSystemDDL> sexList = ddlService.getTypeValue("性别");
		List<ElecSystemDDL> isDutyList = ddlService.getTypeValue("是否在职");
		List<ElecSystemDDL> postList = ddlService.getTypeValue("职位");
		List<ElecSystemDDL> jctList = ddlService.getTypeValue("所属单位");
		
		request.setAttribute("sexList", sexList);
		request.setAttribute("isDutyList", isDutyList);
		request.setAttribute("postList", postList);
		request.setAttribute("jctList", jctList);
	}
	
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 所属部门和单位名称的二级联动查询
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String findJctUnit(){
		List<ElecSystemDDL> jctUnitList = ddlService.getTypeValue(elecUser.getJctID());
		//json插件包的特性,只要把list放到栈顶,页面就可以获得json数组:json = [{},{}..],放Object就是{....}
		StackUtils.pushOnTop(jctUnitList);
		return "findJctUnit";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 登录名校验
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String checkUser(){
		String message = elecUserService.checkUser(elecUser.getLogonName());
		/*result的type还是json,所以放在栈顶就可以了
		StackUtils.pushOnTop(message);*/
		/**
		 * 这里把message传给页面还有别的方法,比如把message作为ElecUser的BO对象
		 * 此时message是作为ElecUser的属性存在与栈顶的,并以json的形式传给ajax的,
		 * 但是页面取值就是data.message了,我们还是可以把message直接放在栈顶(真蛋疼)
		 * 详见struts.xml中的配置
		 */
		elecUser.setMessage(message);
		return "checkUser";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 完成对页面注册用户数据的保存
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String save(){
		elecUserService.saveUser(elecUser);
		//如果是非管理员操作.保存应该从定向到编辑页面
		String roleFlag = elecUser.getRoleFlag();
		if(roleFlag!=null && roleFlag.equals("1")){
			return "redirectEdit";
		}
		return "close";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 用户编辑页面的跳转
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String edit(){
		ElecUser editUser = elecUserService.searchUserByID(elecUser.getUserID());
		editUser.setViewflag(elecUser.getViewflag());
		editUser.setRoleFlag(elecUser.getRoleFlag());
		//要回显就放在栈顶
		StackUtils.pushOnTop(editUser);
		//也要用到数据字典
		this.getAddDdlName();
		//二级联动
		String ddlName = ddlService.getDdlName("所属单位",editUser.getJctID());
		List<ElecSystemDDL> jctUnitList = elecUserService.getDdlList(ddlName);
		request.setAttribute("jctUnitList", jctUnitList);
		return "edit";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: javaweb下载模式
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 
	public String download(){
		//1.根据fileID查询出文件
		ELecUserFile file = fileService.getFielById(elecUser.getFileID());
		//2.获取并设置MIME类型
		String filename = file.getFileName();
		String MIMEType = ServletActionContext.getServletContext().getMimeType(filename);
		response.setContentType(MIMEType);
		try {
			//3.解决中文文件名乱码问题
			filename = new String(filename.getBytes("GBK"), "iso8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			//4.获取文件的绝对路径,因为FileInputStream要用
			String absolutePath = ServletActionContext.getServletContext().getRealPath("")+file.getFileURL();
			//5.将文件发送给页面
			InputStream input = new FileInputStream(absolutePath);
			OutputStream output = response.getOutputStream();
			IOUtils.copy(input, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;//并不需要跳转到任何页面
	}*/
	
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-23
	 * @Description: struts2的文件下载,模型中应该有InputStream属性
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String download(){
		//1.根据fileID查询出文件
		ELecUserFile file = fileService.getFielById(elecUser.getFileID());
		//2.获取并设置MIME类型
		String filename = null;
		try {
			//解决中文乱码
			filename = new String(file.getFileName().getBytes("GBK"), "iso8859-1");
			contentDisposition = "attachment;filename="+filename;
			contentType = ServletActionContext.getServletContext().getMimeType(filename);
			//获取文件的绝对路径,因为FileInputStream要用
			String absolutePath = ServletActionContext.getServletContext().getRealPath("")+file.getFileURL();
			elecUser.setInput(new FileInputStream(absolutePath));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "download";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-23
	 * @Description: 用户的批量删除
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String delete(){
		elecUserService.deleteUser(elecUser);
		//删除后还在当前页
		request.setAttribute("pageNO", request.getParameter("pageNO"));
		return "delete";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-4
	 * @Description: 导出到excel
	 * 			这个操作需要的是导出的字段名和导出的数据,然后交给ExcelFileGenerator处理
	 * ArrayList<String> fieldName:excel的表头(导出的字段名)
	 * ArrayList<ArrayList<String>> fieldData:excel的每一行数据(导出的数据)
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String exportExcel(){
		ArrayList<String> fieldName = elecUserService.getExcelFieldName();
		ArrayList<ArrayList<String>> fieldData = elecUserService.getExcelData(elecUser);
		//设置文件信息
		String fileName = "用户("+DateUtils.formattoFile(new Date())+").xls";
		try {
			//文件名中文乱码
			fileName = new String(fileName.getBytes("gbk"),"iso8859-1");
			String mime = ServletActionContext.getServletContext().getMimeType(fileName);
			response.setContentType(mime);
			response.setHeader("Content-disposition", "attachment;filename="+fileName);
			ExcelFileGenerator generator = new ExcelFileGenerator(fieldName, fieldData);
			OutputStream output = response.getOutputStream();
			generator.expordExcel(output);
			/*
			 * 也可以按照struts2以流的形式返回给页面
			 * 记得在struts.xml中定义结果,type为stream
				generator把excel通过流写入到output
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] arr = output.toByteArray();
				通过一个输出流输出到页面
				ByteArrayInputStream bais = new ByteArrayInputStream(arr);
				elecUser.setInput(bais);
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-6
	 * @Description: 跳转到导入页面
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String importPage(){
		return "importPage";
	}
	/**
	 * 
	 * @throws Exception 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-6
	 * @Description: 导入excel数据到数据库,采用jxl报表导入
	 * 		在action中组织一个List<ElecUser>,然后用Service保存
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String 跳转到导入页面
	 */
	@SuppressWarnings("static-access")
	public String importExcel() throws Exception{
		//获取上传的文件
		File excel = elecUser.getFile();
		GenerateSqlFromExcel jxl = new GenerateSqlFromExcel();
		//获取要导入的数据,String[]表示一行数据
		ArrayList<String[]> data = jxl.generateUserSql(excel);
		//存放校验时的错误信息
		List<String> errorList = new ArrayList<String>();
		//完成从ArrayList<String[]>到List<ElecUser>的转化
		List<ElecUser> userList = this.convertToUserlist(data,errorList);
		if(errorList!=null && errorList.size()>0){
			request.setAttribute("errorList", errorList);
		}else{
			//如果没有错误,保存转换后的userList
			elecUserService.imporExcel(userList);
		}
		return "importPage";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-6
	 * @Description: 完成从ArrayList<String[]>到List<ElecUser>的转化
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: List<ElecUser>
	 */
	private List<ElecUser> convertToUserlist(ArrayList<String[]> data,List<String> errorList) {
		List<ElecUser> userList = new ArrayList<ElecUser>();
		if(data!=null && data.size()>0){
			for (int i = 0; i < data.size(); i++) {
				ElecUser user = new ElecUser();
				String[] userRow = data.get(i);
				//登录名校验是否为空
				if(StringUtils.isNotBlank(userRow[0])){
					//校验用户名是否已存在
					String message = elecUserService.checkUser(userRow[0]);
					if("3".equals(message)){
						user.setLogonName(userRow[0]);
					}else{
						errorList.add("第"+i+"行,第"+(0+1)+"列登录名在数据库中已存在,请修改");
					}
				}else{
					errorList.add("第"+i+"行,第"+(0+1)+"列登录名为空,导入失败!");
				}
				//密码校验,MD5签名也要加上
				if(StringUtils.isNotBlank(userRow[1])){
					MD5keyBean bean = new MD5keyBean();
					String password = bean.getkeyBeanofStr(userRow[1]);
					user.setLogonPwd(password);
				}
				//用户名校验
				if(StringUtils.isNotBlank(userRow[2])){
					user.setUserName(userRow[2]);
				}
				//性别校验,数据是男女,我们要存进数据库的是1,2,所以要转换
				if(StringUtils.isNotBlank(userRow[3])){
					String sexID = ddlService.getDdlCode("性别",userRow[3]);
					if(StringUtils.isNotBlank(sexID)){
						user.setSexID(sexID);
					}else{//假设导入的数据不是"男/女",则导入失败
						errorList.add("第"+i+"行,第"+(0+1)+"列性别错误,请修改");
					}
				
				}else{
					errorList.add("第"+i+"行,第"+(0+1)+"列性别为空,请修改");
				}
				//所属单位校验并完成转换
				if(StringUtils.isNotBlank(userRow[4])){
					String jctID = ddlService.getDdlCode("所属单位",userRow[4]);
					if(StringUtils.isNotBlank(jctID)){
						user.setJctID(jctID);
					}else{
						errorList.add("第"+i+"行,第"+(0+1)+"列所属单位不存在,请修改");
					}
				}else{
					errorList.add("第"+i+"行,第"+(0+1)+"列所属单位为空,请修改");
				}
				//地址校验
				if(StringUtils.isNotBlank(userRow[5])){
					user.setAddress(userRow[5]);
				}
				//是否在职
				if(StringUtils.isNotBlank(userRow[6])){
					String isDuty = ddlService.getDdlCode("是否在职",userRow[6]);
					if(StringUtils.isNotBlank(isDuty)){
						user.setIsDuty(isDuty);
					}else{
						errorList.add("第"+i+"行,第"+(0+1)+"列是否在职属性不存在,请修改为'是'或'否'");
					}
				}else{
					errorList.add("第"+i+"行,第"+(0+1)+"列是否在职属性为空,请修改");
				}
				//出生日期
				if(StringUtils.isNotBlank(userRow[7])){
					//日期也要转换下
					Date birthday = DateUtils.formattoBirthday(userRow[7]);
					user.setBirthday(birthday);
				}
				//职位
				if(StringUtils.isNotBlank(userRow[8])){
					String postID = ddlService.getDdlCode("是否在职",userRow[8]);
					if(StringUtils.isNotBlank(postID)){
						user.setPostID(postID);
					}else{
						errorList.add("第"+i+"行,第"+(0+1)+"列不存在此职位,请修改");
					}
				}else{
					errorList.add("第"+i+"行,第"+(0+1)+"列职位属性为空,请修改");
				}
				userList.add(user);
			}
		}
		return userList;
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-7
	 * @Description: (jfreechart)用柱状图生成报表图片,需要的数据为:所属单位,单位名称,人数
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String userChart(){
		//获取到数据
		List<Object[]> userview = elecUserService.showUserview("所属单位","jctID");
		//将数据转换成柱状图,生产图片,并返回图片的文件路径
		String filename = ChartUtils.generateBarchart(userview);
		//转发
		request.setAttribute("filename", filename);
		return "userChart";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-7
	 * @Description: (fusioncharts)用饼图生成报表图片
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String FCFChart(){
		//查询数据库，获取图形需要数据集合
		List<Object[]> list = elecUserService.showUserview("性别","sexID");
		//组织XML的数据
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			/**b.keyword,b.ddlName,COUNT(b.ddlCode)*/
			Object[] objects = (Object[])list.get(i);
			if(i==0){//组织第一个值
				String x = "男女比例统计";
				String y = "unit";//存在FusionChart中的一个问题，Y轴的显示不支持中文，所以我们用英文代替
				builder.append("<graph caption='用户统计报表("+objects[0].toString()+")' xAxisName='"+x+"' bgColor='FFFFDD' yAxisName='"+y+"' showValues='1'  decimals='0' baseFontSize='18'  maxColWidth='60' showNames='1' decimalPrecision='0'> ");
				builder.append("<set name='"+objects[1].toString()+"' value='"+objects[2].toString()+"' color='AFD8F8'/>");
			}
		    if(i==list.size()-1){//组织最后一个值
		    	builder.append("<set name='"+objects[1].toString()+"' value='"+objects[2].toString()+"' color='FF8E46'/>");
		    	builder.append("</graph>");
		    }
		} 
		request.setAttribute("chart", builder);//request中存放XML格式的数据
		return "FCFChart";
	}
}
