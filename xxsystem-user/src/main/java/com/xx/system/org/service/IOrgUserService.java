package com.xx.system.org.service;

import java.util.List;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.exception.ServiceException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.user.entity.User;

/**
 * 用户组织逻辑接口
 * 
 * @version V1.20,2013-11-25 下午3:40:42
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public interface IOrgUserService {
    
    /**
     * 添加用户组织
     * 
     * @Title addOrgUser
     * @author wanglc
     * @date 2013-11-25
     * @param orgUser 组织用户对象
     * @throws BusinessException
     */
    public void addOrgUser(OrgUser orgUser)
        throws BusinessException;
    
    /**
     * 修改组织用户关系
     * 
     * @Title modifyOrgUser
     * @author wanglc
     * @date 2013-11-25
     * @param orgUser 用户组织对象
     * @throws BusinessException
     */
    public void modifyOrgUser(OrgUser orgUser)
        throws BusinessException;
    
    /**
     * 删除组织用户关系
     * 
     * @Title deleteOrgUsers
     * @author wanglc
     * @date 2013-11-25
     * @param orgUserList 组织用户列表
     * @throws BusinessException
     */
    public void deleteOrgUsers(List<OrgUser> orgUserList)
        throws BusinessException;
    
    /**
     * 根据主键获取组织用户对象
     * 
     * @Title getOrgUserById
     * @author wanglc
     * @date 2013-11-25
     * @param orgUserId 组织用户主键
     * @return OrgUser 用户组织关系对象
     * @throws BusinessException
     */
    public OrgUser getOrgUserById(int orgUserId)
        throws BusinessException;
    
    /**
     * 获取当前用户的组织用户关系列表
     * 
     * @Title getOrgUserByUserId
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @return List<OrgUser>
     * @throws BusinessException
     */
    public List<OrgUser> getOrgUserByUserId(int userId)
        throws BusinessException;
    
    /**
     * 删除当前用户的组织用户关系
     * 
     * @Title deleteOrgUserByUser
     * @author wanglc
     * @date 2013-11-25
     * @param user 用户对象
     * @throws BusinessException
     */
    public void deleteOrgUserByUser(User user)
        throws BusinessException;
    
    /**
     * 删除当前组织的组织用户关系
     * 
     * @Title deleteOrgUserByOrg
     * @author wanglc
     * @date 2013-11-25
     * @param org 组织对象
     * @throws BusinessException
     */
    public void deleteOrgUserByOrg(Organization org)
        throws BusinessException;
    
    /**
     * 获取当前组织的组织用户关系
     * 
     * @Title getOrgUserByOrg
     * @author wanglc
     * @date 2013-11-25
     * @param org 组织对象
     * @return List<OrgUser> 用户组织关系数据集合
     * @throws BusinessException
     */
    public List<OrgUser> getOrgUserByOrg(Organization org)
        throws BusinessException;
    
    /**
     * 根据组织和用户查询用户组织关系
     * 
     * @Title getOrgUserByOrgAndUser
     * @author yzg
     * @date 2014-2-10
     * @param org 组织对象
     * @param user 用户对象
     * @return List<OrgUser> 用户组织关系数据集合
     * @throws BusinessException
     */
    public List<OrgUser> getOrgUserByOrgAndUser(Organization org, User user)
        throws BusinessException;
    
    /**
     * 查询用户组织
     * 
     * @Title getOrgUserListByPage
     * @author wanglc
     * @date 2013-11-25
     * @param start 查询开始
     * @param limit 每页限制条数
     * @throws ServiceException
     * @return ListVo<OrgUser> 用户组织列表集合
     */
    public ListVo<OrgUser> getOrgUserListByPage(int start, int limit)
        throws BusinessException;
    
    /**
     * 新增/更新用户组织
     * 
     * @Title addUpdateUser
     * @author wanglc
     * @date 2013-11-25
     * @param ou 用户组织对象
     * @throws BusinessException
     */
    public void addUpdateUser(OrgUser ou)
        throws BusinessException;
    
    /**
     * 获取当前组织下的人数
     * 
     * @Title getUserCountsByOrgId
     * @author wanglc
     * @date 2014-2-11
     * @return int 当前组织下的人数
     * @throws BusinessException
     */
    public int getUserCountsByOrgIds(String ids)
        throws BusinessException;
    
    /**
     * 删除本地用户
     * 
     * @Title deleteLocalOrgUser
     * @author wanglc
     * @date 2014-2-21
     */
    public void deleteLocalOrgUser()
        throws BusinessException;
    
    /**
     * 根据用户主键获取当前用户所属组织
     * 
     * @Title getLocalOrganizationByUserId
     * @author wanglc
     * @date 2014-2-21
     * @param userId 用户ID
     * @return List<Organization> 组织集合
     */
    public List<Organization> getLocalOrganizationByUserId(int userId)
        throws BusinessException;
    
}
