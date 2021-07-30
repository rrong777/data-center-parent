package com.slzh.service;

import com.slzh.model.ReleaseParams;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.model.sso.InterfaceInfo;

import java.util.List;
import java.util.Map;

public interface InterfaceInfoService {
    HttpResult insert(InterfaceInfo interfaceInfo);
    HttpResult update(InterfaceInfo interfaceInfo);
    HttpResult setIsEnable(InterfaceInfo interfaceInfo);
    HttpResult setIsDelete(InterfaceInfo interfaceInfo);
    HttpResult findPage(PageRequest pageRequest);
    HttpResult releaseUser(ReleaseParams params) throws Exception;

    HttpResult getReleaseRecordByUsername(String username);

    HttpResult listApp();

    HttpResult listInterface(Integer interfaceType);

    HttpResult releaseUserList(ReleaseParams releaseParams) throws Exception;
}
