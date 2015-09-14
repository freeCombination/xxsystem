/**  
 * @文件名: WorkRulePlanAction.java
 * @版权:Copyright 2009-2013 版权所有：大庆金桥信息工程有限公司成都分公司
 * @描述: 规则计划Action类
 * @修改人: lizhengc
 * @修改时间: 2013-11-25 上午10:43:22
 * @修改内容:新增
 */
package com.dqgb.sshframe.workturns.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.util.DateUtil;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.util.RequestUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.workturns.entity.WorkRound;
import com.dqgb.sshframe.workturns.entity.WorkRulePlan;
import com.dqgb.sshframe.workturns.entity.WorkTeam;
import com.dqgb.sshframe.workturns.entity.WorkTurnsRule;
import com.dqgb.sshframe.workturns.service.IRuleWorkTeamMapService;
import com.dqgb.sshframe.workturns.service.IWorkRoundService;
import com.dqgb.sshframe.workturns.service.IWorkRulePlanService;
import com.dqgb.sshframe.workturns.service.IWorkTurnsRuleService;
import com.dqgb.sshframe.workturns.vo.RoundTurnsVo;
import com.dqgb.sshframe.workturns.vo.RuleWorkTeamMapVo;
import com.dqgb.sshframe.workturns.vo.WorkRulePlanVo;

/**
 * 倒班管理计划Action
 * 
 * @author lizhengc
 * @version V1.40,2014-8-22
 * @see [相关类/方法]
 * @since V1.40
 */
public class WorkRulePlanAction extends BaseAction {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = 3060929100865975899L;
    
    /**
     * @Fields workRulePlanService : 管理计划service注入
     */
    @Resource
    private IWorkRulePlanService workRulePlanService;
    
    /**
     * @Fields workTurnsRuleService : 轮班规则管理service注入
     */
    @Resource
    private IWorkTurnsRuleService workTurnsRuleService;
    
    /**
     * @Fields ruleWorkTeamMapService : 班组管理service注入
     */
    @Resource
    private IRuleWorkTeamMapService ruleWorkTeamMapService;
    
    /**
     * @Fields workRoundService : 班次管理service注入
     */
    @Resource
    private IWorkRoundService workRoundService;
    
    /**
     * @Fields workRulePlan : 倒班计划实体
     */
    private WorkRulePlan workRulePlan;
    
    /**
     * @Fields workRulePlanVo : 倒班计划vo
     */
    private WorkRulePlanVo workRulePlanVo;
    
    /**
     * 跳转到倒班计划页面
     * 
     * @Title toWorkRulePlanManage
     * @author 王鹏程
     * @date 2013-11-28
     * @return String
     */
    public String toWorkRulePlanManage() {
        return SUCCESS;
    }
    
    /**
     * 获得所有的已经生成的倒班计划
     * 
     * @Title getworkRulePlanList
     * @author 王鹏程
     * @date 2013-11-28
     * @return String
     */
    /*public String getworkRulePlanList() {
        String start = this.getRequest().getParameter("start");
        String limit = this.getRequest().getParameter("limit");
        String orgId = this.getRequest().getParameter("orgId");
        String ruleName = this.getRequest().getParameter("workRulePlanName");
        String workTeamName = this.getRequest().getParameter("workTeamName");
        String workRoundName = this.getRequest().getParameter("workRoundName");
        String workRulePlanStartDate =
            this.getRequest().getParameter("workRulePlanStartDate");
        String workRulePlanEndDate =
            this.getRequest().getParameter("workRulePlanEndDate");
        // String ids = super.getAuthOrgIdsInSession(Constant.WORKRULEPLAN_KEY);
        Map<String, String> maps = new HashMap<String, String>();
        ListVo<WorkRulePlanVo> vos = null;
        maps.put("start", start);
        maps.put("limit", limit);
        maps.put("orgId", orgId);
        // maps.put("orgIds", ids);
        maps.put("ruleName", ruleName);
        maps.put("workTeamName", workTeamName);
        maps.put("workRoundName", workRoundName);
        maps.put("startDate", workRulePlanStartDate);
        maps.put("endDate", workRulePlanEndDate);
        try {
            vos =
                this.getWorkRulePlanService().getAllWorkRulePlansByParams(maps);
            JsonUtil.outJson(vos);
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkRulePlanAction.class,
                "获得所有的已经生成的倒班计划失败！",
                e,
                false);
            JsonUtil.outJson(vos);
        }
        return null;
    }*/
    
    /**
     * 得到所有的班次信息
     * 
     * @Title getWorkRoundAll
     * @author 王鹏程
     * @date 2013-9-3
     * @return String
     */
    public String getWorkRoundAll() {
        try {
            List<WorkRound> workRounds =
                this.workRoundService.getAllWorkRound();
            JsonUtil.outJsonArray(workRounds);
        }
        catch (Exception e) {
            excepAndLogHandle(WorkRulePlanAction.class,
                "得到所有的班次信息失败！",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 批量的生成倒班计划
     * 
     * @Title batchProduceWorkPlans
     * @author 王鹏程
     * @date 2013-11-28
     * @return String
     */
    public String batchProduceWorkPlans() {
        String ruleId = this.getRequest().getParameter("ruleId");
        String planDays = this.getRequest().getParameter("planDays");
        String planStartDate = this.getRequest().getParameter("planStartDate");
        String planEndDate = this.getRequest().getParameter("planEndDate");
        Calendar cal = Calendar.getInstance();
        List<WorkRulePlan> plans = null;
        WorkRulePlan plan = null;
        StringBuffer info = new StringBuffer();
        try {
            WorkTurnsRule rule =
                this.getWorkTurnsRuleService()
                    .getWorkTurnsRuleById(Integer.valueOf(ruleId));
            List<RuleWorkTeamMapVo> ruleMaps =
                this.getRuleWorkTeamMapService()
                    .getRuleWorkTeamMapByRuleId(ruleId);
            if (rule == null || ruleMaps == null || ruleMaps.size() == 0) {
                JsonUtil.outJson("{success:false,msg:'倒班规则不存在或者其没有制定倒班细则!'}");
                return null;
            }
            else {
                // 删除指定时间段内已生成过的倒班计划
                if (StringUtils.isNotBlank(planStartDate) && StringUtils.isNotBlank(planEndDate)) {
                    this.getWorkRulePlanService().deleteTurnsPlanByDate(
                        ruleId,
                        DateUtil.dateToString(DateUtil.stringToDate(planStartDate, "yyyy-MM-dd"), "yyyy-MM-dd"), 
                        DateUtil.dateToString(DateUtil.stringToDate(planEndDate, "yyyy-MM-dd"), "yyyy-MM-dd"));
                }
                
                Date startDate =
                    DateUtil.stringToDate(planStartDate, "yyyy-MM-dd");
                int cycleDays = rule.getCycleDays();
                int turns = NumberUtils.toInt(planDays) / cycleDays;
                plans = new ArrayList<WorkRulePlan>();
                for (int i = 1; i <= turns; i++) {
                    for (RuleWorkTeamMapVo v : ruleMaps) {
                        // 每次要设置初始的日期;
                        cal.setTime(startDate);
                        cal.add(Calendar.DAY_OF_MONTH, (i - 1) * cycleDays);
                        
                        Integer workTeamId = v.getWorkTeamId();
                        Integer roundId = v.getRoundId();
                        Integer turnsOrder = v.getTurnsOrder();
                        int time = turnsOrder - 1;// 减去1，表明是第一天的;
                        // 计算出日期;
                        cal.add(Calendar.DAY_OF_MONTH, time);
                        /*String dateString =
                            DateUtil.dateToString(cal.getTime(), "yyyy-MM-dd");
                        // 判断是否已经存在计划了;不使用班组ID，班组可能会改变的
                        plan =
                            this.getWorkRulePlanService()
                                .validateWorkRulePlan(rule.getId(),
                                    null,
                                    roundId,
                                    dateString);
                        // 如果存在就更新
                        if (plan == null) {
                            plan = new WorkRulePlan();
                        }*/
                        
                        plan = new WorkRulePlan();
                        // 班组
                        WorkTeam team = new WorkTeam();
                        team.setId(workTeamId);
                        // 班次
                        WorkRound round = new WorkRound();
                        round.setId(roundId);
                        plan.setPlanDate(cal.getTime());
                        plan.setWorkRound(round);
                        plan.setWorkTeam(team);
                        plan.setWorkTurnsRule(rule);
                        plans.add(plan);
                    }
                }
            }
            // 批量的报错倒班计划;
            this.getWorkRulePlanService().batchAddWorkRulePlan(plans);
            // excepHandle("批量的生成倒班计划", null, true);
            JsonUtil.outJson("{success:true,msg:'批量生成倒班计划成功！',flag:'"
                + info.toString() + "'}");
        }
        catch (NumberFormatException e) {
            excepAndLogHandle(WorkRulePlanAction.class, "批量生成倒班计划失败！", e, false);
            JsonUtil.outJson("{success:false,msg:'生成倒班计划失败!'}");
        }
        catch (Exception e) {
            excepAndLogHandle(WorkRulePlanAction.class, "批量生成倒班计划失败！", e, false);
            JsonUtil.outJson("{success:false,msg:'生成倒班计划失败!'}");
        }
        return null;
    }
    
    /**
     * 根据参数获得所有的已经生成的倒班计划
     * 
     * @Title getWorkRulePlans
     * @author lizhengc
     * @date 2013-9-9
     * @return String
     */
    public String getWorkRulePlans() {
        ListVo<String> vos = new ListVo<String>();
        List<String> planMap = null;
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        try {
            ListVo<WorkRulePlanVo> vs =
                this.getWorkRulePlanService()
                    .getWorkRulePlanListByParams(paramsMap);
            //判断工作计划集合是否为空
            if (vs != null) {
                planMap = new ArrayList<String>();
                //将查出的工作计划集合转换为数据行
                planMap = getWorkRulePlanMap(vs.getList());
                vos.setList(planMap);
                vos.setTotalSize(vs.getTotalSize());
            }
            JsonUtil.outJson(vos);
        }
        catch (Exception e) {
            excepAndLogHandle(WorkRulePlanAction.class,
                "根据参数获得所有的已经生成的倒班计划失败！",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 更新倒班计划班组
     * 
     * @Title updateRulePlan
     * @author lizhengc
     * @date 2013-9-9
     * @return String
     */
    public String updateRulePlan() {
        String id = this.getRequest().getParameter("id");//计划id
        String teamId = this.getRequest().getParameter("teamId");//班组id
        String ruleId = this.getRequest().getParameter("ruleId");//规则id
        String planDate = this.getRequest().getParameter("planDate");//计划日期
        String roundId = this.getRequest().getParameter("roundId");//班次id
        try {
            WorkTeam workTeam = new WorkTeam();
            workTeam.setId(Integer.valueOf(teamId));
            WorkRound round = new WorkRound();
            round.setId(Integer.valueOf(roundId));
            WorkTurnsRule rule = new WorkTurnsRule();
            rule.setId(Integer.valueOf(ruleId));
            WorkRulePlan plan = new WorkRulePlan();
            //判断计划id是否为空
            if (StringUtils.isNotBlank(id) && !"0".equals(id)) {
                plan.setId(Integer.valueOf(id));
            }
            plan.setWorkRound(round);
            plan.setWorkTurnsRule(rule);
            plan.setWorkTeam(workTeam);
            //时间转换
            plan.setPlanDate(DateUtil.stringToDate(planDate, "yyyy-MM-dd"));
            this.getWorkRulePlanService().updateWorkRulePlan(plan);
            JsonUtil.outJson("{'success':true,'msg':'更新倒班计划成功!'}");
            excepAndLogHandle(WorkRulePlanAction.class,
                "更新倒班计划班组成功！",
                null,
                true);
        }
        catch (Exception e) {
            excepAndLogHandle(WorkRulePlanAction.class, "更新倒班计划班组失败！", e, false);
        }
        return null;
    }
    
    /**
     * 把计划转换为数据行
     * 
     * @Title getWorkRulePlanMap
     * @author 王鹏程
     * @date 2013-11-28
     * @return String
     */
    private List<String> getWorkRulePlanMap(List<WorkRulePlanVo> maps) {
        List<String> aa = new ArrayList<String>();
        Map<String, List<RoundTurnsVo>> planMap = null;
        List<RoundTurnsVo> roundVo = null;
        RoundTurnsVo rv = null;
        StringBuffer sb = new StringBuffer();
        if (maps != null && maps.size() > 0) {
            planMap = new HashMap<String, List<RoundTurnsVo>>();
            for (WorkRulePlanVo p : maps) {
                rv = new RoundTurnsVo();
                String date = p.getPlanDate();
                rv.setRoundId(p.getRoundId());
                rv.setRoundName(p.getRoundName());
                rv.setWorkTeamId(p.getWorkTeamId());
                rv.setRuleMapId(p.getId());
                rv.setWorkTeamName(p.getOrgName() + "-" + p.getWorkTeamName());
                roundVo = planMap.get(date);
                if (roundVo == null) {
                    roundVo = new ArrayList<RoundTurnsVo>();
                }
                roundVo.add(rv);
                planMap.put(date, roundVo);
            }
            for (String ii : planMap.keySet()) {
                sb = new StringBuffer();
                List<RoundTurnsVo> r = planMap.get(ii);
                if (r != null && r.size() > 0) {
                    sb.append("{'date':'").append(ii).append("',");
                    for (RoundTurnsVo v : r) {
                        // sb.append("'round&&").append(v.getRoundId()).append("':").append(v.getRoundId()).append(",");
                        sb.append("'id&&")
                            .append(v.getRoundId())
                            .append("':")
                            .append(v.getRuleMapId())
                            .append(",");
                        sb.append(v.getRoundId())
                            .append(":'")
                            .append(v.getWorkTeamName())
                            .append("',");
                    }
                }
                sb = sb.deleteCharAt(sb.length() - 1);
                sb.append("}");
                // JSONObject obj=JSONObject.fromObject(sb.toString());
                aa.add(sb.toString());
            }
            // 按照时间排序
            Collections.sort(aa);
        }
        
        return aa;
    }
    
    public void setWorkRoundService(IWorkRoundService workRoundService) {
        this.workRoundService = workRoundService;
    }
    
    public IWorkRoundService getWorkRoundService() {
        return workRoundService;
    }
    
    public IWorkRulePlanService getWorkRulePlanService() {
        return workRulePlanService;
    }
    
    public void setWorkRulePlanService(IWorkRulePlanService workRulePlanService) {
        this.workRulePlanService = workRulePlanService;
    }
    
    public WorkRulePlan getWorkRulePlan() {
        return workRulePlan;
    }
    
    public void setWorkRulePlan(WorkRulePlan workRulePlan) {
        this.workRulePlan = workRulePlan;
    }
    
    public IWorkTurnsRuleService getWorkTurnsRuleService() {
        return workTurnsRuleService;
    }
    
    public void setWorkTurnsRuleService(
        IWorkTurnsRuleService workTurnsRuleService) {
        this.workTurnsRuleService = workTurnsRuleService;
    }
    
    public IRuleWorkTeamMapService getRuleWorkTeamMapService() {
        return ruleWorkTeamMapService;
    }
    
    public void setRuleWorkTeamMapService(
        IRuleWorkTeamMapService ruleWorkTeamMapService) {
        this.ruleWorkTeamMapService = ruleWorkTeamMapService;
    }
    
    public WorkRulePlanVo getWorkRulePlanVo() {
        return workRulePlanVo;
    }
    
    public void setWorkRulePlanVo(WorkRulePlanVo workRulePlanVo) {
        this.workRulePlanVo = workRulePlanVo;
    }
}
