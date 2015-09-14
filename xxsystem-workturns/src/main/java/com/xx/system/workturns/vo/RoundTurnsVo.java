package com.dqgb.sshframe.workturns.vo;

import java.io.Serializable;

/**
 * RoundTurnsVo（倒班计划数据行）VO定义
 * 
 * @author dong.he
 * @version V1.4,2014-08-22 上午8:55:00
 * @see [相关类/方法]
 * @since V1.4
 */
public class RoundTurnsVo implements Serializable
{
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = 151554356012806917L;
    
    private Integer ruleMapId;
    
    private Integer turnsOrder;
    
    private Integer roundId;
    
    private String roundName;
    
    private Integer workTeamId;
    
    private String workTeamName;
    
    public Integer getRuleMapId()
    {
        return ruleMapId;
    }
    
    public void setRuleMapId(Integer ruleMapId)
    {
        this.ruleMapId = ruleMapId;
    }
    
    public Integer getTurnsOrder()
    {
        return turnsOrder;
    }
    
    public void setTurnsOrder(Integer turnsOrder)
    {
        this.turnsOrder = turnsOrder;
    }
    
    public Integer getRoundId()
    {
        return roundId;
    }
    
    public void setRoundId(Integer roundId)
    {
        this.roundId = roundId;
    }
    
    public String getRoundName()
    {
        return roundName;
    }
    
    public void setRoundName(String roundName)
    {
        this.roundName = roundName;
    }
    
    public Integer getWorkTeamId()
    {
        return workTeamId;
    }
    
    public void setWorkTeamId(Integer workTeamId)
    {
        this.workTeamId = workTeamId;
    }
    
    public String getWorkTeamName()
    {
        return workTeamName;
    }
    
    public void setWorkTeamName(String workTeamName)
    {
        this.workTeamName = workTeamName;
    }
}
