package com.dqgb.sshframe.workturns.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RuleMap implements Serializable
{
    
    /**
    * @Fields serialVersionUID : 对象序列化ID
    */ 
    private static final long serialVersionUID = 6293115905358279885L;

    private Integer ruleId;
    
    private String ruleName;
    
    private Integer cycleDays;
    
    private Integer oldRuleCount;
    
    private Map<Integer, List<RoundTurnsVo>> ruleMaps;
    
    public Integer getRuleId()
    {
        return ruleId;
    }
    
    public void setRuleId(Integer ruleId)
    {
        this.ruleId = ruleId;
    }
    
    public String getRuleName()
    {
        return ruleName;
    }
    
    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }
    
    public Map<Integer, List<RoundTurnsVo>> getRuleMaps()
    {
        return ruleMaps;
    }
    
    public void setRuleMaps(Map<Integer, List<RoundTurnsVo>> ruleMaps)
    {
        this.ruleMaps = ruleMaps;
    }
    
    public Integer getCycleDays()
    {
        return cycleDays;
    }
    
    public void setCycleDays(Integer cycleDays)
    {
        this.cycleDays = cycleDays;
    }
    
    public Integer getOldRuleCount()
    {
        return oldRuleCount;
    }
    
    public void setOldRuleCount(Integer oldRuleCount)
    {
        this.oldRuleCount = oldRuleCount;
    }
    
}
