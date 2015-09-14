/**  
 * @文件名 IWorkTurnsRuleService.java
 * @版权 Copyright 2009-2013 版权所有：大庆金桥信息工程有限公司成都分公司
 * @描述 倒班规则管理逻辑接口
 * @作者 liukang-wb
 * @时间 2014-9-5 上午16:37:09
 */
package com.dqgb.sshframe.workturns.service;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.workturns.entity.WorkTurnsRule;
import com.dqgb.sshframe.workturns.vo.WorkTurnsRuleVo;

/**
 * 定义倒班规则逻辑接口
 * 
 * @author dong.he
 * @version V1.40,2014-8-22
 * @see [相关类/方法]
 * @since V1.40
 */
public interface IWorkTurnsRuleService {
    
    /**
     * 据参数获得所有的倒班规则
     * 
     * @Title getWorkTurnsRuleListByParams
     * @author liukang-wb
     * @date 2014年9月11日
     * @param maps 从页面上获取的分页参数和查询条件
     * @return ListVo<WorkTurnsRuleVo> 返回的是WorkTurnsRuleVO集合
     * @throws BusinessException
     */
    public ListVo<WorkTurnsRuleVo> getWorkTurnsRuleListByParams(
        Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 根据ID获得倒班规则VO
     * 
     * @Title getWorkTurnsRuleVoById
     * @author liukang-wb
     * @date 2014年9月11日
     * @param id 倒班规则id
     * @return WorkTurnsRuleVo 返回倒班规则VO
     * @throws BusinessException
     */
    public WorkTurnsRuleVo getWorkTurnsRuleVoById(String id)
        throws BusinessException;
    
    /**
     * 添加倒班规则
     * 
     * @Title addWorkTurnsRule
     * @author liukang-wb
     * @date 2014年9月11日
     * @param wtr 倒班规则实体
     * @throws BusinessException
     */
    public void addWorkTurnsRule(WorkTurnsRule wtr)
        throws BusinessException;
    
    /**
     * 更新倒班规则
     * 
     * @Title updateWorkTurnsRule
     * @author liukang-wb
     * @date 2014年9月11日
     * @param wtr 倒班规则实体
     * @return
     * @throws BusinessException
     */
    public String updateWorkTurnsRule(WorkTurnsRule wtr)
        throws BusinessException;
    
    /**
     * 删除倒班规则
     * 
     * @Title deleteWorkTurnsRule
     * @author liukang-wb
     * @date 2014年9月11日
     * @param ids 倒班规则id字符串
     * @throws BusinessException
     */
    public void deleteWorkTurnsRule(String ids)
        throws BusinessException;
    
    /**
     * 通过ID获得倒班计划
     * 
     * @Title getWorkTurnsRuleById
     * @author liukang-wb
     * @date 2014年9月11日
     * @param id 倒班规则id
     * @return 倒班规则实体
     * @throws BusinessException
     */
    WorkTurnsRule getWorkTurnsRuleById(Integer id)
        throws BusinessException;
    
    /**
     * 据参数获得所有的倒班规则
     * 
     * @Title getAllWorkTurnsRuleByParams
     * @author liukang-wb
     * @date 2014年9月11日
     * @param maps 从前台传回的参数
     * @return 倒班规则VO集合
     * @throws BusinessException
     */
    List<WorkTurnsRuleVo> getAllWorkTurnsRuleByParams(Map<String, Object> maps)
        throws BusinessException;
    
    /**
     * 判断班组是否存在
     * 
     * @Title workTeamIsExsit
     * @author liukang-wb
     * @date 2014年9月10日
     * @param ruleId 倒班规则ID
     * @return int 存在班组数量
     */
    public int workTeamIsExsit(String ruleId)
        throws BusinessException;
    
    /**
     * 删除倒班规则详细
     * 
     * @Title delAllWorkTurnsDetailByRuleId
     * @author liukang-wb
     * @date 2014年9月10日
     * @param ruleId 倒班规则ID
     * @throws BusinessException
     */
    public void delAllWorkTurnsDetailByRuleId(String ruleId)
        throws BusinessException;
    
    /**
     * 验证倒班规则名称在同一部门中的唯一性
     * 
     * @Title validateRuleName
     * @author dong.he
     * @date 2014年9月28日
     * @param val 倒班规则名称
     * @param deptId 组织ID
     * @return Map<String, Object> 验证结果
     * @throws BusinessException
     */
    public Map<String, Object> validateRuleName(String val, String deptId)
        throws BusinessException;
}
