package com.dqgb.sshframe.bpm.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.dom4j.DocumentException;

public interface IActivitiBaseService
{
    
    /**
     * 
     * @Title startProcessInstanceByKey
     * @Description:启动流程实例
     * @date 2014-1-3
     * @author zhxh
     * @author 张小虎
     * @param key流程定义的key值 ， map启动实例是否具有的参数值
     * @param startUserId 启动人员id
     * @throw ActivitiException
     * @return ProcessInstance
     * */
    
    public ProcessInstance startProcessInstanceByKey(String key, Map map,
        String startUserId)
        throws ActivitiException;
    
    /**
     * 
     * @Title deployProcessDefinitionByXml
     * @Description:发布流程定义xml文件
     * @date 2014-1-3
     * @author 张小虎
     * @param absolutePath文件绝对路径
     * @throw ActivitiException
     * @return String
     */
    
    public String deployProcessDefinitionByXml(String absolutePath)
        throws ActivitiException;
    
    /**
     * 
     * @Title deployProcessDefinitionByZip
     * @Description:发布流程定义zip文件
     * @date 2014-1-3
     * @author 张小虎
     * @param absolutePath文件绝对路径
     * @throw ActivitiException
     * @return void
     */
    public void deployProcessDefinitionByZip(String absolutePath)
        throws ActivitiException;
    
    /**
     * 
     * @Title deployProcessDefinitionByZip
     * @Description:完成任务
     * @date 2014-1-3
     * @author 张小虎
     * @param taskId 任务id
     * @param map 完成的相关数据，没有设置为null或者空
     * @throw ActivitiException
     * @return void
     */
    public void completeTask(String taskId, Map map)
        throws ActivitiException;
    
    /**
     * 
     * @Title moveTask
     * @Description:移交任务
     * @date 2014-1-3
     * @author 张小虎
     * @param taskId 任务id
     * @param userId 被移交的人员id
     * @throw ActivitiException
     * @return void
     */
    public void moveTask(String taskId, String userId)
        throws ActivitiException;
    
    /**
     * 
     * @Title delegateTask
     * @Description:托管任务
     * @date 2014-1-3
     * @author 张小虎
     * @param taskId 任务id
     * @param userId 被托管的人员id;
     * @param delegateStatus 为false代表任务完成之后不能够再返回给托管人处理 为true代表任务完成之后仍然需要托管人处理
     * @throw ActivitiException
     * @return void
     */
    public void delegateTask(String taskId, String userId,
        boolean delegateStatus)
        throws ActivitiException;
    
    /**
     * 
     * @Title getToDoTaskList
     * @Description:查询所有未处理任务
     * @date 2014-1-3
     * @author 张小虎
     * @param userId 查询的人员id
     * @throw ActivitiException
     * @return List<Task>
     */
    public List<Task> getToDoTaskList(String userId, String processInstanceId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getDoneTaskList
     * @Description:查询所有已处理任务
     * @date 2014-1-3
     * @author 张小虎
     * @param userId 查询的人员id
     * @throw ActivitiException
     * @return List<Task>
     */
    public List<HistoricTaskInstance> getDoneTaskList(String userId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getHistoricTask
     * @Description:查询历史任务
     * @date 2014-1-3
     * @author 张小虎
     * @param historicTaskId 历史任务id
     * @throw ActivitiException
     * @return HistoricTaskInstance
     */
    public HistoricTaskInstance getHistoricTask(String historicTaskId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getTask
     * @Description:查询未处理任务
     * @date 2014-1-3
     * @author 张小虎
     * @param taskId 任务id
     * @throw ActivitiException
     * @return HistoricTaskInstance
     */
    
    public Task getTask(String taskId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getVariables
     * @Description:得到活动节点上设置的值包含父节点上的范围
     * @date 2014-1-3
     * @author 张小虎
     * @param taskId 任务id
     * @throw ActivitiException
     * @return Task
     */
    public Map getVariables(String executionId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getVariablesLocal
     * @Description:得到活动节点上设置的值，只在当前节点上
     * @date 2014-1-3
     * @author 张小虎
     * @param taskId 任务id
     * @throw ActivitiException
     * @return Task
     */
    public Map getVariablesLocal(String executionId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getVariablesLocal
     * @Description:回退任务
     * @date 2014-1-3
     * @author 张小虎
     * @param taskId 当前的 任务id
     * @param activityId 回退到活动节点的id
     * @param commitMap 提交任务需要的map
     * @throw ActivitiException
     * @return void
     */
    public void rollbackTask(String taskId, String activityId, Map commitMap)
        throws ActivitiException;
    
    /**
     * 
     * @Title deleteDeployMent
     * @Description: 删除发布
     * @date 2014-1-3
     * @author 张小虎
     * @param deploymentId 发布的id号
     * @param cascade true表示删除此流程定义的所有流程实例，false表示只删除发布的数据
     * @throw ActivitiException
     * @return void
     */
    public void deleteDeployMent(String deploymentId, boolean cascade)
        throws ActivitiException;
    
    /**
     * 
     * @Title getProcessImage
     * @Description: 得到流程图
     * @date 2014-1-3
     * @author 张小虎
     * @param processDefineId 流程定义id
     * @throw ActivitiException
     * @return InputStream
     */
    public InputStream getProcessImage(String processDefineId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getActivityImpl
     * @Description: 返回当前正在处理的活动节点
     * @date 2014-1-3
     * @author 张小虎
     * @param taskId 任务Id
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    public List<ActivityImpl> getActivityImpl(String taskId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getRollBackActivity
     * @author zhxh
     * @date 2014-1-3
     * @Description: 获取能够回退活动节点
     * 
     * @param taskId 当前的 任务id
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    public List<ActivityImpl> getRollBackActivity(String taskId)
        throws ActivitiException;
    
    
    public List<ActivityImpl> canback(String taskId)
    		throws ActivitiException;
    
    /**
     * 根据已办任务获取当前正在执行的任务
     * @param htaskId
     * @return
     * @throws ActivitiException
     */
    public Task getActiveTaskByDoneTaskId(String htaskId)
    		throws ActivitiException;
    
    /**
     * 根据流程实例ID取当前正在执行的任务
     * @param htaskId
     * @return
     * @throws ActivitiException
     */
    public Task getActiveTaskByProcessInsId(String processInsId)
    		throws ActivitiException;
    /**
     * 
     * @Title endProcessInstance
     * @date 2014-1-3
     * @Description: 结束整个流程
     * @author 张小虎
     * @param processInstanceId 当前的流程实例Id,reason 原因
     * @throw ActivitiException
     * @return void
     */
    public void endProcessInstance(String processInstanceId, String reason)
        throws ActivitiException;
    
    /**
     * 
     * @Title getProcessDefinition
     * @date 2014-1-3
     * @Description: 获取流程定义
     * @author 张小虎
     * @param taskId 任务Id
     * @throw ActivitiException
     * @return ProcessDefinition
     */
    public ProcessDefinition getProcessDefinition(String taskId)
        throws ActivitiException;;
    
    /**
     * 
     * @Title getProcessInstance
     * @date 2014-1-3
     * @Description: 获取流程实例
     * @author 张小虎
     * @param taskId 任务Id
     * @throw ActivitiException
     * @return ProcessInstance
     */
    public ProcessInstance getProcessInstance(String taskId)
        throws ActivitiException;
    
    /**
     * 
     * @Title executeActiviti
     * @date 2014-1-3
     * @Description: 暴露基本的回调方法，个性化实现无法满足的数据
     * @author 张小虎
     * @param IActivitiCallBack 回调接口
     * @throw ActivitiException
     * @return Object
     */
    public Object executeActiviti(IActivitiCallBack activitiCallBack)
        throws ActivitiException;
    
    /**
     * 
     * @Title executeActiviti
     * @date 2014-1-3
     * @Description: 返回当前task对应的活动节点
     * @author 张小虎
     * @param taskId 任务id
     * @throw ActivitiException
     * @return ActivityImpl
     */
    public ActivityImpl findActivity(String taskId)
        throws ActivitiException;
    
    /**
     * 
     * @Title executeActiviti
     * @date 2014-1-3
     * @Description: 保存任务
     * @author 张小虎
     * @throws ActivitiException
     * @throws IOException
     * @param task 任务实体
     * @date 2012-5-29
     */
    public void saveTask(Task task)
        throws ActivitiException;
    
    /**
     * 
     * @Title findXmlParam
     * @date 2014-1-3
     * @Description: 查找activiti5中流程文件设置的参数
     * @author 张小虎
     * @throws DocumentException
     * @throws IOException
     * @date 2012-5-29
     * @return Map<String,Object>
     */
    public Map<String, Object> findXmlParam(InputStream in, String taskDefineKey)
        throws ActivitiException;
    
    /**
     * 
     * @Title getNextActivitiImpl
     * @date 2014-1-3
     * @Description: 返回当前任务的下一节点所有类型节点
     * @author 张小虎
     * @param taskId 任务Id
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    public List<ActivityImpl> getNextActivitiImpl(String taskId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getToDoTaskListByTaskDefineKey
     * @date 2014-1-3
     * @Description: 查询未处理任务
     * @author 张小虎
     * @param userId 查询的人员id
     * @param taskDefineKey
     * @throw ActivitiException
     * @return List<Task>
     */
    public List<Task> getToDoTaskListByTaskDefineKey(String userId,
        String taskDefineKey, String processInstanceId)
        throws ActivitiException;;
    
    /**
     * 
     * @Title getProcessXmlResource
     * @date 2014-1-3
     * @Description: 获取已经执行的xml报文
     * @author 张小虎
     * @param processInstanceId
     * @throw ActivitiException
     * @return InputStream
     */
    public InputStream getProcessXmlResource(String processInstanceId)
        throws ActivitiException;;
    
    /**
     * 
     * @Title getActivitiImplByProcessInstanceId
     * @date 2014-1-3
     * @Description: 返回当前正在处理的活动节点
     * @author 张小虎
     * @param processInstanceId
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    List<ActivityImpl> getActivitiImplByProcessInstanceId(
        String processInstanceId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getPreAllTaskAcitiviImpl
     * @date 2014-1-3
     * @Description: 返回当前活动节点的前面所有任务节点
     * @author 张小虎
     * @param activityId
     * @param processInstance
     * @param List <ActivityImpl> result
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    public List<ActivityImpl> getPreAllTaskAcitiviImpl(
        List<ActivityImpl> result, String activityId, String processInstance);
    
    /**
     * 
     * @Title getHistoricProcessInstance
     * @date 2014-1-3
     * @Description: 查询历史流程实例
     * @author 张小虎
     * @param processInstanceId
     * @throw ActivitiException
     * @return HistoricProcessInstance
     */
    public HistoricProcessInstance getHistoricProcessInstance(
        String processInstanceId)
        throws ActivitiException;
    
    /**
     * 
     * @Title getProcessDefinitionById
     * @date 2014-1-3
     * @Description: 获取流程定义
     * @author 张小虎
     * @param id
     * @throw ActivitiException
     * @return ProcessDefinition
     */
    public ProcessDefinition getProcessDefinitionById(String id)
        throws ActivitiException;
    
    /**
     * 
     * @Title isPreNodeStartEvent
     * @date 2014-1-3
     * @Description: 检查申请节点前一个节点是否为开始节点
     * @author 张小虎
     * @param InputStream
     * @param applyTaskDefinekey
     * @throw ActivitiException
     * @return boolean
     */
    public boolean isPreNodeStartEvent(InputStream in, String applyTaskDefinekey)
        throws ActivitiException;
    
    /**
     * 
     * @Title getHistoryTaskByPIdAndTaskDef
     * @author zhxh
     * @Description: 通过流程ID，和节点定义，查询该节点定义的历史任务记录
     * @date 2013-12-25
     * @param processInstanceId
     * @param rejeckToTaskDefineKey
     * @return List<HistoricTaskInstance>
     */
    public List<HistoricTaskInstance> getHistoryTaskByPIdAndTaskDef(
        String processInstanceId, String taskDefineKey);
}
