package com.cjs.mycomputer.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建的表的名字的字段名,一般是那个实体类的类名
 * 
 * @author chenjunsen
 * @email chenjunsen@outlook.com
 * 创建于:2017年12月1日上午11:06:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	/**
	 * 数据库表的名字
	 * @return 默认的值是空字符串,到时候创建表的时候就以所注解的类名来命名，如类名叫“AaaBbb”,那么就会自动解析为“_aaa_bbb”
	 */
	String tableName() default "";
}
