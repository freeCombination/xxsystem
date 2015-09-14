package com.dqgb.sshframe.workturns.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.util.DateUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.org.entity.OrgUser;
import com.dqgb.sshframe.org.service.IOrgService;
import com.dqgb.sshframe.user.vo.UserVo;
import com.dqgb.sshframe.workturns.entity.WorkTurnsRule;
import com.dqgb.sshframe.workturns.service.IWorkTurnsRuleService;
import com.dqgb.sshframe.workturns.vo.WorkTurnsRuleVo;

/**
 * 班次逻辑接口实现
 */
@Service("workTurnsRuleService")
@Transactional(readOnly = false)
public class WorkTurnsRuleServiceImpl implements IWorkTurnsRuleService {
    
    /**
     * @Fields baseDao : 数据库操作基础方法
     */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    @Autowired
    @Qualifier("organizationService")
    private IOrgService orgService;
    
    /**
     * 根据orgIds获取userIds
     * <p>
     * Title getUserIdsByOrgIds
     * </p>
     * <p>
     * Author dong.he
     * </p>
     * 
     * @param String orgIds
     * @return
     * @throws BizException
     */
    @SuppressWarnings("unchecked")
    public String getUserIdsByOrgIds(String orgIds)
        throws BusinessException {
        StringBuffer hql = new StringBuffer();
        hql.append(" from OrgUser ou where 1 = 1");
        hql.append(" and ou.organization.orgId in(" + orgIds + ")");
        
        // 执行查询
        List<OrgUser> list =
            (List<OrgUser>)baseDao.queryEntitys(hql.toString());
        
        String userIds = "";
        if (!CollectionUtils.isEmpty(list)) {
            for (OrgUser ou : list) {
                userIds += "," + ou.getUser().getUserId();
            }
        }
        
        if (StringUtils.isNotBlank(userIds)) {
            userIds = userIds.substring(1);
        }
        
        return userIds;
    }
    
    /**
     * 根据参数获得所有的倒班规则
     * 
     * @Title getWorkTurnsRuleListByParams
     * @author liukang-wb
     * @date 2014年9月11日
     * @param maps 从页面上获取的分页参数和查询条件
     * @return ListVo<WorkTurnsRuleVo> 返回的是WorkTurnsRuleVO集合
     * @throws BusinessException
     */
    @SuppressWarnings("unchecked")
    @Override
    public ListVo<WorkTurnsRuleVo> getWorkTurnsRuleListByParams(
        Map<String, String> maps)
        throws BusinessException {
        StringBuffer hql = new StringBuffer();
        ListVo<WorkTurnsRuleVo> vos = new ListVo<WorkTurnsRuleVo>();
        List<WorkTurnsRuleVo> wtvos = null;
        WorkTurnsRuleVo wtvo = null;
        String name = (String)maps.get("name");
        String start = (String)maps.get("start");
        String limit = (String)maps.get("limit");
        String orgId = (String)maps.get("orgId");
        String ids = (String)maps.get("orgIds");
        StringBuffer hqlCount = new StringBuffer();
        hql.append(" from WorkTurnsRule wtr where 1=1");
        hqlCount.append(" from WorkTurnsRule wtr where 1=1");
        if (StringUtils.isNotBlank(name)) {
            hql.append(" and wtr.name like '%" + name + "%' ");
            hqlCount.append(" and wtr.name like '%" + name + "%' ");
        }
        
        if (StringUtils.isNotBlank(orgId) && !orgId.equals("0")) {// 单位
            String permissionIds = orgService.getOrgIdsByPermissionScope(orgId, ids);
            // 判断集合是否为空
            if (StringUtils.isNotBlank(permissionIds)) {
                hql.append(" and wtr.org.orgId in (")
                .append(permissionIds)
                .append(")");
            hqlCount.append(" and wtr.org.orgId in (")
                .append(permissionIds)
                .append(")");
            }
        } else {
            hql.append(" and 1=0 ");
            hqlCount.append(" and 1=0 ");
        }
        hql.append("  order by wtr.makeDate desc");
        List<WorkTurnsRule> wtrs =
            (List<WorkTurnsRule>)baseDao.queryEntitysByPage(NumberUtils.toInt(start),
                NumberUtils.toInt(limit),
                hql.toString(),
                new HashMap<String, Object>());
        int count =
            baseDao.queryTotalCount(hqlCount.toString(),
                new HashMap<String, Object>());
        if (wtrs != null && wtrs.size() > 0) {
            wtvos = new ArrayList<WorkTurnsRuleVo>();
            for (WorkTurnsRule r : wtrs) {
                wtvo = new WorkTurnsRuleVo();
                wtvo.setCycleDays(r.getCycleDays());
                wtvo.setId(r.getId());
                wtvo.setMakeDate(DateUtil.dateToString(r.getMakeDate(),
                    "yyyy-MM-dd HH:mm:ss"));
                wtvo.setName(r.getName());
                wtvo.setRemarks(r.getRemarks());
                wtvo.setUser(r.getUser().getRealname());
                wtvo.setOrgName(r.getOrg().getOrgName());
                wtvos.add(wtvo);
            }
        }
        vos.setList(wtvos);
        vos.setTotalSize(count);
        
        return vos;
    }
    
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
    @Override
    public WorkTurnsRuleVo getWorkTurnsRuleVoById(String id)
        throws BusinessException {
        WorkTurnsRuleVo vo = null;
        WorkTurnsRule wtr =
            (WorkTurnsRule)baseDao.queryEntityById(WorkTurnsRule.class, id);
        if (wtr != null) {
            vo = new WorkTurnsRuleVo();
            vo.setCycleDays(wtr.getCycleDays());
            vo.setId(wtr.getId());
            vo.setMakeDate(DateUtil.dateToString(wtr.getMakeDate(),
                "yyyy-MM-dd HH:mm:ss"));
            vo.setName(wtr.getName());
            vo.setRemarks(wtr.getRemarks());
            UserVo user = new UserVo();
            user.setRealname(wtr.getUser().getRealname());
            vo.setUser(wtr.getUser().getUsername());
        }
        return vo;
    }
    
    /**
     * 添加倒班规则
     * 
     * @Title addWorkTurnsRule
     * @author liukang-wb
     * @date 2014年9月11日
     * @param wtr 倒班规则实体
     * @throws BusinessException
     */
    @Override
    public void addWorkTurnsRule(WorkTurnsRule wtr)
        throws BusinessException {
        baseDao.save(wtr);
    }
    
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
    @Override
    public String updateWorkTurnsRule(WorkTurnsRule wtr)
        throws BusinessException {
        baseDao.update(wtr);
        
        return "";
    }
    
    /**
     * 删除倒班规则
     * 
     * @Title deleteWorkTurnsRule
     * @author liukang-wb
     * @date 2014年9月11日
     * @param ids 倒班规则id字符串
     * @throws BusinessException
     */
    @Override
    public void deleteWorkTurnsRule(String ids)
        throws BusinessException {
        String hql =
            " delete from WorkTurnsRule wtr where wtr.id in(" + ids + ") ";
        baseDao.executeHql(hql);
    }
    
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
    @Override
    public WorkTurnsRule getWorkTurnsRuleById(Integer id)
        throws BusinessException {
        return (WorkTurnsRule)baseDao.queryEntityById(WorkTurnsRule.class, id);
    }
    
    /**
     * 根据参数获得所有的倒班规则;
     * 
     * @Title getAllWorkTurnsRuleByParams
     * @author liukang-wb
     * @date 2014年9月11日
     * @param maps 从前台传回的参数
     * @return 倒班规则VO集合
     * @throws BusinessException
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<WorkTurnsRuleVo> getAllWorkTurnsRuleByParams(
        Map<String, Object> maps)
        throws BusinessException {
        List<WorkTurnsRuleVo> vo = null;
        WorkTurnsRuleVo wtvo = null;
        StringBuffer hql = new StringBuffer();
        String orgId = (String)maps.get("orgId");
        String orgIds = (String)maps.get("orgIds");
        
        hql.append(" from WorkTurnsRule wtr where 1=1 ");
        // 获取userid
        if (StringUtils.isNotBlank(orgId) && !"0".equalsIgnoreCase(orgId)) {
            try {
                String userIds = getUserIdsByOrgIds(orgId);
                hql.append(" and wtr.user.userId in (")
                    .append(userIds)
                    .append(")");
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            // hql.append(" and wtr.user.org.orgId="+NumberUtils.toDouble(orgId));
        }
        if (StringUtils.isNotBlank(orgIds)) {
            try {
                String userIds =
                    getUserIdsByOrgIds(orgIds.substring(0, orgIds.length() - 1));
                hql.append(" and wtr.user.userId in (")
                    .append(userIds)
                    .append(")");
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
        List<WorkTurnsRule> rules =
            (List<WorkTurnsRule>)baseDao.queryEntitys(hql.toString());
        if (rules != null && rules.size() > 0) {
            vo = new ArrayList<WorkTurnsRuleVo>();
            for (WorkTurnsRule r : rules) {
                wtvo = new WorkTurnsRuleVo();
                wtvo.setCycleDays(r.getCycleDays());
                wtvo.setId(r.getId());
                wtvo.setMakeDate(DateUtil.dateToString(r.getMakeDate(),
                    "yyyy-MM-dd HH:mm:ss"));
                wtvo.setName(r.getName());
                wtvo.setRemarks(r.getRemarks());
                UserVo user = new UserVo();
                user.setRealname(r.getUser().getRealname());
                wtvo.setUser(r.getUser().getUsername());
                vo.add(wtvo);
            }
        }
        return vo;
    }
    
    /**
     * 判断班组是否存在
     * 
     * @Title workTeamIsExsit
     * @author liukang-wb
     * @date 2014年9月10日
     * @param ruleId 倒班规则ID
     * @return int 存在班组数量
     */
    @Override
    public int workTeamIsExsit(String ruleId)
        throws BusinessException {
        int count = 0;
        if (ruleId != null && !"".equals(ruleId)) {
            String hql =
                "from WorkRulePlan wrp where wrp.workTurnsRule.id = " + ruleId;
            count = baseDao.queryTotalCount(hql, new HashMap<String, Object>());
        }
        return count;
    }
    
    /**
     * 删除倒班规则详细
     * 
     * @Title delAllWorkTurnsDetailByRuleId
     * @author liukang-wb
     * @date 2014年9月10日
     * @param ruleId 倒班规则ID
     * @throws BusinessException
     */
    @Override
    public void delAllWorkTurnsDetailByRuleId(String ruleId)
        throws BusinessException {
        if (ruleId != null && !"".equals(ruleId)) {
            String hql =
                "delete from RuleWorkTeamMap rwtm where rwtm.workTurnsRule.id ="
                    + ruleId;
            baseDao.executeHql(hql);
        }
    }

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
    @Override
    public Map<String, Object> validateRuleName(String val, String deptId)
        throws BusinessException {
        // 新建查询HQL语句
        StringBuffer hql = new StringBuffer(" from WorkTurnsRule wtr");
        hql.append(" where wtr.org.orgId = ");
        hql.append(deptId);
        hql.append(" and wtr.name = '");
        hql.append(val);
        hql.append("'");
        
        // 执行查询
        int count = baseDao.queryTotalCount(hql.toString(), 
            new HashMap<String, Object>());
        
        // 申明返回验证结果的map
        Map<String, Object> vaildator = new HashMap<String, Object>();
        // 班规则名称已经存在时
        if (count > 0) {
            vaildator.put("success", true);
            vaildator.put("valid", false);
            vaildator.put("reason", "该倒班规则名称已经存在！");
        }
        else {
            vaildator.put("success", true);
            vaildator.put("valid", true);
            vaildator.put("reason", "");
        }
        return vaildator;
    }
}