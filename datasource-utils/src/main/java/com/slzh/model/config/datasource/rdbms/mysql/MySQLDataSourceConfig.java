package com.slzh.model.config.datasource.rdbms.mysql;

import com.slzh.model.config.datasource.rdbms.AbstractRdbmsDataSourceConfig;
import com.slzh.model.config.datasource.rdbms.mysql.MySQLConnectionConfig;

import javax.annotation.PostConstruct;

/**
 * mysql数据源配置
 */
public class MySQLDataSourceConfig extends AbstractRdbmsDataSourceConfig {
    // 声明数据源类型, 反射调用
    public static final String SOURCE_NAME = "MySQL";

    public static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    @Override
    public void setUrlPrefix() {
        this.urlPrefix = "jdbc:mysql://";
    }

    @Override
    public void setDriverClassName() {
        this.driverClassName = "com.mysql.cj.jdbc.Driver";
    }



    @PostConstruct
    public void init() {
        String url = splicingUrl(this.getIp(), this.getPort(), this.getDbName());
        super.setUrl(url);
    }

    // 如果提供无参的构造器SpringMVC就会调用无参的构造器
    public MySQLDataSourceConfig(String ip,
                                 int port,
                                 String dbName,
                                 String user,
                                 String password,
                                 Integer maxConnectionsNum,
                                 Integer maxWaitTimes) {
        // 调用抽象基类
        super(ip, port, dbName, user, password, maxConnectionsNum, maxWaitTimes, new MySQLConnectionConfig());
        this.setUrlPrefix();
        this.setDriverClassName();
//        String url = splicingUrl(ip, port, dbName);
//        super.setUrl(url);
    }

    public static void main(String[] args) {

    }
}
