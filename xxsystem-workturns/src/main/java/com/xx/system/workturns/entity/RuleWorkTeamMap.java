/**
 * 文件名： RuleWorkTeamMap.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：倒班规则详细实体类
 * 修改人： tangh
 * 修改时间： 2014年9月10日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.entity;

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

/**
 * RuleWorkTeamMap（倒班规则详细）实体定义
 * 
 * @author tangh
 * @version V1.40,2014年9月10日 下午4:40:11
 * @see [相关类/方法]
 * @since V1.40
 */
@Entity
@Table(name = "T_RULE_WORK_TEAM_MAP")
public class RuleWorkTeamMap implements java.io.Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = -6400000231120962453L;
    
    /**
     * @Fields id : 规则详细ID
     */
    private Integer id;
    
    /**
     * @Fields workTeam : 规则关联的班组
     */
    private WorkTeam workTeam;
    
    /**
     * @Fields turnsOrder : 周期号,表示一个周期内的第几天。
     */
    private Integer turnsOrder;
    
    /**
     * @Fields workTurnsRule : 倒班规则
     */
    private WorkTurnsRule workTurnsRule;
    
    /**
     * @Fields workRound : 班次
     */
    private WorkRound workRound;
    
    public RuleWorkTeamMap() {
        
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
        return id;
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
     * @Description: 班组
     * @date 2014-8-22
     * @return 班组
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
     * @Title getTurnsOrder
     * @author dong.he
     * @Description: 周期号,表示一个周期内的第几天。
     * @date 2014-8-22
     * @return 周期号,表示一个周期内的第几天。
     */
    @Column(name = "RECYCLE_NUMBER", nullable = false)
    public Integer getTurnsOrder() {
        return turnsOrder;
    }
    
    /**
     * @param turnsOrder the turnsOrder to set
     */
    public void setTurnsOrder(Integer turnsOrder) {
        this.turnsOrder = turnsOrder;
    }
    
    /**
     * @Title getWorkTurnsRule
     * @author dong.he
     * @Description: 倒班规则
     * @date 2014-8-22
     * @return 倒班规则
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RULE_ID", nullable = false)
    public WorkTurnsRule getWorkTurnsRule() {
        return workTurnsRule;
    }
    
    /**
     * @param workTurnsRule the workTurnsRule to set
     */
    public void setWorkTurnsRule(WorkTurnsRule workTurnsRule) {
        this.workTurnsRule = workTurnsRule;
    }
    
    /**
     * @Title getWorkRound
     * @author dong.he
     * @Description: 班次
     * @date 2014-8-22
     * @return 班次
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_ROUND_ID", nullable = false)
    public WorkRound getWorkRound() {
        return workRound;
    }
    
    /**
     * @param workRound the workRound to set
     */
    public void setWorkRound(WorkRound workRound) {
        this.workRound = workRound;
    }
    
}