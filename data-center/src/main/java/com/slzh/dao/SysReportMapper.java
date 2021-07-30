package com.slzh.dao;

import com.slzh.model.SysReport;

import java.util.List;
import java.util.Map;

public interface SysReportMapper {

    List<SysReport> findPage(Map<String, Object> params);

    void receiveReport(SysReport report);
}
