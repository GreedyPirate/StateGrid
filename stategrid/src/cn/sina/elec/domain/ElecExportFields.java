package cn.sina.elec.domain;

import java.io.Serializable;

/**
 * CREATE TABLE Elec_ExportFields(
	belongTo VARCHAR(10)  NOT NULL PRIMARY KEY,           #所属模块（自然主键），如用户管理为：5-1
	expNameList VARCHAR(5000)  NULL,          #导出字段的中文名
	expFieldName VARCHAR(5000)  NULL,         #导出字段的英文名
	noExpNameList VARCHAR(5000)  NULL,        #未导出字段的中文名
	noExpFieldName VARCHAR(5000)  NULL        #未导出字段的英文名
)
 * @author yj
 * @date 2015-12-1 上午10:06:35
 */
@SuppressWarnings("serial")
public class ElecExportFields implements Serializable{
	private String belongTo;
	private String expNameList;
	private String expFieldName;
	private String noExpNameList;
	private String noExpFieldName;
	public String getBelongTo() {
		return belongTo;
	}
	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}
	public String getExpNameList() {
		return expNameList;
	}
	public void setExpNameList(String expNameList) {
		this.expNameList = expNameList;
	}
	public String getExpFieldName() {
		return expFieldName;
	}
	public void setExpFieldName(String expFieldName) {
		this.expFieldName = expFieldName;
	}
	public String getNoExpNameList() {
		return noExpNameList;
	}
	public void setNoExpNameList(String noExpNameList) {
		this.noExpNameList = noExpNameList;
	}
	public String getNoExpFieldName() {
		return noExpFieldName;
	}
	public void setNoExpFieldName(String noExpFieldName) {
		this.noExpFieldName = noExpFieldName;
	}
	
}
