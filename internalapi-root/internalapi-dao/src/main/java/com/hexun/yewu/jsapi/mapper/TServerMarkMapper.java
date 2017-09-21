package com.hexun.yewu.jsapi.mapper;

import com.hexun.yewu.jsapi.entity.TServerMark;

public interface TServerMarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TServerMark record);

    int insertSelective(TServerMark record);

    TServerMark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TServerMark record);

    int updateByPrimaryKey(TServerMark record);

	TServerMark findServerMark(String serverMark);
}