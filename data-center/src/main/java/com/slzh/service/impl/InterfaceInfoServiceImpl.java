package com.slzh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slzh.config.security.JwtAuthenticatioToken;
import com.slzh.dao.InterfaceInfoMapper;
import com.slzh.dao.SysUserMapper;
import com.slzh.model.ReleaseInfo;
import com.slzh.model.ReleaseParams;
import com.slzh.model.SysUser;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import com.slzh.model.sso.InterfaceInfo;
import com.slzh.model.sso.SysUserInfoVO;
import com.slzh.service.InterfaceInfoService;
import com.slzh.service.SysUserService;
import com.slzh.service.sso.util.SM2Util;
import com.slzh.service.sso.util.Util;
import com.slzh.utils.HttpClientUtils;
import com.slzh.utils.IPUtils;
import com.slzh.utils.login.SecurityUtils;
import com.slzh.web.controller.SysLoginController;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

@Service
public class InterfaceInfoServiceImpl implements InterfaceInfoService {
    @Autowired
    private InterfaceInfoMapper interfaceInfoMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(SysLoginController.class);


    @Override
    public HttpResult insert(InterfaceInfo interfaceInfo) {
        String username = SecurityUtils.getUsername();
        String ip = getIp();

        interfaceInfo.setCreateUser(username);
        interfaceInfo.setCreateServerHost(ip);
        interfaceInfo.setCreateDepartment("10000");

        interfaceInfo.setIsEnabled(true);

        try {
            interfaceInfoMapper.insert(interfaceInfo);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("uni_idx")) {
                return HttpResult.error("当前应用系统已存在当前类型的下发接口，若想新增请删除原有接口！");
            }
        }


        return HttpResult.ok();
    }

    private String getIp() {
        String ip = null;
        try {
            ip = IPUtils.getV4IP();
        } catch (Exception e) {
        }
        return ip;
    }


    @Override
    public HttpResult update(InterfaceInfo interfaceInfo) {
        String username = SecurityUtils.getUsername();
        String ip = getIp();
        interfaceInfo.setUpdateDepartment("10000");
        interfaceInfo.setUpdateServerHost(ip);
        interfaceInfo.setUpdateUser(username);
        interfaceInfoMapper.updateByPrimaryKeySelective(interfaceInfo);
        return HttpResult.ok();
    }

    @Override
    public HttpResult setIsEnable(InterfaceInfo interfaceInfo) {
        interfaceInfoMapper.setIsEnable(interfaceInfo);
        return HttpResult.ok();
    }

    @Override
    public HttpResult getReleaseRecordByUsername(String username) {
        return HttpResult.ok(interfaceInfoMapper.getReleaseRecordByUsername(username));
    }

    @Override
    public HttpResult listApp() {
        return HttpResult.ok(interfaceInfoMapper.listApp());
    }

    @Override
    public HttpResult listInterface(Integer interfaceType) {
        return HttpResult.ok(interfaceInfoMapper.listInterface(interfaceType));
    }

    /**
     * 部分下发
     *
     * @return
     */
    public HttpResult releaseUser(ReleaseParams releaseParams) throws Exception {


        Long userId = SecurityUtils.getUserId(sysUserService);
        Boolean allRelease = releaseParams.getAllRelease();
        Integer interfaceType = releaseParams.getInterfaceType();
        List<Integer> interfaceInfoIds = releaseParams.getInterfaceInfoIds();
        if (allRelease == false && (interfaceInfoIds == null || interfaceInfoIds.size() == 0)) {
            return HttpResult.ok("暂未下发！");
        }
        List<InterfaceInfo> interfaceInfoForRelease = interfaceInfoMapper.getInterfaceInfoForRelease(allRelease, interfaceType, interfaceInfoIds);
        Map<String, Object> tempParams = releaseParams.getParams();
        tempParams.put("releaseInterfaceType", interfaceType);

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", ((JwtAuthenticatioToken) SecurityUtils.getAuthentication()).getToken());
        String paramsString = JSON.toJSONString(Arrays.asList(tempParams));
        tempParams.put("userId", tempParams.get("id"));
        tempParams.put("userName", tempParams.get("username"));
        tempParams.put("phone", tempParams.get("mobile"));
        String userInfos = encodeUserInfo(JSON.toJSONString(Arrays.asList(tempParams)));
        Map<String, String> params = new HashMap<>();
        params.put("userInfos", userInfos);
        for (InterfaceInfo temp : interfaceInfoForRelease) {
            String url = temp.getInterfaceAddresses();
            String method = temp.getRequestMode();
            String res = null;
            ReleaseInfo releaseInfo = null;
            int resCode = 200;
            String resMsg = "";
            try {
                if (method.toLowerCase().equals("post")) {
                    res = HttpClientUtils.doPost(url, headers, params, ContentType.APPLICATION_JSON);
                } else if (method.toLowerCase().equals("get")) {
                    System.out.println(paramsString);
                    res = HttpClientUtils.doGet(url, headers, null);
                }
                try {
                    if (!StringUtils.isEmpty(res)) {
                        JSONObject jsonObject = JSONObject.parseObject(res);
                        resCode = (int) jsonObject.get("code");
                        resMsg = (String) jsonObject.get("msg");
                    }
                } catch (Exception e) {
                    releaseInfo = setReleaseInfoForExce(temp.getInterfaceInfoId(), userId, paramsString, 500, getResInsert(res), "解析响应报文异常", null);

                }
                releaseInfo = setReleaseInfoForExce(temp.getInterfaceInfoId(), userId, paramsString, resCode, getResInsert(res), resMsg, null);
            } catch (Exception e) {
                // 异常之后插入record
                releaseInfo = setReleaseInfoForExce(temp.getInterfaceInfoId(), userId, paramsString, 500, getResInsert(res), "请求异常！", null);
            }
            if (releaseInfo != null) {
                interfaceInfoMapper.insertReleaseInfo(releaseInfo);
            }

        }
        return HttpResult.ok();
    }


    private String getResInsert(String res) {
        if (StringUtils.isBlank(res)) {
            return res;
        }
        int length = res.length() >= 1000 ? 1000 : res.length();
        return res.substring(0, length);
    }

    private ReleaseInfo setReleaseInfoForExce(Long interfaceInfoId, Long userId, String params, int responseCode, String response, String resMsg, String username) {
        ReleaseInfo releaseInfo = new ReleaseInfo();
        releaseInfo.setResponseMsg(resMsg);
        releaseInfo.setInterfaceInfoId(interfaceInfoId);
        releaseInfo.setUserId(userId);
        releaseInfo.setParams(params);
        releaseInfo.setResponseCode(responseCode);
        releaseInfo.setResponseStr(response);
        releaseInfo.setReleaseUsername(username);
        return releaseInfo;
    }

    @Override
    public HttpResult findPage(PageRequest pageRequest) {
        PageResult pageResult = MybatisPageHelper.findPage(pageRequest, interfaceInfoMapper, "findPage", pageRequest.getParams());
        return HttpResult.ok(pageResult);
    }


    @Override
    public HttpResult setIsDelete(InterfaceInfo interfaceInfo) {
        interfaceInfoMapper.setIsDelete(interfaceInfo);
        return HttpResult.ok();
    }


    void setUserToSSO(SysUser sysUser) {
        sysUser.setUserName(sysUser.getUsername());
        sysUser.setUserId(sysUser.getId());
        sysUser.setPhone(sysUser.getMobile());
        sysUser.setPersonName(sysUser.getNickname());
        sysUser.setUserNo(sysUser.getUserId().toString());
    }


    /**
     * 加密用户信息
     *
     * @param
     * @return 加密后字符串
     * @author chenzj
     */
    private String encodeUserInfo(String userInfo) throws IOException {
        if (null == userInfo) {
            return null;
        }
        return SM2Util.encrypt(Util.hexToByte(SM2Util.INTERNAL_PUBLIC_KEY), userInfo.getBytes());
    }

    /**
     * 部分下发
     *
     * @return
     */
    @Override
    public HttpResult releaseUserList(ReleaseParams releaseParams) {


        Long userId = SecurityUtils.getUserId(sysUserService);
        Boolean allRelease = releaseParams.getAllRelease();
        Integer interfaceType = releaseParams.getInterfaceType();
        List<Integer> interfaceInfoIds = releaseParams.getInterfaceInfoIds();
        if (allRelease == false && (interfaceInfoIds == null || interfaceInfoIds.size() == 0)) {
            return HttpResult.ok("暂未下发！");
        }
        List<InterfaceInfo> interfaceInfoForRelease = interfaceInfoMapper.getInterfaceInfoForRelease(allRelease, interfaceType, interfaceInfoIds);

        Map<String, Object> userReq = new HashMap<>();
        userReq.put("userIdList", releaseParams.getUserIdList());
        List<SysUser> sysUsers = sysUserMapper.findPage(userReq);
        if (CollectionUtils.isEmpty(sysUsers)) {
            return HttpResult.error("对应用户不存在");
        }
        for (SysUser sysUser : sysUsers) {
            sysUser.setReleaseInterfaceType(interfaceType);
            sysUserService.decodeOutPassword(sysUser);
            sysUser.setOutPassword(null);
            sysUser.setSalt(null);
            setUserToSSO(sysUser);
        }

        String userInfos = null;
        try {
            userInfos = encodeUserInfo(JSON.toJSONString(sysUsers));
        } catch (Exception e) {
            log.error("加密用户数据出错:");
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<>();
        params.put("userInfos", userInfos);
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=UTF-8");
//        headers.put("Authorization", ((JwtAuthenticatioToken) SecurityUtils.getAuthentication()).getToken());
        for (InterfaceInfo temp : interfaceInfoForRelease) {
            String url = temp.getInterfaceAddresses();
            String method = temp.getRequestMode();
            String res = null;
            ReleaseInfo releaseInfo = null;
            int resCode = 200;
            String resMsg = "";
            List<ReleaseInfo> releaseInfos = new ArrayList<>();
            //规整对应releaseInfo
            for (SysUser sysUser : sysUsers) {
                ReleaseInfo releaseInfo1 = new ReleaseInfo();
                releaseInfo1.setUserId(userId);
                releaseInfo1.setReleaseUsername(sysUser.getUsername());
                releaseInfo1.setParams(JSON.toJSONString(sysUser));
                releaseInfo1.setInterfaceInfoId(temp.getInterfaceInfoId());
                releaseInfos.add(releaseInfo1);
            }


            try {
                if (method.toLowerCase().equals("post")) {
                    res = HttpClientUtils.doPost(url, headers, params, ContentType.APPLICATION_JSON);
                }
                try {
                    if (!StringUtils.isEmpty(res)) {
                        JSONObject jsonObject = JSONObject.parseObject(res);
                        resCode = (int) jsonObject.get("code");
                        resMsg = (String) jsonObject.get("msg");
                    }
                } catch (Exception e) {
                    for (ReleaseInfo releaseInfo1 : releaseInfos) {
                        releaseInfo1.setResponseCode(500);
                        releaseInfo1.setResponseMsg("解析响应报文异常");
                    }
                    e.printStackTrace();
                }
                res = getResInsert(res);
                for (ReleaseInfo releaseInfo1 : releaseInfos) {
                    releaseInfo1.setResponseCode(resCode);
                    releaseInfo1.setResponseMsg(resMsg);
                    releaseInfo1.setResponseStr(res);
                }
            } catch (Exception e) {
                // 异常之后插入record
                res = getResInsert(res);
                for (ReleaseInfo releaseInfo1 : releaseInfos) {
                    releaseInfo1.setResponseCode(500);
                    releaseInfo1.setResponseMsg("请求异常");
                    releaseInfo1.setResponseStr(res);
                }
                e.printStackTrace();
            }
            if (!CollectionUtils.isEmpty(releaseInfos)) {
                interfaceInfoMapper.insertReleaseInfos(releaseInfos);
            }

        }
        return HttpResult.ok();
    }
}
