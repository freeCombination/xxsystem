package com.xx.system.role.service;

import java.util.List;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.role.entity.RoleResource;

/**
 * 角色资源接口定义
 * 
 * @version V1.20,2013-11-25 下午2:40:08
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public interface IRoleResourceService {
    
    /**
     * 获取角色资源
     * 
     * @Title getOwnResByUserRole
     * @author wanglc
     * @date 2013-11-25
     * @param roleIds 角色ID数组字符串，以逗号分隔
     * @return List<RoleResource> 角色资源关联对象集合
     */
    List<RoleResource> getOwnResByUserRole(String roleIds)
        throws BusinessException;
    
}
