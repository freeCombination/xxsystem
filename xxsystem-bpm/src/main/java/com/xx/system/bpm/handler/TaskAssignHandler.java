/**
 * @文件名 TaskAssignHandler.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 任务分配
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.dqgb.sshframe.bpm.entity.ActivitiDelegate;
import com.dqgb.sshframe.bpm.service.IActivitiAndBusinessService;
import com.dqgb.sshframe.bpm.service.IActivitiCallBack;
import com.dqgb.sshframe.bpm.service.IActivitiDelegateService;
import com.dqgb.sshframe.bpm.service.IActivitiTaskService;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.util.XMLUtil;

/**
 * 分配人员任务
 * 
 * @author zhxh
 * @version V1.20,2013-12-23 下午3:48:54
 * @see TaskAssignHandler#createTaskListener
 * @since V1.20
 * 
 */
@Component("taskAssignHandler")
@Lazy(false)
public class TaskAssignHandler
{
    /** activiti和业务服务 */
    @Autowired
    @Qualifier("activitiAndBusinessService")
    private IActivitiAndBusinessService activitiAndBusinessService;
    
    /** 托管服务 */
    @Autowired
    @Qualifier("activitiDelegateService")
    private IActivitiDelegateService activitiDelegateService;
    
    /** 任务服务 */
    @Autowired
    @Qualifier("activitiTaskService")
    private IActivitiTaskService activitiTaskService;
    
    private final static Log log = LogFactory.getLog(TaskAssignHandler.class);
    
    /**
     * 
     * @Title createTaskListener
     * @author zhxh
     * @Description:在创建任务的时候分配角色和用户
     * @date 2013-12-23
     * @param delegateTask 任务节点实体
     * @throws ActivitiException
     * @throws IOException
     */
    public void createTaskListener(Task delegateTask)
        throws ActivitiException, IOException
    {
        
        final String processDefinitionId =
            delegateTask.getProcessDefinitionId();
        final String taskDefineKey = delegateTask.getTaskDefinitionKey();
        InputStream in =
            getSourceInputStream(delegateTask.getProcessDefinitionId());
        boolean flag =
            activitiAndBusinessService.isPreNodeStartEvent(in,
                delegateTask.getTaskDefinitionKey());
        in.reset();
        Map<String, Object> mapXml =
            activitiAndBusinessService.findXmlParam(in, taskDefineKey);
        
        String taskType = (String)mapXml.get("taskType");
        String formUrl = (String)mapXml.get("formUrl");
        String userId = (String)mapXml.get("user");
        String roleIds = (String)mapXml.get("roleIds");
        
        if ("bpm_apply".equals(taskType) && flag)
        {
            delegateTask.setFormURL(formUrl);
            delegateTask.setAssignee(SystemConstant.PROCESS_AUTO_LOGINNAME);
            
        }
        else
        {
            List<ActivitiDelegate> activitiDelegates = new ArrayList();
            if (!"bpm_countersign".equals(taskType))// 不是会签节点，单个任务
            {
                if (StringUtils.isNotBlank(userId))// 选择了人员
                {
                    delegateTask.setAssignee(userId);
                    
                }
                else if (StringUtils.isNotBlank(roleIds))
                {
                    this.activitiTaskService.addActivitiTaskRole(delegateTask.getId(),
                        roleIds);
                }
                
                if (StringUtils.isNotBlank(delegateTask.getAssignee()))
                {
                    activitiDelegates =
                        this.activitiDelegateService.getDelegateByUserIdStartTime(delegateTask.getCreateTime(),
                            delegateTask.getAssignee());
                }
                if (activitiDelegates.size() > 0)
                {
                    delegateTask.setAssignee(activitiDelegates.get(0)
                        .getDelegateUser()
                        .getUsername());
                    delegateTask.setOwner(activitiDelegates.get(0)
                        .getOwner()
                        .getUsername());
                }
            }
            else
            // 是会签节点
            {
                activitiDelegates =
                    this.activitiDelegateService.getDelegateByUserIdStartTime(delegateTask.getCreateTime(),
                        delegateTask.getAssignee());
                if (activitiDelegates.size() > 0)
                {
                    delegateTask.setOwner(delegateTask.getAssignee());
                    delegateTask.setAssignee(activitiDelegates.get(0)
                        .getDelegateUser()
                        .getUsername());
                }
            }
            delegateTask.setFormURL(formUrl);
        }
        try
        {
            in.close();
        }
        catch (IOException e)
        {
            log.error(e);
        }
    }
    
    /**
     * 
     * @Title assignTaskListener
     * @author zhxh
     * @Description:分配任务完成执行的操作
     * @date 2013-12-28
     * @param task
     */
    public void assignTaskListener(Task task)
    {
    }
    
    /**
     * 
     * @Title completeTaskListener
     * @author zhxh
     * @Description: 完成任务执行的操作
     * @date 2013-12-28
     * @param task
     */
    public void completeTaskListener(Task task)
    {
        
    }
    
    /**
     * 
     * @Title executionStart
     * @author zhxh
     * @Description: 最开始时候监听任务
     * @date 2013-12-24
     * @param e
     */
    public void executionStart(ExecutionListenerExecution e)
    {
        try
        {
            ExecutionEntity entity = (ExecutionEntity)e;
            final String processDefinitionId = entity.getProcessDefinitionId();
            InputStream in = getSourceInputStream(processDefinitionId);
            final String taskDefineKey = entity.getActivityId();
            ActivityImpl activity =
                (ActivityImpl)activitiAndBusinessService.executeActiviti(new IActivitiCallBack()
                {
                    @Override
                    public Object doActiviti(TaskService taskService,
                        RuntimeService runtimeService,
                        RepositoryService repositoryService,
                        HistoryService historyService,
                        IdentityService identityService)
                    {
                        ProcessDefinitionEntity processDefinition =
                            (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);
                        return processDefinition.findActivity(taskDefineKey);
                    }
                });
            Map<String, Object> mapXml =
                activitiAndBusinessService.findXmlParam(in,
                    entity.getActivityId());
            Map map = new HashMap();
            List list = new ArrayList();
            if (activity.getProperty("multiInstance") != null)
            {
                String user = (String)mapXml.get("user");
                if (StringUtils.isNotBlank(user))
                {
                    String strs[] = user.split(",");
                    for (String str : strs)
                    {
                        if (StringUtils.isNotBlank(str))
                        {
                            if (!list.contains(str))
                            {
                                list.add(str);
                            }
                        }
                    }
                }
                in.reset();
                String roleIds = (String)mapXml.get("roleIds");
                if (StringUtils.isNotBlank(roleIds))
                {
                    Document document = new SAXReader().read(in);
                    Element root = document.getRootElement();
                    List<Element> resultList = new ArrayList<Element>();
                    if ("process".equals(root.getQName().getName().trim()))
                    {
                        resultList.add(root);
                    }
                    XMLUtil.getElementList(resultList, root, "process");
                    String bpmType =
                        resultList.get(0).attributeValue("bpmType");
                    List<String> loginNames =
                        this.activitiAndBusinessService.getUserByRoleIdsAndCategoryId(roleIds,
                            Integer.valueOf(bpmType));
                    for (String loginName : loginNames)
                    {
                        if (!list.contains(loginName))
                        {
                            list.add(loginName);
                        }
                    }
                }
            }
            if (list.size() > 0)
            {
                map.put("assigneeList", list);
                entity.setVariables(map);
            }
            in.close();
        }
        catch (Exception e1)
        {
            throw new ActivitiException("分配任务失败!",e1);
        }
        
    }
    
    /**
     * 
     * @Title getSourceInputStream
     * @author zhxh
     * @Description:获取流程实例报文
     * @date 2013-12-28
     * @param processDefinitionId
     * @return InputStream
     */
    private InputStream getSourceInputStream(final String processDefinitionId)
    {
        InputStream in =
            (InputStream)activitiAndBusinessService.executeActiviti(new IActivitiCallBack()
            {
                @Override
                public Object doActiviti(TaskService taskService,
                    RuntimeService runtimeService,
                    RepositoryService repositoryService,
                    HistoryService historyService,
                    IdentityService identityService)
                {
                    ProcessDefinitionEntity processDefinition =
                        (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);
                    return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                        processDefinition.getResourceName());
                }
            });
        return in;
    }
    
}
