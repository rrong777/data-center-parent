package com.slzh.model;

/**
 * 登录接口封装对象
 * @author lanb
 * @date Oct 29, 2018
 */
public class LoginBean {

	private String account;
	private String password;
	private String jsCode;
	private String captcha;

	public String getJsCode() {
		return jsCode;
	}

	public void setJsCode(String jsCode) {
		this.jsCode = jsCode;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
