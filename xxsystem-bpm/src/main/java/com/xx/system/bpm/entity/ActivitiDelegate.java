/**
 * @文件名 ActivitiDelegate.java
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
 * 委托实体类
 * 
 * @author zhxh
 * @version V1.20,2013-12-28 上午8:26:12
 * @since V1.20
 * @depricated
 */
@Table(name = "SYS_Activiti_Delegate")
@Entity
public class ActivitiDelegate implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /** 主键 */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_Activiti_Delegate_ID")
    private Integer id;
    
    /** 被委托人 */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "DELEGATE_ID")
    private User delegateUser;
    
    /** 委托开始时间 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DElEGATE_START_TIME")
    private Date delegateStartTime;
    
    /** 委托结束时间 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DElEGATE_END_TIME")
    private Date delegateEndTime;
    
    /** 有效无效 1无效 0有效 */
    @Column(name = "DElEGATE_STATUS")
    private int delegateStatus;
    
    /** 委托人 */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "OWNER_ID")
    private User owner;
    
    /** 是否删除: 0 不删除，1 删除 */
    @Column(name = "ISDELETE", length = 2, nullable = true)
    private int isDelete;
    
    /**
     * 
     * @Title getIsDelete
     * @author zhxh
     * @Description: 获取删除标志
     * @date 2014-1-3
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
     * @date 2014-1-3
     * @param isDelete
     */
    public void setIsDelete(int isDelete)
    {
        this.isDelete = isDelete;
    }
    
    /**
     * 
     * @Title getId
     * @author zhxh
     * @Description: 获取主键
     * @date 2014-1-3
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
     * @Description:设置主键
     * @date 2014-1-3
     * @param id
     */
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    /**
     * 
     * @Title getOwner
     * @author zhxh
     * @Description:获取委托人
     * @date 2014-1-3
     * @return User
     */
    public User getOwner()
    {
        return owner;
    }
    
    /**
     * 
     * @Title setOwner
     * @author zhxh
     * @Description: 设置委托人
     * @date 2014-1-3
     * @param owner
     */
    public void setOwner(User owner)
    {
        this.owner = owner;
    }
    
    /**
     * 
     * @Title getDelegateUser
     * @author zhxh
     * @Description:获取被委托人
     * @date 2014-1-3
     * @return User
     */
    public User getDelegateUser()
    {
        return delegateUser;
    }
    
    /**
     * 
     * @Title setDelegateUser
     * @author zhxh
     * @Description: 设置被委托人
     * @date 2014-1-3
     * @param delegateUser
     */
    public void setDelegateUser(User delegateUser)
    {
        this.delegateUser = delegateUser;
    }
    
    /**
     * 
     * @Title getDelegateStartTime
     * @author zhxh
     * @Description: 获取被委托开始时间
     * @date 2014-1-3
     * @return Date
     */
    public Date getDelegateStartTime()
    {
        return delegateStartTime;
    }
    
    /**
     * 
     * @Title setDelegateStartTime
     * @author zhxh
     * @Description: 设置被委托开始时间
     * @date 2014-1-3
     * @param delegateStartTime
     */
    public void setDelegateStartTime(Date delegateStartTime)
    {
        this.delegateStartTime = delegateStartTime;
    }
    
    /**
     * 
     * @Title getDelegateEndTime
     * @author zhxh
     * @Description: 获取委托结束时间
     * @date 2014-1-3
     * @return Date
     */
    public Date getDelegateEndTime()
    {
        return delegateEndTime;
    }
    
    /**
     * 
     * @Title setDelegateEndTime
     * @author zhxh
     * @Description:设置委托结束时间
     * @date 2014-1-3
     * @param delegateEndTime
     */
    public void setDelegateEndTime(Date delegateEndTime)
    {
        this.delegateEndTime = delegateEndTime;
    }
    
    /**
     * 
     * @Title getDelegateStatus
     * @author zhxh
     * @Description:获取委托状态
     * @date 2014-1-3
     * @return int
     */
    public int getDelegateStatus()
    {
        return delegateStatus;
    }
    
    /**
     * 
     * @Title setDelegateStatus
     * @author zhxh
     * @Description:设置委托状态
     * @date 2014-1-3
     * @param delegateStatus
     */
    public void setDelegateStatus(int delegateStatus)
    {
        this.delegateStatus = delegateStatus;
    }
    
}
