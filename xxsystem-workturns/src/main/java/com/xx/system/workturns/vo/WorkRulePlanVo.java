/**
 * 文件名： WorkRulePlanVo.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：倒班计划VO实体类
 * 修改人： dong.he
 * 修改时间： 2014年8月22日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.vo;

import java.io.Serializable;

/**
 * WorkRulePlanVo（倒班计划）VO定义
 * 
 * @author dong.he
 * @version V1.4,2014-08-22 上午8:55:00
 * @see [相关类/方法]
 * @since V1.4
 */
public class WorkRulePlanVo implements Serializable
{
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = -2729675998910625045L;
    
    /**
     * @Fields id : 倒班计划ID
     */
    private Integer id;
    
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
     * @Fields roundId : 班次ID
     */
    private Integer roundId;
    
    /**
     * @Fields roundName : 班次名称
     */
    private String roundName;
    
    /**
     * @Fields workTeamId : 班组ID
     */
    private Integer workTeamId;
    
    /**
     * @Fields workTeamName : 班组名称
     */
    private String workTeamName;
    
    /**
     * @Fields orgId : 部门ID
     */
    private Integer orgId;
    
    /**
     * @Fields orgName : 部门名称
     */
    private String orgName;
    
    /**
     * @Fields planDate : 倒班计划时间
     */
    private String planDate;
    
    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Integer id)
    {
        this.id = id;
    }
    
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
     * @return the roundId
     */
    public Integer getRoundId()
    {
        return roundId;
    }
    
    /**
     * @param roundId the roundId to set
     */
    public void setRoundId(Integer roundId)
    {
        this.roundId = roundId;
    }
    
    /**
     * @return the roundName
     */
    public String getRoundName()
    {
        return roundName;
    }
    
    /**
     * @param roundName the roundName to set
     */
    public void setRoundName(String roundName)
    {
        this.roundName = roundName;
    }
    
    /**
     * @return the workTeamId
     */
    public Integer getWorkTeamId()
    {
        return workTeamId;
    }
    
    /**
     * @param workTeamId the workTeamId to set
     */
    public void setWorkTeamId(Integer workTeamId)
    {
        this.workTeamId = workTeamId;
    }
    
    /**
     * @return the workTeamName
     */
    public String getWorkTeamName()
    {
        return workTeamName;
    }
    
    /**
     * @param workTeamName the workTeamName to set
     */
    public void setWorkTeamName(String workTeamName)
    {
        this.workTeamName = workTeamName;
    }
    
    /**
     * @return the planDate
     */
    public String getPlanDate()
    {
        return planDate;
    }
    
    /**
     * @param planDate the planDate to set
     */
    public void setPlanDate(String planDate)
    {
        this.planDate = planDate;
    }
    
    /**
     * @return the orgId
     */
    public Integer getOrgId()
    {
        return orgId;
    }
    
    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }
    
    /**
     * @return the orgName
     */
    public String getOrgName()
    {
        return orgName;
    }
    
    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
}