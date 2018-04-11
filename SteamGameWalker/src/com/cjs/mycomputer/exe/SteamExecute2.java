package com.cjs.mycomputer.exe;

import com.cjs.mycomputer.api.GameInfo;
import com.cjs.mycomputer.db.DBConnection2;
import com.cjs.mycomputer.walker.HtmlUnitHelper;

public class SteamExecute2 {
	public static void main(String[] args) {
		/*DBConnection2 conn=new DBConnection2();
		conn.generateDBWithSystemTime(GameInfo.class);*/
		HtmlUnitHelper.getHtmlPageForSteamDealsWithPagination();
	}
}
