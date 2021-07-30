package com.slzh.utils;

import org.apache.commons.lang3.StringUtils;

public class ParamCheckUtils {
    // 校验失败消息后缀
    public static final String VERIFICATION_FAILED_MSG_SUFFIX = "参数不合法!";

    /**
     * 校验完毕后做的事情
     * @param result
     * @param paramName
     */
    public static void checkNext(boolean result, String paramName) {
        if(result) {
            throw new IllegalArgumentException(paramName + VERIFICATION_FAILED_MSG_SUFFIX);
        }
    }

    /**
     * 字符串的非空校验
     * @param param 字符串参数
     * @param paramName 参数名
     */
    public static void stringBlankCheck(String param, String paramName) {
        boolean isBlank = StringUtils.isBlank(param);
        checkNext(isBlank, paramName);
    }


    /**
     * 数值的0值校验
     * @param param 整型参数
     * @param paramName 参数名
     */
    public static void numZeroCheck(Integer param, String paramName) {
        boolean isZero = (param == null || param == 0);
        checkNext(isZero, paramName);
    }
}
