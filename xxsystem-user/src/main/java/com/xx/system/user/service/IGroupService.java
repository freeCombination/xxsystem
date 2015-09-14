package com.xx.system.user.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.user.entity.Group;
import com.xx.system.user.entity.GroupMember;
import com.xx.system.user.vo.GroupVo;

/**
 * 群组管理服务接口
 * 
 * @version V1.40,2014年9月15日 上午8:52:00
 * @see [相关类/方法]
 * @since V1.40
 */
public interface IGroupService {
    
    /**
     * 保存群组
     * 
     * @Title addGroup
     * @author tangh
     * @date 2014年9月15日
     * @param groupVo 群组VO对象
     * @return 字符串
     * @throws BusinessException
     */
    public String addGroup(GroupVo groupVo)
        throws BusinessException;
    
    /**
     * 删除群组
     * 
     * @Title delGroup
     * @author tangh
     * @date 2014年9月15日
     * @param id 群组ID
     * @return 字符串
     * @throws BusinessException
     */
    public String delGroup(String ids)
        throws BusinessException;
    
    /**
     * 根据群组主键ID查询是否有子记录
     * 
     * @Title getGMByGroId
     * @author lizhengc
     * @date 2014年9月2日
     * @param id 群组id
     * @return GroupMember 群组成员实体
     * @throws BusinessException
     */
    public GroupMember getGMByGroId(String id)
        throws BusinessException;
    
    /**
     * 修改群组
     * 
     * @Title updateGroup
     * @author tangh
     * @date 2014年9月15日
     * @param groupVo 群组VO对象
     * @return 字符串
     * @throws BusinessException
     */
    public String updateGroup(GroupVo groupVo)
        throws BusinessException;
    
    /**
     * 根据群组Id查询群组
     * 
     * @Title getGroupById
     * @author tangh
     * @date 2014年9月15日
     * @param id 群组ID
     * @return 群组对象
     * @throws BusinessException
     */
    public Group getGroupById(Integer id)
        throws BusinessException;
    
    /**
     * 查询群组
     * 
     * @Title getGroup
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap
     * @return ListVo<GroupVo> 群组集合
     * @throws BusinessException
     */
    public ListVo<GroupVo> getGroup(Map<String, String> paramMap)
        throws BusinessException;
    
    /**
     * 根据群组成员ID获取对应的群组
     * 
     * @Title getGroupByMemberId
     * @author liukang-wb
     * @Description:
     * @date 2014年9月15日
     * @param memberId
     * @return
     */
    public List<GroupMember> getGroupByMemberId(String memberIds, String type)
        throws BusinessException;
    
    /**
     * 验证群组名字
     * 
     * @Title validateGroupName
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap 参数
     * @return Integer 整形
     * @throws BusinessException
     */
    public Map<String, Object> validateGroupName(Map<String, String> paramMap)
        throws BusinessException;
}
