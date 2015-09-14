/**
 * @文件名 IActivitiTaskService.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述  流程任务的逻辑层接口
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service;

import java.util.Map;

import org.activiti.engine.task.Task;

import com.dqgb.sshframe.bpm.vo.TaskVo;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.user.entity.User;

/**
 * 
 * 流程任务的逻辑层接口
 * 
 * @author zhxh
 * @version V1.20,2013-12-25 下午5:36:23
 * @since V1.20
 * 
 */
public interface IActivitiTaskService {
    
    /**
     * 
     * @Title getDoneTaskListByPage
     * @author zhxh
     * @Description: 分页查询已处理任务
     * @date 2013-12-30
     * @param paramMap
     * @return ListVo<TaskVo>
     * @throws ServiceException
     */
    ListVo<TaskVo> getDoneTaskListByPage(Map paramMap)
        throws ServiceException;
    
    /**
     * 
     * @Title getToDoTaskListByPage
     * @author zhxh
     * @Description:分页查询待办理任务
     * @date 2013-12-30
     * @param paramMap
     * @return ListVo<TaskVo>
     * @throws ServiceException
     */
    ListVo<TaskVo> getToDoTaskListByPage(Map paramMap)
        throws ServiceException;
    
    /**
     * 
     * @Title updateTask
     * @author zhxh
     * @Description:
     * @date 2013-12-30
     * @param vo
     * @return Task
     */
    Task updateTask(TaskVo vo);
    
    /**
     * 
     * @Title moveTask
     * @author zhxh
     * @Description:移交任务
     * @date 2013-12-30
     * @param taskId
     * @param userId
     * @param currentUser
     */
    void moveTask(String taskId, String userId, User currentUser);
    
    /**
     * 
     * @Title rejectTaskForApply
     * @author zhxh
     * @Description:驳回任务到申请节点
     * @date 2013-12-30
     * @param paramMap
     * @param commitMap
     */
    
    void rejectTaskForApply(Map paramMap, Map commitMap);
    
    /**
     * 
     * @Title setTaskDelegate
     * @author zhxh
     * @Description: 设置委托
     * @date 2013-12-30
     * @param delegateUserId
     * @param taskDefineKey
     * @param processInstanceId
     * @param currentUserId
     */
    
    void setTaskDelegate(String taskDefineKey, String processInstanceId,
        String currentOwnerUserId, String taskUserId);
    
    /**
     * 
     * @Title deleteActivitiProcessApproval
     * @author zhxh
     * @Description:
     * @date 2013-12-30
     * @param ids
     * @throws ServiceException
     */
    void deleteActivitiProcessApproval(String ids)
        throws ServiceException;
    
    /**
     * 
     * @Title getHistoryTask
     * @author zhxh
     * @Description:获取历史任务
     * @date 2013-12-30
     * @param taskId
     * @return TaskVo
     */
    TaskVo getHistoryTask(String taskId);
    
    /**
     * 
     * @Title completeTask
     * @author zhxh
     * @Description:完成任务 true表示为审批节点
     * @date 2013-12-30
     * @param paramMap
     * @param commitMap
     * @return boolean
     * @throws ServiceException
     */
    public Map<String,String> completeTask(Map paramMap, Map commitMap)
        throws ServiceException;
    
    /**
     * 
     * @Title getProcessParam
     * @author zhxh
     * @Description:获取流程节点自定义参数
     * @date 2013-12-30
     * @param paramMap
     * @param commitMap
     * @return boolean
     * @throws ServiceException
     */
    public String getProcessParam(String taskId)  throws ServiceException;
    /**
     * 
     * @Title rejectTask
     * @author zhxh
     * @Description:驳回任务
     * @date 2013-12-30
     * @param paramMap
     * @param commitMap
     * @param rejeckToTaskDefineKey
     * @throws ServiceException
     */
    public void rejectTask(Map paramMap, Map commitMap,
        String rejeckToTaskDefineKey)
        throws ServiceException;
    
    
    /**
     * 
     * @Title addActivitiTaskRole
     * @author zhxh
     * @Description:保存任务和角色关系
     * @date 2013-12-30
     * @param taskId
     * @param roleIds
     */
    void addActivitiTaskRole(String taskId, String roleIds);
    
    /**
     * @Description: 发送邮件给当前审核人
     * @param @param processInstanceId
     * @param @return
     * @return 返回类型
     * @author yzh
     * @date 2014-8-21 上午09:48:38
     */
    String sendEmailToCurrentCheckUser(String processInstanceId, String password);
    
    /**
     * 发送短信给当前审核人
     * 
     * @Title sendSmsToCurrentCheckUser
     * @author 游正刚
     * @Description:发送短信给当前审核人
     * @date 2014年12月9日
     * @param processInstanceId
     * @return
     */
    Map<String, Object> sendSmsToCurrentCheckUser(String processInstanceId);
}
