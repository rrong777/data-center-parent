package com.slzh.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.slzh.dao.SysNoticeMapper;
import com.slzh.model.SysNotice;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import com.slzh.service.SysNoticeService;
import com.slzh.service.SysUserService;
import com.slzh.utils.login.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysNoticeServiceImpl implements SysNoticeService {
    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    @Autowired
    private SysUserService sysUserService;
    public List<SysNotice> getNoticePanelMsg() {
        Map<String, Object> params = new HashMap<>();
        Long userId = SecurityUtils.getUserId(sysUserService);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String endTime = format.format(currentDate);
        String startTime = format.format(currentDate.getTime() - 1000 * 60 * 60 * 24 * 3);
        String username = SecurityUtils.getUsername();
        params.put("username", username);
        params.put("userId", userId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("status", 0);
        return sysNoticeMapper.findPage(params);
    }

    public PageResult findPage(PageRequest pageRequest) {
        pageRequest.getParams().put("userId", SecurityUtils.getUserId(sysUserService));
        pageRequest.getParams().put("username", SecurityUtils.getUsername());
        return MybatisPageHelper.findPage(pageRequest, sysNoticeMapper, "findPage", pageRequest.getParams());
    }

    public Integer batchAlterStatus(List<String> noticeIds) {
        return sysNoticeMapper.batchAlterStatus(noticeIds);
    }

    @Override
    public HttpResult allRead() {
        Long userId = SecurityUtils.getUserId(sysUserService);
        sysNoticeMapper.allRead(userId);
        return HttpResult.ok();
    }

    @Override
    public HttpResult receiveNotice(SysNotice notice) {
        String typeDesc = notice.getTypeDesc();
        String message = notice.getMessage();
        String noticeOrigin = notice.getNoticeOrigin();
        Long userId = notice.getUserId();
        String username = notice.getUsername();
        String pusher = notice.getNoticePusher();
        // ???????????????????????????username
        if(StringUtils.isBlank(username)) {
            if(userId == null || userId == 0L) {
                notice.setUserId(-1L);
            }
        }
//        String username = notice.getUsername();
//        Assert.notNull(username, "?????????????????????username");
        Assert.notNull(typeDesc,"?????????????????????typeDesc");
        Assert.notNull(pusher,"?????????????????????noticePusher");
        Assert.notNull(message, "?????????????????????message");
        Assert.notNull(noticeOrigin, "?????????????????????noticeOrigin");

        String imgUrls = notice.getImgUrls();
        if(!StringUtils.isEmpty(imgUrls)) {
            try {
                JSONObject temp = JSONObject.parseObject(imgUrls);
                JSONArray imgArr = temp.getJSONArray("imgUrls");
                if(imgArr.size() == 1) {
                    notice.setShowType(1);
                } else if (imgArr.size() >=2) {
                    notice.setShowType(2);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("??????imgUrls??????????????????");
            }
        }
        sysNoticeMapper.pushNotice(notice);
        return HttpResult.ok();
    }
}
