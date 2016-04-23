package cn.sina.elec.domain;

import java.io.Serializable;
import java.util.Date;

/*
 * textID VARCHAR(50) NOT NULL,
    textName VARCHAR(50),
    textDate DATETIME,
    textRemark VARCHAR(500)
 */
public class Elec implements Serializable{
    private static final long serialVersionUID = 1L;
    private String textID;
    private String textName;
    private Date textDate;
    private String textRemark;
    public String getTextID() {
        return textID;
    }
    public void setTextID(String textID) {
        this.textID = textID;
    }
    public String getTextName() {
        return textName;
    }
    public void setTextName(String textName) {
        this.textName = textName;
    }
    public Date getTextDate() {
        return textDate;
    }
    public void setTextDate(Date textDate) {
        this.textDate = textDate;
    }
    public String getTextRemark() {
        return textRemark;
    }
    public void setTextRemark(String textRemark) {
        this.textRemark = textRemark;
    }

    @Override
    public String toString() {
        return "Elec{" +
                "textID='" + textID + '\'' +
                ", textName='" + textName + '\'' +
                ", textDate=" + textDate +
                ", textRemark='" + textRemark + '\'' +
                '}';
    }
}
