package com.slzh.model.config.datasource.base;

import java.lang.reflect.Field;

/**
 * 抽象sql连接配置
 */
public interface ConnectionConfig {
    /**
     * 根据自身配置拼接连接配置字符串（在url中使用）
     * 拼接连接条件
     * @return
     */
    default String splicingConnnectParam() throws IllegalAccessException {
        String res = "";
        Class cls = this.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(int i = 0; i < fields.length; i++) {
            // 获得属性
            Field field = fields[i];
            // 开启访问权限
            field.setAccessible(true);
            // 获得属性名
            String name = field.getName();
            Object value = field.get(this);
            if(value != null) {
                if(i != 0) {
                    res += "&";
                }
                res += (name + "=" + value);
            }
        }
        return res;
    }

}
