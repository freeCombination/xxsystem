/**
 * 文件名： WorkTeamAction.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述： 班组管理Action
 * 修改人： tangh
 * 修改时间： 2014年8月22日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.util.RequestUtil;
import com.dqgb.sshframe.workturns.entity.WorkTeam;
import com.dqgb.sshframe.workturns.service.IWorkTeamService;
import com.dqgb.sshframe.workturns.vo.WorkTeamVo;

/**
 * 班组管理Action
 * 
 * @author tangh
 * @version V1.40,2014年8月26日 下午4:23:34
 * @see [相关类/方法]
 * @since V1.40
 */
public class WorkTeamAction extends BaseAction {
    
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 708844035316525651L;
    
    /**
     * @Fields workTeamService 班组服务类
     */
    @Resource
    public IWorkTeamService workTeamService;
    
    /**
     * 参数传输对象
     */
    private WorkTeamVo workTeam;
    
    /**
     * @return the workTeamService
     */
    public IWorkTeamService getWorkTeamService() {
        return workTeamService;
    }
    
    /**
     * @param workTeamService the workTeamService to set
     */
    public void setWorkTeamService(IWorkTeamService workTeamService) {
        this.workTeamService = workTeamService;
    }
    
    /**
     * @return the workTeam
     */
    public WorkTeamVo getWorkTeamVo() {
        return workTeam;
    }
    
    /**
     * @param workTeam the workTeam to set
     */
    public void setWorkTeamVo(WorkTeamVo workTeam) {
        this.workTeam = workTeam;
    }
    
    /**
     * @return the workTeam
     */
    public WorkTeamVo getWorkTeam() {
        return workTeam;
    }
    
    /**
     * @param workTeam the workTeam to set
     */
    public void setWorkTeam(WorkTeamVo workTeam) {
        this.workTeam = workTeam;
    }
    
    /**
     * 跳转到工作组页面
     * 
     * @Title toWorkTeam
     * @author tangh
     * @date 2014年9月2日
     * @return String
     */
    public String toWorkTeam() {
        return SUCCESS;
    }
    
    /**
     * 获得所有的工作组 ；传入参数(工作组名字)，orgId，是否为叶子节点（leafFlag）
     * 
     * @Title getAllWorkTeam
     * @author tangh
     * @date 2014年9月2日
     * @return String
     */
    public String getAllWorkTeam() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        String orgIds= (String)this.getRequest().getSession().getAttribute("orgPermission");
        paramsMap.put("orgIds", orgIds);
        try {
            JsonUtil.outJson(workTeamService.getAllWorkTeam(paramsMap));
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkTeamAction.class, "获取所有的工作组", e, false);
        }
        return null;
    }
    
    /**
     * 添加工作组
     * 
     * @Title addWorkTeamInfo
     * @author tangh
     * @date 2014年8月28日
     * @return String
     */
    public String addWorkTeamInfo() {
        try {
            int id = workTeamService.addToWorkTeam(workTeam);
            // 判断参数是否大于0
            if (id > 0) {
                JsonUtil.outJson("{success:'true',msg:'添加班组信息成功'}");
                this.excepAndLogHandle(WorkTeamAction.class, "新增班组", null, true);
            }
            else {
                JsonUtil.outJson("{success:'false',msg:'添加班组信息失败'}");
                this.excepAndLogHandle(WorkTeamAction.class,
                    "新增班组",
                    null,
                    false);
            }
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:'false',msg:'添加班组信息失败'}");
            this.excepAndLogHandle(WorkTeamAction.class, "新增班组", e, false);
        }
        return null;
    }
    
    /**
     * 修改班组
     * 
     * @Title updateWorkTeamInfo
     * @author tangh
     * @date 2014年8月29日
     * @return String
     */
    public String updateWorkTeamInfo() {
        String flag = getRequest().getParameter("nodeId");
        try {
            String result = workTeamService.updateWorkTeam(workTeam, flag);
            // 根据修改判断结果是否是成功
            if ("success".equals(result)) {
                JsonUtil.outJson("{success:'true',msg:'修改班组信息成功'}");
                this.excepAndLogHandle(WorkTeamAction.class,
                    "修改班组信息",
                    null,
                    true);
            }
            else {
                JsonUtil.outJson("{success:'false',msg:'该班组不存在，请刷新'}");
                this.excepAndLogHandle(WorkTeamAction.class,
                    "修改班组信息",
                    null,
                    false);
            }
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:'false',msg:'修改班组信息失败'}");
            this.excepAndLogHandle(WorkTeamAction.class, "修改班组信息", e, false);
        }
        return null;
    }
    
    /**
     * 验证工作组的名字
     * 
     * @Title validateAddWorkTeamName
     * @author tangh
     * @date 2014年9月2日
     * @return String
     */
    public String validateAddWorkTeamName() {
        // 声明map集合，用于存储参数
        Map<String, Object> vaildator = new HashMap<String, Object>();
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        int userNameSize;
        try {
            // 查询工作组名称
            userNameSize = this.workTeamService.validateWorkTeamName(paramsMap);
            // 根据查询结果判断是否存在
            if (userNameSize != 0) {
                JsonUtil.outJson("{success:true,msg:'该班组名已经存在！'}");
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "该班组名已经存在！");
            }
            else {
                JsonUtil.outJson("{success:false}");
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkTeamAction.class, "验证工作组的名字", e, false);
        }
        return null;
    }
    
    /**
     * 获取班组成员
     * 
     * @Title getWorkTeamMember
     * @author tangh
     * @date 2014年9月2日
     * @return String
     */
    public String getWorkTeamMember() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            JsonUtil.outJson(workTeamService.getWorkTeamMember(paramsMap));
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkTeamAction.class, "获取班组成员", e, false);
            JsonUtil.outJson("{success:false,msg:'获取班组成员失败！'}");
        }
        return null;
    }
    
    /**
     * 获取组织成员
     * 
     * @Title getDeptMember
     * @author tangh
     * @date 2014年9月2日
     * @return String
     */
    public String getDeptMember() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            JsonUtil.outJson(workTeamService.getDeptMember(paramsMap));
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkTeamAction.class, "获取组织成员", e, false);
            JsonUtil.outJson("{success:false,msg:'获取组织成员失败！'}");
        }
        return null;
    }
    
    /**
     * 添加班组成员
     * 
     * @Title addDeptMember
     * @author tangh
     * @date 2014年9月2日
     * @return String
     */
    public String addDeptMember() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            String flag = workTeamService.addWorkTeamMember(paramsMap);
            // 根据添加返回结果判断是否是添加成功
            if ("success".equals(flag)) {
                this.excepAndLogHandle(WorkTeamAction.class,
                    "添加班组成员",
                    null,
                    true);
                JsonUtil.outJson("{success:true,msg:'添加班组成员成功！'}");
            }
            else {
                this.excepAndLogHandle(WorkTeamAction.class,
                    "添加班组成员",
                    null,
                    false);
                JsonUtil.outJson("{success:false,msg:'添加班组成员失败！'}");
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkTeamAction.class, "添加班组成员", e, false);
            JsonUtil.outJson("{success:false,msg:'添加班组成员失败！'}");
        }
        return null;
    }
    
    /**
     * 删除班组
     * 
     * @Title delWorkTeam
     * @author tangh
     * @date 2014年9月2日
     * @return String
     */
    public String delWorkTeam() {
        // 获取班组id
        String delTeamId = this.getRequest().getParameter("ids");
        try {
            String result = workTeamService.delWorkTeam(delTeamId);
            this.excepAndLogHandle(WorkTeamAction.class, "删除班组", null, true);
            if ("success".equals(result)) {
                JsonUtil.outJson("{'success':'true','msg':'删除班组成功！'}");
            }
            else if ("error".equals(result)) {
                JsonUtil.outJson("{'success':'false','msg':'班组已删除，请刷新！'}");
            }
            else {
                JsonUtil.outJson("{'success':'true','msg':'" + result + "'}");
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkTeamAction.class, "删除班组", e, false);
            JsonUtil.outJson("{'success':'false','msg':'删除班组失败！'}");
        }
        return null;
    }
    
    /**
     * 删除班组成员
     * 
     * @Title delTeamMember
     * @author tangh
     * @date 2014年9月2日
     * @return String
     */
    public String delTeamMember() {
        // 获取参数
        String teamId = this.getRequest().getParameter("teamId");
        String delMemberIds = this.getRequest().getParameter("ids");
        // 封装参数
        Map<String, String> param = new HashMap<String, String>();
        param.put("teamId", StringUtils.isNotBlank(teamId) ? teamId : "0");
        param.put("delMemberIds", delMemberIds);
        try {
            String flag = workTeamService.delWorkTeamMember(param);
            // 判断是否删除成功
            if ("deleted".equals(flag)) {
                JsonUtil.outJson("{success:false,msg:'该班组不存在！'}");
            }
            else {
                this.excepAndLogHandle(WorkTeamAction.class,
                    "删除班组成员",
                    null,
                    true);
                JsonUtil.outJson("{success:true,msg:'删除班组成员成功！'}");
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkTeamAction.class, "删除班组成员", e, false);
            JsonUtil.outJson("{success:false,msg:'删除班组成员失败！'}");
        }
        return null;
    }
    
    /**
     * 根据班组ID获取班组信息
     * 
     * @Title getWorkTeamById
     * @author 何东 2014-7-17
     * @date 2014年9月5日
     * @return String
     */
    public String getWorkTeamById() {
        // 获取参数
        String teamId = this.getRequest().getParameter("teamId");
        try {
            WorkTeam workTeam =
                workTeamService.getWorkTeamById(NumberUtils.toInt(teamId));
            // 判断班组是否存在
            if (workTeam == null) {
                JsonUtil.outJson("{success:false,msg:'该条数据所对应的班组已被删除！'}");
            }
            else {
                String jsonData =
                    "{success:true,teamName:'"
                        + workTeam.getTeamName()
                        + "',orgId:"
                        + workTeam.getOrg().getOrgId()
                        + ",orgName:'"
                        + workTeam.getOrg().getOrgName()
                        + "',remark:'"
                        + (StringUtils.isNotBlank(workTeam.getRemark()) ? workTeam.getRemark()
                            : "") + "'}";
                JsonUtil.outJson(jsonData);
            }
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkTeamAction.class,
                "根据班组ID获取班组信息",
                e,
                false);
        }
        return null;
    }
    
}
