package com.slzh.dao;

import com.slzh.model.ReleaseInfo;
import com.slzh.model.http.HttpResult;
import com.slzh.model.sso.InterfaceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InterfaceInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_interface_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long interfaceInfoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_interface_info
     *
     * @mbggenerated
     */
    int insert(InterfaceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_interface_info
     *
     * @mbggenerated
     */
    int insertSelective(InterfaceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_interface_info
     *
     * @mbggenerated
     */
    InterfaceInfo selectByPrimaryKey(Long interfaceInfoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_interface_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(InterfaceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_interface_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(InterfaceInfo record);

    void update(InterfaceInfo interfaceInfo);

    void setIsDelete(InterfaceInfo interfaceInfo);

    void setIsEnable(InterfaceInfo interfaceInfo);

    List<InterfaceInfo> findPage(Map<String, Object> params);

    List<InterfaceInfo> getInterfaceInfoForRelease(@Param("allRelease") Boolean allRelease, @Param("interfaceType") Integer interfaceType, @Param("interfaceInfoIds") List<Integer> interfaceInfoIds);

    void insertReleaseInfo(ReleaseInfo releaseInfo);

    List<Map<String, Object>> getReleaseRecordByUsername(String username);

    List<Map<String, Object>> listApp();

    List<Map<String, Object>> listInterface(Integer interfaceType);

    void insertReleaseInfos(List<ReleaseInfo> releaseInfo);
}