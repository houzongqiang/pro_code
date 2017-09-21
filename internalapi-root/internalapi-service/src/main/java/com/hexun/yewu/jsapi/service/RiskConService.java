package com.hexun.yewu.jsapi.service;

import java.util.Map;

import com.hexun.framework.core.utils.BaseResponse;
import com.hexun.yewu.vo.riskControl.InvestorInfoVO;
import com.hexun.yewu.vo.riskControl.RiskUserInfoVO;

public interface RiskConService {
	/**
	 * 增加投资者基本信息
	 * 
	 * @param dataMap
	 * @return
	 */
	public BaseResponse addInvestorInfo(InvestorInfoVO vo) throws Exception;

	/**
	 * 判断是否已经加投资者基本信息
	 * 
	 * @param dataMap
	 * @return
	 */
	public BaseResponse isExistInvestor(RiskUserInfoVO vo) throws Exception;

	/**
	 * 上次评测信息
	 * 
	 * @param dataMap
	 * @return
	 */
	public BaseResponse lastTestInfo(RiskUserInfoVO vo) throws Exception;

	/**
	 * 增加评测信息
	 * 
	 * @param dataMap
	 * @return
	 */
	public BaseResponse addRiskTest(RiskUserInfoVO vo) throws Exception;

	/**
	 * 产品与用户风险等级差距
	 * 
	 * @param dataMap
	 * @return
	 */
	public BaseResponse rankDif(RiskUserInfoVO vo) throws Exception;
}
