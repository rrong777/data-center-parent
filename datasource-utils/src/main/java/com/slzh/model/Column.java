package com.slzh.model;

import com.slzh.model.constant.mysql.ColumnType;

/**
 * 列
 */
public class Column {
    // 主键id
    private long id;
    // 列名
    private String name;

    // 字段类型
    private String type;

    // 字段长度
    private int length;

    // 小数点位数
    private int decimalPoint;

    // 是否可空
    private boolean nullable;

    // 是否key
    private boolean key;

    // 注释
    private String notes;

    public Column() {
    }

    public Column(String name, String type, int length, int decimalPoint, boolean nullable, String notes, boolean key) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.decimalPoint = decimalPoint;
        this.nullable = nullable;
        this.notes = notes;
        this.key = key;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDecimalPoint() {
        return decimalPoint;
    }

    public void setDecimalPoint(int decimalPoint) {
        this.decimalPoint = decimalPoint;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Column{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", length=" + length +
                ", decimalPoint=" + decimalPoint +
                ", nullable=" + nullable +
                ", key=" + key +
                ", notes='" + notes + '\'' +
                '}';
    }
}
