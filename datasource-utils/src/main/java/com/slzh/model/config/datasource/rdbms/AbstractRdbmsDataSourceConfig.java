package com.slzh.model.config.datasource.rdbms;

import com.slzh.model.config.datasource.base.ConnectionConfig;
import com.slzh.model.config.datasource.base.AbstractDataSourceConfig;
import com.slzh.utils.ParamCheckUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 关系型数据库抽象数据源配置
 */
public abstract class AbstractRdbmsDataSourceConfig extends AbstractDataSourceConfig {
    private Logger log = LoggerFactory.getLogger(getClass());
    // 数据库连接地址前缀 eg: jdbc:mysql//
    private String urlPrefix;
    // 驱动全类名
    private String driverClassName;

    // ip
    private String ip;
    // 端口
    private int port;
    // 数据库名称 test_db 连接中的哪个库
    private String dbName;

    // 地址
    private String url;
    // 用户名
    private String user;
    // 密码
    private String password;
    // 最大连接数量 默认100
    private int maxConnectionsNum;
    // 最大等待时长 默认 60s
    private int maxWaitTimes;

    // 数据库版本
    private String  databaseVersion;

    // 驱动位置
    private String driverLocation;

    // 数据编码集
    private String dataEncode;

    // 目录
    private String catalog;

    // 日志级别
    private String logLevel;

    // 扩展属性
    private String extension;

    //目标字符集
    private String targetEncode;

    //源字符集
    private String sourceEncode;

    //数据格式
    private String dataFormat;

    // 连接配置文件
    private ConnectionConfig connectionConfig;

    protected String splicingUrl(String ip, int port, String dbName) {
        String url = urlPrefix + ip + ":" + port;
        if(dbName != null) {
            url += ("/" + dbName);
        }

        try {
            // 连接参数
            String connectParams = connectionConfig.splicingConnnectParam();
            if(!StringUtils.isBlank(connectParams)) {
                url += ("?" + connectParams);
            }
        } catch (IllegalAccessException e) {
            log.info(e.getMessage());
        }

        return url;
    }

    public String getDbName() {
        return dbName;
    }

    // 要提供一个无参的构造器，Spring这些框架底层都是利用反射 newInstance去创建对象的
    public AbstractRdbmsDataSourceConfig() {
    }

    public AbstractRdbmsDataSourceConfig(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(String databaseVersion) {
        this.databaseVersion = databaseVersion;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }

    public String getDataEncode() {
        return dataEncode;
    }

    public void setDataEncode(String dataEncode) {
        this.dataEncode = dataEncode;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getTargetEncode() {
        return targetEncode;
    }

    public void setTargetEncode(String targetEncode) {
        this.targetEncode = targetEncode;
    }

    public String getSourceEncode() {
        return sourceEncode;
    }

    public void setSourceEncode(String sourceEncode) {
        this.sourceEncode = sourceEncode;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }



    /**
     * 关系型数据库连接构造器
     * @param ip ip
     * @param port 端口
     * @param dbName 数据库名
     * @param user 用户
     * @param password 密码
     * @param connectionConfig 连接条件
     */
    public AbstractRdbmsDataSourceConfig(String ip,
                                         int port,
                                         String dbName,
                                         String user,
                                         String password,
                                         Integer maxConnectionsNum,
                                         Integer maxWaitTimes,
                                         ConnectionConfig connectionConfig
                                         ) {
        // 参数预检
        ParamCheckUtils.stringBlankCheck(ip, "ip");
        ParamCheckUtils.numZeroCheck(port, "port");
        ParamCheckUtils.stringBlankCheck(user, "user");
        ParamCheckUtils.stringBlankCheck(password, "password");
        if(maxConnectionsNum == null || maxConnectionsNum == 0) {
            maxConnectionsNum = 100;
        }
        if(maxWaitTimes == null || maxWaitTimes == 0) {
            maxWaitTimes = 60000;
        }
        this.maxConnectionsNum = maxConnectionsNum;
        this.maxWaitTimes = maxWaitTimes;
        this.ip = ip;
        this.port = port;
        this.dbName = dbName;
        this.connectionConfig = connectionConfig;
        this.user = user;
        this.password = password;
    }

    public ConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }

    public void setConnectionConfig(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }



    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxConnectionsNum() {
        return maxConnectionsNum;
    }

    public void setMaxConnectionsNum(int maxConnectionsNum) {
        this.maxConnectionsNum = maxConnectionsNum;
    }

    public int getMaxWaitTimes() {
        return maxWaitTimes;
    }

    public void setMaxWaitTimes(int maxWaitTimes) {
        this.maxWaitTimes = maxWaitTimes;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
