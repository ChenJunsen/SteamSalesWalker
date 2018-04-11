package com.cjs.mycomputer.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该字段被指定为数据库中对应的列名
 * 
 * @author chenjunsen
 * @email chenjunsen@outlook.com
 * 创建于:2017年12月1日上午11:09:24
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	public static final int DEFAULT_VALUE_INT=-1;
	public static final String DEFAULT_NULL="NULL";
	/**
	 * 数据库中列的名字
	 * @return
	 */
	String nameInDB() default "";
	
	/**
	 * 字段长度
	 * @return
	 */
	int length() default DEFAULT_VALUE_INT;
	
	/**
	 * 浮点位数,仅当是浮点类型时有效
	 * @return
	 */
	int floatPoint() default 0;
	
	/**
	 * 是否允许该字段的值为空
	 * @return
	 */
	boolean allowNull() default true;
	
	/**
	 * 字段的默认值，只支持简单非复合类型数据，非String类型会转换为对应的类型
	 * 传入错误的类型值会转换为{@link #DEFAULT_VALUE_NULL}或者{@link #DEFAULT_VALUE_INT}
	 * @return
	 */
	String defaultValue() default DEFAULT_NULL;
	
	/**
	 * MySQL里面对该字段的描述
	 * @return
	 */
	String comment() default "";
}
