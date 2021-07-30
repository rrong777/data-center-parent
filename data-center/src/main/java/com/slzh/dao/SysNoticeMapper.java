package com.slzh.dao;

import com.slzh.model.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysNoticeMapper {
    Integer batchAlterStatus(@Param("ids")List<String> ids);
    List<SysNotice> findPage(Map<String, Object> params);

    void pushNotice(SysNotice notice);

    void allRead(Long userId);
}
