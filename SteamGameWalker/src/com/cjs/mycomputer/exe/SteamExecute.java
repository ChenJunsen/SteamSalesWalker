package com.cjs.mycomputer.exe;

import com.cjs.mycomputer.db.DBConnection;

public class SteamExecute {
	
	public static void main(String[] args) {
//		
		DBConnection connection=new DBConnection();
		connection.generateDB();
	}
}
