/**
 * @文件名 ActivitiCategoryAction.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板种类（类型）的控制层
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.bpm.service.IActivitiCategoryService;
import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.vo.TreeNode;
import com.dqgb.sshframe.dict.service.IDictService;
import com.dqgb.sshframe.log.service.ILogService;
import com.dqgb.sshframe.org.action.OrgAction;

/**
 * 
 * 流程模板种类（类型）的控制层
 * 
 * @author zhxh
 * @version V1.20,2013-12-25 下午5:12:47
 * @since V1.20
 */
public class ActivitiCategoryAction extends BaseAction
{
    private static final long serialVersionUID = 1L;
    
    /** 流程种类逻辑层 */
    @Autowired
    @Qualifier("activitiCategoryService")
    private IActivitiCategoryService activitiCategoryService;
    
    /** 流程种类实体 */
    private ActivitiCategory category;
    
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
    
    /**
     * 
     * @Title addCategory
     * @author zhxh
     * @Description: 添加流程模板种类
     * @date 2013-12-25
     * @return String
     */
    public String addCategory()
    {
        try
        {
            category.setIsDelete(0);
            this.activitiCategoryService.addActivitiCategory(category);
            JsonUtil.outJson("{success:true,msg:'保存类型成功！'}");
        }
        catch (ServiceException e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "添加流程模板种类", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("添加流程模板种类失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
            
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "添加流程模板种类", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("添加流程模板种类失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'保存类型失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title updateCategory
     * @author zhxh
     * @Description: 修改流程种类
     * @date 2013-12-26
     * @return string
     */
    public String updateCategory()
    {
        try
        {
            this.activitiCategoryService.updateActivitiCategory(category);
            this.excepAndLogHandle(OrgAction.class, "修改流程模板种类", null, true);
            JsonUtil.outJson("{success:true,msg:'更新种类成功！'}");
        }
        catch (ServiceException e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "修改流程模板种类", e, false);
           /* Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("修改流程模板种类失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "修改流程模板种类", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("修改流程模板种类失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'更新失败，稍后重试!'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title deleteCategory
     * @author zhxh
     * @Description: 删除流程种类
     * @date 2013-12-26
     * @return string
     */
    public String deleteCategory()
    {
        try
        {
            String ids = this.getRequest().getParameter("ids");
            this.activitiCategoryService.deleteActivitiCategory(ids);
            JsonUtil.outJson("{success:true,msg:'删除种类成功！'}");
        }
        catch (ServiceException e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "删除流程模板种类", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("删除流程模板种类失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "删除流程模板种类", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("删除流程模板种类失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'删除种类失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getCategoryById
     * @author zhxh
     * @Description: 通过ID获取流程种类
     * @date 2013-12-26
     * @return String
     */
    public String getCategoryById()
    {
        try
        {
            String id = this.getRequest().getParameter("id");
            Map paramMap = new HashMap();
            paramMap.put("categoryId", Integer.valueOf(id));
            TreeNode t = new TreeNode();
            List<ActivitiCategory> categorys =
                this.activitiCategoryService.getActivitiCategory(paramMap);
            Map map = new HashMap();
            if (categorys.size() > 0)
            {
                ActivitiCategory c = categorys.get(0);
                t.setNodeId(c.getCategoryId());
                t.setText(c.getName());
                t.setType(c.getCode());
            }
            map.put("data", t);
            JsonUtil.outJson(map);
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "通过ID获取流程模板种类", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("通过ID获取流程模板种类失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'获取数据失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getCategoryTree
     * @author zhxh
     * @Description:获取种类属性结构
     * @date 2013-12-26
     * @return string
     */
    public String getCategoryTree()
    {
        try
        {
            Map map = new HashMap();
            int parentId =
                Integer.valueOf(this.getRequest().getParameter("nodeId"));
            String code = this.getRequest().getParameter("type");
            String checkedFlag = this.getRequest().getParameter("checkedFlag");
            map.put("checkedFlag", checkedFlag);
            if (StringUtils.isNotBlank(code))
            {
                map.put("code", code);
            }
            else
            {
                map.put("parentId", parentId);
            }
            List<Map> datas = this.activitiCategoryService.getBPMType(map);
            JsonUtil.outJsonArray(datas);
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "获取流程模板种类", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("获取流程模板种类失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'获取流程模板种类失败!'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getCategoryTree
     * @author zhxh
     * @Description:获取种类属性结构
     * @date 2013-12-26
     * @return string
     */
    public String getCategoryTreeByCheck()
    {
        try
        {
            Map map = new HashMap();
            List<Map> datas = this.activitiCategoryService.getBPMTypeByCheck(map);
            JsonUtil.outJsonArray(datas);
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "获取流程模板种类", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("获取流程模板种类失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'获取流程模板种类失败!'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getBPMType
     * @author zhxh
     * @Description:获取流程模板种类树形，针对flex
     * @date 2013-12-26
     * @return String
     */
    public String getBPMType()
    {
        try
        {
            Map map = new HashMap();
            List<Map> datas = this.activitiCategoryService.getBPMType(map);
            map.clear();
            map.put("data", datas);
            JsonUtil.outJson(map);
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "获取流程模板种类树形", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("获取流程模板种类树形失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'获取类型失败！'}");
        }
        return null;
    }
    
    public ActivitiCategory getCategory()
    {
        return category;
    }
    
    public void setCategory(ActivitiCategory category)
    {
        this.category = category;
    }
    
}
