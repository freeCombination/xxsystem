/**
 * @文件名 ActivitiDefineTemplate.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板实体类
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * 模板实体类
 * 
 * @author zhxh
 * @version V1.20,2013-12-28 上午8:24:57
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "SYS_ACTIVITI_DEFINE_TEMPLATE")
public class ActivitiDefineTemplate implements java.io.Serializable
{
    /** @Fields serialVersionUID : */
    private static final long serialVersionUID = 1L;
    
    /** 主键 */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_ACTIVITI_DEFINE_TEMPLATE_ID")
    private Integer id;
    
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
     * @Description: 设置主键
     * @date 2014-1-3
     * @param id
     */
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    /** 文件名称 */
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    
    /** 实际文件名称，系统转化 */
    @Column(name = "REAL_Name", length = 255)
    private String realName;
    
    /** 文件地址 */
    @Column(name = "URL", nullable = false, length = 255)
    private String url;
    
    /** 流程定义 key值 */
    @Column(name = "PROCESS_DEFINE_KEY", nullable = false, length = 255)
    private String processDefineKey;
    
    /** 模版种类 */
    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY_ID", nullable = false)
    private ActivitiCategory category;
    
    /**
     * 
     * @Title getCategory
     * @author zhxh
     * @Description:获取种类
     * @date 2014-1-3
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
     * @Description:设置种类
     * @date 2014-1-3
     * @param category
     */
    public void setCategory(ActivitiCategory category)
    {
        this.category = category;
    }
    
    /**
     * 
     * @Title getRealName
     * @author zhxh
     * @Description:获取真实名称
     * @date 2014-1-3
     * @return String
     */
    public String getRealName()
    {
        return realName;
    }
    
    /**
     * 
     * @Title setRealName
     * @author zhxh
     * @Description:设置真实名称
     * @date 2014-1-3
     * @param realName
     */
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
    /**
     * 
     * @Title getProcessDefineKey
     * @author zhxh
     * @Description:获取流程定义
     * @date 2014-1-3
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
     * @Description:设置流程定义
     * @date 2014-1-3
     * @param processDefineKey
     */
    public void setProcessDefineKey(String processDefineKey)
    {
        this.processDefineKey = processDefineKey;
    }
    
    /**
     * 
     * @Title getName
     * @author zhxh
     * @Description:获取系统名称
     * @date 2014-1-3
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
     * @Description: 设置系统名称
     * @date 2014-1-3
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * 
     * @Title getUrl
     * @author zhxh
     * @Description: 获取相对路径
     * @date 2014-1-3
     * @return String
     */
    public String getUrl()
    {
        return url;
    }
    
    /**
     * 
     * @Title setUrl
     * @author zhxh
     * @Description:设置相对路径
     * @date 2014-1-3
     * @param url
     */
    public void setUrl(String url)
    {
        this.url = url;
    }
    
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
}
