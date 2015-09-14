package com.xx.system.resource.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.interceptor.Log;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.PagerVo;
import com.xx.system.dict.service.IDictService;
import com.xx.system.resource.entity.Resource;
import com.xx.system.resource.service.IResourceService;
import com.xx.system.resource.vo.ResourceVo;
import com.xx.system.role.entity.Role;
import com.xx.system.role.entity.RoleResource;

/**
 * 详细实现资源接口定义的方法
 * 
 * @version V1.20,2013-11-25 下午3:08:48
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings({"unchecked", "unused", "rawtypes"})
@Service("resourceService")
public class ResourceServiceImpl implements IResourceService {
    
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    @Autowired
    @Qualifier("dictService")
    private IDictService dictService;
    
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
    
    @javax.annotation.Resource
    private IResourceService resourceService;
    
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    /**
     * 添加系统资源
     * 
     * @Title addResource
     * @author wanglc
     * @date 2013-11-25
     * @param resource 资源对象
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addResource(Resource resource)
        throws BusinessException {
        baseDao.addEntity(resource);
    }
    
    /**
     * 取得所有资源的列表
     * 
     * @Title getAllResource
     * @author wanglc
     * @date 2013-11-25
     * @return List<Resource>
     */
    @Override
    public List<Resource> getAllResource()
        throws BusinessException {
        String hql = "from Resource where status=" + Constant.STATUS_NOT_DELETE;
        return baseDao.queryEntitys(hql);
    }
    
    /**
     * 层次递归查询资源
     * 
     * @Title getResourceListRecord
     * @author wanglc
     * @date 2014-3-4
     * @param map
     * @return
     */
    @Override
    public ListVo<ResourceVo> getResourceListRecord(Map<String, String> map)
        throws BusinessException {
        Map<String, Object> param = new HashMap<String, Object>();
        ListVo<ResourceVo> resVoObj = new ListVo<ResourceVo>();
        List<ResourceVo> resVoList = new ArrayList<ResourceVo>();
        String resourceName = (String)map.get("resourceName");
        String currentResId = (String)map.get("currentResId");
        String parentResId = (String)map.get("parentResId");
        int start = NumberUtils.toInt(map.get("start"));
        int limit = NumberUtils.toInt(map.get("limit"));
        List<Resource> listResult = new ArrayList<Resource>();
        if (!StringUtils.isBlank(parentResId) && !"0".equals(parentResId)) {
        	getResListByHierarchicalQuery(Integer.parseInt(parentResId),listResult);
        }
        StringBuffer hqBf = new StringBuffer();
    	for(Resource r : listResult){
    		hqBf.append(",").append(r.getResourceId());
    	}
       
        StringBuffer sql =
            new StringBuffer(
                "From Resource a where a.status = "
                    + Constant.STATUS_NOT_DELETE);
        StringBuffer sqlCount =
            new StringBuffer(
                " select count(*) from Resource a where a.status = "
                    + Constant.STATUS_NOT_DELETE);
        
        if (!StringUtils.isBlank(currentResId)) {
            sql.append(" and a.resourceId = " + currentResId + "");
            sqlCount.append(" and a.resourceId = " + currentResId + "");
        }
        if (!StringUtils.isBlank(resourceName)) {
            sql.append(" and a.resourceName like '%" + resourceName + "%'");
            sqlCount.append(" and a.resourceName like '%" + resourceName + "%'");
        }
        if (hqBf.length()>0) {
            sql.append(" and a.resourceId in( ").append(hqBf.substring(1)).append(")");
            sqlCount.append(" and a.resourceId in( ").append(hqBf.substring(1)).append(")");
        }
        sql.append(" order by a.resourceType.pkDictionaryId,a.resource.resourceId,a.disOrder, a.resourceId ");
        
        List<Resource> result = baseDao.query(sql.toString(),null,start,limit);
      
        int totalSize =  baseDao.queryTotalCount(sqlCount.toString(), new HashMap<String, Object>());
            //baseDao.getTotalCount(sql.toString(),null);
        for (Resource r : result) {
            ResourceVo rv = new ResourceVo(r);
            resVoList.add(rv);
            
        }
        
        resVoObj.setList(resVoList);
        resVoObj.setTotalSize(totalSize);
        return resVoObj;
    }
    
    /**
     * 查询资源下的所有子资源。
     * 
     * @Title getResListByHierarchicalQuery
     * @author hedaojun
     * @date 2014年10月16日
     * @param startId 资源id
     * @param orgListResult 存放结果的LIST
     * @return List<Resource> 存放结果的LIST
     * @throws BusinessException
     */
    private List<Resource>  getResListByHierarchicalQuery(int startId, List<Resource> listResult)
            throws BusinessException {
            // 判断组织id是否为空
                StringBuffer sqlBuffer = new StringBuffer();
                Resource res =(Resource) baseDao.queryEntityById(Resource.class,
                		startId);
               listResult.add(res);
                if(res.getResources()!=null && res.getResources().size() != 0){
                	for(Resource child : res.getResources()){
                		getResListByHierarchicalQuery(child.getResourceId(),listResult);
                	}
                }
              return listResult;
        }
    
    /**
     * 通过参数获取resource
     * 
     * @Title getResourceList
     * @author wanglc
     * @date 2013-11-25
     * @param map 参数Map
     * @return ListVo<ResourceVo>
     */
    @Override
    public ListVo<ResourceVo> getResourceList(Map<String, String> map)
        throws BusinessException {
        Map<String, Object> param = new HashMap<String, Object>();
        ListVo<ResourceVo> resourceVoObj = new ListVo<ResourceVo>();
        List<ResourceVo> resourceVoList = new ArrayList<ResourceVo>();
        String resourceName = (String)map.get("resourceName");
        String parentResName = (String)map.get("parentResName");
        String parentResId = (String)map.get("parentResId");
        int start = NumberUtils.toInt(map.get("start"));
        int limit = NumberUtils.toInt(map.get("limit"));
        Resource res = null;
        StringBuffer hqlQuery =
            new StringBuffer("select r from Resource r where r.status=0 ");
        StringBuffer hqlCount =
            new StringBuffer(
                "select count(c.resourceId) from Resource c where c.status=0 ");
        if (!StringUtils.isBlank(resourceName)) {
            hqlQuery.append(" and r.resourceName like :resourceName ");
            hqlCount.append(" and c.resourceName like :resourceName ");
            param.put("resourceName", "%" + resourceName + "%");
        }
        if (!StringUtils.isBlank(parentResName)) {
            hqlQuery.append(" and r.resource.resourceName = :resourceName ");
            hqlCount.append(" and c.resource.resourceName = :resourceName ");
            param.put("parentResName", parentResName);
        } else if (!StringUtils.isBlank(parentResId)) {
            if ("0".equals(parentResId)) {
                hqlQuery.append(" and r.resource is null ");
                hqlCount.append(" and c.resource is null ");
            } else {
                hqlQuery.append(" and r.resource.resourceId = :parentResId");
                hqlCount.append(" and c.resource.resourceId = :parentResId");
                param.put("parentResId", Integer.parseInt(parentResId));
            }
        }
        hqlQuery.append(" order by r.disOrder,r.resourceId desc ");
        List<Resource> resourceList =
            (List<Resource>)this.baseDao.queryEntitysByPage(start,
                Integer.MAX_VALUE,
                hqlQuery.toString(),
                param);
        int totalSize =
            this.baseDao.queryTotalCount(hqlCount.toString(), param);
        
        for (int i = 0; i < resourceList.size(); i++) {
            Resource r = resourceList.get(i);
            ResourceVo rV = new ResourceVo(r);
            resourceVoList.add(rV);
        }
        resourceVoObj.setList(resourceVoList);
        resourceVoObj.setTotalSize(totalSize);
        
        return resourceVoObj;
    }
    
    /**
     * 根据资源编码查询对象
     * 
     * @Title getResourceByCode
     * @author wanglc
     * @date 2013-11-25
     * @param code 资源编码
     * @return Resource
     */
    @Override
    public Resource getResourceByCode(String code)
        throws BusinessException {
        String hql =
            "from Resource where code=? and status = "
                + Constant.STATUS_NOT_DELETE;
        List<Resource> list = baseDao.query(hql, new String[] {code});
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
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
    @Override
    public Resource getResourceById(int id)
        throws BusinessException {
        String hql =
            "from Resource r where r.resourceId = " + id + " and r.status = "
                + Constant.STATUS_NOT_DELETE;
        List<Resource> rList = baseDao.queryEntitys(hql);
        if (rList != null && rList.size() > 0) {
            return rList.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * 修改系统资源
     * 
     * @Title updateResource
     * @author wanglc
     * @date 2013-11-25
     * @param resource 资源对象
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public String updateResource(Resource resource)
        throws BusinessException {
        try {
            baseDao.updateEntity(resource);
        } catch (Exception e) {
            return "{success:'false',msg:'资源修改失败'}";
        }
        return "{success:'true',msg:'资源修改成功'}";
    }
    
    /**
     * 批量删除系统资源
     * 
     * @Title deleteResource
     * @author wanglc
     * @date 2013-11-25
     * @param resourceIds 资源ID，以逗号分隔的字符串
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public String deleteResource(String resourceIds)
        throws BusinessException {
        String result = "{success:'true',msg:'删除系统资源成功'}";
        Resource r = null;
        try {
            for (String resId : resourceIds.split(",")) {
                r =
                    (Resource)baseDao.queryEntityById(Resource.class,
                        NumberUtils.toInt(resId));
                if (r.getStatus() == 1) {
                    return "{success:'false',msg:'【" + r.getResourceName()
                        + "】已删除，列表已刷新'}";
                }
                String userResHql =
                    "from UserOrgResource uor where  uor.user.status = "
                        + Constant.STATUS_NOT_DELETE
                        + " and uor.organization.status = "
                        + Constant.STATUS_NOT_DELETE
                        + " and uor.resource.status = "
                        + Constant.STATUS_NOT_DELETE
                        + " and uor.resource.resourceId = " + resId + "";
                String roleResHql =
                    "from RoleResource rr where rr.resource.resourceId = "
                        + resId;
                //List<UserOrgResource> list = baseDao.queryEntitys(userResHql);
                List<RoleResource> rrList = baseDao.queryEntitys(roleResHql);
                /*if (list != null && list.size() > 0) {
                    r =
                        (Resource)baseDao.queryEntityById(Resource.class,
                            NumberUtils.toInt(resId));
                    return "{success:'false',msg:'名称为" + r.getResourceName()
                        + "的资源已被使用，不允许删除'}";
                }*/
                if (rrList != null && rrList.size() > 0) {
                    r =
                        (Resource)baseDao.queryEntityById(Resource.class,
                            NumberUtils.toInt(resId));
                    return "{success:'false',msg:'名称为" + r.getResourceName()
                        + "的资源已被使用，不允许删除'}";
                }
                
            }
            this.baseDao.delete(resourceIds,
                Resource.class.getName(),
                "status",
                "resourceId",
                Constant.STATUS_IS_DELETED + "");
            return result;
        } catch (Exception e) {
            return "{success:'false',msg:'删除系统资源失败'}";
        }
    }
    
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
    @Override
    public boolean validateUpdateResourceName(String resourceName,
        String oldResourceId)
        throws BusinessException {
        Resource oldResource =
            (Resource)this.baseDao.queryEntityById(Resource.class,
                NumberUtils.toInt(oldResourceId));
        String sql =
            "SELECT COUNT(RESOURCE_ID) FROM Resource a WHERE a.status = "
                + Constant.STATUS_NOT_DELETE + " AND a.resourceName='"
                + resourceName.replace("'", "''") + "' and a.resourceName != '"
                + oldResource.getResourceName() + "'";
        int flag =baseDao.getTotalCount(sql, null); 
        if (flag == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否是叶子节点
     * 
     * @Title isLeaf
     * @author wanglc
     * @date 2014-2-21
     * @param resourceId
     * @return
     */
    @Override
    public boolean isLeaf(int resourceId)
        throws BusinessException {
        String hql =
            "from Resource r where r.status=" + Constant.STATUS_NOT_DELETE
                + " and r.resource.resourceId = " + resourceId;
        List<Resource> children = baseDao.queryEntitys(hql);
        if (children != null && children.size() > 0) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 查询资源分页，可根据名字模糊查询，可根据类型精确查询
     * 
     * @Title getResourceList
     * @author wanglc
     * @date 2013-11-25
     * @param start 查询开始位置
     * @param limit 每页限制条数
     * @param resource 资源对象
     * @return PagerVo<Resource>
     */
    @Override
    public PagerVo<Resource> getResourceList(int start, int limit,
        Resource resource)
        throws BusinessException {
        StringBuffer hql =
            new StringBuffer("from Resource r where r.status = "
                + Constant.STATUS_NOT_DELETE);
        Map<String, Object> values = new HashMap<String, Object>();
        // 遍历Resource中的名字属性和类型属性
        if (resource != null) {
            if (StringUtils.isNotBlank(resource.getResourceName())) {
                hql.append(" and r.resourceName like '%"
                    + resource.getResourceName() + "%' ");
            }
            if (StringUtils.isNotBlank(resource.getResourceType()
                .getDictionaryName())) {
                hql.append(" and r.resourceType.dictionaryName = '"
                    + resource.getResourceType().getDictionaryName() + "' ");
            }
            if (resource.getStatus() >= 0) {
                hql.append(" and r.status =  " + resource.getStatus() + "");
            }
        }
        hql.append(" order by r.createDate desc,r.disOrder ");
        List<Resource> logList =
            baseDao.queryEntitysByPage(start, limit, hql.toString(), values);
        int count = baseDao.queryTotalCount(hql.toString(), values);
        return new PagerVo<Resource>(start, limit, count, logList);
    }
    
    /**
     * 根据角色对象查询该角色拥有的资源列表
     * 
     * @Title getResourceByRole
     * @author wanglc
     * @date 2013-11-25
     * @param role 角色对象
     * @return List<Resource>
     */
    @Override
    public List<Resource> getResourceByRole(Role role)
        throws BusinessException {
        List<Resource> resourceList = new ArrayList<Resource>();
        String hql =
            "from RoleResource rr where rr.role.isDelete = "
                + Constant.STATUS_NOT_DELETE + " and rr.resource.status = "
                + Constant.STATUS_NOT_DELETE + " and rr.role.id = '"
                + role.getRoleId() + "'";
        List<RoleResource> roleResourceList = baseDao.query(hql);
        for (RoleResource rr : roleResourceList) {
            if (rr.getResource() != null) {
                resourceList.add(rr.getResource());
            }
        }
        return resourceList;
    }
    
    /**
     * 查询当前角色的一级菜单
     * 
     * @Title getFirstLevelResource
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @return List<Resource>
     */
    @Override
    public List<Resource> getFirstLevelResource()
        throws BusinessException {
        String hql =
            "from Resource r where r.resource is null and r.status = "
                + Constant.STATUS_NOT_DELETE + " order by r.disOrder";
        return baseDao.queryEntitys(hql);
    }
    
    /**
     * 根据当前角色ID获取资源列表
     * 
     * @Title getResourceByUserRoleList
     * @author wanglc
     * @date 2013-11-25
     * @param roleIds 角色ID，以逗号分隔的字符串
     * @return List<Resource>
     */
    @Override
    public List<Resource> getResourceByUserRoleList(String roleIds)
        throws BusinessException {
        List<Resource> resourceList = null;
        
        String hql =
            "select distinct rr from Resource rr,RoleResource t where 1=1  "
                + " and rr.resourceId=t.resource.resourceId and rr.status=0 and t.isDelete = 0 and rr.resource.resourceId is null"
                + " and  t.role.roleId in(" + roleIds
                + ") order by rr.disOrder";
        resourceList = baseDao.queryEntitys(hql);
        
        return resourceList;
    }
    
    /**
     * 根据父节点取所有子节点
     * 
     * @Title getAllSonResByParentId
     * @author wanglc
     * @date 2013-11-25
     * @param parentId 父节点ID
     * @return
     */
    @Override
    public List<Resource> getAllSonResByParentId(String parentId)
        throws BusinessException {
        String hql =
            "from Resource o where o.status = " + Constant.STATUS_NOT_DELETE
                + " and o.resource.status = " + Constant.STATUS_NOT_DELETE
                + " and o.resource.resourceId = " + NumberUtils.toInt(parentId)
                + " order by o.disOrder Asc";
        List<Resource> result = baseDao.queryEntitys(hql);
        return result;
    }
    
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
    /*@Override
    public List<UserOrgResource> getOwnResByUserAndOrg(String userId,
        String orgIds)
        throws BusinessException {
        String hql =
            "from UserOrgResource t where t.user.status = "
                + Constant.STATUS_NOT_DELETE + " and t.organization.status = "
                + Constant.STATUS_NOT_DELETE + " and t.resource.status = "
                + Constant.STATUS_NOT_DELETE;
        hql +=
            " and t.user.userId = " + userId + " and t.organization.orgId in ("
                + orgIds + ")";
        List<UserOrgResource> results = baseDao.queryEntitys(hql);
        if (results.size() > 0) {
            return results;
        }
        return null;
    }*/
    
    /**
     * 根据资源属性查询资源
     * 
     * @Title getResource
     * @author wanglc
     * @date 2013-11-25
     * @param params 查询参数Map
     * @return ListVo<ResourceVo>
     */
    @Override
    public ListVo<ResourceVo> getResource(Map<String, String> params)
        throws BusinessException {
        ResourceVo rv = new ResourceVo();
        List<ResourceVo> rvList = new ArrayList<ResourceVo>();
        ListVo<ResourceVo> rvListVo = new ListVo<ResourceVo>();
        int resourceId = NumberUtils.toInt(params.get("resourceId"));
        Resource r =
            (Resource)baseDao.queryEntityById(Resource.class, resourceId);
        rv.setCode(r.getCode());
        rv.setCreateDate(r.getCreateDate());
        rv.setDisOrder(r.getDisOrder());
        if (r.getResource() != null) {
            rv.setParentResourceId(r.getResource().getResourceId());
            rv.setParentResourceName(r.getResource().getResourceName());
        } else {
            rv.setParentResourceName("");
        }
        rv.setRemarks(r.getRemarks());
        rv.setResourceId(r.getResourceId());
        rv.setResourceName(r.getResourceName());
        rv.setResourceTypeId(r.getResourceType().getPkDictionaryId());
        rv.setResourceTypeName(r.getResourceType().getDictionaryName());
        rv.setResourceTypeValue(r.getResourceType().getDictionaryValue());
        rv.setStatus(r.getStatus());
        rv.setUrlpath(r.getUrlpath());
        rvList.add(rv);
        
        rvListVo.setList(rvList);
        rvListVo.setTotalSize(1);
        return rvListVo;
    }
    
    /**
     * 逻辑删除集成平台已经删除的菜单，status置为2.TIPS：0 未删除 1架构删除 2集成平台删除（切换回架构时须置为0）
     * 
     * @Title deleteLocalResLogic
     * @author wanglc
     * @date 2013-12-17
     * @param resList
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Log(operationType = "删除", operationName = "本地资源数据")
    private void deleteLocalResLogic(List<Resource> resList)
        throws BusinessException {
        for (Resource res : resList) {
            // if(res.getResourceFrom() == 0){
            // continue;
            // }
            if (res.getIsDeleteAble() != 1) {
                baseDao.delete(res.getResourceId() + "",
                    "Resource",
                    "status",
                    "resourceId",
                    "2");
            }
        }
    }
    
    /**
     * 根据角色名称查询并返回角色对象
     * 
     * @Title getLocalResByName
     * @author wanglc
     * @date 2013-12-17
     * @param menu_name
     * @return
     */
    @Override
    public Resource getLocalResByName(String menu_name)
        throws BusinessException {
        String hql =
            "from Resource r where r.resourceName = '" + menu_name
                + "' and r.status = " + Constant.STATUS_NOT_DELETE;
        List<Resource> resList = baseDao.queryEntitys(hql);
        if (resList != null && resList.size() > 0) {
            return resList.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * 通过编码查询资源
     * 
     * @Title getLocalResByCode
     * @author ndy
     * @date 2014年4月15日
     * @param menu_name
     * @return
     */
    public Resource getLocalResByCode(String menu_code)
        throws BusinessException {
        String hql =
            "from Resource r where r.code = '" + menu_code
                + "' and r.status = " + Constant.STATUS_NOT_DELETE;
        List<Resource> resList = baseDao.queryEntitys(hql);
        if (resList != null && resList.size() > 0) {
            return resList.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * 验证资源属性的唯一性
     * 
     * @Title validateResourceProperties
     * @author wanglc
     * @date 2014-2-24
     * @param paramsMap 参数Map
     * @return 返回true：验证通过 false：验证不通过
     */
    @Override
    public boolean validateResourceProperties(Map<String, String> paramsMap)
        throws BusinessException {
        String key = paramsMap.get("key");
        String value = paramsMap.get("value");
        String resourceId = paramsMap.get("resourceId");
        String validatorType = paramsMap.get("validatorType");
        String parentId = paramsMap.get("parentId");
        boolean flag = true;
        String valueField = "";
        switch (NumberUtils.toInt(key)) {
            case 0:
                valueField = "resourceName";
                break;
            case 1:
                valueField = "code";
                break;
            case 2:
                valueField = "urlpath";
                break;
            default:
                break;
        }
        String hql =
            "from Resource r where " + " r.resource.resourceId=" + parentId
                + "and r." + valueField + " = '" + value + "' and r.status = "
                + Constant.STATUS_NOT_DELETE;
        if (validatorType != null && "update".equals(validatorType)) {
            hql += " and r.resourceId <> " + resourceId;
        }
        int totleSize = baseDao.queryTotalCount(hql, new HashMap());
        if (totleSize > 0) {
            flag = false;
        }
        return flag;
    }
    
    /**
     * 根据主键获取资源数据，包括已删除的
     * 
     * @Title getResourceByIdIncludeAll
     * @author wanglc
     * @date 2014-3-27
     * @param id
     * @return
     */
    @Override
    public Resource getResourceByIdIncludeAll(int id)
        throws BusinessException {
        return (Resource)baseDao.queryEntityById(Resource.class, id);
    }

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
    @Override
    public List<Resource> getResourceByIds(String ids)
        throws BusinessException {
        // 新建查询资源的HQL语句
        StringBuffer hql = new StringBuffer( "from Resource r");
        hql.append(" where r.status = ");
        hql.append(Constant.STATUS_NOT_DELETE);
        hql.append(" and r.resourceId in (");
        hql.append(ids);
        hql.append(") order by r.disOrder");
        
        return (List<Resource>)baseDao.queryEntitys(hql.toString());
    }
}
