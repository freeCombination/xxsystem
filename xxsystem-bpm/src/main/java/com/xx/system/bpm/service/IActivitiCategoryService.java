/**
 * @文件名 IActivitiCategoryService.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板种类（类型）的逻辑层接口
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.common.exception.ServiceException;

/**
 * 
 * 流程模板种类（类型）的逻辑层接口
 * 
 * @author zhxh
 * @version V1.20,2013-12-25 下午5:36:23
 * @since V1.20
 * 
 */
public interface IActivitiCategoryService
{
    /**
     * 
     * @Title getActivitiCategory
     * @author zhxh
     * @Description: 分页查询流程种类
     * @date 2013-12-25
     * @param paramMap 分页参数和条件
     * @return List<ActivitiCategory>
     * @throws ServiceException
     */
    List<ActivitiCategory> getActivitiCategory(Map paramMap)
        throws ServiceException;
    
    /**
     * 
     * @Title addActivitiCategory
     * @author zhxh
     * @Description: 添加流程模板种类
     * @date 2013-12-26
     * @param activitiCategory
     * @throws ServiceException
     */
    void addActivitiCategory(ActivitiCategory activitiCategory)
        throws ServiceException;
    
    /**
     * 
     * @Title getLevelActivitiCategory
     * @author zhxh
     * @Description: 层次查询种类
     * @date 2013-12-26
     * @param id
     * @return List<ActivitiCategory>
     * @throws ServiceException
     */
    List<ActivitiCategory> getLevelActivitiCategory(int id)
        throws ServiceException;
    
    /**
     * 
     * @Title deleteActivitiCategory
     * @author zhxh
     * @Description: 删除流程模板种类
     * @date 2013-12-26
     * @param ids
     * @throws ServiceException
     */
    void deleteActivitiCategory(String ids)
        throws ServiceException;
    
    /**
     * 
     * @Title updateActivitiCategory
     * @author zhxh
     * @Description: 修改流程种类
     * @date 2013-12-26
     * @param category
     * @throws ServiceException
     */
    void updateActivitiCategory(ActivitiCategory category)
        throws ServiceException;
    
    /**
     * 
     * @Title getActivitiTopCategory
     * @author zhxh
     * @Description: 获取顶级种类
     * @date 2013-12-26
     * @param categoryId
     * @return ActivitiCategory
     */
    ActivitiCategory getActivitiTopCategory(Integer categoryId);
    
    /**
     * 
     * @Title getActivitiTopCategory
     * @author zhxh
     * @Description:针对树形flex，获取流程模板种类
     * @date 2013-12-26
     * @param categoryId
     * @return ActivitiCategory
     */
    public List<Map> getBPMType(Map map);
    
    /**
     * 
    * @Description: 获取流程种类列表 
    * @param @param map
    * @param @return     
    * @return    返回类型 
    * @author lijm
    * @date 2014-9-16 上午08:45:52
     */
    public List<Map> getBPMTypeByCheck(Map map);
    
    /**
     * 
     * @Title getActivitiCategoryById
     * @author zhxh
     * @Description:通过id组合获取流程种类
     * @date 2013-12-26
     * @param ids
     * @return List<ActivitiCategory>
     */
    List<ActivitiCategory> getActivitiCategoryById(String ids);
    

    /**
     * 
    * @Title createBpmFolder
    * @author zhxh
    * @Description:根据种类，创建相关的文件夹 
    * @date 2014-1-6
     */
    public Map createBpmFolder(ActivitiCategory category);
}
