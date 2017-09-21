package com.hexun.yewu.jsapi.entity.rc;

import java.io.Serializable;

public class InvestorInfo implements Serializable {

	/**
	 * 投资者基本信息
	 */
	private static final long serialVersionUID = 6186087247009324472L;
	private String id;
	private String userid;
	private String userName;
	private String sex;
	private String birthday;
	private String nationality;
	private String company;
	private String position;
	private String profession;
	private String educationalLevel;
	private String badRecord;
	private String identityCard;
	private String identityValidBegin;
	private String identityValidEnd;
	private String address;
	private String postalCode;
	private String email;
	private String telephone;
	private String mark;
	private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getEducationalLevel() {
		return educationalLevel;
	}

	public void setEducationalLevel(String educationalLevel) {
		this.educationalLevel = educationalLevel;
	}

	public String getBadRecord() {
		return badRecord;
	}

	public void setBadRecord(String badRecord) {
		this.badRecord = badRecord;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getIdentityValidBegin() {
		return identityValidBegin;
	}

	public void setIdentityValidBegin(String identityValidBegin) {
		this.identityValidBegin = identityValidBegin;
	}

	public String getIdentityValidEnd() {
		return identityValidEnd;
	}

	public void setIdentityValidEnd(String identityValidEnd) {
		this.identityValidEnd = identityValidEnd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
