package com.slzh.model.config.constant;

/**
 * 枚举类也是一个类，只是比较特殊罢了
 * mysql 0时间处理 (Mysql无法处理  0000-00-00 00:00:00这样子的时间，遇到这种处理有几种方式)
 */
public enum ZeroTimeBehavior {
    // 创建
    EXCEPTION("exception"), CONVERT_TO_NULL("convertToNull"), ROUND("round");

    private final String value;
    private ZeroTimeBehavior(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }

    public static void main(String[] args) {
        System.out.println(ZeroTimeBehavior.EXCEPTION.getValue());
    }
}
