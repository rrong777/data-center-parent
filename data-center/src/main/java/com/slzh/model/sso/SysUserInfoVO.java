package com.slzh.model.sso;

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
 * @since 2021/4/15 19:39
 */
public class SysUserInfoVO {
    private Long userId;                        //用户ID
    private String userName;                //账号
    private String personName;            //用户姓名
    private String userNo;                    //用户编号
    private String idcard;        //证件号
    private String departmentName;    //部门名称
    private String phone;            //联系电话
    private String email;    //email

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
