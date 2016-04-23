package cn.sina.elec.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ListUtils {

	public static List<String> toList(String Name, String separator) {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotBlank(Name)){
			String[] arr = Name.split(separator);
			if(arr!=null && arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					list.add(arr[i]);
				}
			}
		}
		return list;
	}

}
