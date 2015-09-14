/**
 * @文件名 IActivitiCategoryDao.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板种类（类型）的持久层接口
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.ServiceException;

/**
 * 
 * 流程种类逻辑层接口
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 上午9:07:02
 * @since V1.20
 * @depricated
 */

public interface IActivitiCategoryDao extends IBaseDao
{
    /**
     * 
     * @Title getActivitiCategory
     * @author zhxh
     * @Description: 查询流程种类
     * @date 2013-12-26
     * @param paramMap 字段-值
     * @return List<ActivitiCategory>
     */
    List<ActivitiCategory> getActivitiCategory(Map paramMap);
    
    /**
     * 
     * @Title addActivitiCategory
     * @author zhxh
     * @Description: 添加流程种类
     * @date 2013-12-26
     * @param category
     */
    void addActivitiCategory(ActivitiCategory category);
    
    /**
     * 
     * @Title getLevelCategory
     * @author zhxh
     * @Description:层次查询种类数据
     * @date 2013-12-26
     * @param id
     * @return List<ActivitiCategory>
     */
    List<ActivitiCategory> getLevelCategory(int id);
    
    /**
     * 
     * @Title deleteActivitiCategory
     * @author zhxh
     * @Description: 删除模板种类
     * @date 2013-12-26
     * @param ids
     */
    
    void deleteActivitiCategory(String ids);
    
    /**
     * 修改种类数据
     * 
     * @throws ServiceException
     * @param category实体
     * @return void
     */
    void updateActivitiCategory(ActivitiCategory category);
    
    /**
     * 
     * @Title getCategoryById
     * @author zhxh
     * @Description:通过id组合获取流程模板种类
     * @date 2013-12-26
     * @param ids
     * @return List<ActivitiCategory>
     */
    List<ActivitiCategory> getCategoryById(String ids);
    
}
