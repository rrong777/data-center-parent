package com.slzh.model;

import com.slzh.model.constant.mysql.IndexMethod;
import com.slzh.model.constant.mysql.IndexType;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引
 */
public class Index {
    // 主键id
    private long id;
    // 索引名
    private String name;
    // 索引列
    private List<String> columns = new ArrayList<>();

    //
    private boolean unique;

    // 索引类型
    private String type;

    public Index() {
    }

    public Index(String name, boolean unique, String type) {
        this.name = name;
        this.unique = unique;
        this.type = type;
    }

    public boolean getUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
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

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public boolean isUnique() {
        return unique;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Index{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", columns=" + columns +
                ", unique=" + unique +
                ", type='" + type + '\'' +
                '}';
    }
}
