/**
 * 文件名： WorkRulePlan.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：班次实体类
 * 修改人： tangh
 * 修改时间： 2014年9月10日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.entity;

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

/**
 * WorkRulePlan（倒班计划详细）实体定义
 * 
 * @author tangh
 * @version V1.40,2014年9月10日 下午4:40:11
 * @see [相关类/方法]
 * @since V1.4
 */
@Entity
@Table(name = "T_WORK_TURNS_PLAN")
public class WorkRulePlan implements java.io.Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = -3785633795230533558L;
    
    /**
     * @Fields id : 倒班计划详细ID
     */
    private Integer id;
    
    /**
     * @Fields workTurnsRule : 倒班规则
     */
    private WorkTurnsRule workTurnsRule;
    
    /**
     * @Fields planDate : 倒班计划时间
     */
    private Date planDate;
    
    /**
     * @Fields workRound : 班次
     */
    private WorkRound workRound;
    
    /**
     * @Fields workTeam : 班组
     */
    private WorkTeam workTeam;
    
    public WorkRulePlan() {
        
    }
    
    public WorkRulePlan(WorkTurnsRule workTurnsRule, Date planDate,
        WorkRound workRound, WorkTeam workTeam) {
        this.planDate = planDate;
        this.workRound = workRound;
        this.workTeam = workTeam;
        this.workTurnsRule = workTurnsRule;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_TURNS_RULE_ID")
    public WorkTurnsRule getWorkTurnsRule() {
        return workTurnsRule;
    }
    
    /**
     * @param workTurnsRule the workTurnsRule to set
     */
    public void setWorkTurnsRule(WorkTurnsRule workTurnsRule) {
        this.workTurnsRule = workTurnsRule;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLAN_DATE", nullable = false, length = 7)
    public Date getPlanDate() {
        return planDate;
    }
    
    /**
     * @param planDate the planDate to set
     */
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_ROUND_ID")
    public WorkRound getWorkRound() {
        return workRound;
    }
    
    /**
     * @param workRound the workRound to set
     */
    public void setWorkRound(WorkRound workRound) {
        this.workRound = workRound;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_TEAM_ID")
    public WorkTeam getWorkTeam() {
        return workTeam;
    }
    
    /**
     * @param workTeam the workTeam to set
     */
    public void setWorkTeam(WorkTeam workTeam) {
        this.workTeam = workTeam;
    }
    
}