package com.hexun.yewu.jsapi.mapper.rc;

import java.util.List;
import java.util.Map;

import com.hexun.yewu.jsapi.entity.rc.UserTestRecord;

public interface UserTestRecordMapper {
	/**
	 * 插入用户评测
	 * 
	 * @param dto
	 */
	public void insert(UserTestRecord dto);

	/**
	 * 根据条件查询用户评测记录
	 * 
	 * @param dto
	 * @return
	 */
	public List<UserTestRecord> selectByCondition(UserTestRecord dto);

	/**
	 * 更新以前评测无效
	 * 
	 * @param dto
	 */
	public void updateLastTestInvalidate(UserTestRecord dto);

	/**
	 * 根据主键查询评测记录
	 * 
	 * @param id
	 * @return
	 */
	public UserTestRecord selectByPrimaryKey(String id);

	/**
	 * 所有评分对应等级信息配置
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> loadRankScore(int score);

	/**
	 * 查询答案对应分值配置
	 * 
	 * @param version
	 * @return
	 */
	public List<Map<String, Object>> loadTopicAnswers(String version);
	/**
	 * 得到产品对应级别
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getProdcutTypeRiskRankByCondtion(Map<String, Object> map);
}
