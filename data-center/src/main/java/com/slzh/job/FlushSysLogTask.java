package com.slzh.job;


import com.slzh.aop.SysLogAspect;
import com.slzh.model.SysLog;
import com.slzh.service.SysLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wqr
 * @data 2019-08-15 - 16:13
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class FlushSysLogTask {
    private static final Logger log = LoggerFactory.getLogger(FlushSysLogTask.class);

    @Autowired
    private SysLogService sysLogService;


    @Scheduled(cron = "0 0/5 * * * ?")
    public void configureTasks() {
        try {
            log.info("刷写日志定时任务开始");
            long start = System.currentTimeMillis();
            int size = 0;
            List<SysLog> tmpLogList = null;
            synchronized (SysLogAspect.sysLogList) {
                size = SysLogAspect.sysLogList.size();
                tmpLogList = new ArrayList(SysLogAspect.sysLogList.size());
                if (!CollectionUtils.isEmpty(SysLogAspect.sysLogList)) {
                    tmpLogList.addAll(SysLogAspect.sysLogList);
                    SysLogAspect.sysLogList.clear();
                }
            }

            if (!CollectionUtils.isEmpty(tmpLogList)) {
                sysLogService.saveList(tmpLogList);
            }
            log.info("刷写日志定时任务结束，总共处理{}条，耗时{}", size, (System.currentTimeMillis() - start));
        } catch (Exception e) {
            log.info("出现异常----{}", e.getMessage());
            log.info("sysLogService -----{}", sysLogService);
            e.printStackTrace();
        }
    }


}
