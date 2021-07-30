package com.slzh.service;

import com.slzh.model.SysReport;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;

public interface SysReportService {

    PageResult findPage(PageRequest pageRequest);

    HttpResult receiveReport(SysReport report) throws Exception;
}
