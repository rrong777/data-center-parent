package com.slzh.web.controller;

import com.slzh.model.SysNotice;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class SysNoticeController {
    @Autowired
    private SysNoticeService sysNoticeService;

    /**
     * 获得通知面板消息
     * @return
     */
    @GetMapping("/getNoticePanelMsg")
    public HttpResult getNoticePanelMsg() {
        return HttpResult.ok(sysNoticeService.getNoticePanelMsg());
    }

    @PostMapping("/batchAlterStatus")
    public HttpResult batchAlterStatus(@RequestBody List<String> noticeIds) {
        return HttpResult.ok(sysNoticeService.batchAlterStatus(noticeIds));
    }

    @PostMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysNoticeService.findPage(pageRequest));
    }
    @GetMapping("/allRead")
    public HttpResult allRead() {
        return sysNoticeService.allRead();
    }


    // 子系统推送通知接口
    @PostMapping("/receiveNotice")
    public HttpResult receiveNotice(@RequestBody SysNotice notice) {
        try {
            return sysNoticeService.receiveNotice(notice);
        } catch (IllegalArgumentException e) {
          return HttpResult.error(e.getMessage());
        } catch (Exception e) {
            return HttpResult.error("服务器异常，请通知管理员");
        }
    }
}
