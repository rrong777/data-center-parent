package com.slzh.model;

import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable {
	private Long id;
	private Long userId;

    private String userName;

    private String operation;

    private String method;

    private String params;

    private Date time;

    private String ip;

	private Byte status;

	private String className;


	public SysLog() {
	}

	public SysLog(Long userId, String userName, String operation, String method, String params, Date time, String ip, Byte status, String className) {
		this.userId = userId;
		this.userName = userName;
		this.operation = operation;
		this.method = method;
		this.params = params;
		this.time = time;
		this.ip = ip;
		this.status = status;
		this.className = className;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}




	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}