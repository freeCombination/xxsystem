/**  
 * @文件名 WorkTurnsRuleAction.java
 * @版权 Copyright 2009-2013 版权所有：大庆金桥信息工程有限公司成都分公司
 * @描述  倒班管理Action
 * @作者 liukang-wb
 * @时间 2014-9-10 15:00:00
 */
package com.dqgb.sshframe.workturns.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.util.RequestUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.workturns.entity.RuleWorkTeamMap;
import com.dqgb.sshframe.workturns.entity.WorkRound;
import com.dqgb.sshframe.workturns.entity.WorkTeam;
import com.dqgb.sshframe.workturns.entity.WorkTurnsRule;
import com.dqgb.sshframe.workturns.service.IRuleWorkTeamMapService;
import com.dqgb.sshframe.workturns.service.IWorkRoundService;
import com.dqgb.sshframe.workturns.service.IWorkRulePlanService;
import com.dqgb.sshframe.workturns.service.IWorkTeamService;
import com.dqgb.sshframe.workturns.service.IWorkTurnsRuleService;
import com.dqgb.sshframe.workturns.vo.RoundTurnsVo;
import com.dqgb.sshframe.workturns.vo.RuleMap;
import com.dqgb.sshframe.workturns.vo.RuleWorkTeamMapVo;
import com.dqgb.sshframe.workturns.vo.WorkTeamVo;
import com.dqgb.sshframe.workturns.vo.WorkTurnsRuleVo;

/**
 * 倒班规则Action
 * 
 * @author dong.he
 * @version V1.40,2014-8-22
 * @see [相关类/方法]
 * @since V1.40
 */
public class WorkTurnsRuleAction extends BaseAction {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = 8501019533076157000L;
    
    // 倒班规则管理Service
    @Autowired
    private IWorkTurnsRuleService workTurnsRuleService;
    
    // 班组管理Service
    @Autowired
    private IWorkTeamService workTeamService;
    
    // 倒班班组详细管理Service
    @Autowired
    private IRuleWorkTeamMapService ruleWorkTeamMapService;
    
    // 倒班计划管理Service
    private IWorkRulePlanService workRulePlanService;
    
    // 倒班规则VO
    private WorkTurnsRuleVo workTurnsRuleVo;
    
    // 倒班规则实体
    private WorkTurnsRule workTurnsRule;
    
    // 倒班管理Service
    @Resource
    private IWorkRoundService workRoundService;
    
    // 倒班班组详细管理List
    private List<RuleWorkTeamMap> ruleWorkTeamMap;
    
    // 规则ID
    private Integer ruleId;
    
    // 周期
    private Integer cycleDays;
    
    /** 规则的详细信息 */
    private List<String> ruleMap;
    
    private List<JSONObject> ruleMaps;
    
    private List<JSONObject> planMaps;
    
    private List<String> aa;
    
    private String deleteRuleMapIds;
    
    /**
     * 跳转到倒班规则页面
     * 
     * @author 王鹏程 2013-11-25
     * @return String
     */
    public String toWorkTurnsRuleManage() {
        return SUCCESS;
    }
    
    /**
     * 获得所有的已制定好的倒班计划
     * 
     * @author liukang-wb 2014-8-27
     * @return String
     */
    public String getWorkTurnsRuleList() {
        // 前台接收参数
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        String orgIds =
            (String)this.getRequest()
                .getSession()
                .getAttribute("orgPermission");
        paramsMap.put("orgIds", orgIds);
        ListVo<WorkTurnsRuleVo> vos = null;
        try {
            // 获得制定好的倒班计划
            vos =
                this.getWorkTurnsRuleService()
                    .getWorkTurnsRuleListByParams(paramsMap);
        }
        catch (Exception e) {
            this.excepAndLogHandle(WorkTurnsRuleAction.class,
                "获得所有的已制定好的倒班计划",
                e,
                false);
        }
        JsonUtil.outJson(vos);
        return null;
    }
    
    /**
     * 添加或者修改倒班规则
     * 
     * @author liukang-wb 2014-8-7
     * @return String
     */
    public String addorupdateWorkTurnsRule() {
        try {
            // 如果workTurnsRule有ID的话，则进入修改页面
            if (workTurnsRule.getId() != null
                && !"".equals(workTurnsRule.getId())) {
                // 通过workTurnsRule.id查出此时在数据库里的workTurnsRule，减少并发问题
                WorkTurnsRule workTurnsRule1 =
                    this.getWorkTurnsRuleService()
                        .getWorkTurnsRuleById(workTurnsRule.getId());
                // 如果workTurnsRule不为空，则向其中赋值
                if (workTurnsRule1 != null) {
                    workTurnsRule1.setName(workTurnsRule.getName());
                    if (StringUtils.isBlank(workTurnsRule.getRemarks())
                        || StringUtils.isBlank(workTurnsRule.getRemarks()
                            .trim())) {
                        workTurnsRule1.setRemarks(null);
                    }
                    else {
                        workTurnsRule1.setRemarks(workTurnsRule.getRemarks()
                            .trim());
                    }
                    workTurnsRule1.setUser(getCurrentUser());
                    workTurnsRule1.setCycleDays(workTurnsRule.getCycleDays());
                    workTurnsRule1.setOrg(workTurnsRule.getOrg());
                    // 修改
                    this.getWorkTurnsRuleService()
                        .updateWorkTurnsRule(workTurnsRule1);
                    JsonUtil.outJson("{success:true,msg:'修改成功!',id:"
                        + workTurnsRule.getId() + "}");
                    this.excepAndLogHandle(WorkTurnsRuleAction.class,
                        "修改成功！",
                        null,
                        true);
                }
                else {
                    // 如果不存在，则代表该条数据已经被删除
                    JsonUtil.outJson("{success:false,msg:'该规则已被删除!',id:"
                        + workTurnsRule.getId() + "}");
                    this.excepAndLogHandle(WorkTurnsRuleAction.class,
                        "修改失败！",
                        null,
                        false);
                }
            }
            else {
                // 这里是添加操作
                workTurnsRule.setUser(getCurrentUser());
                workTurnsRule.setMakeDate(new Date());
                if (StringUtils.isBlank(workTurnsRule.getRemarks())
                    || StringUtils.isBlank(workTurnsRule.getRemarks().trim())) {
                    workTurnsRule.setRemarks(null);
                }
                else {
                    workTurnsRule.setRemarks(workTurnsRule.getRemarks().trim());
                }
                
                // 添加
                this.getWorkTurnsRuleService().addWorkTurnsRule(workTurnsRule);
                excepAndLogHandle(WorkTurnsRuleAction.class,
                    "添加成功！",
                    null,
                    true);
                JsonUtil.outJson("{success:true,msg:'保存成功!',id:"
                    + workTurnsRule.getId() + "}");
            }
        }
        catch (BusinessException e) {
            // 这里是修改的时候出现的异常
            if (workTurnsRule.getId() != null
                && !"".equals(workTurnsRule.getId())) {
                this.excepAndLogHandle(WorkTurnsRuleAction.class,
                    "修改倒班规则",
                    e,
                    false);
                JsonUtil.outJson("{success:false,msg:'" + e.getMessage() + "'}");
            }
            else {
                // 添加的时候出现的异常
                this.excepAndLogHandle(WorkTurnsRuleAction.class,
                    "添加倒班规则",
                    e,
                    false);
                JsonUtil.outJson("{success:false,msg:'" + e.getMessage() + "'}");
            }
            
        }
        return null;
    }
    
    /**
     * 检查是否已经生成倒班计划
     * 
     * @Title checkOrgIsSame
     * @author 111
     * @date 2014年9月11日
     * @return
     */
    public String checkOrgIsSame() {
        // 前台获取ruleid
        String ruleId = this.getRequest().getParameter("ruleId");
        try {
            if (ruleId != null && !"".equals(ruleId)) {
                // 根据规则id获取到与之相关的班组
                int count =
                    this.getWorkTurnsRuleService().workTeamIsExsit(ruleId);
                // 如果存在班组，则已经生成了倒班计划详细，不能修改对应的部门；反之，则可以修改对应的部门，同时删除该规则下所属的班组。
                if (count != 0) {
                    JsonUtil.outJson("{'success':false,msg:'已生成倒班计划详细，不能修改组织！'}");
                }
                else {
                    // 这里代表还未生成倒班计划详细，然后将倒班规则详细删除
                    this.getWorkTurnsRuleService()
                        .delAllWorkTurnsDetailByRuleId(ruleId);
                    JsonUtil.outJson("{'success':true,msg:''}");
                }
            }
        }
        catch (BusinessException e) {
            this.excepAndLogHandle(WorkTurnsRuleAction.class,
                "检查是否已经生成倒班计划",
                e,
                false);
        }
        
        return null;
    }
    
    /**
     * 删除倒班规则
     * 
     * @author 王鹏程 2013-11-25
     * @return String
     */
    public String deleteWorkTurnsRule() {
        // 从前台获取ids，可以用来批量删除
        String ids = this.getRequest().getParameter("ids");
        try {
            // 删除
            this.getWorkTurnsRuleService().deleteWorkTurnsRule(ids);
            excepAndLogHandle(WorkTurnsRuleAction.class, "删除倒班规则:ID为【" + ids
                + "】", null, true);
            JsonUtil.outJson("{success:'true',msg:'删除成功!'}");
            this.excepAndLogHandle(WorkTurnsRuleAction.class,
                "删除成功!",
                null,
                true);
        }
        catch (Exception e) {
            // 删除时出现异常
            JsonUtil.outJson("{success:false,msg:'" + e.getMessage() + "'}");
            this.excepAndLogHandle(WorkTurnsRuleAction.class, "删除失败!", e, false);
        }
        return null;
    }
    
    /**
     * 检查规则是否存在
     * 
     * @Title checkRoleIsExist
     * @author liukang
     * @date 2014年8月28日
     * @return String
     */
    public String checkRoleIsExist() {
        // 接收从前台传回的ids
        String ids = this.getRequest().getParameter("ids");
        // 用,拆分成String数组
        String[] id_list = ids.split(",");
        // 遍历数组
        for (String id : id_list) {
            try {
                // 通过id将workTurnsRule查出来
                WorkTurnsRule workTurnsRule =
                    this.getWorkTurnsRuleService()
                        .getWorkTurnsRuleById(Integer.parseInt(id));
                // 如果workTurnsRule为空，则代表该id的workTurnsRule已经被删除了，然后删除操作就无法进行，return
                if (workTurnsRule == null) {
                    JsonUtil.outJson("{'success':false,msg:'数据已被删除！'}");
                    this.excepAndLogHandle(WorkTurnsRuleAction.class,
                        "数据已被删除!",
                        null,
                        false);
                    return null;
                }
                // 通过id获取已经生成的倒班计划数量
                int count = this.getWorkTurnsRuleService().workTeamIsExsit(id);
                if (count != 0) {
                    JsonUtil.outJson("{'success':false,msg:'所选规则的倒班计划已生成！'}");
                    this.excepAndLogHandle(WorkTurnsRuleAction.class,
                        "所选规则的倒班计划已生成!",
                        null,
                        false);
                    return null;
                }
            }
            catch (Exception e) {
            	e.printStackTrace();
                JsonUtil.outJson("{'success':false,msg:'" + e.getMessage()
                    + "'}");
                this.excepAndLogHandle(WorkTurnsRuleAction.class,
                    "已生成倒班计划!",
                    e,
                    false);
            }
        }
        JsonUtil.outJson("{'success':true,msg:''}");
        this.excepAndLogHandle(WorkTurnsRuleAction.class, "可以修改!", null, true);
        return null;
    }
    
    /**
     * 得到单个的workTurnsRule
     * 
     * @Title getWorkTurnsRuleById
     * @author 111
     * @date 2014年8月29日
     */
    public String getWorkTurnsRuleById() {
        // 接收从前台穿回的ruleid
        String role_id = this.getRequest().getParameter("role_id");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            // 通过role_id得到单个的workTurnsRule
            WorkTurnsRule workTurnsRule =
                this.getWorkTurnsRuleService()
                    .getWorkTurnsRuleById(Integer.parseInt(role_id));
            // 将单个的workTurnsRule转型成VO
            WorkTurnsRuleVo ruleVo = new WorkTurnsRuleVo();
            ruleVo.setId(workTurnsRule.getId());
            ruleVo.setName(workTurnsRule.getName());
            ruleVo.setCycleDays(workTurnsRule.getCycleDays());
            ruleVo.setMakeDate(sdf.format(workTurnsRule.getMakeDate()));
            ruleVo.setOrgId(workTurnsRule.getOrg().getOrgId());
            ruleVo.setOrgName(workTurnsRule.getOrg().getOrgName());
            ruleVo.setUser(workTurnsRule.getUser().getUsername());
            ruleVo.setRemarks(workTurnsRule.getRemarks());
            JsonUtil.outJson(ruleVo);
            this.excepAndLogHandle(WorkTurnsRuleAction.class,
                "得到单个的workTurnsRule!",
                null,
                true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'" + e.getMessage() + "'}");
            this.excepAndLogHandle(WorkTurnsRuleAction.class,
                "workTurnsRule为空!",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 得到所有的班次信息
     * 
     * @Title getWorkRoundAll
     * @author liukang-wb
     * @date 2014年9月3日
     * @return
     */
    public String getWorkRoundAll() {
        try {
            // 得到所有的班次信息
            List<WorkRound> workRounds =
                this.workRoundService.getAllWorkRound();
            JsonUtil.outJsonArray(workRounds);
            excepAndLogHandle(WorkTurnsRuleAction.class,
                "获得班次列表成功！",
                null,
                true);
        }
        catch (Exception e) {
            excepAndLogHandle(WorkTurnsRuleAction.class, "获得班次列表失败！", e, false);
        }
        
        return null;
    }
    
    /**
     * 获得所有的班组信息;
     * 
     * @author liukang-wb
     * @return String
     */
    public String getAllWorkTeam() {
        // 接收从前台传回的ruleId
        String ruleId = this.getRequest().getParameter("ruleId");
        
        List<WorkTeamVo> vos = null;
        WorkTeamVo vo = null;
        List<WorkTeam> wts = null;
        try {
            // 得到单个的WorkTurnsRule
            WorkTurnsRule wtr =
                workTurnsRuleService.getWorkTurnsRuleById(Integer.parseInt(ruleId));
            // 如果WorkTurnsRule不为空
            if (wtr != null) {
                // 通过WorkTurnsRule所含的组织ID得到所含的班组
                wts =
                    this.getWorkTeamService()
                        .getAllWorkTeamByOrgId(String.valueOf(wtr.getOrg()
                            .getOrgId()));
            }
            // 如果班组不为空
            if (wts != null && wts.size() > 0) {
                vos = new ArrayList<WorkTeamVo>();
                // 将班组实体list转换成班组VOlist
                for (WorkTeam wt : wts) {
                    vo = new WorkTeamVo();
                    vo.setWorkTeamName(wt.getOrg().getOrgName() + "-"
                        + wt.getTeamName());
                    vo.setWorkTeamId(wt.getId());
                    vos.add(vo);
                }
            }
            JsonUtil.outJsonArray(vos);
            excepAndLogHandle(WorkTurnsRuleAction.class,
                "获得所有的班组信息成功！",
                null,
                true);
        }
        catch (Exception e) {
            excepAndLogHandle(WorkTurnsRuleAction.class,
                "获得所有的班组信息失败！",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 更新规则-班组信息
     * 
     * @author 王鹏程 2013-11-26
     * @return String
     */
    public String saveOrUpdateRuleWorkTeam() {
        List<RuleWorkTeamMap> maps = null;
        RuleWorkTeamMap rule = null;
        try {
            // 通过ruleId得到WorkTurnsRule
            WorkTurnsRule r =
                this.getWorkTurnsRuleService()
                    .getWorkTurnsRuleById(this.ruleId);
            // 如果WorkTurnsRule为空
            if (r == null) {
                JsonUtil.outJson("{success:false,msg:'保存倒班规则失败'}");
                return null;
            }
            else {
                // 如果WorkTurnsRule不为空
                r.setCycleDays(this.cycleDays);
                this.getWorkTurnsRuleService().updateWorkTurnsRule(r);
            }
            
            // 执行删除的规则详细;
            if (StringUtils.isNotBlank(deleteRuleMapIds)) {
                this.getRuleWorkTeamMapService()
                    .deleteRuleWorkTeamMapByIds(deleteRuleMapIds);
            }
            
            if (ruleMap != null || ruleMap.size() >= 0) {
                // 保存修改和添加的数据;
                maps = new ArrayList<RuleWorkTeamMap>();
                for (String s : ruleMap) {
                    rule = new RuleWorkTeamMap();
                    JSONObject obj = JSONObject.fromObject(s);
                    String id = obj.get("id") + "";
                    Integer orderId = obj.getInt("orderId");
                    Integer ruleId = obj.getInt("ruleId");
                    WorkTurnsRule workRule = new WorkTurnsRule();
                    workRule.setId(ruleId);
                    Integer workTeamId = obj.getInt("workTeamId");
                    WorkTeam team = new WorkTeam();
                    team.setId(workTeamId);
                    Integer roundId = obj.getInt("roundId");
                    WorkRound round = new WorkRound();
                    round.setId(roundId);
                    // 设置表
                    // 更新时加入ID字段；
                    if (!"0".equalsIgnoreCase(id)) {
                        rule.setId(Integer.valueOf(id));
                    }
                    rule.setTurnsOrder(orderId);
                    rule.setWorkRound(round);
                    rule.setWorkTurnsRule(workRule);
                    rule.setWorkTeam(team);
                    maps.add(rule);
                }
                this.getRuleWorkTeamMapService().saveRuleWorkTeamMap(maps);
            }
            JsonUtil.outJson("{success:true,msg:'修改成功!'}");
            excepAndLogHandle(WorkTurnsRuleAction.class, "修改成功！", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'" + e.getMessage() + "'}");
            excepAndLogHandle(WorkTurnsRuleAction.class, "修改失败！", e, false);
        }
        
        return null;
    }
    
    /***
     * 根据规则ID获得规则对应的详细信息;
     * 
     * @author 王鹏程 2013-11-27
     * @return String
     */
    public String getRuleWorkTeamMapsByRuleId1() {
        // 接收从前台传回的rule_id
        String ruleId = this.getRequest().getParameter("rule_id");
        try {
            // 得到对应的详细信息
            List<RuleWorkTeamMapVo> maps =
                this.getRuleWorkTeamMapService()
                    .getRuleWorkTeamMapByRuleId(ruleId);
            RuleMap ruleMaps = getRuleMap1(maps);
            // 如果没有添加数据，则新建一个并初始化倒班规则信息;
            if (ruleMaps == null) {
                WorkTurnsRuleVo vo =
                    this.getWorkTurnsRuleService()
                        .getWorkTurnsRuleVoById(ruleId);
                ruleMaps = new RuleMap();
                if (vo != null) {
                    ruleMaps.setCycleDays(vo.getCycleDays());
                    ruleMaps.setOldRuleCount(vo.getCycleDays());
                    ruleMaps.setRuleId(vo.getId());
                    ruleMaps.setRuleName(vo.getName());
                }
            }
            JsonUtil.outJson(ruleMaps);
            excepAndLogHandle(WorkTurnsRuleAction.class,
                "根据规则ID获得规则对应的详细信息成功！",
                null,
                true);
        }
        catch (Exception e) {
            excepAndLogHandle(WorkTurnsRuleAction.class,
                "根据规则ID获得规则对应的详细信息失败！",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 根据规则id获取对应的班组信息
     * 
     * @Title getRuleWorkTeamMapsByRuleId
     * @author 111
     * @date 2014年9月10日
     * @return
     */
    public String getRuleWorkTeamMapsByRuleId() {
        // 接收从前台传回的rule_id
        String ruleId = this.getRequest().getParameter("rule_id");
        try {
            // 获得对应的详细信息
            List<RuleWorkTeamMapVo> maps =
                this.getRuleWorkTeamMapService()
                    .getRuleWorkTeamMapByRuleId(ruleId);
            List<String> aa = getRuleMap(maps);
            // 如果没有添加数据，则新建一个并初始化倒班规则信息;
            
            JsonUtil.outJsonArray(aa);
        }
        catch (Exception e) {
            excepAndLogHandle(WorkTurnsRuleAction.class,
                "根据规则id获取对应的班组信息",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 把班组规格详细转化以班组ID为key的map结构，方便前台简析;
     * 
     * @author 王鹏程 2013-11-27
     * @param maps
     * @return List<RuleMap>
     */
    private List<String> getRuleMap(List<RuleWorkTeamMapVo> maps) {
        List<String> ruleMap = null;
        Map<Integer, List<RoundTurnsVo>> roundMap = null;
        List<RoundTurnsVo> roundVo = null;
        RoundTurnsVo rv = null;
        StringBuffer sb = null;
        // 如果班组详细存在
        if (maps != null && maps.size() > 0) {
            ruleMap = new ArrayList<String>();
            roundMap = new HashMap<Integer, List<RoundTurnsVo>>();
            for (RuleWorkTeamMapVo rtv : maps) {
                Integer turnsOrder = rtv.getTurnsOrder();
                rv = new RoundTurnsVo();
                rv.setRoundId(rtv.getRoundId());
                rv.setWorkTeamId(rtv.getWorkTeamId());
                rv.setRoundName(rtv.getRoundName());
                rv.setRuleMapId(rtv.getId());
                rv.setTurnsOrder(rtv.getTurnsOrder());
                rv.setWorkTeamName(rtv.getOrgName() + "-"
                    + rtv.getWorkTeamName());
                // 判读是否存在该班组的信息;
                roundVo = roundMap.get(turnsOrder);
                if (roundVo == null) {
                    roundVo = new ArrayList<RoundTurnsVo>();
                }
                roundVo.add(rv);
                roundMap.put(turnsOrder, roundVo);
            }
            // 在这里进行组装转换
            for (Integer ii : roundMap.keySet()) {
                sb = new StringBuffer();
                List<RoundTurnsVo> r = roundMap.get(ii);
                if (r != null && r.size() > 0) {
                    sb.append("{'turnsOrder':").append(ii).append(",");
                    for (RoundTurnsVo v : r) {
                        sb.append("'teamId&&")
                            .append(v.getRoundId())
                            .append("':")
                            .append(v.getWorkTeamId())
                            .append(",");
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
                ruleMap.add(sb.toString());
            }
            /*
             * //按照时间排序 Collections.sort(ruleMap);
             */
        }
        return ruleMap;
    }
    
    /**
     * 把班组规格详细转化以班组ID为key的map结构，方便前台简析;
     * 
     * @author 王鹏程 2013-11-27
     * @param maps
     * @return List<RuleMap>
     */
    private RuleMap getRuleMap1(List<RuleWorkTeamMapVo> maps) {
        RuleMap ruleMap = null;
        Map<Integer, List<RoundTurnsVo>> roundMap = null;
        List<RoundTurnsVo> roundVo = null;
        RoundTurnsVo rv = null;
        int maxCount = 0;
        // 如果接收的List<RuleWorkTeamMapVo>不为空
        if (maps != null && maps.size() > 0) {
            ruleMap = new RuleMap();
            ruleMap.setRuleId(maps.get(0).getRuleId());
            ruleMap.setRuleName(maps.get(0).getRuleName());
            ruleMap.setCycleDays(maps.get(0).getCycleDays());
            roundMap = new HashMap<Integer, List<RoundTurnsVo>>();
            for (RuleWorkTeamMapVo rtv : maps) {
                Integer workTeamId = rtv.getWorkTeamId();
                rv = new RoundTurnsVo();
                rv.setRoundId(rtv.getRoundId());
                rv.setWorkTeamId(rtv.getWorkTeamId());
                rv.setRoundName(rtv.getRoundName());
                rv.setRuleMapId(rtv.getId());
                rv.setTurnsOrder(rtv.getTurnsOrder());
                rv.setWorkTeamName(rtv.getOrgName() + "-"
                    + rtv.getWorkTeamName());
                // 判读是否存在该班组的信息;
                roundVo = roundMap.get(workTeamId);
                if (roundVo == null) {
                    roundVo = new ArrayList<RoundTurnsVo>();
                }
                roundVo.add(rv);
                roundMap.put(workTeamId, roundVo);
            }
            // 获得已有数据的行数;
            if (roundMap != null && roundMap.size() > 0) {
                Collection<List<RoundTurnsVo>> c = roundMap.values();
                Iterator<List<RoundTurnsVo>> it = c.iterator();
                for (; it.hasNext();) {
                    int count = it.next().size();
                    if (count > maxCount) {
                        maxCount = count;
                    }
                }
                ruleMap.setOldRuleCount(maxCount);
            }
            ruleMap.setRuleMaps(roundMap);
        }
        return ruleMap;
    }
    
    /**
     * 验证倒班规则名称在同一部门中的唯一性
     * 
     * @Title validateRuleName
     * @author dong.he
     * @date 2014年9月28日
     * @return String struts视图控制字符串
     */
    public String validateRuleName() {
        // 接收从前台传回的rule_id
        String val = this.getRequest().getParameter("val");
        String deptId = this.getRequest().getParameter("deptId");
        
        try {
            JsonUtil.outJson(workTurnsRuleService.validateRuleName(val, deptId));
        }
        catch (Exception e) {
            excepAndLogHandle(WorkTurnsRuleAction.class,
                "验证倒班规则名称在同一部门中的唯一性",
                e,
                false);
        }
        return null;
    }
    
    public IWorkTurnsRuleService getWorkTurnsRuleService() {
        return workTurnsRuleService;
    }
    
    public void setWorkTurnsRuleService(
        IWorkTurnsRuleService workTurnsRuleService) {
        this.workTurnsRuleService = workTurnsRuleService;
    }
    
    public WorkTurnsRuleVo getWorkTurnsRuleVo() {
        return workTurnsRuleVo;
    }
    
    public void setWorkTurnsRuleVo(WorkTurnsRuleVo workTurnsRuleVo) {
        this.workTurnsRuleVo = workTurnsRuleVo;
    }
    
    public WorkTurnsRule getWorkTurnsRule() {
        return workTurnsRule;
    }
    
    public void setWorkTurnsRule(WorkTurnsRule workTurnsRule) {
        this.workTurnsRule = workTurnsRule;
    }
    
    public IWorkRoundService getWorkRoundService() {
        return workRoundService;
    }
    
    public void setWorkRoundService(IWorkRoundService workRoundService) {
        this.workRoundService = workRoundService;
    }
    
    public IWorkTeamService getWorkTeamService() {
        return workTeamService;
    }
    
    public void setWorkTeamService(IWorkTeamService workTeamService) {
        this.workTeamService = workTeamService;
    }
    
    public List<RuleWorkTeamMap> getRuleWorkTeamMap() {
        return ruleWorkTeamMap;
    }
    
    public void setRuleWorkTeamMap(List<RuleWorkTeamMap> ruleWorkTeamMap) {
        this.ruleWorkTeamMap = ruleWorkTeamMap;
    }
    
    public List<String> getRuleMap() {
        return ruleMap;
    }
    
    public void setRuleMap(List<String> ruleMap) {
        this.ruleMap = ruleMap;
    }
    
    public IRuleWorkTeamMapService getRuleWorkTeamMapService() {
        return ruleWorkTeamMapService;
    }
    
    public void setRuleWorkTeamMapService(
        IRuleWorkTeamMapService ruleWorkTeamMapService) {
        this.ruleWorkTeamMapService = ruleWorkTeamMapService;
    }
    
    public String getDeleteRuleMapIds() {
        return deleteRuleMapIds;
    }
    
    public void setDeleteRuleMapIds(String deleteRuleMapIds) {
        this.deleteRuleMapIds = deleteRuleMapIds;
    }
    
    public static void main(String[] args) {
        String aa =
            "{'workTeamId':5,'workTeamName':'运行三班','round&&3':1,'map&&3':1,'3':,'下夜班','round&&2':2,'map&&2':2,'2':,'白班','round&&1':2,'map&&1':2,'1':,'白班','round&&1':2,'map&&1':2,'1':,'白班'}";
        JSONObject obj = JSONObject.fromObject(aa);
        System.out.println(obj.get("date"));
    }
    
    public Integer getRuleId() {
        return ruleId;
    }
    
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }
    
    public Integer getCycleDays() {
        return cycleDays;
    }
    
    public void setCycleDays(Integer cycleDays) {
        this.cycleDays = cycleDays;
    }
    
    public List<JSONObject> getRuleMaps() {
        return ruleMaps;
    }
    
    public void setRuleMaps(List<JSONObject> ruleMaps) {
        this.ruleMaps = ruleMaps;
    }
    
    public IWorkRulePlanService getWorkRulePlanService() {
        return workRulePlanService;
    }
    
    public void setWorkRulePlanService(IWorkRulePlanService workRulePlanService) {
        this.workRulePlanService = workRulePlanService;
    }
    
    public List<JSONObject> getPlanMaps() {
        return planMaps;
    }
    
    public void setPlanMaps(List<JSONObject> planMaps) {
        this.planMaps = planMaps;
    }
    
    public List<String> getAa() {
        return aa;
    }
    
    public void setAa(List<String> aa) {
        this.aa = aa;
    }
}
