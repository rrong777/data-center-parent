package com.slzh.utils;

import org.springframework.util.Assert;

import java.util.Map;

public class ParamsUtils {
    public static void checkParams(Map<String, Object> params, String ... paramsName) {
        for(String paramName : paramsName) {
            Object param = params.get(paramName);
            Assert.notNull(param, "缺少必传参数：" + paramName);
        }
    }
}
