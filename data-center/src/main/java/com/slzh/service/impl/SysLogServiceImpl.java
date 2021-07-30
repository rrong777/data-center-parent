package com.slzh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.slzh.dao.SysLogMapper;
import com.slzh.model.LoginBean;
import com.slzh.model.SysLog;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import com.slzh.service.SysLogService;
import com.slzh.service.SysUserService;
import com.slzh.utils.IPUtils;
import com.slzh.utils.login.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SysLogServiceImpl implements SysLogService {

    private static Map<String, String> pathDescMap = new ConcurrentHashMap<>();
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Map<String, Object> params = pageRequest.getParams();
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("startTime");
        if(startTime == null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date current = new Date();
            endTime = format.format(current);
            startTime = format.format(new Date(current.getTime() - 1000 * 60 * 60 * 24 * 7));
            params.put("startTime", startTime);
            params.put("endTime", endTime);
        }

        return MybatisPageHelper.findPage(pageRequest, sysLogMapper, "findPage", pageRequest.getParams());
    }

    @Override
    public int saveList(List<SysLog> sysLogList) {
        return sysLogMapper.saveList(sysLogList);
    }

    @Override
    public Integer addLogByRoute(String path, String ip) {
        String desc = getPathDesc(path);
        long userId = SecurityUtils.getUserId(sysUserService);
        SysLog sysLog = new SysLog(userId, "", desc, "","", new Date(), ip, (byte) 0, "");
        return sysLogMapper.insertOne(sysLog);
    }


    private String getPathDesc(String path) {
        if(pathDescMap.containsKey(path)) {
            return pathDescMap.get(path);
        }
        synchronized (pathDescMap) {
            if(pathDescMap.containsKey(path)) {
                return pathDescMap.get(path);
            }
            List<Map<String, String>> pathDesc = sysLogMapper.selectAllPathDesc();
            for(Map<String, String> temp : pathDesc) {
                pathDescMap.put(temp.get("path"), temp.get("desc"));
            }
            return pathDescMap.get(path);
        }

    }

    @Override
    public Integer insertLoginLog(Long userId, LoginBean loginBean, HttpServletRequest request) {
        String ip = IPUtils.getUserIp(request);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("账号", loginBean.getAccount());
        paramsMap.put("密码", loginBean.getPassword());
        String params = JSONObject.toJSONString(paramsMap);
        SysLog sysLog = new SysLog(userId, loginBean.getAccount(), "登录", "login", params, new Date(), ip, (byte)0, "");
        return sysLogMapper.insertOne(sysLog);
    }
}
