package com.slzh.model;



import java.util.Date;
import java.util.List;

public class SysMenu extends BaseModel {

    // 非数据库字段
    private String parentName;
    // 非数据库字段
    private Integer level;
    // 非数据库字段
    private List<SysMenu> children;
    private String perms;
    private String hasMenuRoleId;
    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    private List<SysFunction> sysFunctionList;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.id
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private Long id;

    public List<SysFunction> getSysFunctionList() {
        return sysFunctionList;
    }

    public void setSysFunctionList(List<SysFunction> sysFunctionList) {
        this.sysFunctionList = sysFunctionList;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.name
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private String name;

    public String getHasMenuRoleId() {
        return hasMenuRoleId;
    }

    public void setHasMenuRoleId(String hasMenuRoleId) {
        this.hasMenuRoleId = hasMenuRoleId;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.parent_id
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.url
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private String url;


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.type
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.icon
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private String icon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.order_num
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private Integer orderNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.create_by
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.create_time
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.last_update_by
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private String updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.updated_at
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private Date updatedAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu.del_flag
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    private Integer status;



    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu.id
     *
     * @return the value of sys_menu.id
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu.id
     *
     * @param id the value for sys_menu.id
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu.name
     *
     * @return the value of sys_menu.name
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu.name
     *
     * @param name the value for sys_menu.name
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu.parent_id
     *
     * @return the value of sys_menu.parent_id
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu.parent_id
     *
     * @param parentId the value for sys_menu.parent_id
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu.url
     *
     * @return the value of sys_menu.url
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu.url
     *
     * @param url the value for sys_menu.url
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }



    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu.type
     *
     * @return the value of sys_menu.type
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu.type
     *
     * @param type the value for sys_menu.type
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu.icon
     *
     * @return the value of sys_menu.icon
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu.icon
     *
     * @param icon the value for sys_menu.icon
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu.order_num
     *
     * @return the value of sys_menu.order_num
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu.order_num
     *
     * @param orderNum the value for sys_menu.order_num
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }







    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu.updated_at
     *
     * @return the value of sys_menu.updated_at
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu.updated_at
     *
     * @param updatedAt the value for sys_menu.updated_at
     *
     * @mbggenerated Thu Dec 03 18:48:15 CST 2020
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}