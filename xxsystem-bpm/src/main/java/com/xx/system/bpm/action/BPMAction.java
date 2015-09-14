/**
 * @文件名 BPMAction.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程控制层
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dqgb.sshframe.bpm.entity.ActivitiDefineTemplate;
import com.dqgb.sshframe.bpm.service.IActivitiAndBusinessService;
import com.dqgb.sshframe.bpm.service.IActivitiDefineTemplateService;
import com.dqgb.sshframe.bpm.service.IActivitiTaskService;
import com.dqgb.sshframe.bpm.vo.ActivityProperty;
import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.constant.Constant;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.util.RequestUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.common.vo.TreeNode;
import com.dqgb.sshframe.dict.service.IDictService;
import com.dqgb.sshframe.log.entity.Log;
import com.dqgb.sshframe.log.service.ILogService;
import com.dqgb.sshframe.org.entity.OrgUser;
import com.dqgb.sshframe.org.entity.Organization;
import com.dqgb.sshframe.role.entity.Role;
import com.dqgb.sshframe.user.entity.User;
import com.dqgb.sshframe.user.vo.UserVo;

/**
 * 
 * 流程控制层
 * 
 * @author zhxh
 * @version V1.20,2013-12-27 上午11:49:10
 * @since V1.20
 * @depricated
 */
public class BPMAction extends BaseAction
{
    /** 分页查询集合 */
    private ListVo<?> listVo;
    
    /** 返回map */
    private Map outPut;
    
    /** @Fields activitiDefineTemplateService : */ 
    @Autowired
    @Qualifier("activitiDefineTemplateService")
    private IActivitiDefineTemplateService activitiDefineTemplateService;
    
    @Autowired
    @Qualifier("activitiTaskService")
    private IActivitiTaskService activitiTaskService;
    
	/**
     * @Fields dictService : 字典服务
     */
    @Resource
    public IDictService dictService;
    
    /**
     * @Fields roleService : 日志服务
     */
    @Autowired(required = true)
    @Qualifier("logService")
    private ILogService logService;
    
    /** 节点属性类 */
    List<ActivityProperty> activityProperties =
        new ArrayList<ActivityProperty>();
    
    /**
     * 
     * @Title getActivityProperties
     * @author zhxh
     * @Description: 获取节点值属性
     * @date 2014-1-6
     * @return activityProperties
     */
    public List<ActivityProperty> getActivityProperties()
    {
        return activityProperties;
    }
    
    /**
     * 
     * @Title setActivityProperties
     * @author zhxh
     * @Description: 设置节点值属性
     * @date 2014-1-6
     * @param activityProperties
     */
    public void setActivityProperties(List<ActivityProperty> activityProperties)
    {
        this.activityProperties = activityProperties;
    }
    
    private static final long serialVersionUID = 1L;
    
    /** 表单地址 */
    private String jspLocation;
    
    /**
     * 
     * @Title getJspLocation
     * @author zhxh
     * @Description: 获取表单地址
     * @date 2014-1-6
     * @return String
     */
    public String getJspLocation()
    {
        return jspLocation;
    }
    
    /**
     * 
     * @Title setJspLocation
     * @author zhxh
     * @Description:设置表单地址
     * @date 2014-1-6
     * @param jspLocation
     */
    public void setJspLocation(String jspLocation)
    {
        this.jspLocation = jspLocation;
    }
    
    /**
     * 
     * @Title toProcess
     * @author zhxh
     * @Description:跳转流程管理
     * @date 2013-12-27
     * @return String
     */
    public String toProcess()
    {
        return SUCCESS;
    }
    
    /**
     * 
     * @Title toProcess
     * @author zhxh
     * @Description:跳转个人中心
     * @date 2013-12-27
     * @return String
     */
    public String toPerson()
    {
        return SUCCESS;
    }
    /**流程业务类*/
    @Autowired
    @Qualifier("activitiAndBusinessService")
    public IActivitiAndBusinessService activitiAndBusinessService;
    
    /**
     * 
     * @Title getUserListForFlow
     * @author zhxh
     * @Description:针对在线流程设计器查询人员
     * @date 2013-12-27
     * @return String
     */
    public String getUserListForFlow()
    {
        try
        {
            String usersId = this.getRequest().getParameter("usersId");
            String userName = this.getRequest().getParameter("userName");
            String start = getRequest().getParameter("start");
            String limit = getRequest().getParameter("limit");
            Map paramMap = new HashMap();
            paramMap.put("usersId", usersId);
            paramMap.put("userName", userName);
            paramMap.put("start", start);
            paramMap.put("limit", limit);
            ListVo<User> userListVo =
                this.activitiAndBusinessService.getUserListForFlow(paramMap);
            List<User> userList = userListVo.getList();
            List<Map> userVoList = new ArrayList<Map>();
            for (User user : userList)
            {
                Map userVo = new HashMap();
                userVo.put("userName", user.getRealname());
                userVo.put("loginName", user.getUsername());
                userVo.put("unit", null);
                Set<OrgUser> orgsUsers = user.getOrgUsers();
                String unitName = this.collectionUnitName(orgsUsers);
                userVo.put("unit",unitName);
                userVoList.add(userVo);
            }
            ListVo<Map> userVoListVo = new ListVo<Map>();
            userVoListVo.setList(userVoList);
            userVoListVo.setTotalSize(userListVo.getTotalSize());
            JsonUtil.outJson(userVoListVo);
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'获取人员失败！'}");
        }
        return null;
        
    }
    /**
     * 
    * @Title collectionUnitName
    * @author zhxh
    * @Description: 针对BPMAction收集单位名称
    * @see BPMAction#getUserListForFlow
    * @date 2014-1-16
    * @param orgsUsers
    * @return String
     */
    private String collectionUnitName(Set<OrgUser> orgsUsers)
    {
       StringBuffer unitNames =new StringBuffer();
       String unitName = null;
        if (!CollectionUtils.isEmpty(orgsUsers))
        {
            for (OrgUser orgUser : orgsUsers)
            {
                if (orgUser.getIsDelete() == Constant.STATUS_NOT_DELETE
                    && orgUser.getOrganization() != null
                    && orgUser.getOrganization().getEnable() == Constant.ENABLE
                    && orgUser.getOrganization().getStatus() == Constant.STATUS_NOT_DELETE)
                {
                    unitNames.append(orgUser.getOrganization()
                        .getOrgName());
                }
                if (unitNames.length() > 0)
                {
                    unitName = unitNames.substring(0, unitNames.length());
                }
            }
            
        }
        return unitName;
    }
    /**
     * 
     * @Title formRedirect
     * @author zhxh
     * @Description:bpm表单跳转action
     * @date 2013-12-27
     * @return String
     */
    public String formRedirect()
    {
        String actionName = ServletActionContext.getActionMapping().getName();
        jspLocation = actionName.replaceAll("_", "/");
        return SUCCESS;
    }
    
    /**
     * 
     * @Title getAllProcessInstanceByPage
     * @author zhxh
     * @Description:分页查询流程实例
     * @date 2013-12-27
     * @return String
     */
    public String getAllProcessInstanceByPage()
    {
        try
        {
            Map paramMap = getQueryProcessParam();
            ListVo listVo =
                this.activitiAndBusinessService.getProcessInstanceByPage(paramMap);
            JsonUtil.outJson(listVo);
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'获取数据失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getProcessInstanceByPage
     * @author zhxh
     * @Description:分页查询流程实例
     * @date 2013-12-27
     * @return String
     */
    public String getProcessInstanceByPage()
    {
        try
        {
            Map paramMap = getQueryProcessParam();
            paramMap.put("processCreateUserId", this.getCurrentUser()
                .getUsername());
            ListVo listVo =
                this.activitiAndBusinessService.getProcessInstanceByPage(paramMap);
            JsonUtil.outJson(listVo);
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'获取数据失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title endProcessInstance
     * @author zhxh
     * @Description:结束流程
     * @date 2013-12-27
     * @return String
     */
    public String endProcessInstance()
    {
        try
        {
            String taskId = this.getRequest().getParameter("taskId");
            String opinion = this.getRequest().getParameter("opinion");
            String businessId = this.getRequest().getParameter("businessId");
            String pass = this.getRequest().getParameter("pass");
            Map paramMap = new HashMap();
            paramMap.put("taskId", taskId);
            paramMap.put("user", this.getCurrentUser());
            paramMap.put("opinion", opinion);
            paramMap.put("businessId", businessId);
            paramMap.put("pass", pass);
            this.activitiAndBusinessService.endProcessInstanceByMap(paramMap);
            JsonUtil.outJson("{success:true,msg:'终止流程成功!'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'终止流程失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getProcessResource
     * @author zhxh
     * @Description:获取已经执行的xml报文
     * @date 2013-12-27
     * @return String
     */
    public String getProcessResource()
    {
        String processInstanceId =
            this.getRequest().getParameter("processInstanceId");
        try
        {
            InputStream in =
                this.activitiAndBusinessService.getProcessXmlResource(processInstanceId);
            OutputStream out;
            
            out = this.getResponse().getOutputStream();
            byte b[] = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1)
            {
                out.write(b, 0, len);
            }
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'获取报文失败!'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getProcessListenerShowData
     * @author zhxh
     * @Description: 获取流程展示
     * @date 2013-12-27
     * @return String
     */
    public String getProcessListenerShowData()
    {
        try
        {
            String processInstanceId =
                this.getRequest().getParameter("processInstanceId");
            Map map =
                this.activitiAndBusinessService.getProcessListenerShowData(processInstanceId);
            map.put("success", true);
            JsonUtil.outJson(map);
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'获取数据失败!'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getProcessUserData
     * @author zhxh
     * @Description:获取流程展示上的用户
     * @date 2013-12-28
     * @return String
     */
    public String getProcessUserData()    //FIXME 查看流程鼠标移动上去后，报错。
    {
        try
        {
            String processInstanceId = this.getRequest().getParameter("processInstanceId");
            String taskDefineKey = this.getRequest().getParameter("taskDefineKey");
            Map map = this.activitiAndBusinessService.getProcessUserData(processInstanceId,  taskDefineKey);
            map.put("success", true);
            JsonUtil.outJson(map);
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'获取数据失败!'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getUserByLoginName
     * @author zhxh
     * @Description: 获取用户名称
     * @date 2013-12-28
     * @return String
     */
    public String getUserByLoginName()
    {
        try
        {
            List data = new ArrayList();
            String account = this.getRequest().getParameter("account");
            
            Map mapTemp = new HashMap();
            if (StringUtils.isNotBlank(account))
            {
                StringBuffer bf = new StringBuffer(0);
                String strs[] = account.split(",");
                for (String str : strs)
                {
                    if (StringUtils.isNotBlank(str))
                    {
                        bf.append(str).append(",");
                    }
                }
                List<User> listUser =
                    this.activitiAndBusinessService.getUserListByLoginNames(bf.substring(0,
                        bf.length() - 1));
                for (User u : listUser)
                {
                    mapTemp.put(u.getUsername(), u.getRealname());
                    Map map = new HashMap();
                    map.put("loginName", u.getUsername());
                    map.put("userName", u.getRealname());
                    data.add(map);
                    
                }
                if (mapTemp.size() < strs.length)
                {
                    for (String str : strs)
                    {
                        if (mapTemp.get(str.trim()) == null)
                        {
                            Map map = new HashMap();
                            map.put("loginName", str);
                            map.put("userName", "");
                            data.add(map);
                        }
                    }
                }
            }
            Map resultMap = new HashMap();
            resultMap.put("success", true);
            resultMap.put("data", data);
            JsonUtil.outJson(resultMap);
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'获取数据失败!'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title deleteProcessInstance
     * @author zhxh
     * @Description:删除流程
     * @date 2013-12-28
     * @return String
     */
    public String deleteProcessInstance()
    {
        String processInstanceIds = this.getRequest().getParameter("ids");
        try
        {
            this.activitiAndBusinessService.deleteProcessInstance(processInstanceIds);
            
            JsonUtil.outJson("{success:true,msg:'数据删除成功!'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'部分数据删除失败!'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title updateProcessInstance
     * @author zhxh
     * @Description:修改流程实例
     * @date 2013-12-28
     * @return String
     */
    public String updateProcessInstance()
    {
        try
        {
            String xmlContent = this.getRequest().getParameter("xmlContent");
            String processInstanceId =
                this.getRequest().getParameter("processInstanceId");
            
            if (xmlContent == null)
            {
                JsonUtil.outJson("{success:false,msg:'保存失败！没有获取到xml值'}");
                return null;
            }
            StringReader reader = new StringReader(xmlContent);
            if (reader == null)
            {
                JsonUtil.outJson("{success:false,msg:'保存失败！没有获取到xml值'}");
                return null;
            }
            if (processInstanceId == null || "".equals(processInstanceId))
            {
                JsonUtil.outJson("{success:false,msg:'实例id为空，数据保存失败!'}");
                return null;
            }
            this.activitiAndBusinessService.updateProcessInstance(xmlContent,
                processInstanceId);
            JsonUtil.outJson("{success:true,msg:'保存成功！'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'数据保存失败!'}");
        }
        return null;
    }
    
    /**
     * 启动流程(送审)
     * 
     * @author 张小虎
     * @return String
     */
    
    public String startProcess()
    {
        try
        {
        	Map commitMap =  RequestUtil.getParameterMap(getRequest());
            Map<String, Object> map = new HashMap<String, Object>();
            HttpServletRequest request = getRequest();
            String id = request.getParameter("id");//流程模板主键ID
            String processType = request.getParameter("processType");//业务类别名称
            String processName = request.getParameter("processName");//事项名称
            String processCode = request.getParameter("processCode");//业务类别代码
            String businessId = request.getParameter("businessId");//业务ID
            int businessOrg = super.getCurrentOrg().getOrgId();
            Map processMap = new HashMap();
            processMap.put("user", this.getCurrentUser());
            processMap.put("processName", processName);
            processMap.put("processType", processType);
            processMap.put("processCode", processCode);
            processMap.put("businessId", businessId);//业务主键组合
            processMap.put("businessOrg", String.valueOf(businessOrg));//业务主键组合
            
           /* commitMap.put("assigneeUser",this.getCurrentUser().getUsername());*///如果下一个节点未设置人员，请设置assigneeUser
            String rootPath =
                ServletActionContext.getServletContext().getRealPath("/");
            Map queryMap = new HashMap();
            queryMap.put("id", Integer.valueOf(id));
           List<ActivitiDefineTemplate> templates = 
               this.activitiDefineTemplateService.getActivitiDefineTemplate(queryMap);
           if(CollectionUtils.isEmpty(templates))
           {
               throw new ServiceException("没有找到流程模版，请确认！");
           }
           
            ActivitiDefineTemplate template = templates.get(0); 
            this.activitiAndBusinessService.startProcess(processMap,
                commitMap,
                rootPath,
                template.getProcessDefineKey());//流程模版编码
            JsonUtil.outJson("{success:true,msg:'送审成功！'}");
        }
        catch (ServiceException e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "启动流程", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("启动流程失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "启动流程", e, false);
           /* Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("启动流程失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'送审失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title userLogin
     * @author zhxh
     * @Description:登录操作
     * @date 2013-12-28
     * @return
     * @throws Exception
     */
    public String userLogin()
        throws Exception
    {
        String loginName = this.getRequest().getParameter("loginName");
        List<User> users =
            this.activitiAndBusinessService.getUserListByLoginNames(loginName);
        if (users.size() > 0)
        {
            this.getSession().setAttribute(SystemConstant.CURRENT_USER,
                users.get(0));
        }
        else
        {
            this.addError("用户不存在!");
            throw new Exception("用户不存在!");
        }
        return SUCCESS;
    }
    
    /**
     * 
     * @Title getUserListForBpmByPage
     * @author zhxh
     * @Description:为流程获取用户
     * @date 2013-12-28
     * @return String
     */
    public String getUserListForBpmByPage()
    {
        String userName = this.getRequest().getParameter("userName");
        String start = getRequest().getParameter("start");
        String limit = getRequest().getParameter("limit");
        Map paramMap = new HashMap();
        paramMap.put("userName", userName);
        paramMap.put("start", start);
        paramMap.put("limit", limit);
        ListVo<User> userListVo =
            this.activitiAndBusinessService.getUserListForFlow(paramMap);
        List<User> userList = userListVo.getList();
        List<UserVo> userVoList = new ArrayList<UserVo>();
        ListVo<UserVo> userVoListVo = new ListVo();
        userVoListVo.setList(userVoList);
        userVoListVo.setTotalSize(userListVo.getTotalSize());
        BeanCopier beanCopier =
            BeanCopier.create(User.class, UserVo.class, false);
        for (User user : userList)
        {
            UserVo userVo = new UserVo();
            userVo.setUserId(user.getUserId());
            beanCopier.copy(user, userVo, null);
            Set<OrgUser> orgsUsers = user.getOrgUsers();
            StringBuffer unitNames = new StringBuffer();
            if (!CollectionUtils.isEmpty(orgsUsers))
            {
                for (OrgUser orgUser : orgsUsers)
                {
                    if (orgUser.getIsDelete() == Constant.STATUS_NOT_DELETE
                        && orgUser.getOrganization() != null
                        && orgUser.getOrganization().getEnable() == 
                                                           Constant.ENABLE
                        && orgUser.getOrganization().getStatus() == 
                                                       Constant.STATUS_NOT_DELETE)
                    {
                        Organization org = orgUser.getOrganization();
                            unitNames.append(org.getOrgName());
                        
                    }
                    if (unitNames.length() > 0)
                    {
                        userVo.setOrgName(unitNames.substring(0,
                            unitNames.length()));
                    }
                }
                
            }
            userVoList.add(userVo);
        }
        JsonUtil.outJson(userVoListVo);
        return null;
    }
    
    /**
     * 
     * @Title getOrgTreeForBpm
     * @author zhxh
     * @Description:针对流程获取单位组织树
     * @date 2013-12-28
     * @return String
     */
    public String getOrgTreeForBpm()
    {
        try
        {
            String nodeId = this.getRequest().getParameter("nodeId");
            String checkFlag = this.getRequest().getParameter("checkFlag");
            
            List<Organization> orgs =
                this.activitiAndBusinessService.getOrgTreeForBpm(nodeId);
            List<TreeNode> treeNodes = new ArrayList<TreeNode>();
            for (Organization org : orgs)
            {
                TreeNode node = new TreeNode();
                node.setNodeId(org.getOrgId());
                node.setText(org.getOrgName());
                node.setType(org.getOrgCode());
                if (org.getOrganization() == null)
                {
                    node.setParentId(0);
                }
                else
                {
                    node.setParentId(org.getOrganization().getOrgId());
                }
                if (org.getOrganizations() != null
                    && org.getOrganizations().size() > 0)
                {
                    node.setLeaf(false);
                }
                else
                {
                    node.setLeaf(true);
                }
                if ("true".equals(checkFlag))
                {
                    node.setChecked(false);
                }
                treeNodes.add(node);
            }
            JsonUtil.outJsonArray(treeNodes);
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,'获取数据失败!'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getRoleForBpmByPage
     * @author zhxh
     * @Description:针对流程分页查询角色
     * @date 2013-12-28
     * @return String
     */
    public String getRoleForBpmByPage()
    {
        try
        {
            String roleName = this.getRequest().getParameter("roleName");
            String start = getRequest().getParameter("start");
            String limit = getRequest().getParameter("limit");
            Map paramMap = new HashMap();
            paramMap.put("roleName", roleName);
            paramMap.put("start", start);
            paramMap.put("limit", limit);
            listVo =
                this.activitiAndBusinessService.getRoleForBpmByPage(paramMap);
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "针对流程分页查询角色", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("针对流程分页查询角色失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,'获取数据失败!'}");
        }
        return SUCCESS;
    }
    
    /**
     * 
     * @Title getRoleForBpmById
     * @author zhxh
     * @Description:针对流程获取查询角色
     * @date 2013-12-28
     * @return String
     */
    public String getRoleForBpmById()
    {
        try
        {
            String roleIds = this.getRequest().getParameter("roleIds");
            StringBuffer roleIdBuffer = new StringBuffer(0);
            List<Role> roles = new ArrayList(0);
            if (StringUtils.isNotBlank(roleIds))
            {
                for (String id : roleIds.split(","))
                {
                    if (StringUtils.isNotBlank(id))
                    {
                        roleIdBuffer.append(id).append(",");
                    }
                    
                }
                if (roleIdBuffer.length() > 0)
                {
                    roles =
                        this.activitiAndBusinessService.getRoleForBpmById(roleIdBuffer.substring(0,
                            roleIdBuffer.length() - 1));
                }
                
            }
            outPut = new HashMap();
            outPut.put("success", true);
            outPut.put("data", roles);
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,'获取角色信息失败!'}");
        }
        return SUCCESS;
    }
    
    /**
     * 
     * @Title getQueryProcessParam
     * @author zhxh
     * @Description:查询流程实例的时候获取流程实例参数
     * @date 2013-12-27
     * @return
     */
    private Map getQueryProcessParam()
    {
        Map paramMap = new HashMap();
        String start = this.getRequest().getParameter("start");
        String limit = this.getRequest().getParameter("limit");
        String processName = this.getRequest().getParameter("processName");
        String processStartTime =
            this.getRequest().getParameter("processStartTime");
        String processCreateUserName =
            this.getRequest().getParameter("processCreateUserName");
        String processEndTime =
            this.getRequest().getParameter("processEndTime");
        String processStartEndTime =
            this.getRequest().getParameter("processStartEndTime");
        String processEndStartTime =
            this.getRequest().getParameter("processEndStartTime");
        paramMap.put("start", Integer.valueOf(start));
        paramMap.put("limit", Integer.valueOf(limit));
        paramMap.put("processName", processName);
        paramMap.put("processCreateUserName", processCreateUserName);
        paramMap.put("processStartTime", processStartTime);
        paramMap.put("processEndTime", processEndTime);
        paramMap.put("processStartEndTime", processStartEndTime);
        paramMap.put("processEndStartTime", processEndStartTime);
        return paramMap;
    }
    
    /**
    * @Title compeleteTask
    * @author wanglc
    * @Description: 提交任务
    * @date 2014-2-18
    * @return String
    */ 
    public String compeleteTask()
    {
    	boolean finalPass=false;
        try
        {
            String taskId = this.getRequest().getParameter("taskId");
            String opinion = this.getRequest().getParameter("opinion");
            String pass = this.getRequest().getParameter("pass");
            String businessId = this.getRequest().getParameter("businessId");
            String param="";
            Map paramMap = new HashMap();
            paramMap.put("taskId", taskId);
            paramMap.put("user", this.getCurrentUser());
            paramMap.put("opinion", getCurrentUser().getRealname()+ "：" +opinion);
            paramMap.put("businessId", businessId);
            
            if (pass != null)
            {
                paramMap.put("pass", pass);
            }
            
            Map commitMap = new HashMap();
            
          //设置业务判断条件，如请假时长（小于3天和大于3天分别走不同的流程），x的名称须与流程模板设置的对应
            commitMap.put("test",2);
            
            Map<String,String> taskParam = null;
            if("false".equals(pass)){
            	taskParam  = new HashMap();
            	param = this.activitiTaskService.getProcessParam(taskId);
            	this.activitiAndBusinessService.endProcessInstanceByMap(paramMap);
            	
            	taskParam.put("param", param);
            	taskParam.put("finalNode", "true");
            }else{
            	taskParam = this.activitiTaskService.completeTask(paramMap, commitMap);
            }
            taskParam.put("pass", pass);
            this.taskCallBack(taskParam);
            
            StringBuffer logContent = new StringBuffer();
            logContent.append("compeleteTask操作:");
            logContent.append(getCurrentUser().getRealname());
            logContent.append("执行完成任务操作【任务id为【" + taskId + "】】");
            // addLog(logContent.toString());
            
            JsonUtil.outJson("{success:true,msg:'完成任务成功'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'完成任务失败！'}");
        }
        return null;
    }
    
    /**
     * @Title taskCallBack
     * @author hedaojun
     *  ("finalNode", "false"); //是否是最后的节点
		("pass", "true/false"); //是否通过
		("param", "xgfsh"); //流程节点自定义参数
		("businessId", "businessId");// 业务ID
		("categoryCode","categoryCode");//流程分类编码
		("username", "jg_admin");
     * @Description: 流程结束后，回调业务方法
     * @date 2015-1-18
     * @return String
     */ 
     private String taskCallBack(Map<String,String> taskParam)
     {
     	boolean pass=Boolean.valueOf(taskParam.get("pass"));
     	String businessId = this.getRequest().getParameter("businessId");
        String categoryCode = this.getRequest().getParameter("realType");
        taskParam.put("businessId", businessId);
        taskParam.put("categoryCode", categoryCode);
        taskParam.put("username", this.getCurrentUser().getUsername());
         try
         {
        	//根据流程分类调用不同的业务方法
        	 if("ZYYY".equals(categoryCode)){
        		
        	 }else{
        	 }
         
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
         return null;
     }
    
    public ListVo<?> getListVo()
    {
        return listVo;
    }
    
    public void setListVo(ListVo<?> listVo)
    {
        this.listVo = listVo;
    }
    
    public Map getOutPut()
    {
        return outPut;
    }
    
    public void setOutPut(Map outPut)
    {
        this.outPut = outPut;
    }
    
    
}
