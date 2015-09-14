/**
 * 文件名： WorkRoundVo.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：班次VO
 * 修改人： lizhengc
 * 修改时间： 2014年8月22日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.vo;

import java.io.Serializable;

/**
 * WorkRoundVo（班次）VO定义
 * 
 * @author dong.he
 * @version V1.4,2014-08-22 上午8:55:00
 * @see [相关类/方法]
 * @since V1.4
 */
public class WorkRoundVo implements Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = -1328589830016249559L;
    
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
    
    public Integer getId() {
        return id;
    }
    
    /**
     * @param id 班次ID
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getRoundName() {
        return roundName;
    }
    
    /**
     * @param roundName 班次名称
     */
    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }
    
    public Integer getEndTime() {
        return endTime;
    }
    
    /**
     * @param endTime 班次结束时间点
     */
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
    
    public Integer getStartTime() {
        return startTime;
    }
    
    /**
     * @param startTime 班次开始时间点
     */
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }
    
}