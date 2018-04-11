package com.cjs.mycomputer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.cjs.mycomputer.api.GameInfo;
import com.cjs.mycomputer.tools.Log;
import com.cjs.mycomputer.tools.StringUtil;
import com.cjs.mycomputer.tools.TimeKit;
import com.cjs.mycomputer.walker.HtmlUnitHelper;
import com.cjs.mycomputer.walker.JsoupHelper;

/**
 * 数据库连接工具类
 * 
 * @author chenjunsen
 * @email chenjunsen@outlook.com
 * 创建于:2017年11月29日下午4:45:58
 */
public class DBConnection {

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
	 */
	public void generateDB() {
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
			String timestr=TimeKit.getFormatTime(System.currentTimeMillis(), TimeKit.FORMAT_SIMPLE_TIME4);
			table+=timestr;//重命名数据库
			Log.d(TAG, "generateDB->创建表:"+table);
			//warining:
			//在name和deals_desc字段处对字符集的设置采用的是utf8mb4,使用该字符集可以使mysql支持emoji表情
			//否则会抛出类似：Incorrect string value: '\xF0\x9F\x8D\x89 2' for column 'name' at row 1的错误
			//修改字符集的同时，也要对应修改后面的collate
			String sql2="CREATE TABLE `"+table+"` ("+
					"`_id`  int(10) NOT NULL AUTO_INCREMENT COMMENT '数据库对应的编号' ,"+
					"`appid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,"+
					"`name`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '产品名字' ,"+
					"`img_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '产品图片链接' ,"+
					"`store_link`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,"+
					"`deals_desc`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' ,"+
					"`detail_link`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,"+
					"`current_price`  decimal(10,2) NULL DEFAULT NULL ,"+
					"`discount`  float(5,2) NULL DEFAULT NULL ,"+
					"`rating`  float(5,2) NULL DEFAULT NULL ,"+
					"`deals_ends_utc`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,"+
					"`deals_start_utc`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,"+
					"`release_utc`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,"+
					"`deals_end`  bigint(20) NULL DEFAULT NULL ,"+
					"`deals_start`  bigint(20) NULL DEFAULT NULL ,"+
					"`release`  bigint(20) NULL DEFAULT NULL ,"+
					"PRIMARY KEY (`_id`)"+
					")";
			
			/*sql2=DBHelper.generateCreateTableSql(TestSuit.class);
			Log.d(TAG,sql2);*/
			sta.execute(sql2);
			Log.d(TAG, "generateDB->--------------------数据库构建成功,开始进行网页数据爬虫--------------------------");
			List<GameInfo> gameList=JsoupHelper.getParsedSteamGameinfoList(HtmlUnitHelper.getHtmlPageForSteamDeals());
			if(gameList!=null && gameList.size()>0){
				String sql3="INSERT INTO `steam`.`"+table+"` ("
						+ "`appid`, "
						+ "`name`, "
						+ "`img_url`, "
						+ "`store_link`, "
						+ "`deals_desc`, "
						+ "`detail_link`, "
						+ "`current_price`, "
						+ "`discount`, "
						+ "`rating`, "
						+ "`deals_ends_utc`, "
						+ "`deals_start_utc`, "
						+ "`release_utc`, "
						+ "`deals_end`, "
						+ "`deals_start`, "
						+ "`release`) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				psta=conn.prepareStatement(sql3);
				for(GameInfo game:gameList){
					psta.setString(1, game.getAppId());
					//名字中可能存在emoji表情字符串，需要处理后才能存入数据库
					psta.setString(2, StringUtil.emojiConvert1(game.getName()));
					psta.setString(3, game.getImgUrl());
					psta.setString(4, game.getStoreLink());
					psta.setString(5, game.getDealsDesc());
					psta.setString(6, game.getDetailLink());
					psta.setFloat(7, game.getCurrentPrice());
					psta.setFloat(8, game.getDiscount());
					psta.setFloat(9, game.getRating());
					psta.setString(10, game.getDealsEndsInUTC());
					psta.setString(11, game.getDealsStartAtUTC());
					psta.setString(12, game.getReleaseTimeUTC());
					psta.setLong(13, game.getDealsEndsIn());
					psta.setLong(14, game.getDealsStartAt());
					psta.setLong(15, game.getReleaseTime());
					psta.execute();
				}
				Log.d(TAG, "generateDB->数据完全插入，操作成功");
			}else{
				Log.e(TAG, "generateDB->解析网页的数据列表为空,数据库填充失败");
			}
		}catch (Exception e) {
			Log.e(TAG, "generateDB->--------------------数据库插入数据失败（插入数据可能不完整）:"+e.getMessage());
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
