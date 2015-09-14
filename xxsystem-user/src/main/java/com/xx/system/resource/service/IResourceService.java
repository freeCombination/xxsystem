package com.xx.system.resource.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.PagerVo;
import com.xx.system.resource.entity.Resource;
import com.xx.system.resource.vo.ResourceVo;
import com.xx.system.role.entity.Role;

/**
 * 资源接口定义
 * 
 * @version V1.20,2013-11-25 下午2:58:45
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public interface IResourceService {
    /**
     * 添加系统资源
     * 
     * @Title addResource
     * @author wanglc
     * @date 2013-11-25
     * @param resource 资源对象
     */
    public void addResource(Resource resource)
        throws BusinessException;
    
    /**
     * 修改系统资源
     * 
     * @Title updateResource
     * @author wanglc
     * @date 2013-11-25
     * @param resource 资源对象
     */
    public String updateResource(Resource resource)
        throws BusinessException;
    
    /**
     * 根据ID获得资源
     * 
     * @Title getResourceById
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param id 资源ID
     * @return Resource
     */
    public Resource getResourceById(int id)
        throws BusinessException;
    
    /**
     * 根据资源编码查询对象
     * 
     * @Title getResourceByCode
     * @author wanglc
     * @date 2013-11-25
     * @param code 资源编码
     * @return Resource
     */
    public Resource getResourceByCode(String code)
        throws BusinessException;
    
    /**
     * 取得所有资源的列表
     * 
     * @Title getAllResource
     * @author wanglc
     * @date 2013-11-25
     * @return List<Resource> 资源对象集合
     */
    public List<Resource> getAllResource()
        throws BusinessException;
    
    /**
     * 通过参数获取resource
     * 
     * @Title getResourceList
     * @author wanglc
     * @date 2013-11-25
     * @param map 参数Map
     * @return ListVo<ResourceVo> 资源对象集合
     */
    public ListVo<ResourceVo> getResourceList(Map<String, String> map)
        throws BusinessException;
    
    /**
     * 批量删除系统资源
     * 
     * @Title deleteResource
     * @author wanglc
     * @date 2013-11-25
     * @param resourceIds 资源ID，以逗号分隔的字符串
     */
    public String deleteResource(String resourceIds)
        throws BusinessException;
    
    /**
     * 修改资源名称时校验名称是否重复
     * 
     * @Title validateUpdateResourceName
     * @author wanglc
     * @date 2013-11-25
     * @param resourceName 资源名称
     * @param oldResourceId 原有资源ID
     * @return boolean
     */
    public boolean validateUpdateResourceName(String resourceName,
        String oldResourceId)
        throws BusinessException;
    
    /**
     * 查询资源分页，可根据名字模糊查询，可根据类型精确查询
     * 
     * @Title getResourceList
     * @author wanglc
     * @date 2013-11-25
     * @param start 查询开始位置
     * @param limit 每页限制条数
     * @param resource 资源对象
     * @return PagerVo<Resource> 资源对象集合
     */
    public PagerVo<Resource> getResourceList(int start, int limit,
        Resource resource)
        throws BusinessException;
    
    /**
     * 根据角色对象查询该角色拥有的资源列表
     * 
     * @Title getResourceByRole
     * @author wanglc
     * @date 2013-11-25
     * @param role 角色对象
     * @return List<Resource> 资源对象集合
     */
    public List<Resource> getResourceByRole(Role role)
        throws BusinessException;
    
    /**
     * @Title getFirstLevelResource
     * @author wanglc
     * @Description: 查询当前角色的一级菜单
     * @date 2013-11-25
     * @return List<Resource> 资源对象集合
     */
    public List<Resource> getFirstLevelResource()
        throws BusinessException;
    
    /**
     * 根据当前角色ID获取资源列表
     * 
     * @Title getResourceByUserRoleList
     * @author wanglc
     * @date 2013-11-25
     * @param roleIds 角色ID，以逗号分隔的字符串
     * @return List<Resource> 资源对象集合
     */
    public List<Resource> getResourceByUserRoleList(String roleIds)
        throws BusinessException;
    
    /**
     * 根据父节点取所有子节点
     * 
     * @Title getAllSonResByParentId
     * @author wanglc
     * @date 2013-11-25
     * @param parentId 父节点ID
     * @return List<Resource> 资源对象集合
     */
    public List<Resource> getAllSonResByParentId(String parentId)
        throws BusinessException;
    
    /**
     * 用户ID和组织ID集合取资源列表
     * 
     * @Title getOwnResByUserAndOrg
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @param orgIds 组织ID，以逗号分隔的字符串
     * @return List<UserOrgResource>
     */
    /*
     * public List<UserOrgResource> getOwnResByUserAndOrg(String userId, String orgIds) throws
     * BusinessException;
     */
    
    /**
     * 根据资源属性查询资源
     * 
     * @Title getResource
     * @author wanglc
     * @date 2013-11-25
     * @param params 查询参数Map
     * @return ListVo<ResourceVo>
     */
    public ListVo<ResourceVo> getResource(Map<String, String> params)
        throws BusinessException;
    
    /**
     * 判断是否是叶子节点
     * 
     * @Title isLeaf
     * @author wanglc
     * @date 2014-2-21
     * @param resourceId
     * @return boolean 否是叶子节点
     */
    public boolean isLeaf(int resourceId)
        throws BusinessException;
    
    /**
     * 根据名称取资源
     * 
     * @Title getLocalResByName
     * @author wanglc
     * @date 2014-2-21
     * @param menu_name
     * @return Resource
     */
    public Resource getLocalResByName(String menu_name)
        throws BusinessException;
    
    /**
     * 验证资源属性的唯一性
     * 
     * @Title validateResourceProperties
     * @author wanglc
     * @date 2014-2-24
     * @param paramsMap 参数Map
     * @return 返回true：验证通过 false：验证不通过
     */
    public boolean validateResourceProperties(Map<String, String> paramsMap)
        throws BusinessException;
    
    /**
     * 层次递归查询资源
     * 
     * @Title getResourceListRecord
     * @author wanglc
     * @date 2014-3-4
     * @param map
     * @return ListVo<ResourceVo>
     */
    public ListVo<ResourceVo> getResourceListRecord(Map<String, String> map)
        throws BusinessException;
    
    /**
     * 根据主键获取资源数据，包括已删除的
     * 
     * @Title getResourceByIdIncludeAll
     * @author wanglc
     * @date 2014-3-27
     * @param id
     * @return Resource
     */
    public Resource getResourceByIdIncludeAll(int id)
        throws BusinessException;
    
    /**
     * 通过资源IDS查询资源集合
     * 
     * @Title getResourceByIds
     * @author dong.he
     * @date 2014年9月29日
     * @param ids 资源IDS
     * @return List<Resource> 资源集合
     * @throws BusinessException
     */
    public List<Resource> getResourceByIds(String ids)
        throws BusinessException;
}
