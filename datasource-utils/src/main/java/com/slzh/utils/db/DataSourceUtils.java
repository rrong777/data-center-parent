package com.slzh.utils.db;


import com.slzh.model.Column;
import com.slzh.model.DataBase;
import com.slzh.model.Index;
import com.slzh.model.Table;
import com.slzh.model.config.datasource.rdbms.mysql.MySQLDataSourceConfig;
import com.slzh.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.*;

public class DataSourceUtils {
    // 异常描述映射
    private static final Map<String, String> EXCEPTION_DESC_MAP = new HashMap<>();

    // 数据源配置包名
    private static final String DATASOURCE_CONFIG_BASE_PACKAGE_NAME = "com.slzh.model.config";
    // 数据源基类名
    private static final String DATASOURCE_BASE_CLASS_NAME = "com.slzh.model.config.datasource.base.AbstractDataSourceConfig";

    private static final Logger log = LoggerFactory.getLogger(DataSourceUtils.class);

    // 内置数据源有几种
    private static List<String> dataSources = new ArrayList<>();

    private static final Map<Integer, String> INDEX_TYPE_MAP = new HashMap<>();


    static {
        INDEX_TYPE_MAP.put(1, "tableIndexStatistic");
        INDEX_TYPE_MAP.put(2, "tableIndexClustered");
        INDEX_TYPE_MAP.put(3, "tableIndexHashed");
        INDEX_TYPE_MAP.put(4, "tableIndexOther");
        EXCEPTION_DESC_MAP.put("Could not create connection to database server. Attempted reconnect 3 times. Giving up.", "数据库连接超时！请确认配置无误");
        // 初始化
        setDataSources();
    }

    public static List<String> getDataSources() {
        return dataSources;
    }

    public static void setDataSources() {
        List<String> dataSources = new ArrayList<>();
        try {
            // 加载配置基类
            Class dataSourceBaseClass = Class.forName(DATASOURCE_BASE_CLASS_NAME);
            // 加载配置包下的所有基类实现类（一个实现类就是一种数据源）
            Set<Class<?>> classSet = ClassUtil.getClassSet(DATASOURCE_CONFIG_BASE_PACKAGE_NAME);
            for (Class<?> aClass : classSet) {
                // 过滤接口和抽象类
                if (Modifier.isAbstract(aClass.getModifiers()) || Modifier.isInterface(aClass.getModifiers())) {
                    continue;
                }
                boolean assignableFrom = dataSourceBaseClass.isAssignableFrom(aClass);
                if (assignableFrom) {
                    Field sourceName = aClass.getDeclaredField("SOURCE_NAME");
                    sourceName.setAccessible(true);
                    Object o = sourceName.get(aClass);
                    System.out.println(o);
                    dataSources.add((String) o);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DataSourceUtils.dataSources = dataSources;
    }





    /**
     * 获得连接对象
     *
     * @return
     * @throws ClassNotFoundException 驱动未找到异常
     * @throws SQLException           连接异常
     */
    public static Connection getConnection(String driverClassName, String url, String user, String password) throws ClassNotFoundException, SQLException {
        // 加载数据库驱动
        Class.forName(driverClassName);
        // 获得连接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // 获取内置的所有数据源类型
//        List<String> allDataSource = getAllDataSourceType();

        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String ip = "192.168.3.40";
        int port = 3306;
        String dbName = "data_schedule";
        String url = "jdbc:mysql://192.168.3.40:3306?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
        String user = "root";
        String password = "slzh@mysql2020";
        Connection connection = getConnection(MySQLDataSourceConfig.DRIVER_CLASS_NAME, url, user, password);
        DatabaseMetaData metaData = connection.getMetaData();
        // 测试获得所有数据库名
//        List<String> databaseName = getAllDbNamefromConnection(connection);
//
//        // 测试获得数据库下所有表名
//        getTablesNameFromDb(connection, null);
//        DatabaseMetaData dm = connection.getMetaData();
        // 获得表的列信息
//        getTableColumns(dm, "data_schedule", "t_config_data_source");

        // 获得主键信息
//        getPrimaryKey(dm, "data_schedule", "t_config_data_table");

//        getIndexes(metaData, "data_schedule", "t_config_data_source");

        List<DataBase> connectionDetails = getConnectionDetails(connection);
        System.out.println(connectionDetails);
    }

    public static List<Index> getIndexes(DatabaseMetaData dbmd, String catalog, String table) {
        Map<String, Index> indexesMap = new HashMap<>();
        try (ResultSet rs = dbmd.getIndexInfo(catalog, null, table, false, false);){
            while(rs.next()) {
                String name = rs.getString("INDEX_NAME");
                Index index = indexesMap.get(name);
                // 索引列名
                String columnName = rs.getString("COLUMN_NAME");
                // 索引列顺序
                Integer columnSort = rs.getInt("ORDINAL_POSITION");
                if(index == null) {
                    // 是否非唯一索引
                    Integer nonUniqueInt = rs.getInt("NON_UNIQUE");
                    boolean unique = nonUniqueInt == 0 ? true : false;
                    // 索引类型 聚簇、散列……
                    Integer typeInt = rs.getInt("TYPE");
                    String type = INDEX_TYPE_MAP.get(typeInt);
                    // 索引方法 是唯一索引还是 非唯一索引
                    index = new Index(name, unique, type);
                    indexesMap.put(name, index);
                }
                List<String> columnNames = index.getColumns();
                // columnSort 从1开始
                columnNames.add(columnSort - 1, columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Index> indexes = new ArrayList<>();
        for (String key : indexesMap.keySet()) {
            Index index = indexesMap.get(key);
            indexes.add(index);
        }
        return indexes;
    }

    /**
     * 获得连接详细信息
     * getIndexes
     * getCatalogs
     */
    public static List<DataBase> getConnectionDetails(Connection connection) throws SQLException, ClassNotFoundException {
        DatabaseMetaData dbmd = connection.getMetaData();
        List<String> catalogs = getCatalogs(dbmd);
        List<DataBase> dataBases = new ArrayList<>();
        for (String catalog : catalogs) {
            DataBase dataBase = new DataBase(catalog);
            dataBases.add(dataBase);
            List<Table> tables = getTables(dbmd, catalog);
            for (Table table : tables) {
                String tableName = table.getName();
                List<String> primaryKeys = getPrimaryKeys(dbmd, catalog, tableName);
                List<Column> columns = getColumns(dbmd, catalog, tableName, primaryKeys);
                List<Index> indexes = getIndexes(dbmd, catalog, tableName);
                table.setColumns(columns);
                table.setIndexes(indexes);
            }
            dataBase.setTables(tables);
        }
        return dataBases;
    }

    /**
     * 获得数据库的catalog(数据库名)
     */
    public static List<String> getCatalogs(DatabaseMetaData metaData) throws SQLException {
        List<String> catalogs = new ArrayList<>();
        // 获得数据库目录信息
        ResultSet catalogsRs = metaData.getCatalogs();
        while (catalogsRs.next()) {
            String catalog = catalogsRs.getString("TABLE_CAT");
            catalogs.add(catalog);
        }
        return catalogs;
    }

    /**
     * 获得数据库的表名
     */
    public static List<Table> getTables(DatabaseMetaData metaData, String dbName) throws SQLException, ClassNotFoundException {
        // dbName为null的时候，把整个连接的的所有库的所有表名都搂出来
        Map<String, DataBase> dataBases = new HashMap<>();
        List<Table> tables = new ArrayList<>();
        // 获得数据库表信息
        try(ResultSet tableResults = metaData.getTables(dbName, null, null, new String[]{"TABLE"})) {
            while (tableResults.next()) {
                String tableName = tableResults.getString("TABLE_NAME");
                Table table = new Table(tableName);
                tables.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return tables;
    }

    public static <T>void cacheObject(Class<T> cls, Map<Object, T> map, Object key) throws IllegalAccessException, InstantiationException {
        T val = map.get(key);
        if(val == null) {
            T newObject = cls.newInstance();
            map.put(key, newObject);
        }
    }

    /**
     * 获得表的主键列名
     * @param dbmd
     * @param dbName
     * @param tableName
     * @throws SQLException
     */
    public static List<String> getPrimaryKeys(DatabaseMetaData dbmd, String dbName, String tableName) {
        List<String> primaryKeys = new ArrayList<>();
        try(ResultSet rs = dbmd.getPrimaryKeys(dbName, null, tableName)) {
            while(rs.next()) {
                // 主键列名
                String columnName = rs.getString("COLUMN_NAME");
                primaryKeys.add(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return primaryKeys;
    }

    /**
     * 获得表的列信息
     * @param dbmd
     * @param dbName 目录名称（数据库名称）
     * @param tableName 表格名称
     * @throws SQLException
     */
    private static List<Column> getColumns(DatabaseMetaData dbmd, String dbName, String tableName, List<String> primaryKeys){
        List<Column> tableColumns = new ArrayList<>();
        try (ResultSet rs = dbmd.getColumns(dbName, null, tableName, null)){
            while(rs.next()) {
                // 列名
                String columnName = rs.getString("COLUMN_NAME");// 来自 java.sql.Types 的 SQL 数据类型。
                // 列类型
                String columnType = rs.getString("TYPE_NAME");// 数据类型的名称。
                // 是否可空
                Integer nullableInt = rs.getInt("NULLABLE");// 是否可空
                // 长度
                Integer length = rs.getInt("COLUMN_SIZE");// length
                // 小数点
                Integer decimalPoint = rs.getInt("DECIMAL_DIGITS");// 小数部分的位数
                // 注释
                String notes = rs.getString("REMARKS");// 注释
                // 是否可空
                boolean nullable = true;
                if(nullableInt != null && nullableInt == 0) {
                    nullable = false;
                }
                // 是否主键
                boolean isKey = false;
                if(primaryKeys.contains(columnName)) {
                    isKey = true;
                }
                // 列对象
                Column column = new Column(columnName, columnType, length, decimalPoint, nullable, notes, isKey);
                tableColumns.add(column);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return tableColumns;
    }
}
