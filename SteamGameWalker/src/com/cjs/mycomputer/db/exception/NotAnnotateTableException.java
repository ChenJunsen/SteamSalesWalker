package com.cjs.mycomputer.db.exception;

import com.cjs.mycomputer.db.annotation.Table;

/**
 * 没有给实体类使用{@link Table}注解
 * 
 * @author chenjunsen
 * @email chenjunsen@outlook.com
 * 创建于:2017年12月1日上午11:42:15
 */
public class NotAnnotateTableException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotAnnotateTableException(String msg) {
		super(msg);
	}
	
	public NotAnnotateTableException() {
		super("你没有使用Table注解");
	}
	
}
