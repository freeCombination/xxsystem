/**
 * @文件名 ActivitiProcessApproval.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 审批意见表
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.dqgb.sshframe.user.entity.User;

/**
 * 
 * 审批意见实体类
 * 
 * @author zhxh
 * @version V1.20,2013-12-28 上午8:30:45
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "SYS_ACTIVITI_PROCESS_APPROVAL")
public class ActivitiProcessApproval implements java.io.Serializable
{
    
    private static final long serialVersionUID = -5598131603959637969L;
    
    /** 流程审批意见主键 */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_ACTIVITI_PRO_APPROVAL_ID")
    private Integer id;
    
    /** 审批结果 0.未通过， 1.通过 2.非审批节点 */
    @Column(name = "APPROVAL", length = 1)
    private int approval;
    
    /** 流程审批意见 */
    @Column(name = "OPINION", length = 2000)
    private String opinion;
    
    /** 流程实例id */
    @Column(name = "PROCESS_INSTANCE_ID", length = 255)
    private String processInstanceId;
    
    /** 电子签名 */
    @Column(name = "ELECTRONIC_SIGNATURE")
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private Byte[] eSignature;
    
    /** 电子签名来源 0 没有电子签名 1来至于blob字段 2来至于url */
    @Column(name = "ELECTRONIC_SOURCE", length = 2)
    private int eSource = 0;
    
    /** 电子签名url */
    @Column(name = "ELECTRONIC_URL", length = 300)
    private String eUrl;
    
    /**
     * 
     * @Title geteUrl
     * @author zhxh
     * @Description:获取电子签名地址
     * @date 2014-1-6
     * @return String
     */
    public String geteUrl()
    {
        return eUrl;
    }
    
    /**
     * 
     * @Title seteUrl
     * @author zhxh
     * @Description: 设置电子签名地址
     * @date 2014-1-6
     * @param eUrl
     */
    public void seteUrl(String eUrl)
    {
        this.eUrl = eUrl;
    }
    
    /**
     * 
     * @Title geteSource
     * @author zhxh
     * @Description: 获取电子签名来源
     * @date 2014-1-6
     * @return int
     */
    public int geteSource()
    {
        return eSource;
    }
    
    /**
     * 
     * @Title seteSource
     * @author zhxh
     * @Description: 设置电子签名来源
     * @date 2014-1-6
     * @param eSource
     */
    public void seteSource(int eSource)
    {
        this.eSource = eSource;
    }
    
    /**
     * 
     * @Title getId
     * @author zhxh
     * @Description: 获取主键ID
     * @date 2014-1-6
     * @return Integer
     */
    public Integer getId()
    {
        return id;
    }
    
    /**
     * 
     * @Title setId
     * @author zhxh
     * @Description:设置主键ID
     * @date 2014-1-6
     * @param id
     */
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    /**
     * 
     * @Title getApproval
     * @author zhxh
     * @Description: 获取通过标志
     * @date 2014-1-6
     * @return int
     */
    public int getApproval()
    {
        return approval;
    }
    
    /**
     * 
     * @Title setApproval
     * @author zhxh
     * @Description: 设置通过标志
     * @date 2014-1-6
     * @param approval
     */
    public void setApproval(int approval)
    {
        this.approval = approval;
    }
    
    /**
     * 
     * @Title getOpinion
     * @author zhxh
     * @Description: 获取意见
     * @date 2014-1-6
     * @return String
     */
    public String getOpinion()
    {
        return opinion;
    }
    
    /**
     * 
     * @Title setOpinion
     * @author zhxh
     * @Description:设置意见
     * @date 2014-1-6
     * @param opinion
     */
    public void setOpinion(String opinion)
    {
        this.opinion = opinion;
    }
    
    /**
     * 
     * @Title setProcessInstanceId
     * @author zhxh
     * @Description: 设置流程实例
     * @date 2014-1-6
     * @param processInstanceId
     */
    public void setProcessInstanceId(String processInstanceId)
    {
        this.processInstanceId = processInstanceId;
    }
    
    /**
     * 
     * @Title getProcessInstanceId
     * @author zhxh
     * @Description: 获取流程实例
     * @date 2014-1-6
     * @return String
     */
    public String getProcessInstanceId()
    {
        return processInstanceId;
    }
    
    /** 处理人 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USER_ID")
    private User dealUser;
    
    /** 是否删除: 0 不删除，1 删除 */
    @Column(name = "ISDELETE", length = 2, nullable = true)
    private int isDelete;
    
    /**
     * 
     * @Title getIsDelete
     * @author zhxh
     * @Description:获取删除标志
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
    
    /**
     * @Title geteSignature
     * @author wanglc
     * @Description: 电子签名
     * @date 2013-12-6
     * @return 电子签名
     */
    
    public Byte[] geteSignature()
    {
        return eSignature;
    }
    
    /**
     * 
     * @Title seteSignature
     * @author zhxh
     * @Description: 设置电子签名
     * @date 2014-1-6
     * @param eSignature
     */
    public void seteSignature(Byte[] eSignature)
    {
        this.eSignature = eSignature;
    }
    
    /** 任务Id */
    @Column(name = "TASKID", length = 255)
    private String taskId;
    
    /**
     * 
     * @Title getTaskId
     * @author zhxh
     * @Description:获取任务Id
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
     * @Description:设置任务Id
     * @date 2014-1-6
     * @param taskId
     */
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    /**
     * 
     * @Title getDealUser
     * @author zhxh
     * @Description:获取任务处理人
     * @date 2014-1-6
     * @return User
     */
    public User getDealUser()
    {
        return dealUser;
    }
    
    /**
     * 
     * @Title setDealUser
     * @author zhxh
     * @Description:设置任务处理人
     * @date 2014-1-6
     * @param dealUser
     */
    public void setDealUser(User dealUser)
    {
        this.dealUser = dealUser;
    }
    
}
