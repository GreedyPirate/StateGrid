package cn.sina.elec.web.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sina.elec.domain.ElecFileUpload;
import cn.sina.elec.domain.ElecSystemDDL;
import cn.sina.elec.service.ElecFileUploadService;
import cn.sina.elec.service.ElecSystemDDLService;

@SuppressWarnings("serial")
@Controller("elecFileUploadAction")
@Scope("prototype")
public class ElecFileUploadAction extends BaseAction<ElecFileUpload>{

	@Resource(name=ElecFileUploadService.ELEC_SERVICE)
	ElecFileUploadService fileUploadService;
	
	@Resource(name=ElecSystemDDLService.ELEC_SERVICE)
	ElecSystemDDLService systemDDLService;
	
	ElecFileUpload elecFileUpload = this.getModel();
	
	private String contentDisposition;
	private String contentType;
	private long bufferSize = 1024*1024;
	
	
	public long getBufferSize() {
		return bufferSize;
	}
	public String getContentDisposition() {
		return contentDisposition;
	}
	public String getContentType() {
		return contentType;
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-1
	 * @Description: 跳转到资料图纸管理首页
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String home(){
		//加载所属单位和图纸类别
		this.getSystemDDL();
		return "home";
	}
	/*
	 * 查询所属单位和图纸类别
	 */
	private void getSystemDDL() {
		List<ElecSystemDDL> jctList = systemDDLService.getTypeValue("所属单位");
		List<ElecSystemDDL> picList = systemDDLService.getTypeValue("图纸类别");
		request.setAttribute("jctList", jctList);
		request.setAttribute("picList", picList);
	}
	/**
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-1
	 * @Description: 跳转到资料图纸添加页面
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String add(){
		this.getSystemDDL();
		return "add";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-1
	 * @Description: 完成资料上传功能
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String save(){
		fileUploadService.saveFiles(elecFileUpload);
		return "save";
	}
	
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-1
	 * @Description: 通过所属单位和图纸类别来获取已经上传的文件
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String addList(){
		List<ElecFileUpload> list = fileUploadService.getUploadedByID(elecFileUpload);
		request.setAttribute("list", list);
		return "addList";
	}
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-2
	 * @Description: 资料图纸文件的的下载
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String download(){
		//1.根据fileID查询出文件
		ElecFileUpload file = fileUploadService.getFielById(elecFileUpload.getSeqID());
		//2.获取并设置MIME类型
		String filename = null;
		try {
			//解决中文乱码
			filename = new String(file.getFileName().getBytes("GBK"), "iso8859-1");
			contentDisposition = "attachment;filename="+filename;
			contentType = ServletActionContext.getServletContext().getMimeType(filename);
			//获取文件的绝对路径,因为FileInputStream要用
			String absolutePath = ServletActionContext.getServletContext().getRealPath("")+file.getFileURL();
			elecFileUpload.setInput(new FileInputStream(absolutePath));
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
	 * @CreateDate: 2015-12-2
	 * @Description:dataChartIndex.jsp的条件查询
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public String luceneHome(){
		//加载所属单位和图纸类别
		this.getSystemDDL();
		//查询结果是多条记录
		List<ElecFileUpload> fileUploads = fileUploadService.retrieval(elecFileUpload);
		//放在request中
		request.setAttribute("fileUploads", fileUploads);
		return "luceneHome";
	}
}
