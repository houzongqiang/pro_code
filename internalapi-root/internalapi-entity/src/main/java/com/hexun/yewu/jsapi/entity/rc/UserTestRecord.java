package com.hexun.yewu.jsapi.entity.rc;

import java.io.Serializable;

public class UserTestRecord implements Serializable {

	/**
	 * 用户评测记录
	 */
	private static final long serialVersionUID = -7380829072865719190L;

	private String id;
	private String userid;
	private String answers;
	private String score;
	private String flag;
	private String version;
	private String createTime;
	private String rankStr;
	private Integer rank;

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

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRankStr() {
		return rankStr;
	}

	public void setRankStr(String rankStr) {
		this.rankStr = rankStr;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
