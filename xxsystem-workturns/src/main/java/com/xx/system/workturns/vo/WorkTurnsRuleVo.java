/**
 * 文件名： WorkTurnsRuleVo.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：倒班规则VO实体类
 * 修改人： dong.he
 * 修改时间： 2014年8月22日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.vo;

import java.io.Serializable;

/**
 * WorkTurnsRuleVo（倒班规则）VO定义
 * 
 * @author dong.he
 * @version V1.4,2014-08-22 上午8:55:00
 * @see [相关类/方法]
 * @since V1.4
 */
public class WorkTurnsRuleVo implements Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = -800902722866522013L;
    
    /**
     * @Fields id : 倒班规则ID
     */
    private Integer id;
    
    /**
     * @Fields name : 倒班规则名称
     */
    private String name;
    
    /**
     * @Fields user : 制定人
     */
    private String user;
    
    /**
     * @Fields makeDate : 制定时间
     */
    private String makeDate;
    
    /**
     * @Fields cycleDays : 倒班周期天数，由倒班规则制定人动态设定。
     */
    private Integer cycleDays;
    
    /**
     * @Fields remarks : 备注
     */
    private String remarks;
    
    /**
     * @Fields orgId : 部门ID
     */
    private Integer orgId;
    
    /**
     * @Fields orgName : 部门名称
     */
    private String orgName;
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the id to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the cycleDays
     */
    public Integer getCycleDays() {
        return cycleDays;
    }
    
    /**
     * @param cycleDays the cycleDays to set
     */
    public void setCycleDays(Integer cycleDays) {
        this.cycleDays = cycleDays;
    }
    
    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }
    
    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }
    
    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    
    /**
     * @return the makeDate
     */
    public String getMakeDate() {
        return makeDate;
    }
    
    /**
     * @param makeDate the makeDate to set
     */
    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }
    
    /**
     * @return the orgId
     */
    public Integer getOrgId() {
        return orgId;
    }
    
    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    
    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }
    
    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
}