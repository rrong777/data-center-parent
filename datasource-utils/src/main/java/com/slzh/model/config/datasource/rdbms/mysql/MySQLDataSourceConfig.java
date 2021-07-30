package com.slzh.model.config.datasource.rdbms.mysql;

import com.slzh.model.config.datasource.rdbms.AbstractRdbmsDataSourceConfig;
import com.slzh.model.config.datasource.rdbms.mysql.MySQLConnectionConfig;

/**
 * mysql数据源配置
 */
public class MySQLDataSourceConfig extends AbstractRdbmsDataSourceConfig {
    // 声明数据源类型, 反射调用
    public static final String SOURCE_NAME = "MySQL";

    public static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";


    public MySQLDataSourceConfig(String ip,
                                 int port,
                                 String dbName,
                                 String user,
                                 String password,
                                 Integer maxConnectionsNum,
                                 Integer maxWaitTimes) {
        // 调用抽象基类
        super(ip, port, dbName, user, password, maxConnectionsNum, maxWaitTimes, new MySQLConnectionConfig());
        super.setUrlPrefix("jdbc:mysql://");
        super.setDriverClassName("com.mysql.cj.jdbc.Driver");
        String url = splicingUrl(ip, port, dbName);
        super.setUrl(url);
    }

    public static void main(String[] args) {

    }
}
