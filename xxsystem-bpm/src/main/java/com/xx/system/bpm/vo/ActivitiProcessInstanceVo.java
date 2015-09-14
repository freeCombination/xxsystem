package com.dqgb.sshframe.bpm.vo;

/**
 * 
 * 业务流程vo
 * 
 * @author zhxh
 * @version V1.20,2013-12-30 下午3:46:49
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class ActivitiProcessInstanceVo
{
    
    /** 流程实例id */
    private String processInstanceId;
    
    /** 流程发起人登录名 */
    private String userId;
    
    /** 流程发起人姓名 */
    private String userName;
    
    /** 流程定义key */
    private String processDefineKey;
    
    /** 流程名称 */
    private String processName;
    
    /** 流程状态0正在处理，1，完成，2终止 */
    private int processStatus;
    
    /** 终止原因 */
    private String deleteReason;
    
    /** 流程开始时间 */
    private String startTime;
    
    /** 流程结束时间 */
    private String endTime;
    
    /** 流程总共耗时时间 */
    private String durationTime;
    
    /** 流程类别 */
    private String processType;
    
    /** 流程种类 */
    private Integer categoryId;
    
    /** 流程种类代码 */
    private String categoryCode;
    
    /** 流程种类名称 */
    private String categoryName;
    
    
    /** 流程种代码 */
    private String processCode;
    
    
    /**
     * 
    * @Title getProcessCode
    * @author zhxh
    * @Description:获取流程代码 
    * @date 2014-1-7
    * @return String
     */
    public String getProcessCode()
    {
        return processCode;
    }
    /**
     * 
    * @Title setProcessCode
    * @author zhxh
    * @Description: 设置流程代码
    * @date 2014-1-7
    * @param processCode
     */
    public void setProcessCode(String processCode)
    {
        this.processCode = processCode;
    }

    /**
     * 
     * @Title getCategoryId
     * @author zhxh
     * @Description:获取种类主键
     * @date 2014-1-6
     * @return Integer
     */
    public Integer getCategoryId()
    {
        return categoryId;
    }
    
    /**
     * 
     * @Title setCategoryId
     * @author zhxh
     * @Description:设置种类主键
     * @date 2014-1-6
     * @param categoryId
     */
    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }
    
    /**
     * 
     * @Title getCategoryCode
     * @author zhxh
     * @Description: 获取种类编码
     * @date 2014-1-6
     * @return String
     */
    public String getCategoryCode()
    {
        return categoryCode;
    }
    
    /**
     * 
     * @Title setCategoryCode
     * @author zhxh
     * @Description: 设置种类编码
     * @date 2014-1-6
     * @param categoryCode
     */
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }
    
    /**
     * 
     * @Title getCategoryName
     * @author zhxh
     * @Description: 获取种类名称
     * @date 2014-1-6
     * @return String
     */
    public String getCategoryName()
    {
        return categoryName;
    }
    
    /**
     * 
     * @Title setCategoryName
     * @author zhxh
     * @Description: 设置种类名称
     * @date 2014-1-6
     * @param categoryName
     */
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
    
    /**
     * 
     * @Title getProcessDefineKey
     * @author zhxh
     * @Description: 获取流程定义key
     * @date 2014-1-6
     * @return processDefineKey
     */
    public String getProcessDefineKey()
    {
        return processDefineKey;
    }
    
    /**
     * 
     * @Title setProcessDefineKey
     * @author zhxh
     * @Description: 设置流程定义key
     * @date 2014-1-6
     * @param processDefineKey
     */
    public void setProcessDefineKey(String processDefineKey)
    {
        this.processDefineKey = processDefineKey;
    }
    
    /**
     * 
     * @Title getProcessInstanceId
     * @author zhxh
     * @Description: 获取流程实例Id
     * @date 2014-1-6
     * @return String
     */
    public String getProcessInstanceId()
    {
        return processInstanceId;
    }
    
    /**
     * 
     * @Title setProcessInstanceId
     * @author zhxh
     * @Description: 设置流程实例ID
     * @date 2014-1-6
     * @param processInstanceId
     */
    public void setProcessInstanceId(String processInstanceId)
    {
        this.processInstanceId = processInstanceId;
    }
    
    /**
     * 
     * @Title getUserId
     * @author zhxh
     * @Description: 获取创建用户的登录名
     * @date 2014-1-6
     * @return String
     */
    public String getUserId()
    {
        return userId;
    }
    
    /**
     * 
     * @Title setUserId
     * @author zhxh
     * @Description: 设置创建用户的登录名
     * @date 2014-1-6
     * @param userId
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    /**
     * 
     * @Title getUserName
     * @author zhxh
     * @Description: 获取创建用户姓名
     * @date 2014-1-6
     * @return String
     */
    public String getUserName()
    {
        return userName;
    }
    
    /**
     * 
     * @Title setUserName
     * @author zhxh
     * @Description: 设置创建用户姓名
     * @date 2014-1-6
     * @param userName
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    /**
     * 
     * @Title getProcessName
     * @author zhxh
     * @Description: 获取流程名称
     * @date 2014-1-6
     * @return String
     */
    public String getProcessName()
    {
        return processName;
    }
    
    /**
     * 
     * @Title setProcessName
     * @author zhxh
     * @Description: 设置流程名称
     * @date 2014-1-6
     * @param processName
     */
    public void setProcessName(String processName)
    {
        this.processName = processName;
    }
    
    /**
     * 
     * @Title getProcessStatus
     * @author zhxh
     * @Description: 获取流程状态
     * @date 2014-1-6
     * @return int
     */
    public int getProcessStatus()
    {
        return processStatus;
    }
    
    /**
     * 
     * @Title setProcessStatus
     * @author zhxh
     * @Description: 设置流程状态
     * @date 2014-1-6
     * @param processStatus
     */
    public void setProcessStatus(int processStatus)
    {
        this.processStatus = processStatus;
    }
    
    /**
     * 
     * @Title getDeleteReason
     * @author zhxh
     * @Description: 获取流程终止原因
     * @date 2014-1-6
     * @return String
     */
    public String getDeleteReason()
    {
        return deleteReason;
    }
    
    /**
     * 
     * @Title setDeleteReason
     * @author zhxh
     * @Description: 设置流程终止原因
     * @date 2014-1-6
     * @param deleteReason
     */
    public void setDeleteReason(String deleteReason)
    {
        this.deleteReason = deleteReason;
    }
    
    /**
     * 
     * @Title getStartTime
     * @author zhxh
     * @Description:获取流程开始时间
     * @date 2014-1-6
     * @return String
     */
    public String getStartTime()
    {
        return startTime;
    }
    
    /**
     * 
     * @Title setStartTime
     * @author zhxh
     * @Description: 设置流程开始时间
     * @date 2014-1-6
     * @param startTime
     */
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    /**
     * 
     * @Title getEndTime
     * @author zhxh
     * @Description: 获取流程结束时间
     * @date 2014-1-6
     * @return String
     */
    public String getEndTime()
    {
        return endTime;
    }
    
    /**
     * 
     * @Title setEndTime
     * @author zhxh
     * @Description: 设置流程结束时间
     * @date 2014-1-6
     * @param endTime
     */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    /**
     * 
     * @Title getDurationTime
     * @author zhxh
     * @Description: 获取无效时间
     * @date 2014-1-6
     * @return String
     */
    public String getDurationTime()
    {
        return durationTime;
    }
    
    /**
     * 
     * @Title setDurationTime
     * @author zhxh
     * @Description: 设置无效时间
     * @date 2014-1-6
     * @param durationTime
     */
    public void setDurationTime(String durationTime)
    {
        this.durationTime = durationTime;
    }
    
    /**
     * 
     * @Title getProcessType
     * @author zhxh
     * @Description:获取流程种类名称
     * @date 2014-1-6
     * @return String
     */
    public String getProcessType()
    {
        return processType;
    }
    
    /**
     * 
     * @Title setProcessType
     * @author zhxh
     * @Description: 设置流程种类名称
     * @date 2014-1-6
     * @param processType
     */
    public void setProcessType(String processType)
    {
        this.processType = processType;
    }
    
}
