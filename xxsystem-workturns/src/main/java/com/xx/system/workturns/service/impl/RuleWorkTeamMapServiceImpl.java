package com.dqgb.sshframe.workturns.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.workturns.entity.RuleWorkTeamMap;
import com.dqgb.sshframe.workturns.service.IRuleWorkTeamMapService;
import com.dqgb.sshframe.workturns.vo.RuleWorkTeamMapVo;

/**
 * 定义倒班计划逻辑接口
 * 
 * @author dong.he
 * @version V1.40,2014-8-22
 * @see [相关类/方法]
 * @since V1.40
 */
@Service("ruleWorkTeamMapService")
@Transactional(readOnly = false)
public class RuleWorkTeamMapServiceImpl implements IRuleWorkTeamMapService
{
    /**
     * @Fields baseDao : 数据库操作基础方法
     */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao)
    {
        this.baseDao = baseDao;
    }
    
    /**
     * 保存规则详细
     * 
     * @author dong.he 2014-8-22
     * @param params
     * @throws BusinessException
     * @return void
     */
    @Override
    public void saveRuleWorkTeamMap(List<RuleWorkTeamMap> maps)
        throws BusinessException
    {
        baseDao.saveOrUpdate(maps);
    }
    
    /**
     * 根据规则ID获得规则对应的规则详细
     * 
     * @author dong.he 2014-8-22
     * @param ruleId
     * @throws BusinessException
     * @return List<RuleWorkTeamMap>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RuleWorkTeamMapVo> getRuleWorkTeamMapByRuleId(String ruleId)
        throws BusinessException
    {
        List<RuleWorkTeamMapVo> vos = null;
        RuleWorkTeamMapVo vo = null;
        
        String hql =
            " from RuleWorkTeamMap rtm where rtm.workTurnsRule.id= "
                + Integer.valueOf(ruleId)
                + " order by rtm.workTurnsRule.id,rtm.workTeam.id,rtm.turnsOrder desc";
        List<RuleWorkTeamMap> rules = (List<RuleWorkTeamMap>)baseDao.queryEntitys(hql);
        if (rules != null && rules.size() > 0)
        {
            vos = new ArrayList<RuleWorkTeamMapVo>();
            for (RuleWorkTeamMap r : rules)
            {
                vo = new RuleWorkTeamMapVo();
                vo.setId(r.getId());
                vo.setRoundId(r.getWorkRound().getId());
                vo.setRoundName(r.getWorkRound().getRoundName());
                vo.setRuleId(r.getWorkTurnsRule().getId());
                vo.setRuleName(r.getWorkTurnsRule().getName());
                vo.setWorkTeamId(r.getWorkTeam().getId());
                vo.setWorkTeamName(r.getWorkTeam().getTeamName());
                vo.setOrgId(r.getWorkTeam().getOrg().getOrgId());
                vo.setOrgName(r.getWorkTeam().getOrg().getOrgName());
                vo.setTurnsOrder(r.getTurnsOrder());
                vo.setCycleDays(r.getWorkTurnsRule().getCycleDays());
                vos.add(vo);
            }
        }
        return vos;
    }
    
    /**
     * 批量删除倒班规则的规则详细
     * 
     * @author dong.he 2014-8-22
     * @param ids
     * @throws BusinessException
     * @return void
     */
    @Override
    public void deleteRuleWorkTeamMapByIds(String ids)
        throws BusinessException
    {
        String hql =
            " delete from RuleWorkTeamMap rtm where rtm.id in(" + ids + ")";
        baseDao.executeHql(hql);
    }
    
}