/**  
 * @文件名 WorkRoundAction.java
 * @版权 Copyright 2009-2013 版权所有：大庆金桥信息工程有限公司成都分公司
 * @描述  班次管理Action
 * @作者 lizhengc
 * @时间 2014-9-5 上午16:37:09
 */
package com.dqgb.sshframe.workturns.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;

import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.util.RequestUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.workturns.entity.WorkRound;
import com.dqgb.sshframe.workturns.service.IWorkRoundService;
import com.dqgb.sshframe.workturns.vo.WorkRoundVo;

/**
 * 班次管理Action
 * 
 * @author lizhengc
 * @version V1.40,2014-8-22
 * @see [相关类/方法]
 * @since V1.40
 */
public class WorkRoundAction extends BaseAction {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = 7495083375984477328L;
    
    /**
     * @Fields workRoundService : 班次service注入
     */
    @Resource
    private IWorkRoundService workRoundService;
    
    /**
     * @Fields workRoundVo : 班次vo
     */
    private WorkRoundVo workRoundVo;
    
    /**
     * @Fields workRound : 班次实体
     */
    private WorkRound workRound;
    
    /**
     * 跳转班次页面
     * 
     * @Title toWorkRoundManage
     * @author lizhengc
     * @date 2014-09-01
     * @return String
     */
    public String toWorkRoundManage() {
        return SUCCESS;
    }
    
    /**
     * 获得班次列表
     * 
     * @Title getWorkRoundList
     * @author lizhengc
     * @date 2014-09-01
     * @return String
     */
    public String getWorkRoundList() {
        Map<String, String> maps = new HashMap<String, String>();
        String name = this.getRequest().getParameter("name");
        String start = this.getRequest().getParameter("start");
        String limit = this.getRequest().getParameter("limit");
        maps.put("name", name);
        maps.put("start", start);
        maps.put("limit", limit);
        ListVo<WorkRoundVo> vos = null;
        try {
            vos = this.workRoundService.getWorkRoundByParams(maps);
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkRoundAction.class, "获得班次列表", e, false);
        }
        JsonUtil.outJson(vos);
        //this.excepAndLogHandle(WorkRoundAction.class, "获得班次列表", null, true);
        return null;
    }
    
    /**
     * 添加班次
     * 
     * @Title addWorkRound
     * @author lizhengc
     * @date 2014-09-01
     * @return String
     */
    public String addWorkRound() {
        try {
            // 判断班次的时间是否有交叉;
            Map<String, String> info =
                this.getWorkRoundService().isTimeCross(workRound);
            if ("false".equalsIgnoreCase(info.get("success"))) {
                JsonUtil.outJson("{'success':false,'msg':'" + info.get("info")
                    + "'}");
                return null;
            }
            this.getWorkRoundService().addWorkRound(workRound);
            this.excepAndLogHandle(WorkRoundAction.class, "添加班次", null, true);
            JsonUtil.outJson("{'success':true,msg:'保存成功'}");
        }
        catch (Exception e) {
            JsonUtil.outJson("{'success':false,msg:'添加失败！'}");
            this.excepAndLogHandle(WorkRoundAction.class, "添加班次", e, false);
        }
        return null;
    }
    
    /**
     * 修改班次
     * 
     * @Title updateWorkRound
     * @author lizhengc
     * @date 2014-09-01
     * @return String
     */
    public String updateWorkRound() {
        try {
            // 判断班次的时间是否有交叉;
            Map<String, String> info =
                this.getWorkRoundService().isTimeCross(workRound);
            if ("false".equalsIgnoreCase(info.get("success"))) {
                JsonUtil.outJson("{'success':false,'msg':'" + info.get("info")
                    + "'}");
                return null;
            }
            this.getWorkRoundService().updateWorkRound(workRound);
            this.excepAndLogHandle(WorkRoundAction.class, "修改班次", null, true);
            JsonUtil.outJson("{'success':true,'msg':'修改成功'}");
        }
        catch (Exception e) {
            JsonUtil.outJson("{'success':false,'msg':'修改失败!'}");
            this.excepAndLogHandle(WorkRoundAction.class, "修改班次", e, false);
        }
        return null;
    }
    
    /**
     * 删除班次
     * 
     * @Title deleteWorkRound
     * @author lizhengc
     * @date 2014-09-02
     * @return String
     */
    public String deleteWorkRound() {
        String ids = this.getRequest().getParameter("ids");
        try {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                int existPlan =
                    this.getWorkRoundService()
                        .getroundByPlanRule(NumberUtils.toInt(id));
                int existTeam =
                    this.getWorkRoundService()
                        .getroundByRuleWork(NumberUtils.toInt(id));
                if (existPlan == 0 && existTeam == 0) {
                    this.getWorkRoundService().deleteWorkRound(ids);
                    JsonUtil.outJson("{'success':true,msg:'删除成功'}");
                    this.excepAndLogHandle(WorkRoundAction.class,
                        "删除班次",
                        null,
                        true);
                    return null;
                }else{
                    JsonUtil.outJson("{'success':false,msg:'数据已被其他资源使用！'}");
                    return null;
                }
            }
        }
        catch (Exception e) {
            JsonUtil.outJson("{'success':false,msg:'删除失败！请稍候重试！'}");
            this.excepAndLogHandle(WorkRoundAction.class, "删除班次", e, false);
        }
        return null;
    }
    
    /**
     * 查看班次详细信息
     * 
     * @Title viewWorkRound
     * @author lizhengc
     * @date 2014-09-01
     * @return String
     */
    public String viewWorkRound() {
        String id = this.getRequest().getParameter("id");
        try {
            workRoundVo = this.getWorkRoundService().getWorkRoundById(id);
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkRoundAction.class, "查看班次详细信息", e, false);
        }
        return SUCCESS;
    }
    
    /**
     * 检查班次数据是否存在
     * 
     * @Title checkRounIsExist
     * @author lizhengc
     * @date 2014-09-01
     * @return String
     */
    public String checkRounIsExist() {
        String msg = "{'success':true,'msg':''}";
        String ids = getRequest().getParameter("ids");
        if (!"".equals(ids) && !"0".equals(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                try {
                    workRound =
                        this.getWorkRoundService().getWorkRoundById1(id);
                }
                catch (BusinessException e) {
                    this.excepAndLogHandle(WorkRoundAction.class,
                        "检查班次数据是否存在",
                        e,
                        false);
                }
                if (workRound == null) {
                    msg =
                        "{'success':false,'msg':'【" + workRound.getRoundName()
                            + "】已删除，列表已刷新'}";
                    break;
                }
            }
        }
        JsonUtil.outJson(msg);
        return null;
    }
    
    /**
     * 检验班次名称的唯一性
     * 
     * @Title validateRoundName
     * @author lizhengc
     * @date 2014-09-01
     * @return String
     */
    public String validateRoundName() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        Map<String, Object> vaildator = new HashMap<String, Object>();
        try {
            vaildator = this.getWorkRoundService().validateRoundName(paramsMap);
            JsonUtil.outJson(vaildator);
        }
        catch (BusinessException e) {
            this.excepAndLogHandle(WorkRoundAction.class,
                "检验班次名称的唯一性",
                e,
                false);
        }
        return null;
    }
    
    /**
     * @return the workRoundService
     */
    public IWorkRoundService getWorkRoundService() {
        return workRoundService;
    }
    
    /**
     * @param workRoundService the workRoundService to set
     */
    public void setWorkRoundService(IWorkRoundService workRoundService) {
        this.workRoundService = workRoundService;
    }
    
    /**
     * @return the workRoundVo
     */
    public WorkRoundVo getWorkRoundVo() {
        return workRoundVo;
    }
    
    /**
     * @param workRoundVo the workRoundVo to set
     */
    public void setWorkRoundVo(WorkRoundVo workRoundVo) {
        this.workRoundVo = workRoundVo;
    }
    
    /**
     * @return the workRound
     */
    public WorkRound getWorkRound() {
        return workRound;
    }
    
    /**
     * @param workRound the workRound to set
     */
    public void setWorkRound(WorkRound workRound) {
        this.workRound = workRound;
    }
}
