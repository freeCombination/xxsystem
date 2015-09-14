package com.dqgb.sshframe.bpm.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 任务vo
 * 
 * @author zhxh
 * @version V1.20,2013-12-30 上午8:51:37
 * @since V1.20
 * @depricated
 */
public class TaskVo
{
    /** 任务ID */
    private String taskId;
    
    /** 委托人ID */
    private String owner;
    
    /** 委托人姓名 */
    private String ownerName;
    
    /** 任务名称 */
    private String name;
    
    /** 任务描述 */
    private String description;
    
    /** 任务创建时间 */
    private String createTime;
    
    /** 任务结束时间 */
    private String endTime;
    
    /** 任务定义Id */
    private String taskDefinitionKey;
    
    /** 流程定义ID */
    private String processInstanceId;
    
    /** 任务表单地址 */
    private String formUrl;
    
    /** 流程名称 */
    private String processName;
    
    /** 流程类型名称 */
    private String processType;
    
    /** 失效日期 */
    private String durationTime;
    
    /** 流程类型编码 */
    private String processCode;
    
    /** 流程定义Key */
    private String processDefinitionKey;
    
    /** 任务状态 1终止，2完成 */
    private String taskStatus;
    
    /** 业务主键值 */
    private String businessId;
    
    /** 任务人员Id */
    private String assignee;
    
    /** 任务所属人员名称 */
    private String assigneeName;
    
    /** 任务属性 */
    private List<ActivityProperty> activityPropertys = new ArrayList();
    
    /** 种类Id */
    private int categoryId;
    
    /** 种类名称 */
    private String categoryName;
    
    /** 种类编码 */
    private String categoryCode;
    
    /** 顶级种类Id */
    private int topCategoryId;
    
    /** 顶级种类名称 */
    private String topCategoryName;
    
    /** 顶级种类编码 */
    private String topCategoryCode;
    
    /** 执行节点Id */
    private String executionId;
    
    /**
     * 
     * @Title getCategoryId
     * @author zhxh
     * @Description:获取当前种类Id
     * @date 2014-1-6
     * @return int
     */
    public int getCategoryId()
    {
        return categoryId;
    }
    
    /**
     * 
     * @Title setCategoryId
     * @author zhxh
     * @Description: 设置当前种类Id
     * @date 2014-1-6
     * @param categoryId
     */
    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }
    
    /**
     * 
     * @Title getCategoryName
     * @author zhxh
     * @Description: 获取当前种类名称
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
     * @Description: 设置前种类名称
     * @date 2014-1-6
     * @param categoryName
     */
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
    
    /**
     * 
     * @Title getCategoryCode
     * @author zhxh
     * @Description: 获取当前种类编码
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
     * @Description: 设置当前种类编码
     * @date 2014-1-6
     * @param categoryCode
     */
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }
    
    /**
     * 
     * @Title getTopCategoryId
     * @author zhxh
     * @Description: 获取顶级种类Id
     * @date 2014-1-6
     * @return int
     */
    public int getTopCategoryId()
    {
        return topCategoryId;
    }
    
    /**
     * 
     * @Title setTopCategoryId
     * @author zhxh
     * @Description: 设置顶级种类Id
     * @date 2014-1-6
     * @param topCategoryId
     */
    public void setTopCategoryId(int topCategoryId)
    {
        this.topCategoryId = topCategoryId;
    }
    
    /**
     * 
     * @Title getTopCategoryName
     * @author zhxh
     * @Description: 获取顶级种类名称
     * @date 2014-1-6
     * @return String
     */
    public String getTopCategoryName()
    {
        return topCategoryName;
    }
    
    /**
     * 
     * @Title setTopCategoryName
     * @author zhxh
     * @Description: 设置顶级种类名称
     * @date 2014-1-6
     * @param topCategoryName
     */
    public void setTopCategoryName(String topCategoryName)
    {
        this.topCategoryName = topCategoryName;
    }
    
    /**
     * 
     * @Title getTopCategoryCode
     * @author zhxh
     * @Description: 设置顶级种类编码
     * @date 2014-1-6
     * @return String
     */
    public String getTopCategoryCode()
    {
        return topCategoryCode;
    }
    
    /**
     * 
     * @Title setTopCategoryCode
     * @author zhxh
     * @Description: 获取顶级种类代码
     * @date 2014-1-6
     * @param topCategoryCode
     */
    public void setTopCategoryCode(String topCategoryCode)
    {
        this.topCategoryCode = topCategoryCode;
    }
    
    /**
     * 
     * @Title getProcessCode
     * @author zhxh
     * @Description: 获取流程编码
     * @date 2014-1-6
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
     * @Description: 设置流程编码
     * @date 2014-1-6
     * @param processCode
     */
    public void setProcessCode(String processCode)
    {
        this.processCode = processCode;
    }
    
    /**
     * 
     * @Title getProcessType
     * @author zhxh
     * @Description: 获取流程类型名称
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
     * @Description: 设置流程类型名称
     * @date 2014-1-6
     * @param processType
     */
    public void setProcessType(String processType)
    {
        this.processType = processType;
    }
    
    /**
     * 
     * @Title getOwnerName
     * @author zhxh
     * @Description:获取任务委托人 姓名
     * @date 2014-1-6
     * @return String
     */
    public String getOwnerName()
    {
        return ownerName;
    }
    
    /**
     * 
     * @Title setOwnerName
     * @author zhxh
     * @Description:设置 任务委托人 姓名
     * @date 2014-1-6
     * @param ownerName
     */
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    /**
     * 
     * @Title getAssignee
     * @author zhxh
     * @Description:获取任务执行人员登录名
     * @date 2014-1-6
     * @return String
     */
    public String getAssignee()
    {
        return assignee;
    }
    
    /**
     * 
     * @Title setAssignee
     * @author zhxh
     * @Description: 设置用户执行人员登录名
     * @date 2014-1-6
     * @param assignee
     */
    public void setAssignee(String assignee)
    {
        this.assignee = assignee;
    }
    
    /**
     * 
     * @Title getAssigneeName
     * @author zhxh
     * @Description: 获取任务执行人员登录姓名
     * @date 2014-1-6
     * @return String
     */
    public String getAssigneeName()
    {
        return assigneeName;
    }
    
    /**
     * 
     * @Title setAssigneeName
     * @author zhxh
     * @Description:设置用户执行人员登录姓名
     * @date 2014-1-6
     * @param assigneeName
     */
    public void setAssigneeName(String assigneeName)
    {
        this.assigneeName = assigneeName;
    }
    
    /**
     * 
     * @Title getActivityPropertys
     * @author zhxh
     * @Description:获取其他值属性
     * @date 2014-1-6
     * @return activityPropertys
     */
    public List<ActivityProperty> getActivityPropertys()
    {
        return activityPropertys;
    }
    
    /**
     * 
     * @Title setActivityPropertys
     * @author zhxh
     * @Description:设置其他值属性
     * @date 2014-1-6
     * @param activityPropertys
     */
    public void setActivityPropertys(List<ActivityProperty> activityPropertys)
    {
        this.activityPropertys = activityPropertys;
    }
    
    /**
     * 
     * @Title getBusinessId
     * @author zhxh
     * @Description: 获取业务Id
     * @date 2014-1-6
     * @return String
     */
    public String getBusinessId()
    {
        return businessId;
    }
    
    /**
     * 
     * @Title setBusinessId
     * @author zhxh
     * @Description: 设置 业务Id
     * @date 2014-1-6
     * @param businessId
     */
    public void setBusinessId(String businessId)
    {
        this.businessId = businessId;
    }
    
    /**
     * 
     * @Title getTaskStatus
     * @author zhxh
     * @Description: 获取任务状态
     * @date 2014-1-6
     * @return String
     */
    public String getTaskStatus()
    {
        return taskStatus;
    }
    
    /**
     * 
     * @Title setTaskStatus
     * @author zhxh
     * @Description: 设置任务状态
     * @date 2014-1-6
     * @param taskStatus
     */
    public void setTaskStatus(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }
    
    /**
     * 
     * @Title getDurationTime
     * @author zhxh
     * @Description: 获取过期时间
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
     * @Description: 设置过期时间
     * @date 2014-1-6
     * @param durationTime
     */
    public void setDurationTime(String durationTime)
    {
        this.durationTime = durationTime;
    }
    
    /**
     * 
     * @Title getProcessName
     * @author zhxh
     * @Description:获取流程名称
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
     * @Title getFormUrl
     * @author zhxh
     * @Description: 获取表单地址
     * @date 2014-1-6
     * @return String
     */
    public String getFormUrl()
    {
        return formUrl;
    }
    
    /**
     * 
     * @Title setFormUrl
     * @author zhxh
     * @Description: 设置表单地址
     * @date 2014-1-6
     * @param formUrl
     */
    public void setFormUrl(String formUrl)
    {
        this.formUrl = formUrl;
    }
    
    /**
     * 
     * @Title getProcessInstanceId
     * @author zhxh
     * @Description: 获取流程实例ID
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
     * @Description:设置流程实例Id
     * @date 2014-1-6
     * @param processInstanceId
     */
    public void setProcessInstanceId(String processInstanceId)
    {
        this.processInstanceId = processInstanceId;
    }
    
    /**
     * 
     * @Title getTaskId
     * @author zhxh
     * @Description:获取任务ID
     * @date 2014-1-6
     * @return String
     */
    public String getTaskId()
    {
        return taskId;
    }
    
    /**
     * 
     * @Title setTaskId
     * @author zhxh
     * @Description: 设置任务ID
     * @date 2014-1-6
     * @param taskId
     */
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    /**
     * 
     * @Title getOwner
     * @author zhxh
     * @Description: 获取委托人登录名
     * @date 2014-1-6
     * @return String
     */
    public String getOwner()
    {
        return owner;
    }
    
    /**
     * 
     * @Title setOwner
     * @author zhxh
     * @Description:设置委托人登录名
     * @date 2014-1-6
     * @param owner
     */
    public void setOwner(String owner)
    {
        this.owner = owner;
    }
    
    /**
     * 
     * @Title getName
     * @author zhxh
     * @Description:获取任务名称
     * @date 2014-1-6
     * @return String
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * 
     * @Title setName
     * @author zhxh
     * @Description:设置任务名称
     * @date 2014-1-6
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * 
     * @Title getDescription
     * @author zhxh
     * @Description:获取任务意见
     * @date 2014-1-6
     * @return String
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * 
     * @Title setDescription
     * @author zhxh
     * @Description: 设置任务意见
     * @date 2014-1-6
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * 
     * @Title getCreateTime
     * @author zhxh
     * @Description: 获取任务创建时间
     * @date 2014-1-6
     * @return String
     */
    public String getCreateTime()
    {
        return createTime;
    }
    
    /**
     * 
     * @Title setCreateTime
     * @author zhxh
     * @Description: 设置任务创建时间
     * @date 2014-1-6
     * @param createTime
     */
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    /**
     * 
     * @Title getEndTime
     * @author zhxh
     * @Description: 获取任务结束时间
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
     * @Description: 设置任务结束时间
     * @date 2014-1-6
     * @param endTime
     */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    /**
     * 
     * @Title getTaskDefinitionKey
     * @author zhxh
     * @Description:获取任务key
     * @date 2014-1-6
     * @return String
     */
    public String getTaskDefinitionKey()
    {
        return taskDefinitionKey;
    }
    
    /**
     * 
     * @Title setTaskDefinitionKey
     * @author zhxh
     * @Description: 设置任务key
     * @date 2014-1-6
     * @param taskDefinitionKey
     */
    public void setTaskDefinitionKey(String taskDefinitionKey)
    {
        this.taskDefinitionKey = taskDefinitionKey;
    }
    
    /**
     * 
     * @Title getProcessDefinitionKey
     * @author zhxh
     * @Description: 获取流程定义Key
     * @date 2014-1-6
     * @return String
     */
    public String getProcessDefinitionKey()
    {
        return processDefinitionKey;
    }
    
    /**
     * 
     * @Title setProcessDefinitionKey
     * @author zhxh
     * @Description:设置流程定义key
     * @date 2014-1-6
     * @param processDefinitionKey
     */
    public void setProcessDefinitionKey(String processDefinitionKey)
    {
        this.processDefinitionKey = processDefinitionKey;
    }
    
    /**
     * 
     * @Title getExecutionId
     * @author zhxh
     * @Description: 获取执行节点Id
     * @date 2014-1-6
     * @return String
     */
    public String getExecutionId()
    {
        return executionId;
    }
    
    /**
     * 
     * @Title setExecutionId
     * @author zhxh
     * @Description: 设置执行节点ID
     * @date 2014-1-6
     * @param executionId
     */
    public void setExecutionId(String executionId)
    {
        this.executionId = executionId;
    }
    
}
