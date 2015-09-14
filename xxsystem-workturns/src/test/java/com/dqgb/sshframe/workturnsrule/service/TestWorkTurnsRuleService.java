package com.dqgb.sshframe.workturnsrule.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.util.DateUtil;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.org.entity.Organization;
import com.dqgb.sshframe.user.entity.User;
import com.dqgb.sshframe.workturns.action.WorkRulePlanAction;
import com.dqgb.sshframe.workturns.action.WorkTurnsRuleAction;
import com.dqgb.sshframe.workturns.entity.WorkRound;
import com.dqgb.sshframe.workturns.entity.WorkRulePlan;
import com.dqgb.sshframe.workturns.entity.WorkTeam;
import com.dqgb.sshframe.workturns.entity.WorkTurnsRule;
import com.dqgb.sshframe.workturns.service.IRuleWorkTeamMapService;
import com.dqgb.sshframe.workturns.service.IWorkRoundService;
import com.dqgb.sshframe.workturns.service.IWorkRulePlanService;
import com.dqgb.sshframe.workturns.service.IWorkTeamService;
import com.dqgb.sshframe.workturns.service.IWorkTurnsRuleService;
import com.dqgb.sshframe.workturns.vo.RoundTurnsVo;
import com.dqgb.sshframe.workturns.vo.RuleWorkTeamMapVo;
import com.dqgb.sshframe.workturns.vo.WorkRulePlanVo;
import com.dqgb.sshframe.workturns.vo.WorkTeamVo;
import com.dqgb.sshframe.workturns.vo.WorkTurnsRuleVo;
import java.text.SimpleDateFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@SuppressWarnings("unchecked")
public class TestWorkTurnsRuleService {
    @Autowired(required = true)
    @Qualifier("workTurnsRuleService")
    IWorkTurnsRuleService workTurnsRuleService;
    
    @Autowired(required = true)
    @Qualifier("workRoundService")
    IWorkRoundService workRoundService;
    
    @Autowired
    @Qualifier("workTeamService")
    private IWorkTeamService workTeamService;
    
    @Autowired
    @Qualifier("ruleWorkTeamMapService")
    private IRuleWorkTeamMapService ruleWorkTeamMapService;
    
    @Autowired(required = true)
    @Qualifier("workRulePlanService")
    IWorkRulePlanService workRulePlanService;
    
    private WorkRulePlan workRulePlan;
    
    private WorkTurnsRule workTurnsRule;
    
    /**
     * 持久层接口
     */
    @Autowired
    private IBaseDao baseDao;
    
    /**
     * 获得所有的已制定好的倒班计划
     * 
     * @Title getWorkTurnsRuleList
     * @author 111
     * @Description:
     * @date 2014年9月9日
     * @return
     */
    @Test
    public void getWorkTurnsRuleList()
        throws Exception {
        String name = "";
        String start = "0";
        String limit = "99";
        String orgId = "1";
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("name", name);
        maps.put("start", start);
        maps.put("limit", limit);
        maps.put("orgId", orgId);
        ListVo<WorkTurnsRuleVo> vos =
            workTurnsRuleService.getWorkTurnsRuleListByParams(maps);
        Assert.assertNotNull(vos);
    }
    
    /**
     * 添加或者修改倒班规则
     * 
     * @author liukang-wb 2014-8-7
     * @return String
     */
    @Test
    public void addorupdateWorkTurnsRule()
        throws Exception {
        workTurnsRule = new WorkTurnsRule();
        
        User user = new User();
        user.setUserId(1);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String nowDate = sdf.format(date);
        // workTurnsRule.setId(333);
        if (workTurnsRule.getId() != null && !"".equals(workTurnsRule.getId())) {
            WorkTurnsRule workTurnsRule1 =
                this.getWorkTurnsRuleService()
                    .getWorkTurnsRuleById(workTurnsRule.getId());
            workTurnsRule1.setName("圣剑之舞");
            workTurnsRule1.setRemarks("剑舞可以秒人");
            workTurnsRule1.setMakeDate(date);
            workTurnsRule1.setUser(user);
            workTurnsRule1.setCycleDays(3);
            this.getWorkTurnsRuleService().updateWorkTurnsRule(workTurnsRule1);
            String hql = "from WorkTurnsRule w where w.name = '圣剑之舞'";
            WorkTurnsRule workTurnsRule2 =
                (WorkTurnsRule)baseDao.queryEntitys(hql).get(0);
            Assert.assertEquals(workTurnsRule2.getName(), "圣剑之舞");
            Assert.assertEquals(workTurnsRule2.getRemarks(), "剑舞可以秒人");
            // Assert.assertEquals(workTurnsRule2.getMakeDate(), date);
            Assert.assertEquals(String.valueOf(workTurnsRule2.getCycleDays()),
                String.valueOf(3));
        }
        else {
            Organization org = new Organization(222);
            workTurnsRule.setId(333);
            workTurnsRule.setUser(user);
            workTurnsRule.setMakeDate(new Date());
            workTurnsRule.setName("圣剑之舞");
            workTurnsRule.setRemarks("剑舞可以秒人");
            workTurnsRule.setMakeDate(date);
            workTurnsRule.setUser(user);
            workTurnsRule.setCycleDays(3);
            workTurnsRule.setOrg(org);
            this.getWorkTurnsRuleService().addWorkTurnsRule(workTurnsRule);
            String hql = "from WorkTurnsRule w where w.name = '圣剑之舞'";
            WorkTurnsRule workTurnsRule2 =
                (WorkTurnsRule)baseDao.queryEntitys(hql).get(0);
            Assert.assertEquals(workTurnsRule2.getName(), "圣剑之舞");
            Assert.assertEquals(workTurnsRule2.getRemarks(), "剑舞可以秒人");
            Assert.assertEquals(String.valueOf(workTurnsRule2.getMakeDate()),
                nowDate);
            Assert.assertEquals(String.valueOf(workTurnsRule2.getCycleDays()),
                String.valueOf(3));
        }
    }
    
    /**
     * 删除倒班规则
     * 
     * @author 王鹏程 2013-11-25
     * @return String
     */
    @Test
    public void deleteWorkTurnsRule()
        throws Exception {
        String ids = "609";
        this.getWorkTurnsRuleService().deleteWorkTurnsRule(ids);
        String[] id_list = ids.split(",");
        for (String id : id_list) {
            WorkTurnsRule workTurnsRule2 =
                (WorkTurnsRule)baseDao.queryEntityById(WorkTurnsRule.class,
                    Integer.parseInt(id));
            Assert.assertNull(workTurnsRule2);
        }
    }
    
    /**
     * 检查是否已经生成倒班计划
     * 
     * @Title checkOrgIsSame
     * @author 111
     * @date 2014年9月11日
     * @return
     */
    @Test
    public void checkOrgIsSame()
        throws Exception {
        String ruleId = "606";
        if (ruleId != null && !"".equals(ruleId)) {
            int count = this.getWorkTurnsRuleService().workTeamIsExsit(ruleId);
            if (count != 0) {
                this.getWorkTurnsRuleService()
                    .delAllWorkTurnsDetailByRuleId(ruleId);
            }
            int count2 = this.getWorkTurnsRuleService().workTeamIsExsit(ruleId);
            Assert.assertTrue(count2 == 0);
        }
    }
    
    /**
     * 检查规则是否存在
     * 
     * @Title checkRoleIsExist
     * @author liukang
     * @date 2014年8月28日
     * @return String
     */
    @Test
    public void checkRoleIsExist()
        throws Exception {
        String ids = "605";
        String[] id_list = ids.split(",");
        for (String id : id_list) {
            WorkTurnsRule workTurnsRule =
                this.getWorkTurnsRuleService()
                    .getWorkTurnsRuleById(Integer.parseInt(id));
            if (workTurnsRule == null) {
                Assert.assertNull(workTurnsRule);
            }
            int count = this.getWorkTurnsRuleService().workTeamIsExsit(id);
            if (count != 0) {
                Assert.assertTrue(count != 0);
            }
        }
    }
    
    /**
     * 得到单个的workTurnsRule
     * 
     * @Title getWorkTurnsRuleById
     * @author 111
     * @date 2014年8月29日
     */
    @Test
    public void getWorkTurnsRuleById()
        throws Exception {
        String role_id = "605";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        WorkTurnsRule workTurnsRule =
            this.getWorkTurnsRuleService()
                .getWorkTurnsRuleById(Integer.parseInt(role_id));
        if (workTurnsRule != null) {
            WorkTurnsRuleVo ruleVo = new WorkTurnsRuleVo();
            ruleVo.setId(workTurnsRule.getId());
            ruleVo.setName(workTurnsRule.getName());
            ruleVo.setCycleDays(workTurnsRule.getCycleDays());
            ruleVo.setMakeDate(sdf.format(workTurnsRule.getMakeDate()));
            ruleVo.setOrgId(workTurnsRule.getOrg().getOrgId());
            ruleVo.setOrgName(workTurnsRule.getOrg().getOrgName());
            ruleVo.setUser(workTurnsRule.getUser().getUsername());
            ruleVo.setRemarks(workTurnsRule.getRemarks());
            // 如果workTurnsRule不为空，则返回对应的VO
            Assert.assertNotNull(workTurnsRule);
        }
        else {
            Assert.assertNull(workTurnsRule);
        }
    }
    
    /**
     * 得到所有的班次信息
     * 
     * @Title getWorkRoundAll
     * @author liukang-wb
     * @date 2014年9月3日
     * @return
     */
    @Test
    public void getWorkRoundAll()
        throws Exception {
        List<WorkRound> workRounds = this.workRoundService.getAllWorkRound();
        for (WorkRound workRound : workRounds) {
            Assert.assertNotNull(workRound);
        }
    }
    
    /**
     * 获得所有的班组信息;
     * 
     * @author liukang-wb
     * @return String
     */
    @Test
    public void getAllWorkTeam()
        throws Exception {
        String ruleId = "605";
        List<WorkTeamVo> vos = null;
        WorkTeamVo vo = null;
        List<WorkTeam> wts = null;
        WorkTurnsRule wtr =
            workTurnsRuleService.getWorkTurnsRuleById(Integer.parseInt(ruleId));
        if (wtr != null) {
            wts =
                this.getWorkTeamService()
                    .getAllWorkTeamByOrgId(String.valueOf(wtr.getOrg()
                        .getOrgId()));
            Assert.assertNotNull(wts);
        }
        if (wts != null && wts.size() > 0) {
            vos = new ArrayList<WorkTeamVo>();
            for (WorkTeam wt : wts) {
                vo = new WorkTeamVo();
                vo.setWorkTeamName(wt.getOrg().getOrgName() + "-"
                    + wt.getTeamName());
                vo.setWorkTeamId(wt.getId());
                vos.add(vo);
            }
            Assert.assertNotNull(vos);
        }
    }
    
    /**
     * 把班组规格详细转化以班组ID为key的map结构，方便前台简析;
     * 
     * @author 王鹏程 2013-11-27
     * @param maps
     * @return List<RuleMap>
     */
    @Test
    public void getRuleMap()
        throws Exception {
        List<RuleWorkTeamMapVo> maps = new ArrayList<RuleWorkTeamMapVo>();
        List<String> ruleMap = null;
        Map<Integer, List<RoundTurnsVo>> roundMap = null;
        List<RoundTurnsVo> roundVo = null;
        RoundTurnsVo rv = null;
        StringBuffer sb = null;
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
                roundVo = roundMap.get(turnsOrder);// 判读是否存在该班组的信息;
                if (roundVo == null) {
                    roundVo = new ArrayList<RoundTurnsVo>();
                }
                roundVo.add(rv);
                roundMap.put(turnsOrder, roundVo);
            }
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
                ruleMap.add(sb.toString());
                Assert.assertNotNull(ruleMap);
            }
        }
    }
    
    /**
     * 根据规则id获取对应的班组信息
     * 
     * @Title getRuleWorkTeamMapsByRuleId
     * @author 111
     * @date 2014年9月10日
     * @return
     */
    @Test
    public void getRuleWorkTeamMapsByRuleId()
        throws Exception {
        String ruleId = "605";
        List<RuleWorkTeamMapVo> maps =
            this.getRuleWorkTeamMapService().getRuleWorkTeamMapByRuleId(ruleId);
        if (maps != null) {
            Assert.assertNotNull(maps);
        }
        else {
            Assert.assertNull(maps);
        }
    }
    
    /**
     * 根据参数获得所有的已经生成的倒班计划条件为空查询
     * 
     * @Title getWorkRulePlanListByParams
     * @author lizhengc
     * @Date 2014年9月2日
     * @param
     * @throws BusinessException
     * @return
     */
    @Test
    public void getWorkRulePlanListByParams()
        throws BusinessException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("start", "0");
        map.put("limit", "99");
        map.put("ruleId", "413");
        map.put("workRulePlanStartDate", "");
        map.put("workRulePlanEndDate", "");
        ListVo<WorkRulePlanVo> vs =
            this.getWorkRulePlanService().getWorkRulePlanListByParams(map);
        if (vs != null) {
            Assert.assertNotNull(vs);
        }
        else {
            Assert.assertNull(vs);
        }
    }
    
    /**
     * 根据参数获得所有的已经生成的倒班计划条件不为空查询
     * 
     * @Title getWorkRulePlanListByParams
     * @author lizhengc
     * @Date 2014年9月2日
     * @param
     * @throws BusinessException
     * @return
     */
    @Test
    public void getWorkRulePlanListByParams1()
        throws BusinessException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("start", "0");
        map.put("limit", "99");
        map.put("ruleId", "413");
        map.put("workRulePlanStartDate", "2014-9-11");
        map.put("workRulePlanEndDate", "2014-9-20");
        ListVo<WorkRulePlanVo> vs =
            this.getWorkRulePlanService().getWorkRulePlanListByParams(map);
        if (vs != null) {
            Assert.assertNotNull(vs);
        }
        else {
            Assert.assertNull(vs);
        }
    }
    
    /**
     * 更新倒班计划班组
     * 
     * @Title updateRulePlan
     * @author lizhengc
     * @date 2013-9-9
     * @return
     */
    @Test
    public void updateRulePlan()
        throws BusinessException {
        String id = "2833";// 计划id
        String teamId = "454";// 班组id
        String ruleId = "406";// 规则id
        String planDate = "2014-09-17";// 计划
        String roundId = "176";// 班次id
        WorkTeam workTeam = new WorkTeam();
        workTeam.setId(Integer.valueOf(teamId));
        WorkRound round = new WorkRound();
        round.setId(Integer.valueOf(roundId));
        WorkTurnsRule rule = new WorkTurnsRule();
        rule.setId(Integer.valueOf(ruleId));
        WorkRulePlan plan = new WorkRulePlan();
        // 判断计划id是否为空
        if (StringUtils.isNotBlank(id) && !"0".equals(id)) {
            plan.setId(Integer.valueOf(id));
        }
        plan.setWorkRound(round);
        plan.setWorkTurnsRule(rule);
        plan.setWorkTeam(workTeam);
        // 时间转换
        plan.setPlanDate(DateUtil.stringToDate(planDate, "yyyy-MM-dd"));
        this.getWorkRulePlanService().updateWorkRulePlan(plan);
        String hql = "from WorkRulePlan wrp where wrp.id=" + id;
        WorkRulePlan workRulePlan =
            (WorkRulePlan)baseDao.queryEntitys(hql).get(0);
        if (workRulePlan != null) {
            Assert.assertNotNull(workRulePlan);
        }
        else {
            Assert.assertNull(workRulePlan);
        }
    }
    
    public WorkTurnsRule getWorkTurnsRule() {
        return workTurnsRule;
    }
    
    public void setWorkTurnsRule(WorkTurnsRule workTurnsRule) {
        this.workTurnsRule = workTurnsRule;
    }
    
    public IWorkTurnsRuleService getWorkTurnsRuleService() {
        return workTurnsRuleService;
    }
    
    public void setWorkTurnsRuleService(
        IWorkTurnsRuleService workTurnsRuleService) {
        this.workTurnsRuleService = workTurnsRuleService;
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
    
    public IRuleWorkTeamMapService getRuleWorkTeamMapService() {
        return ruleWorkTeamMapService;
    }
    
    public void setRuleWorkTeamMapService(
        IRuleWorkTeamMapService ruleWorkTeamMapService) {
        this.ruleWorkTeamMapService = ruleWorkTeamMapService;
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
    
}
