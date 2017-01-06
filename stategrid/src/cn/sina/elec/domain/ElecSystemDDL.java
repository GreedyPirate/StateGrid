package cn.sina.elec.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * SeqID INT NOT NULL,          #主键ID(自增长)
	Keyword VARCHAR(20)   NULL,  #查询关键字
	DdlCode INT  NULL,           #数据字典的code
	DdlName VARCHAR(50)  NULL    #数据字典的value
 * @author yj
 * @date 2015-11-19 下午9:24:09
 * 
 */
@SuppressWarnings("serial")
public class ElecSystemDDL implements Serializable{
	private Integer seqID;
	private String keyword;
	private Integer ddlCode;
	private String ddlName;
	
	public ElecSystemDDL() {
	}
	public ElecSystemDDL(String keyword) {
		this.keyword = keyword;
	}
	public Integer getSeqID() {
		return seqID;
	}
	public void setSeqID(Integer seqID) {
		this.seqID = seqID;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getDdlCode() {
		return ddlCode;
	}
	public void setDdlCode(Integer ddlCode) {
		this.ddlCode = ddlCode;
	}
	public String getDdlName() {
		return ddlName;
	}
	public void setDdlName(String ddlName) {
		this.ddlName = ddlName;
	}
	/**
	 * 因为提交了时候多了3个参数,分别是:保存的类型(add/new),要保存的类型名称(keywordname)
	 * 新增类型的值(items),所以ElecSystemDDL要多3个属性进行封装
	 * 它们和数据库无关,只是业务的需要,所以是非持久化的属性
	 */
	private String keywordname;
	private String typeflag;
	private String[] itemname;//一次性要保存的子条目是多个

	public String getKeywordname() {
		return keywordname;
	}
	public void setKeywordname(String keywordname) {
		this.keywordname = keywordname;
	}
	public String getTypeflag() {
		return typeflag;
	}
	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}
	public String[] getItemname() {
		return itemname;
	}
	public void setItemname(String[] itemname) {
		this.itemname = itemname;
	}
	@Override
	public String toString() {
		return "ElecSystemDDL [seqID=" + seqID + ", keyword=" + keyword
				+ ", ddlCode=" + ddlCode + ", ddlName=" + ddlName + "]";
	}
	
}
