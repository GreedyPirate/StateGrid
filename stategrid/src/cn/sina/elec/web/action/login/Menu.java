package cn.sina.elec.web.action.login;

import java.io.Serializable;

/**
 * Created by yj on 2015/11/15.
 */
@SuppressWarnings("serial")
public class Menu implements Serializable{
    private String name;
    private String password;

    //验证码
    private String checkNumber;
    //记住我
    private String remeberMe;
    
    
    public String getRemeberMe() {
		return remeberMe;
	}

	public void setRemeberMe(String remeberMe) {
		this.remeberMe = remeberMe;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "username='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
