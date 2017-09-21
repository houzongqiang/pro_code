package com.hexun.yewu.jsapi.service;

import javax.servlet.http.HttpServletRequest;

import com.hexun.framework.core.utils.BaseResponse;

public interface UserRightService {

	BaseResponse getUserRightList(HttpServletRequest req) throws Exception;
}
