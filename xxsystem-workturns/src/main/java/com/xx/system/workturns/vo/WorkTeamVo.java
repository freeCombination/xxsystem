/**
 * 文件名： WorkTeamVo.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：班组VO实体类
 * 修改人： tangh
 * 修改时间： 2014年8月22日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.vo;

/**
 * WorkTeamVo（班组）VO定义
 * 
 * @author dong.he
 * @version V1.4,2014-08-22 上午8:55:00
 * @see [相关类/方法]
 * @since V1.4
 */
public class WorkTeamVo implements java.io.Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = -1195612312458575650L;
    
    /**
     * @Fields workTeamId : 班组ID
     */
    private int workTeamId;
    
    /**
     * @Fields orgId : 部门ID
     */
    private int orgId;
    
    /**
     * @Fields orgName : 部门名称
     */
    private String orgName;
    
    /**
     * @Fields workTeamName : 班组名称
     */
    private String workTeamName;
    
    /**
     * @Fields monitorId : 班组负责人ID
     */
    private int monitorId;
    
    /**
     * @Fields monitorName : 班组负责人姓名
     */
    private String monitorName;
    
    /**
     * @Fields createDate : 创建时间
     */
    private String createDate;
    
    /**
     * @Fields remark : 备注
     */
    private String remark;
    
    /**
     * @return the workTeamId
     */
    public int getWorkTeamId() {
        return workTeamId;
    }
    
    /**
     * @param workTeamId 班组ID
     */
    public void setWorkTeamId(int workTeamId) {
        this.workTeamId = workTeamId;
    }
    
    /**
     * @return the orgId
     */
    public int getOrgId() {
        return orgId;
    }
    
    /**
     * @param orgId 组织ID
     */
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
    
    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }
    
    /**
     * @param orgName 组织名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    /**
     * @return the workTeamName
     */
    public String getWorkTeamName() {
        return workTeamName;
    }
    
    /**
     * @param workTeamName 班组名称
     */
    public void setWorkTeamName(String workTeamName) {
        this.workTeamName = workTeamName;
    }
    
    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }
    
    /**
     * @param createDate 创建时间
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return the monitorId
     */
    public int getMonitorId() {
        return monitorId;
    }
    
    /**
     * @param monitorId 班组负责人ID
     */
    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }
    
    /**
     * @return the monitorName
     */
    public String getMonitorName() {
        return monitorName;
    }
    
    /**
     * @param monitorName 班组负责人姓名
     */
    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
    
}