package com.xx.system.role.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.PagerVo;
import com.xx.system.org.entity.Organization;
import com.xx.system.role.entity.Role;
import com.xx.system.role.entity.RoleMemberScope;
import com.xx.system.role.entity.RoleResource;
import com.xx.system.role.entity.ScopeMember;
import com.xx.system.role.vo.RoleVo;
import com.xx.system.user.vo.GroupVo;
import com.xx.system.user.vo.UserVo;

/**
 * 定义角色接口
 * 
 * @version V1.20,2013-11-25 下午2:32:26
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public interface IRoleService {
    /**
     * 添加或修改角色
     * 
     * @Title addUpdateRole
     * @author lizhengc
     * @date 2014年9月3日
     * @param role 角色对象
     * @return String
     * @throws BusinessException
     */
    public String addUpdateRole(Role role)
        throws BusinessException;
    
    /**
     * 根据id查询角色
     * 
     * @Title getRole
     * @author lizhengc
     * @date 2014年9月3日
     * @param id 角色id
     * @return Role 角色对象
     * @throws BusinessException
     */
    public RoleVo getRole(int id)
        throws BusinessException;
    
    /**
     * 删除角色
     * 
     * @Title deleteRole
     * @author lizhengc
     * @date 2014年9月3日
     * @param roleId 角色id
     * @return String
     * @throws BusinessException
     */
    public String deleteRole(String roleId)
        throws BusinessException;
    
    /**
     * 获取角色列表分页
     * 
     * @Title getRoleList
     * @author lizhengc
     * @date 2014年9月3日
     * @param role 角色对象
     * @param start 分页条件
     * @param limit 分页条件
     * @return PagerVo<Role> 角色PagerVo
     * @throws BusinessException
     */
    public PagerVo<Role> getRoleList(Role role, int start, int limit)
        throws BusinessException;
    
    /**
     * 根据角色的名称获取数量
     * 
     * @Title getRoleNumByName
     * @author lizhengc
     * @date 2014年9月3日
     * @param roleName 角色名称
     * @param roleType 角色类型
     * @return int 角色数量
     * @throws BusinessException
     */
    public int getRoleNumByName(String roleName, String roleType)
        throws BusinessException;
    
    /**
     * 验证角色编码
     * 
     * @Title validateAddRoleCode
     * @author lizhengc
     * @date 2014年9月3日
     * @param roleCode 角色编码
     * @param roleType 角色类型
     * @return int 角色数量
     * @throws BusinessException
     */
    int validateAddRoleCode(String roleCode, String roleType)
        throws BusinessException;
    
    /**
     * 验证角色名
     * 
     * @Title validateUpdateRoleName
     * @author lizhengc
     * @date 2014年9月3日
     * @param roleName 角色编码
     * @param oldRoleId 角色id
     * @param roleType 角色类型
     * @return int 角色数量
     * @throws BusinessException
     */
    int validateUpdateRoleName(String roleName, String oldRoleId,
        String roleType)
        throws BusinessException;
    
    /**
     * 修改时验证角色编码
     * 
     * @Title validateUpdateRoleCode
     * @author lizhengc
     * @date 2014年9月3日
     * @param roleCode 角色编码
     * @param oldRoleId 角色id
     * @param roleType 角色类型
     * @return int 角色数量
     * @throws BusinessException
     */
    int validateUpdateRoleCode(String roleCode, String oldRoleId,
        String roleType)
        throws BusinessException;
    
    /**
     * 修改角色
     * 
     * @Title updateRole
     * @author lizhengc
     * @date 2014年9月3日
     * @param role 角色对象
     * @return null
     * @throws BusinessException
     */
    void updateRole(Role role)
        throws BusinessException;
    
    /**
     * 添加角色资源
     * 
     * @Title addRoleResources
     * @author lizhengc
     * @date 2014年9月3日
     * @param resourceIds 资源id
     * @param roleId 角色id
     * @return null
     * @throws BusinessException
     */
    public void addRoleResources(String resourceIds, String roleId)
        throws BusinessException;
    
    /**
     * 删除角色资源
     * 
     * @Title deleteRoleResources
     * @author lizhengc
     * @date 2014年9月3日
     * @param resourceIds 资源id
     * @param roleId 角色id
     * @return null
     * @throws BusinessException
     */
    public void deleteRoleResources(String resourceIds, String roleId)
        throws BusinessException;
    
    /**
     * 删除角色资源
     * 
     * @Title getRoleVoList
     * @author lizhengc
     * @date 2014年9月3日
     * @param paramMap 条件
     * @return ListVo<RoleVo> 角色vo集合
     * @throws BusinessException
     */
    ListVo<RoleVo> getRoleVoList(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 获取角色资源列表
     * 
     * @Title getRoleResourceList
     * @author lizhengc
     * @date 2014年9月3日
     * @param roleId 角色id
     * @return List<RoleResource> 资源集合
     * @throws BusinessException
     */
    List<RoleResource> getRoleResourceList(int roleId)
        throws BusinessException;
    
    /**
     * 导入角色资源
     * 
     * @Title importRoleResource
     * @author lizhengc
     * @date 2014年9月3日
     * @param attachUrl 导入url路径
     * @return String
     * @throws BusinessException
     */
    public String importRoleResource(String attachUrl)
        throws BusinessException;
    
    /**
     * 根据用户获取角色列表
     * 
     * @Title getRoleListByUser
     * @author lizhengc
     * @date 2014年9月3日
     * @param user 用户对象
     * @return List<Role> 角色集合
     * @throws BusinessException
     */
    /*public List<Role> getRoleListByUser(User user)
        throws BusinessException;*/
    
    /**
     * 根据角色名称获取角色列表
     * 
     * @Title getRoleListByRoleName
     * @author wanglc
     * @date 2013-11-25
     * @param roleName 角色名称
     * @param pageIndex 分页开始位置
     * @param pageSize 每页显示条数
     * @return ListVo<Role> 角色集合
     * @throws BusinessException
     */
    public ListVo<Role> getRoleListByRoleName(String roleName, int pageIndex,
        int pageSize)
        throws BusinessException;
    
    /**
     * 根据角色名称取本地角色
     * 
     * @Title getLocalRoleByName
     * @author wanglc
     * @date 2013-12-17
     * @param roleName 角色名称
     * @return role 角色对象
     * @throws BusinessException
     */
    public Role getLocalRoleByName(String role_name)
        throws BusinessException;
    
    /**
     * 根据角色id和角色编码判断角色是否存在
     * 
     * @Title roleIsExist
     * @Author ndy
     * @param roleId 角色id
     * @param code 角色编码
     * @return int 角色数量
     * @throws BusinessException
     */
    int roleIsExist(String roleId, String code)
        throws BusinessException;
    
    /**
     * 通过成员的ID获取对应的角色ID
     * 
     * @Title getRoleMemberScopeByMemberId
     * @author liukang-wb
     * @date 2014年9月15日
     * @param memberId
     * @return
     */
    public List<RoleMemberScope> getRoleMemberScopeByMemberId(String memberIds,
        String type)
        throws BusinessException;
    
    /**
     * 通过角色用户群组表的id得到群组所含群组成员id
     * 
     * @Title getScpMemberByRMSid
     * @author liukang-wb
     * @date 2014年9月17日
     * @param rmsids
     * @param type
     * @return List<ScopeMember> 群组成员List
     */
    public List<ScopeMember> getScpMemberByRMSid(String rmsids, String type)
        throws BusinessException;
    
    /**
     * 添加角色组织范围
     * 
     * @Title addRoleOrg
     * @author lizhengc
     * @date 2014年09月16日
     * @param param 条件参数
     * @return String
     * @throws BusinessException
     */
    public String addRoleOrg(Map<String, String> param)
        throws BusinessException;
    
    /**
     * 添加角色组织范围
     * 
     * @Title addRoleOrg
     * @author lizhengc
     * @date 2014年09月16日
     * @param param 条件参数
     * @return String
     * @throws BusinessException
     */
    public String addRoleUserScope(Map<String, String> param)
    		throws BusinessException;
    /**
     * 添加角色成员范围
     * 
     * @Title addRoleMember
     * @author lizhengc
     * @date 2014年09月16日
     * @param param 条件参数
     * @return String
     * @throws BusinessException
     */
    public String addRoleMember(Map<String, String> param)
        throws BusinessException;
    
    /**
     * 获得所有的群组权限
     * 
     * @Title getRoleMember
     * @author lizhengc
     * @date 2014年09月16日
     * @param param 条件
     * @return String
     * @throws BusinessException
     */
    public ListVo<GroupVo> getRoleMember(Map<String, String> param)
        throws BusinessException;
    
    /**
     * 删除权限查询群组是否存在
     * 
     * @Title checkRGIsExist
     * @author lizhegnc
     * @Description:
     * @date 2014年9月17日
     * @param id 群组id
     * @param roleId 角色id
     * @return roleMemberScope 对象
     * @throws BusinessException
     */
    public RoleMemberScope checkRGIsExist(String id, String roleId)
        throws BusinessException;
    
    /**
     * 删除权限查询用户是否存在
     * 
     * @Title checkRGIsExist
     * @author lizhegnc
     * @Description:
     * @date 2014年9月17日
     * @param id 用户id
     * @param roleId 角色id
     * @return roleMemberScope 对象
     * @throws BusinessException
     */
    public RoleMemberScope checkRUIsExist(String id, String roleId)
        throws BusinessException;
    
    /**
     * 删除权限群组
     * 
     * @Title delRoleGroup
     * @author lizhengc
     * @Description:
     * @date 2014年9月17日
     * @param ids 群组id
     * @param roleId 角色id
     * @throws BusinessException
     */
    public void delRoleGroup(Map<String, String> param)
        throws BusinessException;
    
    /**
     * 删除权限用户
     * 
     * @Title delRoleUser
     * @author lizhengc
     * @Description:
     * @date 2014年9月17日
     * @param param 参数
     * @throws BusinessException
     */
    public void delRoleUser(Map<String, String> param)
        throws BusinessException;
    
    /**
     * 查询本角色所有用户的权限
     * 
     * @Title getRoleMemberByUser
     * @author lizhengc
     * @Description:
     * @date 2014年9月17日
     * @param param 查询条件
     * @return user集合
     * @throws BusinessException
     */
    public ListVo<UserVo> getRoleMemberByUser(Map<String, String> param)
        throws BusinessException;
    
    /**
     * 添加角色用户范围权限
     * 
     * @Title addRoleUser
     * @author lizhengc
     * @date 2014年09月16日
     * @param param 条件参数
     * @return String
     * @throws BusinessException
     */
    public String addRoleUser(Map<String, String> param)
        throws BusinessException;
    
    /**
     * 根据角色用户查询组织
     * 
     * @Title getOrgSM
     * @author lizhengc
     * @date 2014年09月18日
     * @param roleMemberId 角色组织id
     * @return List<ScopeMember> 角色组织集合
     * @throws BusinessException
     */
    public List<ScopeMember> getOrgSM(int roleMemberId)
        throws BusinessException;
    
    /**
     * 权限范围
     * 
     * @Title getOrgSM
     * @author lizhengc
     * @date 2014年09月18日
     * @param roleMemberId 角色组织id
     * @return List<ScopeMember> 角色组织集合
     * @throws BusinessException
     */
    public ListVo<UserVo> getScopeByMenberId(Map<String, String> param) throws BusinessException;
    /**
     * @Title getFirstLevelOrg
     * @author wanglc
     * @Description: 查询当前组织的一级菜单
     * @date 2013-09-18
     * @return List<Organization>
     * @throws BusinessException
     */
    public List<Organization> getFirstLevelOrg()
        throws BusinessException;
    
    /**
     * @Title getAllSonOrgByParentId
     * @author lizhengc
     * @Description: 根据父节点取所有子节点
     * @date 2013-09-18
     * @param parentId 父节点ID
     * @return List<Organization>
     * @throws BusinessException
     */
    public List<Organization> getAllSonOrgByParentId(String parentId)
        throws BusinessException;
    
    /**
     * 删除角色组织权限
     * 
     * @Title delRoleUser
     * @author lizhengc
     * @Description:
     * @date 2014年9月18日
     * @param ids 用户id
     * @param roleId 角色id
     * @throws BusinessException
     */
    public void delRoleOrg(String roleMemeberIds, String orgIds)
        throws BusinessException;
    
    /**
     * 删除角色用户数据范围
     * 
     * @Title delRoleUser
     * @author lizhengc
     * @Description:
     * @date 2014年9月18日
     * @param ids 用户id
     * @param roleId 角色id
     * @throws BusinessException
     */
    public void delScopeUser(String roleMemeberId, String userIds)
    		throws BusinessException;
    
    /**
     * 根据角色用户查询数据范围内的用户
     * 
     * @Title getUserSm
     * @author dong.he
     * @date 2015年04月23日
     * @param roleMemberId 角色成员id
     * @return List<ScopeMember> 数据范围内的用户集合
     * @throws BusinessException
     */
    public List<ScopeMember> getUserSm(int roleMemberId)
        throws BusinessException;
    
    /**
     * 通过角色的ID获取对应的成员ID
     * 
     * @Title getRoleMemberScopeByRoleId
     * @author dong.he
     * @date 2014年9月15日
     * @param roleId 角色ID
     * @param flag 获取user还是group
     * @return
     */
    public List<RoleMemberScope> getRoleMemberScopeByRoleId(int roleId, String flag)
        throws BusinessException;
}
