package com.xx.system.org.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;

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
import com.xx.system.common.vo.ZTreeNodeVo;
import com.xx.system.deptgrade.action.IndexManageAction;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.service.IDictService;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.service.IOrgService;
import com.xx.system.org.service.IOrgUserService;
import com.xx.system.org.vo.OrgVo;

/**
 * 定义组织部门Action
 * 
 * @version V1.20,2013-11-25 下午3:23:42
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class OrgAction extends BaseAction {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = -1661933388897792411L;
    
    /**
     * @Fields org : 组织对象
     */
    private Organization org;
    
    /**
     * @Fields orgs : 组织集合
     */
    private List<Organization> orgs;
    
    /**
     * @Fields organizationService : 组织服务
     */
    @Resource
    public IOrgService organizationService;
    
    /**
     * @Fields dictService : 字典服务
     */
    @Resource
    public IDictService dictService;
    
    /** @Fields orgUserService : */
    @Resource
    public IOrgUserService orgUserService;
    
    /**
     * @Fields result : 返回数据
     */
    private String result;
    
    /**
     * @Fields srcOrgId : 源组织ID
     */
    private int srcOrgId;
    
    /**
     * @Fields uploadAttach : 附件上传文件
     */
    private File uploadAttach;
    
    /**
     * @Fields filename : 文件名
     */
    private String filename;
    
    /**
     * @Fields targetOrgId : 目标组织ID
     */
    private int targetOrgId;
    
    /**
     * @Fields position : 位置
     */
    private String position;
    
    /**
     * @Fields menucode : 菜单编码
     */
    private String menucode;
    
    /**
     * @Fields tip : 前台提示信息
     */
    private String tip;
    
    /**
     * @Fields treeNodeList : 节点集合
     */
    private List<TreeNode> treeNodeList;
    
    /**
     * @Fields zTreeNodes : 包含组织和用户ztree节点对象
     */
    private List<ZTreeNodeVo> zTreeNodes;
    
    /**
     * 获取组织列表
     * 
     * @Title getOrgList
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String getOrgList() {
        Map<String, String> map = RequestUtil.getParameterMap(getRequest());
        ListVo<OrgVo> list;
        try {
            list = organizationService.getOrgList(map);
            JsonUtil.outJson(list);
        }
        catch (BusinessException e) {
            this.excepAndLogHandle(OrgAction.class, "获取组织列表", e, false);
        }
        return null;
    }
    
    /**
     * 根据ID获取组织对象
     * 
     * @Title getOrgById
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return
     */
    public String getOrgById() {
        
        try {
            int id = RequestUtil.getInt(getRequest(), "orgId");
            if (id != 0) {
                Organization org = organizationService.getOrgById(id);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("success", true);
                map.put("msg", "获取组织成功");
                map.put("org.orgId", org.getOrgId());
                map.put("org.orgName", org.getOrgName());
                map.put("org.orgType.pkDictionaryId", org.getOrgType().getPkDictionaryId());
                map.put("org.orgTypeName", org.getOrgType().getDictionaryName());
                map.put("org.orgCode", org.getOrgCode());
                map.put("org.disOrder", org.getDisOrder());
                if (org.getOrganization() != null) {
                    map.put("org.organization.orgId", org.getOrganization().getOrgId());
                    map.put("org.organization.orgName", org.getOrganization().getOrgName());
                }
                
                ResponseVo vo = new ResponseVo();
                vo.setData(map);
                JsonUtil.outJson(vo);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "根据ID获取组织对象", e, false);
        }
        return null;
    }
    
    /**
     * @Title toOrgIndex
     * @author hedong
     * @Description: 组织管理页面跳转
     * @date 2013-11-25
     * @return
     */
    public String toOrgIndex() {
        List<Dictionary> dictList =
            dictService.getDictByTypeCode(Constant.ORGTYPE);
        getSession().setAttribute("orgDictList", dictList);
        return SUCCESS;
    }
    
    /**
     * 取得所以组织用于显示组织树
     * 
     * @Title getAllOrg
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String getAllOrg() {
        try {
            orgs = organizationService.getAllOrg();
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, " 取得所以组织用于显示组织树", e, false);
        }
        return SUCCESS;
    }
    
    /**
     * 添加用户时构造组织树
     * 
     * @Title getUnitTreeListForAddUser
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String getUnitTreeListForAddUser() {
        try {
            Map<String, String> params =
                RequestUtil.getParameterMap(getRequest());
            /*
             * String permissionOrgIds = (String)getSession().getAttribute("orgPermission");
             */
            if ("0".equals(params.get("parentId"))) {
                treeNodeList = organizationService.getRootOrg(params);
            }
            else {
                treeNodeList =
                    organizationService.getAllSonOrgByParentId(params);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "添加用户时构造组织树", e, false);
        }
        return SUCCESS;
    }
    
    /**
     * 添加和修改用户时构造组织树
     * 
     * @Title getUnitTreeListForModifyUser
     * @author yzg
     * @Description:
     * @date 2014-2-17
     * @return String
     */
    public String getUnitTreeListForModifyUser() {
        try {
            Map<String, String> params =
                RequestUtil.getParameterMap(getRequest());
            treeNodeList =
                organizationService.getUnitTreeListForModifyUser(params);
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "添加和修改用户时构造组织树", e, false);
        }
        return SUCCESS;
    }
    
    /**
     * 修改组织上级部门列表
     * 
     * @Title getUnitTreeListForUpdateOrg
     * @author wujialing
     * @Description: 
     * @date 2015年4月23日
     * @return
     */
    public String getUnitTreeListForUpdateOrg() {
        try {
            Map<String, String> params =
                RequestUtil.getParameterMap(getRequest());
            treeNodeList =
                organizationService.getUnitTreeListForUpdateOrg(params);
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "获取组织上级部门列表", e, false);
        }
        return SUCCESS;
    }
    
    /**
     * 返回没有复选框的组织树
     * 
     * @Title getUnitTreeListNotCheck
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return
     */
    public String getUnitTreeListNotCheck() {
        try {
            Map<String, String> params =
                RequestUtil.getParameterMap(getRequest());
            /*
             * String permissionOrgIds = (String)getSession().getAttribute("orgPermission");
             */
            if (StringUtil.isBlank(params.get("parentId"))
                || "0".equals(params.get("parentId"))) {
                treeNodeList = organizationService.getRootOrg(params);
            }
            else {
                treeNodeList =
                    organizationService.getAllSonOrgByParentId(params);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "返回没有复选框的组织树", e, false);
        }
        return SUCCESS;
    }
    
    /**
     * 返回没有复选框的组织树
     * 
     * @Title getUnitTreeListNotCheckForRoleSearch
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return
     */
    /*public String getUnitTreeListNotCheckForRoleSearch() {
        try {
            String parentId = this.getRequest().getParameter("parentId");
            String currentRoleId =
                this.getRequest().getParameter("currentRoleId");
            if ("0".equals(parentId)) {
                treeNodeList =
                    organizationService.getRootOrgForRoleSearch(currentRoleId);
            }
            else {
                treeNodeList =
                    organizationService.getAllSonOrgByParentIdForRoleSearch(parentId,
                        currentRoleId);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "返回没有复选框的组织树", e, false);
        }
        return SUCCESS;
    }*/
    
    /**
     * 返回非叶子节点的组织树
     * 
     * @Title getOrgTreeList
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return
     */
    public String getOrgTreeList() {
        try {
            Map<String, String> params =
                RequestUtil.getParameterMap(getRequest());
            String permissionOrgIds =
                (String)getSession().getAttribute("orgPermission");
            params.put("permissionOrgIds", permissionOrgIds);
            
            if ("0".equals(params.get("parentId"))) {
                treeNodeList = organizationService.getRootOrg(params);
            }
            else {
                treeNodeList =
                    organizationService.getAllSonOrgByParentId(params);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "返回非叶子节点的组织树", e, false);
        }
        return SUCCESS;
    }
    
    /**
     * 判断组织树节点是否为叶子节点
     * 
     * @Title decideIsLeaf
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @param org 组织对象
     * @return
     */
    public boolean decideIsLeaf(Organization org) {
        boolean result = true;
        try {
            Set<Organization> sonOrgs = org.getOrganizations();
            for (Organization sonOrg : sonOrgs) {
                if (sonOrg.getStatus() != Constant.STATUS_IS_DELETED) {
                    result = false;
                    break;
                }
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "判断组织树节点是否为叶子节点", e, false);
        }
        return result;
    }
    
    /**
     * 根据用户ID和菜单的编码取得用户在某个菜单下能够操作的组织列表
     * 
     * @Title getUserOrgs
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    /*public String getUserOrgs() {
        try {
            orgs =
                organizationService.getUserOrgs(((User)super.getSession()
                    .getAttribute(Constant.CURRENT_USER)).getUserId(), menucode);
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class,
                "根据用户ID和菜单的编码取得用户在某个菜单下能够操作的组织列表",
                e,
                false);
        }
        return SUCCESS;
    }*/
    
    /**
     * @Title getUserOrgsTreeNodes
     * @author hedong
     * @Description: 根据用户ID和菜单的编码取得用户在某个菜单下能够操作的组织和用户列表，以树的形式返回
     * @date 2013-11-25
     * @return
     */
    /*public String getUserOrgsTreeNodes() {
        try {
            zTreeNodes =
                organizationService.getUserOrgsTreeNodes(((User)super.getSession()
                    .getAttribute(Constant.CURRENT_USER)).getUserId(),
                    menucode);
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class,
                "根据用户ID和菜单的编码取得用户在某个菜单下能够操作的组织和用户列表，以树的形式返回",
                e,
                false);
        }
        return SUCCESS;
    }*/
    
    /**
     * 添加组织
     * 
     * @Title addOrg
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String addOrg() {
        String msg = "{success:'false',msg:'组织添加失败'}";
        try {
            if (org.getOrganization() == null
                || org.getOrganization().getOrgId() == 0) {
                org.setOrganization(null);
            }
            org.setEnable(1);
            
            organizationService.addOrg(org);
            msg = "{success:'true',msg:'组织添加成功',orgId:" + org.getOrgId() + "}";
            this.excepAndLogHandle(OrgAction.class, "添加组织", null, true);
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "添加组织", e, false);
        }
        JsonUtil.outJson(msg);
        return null;
    }
    
    /**
     * @Title updateOrg
     * @author hedong
     * @Description: 修改组织
     * @date 2013-11-25
     * @return String
     */
    public String updateOrg() {
        try {
            if (!StringUtil.isBlank(org.getOrgName())) {
                if (!organizationService.checkOrgName(org, org.getOrgName())) {
                    JsonUtil.outJson("{success:'false',msg:'修改失败，同级下已存在名为：“"
                        + org.getOrgName() + "”的组织'}");
                    return null;
                }
            }
            if (!StringUtil.isBlank(org.getOrgCode())) {
                if (!organizationService.checkOrgCode(org)) {
                    JsonUtil.outJson("{success:'false',msg:'修改失败，已存在编码为：“"
                        + org.getOrgCode() + "”的组织'}");
                    return null;
                }
            }
            Dictionary dict =
                dictService.getDictById(org.getOrgType().getPkDictionaryId());
            Organization updateOrg =
                this.organizationService.getOrgById(org.getOrgId());
            // org.setOrgTypeUUID(dict.getDictUUID());
            if (org.getOrganization() != null)
            {
                updateOrg.setOrganization(org.getOrganization());
            }
            updateOrg.setOrgType(dict);
            updateOrg.setOrgName(org.getOrgName());
            updateOrg.setDisOrder(org.getDisOrder());
            updateOrg.setEnable(1);
            organizationService.updateOrg(updateOrg);
            JsonUtil.outJson("{success:'true',msg:'修改成功'}");
            this.excepAndLogHandle(OrgAction.class, "修改组织", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:'false',msg:'修改失败'}");
            this.excepAndLogHandle(OrgAction.class, "修改组织", e, false);
        }
        return null;
    }
    
    /**
     * 修改组织排序
     * 
     * @Title updateOrgDisOrder
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String updateOrgDisOrder() {
        try {
            organizationService.updateOrder(srcOrgId, targetOrgId, position);
            JsonUtil.outJson("{success:'true',msg:'修改成功'}");
            this.excepAndLogHandle(OrgAction.class, "修改组织排序", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:'false',msg:'修改失败'}");
            this.excepAndLogHandle(OrgAction.class, "修改组织排序", e, false);
        }
        return null;
    }
    
    /**
     * 删除组织
     * 
     * @Title delOrg
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String delOrg() {
        try {
            // 删除的时候验证当前组织下是否有用户存在，如存在提示，并不允许删除
            String orgIds = RequestUtil.getString(getRequest(), "orgIds");
            if (orgIds.split(",").length == 1)
            {
                for (String orgId : orgIds.split(",")) {
                    org = organizationService.getOrgById(NumberUtils.toInt(orgId));
                    int orgUserCounts = orgUserService.getUserCountsByOrgIds(orgId);
                    boolean exsistLeafOrg = organizationService.isLeafOrg(org);
                    if (orgUserCounts == 0) {
                        if (exsistLeafOrg) {
                            org.setStatus(Constant.STATUS_IS_DELETED);
                            organizationService.updateOrg(org);
                            result = "{success:true,msg:'删除组织成功'}";
                        }
                        else {
                            result =
                                "{success:false,msg:'组织【" + org.getOrgName()
                                    + "】下存在下级组织，不允许删除'}";
                        }
                    }
                    else {
                        result =
                            "{success:false,msg:'组织【" + org.getOrgName()
                                + "】或其子组织下有用户存在，不允许删除'}";
                    }
                }
            }
            else
            {
                //如果传入了多个组织
              String msg =  organizationService.isCandelOrgIds(orgIds);
              if (StringUtil.isEmpty(msg))
              {
                  for (String orgId : orgIds.split(",")) {
                      org = organizationService.getOrgById(NumberUtils.toInt(orgId));
                      org.setStatus(Constant.STATUS_IS_DELETED);
                      organizationService.updateOrg(org);
                  }
                  result = "{success:true,msg:'删除组织成功'}";
              }else{
                  result =
                      "{success:false,msg:'"+msg+"'}";
              }
            }
            this.excepAndLogHandle(OrgAction.class, "删除组织", null, true);
        }
        catch (Exception e) {
            result = "{success:false,msg:'删除组织失败'}";
            this.excepAndLogHandle(OrgAction.class, "删除组织", e, false);
        }
        JsonUtil.outJson(result);
        return null;
    }
    
    /**
     * 上传EXCEL，导入组织
     * 
     * @Title uploadExcelToBacthImportOrg
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String uploadExcelToBacthImportOrg() {
        try {
            HttpServletRequest request = this.getRequest();
            ServletContext servletContext =
                request.getSession().getServletContext();
            String fileUrl =
                FileUtil.upload(uploadAttach, filename, "upload/importOrg");
            String message =
                organizationService.importOrg(servletContext.getRealPath("/")
                    + fileUrl);
            JsonUtil.outJson("{success:'true',msg:'" + message + "'}");
            this.excepAndLogHandle(OrgAction.class, "上传EXCEL，导入组织", null, true);
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "上传EXCEL，导入组织", e, false);
        }
        return null;
    }
    
    /**
     * 下载组织导入模版
     * 
     * @Title getExcelTemplateInputStream
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return
     */
    public InputStream getExcelTemplateInputStream() {
        HttpServletRequest request = this.getRequest();
        ServletContext servletContext =
            request.getSession().getServletContext();
        InputStream in = null;
        try {
            in =
                new FileInputStream(
                    servletContext.getRealPath("/template/orgTemplate.xls"));
            filename = new String("平台组织导入模版.xls".getBytes(), "ISO8859-1");
        }
        catch (Exception e) {
            this.excepAndLogHandle(OrgAction.class, "下载组织导入模版", e, false);
        }
        return in;
    }
    
    /**
     * 验证组织
     * 
     * @Title validateOrgProperties
     * @author hedong
     * @Description:
     * @date 2013-11-25
     * @return
     */
    public String validateOrgProperties() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        Map<String, Object> vaildator;
        try {
            vaildator = organizationService.validateOrgProperties(paramsMap);
            JsonUtil.outJson(vaildator);
        }
        catch (BusinessException e) {
            this.excepAndLogHandle(OrgAction.class, "验证组织", e, false);
        }
        
        return null;
    }
    
    /**
     * 通过ID和操作编码判断当前组织是否存在
     * 
     * @Title orgIsExist
     * @author ndy
     * @date 2014年3月27日
     * @return
     */
    public String orgIsExist() {
        String orgId = RequestUtil.getString(getRequest(), "orgId");
        String code = RequestUtil.getString(getRequest(), "code");
        try {
            int size = this.organizationService.orgIsExist(orgId, code);
            if (code.equals("delete")) {
                if (size != 0) {
                    JsonUtil.outJson("{success:true,msg:'选择的数据已删除，列表已刷新'}");
                }
                else {
                    JsonUtil.outJson("{success:false,msg:'组织存在'}");
                }
            }
            else {
                if (size != 0) {
                    JsonUtil.outJson("{success:true,msg:'组织存在'}");
                }
                else {
                    JsonUtil.outJson("{success:false,msg:'选择的数据已删除，列表已刷新'}");
                }
            }
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'操作失败'}");
            this.excepAndLogHandle(OrgAction.class,
                "通过ID和操作编码判断当前组织是否存在",
                e,
                false);
        }
        return null;
    }
    
    /**
	 * 锁定和解锁组织
	 * @return
	 */
	public String lockupOrg() {
		String msg = "{success:'false',msg:'锁定组织失败'}";
		String msg1 = "{success:'false',msg:'解锁组织失败'}";
		
		String id = getRequest().getParameter("orgId");
		String enable = getRequest().getParameter("enable");
		Integer orgId = StringUtil.isNotBlank(id) ? Integer.valueOf(id) : 0;
		Integer en = StringUtil.isNotBlank(enable) ? Integer.valueOf(enable) : 0;
		try {
			organizationService.lockupOrg(orgId);
			msg = "{success:'true',msg:'锁定组织成功'}";
			msg1 = "{success:'true',msg:'解锁组织成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "锁定和解锁组织", e, false);
		}
		
		if (en == 1) {
			JsonUtil.outJson(msg);
		}
		else {
			JsonUtil.outJson(msg1);
		}
		return null;
	}
    
    /**
     * <p>
     * Title execute
     * </p>
     * <p>
     * Author wanglc
     * </p>
     * <p>
     * Description ActionSupport#execute()
     * </p>
     * 
     * @return
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        return SUCCESS;
    }
    
    public Organization getOrg() {
        return org;
    }
    
    public void setOrg(Organization org) {
        this.org = org;
    }
    
    /**
     * @Title setOrganizationService
     * @author hedong
     * @Description: setOrganizationService
     * @date 2013-12-6
     * @param organizationService
     */
    public void setOrganizationService(IOrgService organizationService) {
        this.organizationService = organizationService;
    }
    
    public List<Organization> getOrgs() {
        return orgs;
    }
    
    public void setOrgs(List<Organization> orgs) {
        this.orgs = orgs;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public int getSrcOrgId() {
        return srcOrgId;
    }
    
    public void setSrcOrgId(int srcOrgId) {
        this.srcOrgId = srcOrgId;
    }
    
    public int getTargetOrgId() {
        return targetOrgId;
    }
    
    public void setTargetOrgId(int targetOrgId) {
        this.targetOrgId = targetOrgId;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getMenucode() {
        return menucode;
    }
    
    public void setMenucode(String menucode) {
        this.menucode = menucode;
    }
    
    public String getTip() {
        return tip;
    }
    
    public void setTip(String tip) {
        this.tip = tip;
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
    
    public List<TreeNode> getTreeNodeList() {
        return treeNodeList;
    }
    
    /**
     * @Title setUserService
     * @author hedong
     * @Description: userService
     * @date 2013-12-6
     * @param userService
     */
    /*public void setUserService(IUserService userService) {
        this.userService = userService;
    }*/
    
    public List<ZTreeNodeVo> getZTreeNodes() {
        return zTreeNodes;
    }
}
