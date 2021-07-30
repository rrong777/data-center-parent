package com.slzh.adapter;

import com.slzh.model.config.datasource.base.AbstractDataSourceConfig;
import com.slzh.model.config.datasource.rdbms.AbstractRdbmsDataSourceConfig;
import com.slzh.model.config.datasource.rdbms.mysql.MySQLDataSourceConfig;
import com.slzh.model.datasource.DataSourceConfig;

import java.util.Locale;

public class RdbmsDataSourceConfigAdapter {
    private int getDataSourceTypeInt(String sourceType) {
        sourceType = sourceType.toUpperCase(Locale.ROOT);
        // 默认mysql
        int sourceTypeInt = 1;
        switch (sourceType) {
            case "mysql":
                sourceTypeInt = 1;
            case "oracle":
                sourceTypeInt = 2;
            case "postgresql":
                sourceTypeInt = 3;
            case "excel":
                sourceTypeInt = 4;
            case "txt":
                sourceTypeInt = 5;
        }
        return sourceTypeInt;
    }
    private String getDataSourceType(int sourceTypeInt) {

        // 默认mysql
        String sourceType = "MySQL";
        switch (sourceTypeInt) {
            case 1:
                sourceType = "MySQL";
            case 2:
                sourceType = "Oracle";
            case 3:
                sourceType = "PostgreSQL";
            case 4:
                sourceType = "Excel";
            case 5:
                sourceType = "Txt";
        }
        return sourceType;
    }
    public DataSourceConfig getDataSourceConfig(AbstractRdbmsDataSourceConfig config){
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setId(config.getId());
        dataSourceConfig.setDataSourceName(config.getName());
        dataSourceConfig.setDescription(config.getDesc());
        dataSourceConfig.setConnectionAddress(config.getUser());
        dataSourceConfig.setUserName(config.getUser());
        dataSourceConfig.setPassWord(config.getUser());
        dataSourceConfig.setDataSourceType(getDataSourceTypeInt(config.getSourceType()));
        dataSourceConfig.setDatabaseVersion(config.getDatabaseVersion());
        dataSourceConfig.setDataDriverName(config.getDriverClassName());
        dataSourceConfig.setDriverLocation(config.getDriverLocation());
        dataSourceConfig.setDataEncode(config.getDataEncode());
        dataSourceConfig.setCatalog(config.getCatalog());
        dataSourceConfig.setMaxConnection(config.getMaxConnectionsNum());
        dataSourceConfig.setMaxWaitTime(Long.valueOf(config.getMaxWaitTimes()));
        dataSourceConfig.setLogLevel(config.getLogLevel());
        dataSourceConfig.setExtension(config.getExtension());
        dataSourceConfig.setTargetEncode(config.getTargetEncode());
        dataSourceConfig.setSourceEncode(config.getSourceEncode());
        dataSourceConfig.setDataFormat(config.getDataFormat());
        return dataSourceConfig;
    }
    public AbstractRdbmsDataSourceConfig getMySQLDataSourceConfig(DataSourceConfig config){
        Integer maxWaitTimes = (config.getMaxWaitTime() == null ? new Long(60000L) : config.getMaxWaitTime()).intValue();
        Integer maxConnectionNum = (config.getMaxConnection() == null ? 100 : config.getMaxConnection());
        MySQLDataSourceConfig dataSourceConfig = new MySQLDataSourceConfig(config.getIp(), config.getPort(),
                config.getDatabaseName(),config.getUserName(), config.getPassWord(), maxConnectionNum, maxWaitTimes);

        dataSourceConfig.setId(config.getId());
        dataSourceConfig.setName(config.getDataSourceName());
        dataSourceConfig.setDesc(config.getDescription());
        dataSourceConfig.setUrl(config.getConnectionAddress());
        dataSourceConfig.setSourceType(getDataSourceType(config.getDataSourceType()));
        dataSourceConfig.setDatabaseVersion(config.getDatabaseVersion());
        dataSourceConfig.setDriverLocation(config.getDriverLocation());
        dataSourceConfig.setDataEncode(config.getDataEncode());
        dataSourceConfig.setCatalog(config.getCatalog());
        dataSourceConfig.setLogLevel(config.getLogLevel());
        dataSourceConfig.setExtension(config.getExtension());
        dataSourceConfig.setTargetEncode(config.getTargetEncode());
        dataSourceConfig.setSourceEncode(config.getSourceEncode());
        dataSourceConfig.setDataFormat(config.getDataFormat());
        return dataSourceConfig;
    }

}
