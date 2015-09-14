package com.dqgb.sshframe.bpm.vo;

/**
 * 
 * 流程模板Vo
 * 
 * @author zhxh
 * @version V1.20,2014-1-6 上午9:40:33
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class ActivitiDefineTemplateVo
{
    /** 主键 */
    private Integer id;
    
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
    
    /** 文件名称 */
    
    private String name;
    
    /** 实际文件名称，系统转化 */
    private String realName;
    
    /** 文件地址 */
    private String url;
    
    /** 流程定义 key值 */
    private String processDefineKey;
    
    /** 模版种类id */
    private Integer categoryId;
    
    /**
     * 
     * @Title getCategoryId
     * @author zhxh
     * @Description: 获取种类Id
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
     * @Description: 设置种类ID
     * @date 2014-1-6
     * @param categoryId
     */
    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }
    
    /** 种类名称 */
    private String categoryName;
    
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
     * @Description:设置种类名称
     * @date 2014-1-6
     * @param categoryName
     */
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
    
    /**
     * 
     * @Title getRealName
     * @author zhxh
     * @Description: 获取模板真实名称
     * @date 2014-1-6
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
     * @Description: 设置模板真实名称
     * @date 2014-1-6
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
     * @Description: 获取模板定义key
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
     * @Description: 设置模板定义key
     * @date 2014-1-6
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
    
}
