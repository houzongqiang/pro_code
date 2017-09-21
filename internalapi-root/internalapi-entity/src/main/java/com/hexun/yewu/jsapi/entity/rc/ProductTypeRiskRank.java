package com.hexun.yewu.jsapi.entity.rc;

import java.io.Serializable;

public class ProductTypeRiskRank implements Serializable {

	/**
	 * 产品类型风险等级信息
	 */
	private static final long serialVersionUID = 7543544733986524521L;
	private String id;
	private String productType;
	private String riskRank;
	private String riskRankValue;
	private String switchFlag;
	private String operator;
	private String createTime;
	private String updateTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getRiskRank() {
		return riskRank;
	}

	public void setRiskRank(String riskRank) {
		this.riskRank = riskRank;
	}

	public String getRiskRankValue() {
		return riskRankValue;
	}

	public void setRiskRankValue(String riskRankValue) {
		this.riskRankValue = riskRankValue;
	}

	public String getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(String switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
