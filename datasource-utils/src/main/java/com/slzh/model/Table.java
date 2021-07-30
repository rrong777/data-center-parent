package com.slzh.model;

import java.util.List;

/**
 * 表
 */
public class Table {
    // 主键id
    private long id;
    // 表名
    private String name;
    // 字段 列
    private List<Column> columns;
    // 索引
    private List<Index> indexes;

    public Table(String name) {
        this.name = name;
    }

    public Table() {
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

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", columns=" + columns +
                ", indexes=" + indexes +
                '}';
    }
}
