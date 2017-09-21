package com.hexun.yewu.jsapi.entity.rc;

import java.io.Serializable;

public class RankScore implements Serializable {

	/**
	 * 评测分数对应等级表
	 */
	private static final long serialVersionUID = -6446233484453100815L;

	private String id;
	private String riskRank;
	private String risk_rank_Value;
	private String low;
	private String high;
	private String operator;
	private String createTime;
	private String updateTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRiskRank() {
		return riskRank;
	}

	public void setRiskRank(String riskRank) {
		this.riskRank = riskRank;
	}

	public String getRisk_rank_Value() {
		return risk_rank_Value;
	}

	public void setRisk_rank_Value(String risk_rank_Value) {
		this.risk_rank_Value = risk_rank_Value;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
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
