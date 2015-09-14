/**
* @文件名 TestBpmService.java
* @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
* @描述 TestBpmService.java
* @修改人 zhxh
* @修改时间 2013-12-23 下午4:46:48
* @修改内容 测试bpm发布流程，提交动态节点人员任务
*/
package com.dqgb.sshframe.bpm.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 测试bpm相关功能
 * @author zhxh
 * @version V1.20,2013-12-23 下午4:46:48
 * @since V1.20
 * @depricated
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class TestBpmService {
	    @Autowired(required = true)
	    @Qualifier("activitiAndBusinessService")
	    private IActivitiAndBusinessService activitiAndBusinessService;
	    
	    private IActivitiDefineTemplateService activitiDefineTemplateService;
	    @Autowired
	    private ApplicationContext applicationContext;
	   /**
	    * 发布bpm 
	   * @Title testDeployBpm
	   * @author zhxh
	   * @Description: 
	   * @date 2013-12-23
	    
	   @Test
	   public void testDeployBpm(){
		       String sourceFile=this.getClass().getClassLoader().getResource("f2bfc2b3-33ec-473a-a8f6-5cff7a8f310f.bpmn20.xml").getPath();
		       //this.activitiAndBusinessService.startProcess(map, rootPath, processDefineKey);
		       this.activitiAndBusinessService.deployProcessDefinitionByXml(sourceFile);
			   Map startMap=new HashMap();
			   startMap.put("createUser","wanglc");
			   ProcessInstance p=this.activitiAndBusinessService.startProcessInstanceByKey("testaaa",startMap,"zhxh");
			   Map map=new HashMap();
			   map.put("assigneeUser", "wanglucong");
			   map.put("processInstanceId", p.getProcessInstanceId());
		       map.put("baseType", "free");
		       map.put("realType", "a1_ZCZX");
		       map.put("businessId", "1");
		       User u=new User();
		       u.setUsername("wanglc");
		       u.setRealname("王璐聪");
		       map.put("user", u);
	           this.activitiAndBusinessService.commitApplyTask(map, p.getProcessInstanceId());
		 
	   }
	   @Test
	   public void completeSingleTask(){
		      List<Task> tasks=this.activitiAndBusinessService.getToDoTaskList("wanglucong");
		      for (Task t : tasks){
		    	   Map paramMap=new HashMap();
		    	   Map commitMap=new HashMap();
			       User u=new User();
			       u.setUsername("wanglc");
			       u.setRealname("王璐聪");
		    	   paramMap.put("baseType", "free");
			       paramMap.put("user", u);
			       paramMap.put("realType", "a1_ZCZX");
			       paramMap.put("businessId", "1");
			       paramMap.put("taskId", t.getId());
			       paramMap.put("processInstanceId", t.getProcessInstanceId());
			       paramMap.put("opinion", "测试而已，不要当真!");
		    	 //  commitMap.put("assigneeUser", "wanglucong");
			       this.activitiAndBusinessService.compeleteTask(paramMap, commitMap);
		      }
		 
	   }
	   
	   @Test
	   public void completeMulTask(){
		      List<Task> tasks=this.activitiAndBusinessService.getToDoTaskList("wanglucong");
		      for (Task t : tasks){
		    	   Map paramMap=new HashMap();
		    	   Map commitMap=new HashMap();
			       User u=new User();
			       u.setUsername("wanglc");
			       u.setRealname("王璐聪");
		    	   paramMap.put("baseType", "free");
			       paramMap.put("user", u);
			       paramMap.put("realType", "a1_ZCZX");
			       paramMap.put("businessId", "1");
			       paramMap.put("taskId", t.getId());
			       paramMap.put("processInstanceId", t.getProcessInstanceId());
			       paramMap.put("opinion", "测试而已，不要当真!");
			       List userList=new ArrayList();
			       userList.add("wanglucong");
			       userList.add("wanglc");
		    	   commitMap.put("assigneeList",userList);
			       this.activitiAndBusinessService.compeleteTask(paramMap, commitMap);
		      }
		 
	   }
	   
	   @Test
	   public void rejectFrontTask(){
			   List<ActivityImpl>  activityImples = this.activitiAndBusinessService.getRollBackActivity("1202");
			   for(ActivityImpl ac:activityImples){
				   System.out.println(ac.getProperty("name"));
				   User u=new User();
			       u.setUsername("wanglc");
			       u.setRealname("王璐聪");
			       Map paramMap=new HashMap();
		    	   Map commitMap=new HashMap();
		    	   paramMap.put("baseType", "free");
			       paramMap.put("user", u);
			       paramMap.put("realType", "a1_ZCZX");
			       paramMap.put("businessId", "1");
			       paramMap.put("taskId", "1102");
			       paramMap.put("opinion", "测试而已，不要当真!");
				   this.activitiAndBusinessService.rejectTask(paramMap, commitMap,activityImples.get(2).getId());
				   break;
				
			  
			
	      }
		 
	   }*/
}
