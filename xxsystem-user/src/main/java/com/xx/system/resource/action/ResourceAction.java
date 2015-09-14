package com.xx.system.resource.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.constant.Constant;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.exception.ServiceException;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.ResponseVo;
import com.xx.system.common.vo.TreeNode;
import com.xx.system.common.vo.TreeNodeVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.service.IDictService;
import com.xx.system.resource.entity.Resource;
import com.xx.system.resource.service.IResourceService;
import com.xx.system.resource.vo.ResourceVo;
import com.xx.system.role.service.IRoleResourceService;
import com.xx.system.user.service.IUserService;

/**
 * 资源Action详细定义
 * 
 * @version V1.20,2013-11-25 下午2:46:27
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class ResourceAction extends BaseAction {
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 8530026311372556739L;
    
    /**
     * @Fields resource : 资源对象
     */
    private Resource resource;
    
    /**
     * @Fields resourceService : 资源服务
     */
    @Autowired(required = true)
    @Qualifier("resourceService")
    public IResourceService resourceService;
    
    /**
     * @return treeNodeList
     */
    public List<TreeNode> getTreeNodeList() {
        return treeNodeList;
    }
    
    /**
     * @Fields userService : 用户服务
     */
    @Autowired(required = true)
    @Qualifier("userService")
    public IUserService userService;
    
    /**
     * @Fields userRoleService : 用户角色服务
     */
    /*@Autowired(required = true)
    @Qualifier("userRoleService")
    public IUserRoleService userRoleService;*/
    
    /**
     * @Fields roleResourceService : 角色资源服务
     */
    @Autowired(required = true)
    @Qualifier("roleResourceService")
    public IRoleResourceService roleResourceService;
    
    /**
     * @Fields roleResourceService : 字典资源服务
     */
    @Autowired(required = true)
    @Qualifier("dictService")
    public IDictService dictService;
    
    /**
     * @Fields treeNodeList : 节点集合
     */
    private List<TreeNode> treeNodeList;
    
    StringBuffer sb = new StringBuffer();
    
    /**
     * 资源管理页面跳转
     * 
     * @Title toResourcesManager
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String toResourcesManager() {
        List<Dictionary> dictList =
            dictService.getDictByTypeCode(Constant.RESOURCETYPE);
        getSession().setAttribute("resDictList", dictList);
        return SUCCESS;
    }
    
    /**
     * 获取资源集合
     * 
     * @Title getResourceList
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String getResourceList() {
       /* 优化之前的代码
        * Map<String, String> map = RequestUtil.getParameterMap(getRequest());
        ListVo<ResourceVo> list;
        try {
            list = resourceService.getResourceListRecord(map);
            JsonUtil.outJson(list);
        } catch (BusinessException e) {
            this.excepAndLogHandle(ResourceAction.class, "获取资源集合", e, false);
        }*/
    	//优化后的代码
    	Map<String, String> map = RequestUtil.getParameterMap(getRequest());
    	ResponseVo rv = new ResponseVo();
    	ListVo<ResourceVo> list;
        try {
            list = resourceService.getResourceListRecord(map);
            rv.setList(list.getList());
            rv.setTotalSize(list.getTotalSize());
            JsonUtil.outJson(list);
        } catch (BusinessException e) {
            this.excepAndLogHandle(ResourceAction.class, "获取资源集合", e, false);
        }
        return null;
    }
    
    /**
     * 根据ID获取资源对象
     * 
     * @Title getResourceById
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String getResourceById() {
        try {
            Map<String, String> params =
                RequestUtil.getParameterMap(getRequest());
            ListVo<ResourceVo> list = resourceService.getResource(params);
            JsonUtil.outJson(list);
        } catch (Exception e) {
            this.excepAndLogHandle(ResourceAction.class, "根据ID获取资源对象", e, false);
        }
        return null;
    }
    
    /**
     * 通过资源ID获取资源基本信息（优化后的代码）
     * 
     * @Title getRById
     * @author ndy
     * @date 2015-7-9
     * @return
     */
    public String getRById(){
    	try {
    		int id = RequestUtil.getInt(getRequest(), "resourceId");
    		Map<String, Object> map = new HashMap<String, Object>();
    		ResponseVo rs = new ResponseVo();
    		Resource res = resourceService.getResourceById(id);
    		if (res.getResource() != null) {
                map.put("resource.resource.resourceId", res.getResource().getResourceId());
                map.put("resource.resource.resourceName", res.getResource().getResourceName());
            }
    		map.put("resource.resourceName", res.getResourceName());
    		map.put("resource.urlpath", res.getUrlpath());
    		map.put("resource.code",res.getCode());
    		map.put("resource.disOrder",res.getDisOrder());
    		map.put("resource.remarks",res.getRemarks());
    		map.put("hiddenResourceCode", res.getCode());
    		map.put("dictionaryName", res.getResourceType().getDictionaryName());
    		map.put("resource.resourceType.pkDictionaryId", res.getResourceType().getPkDictionaryId());
    		rs.setData(map);
    		JsonUtil.outJson(rs);
		} catch (Exception e) {
			this.excepAndLogHandle(ResourceAction.class,"通过资源ID获取资源基础数据", e,false);
		}
    	return null;
    }
    
    /**
     * 通过资源ID获取资源基础数据
     * 
     * @Title getResById
     * @author ndy
     * @date 2014年2月27日
     */
    public String getResById() {
        int id = RequestUtil.getInt(getRequest(), "resourceId");
        if (id != 0) {
            Resource res;
            try {
                res = resourceService.getResourceById(id);
                Map<String, Object> map = new HashMap<String, Object>();
                if (res != null) {
                    map.put("success", true);
                    map.put("msg", "获取资源成功");
                    if (res.getResource() != null) {
                        map.put("parentResourceId", res.getResource()
                            .getResourceId());
                        map.put("parentResourceName", res.getResource()
                            .getResourceName());
                    }
                    map.put("resourceId", res.getResourceId());
                    map.put("resourceTypeId", res.getResourceType()
                        .getPkDictionaryId());
                    map.put("dictionaryName", res.getResourceType()
                        .getDictionaryName());
                    map.put("resourceName", res.getResourceName());
                    map.put("code", res.getCode());
                    map.put("urlpath", res.getUrlpath());
                    map.put("disOrder", res.getDisOrder());
                    map.put("remarks", res.getRemarks());
                } else {
                    Resource r = resourceService.getResourceByIdIncludeAll(id);
                    map.put("success", false);
                    map.put("msg", "【" + r.getResourceName() + "】已删除，列表已刷新");
                }
                JsonUtil.outJson(map);
            } catch (BusinessException e) {
                this.excepAndLogHandle(ResourceAction.class,
                    "通过资源ID获取资源基础数据",
                    e,
                    false);
            }
        }
        return null;
    }
    
    /**
     * 添加资源
     * 
     * @Title addResource
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String addResource() {
       try {
            int resTypeDictId = resource.getResourceType().getPkDictionaryId();// 资源类型
            String uuid = dictService.getUUIDById(resTypeDictId);
            
            if ("".equals(uuid)) {
                JsonUtil.outJson("{'success':'false','msg':'选择的资源类型已删除'}");
            } else {
                resource.setResourceTypeDictUUID(uuid);
                resource.setCreateDate(new Date());
                resource.setStatus(Constant.STATUS_NOT_DELETE);
                resource.setRemarks(resource.getRemarks().trim());
                //排序如果为空，默认改写为0
                if (resource.getDisOrder() == null ){
                    resource.setDisOrder(0);
                }
                Integer pId=resource.getResource().getResourceId();
                if(pId==null){
                	resource.setResource(null);
                }
                resourceService.addResource(resource);
                JsonUtil.outJson("{'success':'true','msg':'添加系统资源成功'}");
                this.excepAndLogHandle(ResourceAction.class, "添加资源", null, true);
            }
        } catch (Exception e) {
            JsonUtil.outJson("{'success':'false','msg':'添加系统资源失败'}");
            this.excepAndLogHandle(ResourceAction.class, "添加资源", e, false);
        }
        return null;
    }
    
    /**
     * 检查是否存在
     * 
     * @Title checkIsExist
     * @author wanglc
     * @date 2014-3-27
     * @param resId
     * @return
     */
    public boolean checkIsExist(int resId) {
        Resource r;
        try {
            r = resourceService.getResourceById(resId);
            if (r == null) {
                return true;
            }
        } catch (BusinessException e) {
            this.excepAndLogHandle(ResourceAction.class, "检查是否存在", e, false);
        }
        return false;
    }
    
    /**
     * 修改资源
     * 
     * @Title editResource
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String editResource() {
        String msg = "{'success':'true','msg':'修改系统资源成功'}";
        Map<String, String> params = RequestUtil.getParameterMap(getRequest());
        resource.setCode(params.get("hiddenResourceCode"));
        try {
            if (resource != null) {
                int resTypeDictId =
                    resource.getResourceType().getPkDictionaryId();// 资源类型
                String uuid = dictService.getUUIDById(resTypeDictId);
                if ("".equals(uuid)) {
                    msg = "{'success':'false','msg':'选择的资源类型已删除'}";
                } else {
                    resource.setCreateDate(new Date());
                    resource.setResourceTypeDictUUID(uuid);
                    resource.setRemarks(resource.getRemarks().trim());
                    //排序如果为空，默认改写为0
                    if (resource.getDisOrder() ==null)
                    {
                        resource.setDisOrder(0);
                    }
                    Integer pId=resource.getResource().getResourceId();
                    if(pId==null){
                    	resource.setResource(null);
                    }
                    msg = resourceService.updateResource(resource);
                    this.excepAndLogHandle(ResourceAction.class, 
                        "修改资源", null, true);
                }
            }
        } catch (Exception e) {
            this.excepAndLogHandle(ResourceAction.class, "修改资源", e, false);
            msg = "{'success':'false','msg':'修改系统资源失败'}";
        }
        JsonUtil.outJson(msg);
        return null;
    }
    
    /**
     * 获取当前父节点下所有子节点
     * 
     * @Title getResourceByParentId
     * @author wanglc
     * @date 2013-11-25
     * @return
     * @throws BusinessException
     */
    public String getResourceByParentId()
        throws BusinessException {
        try {
            Map<String, String> map = RequestUtil.getParameterMap(getRequest());
            map.put("start", super.start + "");
            map.put("limit", super.limit + "");
            ListVo<ResourceVo> list = resourceService.getResourceList(map);
            List<TreeNodeVo> result = new ArrayList<TreeNodeVo>();
            for (ResourceVo rv : list.getList()) {
                TreeNodeVo tn = new TreeNodeVo();
                tn.setText(rv.getResourceName());
                tn.setDescription(rv.getRemarks());
                tn.setLeaf(resourceService.isLeaf(rv.getResourceId()));
                tn.setNodeId(rv.getResourceId() + "");
                tn.setId(rv.getResourceId() + "");
                result.add(tn);
            }
            JsonUtil.outJsonArray(result);
        } catch (ServiceException e) {
            this.excepAndLogHandle(ResourceAction.class,
                "获取当前父节点下所有子节点",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 检查
     * 
     * @Title isChecked
     * @author wanglc
     * @date 2014-3-11
     * @param resIdsByUser
     * @param resIdsByRole
     * @param r
     * @return
     */
    public boolean isChecked(List<Integer> resIdsByUser,
        List<Integer> resIdsByRole, Resource r) {
        boolean checkFlag = false;
        if (resIdsByUser != null && resIdsByUser.contains(r.getResourceId())) {
            checkFlag = true;
        }
        if (resIdsByRole != null && resIdsByRole.contains(r.getResourceId())) {
            checkFlag = true;
        }
        return checkFlag;
    }
    
    /**
     * 得到父节点
     * 
     * @Title getByParent
     * @author wanglc
     * @date 2014-3-11
     * @param rr
     * @param resIdsByUser
     * @param resIdsByRole
     */
    public void getByParent(Resource rr, List<Integer> resIdsByUser,
        List<Integer> resIdsByRole) {
        try {
            List<Resource> res =
                resourceService.getAllSonResByParentId(rr.getResourceId() + "");
            if (res.size() != 0) {
                for (Resource r : res) {
                    sb.append("{\"nodeId\":\"" + r.getResourceId() + "\",");
                    sb.append("\"text\":\"" + r.getResourceName() + "\",");
                    sb.append("\"checked\":"
                        + isChecked(resIdsByUser, resIdsByRole, r) + ",");
                    sb.append("\"leaf\":" + decideIsLeaf(r) + ",");
                    sb.append("\"children\":[");
                    getByParent(r, resIdsByUser, resIdsByRole);
                    sb.append("]},");
                }
                
            }
        } catch (BusinessException e) {
            this.excepAndLogHandle(ResourceAction.class, "得到父节点", e, false);
        }
        
    }
    
    /**
     * 判断树节点是否为叶子节点
     * 
     * @Title decideIsLeaf
     * @author wanglc
     * @date 2013-11-25
     * @param r 资源对象
     * @return boolean
     */
    public boolean decideIsLeaf(Resource r) {
        boolean result = true;
        Set<Resource> sonResources = r.getResources();
        for (Resource sonResource : sonResources) {
            if (sonResource.getStatus() != Constant.STATUS_IS_DELETED) {
                result = false;
                break;
            }
        }
        return result;
    }
    
    /**
     * 批量删除系统资源
     * 
     * @Title deleteResource
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String deleteResource() {
        try {
            String resourceIds = this.getRequest().getParameter("resourceIds");
            String result = resourceService.deleteResource(resourceIds);
            JsonUtil.outJson(result);
            this.excepAndLogHandle(ResourceAction.class, "批量删除系统资源", null, true);
        } catch (Exception e) {
            JsonUtil.outJson("{'success':'false','msg':'删除系统资源失败'}");
            this.excepAndLogHandle(ResourceAction.class, "批量删除系统资源", e, false);
        }
        return null;
    }
    
    /**
     * 检查资源是否存在
     * 
     * @Title checkResIsExist
     * @author wanglc
     * @date 2014-3-27
     * @return
     */
    public String checkResIsExist() {
        String msg = "{'success':true,'msg':''}";
        String ids = getRequest().getParameter("ids");
        if (!"".equals(ids) && !"0".equals(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                try {
                    Resource r =
                        resourceService.getResourceById(NumberUtils.toInt(id));
                    if (r == null) {
                        r =
                            resourceService.getResourceByIdIncludeAll(NumberUtils.toInt(id));
                        msg =
                            "{'success':false,'msg':'【" + r.getResourceName()
                                + "】已删除，列表已刷新'}";
                        break;
                    }
                } catch (BusinessException e) {
                    this.excepAndLogHandle(ResourceAction.class,
                        "检查资源是否存在",
                        e,
                        false);
                }
                
            }
        }
        JsonUtil.outJson(msg);
        return null;
    }
    
    /**
     * 修改时验证资源名的唯一性
     * 
     * @Title validateUpdateResourceName
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String validateResourceProperties() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        Map<String, Object> vaildator = new HashMap<String, Object>();
        try {
            boolean flag =
                this.resourceService.validateResourceProperties(paramsMap);
            if (!flag) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "数据已存在");
            } else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            JsonUtil.outJson(vaildator);
        } catch (BusinessException e) {
            this.excepAndLogHandle(ResourceAction.class,
                "修改时验证资源名的唯一性",
                e,
                false);
        }
        
        return null;
    }
    
    public Resource getResource() {
        return resource;
    }
    
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    /**
     * setResourceService set方法
     * 
     * @Title setResourceService
     * @author wanglc
     * @date 2013-12-6
     * @param resourceService
     */
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    public void setTreeNodeList(List<TreeNode> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }
    
    /**
     * userService set方法
     * 
     * @Title setUserService
     * @author wanglc
     * @date 2013-12-6
     * @param userService
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    /**
     * userRoleService set方法
     * 
     * @Title setUserRoleService
     * @author wanglc
     * @date 2013-12-6
     * @param userRoleService
     */
    /*public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }*/
    
    /**
     * setRoleResourceService set方法
     * 
     * @Title setRoleResourceService
     * @author wanglc
     * @date 2013-12-6
     * @param roleResourceService
     */
    public void setRoleResourceService(IRoleResourceService roleResourceService) {
        this.roleResourceService = roleResourceService;
    }
    
    /**
     * @param dictService 要设置的 dictService
     */
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
}
