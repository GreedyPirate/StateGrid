package cn.sina.elec.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ELecUserFile implements Serializable{
	private String fileID;
	private String userID;
	private String fileName;
	private String fileURL;
	private Date progressTime;
	private ElecUser elecUser;
	
	public ElecUser getElecUser() {
		return elecUser;
	}
	public void setElecUser(ElecUser elecUser) {
		this.elecUser = elecUser;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
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
	public Date getProgressTime() {
		return progressTime;
	}
	public void setProgressTime(Date progressTime) {
		this.progressTime = progressTime;
	}
	@Override
	public String toString() {
		return "ELecUserFile [fileID=" + fileID + ", userID=" + userID
				+ ", fileName=" + fileName + ", fileURL=" + fileURL
				+ ", progressTime=" + progressTime + "]";
	}
	
}
