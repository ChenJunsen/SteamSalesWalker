package com.cjs.mycomputer.db.exception;

public class IllegalIdColumnAnnotationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IllegalIdColumnAnnotationException(String msg) {
		super(msg);
	}
	
	public IllegalIdColumnAnnotationException() {
		super("Id和Column注解不能同时存在");
	}

}
