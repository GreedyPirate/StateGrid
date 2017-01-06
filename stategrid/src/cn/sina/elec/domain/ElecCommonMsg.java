package cn.sina.elec.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * ComID VARCHAR(50)  NOT NULL,    #主键ID
	StationRun VARCHAR(1000)  NULL, #站点运行情况
	DevRun VARCHAR(1000)  NULL,    #设备运行情况
	CreateDate DATETIME NULL        #创建日期
 * @author yj
 *
 */
public class ElecCommonMsg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String comID;
	private String stationRun;
	private String devRun;
	private Date createDate;
	public String getComID() {
		return comID;
	}
	public void setComID(String comID) {
		this.comID = comID;
	}
	public String getStationRun() {
		return stationRun;
	}
	public void setStationRun(String stationRun) {
		this.stationRun = stationRun;
	}
	public String getDevRun() {
		return devRun;
	}
	public void setDevRun(String devRun) {
		this.devRun = devRun;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "ElecCommonMsg [comID=" + comID + ", stationRun=" + stationRun
				+ ", devRun=" + devRun + ", createDate=" + createDate + "]";
	}
	
}
