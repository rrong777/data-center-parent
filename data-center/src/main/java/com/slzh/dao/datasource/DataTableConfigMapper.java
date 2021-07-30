package com.slzh.dao.datasource;

import com.slzh.model.datasource.DataTableConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataTableConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config_data_table
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config_data_table
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    int insert(DataTableConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config_data_table
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    int insertSelective(DataTableConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config_data_table
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    DataTableConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config_data_table
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    int updateByPrimaryKeySelective(DataTableConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config_data_table
     *
     * @mbggenerated Fri Jul 23 15:06:48 CST 2021
     */
    int updateByPrimaryKey(DataTableConfig record);

    int deleteDataTableConfig(@Param("ids") List<Long> ids);

    List<DataTableConfig> findPageDataTableConfig(@Param("ids") List<Long> ids);
}