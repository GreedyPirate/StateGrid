package cn.sina.elec.domain;

import java.io.Serializable;

/**
 * 	comID VARCHAR(50) NOT NULL PRIMARY KEY,
	TYPE CHAR(2) NULL,
	content VARCHAR(5000) NULL,
	orderby INT NULL 
 * @author yj
 * @date 2015-11-18 下午9:56:20
 */
@SuppressWarnings("serial")
public class ElecCommonMsgContent implements Serializable{
	
	private String comID;
	private String type;
	private String content;
	private Integer orderby;
	public String getComID() {
		return comID;
	}
	public void setComID(String comID) {
		this.comID = comID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getOrderby() {
		return orderby;
	}
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
	@Override
	public String toString() {
		return "ElecCommonMsgContent [comID=" + comID + ", type=" + type
				+ ", content=" + content + ", orderby=" + orderby + "]";
	}
	
}
