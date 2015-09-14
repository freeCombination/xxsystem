package com.xx.system.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.role.entity.RoleResource;
import com.xx.system.role.service.IRoleResourceService;

/**
 * 详细事项角色资源接口定义的方法
 * 
 * @version V1.20,2013-11-25 下午2:42:32
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings("unchecked")
@Service("roleResourceService")
public class RoleResourceServiceImpl implements IRoleResourceService {
    
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    /**
     * 获取角色资源
     * 
     * @Title getOwnResByUserRole
     * @author wanglc
     * @date 2013-11-25
     * @param roleIds 角色ID数组字符串，以逗号分隔
     * @return
     */
    @Override
    public List<RoleResource> getOwnResByUserRole(String roleIds)
        throws BusinessException {
        if (roleIds != null && !"".equals(roleIds)) {
            String hql =
                "from RoleResource rr where rr.role.roleId in (" + roleIds
                    + ")";
            List<RoleResource> rrList = baseDao.queryEntitys(hql);
            if (rrList.size() > 0) {
                return rrList;
            }
        }
        return null;
    }
    
}
