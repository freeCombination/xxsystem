package com.xx.system.role.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.PagerVo;
import com.xx.system.dict.service.IDictService;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.service.IOrgService;
import com.xx.system.resource.entity.Resource;
import com.xx.system.resource.service.IResourceService;
import com.xx.system.role.entity.Role;
import com.xx.system.role.entity.RoleMemberScope;
import com.xx.system.role.entity.RoleResource;
import com.xx.system.role.entity.ScopeMember;
import com.xx.system.role.service.IRoleService;
import com.xx.system.role.vo.RoleVo;
import com.xx.system.user.entity.Group;
import com.xx.system.user.entity.User;
import com.xx.system.user.util.HSSFUtils;
import com.xx.system.user.vo.GroupVo;
import com.xx.system.user.vo.UserVo;

/**
 * 详细事项角色接口定义的方法
 * 
 * @version V1.20,2013-11-25 下午2:42:32
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Service("roleService")
@SuppressWarnings({"rawtypes", "unchecked"})
public class RoleServiceImpl implements IRoleService {
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    @Autowired
    @Qualifier("organizationService")
    private IOrgService orgService;
    
    @Autowired
    @Qualifier("resourceService")
    private IResourceService resourceService;
    
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    /*
     * @Autowired
     * @Qualifier("userRoleService") private IUserRoleService userRoleService; public void
     * setUserRoleService(IUserRoleService userRoleService) { this.userRoleService =
     * userRoleService; }
     */
    
    @Autowired
    @Qualifier("dictService")
    private IDictService dictService;
    
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
    
    @javax.annotation.Resource
    private IRoleService roleService;
    
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }
    
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Log(operationType = "添加", operationName = "角色信息")
    @Override
    public String addUpdateRole(Role role)
        throws BusinessException {
        if (role == null) {
            return "{success:'false',msg:'添加角色失败！'}";
        }
        try {
            baseDao.saveOrUpdate(role);
            return "{success:'true',msg:'添加角色成功！'}";
        }
        catch (Exception e) {
            
            return "{success:'false',msg:'添加角色失败！'}";
        }
        
    }
    
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
    @Override
    public RoleVo getRole(int id)
        throws BusinessException {
    	Role role = (Role)baseDao.queryEntityById(Role.class, id);
    	RoleVo roleVo = new RoleVo(role);
        
    	return roleVo;
    }
    
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Log(operationType = "删除", operationName = "角色信息")
    @Override
    public String deleteRole(String roleId)
        throws BusinessException {
        try {
            if (StringUtils.isNotBlank(roleId)) {
                // 删除范围成员表
                String hql1 =
                    "from ScopeMember sm where sm.roleMemberScope.role.roleId in ("
                        + roleId+" )";
                List<ScopeMember> sms = baseDao.queryEntitys(hql1);
                if (!"".equals(sms) || sms != null) {
                    for (ScopeMember sm : sms) {
                        String hql2 =
                            "delete from ScopeMember sm where sm.id ="
                                + sm.getId();
                        baseDao.executeHql(hql2);
                    }
                }
                
                // 删除角色成员表
                String hql3 =
                    "from RoleMemberScope rms where rms.role.roleId in (" + roleId+")";
                List<RoleMemberScope> rmss = baseDao.queryEntitys(hql3);
                if ("".equals(rmss) || rmss != null) {
                    for (RoleMemberScope sm : rmss) {
                        String hql =
                            "delete from RoleMemberScope rms where rms.id ="
                                + sm.getId();
                        baseDao.executeHql(hql);
                    }
                }
                
                // 删除角色资源表
                String hql4 =
                    "from RoleResource rr where rr.role.roleId in (" + roleId+")";
                List<RoleResource> rrs = baseDao.queryEntitys(hql4);
                if ("".equals(rrs) || rrs != null) {
                    for (RoleResource rr : rrs) {
                        String hql5 =
                            "delete from RoleResource rr where rr.roleResourceId="
                                + rr.getRoleResourceId();
                        baseDao.executeHql(hql5);
                    }
                }
                baseDao.delete(roleId, "Role", "isDelete", "roleId", "1");
            }
            return "{success:true,msg:'删除角色成功！'}";
        }
        catch (Exception e) {
            return "{success:false,msg:'删除角色失败！'}";
        }
    }
    
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
    @Override
    public PagerVo<Role> getRoleList(Role role, int start, int limit)
        throws BusinessException {
        StringBuffer hql = new StringBuffer("from Role r where 1=1 ");
        Map<String, Object> values = new HashMap<String, Object>();
        if (role != null) {
            if (StringUtils.isNotBlank(role.getRoleName())) {
                hql.append(" and r.roleName like '%" + role.getRoleName()
                    + "%'");
            }
        }
        hql.append(" order by r.roleId ");
        List<Role> userList =
            (List<Role>)baseDao.queryEntitysByPage(start,
                limit,
                hql.toString(),
                values);
        int count = baseDao.queryTotalCount(hql.toString(), values);
        return new PagerVo<Role>(start, limit, count, userList);
    }
    
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
    @Override
    public int getRoleNumByName(String roleName, String roleType)
        throws BusinessException {
    	if(StringUtils.isBlank(roleName)){
    		return 1;
    	}
        String sql =
            "SELECT COUNT(*) FROM Role a WHERE a.isDelete = "
                + Constant.STATUS_NOT_DELETE + " AND a.roleType.pkDictionaryId = "
                + roleType + " and a.roleName='" + roleName.replace("'", "''")
                + "'";
        return baseDao.getTotalCount(sql, null); 
    }
    
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Log(operationType = "添加", operationName = "角色资源数据信息")
    @Override
    public void addRoleResources(String resourceIds, String roleId)
        throws BusinessException {
        try {
            for (String resourceId : resourceIds.split(",")) {
                if ("".equals(resourceId)) {
                    continue;
                }
                RoleResource roleResource = new RoleResource();
                Resource resource =
                    (Resource)this.baseDao.queryEntityById(Resource.class,
                        NumberUtils.toInt(resourceId));
                Role role =
                    (Role)this.baseDao.queryEntityById(Role.class,
                        NumberUtils.toInt(roleId));
                roleResource.setResource(resource);
                roleResource.setRole(role);
                
                this.baseDao.addEntity(roleResource);
            }
        }
        catch (Exception e) {
            
        }
    }
    
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Log(operationType = "删除", operationName = "角色资源关系数据信息")
    @Override
    public void deleteRoleResources(String resourceIds, String roleId)
        throws BusinessException {
        for (String resourceId : resourceIds.split(",")) {
            if ("".equals(resourceId)) {
                continue;
            }
            String deleteSql =
                "DELETE FROM T_ROLE_RESOURCE WHERE resource_id=" + resourceId
                    + " and  role_id =" + roleId;
            this.baseDao.executeNativeSQL(deleteSql);
        }
    }
    
    /**
     * 获取角色列表
     * 
     * @Title getRoleVoList
     * @author lizhengc
     * @date 2014年9月3日
     * @param paramMap 条件
     * @return ListVo<RoleVo> 角色vo集合
     * @throws BusinessException
     */
    @Override
    public ListVo<RoleVo> getRoleVoList(Map<String, String> paramMap)
        throws BusinessException {
        
        ListVo<RoleVo> roleVoListVo = new ListVo<RoleVo>();
        List<RoleVo> roleVoList = new ArrayList<RoleVo>();
        
        StringBuffer hqlList = new StringBuffer();
        StringBuffer hqlCount = new StringBuffer();
        
        Map<Object, Object> map = new HashMap<Object, Object>();
        String start = (String)paramMap.get("start");
        String limit = (String)paramMap.get("limit");
        String roleName = (String)paramMap.get("roleName");
        String roleType = (String)paramMap.get("roleType");
        
        hqlList.append("select a from Role a where a.isDelete = "
            + Constant.STATUS_NOT_DELETE);
        hqlCount.append("select count(d.roleId) from Role d where d.isDelete = "
            + Constant.STATUS_NOT_DELETE);
        
        if (!StringUtils.isBlank(roleName)) {
            hqlList.append(" and a.roleName like :roleName ");
            hqlCount.append(" and  d.roleName like :roleName ");
            map.put("roleName", "%" + roleName + "%");
        }
        if (!StringUtils.isBlank(roleType) && !"0".equals(roleType)) {
            hqlList.append(" and a.roleType.pkDictionaryId = " + roleType);
            hqlCount.append(" and  d.roleType.pkDictionaryId = " + roleType);
        }
        hqlList.append(" order by a.roleId desc");
        
        List<Role> roleList =
            (List<Role>)this.baseDao.findPageByQuery(NumberUtils.toInt(start),
                NumberUtils.toInt(limit),
                hqlList.toString(),
                map);
        int totalSize = this.baseDao.getTotalCount(hqlCount.toString(), map);
        
        for (int i = 0; i < roleList.size(); i++) {
            RoleVo roleVo = new RoleVo(roleList.get(i));
            
            List<RoleResource> roleResourceList =
                this.getRoleResourceList(roleList.get(i).getRoleId());
            List<Integer> roleResourceIdList = new ArrayList<Integer>();
            for (RoleResource roleResource : roleResourceList) {
                int roleResourceId = roleResource.getResource().getResourceId();
                roleResourceIdList.add(roleResourceId);
            }
            roleVo.setRoleResourceIds(roleResourceIdList);
            
            roleVoList.add(roleVo);
        }
        roleVoListVo.setList(roleVoList);
        roleVoListVo.setTotalSize(totalSize);
        
        return roleVoListVo;
    }
    
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
    @Override
    public int validateAddRoleCode(String roleCode, String roleType)
        throws BusinessException {
        String sql =
        		 "SELECT COUNT(*) FROM Role a WHERE a.isDelete = "
        				 + Constant.STATUS_NOT_DELETE + " AND a.roleType.pkDictionaryId = "
                + roleType + " AND  a.roleCode='" + roleCode.replace("'", "''")
                + "'";
        return this.baseDao.getTotalCount(sql, null);
        
    }
    
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
    @Override
    public int validateUpdateRoleName(String roleName, String oldRoleId,
        String roleType)
        throws BusinessException {
        Role oldRole =
            (Role)this.baseDao.queryEntityById(Role.class,
                NumberUtils.toInt(oldRoleId));
       
        String sql =
                "SELECT COUNT(*) FROM Role a WHERE a.isDelete = "
                    + Constant.STATUS_NOT_DELETE + " AND a.roleType.pkDictionaryId = "
                    + roleType + " and a.roleName='" + roleName.replace("'", "''")
                     + "' and a.roleName != '" + oldRole.getRoleName() + "'";
            return this.baseDao.getTotalCount(sql, null);
    }
    
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Log(operationType = "修改", operationName = "角色信息")
    @Override
    public void updateRole(Role role)
        throws BusinessException {
        baseDao.updateEntity(role);
    }
    
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
    @Override
    public int validateUpdateRoleCode(String roleCode, String oldRoleId,
        String roleType)
        throws BusinessException {
        Role oldRole =
            (Role)this.baseDao.queryEntityById(Role.class,
                NumberUtils.toInt(oldRoleId));
        String sql =
        		"SELECT COUNT(*) FROM Role a WHERE a.isDelete = "
          				 + Constant.STATUS_NOT_DELETE + " AND a.roleType.pkDictionaryId = "
                  + roleType + " AND  a.roleCode='" + roleCode.replace("'", "''")
                + "' and ROLE_CODE != '" + oldRole.getRoleCode() + "'";
        return this.baseDao.getTotalCount(sql, null);
        
    }
    
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
    @Override
    public List<RoleResource> getRoleResourceList(int roleId)
        throws BusinessException {
        String hql =
            "from RoleResource rr where rr.resource.status = "
                + Constant.STATUS_NOT_DELETE + " and rr.role.isDelete = "
                + Constant.STATUS_NOT_DELETE + " and rr.role = " + roleId;
        
        List<RoleResource> RoleResourceList =
            (List<RoleResource>)this.baseDao.queryEntitys(hql);
        
        return RoleResourceList;
    }
    
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
    @Override
    public String importRoleResource(String attachUrl)
        throws BusinessException {
        boolean flag = false;
        String sameIds = "";
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
                StringBuffer sqlParam = new StringBuffer("");
                for (int i = 1; i < col; i++) {
                    int j = i - 1;
                    sql[j] = new StringBuffer("");
                    sql[j].append("INSERT INTO T_ROLE_RESOURCE(");
                    
                    /**
                     * 角色名
                     */
                    if (null != content[i][0]
                        && !StringUtils.isBlank(content[i][0].trim())
                        && !StringUtils.isEmpty(content[i][0].trim())
                        && !StringUtils.equals("null", content[i][0].trim())) {
                        String roleName = content[i][0].trim();
                        String hql =
                            "FROM com.dqgb.user.entity.Role r where r.roleName = ?";
                        List<Role> list =
                            this.baseDao.query(hql, new String[] {roleName});
                        if (null != list && list.size() > 0) {
                            Role role = list.get(0);
                            sql[j].append("ROLE_ID");
                            sqlParam.append(role.getRoleId());
                        }
                        else {
                            int o = i + 1;
                            message = "第【" + o + "】行,【1】列发生错误,原因:指定的角色名未找到！";
                            break;
                        }
                    }
                    else {
                        int o = i + 1;
                        message = "第【" + o + "】行,【1】列发生错误,原因:角色名不能为空！";
                        break;
                    }
                    /**
                     * 资源名
                     */
                    if (null != content[i][1]
                        && !StringUtils.isBlank(content[i][1].trim())
                        && !StringUtils.isEmpty(content[i][1].trim())
                        && !StringUtils.equals("null", content[i][1].trim())) {
                        String resourceName = content[i][1].trim();
                        String roleName = content[i][0].trim();
                        String hql =
                            "FROM com.dqgb.user.entity.Resource r where r.resourceName = ?";
                        List<Resource> list =
                            this.baseDao.query(hql, new String[] {resourceName});
                        if (null != list && list.size() > 0) {
                            String hql2 =
                                "FROM com.dqgb.user.entity.RoleResource rr WHERE rr.role.roleName=? AND rr.resource.resourceName=?";
                            List<RoleResource> list2 =
                                this.baseDao.query(hql2, new String[] {
                                    roleName, resourceName});
                            if (null != list2 && list2.size() > 0) {
                                int o = i + 1;
                                message =
                                    "第【"
                                        + sameIds
                                        + o
                                        + "】行发生错误,原因:指定的角色已具有指定的资源<br><br>该条记录插入将会被忽略,其他行记录将继续插入！";
                                flag = true;
                                sqlParam.delete(0, sqlParam.length());
                                sameIds += o + ",";
                                continue;
                            }
                            else {
                                Resource resource = list.get(0);
                                sql[j].append(",RESOURCE_ID");
                                sqlParam.append("," + resource.getResourceId()
                                    + "");
                            }
                        }
                        else {
                            int o = i + 1;
                            message = "第【" + o + "】行,【2】列发生错误,原因:指定的资源名未找到！";
                            break;
                        }
                    }
                    else {
                        int o = i + 1;
                        message = "第【" + o + "】行,【2】列发生错误,原因:资源名不能为空！";
                        break;
                    }
                    
                    sql[j].append(",ROLE_RESOURCE_ID");
                    int id =
                        this.baseDao.getIdFromSequence("SEQ_ROLE_RESOURCE");
                    sqlParam.append("," + id + "");
                    
                    sql[j].append(") values(");
                    sqlParam.append(")");
                    sql[j].append(sqlParam.toString());
                    sqlArray.add(sql[j].toString());
                    sqlParam.delete(0, sqlParam.length());
                }
                if (message.equals("importSuccess") || flag == true) {
                    if (sqlArray.size() > 0) {
                        String[] sqlInsert = new String[sqlArray.size()];
                        for (int i = 0; i < sqlArray.size(); i++) {
                            sqlInsert[i] = (String)sqlArray.get(i);
                        }
                        if (sqlInsert.length > 0) {
                            baseDao.batchUpdate(sqlInsert);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            
        }
        return message;
    }
    
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
    /*
     * @Override public List<Role> getRoleListByUser(User user) throws BusinessException {
     * List<Role> roleList = new ArrayList<Role>(); String hql =
     * "from UserRole ur where ur.user.id = '" + user.getUserId() + "'"; List<UserRole> userRoleList
     * = baseDao.query(hql); for (UserRole ur : userRoleList) { if (ur.getRole() != null) {
     * roleList.add(ur.getRole()); } } return roleList; }
     */
    
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
    @Override
    public ListVo<Role> getRoleListByRoleName(String roleName, int pageIndex,
        int pageSize)
        throws BusinessException {
        String hql = "from Role r where 1=1";
        if (!"".equals(roleName) && roleName != null) {
            hql += "  and r.roleName like '%" + roleName + "%'";
        }
        List<Role> roleList = baseDao.query(hql);
        int count =
            baseDao.getTotalCount("select count(1) " + hql, new HashMap());
        ListVo<Role> roleListVo = new ListVo<Role>();
        roleListVo.setList(roleList);
        roleListVo.setTotalSize(count);
        return roleListVo;
    }
    
    /**
     * 本地角色资源关系信息（同步时）
     * 
     * @Title synchronizeRoleInfo
     * @author lizhengc
     * @date 2014年9月3日
     * @param null
     * @return null
     * @throws BusinessException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Log(operationType = "删除", operationName = "本地角色资源关系信息（同步时）")
    private void deleteLocalRoleResRelation()
        throws BusinessException {
        try {
            String hql = "from RoleResource rr";
            List<Resource> rrList = baseDao.queryEntitys(hql);
            if (rrList != null && rrList.size() > 0) {
                baseDao.deleteEntities(rrList);
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        
    }
    
    /**
     * 本地角色信息（同步时）
     * 
     * @Title deleteLocalRoleLogic
     * @author wanglc
     * @Description: 逻辑删除架构角色信息，状态置为2（集成平台已删除）
     * @date 2013-12-17
     * @param roles 角色集合
     * @return null
     * @throws BusinessException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Log(operationType = "删除", operationName = "本地角色信息（同步时）")
    private void deleteLocalRoleLogic(List<Role> roles)
        throws BusinessException {
        try {
            for (Role r : roles) {
                if (r.getIsDeleteAble() != 1) {
                    baseDao.delete(r.getRoleId() + "",
                        "Role",
                        "isDelete",
                        "roleId",
                        "2");
                }
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
    /**
     * 取本地所有角色集合
     * 
     * @Title getRoleList
     * @author lizhengc
     * @date 2013-12-17
     * @param null
     * @return List<Role> 角色集合
     * @throws BusinessException
     */
    public List<Role> getRoleList()
        throws BusinessException {
        String hql =
            "from Role r where r.isDelete = " + Constant.STATUS_NOT_DELETE;
        List<Role> roleList = baseDao.queryEntitys(hql);
        return roleList;
    }
    
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
    public Role getLocalRoleByName(String roleName)
        throws BusinessException {
        String hql =
            "from Role r where r.roleName = '" + roleName
                + "' and r.isDelete = " + Constant.STATUS_NOT_DELETE;
        List<Role> roleList = baseDao.queryEntitys(hql);
        if (roleList != null && roleList.size() > 0) {
            return roleList.get(0);
        }
        else {
            return null;
        }
    }
    
    /**
     * 获取角色列表
     * 
     * @Title roleList
     * @author wanglc
     * @date 2013-12-17
     * @param baseDao 数据库操作基础方法
     * @return List<Role> 角色集合
     * @throws BusinessException
     */
    public static List<Role> roleList(IBaseDao baseDao)
        throws BusinessException {
        String hql =
            "from Role r where r.isDelete = " + Constant.STATUS_NOT_DELETE;
        List<Role> roleList = baseDao.queryEntitys(hql);
        return roleList;
    }
    
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
    @Override
    public int roleIsExist(String roleId, String code)
        throws BusinessException {
        int size = 0;
        int status = 0;
        try {
            if (!code.equals("update")) {
                status = Constant.DISABLE;
            }
            String sql =
                "select count(*) from t_role a  where a.role_id in (" + roleId
                    + ") and a.isdelete =  " + status;
            size = this.baseDao.queryCountSQL(sql);
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return size;
    }
    
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
    @Override
    public String addRoleMember(Map<String, String> param)
        throws BusinessException {
        String roleId = param.get("roleId");
        String groupIds = param.get("groupIds");
        try {
            // 判断参数是否为空
            if (StringUtils.isNotBlank(roleId)
                && StringUtils.isNotBlank(groupIds)) {
                String[] gIds = groupIds.split(",");
                // 循环遍历数组，获取用户并保存
                for (String gId : gIds) {
                    RoleMemberScope rms = new RoleMemberScope();
                    Role role = new Role();
                    role.setRoleId(NumberUtils.toInt(roleId));
                    rms.setRole(role);
                    Group group = new Group();
                    group.setId(NumberUtils.toInt(gId));
                    rms.setGroup(group);
                    baseDao.save(rms);
                }
                return "success";
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }
    
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
    @Override
    public String addRoleOrg(Map<String, String> param)
        throws BusinessException {
        // 角色成员id
        String roleMemeberIds = param.get("roleMemeberIds");
        // 组织id
        String orgIds = param.get("orgIds");
        try {
            // 判断参数是否为空
            if (StringUtils.isNotBlank(roleMemeberIds)
                && StringUtils.isNotBlank(orgIds)) {
                String[] oIds = orgIds.split(",");
                String[] rmIds = roleMemeberIds.split(",");
                // 循环遍历数组，获取用户并保存
                for (String oId : oIds) {
                    for (String rmId : rmIds) {
                        ScopeMember sm = new ScopeMember();
                        RoleMemberScope rms = new RoleMemberScope();
                        rms.setId(NumberUtils.toInt(rmId));
                        sm.setRoleMemberScope(rms);
                        Organization org = new Organization();
                        org.setOrgId(NumberUtils.toInt(oId));
                        sm.setOrg(org);
                        baseDao.save(sm);
                    }
                }
                return "success";
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }
    
    /**
     * 添加角色用户范围
     * 
     * @Title addRoleOrg
     * @author lizhengc
     * @date 2014年09月16日
     * @param param 条件参数
     * @return String
     * @throws BusinessException
     */
    @Override
    public String addRoleUserScope(Map<String, String> param)
    		throws BusinessException {
    	// 角色成员id
    	String rmId = param.get("roleMemeberId");
    	// id
    	String userIds = param.get("userIds");
    	try {
    		// 判断参数是否为空
    		if (StringUtils.isNotBlank(rmId)
    				&& StringUtils.isNotBlank(userIds)) {
    			String[] oIds = userIds.split(",");
    			// 循环遍历数组，获取用户并保存
    			ScopeMember sm;
    			for (String oId : oIds) {
    					sm = new ScopeMember();
    					RoleMemberScope rms = new RoleMemberScope();
    					rms.setId(NumberUtils.toInt(rmId));
    					sm.setRoleMemberScope(rms);
    					User org = new User();
    					org.setUserId(NumberUtils.toInt(oId));
    					sm.setUser(org);
    					 int count = baseDao.getTotalCount("select count(*) from ScopeMember where roleMemberScope.id="+rmId+" and user.userId="+oId,
    	                                new HashMap<String, Object>());
    					if(count == 0){
    						baseDao.save(sm);
    					}
    			}
    			return "success";
    		}
    	}
    	catch (Exception e) {
    		throw new BusinessException(e.getMessage());
    	}
    	return "";
    }
    
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
    @Override
    public ListVo<GroupVo> getRoleMember(Map<String, String> param)
        throws BusinessException {
        ListVo<GroupVo> vos = new ListVo<GroupVo>();
        List<GroupVo> wr = null;
        // 获取参数
        String start = (String)param.get("start");
        String limit = (String)param.get("limit");
        String roleId = (String)param.get("roleId");
        String groupName = param.get("groupName");
        StringBuffer hql = new StringBuffer();
        StringBuffer hqlCount = new StringBuffer();
        hql.append(" from RoleMemberScope rms where 1=1 and rms.group.id is not null ");
        hqlCount.append("select count(*) from RoleMemberScope rms where 1=1  and rms.group.id is not null ");
        if (StringUtils.isNotBlank(roleId)) {
            hql.append(" and rms.role.roleId = " + roleId + " ");
            hqlCount.append(" and rms.role.roleId = " + roleId + " ");
        }
        else {
            hql.append(" and rms.role.roleId is null ");
            hqlCount.append(" and rms.role.roleId is null ");
        }
        
        if (StringUtils.isNotBlank(groupName)) {
            hql.append(" and rms.group.groupName like '%" + groupName + "%' ");
            hqlCount.append(" and rms.group.groupName like '%" + groupName
                + "%' ");
        }
        
        hql.append(" order by rms.id");
        
        List<RoleMemberScope> rms =
            (List<RoleMemberScope>)baseDao.queryEntitysByPage(NumberUtils.toInt(start),
                NumberUtils.toInt(limit),
                hql.toString(),
                new HashMap<String, Object>());
        if (rms != null && rms.size() > 0) {
            wr = new ArrayList<GroupVo>();
            GroupVo groupVo = null;
            for (RoleMemberScope roleMemberScope : rms) {
                groupVo = new GroupVo();
                groupVo.setId(roleMemberScope.getGroup().getId());
                groupVo.setRoleMemberId(roleMemberScope.getId());
                groupVo.setGroupName(roleMemberScope.getGroup().getGroupName());
                groupVo.setRemark(roleMemberScope.getGroup().getRemark());
                wr.add(groupVo);
            }
        }
        int count =
            baseDao.getTotalCount(hqlCount.toString(),
                new HashMap<String, Object>());
        vos.setList(wr);
        vos.setTotalSize(count);
        return vos;
    }
    
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
    @Override
    public RoleMemberScope checkRGIsExist(String id, String roleId)
        throws BusinessException {
        String hql =
            "from RoleMemberScope rms where rms.role.roleId = " + roleId
                + " and rms.group.id =" + id;
        return (RoleMemberScope)baseDao.queryEntitys(hql).get(0);
    }
    
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
    @Override
    public RoleMemberScope checkRUIsExist(String id, String roleId)
        throws BusinessException {
        String hql =
            "from RoleMemberScope rms where rms.role.roleId = " + roleId
                + " and rms.user.userId =" + id;
        return (RoleMemberScope)baseDao.queryEntitys(hql).get(0);
    }
    
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
    @Override
    public void delRoleGroup(Map<String, String> param)
        throws BusinessException {
        // 用户id
        String ids = param.get("ids");
        // 角色id
        String roleId = param.get("roleId");
        // 角色成员范围id
        String roleMemberIds = param.get("roleMemberIds");
        
        String hql2 =
            "from ScopeMember sm where sm.roleMemberScope.id in ("
                + roleMemberIds + ") ";
        List<ScopeMember> scopeMembers = baseDao.queryEntitys(hql2);
        if (scopeMembers != null) {
            for (ScopeMember scopeMember : scopeMembers) {
                String hql3 =
                    "delete from ScopeMember sm where sm.id = "
                        + scopeMember.getId() + "";
                baseDao.executeHql(hql3);
            }
        }
        
        String hql =
            " delete from RoleMemberScope rms where rms.role.roleId = "
                + roleId + " and  rms.group.id in (" + ids + ")";
        baseDao.executeHql(hql);
    }
    
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
    @Override
    public void delRoleUser(Map<String, String> param)
        throws BusinessException {
        // 用户id
        String ids = param.get("ids");
        // 角色id
        String roleId = param.get("roleId");
        // 角色成员范围id
        String roleMemberIds = param.get("roleMemberIds");
        
        String hql2 =
            "from ScopeMember sm where sm.roleMemberScope.id in ("
                + roleMemberIds + ") ";
        List<ScopeMember> scopeMembers = baseDao.queryEntitys(hql2);
        if (scopeMembers != null) {
            for (ScopeMember scopeMember : scopeMembers) {
                String hql3 =
                    "delete from ScopeMember sm where sm.id = "
                        + scopeMember.getId() + "";
                baseDao.executeHql(hql3);
            }
        }
        
        String hql =
            " delete from RoleMemberScope rms where rms.role.roleId = "
                + roleId + " and  rms.user.userId in (" + ids + ")";
        baseDao.executeHql(hql);
    }
    
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
    @Override
    public ListVo<UserVo> getRoleMemberByUser(Map<String, String> param)
        throws BusinessException {
        ListVo<UserVo> vos = new ListVo<UserVo>();
        List<UserVo> wr = null;
        // 获取参数
        String start = (String)param.get("start");
        String limit = (String)param.get("limit");
        String roleId = (String)param.get("roleId");
        String realName = param.get("realname");
        StringBuffer hql = new StringBuffer();
        StringBuffer hqlCount = new StringBuffer();
        
        hql.append(" from RoleMemberScope rms where 1=1 and rms.user.userId is not null ");
        hqlCount.append("select count(*) from RoleMemberScope rms where 1=1  and rms.user.userId is not null ");
        
        if (StringUtils.isNotBlank(roleId)) {
            hql.append(" and rms.role.roleId = " + roleId + " ");
            hqlCount.append(" and rms.role.roleId = " + roleId + " ");
        }
        else {
            hql.append(" and rms.role.roleId is null ");
            hqlCount.append(" and rms.role.roleId is null ");
        }
        
        if (StringUtils.isNotBlank(realName)) {
            hql.append(" and rms.user.realname like '%" + realName + "%' ");
            hqlCount.append(" and rms.user.realname like '%" + realName + "%' ");
        }
        
        hql.append(" and rms.user.status = 0 order by rms.user.disOrder");
        hqlCount.append(" and rms.user.status = 0 ");
        
        List<RoleMemberScope> rms =
            (List<RoleMemberScope>)baseDao.queryEntitysByPage(NumberUtils.toInt(start),
                NumberUtils.toInt(limit),
                hql.toString(),
                new HashMap<String, Object>());
        if (rms != null && rms.size() > 0) {
            wr = new ArrayList<UserVo>();
            UserVo userVo = null;
            for (RoleMemberScope roleMemberScope : rms) {
                userVo = new UserVo();
                userVo.setRoleMemberId(roleMemberScope.getId());
                userVo.setUserId(roleMemberScope.getUser().getUserId());
                userVo.setUsername(roleMemberScope.getUser().getUsername());
                
                Iterator<OrgUser> it =
                    roleMemberScope.getUser().getOrgUsers().iterator();
                String orgIds = "";
                while (it.hasNext()) {
                    OrgUser ou = it.next();
                    if (ou.getIsDelete() == Constant.STATUS_NOT_DELETE) {
                        orgIds =
                            orgIds + "," + ou.getOrganization().getOrgId();
                    }
                }
                
                List<Organization> orgLst =
                    orgService.getOrgByIds(orgIds.substring(1));
                
                String orgNames = "";
                if(!CollectionUtils.isEmpty(orgLst)){
                    for (Organization org : orgLst) {
                        orgNames = orgNames + "," + org.getOrgName();
                    }
                    
                    userVo.setOrgName(orgNames.substring(1));
                }
                
                userVo.setRealname(roleMemberScope.getUser().getRealname());
                wr.add(userVo);
            }
        }
        int count =
            baseDao.getTotalCount(hqlCount.toString(),
                new HashMap<String, Object>());
        vos.setList(wr);
        vos.setTotalSize(count);
        return vos;
    }
    
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
    @Override
    public String addRoleUser(Map<String, String> param)
        throws BusinessException {
        String roleId = param.get("roleId");
        String userIds = param.get("userIds");
        try {
            // 判断参数是否为空
            if (StringUtils.isNotBlank(roleId)
                && StringUtils.isNotBlank(userIds)) {
                String[] uIds = userIds.split(",");
                // 循环遍历数组，获取用户并保存
                for (String uId : uIds) {
                    RoleMemberScope rms = new RoleMemberScope();
                    Role role = new Role();
                    role.setRoleId(NumberUtils.toInt(roleId));
                    rms.setRole(role);
                    User user = new User();
                    user.setUserId(NumberUtils.toInt(uId));
                    rms.setUser(user);
                    
                    int count =
                            baseDao.getTotalCount("select count(*) from RoleMemberScope where role.roleId="+roleId+" and user.userId="+uId,
                                new HashMap<String, Object>());
                    if(count ==0){
                    	baseDao.save(rms);
                    }
                }
                return "success";
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }
    
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
    @Override
    public List<ScopeMember> getOrgSM(int roleMemberId)
        throws BusinessException {
        String hql =
            "from ScopeMember sm where sm.org is not null and sm.roleMemberScope.id = " + roleMemberId;
        List<ScopeMember> scopeMember =
            (List<ScopeMember>)this.baseDao.queryEntitys(hql);
        
        return scopeMember;
    }
    
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
    @Override
    public ListVo<UserVo> getScopeByMenberId(Map<String, String> param) throws BusinessException {
    	ListVo<UserVo> vos = new ListVo<UserVo>();
    	 String start = (String)param.get("start");
         String limit = (String)param.get("limit");
         String roleMemberId = (String)param.get("roleMemberIds");
        List<UserVo> wr = null;
        // 获取参数
        StringBuffer hql = new StringBuffer();
        StringBuffer hqlCount = new StringBuffer();
        hql.append(" select u from ScopeMember sm,User u where sm.user.userId=u.userId and sm.roleMemberScope.id = " + roleMemberId);
        hqlCount.append(" select count(*) from ScopeMember sm,User u where sm.user.userId=u.userId and sm.roleMemberScope.id = " + roleMemberId);
        
        hql.append(" and u.status = 0 order by u.disOrder");
        hqlCount.append(" and u.status = 0 ");
        
        List<User> rms =
            (List<User>)baseDao.queryEntitysByPage(NumberUtils.toInt(start),
                NumberUtils.toInt(limit),
                hql.toString(),
                new HashMap<String, Object>());
        if (rms != null && rms.size() > 0) {
            wr = new ArrayList<UserVo>();
            UserVo userVo = null;
            for (User user : rms) {
                userVo = new UserVo();
                userVo.setUserId(user.getUserId());
                OrgUser orgUser = null;
                String orgName = "";
                Iterator  it = user.getOrgUsers().iterator();
                while(it.hasNext()){
                	orgUser = (OrgUser) it.next();
                	orgName+=orgUser.getOrganization().getOrgName()+" ";
                }
                userVo.setOrgName(orgName);
                userVo.setUsername(user.getUsername());
                userVo.setRealname(user.getRealname());
                wr.add(userVo);
            }
        }
        int count =
            baseDao.getTotalCount(hqlCount.toString(),
                new HashMap<String, Object>());
        vos.setList(wr);
        vos.setTotalSize(count);
        return vos;
    }
    
    /**
     * @Title getFirstLevelOrg
     * @author wanglc
     * @Description: 查询当前组织的一级菜单
     * @date 2013-09-18
     * @return List<Organization>
     * @throws BusinessException
     */
    @Override
    public List<Organization> getFirstLevelOrg()
        throws BusinessException {
        String hql =
            "from Organization o where o.organization is null and o.status = "
                + Constant.STATUS_NOT_DELETE + " order by o.disOrder";
        return baseDao.queryEntitys(hql);
    }
    
    /**
     * @Title getAllSonOrgByParentId
     * @author lizhengc
     * @Description: 根据父节点取所有子节点
     * @date 2013-09-18
     * @param parentId 父节点ID
     * @return List<Organization>
     * @throws BusinessException
     */
    @Override
    public List<Organization> getAllSonOrgByParentId(String parentId)
        throws BusinessException {
        String hql =
            "from Organization o where o.status = "
                + Constant.STATUS_NOT_DELETE + " and o.organization.status = "
                + Constant.STATUS_NOT_DELETE + " and o.organization.orgId = "
                + NumberUtils.toInt(parentId) + " order by o.disOrder Asc";
        List<Organization> result = baseDao.queryEntitys(hql);
        return result;
    }
    
    /**
     * 删除角色组织权限
     * 
     * @Title delRoleUser
     * @author lizhengc
     * @date 2014年9月18日
     * @param ids 用户id
     * @param roleId 角色id
     * @throws BusinessException
     */
    @Override
    public void delRoleOrg(String roleMemeberIds, String orgIds)
        throws BusinessException {
        String hql =
            " delete from ScopeMember sm where sm.roleMemberScope.id = "
                + roleMemeberIds + " and  sm.org.orgId in (" + orgIds + ")";
        baseDao.executeHql(hql);
    }
    
    /**
     * 删除角色组织权限
     * 
     * @Title delRoleUser
     * @author lizhengc
     * @date 2014年9月18日
     * @param ids 用户id
     * @param roleId 角色id
     * @throws BusinessException
     */
    @Override
    public void delScopeUser(String roleMemeberId, String userIds)
    		throws BusinessException {
    	String hql =
    			" delete from ScopeMember sm where sm.roleMemberScope.id = "
    					+ roleMemeberId + " and  sm.user.userId in (" + userIds + ")";
    	baseDao.executeHql(hql);
    }
    
    /**
     * 通过成员的ID获取对应的角色ID
     * 
     * @Title getRoleMemberScopeByMemberId
     * @author liukang-wb
     * @date 2014年9月15日
     * @param memberId
     * @return
     */
    @Override
    public List<RoleMemberScope> getRoleMemberScopeByMemberId(String memberIds,
        String type)
        throws BusinessException {
        List<RoleMemberScope> rms_list = new ArrayList<RoleMemberScope>();
        // userId不为空
        if (!"".equals(memberIds) && memberIds.length() != 0) {
            String hql = "from RoleMemberScope rms where 1=1";
            if (type.equals(Constant.USER)) {
                hql += "and rms.user.userId in (" + memberIds + ")";
            }
            else if (type.equals(Constant.GROUP)) {
                hql += "and rms.group.id in (" + memberIds + ")";
            }
            rms_list = baseDao.queryEntitys(hql);
        }
        return rms_list;
    }
    
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
    @Override
    public List<ScopeMember> getScpMemberByRMSid(String rmsids, String type) {
        List<ScopeMember> sm_list = new ArrayList<ScopeMember>();
        String hql = "from ScopeMember sm where 1=1 and sm.org is not null ";
        if (!"".equals(rmsids) && rmsids.length() != 0) {
            if (type.equals(Constant.ORG)) {
                hql += " and sm.roleMemberScope.id in (" + rmsids + ")";
            }
        }
        sm_list = baseDao.queryEntitys(hql);
        return sm_list;
    }

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
    @Override
    public List<ScopeMember> getUserSm(int roleMemberId)
        throws BusinessException {
        String hql =
            "from ScopeMember sm where sm.user is not null and sm.roleMemberScope.id = " + roleMemberId;
        List<ScopeMember> scopeMember =
            (List<ScopeMember>)this.baseDao.queryEntitys(hql);
        
        return scopeMember;
    }

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
    @Override
    public List<RoleMemberScope> getRoleMemberScopeByRoleId(int roleId, String flag)
        throws BusinessException {
        String hql =
            "from RoleMemberScope rms where rms.role.roleId = " + roleId;
        
        if ("userIds".equals(flag)) {
            hql += " and rms.user is not null";
        }
        else {
            hql += " and rms.group is not null";
        }
        
        List<RoleMemberScope> members =
            (List<RoleMemberScope>)this.baseDao.queryEntitys(hql);
        
        return members;
    }
}
