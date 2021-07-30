package com.slzh.model.config.datasource.rdbms.mysql;

import com.slzh.model.config.datasource.base.ConnectionConfig;
import com.slzh.model.config.constant.CharSet;
import com.slzh.model.config.constant.TimeZone;
import com.slzh.model.config.constant.ZeroTimeBehavior;

/**
 * mysql连接配置
 */
public class MySQLConnectionConfig implements ConnectionConfig {
    // 是否使用unicode编码  默认true
    private boolean useUnicode = true;

    // 上海时间，或者 GMT%2B8 格林威治时间 + 8
    private String serverTimezone= TimeZone.SHANGHAI.getValue();

    // 默认字符集编码 utf-8
    private String characterEncoding= CharSet.UTF_8.getValue();

    // 0时间处理，默认转化为null
    private String zeroDateTimebehavior = ZeroTimeBehavior.CONVERT_TO_NULL.getValue();

    // 自动连接 如果连接闲置8小时 (8小时内没有进行数据库操作), mysql就会自动断开连接, 要重启tomcat. 需要自动重连
    private boolean autoReconnect = true;

    public boolean isUseUnicode() {
        return useUnicode;
    }

    public void setUseUnicode(boolean useUnicode) {
        this.useUnicode = useUnicode;
    }

    public String getServerTimezone() {
        return serverTimezone;
    }

    public void setServerTimezone(String serverTimezone) {
        this.serverTimezone = serverTimezone;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public String getZeroDateTimebehavior() {
        return zeroDateTimebehavior;
    }

    public void setZeroDateTimebehavior(String zeroDateTimebehavior) {
        this.zeroDateTimebehavior = zeroDateTimebehavior;
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }
}
