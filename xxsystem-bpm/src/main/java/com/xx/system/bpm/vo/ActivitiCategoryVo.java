package com.dqgb.sshframe.bpm.vo;

/**
 * 
 * 流程种类Vo
 * 
 * @author zhxh
 * @version V1.20,2014-1-6 上午9:16:58
 * @since V1.20
 * @depricated
 */
public class ActivitiCategoryVo
{
    /** 主键 */
    private Integer categoryId;
    
    /** 名称 */
    private String name; // 分类 名字
    
    /** 父类id */
    private int parentId;
    
    /**
     * 分类编码
     */
    private String code;
    
    /** 是否是叶节点 0不是叶子节点,1是叶子节点 */
    private int leaf;
    
    /** 排序 */
    private int sort;//
    
    /**
     * 
     * @Title getSort
     * @author zhxh
     * @Description:获取排序值
     * @date 2014-1-6
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
     * @Description:设置排序值
     * @date 2014-1-6
     * @param sort
     */
    public void setSort(int sort)
    {
        this.sort = sort;
    }
    
    /**
     * 
     * @Title getLeaf
     * @author zhxh
     * @Description: 获取子节点标志
     * @date 2014-1-6
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
     * @Description: 设置子节点标志
     * @date 2014-1-6
     * @param leaf
     */
    public void setLeaf(int leaf)
    {
        this.leaf = leaf;
    }
    
    /**
     * 
     * @Title getCategoryId
     * @author zhxh
     * @Description:获取种类主键
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
     * @Description: 设置种类主键
     * @date 2014-1-6
     * @param categoryId
     */
    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }
    
    /**
     * 
     * @Title getName
     * @author zhxh
     * @Description:种类名称
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
     * @Description: 设置种类名称
     * @date 2014-1-6
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * 
     * @Title getParentId
     * @author zhxh
     * @Description: 获取父节点
     * @date 2014-1-6
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
     * @Description:设置父节点
     * @date 2014-1-6
     * @param parentId
     */
    public void setParentId(int parentId)
    {
        this.parentId = parentId;
    }
    
    /**
     * 
     * @Title getCode
     * @author zhxh
     * @Description: 获取种类编码
     * @date 2014-1-6
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
     * @Description:设置种类编码
     * @date 2014-1-6
     * @param code
     */
    public void setCode(String code)
    {
        this.code = code;
    }
    
}