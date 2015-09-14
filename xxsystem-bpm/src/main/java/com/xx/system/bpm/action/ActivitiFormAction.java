/**
 * @文件名 ActivitiFormAction.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程表单的控制层
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

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.bpm.entity.ActivitiForm;
import com.dqgb.sshframe.bpm.service.IActivitiFormService;
import com.dqgb.sshframe.bpm.vo.ActivitiFormVo;
import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.org.action.OrgAction;

/**
 * 
 * 流程表单的控制层
 * 
 * @author zhxh
 * @version V1.20,2013-12-25 下午5:12:47
 * @since V1.20
 */
public class ActivitiFormAction extends BaseAction
{
    private static final long serialVersionUID = 1L;
    
    /** 流程表单逻辑层接口 */
    @Autowired
    @Qualifier("activitiFormService")
    private IActivitiFormService activitiFormService;
    
    /** 流程表单实体 */
    private ActivitiForm activitiForm;
    
    public ActivitiForm getActivitiForm()
    {
        return activitiForm;
    }
    
    public void setActivitiForm(ActivitiForm activitiForm)
    {
        this.activitiForm = activitiForm;
    }
    
    /**
     * 
     * @Title addActivitiForm
     * @author zhxh
     * @Description:添加流程表单
     * @date 2013-12-26
     * @return String
     */
    public String addActivitiForm()
    {
        try
        {
            String categoryIds = this.getRequest().getParameter("categoryIds");
            String[] categoryIdArray = categoryIds.split(",");
            List<ActivitiCategory> categories =
                new ArrayList<ActivitiCategory>();
            for (String categoryId : categoryIdArray)
            {
                ActivitiCategory category = new ActivitiCategory();
                category.setCategoryId(Integer.valueOf(categoryId));
                categories.add(category);
            }
            activitiForm.setCategories(categories);
            this.activitiFormService.addActivitiForm(activitiForm);
            this.excepAndLogHandle(ActivitiFormAction.class, "添加表单地址", null, true);
            JsonUtil.outJson("{success:true,msg:'添加成功！'}");
        }
        catch (ServiceException e)
        {
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
        }
        catch (Exception e)
        {
            if (e.getCause() instanceof ConstraintViolationException)
            {
                this.excepAndLogHandle(ActivitiFormAction.class, "添加表单地址失败，请检查是否有种类不存在！", null, false);
                JsonUtil.outJson("{success:false,msg:'失败，请检查是否有种类不存在！'}");
            }
            else
            {
                JsonUtil.outJson("{success:false,msg:'失败，请稍后重试！'}");
            }
        }
        
        return null;
    }
    
    /**
     * 
     * @Title updateActivitiForm
     * @author zhxh
     * @Description:修改表单地址
     * @date 2013-12-26
     * @return String
     */
    public String updateActivitiForm()
    {
        try
        {
            String categoryIds = this.getRequest().getParameter("categoryIds");
            String[] categoryIdArray = categoryIds.split(",");
            List<ActivitiCategory> categories =
                new ArrayList<ActivitiCategory>();
            for (String categoryId : categoryIdArray)
            {
                ActivitiCategory category = new ActivitiCategory();
                category.setCategoryId(Integer.valueOf(categoryId));
                categories.add(category);
            }
            activitiForm.setAdaptationNode(","
                + activitiForm.getAdaptationNode() + ",");
            activitiForm.setCategories(categories);
            this.activitiFormService.updateActivitiForm(activitiForm);
            this.excepAndLogHandle(ActivitiFormAction.class, "修改表单地址", null, true);
            JsonUtil.outJson("{success:true,msg:'修改成功！'}");
        }
        catch (ServiceException e)
        {
            this.excepAndLogHandle(ActivitiFormAction.class, "修改表单地址失败，"+e.getMessageKey(), e, false);
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
        }
        catch (Exception e)
        {
            if (e.getCause() instanceof ConstraintViolationException)
            {
                JsonUtil.outJson("{success:false,msg:'失败，请检查是否有种类不存在！'}");
                this.excepAndLogHandle(ActivitiFormAction.class, "修改表单地址失败，请检查是否有种类不存在！", e, false);
            }
            else
            {
                JsonUtil.outJson("{success:false,msg:'失败，请稍后重试！'}");
            }
        }
        return null;
    }
    
    /**
     * 
     * @Title toViewActivitiForm
     * @author zhxh
     * @Description:查看表单地址
     * @date 2013-12-26
     * @return string
     */
    public String toViewActivitiForm()
    {
        try
        {
            String id = this.getRequest().getParameter("id");
            Map map = new HashMap();
            map.put("id", Integer.valueOf(id));
            List<ActivitiForm> forms =
                this.activitiFormService.getActivitiForm(map);
            if (forms.size() < 1)
            {
                throw new ServiceException("数据已经不存在!");
            }
              activitiForm = forms.get(0);
            Map<String, String> nodeMap = new HashMap<String, String>();
                String nodeFile =
                    ServletActionContext.getServletContext().getRealPath("/")
                        + SystemConstant.UPLOAD_FOLDER_ACTIVITI
                        + File.separator + "flowNode.js";
                File file = new File(nodeFile);
                StringWriter writer = new StringWriter();
                BufferedReader reader =
                    new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), "UTF-8"));
                String str = null;
                while ((str = reader.readLine()) != null)
                {
                    writer.write(str.trim());
                }
                String jsonString = writer.toString();
                reader.close();
                writer.close();
                JSONArray jsonArray = JSONArray.fromObject(jsonString);
                for (Iterator it = jsonArray.iterator(); it.hasNext();)
                {
                    JSONObject object = (JSONObject)it.next();
                    repeateChild(object, nodeMap);
                }
                this.getAdaptationNode(nodeMap,activitiForm);
           
        }
        catch (ServiceException e)
        {
            throw new ServiceException(e.getMessageKey());
        }
        catch (Exception e)
        {
            throw new ServiceException("获取数据失败，请稍后重试!");
        }
        return SUCCESS;
    }
    /**
     * 
    * @Title getAdaptationNode
    * @author zhxh
    * @Description: 获取表单上的node
    * @date 2014-1-15
    * @param nodeMap
    * @param activitiForm
     */
    private void getAdaptationNode(Map<String, String>  nodeMap,
        ActivitiForm activitiForm)
    {
            StringBuffer nodesBuffer = new StringBuffer();
            if (StringUtils.isNotBlank(activitiForm.getAdaptationNode()))
            {
                if (activitiForm.getAdaptationNode().startsWith(","))
                {
                    activitiForm.setAdaptationNode(
                        activitiForm.getAdaptationNode()
                        .substring(1));
                }
                if (activitiForm.getAdaptationNode().endsWith(","))
                {
                    activitiForm.setAdaptationNode(
                        activitiForm.getAdaptationNode()
                        .substring(0,
                          activitiForm.getAdaptationNode().length() - 1));
                }
                if (StringUtils.isNotBlank(activitiForm.getAdaptationNode()))
                {
                    String strs[] =
                        activitiForm.getAdaptationNode().split(",");
                    nodesBuffer.delete(0, nodesBuffer.length());
                    for (String str : strs)
                    {
                        if (StringUtils.isNotBlank(nodeMap.get(str)))
                        {
                            nodesBuffer.append(nodeMap.get(str))
                                .append(",");
                        }
                    }
                    if (nodesBuffer.length() > 0)
                    {
                        activitiForm.setAdaptationNode(nodesBuffer.substring(0,
                            nodesBuffer.length() - 1));
                    }
                }
            }
    }
    /**
     * 
    * @Title getAdaptationNode
    * @author zhxh
    * @Description: 获取表单上的node
    * @date 2014-1-15
    * @param nodeMap
    * @param activitiForm
     */
    private void getAdaptationNode(Map<String, String>  nodeMap,
        List<ActivitiFormVo> vos)
    {
        StringBuffer nodesBuffer = new StringBuffer();
        for (ActivitiFormVo vo : vos)
        {
            if (StringUtils.isNotBlank(vo.getAdaptationNode()))
            {
                if (vo.getAdaptationNode().startsWith(","))
                {
                    vo.setAdaptationNode(vo.getAdaptationNode()
                        .substring(1));
                }
                if (vo.getAdaptationNode().endsWith(","))
                {
                    vo.setAdaptationNode(vo.getAdaptationNode()
                        .substring(0,
                            vo.getAdaptationNode().length() - 1));
                }
                if (StringUtils.isNotBlank(vo.getAdaptationNode()))
                {
                    String strs[] = vo.getAdaptationNode().split(",");
                    nodesBuffer.delete(0, nodesBuffer.length());
                    for (String nodeKey : strs)
                    {
                        if (StringUtils.isNotBlank(nodeMap.get(nodeKey)))
                        {
                            nodesBuffer.append(nodeMap.get(nodeKey))
                                .append(",");
                        }
                    }
                    if (nodesBuffer.length() > 0)
                    {
                        vo.setAdaptationName(nodesBuffer.substring(0,
                            nodesBuffer.length() - 1));
                    }
                }
            }
        }
    }
    /**
     * 
     * @Title getActivitiFormByPage
     * @author zhxh
     * @Description:分页查询流程表单
     * @date 2013-12-26
     * @return String
     */
    public String getActivitiFormByPage()
    {
        try
        {
            Map map = new HashMap();
            String fromName = this.getRequest().getParameter("formName");
            String categoryId = this.getRequest().getParameter("categoryId");
            String adaptationNode =
                this.getRequest().getParameter("adaptationNode");
            String limit = this.getRequest().getParameter("limit");
            String start = this.getRequest().getParameter("start");
            Map<String, String> nodeMap = new HashMap<String, String>();
            map.put("formName", fromName);
            map.put("categoryId", categoryId);
            map.put("adaptationNode", adaptationNode);
            map.put("limit", limit);
            map.put("start", start);
            ListVo<ActivitiFormVo> listVo =
                this.activitiFormService.getActivitiFormByPage(map);
                String nodeFile =
                    ServletActionContext.getServletContext().getRealPath("/")
                        + SystemConstant.UPLOAD_FOLDER_ACTIVITI
                        + File.separator + "flowNode.js";
                File file = new File(nodeFile);
                StringWriter writer = new StringWriter();
                BufferedReader reader =
                    new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), "UTF-8"));
                String str = null;
                while ((str = reader.readLine()) != null)
                {
                    writer.write(str.trim());
                }
                String jsonString = writer.toString();
                reader.close();
                writer.close();
                JSONArray jsonArray = JSONArray.fromObject(jsonString);
                for (Iterator it = jsonArray.iterator(); it.hasNext();)
                {
                    JSONObject object = (JSONObject)it.next();
                    repeateChild(object, nodeMap);
                }
               this.getAdaptationNode(nodeMap, listVo.getList());
               JsonUtil.outJson(listVo);
        }
        catch (ServiceException e)
        {
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'失败，请稍后重试！'}");
        }
        
        return null;
    }
    
    /**
     * 
     * @Title repeateChild
     * @author zhxh
     * @Description:循环chidren，取值
     * @date 2013-12-26
     * @param object
     * @param map
     */
    private void repeateChild(JSONObject object, Map<String, String> map)
    {
        map.put(object.getString("nodeId"), object.getString("text"));
        if (object.get("children") != null)
        {
            for (Iterator it = object.getJSONArray("children").
                iterator(); it.hasNext();)
            {
                JSONObject temp = (JSONObject)it.next();
                repeateChild(temp, map);
            }
        }
    }
    
    /**
     * 
     * @Title deleteActivitiForm
     * @author zhxh
     * @Description:删除表单地址
     * @date 2013-12-26
     */
    public void deleteActivitiForm()
    {
        try
        {
            String ids = this.getRequest().getParameter("ids");
            this.activitiFormService.deleteActivitiForm(ids);
            this.excepAndLogHandle(ActivitiFormAction.class, "删除表单地址", null, true);
            JsonUtil.outJson("{success:true,msg:'删除成功！'}");
        }
        catch (Exception e)
        {
            this.excepAndLogHandle(ActivitiFormAction.class, "删除表单地址失败", null, false);
            JsonUtil.outJson("{success:false,msg:'删除失败，请稍后重试！'}");
        }
    }
    
}
