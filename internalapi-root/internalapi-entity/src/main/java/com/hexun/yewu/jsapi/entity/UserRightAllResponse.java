package com.hexun.yewu.jsapi.entity;

import java.sql.Timestamp;

import com.hexun.framework.core.mogodb.MongoField;

/**
 * 说明：用户权限列表分页 返回值
 */

public class UserRightAllResponse {
	
	@MongoField(name="UserID")
	private long userID;
	@MongoField(name="UserName")
	private String userName;
	@MongoField(name="ProductID")
	private int productID;	
	private String productName;
	private int productType;	
	@MongoField(name="PriceID")
	private int priceID;
	private String priceName;
	@MongoField(name="ToUserID")
	private String toUserID;
	@MongoField(name="BeginTime")
	private Timestamp beginTime;
	@MongoField(name="EndTime")
	private Timestamp endTime;
	//private Timestamp addTime;
	@MongoField(name="IsSus")
	private int isSus;
	private String ServerDay;

	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getPriceID() {
		return priceID;
	}
	public void setPriceID(int priceID) {
		this.priceID = priceID;
	}
	public String getPriceName() {
		return priceName;
	}
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	public String getToUserID() {
		return toUserID;
	}
	public void setToUserID(String toUserID) {
		this.toUserID = toUserID;
	}
	public Timestamp getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public int getIsSus() {
		return isSus;
	}
	public void setIsSus(int isSus) {
		this.isSus = isSus;
	}
	public String getServerDay() {
		return ServerDay;
	}
	public void setServerDay(String serverDay) {
		ServerDay = serverDay;
	}
	
	public int getProductType() {
		return productType;
	}
	public void setProductType(int productType) {
		this.productType = productType;
	}
}

