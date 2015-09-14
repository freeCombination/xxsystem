/**
 * @文件名 ActivitiForm.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 委托实体类
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "SYS_Activiti_Form")
@Entity
public class ActivitiForm implements java.io.Serializable
{
    /** @Fields serialVersionUID : */
    private static final long serialVersionUID = 1L;
    
    /** 主键Id */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_ACTIVITI_FORM_ID")
    private Integer id;
    
    /** 表单地址 */
    @Column(name = "FORM_URL", length = 200)
    private String formUrl;
    
    /** 表单地址 */
    @Column(name = "FORM_NAME", length = 200)
    private String formName;
    
    /** 类型外键 */
    @ManyToMany(targetEntity = ActivitiCategory.class, cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_ACTIVITI_FORM_CATEGORY", joinColumns = {@JoinColumn(name = "PK_ACTIVITI_FORM_ID")}, inverseJoinColumns = {@JoinColumn(name = "PK_CATEGORY_ID")})
    private List<ActivitiCategory> categories;
    
    /** 表单描述 */
    @Column(name = "DESCRIPTION", length = 500)
    private String description;
    
    /** 适用节点 */
    @Column(name = "ADAPTATION_NODE", length = 500)
    private String adaptationNode;
    
    /**
     * 
     * @Title getAdaptationNode
     * @author zhxh
     * @Description:获取适用节点编码
     * @date 2014-1-6
     * @return String
     */
    public String getAdaptationNode()
    {
        return adaptationNode;
    }
    
    /**
     * 
     * @Title setAdaptationNode
     * @author zhxh
     * @Description:设置适用节点编码
     * @date 2014-1-6
     * @param adaptationNode
     */
    public void setAdaptationNode(String adaptationNode)
    {
        this.adaptationNode = adaptationNode;
    }
    
    /**
     * 
     * @Title getId
     * @author zhxh
     * @Description:获取主键
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
     * @Description:设置主键
     * @date 2014-1-6
     * @param id
     */
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    /**
     * 
     * @Title getFormUrl
     * @author zhxh
     * @Description:获取表单地址
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
     * @Title getCategories
     * @author zhxh
     * @Description: 获取适用种类
     * @date 2014-1-6
     * @return List<ActivitiCategory>
     */
    public List<ActivitiCategory> getCategories()
    {
        return categories;
    }
    
    /**
     * 
     * @Title setCategories
     * @author zhxh
     * @Description: 设置适用种类
     * @date 2014-1-6
     * @param categories
     */
    public void setCategories(List<ActivitiCategory> categories)
    {
        this.categories = categories;
    }
    
    /**
     * 
     * @Title getDescription
     * @author zhxh
     * @Description: 获取描述
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
     * @Description: 设置描述
     * @date 2014-1-6
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * 
     * @Title getFormName
     * @author zhxh
     * @Description:获取表单名称
     * @date 2014-1-6
     * @return String
     */
    public String getFormName()
    {
        return formName;
    }
    
    /**
     * 
     * @Title setFormName
     * @author zhxh
     * @Description:设置表单名称
     * @date 2014-1-6
     * @param formName
     */
    public void setFormName(String formName)
    {
        this.formName = formName;
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
     * @Description:设置删除标志
     * @date 2014-1-6
     * @param isDelete
     */
    public void setIsDelete(int isDelete)
    {
        this.isDelete = isDelete;
    }
    
}
