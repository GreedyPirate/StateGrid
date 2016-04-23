package cn.sina.elec.utils;
/**
 * 用于细颗粒度权限控制的注解
 * @author yj
 * @date 2015-11-29 上午11:20:54
 */
public @interface PrevilegeAnnotation {
	String pid();
	String mid();
}
