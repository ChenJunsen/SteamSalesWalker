package com.cjs.mycomputer.tools;

public class Log {
	private static boolean isLog=true;

	public static void d(String tag,String msg){
		if(isLog){
			System.out.println(String.format("debug--[%1$s]%2$s",tag,msg));
		}
	}
	
	public static void e(String tag,String msg){
		if(isLog){
			System.err.println(String.format("error--[%1$s]%2$s",tag,msg));
		}
	}
	
}
