package com.dqgb.sshframe.bpm.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dqgb.sshframe.bpm.service.IActivitiBaseService;
import com.dqgb.sshframe.bpm.service.IActivitiCallBack;
import com.dqgb.sshframe.common.util.XMLUtil;

@Transactional(readOnly = true)
public class ActivitiBaseServiceImpl implements IActivitiBaseService
{
    /**注入运行时服务*/
    @Autowired
    @Qualifier("runtimeService")
    private RuntimeService runtimeService;
    /**注入任务服务*/
    @Autowired
    @Qualifier("taskService")
    private TaskService taskService;
    /**发布服务*/
    @Autowired
    @Qualifier("repositoryService")
    private RepositoryService repositoryService;
    /**历史服务*/
    @Autowired
    @Qualifier("historyService")
    private HistoryService historyService;
    /**管理服务*/
    @Autowired
    @Qualifier("managementService")
    private ManagementService managementService;
    /**人员识别服务*/
    @Autowired
    @Qualifier("identityService")
    private IdentityService identityService;
    
    /**
     * 
     * @Title getHistoryService
     * @author zhxh
     * @Description: 获取历史任务服务
     * @date 2014-1-6
     * @return HistoryService
     */
    public HistoryService getHistoryService()
    {
        return historyService;
    }
    
    /**
     * 
     * @Title getIdentityService
     * @author zhxh
     * @Description:获取流程引擎中的身份识别服务
     * @date 2014-1-3
     * @return IdentityService
     */
    public IdentityService getIdentityService()
    {
        return identityService;
    }
    
    /**
     * 
     * @Title getRepositoryService
     * @author zhxh
     * @Description:获取流程引擎中的发布服务
     * @date 2014-1-3
     * @return RepositoryService
     */
    public RepositoryService getRepositoryService()
    {
        return repositoryService;
    }
    
    /**
     * 
     * @Title getManagementService
     * @author zhxh
     * @Description:获取流程引擎中的管理服务
     * @date 2014-1-3
     * @return ManagementService
     */
    public ManagementService getManagementService()
    {
        return managementService;
    }
    
    /**
     * 
     * @Title getRuntimeService
     * @author zhxh
     * @Description:获取流程引擎中的运行服务
     * @date 2014-1-3
     * @return runtimeService
     */
    public RuntimeService getRuntimeService()
    {
        return runtimeService;
    }
    
    /**
     * 
     * @Title getTaskService
     * @author zhxh
     * @Description:获取流程引擎中的任务服务
     * @date 2014-1-3
     * @return taskService
     */
    public TaskService getTaskService()
    {
        return taskService;
    }
    
    
    /**
     * 返回当前task对应的活动节点
     * 
     * @author 张小虎
     * @param taskId 任务id
     * @throw ActivitiException
     * @return ActivityImpl
     */
    
    public ActivityImpl findActivity(String taskId)
        throws ActivitiException
    {
        Task task = getTask(taskId);
        if (task == null)
        {
            throw new ActivitiException("任务不存在！");
        }
        ProcessDefinitionEntity processDefinition =
            (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService)
            .getDeployedProcessDefinition(task.getProcessDefinitionId());
        ActivityImpl activityImpl =
            processDefinition.findActivity(task.getTaskDefinitionKey());
        return activityImpl;
    }
    
    /**
     * 启动流程实例
     * 
     * @author 张小虎
     * @param key流程定义的key值 ， map启动实例是否具有的参数值
     * @return void
     * */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ProcessInstance startProcessInstanceByKey(String key, Map map,
        String startUserId)
        throws ActivitiException
    {
        
        if (map == null || CollectionUtils.isEmpty(map))
        {
            Authentication.setAuthenticatedUserId(startUserId);
            return runtimeService.startProcessInstanceByKey(key);
        }
        Authentication.setAuthenticatedUserId(startUserId);
        return runtimeService.startProcessInstanceByKey(key, map);
        
    }
    
    /**
     * 发布流程定义xml文件
     * 
     * @author 张小虎
     * @param absolutePath文件绝对路径
     * @throw ActivitiException
     * @return String
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String deployProcessDefinitionByXml(String absolutePath)
        throws ActivitiException
    {
        
        InputStream inputStream = null;
        File file = new File(absolutePath);
        try
        {
            synchronized (absolutePath)
            {
                inputStream = new FileInputStream(file);
                String deployMentFileName = file.getPath();
                if (file.getPath().lastIndexOf("\\") != -1)
                {
                    deployMentFileName =
                        file.getPath().substring(file.getPath()
                            .lastIndexOf("\\") + 1);
                }
                else if (file.getPath().lastIndexOf("/") != -1)
                {
                    deployMentFileName =
                        file.getPath().substring(file.getPath()
                            .lastIndexOf("/") + 1);
                }
                DeploymentBuilder d =
                    repositoryService.createDeployment()
                        .addInputStream(deployMentFileName, inputStream);
                inputStream.close();
                
                return d.deploy().getId();
            }
            
        }
        catch (FileNotFoundException e)
        {
            throw new ActivitiException(file.getAbsolutePath() + ",文件不存在",e);
        }
        catch (Exception e)
        {
            throw new ActivitiException("发布失败！" + e.getMessage(),e);
        }
        
    }
    
    /**
     * 发布流程定义zip文件
     * 
     * @author 张小虎
     * @param absolutePath文件绝对路径
     * @throw ActivitiException
     * @return void
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public synchronized void deployProcessDefinitionByZip(String absolutePath)
        throws ActivitiException
    {
        try
        {
            repositoryService.createDeployment()
                .addZipInputStream(new ZipInputStream(new FileInputStream(
                    new File(absolutePath))))
                .deploy();
        }
        catch (FileNotFoundException e)
        {
            throw new ActivitiException(absolutePath + ",文件不存在!",e);
        }
        catch (ActivitiException e)
        {
            throw new ActivitiException("发布失败！" + e.getMessage(),e);
        }
        
    }
    
    /**
     * 完成任务
     * 
     * @author 张小虎
     * @param taskId 任务id
     * @param map 完成的相关数据，没有设置为null或者空
     * @throw ActivitiException
     * @return void
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void completeTask(String taskId, Map map)
        throws ActivitiException
    {
        if (map == null || CollectionUtils.isEmpty(map))
        {
            taskService.complete(taskId);
        }
        else
        {
            taskService.complete(taskId, map);
        }
        
    }
    
    /**
     * 移交任务
     * 
     * @author 张小虎
     * @param taskId 任务id
     * @param userId 被移交的人员id
     * @throw ActivitiException
     * @return void
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void moveTask(String taskId, String userId)
        throws ActivitiException
    {
        taskService.setAssignee(taskId, userId);
        
    }
    
    /**
     * 托管任务
     * 
     * @author 张小虎
     * @param taskId 任务id
     * @param userId 被托管的人员id;
     * @param delegateStatus 为false代表任务完成之后不能够再返回给托管人处理 为true代表任务完成之后仍然需要托管人处理
     * @throw ActivitiException
     * @return void
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delegateTask(String taskId, String userId,
        boolean delegateStatus)
        throws ActivitiException
    {
        if (delegateStatus)
        {
            Task task = getTask(taskId);
            if (task == null)
            {
                throw new ActivitiException("任务不存在");
            }
            taskService.setOwner(taskId, task.getAssignee());
            taskService.setAssignee(taskId, userId);
            task.setDelegationState(DelegationState.RESOLVED);
            taskService.resolveTask(taskId);
        }
        else
        {
            taskService.delegateTask(taskId, userId);
        }
        
    }
    
    /**
     * 查询历史任务
     * 
     * @author 张小虎
     * @param taskId 历史任务id
     * @throw ActivitiException
     * @return HistoricTaskInstance
     */
    
    public HistoricTaskInstance getHistoricTask(String historicTaskId)
        throws ActivitiException
    {
        return historyService.createHistoricTaskInstanceQuery()
            .taskId(historicTaskId)
            .finished()
            .singleResult();
    }
    
    /**
     * 查询未处理任务
     * 
     * @author 张小虎
     * @param userId 查询的人员id
     * @throw ActivitiException
     * @return List<Task>
     */
    
    public List<Task> getToDoTaskList(String userId, String processInstanceId)
        throws ActivitiException
    {
        return taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .taskAssignee(userId)
            .orderByTaskCreateTime()
            .desc()
            .list();
    }
    
    /**
     * 查询已处理任务
     * 
     * @author 张小虎
     * @param userId 查询的人员id
     * @throw ActivitiException
     * @return List<Task>
     */
    
    public List<HistoricTaskInstance> getDoneTaskList(String userId)
    {
        
        return historyService.createHistoricTaskInstanceQuery()
            .taskAssignee(userId)
            .finished()
            .orderByHistoricTaskInstanceEndTime()
            .desc()
            .list();
    }
    
    /**
     * 查询任务
     * 
     * @author 张小虎
     * @param taskId 任务id
     * @throw ActivitiException
     * @return Task
     */
    
    public Task getTask(String taskId)
        throws ActivitiException
    {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task;
    }
    
    /**
     * 查询任务
     * 
     * @author 张小虎
     * @param taskId 任务id
     * @throw ActivitiException
     * @return HistoricTaskInstance
     */
    
    public Map getVariables(String executionId)
        throws ActivitiException
    {
        try
        {
            return runtimeService.getVariables(executionId);
        }
        catch (Exception e)
        {
            return null;
        }
        
    }
    
    /**
     * 回退任务
     * 
     * @author 张小虎
     * @param taskId 任务id
     * @param activityId 需要跳转活动节点Id
     * @throw ActivitiException
     * @return void
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void rollbackTask(String taskId, String activityId, Map commitMap)
        throws ActivitiException
    {
        Task task = getTask(taskId);
        if (task == null)
        {
            throw new ActivitiException("任务不存在");
        }
        
        ProcessDefinitionEntity processDefinition =
            (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).
            getDeployedProcessDefinition(task.getProcessDefinitionId());
        ActivityImpl activityImpl =
            processDefinition.findActivity(task.getTaskDefinitionKey());
        List<PvmTransition> pvmOrgTransitions =
            activityImpl.getOutgoingTransitions();
        List<PvmTransition> pvmTransitions = new ArrayList<PvmTransition>();
        for (PvmTransition pv : pvmOrgTransitions)
        {
            pvmTransitions.add(pv);
        }
        // 清除当前节点的流出
        pvmOrgTransitions.removeAll(pvmOrgTransitions);
        TransitionImpl newTransition = activityImpl.createOutgoingTransition();
        // 设置到需要跳转的节点
        ActivityImpl newActivityImpl =
            processDefinition.findActivity(activityId);
        newTransition.setDestination(newActivityImpl);
        
        // 提交当前任务，任务跳转到rollTaskId的节点上去
        if (commitMap != null && commitMap.size() > 0)
        {
            completeTask(taskId, commitMap);
        }
        else
        {
            completeTask(taskId, null);
        }
        
        // 获取更改后的当前节点流出
        pvmOrgTransitions = activityImpl.getOutgoingTransitions();
        // 清除掉更改的当前节点的流出
        pvmOrgTransitions.remove(newTransition);
        // 恢复原来的流出
        pvmOrgTransitions.addAll(pvmTransitions);
        // 去掉转向节点的输入
        List<PvmTransition> desTransitions =
            newActivityImpl.getIncomingTransitions();
        
        desTransitions.remove(newTransition);
    }
    
    /**
     * 删除发布
     * 
     * @author 张小虎
     * @param deploymentId 发布的id号
     * @param cascade true表示删除此流程定义的所有流程实例，false表示只删除发布的数据
     * @throw ActivitiException
     * @return void
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDeployMent(String deploymentId, boolean cascade)
        throws ActivitiException
    {
        //
        repositoryService.deleteDeployment(deploymentId, cascade);
    }
    
    /**
     * 得到流程图
     * 
     * @author 张小虎
     * @param processDefineId 流程定义id
     * @throw ActivitiException
     * @return InputStream
     */
    
    public InputStream getProcessImage(String processDefineId)
        throws ActivitiException
    {
        ProcessDefinition procDef =
            repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefineId)
                .singleResult();
        String diagramResourceName = procDef.getDiagramResourceName();
        InputStream imageStream =
            repositoryService.getResourceAsStream(procDef.getDeploymentId(),
                diagramResourceName);
        return imageStream;
    }
    
    /**
     * 返回当前正在处理的活动节点
     * 
     * @author 张小虎
     * @param taskId 任务Id
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    
    public List<ActivityImpl> getActivityImpl(String taskId)
        throws ActivitiException
    {
        List<ActivityImpl> actImpls = new ArrayList<ActivityImpl>();
        Task task = this.getTask(taskId);
        if (task == null)
        {
            throw new ActivitiException("任务不存在");
        }
        String processDefinitionId = task.getProcessDefinitionId();
        String processInstanceId = task.getProcessInstanceId();
        ProcessDefinitionEntity def =
            (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService)
            .getDeployedProcessDefinition(processDefinitionId);
        List<ActivityImpl> activitiList = def.getActivities();// 获得流程定义的所有节点
        List<String> activeActivityIds =
            runtimeService.getActiveActivityIds(processInstanceId);// 获取当前活动节点id
        for (String activeId : activeActivityIds)
        {
            for (ActivityImpl activityImpl : activitiList)
            {
                String id = activityImpl.getId();
                if (activityImpl.isScope())
                {
                    if (activityImpl.getActivities().size() > 1)
                    {
                        List<ActivityImpl> subAcList =
                            activityImpl.getActivities();
                        for (ActivityImpl subActImpl : subAcList)
                        {
                            String subid = subActImpl.getId();
                            if (activeId.equals(subid))
                            {// 获得执行到那个节点
                                actImpls.add(subActImpl);
                                break;
                            }
                        }
                    }
                }
                if (activeId.equals(id))
                {// 获得执行到那个节点
                    actImpls.add(activityImpl);
                }
            }
        }
        
        return actImpls;
    }
    
    /**
     * 返回当前正在处理的活动节点
     * 
     * @author 张小虎
     * @param processInstanceId
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    
    public List<ActivityImpl> getActivitiImplByProcessInstanceId(
        String processInstanceId)
        throws ActivitiException
    {
        List<ActivityImpl> actImpls = new ArrayList<ActivityImpl>();
        String processDefinitionId = "";
        HistoricProcessInstance hisProcessInstance =
            this.historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (hisProcessInstance != null)
        {
            processDefinitionId = hisProcessInstance.getProcessDefinitionId();
        }
        else
        {
            ProcessInstance processInstance =
                this.runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            processDefinitionId = processInstance.getProcessDefinitionId();
        }
        ProcessDefinitionEntity def =
            (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService)
            .getDeployedProcessDefinition(processDefinitionId);
        List<ActivityImpl> activitiList = def.getActivities();// 获得流程定义的所有节点
        List<String> activeActivityIds =
            runtimeService.getActiveActivityIds(processInstanceId);// 获取当前活动节点id
        for (String activeId : activeActivityIds)
        {
            for (ActivityImpl activityImpl : activitiList)
            {
                String id = activityImpl.getId();
                if (activityImpl.isScope())// 子流程节点，获取子流程
                {
                    if (activityImpl.getActivities().size() > 1)
                    {
                        List<ActivityImpl> subAcList =
                            activityImpl.getActivities();
                        
                        for (ActivityImpl subActImpl : subAcList)
                        {
                            String subid = subActImpl.getId();
                            
                            if (activeId.equals(subid))
                            {// 获得执行到那个节点
                                actImpls.add(subActImpl);
                                break;
                            }
                        }
                    }
                }
                if (activeId.equals(id))
                {// 获得执行到那个节点
                    actImpls.add(activityImpl);
                }
            }
        }
        
        return actImpls;
    }
    
    /**
     * 返回当前活动节点的前面所有任务节点
     * 
     * @author 张小虎
     * @param activityId
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    
    public List<ActivityImpl> getPreAllTaskAcitiviImpl(
        List<ActivityImpl> result, String activityId, String processInstanceId)
    {
        HistoricProcessInstance hi =
            this.historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        ProcessDefinitionEntity processDefinition =
            (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService)
            .getDeployedProcessDefinition(hi.getProcessDefinitionId());
        ActivityImpl activityImpl = processDefinition.findActivity(activityId);
        List<PvmTransition> pvms = activityImpl.getIncomingTransitions();
        for (PvmTransition pv : pvms)
        {
            ActivityImpl temp = (ActivityImpl)pv.getSource();
            if ("userTask".equals(temp.getProperty("type")))
            {
                result.add(temp);
                getPreAllTaskAcitiviImpl(result,
                    temp.getId(),
                    processInstanceId);
            }
            else if ("startEvent".equals(temp.getProperty("type")))
            {
                return result;
            }
            else
            {
                getPreAllTaskAcitiviImpl(result,
                    temp.getId(),
                    processInstanceId);
            }
        }
        return null;
    }
    
    /**
     * 查询历史流程实例
     * 
     * @author 张小虎
     * @param processInstanceId
     * @throw ActivitiException
     * @return HistoricProcessInstance
     */
    
    public HistoricProcessInstance getHistoricProcessInstance(
        String processInstanceId)
        throws ActivitiException
    {
        return this.historyService.createHistoricProcessInstanceQuery()
            .processInstanceId(processInstanceId)
            .singleResult();
    }
    
    /**
     * 获取能够回退活动节点
     * 
     * @author 张小虎
     * @param taskId 当前的 任务id
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    
    public List<ActivityImpl> getRollBackActivity(String taskId)
        throws ActivitiException
    {
        Task task = getTask(taskId);
        if (task == null)
        {
            throw new ActivitiException("任务不存在");
        }
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance =
            runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        ProcessDefinitionEntity processDefinitionEntity =
            (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService)
            .getDeployedProcessDefinition(task.getProcessDefinitionId());
        ActivityImpl currentActivityImpl =
            processDefinitionEntity.findActivity(task.getTaskDefinitionKey());
        List<ActivityImpl> rollBackActivity = new ArrayList<ActivityImpl>();
        if (currentActivityImpl.getProperty("multiInstance") != null)
        {// 会签节点不允许回退
            return rollBackActivity;
        }
        iteratorBackAcitivity(currentActivityImpl,
            rollBackActivity,
            processInstance);
        
        return rollBackActivity;
    }
    
    public List<ActivityImpl> canback(String taskId) throws ActivitiException{
    	HistoricTaskInstance htask = this.getHistoricTask(taskId);
    	if (htask == null)
    	{
    		throw new ActivitiException("任务不存在");
    	}
    	String processInstanceId = htask.getProcessInstanceId();
    	System.out.println("count on you :"+htask.getTaskDefinitionKey());
    	ProcessInstance processInstance =
    			runtimeService.createProcessInstanceQuery()
    			.processInstanceId(processInstanceId)
    			.singleResult();
    	if(processInstance == null ){
    		throw new ActivitiException("流程已完成。");
    	}
    	Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    	ProcessDefinitionEntity processDefinitionEntity =
    			(ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService)
    			.getDeployedProcessDefinition(task.getProcessDefinitionId());
    	ActivityImpl currentActivityImpl =
    			processDefinitionEntity.findActivity(task.getTaskDefinitionKey());
    	List<ActivityImpl> rollBackActivity = new ArrayList<ActivityImpl>();
    	if (currentActivityImpl.getProperty("multiInstance") != null)
    	{// 会签节点不允许回退
    		throw new ActivitiException("会签节点不允许回退。");
    	}
    	
    	iteratorBackAcitivity(currentActivityImpl,
    			rollBackActivity,
    			processInstance);
    	
    	return rollBackActivity;
    		}
    
    public Task getActiveTaskByDoneTaskId(String htaskId)
    		throws ActivitiException{

    	HistoricTaskInstance htask = this.getHistoricTask(htaskId);
    	if (htask == null)
    	{
    		throw new ActivitiException("任务不存在");
    	}
    	String processInstanceId = htask.getProcessInstanceId();
    	ProcessInstance processInstance =
    			runtimeService.createProcessInstanceQuery()
    			.processInstanceId(processInstanceId)
    			.singleResult();
    	if(processInstance == null ){
    		throw new ActivitiException("流程已完成。");
    	}
    	Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    	ProcessDefinitionEntity processDefinitionEntity =
    			(ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService)
    			.getDeployedProcessDefinition(task.getProcessDefinitionId());
    	ActivityImpl currentActivityImpl =
    			processDefinitionEntity.findActivity(task.getTaskDefinitionKey());
    	if (currentActivityImpl.getProperty("multiInstance") != null)
    	{// 会签节点不允许回退
    		throw new ActivitiException("会签节点不允许回退。");
    	}
    	return task;
    }
    
    public Task getActiveTaskByProcessInsId(String processInstanceId)
    		throws ActivitiException{

    	ProcessInstance processInstance =
    			runtimeService.createProcessInstanceQuery()
    			.processInstanceId(processInstanceId)
    			.singleResult();
    	if(processInstance == null ){
    		throw new ActivitiException("流程已完成。");
    	}
    	Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    	return task;
    }
    
    /**
     * 递归获取回退节点
     * 
     * @author 张小虎
     * @param currentActivityImpl 当前的任务对应的活动节点
     * @param rollBackActivity 最后返回的活动节点集合
     * @param processInstance 当前流程实例
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    protected List<ActivityImpl> iteratorBackAcitivity(
        ActivityImpl currentActivityImpl, List<ActivityImpl> rollBackActivity,
        ProcessInstance processInstance)
        throws ActivitiException
    {
        List<PvmTransition> inComing =
            currentActivityImpl.getIncomingTransitions();
        List<PvmTransition> cloneComing = new ArrayList<PvmTransition>();
        cloneComing.addAll(inComing);
        this.getUserTaskCommingAndRollBack(currentActivityImpl, 
            cloneComing, rollBackActivity,processInstance);
        for (PvmTransition transition : cloneComing)
        {
            ActivityImpl activityImpl = (ActivityImpl)transition.getSource();
            String type = (String)activityImpl.getProperty("type");
            if ("parallelGateway".equals(type))
            {
                String gatewayId = activityImpl.getId();
                if (gatewayId.toUpperCase().startsWith(("FORK")))
                {// 并行节点的起点,不能回退，直接返回
                    return rollBackActivity;
                }
                this.getGatewayRollBackNode(gatewayId, processInstance,
                    activityImpl, rollBackActivity);
            }
            else if ("userTask".equals(type))
            {// 任务节点
                rollBackActivity.add(activityImpl);
                iteratorBackAcitivity(activityImpl,
                    rollBackActivity,
                    processInstance);
            }
            else if ("exclusiveGateway".equals(type))
            {// 分支节点
                iteratorBackAcitivity(activityImpl,
                    rollBackActivity,
                    processInstance);
            }
            else if ("startEvent".equals(type))
            {// 开始节点，不能回退了直接返回
                return rollBackActivity;
            }
            else
            {
                iteratorBackAcitivity(activityImpl,
                    rollBackActivity,
                    processInstance);
            }
        }
        
        return rollBackActivity;
    }
    /**
     * 
    * @Title getUserTaskCommingAndRollBack
    * @author zhxh
    * @Description: 获取驳回节点的时候，任务节点的流入和驳回节点
    * @date 2014-1-6
    * @param currentActivityImpl
    * @param cloneComing
    * @param rollBackActivity
     */
    private void getUserTaskCommingAndRollBack(
        ActivityImpl currentActivityImpl,
        List<PvmTransition> cloneComing,
        List<ActivityImpl> rollBackActivity,
        ProcessInstance processInstance){

        // 判断是不是任务节点
        if ("userTask".equals(currentActivityImpl.getProperty("type")))
        {
            if (cloneComing.size() > 1)
            {// 任务前面节点来至于有条件的分支任务
                cloneComing.clear();
                ActivityImpl lastActivity =
                    findLastActivity(processInstance, currentActivityImpl);// 找到最近的分支路径
                if ("userTask".equals(lastActivity.getProperty("type")))
                {
                    rollBackActivity.add(lastActivity);// 返回的分支第一个节点为任务则添加
                }
                cloneComing.addAll(lastActivity.getIncomingTransitions());
            }
        }
    }
    /**
     * 
    * @Title getGatewayRollBackNode
    * @author zhxh
    * @Description: 获取并行节点的驳回节点
    * @date 2014-1-6
    * @param gatewayId
    * @param processInstance
    * @param activityImpl
    * @param rollBackActivity
     */
    private void getGatewayRollBackNode(String gatewayId,ProcessInstance processInstance,
        ActivityImpl activityImpl,List<ActivityImpl> rollBackActivity)
    {

        if (gatewayId.toUpperCase().startsWith(("JOIN")))
        {// 并行的结束
         // 并行结束节点
         // 寻找并行开始节点
            List<PvmTransition> parallelIn =
                activityImpl.getIncomingTransitions();
            if (parallelIn.size() > 0)
            {
                ActivityImpl returnActivity = null;
                List<ActivityImpl> joins =
                    new ArrayList<ActivityImpl>();
                joins.add(activityImpl);
                PvmTransition paralleTransation = parallelIn.get(0);
                returnActivity =
                    findParallelGatewayStart(paralleTransation, joins);// 查找join活动节点对应的fork节点
                if (returnActivity != null)
                {
                    // 从fork(开始)查询其他节点
                    iteratorBackAcitivity(returnActivity,
                        rollBackActivity,
                        processInstance);
                }
            }
        }
    }
    /**
     * 获取流程定义
     * 
     * @author 张小虎
     * @param id
     * @throw ActivitiException
     * @return ProcessDefinition
     */
    
    public ProcessDefinition getProcessDefinitionById(String id)
        throws ActivitiException
    {
        return this.repositoryService.createProcessDefinitionQuery()
            .processDefinitionId(id)
            .singleResult();
    }
    
    @Override
    public boolean isPreNodeStartEvent(InputStream in, String applytaskDefinekey)
        throws ActivitiException
    {
        try
        {
            boolean isPreNode = false;
            Document document = new SAXReader().read(in);
            List<Element> resultList = new ArrayList();
            resultList =
                XMLUtil.getElementList(document,
                    resultList,
                    null,
                    "sequenceFlow");
            List sourceIds = new ArrayList();
            for (Element e : resultList)
            {
                if (e.attributeValue("targetRef").equals(applytaskDefinekey))
                {
                    sourceIds.add(e.attributeValue("sourceRef"));
                }
            }
            if (sourceIds.size() != 1)
            {
                isPreNode = false;
            }
            else 
            {
            String startEventId = (String)sourceIds.get(0);
            resultList = new ArrayList();
            XMLUtil.getElementList(document, resultList, null, "startEvent");
            String startEventRealId = resultList.get(0).attributeValue("id");
            if (startEventRealId.equals(startEventId))
            {
                isPreNode = true;
            }
            else
            {
                isPreNode = false;
            }
           }
            return isPreNode;
        }
        catch (Exception e)
        {
            throw new ActivitiException(e.getMessage(),e);
        }
    }
    
    /**
     * 获取ParallelGateway的join节点对应的fork节点
     * 
     * @author 张小虎
     * @param transition 当前的任务对应的活动节点
     * @param joins ParallelGateway的join活动节点集合，joins中包含当前join节点
     * @throw ActivitiException
     * @return ActivityImpl
     */
    protected ActivityImpl findParallelGatewayStart(PvmTransition transition,
        List<ActivityImpl> joins)
        throws ActivitiException
    {
        ActivityImpl activity = (ActivityImpl)transition.getSource();
        String type = (String)activity.getProperty("type");
        if ("parallelGateway".equals(type))
        {
            String gatewayId = activity.getId();
            if (gatewayId.toUpperCase().startsWith(("FORK")))
            {// 并行节点的起点
                joins.remove(0);// 去掉一个join
                if (joins.size() < 1)
                {
                    return activity;
                }
            }
            if (gatewayId.toUpperCase().startsWith(("JOIN")))
            {// 并行的结束
                joins.add(activity);
                List<PvmTransition> parallelIn =
                    activity.getIncomingTransitions();
                // 选择其中一条路 径
                transition = parallelIn.get(0);
                return findParallelGatewayStart(transition, joins);
            }
        }
        else if ("startEvent".equals(type))
        {// 开始节点，不能直接返回
            return null;
        }
        else
        {
            // 任何节点只选择一条路路径
            return findParallelGatewayStart(activity.getIncomingTransitions()
                .get(0), joins);
            
        }
        return null;
        
    }
    
    /**
     * 暴露基本的回调方法，个性化实现无法满足的数据
     * 
     * @author 张小虎
     * @param IActivitiCallBack 回调接口
     * @throw ActivitiException
     * @return Object
     */
    
    public Object executeActiviti(IActivitiCallBack activitiCallBack)
        throws ActivitiException
    {
        return activitiCallBack.doActiviti(taskService,
            runtimeService,
            repositoryService,
            historyService,
            identityService);
    }
    
    /**
     * 获取当前的活动节点最近一次执行的路径的节点
     * 
     * @author 张小虎
     * @param processInstance 当前的任务对应的流程实例
     * @param activityImpl 当前活动节点
     * @throw ActivitiException
     * @return ActivityImpl
     */
    protected ActivityImpl findLastActivity(ProcessInstance processInstance,
        ActivityImpl activityImpl)
        throws ActivitiException
    {
        List<PvmTransition> inComing = activityImpl.getIncomingTransitions();
        ActivityImpl activityImplReturn = null;
        Date date = null;
        for (PvmTransition transaction : inComing)
        {
            ActivityImpl activity = (ActivityImpl)transaction.getSource();
            List<HistoricActivityInstance> historicActivityInstances =
                historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstance.getId())
                    .activityId(activity.getId())
                    .finished()
                    .orderByHistoricActivityInstanceEndTime()
                    .desc()
                    .list();
            if (historicActivityInstances != null
                && historicActivityInstances.size() > 0)
            {
                HistoricActivityInstance historicActivityInstance =
                    historicActivityInstances.get(0);
                if (date != null)
                {
                    if (date.before(historicActivityInstance.getEndTime()))
                    {
                        date = historicActivityInstance.getEndTime();
                        activityImplReturn = activity;
                    }
                }
                else
                {
                    date = historicActivityInstance.getEndTime();
                    activityImplReturn = activity;
                }
            }
        }
        
        return activityImplReturn;
    }
    
    /**
     * 结束整个流程
     * 
     * @author 张小虎
     * @param processInstanceId 当前的流程实例Id
     * @throw ActivitiException
     * @return void
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void endProcessInstance(String processInstanceId, String reason)
        throws ActivitiException
    {
        runtimeService.deleteProcessInstance(processInstanceId, reason);
        
    }
    
    /**
     * 获取流程定义
     * 
     * @author 张小虎
     * @param taskId 任务Id
     * @throw ActivitiException
     * @return ProcessDefinition
     */
    
    public ProcessDefinition getProcessDefinition(String taskId)
        throws ActivitiException
    {
        Task task = this.getTask(taskId);
        if (task == null)
        {
            throw new ActivitiException("任务不存在");
        }
        ProcessDefinition processDefinition =
            repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(task.getProcessDefinitionId())
                .singleResult();
        return processDefinition;
    }
    
    /**
     * 获取流程实例
     * 
     * @author 张小虎
     * @param taskId 任务Id
     * @throw ActivitiException
     * @return ProcessInstance
     */
    
    public ProcessInstance getProcessInstance(String taskId)
        throws ActivitiException
    {
        Task task = this.getTask(taskId);
        if (task == null)
        {
            throw new ActivitiException("任务不存在");
        }
        ProcessInstance processInstance =
            runtimeService.createProcessInstanceQuery().singleResult();
        return processInstance;
    }
    
    /**
     * 查找activiti5中流程文件设置的参数
     * 
     * @author 张小虎
     * @throws ActivitiException
     * @throws IOException
     * @date 2012-5-29
     * @return Map<String,Object>
     */
    
    public Map<String, Object> findXmlParam(InputStream in, String taskDefineKey)
        throws ActivitiException
    {
        Map map = new HashMap();
        List usersId = new ArrayList();
        try
        {
            Document document = new SAXReader().read(in);
            Element rootElement = document.getRootElement();
            List<Element> children = rootElement.elements();
            Element processElement = null;
            for (Element e : children)
            {
                if ("process".equals(e.getQName().getName()))
                {
                    processElement = e;
                    break;
                }
            }
            
            List<Element> childrenTemp = processElement.elements();
            for (Element eTmp : childrenTemp)
            {
                if ("userTask".equals(eTmp.getQName().getName())
                    && eTmp.attributeValue("id").equals(taskDefineKey))
                {
                    String taskType = eTmp.attributeValue("taskType");
                    String formUrl = eTmp.attributeValue("formUrl");
                    String userId = eTmp.attributeValue("user");
                    String roleIds = eTmp.attributeValue("roleIds");
                    map.put("user", userId);
                    map.put("taskType", taskType);
                    map.put("formUrl", formUrl);
                    map.put("roleIds", roleIds);
                    break;
                }
            }
        }
        catch (Exception e)
        {
            throw new ActivitiException(e.getMessage(),e);
        }
        return map;
        
    }
    
    /**
     * 保存任务
     * 
     * @author 张小虎
     * @throws ActivitiException
     * @throws IOException
     * @param task 任务实体
     * @date 2012-5-29
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveTask(Task task)
        throws ActivitiException
    {
        this.taskService.saveTask(task);
        
    }
    
    /**
     * 返回当前任务的下一节点所有类型节点
     * 
     * @author 张小虎
     * @param taskId 任务Id
     * @throw ActivitiException
     * @return List<ActivityImpl>
     */
    
    public List<ActivityImpl> getNextActivitiImpl(String taskId)
        throws ActivitiException
    {
        ActivityImpl activity = this.findActivity(taskId);
        List<ActivityImpl> list = new ArrayList<ActivityImpl>();
        for (PvmTransition sequenceFlow : activity.getOutgoingTransitions())
        {
            list.add((ActivityImpl)sequenceFlow.getDestination());
        }
        return list;
    }
    
    /**
     * 查询未处理任务
     * 
     * @author 张小虎
     * @param userId 查询的人员id
     * @param taskDefineKey
     * @throw ActivitiException
     * @return List<Task>
     */
    
    public List<Task> getToDoTaskListByTaskDefineKey(String userId,
        String taskDefineKey, String processInstanceId)
        throws ActivitiException
    {
        TaskQuery query = taskService.createTaskQuery();
        if(StringUtils.isNotBlank(userId))
        {
            query.taskAssignee(userId);
        }
        if(StringUtils.isNotBlank(taskDefineKey))
        {
            query.taskDefinitionKey(taskDefineKey);
        }
        if(StringUtils.isNotBlank(processInstanceId))
        {
            query.processInstanceId(processInstanceId);
        }
        query.orderByTaskCreateTime().desc();
        return query.list();
    }
    
    /**
     * 获取已经执行的xml报文
     * 
     * @author 张小虎
     * @param processInstanceId
     * @throw ActivitiException
     * @return InputStream
     */
    
    public InputStream getProcessXmlResource(String processInstanceId)
    {
        String processDefinitionId = "";
        HistoricProcessInstance hisProcessInstance =
            this.historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (hisProcessInstance != null)
        {
            processDefinitionId = hisProcessInstance.getProcessDefinitionId();
        }
        else
        {
            ProcessInstance processInstance =
                this.runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            processDefinitionId = processInstance.getProcessDefinitionId();
        }
        ProcessDefinitionEntity processDefinition =
            (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).
            getDeployedProcessDefinition(processDefinitionId);
        return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
            processDefinition.getResourceName());
    }
    
    /**
     * 得到活动节点上设置的值，只在当前节点上
     * 
     * @author 张小虎
     * @param taskId 任务id
     * @throw ActivitiException
     * @return Task
     */
    
    public Map getVariablesLocal(String executionId)
        throws ActivitiException
    {
        try
        {
            return runtimeService.getVariablesLocal(executionId);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
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
        String processInstanceId, String taskDefineKey)
    {
        return this.historyService.createHistoricTaskInstanceQuery()
            .processInstanceId(processInstanceId)
            .taskDefinitionKey(taskDefineKey)
            .finished()
            .orderByHistoricTaskInstanceEndTime()
            .desc()
            .list();
    }
    
}
