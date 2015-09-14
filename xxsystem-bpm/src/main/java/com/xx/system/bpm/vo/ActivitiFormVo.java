package com.dqgb.sshframe.bpm.vo;

import java.util.List;

/**
 * 
 * 表单Vo
 * 
 * @author zhxh
 * @version V1.20,2014-1-6 上午9:53:33
 * @since V1.20
 * @depricated
 */
public class ActivitiFormVo
{
    /** 主键Id */
    private Integer id;
    
    /** 表单地址 */
    private String formUrl;
    
    /** 表单名称 */
    private String formName;
    
    /** 使用的节点 */
    private String adaptationNode;
    
    /** 适用的节点名称 */
    private String adaptationName;
    
    /** 类型外键 */
    private List<ActivitiCategoryVo> categories;
    
    /** 表单描述 */
    private String description;
    
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
     * @Title setAdaptationNode
     * @author zhxh
     * @Description:设置适用节点名称
     * @date 2014-1-6
     * @param adaptationNode
     */
    public String getAdaptationName()
    {
        return adaptationName;
    }
    
    /**
     * 
     * @Title setAdaptationNode
     * @author zhxh
     * @Description:设置适用节点名称
     * @date 2014-1-6
     * @param adaptationNode
     */
    public void setAdaptationName(String adaptationName)
    {
        this.adaptationName = adaptationName;
    }
    
    /**
     * 
     * @Title getCategories
     * @author zhxh
     * @Description: 获取适用种类
     * @date 2014-1-6
     * @return List<ActivitiCategory>
     */
    public List<ActivitiCategoryVo> getCategories()
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
    public void setCategories(List<ActivitiCategoryVo> categories)
    {
        this.categories = categories;
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
    
}
