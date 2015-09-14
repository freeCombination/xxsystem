package com.xx.system.user.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.constant.Constant;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.org.entity.Organization;
import com.xx.system.user.entity.GroupMember;
import com.xx.system.user.service.IGroupMemberService;
import com.xx.system.user.service.IGroupService;
import com.xx.system.user.service.IUserService;
import com.xx.system.user.vo.GroupVo;
import com.xx.system.user.vo.UserVo;

/**
 * 群组管理Action
 * 
 * @version V1.40,2014年9月15日 上午8:52:00
 * @see [相关类/方法]
 * @since V1.40
 */
public class GroupManagerAction extends BaseAction {
    
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    
    StringBuffer buffer = new StringBuffer();
    
    /**
     * @Fields groupService : 群组服务类
     */
    @Resource
    private IGroupService groupService;
    
    /**
     * @Fields userService : 用户服务类
     */
    @Resource
    private IUserService userService;
    
    /**
     * @Fields groupMemberService : 群组成员服务类
     */
    @Resource
    private IGroupMemberService groupMemberService;
    
    /**
     * @Fields groupVo : 参数传输对象
     */
    private GroupVo group;
    
    /**
     * @return the group
     */
    public GroupVo getGroup() {
        return group;
    }
    
    /**
     * @param group the group to set
     */
    public void setGroup(GroupVo group) {
        this.group = group;
    }
    
    /**
     * @return the groupService
     */
    public IGroupService getGroupService() {
        return groupService;
    }
    
    /**
     * @param groupService the groupService to set
     */
    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }
    
    /**
     * @return the userService
     */
    public IUserService getUserService() {
        return userService;
    }
    
    /**
     * @param userService the userService to set
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    /**
     * @return the groupMemberService
     */
    public IGroupMemberService getGroupMemberService() {
        return groupMemberService;
    }
    
    /**
     * @param groupMemberService the groupMemberService to set
     */
    public void setGroupMemberService(IGroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }
    
    /**
     * 跳转到群组管理页面
     * 
     * @Title toGroupManagement
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String toGroupManagement() {
        return SUCCESS;
    }
    
    /**
     * 获取群组
     * 
     * @Title getGroup
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String getGroups() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            JsonUtil.outJson(groupService.getGroup(paramsMap));
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "获取群组", e, false);
        }
        return null;
    }
    
    /**
     * 添加群组
     * 
     * @Title addGroupInfo
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String addGroupInfo() {
        try {
            String msg = groupService.addGroup(group);
            if ("success".equals(msg)) {
                JsonUtil.outJson("{'success':'true','msg':'新增群组成功'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "新增群组",
                    null,
                    true);
            }
            else {
                JsonUtil.outJson("{'success':'false','msg':'新增群组失败'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "新增群组",
                    null,
                    false);
            }
        }
        catch (Exception e) {
            JsonUtil.outJson("{'success':'false','msg':'新增群组失败'}");
            this.excepAndLogHandle(GroupManagerAction.class, "添加群组", e, false);
        }
        return null;
    }
    
    /**
     * 删除群组
     * 
     * @Title addGroupInfo
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String delGroupInfo() {
        // 获取群组id
        String ids = this.getRequest().getParameter("ids");
        try {
            String result = groupService.delGroup(ids);
            if ("success".equals(result)) {
                JsonUtil.outJson("{'success':'true','msg':'删除群组成功！'}");
            }
            else if ("error".equals(result)) {
                JsonUtil.outJson("{'success':'false','msg':'群组删除失败，请刷新！'}");
            }
            else {
                JsonUtil.outJson("{'success':'false','msg':'" + result + "'}");
            }
        }
        catch (Exception e) {
            JsonUtil.outJson("{'success':'false','msg':'删除群组失败！'}");
            this.excepAndLogHandle(GroupManagerAction.class, "删除群组组", e, false);
        }
        return null;
    }
    
    /**
     * 删除群组前查询该群组下是否有群组成员信息
     * 
     * @Title groupExitInfo
     * @author lizhengc
     * @date 2014年9月24日
     * @return String
     */
    public String groupExsitInfo() {
        try {
            String ids = this.getRequest().getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                String[] idss = ids.split(",");
                for (String id : idss) {
                    GroupMember gm = groupService.getGMByGroId(id);
                    if ("".equals(gm) || gm != null) {
                        JsonUtil.outJson("{'success':'true','msg':'删除群组【"
                            + gm.getGroup().getGroupName()
                            + "】时会将群组下的相关信息一并删除，确认删除吗？'}");
                        return null;
                    }
                }
                JsonUtil.outJson("{'success':'false','msg':'确认删除数据吗？'}");
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "删除群组", e, false);
        }
        return null;
    }
    
    /**
     * 修改群组
     * 
     * @Title addGroupInfo
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String updateGroupInfo() {
        try {
            String msg = groupService.updateGroup(group);
            if ("success".equals(msg)) {
                JsonUtil.outJson("{'success':'false','msg':'修改成功！'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "修改群组",
                    null,
                    true);
            }
            else {
                JsonUtil.outJson("{'success':'false','msg':'修改失败！'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "修改群组",
                    null,
                    false);
            }
        }
        catch (Exception e) {
            JsonUtil.outJson("{'success':'false','msg':'修改失败！'}");
            this.excepAndLogHandle(GroupManagerAction.class, "修改群组", e, false);
        }
        return null;
    }
    
    /**
     * 验证群组名称
     * 
     * @Title validateGroupName
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String validateGroupName() {
        // 声明map集合，用于存储参数
        Map<String, Object> validate = new HashMap<String, Object>();
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            // 查询工作组名称
            validate = groupService.validateGroupName(paramsMap);
            JsonUtil.outJson(validate);
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "验证群组名字", e, false);
        }
        return null;
    }
    
    /**
     * 获取群组用户列表
     * 
     * @Title getUserList
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String getUserList() {
        try {
            Map<String, String> params =
                RequestUtil.getParameterMap(getRequest());
            ListVo<UserVo> userVoListVo = this.userService.getUserList(params);
            JsonUtil.outJson(userVoListVo);
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "获取群组用户", e, false);
        }
        return null;
    }
    
    /**
     * 获取群组成员列表
     * 
     * @Title getGroupMembers
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String getGroupMembers() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            JsonUtil.outJson(groupMemberService.getGroupMember(paramsMap));
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "获取群组成员", e, false);
        }
        return null;
    }
    
    /**
     * 获取群组部门列表
     * 
     * @Title getDept
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String getDept() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            JsonUtil.outJson(groupMemberService.getDept(paramsMap));
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "获取群组部门", e, false);
        }
        return null;
    }
    
    /**
     * 添加群组成员
     * 
     * @Title addGroupMember
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String addGroupMember() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            String flag = groupMemberService.addGroupMember(paramsMap);
            // 根据添加返回结果判断是否是添加成功
            if ("success".equals(flag)) {
                this.excepAndLogHandle(GroupManagerAction.class,
                    "添加群组成员",
                    null,
                    true);
                JsonUtil.outJson("{success:true,msg:'添加群组成员成功！'}");
            } 
            else if ("exist".equals(flag)) {
                JsonUtil.outJson("{'success':false,'msg':'此用户已经存在！'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "新增群组",
                    null,
                    false);
            }
            else {
                this.excepAndLogHandle(GroupManagerAction.class,
                    "添加群组成员",
                    null,
                    false);
                JsonUtil.outJson("{success:false,msg:'添加群组成员失败！'}");
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "添加群组成员", e, false);
            JsonUtil.outJson("{success:false,msg:'添加群组成员失败！'}");
        }
        return null;
    }
    
    /**
     * 删除群组成员
     * 
     * @Title delGroupMember
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String delGroupMember() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            String flag = groupMemberService.delGroupMember(paramsMap);
            // 判断是否删除成功
            if ("success".equals(flag)) {
                JsonUtil.outJson("{'success':'true','msg':'删除群组成员成功！'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "删除群组成员",
                    null,
                    true);
            }
            else {
                JsonUtil.outJson("{'success':'false','msg':'删除群组成员失败！'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "删除群组成员",
                    null,
                    false);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "删除群组成员", e, false);
            JsonUtil.outJson("{'success':'false','msg':'删除群组成员失败！'}");
        }
        return null;
    }
    
    /**
     * 添加部门
     * 
     * @Title addDept
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String addDept() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            String result = groupMemberService.addDept(paramsMap);
            if ("success".equals(result)) {
                JsonUtil.outJson("{'success':'true','msg':'添加部门成功！'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "添加群组部门",
                    null,
                    true);
            }
            else {
                JsonUtil.outJson("{'success':'false','msg':'添加部门失败！'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "添加群组部门",
                    null,
                    false);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "添加群组部门", e, false);
        }
        return null;
    }
    
    /**
     * 删除群组部门
     * 
     * @Title delDept
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String delDept() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            String flag = groupMemberService.delDept(paramsMap);
            // 判断是否删除成功
            if ("success".equals(flag)) {
                JsonUtil.outJson("{'success':'true','msg':'删除部门成功！'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "删除群组部门",
                    null,
                    true);
            }
            else {
                JsonUtil.outJson("{'success':'false','msg':'删除部门失败！'}");
                this.excepAndLogHandle(GroupManagerAction.class,
                    "删除群组部门",
                    null,
                    false);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class, "删除群组部门", e, false);
            JsonUtil.outJson("{'success':'false','msg':'删除部门失败！'}");
        }
        return null;
    }
    
    /**
     * 获取群组所属组织的用户
     * 
     * @Title getDeptByOrgId
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String getUser() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            JsonUtil.outJson(groupMemberService.getUserList(paramsMap));
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class,
                "获取群组所属组织的用户",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 获取群组组织机构树
     * 
     * @Title getRoleOrgForTree
     * @author tangh
     * @date 2014年9月15日
     * @return String
     */
    public String getOrgForTree() {
        // 获取群组ID
        String groupId = this.getRequest().getParameter("groupId");
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupId", groupId);
        // 用户存储组织ID
        List<Integer> orgIds = new ArrayList<Integer>();
        try {
            List<GroupMember> gmList = groupMemberService.getDeptByGroupId(map);
            // 判断群组已经存在的的组织集合是否为空
            if (gmList != null && gmList.size() > 0) {
                // 循环遍历集合，并将组织ID封装到集合中
                for (GroupMember groupMember : gmList) {
                    orgIds.add(groupMember.getOrg().getOrgId());
                }
            }
            // 获取当前组织的一级菜单
            List<Organization> res = groupMemberService.getFirstLevelOrg();
            // 封装组织机构树
            buffer.append("[");
            for (Organization r : res) {
                buffer.append("{\"nodeId\":\"" + r.getOrgId() + "\",");
                buffer.append("\"checked\":" + orgIds.contains(r.getOrgId())
                    + ",");
                buffer.append("\"leaf\":" + decideIsLeafOrg(r) + ",");
                buffer.append("\"text\":\"" + r.getOrgName() + "\",");
                buffer.append("\"children\":[");
                getByParentOrg(r, orgIds);
                buffer.append("]},");
            }
            buffer.append("]");
            String result = buffer.toString().replaceAll(",]", "]");
            JsonUtil.outJsonArray(result);
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class,
                "获取群组组织机构树",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 根据父节点查询所有子节点
     * 
     * @Title getByParentOrg
     * @author tangh
     * @date 2014年9月18日
     * @param org 组织对象
     * @param orgIds 组织
     * @return String
     */
    private void getByParentOrg(Organization org, List<Integer> orgIds) {
        try {
            List<Organization> list =
                groupMemberService.getAllSonOrgByParentId(org.getOrgId() + "");
            // 判断集合是否为空
            if (list.size() != 0) {
                // 循环遍历集合并封装数据
                for (Organization r : list) {
                    buffer.append("{\"nodeId\":\"" + r.getOrgId() + "\",");
                    buffer.append("\"text\":\"" + r.getOrgName() + "\",");
                    buffer.append("\"checked\":"
                        + orgIds.contains(r.getOrgId()) + ",");
                    buffer.append("\"leaf\":" + decideIsLeafOrg(r) + ",");
                    buffer.append("\"children\":[");
                    getByParentOrg(r, orgIds);
                    buffer.append("]},");
                }
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(GroupManagerAction.class,
                "根据父节点查询所有子节点",
                e,
                false);
        }
    }
    
    /**
     * 判断树节点是否为叶子节点
     * 
     * @Title decideIsLeafOrg
     * @author tangh
     * @date 2014年9月19日
     * @param org 组织对象
     * @return
     */
    public boolean decideIsLeafOrg(Organization org) {
        boolean result = true;
        Set<Organization> sonOrganization = org.getOrganizations();
        for (Organization sonOrg : sonOrganization) {
            if (sonOrg.getStatus() != Constant.STATUS_IS_DELETED) {
                result = false;
                break;
            }
        }
        return result;
    }
    
}
