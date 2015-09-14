/**
 * @文件名 ActivitiProcessInstance.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 委托实体类
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dqgb.sshframe.user.entity.User;

/**
 * 
 * 流程实例表,记录流程实例和业务的关系
 * 
 * @author zhxh
 * @version V1.20,2013-12-28 上午8:48:14
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "SYS_ACTIVITI_PROCESS_INST")
public class ActivitiProcessInstance implements Serializable
{
    
    /** @Fields serialVersionUID : */
    private static final long serialVersionUID = 1L;
    
    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_ACTIVITI_PROCESS_INST_ID")
    private Integer id;
    
    /** 流程实例id */
    @Column(name = "PROCESS_INSTANCE_ID", length = 255, nullable = true)
    private String processInstanceId;
    
    /** 流程发起人 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESS_CREATER")
    private User processCreater;
    
    /** 流程创建日期 */
    @Column(name = "PROCESS_CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processCreateDate;
    
    /** 流程名字 */
    @Column(name = "PROCESS_NAME", length = 255)
    private String processName;
    
    /** 流程模版key */
    @Column(name = "PROCESS_DefineKey", length = 255)
    private String processDefineKey;
    
    /** 流程类型 */
    @Column(name = "PROCESS_TYPE", length = 255)
    private String processType;
    
    /**
     * 
     * @Title getProcessCode
     * @author zhxh
     * @Description:获取流程代码
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
     * @Description: 设置流程代码
     * @date 2014-1-6
     * @param processCode
     */
    public void setProcessCode(String processCode)
    {
        this.processCode = processCode;
    }
    
    /** 流程代码 */
    @Column(name = "PROCESS_CODE", length = 255)
    private String processCode;
    
    /** 业务主键值，多个可以用逗号隔开 */
    @Column(name = "BUSINESS_ID", length = 1000)
    private String businessId;
    
    /** 业务数据的归属组织ID */
    @Column(name = "BUSINESS_ORG", length = 300)
    private String businessOrg;
    
    /**
     * 
     * @Title getBusinessId
     * @author zhxh
     * @Description: 获取业务主键值
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
     * @Description: 设置业务主键值
     * @date 2014-1-6
     * @param businessId
     */
    public void setBusinessId(String businessId)
    {
        this.businessId = businessId;
    }
    
    /**
     * 
     * @Title getProcessType
     * @author zhxh
     * @Description:获取流程名称
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
     * @Description: 设置流程名称
     * @date 2014-1-6
     * @param processType
     */
    public void setProcessType(String processType)
    {
        this.processType = processType;
    }
    
    /**
     * 
     * @Title getProcessDefineKey
     * @author zhxh
     * @Description: 获取流程定义key
     * @date 2014-1-6
     * @return String
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
     * @Title getProcessCreateDate
     * @author zhxh
     * @Description:获取流程创建日期
     * @date 2014-1-6
     * @return Date
     */
    public Date getProcessCreateDate()
    {
        return processCreateDate;
    }
    
    /**
     * 
     * @Title setProcessCreateDate
     * @author zhxh
     * @Description:设置流程创建日期
     * @date 2014-1-6
     * @param processCreateDate
     */
    public void setProcessCreateDate(Date processCreateDate)
    {
        this.processCreateDate = processCreateDate;
    }
    
    /**
     * 
     * @Title getProcessCreater
     * @author zhxh
     * @Description: 获取流程创建人员
     * @date 2014-1-6
     * @return User
     */
    public User getProcessCreater()
    {
        return processCreater;
    }
    
    /**
     * 
     * @Title setProcessCreater
     * @author zhxh
     * @Description: 设置流程创建人员
     * @date 2014-1-6
     * @param processCreater
     */
    public void setProcessCreater(User processCreater)
    {
        this.processCreater = processCreater;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getProcessInstanceId()
    {
        return processInstanceId;
    }
    
    public void setProcessInstanceId(String processInstanceId)
    {
        this.processInstanceId = processInstanceId;
    }
    
    /** 是否删除: 0 不删除，1 删除 */
    @Column(name = "ISDELETE", length = 2, nullable = true)
    private int isDelete;
    
    /**
     * 
     * @Title getIsDelete
     * @author zhxh
     * @Description: 获取删除标志
     * @date 2014-1-6
     * @return int
     */
    public int getIsDelete()
    {
        return isDelete;
    }
    
    /**
     * 
     * @Title setIsDelete
     * @author zhxh
     * @Description: 设置删除标志
     * @date 2014-1-6
     * @param isDelete
     */
    public void setIsDelete(int isDelete)
    {
        this.isDelete = isDelete;
    }
    
    /** 关联种类 */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "FK_ACTIVITI_CATEGORY_ID")
    private ActivitiCategory category;
    
    /**
     * 
     * @Title getCategory
     * @author zhxh
     * @Description: 获取流程种类
     * @date 2014-1-6
     * @return ActivitiCategory
     */
    public ActivitiCategory getCategory()
    {
        return category;
    }
    
    /**
     * 
     * @Title setCategory
     * @author zhxh
     * @Description: 设置流程种类
     * @date 2014-1-6
     * @param category
     */
    public void setCategory(ActivitiCategory category)
    {
        this.category = category;
    }

	public String getBusinessOrg() {
		return businessOrg;
	}

	public void setBusinessOrg(String businessOrg) {
		this.businessOrg = businessOrg;
	}
    
}
