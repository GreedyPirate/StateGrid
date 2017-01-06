package cn.sina.elec.domain;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * CREATE TABLE Elec_FileUpload(
    SeqID INT NOT NULL PRIMARY KEY,   #主键ID
	ProjID VARCHAR(50)  NULL,         #工程ID/所属单位
	BelongTo VARCHAR(50)  NULL,       #所属模块/图纸类别
	FileName VARCHAR(50)  NULL,       #文件名
	FileURL VARCHAR(1000)  NULL,      #文件路径
	ProgressTime VARCHAR(20)  NULL,   #上传时间
	COMMENT VARCHAR(500)  NULL        #文件描述
) 
 * @author yj
 * @date 2015-12-1 下午2:53:10
 */
@SuppressWarnings("serial")
public class ElecFileUpload implements Serializable{
	private Integer seqID;
	private String projId;
	private String belongTo;
	private String fileName;
	private String fileURL;
	private String progressTime;
	private String comment;
	
	/*
	 * 非持久化数据
	 */
	private String queryString;//查询条件
	
	private File[] uploads;//资料图纸添加的文件上传
	private String[] uploadsFileName;
	private String[] comments;
	
	//文件下载
	private InputStream input;
	
	
	public InputStream getInput() {
		return input;
	}
	public void setInput(InputStream input) {
		this.input = input;
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
	public String[] getComments() {
		return comments;
	}
	public void setComments(String[] comments) {
		this.comments = comments;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	public Integer getSeqID() {
		return seqID;
	}
	public void setSeqID(Integer seqID) {
		this.seqID = seqID;
	}
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public String getBelongTo() {
		return belongTo;
	}
	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileURL() {
		return fileURL;
	}
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
	public String getProgressTime() {
		return progressTime;
	}
	public void setProgressTime(String progressTime) {
		this.progressTime = progressTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "ElecFileUpload [seqID=" + seqID + ", projId=" + projId
				+ ", belongTo=" + belongTo + ", fileName=" + fileName
				+ ", fileURL=" + fileURL + ", progressTime=" + progressTime
				+ ", comment=" + comment + "]";
	}
	
}
