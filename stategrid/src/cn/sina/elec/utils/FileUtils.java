package cn.sina.elec.utils;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TokenHelper;

public class FileUtils {

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 我要返回的是"/upload/2015/11/22/用户管理/uuid+文件名.后缀名/"
	 * 				同时还要保存文件
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public static String uplaodAndReturnPath(File file, String fileName,
			String module) {
		String realpath = ServletActionContext.getServletContext().getRealPath("/upload");
		String dateDirs = DateUtils.formattoDirs(new Date());
		//		/upload/2015/11/22/用户管理
		String filePath = realpath+dateDirs+module;
		//创建文件目录
		File dirs = new File(filePath);
		if(!dirs.exists()){
			dirs.mkdirs();//创建的是多级目录
		}
		//忘了加"."
		String saveFileName = TokenHelper.generateGUID()+"."+FilenameUtils.getExtension(fileName);
		//最后要保存的文件
		File finalFile = new File(dirs,saveFileName);
		//保存:把源文件删除,在保存现在的文件,但是这样做太麻烦了,直接剪切
		file.renameTo(finalFile);
		//filePath+"/"+saveFileName 保存的是绝对路径
		return "/upload"+dateDirs+module+"/"+saveFileName;
	}
}
