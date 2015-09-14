/**
 * @文件名 ActivitiCategory.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程种类实体类
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 流程种类实体类
 * 
 * @author zhxh
 * @version V1.20,2013-12-28 上午8:22:55
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "SYS_ACTIVITI_CATEGORY")
public class ActivitiCategory implements java.io.Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /** 种类主键 */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_ACTIVITI_CATEGORY_ID", nullable = false)
    private Integer categoryId;
    
    /** 种类名称 */
    @Column(name = "NAME", nullable = false, length = 100)
    private String name; // 分类 名字
    
    /** 种类父节点 */
    @Column(name = "PARENTID")
    private int parentId;
    
    /**
     * 分类编码
     */
    @Column(name = "CATEGORY_CODE", nullable = false, length = 50)
    private String code;
    
    /** 是否是叶节点 0不是叶子节点,1是叶子节点 */
    @Column(name = "LEAF", length = 10)
    private int leaf;
    
    /** 描述 */
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
    
    /**
     * 
     * @Title getDescription
     * @author zhxh
     * @Description:获取描述
     * @date 2013-12-28
     * @return
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
     * @date 2013-12-28
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /** 排序 */
    @Column(name = "SORT")
    private int sort;//
    
    /**
     * 
     * @Title getSort
     * @author zhxh
     * @Description:获取排序
     * @date 2014-1-3
     * @return int
     */
    public int getSort()
    {
        return sort;
    }
    
    /**
     * 
     * @Title setSort
     * @author zhxh
     * @param leaf
     * @Description:设置排序
     * @date 2014-1-3
     */
    public void setSort(int sort)
    {
        this.sort = sort;
    }
    
    /**
     * 
     * @Title getLeaf
     * @author zhxh
     * @Description:获取子节点标志
     * @date 2014-1-3
     * @return int
     */
    public int getLeaf()
    {
        return leaf;
    }
    
    /**
     * 
     * @Title setLeaf
     * @author zhxh
     * @Description:获取子节点标志
     * @date 2014-1-3
     */
    public void setLeaf(int leaf)
    {
        this.leaf = leaf;
    }
    
    /**
     * 
     * @Title getCategoryId
     * @author zhxh
     * @Description:获取种类节点ID
     * @date 2014-1-3
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
     * @param categoryId
     * @Description:设置种类节点ID
     * @date 2014-1-3
     */
    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }
    
    /**
     * 
     * @Title getName
     * @author zhxh
     * @Description:获取名称
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
     * @param name
     * @Description:设置名称
     * @date 2014-1-3
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * 
     * @Title getParentId
     * @author zhxh
     * @Description:获取父亲节点Id
     * @date 2014-1-3
     * @return int
     */
    public int getParentId()
    {
        return parentId;
    }
    
    /**
     * 
     * @Title setParentId
     * @author zhxh
     * @param parentId
     * @Description:设置父亲节点Id
     * @date 2014-1-3
     */
    public void setParentId(int parentId)
    {
        this.parentId = parentId;
    }
    
    /**
     * 
     * @Title getCode
     * @author zhxh
     * @Description:获取种类代码
     * @date 2014-1-3
     * @return String
     */
    public String getCode()
    {
        return code;
    }
    
    /**
     * 
     * @Title setCode
     * @author zhxh
     * @Description:设置种类代码
     * @param code
     * @date 2014-1-3
     */
    public void setCode(String code)
    {
        this.code = code;
    }
    
    /** 是否删除: 0 不删除，1 删除 */
    @Column(name = "ISDELETE", length = 2, nullable = true)
    private int isDelete;
    
    /**
     * 
     * @Title getIsDelete
     * @author zhxh
     * @Description:获取删除标志
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