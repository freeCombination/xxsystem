/**
 * @文件名 ActivitiAndBusinessServiceImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述  流程操作与业务
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dqgb.sshframe.bpm.dao.IActivitiAndBusinessDao;
import com.dqgb.sshframe.bpm.entity.ActivitiDefineTemplate;
import com.dqgb.sshframe.bpm.entity.ActivitiProcessApproval;
import com.dqgb.sshframe.bpm.entity.ActivitiProcessInstance;
import com.dqgb.sshframe.bpm.service.IActivitiAndBusinessService;
import com.dqgb.sshframe.bpm.service.IActivitiCallBack;
import com.dqgb.sshframe.bpm.service.IActivitiDefineTemplateService;
import com.dqgb.sshframe.bpm.vo.ActivitiProcessInstanceVo;
import com.dqgb.sshframe.common.constant.Constant;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.org.entity.Organization;
import com.dqgb.sshframe.role.entity.Role;
import com.dqgb.sshframe.role.service.IRoleService;
import com.dqgb.sshframe.user.entity.User;
import com.dqgb.sshframe.user.vo.UserVo;

/**
 * 
 * 流程操作与业务实现类
 * 
 * @author zhxh
 * @version V1.20,2013-12-28 上午8:57:44
 * @since V1.20
 * @depricated
 */
@Transactional(readOnly = true)
@Service("activitiAndBusinessService")
public class ActivitiAndBusinessServiceImpl extends ActivitiBaseServiceImpl
    implements IActivitiAndBusinessService
{
    private static Log log =
        LogFactory.getLog(ActivitiAndBusinessServiceImpl.class);
    
    /** 流程业务持久层 */
    @Autowired
    @Qualifier("activitiAndBusinessDao")
    private IActivitiAndBusinessDao activitiAndBusinessDao;
    
    /** 角色 **/
    @Autowired
    @Qualifier("roleService")
    private IRoleService roleService;
    
    /** 托管服务接口 */
    @Autowired
    @Qualifier("activitiDefineTemplateService")
    private IActivitiDefineTemplateService activitiDefineTemplateService;
    
    
    @Override
    public List<ActivitiProcessInstanceVo> getActivitiProcessInstance(
        String processInstanceIds)
        throws ServiceException
    {
        try
        {
            List<ActivitiProcessInstanceVo> vos = new ArrayList();
            List<ActivitiProcessInstance> datas =
                activitiAndBusinessDao.getActivitiProcessInstance(processInstanceIds);
            for (ActivitiProcessInstance p : datas)
            {
                ActivitiProcessInstanceVo vo = new ActivitiProcessInstanceVo();
                vo.setProcessName(p.getProcessName());
                vo.setProcessType(p.getProcessType());
                if (p.getProcessCreater() != null)
                {
                    vo.setUserId(p.getProcessCreater().getUsername());
                    vo.setUserName(p.getProcessCreater().getRealname());
                }
                if (p.getCategory() != null
                    && p.getCategory().getIsDelete() == 0)
                {
                    vo.setCategoryId(p.getCategory().getCategoryId());
                    vo.setCategoryCode(p.getCategory().getCode());
                    vo.setCategoryName(p.getCategory().getName());
                }
                vo.setProcessDefineKey(vo.getProcessDefineKey());
                vo.setProcessInstanceId(p.getProcessInstanceId());
                vos.add(vo);
            }
            return vos;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public String startProcess(Map<String, Object> paramMap, Map commitMap,
        String rootPath, String processDefineKey)
        throws ServiceException
    {
        String depolyMent = null;
        ProcessInstance processInstance = null;
        try
        {
            synchronized (processDefineKey)
            {
                Map map = new HashMap();
                map.put("processDefineKey", processDefineKey);
                map.put("isDelete", 0);
                User u = (User)paramMap.get("user");
                String processType = (String)paramMap.get("processType");
                String processCode = (String)paramMap.get("processCode");
                String processName = (String)paramMap.get("processName");
                String businessId = (String)paramMap.get("businessId");
                String businessOrg = (String)paramMap.get("businessOrg");
                businessId = createBusinessId(businessId);
                paramMap.remove("processName");
                paramMap.remove("processType");
                paramMap.remove("processCode");
                paramMap.remove("businessId");
                paramMap.remove("businessOrg");
                paramMap.remove("user");
                List<ActivitiDefineTemplate> templates =
                    this.activitiDefineTemplateService.getActivitiDefineTemplate(map);
                if (templates.size() < 1)
                {
                    throw new ServiceException("流程模板不存在!");
                }
                ActivitiDefineTemplate activitiDefineTemplate =
                    templates.get(0);
                String absolutePath =
                    rootPath + activitiDefineTemplate.getUrl();
                depolyMent = this.deployProcessDefinitionByXml(absolutePath);//发布模版
                processInstance =
                    this.startProcessInstanceByKey(activitiDefineTemplate.getProcessDefineKey(),
                        commitMap,
                        u.getUsername());
                
                List<ActivitiProcessInstance> activitiProcessInstanceses =  new ArrayList();
                ActivitiProcessInstance activitiProcessInstance =
                    new ActivitiProcessInstance();
                activitiProcessInstance.setProcessCreateDate(new Date());
                activitiProcessInstance.setProcessCreater(u);
                activitiProcessInstance.setProcessInstanceId(processInstance.getId());
                activitiProcessInstance.setProcessType(processType);
                activitiProcessInstance.setProcessName(processName);
                activitiProcessInstance.setProcessCode(processCode);
                activitiProcessInstance.setProcessDefineKey(processDefineKey);
                activitiProcessInstance.setCategory(activitiDefineTemplate.getCategory());
                activitiProcessInstance.setBusinessId(businessId);
                activitiProcessInstance.setBusinessOrg(businessOrg);
                activitiProcessInstanceses.add(activitiProcessInstance);
                this.addActivitiProcessInstances(activitiProcessInstanceses);
                for (ActivitiProcessInstance p : activitiProcessInstanceses)
                {
                    this.commitApplyTask(paramMap, p.getProcessInstanceId(), u);
                }
            }
        }
        catch (ServiceException e)
        {
            throw new ServiceException(e.getMessageKey(), e);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
        return processInstance.getId();
        
    }
    
    /**
     * 
     * @Title createBusinessId
     * @author zhxh
     * @Description: 创建日期和状态
     * @date 2014-1-6
     * @param vo
     * @param endTime
     * @param status
     * @param durationTime
     */
    private String createBusinessId(String businessId)
    {
        StringBuffer temp = new StringBuffer();
        if (StringUtils.isNotBlank(businessId))
        {
            if (!businessId.startsWith(","))
            {
                temp.append(",").append(businessId);
            }
            
            if (!businessId.endsWith(","))
            {
                temp.append(businessId).append(",");
            }
        }
        return businessId;
    }
    
    /**
     * 添加事项预流程实例对应
     * 
     * @param List <ProcessInstances>
     * @throws ServiceException
     * @return void
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addActivitiProcessInstances(
        List<ActivitiProcessInstance> processInstances)
        throws ServiceException
    {
        try
        {
            this.activitiAndBusinessDao.addActivitiProcessInstances(processInstances);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    /**
     * 获取流程审批签名信息
     * 
     * @author 侯永超
     * @date 2011-5-24
     * @param processInstanceId
     * @throws ServiceException
     * @return ProcessApproval
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ActivitiProcessApproval> getProcessApprovalList(
        String processInstanceId)
        throws ServiceException
    {
        try
        {
            return this.activitiAndBusinessDao.getProcessApprovalList(processInstanceId);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    @Override
    public void endProcessInstanceByMap(Map paramMap)
        throws ServiceException
    {
        try
        {
            User u = (User)paramMap.get("user");
            Task t = this.getTask((String)paramMap.get("taskId"));
            ActivitiProcessApproval p =
                this.generateActivitiProcessApproval(paramMap, t, 0);
            t.setDescription((String)paramMap.get("opinion"));
            if (t.getFormURL().indexOf("?") != -1)
            {
                String url[] = t.getFormURL().split("\\?");
                t.setFormURL(url[0]);
            }
            t.setFormURL(t.getFormURL() + "?taskId=" + t.getId());
            paramMap.remove("user");
            paramMap.remove("taskId");
            paramMap.remove("opinion");
            if (paramMap != null && paramMap.size() > 0)
            {
                StringBuffer otherParam = new StringBuffer();
                for (Iterator it = paramMap.keySet().iterator(); it.hasNext();)
                {
                    String key = (String)it.next();
                    otherParam.append("&")
                        .append(key)
                        .append("=")
                        .append(paramMap.get(key));
                }
                t.setFormURL(t.getFormURL() + otherParam);
            }
            
            this.activitiAndBusinessDao.addEntity(p);
            if (StringUtils.isBlank(t.getAssignee()))
            {
                t.setAssignee(u.getUsername());
            }
            this.saveTask(t);
            this.endProcessInstance(t.getProcessInstanceId(), null);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    @Override
    public Map getProcessListenerShowData(String processInstanceId)
        throws ServiceException
    {
        Map map = new HashMap();
        try
        {
            List<String> opinions = new ArrayList<String>();
            Set<String> doneActivity = new HashSet();
            Set<String> doningActivity = new HashSet();
            List<ActivitiProcessInstance> processInstances =
                (List<ActivitiProcessInstance>)this.activitiAndBusinessDao.queryEntitys(
                    "from ActivitiProcessInstance"
                    + " where processInstanceId='"
                    + processInstanceId
                    + "' order by id");
            if (CollectionUtils.isEmpty(processInstances))
            {
                throw new ServiceException("无流程实例!");
            }
            map.put("processName", processInstances.get(0).getProcessName());
            List<ActivitiProcessApproval> processApprovals =
                (List<ActivitiProcessApproval>)this.activitiAndBusinessDao.queryEntitys(
                    "from ActivitiProcessApproval where processInstanceId='"
                    + processInstanceId + "' order by id");
            for (ActivitiProcessApproval p : processApprovals)
            {
            	if(StringUtils.isNotBlank(p.getOpinion())){
            		opinions.add(p.getOpinion());
            	}
            }
            map.put("opinions", opinions);
            String processStatus = "正在执行";
            HistoricProcessInstance pi =
                this.getHistoricProcessInstance(processInstanceId);
            List<HistoricTaskInstance> taskInstances =
                getHistoricTaskByProcessId(processInstanceId,null);
            for (HistoricTaskInstance instance : taskInstances)
            {
                doneActivity.add(instance.getTaskDefinitionKey());
            }
            if (pi == null)
            {
                throw new ServiceException("流程实例不存在");
            }
            if (pi.getEndTime() != null)
            {
                map.put("endStatus", true);
                processStatus = this.getProcessEndStatus(pi, taskInstances);
            }
            else
            {
                map.put("endStatus", false);
                doningActivity =
                    this.removeDoneActivityByDoingActivity(doneActivity,
                        processInstanceId);
            }
            
            List doneActivityTemp =
                this.removeDoingActivityByPreActivity(doneActivity,
                    processInstanceId,
                    doningActivity);
            map.put("activity", doningActivity);
            map.put("processStatus", processStatus);
            if (doneActivityTemp.size() > 0)
            {
                map.put("doneActivity", doneActivityTemp);
            }
            else
            {
                map.put("doneActivity", doneActivity);
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
        return map;
    }
    
    /**
     * 
     * @Title removeDoneActivityByDoingActiviti
     * @author zhxh
     * @Description:从已经执行的节点中移出正在执行的节点
     * @date 2014-1-9
     * @param doneActivity
     * @param processInstanceId
     */
    private Set<String> removeDoneActivityByDoingActivity(
        Set<String> doneActivity, String processInstanceId)
    {
        List<ActivityImpl> activitys =
            this.getActivitiImplByProcessInstanceId(processInstanceId);
        Set<String> set = new HashSet();
        for (ActivityImpl a : activitys)
        {
            set.add(a.getId());
        }
        doneActivity.removeAll(set);
        return set;
    }
    
    /**
     * 
     * @Title removeDoingActivityByPreActivity
     * @author zhxh
     * @Description:从已经执行的节点中移出正在执行的节点
     * @date 2014-1-9
     * @param doneActivity
     * @param processInstanceId
     */
    private List removeDoingActivityByPreActivity(Set<String> doneActivity,
        String processInstanceId, Set<String> doningActivity)
    {
        List doneActivityTemp = new ArrayList();
        for (String activityId : doningActivity)
        {
            List<ActivityImpl> pres = new ArrayList();
            this.getPreAllTaskAcitiviImpl(pres, activityId, processInstanceId);
            if (pres.size() > 0)
            {
                for (String done : doneActivity)
                {
                    for (ActivityImpl pre : pres)
                    {
                        if (done.equals(pre.getId()))
                        {
                            doneActivityTemp.add(done);
                        }
                    }
                }
                
            }
            else
            {
                doneActivity.removeAll(doneActivity);
            }
        }
        return doneActivityTemp;
    }
    
    /**
     * 
     * @Title getProcessEndStatus
     * @author zhxh
     * @Description: 获取已经结束流程的真实状态
     * @date 2014-1-8
     * @param pi
     * @return String
     * @throws IOException
     */
    private String getProcessEndStatus(HistoricProcessInstance pi,
        List<HistoricTaskInstance> taskInstances)
        throws IOException
    {
        String processStatus = "";
        InputStream in = null;
        if (pi.getEndActivityId() == null)// 非正常结束
        {
            processStatus = "未通过(终止)";
        }
        else
        // 正常结束
        {
            String formUrl = null;
            Map tempMap = new HashMap();
            processStatus = "未知";
            in =
                this.getActivitiXmlByProcessDefineId(taskInstances.get(0)
                    .getProcessDefinitionId());
            HistoricTaskInstance exaapproveTask =
                this.compareLastExaapprove(taskInstances, in);// 获取审批节点任务
            if (exaapproveTask != null)
            {// 寻找具有pass和非pass的节点
                processStatus =
                    this.getProcessStatusByFormUrl(exaapproveTask.getFormURL());
            }
        }
        if(in!=null){
        	in.close();
        }
        return processStatus;
    }
    
    /**
     * 
     * @Title getProcessStatusByFormUrl
     * @author zhxh
     * @Description: 通过Url获取流程状态
     * @date 2014-1-8
     * @param formUrl
     * @return String
     */
    public String getProcessStatusByFormUrl(String formUrl)
    {
        
        String processStatus = "未知";
        if (StringUtils.isNotBlank(formUrl))
        {
            String str[] = formUrl.split("\\?");
            if (str.length > 1)
            {
                if (str[1].indexOf("pass=true") != -1)
                {
                    processStatus = "通过";
                }
                else if (str[1].indexOf("pass=false") != -1)
                {
                    processStatus = "未通过";
                }
            }
        }
        return processStatus;
    }
    
    /**
     * 
     * @Title compareLastExaapprove
     * @author zhxh
     * @Description:获取最后一次审批节点
     * @date 2014-1-8
     * @param taskInstances
     * @param in
     * @return HistoricTaskInstance
     * @throws IOException 
     */
    private HistoricTaskInstance compareLastExaapprove(
        List<HistoricTaskInstance> taskInstances, InputStream in) 
            throws IOException
    {
        HistoricTaskInstance exaapproveTask = null;
        for (HistoricTaskInstance ht : taskInstances)
        {
            in.reset();
            Map result = this.findXmlParam(in, ht.getTaskDefinitionKey());
            // 获取最后一次审批任务
            if ("bpm_exa_approve".equals((String)result.get("taskType")))// 审批节点
            {
                if (exaapproveTask == null && ht.getEndTime() != null)
                {
                    exaapproveTask = ht;
                }
                else if (ht != null && ht.getEndTime() != null
                    && ht.getEndTime().after(exaapproveTask.getEndTime()))
                {
                    exaapproveTask = ht;
                }
                
            }
        }
        return exaapproveTask;
    }
    
    /**
     * 获取流程节点数据
     * 
     * @author 张小虎
     * @param processInstanceId
     * @param taskDefineKey
     * @return Map
     */
    @Override
    public Map getProcessUserData(final String processInstanceId,
        final String taskDefineKey)
        throws ServiceException
    {
        Map map = new HashMap();
        map.put("executeUser", "");
        map.put("executeRoleName", "");
        HistoricProcessInstance pi = this.getHistoricProcessInstance(processInstanceId);
        if (pi == null)
        {
            throw new ServiceException("流程实例不存在");
        }
        if(pi.getEndTime()!=null){
        	return map;
        }
        Map xmlMap =
            this.findXmlParam(this.getProcessXmlResource(processInstanceId),
                taskDefineKey);
        List<ActivityImpl> doingActs =
            this.getActivitiImplByProcessInstanceId(processInstanceId);// 获取当前正在处理的活动节点
        
        Set<String> taskDefineKeySet = new HashSet<String>();
        
        List<ActivityImpl> pres = new ArrayList();
        
        String userLoginNames = (String)xmlMap.get("user");
        
        String roleIds = (String)xmlMap.get("roleIds");
        for (ActivityImpl act : doingActs)
        {
            taskDefineKeySet.add(act.getId());
        }
        if(taskDefineKeySet.contains(taskDefineKey))//正在执行的包括该节点
        {
            //直接获取任务上的人员
         this.getUserNameOrRoleNameByKey(map,processInstanceId,
             taskDefineKey,roleIds);
            
        }
        else
        {
          
            for (ActivityImpl act : doingActs)
            {
                this.getPreAllTaskAcitiviImpl(pres, act.getId(), processInstanceId);
            }
            Set<String> preTaskDefineList = new HashSet<String>();
            for (ActivityImpl act : pres)
            {
                preTaskDefineList.add(act.getId());//获取正在执行的所有前置节点
            }
            if(preTaskDefineList.contains(taskDefineKey))//属于前置节点中
            {
              getUserNameByNearHisTask(map,processInstanceId,
                   taskDefineKey,userLoginNames,roleIds);
              
            }
            else
            {
             this.getUserAndRole(map, userLoginNames, roleIds);
            }
        }
        
        
        return map;
}
/**
 * 
* @Title getUserNameByNearHisTask
* @author zhxh
* @Description:获取用户靠最近的历史任务 
* @date 2014-1-9
* @param map
* @param processInstanceId
* @param taskDefineKey
 * @param taskType 
 */
private void getUserNameByNearHisTask(Map map, String processInstanceId,
        String taskDefineKey,String userIds,String roleIds)
    {
    List<HistoricTaskInstance> hisTasks = this.getHistoricTaskByProcessId(
                           processInstanceId,taskDefineKey);
    if(CollectionUtils.isEmpty(hisTasks))//没有执行过
     {
        this.getUserAndRole(map,userIds,roleIds);
     }
    else
    {
       if(StringUtils.isNotBlank(hisTasks.get(0).getAssignee()))
       {
           List<User> users = this.getUserListByLoginNames
                (hisTasks.get(0).getAssignee());
           if(!CollectionUtils.isEmpty(users))
           {
               map.put("executeUser", users.get(0).getRealname()); 
           }
       }
    }
  }
/**
 * 
* @Title getUserAndRole
* @author zhxh
* @Description: 获取用户和角色
* @date 2014-1-9
* @param map
* @param userIds
* @param roleIds
 */
private void getUserAndRole(Map map, String userIds, String roleIds)
{
    StringBuffer userNames = new StringBuffer();
    if(StringUtils.isNotBlank(userIds))
    {
     List<User> users =  this.getUserListByLoginNames(userIds);
     for(User user:users)
     {
        userNames.append(user.getRealname()).append(",");   
     }
     if(userNames.length()>0)
     {
     map.put("executeUser", userNames.substring(0,userNames.length() - 1));
     }
    }
    if (StringUtils.isNotBlank(roleIds))
    {
        List<Role> roles = this.getRoleForBpmById(roleIds);
        StringBuffer roleSB = new StringBuffer(0);
        for (Role r : roles)
        {
            roleSB.append(r.getRoleName()).append(",");
        }
        if (roleSB.length() > 0)
        {
            map.put("executeRoleName",
                roleSB.substring(0, roleSB.length() - 1));
        }
    }
    
}

/**
 * 
* @Title getUserNameOrRoleNameByKey
* @author zhxh
* @Description: 获取用户和角色通过taskDefinekey
* @date 2014-1-9
* @param map
* @param processInstanceId
* @param taskDefineKey
 */
    private void getUserNameOrRoleNameByKey(
     Map map, String processInstanceId, String taskDefineKey,String roleIds)
    {  
        List<Task> tasks = this.getToDoTaskListByTaskDefineKey(null, 
            taskDefineKey, processInstanceId);
        StringBuffer userIds = new StringBuffer();
        StringBuffer userNames = new StringBuffer();
         for (Task task: tasks)
         {
            if(StringUtils.isNotBlank(task.getAssignee()))
            {
            userIds.append("'").append(task.getAssignee()).append("'").append(",");
            }
         }
         if(StringUtils.isNotBlank(userIds.toString()))
          {
            List<User> users =
              (List<User>)this.activitiAndBusinessDao.getUserListByLoginNames
              (userIds.substring(0,userIds.length() - 1));
           for(User user:users)
           {
              userNames.append(user.getRealname()).append(",");   
           }
           map.put("executeUser", userNames.substring(0,userNames.length() - 1));
         }
         else
         {
             if (StringUtils.isNotBlank(roleIds))
             {
                 List<Role> roles = this.getRoleForBpmById(roleIds);
                 StringBuffer roleSB = new StringBuffer(0);
                 for (Role r : roles)
                 {
                     roleSB.append(r.getRoleName()).append(",");
                 }
                 if (roleSB.length() > 0)
                 {
                     map.put("executeRoleName",
                         roleSB.substring(0, roleSB.length() - 1));
                 }
             }
         }
    }


    
    /**
     * 分页查询流程实例
     * 
     * @param paramMap
     * @return ListVo
     */
    @Override
    public ListVo<ActivitiProcessInstanceVo> getProcessInstanceByPage(
        Map paramMap)
        throws ServiceException
    {
        try
        {
            ListVo<ActivitiProcessInstanceVo> listVo =
                new ListVo<ActivitiProcessInstanceVo>();
            List<ActivitiProcessInstanceVo> vos = new ArrayList();
            
            ListVo<Object[]> tempListVo =
                this.activitiAndBusinessDao.getProcessInstanceByPage(paramMap);
            listVo.setList(vos);
            listVo.setTotalSize(tempListVo.getTotalSize());
            for (Object object[] : tempListVo.getList())
            {
                ActivitiProcessInstanceVo vo = new ActivitiProcessInstanceVo();
                vo.setProcessStatus(0);
                vo.setProcessInstanceId((String)object[0]);
                vo.setProcessName((String)object[1]);
                vo.setUserName((String)object[2]);
                vo.setUserId((String)object[3]);
                vo.setStartTime((String)object[4]);
                vo.setDeleteReason((String)object[7]);
                if (object[5] != null)
                {
                    vo.setEndTime((String)object[5]);
                    if (object[6] != null)
                    {
                        vo.setProcessStatus(1);
                    }
                    else
                    {
                        vo.setProcessStatus(2);
                    }
                }
                if (object[8] != null)
                {
                    vo.setDurationTime(this.convertTimeToChinese((Long.parseLong(object[8]+""))));
                }
                vo.setProcessType((String)object[9]);
                vo.setProcessCode((String)object[10]);
                vos.add(vo);
            }
            return listVo;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteProcessInstance(String processInstanceIds)
        throws ServiceException
    {
        try
        {
            String ids[] = processInstanceIds.split(",");
            for (final String processInstanceId : ids)
            {
                if (processInstanceId != null
                    && !"".equals(processInstanceId.trim()))
                {
                    HistoricProcessInstance hi =
                        this.getHistoricProcessInstance(processInstanceId);
                    ProcessDefinition pf =
                        this.getProcessDefinitionById(hi.getProcessDefinitionId());
                    this.deleteDeployMent(pf.getDeploymentId(), true);
                }
            }
            this.deleteActivitiProcessApproval(processInstanceIds);
        }
        catch (Exception e)
        {
            throw new ServiceException("数据删除失败！", e);
        }
    }
    
    /**
     * 修改流程实例
     * 
     * @param xmlContent
     * @param processInstanceId
     * @return void
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateProcessInstance(String xmlContent,
        String processInstanceId)
        throws ServiceException
    {
        HistoricProcessInstance hi =
            this.getHistoricProcessInstance(processInstanceId);
        ProcessDefinition pf =
            this.getProcessDefinitionById(hi.getProcessDefinitionId());
        this.activitiAndBusinessDao.updateProcessInstance(xmlContent,
            pf.getDeploymentId());
        
    }
    
    /**
     * 获取用户列表，为流程在线设计器
     * 
     * @author 张小虎
     * @date 2011-3-25
     * @param paramMap 参数map
     * @throws ServiceException
     * @return ListVo<User>
     */
    @Override
    public ListVo<User> getUserListForFlow(Map<String, Object> paramMap)
    {
        try
        {
            return this.activitiAndBusinessDao.getUserListForFlow(paramMap);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }
    
    /**
     * 为流程图获取用户展示
     * 
     * @param substring
     * @return
     */
    @Override
    public List<User> getUserListByLoginNames(String loginNames)
    {
        StringBuffer loginNameBuffer = new StringBuffer();
        for (String str : loginNames.split(","))
        {
            loginNameBuffer.append("'").append(str).append("'").append(",");
        }
        List<User> users =
            this.activitiAndBusinessDao.getUserListByLoginNames(loginNameBuffer.substring(0,
                loginNameBuffer.length() - 1));
        
        return users;
    }
    
    /**
     * 获取组织树
     * 
     * @param nodeId
     */
    @Override
    public List<Organization> getOrgTreeForBpm(String nodeId)
    {
        Map map = new HashMap();
        map.put("enable", Constant.ENABLE);
        map.put("status", Constant.STATUS_NOT_DELETE);
        StringBuffer hql =
            new StringBuffer(
                "from Organization where enable=:enable and status=:status");
        if (StringUtils.isBlank(nodeId) || "0".equals(nodeId))
        {
            map.put("organizationId", 0L);
            hql.append(" and (organization is null or organization.id=:organizationId)");
            
        }
        else
        {
            map.put("organizationId", Long.parseLong(nodeId));
            hql.append(" and  organization.id=:organizationId ");
        }
        hql.append(" order by orgId desc");
        List<Organization> orgs =
            this.activitiAndBusinessDao.queryEntitysByMap(hql.toString(), map);
        return orgs;
    }
    
    /**
     * 获取角色
     * 
     * @param paramMap
     * @return
     */
    @Override
    public ListVo<Role> getRoleForBpmByPage(Map paramMap)
    {
        ListVo<Role> roleListVo =
            this.activitiAndBusinessDao.getRoleForBpmByPage(paramMap);
        return roleListVo;
    }
    
    /**
     * 通过角色ID，用逗号分割查询
     * 
     * @param roleIds
     * @return List<Role>
     */
    @Override
    public List<Role> getRoleForBpmById(String roleIds)
    {
        
        String hql =
            "from Role where  isDelete=0 and roleId in(" + roleIds + ")";
        return this.activitiAndBusinessDao.queryEntitys(hql);
    }
    
    /**
     * 通过角色Id以及bpmType获取角色单位下的人员
     * 
     * @param roleIds
     * @param bpmType
     * @return loginNames
     */
    @Override
    public List<String> getUserByRoleIdsAndCategoryId(String roleIds,
        int categoryId)
    {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("roleId", roleIds);
		paramsMap.put("start", "0");
		paramsMap.put("limit", String.valueOf(Integer.MAX_VALUE));
		List<String> loginNames=null;
		try {
			ListVo<UserVo> vos = this.roleService.getRoleMemberByUser(paramsMap);
			loginNames = new ArrayList();
	        for (UserVo u : vos.getList())
	        {
	            if (StringUtils.isNotBlank(u.getUsername())
	                && !loginNames.contains(u.getUsername()))
	            {
	                loginNames.add(u.getUsername());
	            }
	        }
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return loginNames;
    }
    
    /**
     * 
     * @Title getActivitiXmlByTaskDefineId
     * @author zhxh
     * @Description: 通过流程定义Id获取xml
     * @date 2013-12-24
     * @param processDefinitionId
     * @return InputStream
     */
    @Override
    public InputStream getActivitiXmlByProcessDefineId(
        final String processDefinitionId)
    {
        InputStream in =
            (InputStream)this.executeActiviti(new IActivitiCallBack()
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
    
    /**
     * 
     * @Title getHistoricTaskByProcessId
     * @author zhxh
     * @Description: 通过流程实例Id获取历史任务
     * @date 2013-12-24
     * @param processInstanceId
     * @return List<HistoricTaskInstance>
     */
    private List<HistoricTaskInstance> getHistoricTaskByProcessId(
        final String processInstanceId,final String taskDefineKey)
    {
        return (List<HistoricTaskInstance>)this.executeActiviti(new IActivitiCallBack()
        {
            @Override
            public Object doActiviti(TaskService taskService,
                RuntimeService runtimeService,
                RepositoryService repositoryService,
                HistoryService historyService, IdentityService identityService)
            {
               
                    HistoricTaskInstanceQuery query = historyService
                    .createHistoricTaskInstanceQuery()
                        .finished();
                   if(StringUtils.isNotBlank(taskDefineKey))
                   {
                       query.taskDefinitionKey(taskDefineKey);
                   }
                   if(StringUtils.isNotBlank(processInstanceId))
                   {
                       query.processInstanceId(processInstanceId);
                   }
                return query.orderByHistoricTaskInstanceEndTime().desc().list();
            }
        });
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void commitApplyTask(Map<String, Object> paramMap,
        String processInstanceId, User user)
    {
        User u = user;
        List<Task> tasks =
            this.getToDoTaskList(SystemConstant.PROCESS_AUTO_LOGINNAME,//内置的流程申请节点人员
                processInstanceId);
        for (Task t : tasks)
        {
            Map xmlMap =
                this.findXmlParam(this.getProcessXmlResource(processInstanceId),
                    t.getTaskDefinitionKey());
            String taskType = (String)xmlMap.get("taskType");
            if (!"bpm_apply".equals(taskType))
            {
                continue;
            }
            paramMap.put("user", user);
            paramMap.put("opinion", u.getRealname() + ",发起申请任务");
            ActivitiProcessApproval p =
                this.generateActivitiProcessApproval(paramMap, t, 2);
            this.activitiAndBusinessDao.addActivitiProcessApproval(p);
            t.setDescription(u.getRealname() + ",发起申请任务");
            paramMap.remove("opinion");
            paramMap.remove("user");
            paramMap.remove("eSignature");
            paramMap.remove("eUrl");
            StringBuffer otherParam = new StringBuffer();
            t.setFormURL(t.getFormURL() + "?taskId=" + t.getId());
            for (Iterator it = paramMap.keySet().iterator(); it.hasNext();)
            {
                Object obj = it.next();
                if (obj != null)
                {
                    String key = String.valueOf(obj);
                    otherParam.append("&")
                        .append(key)
                        .append("=")
                        .append(paramMap.get(key));
                }
            }
            t.setFormURL(t.getFormURL() + otherParam);
            t.setAssignee(u.getUsername());
            this.saveTask(t);
            Map commitMap = new HashMap();
            
            //设置业务判断条件，如请假时长（小于3天和大于3天分别走不同的流程），x的名称须与流程模板设置的对应
              //commitMap.put("x",4);
            this.completeTask(t.getId(), commitMap);
            
        }
    }
    
    /**
     * 
     * @Title generateActivitiProcessApproval
     * @author zhxh
     * @Description:为提交任务产生意见
     * @date 2013-12-30
     * @param paramMap
     * @return ActivitiProcessApproval
     */
    @Override
    public ActivitiProcessApproval generateActivitiProcessApproval(
        Map paramMap, Task task, int approval)
    {
        ActivitiProcessApproval p = new ActivitiProcessApproval();
        User u = (User)paramMap.get("user");
        Byte[] bytes = (Byte[])paramMap.get("eSignature");
        String eUrl = (String)paramMap.get("eUrl");
        p.setApproval(approval);
        p.setIsDelete(0);
        p.setDealUser(u);
        p.setTaskId(task.getId());
        p.setOpinion((String)paramMap.get("opinion"));
        p.setProcessInstanceId(task.getProcessInstanceId());
        if (bytes != null && bytes.length > 0)
        {
            p.seteSignature(bytes);
            p.seteSource(1);
        }
        else if (StringUtils.isNotBlank(eUrl))
        {
            p.seteUrl(eUrl);
            p.seteSource(1);
        }
        else
        {
            p.seteSource(0);
        }
        return p;
    }
    
    @Override
    public void addActivitiProcessApproval(
        ActivitiProcessApproval processApproval)
    {
        this.activitiAndBusinessDao.addActivitiProcessApproval(processApproval);
        
    }
    
    @Override
    public void deleteActivitiProcessApproval(String processInstanceIds)
    {
        String ids[] = processInstanceIds.split(",");
        StringBuffer processInstBf = new StringBuffer();
        for (String processInstanceId : ids)
        {
            if (StringUtils.isNotBlank(processInstanceId))
            {
                processInstBf.append("'")
                    .append(processInstanceId)
                    .append("',");
            }
        }
        if (processInstBf.length() > 0)
        {
            this.activitiAndBusinessDao.deleteActivitiProcessApproval(processInstBf.substring(0,
                processInstBf.length() - 1));
        }
        
    }
    
    /**
     * 
     * @Title convertTimeToChinese
     * @author zhxh
     * @Description:转化时间
     * @date 2013-12-30
     * @return String
     */
    @Override
    public String convertTimeToChinese(long longTime)
    {
        String str = "";
        long day = longTime / 86400000;
        long hour = (longTime % 86400000) / 3600000;
        long minute = (longTime % 86400000 % 3600000) / 60000;
        if (day > 0)
        {
            str = String.valueOf(day) + "天";
        }
        if (hour > 0)
        {
            str += String.valueOf(hour) + "小时";
        }
        if (minute > 0)
        {
            str += String.valueOf(minute) + "分钟";
        }
        if (("").equals(str))
        {
            str = longTime / 1000 + "秒";
        }
        return str;
    }
    
}
