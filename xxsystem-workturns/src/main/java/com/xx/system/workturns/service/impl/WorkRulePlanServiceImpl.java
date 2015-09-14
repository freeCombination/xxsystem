/**  
 * @文件名: WorkRulePlanServiceImpl.java
 * @版权:Copyright 2009-2013 版权所有：大庆金桥信息工程有限公司成都分公司
 * @描述: 倒班管理计划service实现类
 * @修改人: lizhengc
 * @修改时间: 2013-11-25 上午10:43:22
 * @修改内容:新增
 */
package com.dqgb.sshframe.workturns.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.util.DateUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.log.entity.Log;
import com.dqgb.sshframe.org.service.IOrgService;
import com.dqgb.sshframe.workturns.entity.WorkRulePlan;
import com.dqgb.sshframe.workturns.service.IWorkRulePlanService;
import com.dqgb.sshframe.workturns.vo.WorkRulePlanDateVo;
import com.dqgb.sshframe.workturns.vo.WorkRulePlanVo;

/**
 * 定义倒班管理计划逻辑接口实现
 * 
 * @Title WorkRulePlanServiceImpl
 * @author lizhengc
 * @version V1.40,2014-8-22
 * @Date 2014年9月10日
 * @see [相关类/方法]
 * @since V1.40
 */
@Service("workRulePlanService")
@Transactional(readOnly = false)
public class WorkRulePlanServiceImpl implements IWorkRulePlanService {
    /**
     * @Fields baseDao : 数据库操作基础方法
     */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    @Autowired
    @Qualifier("organizationService")
    private IOrgService orgService;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    
    /**
     * 更新倒班计划
     * 
     * @Title updateWorkRulePlan
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param plan 倒班计划对象
     * @throws BusinessException
     * @return
     */
    @Override
    public void updateWorkRulePlan(WorkRulePlan plan)
        throws BusinessException {
        baseDao.saveOrUpdate(plan);
    }
    
    /**
     * 批量的添加倒班计划
     * 
     * @Title batchAddWorkRulePlan
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param plans 倒班计划集合
     * @throws BusinessException
     * @return
     */
    @Override
    public void batchAddWorkRulePlan(List<WorkRulePlan> plans)
        throws BusinessException {
        baseDao.saveAll(plans);
    }
    
    /**
     * 根据倒班计划ID获得倒班计划
     * 
     * @Title getWorkRulePlanVoById
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param id 倒班计划id
     * @throws BusinessException
     * @return WorkRulePlanVo 倒班计划vo
     */
    @Override
    public WorkRulePlanVo getWorkRulePlanVoById(String id)
        throws BusinessException {
        WorkRulePlanVo vo = null;
        WorkRulePlan p =
            (WorkRulePlan)baseDao.queryEntityById(WorkRulePlan.class, id);
        if (p != null) {
            vo = new WorkRulePlanVo();
            vo.setId(p.getId());
            vo.setCycleDays(p.getWorkTurnsRule().getCycleDays());
            vo.setRoundId(p.getWorkRound().getId());
            vo.setRoundName(p.getWorkRound().getRoundName());
            vo.setRuleId(p.getWorkTurnsRule().getId());
            vo.setWorkTeamId(p.getWorkTeam().getId());
            vo.setWorkTeamName(p.getWorkTeam().getTeamName());
            vo.setRuleName(p.getWorkTurnsRule().getName());
            vo.setPlanDate(DateUtil.dateToString(p.getPlanDate(), "yyyy-MM-dd"));
        }
        return vo;
    }
    
    /**
     * 根据IDS删除倒班计划
     * 
     * @Title deleteWorkRulePlanByIds
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param ids 倒班计划id
     * @throws BusinessException
     * @return
     */
    @Override
    public void deleteWorkRulePlanByIds(String ids)
        throws BusinessException {
        String hql =
            " delete from WorkRulePlan wrp where wrp.id in(" + ids + ")";
        baseDao.executeHql(hql);
    }
    
    /**
     * 验证工作计划是否重复了
     * 
     * @Title validateWorkRulePlan
     * @author dong.he 2014-8-22
     * @param ruleId 规则id
     * @param workTeamId 班组id
     * @param roundId 班次id
     * @param planDate 计划天数
     * @Date 2014年9月2日
     * @throws BusinessException
     * @return WorkRulePlan 倒班计划对象
     */
    @Override
    public WorkRulePlan validateWorkRulePlan(Integer ruleId,
        Integer workTeamId, Integer roundId, String planDate)
        throws BusinessException {
        WorkRulePlan plan = null;
        Map pram = new HashMap();
        String hql =
            " from WorkRulePlan wrp where 1=1 " + " and wrp.workTurnsRule.id="
                + ruleId + " and wrp.workRound.id=" + roundId
                + " and wrp.planDate=:planDate";
        pram.put("planDate", DateUtil.stringToDate(planDate, "yyyy-dd-MM"));
        List<WorkRulePlan> list =
                (List<WorkRulePlan>)baseDao.findPageByQuery(0,
                		Integer.MAX_VALUE,
                    hql,
                    pram);
        
        if (list != null && list.size() > 0) {
            plan = list.get(0);
        }
        return plan;
    }
    
    /**
     * 通过倒班规则ID获取该规则的所有倒班计划
     * 
     * @Title getAllWorkRulePlanByRuleId
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param ruleId 规则id
     * @throws BusinessException
     * @return List<WorkRulePlanVo> 倒班计划vo集合
     */
    @Override
    public List<WorkRulePlanVo> getAllWorkRulePlanByRuleId(String ruleId)
        throws BusinessException {
        List<WorkRulePlanVo> vos = new ArrayList<WorkRulePlanVo>();
        WorkRulePlanVo vo = null;
        StringBuffer hql = new StringBuffer();
        hql.append(" from WorkRulePlan wrp where 1=1 ");
        List<WorkRulePlan> plans =
            (List<WorkRulePlan>)baseDao.queryEntitys(hql.toString());
        
        if (plans != null && plans.size() > 0) {
            for (WorkRulePlan p : plans) {
                vo = new WorkRulePlanVo();
                vo.setCycleDays(p.getWorkTurnsRule().getCycleDays());
                vo.setId(p.getId());
                vo.setPlanDate(DateUtil.dateToString(p.getPlanDate(),
                    "yyyy-MM-dd"));
                vo.setRoundId(p.getWorkRound().getId());
                vo.setRoundName(p.getWorkRound().getRoundName());
                vo.setRuleId(p.getWorkTurnsRule().getId());
                vo.setRuleName(p.getWorkTurnsRule().getName());
                vo.setWorkTeamId(p.getWorkTeam().getId());
                vo.setWorkTeamName(p.getWorkTeam().getTeamName());
                vos.add(vo);
            }
        }
        return vos;
    }
    
    /**
     * 根据参数获得所有的已经生成的倒班计划
     * 
     * @Title getWorkRulePlanListByParams
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param maps 倒班计划vo集合
     * @throws BusinessException
     * @return List<WorkRulePlanVo> 倒班计划vo集合
     */
    @Override
    public ListVo<WorkRulePlanVo> getWorkRulePlanListByParams(
        Map<String, String> paramsMap)
        throws BusinessException {
        ListVo<WorkRulePlanVo> pvo = new ListVo<WorkRulePlanVo>();
        List<WorkRulePlanVo> vos = null;
        WorkRulePlanVo vo = null;
        Map pram = new HashMap();
        String start = paramsMap.get("start");
        String limit = paramsMap.get("limit");
        String ruleId = paramsMap.get("ruleId");
        String startDate = paramsMap.get("workRulePlanStartDate");
        String endDate = paramsMap.get("workRulePlanEndDate");
        
        StringBuffer hql = new StringBuffer();
        StringBuffer hqlCount = new StringBuffer();
        hql.append(" from WorkRulePlan wrp where 1=1 ");
        hqlCount.append(" select count(*) from WorkRulePlan wrp where 1=1 ");
        if (StringUtils.isNotBlank(ruleId)) {
            hql.append(" and wrp.workTurnsRule.id ="
                + NumberUtils.toInt(ruleId));
            hqlCount.append(" and wrp.workTurnsRule.id ="
                + NumberUtils.toInt(ruleId));
        }
       
        if (StringUtils.isNotBlank(startDate)) {
            hql.append(" and wrp.planDate>=:startDate ");
            hqlCount.append(" and wrp.planDate>=:startDate ");
            pram.put("startDate", DateUtil.string2date(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            hql.append(" and wrp.planDate<:endDate ");
            hqlCount.append(" and wrp.planDate<:endDate ");
            Date d = new Date(DateUtil.string2date(endDate).getTime()+24*60*60*1000);
        	pram.put("endTime", d);
        }
        hql.append(" order by wrp.planDate ");
        
        // 获得班次的数量，便于取得数据的行数;
        int roundCount =
            baseDao.getTotalCount("select count(*) from WorkRound ",
                new HashMap<String, Object>());
        int startCount = NumberUtils.toInt(start) * roundCount;
        int limitCount = NumberUtils.toInt(limit) * roundCount;
        List<WorkRulePlan> plans =
            (List<WorkRulePlan>)baseDao.queryEntitysByPage(startCount,
                limitCount,
                hql.toString(),
                pram);
        int count =
            baseDao.getTotalCount(hqlCount.toString(),
                pram);
        
        if (plans != null && plans.size() > 0) {
            vos = new ArrayList<WorkRulePlanVo>();
            for (WorkRulePlan p : plans) {
                vo = new WorkRulePlanVo();
                vo.setCycleDays(p.getWorkTurnsRule().getCycleDays());
                vo.setId(p.getId());
                vo.setPlanDate(DateUtil.dateToString(p.getPlanDate(),
                    "yyyy-MM-dd"));
                vo.setRoundId(p.getWorkRound().getId());
                vo.setRoundName(p.getWorkRound().getRoundName());
                vo.setRuleId(p.getWorkTurnsRule().getId());
                vo.setRuleName(p.getWorkTurnsRule().getName());
                vo.setWorkTeamId(p.getWorkTeam().getId());
                vo.setWorkTeamName(p.getWorkTeam().getTeamName());
                vo.setOrgId(p.getWorkTeam().getOrg().getOrgId());
                vo.setOrgName(p.getWorkTeam().getOrg().getOrgName());
                vos.add(vo);
            }
        }
        pvo.setList(vos);
        pvo.setTotalSize(count/roundCount);
        return pvo;
    }
    

    /**
     * 删除指定时间段内的倒班计划
     * 
     * @Title deleteTurnsPlanByDate
     * @author dong.he
     * @date 2014年10月8日
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @throws BusinessException
     */
    @Override
    public void deleteTurnsPlanByDate(String ruleId, String startDate, String endDate)
        throws BusinessException {
    	
    	Map<String,Object> pram = new HashMap<String,Object>();
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)
            && StringUtils.isNotBlank(ruleId)) {
            // 新建删除倒班计划的HQL语句
            StringBuffer hql = new StringBuffer(" delete from WorkRulePlan wrp where ");
            hql.append(" wrp.workTurnsRule.id = ");
            hql.append(ruleId);
            hql.append(" and wrp.planDate >= :startDate ");
            hql.append(" and wrp.planDate <= :endDate ");
            pram.put("startDate", DateUtil.string2date(startDate));
            pram.put("endDate", DateUtil.string2date(endDate));
            baseDao.executeHql(hql.toString(),pram);
        }
    }
}
