package com.xx.system.role.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.constant.Constant;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.FileUtil;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.ResponseVo;
import com.xx.system.common.vo.TreeNode;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.service.IDictService;
import com.xx.system.org.entity.Organization;
import com.xx.system.resource.entity.Resource;
import com.xx.system.resource.service.IResourceService;
import com.xx.system.role.entity.Role;
import com.xx.system.role.entity.RoleMemberScope;
import com.xx.system.role.entity.RoleResource;
import com.xx.system.role.entity.ScopeMember;
import com.xx.system.role.service.IRoleService;
import com.xx.system.role.vo.ResourceTreeNode;
import com.xx.system.role.vo.RoleVo;
import com.xx.system.user.vo.GroupVo;
import com.xx.system.user.vo.UserVo;
/**
 * 角色Action
 * 
 * @version V1.20,2013-11-25 下午2:19:02
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class RoleAction extends BaseAction {
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = -5433084327100824821L;
    
    /**
     * @Fields resourceService : 资源服务
     */
    private IResourceService resourceService;
    
    /**
     * @Fields dictService : 资源服务
     */
    @Autowired
    private IDictService dictService;
    
    /**
     * @Fields roleService : 角色服务
     */
    @Autowired
    private IRoleService roleService;
    
    /**
     * @Fields workRound : 角色成员实体
     */
    private RoleMemberScope roleMemberScope;
    
    /**
     * @Fields role : 角色对象
     */
    private Role role;
    
    /**
     * @Fields uploadAttach : 附件上传文件
     */
    private File uploadAttach;
    
    /**
     * @Fields filename : 文件名称
     */
    private String filename;
    
    /**
     * @Fields id : ID
     */
    private int id;
    
    /**
     * @Fields resourceList : 资源列表
     */
    private List<Resource> resourceList;
    
    /**
     * @Fields resource : 资源对象
     */
    private Resource resource;
    
    /**
     * @Fields resourceIds : 资源ID，以逗号隔开的字符串
     */
    private String resourceIds;
    
    /**
     * @Fields roleId : 角色ID
     */
    private String roleId;
    
    /**
     * @Fields result : 返回结果
     */
    private String result;
    
    /**
     * @Fields treeNodeList : 节点集合
     */
    private ArrayList<TreeNode> treeNodeList;
    
    /**
     * @Fields resList : 返回数据集合
     */
    private List<Resource> resList;
    
    /**
     * @Fields resList : 返回数据集合
     */
    private List<ScopeMember> smList;
    
    private StringBuffer sb = new StringBuffer();
    
    /**
     * 跳转到角色管理页面
     * 
     * @Title toRoleManage
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String toRoleManage() {
        return SUCCESS;
    }
    
    /**
     * 跳转到角色成员管理页面
     * 
     * @Title toRoleMember
     * @author lizhengc
     * @date 2013-09-22
     * @return String
     */
    public String toRoleMember() {
        return SUCCESS;
    }
    
    /**
     * 取得 角色列表
     * 
     * @Title getRoleList
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String getRoleList() {
        try {
            Map<String, String> paramMap =
                RequestUtil.getParameterMap(getRequest());
            ListVo<RoleVo> roleVoListVo =
                this.roleService.getRoleVoList(paramMap);
            JsonUtil.outJson(roleVoListVo);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "添加角色成功", e, true);
        }
        return null;
    }
    
    /**
     * 跳转至添加角色页面
     * 
     * @Title toAddRole
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String toAddRole() {
        return SUCCESS;
    }
    
    /**
     * 添加角色
     * 
     * @Title addRole
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String addRole() {
        try {
        	role = parseRoleFormRequest();
            int dictId = role.getRoleType().getPkDictionaryId();
            Dictionary dict = dictService.getDictById(dictId);
            role.setRoleTypeUUID(dict.getDictUUID());
            role.setDescription(role.getDescription().trim());
            String result = roleService.addUpdateRole(role);
            JsonUtil.outJson(result);
            this.excepAndLogHandle(RoleAction.class, "添加角色成功", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'添加角色失败！'}");
            this.excepAndLogHandle(RoleAction.class, "添加角色失败", e, false);
        }
        return null;
    }
    
    /**
     * 获取参数
     * 
     * @Title parseRoleFormRequest
     * @author hedaojun
     * @date 2015-05-25
     * @return String
     */
    private Role parseRoleFormRequest() {
    	Role role = new Role();
    	try {
    		Map<String, String> roleMap = RequestUtil.getParameterMap(super.getRequest());
    		role.setDescription(roleMap.get("description"));
    		role.setRoleCode(roleMap.get("roleCode"));
    		if(roleMap.get("roleId")!=null && StringUtil.isNotBlank(roleMap.get("roleId")) ){
    			role.setRoleId(Integer.parseInt(roleMap.get("roleId")));
    		}
    		role.setRoleName(roleMap.get("roleName"));
    		if(roleMap.get("roleTypeId")!=null && StringUtil.isNotBlank(roleMap.get("roleTypeId"))){
    			role.setRoleType(new Dictionary(Integer.parseInt(roleMap.get("roleTypeId"))));
    		}
    		}
    	catch (Exception e) {
    		JsonUtil.outJson("{success:'false',msg:'添加角色失败！'}");
    		this.excepAndLogHandle(RoleAction.class, "添加角色失败", e, false);
    	}
    	return role;
    }
    
    /**
     * 根据ID获取角色
     * 
     * @Title getRoleById
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String getRoleById() {
        try {
        	ResponseVo rv = new ResponseVo();
        	RoleVo roleVo = roleService.getRole(id);
            rv.setData(roleVo);
            JsonUtil.outJson(rv);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "根据ID获取角色", e, false);
        }
        return null;
    }
    
    /**
     * 删除角色
     * 
     * @Title deleteRole
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String deleteRole() {
        try {
            String ids = this.getRequest().getParameter("ids");
            String result = roleService.deleteRole(ids);
            JsonUtil.outJson(result);
            this.excepAndLogHandle(RoleAction.class, "删除角色", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'删除角色失败！'}");
            this.excepAndLogHandle(RoleAction.class, "删除角色", e, false);
        }
        return null;
    }
    
    /**
     * 获取角色对应的资源
     * 
     * @Title getRoleResource
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String getRoleResource() {
        try {
            // 角色ID
            String roleId = this.getRequest().getParameter("roleId");
            List<RoleResource> list =
                this.roleService.getRoleResourceList(NumberUtils.toInt(roleId));
            
            Integer[] resourceIds = new Integer[list.size()];
            for (int i = 0; i < list.size(); i++) {
                resourceIds[i] = list.get(i).getResource().getResourceId();
            }
            JsonUtil.outJsonArray(resourceIds);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "获取角色对应的资源", e, false);
        }
        return null;
    }
    
    /**
     * 获取角色对应的资源树
     * 
     * @Title getRoleResourceForTree
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String getRoleResourceForTree() {
        try {
            String roleId = this.getRequest().getParameter("roleId");
            
            treeNodeList = new ArrayList<TreeNode>();
            List<Integer> resIds = new ArrayList<Integer>();
            resList = new ArrayList<Resource>();
            List<RoleResource> list =
                this.roleService.getRoleResourceList(NumberUtils.toInt(roleId));
            if (list != null && list.size() > 0) {
                for (RoleResource rr : list) {
                    resIds.add(rr.getResource().getResourceId());
                    if (rr.getResource().getResource() != null) {
                        resIds.add(rr.getResource()
                            .getResource()
                            .getResourceId());
                    }
                }
            }
            
            JsonUtil.outJsonArray(getAllResource(resIds));
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "获取角色对应的资源树", e, false);
        }
        return null;
    }
    
    private List<ResourceTreeNode> getAllResource( List<Integer> resIds){
    	List<ResourceTreeNode> levelOne = null;
    	try {
			List<Resource> relist = resourceService.getAllResource();
			Map<Integer,ResourceTreeNode> reMap = new HashMap<Integer,ResourceTreeNode>();
			levelOne = new ArrayList<ResourceTreeNode>();
			ResourceTreeNode tn = null;
			for(Resource r : relist){
				tn = new ResourceTreeNode();
				tn.setChecked(resIds.contains(r.getResourceId()));
				tn.setLeaf(true);
				tn.setNodeId(r.getResourceId());
				tn.setText(r.getResourceName());
				
				reMap.put(r.getResourceId(), tn);
				
				if(r.getResource()==null){
					levelOne.add(tn);
				}
			}
			Resource parent = null;
			ResourceTreeNode parentNode = null;
			for(Resource r : relist){
				parent=r.getResource();
				if(parent!=null){
					parentNode = reMap.get(parent.getResourceId());
					parentNode.getChildren().add(reMap.get(r.getResourceId()));
					parentNode.setLeaf(false);
				}
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return levelOne;
    }
    
    private void getByParent(Resource rr, List<Integer> resIds)
        throws BusinessException {
        List<Resource> res =
            resourceService.getAllSonResByParentId(rr.getResourceId() + "");
        if (res.size() != 0) {
            for (Resource r : res) {
                sb.append("{\"nodeId\":\"" + r.getResourceId() + "\",");
                sb.append("\"text\":\"" + r.getResourceName() + "\",");
                sb.append("\"checked\":" + resIds.contains(r.getResourceId())
                    + ",");
                sb.append("\"leaf\":" + decideIsLeaf(r) + ",");
                sb.append("\"children\":[");
                getByParent(r, resIds);
                sb.append("]},");
            }
            
        }
    }
    
    /**
     * 判断树节点是否为叶子节点
     * 
     * @Title decideIsLeaf
     * @author wanglc
     * @date 2013-11-25
     * @param r 资源对象
     * @return bolean
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
     * 给角色分配资源
     * 
     * @Title addRoleResource
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String addRoleResource() {
        try {
            String checked = this.getRequest().getParameter("checked");// 复选框状态
            String resourceIds = this.getRequest().getParameter("resourceIds");// 资源ID
            String roleId = this.getRequest().getParameter("roleId");// 角色ID
            if (Boolean.valueOf(checked)) {
                this.roleService.addRoleResources(resourceIds, roleId);
                JsonUtil.outJson("{success:true,msg:'分配资源成功！'}");
                this.excepAndLogHandle(RoleAction.class, "分配资源成功", null, true);
            }
            else {
                this.roleService.deleteRoleResources(resourceIds, roleId);
                JsonUtil.outJson("{success:true,msg:'移除资源成功！'}");
                this.excepAndLogHandle(RoleAction.class, "移除资源成功", null, true);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "给角色分配资源", e, false);
            JsonUtil.outJson("{success:'false',msg:'操作资源失败！'}");
        }
        
        return null;
    }
    
    /**
     * 修改角色信息
     * 
     * @Title updateRole
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String updateRole() {
        try {
        	role = parseRoleFormRequest();
            Dictionary dict = dictService.getDictById(role.getRoleType().getPkDictionaryId());
            role.setRoleTypeUUID(dict.getDictUUID());
            role.setDescription(role.getDescription().trim());
            this.roleService.updateRole(role);
            JsonUtil.outJson("{success:true,msg:'修改角色成功！'}");
            this.excepAndLogHandle(RoleAction.class, "修改角色信息", null, true);
           
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'修改角色失败！'}");
            this.excepAndLogHandle(RoleAction.class, "修改角色信息", e, false);
            return null;
        }
        return null;
    }
    
    /**
     * 添加角色验证角色名是否唯一
     * 
     * @Title validateAddRoleName
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String validateAddRoleName() {
        try {
            Map<String, Object> vaildator = new HashMap<String, Object>();
            String roleName = this.getRequest().getParameter("roleName");
            String roleType = this.getRequest().getParameter("roleType");
            int roleSize =
                this.roleService.getRoleNumByName(roleName, roleType);
            
            if (roleSize != 0) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "角色名称已经存在！");
            }
            else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            JsonUtil.outJson(vaildator);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "添加角色验证角色名是否唯一", e, false);
        }
        return null;
    }
    
    /**
     * 添加角色验证角色编码是否唯一
     * 
     * @Title validateAddRoleCode
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String validateAddRoleCode() {
        try {
            Map<String, Object> vaildator = new HashMap<String, Object>();
            String roleCode = this.getRequest().getParameter("roleCode");
            String roleType = this.getRequest().getParameter("roleType");
            int roleSize =
                this.roleService.validateAddRoleCode(roleCode, roleType);
            
            if (roleSize != 0) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "角色编码已经存在！");
            }
            else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            JsonUtil.outJson(vaildator);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "添加角色验证角色编码是否唯一", e, false);
        }
        return null;
    }
    
    /**
     * 修改角色，验证角色名是否唯一
     * 
     * @Title validateUpdateRoleName
     * @author wanglc
     * @date 2013-11-25
     * @return Stirng
     */
    public String validateUpdateRoleName() {
        try {
            HttpServletRequest request = getRequest();
            String roleName = request.getParameter("roleName");
            String oldRoleId = request.getParameter("roleId");
            String roleType = request.getParameter("roleType");
            Map<String, Object> vaildator = new HashMap<String, Object>();
            
            int roleSize =
                this.roleService.validateUpdateRoleName(roleName,
                    oldRoleId,
                    roleType);
            if (roleSize != 0) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "角色名已经存在！");
            }
            else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            JsonUtil.outJson(vaildator);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "修改角色，验证角色名是否唯一", e, false);
        }
        return null;
    }
    
    /**
     * 修改角色，验证角色编码是否唯一
     * 
     * @Title validateUpdateRoleCode
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String validateUpdateRoleCode() {
        try {
            HttpServletRequest request = getRequest();
            String roleCode = request.getParameter("roleCode");
            String oldRoleId = request.getParameter("roleId");
            String roleType = request.getParameter("roleType");
            Map<String, Object> vaildator = new HashMap<String, Object>();
            
            int roleSize =
                this.roleService.validateUpdateRoleCode(roleCode,
                    oldRoleId,
                    roleType);
            if (roleSize != 0) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "角色编码已经存在！");
            }
            else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            JsonUtil.outJson(vaildator);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class,
                "修改角色，验证角色编码是否唯一",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 下载角色与资源关系导入模版
     * 
     * @Title getExcelTemplateStream
     * @author wanglc
     * @date 2013-11-25
     * @return InputStream
     */
    public InputStream getExcelTemplateStream() {
        HttpServletRequest request = this.getRequest();
        ServletContext servletContext =
            request.getSession().getServletContext();
        InputStream in = null;
        try {
            in =
                new FileInputStream(
                    servletContext.getRealPath("/template/roleResourceTemplate.xls"));
            filename = new String("角色资源关系导入模版.xls".getBytes(), "ISO8859-1");
        }
        catch (FileNotFoundException e) {
            this.excepAndLogHandle(RoleAction.class, "下载角色与资源关系导入模版", e, false);
        }
        catch (UnsupportedEncodingException e) {
            this.excepAndLogHandle(RoleAction.class, "下载角色与资源关系导入模版", e, false);
        }
        return in;
    }
    
    /**
     * 通过上传EXCEl文件，导入角色与资源关系
     * 
     * @Title uploadExcelToBacthImportRoleResource
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String uploadExcelToBacthImportRoleResource() {
        try {
            HttpServletRequest request = this.getRequest();
            ServletContext servletContext =
                request.getSession().getServletContext();
            String fileUrl =
                FileUtil.upload(uploadAttach,
                    filename,
                    "upload/importRoleResource");
            String message =
                roleService.importRoleResource(servletContext.getRealPath("/")
                    + fileUrl);
            JsonUtil.outJson("{success:'true',msg:'" + message + "'}");
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class,
                "通过上传EXCEl文件，导入角色与资源关系",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 通过ID和操作编码判断当前角色是否存在
     * 
     * @Title userIsExist
     * @author ndy
     * @date 2014年3月26日
     * @return String
     */
    public String roleIsExist() {
        String roleId = this.getRequest().getParameter("roleId");
        String code = RequestUtil.getString(getRequest(), "code");
        try {
            int size = this.roleService.roleIsExist(roleId, code);
            if (code.equals("delete")) {
                if (size != 0) {
                    JsonUtil.outJson("{success:true,msg:'选择的数据已删除，列表已刷新'}");
                }
                else {
                    JsonUtil.outJson("{success:false,msg:'角色存在'}");
                }
            }
            else {
                if (size != 0) {
                    JsonUtil.outJson("{success:true,msg:'角色存在'}");
                }
                else {
                    JsonUtil.outJson("{success:false,msg:'选择的数据已删除，列表已刷新'}");
                }
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class,
                "通过ID和操作编码判断当前角色是否存在",
                e,
                false);
            JsonUtil.outJson("{success:false,msg:'操作失败'}");
        }
        return null;
    }
    
    /**
     * 添加角色成员范围
     * 
     * @Title addRoleMember
     * @author lizhengc
     * @date 2014年09月16日
     * @return String
     */
    public String addRoleMember() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            String flag = roleService.addRoleMember(paramsMap);
            // 根据添加返回结果判断是否是添加成功
            if ("success".equals(flag)) {
                this.excepAndLogHandle(RoleAction.class, "添加群组权限", null, true);
                JsonUtil.outJson("{success:true,msg:'添加群组权限成功！'}");
            }
            else {
                this.excepAndLogHandle(RoleAction.class, "添加群组权限", null, false);
                JsonUtil.outJson("{success:false,msg:'添加群组权限失败！'}");
            }
            
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "添加群组权限", e, false);
            JsonUtil.outJson("{success:false,msg:'添加群组权限失败！'}");
        }
        return null;
    }
    
    /**
     * 添加角色组织权限
     * 
     * @Title addRoleOrg
     * @author lizhengc
     * @date 2014年09月16日
     * @return String
     */
    public String addRoleOrg() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            String flag = roleService.addRoleOrg(paramsMap);
            // 根据添加返回结果判断是否是添加成功
            if ("success".equals(flag)) {
                this.excepAndLogHandle(RoleAction.class, "添加角色组织权限", null, true);
                JsonUtil.outJson("{success:true,msg:'添加角色组织权限成功！'}");
            }
            else {
                this.excepAndLogHandle(RoleAction.class,
                    "添加角色组织权限",
                    null,
                    false);
                JsonUtil.outJson("{success:false,msg:'添加角色组织权限失败！'}");
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "添加角色组织权限", e, false);
            JsonUtil.outJson("{success:false,msg:'添加角色组织权限失败！'}");
        }
        return null;
    }
    
    /**
     * 获得所有的群组权限
     * 
     * @Title getRoleMember
     * @author lizhengc
     * @date 2014年9月16日
     * @return String
     */
    public String getRoleMember() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        
        ListVo<GroupVo> vos = null;
        try {
            vos = this.roleService.getRoleMember(paramsMap);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "获得所有的群组权限", e, false);
        }
        JsonUtil.outJson(vos);
        return null;
    }
    
    /**
     * 删除权限查询群组是否存在
     * 
     * @Title checkRGIsExist
     * @author lizhegnc
     * @date 2014年9月17日
     * @return String
     */
    public String checkRGIsExist() {
        String msg = "{'success':true,'msg':''}";
        // 群组id
        String ids = getRequest().getParameter("ids");
        String roleId = getRequest().getParameter("roleId");
        if (!"".equals(ids) && !"0".equals(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                try {
                    roleMemberScope =
                        this.roleService.checkRGIsExist(id, roleId);
                }
                catch (BusinessException e) {
                    this.excepAndLogHandle(RoleAction.class,
                        "检查群组权限是否存在",
                        e,
                        false);
                }
                if (roleMemberScope == null) {
                    msg =
                        "{'success':false,'msg':'【"
                            + roleMemberScope.getGroup().getGroupName()
                            + "】已删除，列表已刷新'}";
                    break;
                }
            }
        }
        JsonUtil.outJson(msg);
        return null;
    }
    
    /**
     * 删除权限查询用户是否存在
     * 
     * @Title checkRUIsExist
     * @author lizhegnc
     * @date 2014年9月17日
     * @return String
     */
    public String checkRUIsExist() {
        String msg = "{'success':true,'msg':''}";
        // 用户id
        String ids = getRequest().getParameter("ids");
        String roleId = getRequest().getParameter("roleId");
        if (!"".equals(ids) && !"0".equals(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                try {
                    roleMemberScope =
                        this.roleService.checkRUIsExist(id, roleId);
                }
                catch (BusinessException e) {
                    this.excepAndLogHandle(RoleAction.class,
                        "检查用户权限是否存在",
                        e,
                        false);
                }
                if (roleMemberScope == null) {
                    msg =
                        "{'success':false,'msg':'【"
                            + roleMemberScope.getGroup().getGroupName()
                            + "】已删除，列表已刷新'}";
                    break;
                }
            }
        }
        JsonUtil.outJson(msg);
        return null;
    }
    
    /**
     * 删除权限群组
     * 
     * @Title delRoleGroup
     * @author lizhegnc
     * @date 2014年9月17日
     * @return String
     */
    public String delRoleGroup() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            this.roleService.delRoleGroup(paramsMap);
            JsonUtil.outJson("{'success':true,msg:'删除权限群组成功'}");
            this.excepAndLogHandle(RoleAction.class, "删除权限群组", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{'success':false,msg:'删除权限群组失败！请稍候重试！'}");
            this.excepAndLogHandle(RoleAction.class, "删除权限群组", e, false);
        }
        return null;
    }
    
    /**
     * 删除角色组织权限
     * 
     * @Title delRoleOrg
     * @author lizhegnc
     * @date 2014年9月17日
     * @return String
     */
    public String delRoleOrg() {
        // 组织id
        String orgIds = this.getRequest().getParameter("orgIds");
        // 权限id
        String roleMemeberIds = getRequest().getParameter("roleMemeberIds");
        try {
            this.roleService.delRoleOrg(roleMemeberIds, orgIds);
            JsonUtil.outJson("{'success':true,msg:'删除角色组织权限成功'}");
            this.excepAndLogHandle(RoleAction.class, "删除角色组织权限", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{'success':false,msg:'删除角色组织权限失败！请稍候重试！'}");
            this.excepAndLogHandle(RoleAction.class, "删除角色组织权限", e, false);
        }
        return null;
    }
    
    /**
     * 删除角色用户范围权限
     * 
     * @Title delScopeUser
     * @author lizhegnc
     * @date 2014年9月17日
     * @return String
     */
    public String delScopeUser() {
    	// 组织id
    	String userIds = this.getRequest().getParameter("userIds");
    	// 权限id
    	String roleMemeberIds = getRequest().getParameter("roleMemeberIds");
    	try {
    		this.roleService.delScopeUser(roleMemeberIds, userIds);
    		JsonUtil.outJson("{'success':true,msg:'删除角色用户权限成功'}");
    		this.excepAndLogHandle(RoleAction.class, "删除角色用户权限", null, true);
    	}
    	catch (Exception e) {
    		JsonUtil.outJson("{'success':false,msg:'删除角色用户权限失败！请稍候重试！'}");
    		this.excepAndLogHandle(RoleAction.class, "删除角色用户权限", e, false);
    	}
    	return null;
    }
    
    /**
     * 删除权限用户
     * 
     * @Title delRoleUser
     * @author lizhegnc
     * @date 2014年9月17日
     * @return String
     */
    public String delRoleUser() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        
        try {
            this.roleService.delRoleUser(paramsMap);
            JsonUtil.outJson("{'success':true,msg:'删除权限用户成功'}");
            this.excepAndLogHandle(RoleAction.class, "删除权限用户", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{'success':false,msg:'删除权限用户失败！请稍候重试！'}");
            this.excepAndLogHandle(RoleAction.class, "删除权限用户", e, false);
        }
        return null;
    }
    
    /**
     * 获得所有的用户权限
     * 
     * @Title getRoleMember
     * @author lizhengc
     * @date 2014年9月16日
     * @return String
     */
    public String getRoleMemberByUser() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        
        ListVo<UserVo> vos = null;
        try {
            vos = this.roleService.getRoleMemberByUser(paramsMap);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "获得所有的用户权限", e, false);
        }
        JsonUtil.outJson(vos);
        return null;
    }
    
    /**
     * 获得所有的用户权限
     * 
     * @Title getRoleMember
     * @author lizhengc
     * @date 2014年9月16日
     * @return String
     */
    public String getUserListByRoleMemberId() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        
        ListVo<UserVo> vos = null;
        try {
            vos = this.roleService.getScopeByMenberId(paramsMap);
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "获得所有的用户权限", e, false);
        }
        JsonUtil.outJson(vos);
        return null;
    }
    
    /**
     * 添加角色用户范围权限
     * 
     * @Title addRoleUser
     * @author lizhengc
     * @date 2014年09月16日
     * @return String
     */
    public String addRoleUser() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            String flag = roleService.addRoleUser(paramsMap);
            // 根据添加返回结果判断是否是添加成功
            if ("success".equals(flag)) {
                this.excepAndLogHandle(RoleAction.class,
                    "添加角色部门范围权限",
                    null,
                    true);
                JsonUtil.outJson("{success:true,msg:'添加角色部门范围权限成功！'}");
            }
            else {
                this.excepAndLogHandle(RoleAction.class,
                    "添加角色用户范围权限",
                    null,
                    false);
                JsonUtil.outJson("{success:false,msg:'添加角色部门范围权限失败！'}");
            }
            
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "添加角色部门范围权限", e, false);
            JsonUtil.outJson("{success:false,msg:'添加角色部门范围权限失败！'}");
        }
        return null;
    }
    
    /**
     * 添加角色的用户 数据范围。
     * 
     * @Title addMeberUserScope
     * @author lizhengc
     * @date 2015年03月31日
     * @return String
     */
    public String addMeberUserScope() {
    	Map<String, String> paramsMap = RequestUtil.getParameterMap(getRequest());
    	try {
    		String flag = roleService.addRoleUserScope(paramsMap);
    		// 根据添加返回结果判断是否是添加成功
    		if ("success".equals(flag)) {
    			this.excepAndLogHandle(RoleAction.class,
    					"添加角色用户范围权限",
    					null,
    					true);
    			JsonUtil.outJson("{success:true,msg:'添加角色用户范围权限成功！'}");
    		}
    		else {
    			this.excepAndLogHandle(RoleAction.class,
    					"添加角色用户范围权限",
    					null,
    					false);
    			JsonUtil.outJson("{success:false,msg:'添加角色用户范围权限失败！'}");
    		}
    		
    	}
    	catch (Exception e) {
    		this.excepAndLogHandle(RoleAction.class, "添加角色用户范围权限", e, false);
    		JsonUtil.outJson("{success:false,msg:'添加角色用户范围权限失败！'}");
    	}
    	return null;
    }
    
    /**
     * 获取角色对应的组织
     * 
     * @Title getRoleOrgForTree
     * @author lizhengc
     * @date 2013-09-18
     * @return String
     */
    public String getRoleOrgForTree() {
        try {
            // 获取权限id
            String roleMemberId =
                this.getRequest().getParameter("roleMemberIds");
            treeNodeList = new ArrayList<TreeNode>();
            List<Integer> resIds = new ArrayList<Integer>();
            smList = new ArrayList<ScopeMember>();
            // 获取角色组织表中的权限id
            List<ScopeMember> list =
                this.roleService.getOrgSM(NumberUtils.toInt(roleMemberId));
            // 如果角色组织表不为空
            if (list != null && list.size() > 0) {
                // 循环取出组织id
                for (ScopeMember rr : list) {
                    //if (rr.getOrg().getOrganization() != null) {
                        resIds.add(rr.getOrg().getOrgId());
                        //resIds.add(rr.getOrg().getOrganization().getOrgId());
                    //}
                }
            }
            // 获取当前组织的一级菜单
            List<Organization> res = roleService.getFirstLevelOrg();
            
            sb.append("[");
            for (Organization r : res) {
                sb.append("{\"nodeId\":\"" + r.getOrgId() + "\",");
                sb.append("\"checked\":" + resIds.contains(r.getOrgId()) + ",");
                sb.append("\"leaf\":" + decideIsLeafOrg(r) + ",");
                sb.append("\"text\":\"" + r.getOrgName() + "\",");
                sb.append("\"children\":[");
                getByParentOrg(r, resIds);
                sb.append("]},");
            }
            sb.append("]");
            
            String result = sb.toString().replaceAll(",]", "]");
            
            JsonUtil.outJsonArray(result);
            
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "获取角色对应的组织", e, false);
        }
        return null;
    }
    
    /**
     * 获取组织下的子组织
     * 
     * @Title getByParentOrg
     * @author wangll
     * @date 2014年9月19日
     * @param rr 组织对象
     * @param resIds 查询取出的组织id
     */
    private void getByParentOrg(Organization rr, List<Integer> resIds) {
        try {
            List<Organization> res =
                roleService.getAllSonOrgByParentId(rr.getOrgId() + "");
            if (res.size() != 0) {
                for (Organization r : res) {
                    sb.append("{\"nodeId\":\"" + r.getOrgId() + "\",");
                    sb.append("\"text\":\"" + r.getOrgName() + "\",");
                    sb.append("\"checked\":" + resIds.contains(r.getOrgId())
                        + ",");
                    sb.append("\"leaf\":" + decideIsLeafOrg(r) + ",");
                    sb.append("\"children\":[");
                    getByParentOrg(r, resIds);
                    sb.append("]},");
                }
                
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(RoleAction.class, "获取组织下的子组织", e, false);
        }
    }
    
    /**
     * 判断树节点是否为叶子节点
     * 
     * @Title decideIsLeafOrg
     * @author lizhengc
     * @date 2013-09-19
     * @param r 组织对象
     * @return boolean
     */
    public boolean decideIsLeafOrg(Organization r) {
        boolean result = true;
        Set<Organization> sonOrganization = r.getOrganizations();
        for (Organization sonOrg : sonOrganization) {
            if (sonOrg.getStatus() != Constant.STATUS_IS_DELETED) {
                result = false;
                break;
            }
        }
        return result;
    }
    
    public String execute() {
        return SUCCESS;
    }
    
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public List<Resource> getResourceList() {
        return resourceList;
    }
    
    public Resource getResource() {
        return resource;
    }
    
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    public String getResourceIds() {
        return resourceIds;
    }
    
    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
    
    public String getRoleId() {
        return roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public File getUploadAttach() {
        return uploadAttach;
    }
    
    public void setUploadAttach(File uploadAttach) {
        this.uploadAttach = uploadAttach;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public IResourceService getResourceService() {
        return resourceService;
    }
    
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    public ArrayList<TreeNode> getTreeNodeList() {
        return treeNodeList;
    }
    
    public void setTreeNodeList(ArrayList<TreeNode> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }
    
    public List<Resource> getResList() {
        return resList;
    }
    
    public void setResList(List<Resource> resList) {
        this.resList = resList;
    }
    
    public IDictService getDictService() {
        return dictService;
    }
    
    public List<ScopeMember> getSmList() {
        return smList;
    }
    
    public void setSmList(List<ScopeMember> smList) {
        this.smList = smList;
    }
    
}
