package com.slzh.service;

import com.slzh.model.LoginBean;
import com.slzh.model.SysLog;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wqr
 * @data 2019-08-15 - 09:35
 */
public interface SysLogService {
    int saveList(List<SysLog> sysLogList);
    PageResult findPage(PageRequest pageRequest);
    Integer insertLoginLog(Long userId, LoginBean loginBean, HttpServletRequest request);
    Integer addLogByRoute(String path, String ip);
}