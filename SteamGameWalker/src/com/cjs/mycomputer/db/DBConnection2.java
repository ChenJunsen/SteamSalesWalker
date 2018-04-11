package com.cjs.mycomputer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.cjs.mycomputer.tools.Log;

public class DBConnection2 {
	private String hostname = "localhost";
	private String port = "3306";
	private String database = "steam";
	private String table="steam_sales";
	private String user = "cjs";
	private String password = "cjs123";

	private String baseUrl = "jdbc:mysql://" + hostname + ":" + Integer.valueOf(port) + "/" + database;
	
	static final String TAG="DBConnection";

	/**
	 * 生成数据库
	 * @param prototype
	 */
	public void generateDB(Object prototype) {
		if(prototype==null){
			Log.e(TAG, "传入的原型不能为空");
			throw new IllegalArgumentException("传入的原型不能为空");
		}
		generateDB(prototype.getClass());
	}

	/**
	 * 生成数据库
	 */
	public void generateDB(Class<? extends Object> prototypeClazz) {
		Log.d(TAG, "generateDB->-------------------------------start-----------------------------");
		Connection conn = null;
		Statement sta=null;
		PreparedStatement psta=null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(baseUrl, user, password);
			sta=conn.createStatement();
			String sql1="drop table if exists "+table;
			sta.execute(sql1);
			String sql2=DBHelper.generateCreateTableSql(prototypeClazz);
			Log.d(TAG,sql2);
			sta.execute(sql2);
			Log.d(TAG, "generateDB->--------------------数据库构建成功,开始进行网页数据爬虫--------------------------");
		}catch (Exception e) {
			Log.e(TAG, "generateDB->--------------------构建数据库失败:"+e.getMessage());
			e.printStackTrace();
		} finally {
			if(sta!=null){
				try {
					sta.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(psta!=null){
				try {
					psta.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			Log.d(TAG, "generateDB->----------------------------------end---------------------------------");
		}

	}
	
	/**
	 * 生成根据系统时间命名的数据库
	 * @param prototypeClazz
	 */
	public void generateDBWithSystemTime(Class<? extends Object> prototypeClazz) {
		Log.d(TAG, "generateDB->-------------------------------start-----------------------------");
		Connection conn = null;
		Statement sta=null;
		PreparedStatement psta=null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(baseUrl, user, password);
			sta=conn.createStatement();
			String sql1="drop table if exists "+table;
			sta.execute(sql1);
			String sql2=DBHelper.generateCreateTableSqlBasedOnSystemTime(prototypeClazz);
			Log.d(TAG,sql2);
			sta.execute(sql2);
			Log.d(TAG, "generateDB->--------------------数据库构建成功,开始进行网页数据爬虫--------------------------");
		}catch (Exception e) {
			Log.e(TAG, "generateDB->--------------------构建数据库失败:"+e.getMessage());
			e.printStackTrace();
		} finally {
			if(sta!=null){
				try {
					sta.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(psta!=null){
				try {
					psta.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			Log.d(TAG, "generateDB->----------------------------------end---------------------------------");
		}

	}
}
