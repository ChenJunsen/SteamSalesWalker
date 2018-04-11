package com.cjs.mycomputer.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该字段被指定为数据库中的自增型id
 * 
 * @author chenjunsen
 * @email chenjunsen@outlook.com
 * 创建于:2017年12月1日上午11:12:42
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
	public static final int DEFAULT_VALUE_INT=-1;
	/**
	 * 该字段在数据库中对应的字段名
	 * @return
	 */
	String nameInDB() default "";
	/**
	 * 字段长度
	 * @return
	 */
	int length() default DEFAULT_VALUE_INT;
	
	/**
	 * MySQL里面对该字段的描述
	 * @return
	 */
	String comment() default "";
}
