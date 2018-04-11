package com.cjs.mycomputer.api;

import java.io.Serializable;

import com.cjs.mycomputer.db.annotation.Column;
import com.cjs.mycomputer.db.annotation.Id;
import com.cjs.mycomputer.db.annotation.Table;

@Table(tableName="steam_sales")
public class GameInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id(comment="数据库对应的编号",length=10)
	private int id;
	@Column(length=10,comment="产品在steam中的真实id")
	private String appId;//产品id
	@Column(length=100,defaultValue="",comment="产品名字")
	private String name;//名字
	@Column(length=255,defaultValue="",comment="产品图片链接")
	private String imgUrl;//图片链接
	@Column(length=255,defaultValue="",comment="steam商店的产品链接")
	private String storeLink;//steam商店链接
	@Column(length=255,defaultValue="",comment="商品的折扣描述")
	private String dealsDesc;//折扣描述
	@Column(length=255,defaultValue="",comment="商品在steam db里面的详情链接")
	private String detailLink;//steam db详情链接
	@Column(length=10,floatPoint=2,comment="打折过后的现价")
	private float currentPrice;//折扣后的价格
	@Column(length=5,floatPoint=2,comment="折扣率")
	private float discount;//折扣
	@Column(length=5,floatPoint=2,comment="好评率")
	private float rating;//好评率
	
	//这组是原始的国际标准时间，因为steam不在国内进行售卖服务，所以采用的时区是+0.00
	@Column(length=30,comment="促销结束的UTC时间",nameInDB="deals_ends_in_utc")
	private String dealsEndsInUTC;
	@Column(length=30,comment="促销开始的UTC时间",nameInDB="deals_start_in_utc")
	private String dealsStartAtUTC;
	@Column(length=30,comment="商品发行的UTC时间",nameInDB="release_time_utc")
	private String releaseTimeUTC;
	
	//这组是转换为国内时间即时区+8.00后的时间戳
	@Column(length=20,comment="促销结束的时间戳")
	private long dealsEndsIn;
	@Column(length=20,comment="促销开始的时间戳")
	private long dealsStartAt;
	@Column(length=20,comment="商品发行的时间戳")
	private long releaseTime;
	
	public GameInfo() {
		super();
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getDealsDesc() {
		return dealsDesc;
	}
	public void setDealsDesc(String dealsDesc) {
		this.dealsDesc = dealsDesc;
	}
	
	public String getDetailLink() {
		return detailLink;
	}
	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public long getDealsEndsIn() {
		return dealsEndsIn;
	}
	public void setDealsEndsIn(long dealsEndsIn) {
		this.dealsEndsIn = dealsEndsIn;
	}
	public long getDealsStartAt() {
		return dealsStartAt;
	}
	public void setDealsStartAt(long dealsStartAt) {
		this.dealsStartAt = dealsStartAt;
	}
	public long getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(long releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	
	public String getDealsEndsInUTC() {
		return dealsEndsInUTC;
	}
	public void setDealsEndsInUTC(String dealsEndsInUTC) {
		this.dealsEndsInUTC = dealsEndsInUTC;
	}
	public String getDealsStartAtUTC() {
		return dealsStartAtUTC;
	}
	public void setDealsStartAtUTC(String dealsStartAtUTC) {
		this.dealsStartAtUTC = dealsStartAtUTC;
	}
	public String getReleaseTimeUTC() {
		return releaseTimeUTC;
	}
	public void setReleaseTimeUTC(String releaseTimeUTC) {
		this.releaseTimeUTC = releaseTimeUTC;
	}
	
	
	public String getStoreLink() {
		return storeLink;
	}
	public void setStoreLink(String storeLink) {
		this.storeLink = storeLink;
	}
	
	
	public float getCurrentPrice() {
		return currentPrice;
	}
	
	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}
	@Override
	public String toString() {
		return "GameInfo [appId=" + appId + ", name=" + name + ", imgUrl=" + imgUrl + ", storeLink=" + storeLink
				+ ", dealsDesc=" + dealsDesc + ", detailLink=" + detailLink + ", currentPrice=" + currentPrice
				+ ", discount=" + discount + ", rating=" + rating + ", dealsEndsInUTC=" + dealsEndsInUTC
				+ ", dealsStartAtUTC=" + dealsStartAtUTC + ", releaseTimeUTC=" + releaseTimeUTC + ", dealsEndsIn="
				+ dealsEndsIn + ", dealsStartAt=" + dealsStartAt + ", releaseTime=" + releaseTime + "]";
	}


	
	
	
	
}
