package com.slzh.aop;

import com.slzh.annotation.Operation;
import com.slzh.model.SysLog;
import com.slzh.model.page.PageRequest;
import com.slzh.service.SysLogService;
import com.slzh.service.SysUserService;

import com.slzh.utils.IPUtils;
import com.slzh.utils.StringUtils;
import com.slzh.utils.login.SecurityUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author wqr
 * @data 2019-08-14 - 17:14
 */
@Order(2)
@Aspect
@Component
public class SysLogAspect {
    private static final Logger log = LoggerFactory.getLogger(SysLogAspect.class);
    public static List<SysLog> sysLogList = new ArrayList<>();
    private static Map<String, String> paramDesc = new HashMap<>();
    static {
        paramDesc.put("params", "参数");
        paramDesc.put("imgNewName", "图片地址");
        paramDesc.put("pageRequest", "分页请求参数");
        paramDesc.put("searchParam", "搜索参数");
        paramDesc.put("IDNumber", "身份证号码");
        paramDesc.put("PlateNo", "车牌号");
        paramDesc.put("UserName", "姓名");
        paramDesc.put("MobileNum", "手机号");
        paramDesc.put("idNumber", "身份证号码");
        paramDesc.put("plateNumber", "车牌号");
        paramDesc.put("batchSearchParam", "批量搜索参数");
        paramDesc.put("KsltSearchReq", "行人搜索参数");
        paramDesc.put("properties", "属性");
        paramDesc.put("startTime", "开始时间");
        paramDesc.put("endTime", "结束时间");
        paramDesc.put("pageNo", "页码");
        paramDesc.put("pageSize", "每页数据量");
        paramDesc.put("null", "无");
        paramDesc.put("quality", "图片质量");
        paramDesc.put("cameraIds", "相机点位ID");
    }
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private SysUserService sysUserService;

    // 指定两个切点 包含所有Controller
    @Pointcut("execution(public * com.slzh.web.controller.*.*(..))") public void pointCutOne() {

    }

    @Pointcut("execution(public * com.slzh.web.controller.*.*.*(..))") public void pointCutTwo() {

    }

//    @Before("pointCutOne() || pointCutTwo()")
    public void beforeMethod(JoinPoint joinPoint) {
        // 强转之后的类型可以获得方法（方法名），从而获得注解（操作）
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获得方法名
        String methodName = method.getName();


        // 获得操作描述，排除日志记录
        Operation operation = method.getAnnotation(Operation.class);
        if(operation == null) {
            return;
        }
        // 日志对象保存日志
        SysLog sysLog = new SysLog();
        sysLog.setMethod(methodName);
        // 获得HTTP请求对象
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        // 获得类名
        String className = (String) joinPoint.getTarget().getClass().getName();

        // 获得用户名
        String userName = SecurityUtils.getUsername();
        sysLog.setUserName(userName);
        sysLog.setUserId(SecurityUtils.getUserId(sysUserService).longValue());
        // 获得IP
        String ip = IPUtils.getUserIp(request);

        sysLog.setIp(ip);
        sysLog.setStatus((byte)0);


        sysLog.setOperation(operation.value());

        // 设置类名
        String nonQualified = className.substring(className.lastIndexOf('.') + 1, className.length());
        sysLog.setClassName(nonQualified);

        // 获得参数列表
        Object[] params = joinPoint.getArgs();
        // 获得参数名列表
        String[] paramNames = signature.getParameterNames();
        String paramsStr = this.paramHandle(methodName, params, paramNames);
        if (paramsStr.length() > 450) {
            paramsStr = paramsStr.substring(0,450);
        }
        sysLog.setParams(paramsStr);
        // 获得日志时间的毫秒数
        // Long lastUpdateTime = System.currentTimeMillis();
        Date lastUpdateTime = new Date();
        sysLog.setTime(lastUpdateTime);
        if (sysLog == null || StringUtils.isBlank(sysLog.getOperation())) {
            return;
        }
        // 调用批量保存方法对日志进行保存
        toSaveSysLog(sysLog);
    }

    private  void toSaveSysLog(SysLog sysLog) {
        List<SysLog> tmpLogList = null;
        synchronized (sysLogList) {
            if (sysLogList.size() >= 50) {
                tmpLogList = new ArrayList<>(sysLogList.size());
                tmpLogList.addAll(sysLogList);
                sysLogList.clear();
            } else {
                sysLogList.add(sysLog);
            }
            if (!CollectionUtils.isEmpty(tmpLogList)) {
                log.info("开始准备刷写操作日志tmpLogList 的size="+tmpLogList.size());
                long start = System.currentTimeMillis();
                if(sysLogService==null){
                    log.debug("-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=sysLogService==null");
                    return;
                }
                if(tmpLogList==null){
                    log.debug("-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=tmpLogList==null");
                    return;
                }
                sysLogService.saveList(tmpLogList);
                log.info("结束刷写操作日志，耗时{}", (System.currentTimeMillis() - start));
            }
        }
    }

    private String paramHandle(String methodName, Object[] params, String[] paramNames) {
        if(params[0] == null) {
            return null;
        }
        if(methodName.equals("camerasCapturesPropertyBodys")) {
            return params[0].toString();
        } else if(methodName.equals("searchAnalysisInfo") || methodName.equals("uploadPic")) {
            Map<String, Object> param =(Map<String, Object>)params[0];
            if(param.get("imgNewName") != null) {
                return "{" + "\"图片名称\": \"" + param.get("imgNewName") + "\"}";
            } else if(param.get("imageBase64Str") != null) {
                return "{" + "\"图片Base64编码\": \"" + param.get("imageBase64Str") + "\"}";
            }
        } else if(methodName.equals("findCloudSearchPage")) {
            PageRequest pageRequest = (PageRequest) params[0];
            Map<String, Object> innerParams = pageRequest.getParams();
            return "{" + "\"搜索参数\":" + "\""+ innerParams.get("searchParam") + "\"," +
                      "\"批量搜索参数\":" + "\""+ innerParams.get("batchSearchParam") + "\"," +
                      "\"身份证号码\":" + "\""+ innerParams.get("IDNumber") + "\"," +
                      "\"车牌号\":" + "\""+ innerParams.get("PlateNo") + "\"," +
                      "\"用户名\":" + "\""+ innerParams.get("UserName") + "\"," +
                      "\"手机号码\":" + "\""+ innerParams.get("MobileNum") + "\"}";
        } else if(methodName.equals("queryUserInfoById")) {
            return "{" + "\"" + "身份证号码\":" + "\""+ params[0].toString() + "\"}";
        } else if(methodName.equals("queryCarInfoByPlateNumber")) {
            return "{" + "\"" + "车牌号\":" + "\"" + params[0].toString() + "\"}";
        }
        return null;
    }

}
