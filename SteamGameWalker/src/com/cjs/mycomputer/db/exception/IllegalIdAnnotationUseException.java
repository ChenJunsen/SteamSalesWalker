package com.cjs.mycomputer.db.exception;

public class IllegalIdAnnotationUseException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IllegalIdAnnotationUseException(String msg) {
		super(msg);
	}
	
	public IllegalIdAnnotationUseException() {
		super("Id注解只能用在int类型的变量上面");
	}
}
