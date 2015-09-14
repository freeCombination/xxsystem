package com.dqgb.sshframe.workturns.service;

import java.util.List;

import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.workturns.entity.RuleWorkTeamMap;
import com.dqgb.sshframe.workturns.vo.RuleWorkTeamMapVo;

/**
 * 定义规则详细逻辑接口
 * 
 * @author dong.he
 * @version V1.40,2014-8-22
 * @see [相关类/方法]
 * @since V1.40
 */
public interface IRuleWorkTeamMapService
{
    /**
     * 保存规则详细
     * 
     * @author dong.he 2014-8-22
     * @param params
     * @throws BusinessException
     * @return void
     */
    public void saveRuleWorkTeamMap(List<RuleWorkTeamMap> params)
        throws BusinessException;
    
    /**
     * 根据规则ID获得规则对应的规则详细
     * 
     * @author dong.he 2014-8-22
     * @param ruleId
     * @throws BusinessException
     * @return List<RuleWorkTeamMap>
     */
    public List<RuleWorkTeamMapVo> getRuleWorkTeamMapByRuleId(String ruleId)
        throws BusinessException;
    
    /**
     * 批量删除倒班规则的规则详细
     * 
     * @author dong.he 2014-8-22
     * @param ids
     * @throws BusinessException
     * @return void
     */
    public void deleteRuleWorkTeamMapByIds(String ids)
        throws BusinessException;
}
