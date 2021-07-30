package com.slzh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 由于包含语法错误，当前请求无法被服务器理解。
 * 除非进行修改，否则客户端不应该重复提交这个请求
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public BadRequestException(String message)
	{
		super(message);
	}
}
