package com.slzh.model.desktop;

import java.util.Date;

public class PortalLabel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column portal_label.id
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column portal_label.label_name
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    private String labelName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column portal_label.type
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column portal_label.create_time
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column portal_label.update_time
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column portal_label.del
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    private Integer del;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column portal_label.id
     *
     * @return the value of portal_label.id
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column portal_label.id
     *
     * @param id the value for portal_label.id
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column portal_label.label_name
     *
     * @return the value of portal_label.label_name
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public String getLabelName() {
        return labelName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column portal_label.label_name
     *
     * @param labelName the value for portal_label.label_name
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column portal_label.type
     *
     * @return the value of portal_label.type
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column portal_label.type
     *
     * @param type the value for portal_label.type
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column portal_label.create_time
     *
     * @return the value of portal_label.create_time
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column portal_label.create_time
     *
     * @param createTime the value for portal_label.create_time
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column portal_label.update_time
     *
     * @return the value of portal_label.update_time
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column portal_label.update_time
     *
     * @param updateTime the value for portal_label.update_time
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column portal_label.del
     *
     * @return the value of portal_label.del
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public Integer getDel() {
        return del;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column portal_label.del
     *
     * @param del the value for portal_label.del
     *
     * @mbggenerated Thu Jul 15 11:35:05 CST 2021
     */
    public void setDel(Integer del) {
        this.del = del;
    }
}