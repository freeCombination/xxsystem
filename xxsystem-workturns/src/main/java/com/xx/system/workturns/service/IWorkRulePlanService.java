/**  
 * @文件名: IWorkRulePlanService.java
 * @版权:Copyright 2009-2013 版权所有：大庆金桥信息工程有限公司成都分公司
 * @描述: 倒班管理计划service接口类
 * @修改人: lizhengc
 * @修改时间: 2013-11-25 上午10:43:22
 * @修改内容:新增
 */
package com.dqgb.sshframe.workturns.service;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.workturns.entity.WorkRulePlan;
import com.dqgb.sshframe.workturns.vo.WorkRulePlanDateVo;
import com.dqgb.sshframe.workturns.vo.WorkRulePlanVo;

/**
 * 定义倒班管理计划逻辑接口
 * 
 * @Title IWorkRulePlanService
 * @author lizhengc
 * @version V1.40,2014-8-22
 * @Date 2014年9月10日
 * @see [相关类/方法]
 * @since V1.40
 */
public interface IWorkRulePlanService {
    
    /**
     * 根据参数获得所有的已经生成的倒班计划
     * 
     * @Title getAllWorkRulePlansByParams
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param maps 返回vo集合
     * @throws BusinessException
     * @return ListVo<WorkRulePlanVo> WorkRulePlanVo集合
     */
    /*
     * public ListVo<WorkRulePlanVo> getAllWorkRulePlansByParams( Map<String, String> maps) throws
     * BusinessException;
     */
    
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
    public void updateWorkRulePlan(WorkRulePlan plan)
        throws BusinessException;
    
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
    void batchAddWorkRulePlan(List<WorkRulePlan> plans)
        throws BusinessException;
    
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
    WorkRulePlanVo getWorkRulePlanVoById(String id)
        throws BusinessException;
    
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
    void deleteWorkRulePlanByIds(String ids)
        throws BusinessException;
    
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
    WorkRulePlan validateWorkRulePlan(Integer ruleId, Integer workTeamId,
        Integer roundId, String planDate)
        throws BusinessException;
    
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
    List<WorkRulePlanVo> getAllWorkRulePlanByRuleId(String ruleId)
        throws BusinessException;
    
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
    ListVo<WorkRulePlanVo> getWorkRulePlanListByParams(Map<String, String> maps)
        throws BusinessException;
    
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
    public void deleteTurnsPlanByDate(String ruleId, String startDate, String endDate)
        throws BusinessException;
}
