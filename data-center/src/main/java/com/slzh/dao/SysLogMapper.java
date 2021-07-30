package com.slzh.dao;

import com.slzh.model.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wqr
 * @data 2019-08-15 - 09:35
 */
public interface SysLogMapper {
    int saveList(List<SysLog> sysLogList);
    List<Map<String, Object>> findPage(@Param("params") Map<String,Object> params);
    List<Map<String, String>> selectAllPathDesc();
    Integer insertOne(@Param("sysLog") SysLog sysLog);
}
