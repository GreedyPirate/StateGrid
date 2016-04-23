package cn.sina.elec.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//日期转换工具类
public class DateUtils {
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 把日期转换为/2015/11/22/的形式
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: String
	 */
	public static String formattoDirs(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("/yyyy/MM/dd/");
		return formatter.format(date);
	}

	public static String formattoString(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
	
	public static String formattoFile(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(date);
	}

	public static Date formattoBirthday(String string) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
