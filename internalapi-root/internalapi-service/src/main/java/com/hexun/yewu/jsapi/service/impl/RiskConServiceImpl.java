package com.hexun.yewu.jsapi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hexun.framework.core.redis.RedisUtils;
import com.hexun.framework.core.utils.BaseResponse;
import com.hexun.framework.core.utils.RespEnum;
import com.hexun.framework.core.utils.StringUtils;
import com.hexun.yewu.jsapi.common.Constants;
import com.hexun.yewu.jsapi.entity.rc.InvestorInfo;
import com.hexun.yewu.jsapi.entity.rc.UserTestRecord;
import com.hexun.yewu.jsapi.mapper.rc.InvestorInfoMapper;
import com.hexun.yewu.jsapi.mapper.rc.UserTestRecordMapper;
import com.hexun.yewu.jsapi.service.RiskConService;
import com.hexun.yewu.vo.riskControl.InvestorInfoVO;
import com.hexun.yewu.vo.riskControl.RiskUserInfoVO;

@Service
public class RiskConServiceImpl implements RiskConService {
	@Resource
	private InvestorInfoMapper investorInfoMapper;
	@Resource
	private UserTestRecordMapper userTestRecordMapper;
	/**
	 * 用户风险名称对应
	 */
	private static Map<String, String> riskRank = null;
	/**
	 * 产品类型风险名称对应
	 */
	private static Map<String, String> productTypeRiskRank=null;
	static {
		riskRank = new HashMap<String, String>();
		riskRank.put("DEFEND", "保守型");
		riskRank.put("CAREFULLY", "谨慎型");
		riskRank.put("STEADINESS", "稳健型");
		riskRank.put("POSITIVE", "积极型");
		riskRank.put("RADICAL", "激进型");
		productTypeRiskRank=new HashMap<String, String>();
		productTypeRiskRank.put("LOW", "低风险");
		productTypeRiskRank.put("MID_LOW", "中等偏低风险");
		productTypeRiskRank.put("MID", "中风险");
		productTypeRiskRank.put("MID_HIGH", "中等偏高");
		productTypeRiskRank.put("HIGH", "高风险");
	}
   
	@Override
	public BaseResponse addInvestorInfo(InvestorInfoVO vo) {
		BaseResponse response = new BaseResponse();

		InvestorInfo dto = new InvestorInfo();
		dto.setUserid(vo.getUserid());
		if (CollectionUtils.isNotEmpty(investorInfoMapper.selectByCondition(dto))) {
			response.setCharset("UTF-8");
			response.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			response.setErrorData("已存在用户信息！");
			return response;
		}
		this.saveInvestor(vo);
		response.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
		return response;
	}


	public void saveInvestor(InvestorInfoVO vo) {
		InvestorInfo dto = new InvestorInfo();
		dto.setUserid(vo.getUserid());
		dto.setUserName(vo.getUserName());
		dto.setAddress(vo.getAddress());
		dto.setSex(vo.getSex());
		dto.setBirthday(vo.getBirthday());
		dto.setNationality(vo.getNationality());
		dto.setCompany(vo.getCompany());
		dto.setPosition(vo.getPosition());
		dto.setProfession(vo.getProfession());
		dto.setBadRecord(vo.getBadRecord());
		dto.setIdentityCard(vo.getIdentityCard());
		dto.setTelephone(vo.getTelephone());
		dto.setEducationalLevel(vo.getEducationalLevel());
		dto.setIdentityValidBegin(
				StringUtils.isBlank(vo.getIdentityValidBegin()) ? null : vo.getIdentityValidBegin());
		dto.setIdentityValidEnd(
				StringUtils.isBlank(vo.getIdentityValidEnd()) ? null : vo.getIdentityValidEnd());
		dto.setPostalCode(vo.getPostalCode());
		dto.setEmail(vo.getEmail());
		dto.setMark(vo.getPhone());
		investorInfoMapper.insert(dto);
	}

	@Override
	public BaseResponse isExistInvestor(RiskUserInfoVO vo) {
		BaseResponse response = new BaseResponse();
		InvestorInfo dto = new InvestorInfo();
		dto.setUserid(vo.getUserid());
		List<InvestorInfo> list = this.investorInfoMapper.selectByCondition(dto);
		JSONObject json = new JSONObject();
		if (CollectionUtils.isEmpty(list)) {
			json.put("isExist", "false");
		} else {
			json.put("isExist", "true");
		}
		response.setResult(json);
		response.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
		return response;
	}

	@Override
	public BaseResponse lastTestInfo(RiskUserInfoVO vo) throws Exception {
		BaseResponse response = new BaseResponse();
		String userid = vo.getUserid();
		UserTestRecord record = this.getUserRecord(userid);
		if (record == null) {
			response.setErrorData("没有找到用户风险评测信息！");
			response.setCharset("UTF-8");
			response.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
		} else {
			JSONObject json = (JSONObject) JSON.toJSON(record);
			json.remove("id");
			json.remove("rank");
			json.remove("flag");
			json.remove("createTime");
			json.put("date", record.getCreateTime());
			response.setResult(json);
			response.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
		}
		return response;
	}


	public boolean isRegist(String userid) {
		InvestorInfo dto = new InvestorInfo();
		dto.setUserid(userid);
		if (CollectionUtils.isNotEmpty(investorInfoMapper.selectByCondition(dto))) {
			return true;
		}
		return false;
	}

	@Override
	public BaseResponse addRiskTest( RiskUserInfoVO vo) {
		BaseResponse response = new BaseResponse();

		if (!isRegist(vo.getUserid())) {
			response.setCharset("UTF-8");
			response.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			response.setErrorData("用户未完善投资者基本信息！");
			return response;
		}
		String userid = vo.getUserid();
		// 查出所有题目对应分数
		List<Map<String, Object>> topAns = this.userTestRecordMapper.loadTopicAnswers(vo.getVersion());
		// 计算分值或类型
		Map<Integer, Object> topAnsMap = this.list2Map(topAns);
		Integer userScore = getTestScore(topAnsMap, vo.getAnswerMap());
		// 根据分值查询等级类型；
		List<Map<String, Object>> rankMap = this.userTestRecordMapper.loadRankScore(userScore);
		// 保存评测信息
		this.saveTest(vo, userScore, rankMap.get(0));
		// 存入或更新redis
		String lastTestDate = getLastTestDateFromRedis(userid, vo.getVersion());

		JSONObject result = createResponseJson(rankMap, userid, userScore, lastTestDate);
		response.setResult(result);
		response.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));

		return response;
	}

	public Map<Integer, Object> list2Map(List<Map<String, Object>> list) {
		if (!CollectionUtils.isEmpty(list)) {
			Map<Integer, Object> allAnswer = new HashMap<Integer, Object>();
			for (Map<String, Object> entity : list) {
				Map<String, Object> optionAndValue = new HashMap<String, Object>();
				Integer topic_id = Integer.valueOf(entity.get("topic_id").toString());
				String options = entity.get("option").toString();
				String optionList[] = options.split(";");
				for (String option : optionList) {
					optionAndValue.put(option.split("-")[0], option.split("-")[1]);
				}
				allAnswer.put(topic_id, optionAndValue);
			}
			return allAnswer;
		}
		return null;
	}

	public int getTestScore(Map<Integer, Object> topAnsMap, Map<Integer, String> userAnswer) {
		int scoreSum = 0;
		Set set = userAnswer.keySet();
		Iterator<?> it = set.iterator();
		while (it.hasNext()) {
			Integer topid = Integer.valueOf(it.next().toString());
			String userCheck = userAnswer.get(topid);
			Map<String, Object> anserScore = (Map) topAnsMap.get(topid);
			Integer checkScore = Integer.valueOf(anserScore.get(userCheck).toString());
			scoreSum += checkScore;
		}
		return scoreSum;
	}

	/**
	 * 返回
	 * 
	 * @param userid
	 * @param version
	 * @return
	 */
	public JSONObject createResponseJson(List<Map<String, Object>> rankMap, String userid, Integer score,
			String lastTestDate) {
		JSONObject jo = new JSONObject();
		jo.put("userid", userid);
		jo.put("score", score);
		jo.put("riskRankStr", riskRank.get(rankMap.get(0).get("risk_rank")));
		jo.put("lastTestDate", lastTestDate);

		return jo;

	}

	/**
	 * 保存评测并入redis
	 * 
	 * @param userid
	 * @param version
	 * @return
	 */
	public void saveTest(RiskUserInfoVO vo, Integer score, Map rankMap) {

		UserTestRecord re = new UserTestRecord();
		re.setUserid(vo.getUserid());
		re.setAnswers(vo.getAnswers());
		re.setScore(String.valueOf(score));
		re.setFlag("1");
		re.setRank((Integer) rankMap.get("risk_rank_value"));
		re.setRankStr(riskRank.get(rankMap.get("risk_rank")));
		re.setVersion(vo.getVersion());
		re.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		this.userTestRecordMapper.updateLastTestInvalidate(re);
		this.userTestRecordMapper.insert(re);

		RedisUtils.set(Constants.USER_LASTTEST_INFO_KEY + vo.getUserid(), JSON.toJSONString(re));

	}

	/**
	 * 最近一次的评测时间
	 * 
	 * @param userid
	 * @param version
	 * @return
	 */
	public String getLastTestDateFromRedis(String userid, String version) {
		String lastTestDate = null;
		String json = RedisUtils.get(Constants.USER_LASTTEST_INFO_KEY + userid);
		if (json != null) {
			UserTestRecord record = JSON.parseObject(json, UserTestRecord.class);
			return record.getCreateTime();
		}
		return lastTestDate;
	}

	@Override
	public BaseResponse rankDif(RiskUserInfoVO vo) {
		BaseResponse response = new BaseResponse();
		String producttype = vo.getProducttype();
		String userid = vo.getUserid();
		UserTestRecord record = this.getUserRecord(userid);
		if (record == null) {
			JSONObject jo = new JSONObject();
			jo.put("isDoTest", "false");
			response.setResult(jo);
			response.setCharset("UTF-8");
			response.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
			return response;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productType", producttype);
		List<Map<String, Object>> prodcutTypeRank = this.userTestRecordMapper.getProdcutTypeRiskRankByCondtion(map);
		if (CollectionUtils.isEmpty(prodcutTypeRank)) {
			response.setErrorData("没有对应产品类型风险级别设置！");
			response.setCharset("UTF-8");
			response.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			return response;
		}

		int userRankValue = record.getRank();
		int proTypeRankValue = (Integer) prodcutTypeRank.get(0).get("risk_rank_value");
		int def = proTypeRankValue - userRankValue;// 正数表示与产品级别的差距，方便页面展示；
		JSONObject jo = new JSONObject();
		jo.put("isDoTest", "true");
		jo.put("productRankStr", productTypeRiskRank.get(prodcutTypeRank.get(0).get("risk_rank")));
		jo.put("riskRankStr", record.getRankStr());
		jo.put("rankDif", def);
		response.setResult(jo);
		response.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
		return response;
	}

	/**
	 * 从redis 取不到评测记录从库里取
	 * 
	 * @param userid
	 * @return
	 */
	public UserTestRecord getUserRecord(String userid) {
		UserTestRecord record = null;
		String json = RedisUtils.get(Constants.USER_LASTTEST_INFO_KEY + userid);
		if (json != null) {
			record = JSON.parseObject(json, UserTestRecord.class);
		} else {
			UserTestRecord	dto = new UserTestRecord();
			dto.setUserid(userid);
			List<UserTestRecord> records = this.userTestRecordMapper.selectByCondition(dto);

			if (!CollectionUtils.isEmpty(records)) {
				record= new UserTestRecord();
				int userScore = Integer.parseInt(records.get(0).getScore());
				List<Map<String, Object>> rankMap = this.userTestRecordMapper.loadRankScore(userScore);
				record.setRankStr(riskRank.get(rankMap.get(0).get("risk_rank")));
				record.setRank(Integer.parseInt((String) rankMap.get(0).get("risk_rank_value")));
				RedisUtils.set(Constants.USER_LASTTEST_INFO_KEY + userid, JSON.toJSONString(record));
			}
		}
		return record;
	}

}
