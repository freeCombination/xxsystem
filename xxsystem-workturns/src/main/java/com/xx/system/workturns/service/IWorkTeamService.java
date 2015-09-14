/**
 * 文件名： IWorkTeamService.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：班组管理逻辑接口
 * 修改人： tangh
 * 修改时间： 2014年8月22日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.service;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.user.vo.UserVo;
import com.dqgb.sshframe.workturns.entity.WorkTeam;
import com.dqgb.sshframe.workturns.vo.WorkTeamVo;

/**
 * 定义班组管理逻辑接口
 * 
 * @author tangh
 * @version V1.40,2014年9月5日 下午4:16:50
 * @see [相关类/方法]
 * @since V1.40
 */
public interface IWorkTeamService {
    
    /**
     * 添加工作组
     * 
     * @Title addToWorkTeam
     * @author tangh
     * @date 2014年9月3日
     * @param workTeamVo 班组VO对象
     * @return 整形
     * @throws BusinessException
     */
    public int addToWorkTeam(WorkTeamVo workTeamVo)
        throws BusinessException;
    
    /**
     * 获取班组成员
     * 
     * @Title getWorkTeamMember
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 查询参数
     * @return ListVo<UserVo> 用户集合
     * @throws BusinessException
     */
    public ListVo<UserVo> getWorkTeamMember(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 获取组织成员
     * 
     * @Title getDeptMember
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 查询参数
     * @return ListVo<UserVo> 用户集合
     * @throws BusinessException
     */
    public ListVo<UserVo> getDeptMember(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 添加班组成员
     * 
     * @Title addWorkTeamMember
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 参数
     * @return String 字符串
     * @throws BusinessException
     */
    public String addWorkTeamMember(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 删除班组
     * 
     * @Title delWorkTeam
     * @author tangh
     * @date 2014年9月3日
     * @param teamId 班组id
     * @return String 字符串
     * @throws BusinessException
     */
    public String delWorkTeam(String teamId)
        throws BusinessException;
    
    /**
     * 删除班组成员
     * 
     * @Title delWorkTeamMember
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 参数
     * @return String 字符串
     * @throws BusinessException
     */
    public String delWorkTeamMember(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 修改班组信息
     * 
     * @Title updateWorkTeam
     * @author tangh
     * @date 2014年9月3日
     * @param workTeamVo 班组VO对象
     * @param flag 标识符
     * @return String 字符串
     * @throws BusinessException
     */
    public String updateWorkTeam(WorkTeamVo workTeamVo, String flag)
        throws BusinessException;
    
    /**
     * 获得所有的工作组
     * 
     * @Title getAllWorkTeam
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 查询参宿
     * @return ListVo<WorkTeamVo> 班组集合
     * @throws BusinessException
     */
    public ListVo<WorkTeamVo> getAllWorkTeam(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 验证工作组名字
     * 
     * @Title validateWorkTeamName
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 参数
     * @return Integer 整形
     * @throws BusinessException
     */
    public int validateWorkTeamName(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 根据班组ID获取班组信息
     * 
     * @Title getWorkTeamById
     * @author tangh
     * @date 2014年9月3日
     * @param id 班组id
     * @return WorkTeam 班组对象
     * @throws BusinessException
     */
    public WorkTeam getWorkTeamById(int id)
        throws BusinessException;
    
    /**
     * 删除班组下所有的成员
     * 
     * @Title delWorkTeamMemberAll
     * @author tangh
     * @date 2014年9月4日
     * @param teamId 班组id
     * @return 无
     * @throws BusinessException
     */
    public void delWorkTeamMemberAll(String teamId)
        throws BusinessException;
    
    /**
     * 通过组织ID获取班组
     * 
     * @Title getAllWorkTeamByOrgId
     * @author dong.he
     * @date 2014年9月5日
     * @param orgId 组织id
     * @return 班组集合
     * @throws BusinessException
     */
    public List<WorkTeam> getAllWorkTeamByOrgId(String orgId)
        throws BusinessException;
}
