package com.slzh.model.datasource;

import java.util.Date;

public class DataSourceConfig {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.id
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private Long id;

    private String ip;

    private int port;

    private String databaseName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.dataSourceName
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String dataSourceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.description
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.connectionAddress
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String connectionAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.userName
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.passWord
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String passWord;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.dataSourceType
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private Integer dataSourceType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.databaseVersion
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String databaseVersion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.dataDriverName
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String dataDriverName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.dataDriverVersion
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String dataDriverVersion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.driverLocation
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String driverLocation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.dataEncode
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String dataEncode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.catalog
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String catalog;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.maxConnection
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private Integer maxConnection;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.maxWaitTime
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private Long maxWaitTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.logLevel
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String logLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.extension
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String extension;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.method
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String method;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.targetEncode
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String targetEncode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.sourceEncode
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String sourceEncode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.dataFormat
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String dataFormat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.remark
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.createTime
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.userId
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_config_data_source.updateTime
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.id
     *
     * @return the value of t_config_data_source.id
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.id
     *
     * @param id the value for t_config_data_source.id
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.dataSourceName
     *
     * @return the value of t_config_data_source.dataSourceName
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.dataSourceName
     *
     * @param dataSourceName the value for t_config_data_source.dataSourceName
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName == null ? null : dataSourceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.description
     *
     * @return the value of t_config_data_source.description
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.description
     *
     * @param description the value for t_config_data_source.description
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.connectionAddress
     *
     * @return the value of t_config_data_source.connectionAddress
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getConnectionAddress() {
        return connectionAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.connectionAddress
     *
     * @param connectionAddress the value for t_config_data_source.connectionAddress
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setConnectionAddress(String connectionAddress) {
        this.connectionAddress = connectionAddress == null ? null : connectionAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.userName
     *
     * @return the value of t_config_data_source.userName
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.userName
     *
     * @param userName the value for t_config_data_source.userName
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.passWord
     *
     * @return the value of t_config_data_source.passWord
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.passWord
     *
     * @param passWord the value for t_config_data_source.passWord
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.dataSourceType
     *
     * @return the value of t_config_data_source.dataSourceType
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public Integer getDataSourceType() {
        return dataSourceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.dataSourceType
     *
     * @param dataSourceType the value for t_config_data_source.dataSourceType
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setDataSourceType(Integer dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.databaseVersion
     *
     * @return the value of t_config_data_source.databaseVersion
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getDatabaseVersion() {
        return databaseVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.databaseVersion
     *
     * @param databaseVersion the value for t_config_data_source.databaseVersion
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setDatabaseVersion(String databaseVersion) {
        this.databaseVersion = databaseVersion == null ? null : databaseVersion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.dataDriverName
     *
     * @return the value of t_config_data_source.dataDriverName
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getDataDriverName() {
        return dataDriverName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.dataDriverName
     *
     * @param dataDriverName the value for t_config_data_source.dataDriverName
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setDataDriverName(String dataDriverName) {
        this.dataDriverName = dataDriverName == null ? null : dataDriverName.trim();
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

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.dataDriverVersion
     *
     * @return the value of t_config_data_source.dataDriverVersion
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getDataDriverVersion() {
        return dataDriverVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.dataDriverVersion
     *
     * @param dataDriverVersion the value for t_config_data_source.dataDriverVersion
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setDataDriverVersion(String dataDriverVersion) {
        this.dataDriverVersion = dataDriverVersion == null ? null : dataDriverVersion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.driverLocation
     *
     * @return the value of t_config_data_source.driverLocation
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getDriverLocation() {
        return driverLocation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.driverLocation
     *
     * @param driverLocation the value for t_config_data_source.driverLocation
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation == null ? null : driverLocation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.dataEncode
     *
     * @return the value of t_config_data_source.dataEncode
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getDataEncode() {
        return dataEncode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.dataEncode
     *
     * @param dataEncode the value for t_config_data_source.dataEncode
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setDataEncode(String dataEncode) {
        this.dataEncode = dataEncode == null ? null : dataEncode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.catalog
     *
     * @return the value of t_config_data_source.catalog
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.catalog
     *
     * @param catalog the value for t_config_data_source.catalog
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog == null ? null : catalog.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.maxConnection
     *
     * @return the value of t_config_data_source.maxConnection
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public Integer getMaxConnection() {
        return maxConnection;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.maxConnection
     *
     * @param maxConnection the value for t_config_data_source.maxConnection
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setMaxConnection(Integer maxConnection) {
        this.maxConnection = maxConnection;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.maxWaitTime
     *
     * @return the value of t_config_data_source.maxWaitTime
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public Long getMaxWaitTime() {
        return maxWaitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.maxWaitTime
     *
     * @param maxWaitTime the value for t_config_data_source.maxWaitTime
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setMaxWaitTime(Long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.logLevel
     *
     * @return the value of t_config_data_source.logLevel
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.logLevel
     *
     * @param logLevel the value for t_config_data_source.logLevel
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel == null ? null : logLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.extension
     *
     * @return the value of t_config_data_source.extension
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getExtension() {
        return extension;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.extension
     *
     * @param extension the value for t_config_data_source.extension
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.method
     *
     * @return the value of t_config_data_source.method
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getMethod() {
        return method;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.method
     *
     * @param method the value for t_config_data_source.method
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.targetEncode
     *
     * @return the value of t_config_data_source.targetEncode
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getTargetEncode() {
        return targetEncode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.targetEncode
     *
     * @param targetEncode the value for t_config_data_source.targetEncode
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setTargetEncode(String targetEncode) {
        this.targetEncode = targetEncode == null ? null : targetEncode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.sourceEncode
     *
     * @return the value of t_config_data_source.sourceEncode
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getSourceEncode() {
        return sourceEncode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.sourceEncode
     *
     * @param sourceEncode the value for t_config_data_source.sourceEncode
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setSourceEncode(String sourceEncode) {
        this.sourceEncode = sourceEncode == null ? null : sourceEncode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.dataFormat
     *
     * @return the value of t_config_data_source.dataFormat
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getDataFormat() {
        return dataFormat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.dataFormat
     *
     * @param dataFormat the value for t_config_data_source.dataFormat
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat == null ? null : dataFormat.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.remark
     *
     * @return the value of t_config_data_source.remark
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.remark
     *
     * @param remark the value for t_config_data_source.remark
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.createTime
     *
     * @return the value of t_config_data_source.createTime
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.createTime
     *
     * @param createTime the value for t_config_data_source.createTime
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.userId
     *
     * @return the value of t_config_data_source.userId
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.userId
     *
     * @param userId the value for t_config_data_source.userId
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_config_data_source.updateTime
     *
     * @return the value of t_config_data_source.updateTime
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_config_data_source.updateTime
     *
     * @param updateTime the value for t_config_data_source.updateTime
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}