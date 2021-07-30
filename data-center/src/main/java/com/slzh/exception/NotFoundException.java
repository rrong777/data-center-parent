package com.slzh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 请求失败，请求所希望得到的资源未被在服务器上发现
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public NotFoundException(String message)
	{
		super(message);
	}

}
