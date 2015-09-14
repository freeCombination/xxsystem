package com.dqgb.sshframe.bpm.vo;

import java.util.List;

/**
 * 
 * 任务节点值相关属性
 * 
 * @author zhxh
 * @version V1.20,2014-1-6 上午10:04:06
 * @since V1.20
 * @depricated
 */
public class ActivityProperty
{
    
    /** 变量名称 */
    private String variable;
    
    /** 变量值 */
    private String variableValue;
    
    /** 变量类型 String,int,boolean */
    private String variableType;
    
    /**
     * 
     * @Title getVariable
     * @author zhxh
     * @Description:获取值名称
     * @date 2014-1-6
     * @return String
     */
    public String getVariable()
    {
        return variable;
    }
    
    /**
     * 
     * @Title setVariable
     * @author zhxh
     * @Description: 设置值名称
     * @date 2014-1-6
     * @param variable
     */
    public void setVariable(String variable)
    {
        this.variable = variable;
    }
    
    /**
     * 
     * @Title getVariableValue
     * @author zhxh
     * @Description: 获取值
     * @date 2014-1-6
     * @return String
     */
    public String getVariableValue()
    {
        return variableValue;
    }
    
    /**
     * 
     * @Title setVariableValue
     * @author zhxh
     * @Description: 设置值
     * @date 2014-1-6
     * @param variableValue
     */
    public void setVariableValue(String variableValue)
    {
        this.variableValue = variableValue;
    }
    
    /**
     * 
     * @Title getVariableType
     * @author zhxh
     * @Description: 获取值类型
     * @date 2014-1-6
     * @return String
     */
    public String getVariableType()
    {
        return variableType;
    }
    
    /**
     * 
     * @Title setVariableType
     * @author zhxh
     * @Description: 设置值类型
     * @date 2014-1-6
     * @param variableType
     */
    public void setVariableType(String variableType)
    {
        this.variableType = variableType;
    }
    
}
