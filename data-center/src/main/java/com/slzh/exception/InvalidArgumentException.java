package com.slzh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *400 用户请求的参数错误 
 * @author fly
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public InvalidArgumentException(String message)
	{
		super(message);
	}

}
