/**
 * @文件名 ActivitiTaskRole.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 任务角色
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.entity;

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

import com.dqgb.sshframe.role.entity.Role;

/**
 * 
 * 任务角色实体类
 * 
 * @author zhxh
 * @version V1.20,2013-12-28 上午8:52:33
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "SYS_ACTIVITI_ROLE")
public class ActivitiTaskRole implements java.io.Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /** 主键 */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_ACTIVITI_TASK_ROLE_ID", nullable = false)
    private Integer id;
    
    /** 任务ID */
    @Column(length = 300, nullable = false, name = "TASKID")
    private String taskId;
    
    /** 角色主键 */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "FK_ROLE_ID")
    private Role role;
    
    /**
     * 
     * @Title getId
     * @author zhxh
     * @Description: 获取主键
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
     * @Description: 设置主键
     * @date 2014-1-6
     * @param id
     */
    public void setId(Integer id)
    {
        this.id = id;
    }
    
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
     * @Description: 设置任务Id
     * @date 2014-1-6
     * @param taskId
     */
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    /**
     * 
     * @Title getRole
     * @author zhxh
     * @Description:获取角色
     * @date 2014-1-6
     * @return Role
     */
    public Role getRole()
    {
        return role;
    }
    
    /**
     * 
     * @Title setRole
     * @author zhxh
     * @Description:
     * @date 2014-1-6
     * @param role
     */
    public void setRole(Role role)
    {
        this.role = role;
    }
    
}