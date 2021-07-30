package com.slzh.service.sso;

import com.slzh.model.http.HttpResult;
import com.slzh.model.http.HttpStatus;

/**
 * <p>文件名称:null.java</p>
 * <p>文件描述:</p>
 * <p>版权所有: 版权所有(C)2013-2099</p>
 * <p>公 司:  </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 *
 * @author zhengl
 * @version 1.0
 * @since 2021/4/15 20:38
 */
public class SSOPageResult {

    private int code = 200;
    private String message;
    private Object result;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static SSOPageResult error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static SSOPageResult error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static SSOPageResult error(int code, String msg) {
        SSOPageResult r = new SSOPageResult();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    public static SSOPageResult ok(int code, String msg) {
        SSOPageResult r = new SSOPageResult();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    public static SSOPageResult ok(String msg) {
        SSOPageResult r = new SSOPageResult();
        r.setMessage(msg);
        return r;
    }

    public static SSOPageResult ok(Object data,String msg) {
        SSOPageResult r = new SSOPageResult();
        r.setResult(data);
        r.setSuccess(true);
        r.setMessage(msg);
        return r;
    }

    public static SSOPageResult ok() {
        return new SSOPageResult();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
