/**
 * 文件名： WorkTeamMember.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：班组成员实体类
 * 修改人： tangh
 * 修改时间： 2014年9月10日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.entity;

import java.io.Serializable;

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

import com.dqgb.sshframe.user.entity.User;

/**
 * WorkTeamMember（班组成员）实体定义
 * 
 * @author tangh
 * @version V1.40,2014年9月10日 下午4:40:11
 * @see [相关类/方法]
 * @since V1.4
 */
@Entity
@Table(name = "T_WORK_TEAM_MEMBER")
public class WorkTeamMember implements Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = -4423500539830999550L;
    
    /**
     * @Fields id : 班组成员关联ID
     */
    private Integer id;
    
    /**
     * @Fields workTeam : 关联的班组
     */
    private WorkTeam workTeam;
    
    /**
     * @Fields members : 班组成员
     */
    private User members;
    
    // Constructors
    
    /** default constructor */
    public WorkTeamMember() {
    }
    
    /** minimal constructor */
    public WorkTeamMember(Integer id, WorkTeam workTeam, User members) {
        this.id = id;
        this.workTeam = workTeam;
        this.members = members;
    }
    
    // Property accessors
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
     * @Title getWorkTeam
     * @author dong.he
     * @Description: 关联的班组
     * @date 2014-8-22
     * @return 关联的班组
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_TEAM_ID", nullable = false)
    public WorkTeam getWorkTeam() {
        return workTeam;
    }
    
    /**
     * @param workTeam the workTeam to set
     */
    public void setWorkTeam(WorkTeam workTeam) {
        this.workTeam = workTeam;
    }
    
    /**
     * @Title getMembers
     * @author dong.he
     * @Description: 班组成员
     * @date 2014-8-22
     * @return 班组成员
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = true)
    public User getMembers() {
        return members;
    }
    
    /**
     * @param members the members to set
     */
    public void setMembers(User members) {
        this.members = members;
    }
}