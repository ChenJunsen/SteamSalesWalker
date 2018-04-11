package com.cjs.mycomputer.db;

import com.cjs.mycomputer.db.annotation.Column;
import com.cjs.mycomputer.db.annotation.Id;
import com.cjs.mycomputer.db.annotation.Table;

@Table(tableName = "steam")
public class TestSuit {
	
	@Id
	private int id;
	@Column(nameInDB = "appid")
	private String appid;
	@Column(nameInDB = "name",comment="条目名字")
	private String name;
	
	public TestSuit() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "TestSuit [id=" + id + ", appid=" + appid + ", name=" + name + "]";
	}
	
	
}
