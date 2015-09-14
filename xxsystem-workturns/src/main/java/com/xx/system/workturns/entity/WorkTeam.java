/**
 * 文件名： WorkTeam.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：班组实体类
 * 修改人： tangh
 * 修改时间： 2014年8月22日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dqgb.sshframe.org.entity.Organization;
import com.dqgb.sshframe.user.entity.User;

/**
 * WorkTeam（班组）实体定义
 * 
 * @author tangh
 * @version V1.4,2014-08-22 上午8:55:00
 * @see [相关类/方法]
 * @since V1.4
 */
@Entity
@Table(name = "T_WORK_TEAM")
public class WorkTeam implements Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = 1230051032089053161L;
    
    /**
     * @Fields id : 班组ID
     */
    private Integer id;
    
    /**
     * @Fields org : 班组所属组织
     */
    private Organization org;
    
    /**
     * @Fields monitor : 班组负责人，或叫班长
     */
    private User monitor;
    
    /**
     * @Fields teamName : 班组名称
     */
    private String teamName;
    
    /**
     * @Fields createDate : 创建时间
     */
    private Date createDate;
    
    /**
     * @Fields remark : 备注
     */
    private String remark;
    
    /** default constructor */
    public WorkTeam() {
    }
    
    /**
     * @Title getId
     * @author dong.he
     * @Description: 主键
     * @date 2014-8-22
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public Integer getId() {
        return this.id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @Title getOrg
     * @author dong.he
     * @Description: 班组所属组织
     * @date 2014-8-22
     * @return 班组所属组织
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID", nullable = false)
    public Organization getOrg() {
        return this.org;
    }
    
    /**
     * @param org the org to set
     */
    public void setOrg(Organization org) {
        this.org = org;
    }
    
    /**
     * @Title getMonitor
     * @author dong.he
     * @Description: 班组负责人，或叫班长
     * @date 2014-8-22
     * @return 班组负责人，或叫班长
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MONITOR_ID", nullable = true)
    public User getMonitor() {
        return monitor;
    }

    /**
     * @param monitor the monitor to set
     */
    public void setMonitor(User monitor) {
        this.monitor = monitor;
    }
    
    /**
     * @Title getTeamName
     * @author dong.he
     * @Description: 班组名称
     * @date 2014-8-22
     * @return 班组名称
     */
    @Column(name = "TEAM_NAME", nullable = false, length = 100)
    public String getTeamName() {
        return teamName;
    }
    
    /**
     * @param teamName the teamName to set
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    /**
     * @Title getCreateDate
     * @author dong.he
     * @Description: 创建时间
     * @date 2014-8-22
     * @return 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", nullable = false)
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * @Title getRemark
     * @author dong.he
     * @Description: 备注
     * @date 2014-8-22
     * @return 备注
     */
    @Column(name = "REMARK", nullable = true, length = 2000)
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}