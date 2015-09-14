/**  
 * @文件名: IWorkRoundService.java
 * @版权:Copyright 2009-2014 版权所有：大庆金桥信息工程有限公司成都分公司
 * @描述: 班次逻辑接口
 * @修改人: lizhengc
 * @修改时间: 2014-8-22 上午10:43:22
 * @修改内容:新增
 */
package com.dqgb.sshframe.workturns.service;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.workturns.entity.WorkRound;
import com.dqgb.sshframe.workturns.vo.WorkRoundVo;

/**
 * 定义班次管理逻辑接口
 * 
 * @Title IWorkRoundService
 * @author lizhengc
 * @version V1.40,2014-8-22
 * @Date 2014年9月10日
 * @see [相关类/方法]
 * @since V1.40
 */
public interface IWorkRoundService {
    
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
    public void addWorkRound(WorkRound round)
        throws BusinessException;
    
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
    public String updateWorkRound(WorkRound round)
        throws BusinessException;
    
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
    void deleteWorkRound(String ids)
        throws BusinessException;
    
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
        throws BusinessException;
    
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
        throws BusinessException;
    
    /**
     * 根据条件查询所有的班次
     * 
     * @Title getWorkRoundByParams
     * @author lizhengc
     * @date 2014年9月2日
     * @param Map<String, String> paramMap 获取参数
     * @return ListVo<WorkRoundVo> 班次vo集合
     * @throws BusinessException
     */
    ListVo<WorkRoundVo> getWorkRoundByParams(Map<String, String> paramMap)
        throws BusinessException;
    
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
    WorkRoundVo getWorkRoundById(String id)
        throws BusinessException;
    
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
    WorkRound getWorkRoundById1(String id)
        throws BusinessException;
    
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
    public Map<String, Object> validateRoundName(Map<String, String> paramsMap)
        throws BusinessException;
    
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
    List<WorkRound> getAllWorkRound()
        throws BusinessException;
    
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
    Map<String, String> isTimeCross(WorkRound round)
        throws BusinessException;
}
