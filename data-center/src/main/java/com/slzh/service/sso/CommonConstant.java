package com.slzh.service.sso;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 常量类
 * @author : cwp 2020-5-13 16:51
 */
public final class CommonConstant {

    /**
     * 构造器私有化
     */
    private CommonConstant() {

    }

    /**
     * 应用类型常量类
     */
    public static final class ApplicationTypeConstant {
        /**
         * BS架构
         */
        public static final String BS = "1";
        /**
         * CS架构
         */
        public static final String CS = "2";
    }

    /**
     * 授权码key
     */
    public static final class LicenseCodeKey {
        /**
         * 外部key
         */
        public static final String EXTERNAL_KEY = "Ropeok2020@external";
        /**
         * 内部key
         */
        public static final String INTERNAL_KEY = "Ropeok2020@internal";
    }

    
}
