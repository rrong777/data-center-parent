package com.slzh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 由于和被请求的资源的当前状态之间存在冲突，请求无法完成
 */
@ResponseStatus(value= HttpStatus.CONFLICT)
public class ConflictException  extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ConflictException(String message)
    {
        super(message);
    }
}
