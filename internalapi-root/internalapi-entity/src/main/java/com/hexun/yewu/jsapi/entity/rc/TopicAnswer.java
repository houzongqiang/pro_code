package com.hexun.yewu.jsapi.entity.rc;

import java.io.Serializable;

public class TopicAnswer implements Serializable {

	/**
	 * 答案对应分值
	 */
	private static final long serialVersionUID = -5248731695958704281L;
	private String id;
	private String topicId;
	private String option;
	private String version;
	private String createTime;
	private String updateTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
