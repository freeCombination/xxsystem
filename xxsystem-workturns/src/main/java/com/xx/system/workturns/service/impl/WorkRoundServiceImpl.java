/**  
 * @文件名: WorkRoundServiceImpl.java
 * @版权:Copyright 2009-2014 版权所有：大庆金桥信息工程有限公司成都分公司
 * @描述: 班次逻辑接口实现类
 * @修改人: lizhengc
 * @修改时间: 2014-8-22 上午10:43:22
 * @修改内容:新增
 */
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

import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.workturns.entity.WorkRound;
import com.dqgb.sshframe.workturns.service.IWorkRoundService;
import com.dqgb.sshframe.workturns.vo.WorkRoundVo;

/**
 * 班次逻辑接口实现类
 * 
 * @Title WorkRoundServiceImpl
 * @author lizhengc
 * @version V1.40,2014-8-22
 * @Date 2014年9月10日
 * @see [相关类/方法]
 * @since V1.40
 */
@Service("workRoundService")
public class WorkRoundServiceImpl implements IWorkRoundService {
    
    /**
     * @Fields baseDao : 数据库操作基础方法
     */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    /**
     * 添加班次
     * 
     * @Title addWorkRound
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param round 班次对象
     * @throws BusinessException
     * @return void
     */
    @Override
    public void addWorkRound(WorkRound round)
        throws BusinessException {
        baseDao.save(round);
    }
    
    /**
     * 更新班次
     * 
     * @Title updateWorkRound
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param round 班次对象
     * @throws BusinessException
     * @return String
     */
    @Override
    public String updateWorkRound(WorkRound round)
        throws BusinessException {
        WorkRound wr =
            (WorkRound)baseDao.queryEntityById(WorkRound.class, round.getId());
        wr.setEndTime(round.getEndTime());
        wr.setRoundName(round.getRoundName());
        wr.setStartTime(round.getStartTime());
        this.baseDao.update(wr);
        
        return "";
    }
    
    /**
     * 删除班次
     * 
     * @Title deleteWorkRound
     * @author dong.he 2014-8-22
     * @Date 2014年9月2日
     * @param ids 班次id
     * @throws BusinessException
     * @return void
     */
    @Override
    public void deleteWorkRound(String ids)
        throws BusinessException {
        String hql = " delete from WorkRound wr where wr.id in (" + ids + ")";
        baseDao.executeHql(hql);
    }
    
    /**
     * 根据班次主键ID查询班次计划是否关联
     * 
     * @Title getroundByPlanRule
     * @author lizhengc
     * @date 2014年9月2日
     * @param id 字典主键
     * @return int 数量
     * @throws BusinessException
     */
    public int getroundByPlanRule(int id)
        throws BusinessException {
        String hql = "from WorkRulePlan wrp where wrp.workRound.id=" + id;
        int count = baseDao.queryTotalCount(hql, new HashMap<String, Object>());
        return count;
    }
    
    /**
     * 根据班次主键ID查询班组规则是否关联
     * 
     * @Title getroundByRuleWork
     * @author lizhengc
     * @date 2014年9月2日
     * @param id 字典主键
     * @return int 数量
     * @throws BusinessException
     */
    public int getroundByRuleWork(int id)
        throws BusinessException {
        String hql = "from RuleWorkTeamMap rwtm where rwtm.workRound.id=" + id;
        int count = baseDao.queryTotalCount(hql, new HashMap<String, Object>());
        return count;
    }
    
    /**
     * 根据条件查询所有的班次
     * 
     * @Title getWorkRoundByParams
     * @author lizhengc
     * @date 2014年9月2日
     * @param Map<String, String> maps 获取参数
     * @return ListVo<WorkRoundVo> 班次vo集合
     * @throws BusinessException
     */
    @Override
    public ListVo<WorkRoundVo> getWorkRoundByParams(Map<String, String> maps)
        throws BusinessException {
        ListVo<WorkRoundVo> vos = new ListVo<WorkRoundVo>();
        List<WorkRoundVo> wr = null;
        StringBuffer hql = new StringBuffer();
        StringBuffer hqlCount = new StringBuffer();
        String start = (String)maps.get("start");
        String limit = (String)maps.get("limit");
        String name = (String)maps.get("name");
        hql.append(" from WorkRound wr where 1=1 ");
        hqlCount.append(" select count(*) from WorkRound wr where 1=1 ");
        if (StringUtils.isNotBlank(name)) {
            hql.append(" and wr.roundName like '%" + name + "%' ");
            hqlCount.append(" and wr.roundName like '%" + name + "%' ");
        }
        hql.append(" order by wr.startTime,wr.id ");
        List<WorkRound> wrs =
            (List<WorkRound>)baseDao.queryEntitysByPage(NumberUtils.toInt(start),
                NumberUtils.toInt(limit),
                hql.toString(),
                new HashMap<String, Object>());
        if (wrs != null && wrs.size() > 0) {
            wr = new ArrayList<WorkRoundVo>();
            WorkRoundVo round = null;
            for (WorkRound r : wrs) {
                round = new WorkRoundVo();
                round.setEndTime(r.getEndTime());
                round.setStartTime(r.getStartTime());
                round.setId(r.getId());
                round.setRoundName(r.getRoundName());
                wr.add(round);
            }
        }
        int count =
            baseDao.getTotalCount(hqlCount.toString(),
                new HashMap<String, Object>());
        vos.setList(wr);
        vos.setTotalSize(count);
        return vos;
    }
    
    /**
     * 根据ID获得班次详细信息
     * 
     * @Title getWorkRoundById
     * @author lizhengc
     * @date 2014年9月2日
     * @param id 班次id
     * @return workRoundVo 班次vo对象
     * @throws BusinessException
     */
    @Override
    public WorkRoundVo getWorkRoundById(String id)
        throws BusinessException {
        WorkRoundVo vo = new WorkRoundVo();
        WorkRound wr = (WorkRound)baseDao.queryEntityById(WorkRound.class, id);
        if (wr != null) {
            vo.setId(wr.getId());
            vo.setEndTime(wr.getEndTime());
            vo.setStartTime(wr.getStartTime());
            vo.setRoundName(wr.getRoundName());
        }
        return vo;
    }
    
    /**
     * 获得所有的班次信息
     * 
     * @Title getAllWorkRound
     * @author lizhengc
     * @date 2014年9月2日
     * @param
     * @return List<WorkRound> 班次集合
     * @throws BusinessException
     */
    @Override
    public List<WorkRound> getAllWorkRound()
        throws BusinessException {
        String hql = " from WorkRound wr  where 1=1 order by wr.startTime";
        return (List<WorkRound>)baseDao.queryEntitys(hql);
    }
    
    /**
     * 判断班次的时间是否交叉
     * 
     * @Title isTimeCross
     * @author lizhengc
     * @date 2014年9月2日
     * @param round 班次对象
     * @return Map<String, String> 提示信息maps
     * @throws BusinessException
     */
    public Map<String, String> isTimeCross(WorkRound round) {
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("success", "true");
        maps.put("info", "");
        try {
            if (round.getStartTime() < 0 && round.getEndTime() > 0) {
                maps.put("success", "false");
                maps.put("info", "班次【" + round.getRoundName() + "】的结束时间必须小于等于0");
            }
            else {
                List<WorkRound> rounds = getAllWorkRound();
                WorkRound wr = null;
                int startTime = round.getStartTime();
                int endTime = round.getEndTime();
                boolean flag = false;
                if (rounds != null && rounds.size() > 0) {
                    for (int i = 0; i < rounds.size(); i++) {
                        wr = rounds.get(i);
                        // 如果是更新的时候，则排除以前的数据;
                        if ((round.getId() != null && round.getId()
                            .equals(wr.getId())) || round.getStartTime() < 0) {
                            continue;
                        }
                        if (startTime == wr.getStartTime()
                            && endTime == wr.getEndTime()) {
                            maps.put("success", "false");
                            maps.put("info", "班次【" + round.getRoundName()
                                + "】和班次【" + wr.getRoundName() + "】的时间完全相同!");
                            break;
                        }
                        if ((startTime > wr.getStartTime() && startTime < wr.getEndTime())
                            || (endTime > wr.getStartTime() && endTime < wr.getEndTime())
                            || (wr.getStartTime() > startTime && wr.getStartTime() < endTime)
                            || (wr.getEndTime() > startTime && wr.getEndTime() < endTime)) {
                            flag = true;
                        }
                        if (flag) {
                            maps.put("success", "false");
                            maps.put("info", "班次【" + round.getRoundName()
                                + "】和班次【" + wr.getRoundName() + "】的时间有交叉!");
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            maps.put("success", "false");
            maps.put("info", "保存失败！");
        }
        return maps;
    }
    
    /**
     * 查看班次是否存在
     * 
     * @Title getWorkRoundById
     * @author lizhengc
     * @date 2014年9月2日
     * @param id 班次id
     * @return workRoundVo 班次vo对象
     * @throws BusinessException
     */
    @Override
    public WorkRound getWorkRoundById1(String id)
        throws BusinessException {
        String hql = "from WorkRound where id =" + id;
        return (WorkRound)baseDao.queryEntitys(hql).get(0);
    }
    
    /**
     * 验证班次名称值的唯一性
     * 
     * @Title validateRoundName
     * @author lizhengc
     * @date 2014年8月29日
     * @param paramsMap 参数map
     * @return Map<String, Object> 返回验证结果，其中包含验证的结果和理由
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> validateRoundName(Map<String, String> paramsMap)
        throws BusinessException {
        String value = paramsMap.get("value");
        
        Map<String, Object> vaildator = new HashMap<String, Object>();
        String hql = "from WorkRound d where d.roundName = '" + value + "'";
        try {
            int totleSize = baseDao.queryTotalCount(hql, new HashMap());
            if (totleSize > 0) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "数据已存在");
            }
            else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            return vaildator;
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        
    }
}