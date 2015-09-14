/**
 * @文件名 IActivitiAndBusinessDao.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程操作,如删除流程,保存意见,获取待办已办
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.bpm.entity.ActivitiProcessApproval;
import com.dqgb.sshframe.bpm.entity.ActivitiProcessInstance;
import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.role.entity.Role;
import com.dqgb.sshframe.user.entity.User;

/**
 * 
 * 流程操作,如删除流程,保存意见,获取待办已办
 * 
 * @author zhxh
 * @version V1.20,2013-12-27 下午12:15:23
 * @since V1.20
 * @depricated
 */
public interface IActivitiAndBusinessDao extends IBaseDao
{
    /**
     * 
     * @Title getBusinessProcessAndBusinessData
     * @author zhxh
     * @Description:获取业务数据和流程实例关系
     * @date 2013-12-27
     * @param processInstanceIds 流程实例id，用逗号分开
     * @return List<ActivitiProcessInstance>
     */
    List<ActivitiProcessInstance> getActivitiProcessInstance(
        String processInstanceIds);
    
    /**
     * 
     * @Title addActivitiProcessInstances
     * @author zhxh
     * @Description:添加事项预流程实例对应
     * @date 2013-12-27
     * @param processInstances
     */
    void addActivitiProcessInstances(
        List<ActivitiProcessInstance> processInstances);
    
    /**
     * 
     * @Title getProcessApprovalList
     * @author zhxh
     * @Description:获取流程审批签名信息
     * @date 2013-12-27
     * @param processInstanceId
     * @return ProcessApproval
     */
    public List<ActivitiProcessApproval> getProcessApprovalList(
        String processInstanceId);
    
    /**
     * 
     * @Title getProcessInstanceByPage
     * @author zhxh
     * @Description:分页查询流程实例
     * @date 2013-12-27
     * @param paramMap
     * @return ListVo<Object[]>
     */
    ListVo<Object[]> getProcessInstanceByPage(Map paramMap);
    
    /**
     * 
     * @Title updateProcessInstance
     * @author zhxh
     * @Description: 修改流程实例
     * @date 2013-12-27
     * @param xmlContent
     * @param deploymentId
     */
    void updateProcessInstance(String xmlContent, String deploymentId);
    
    /**
     * 
     * @Title getUserListForFlow
     * @author zhxh
     * @Description: 获取用户列表，为流程在线设计器
     * @date 2013-12-27
     * @param paramMap
     * @return ListVo<User>
     */
    ListVo<User> getUserListForFlow(Map<String, Object> paramMap);
    
    /**
     * 
     * @Title getUserListByLoginNames
     * @author zhxh
     * @Description:获取用户
     * @date 2013-12-27
     * @param loginNames
     * @return List<User>
     */
    List<User> getUserListByLoginNames(String loginNames);
    
    /**
     * 
     * @Title getRoleForBpmByPage
     * @author zhxh
     * @Description:
     * @date 2013-12-27
     * @param paramMap
     * @return 获取角色
     */
    ListVo<Role> getRoleForBpmByPage(Map paramMap);
    
    /**
     * 通过角色Id以及bpmType获取角色单位下的人员
     * 
     * @param roleIds
     * @param bpmType
     * @return loginNames
     */
    List<User> getUserByRoleIdsAndCategoryId(String roleIds, int categoryId);
    
    /**
     * 
     * @Title addActivitiProcessApproval
     * @author zhxh
     * @Description:保存意见
     * @date 2013-12-30
     * @param processApproval
     */
    void addActivitiProcessApproval(ActivitiProcessApproval processApproval);
    
    /**
     * 
     * @Title deleteActivitiProcessApproval
     * @author zhxh
     * @Description:删除意见
     * @date 2013-12-30
     * @param processInstanceIds
     */
    void deleteActivitiProcessApproval(String processInstanceIds);
    

    
}
