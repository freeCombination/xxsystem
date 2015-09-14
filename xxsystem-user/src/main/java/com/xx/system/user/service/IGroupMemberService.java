package com.xx.system.user.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.org.entity.Organization;
import com.xx.system.user.entity.GroupMember;
import com.xx.system.user.vo.GroupMemberVo;
import com.xx.system.user.vo.UserVo;

/**
 * 群组成员接口
 * 
 * @version V1.40,2014年9月15日 上午8:52:00
 * @see [相关类/方法]
 * @since V1.40
 */
public interface IGroupMemberService {
    
    /**
     * 添加群组成员
     * 
     * @Title addGroupMember
     * @author tangh
     * @date 2014年9月15日
     * @param param 参数
     * @return 字符串
     * @throws BusinessException
     */
    public String addGroupMember(Map<String, String> param)
        throws BusinessException;
    
    /**
     * 添加群组成员时查询该用户是否已经存在
     * 
     * @Title isExitUserId
     * @author tangh
     * @date 2014年9月15日
     * @param userId 用户id
     * @param groupId 群组id
     * @return boolean
     * @throws BusinessException
     */
    public boolean isExitUserId(String userId,String groupId)
        throws BusinessException;
    
    /**
     * 删除群组成员
     * 
     * @Title delGroup
     * @author tangh
     * @date 2014年9月15日
     * @param paramsMap 参数
     * @return 字符串
     * @throws BusinessException
     */
    public String delGroupMember(Map<String, String> paramsMap)
        throws BusinessException;
    
    /**
     * 查询群组成员
     * 
     * @Title getGroupMember
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap
     * @return ListVo<GroupMemberVo> 群组成员集合
     * @throws BusinessException
     */
    public ListVo<GroupMemberVo> getGroupMember(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 查询属于该群组的部门
     * 
     * @Title getDept
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap
     * @return ListVo<GroupMemberVo> 群组成员集合
     * @throws BusinessException
     */
    public ListVo<GroupMemberVo> getDept(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 为该群组添加部门
     * 
     * @Title addDept
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap 参数
     * @return String 字符串
     * @throws BusinessException
     */
    public String addDept(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 删除该群组中的部门
     * 
     * @Title delDept
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap 参数
     * @return String 字符串
     * @throws BusinessException
     */
    public String delDept(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 根据群组ID查询该群组下的所有组织
     * 
     * @Title getDeptByGroupId
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap
     * @return List<GroupMember> 群组部门集合
     * @throws BusinessException
     */
    public List<GroupMember> getDeptByGroupId(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 获取用户列表
     * 
     * @Title getUserList
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap
     * @return ListVo<UserVo> 用户集合
     * @throws BusinessException
     */
    public ListVo<UserVo> getUserList(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 查询当前组织的一级菜单
     * 
     * @Title getFirstLevelOrg
     * @author tangh
     * @date 2014年9月15日
     * @return List<Organization> 获取组织
     * @throws BusinessException
     */
    public List<Organization> getFirstLevelOrg()
        throws BusinessException;
    
    /**
     * 根据父节点取所有子节点
     * 
     * @Title getAllSonOrgByParentId
     * @author tangh
     * @date 2014年9月15日
     * @param parentId 父节点ID
     * @return List<Organization> 获取组织
     * @throws BusinessException
     */
    public List<Organization> getAllSonOrgByParentId(String parentId)
        throws BusinessException;
}
