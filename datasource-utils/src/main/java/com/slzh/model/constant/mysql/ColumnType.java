package com.slzh.model.constant.mysql;

/**
 * 内置的数据类型，我可以供你选择
 */
public enum ColumnType {
    // 浮点型
    DOUBLE,FLOAT, DECIMAL,
    // 整型
    TINYINT,INT,BIGINT,
    // 字符类型
    CHAR, VARCHAR,TEXT,
    // 时间类型
    DATETIME, DATE,TIMESTAMP
}
