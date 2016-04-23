package cn.sina.elec.utils;

import com.opensymphony.xwork2.ActionContext;


public class StackUtils {

	public static void pushOnTop(Object object) {
		ActionContext.getContext().getValueStack().push(object);
	}
	
}
