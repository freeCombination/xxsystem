/**
 * @文件名 ActivitiTaskAction.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 任务的控制层
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dqgb.sshframe.bpm.service.IActivitiAndBusinessService;
import com.dqgb.sshframe.bpm.service.IActivitiTaskService;
import com.dqgb.sshframe.bpm.vo.TaskVo;
import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.util.RequestUtil;
import com.dqgb.sshframe.common.vo.ListVo;

/**
 * 
 * 任务的控制层
 * 
 * @author zhxh
 * @version V1.20,2013-12-25 下午5:12:47
 * @since V1.20
 */
public class ActivitiTaskAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    
    /** 任务逻辑层接口 */
    @Autowired
    @Qualifier("activitiTaskService")
    private IActivitiTaskService activitiTaskService;
    
    /** 任务逻辑层接口 */
    @Autowired
    @Qualifier("activitiAndBusinessService")
    private IActivitiAndBusinessService activitiAndBusinessService;
    
    /**
     * 
     * @Title getToDoTaskListByPage
     * @author zhxh
     * @Description:分页查询待办任务
     * @date 2013-12-27
     * @return String
     */
    public String getToDoTaskListByPage() {
        try {
            Map paramMap = new HashMap();
            String start = this.getRequest().getParameter("start");
            String limit = this.getRequest().getParameter("limit");
            String processName = this.getRequest().getParameter("processName");
            String processCode = this.getRequest().getParameter("processCode");
            String taskOwnerName =
                this.getRequest().getParameter("taskOwnerName");
            String taskStartTime =
                this.getRequest().getParameter("taskStartTime");
            String taskStartEndTime =
                this.getRequest().getParameter("taskStartEndTime");
            paramMap.put("start", Integer.valueOf(start));
            paramMap.put("limit", Integer.valueOf(limit));
            paramMap.put("processName", processName);
            paramMap.put("processCode", processCode);
            paramMap.put("taskOwnerName", taskOwnerName);
            paramMap.put("taskStartTime", taskStartTime);
            paramMap.put("taskStartEndTime", taskStartEndTime);
            paramMap.put("user", this.getCurrentUser());
            ListVo listVo =
                this.activitiTaskService.getToDoTaskListByPage(paramMap);
            JsonUtil.outJson(listVo);
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'获取数据失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getDoneTaskListByPage
     * @author zhxh
     * @Description:分页查询已办任务
     * @date 2013-12-27
     * @return String
     */
    public String getDoneTaskListByPage() {
        try {
            Map paramMap = new HashMap();
            String start = this.getRequest().getParameter("start");
            String limit = this.getRequest().getParameter("limit");
            String processName = this.getRequest().getParameter("processName");
            String processCode = this.getRequest().getParameter("processCode");
            String taskOwnerName =
                this.getRequest().getParameter("taskOwnerName");
            String taskStartTime =
                this.getRequest().getParameter("taskStartTime");
            String taskStartEndTime =
                this.getRequest().getParameter("taskStartEndTime");
            String taskEndStartTime =
                this.getRequest().getParameter("taskEndStartTime");
            String taskEndTime = this.getRequest().getParameter("taskEndTime");
            
            String taskCompeletUser =
                this.getRequest().getParameter("taskCompeletUser");
            String taskOwnerId = this.getRequest().getParameter("taskOwnerId");
            paramMap.put("start", Integer.valueOf(start));
            paramMap.put("limit", Integer.valueOf(limit));
            paramMap.put("processName", processName);
            paramMap.put("taskOwnerName", taskOwnerName);
            paramMap.put("taskStartTime", taskStartTime);
            paramMap.put("taskEndTime", taskEndTime);
            paramMap.put("taskStartEndTime", taskStartEndTime);
            paramMap.put("taskEndStartTime", taskEndStartTime);
            paramMap.put("taskCompeletUser", taskCompeletUser);
            paramMap.put("taskOwnerId", taskOwnerId);
            paramMap.put("processCode", processCode);
            paramMap.put("user", this.getCurrentUser());
            ListVo listVo =
                this.activitiTaskService.getDoneTaskListByPage(paramMap);
            JsonUtil.outJson(listVo);
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'获取数据失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title updateTask
     * @author zhxh
     * @Description:更新任务
     * @date 2013-12-28
     * @return String
     */
    public String updateTask() {
        try {
            String userId = this.getRequest().getParameter("userId");
            String userName = this.getRequest().getParameter("userName");
            String taskId = this.getRequest().getParameter("taskId");
            String formUrl = this.getRequest().getParameter("formUrl");
            String name = this.getRequest().getParameter("taskName");
            TaskVo vo = new TaskVo();
            vo.setFormUrl(formUrl);
            vo.setTaskId(taskId);
            vo.setAssignee(userId);
            vo.setAssigneeName(userName);
            vo.setName(name);
            this.activitiTaskService.updateTask(vo);
            JsonUtil.outJson("{success:true,msg:'修改成功！'}");
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'失败！'}");
        }
        
        return null;
    }
    
    /**
     * 
     * @Title moveTask
     * @author zhxh
     * @Description:移交任务
     * @date 2013-12-28
     * @return String
     */
    public String moveTask() {
        try {
            String userId = this.getRequest().getParameter("userId");
            String taskId = this.getRequest().getParameter("taskId");
            this.activitiTaskService.moveTask(taskId,
                userId,
                this.getCurrentUser());
            JsonUtil.outJson("{success:true,msg:'设置成功！'}");
        } catch (ServiceException e) {
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'数据保存失败!'}");
        }
        return null;
        
    }
    
    /**
     * 
     * @Title rejectTask
     * @author zhxh
     * @Description:驳回任务
     * @date 2013-12-27
     * @return String
     */
    public String rejectTaskForApply() {
        try {
            String taskId = this.getRequest().getParameter("taskId");
            String opinion = this.getRequest().getParameter("opinion");
            String pass = this.getRequest().getParameter("pass");
            Map paramMap = new HashMap();
            paramMap.put("taskId", taskId);
            paramMap.put("user", this.getCurrentUser());
            paramMap.put("opinion",getCurrentUser().getRealname()+": "+ opinion);
            Map commitMap = new HashMap();
            if (pass != null) {
                paramMap.put("pass", pass);
            }
            
            List<ActivityImpl> acs =
                this.activitiAndBusinessService.getRollBackActivity(taskId);
            
            /*for (ActivityImpl ac : acs) {
                System.out.println(ac.getId());
                System.out.println(ac.getProperty("name"));
            }*/
            this.activitiTaskService.rejectTask(paramMap,
                commitMap,
                acs.get(0).getId());
            // this.activitiTaskService.rejectTaskForApply(paramMap, commitMap);
          JsonUtil.outJson("{success:true,msg:'驳回任务成功'}");
        } catch (Exception e) {
        	e.printStackTrace();
            JsonUtil.outJson("{success:false,msg:'驳回任务失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title rejectTask
     * @author zhxh
     * @Description:撤销任务
     * @date 2013-12-27
     * @return String
     */
    public String rejectTask() {
        try {
            String htaskId = this.getRequest().getParameter("taskId");
            String opinion = this.getRequest().getParameter("opinion");
            String pass = this.getRequest().getParameter("pass");
            Map paramMap = new HashMap();
            
            paramMap.put("user", this.getCurrentUser());
            paramMap.put("opinion",getCurrentUser().getRealname()+": "+ opinion);
            Map commitMap = new HashMap();
            if (pass != null) {
                paramMap.put("pass", pass);
            }
           Task task = activitiAndBusinessService.getActiveTaskByDoneTaskId(htaskId);
           HistoricTaskInstance hisTask = activitiAndBusinessService.getHistoricTask(htaskId);
            paramMap.put("taskId", task.getId());
            List<ActivityImpl> acs =
                    this.activitiAndBusinessService.getRollBackActivity(task.getId());
            
            if(acs.get(0).getId().equals(hisTask.getTaskDefinitionKey())){
            	 this.activitiTaskService.rejectTask(paramMap,
                         commitMap,
                         acs.get(0).getId());
            	 JsonUtil.outJson("{success:true,msg:'撤销任务成功'}");
            }else{
            	JsonUtil.outJson("{success:false,msg:'任务已被处理，不能撤销。'}");
            }
           
          return null;
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'撤回回任务失败,"+e.getMessage()+"'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title rejectTask
     * @author zhxh
     * @Description:撤销任务
     * @date 2013-12-27
     * @return String
     */
    public String rejectTaskToFirstNode() {
    	try {
    		String processInstanceId = this.getRequest().getParameter("processInstanceId");
    		String opinion = this.getRequest().getParameter("opinion");
    		String pass = this.getRequest().getParameter("pass");
    		Map paramMap = new HashMap();
    		paramMap.put("user", this.getCurrentUser());
    		paramMap.put("opinion",getCurrentUser().getRealname()+": "+ opinion);
    		
    		Map commitMap = new HashMap();
    		if (pass != null) {
    			paramMap.put("pass", pass);
    		}
    		Task task = activitiAndBusinessService.getActiveTaskByProcessInsId(processInstanceId);
    		paramMap.put("taskId", task.getId());
    		List<ActivityImpl> acs =
    				this.activitiAndBusinessService.getRollBackActivity(task.getId());
    		
			this.activitiTaskService.rejectTask(paramMap,
					commitMap,
					acs.get(acs.size()-1).getId());
			JsonUtil.outJson("{success:true,msg:'重新发起流程成功'}");
    		
    		return null;
    	} catch (Exception e) {
    		JsonUtil.outJson("{success:false,msg:'重新发起流程失败,"+e.getMessage()+"'}");
    	}
    	return null;
    }
    
    /**
     * @Title getRejectTask
     * @author wanglc
     * @Description:
     * @date 2014-2-18
     * @return
     */
    public String getRejectTask() {
        String taskId = this.getRequest().getParameter("taskId");
        List<ActivityImpl> acs =
            this.activitiAndBusinessService.getRollBackActivity(taskId);
        List<Map> list = new ArrayList();
        for (ActivityImpl a : acs) {
            Map map = new HashMap();
            map.put("taskDefineKey", a.getId());
            map.put("name", a.getProperty("name"));
            list.add(map);
        }
        JsonUtil.outJsonArray(list, null);
        return null;
    }
    
    /**
     * 
     * @Title getHistoryTask
     * @author zhxh
     * @Description:获取历史任务信息
     * @date 2013-12-27
     * @return String
     */
    public String getHistoryTask() {
        try {
            String taskId = this.getRequest().getParameter("taskId");
            TaskVo vo = this.activitiTaskService.getHistoryTask(taskId);
            Map map = new HashMap();
            map.put("data", vo);
            map.put("success", true);
            JsonUtil.outJson(map);
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'获取历史任务失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getFlowNodeByCheck
     * @author zhxh
     * @Description:获取所有定义任务节点
     * @date 2013-12-30
     * @return String
     */
    public String getFlowNodeByCheck() {
        try {
            String checkedFlag = this.getRequest().getParameter("checkedFlag");
            String nodeFile =
                ServletActionContext.getServletContext().getRealPath("/")
                    + SystemConstant.UPLOAD_FOLDER_ACTIVITI + File.separator
                    + "flowNode.js";
            File file = new File(nodeFile);
            StringWriter writer = new StringWriter();
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(
                    file), "UTF-8"));
            String str = null;
            while ((str = reader.readLine()) != null) {
                writer.write(str.trim());
            }
            String jsonString = writer.toString();
            reader.close();
            writer.close();
            if ("true".equals(checkedFlag)) {
                JsonUtil.outJsonArray(writer.toString());
            } else {// 去掉checked
                JSONArray jsonArray = JSONArray.fromObject(jsonString);
                deleteChecked(jsonArray);
                JsonUtil.outJsonArray(jsonArray.toString());
            }
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'失败，请稍后重试！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title deleteChecked
     * @author zhxh
     * @Description:去掉其中的checked
     * @date 2013-12-30
     * @param jsonArray
     * @throws Exception
     */
    private void deleteChecked(JSONArray jsonArray)
        throws Exception {
        for (Iterator it = jsonArray.iterator(); it.hasNext();) {
            JSONObject temp = (JSONObject)it.next();
            temp.remove("checked");
            if (temp.get("children") != null) {
                deleteChecked(temp.getJSONArray("children"));
            }
        }
    }
    
    /**
     * 发送邮件
     * 
     * @Title sendEmailToCurrentCheckUser
     * @author 游正刚
     * @Description:发送邮件
     * @date 2014年12月9日
     * @return
     */
    public String sendEmailToCurrentCheckUser() {
        String processInstanceId =
            RequestUtil.getString(getRequest(), "processInstanceId");
        try {
            //String msg =this.activitiTaskService.sendEmailToCurrentCheckUser(processInstanceId,this.getCurrentUserPassword());
            String msg ="{success:true,msg:'未实验发送邮件功能！'}";
            JsonUtil.outJson(msg);
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'邮件发送失败，中油邮箱服务获取错误！'}");
        }
        return null;
    }
    
    /**
     * 发送短信提醒
     * 
     * @Title sendSmsToCurrentCheckUser
     * @author 游正刚
     * @Description:发送短信提醒
     * @date 2014年12月9日
     * @return
     */
    public void sendSmsToCurrentCheckUser() {
        String processInstanceId =
            RequestUtil.getString(getRequest(), "processInstanceId");
        JsonUtil.outJson(activitiTaskService.sendSmsToCurrentCheckUser(processInstanceId));
    }
}
