/**
 * @文件名 ActivitiTaskServiceImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述  流程任务的逻辑实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dqgb.sshframe.bpm.dao.IActivitiTaskDao;
import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.bpm.entity.ActivitiDelegate;
import com.dqgb.sshframe.bpm.entity.ActivitiProcessApproval;
import com.dqgb.sshframe.bpm.entity.ActivitiTaskRole;
import com.dqgb.sshframe.bpm.service.IActivitiAndBusinessService;
import com.dqgb.sshframe.bpm.service.IActivitiCategoryService;
import com.dqgb.sshframe.bpm.service.IActivitiDelegateService;
import com.dqgb.sshframe.bpm.service.IActivitiTaskService;
import com.dqgb.sshframe.bpm.vo.ActivitiProcessInstanceVo;
import com.dqgb.sshframe.bpm.vo.TaskVo;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.exception.ServiceException;
/*import com.dqgb.sshframe.common.mail.Email;
import com.dqgb.sshframe.common.mail.MailService;
import com.dqgb.sshframe.common.mail.MailServiceImpl;*/
import com.dqgb.sshframe.common.util.DateUtil;
import com.dqgb.sshframe.common.util.PropertyUtil;
import com.dqgb.sshframe.common.util.RequestUtil;
import com.dqgb.sshframe.common.util.StringUtil;
import com.dqgb.sshframe.common.util.XMLUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.dict.service.IDictService;
import com.dqgb.sshframe.log.service.ILogService;
import com.dqgb.sshframe.role.entity.Role;
import com.dqgb.sshframe.user.entity.User;

/**
 * 
 * 流程任务的逻辑实现
 * 
 * @author zhxh
 * @version V1.20,2013-12-28 下午1:16:31
 * @since V1.20
 * @depricated
 */
@Transactional(readOnly = true)
@Service("activitiTaskService")
@SuppressWarnings({"rawtypes", "unchecked"})
public class ActivitiTaskServiceImpl implements IActivitiTaskService {
    /** 日志 */
    private Log log = LogFactory.getLog(ActivitiTaskServiceImpl.class);
    
    @Autowired
    @Qualifier("activitiTaskDao")
    /**流程任务持久层接口*/
    private IActivitiTaskDao activitiTaskDao;
    
    /** 流程种类接口 */
    @Autowired
    @Qualifier("activitiCategoryService")
    private IActivitiCategoryService activitiCategoryService;
    
    /** 流程业务接口 */
    @Autowired
    @Qualifier("activitiAndBusinessService")
    private IActivitiAndBusinessService activitiAndBusinessService;
    
    /** 委托接口 */
    @Autowired
    @Qualifier("activitiDelegateService")
    private IActivitiDelegateService activitiDelegateService;
    
    @Resource
    private IDictService dictService;
    
    /**
     * @Fields roleService : 日志服务
     */
    @Autowired(required = true)
    @Qualifier("logService")
    private ILogService logService;
    /*
    *//**
     * 邮件服务 yzh
     *//*
    private Email email;
    
    *//**
     * service yzh
     *//*
    private MailService mailService = new MailServiceImpl();
    
    *//**
     * @return the email
     *//*
    public Email getEmail() {
        return email;
    }
    
    *//**
     * @param email the email to set
     *//*
    public void setEmail(Email email) {
        this.email = email;
    }*/
    
    /**
     * @return the dictService
     */
    public IDictService getDictService() {
        return dictService;
    }
    
    /**
     * @param dictService the dictService to set
     */
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
    
    /**
     * @Description: Log服务
     * @param @param logService
     * @return 返回类型
     * @author yzh
     * @date 2014-8-21 上午10:28:05
     */
    public void setLogService(ILogService logService) {
        this.logService = logService;
    }
    
    /**
     * @Description: Log服务
     * @param @return
     * @return 返回类型
     * @author yzh
     * @date 2014-8-21 上午10:28:17
     */
    public ILogService getLogService() {
        return logService;
    }
    
    @Override
    public ListVo<TaskVo> getDoneTaskListByPage(Map paramMap)
        throws ServiceException {
        ListVo<Object[]> listVo =
            this.activitiTaskDao.getDoneTaskListByPage(paramMap);
        List<TaskVo> resultList = new ArrayList<TaskVo>();
        ListVo<TaskVo> returnListVo = new ListVo<TaskVo>();
        returnListVo.setList(resultList);
        returnListVo.setTotalSize(listVo.getTotalSize());
        StringBuffer categoryIds = new StringBuffer();
        for (Object object[] : listVo.getList()) {
            TaskVo vo = new TaskVo();
            vo.setTaskId((String)object[0]);
            vo.setTaskDefinitionKey((String)object[1]);
            vo.setProcessInstanceId((String)object[2]);
            vo.setFormUrl((String)object[3]);
            vo.setName((String)object[4]);
            vo.setCreateTime((String)object[5]);
            vo.setProcessName((String)object[9]);
            vo.setAssignee((String)object[10]);
            vo.setAssigneeName((String)object[11]);
            vo.setOwnerName((String)object[12]);
            vo.setOwner((String)object[13]);
            vo.setProcessType((String)object[14]);
            vo.setProcessCode((String)object[14]);
            vo.setDescription((String)object[18]);
            this.createTimeAndStatus(vo, object[6], object[8], object[7]);
            this.createBusinessId(vo, object[17]);
            if (object[16] != null) {
                vo.setCategoryId(Integer.parseInt(object[16] + ""));
                categoryIds.append(Integer.parseInt(object[16] + ""))
                    .append(",");
            }
            
            resultList.add(vo);
            
        }
        if (categoryIds.length() > 0) {
            this.getCategoryData(resultList,
                categoryIds.substring(0, categoryIds.length() - 1));
        }
        return returnListVo;
    }
    
    /**
     * 
     * @Title createTimeAndStatus
     * @author zhxh
     * @Description: 创建日期和状态
     * @date 2014-1-6
     * @param vo
     * @param endTime
     * @param status
     * @param durationTime
     */
    private void createTimeAndStatus(TaskVo vo, Object endTime, Object status,
        Object durationTime) {
        
        if (endTime != null) {
            vo.setEndTime((String)endTime);
            if (status != null && !"completed".equals((String)status)) {
                vo.setTaskStatus("2");
            } else {
                vo.setTaskStatus("1");
            }
        }
        if (durationTime != null && (Long.parseLong(durationTime + "")) != 0L) {
            vo.setDurationTime(this.activitiAndBusinessService.convertTimeToChinese((Long.parseLong(durationTime
                + ""))));
        }
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
    private void createBusinessId(TaskVo vo, Object businessIdObj) {
        if (businessIdObj != null) {
            String businessId = (String)businessIdObj;
            if (businessId.startsWith(",")) {
                businessId = businessId.substring(businessId.indexOf(",") + 1);
            }
            if (businessId.endsWith(",")) {
                businessId =
                    businessId.substring(0, businessId.lastIndexOf(","));
            }
            vo.setBusinessId(businessId);
        }
    }
    
    @Override
    public ListVo<TaskVo> getToDoTaskListByPage(Map paramMap)
        throws ServiceException {
        ListVo<Object[]> listVo =
            this.activitiTaskDao.getToDoTaskListByPage(paramMap);
        ListVo<TaskVo> returnListVo = new ListVo<TaskVo>();
        List<TaskVo> resultList = new ArrayList<TaskVo>();
        StringBuffer categoryIds = new StringBuffer();
        returnListVo.setTotalSize(listVo.getTotalSize());
        returnListVo.setList(resultList);
        for (Object object[] : listVo.getList()) {
            TaskVo vo = new TaskVo();
            vo.setTaskId((String)object[0]);
            vo.setTaskDefinitionKey((String)object[1]);
            vo.setProcessInstanceId((String)object[2]);
            vo.setFormUrl((String)object[3]);
            vo.setName((String)object[4]);
            vo.setCreateTime((String)object[5]);// (String)object[5]
            vo.setProcessName((String)object[6]);
            vo.setAssignee((String)object[7]);
            vo.setAssigneeName((String)object[8]);
            vo.setOwnerName((String)object[9]);
            vo.setOwner((String)object[10]);
            vo.setExecutionId((String)object[11]);
            vo.setProcessType((String)object[12]);
            vo.setProcessCode((String)object[13]);
            if (object[14] != null) {
                vo.setCategoryId(Integer.parseInt(object[14] + ""));
                categoryIds.append(Integer.parseInt(object[14] + ""))
                    .append(",");
            }
            this.createBusinessId(vo, object[15]);
            resultList.add(vo);
        }
        
        if (categoryIds.length() > 0) {
            this.getCategoryData(resultList,
                categoryIds.substring(0, categoryIds.length() - 1));
        }
        return returnListVo;
    }
    
    /**
     * 
     * @Title getCategoryData
     * @author zhxh
     * @Description:获取任务的种类数据
     * @date 2013-12-30
     */
    private void getCategoryData(List<TaskVo> resultList, String categoryIds) {
        if (categoryIds.length() > 0) {
            List<ActivitiCategory> categories =
                activitiCategoryService.getActivitiCategoryById(categoryIds);
            for (ActivitiCategory category : categories) {
                for (TaskVo taskVo : resultList) {
                    if (taskVo.getCategoryId() == category.getCategoryId()
                        .intValue() && category.getParentId() != 0) {
                        taskVo.setCategoryCode(category.getCode());
                        taskVo.setCategoryName(category.getName());
                        ActivitiCategory top =
                            this.activitiCategoryService.getActivitiTopCategory(category.getCategoryId());
                        if (top != null) {
                            taskVo.setTopCategoryCode(top.getCode());
                            taskVo.setTopCategoryId(top.getCategoryId());
                            taskVo.setTopCategoryName(top.getName());
                        }
                    } else if (taskVo.getCategoryId() == category.getCategoryId()
                        .intValue()
                        && category.getParentId() == 0) {
                        taskVo.setCategoryCode(category.getCode());
                        taskVo.setCategoryName(category.getName());
                        taskVo.setTopCategoryCode(category.getCode());
                        taskVo.setTopCategoryId(category.getCategoryId());
                        taskVo.setTopCategoryName(category.getName());
                    }
                }
            }
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Task updateTask(TaskVo vo) {
        Task task = this.activitiAndBusinessService.getTask(vo.getTaskId());
        task.setAssignee(vo.getAssignee());
        task.setFormURL(vo.getFormUrl());
        task.setName(vo.getName());
        this.activitiAndBusinessService.saveTask(task);
        this.activitiTaskDao.updateHistoryTask(vo);
        return task;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void moveTask(String taskId, String userId, User currentUser) {
        
        try {
            ActivitiProcessApproval p = new ActivitiProcessApproval();
            Task t = this.activitiAndBusinessService.getTask(taskId);
            p.setApproval(0);
            p.setIsDelete(0);
            p.setDealUser(currentUser);
            List<User> users =
                this.activitiAndBusinessService.getUserListByLoginNames(userId);
            if (users.size() < 1) {
                throw new ServiceException("移交人员不存在!");
            }
            User user = users.get(0);
            p.setOpinion("任务" + t.getName() + "移交给" + user.getRealname());
            p.setProcessInstanceId(t.getProcessInstanceId());
            p.setTaskId(t.getId());
            p.seteSource(0);
            this.activitiAndBusinessService.addActivitiProcessApproval(p);
            this.activitiAndBusinessService.moveTask(taskId, userId);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessageKey(), e);
        } catch (Exception e) {
            throw new ServiceException("移交失败!", e);
        }
        
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void rejectTaskForApply(Map paramMap, Map commitMap) {
        ActivitiProcessApproval processApproval = null;
        InputStream in = null;
        try {
            User u = (User)paramMap.get("user");
            Task task =
                this.activitiAndBusinessService.getTask((String)paramMap.get("taskId"));
            if (task == null) {
                throw new ServiceException("任务不存在!");
            }
            in =
                this.activitiAndBusinessService.getActivitiXmlByProcessDefineId(task.getProcessDefinitionId());
            List<Element> resultList = new ArrayList<Element>();
            Document doc = new SAXReader().read(in);
            resultList =
                XMLUtil.getElementList(doc, resultList, null, "userTask");
            String taskDefineKey = getBpmApplyTaskDefine(resultList);
            List<ActivityImpl> activityImpls =
                this.activitiAndBusinessService.getRollBackActivity((String)paramMap.get("taskId"));
            ActivityImpl a =
                this.getBpmApplyActivityImpl(activityImpls, taskDefineKey);
            if (a == null) {
                throw new ServiceException("回滚之前没有任何申请节点");
            }
            processApproval =
                this.activitiAndBusinessService.generateActivitiProcessApproval(paramMap,
                    task,
                    0);
            this.activitiAndBusinessService.addActivitiProcessApproval(processApproval);
            this.generateFormUrl(paramMap, task);
            if (StringUtils.isBlank(task.getAssignee())) {
                task.setAssignee(u.getUsername());
            }
            this.activitiAndBusinessService.saveTask(task);
            this.activitiAndBusinessService.rollbackTask(task.getId(),
                a.getId(),
                commitMap);
            List<ActivitiProcessInstanceVo> activitiProcessInstanceVos =
                this.activitiAndBusinessService.getActivitiProcessInstance(task.getProcessInstanceId());
            if (CollectionUtils.isEmpty(activitiProcessInstanceVos)) {
                throw new ServiceException("流程实例发起人为空!");
            } else {
                setTaskDelegate(task.getTaskDefinitionKey(),
                    task.getProcessInstanceId(),
                    activitiProcessInstanceVos.get(0).getUserId(),
                    SystemConstant.PROCESS_AUTO_LOGINNAME);
            }
            
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            this.closeInputStream(in);
        }
    }
    
    /**
     * 
     * @Title getBpmApplyActivityImpl
     * @author zhxh
     * @Description:通过申请节点的taskDefineKey，从任务活动节点中找到它
     * @date 2014-1-6
     * @param activityImpls
     * @param taskDefineKey
     * @return
     */
    private ActivityImpl getBpmApplyActivityImpl(
        List<ActivityImpl> activityImpls, String taskDefineKey) {
        ActivityImpl tempApply = null;
        for (ActivityImpl a : activityImpls) {
            if (a.getId().equals(taskDefineKey)) {
                tempApply = a;
            }
        }
        return tempApply;
    }
    
    /**
     * 
     * @Title getBpmApplyTaskDefine
     * @author zhxh
     * @Description:获取申请节点的任务定义key
     * @date 2014-1-6
     * @param resultList
     * @return String
     */
    private String getBpmApplyTaskDefine(List<Element> resultList) {
        String taskDefineKey = "";
        for (Element e : resultList) {
            if ("bpm_apply".equals(e.attributeValue("taskType"))) {
                taskDefineKey = e.attributeValue("id");
                break;
            }
        }
        if ("".equals(taskDefineKey)) {
           // throw new ServiceException("没有申请节点");
        }
        return taskDefineKey;
    }
    
    /**
     * 
     * @Title closeInputStream
     * @author zhxh
     * @Description:关闭输入流
     * @date 2014-1-6
     * @param in
     */
    private void closeInputStream(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                log.warn(e);
            }
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void setTaskDelegate(String taskDefineKey, String processInstanceId,
        String currentOwnerUserId, String taskUserId) {
        List<Task> tasks =
            this.activitiAndBusinessService.getToDoTaskListByTaskDefineKey(taskUserId,
                taskDefineKey,
                processInstanceId);
        // 设置委托
        for (Task t : tasks) {
            List<ActivitiDelegate> activitiDelegates =
                this.activitiDelegateService.getDelegateByUserIdStartTime(t.getCreateTime(),
                    currentOwnerUserId);
            if (activitiDelegates.size() > 0) {
                t.setOwner(currentOwnerUserId);
                t.setAssignee(activitiDelegates.get(0)
                    .getDelegateUser()
                    .getUsername());
            } else {
                t.setAssignee(currentOwnerUserId);
            }
            this.activitiAndBusinessService.saveTask(t);
        }
        
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteActivitiProcessApproval(String ids)
        throws ServiceException {
        this.activitiAndBusinessService.deleteActivitiProcessApproval(ids);
    }
    
    @Override
    public TaskVo getHistoryTask(String taskId) {
        HistoricTaskInstance h =
            this.activitiAndBusinessService.getHistoricTask(taskId);
        TaskVo vo = null;
        if (h != null) {
            vo = new TaskVo();
            vo.setDescription(h.getDescription());
            vo.setFormUrl(h.getFormURL());
            vo.setName(h.getName());
            vo.setOwner(h.getOwner());
            vo.setTaskId(h.getId());
            vo.setTaskDefinitionKey(h.getTaskDefinitionKey());
            vo.setProcessInstanceId(h.getProcessInstanceId());
            vo.setAssignee(h.getAssignee());
            if (h.getEndTime() != null) {
                vo.setEndTime(DateUtil.dateToString(h.getEndTime(),
                    SystemConstant.DATE_PATTEN_LONG));
                if (h.getDeleteReason() != null
                    && !"completed".equals(h.getDeleteReason())) {
                    vo.setTaskStatus("2");
                } else {
                    vo.setTaskStatus("1");
                }
            }
            
            if (h.getStartTime() != null) {
                vo.setCreateTime(DateUtil.dateToString(h.getStartTime(),
                    SystemConstant.DATE_PATTEN_LONG));
            }
            if (h.getDurationInMillis() != null) {
                vo.setDurationTime(this.activitiAndBusinessService.convertTimeToChinese(h.getDurationInMillis()));
            }
            List<ActivitiProcessInstanceVo> activitiProcessInstanceVos =
                this.activitiAndBusinessService.getActivitiProcessInstance(vo.getProcessInstanceId());
            if (activitiProcessInstanceVos.size() > 0) {
                vo.setCategoryId(activitiProcessInstanceVos.get(0)
                    .getCategoryId());
                vo.setCategoryCode(activitiProcessInstanceVos.get(0)
                    .getCategoryCode());
                vo.setCategoryName(activitiProcessInstanceVos.get(0)
                    .getCategoryName());
                ActivitiCategory top =
                    this.activitiCategoryService.getActivitiTopCategory(activitiProcessInstanceVos.get(0)
                        .getCategoryId());
                if (top != null) {
                    vo.setTopCategoryCode(top.getCode());
                    vo.setTopCategoryId(top.getCategoryId());
                    vo.setTopCategoryName(top.getName());
                }
            }
        }
        
        return vo;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Map<String,String> completeTask(Map paramMap, Map commitMap)
        throws ServiceException {
        InputStream in = null;
        Map<String,String> result =null;
        try {
            ActivitiProcessApproval processApproval = new ActivitiProcessApproval();
            // 是否为审批节点
            Task task = this.activitiAndBusinessService.getTask((String)paramMap.get("taskId"));
            
            if (task == null) {
                throw new ServiceException("任务不存在");
            }
            in = this.activitiAndBusinessService.getProcessXmlResource(task.getProcessInstanceId());
           
            result =  this.checkExaapproveNode(in, task,  (String)paramMap.get("pass"), processApproval);
            task.setDescription((String)paramMap.get("opinion"));
            processApproval = this.activitiAndBusinessService.generateActivitiProcessApproval(paramMap,task,2);
            
            Object userObj = paramMap.get("user");
            if (task == null) {
                throw new ServiceException("任务不存在");
            }
            // 人员为空设置人员
            if (StringUtils.isBlank(task.getAssignee()) && userObj !=null) {
                task.setAssignee(((User)userObj).getUsername());
            }
            
            this.generateFormUrl(paramMap, task);
            this.activitiAndBusinessService.saveTask(task);
            this.checkAssigneeUserMap(commitMap);
            this.activitiAndBusinessService.completeTask(task.getId(), commitMap);
            this.activitiAndBusinessService.addActivitiProcessApproval(processApproval);
            
            if("false".equals((String)paramMap.get("pass"))){
         	   result.put("finalNode", "true");
            }else{
	            HistoricProcessInstance pi = activitiAndBusinessService.getHistoricProcessInstance(task.getProcessInstanceId());
	            if(pi !=null && pi.getEndTime() !=null){
	            	result.put("finalNode", "true");
	            }else{
	            	result.put("finalNode", "false");
	            }
            }
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessageKey(), e);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            this.closeInputStream(in);
        }
        return result;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String getProcessParam(String taskId)
        throws ServiceException {
        InputStream in = null;
        String result ="";
        try {
            // 是否为审批节点
            Task task = this.activitiAndBusinessService.getTask(taskId);
            
            in = this.activitiAndBusinessService.getProcessXmlResource(task.getProcessInstanceId());
           
            Map<String,Object> res = this.activitiAndBusinessService.findXmlParam(in,task.getTaskDefinitionKey());
            Object ob = res.get("param");
            
            if (ob != null) {
                result =  (String)res.get("param");
            }
           return result;
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessageKey(), e);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            this.closeInputStream(in);
        }
    }
    
    /**
     * 
     * @Title checkAssigneeUserMap
     * @author zhxh
     * @Description: 检查提交的是否需要去掉动态设置人员
     * @date 2014-1-6
     * @param commitMap
     */
    private void checkAssigneeUserMap(Map commitMap) {
        if (commitMap == null) {
            commitMap = new HashMap();
        }
        if (commitMap.get("assigneeUser") == null) {
            commitMap.put("assigneeUser", null);
        }
        if (commitMap.get("assigneeList") == null) {
            commitMap.put("assigneeList", null);
        }
    }
    
    /**
     * 
     * @Title checkExaapproveNode
     * @author zhxh
     * @Description: 检查是否是审批节点,并设置意见
     * @date 2014-1-6
     * @param in
     * @param task
     * @param pass
     * @param processApproval
     * @return boolean
     */
    private Map<String,String> checkExaapproveNode(InputStream in, Task task, String pass,
        ActivitiProcessApproval processApproval) {
    	Map<String,String>  resultmap =  new HashMap<String,String>();
        Map result = this.activitiAndBusinessService.findXmlParam(in,task.getTaskDefinitionKey());
        resultmap.put("param", (String)result.get("param"));
        if (StringUtils.equalsIgnoreCase("bpm_exa_approve",
            (String)result.get("taskType"))) {
            
            if (pass == null) {
                throw new ServiceException("paramMap中不存在pass参数!");
            }
            if (!"false".equals(pass) && !"true".equals(pass)) {
                throw new ServiceException("paramMap中不存在pass参数的值不是true或者false");
            } else if ("false".equals(pass)) {
                processApproval.setApproval(0);
            } else if ("true".equals(pass)) {
                processApproval.setApproval(1);
            }
        }
        return resultmap;
    }
    
    /**
     * 驳回任务
     * 
     * @author 张小虎
     * @date 2011-5-24
     * @param paramMap
     * @param commitMap
     * @throws ServiceException
     * @return void
     */
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void rejectTask(Map paramMap, Map commitMap,
        String rejeckToTaskDefineKey)
        throws ServiceException {
        InputStream in = null;
        try {
            Task task =
                this.activitiAndBusinessService.getTask((String)paramMap.get("taskId"));
            User u = (User)paramMap.get("user");
            if (task == null) {
                throw new ServiceException("任务不存在!");
            }
            if (commitMap == null) {
                commitMap = new HashMap();
            }
            this.checkRejectAssigneeUser(task, commitMap, rejeckToTaskDefineKey);
            ActivitiProcessApproval processApproval =
                this.activitiAndBusinessService.generateActivitiProcessApproval(paramMap,
                    task,
                    0);
            task.setDescription((String)paramMap.get("opinion"));
            this.generateFormUrl(paramMap, task);
            
            if (StringUtils.isBlank(task.getAssignee())) {
                task.setAssignee(u.getUsername());
            }
            this.activitiAndBusinessService.saveTask(task);
            this.activitiAndBusinessService.rollbackTask(task.getId(),
                rejeckToTaskDefineKey,
                commitMap);
            
            this.activitiAndBusinessService.addActivitiProcessApproval(processApproval);
            // 判断rejeckToTaskDefineKey是不是申请
            in =
                this.activitiAndBusinessService.getActivitiXmlByProcessDefineId(task.getProcessDefinitionId());
            List<Element> resultList = new ArrayList<Element>();
            Document doc = new SAXReader().read(in);
            resultList =
                XMLUtil.getElementList(doc, resultList, null, "userTask");
            
            String taskDefineKey = getBpmApplyTaskDefine(resultList);
            if (rejeckToTaskDefineKey.equals(taskDefineKey)) {
                List<ActivitiProcessInstanceVo> activitiProcessInstanceVos =
                    this.activitiAndBusinessService.getActivitiProcessInstance(task.getProcessInstanceId());
                if (CollectionUtils.isEmpty(activitiProcessInstanceVos)) {
                    throw new ServiceException("流程实例发起人为空!");
                } else {
                    setTaskDelegate(rejeckToTaskDefineKey,
                        task.getProcessInstanceId(),
                        activitiProcessInstanceVos.get(0).getUserId(),
                        SystemConstant.PROCESS_AUTO_LOGINNAME);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    /**
     * 
     * @Title checkRejectAssigneeUser
     * @author zhxh
     * @Description: 检查驳回的时候动态分配人员参数设置
     * @date 2014-1-6
     * @param task
     * @param commitMap
     */
    private void checkRejectAssigneeUser(Task task, Map commitMap,
        String rejeckToTaskDefineKey) {
        // 获取驳回节点最近历史任务人员
        List<HistoricTaskInstance> hInstances =
            this.activitiAndBusinessService.getHistoryTaskByPIdAndTaskDef(task.getProcessInstanceId(),
                rejeckToTaskDefineKey);
        if (CollectionUtils.isEmpty(hInstances)) {
            throw new ServiceException("不存在已完成的驳回节点!");
        }
        HistoricTaskInstance nearHInstance = hInstances.get(0);
        if (nearHInstance.getOwner() != null) {
            commitMap.put("assigneeUser", nearHInstance.getOwner());
        } else if (nearHInstance.getAssignee() != null) {
            commitMap.put("assigneeUser", nearHInstance.getAssignee());
        } else {
            commitMap.put("assigneeUser", null);
        }
        
    }
    
    /**
     * 保存任务和角色关系
     * 
     * @param taskId
     * @param roleIds
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addActivitiTaskRole(String taskId, String roleIds) {
        try {
            List<ActivitiTaskRole> activitiTaskRoles = new ArrayList();
            List<Role> roles =
                this.activitiAndBusinessService.getRoleForBpmById(roleIds);
            if (roles.size() < 1 || roles.size() != roleIds.split(",").length) {
                throw new ServiceException("角色信息不存在!");
            } else {
                for (Role role : roles) {
                    ActivitiTaskRole t = new ActivitiTaskRole();
                    t.setTaskId(taskId);
                    t.setRole(role);
                    activitiTaskRoles.add(t);
                }
            }
            this.activitiTaskDao.addActivitiTaskRole(activitiTaskRoles);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessageKey(), e);
        } catch (Exception e) {
            throw new ServiceException("保存任务角色信息失败!", e);
        }
        
    }
    
    /**
     * 
     * @Title generateActivitiProcessApproval
     * @author zhxh
     * @Description:为提交任务产生表单地址
     * @date 2013-12-30
     * @param paramMap
     * @return ActivitiProcessApproval
     */
    private void generateFormUrl(Map paramMap, Task task) {
        paramMap.remove("businessId");
        paramMap.remove("user");
        paramMap.remove("opinion");
        paramMap.remove("eSignature");
        paramMap.remove("eUrl");
        paramMap.remove("taskId");
        if (task.getFormURL().indexOf("?") != -1) {
            String url[] = task.getFormURL().split("\\?");
            task.setFormURL(url[0]);
        }
        task.setFormURL(task.getFormURL() + "?taskId=" + task.getId());
        
        if (paramMap != null && paramMap.size() > 0) {
            StringBuffer otherParam = new StringBuffer();
            for (Iterator it = paramMap.keySet().iterator(); it.hasNext();) {
                String key = (String)it.next();
                otherParam.append("&")
                    .append(key)
                    .append("=")
                    .append(paramMap.get(key));
            }
            task.setFormURL(task.getFormURL() + otherParam);
        }
        
    }
    
    @Override
    public String sendEmailToCurrentCheckUser(String processInstanceId,
        String password) {/*
        {
            String msg = "{success:false,msg:'邮件发送失败，中油邮箱服务获取错误！'}";
            String sql =
                "select u.username, u.password, u.email, s.process_name ,u.user_id"
                    + " from t_user u, act_ru_task a, sys_activiti_process_inst s "
                    + " where u.username = a.assignee_ "
                    + " and s.process_instance_id = a.proc_inst_id_ "
                    + " and a.proc_inst_id_ = '" + processInstanceId + "'";
            List<Object[]> list = this.activitiTaskDao.executeNativeQuery(sql);
            Object[] obj = list.get(0);
            try {
                if (obj[2] != null) {
                    // 接收人
                    email = new Email();
                    email.setTo(obj[2].toString());// 接收人油箱地址
                    // email.setCc("抄送人油箱地址");
                    email.setSubject(obj[3].toString());// 邮件主题
                    email.setContent(obj[3].toString()
                        + "已经到您审核,请注意到人事自动考勤系统中审核！");// 邮件内容
                    email.setSentDate(DateUtil.dateToString(new Date(),
                        "yyyy-MM-dd HH:mm:ss"));
                    // 设置发送人
                    email.setFrom(RequestUtil.getLoginUser().getRealname()
                        + "<" + RequestUtil.getLoginUser().getEmail() + ">");
                    boolean isSent = mailService.sendMail(
                    // RequestUtil.getLoginUser().getUsername(),
                    // password,
                    PropertyUtil.get("emailAccount"),
                        PropertyUtil.get("emailPwd"),
                        email);
                    // String msg = null;
                    if (isSent) {
                        // msg = "邮件发送成功!";
                        
                    }
                    msg = "{success:true,msg:'发送提醒邮件成功！'}";
                } else {
                    msg = "{success:false,msg:'邮件发送失败！找不到审核人邮箱！'}";
                }
            } catch (Exception e) {
                
                 * com.dqgb.sshframe.log.entity.Log log = new com.dqgb.sshframe.log.entity.Log();
                 * log.setOpDate(new Date());
                 * log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
                 * log.setOpContent("邮件发送失败!错误信息:" + e.getMessage());
                 * log.setUser(RequestUtil.getLoginUser());
                 * log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1)); try {
                 * logService.addLog(log); } catch (BusinessException e1) { // TODO Auto-generated
                 * catch block e1.printStackTrace(); }
                 
                // logger.error("sendEmailToCurrentCheckUser error", e);
               
                msg = "{success:true,msg:'邮件发送成功'}";
                return msg;
            }
            return msg;
        }
        
    */
    	 return "{success:true,msg:'邮件功能未实现'}";	
    }
    
    @Override
    public Map<String, Object> sendSmsToCurrentCheckUser(
        String processInstanceId) {
        Map<String, Object> result = new HashMap<String, Object>();
        /*try {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.user_id       \"userId\", \n")
                .append("       u.realname      \"name\", \n")
                .append("       u.mobile_phone1 \"phone\", \n")
                .append("       s.process_name  \"processName\" \n")
                .append("  from t_user u, act_ru_task a, sys_activiti_process_inst s \n")
                .append(" where u.username = a.assignee_ \n")
                .append("   and s.process_instance_id = a.proc_inst_id_ \n")
                .append("   and a.proc_inst_id_ = '")
                .append(processInstanceId)
                .append("'");
            
            List<Map> list = activitiTaskDao.querySQLForMap(sql.toString());
            if (list != null && list.size() > 0) {
                Map map = list.get(0);
                String phone = StringUtil.getStr(map.get("phone"));
                if (StringUtil.isNotEmpty(phone)) {
                    String processName =
                        StringUtil.getStr(map.get("processName"));
                    
                    String url = PropertyUtil.get("smsUrl");
                    String smsLoginName = PropertyUtil.get("smsLoginName");
                    String smsLoginPassword =
                        PropertyUtil.get("smsLoginPassword");
                    Client client = new Client(new URL(url));
                    
                    client.invoke("smsSend", new Object[] {phone,
                        processName + "已经到您审核,请注意到人事自动考勤系统中审核！", smsLoginName,
                        smsLoginPassword,""});
                    
                    result.put("success", true);
                    result.put("msg", "短信发送成功!");
                } else {
                    String name = StringUtil.getStr(map.get("name"));
                    result.put("success", false);
                    result.put("msg", name + "的手机号为空，无法发送短信提醒!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "已办结的流程无需提醒!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "短信发送失败!请联系管理员!");
        }*/
        result.put("success", true);
        result.put("msg", "短信成功未实现!");
        return result;
    }

}