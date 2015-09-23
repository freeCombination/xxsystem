package com.xx.system.user.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.interceptor.Log;
import com.xx.system.common.util.MD5Util;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.util.UUIDUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.PagerVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.service.IDictService;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.service.IOrgService;
import com.xx.system.org.service.IOrgUserService;
import com.xx.system.resource.entity.Resource;
import com.xx.system.resource.service.IResourceService;
import com.xx.system.resource.vo.ResMenuVo;
import com.xx.system.resource.vo.ResourceVo;
import com.xx.system.role.entity.RoleMemberScope;
import com.xx.system.role.entity.RoleResource;
import com.xx.system.role.entity.ScopeMember;
import com.xx.system.role.service.IRoleResourceService;
import com.xx.system.role.service.IRoleService;
import com.xx.system.user.entity.GroupMember;
import com.xx.system.user.entity.User;
import com.xx.system.user.service.IGroupService;
import com.xx.system.user.service.IUserService;
import com.xx.system.user.util.HSSFUtils;
import com.xx.system.user.vo.UserVo;

/**
 * 用户接口实现类
 * 
 * @version V1.20,2013-12-6 下午3:30:45
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Service("userService")
@SuppressWarnings({"unchecked", "rawtypes", "deprecation"})
public class UserServiceImpl implements IUserService {
    /**
     * 持久层接口
     */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    @Autowired
    @Qualifier("organizationService")
    private IOrgService orgService;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    /**
     * @Fields orgUserService 组织用户服务
     */
    @Autowired
    @Qualifier("orgUserService")
    private IOrgUserService orgUserService;
    
    public void setOrgUserService(IOrgUserService orgUserService) {
        this.orgUserService = orgUserService;
    }
    
    /**
     * @Fields userRoleService 用户角色服务
     */
    /*
     * @Autowired
     * @Qualifier("userRoleService") private IUserRoleService userRoleService;
     */
    
    /**
     * @Fields organizationService 组织服务
     */
    @Autowired
    @Qualifier("organizationService")
    private IOrgService organizationService;
    
    /**
     * @param orgService 要设置的 orgService
     */
    public void setOrgService(IOrgService organizationService) {
        this.organizationService = organizationService;
    }
    
    /**
     * @Fields roleResourceService : 角色资源服务
     */
    @Autowired(required = true)
    @Qualifier("roleResourceService")
    public IRoleResourceService roleResourceService;
    
    /**
     * @Fields roleService : 角色服务
     */
    @Autowired
    @Qualifier("roleService")
    private IRoleService roleService;
    
    /**
     * @Fields groupService :群组服务
     */
    @Autowired(required = true)
    @Qualifier("groupService")
    private IGroupService groupService;
    
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }
    
    /**
     * @Fields resourceService :资源服务
     */
    @Autowired
    @Qualifier("resourceService")
    private IResourceService resourceService;
    
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    /**
     * 字典业务接口
     */
    @Autowired
    @Qualifier("dictService")
    private IDictService dictService;
    
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
    
    /**
     * 根据ID获取组织对象
     * 
     * @Title getOrganizationById
     * @author wanglc
     * @date 2013-11-25
     * @param id 组织ID
     * @return Organization
     */
    @Override
    public Organization getOrganizationById(int id) {
        String hql = "FROM Organization WHERE orgId=?";
        List<Organization> list = baseDao.query(hql, new Integer[] {id});
        if (null != list && list.size() != 0) {
            return list.get(0);
        }
        return null;
    }
    
    /**
     * 添加或修改用户
     * 
     * @Title addUpdateUser
     * @author wanglc
     * @date 2013-11-25
     * @param user 用户对象
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    @Log(operationType = "添加", operationName = "用户数据")
    public void addUpdateUser(User user) {
        if (user != null) {
            baseDao.saveOrUpdate(user);
        }
        
    }
    
    /**
     * 批量删除用户
     * 
     * @Title batchDeleteUser
     * @author wanglc
     * @date 2013-11-25
     * @param ids 用户ID，以逗号隔开的字符串
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    @Log(operationType = "批量删除", operationName = "用户数据")
    public void batchDeleteUser(String userIds) {
        baseDao.delete(userIds, "User", "status", "userId", "1");
    }
    
    /**
     * 根据用户名取得用户
     * 
     * @Title getUserByUsername
     * @author wanglc
     * @date 2013-11-25
     * @param username 用户名
     * @return User
     */
    @Override
    public User getUserByUsername(String username) {
    	User user = null;
        try {
            String hql =
                "FROM User U WHERE U.status = " + Constant.STATUS_NOT_DELETE
                    + " and U.username='" + username +"'";
            List<User> list = baseDao.queryEntitys(hql);
            
            if (list != null && list.size() > 0) {
            	user = list.get(0);
            	Set<OrgUser> uset = user.getOrgUsers();
            	if(uset !=null && uset.size()>0){
            		uset.iterator().next().getOrganization();
            	}
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        return user;
    }
    
    /**
     * 获取用户分页数据
     * 
     * @Title getUserPager
     * @author wanglc
     * @date 2013-12-6
     * @param start
     * @param limit
     * @param user
     * @return PagerVo<User>
     */
    public PagerVo<User> getUserPager(int start, int limit, User user) {
        StringBuffer hql = new StringBuffer(" 1=1 ");
        Map<String, Object> values = new HashMap<String, Object>();
        // 遍历user中的相关属性
        if (user != null) {
            if (StringUtils.isNotBlank(user.getRealname())) {
                hql.append(" and obj.realname like ? ");
                values.put("realname", "%" + user.getRealname() + "%");
            }
            if (StringUtils.isNotBlank(user.getUsername())) {
                hql.append(" and obj.username like ? ");
                values.put("username", "%" + user.getUsername() + "%");
            }
            if (StringUtils.isNotBlank(user.getGender())) {
                hql.append(" and obj.gender = ? ");
                values.put("gender", user.getGender());
            }
            if (user.getEnable() >= 0) {
                hql.append(" and obj.enable = ? ");
                values.put("enable", user.getEnable());
            }
        }
        hql.append(" and obj.status=" + Constant.STATUS_NOT_DELETE
            + " order by obj.status,obj.enable,obj.disOrder,obj.userId ");
        List<User> userList =
            this.baseDao.queryEntitysByPage(start,
                limit,
                hql.toString(),
                values);
        int count = baseDao.getTotalCount(hql.toString(), values);
        return new PagerVo<User>(start, limit, count, userList);
    }
    
    /**
     * 获取用户列表
     * 
     * @Title getUserList
     * @author wanglc
     * @date 2013-11-25
     * @param paramMap 请求参数Map
     * @return ListVo<UserVo>
     */
    @Override
    public ListVo<UserVo> getUserList(Map<String, String> paramMap)
        throws BusinessException {
        int start = NumberUtils.toInt(paramMap.get("start"));
        int limit = NumberUtils.toInt(paramMap.get("limit"));
        String orgId = paramMap.get("orgId");
        String userName = paramMap.get("userName");
        String flag = paramMap.get("flag");
        
        String roleIdOrRoleMemberId = paramMap.get("roleIdOrRoleMemberId");
        String roleId = paramMap.get("roleId");
        
        String orgIds = "";
        
        String orgUserHql =
            "select distinct u from OrgUser ou,User u where u.userId=ou.user.userId "
                + " and ou.isDelete = "
                + Constant.STATUS_NOT_DELETE + " and ou.user.status = "
                + Constant.STATUS_NOT_DELETE + " and ou.organization.status ="
                + Constant.STATUS_NOT_DELETE;
        
        if ("QxUser".equals(flag) || StringUtils.isNotBlank(flag)) {
            orgIds = orgService.getOrgIdsByPermissionScope(orgId, null);
            if (orgIds != null && !"".equals(orgIds)) {
                orgUserHql += " and ou.organization.orgId in (" + orgIds + ")";
            }
        }
        else {
            if (orgId != null && !"".equals(orgId)) {
                orgUserHql += " and ou.organization.orgId in (" + orgId + ")";
            }
        }
        
        // 当roleIdOrRoleMemberId = “scope”表示数据范围中user添加时限制用户已添加id，此时roleId表示角色成员ID，
        // 当roleIdOrRoleMemberId != “scope”表示添加角色成员时限制已添加user id，此时roleId表示角色ID
        if (StringUtils.isNotBlank(roleId)) {
            if (StringUtils.isNotBlank(roleIdOrRoleMemberId) && "scope".equals(roleIdOrRoleMemberId)) {
                // 根据角色成员ID查询该成员已有控制权限下的userIds
                List<ScopeMember> userSm = roleService.getUserSm(NumberUtils.toInt(roleId));
                if (!CollectionUtils.isEmpty(userSm)) {
                    String userIds = "";
                    for (ScopeMember sm : userSm) {
                        userIds += "," + sm.getUser().getUserId();
                    }
                    
                    orgUserHql += " and u.userId not in (" + userIds.substring(1) + ")";
                }
            }
            else {
                // 根据角色ID查询该角色已有成员的userIds
                List<RoleMemberScope> members = roleService.getRoleMemberScopeByRoleId(NumberUtils.toInt(roleId), "userIds");
                if (!CollectionUtils.isEmpty(members)) {
                    String userIds = "";
                    for (RoleMemberScope m : members) {
                        userIds += "," + m.getUser().getUserId();
                    }
                    
                    orgUserHql += " and u.userId not in (" + userIds.substring(1) + ")";
                }
            }
        }
        
        if (userName != null && !"".equals(userName)) {
            orgUserHql += " and ou.user.realname like '%" + userName + "%'";
        }
        
        //int totalSize = baseDao.queryTotalCount(orgUserHql, new HashMap<String, Object>());
        
        int beginPos = orgUserHql.toLowerCase().indexOf("from");
        String hql4Count = " select count(distinct u.userId) " + orgUserHql.substring(beginPos);
        int totalSize = baseDao.getTotalCount(hql4Count, new HashMap<String, Object>());
        
        orgUserHql +=" order by u.disOrder, u.userId";
        List<User> userList =  (List<User>)baseDao.queryEntitysByPage(start, limit, orgUserHql,new HashMap<String, Object>());
        
        ListVo<UserVo> userVoListVo = new ListVo<UserVo>();
        List<UserVo> userVoList = new ArrayList<UserVo>();
        for (User u : userList) {
            UserVo uv = convertVo(u);
            userVoList.add(uv);
        }
        userVoListVo.setList(userVoList);
        userVoListVo.setTotalSize(totalSize);
        
        return userVoListVo;
    }
    
    /**
     * 根据id取得用户
     * 
     * @Title getUserById
     * @author wanglc
     * @date 2013-11-25
     * @param id 用户ID
     * @return User
     */
    @Override
    public User getUserById(int id) {
        String hql = "from User u where u.userId=" + id;
        List<User> users = (List<User>)this.baseDao.queryEntitys(hql);
        User user = null;
        if (users != null && users.size() > 0) {
            user = users.get(0);
        }
        return user;
    }
    
    @Override
    public Map<String, Object> getUserByIdForUpdate(Integer userId) {
        String hql = "from User u where u.userId=" + userId;
        List<User> users = (List<User>)this.baseDao.queryEntitys(hql);
        User user = null;
        if (users != null && users.size() > 0) {
            user = users.get(0);
        }
        Map<String, Object> data = convertMap(user);
        return data;
    }
    
    
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
    /*
     * @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
     * @Override
     * @Log(operationType = "添加", operationName = "用户角色关系数据") public void addUserRoles(int userId,
     * String roleIds, int orgId) { User u = (User)baseDao.queryEntityById(User.class, userId);
     * Organization o = (Organization)baseDao.queryEntityById(Organization.class, orgId);
     * List<UserRole> urList = new ArrayList<UserRole>(); if (u != null) { String[] roleArr =
     * roleIds.split(","); if (roleArr.length > 0 && StringUtils.isNotEmpty(roleArr[0])) { for
     * (String roleId : roleArr) { UserRole ur = new UserRole(); ur.setUser(u);
     * ur.setRole((Role)baseDao.queryEntityById(Role.class, NumberUtils.toInt(roleId)));
     * ur.setOrganization(o); ur.setStatus(Constant.STATUS_NOT_DELETE); urList.add(ur); }
     * baseDao.saveOrUpdate(urList); } } }
     */
    
    /**
     * 为用户分配资源
     * 
     * @Title addUserResources
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param userId 用户ID
     * @param resourceIds 资源ID，以逗号隔开的字符串
     * @param orgId
     */
    /*
     * @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
     * @Override
     * @Log(operationType = "添加", operationName = "用户特殊资源数据") public void addUserResources(int
     * userId, String resourceIds, int orgId) { User u = (User)baseDao.queryEntityById(User.class,
     * userId); Organization org = (Organization)baseDao.queryEntityById(Organization.class, orgId);
     * if (u != null && org != null) { String[] ids = resourceIds.split(","); if (ids.length > 0 &&
     * StringUtils.isNotEmpty(ids[0])) { for (String s : ids) { Resource res =
     * (Resource)baseDao.queryEntityById(Resource.class, NumberUtils.toInt(s)); if (res != null) {
     * UserOrgResource uor = new UserOrgResource(); uor.setOrganization(org); uor.setResource(res);
     * uor.setUser(u); baseDao.saveOrUpdate(uor); } } } } }
     */
    
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    @Log(operationType = "删除", operationName = "用户角色关系数据")
    public void deleteUserRoles(int userId, int roleId, int orgId) {
        if (userId != 0) {
            String sql =
                "DELETE FROM SYS_USER_ROLE WHERE USER_ID=" + userId
                    + " AND ORG_ID=" + orgId + " AND ROLE_ID=" + roleId;
            baseDao.executeNativeSQL(sql);
        }
    }
    
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
    /*
     * @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
     * @Override public boolean deleteUserResource(int userId, int resourceId, int orgId) throws
     * BusinessException { boolean msg = true; User u = this.getUserById(userId); List<UserRole>
     * urList = this.userRoleService.getUserRoleListByUser(u); if (urList.size() > 0) { String
     * roleIds = ""; for (UserRole ur : urList) { roleIds += (ur.getRole().getRoleId() + ","); }
     * List<RoleResource> ownedReslistByRole =
     * this.roleResourceService.getOwnResByUserRole(roleIds.substring(0, roleIds.length() - 1)); if
     * (ownedReslistByRole != null) { for (RoleResource rr : ownedReslistByRole) { if (resourceId ==
     * rr.getResource().getResourceId()) { msg = false; break; } else { msg =
     * delUserOrgResource(userId, resourceId, orgId); } } } else { msg = delUserOrgResource(userId,
     * resourceId, orgId); } } else { msg = delUserOrgResource(userId, resourceId, orgId); } return
     * msg; }
     */
    
    /**
     * @Title delUserOrgResource
     * @author ndy
     * @date 2014年4月17日
     * @param userId
     * @param resourceId
     * @param orgId
     * @return
     */
    public boolean delUserOrgResource(int userId, int resourceId, int orgId)
        throws BusinessException {
        String hql =
            "from UserOrgResource uor where uor.organization.orgId = " + orgId
                + " and " + "uor.user.userId = " + userId
                + " and uor.resource.resourceId = " + resourceId + "";
        List<Resource> resList = baseDao.queryEntitys(hql);
        if (resList != null && resList.size() == 1) {
            baseDao.deleteEntity(resList.get(0));
        }
        return true;
    }
    
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
    /*
     * @Override public List<UserRole> getUserRoles(int userId, int orgId) throws BusinessException
     * { String hql = "from UserRole ur where ur.status =" + Constant.STATUS_NOT_DELETE +
     * " and ur.user.userId=" + userId; hql += " and ur.organization.orgId=" + orgId; return
     * (List<UserRole>)baseDao.queryEntitys(hql); }
     */
    
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
    /*
     * @Override public Integer[] getUserResources(int userId, int orgId) throws BusinessException {
     * // 获取用户特殊的资源 String hql = "from UserOrgResource ur where ur.organization.status = " +
     * Constant.STATUS_NOT_DELETE + " and ur.user.status = " + Constant.STATUS_NOT_DELETE +
     * " and ur.user.userId=" + userId; if (orgId != 0) { hql += " and ur.organization.orgId=" +
     * orgId; } List<UserOrgResource> userOrgResourceList =
     * (List<UserOrgResource>)baseDao.queryEntitys(hql); Integer[] userOrgResources = null; if
     * (userOrgResourceList != null) { userOrgResources = new Integer[userOrgResourceList.size()];
     * for (int i = 0; i < userOrgResourceList.size(); i++) { userOrgResources[i] =
     * userOrgResourceList.get(i).getResource().getResourceId(); } } return userOrgResources; }
     */
    
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
    /*
     * @Override public Integer[] getRoleResource(int userId, int orgId) throws BusinessException {
     * // 通过用户ID，组织ID，获取到角色ID String userRoleHql = "select u from UserRole u where u.user = " +
     * userId + " and u.organization  = " + orgId; List<UserRole> userRoleList =
     * baseDao.queryEntitys(userRoleHql); // 定义角色对应资源的ID List<Integer> roleResourcesList = new
     * ArrayList<Integer>(); // 根据角色ID获取到相应的资源ID if (userRoleList != null) { for (int i = 0; i <
     * userRoleList.size(); i++) { int roleId = userRoleList.get(i).getRole().getRoleId(); String
     * roleResourceHql = "select r from RoleResource r where r.role= " + roleId; List<RoleResource>
     * roleResourceList = baseDao.queryEntitys(roleResourceHql); for (int j = 0; j <
     * roleResourceList.size(); j++) { roleResourcesList.add(roleResourceList.get(j) .getResource()
     * .getResourceId()); } } } // 将角色对应的资源ID封装到该数组中 Integer[] roleResources = new
     * Integer[roleResourcesList.size()]; for (int i = 0; i < roleResourcesList.size(); i++) {
     * roleResources[i] = (Integer)roleResourcesList.get(i); } return roleResources; }
     */
    
    /**
     * 修改用户启用/锁定
     * 
     * @Title updateUserEnable
     * @author wanglc
     * @date 2013-11-25
     * @param user 用户对象
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateUserEnable(User user)
        throws BusinessException {
        this.baseDao.update(user);
    }
    
    /**
     * 验证添加方法中输入的用户名是否唯一
     * 
     * @Title validateAddUserName
     * @author wanglc
     * @date 2013-11-25
     * @param userName 用户名
     * @return
     */
    @Override
    public int validateAddUserName(String userName)
        throws BusinessException {
        String sql =
            "SELECT COUNT(*) FROM User a WHERE a.status = 0 AND a.username='"
                + userName.replace("'", "''") + "'";
        return baseDao.getTotalCount(sql, null);
    }
    
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
    @Override
    public int validateUpdateUerName(String userName, String userId)
        throws BusinessException {
        /**
         * User oldUser = (User)this.baseDao.get(NumberUtils.toInt(userId)); String sql =
         * "SELECT COUNT(*) FROM SYS_USER WHERE STATUS = 0 AND USERNAME='"+userName.replace("'",
         * "''")+"' and USERNAME != '"+oldUser.getUsername()+"'"; return
         * ((BigDecimal)baseDao.executeNativeQuery(sql).get(0)).intValue();
         */
        return 0;
    }
    
    /**
     * 批量导入用户
     * 
     * @Title importUser
     * @author wanglc
     * @date 2013-11-25
     * @param fileUrl 文件路径
     * @return String
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public String importUser(String attachUrl)
        throws BusinessException {
        String message = "importSuccess";
        try {
            String[][] content = HSSFUtils.extractTextFromExcel(attachUrl);
            if (null == content) {
                message = "不是有效的Excel文件,请按照模版来定义！";
            }
            else {
                File attachFile = new File(attachUrl);
                FileUtils.forceDelete(attachFile);
                int col = content.length;
                StringBuffer sql[] = new StringBuffer[col - 1];
                List<String> sqlArray = new ArrayList<String>();
                List<String> orgUserSqlArray = new ArrayList<String>();
                StringBuffer sqlParam = new StringBuffer("");
                int sign = 1;
                Map<String, String> checkUserName = new HashMap<String, String>();
                for (int i = 1; i < col; i++) {
                    int j = i - 1;
                    sql[j] = new StringBuffer("");
                    sql[j].append("insert into t_user(");
                    // 登录名
                    if (null != content[i][0]
                        && !StringUtils.isBlank(content[i][0].trim())
                        && !StringUtils.isEmpty(content[i][0].trim())
                        && !StringUtils.equals("null", content[i][0].trim())) {
                        String username = content[i][0].trim();
                        if (!username.matches("^\\w{4,20}$")) {
                            int o = i + 1;
                            message =
                                "第【" + o
                                    + "】行,【1】列发生错误,原因:登录名由4到20位数字、字母、下划线或中划线组成！";
                            break;
                        }
                        
                        // 检查Excel中是否包含用户名相同的数据
                        if(checkUserName.containsKey(username)) {
                            int o = i + 1;
                            message =
                                "第【" + o + "】行,【1】列发生错误,原因:指定的登录名重复！";
                            break;
                        } 
                        else {
                            checkUserName.put(username, username);
                        }
                        
                        sign = this.validateAddUserName(username);
                        if (sign != 0) {
                            int o = i + 1;
                            message =
                                "第【" + o + "】行,【1】列发生错误,原因:指定的登录名在数据库中已存在！";
                            break;
                        }
                        else {
                            sql[j].append("USERNAME");
                            sqlParam.append("'" + username + "'");
                        }
                    }
                    else {
                        int o = i + 1;
                        message = "第【" + o + "】行,【1】列发生错误,原因:登录名不能空！";
                        break;
                    }
                    // 密码
                    if (null != content[i][1]
                        && !StringUtils.isBlank(content[i][1].trim())
                        && !StringUtils.isEmpty(content[i][1].trim())
                        && !StringUtils.equals("null", content[i][1].trim())) {
                        if (content[i][1].trim().length() > 18) {
                            int o = i + 1;
                            message = "第【" + o + "】行,【2】列发生错误,原因:请输入6-18位字符！";
                            break;
                        }
                        if (content[i][1].trim().length() < 6) {
                            int o = i + 1;
                            message = "第【" + o + "】行,【2】列发生错误,原因:请输入6-18位字符！";
                            break;
                        }
                        sql[j].append(",PASSWORD");
                        sqlParam.append(",'"
                            + MD5Util.encode(content[i][1].trim()) + "'");
                    }
                    else {
                        int o = i + 1;
                        message = "第【" + o + "】行,【2】列发生错误,原因:密码不能空！";
                        break;
                    }
                    // 真实姓名
                    if (null != content[i][2]
                        && !StringUtils.isBlank(content[i][2].trim())
                        && !StringUtils.isEmpty(content[i][2].trim())
                        && !StringUtils.equals("null", content[i][2].trim())) {
                        if (!content[i][2].trim()
                            .matches("^[\\w.\\-\\u4e00-\\u9fa5]+$")) {
                            int o = i + 1;
                            message = "第【" + o + "】行,【3】列发生错误,原因:姓名格式不对！";
                            break;
                        }
                        if (content[i][2].trim().length() > 21) {
                            int o = i + 1;
                            message =
                                "第【" + o + "】行,【3】列发生错误,原因:数据超长，请输入20以内字符！";
                            break;
                        }
                        sql[j].append(",REALNAME");
                        sqlParam.append(",'" + content[i][2].trim() + "'");
                    }
                    else {
                        int o = i + 1;
                        message = "第【" + o + "】行,【3】列发生错误,原因:姓名不能空！";
                        break;
                    }
                    // 手机号1
                    if (null != content[i][3]
                        && !StringUtils.isBlank(content[i][3].trim())
                        && !StringUtils.isEmpty(content[i][3].trim())
                        && !StringUtils.equals("null", content[i][3].trim())) {
                        String phone = content[i][3].trim();
                        if (!phone.matches("^1[3|4|5|8][0-9]\\d{8}$")) {
                            int o = i + 1;
                            message = "第【" + o + "】行,【4】列发生错误,原因:指定的手机1非法！";
                            break;
                        }
                        sql[j].append(",MOBILE_PHONE1");
                        sqlParam.append(",'" + content[i][3].trim() + "'");
                    }
                    // 手机号2
                    if (null != content[i][4]
                        && !StringUtils.isBlank(content[i][4].trim())
                        && !StringUtils.isEmpty(content[i][4].trim())
                        && !StringUtils.equals("null", content[i][4].trim())) {
                        String phone = content[i][4].trim();
                        if (!phone.matches("^1[3|4|5|8][0-9]\\d{8}$")) {
                            int o = i + 1;
                            message = "第【" + o + "】行,【5】列发生错误,原因:指定的手机2非法！";
                            break;
                        }
                        sql[j].append(",MOBILE_PHONE2");
                        sqlParam.append(",'" + content[i][4].trim() + "'");
                    }
                    // 默认首页
                    /*
                     * if (null != content[i][5] && !StringUtils.isBlank(content[i][5].trim()) &&
                     * !StringUtils.isEmpty(content[i][5].trim()) && !StringUtils.equals("null",
                     * content[i][5].trim())) { String defaultPageString = content[i][5].trim();
                     * String defalutPage = ""; if (defaultPageString.equals("个人工作")) { defalutPage
                     * = "/task/getTaskToDoList.do?type=1&menucode=menberTaskListMenu"; } else if
                     * (defaultPageString.equals("组织工作")) { defalutPage =
                     * "/task/getTaskToDoList.do?type=2&menucode=orgTaskListMenu"; } else { int o =
                     * i + 1; message = "第【" + o + "】行,【6】列发生错误,原因:指定的个人首页不合法！"; break; }
                     * sql[j].append(",DEFAULT_HOME_PAGE"); sqlParam.append(",'" + defalutPage +
                     * "'"); }
                     */
                    // 性别
                    /*
                     * if (null != content[i][5] && !StringUtils.isBlank(content[i][5].trim()) &&
                     * !StringUtils.isEmpty(content[i][5].trim()) && !StringUtils.equals("null",
                     * content[i][5].trim())) { sql[j].append(",GENDER"); sqlParam.append(",'" +
                     * content[i][5].trim() + "'"); }
                     */
                    // 出生日期
                    /*
                     * if (null != content[i][7] && !StringUtils.isBlank(content[i][7].trim()) &&
                     * !StringUtils.isEmpty(content[i][7].trim()) && !StringUtils.equals("null",
                     * content[i][7].trim())) { String birthday = content[i][7].trim();
                     * sql[j].append(",BIRTHDAY"); if (birthday != null && !"".equals(birthday) &&
                     * !birthday.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) { int o = i + 1; message =
                     * "第【" + o + "】行,【8】列发生错误,原因:指定的出生日期不合法,格式：yyyy/mm/dd"; break; }
                     * sqlParam.append("," + birthday + ""); }
                     */
                    // 组织部门
                    int orgId = 0;
                    if (null != content[i][5]
                        && !StringUtils.isBlank(content[i][5].trim())
                        && !StringUtils.isEmpty(content[i][5].trim())
                        && !StringUtils.equals("null", content[i][5].trim())) {
                        String orgName = content[i][5].trim();
                        String hql =
                            "from Organization o where o.orgName = '" + orgName
                                + "'";
                        List<Organization> list =
                            this.baseDao.queryEntitys(hql);
                        if (null != list && list.size() > 0) {
                            orgId = list.get(0).getOrgId();
                        }
                        else {
                            int o = i + 1;
                            message = "第【" + o + "】行,【6】列发生错误,原因:指定的部门未找到！";
                            break;
                        }
                    }
                    else {
                        int o = i + 1;
                        message = "第【" + o + "】行,【7】列发生错误,原因:部门不能空！";
                        break;
                    }
                    // 职位
                    /*
                     * if (null != content[i][9] && !StringUtils.isBlank(content[i][9].trim()) &&
                     * !StringUtils.isEmpty(content[i][9].trim()) && !StringUtils.equals("null",
                     * content[i][9].trim())) { sql[j].append(",FK_POST"); String postString =
                     * content[i][9].trim(); String hql =
                     * "from Dictionary d where d.dictionaryName = '" + postString + "'";
                     * List<Dictionary> d = this.baseDao.queryEntitys(hql); int post = 0; if (d !=
                     * null && d.size() > 0) { post = d.get(0).getPkDictionaryId(); } else { int o =
                     * i + 1; message = "第【" + o + "】行,【10】列发生错误,原因:指定的职位未找到！"; break; } if (post !=
                     * 0) { sqlParam.append("," + post + ""); } }
                     */
                    // 职称
                    /*
                     * if (null != content[i][10] && !StringUtils.isBlank(content[i][10].trim()) &&
                     * !StringUtils.isEmpty(content[i][10].trim()) && !StringUtils.equals("null",
                     * content[i][10].trim())) { sql[j].append(",FK_POSTTITLE"); String
                     * postTitleString = content[i][10].trim(); String hql =
                     * "from Dictionary  d where d.dictionaryName = '" + postTitleString + "'";
                     * List<Dictionary> d = this.baseDao.queryEntitys(hql); int postTitle = 0; if (d
                     * != null && d.size() > 0) { postTitle = d.get(0).getPkDictionaryId(); } else {
                     * int o = i + 1; message = "第【" + o + "】行,【11】列发生错误,原因:指定的职称未找到！"; break; } if
                     * (postTitle != 0) { sqlParam.append("," + postTitle + ""); } }
                     */
                    // 职务1
                    /*
                     * if (null != content[i][11] && !StringUtils.isBlank(content[i][11].trim()) &&
                     * !StringUtils.isEmpty(content[i][11].trim()) && !StringUtils.equals("null",
                     * content[i][11].trim())) { sql[j].append(",FK_JOB1"); String jobString =
                     * content[i][11].trim(); String hql =
                     * "from Dictionary  d where d.dictionaryName = '" + jobString + "'";
                     * List<Dictionary> d = this.baseDao.queryEntitys(hql); int job = 0; if (d !=
                     * null && d.size() > 0) { job = d.get(0).getPkDictionaryId(); } else { int o =
                     * i + 1; message = "第【" + o + "】行,【12】列发生错误,原因:指定的职务未找到！"; break; } if (job !=
                     * 0) { sqlParam.append("," + job + ""); } }
                     */
                    // 职务2
                    /*
                     * if (null != content[i][12] && !StringUtils.isBlank(content[i][12].trim()) &&
                     * !StringUtils.isEmpty(content[i][12].trim()) && !StringUtils.equals("null",
                     * content[i][12].trim())) { sql[j].append(",FK_JOB2"); String jobString =
                     * content[i][12].trim(); String hql =
                     * "from Dictionary  d where d.dictionaryName = '" + jobString + "'";
                     * List<Dictionary> d = this.baseDao.queryEntitys(hql); int job = 0; if (d !=
                     * null && d.size() > 0) { job = d.get(0).getPkDictionaryId(); } else { int o =
                     * i + 1; message = "第【" + o + "】行,【13】列发生错误,原因:指定的职务未找到！"; break; } if (job !=
                     * 0) { sqlParam.append("," + job + ""); } }
                     */
                    // 职级
                    /*
                     * if (null != content[i][13] && !StringUtils.isBlank(content[i][13].trim()) &&
                     * !StringUtils.isEmpty(content[i][13].trim()) && !StringUtils.equals("null",
                     * content[i][13].trim())) { sql[j].append(",FK_JOBLEVEL"); String
                     * jobLevelString = content[i][13].trim(); String hql =
                     * "from Dictionary  d where d.dictionaryName = '" + jobLevelString + "'";
                     * List<Dictionary> d = this.baseDao.queryEntitys(hql); int jobLevel = 0; if (d
                     * != null && d.size() > 0) { jobLevel = d.get(0).getPkDictionaryId(); } else {
                     * int o = i + 1; message = "第【" + o + "】行,【14】列发生错误,原因:指定的职级未找到！"; break; } if
                     * (jobLevel != 0) { sqlParam.append("," + jobLevel + ""); } }
                     */
                    // 用户类别
                    if (null != content[i][6]
                        && !StringUtils.isBlank(content[i][6].trim())
                        && !StringUtils.isEmpty(content[i][6].trim())
                        && !StringUtils.equals("null", content[i][6].trim())) {
                        sql[j].append(",FK_TYPE");
                        String typeString = content[i][6].trim();
                        String hql =
                            "from Dictionary  d where d.dictionaryName = '"
                                + typeString + "'";
                        List<Dictionary> d = this.baseDao.queryEntitys(hql);
                        int type = 0;
                        if (d != null && d.size() > 0) {
                            type = d.get(0).getPkDictionaryId();
                        }
                        else {
                            int o = i + 1;
                            message = "第【" + o + "】行,【7】列发生错误,原因:指定的用户类别未找到！";
                            break;
                        }
                        if (type != 0) {
                            sqlParam.append("," + type + "");
                        }
                    }
                    else {
                        int o = i + 1;
                        message = "第【" + o + "】行,【7】列发生错误,原因:用户类别不能为空！";
                        break;
                    }
                    // 班组
                    /*
                     * if (null != content[i][15] && !StringUtils.isBlank(content[i][15].trim()) &&
                     * !StringUtils.isEmpty(content[i][15].trim()) && !StringUtils.equals("null",
                     * content[i][15].trim())) { sql[j].append(",FK_TEAM"); String teamString =
                     * content[i][15].trim(); String hql =
                     * "from Dictionary  d where d.dictionaryName = '" + teamString + "'";
                     * List<Dictionary> d = this.baseDao.queryEntitys(hql); int team = 0; if (d !=
                     * null && d.size() > 0) { team = d.get(0).getPkDictionaryId(); } else { int o =
                     * i + 1; message = "第【" + o + "】行,【16】列发生错误,原因:指定的班组未找到！"; break; } if (team !=
                     * 0) { sqlParam.append("," + team + ""); } }
                     */
                    sql[j].append(",USER_ONLINE");
                    sqlParam.append("," + 0 + "");
                    
                    sql[j].append(",STATUS");
                    sqlParam.append("," + 0 + "");
                    
                    sql[j].append(",ISENABLE");
                    sqlParam.append("," + 0 + "");
                    
                    sql[j].append(",USER_ID");
                    int id = this.baseDao.getIdFromSequence("HIBERNATE_SEQUENCE");
                    sqlParam.append("," + id + "");
                    
                    int orgUserId =
                        this.baseDao.getIdFromSequence("HIBERNATE_SEQUENCE");
                    String orgUserSql =
                        "INSERT INTO T_ORG_USER(PK_ORG_USER_ID,FK_ORG_ID,FK_USER_ID,ISDELETE) "
                            + "VALUES(" + orgUserId + "," + orgId + "," + id
                            + "," + Constant.STATUS_NOT_DELETE + ")";
                    
                    sql[j].append(") values(");
                    sqlParam.append(")");
                    sql[j].append(sqlParam.toString());
                    orgUserSqlArray.add(orgUserSql);
                    sqlArray.add(sql[j].toString());
                    sqlParam.delete(0, sqlParam.length());
                }
                if (message.equals("importSuccess")) {
                    if (sqlArray.size() > 0) {
                        String[] sqlInsert = new String[sqlArray.size()];
                        for (int i = 0; i < sqlArray.size(); i++) {
                            sqlInsert[i] = (String)sqlArray.get(i);
                        }
                        if (sqlInsert.length > 0) {
                            this.baseDao.batchUpdate(sqlInsert);
                        }
                    }
                    else {
                        message = "导入失败，Excel无用户数据！";
                    }
                    if (orgUserSqlArray.size() > 0) {
                        String[] sqlInsert = new String[orgUserSqlArray.size()];
                        for (int i = 0; i < orgUserSqlArray.size(); i++) {
                            sqlInsert[i] = (String)orgUserSqlArray.get(i);
                        }
                        if (sqlInsert.length > 0) {
                            this.baseDao.batchUpdate(sqlInsert);
                        }
                    }
                    else {
                        message = "导入失败，Excel无用户数据！";
                    }
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
    
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
    @Override
    public InputStream exportAllUsers(List<User> userList,
        List<OrgUser> orgUserList)
        throws BusinessException {
        InputStream inputStream = null;
        inputStream = HSSFUtils.getExcelInputStream(userList, orgUserList);
        return inputStream;
    }
    
    /**
     * 获取所有用户列表
     * 
     * @Title getAllUser
     * @author wanglc
     * @date 2013-11-25
     * @return List<User>
     */
    @Override
    public List<User> getAllUser()
        throws BusinessException {
        String hql =
            "from User o where o.enable=" + Constant.ENABLE + " and o.status="
                + Constant.STATUS_NOT_DELETE;
        List<User> result = baseDao.queryEntitys(hql);
        return result;
    }
    
    /**
     * 修改用户资源
     * 
     * @Title updateUserResource
     * @author wanglc
     * @date 2013-12-23
     * @param params
     * @param sysCode
     * @param userInfoList
     * @throws Exception
     */
    /*
     * @Transactional(readOnly = false, propagation = Propagation.REQUIRED) private void
     * updateUserResource(String sysCode, List<UserInfo> userInfoList) throws Exception { //
     * 删除本地用户资源数据 deleteLocalUserRes(); String[] params = CordysUtil.getCordysParam();
     * UserRightAdmin ua = new UserRightAdmin(params); for (UserInfo ui : userInfoList) {
     * Map<String, List<?>> userRights = ua.getAllUserRights(sysCode, ui.getAccount()); List<Menu>
     * userMenuList = (List<Menu>)userRights.get("MENU列表"); // 执行更新 doChange(userMenuList, ui); } }
     */
    
    /**
     * 执行更新
     * 
     * @Title doChange
     * @author wanglc
     * @date 2013-12-23
     * @param userMenuList
     * @param ui
     */
    /*
     * @Transactional(readOnly = false, propagation = Propagation.REQUIRED) private void
     * doChange(List<Menu> userMenuList, UserInfo ui) throws BusinessException { List<Resource>
     * resList = new ArrayList<Resource>(); if (userMenuList != null && userMenuList.size() > 0) {
     * for (Menu menu : userMenuList) { Resource res =
     * resourceService.getLocalResByName(menu.getMenu_name()); resList.add(res); } } User user =
     * getLocalUserByUserName(ui.getAccount()); if (user != null) { List<Organization> orgList =
     * orgUserService.getLocalOrganizationByUserId(user.getUserId()); if (orgList != null &&
     * orgList.size() > 0) { List<UserOrgResource> uorList = new ArrayList<UserOrgResource>(); for
     * (Resource resource : resList) { UserOrgResource uor = new UserOrgResource();
     * uor.setResource(resource); uor.setUser(user); for (Organization org : orgList) {
     * uor.setOrganization(org); uorList.add(uor); } } baseDao.saveOrUpdate(uorList); } } }
     */
    
    /**
     * 删除本地用户资源
     * 
     * @Title deleteLocalUserRes
     * @author wanglc
     * @date 2013-12-23
     */
    /*
     * @Transactional(readOnly = false, propagation = Propagation.REQUIRED) private void
     * deleteLocalUserRes() throws BusinessException { String hql =
     * "from UserOrgResource uor where uor.organization.orgFrom <> 0"; List<UserOrgResource> uorList
     * = baseDao.queryEntitys(hql); baseDao.deleteEntities(uorList); }
     */
    
    /**
     * 更新用户角色
     * 
     * @Title updateUserRole
     * @author wanglc
     * @date 2013-12-23
     * @param sysCode
     * @param userInfoList
     * @throws Exception
     */
    /*
     * @Transactional(readOnly = false, propagation = Propagation.REQUIRED) private void
     * updateUserRole(String sysCode, List<UserInfo> userInfoList) throws Exception { String[]
     * params = CordysUtil.getCordysParam(); // 删除本地用户角色关系
     * UserRoleServiceImpl.deleteLocalUserRoles(baseDao); UserRightAdmin ua = new
     * UserRightAdmin(params); Map<UserInfo, List<com.dqgb.cordys.userAdmin.entity.Role>>
     * userRoleMap = new HashMap<UserInfo, List<com.dqgb.cordys.userAdmin.entity.Role>>(); for
     * (UserInfo ui : userInfoList) { List<com.dqgb.cordys.userAdmin.entity.Role> roleList =
     * ua.getUserRoles(sysCode, ui.getAccount()); userRoleMap.put(ui, roleList); } // 执行用户角色更新
     * doUserRoleChange(userRoleMap); }
     */
    
    /**
     * 执行用户角色更新
     * 
     * @Title doUserRoleChange
     * @author wanglc
     * @date 2013-12-23
     * @param userRoleMap
     */
    /*
     * @Transactional(readOnly = false, propagation = Propagation.REQUIRED) private void
     * doUserRoleChange( Map<UserInfo, List<com.dqgb.cordys.userAdmin.entity.Role>> userRoleMap)
     * throws BusinessException { for (Map.Entry<UserInfo,
     * List<com.dqgb.cordys.userAdmin.entity.Role>> entry : userRoleMap.entrySet()) { UserInfo ui =
     * entry.getKey(); Organization org = getLocalOrgByCode(ui.getOrg_code()); if (org == null) {
     * continue; } User user = getLocalUserByUserName(ui.getAccount()); if (user == null) {
     * continue; } List<com.dqgb.cordys.userAdmin.entity.Role> rList =
     * (List<com.dqgb.cordys.userAdmin.entity.Role>)entry.getValue(); for
     * (com.dqgb.cordys.userAdmin.entity.Role r : rList) { UserRole ur = new UserRole();
     * ur.setOrganization(org); ur.setUser(user); Role role =
     * roleService.getLocalRoleByName(r.getRole_name()); if (role == null) { continue; } else {
     * ur.setRole(role); } baseDao.saveOrUpdate(ur); } } }
     */
    
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
    @Override
    public List<User> getOnlineUser(int userId)
        throws Exception {
        /*
         * User u = this.getUserById(userId); u.setUserOnline(System.currentTimeMillis());
         * baseDao.updateEntity(u); StringBuffer hql = new StringBuffer(" " +
         * System.currentTimeMillis() + "- obj.userOnline< " +
         * PropertyUtil.get("interval.time.millis")); List<User> userList =
         * (List)baseDao.find(hql.toString(), new Object[] {}, 0, Integer.MAX_VALUE);
         */
        return null;
    }
    
    /**
     * 根据登录名称（以“,”分割的字符串）获取用户对象列表
     * 
     * @Title getUserListByLoginNames
     * @author wanglc
     * @date 2013-11-25
     * @param loginNames 登录名称
     * @return List<User>
     */
    @Override
    public List<User> getUserListByLoginNames(String loginNames)
        throws BusinessException {
        StringBuffer loginNameBuffer = new StringBuffer();
        for (String str : loginNames.split(",")) {
            loginNameBuffer.append("'").append(str).append("'").append(",");
        }
        
        Map paramMap = new HashMap();
        paramMap.put("enable", Constant.ENABLE);
        paramMap.put("status", Constant.STATUS_NOT_DELETE);
        String hql =
            "from User where enable=:enable and status=:status and username in("
                + loginNameBuffer.substring(0, loginNameBuffer.length() - 1)
                + ")";
        return (List<User>)this.baseDao.queryEntitysByMap(hql, paramMap);
    }
    
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
    @Override
    public ListVo<User> getUsersByOrgId(String orgId, String realname,
        int start, int limit)
        throws BusinessException {
        StringBuffer hql =
            new StringBuffer("from User u where u.enable = 0 and u.status = 0");
        String totalSizeHql =
            "select count(1) from User u where u.enable = 0 and u.status = 0";
        
        if (!"".equals(orgId) && orgId != null) {
            hql.append("and u.organization.orgId = " + orgId + "");
            totalSizeHql += "and u.organization.orgId = " + orgId + "";
        }
        if (!"".equals(realname) && realname != null) {
            hql.append(" and u.realname like '%" + realname + "%'");
            totalSizeHql += " and u.realname like '%" + realname + "%'";
        }
        int totalSize = baseDao.totalCountHql(totalSizeHql, null);
        List<User> userList =
            baseDao.query(hql.toString(), new Object[] {}, start, limit);
        ListVo<User> userListVo = new ListVo<User>();
        userListVo.setList(userList);
        userListVo.setTotalSize(totalSize);
        return userListVo;
    }
    
    /**
     * 密码重置
     * 
     * @Title resetUserPassword
     * @author wanglc
     * @date 2013-11-25
     * @param u 用户对象
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void resetUserPassword(User user)
        throws BusinessException {
        baseDao.saveOrUpdate(user);
    }
    
    /**
     * 获取用户列表，为流程在线设计器
     * 
     * @Title getUserListForFlow
     * @author wanglc
     * @date 2013-11-25
     * @param paramMap 参数Map
     * @return ListVo<User>
     */
    @Override
    public ListVo<User> getUserListForFlow(Map<String, Object> paramMap)
        throws BusinessException {
        StringBuffer hqlList = new StringBuffer();
        StringBuffer hqlCount = new StringBuffer();
        StringBuffer idBuffer = new StringBuffer();
        Map map = new HashMap();
        Map countMap = new HashMap();
        ListVo<User> userListVo = new ListVo<User>();
        List<User> userOtherList = new ArrayList();
        List<User> userList = new ArrayList();
        List<User> userListTemp = new ArrayList();
        int start = NumberUtils.toInt((String)paramMap.get("start"));
        int limit = NumberUtils.toInt((String)paramMap.get("limit"));
        String usersId = (String)paramMap.get("usersId");
        String userName = (String)paramMap.get("userName");
        hqlList.append("select a from User a where 1 = 1 ");
        hqlList.append(" and a.enable=" + Constant.ENABLE);
        hqlList.append(" and a.status=" + Constant.STATUS_NOT_DELETE);
        hqlCount.append("select count(a.id) from User a where 1 = 1");
        hqlCount.append(" and a.enable=" + Constant.ENABLE);
        hqlCount.append(" and a.status=" + Constant.STATUS_NOT_DELETE);
        if (userName != null && !userName.trim().equals("")) {
            hqlList.append(" and a.realname like '%"
                + userName.toString().trim() + "%'");
            hqlCount.append(" and a.realname like '%"
                + userName.toString().trim() + "%'");
            if (usersId != null && !usersId.trim().equals("")) {
                String ids[] = usersId.split(",");
                for (String id : ids) {
                    idBuffer.append("'").append(id).append("'").append(",");
                }
                userList =
                    (List<User>)this.baseDao.queryEntitysByMap("from User a where  a.enable="
                        + Constant.ENABLE
                        + " and  a.status="
                        + Constant.STATUS_NOT_DELETE
                        + " and a.realname like  '%"
                        + userName.toString().trim()
                        + "%' and a.username in("
                        + idBuffer.substring(0, idBuffer.length() - 1) + ")",
                        countMap);
                StringBuffer loginName = new StringBuffer();
                if (userList.size() > 0) {
                    for (User u : userList) {
                        loginName.append("'")
                            .append(u.getUsername())
                            .append("'")
                            .append(",");
                    }
                    hqlList.append(" and a.username not in ("
                        + loginName.substring(0, loginName.length() - 1) + ")");
                    if (start < userList.size()) {
                        if (limit + start <= userList.size()) {
                            userList = userList.subList(start, limit + start);
                        }
                        else {
                            userList = userList.subList(start, userList.size());
                            limit = limit - userList.size();
                            userListTemp =
                                (List<User>)this.baseDao.findPageByQuery(0,
                                    limit,
                                    hqlList.toString(),
                                    map);
                        }
                    }
                    else {
                        
                        start = start - userList.size();
                        userListTemp =
                            (List<User>)this.baseDao.findPageByQuery(start,
                                limit,
                                hqlList.toString(),
                                map);
                        userList.clear();
                    }
                }
                else {
                    userListTemp =
                        (List<User>)this.baseDao.findPageByQuery(start,
                            limit,
                            hqlList.toString(),
                            map);
                }
            }
            else {
                userListTemp =
                    (List<User>)this.baseDao.findPageByQuery(start,
                        limit,
                        hqlList.toString(),
                        map);
            }
            
        }
        else if (usersId != null && !usersId.trim().equals("")) {
            map.clear();
            idBuffer = new StringBuffer();
            String ids[] = usersId.split(",");
            for (String id : ids) {
                idBuffer.append("'").append(id).append("'").append(",");
            }
            hqlList.append(" and a.username not in ("
                + idBuffer.substring(0, idBuffer.length() - 1) + ")");
            
            String hql =
                "select a from User a where a.enable=" + Constant.ENABLE
                    + " and  a.status=" + Constant.STATUS_NOT_DELETE
                    + " and a.username in("
                    + idBuffer.substring(0, idBuffer.length() - 1) + ")  ";
            userOtherList =
                (List<User>)this.baseDao.queryEntitysByMap(hql, map);
            if (userOtherList.size() > 0) {
                if (start < userOtherList.size()) {
                    if (limit + start <= userOtherList.size()) {
                        userOtherList =
                            userOtherList.subList(start, limit + start);
                    }
                    else {
                        userOtherList =
                            userOtherList.subList(start, userOtherList.size());
                        limit = limit - userOtherList.size();
                        userListTemp =
                            (List<User>)this.baseDao.findPageByQuery(0,
                                limit,
                                hqlList.toString(),
                                map);
                        
                    }
                }
                else {
                    
                    start = start - userOtherList.size();
                    userListTemp =
                        (List<User>)this.baseDao.findPageByQuery(start,
                            limit,
                            hqlList.toString(),
                            map);
                    userOtherList.clear();
                }
            }
            else {
                userListTemp =
                    (List<User>)this.baseDao.findPageByQuery(start,
                        limit,
                        hqlList.toString(),
                        map);
            }
            
        }
        else {
            userListTemp =
                (List<User>)this.baseDao.findPageByQuery(start,
                    limit,
                    hqlList.toString(),
                    map);
        }
        int count = this.baseDao.getTotalCount(hqlCount.toString(), countMap);
        userList.addAll(userOtherList);
        userList.addAll(userListTemp);
        userListVo.setTotalSize(count);
        userListVo.setList(userList);
        return userListVo;
    }
    
    /**
     * 用户登录
     * 
     * @Title login
     * @throws Exception 
     * @throws BusinessException 
     * @date 2013-12-13
     */
    @Override
    public Map<String, String> userLogin(Map<String, String> paramMap, User user)
        throws BusinessException, Exception {
        Map<String, String> map = new HashMap<String, String>();
        //String username = paramMap.get("userName");
        String password = paramMap.get("password");
       
        if (user == null || user.getStatus() == Constant.STATUS_IS_DELETED) {
            map.put("flag", "false");
            map.put("msg", "用户不存在");
            return map;
        }
        
        if (!user.getPassword().equals(MD5Util.encode(password))) {
            map.put("flag", "false");
            map.put("msg", "密码错误");
            return map;
        }
        else {
            map.put("flag", "true");
        }
        return map;
    }
    
    /**
     * 逻辑删除用户，更改用户状态为2（集成平台已删除）
     * 
     * @Title deleteLocalUserLogic
     * @date 2013-12-17
     * @param user
     * @param ui
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private void deleteLocalUserLogic(User user)
        throws BusinessException {
        user.setStatus(Constant.STATUS_DELETE_BYJCPT);
        baseDao.saveOrUpdate(user);
    }
    
    /**
     * 取本地数库中的当前组织
     * 
     * @Title getLocalOrgByCode
     * @date 2013-12-16
     * @param code
     * @return
     */
    private Organization getLocalOrgByCode(String code)
        throws BusinessException {
        List<Organization> orgList = new ArrayList<Organization>();
        String hql =
            "from Organization o where o.orgCode = '" + code
                + "' and o.status = " + Constant.STATUS_NOT_DELETE;
        orgList = baseDao.queryEntitys(hql);
        if (orgList != null && orgList.size() > 0) {
            return orgList.get(0);
        }
        else {
            return null;
        }
    }
    
    /**
     * 根据登录名称取用户
     * 
     * @Title getLocalUserByUserName
     * @author wanglc
     * @date 2013-12-16
     * @param code
     * @return
     */
    private User getLocalUserByUserName(String userName)
        throws BusinessException {
        List<User> userList = new ArrayList<User>();
        String hql =
            "from User u where u.username = '" + userName + "'";
        userList = baseDao.queryEntitys(hql);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        else {
            return null;
        }
    }
    
    /**
     * 添加一个新的字典项
     * 
     * @Title addNewDictionary
     * @author yzg
     * @date 2014-2-13
     * @param dictionaryName
     * @param dictionaryType
     * @param dictionaries 系统中现有的职务或班组集合
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Dictionary addNewDictionary(String dictionaryName,
        Dictionary dictionaryType, List<Dictionary> dictionaries)
        throws BusinessException {
        Dictionary dictionary = new Dictionary();
        dictionary.setDictionaryName(dictionaryName);
        // dictionary.setFkDictTypeUUID(dictionaryType.getDictUUID());
        dictionary.setDictUUID(UUIDUtil.getUUID("Dictionary"));
        dictionary.setStatus(StringUtil.getStr(Constant.STATUS_NOT_DELETE));
        dictionary.setDictionaryValue(StringUtil.getStr(getMaxValue(dictionaries,
            1)));
        dictionary.setLevelOrder(getMaxValue(dictionaries, 2));
        
        dictionary.setDictionary(dictionaryType);
        
        SessionFactory sf = baseDao.getHibernateTemp().getSessionFactory();
        Session session = sf.getCurrentSession();
        
        session.saveOrUpdate(dictionary);
        session.flush();
        return dictionary;
    }
    
    /**
     * 获取最大的字典值或排序号
     * 
     * @Title getMaxValue
     * @author yzg
     * @date 2014-2-13
     * @param dictionaries
     * @param type 类别：1：字典值，2：排序号
     * @return
     */
    public int getMaxValue(List<Dictionary> dictionaries, int type)
        throws BusinessException {
        int max = 1;
        for (int i = 0; i < dictionaries.size(); i++) {
            int value = 0;
            if (type == 1) {
                value =
                    org.apache.commons.lang.NumberUtils.stringToInt(dictionaries.get(i)
                        .getDictionaryValue());
            }
            else {
                value = dictionaries.get(i).getLevelOrder();
            }
            if (value >= max) {
                max = value + 1;
            }
        }
        return max;
    }
    
    /**
     * 获取指定用户的所有部门名称及ID，逗号分隔
     * 
     * @Title getUserOrgs
     * @author yzg
     * @date 2014-2-19
     * @param userId
     * @return
     */
    @Override
    public String getUserOrgs(String userId)
        throws BusinessException {
        String orgId = "";
        String orgName = "";
        String hql =
            "from OrgUser ou where ou.isDelete = " + Constant.STATUS_NOT_DELETE
                + " and ou.user.userId = " + userId;
        List<OrgUser> list = baseDao.queryEntitys(hql);
        for (int i = 0; i < list.size(); i++) {
            OrgUser orgUser = list.get(i);
            orgId += orgUser.getOrganization().getOrgId();
            orgName += orgUser.getOrganization().getOrgName();
            if (i != list.size() - 1) {
                orgId += ",";
                orgName += ",";
            }
        }
        return "{orgId:'" + orgId + "',orgName:'" + orgName + "'}";
    }
    
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
    @Override
    public int userIsExist(String userId, String code)
        throws BusinessException {
        int size = 0;
        int status = 0;
        try {
            if (!code.equals("update")) {
                status = Constant.DISABLE;
            }
            String sql =
                "select count(*) from T_USER a  where a.user_id in (" + userId
                    + ") and a.status =  " + status;
            size = this.baseDao.queryCountSQL(sql);
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        
        return size;
    }
    
    /**
     * @return userRoleService
     */
    /*
     * public IUserRoleService getUserRoleService() { return userRoleService; }
     */
    
    /**
     * @param userRoleService 要设置的 userRoleService
     */
    /*
     * public void setUserRoleService(IUserRoleService userRoleService) { this.userRoleService =
     * userRoleService; }
     */
    
    /**
     * @return roleResourceService
     */
    public IRoleResourceService getRoleResourceService() {
        return roleResourceService;
    }
    
    /**
     * @param roleResourceService 要设置的 roleResourceService
     */
    public void setRoleResourceService(IRoleResourceService roleResourceService) {
        this.roleResourceService = roleResourceService;
    }
    
    /**
     * 通过用户id得到该用户拥有的资源以及该用户的部门权限
     * 
     * @Title getResAndOrg
     * @author liukang-wb
     * @date 2014年9月16日
     * @param userId
     * @return Map<String,Object>
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> getResAndOrg(User user)
        throws BusinessException {
        Map<String, Object> params = new HashMap<String, Object>();
        // 用来放资源的List
        List<ResourceVo> resList = new ArrayList<ResourceVo>();
        // 用来放部门权限的List
        List<Organization> orgList = new ArrayList<Organization>();
        // 用来存放菜单的list
        List<ResMenuVo> menu = new ArrayList<ResMenuVo>();
        
        // 获得当前用户的组织
        List<Organization> organizations = new ArrayList<Organization>();
        List<OrgUser> orgUsers =
            orgUserService.getOrgUserByUserId(user.getUserId());
        if (!CollectionUtils.isEmpty(orgUsers)) {
            for (OrgUser orgUser : orgUsers) {
                organizations.add(orgUser.getOrganization());
            }
            // 部门默认为当前登录用户所属组织
            // orgList.addAll(organizations);
        }
        /**** 以下是组装资源（权限） ****/
        // 存放角色id
        String roleIds = "";
        // 存放群组id
        String groupId = "";
        // 存放角色成员群组id
        String rmsId = "";
        // 1.当用户有角色的时候
        List<RoleMemberScope> roleMemberScopes1 =
            roleService.getRoleMemberScopeByMemberId(String.valueOf(user.getUserId()),
                Constant.USER);
        
        // 当改用户已经分配了角色
        if (roleMemberScopes1.size() > 0) {
            for (RoleMemberScope roleMemberScope : roleMemberScopes1) {
                roleIds += "," + roleMemberScope.getRole().getRoleId();
                rmsId += "," + roleMemberScope.getId();
            }
        }
        // 2.当用户没有角色，而用户所在群组有角色的时候（通过userid去获取群组）
        List<GroupMember> groupMembers1 =
            groupService.getGroupByMemberId(String.valueOf(user.getUserId()),
                Constant.USER);
        // 如果用户有群组
        if (groupMembers1 != null && groupMembers1.size() > 0) {
            for (GroupMember groupMember : groupMembers1) {
                groupId += "," + groupMember.getGroup().getId();
            }
        }
        
        // 3.当用户及用户所在的群组都没有角色的时候，而用户所在的部门所在的群组有角色的时候
        // 如果用户有部门
        if (organizations != null && organizations.size() > 0) {
            // 装载orgid
            String orgIds = "";
            for (Organization org : organizations) {
                orgIds += "," + org.getOrgId();
            }
            orgIds = orgIds.substring(1);
            List<GroupMember> groupMembers2 =
                groupService.getGroupByMemberId(orgIds, Constant.ORG);
            // 如果为部门分配了群组
            if (groupMembers2 != null && groupMembers2.size() > 0) {
                for (GroupMember groupMember : groupMembers2) {
                    groupId += "," + groupMember.getGroup().getId();
                }
            }
        }
        // 对groupid进行拼接
        if (StringUtils.isNotBlank(groupId)) {
            groupId = groupId.substring(1);
        }
        List<RoleMemberScope> roleMemberScopes2 =
            roleService.getRoleMemberScopeByMemberId(groupId, Constant.GROUP);
        // 如果为群组分配了角色
        if (roleMemberScopes2 != null && roleMemberScopes2.size() > 0) {
            for (RoleMemberScope roleMemberScope : roleMemberScopes2) {
                roleIds += "," + roleMemberScope.getRole().getRoleId();
                rmsId += "," + roleMemberScope.getId();
            }
        }
        
        if (roleIds.length() > 1) {
            // 对roleid进行拼接
            roleIds = roleIds.substring(1);
            List<RoleResource> roleResources =
                roleResourceService.getOwnResByUserRole(roleIds);
            
            // 拼装资源ID，用于查询排除重复
            String resIds = "";
            if (roleResources != null && roleResources.size() > 0) {
                for (RoleResource roleResource : roleResources) {
                    resIds =
                        resIds + ","
                            + roleResource.getResource().getResourceId();
                }
            }
            
            // 查询资源
            List<Resource> resLst = null;
            if (StringUtils.isNotBlank(resIds)) {
                resLst = resourceService.getResourceByIds(resIds.substring(1));
            }
            
            // 获取菜单
            menu.addAll(changeResourceToMenu(resLst));
            
            // 获取权限内资源
            ResourceVo vo = null;
            if (resLst != null && resLst.size() > 0) {
                for (Resource res : resLst) {
                    vo = new ResourceVo();
                    vo.setResourceId(res.getResourceId());
                    vo.setResourceName(res.getResourceName());
                    vo.setCode(res.getCode());
                    resList.add(vo);
                }
            }
        }
        
        // 获取权限内部门
        if (rmsId.length() > 1) {
            rmsId = rmsId.substring(1);
            List<ScopeMember> scopeMembers =
                roleService.getScpMemberByRMSid(rmsId, Constant.ORG);
            for (ScopeMember scopeMember : scopeMembers) {
                orgList.add(scopeMember.getOrg());
            }
        }
        
        /***** 组装完毕 *****/
        // 放入map返回供action调用
        params.put("userPermission", resList);
        params.put("orgPermission", removeTheSame(orgList));
        params.put("menuList", menu);
        
        // params.put("userPermission",resList);
        // params.put("orgPermission", orgList);
        return params;
    }
    
    /**
     * 去掉重复
     * 
     * @Title removeTheSame
     * @author liukang-wb
     * @date 2014年9月18日
     * @param list
     * @return
     */
    public Set<Organization> removeTheSame(List<Organization> list)
        throws BusinessException {
        Set<Organization> newset = new HashSet<Organization>();
        for (int i = 0; i < list.size(); i++) {
            newset.add(list.get(i));
        }
        return newset;
    }
    
    /**
     * 把资源转化为资源菜单
     * 
     * @Title changeResourceToTreeVo
     * @author dong.he
     * @Description:
     * @date 2014年9月29日
     * @param res
     * @return
     */
    private List<ResMenuVo> changeResourceToMenu(List<Resource> res)
        throws BusinessException {
        List<ResMenuVo> vos = new ArrayList<ResMenuVo>();
        ResMenuVo vo = null;
        Map<String, ResMenuVo> maps =  new HashMap<String, ResMenuVo>();
        if (res != null && res.size() > 0) {
            // 把页面资源归类;适用于两级菜单的且父类的parentId=null；
            for (Resource re : res) {
                String dictCode = re.getResourceType().getDictCode();
                if (StringUtil.isNotBlank(dictCode)
                    && Constant.RES_PAGE.equals(dictCode)) {
                    if (re.getResource() == null) {
                        // 判断是否已经添加该资源;
                        if (!maps.containsKey(re.getResourceId().toString())) {
                            vo = new ResMenuVo();
                            vo.setName(re.getResourceName());
                            vo.setText(re.getResourceName());
                            vo.setId(re.getResourceId().toString());
                            vo.setUrl(re.getUrlpath());
                            vo.setDisOrder(re.getDisOrder());
                            maps.put(re.getResourceId().toString(), vo);
                        }
                    }
                    else {
                        ResMenuVo rs = maps.get(re.getResource().getResourceId().toString());
                        if (rs == null) {
                            rs = new ResMenuVo();
                            rs.setName(re.getResource().getResourceName());
                            rs.setText(re.getResource().getResourceName());
                            rs.setId(re.getResource().getResourceId().toString());
                            rs.setUrl(re.getResource().getUrlpath());
                            if (re.getResource().getResource() != null) {
                                rs.setParentId(re.getResource().getResource().getResourceId().toString());
                            }
                            rs.setDisOrder(re.getResource().getDisOrder());
                            maps.put(re.getResource().getResourceId().toString(), rs);
                        }
                        
                        if (CollectionUtils.isEmpty(rs.getCls())) {
                            rs.setCls(new ArrayList<ResMenuVo>()); 
                        }
                        
                        // 将自己设定到父节点
                        ResMenuVo self = maps.get(re.getResourceId().toString());
                        if (self == null) {
                            vo = new ResMenuVo();
                            vo.setName(re.getResourceName());
                            vo.setText(re.getResourceName());
                            vo.setId(re.getResourceId().toString());
                            vo.setUrl(re.getUrlpath());
                            vo.setParentId(re.getResource().getResourceId().toString());
                            vo.setDisOrder(re.getDisOrder());
                            
                            rs.getCls().add(vo);
                            // 将自己加入maps
                            maps.put(re.getResourceId().toString(), vo);
                        }
                        else {
                            rs.getCls().add(self);
                        }
                    }
                }
            }
            // 只要顶层节点;
            Iterator it = maps.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry)it.next();
                ResMenuVo rmv = (ResMenuVo)entry.getValue();
                if (rmv != null && StringUtils.isBlank(rmv.getParentId())) {
                    vos.add(rmv);
                }
            }
            
            // 菜单排序
            mapSort(vos);
        }
        return vos;
    }
    
    /**
     * 把菜单进行排序
     * 
     * @Title mapSort
     * @author dong.he
     * @date 2014年9月29日
     * @param maps
     * @return
     */
    private void mapSort(List<ResMenuVo> list)
        throws BusinessException {
        Collections.sort(list,
            new Comparator<ResMenuVo>() {
                public int compare(ResMenuVo r1, ResMenuVo r2) {
                    return r1.getDisOrder()  - r2.getDisOrder();
                }
            }
        );
        
        for (ResMenuVo rmVo : list) {
            if (!CollectionUtils.isEmpty(rmVo.getCls())) {
                mapSort(rmVo.getCls());
            }
        }
    }
    
    
    public Map<String, Object> convertMap(User user){
        Map<String, Object> data = new HashMap<String, Object>();
        if (user != null)
        {
        //出生日期
        data.put("user.birthDay",user.getBirthDay() == null ? "" : user.getBirthDay());
        //排序
        data.put("user.disOrder",user.getDisOrder() == null ? 0 : user.getDisOrder());
        //性别
        data.put("user.gender",user.getGender() == null ? "" : user.getGender());
        //邮箱
        data.put("user.email",user.getEmail() == null ? "" : user.getEmail());
        //移动电话
        data.put("user.mobileNo",user.getMobileNo() == null ? "" : user.getMobileNo());
        //电话号码
        data.put("user.phoneNo",user.getPhoneNo() == null ? "" : user.getPhoneNo());
        //集团短号
        data.put("user.shortNo",user.getShortNo() == null ? "" : user.getShortNo());
        //出生地
        data.put("user.birthPlace",user.getBirthPlace() == null ? ""
            : user.getBirthPlace());
        //密码 
        //data.put("user.password",user.getPassword() == null ? "" : user.getPassword());
        //真实姓名
        data.put("user.realname",user.getRealname() == null ? "" : user.getRealname());
        //id
        data.put("user.userId",user.getUserId() == null ? 0 : user.getUserId());
        data.put("user.username",user.getUsername() == null ? "" : user.getUsername());
        data.put("user.erpId",user.getErpId() == null ? "" : user.getErpId());
        data.put("user.idCard",user.getIdCard() == null ? "" : user.getIdCard());
        
        if (user.getResponsibilities() != null) {
        	data.put("user.responsibilities.pkRespId",user.getResponsibilities().getPkRespId());
            data.put("user.respName",user.getResponsibilities().getName());
        }
        
        data.put("user.nationality", user.getNationality() == null ? "" : user.getNationality());
        data.put("user.partyDate", user.getPartyDate() == null ? "" : user.getPartyDate());
        data.put("user.jobStartDate", user.getJobStartDate() == null ? "" : user.getJobStartDate());
        data.put("user.officeHoldingDate", user.getOfficeHoldingDate() == null ? "" : user.getOfficeHoldingDate());
        data.put("user.educationBackground", user.getEducationBackground() == null ? "" : user.getEducationBackground());
        data.put("user.technicaTitles", user.getTechnicaTitles() == null ? "" : user.getTechnicaTitles());
        data.put("user.comeDate", user.getComeDate() == null ? "" : user.getComeDate());
        data.put("user.skill", user.getSkill() == null ? "" : user.getSkill());
        data.put("user.performance", user.getPerformance() == null ? "" : user.getPerformance());
        data.put("user.employmentInfo", user.getEmploymentInfo() == null ? "" : user.getEmploymentInfo());
        data.put("user.postWage", user.getPostWage() == null ? "" : user.getPostWage());
        data.put("user.trainInfo", user.getTrainInfo() == null ? "" : user.getTrainInfo());
        
        // 组织
        String orgId = "";
        String orgName = "";
        try
        {
            String hql =
                "from OrgUser ou where ou.isDelete = " + Constant.STATUS_NOT_DELETE
                    + " and ou.user.userId = " + user.getUserId();
            List<OrgUser> list = baseDao.queryEntitys(hql);
            for (int i = 0; i < list.size(); i++) {
                OrgUser orgUser = list.get(i);
                orgId += orgUser.getOrganization().getOrgId();
                orgName += orgUser.getOrganization().getOrgName();
                if (i != list.size() - 1) {
                    orgId += ",";
                    orgName += ",";
                }
            }
        }
        catch (Exception e)
        {
            
        }
        data.put("orgIds", orgId);
        data.put("orgNames", orgName);
        }
        return data;
    }
    
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
    @Override
    public UserVo convertVo(User user)
        throws BusinessException {
        UserVo vo = new UserVo();
        vo.setBirthDay(user.getBirthDay() == null ? "" : user.getBirthDay());
        vo.setDisOrder(user.getDisOrder() == null ? 0 : user.getDisOrder());
        vo.setEnable(user.getEnable());
        vo.setGender(user.getGender() == null ? "" : user.getGender());
        vo.setEmail(user.getEmail() == null ? "" : user.getEmail());
        vo.setIdCard(user.getIdCard() == null ? "" : user.getIdCard());
        vo.setMobileNo(user.getMobileNo() == null ? "" : user.getMobileNo());
        vo.setPhoneNo(user.getPhoneNo() == null ? "" : user.getPhoneNo());
        vo.setShortNo(user.getShortNo() == null ? "" : user.getShortNo());
        vo.setBirthPlace(user.getBirthPlace() == null ? ""
            : user.getBirthPlace());
        vo.setPassword(user.getPassword() == null ? "" : user.getPassword());
        vo.setRealname(user.getRealname() == null ? "" : user.getRealname());
        vo.setStatus(user.getStatus());
        vo.setUserId(user.getUserId() == null ? 0 : user.getUserId());
        vo.setUsername(user.getUsername() == null ? "" : user.getUsername());
        vo.setErpId(user.getErpId() == null ? "" : user.getErpId());
        
        if (user.getResponsibilities() != null) {
        	vo.setRespId(user.getResponsibilities().getPkRespId());
        	vo.setRespName(user.getResponsibilities().getName());
        }
        
        vo.setNationality(user.getNationality() == null ? "" : user.getNationality());
        vo.setPartyDate(user.getPartyDate() == null ? "" : user.getPartyDate());
        vo.setJobStartDate(user.getJobStartDate() == null ? "" : user.getJobStartDate());
        vo.setOfficeHoldingDate(user.getOfficeHoldingDate() == null ? "" : user.getOfficeHoldingDate());
        vo.setEducationBackground(user.getEducationBackground() == null ? "" : user.getEducationBackground());
        vo.setTechnicaTitles(user.getTechnicaTitles() == null ? "" : user.getTechnicaTitles());
        vo.setComeDate(user.getComeDate() == null ? "" : user.getComeDate());
        vo.setSkill(user.getSkill() == null ? "" : user.getSkill());
        vo.setPerformance(user.getPerformance() == null ? "" : user.getPerformance());
        vo.setEmploymentInfo(user.getEmploymentInfo() == null ? "" : user.getEmploymentInfo());
        vo.setPostWage(user.getPostWage() == null ? "" : user.getPostWage());
        vo.setTrainInfo(user.getTrainInfo() == null ? "" : user.getTrainInfo());
        
        // 属于多部门的情况，进行部门名称的拼接
        Set<OrgUser> ouSet = user.getOrgUsers();
        if (ouSet.size() <= 1) {
            vo.setOrgName(ouSet.iterator()
                .next()
                .getOrganization()
                .getOrgName());
        }
        else {
            String orgIds = "";
            Iterator<OrgUser> it = ouSet.iterator();
            while (it.hasNext()) {
                OrgUser ou = it.next();
                if (ou.getIsDelete() == Constant.STATUS_NOT_DELETE) {
                    orgIds = orgIds + "," + ou.getOrganization().getOrgId();
                }
            }
            List<Organization> orgLst =
                orgService.getOrgByIds(orgIds.substring(1));
            
            String orgNames = "";
            if (!CollectionUtils.isEmpty(orgLst)) {
                for (Organization organization : orgLst) {
                    orgNames = orgNames + "," + organization.getOrgName();
                }
                orgNames = orgNames.substring(1);
            }
            vo.setOrgName(orgNames);
        }
        return vo;
    }

    @Override
    public InputStream updateTempleUser(String path, String[] textlist)
        throws BusinessException
    {
        return  HSSFUtils.updateTempleUser(path, textlist);
    }
}
