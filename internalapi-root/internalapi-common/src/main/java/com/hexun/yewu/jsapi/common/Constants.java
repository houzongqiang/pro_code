package com.hexun.yewu.jsapi.common;

/**
 * 常量
 * @author zhoudong
 *
 */
public class Constants {

    public static final String  charset_iso_latin    = "ISO-8859-1";
    public static final String  charset_utf_8        = "UTF-8";
    public static final String  charset_gbk          = "GBK";
    public static final String  charset_gb2312       = "GB2312";
    
    
    /**
     * redis key
     */
    public static final String  DPLUSAPI_REQ_COUNT_KEY       = "DPLUSAPI_REQ_COUNT:";
    /**
     * 用户Key
     */
    public static final String  DPLUS_USER_INFO_KEY       = "DPLUS:USER_INFO:";
    /**
     * 权限Key
     */
    public static final String  DPLUS_PERMISSION_INFO_KEY       = "DPLUS:PERMISSION_INFO:";
    /**
     * 价格策略权限Key
     */
    public static final String  DPLUS_PERMISSION_PRICE_INFO_KEY       = "DPLUS:PERMISSION_PRICE_INFO:";
    
    /**
     * 产品KEY
     */
    public static final String  PRODUCT_KEY       = "product:";
    /**
	 * 增加投资者sevice 名称
	 */
	public static final String ADDINVERSTORIFNO = "addInvestorInfo";
	/**
	 * 增加风险评测sevice 名称
	 */
	public static final String ADDRISKTEST = "addRiskTest";
	/**
	 * 查询用户最近一次风测信息 sevice 名称
	 */
	public static final String LASTTESTINFO = "lastTestInfo";

	/**
	 * 判断用户是否已注册投资者基本信息 sevice 名称
	 */
	public static final String ISEXISTINVESTOR = "isExistInvestor";
	/**
	 * 用户风测等级与所购产品等级差距返回数值 sevice 名称
	 */
	public static final String RANKDIF = "rankDif";
 
	/**
	 * 最近一次评测信息REIDS KEY
	 */
	public static final String USER_LASTTEST_INFO_KEY="RC:USER_LASTTEST_INFO:";
	/**
	 * 用户领取的ipone8 购买码
	 */	
	public static final String USER_RECEIVE_IPONE8_CODE="COUPON:IPONE8CODE:";	
	/**
	 * 当前已领数量
	 */
	public static final String CUR_USED_IPONE8_CODE_COUNT="COUPON:CUR_USED_IPONE8_CODE_COUNT:";
}
