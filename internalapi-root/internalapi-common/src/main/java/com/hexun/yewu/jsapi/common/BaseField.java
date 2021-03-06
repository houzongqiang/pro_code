/**
 * 
 */
package com.hexun.yewu.jsapi.common;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author zhoudong
 *
 */
public enum BaseField {
    SERVICE("service",64),
    VERSION("version",5),
    PARTNER_ID("partner_id",16),
    INPUT_CHARSET("_input_charset",10),
    SIGN("sign",1024),
    SIGN_TYPE("sign_type",10),
    MEMO("memo",1000),
    SERVICE_MARK("service_mark",64);

    private String code;
    private int    length;

    private BaseField(String code, int length) {
        this.code = code;
        this.length = length;

    }

    public String getCode() {
        return code;
    }

    public int getLength() {
        return length;
    }

    public static BaseField getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (BaseField item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
