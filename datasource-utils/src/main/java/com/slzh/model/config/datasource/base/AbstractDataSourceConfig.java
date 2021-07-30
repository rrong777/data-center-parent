package com.slzh.model.config.datasource.base;

/**
 * 抽象数据源配置
 */
public abstract class AbstractDataSourceConfig {
    // id
    private long id;
    // 本数据源配置名称
    private String name;

    private String desc;

    // 数据源类型
    private String sourceType;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
