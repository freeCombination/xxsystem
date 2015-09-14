/**
 * 文件名： WorkRulePlanDateVo.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：倒班规则计划最大时间和最小时间VO实体类
 * 修改人： dong.he
 * 修改时间： 2014年8月22日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.vo;

import java.io.Serializable;

/**
 * WorkRulePlanDateVo（倒班规则计划最大时间和最小时间）VO定义
 * 
 * @author dong.he
 * @version V1.4,2014-08-22 上午8:55:00
 * @see [相关类/方法]
 * @since V1.4
 */
public class WorkRulePlanDateVo implements Serializable
{
    
    /**
    * @Fields serialVersionUID : 对象序列化ID
    */ 
    private static final long serialVersionUID = 283021313674663293L;

    /**
     * @Fields ruleId : 倒班规则ID
     */
    private Integer ruleId;
    
    /**
     * @Fields ruleName : 倒班规则名称
     */
    private String ruleName;
    
    /**
     * @Fields cycleDays : 倒班周期天数
     */
    private Integer cycleDays;
    
    /**
     * @Fields minDate : 最小时间
     */
    private String minDate;
    
    /**
     * @Fields maxDate : 最大时间
     */
    private String maxDate;
    
    /**
     * @Fields nowDate : 现在
     */
    private String nowDate;
    
    /**
     * @return the ruleId
     */
    public Integer getRuleId()
    {
        return ruleId;
    }
    
    /**
     * @param ruleId the ruleId to set
     */
    public void setRuleId(Integer ruleId)
    {
        this.ruleId = ruleId;
    }
    
    /**
     * @return the ruleName
     */
    public String getRuleName()
    {
        return ruleName;
    }
    
    /**
     * @param ruleName the ruleName to set
     */
    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }
    
    /**
     * @return the cycleDays
     */
    public Integer getCycleDays()
    {
        return cycleDays;
    }
    
    /**
     * @param cycleDays the cycleDays to set
     */
    public void setCycleDays(Integer cycleDays)
    {
        this.cycleDays = cycleDays;
    }
    
    /**
     * @return the minDate
     */
    public String getMinDate()
    {
        return minDate;
    }
    
    /**
     * @param minDate the minDate to set
     */
    public void setMinDate(String minDate)
    {
        this.minDate = minDate;
    }
    
    /**
     * @return the maxDate
     */
    public String getMaxDate()
    {
        return maxDate;
    }
    
    /**
     * @param maxDate the maxDate to set
     */
    public void setMaxDate(String maxDate)
    {
        this.maxDate = maxDate;
    }
    
    /**
     * @return the nowDate
     */
    public String getNowDate()
    {
        return nowDate;
    }
    
    /**
     * @param nowDate the nowDate to set
     */
    public void setNowDate(String nowDate)
    {
        this.nowDate = nowDate;
    }
    
}
