/**
 * 文件名： WorkRound.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：班次实体类
 * 修改人： tangh
 * 修改时间： 2014年9月10日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * WorkRound（班次）实体定义
 * 
 * @author tangh
 * @version V1.40,2014年9月10日 下午4:40:11
 * @see [相关类/方法]
 * @since V1.40
 */
@Entity
@Table(name = "T_WORK_ROUND")
public class WorkRound implements Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = -7012008763848036963L;
    
    /**
     * @Fields id : 班次ID
     */
    private Integer id;
    
    /**
     * @Fields roundName : 班次名称
     */
    private String roundName;
    
    /**
     * @Fields endTime : 班次结束时间点
     */
    private Integer endTime;
    
    /**
     * @Fields startTime : 班次开始时间点
     */
    private Integer startTime;
    
    public WorkRound() {
        
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
     * @Title getId
     * @author dong.he
     * @Description: 班次结束时间点
     * @date 2014-8-22
     * @return 班次结束时间点
     */
    @Column(name = "END_TIME")
    public Integer getEndTime() {
        return endTime;
    }
    
    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
    
    /**
     * @Title getId
     * @author dong.he
     * @Description: 班次名称
     * @date 2014-8-22
     * @return 班次名称
     */
    @Column(name = "ROUND_NAME", nullable = false, length = 7)
    public String getRoundName() {
        return roundName;
    }
    
    /**
     * @param roundName the roundName to set
     */
    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }
    
    /**
     * @Title getId
     * @author dong.he
     * @Description: 班次开始时间点
     * @date 2014-8-22
     * @return 班次开始时间点
     */
    @Column(name = "START_TIME")
    public Integer getStartTime() {
        return startTime;
    }
    
    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }
    
}