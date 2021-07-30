package com.slzh.service;

import com.slzh.model.SysNotice;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import com.slzh.utils.login.SecurityUtils;

import java.util.List;

public interface SysNoticeService {
    List<SysNotice> getNoticePanelMsg();
    PageResult findPage(PageRequest pageRequest);
    Integer batchAlterStatus(List<String> noticeIds);

    HttpResult receiveNotice(SysNotice notice);
    HttpResult allRead();

}
