package cn.sina.elec.utils;

import java.lang.reflect.ParameterizedType;

/**
 * Created by yj on 2015/11/14.
 */
public class GenericUtils {
    @SuppressWarnings("rawtypes")
	public static Class getActualType(Class klass){
        ParameterizedType parameterizedType = (ParameterizedType) klass.getGenericSuperclass();
        Class argType = (Class) parameterizedType.getActualTypeArguments()[0];
        return argType;
    }
}
