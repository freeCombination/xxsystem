package com.xx.system.user.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.user.entity.User;
import com.xx.system.user.vo.UserVo;

/**
 * 用户服务 定义用户服务中的方法
 * 
 * @version V1.20,2013-11-25 上午11:59:09
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public interface IUserService {
    
    /**
     * 获取所有用户列表
     * 
     * @Title getAllUser
     * @author wanglc
     * @date 2013-11-25
     * @return List<User>
     */
    public List<User> getAllUser()
        throws BusinessException;
    
    /**
     * 添加或修改用户
     * 
     * @Title addUpdateUser
     * @author wanglc
     * @date 2013-11-25
     * @param user 用户对象
     */
    public void addUpdateUser(User user)
        throws BusinessException;
    
    /**
     * 根据用户名取得用户
     * 
     * @Title getUserByUsername
     * @author wanglc
     * @date 2013-11-25
     * @param username 用户名
     * @return User
     */
    public User getUserByUsername(String username)
        throws BusinessException;
    
    /**
     * 获取用户列表
     * 
     * @Title getUserList
     * @author wanglc
     * @date 2013-11-25
     * @param paramMap 请求参数Map
     * @return ListVo<UserVo>
     */
    public ListVo<UserVo> getUserList(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 根据id取得用户
     * 
     * @Title getUserById
     * @author wanglc
     * @date 2013-11-25
     * @param id 用户ID
     * @return User
     */
    public User getUserById(int id)
        throws BusinessException;
    
    /**
     * 为用户分配角色
     * 
     * @Title addUserRoles
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @param roleIds 角色ID，以逗号隔开的字符串
     * @param orgId 组织ID
     */
    /*public void addUserRoles(int userId, String roleIds, int orgId)
        throws BusinessException;*/
    
    /**
     * 为用户分配资源
     * 
     * @Title addUserResources
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @param resourceIds 资源ID，以逗号隔开的字符串
     * @param orgId
     */
    /*public void addUserResources(int userId, String resourceIds, int orgId)
        throws BusinessException;*/
    
    /**
     * 删除用户的角色
     * 
     * @Title deleteUserRoles
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @param roleId 角色ID
     * @param orgId 组织ID
     */
    public void deleteUserRoles(int userId, int roleId, int orgId)
        throws BusinessException;
    
    /**
     * 删除用户资源
     * 
     * @Title deleteUserResource
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @param resourceId 资源ID
     * @param orgId 组织ID
     */
    /*public boolean deleteUserResource(int userId, int resourceId, int orgId)
        throws BusinessException;*/
    
    /**
     * 取得用户拥有的角色列表，如果org不为空表示取得用户在该组织下拥有的角色列表
     * 
     * @Title getUserRoles
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @param orgId 组织ID
     * @return List<UserRole>
     */
    /*public List<UserRole> getUserRoles(int userId, int orgId)
        throws BusinessException;*/
    
    /**
     * 修改用户启用/锁定
     * 
     * @Title updateUserEnable
     * @author wanglc
     * @date 2013-11-25
     * @param user 用户对象
     */
    void updateUserEnable(User user)
        throws BusinessException;
    
    /**
     * 根据ID获取组织对象
     * 
     * @Title getOrganizationById
     * @author wanglc
     * @date 2013-11-25
     * @param id 组织ID
     * @return Organization
     */
    Organization getOrganizationById(int id)
        throws BusinessException;
    
    /**
     * 批量删除用户
     * 
     * @Title batchDeleteUser
     * @author wanglc
     * @date 2013-11-25
     * @param ids 用户ID，以逗号隔开的字符串
     */
    void batchDeleteUser(String ids)
        throws BusinessException;
    
    /**
     * 验证添加方法中输入的用户名是否唯一
     * 
     * @Title validateAddUserName
     * @author wanglc
     * @date 2013-11-25
     * @param userName 用户名
     * @return
     */
    int validateAddUserName(String userName)
        throws BusinessException;
    
    /**
     * 验证修改方法中输入的用户名是否唯一
     * 
     * @Title validateUpdateUerName
     * @author wanglc
     * @date 2013-11-25
     * @param userName 用户名
     * @param userId 用户ID
     * @return
     */
    int validateUpdateUerName(String userName, String userId)
        throws BusinessException;
    
    /**
     * 获取用户对应的特殊资源
     * 
     * @Title getUserResources
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @param orgId 组织ID
     * @return Integer[]
     */
    /*Integer[] getUserResources(int userId, int orgId)
        throws BusinessException;*/
    
    /**
     * 获取角色对应的资源
     * 
     * @Title getRoleResource
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @param orgId 组织ID
     * @return Integer[]
     */
    /*Integer[] getRoleResource(int userId, int orgId)
        throws BusinessException;*/
    
    /**
     * 批量导入用户
     * 
     * @Title importUser
     * @author wanglc
     * @date 2013-11-25
     * @param fileUrl 文件路径
     * @return String
     */
    public String importUser(String fileUrl)
        throws BusinessException;
    
    /**
     * 批量导入用户与角色关系
     * 
     * @Title importUserRole
     * @author wanglc
     * @date 2013-11-25
     * @param fileUrl 文件路径
     * @return String
     * @throws Exception
     */
    /*public String importUserRole(String fileUrl)
        throws BusinessException, Exception;*/
    
    /**
     * 导出系统用户信息到EXCEL
     * 
     * @Title exportAllUsers
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param userList 用户列表
     * @param orgUserList 组织用户列表
     * @return InputStream
     */
    public InputStream exportAllUsers(List<User> userList,
        List<OrgUser> orgUserList)
        throws BusinessException;
    
    /**
     * 取得当前在线的用户列表
     * 
     * @Title getOnlineUser
     * @author wanglc
     * @date 2013-11-25
     * @param userId 当时在线用户ID
     * @return List<User> 所有在线用户LIST
     * @throws Exception
     */
    public List<User> getOnlineUser(int userId)
        throws Exception;
    
    /**
     * 根据登录名称（以“,”分割的字符串）获取用户对象列表
     * 
     * @Title getUserListByLoginNames
     * @author wanglc
     * @date 2013-11-25
     * @param loginNames 登录名称
     * @return List<User>
     */
    public List<User> getUserListByLoginNames(String loginNames)
        throws BusinessException;
    
    /**
     * 根据组织ID和真实姓名获取用户
     * 
     * @Title getUsersByOrgId
     * @author wanglc
     * @param orgId 组织ID
     * @param realname 真实姓名
     * @param start 分页开始
     * @param limit 分页结束
     * @return ListVo<User>
     */
    public ListVo<User> getUsersByOrgId(String orgId, String realname,
        int start, int limit)
        throws BusinessException;
    
    /**
     * 密码重置
     * 
     * @Title resetUserPassword
     * @author wanglc
     * @date 2013-11-25
     * @param u 用户对象
     */
    public void resetUserPassword(User u)
        throws BusinessException;
    
    /**
     * 获取用户列表，为流程在线设计器
     * 
     * @Title getUserListForFlow
     * @author wanglc
     * @date 2013-11-25
     * @param paramMap 参数Map
     * @return ListVo<User>
     */
    public ListVo<User> getUserListForFlow(Map<String, Object> paramMap)
        throws BusinessException;
    
    /**
     * 用户登录
     * 
     * @Title login
     * @author wanglc
     * @throws Exception 
     * @throws CordysException 
     * @date 2013-12-13
     */
    public Map<String, String> userLogin(Map<String, String> paramMap, User user)
        throws BusinessException, Exception;
    
    /**
     * 获取指定用户的所有部门名称及ID，逗号分隔
     * 
     * @Title getUserOrgs
     * @author yzg
     * @date 2014-2-19
     * @param userId
     * @return
     */
    public String getUserOrgs(String userId)
        throws BusinessException;
    
    /**
     * 通过ID判断当前用户是否存在
     * 
     * @Title userIsExist
     * @author ndy
     * @date 2014年3月26日
     * @param userId 用户ID
     * @param code 功能编码
     * @return
     */
    int userIsExist(String userId, String code)
        throws BusinessException;
    
    /**
     * 通过用户得到该用户拥有的资源以及该用户的部门权限
     * 
     * @Title getResAndOrg
     * @author liukang-wb
     * @date 2014年9月16日
     * @param userId
     * @return
     */
    public Map<String, Object> getResAndOrg(User user)
        throws BusinessException;
    
    
    /**
     * 将用户entity转换成vo
     * 
     * @Title convertVo
     * @author dong.he
     * @Description:
     * @date 2014年9月30日
     * @param user
     * @return
     * @throws BusinessException
     */
    public UserVo convertVo(User user)
        throws BusinessException;
    /**
     * 
     * @Title updateTempleUser
     * @author wujialing
     * @Description: 
     * @date 2015年4月29日
     * @param path 文件路径
     * @param textlist 修改内容 {"列表1","列表2"}
     * @throws BusinessException
     */
     public InputStream updateTempleUser(String path,String[] textlist) throws BusinessException;
     
     /**
      * 获取用户数据
      * 
      * @Title getUserByIdForUpdate
      * @author wujialing
      * @Description: 
      * @date 2015年7月9日
      * @param userId
      * @return
      * @throws BusinessException
      */
     public Map<String, Object> getUserByIdForUpdate(Integer userId)
         throws BusinessException;
}
