package com.slzh.model;

import com.slzh.model.constant.mysql.IndexType;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 数据库映射对象
 */
public class DataBase {
    // 主键id
    private long id;
    // 数据库名
    private String name;
    private List<Table> tables;

    public DataBase(String name) {
        this.name = name;
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

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }



    @Override
    public String toString() {
        return "DataBase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tables=" + tables +
                '}';
    }
}
