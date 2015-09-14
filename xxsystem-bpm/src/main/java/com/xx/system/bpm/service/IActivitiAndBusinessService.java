package com.dqgb.sshframe.bpm.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.bpm.entity.ActivitiProcessApproval;
import com.dqgb.sshframe.bpm.entity.ActivitiProcessInstance;
import com.dqgb.sshframe.bpm.vo.ActivitiProcessInstanceVo;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.org.entity.Organization;
import com.dqgb.sshframe.role.entity.Role;
import com.dqgb.sshframe.user.entity.User;

public interface IActivitiAndBusinessService extends IActivitiBaseService
{
    /**
     * 
     * @Title getActivitiProcessInstance
     * @author zhxh
     * @Description:获取业务和流程信息
     * @date 2013-12-30
     * @param prcessInstanceIds
     * @return List<ActivitiProcessInstanceVo>
     * @throws ServiceException
     */
    List<ActivitiProcessInstanceVo> getActivitiProcessInstance(
        String processInstanceIds)
        throws ServiceException;
    
    /**
     * 
     * @Title startProcess
     * @author zhxh
     * @Description:
     * @date 2013-12-30
     * @param paramMap 启动流程实例所需要的参数,以及需要放到申请任务节点url上的数据
     * @param commitMap 提交给流程引擎保存的数据
     * @param rootPath 系统根路径
     * @param processDefineKey 流程模板定义key
     * @return String processInstance 流程实例ID
     * @throws ServiceException
     */
    String startProcess(Map<String, Object> paramMap, Map commitMap,
        String rootPath, String processDefineKey)
        throws ServiceException;
    
    /**
     * 添加事项预流程实例对应
     * 
     * @param List <ProcessInstances>
     * @throws ServiceException
     * @return void
     */
    void addActivitiProcessInstances(
        List<ActivitiProcessInstance> processInstances)
        throws ServiceException;
    
    /**
     * 
     * @Title deleteProcessInstance
     * @author zhxh
     * @Description:删除流程实例
     * @date 2013-12-30
     * @param processInstanceIds
     * @throws ServiceException
     */
    void deleteProcessInstance(String processInstanceIds)
        throws ServiceException;
    
    /**
     * 获取流程审批签名信息
     * 
     * @author 张小虎
     * @date 2011-5-24
     * @param processInstanceId
     * @throws ServiceException
     * @return ProcessApproval
     */
    public List<ActivitiProcessApproval> getProcessApprovalList(
        String processInstanceId)
        throws ServiceException;
    
    /**
     * 
     * @Title endProcessInstanceByMap
     * @author zhxh
     * @Description:终止流程
     * @date 2013-12-30
     * @param paramMap 需要提交给任务url上的数据
     * @param commitMap
     * @throws ServiceException
     */
    void endProcessInstanceByMap(Map paramMap)
        throws ServiceException;
    
    /**
     * 获取流程展示
     * 
     * @author 张小虎
     * @param processInstanceId
     * @return Map
     */
    Map getProcessListenerShowData(String processInstanceId)
        throws ServiceException;
    
    /**
     * 获取流程节点数据
     * 
     * @author 张小虎
     * @param processInstanceId
     * @return Map
     */
    Map getProcessUserData(final String processInstanceId, String taskDefineKey)
        throws ServiceException;
    
    /**
     * 分页查询流程实例
     * 
     * @param paramMap
     * @return ListVo
     */
    ListVo<ActivitiProcessInstanceVo> getProcessInstanceByPage(Map paramMap)
        throws ServiceException;
    
    /**
     * 修改流程实例
     * 
     * @param xmlContent
     * @param processInstanceId
     * @return void
     */
    void updateProcessInstance(String xmlContent, String processInstanceId)
        throws ServiceException;
    
    /**
     * 为流程获取用户数据
     * 
     * @param paramMap
     * @return
     */
    ListVo<User> getUserListForFlow(Map<String, Object> paramMap);
    
    /**
     * 为流程图获取用户展示
     * 
     * @param substring
     * @return
     */
    List<User> getUserListByLoginNames(String loginNames);
    
    /**
     * 获取组织树
     * 
     * @param nodeId
     */
    List<Organization> getOrgTreeForBpm(String nodeId);
    
    /**
     * 获取角色
     * 
     * @param paramMap
     * @return
     */
    ListVo<Role> getRoleForBpmByPage(Map paramMap);
    
    /**
     * 通过角色ID，用逗号分割查询
     * 
     * @param roleIds
     * @return
     */
    List<Role> getRoleForBpmById(String roleIds);
    
    /**
     * 通过角色Id以及bpmType获取角色单位下的人员
     * 
     * @param roleIds
     * @param bpmType
     * @return loginNames
     */
    List<String> getUserByRoleIdsAndCategoryId(String roleIds, int categoryId);
    
    /**
     * 
     * @Title getActivitiXmlByTaskDefineId
     * @author zhxh
     * @Description: 通过流程定义Id获取xml
     * @date 2013-12-24
     * @param processDefinitionId
     * @return InputStream
     */
    public InputStream getActivitiXmlByProcessDefineId(
        final String processDefinitionId);
    
    /**
     * 
     * @Title commitApplyTask
     * @author zhxh
     * @Description:提交申请任务
     * @date 2013-12-30
     * @param map
     * @param processInstanceId
     * @throws ServiceException
     */
    void commitApplyTask(Map<String, Object> map, String processInstanceId,
        User user)
        throws ServiceException;
    
    /**
     * 
     * @Title generateActivitiProcessApproval
     * @author zhxh
     * @Description:通过任务提交的参数产生意见
     * @date 2013-12-30
     * @param paramMap
     * @param task
     * @param approval
     * @return ActivitiProcessApproval
     */
    ActivitiProcessApproval generateActivitiProcessApproval(Map paramMap,
        Task task, int approval);
    
    /**
     * 
     * @Title addActivitiProcessApproval
     * @author zhxh
     * @Description:保存意见
     * @date 2013-12-27
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

    
    /**
     * 
     * @Title convertTimeToChinese
     * @author zhxh
     * @Description:转化时间
     * @date 2013-12-30
     * @return String
     */
    public String convertTimeToChinese(long longTime);
    

    
}
