package com.hexun.yewu.jsapi.mapper.rc;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hexun.yewu.jsapi.entity.rc.InvestorInfo;
@Repository
public interface InvestorInfoMapper {
	public InvestorInfo selectByPrimaryKey(String id);

	public void insert(InvestorInfo dto);

	public List<InvestorInfo> selectByCondition(InvestorInfo dto);
}
