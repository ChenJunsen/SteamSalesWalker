package com.cjs.mycomputer.exe;

import com.cjs.mycomputer.db.DBConnection2;
import com.cjs.mycomputer.db.Fruit;

public class DBAnnotationEXE {
	public static void main(String[] args) {
		new DBConnection2().generateDB(Fruit.class);
//		new DBConnection2().generateDB(Animal.class);
	}
}
