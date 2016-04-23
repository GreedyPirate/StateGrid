package cn.sina.elec.domain;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UserID VARCHAR(50)  NOT NULL, #主键ID
	JctID VARCHAR(50)   NULL,     #所属单位code
	UserName VARCHAR(50)   NULL,  #用户姓名
	LogonName VARCHAR(50)   NULL, #登录名
	LogonPwd VARCHAR(50)  NULL,   #密码#
	SexID VARCHAR(10)  NULL,      #性别
	Birthday DATETIME NULL,       #出生日期
	Address VARCHAR(100)  NULL,    #联系地址
	ContactTel VARCHAR(50)  NULL, #联系电话 
	Email VARCHAR(50)  NULL,      #电子邮箱
	Mobile VARCHAR(50)  NULL,     #手机
	IsDuty VARCHAR(10)  NULL,     #是否在职
	OnDutyDate DATETIME NULL,     #入职时间
	OffDutyDate DATETIME NULL,    #离职时间
	remark VARCHAR(500)  NULL   #备注
 * @author yj
 * @date 2015-11-20 下午10:33:16
 */
@SuppressWarnings("serial")
public class ElecUser implements Serializable{
	private String userID;		//主键ID
	private String jctID;		//所属单位code
	private String jctUnitID;	//所属单位的单位名称（联动）
	private String userName;	//用户姓名
	private String logonName;	//登录名
	private String logonPwd;	//密码
	private String sexID;		//性别
	private Date birthday;		//出生日期
	private String address;		//联系地址
	private String contactTel;	//联系电话 
	private String email;		//电子邮箱
	private String mobile;		//手机
	private String isDuty;		//是否在职
	private String postID;      //职位
	private Date onDutyDate;	//入职时间
	private Date offDutyDate;	//离职时间
	private String remark;		//备注
	private Set<ELecUserFile> elecUserFiles = new HashSet<ELecUserFile>();	//用户附件
	private Set<ElecRole> elecRoles = new HashSet<ElecRole>();	//用户角色
	
	
	
	
	
	

	//-----------------------为了业务需要的非持久化字段-------------------------------
	
	/**
	 * @see cn.sina.elec.service.impl.ElecUserServiceImp.queryUser(ElecUser)
	 */
	private Date onDutyDateBegin;//入职开始时间
	private Date onDutyDateEnd;//入职最终时间
	
	/**
	 * @see cn.sina.elec.service.impl.ElecUserServiceImp.checkUser(String)
	 */
	private String message;//判断用户是否已存在的字段
	
	/**
	 * 文件上传字段,页面数据的封装是由struts2的拦截器做的
	 * @see cn.sina.elec.web.action.ElecUserAction.save()
	 * @see cn.sina.elec.service.impl.ElecUserServiceImp.saveUser(ElecUser)
	 * @see cn.sina.elec.service.impl.ElecUserServiceImp.saveUserFile(ElecUser)
	 */
	private File[] uploads;//上传的文件
	private String[] uploadsFileName;//上传的文件名
	private String[] uploadsContentType;//上传的文件类型
	
	
	/**
	 * 文件下载时要用的fileID和输入流
	 * @see cn.sina.elec.web.action.ElecUserAction.download()
	 */
	private String fileID;
	private InputStream input;
	
	/*
	 * 用来判断用户编辑和用户查看
	 */
	private String viewflag;
	
	/**
	 * 用来判断是否对密码进行加密，存放修改用户之前的密码
	 * @see cn.sina.elec.service.impl.ElecUserServiceImp.saveUser(ElecUser)
	 */
	private String md5pwd;
	
	/**
	 * 用来判断是否是角色对应的用户
	 * @see	cn.sina.elec.service.impl.ElecRoleServiceImp.getUsersByID(String)
	 */
	private String roleUser;
	
	/**
	 * 判断是否是非系统管理员打开用户编辑页面
	 * roleFlag = 1:是非系统管理员
	 */
	private String roleFlag;
	
	/**
	 * 上传的要导入的excel文件
	 */
	private File file;
	
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getRoleFlag() {
		return roleFlag;
	}

	public void setRoleFlag(String roleFlag) {
		this.roleFlag = roleFlag;
	}

	public String getRoleUser() {
		return roleUser;
	}

	public void setRoleUser(String roleUser) {
		this.roleUser = roleUser;
	}

	public Set<ElecRole> getElecRoles() {
		return elecRoles;
	}
	
	public void setElecRoles(Set<ElecRole> elecRoles) {
		this.elecRoles = elecRoles;
	}
	public String getMd5pwd() {
		return md5pwd;
	}

	public void setMd5pwd(String md5pwd) {
		this.md5pwd = md5pwd;
	}

	public String getViewflag() {
		return viewflag;
	}

	public void setViewflag(String viewflag) {
		this.viewflag = viewflag;
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public File[] getUploads() {
		return uploads;
	}

	public void setUploads(File[] uploads) {
		this.uploads = uploads;
	}


	public String[] getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(String[] uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public String[] getUploadsContentType() {
		return uploadsContentType;
	}

	public void setUploadsContentType(String[] uploadsContentType) {
		this.uploadsContentType = uploadsContentType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getOnDutyDateBegin() {
		return onDutyDateBegin;
	}

	public void setOnDutyDateBegin(Date onDutyDateBegin) {
		this.onDutyDateBegin = onDutyDateBegin;
	}

	public Date getOnDutyDateEnd() {
		return onDutyDateEnd;
	}

	public void setOnDutyDateEnd(Date onDutyDateEnd) {
		this.onDutyDateEnd = onDutyDateEnd;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getJctID() {
		return jctID;
	}

	public void setJctID(String jctID) {
		this.jctID = jctID;
	}

	public String getJctUnitID() {
		return jctUnitID;
	}

	public void setJctUnitID(String jctUnitID) {
		this.jctUnitID = jctUnitID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getLogonPwd() {
		return logonPwd;
	}

	public void setLogonPwd(String logonPwd) {
		this.logonPwd = logonPwd;
	}

	public String getSexID() {
		return sexID;
	}

	public void setSexID(String sexID) {
		this.sexID = sexID;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIsDuty() {
		return isDuty;
	}

	public void setIsDuty(String isDuty) {
		this.isDuty = isDuty;
	}

	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
	}

	public Date getOnDutyDate() {
		return onDutyDate;
	}

	public void setOnDutyDate(Date onDutyDate) {
		this.onDutyDate = onDutyDate;
	}

	public Date getOffDutyDate() {
		return offDutyDate;
	}

	public void setOffDutyDate(Date offDutyDate) {
		this.offDutyDate = offDutyDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<ELecUserFile> getElecUserFiles() {
		return elecUserFiles;
	}

	public void setElecUserFiles(Set<ELecUserFile> elecUserFiles) {
		this.elecUserFiles = elecUserFiles;
	}

}
