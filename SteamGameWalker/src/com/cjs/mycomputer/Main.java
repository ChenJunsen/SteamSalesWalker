package com.cjs.mycomputer;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjs.mycomputer.api.Api;
import com.cjs.mycomputer.db.DBHelper;
import com.cjs.mycomputer.db.TestSuit;
import com.cjs.mycomputer.db.annotation.Table;
import com.cjs.mycomputer.entity.Fish;
import com.cjs.mycomputer.tools.StringUtil;
import com.cjs.mycomputer.tools.TimeKit;

public class Main {
	public static void main(String[] args) {
//		Fish fish=new Fish();
//		fish.setAge(3);
//		fish.setHeight(0.45f);
//		fish.setName("Dark fish");
//		fish.setWeight(0.66f);
//		
//		JSONObject obj=(JSONObject) JSON.toJSON(fish);
//		System.out.println(obj.toJSONString());
		
		/*long timestamp=1511203225;
		System.out.println(TimeKit.getFormatTime(timestamp));*/
		
//		System.out.println(System.currentTimeMillis());
		
		/*Date date=new Date(System.currentTimeMillis());
		System.out.println(date);*/
		
		/*String date="2017-11-30T18:00:00+00:00";
		System.out.println(TimeKit.getFormatTime(date, TimeKit.FORMAT_UTC_XXX,TimeKit.FORMAT_SIMPLE_TIME));*/
		
		/*TestSuit suit=null;
		boolean isOk=suit.getClass().isAnnotationPresent(Table.class);
		System.out.println(isOk);*/
		
//		System.out.println(Api.class.getSimpleName());
		/*String s="AddBvv";
		System.out.println(StringUtil.CamelTo_(s));*/
		
		/*String s2="1_2_3_4";
		System.out.println(StringUtil._ToCamel(s2));*/
		
		System.out.println(DBHelper.generateCreateTableSql(TestSuit.class));
		
		String emoji="Fruit Sudoku🍉 2";
	}
}
