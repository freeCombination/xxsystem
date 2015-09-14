/**
 * @文件名 ActivitiDefineTemplateAction.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板控制层
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dqgb.sshframe.bpm.entity.ActivitiDefineTemplate;
import com.dqgb.sshframe.bpm.service.IActivitiDefineTemplateService;
import com.dqgb.sshframe.bpm.vo.ActivitiDefineTemplateVo;
import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.util.RequestUtil;
import com.dqgb.sshframe.common.vo.ListVo;

/**
 * 
 * 流程模板控制层
 * 
 * @author zhxh
 * @version V1.20,2013-12-25 下午5:12:47
 * @since V1.20
 */
public class ActivitiDefineTemplateAction extends BaseAction
{
    private static final long serialVersionUID = 1L;
    
    /** 流程模板逻辑层接口 */
    @Autowired
    @Qualifier("activitiDefineTemplateService")
    private IActivitiDefineTemplateService activitiDefineTemplateService;
    
    /**
     * 
     * @Title addActivitiDefineTemplate
     * @author zhxh
     * @Description:添加流程模板
     * @date 2013-12-26
     * @return String
     */
    public String addActivitiDefineTemplate()
    {
        try
        {
            String xmlContent = this.getRequest().getParameter("xmlContent");
            if (xmlContent == null)
            {
                JsonUtil.outJson("{success:false,msg:'保存失败！没有获取到xml值'}");
                return null;
            }
            
            this.activitiDefineTemplateService.
            addActivitiDefineTemplate(xmlContent);
            JsonUtil.outJson("{success:true,msg:'添加成功！'}");
        }
        catch (ServiceException e)
        {
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'保存失败！'}");
        }
        
        return null;
        
    }
    
    /**
     * 
     * @Title checkActivitiDefineTemplate
     * @author zhxh
     * @Description:检查模板编码存在与否
     * @date 2013-12-27
     * @return String
     */
    public String checkActivitiDefineTemplate()
    {
        String processDefineKey =
            this.getRequest().getParameter("processDefineKey");
        if (StringUtils.isBlank(processDefineKey))
        {
            JsonUtil.outJson("{success:false,msg:'编码不能为空！'}");
            return null;
        }
        Map map = new HashMap();
        map.put("processDefineKey", processDefineKey);
        map.put("isDelete", 0);
        List<ActivitiDefineTemplate> templates =
            this.activitiDefineTemplateService
             .getActivitiDefineTemplate(map);
        if (templates.size() < 1)
        {
            JsonUtil.outJson("{success:true,msg:'编码可以使用！'}");
        }
        else
        {
            JsonUtil.outJson("{success:false,msg:'编码已经存在！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title modifyActivitiDefineTemplatePre
     * @author zhxh
     * @Description:修改之前获取相关流程模板数据
     * @date 2013-12-27
     * @return String
     */
    public String modifyActivitiDefineTemplatePre()
    {
        String processDefineKey =
            this.getRequest().getParameter("processDefineKey");
        if (StringUtils.isBlank(processDefineKey))
        {
            JsonUtil.outJson("{success:false,msg:'processDefineKey编码不能为空！'}");
            return null;
        }
        try
        {
            Map map = new HashMap();
            map.put("processDefineKey", processDefineKey);
            map.put("isDelete", 0);
            List<ActivitiDefineTemplate> templates =
                this.activitiDefineTemplateService.getActivitiDefineTemplate(map);
            if (templates.size() < 1)
            {
                JsonUtil.outJson("{success:false,msg:'数据不存在！'}");
                return null;
            }
            else
            {
                String fileUrl =
                    ServletActionContext.getServletContext().getRealPath("/")
                        + templates.get(0).getUrl();
                InputStream in = new FileInputStream(new File(fileUrl));
                OutputStream out = this.getResponse().getOutputStream();
                byte b[] = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) != -1)
                {
                    out.write(b, 0, len);
                }
                out.flush();
                out.close();
            }
            
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'获取数据失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title updateActivitiDefineTemplate
     * @author zhxh
     * @Description:修改流程模板
     * @date 2013-12-27
     * @return String
     */
    public String updateActivitiDefineTemplate()
    {
        try
        {
            String xmlContent = this.getRequest().getParameter("xmlContent");
            if (xmlContent == null)
            {
                JsonUtil.outJson("{success:false,msg:'修改失败！没有获取到xml值'}");
                return null;
            }
            this.activitiDefineTemplateService.
             updateActivitiDefineTemplate(xmlContent);
            
            JsonUtil.outJson("{success:true,msg:'保存成功！'}");
        }
        catch (ServiceException e)
        {
            JsonUtil.outJson("{success:false,msg:'" + e.getMessageKey() + "'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'保存失败！'}");
        }
        
        return null;
        
    }
    
    /**
     * 
     * @Title deleteActivitiDefineTemplate
     * @author zhxh
     * @Description: 删除流程模板
     * @date 2013-12-27
     * @return String
     */
    public String deleteActivitiDefineTemplate()
    {
        String ids = this.getRequest().getParameter("ids");
        try
        {
            this.activitiDefineTemplateService.
            deleteActivitiDefineTemplate(ids);
            JsonUtil.outJson("{success:true,msg:'删除成功！'}");
            
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'删除失败！'}");
        }
        return null;
    }
    
    /**
     * 
     * @Title getActivitiDefineTemplateInfo
     * @author zhxh
     * @Description:查看流程模板数据
     * @date 2013-12-27
     * @return
     */
    public String getActivitiDefineTemplateInfo()
    {
        String id = this.getRequest().getParameter("id");
        Map map = new HashMap();
        map.put("id", Integer.valueOf(id));
        map.put("isDelete", 0);
        List<ActivitiDefineTemplate> templates =
            this.activitiDefineTemplateService.getActivitiDefineTemplate(map);
        if (templates.size() < 1)
        {
            throw new ServiceException("数据不存在!");
        }
        ActivitiDefineTemplate template = templates.get(0);
        ActivitiDefineTemplateVo vo = new ActivitiDefineTemplateVo();
        BeanCopier copier =
            BeanCopier.create(ActivitiDefineTemplate.class,
                ActivitiDefineTemplateVo.class,
                false);
        copier.copy(template, vo, null);
        if (template.getCategory() != null
            && template.getCategory().getIsDelete() == 0)
        {
            vo.setCategoryName(template.getCategory().getName());
        }
        this.getRequest().setAttribute("activitiDefineTemplate", vo);
        return SUCCESS;
    }
    
    /**
     * 
     * @Title getActivitiDefineTemplateByPage
     * @author zhxh
     * @Description:分页查询流程模板
     * @date 2013-12-27
     * @return String
     */
    public String getActivitiDefineTemplateByPage()
    {
        try
        {
            Map map = new HashMap();
            Field fields[] = ActivitiDefineTemplate.class.getDeclaredFields();
            for (Field field : fields)
            {
                String tempValue =
                    this.getRequest().getParameter(field.getName());
                if (StringUtils.isNotBlank(tempValue))
                {
                    if (field.getType() == int.class)
                    {
                        map.put(field.getName(), Integer.valueOf(tempValue));
                    }
                    else if (field.getType() == String.class)
                    {
                        map.put(field.getName(), tempValue);
                    }
                }
                
            }
            String categoryId = this.getRequest().getParameter("categoryId");
            String templateName =
                this.getRequest().getParameter("templateName");
            map.put("categoryId", categoryId);
            map.put("templateName", templateName);
            int start =
                Integer.valueOf(this.getRequest().getParameter("start"));
            int limit =
                Integer.valueOf(this.getRequest().getParameter("limit"));
            map.put("start", start);
            map.put("limit", limit);
            ListVo listVo =
                this.activitiDefineTemplateService.
                getActivitiDefineTemplateByPage(map);
            JsonUtil.outJson(listVo);
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'获取类型失败！'}");
        }
        return null;
    }
    
    /**
     * 判断是否有活动的流程模板实例
     *
     * @Title isProcessExist
     * @author 王龙涛
     * @Description: 
     * @date 2014-11-27
     */ 
    public void isProcessExist(){
        Map<String, String> params = RequestUtil.getParameterMap(getRequest());
        JsonUtil.outJson(activitiDefineTemplateService.isProcessExist(params));
    }
    
}
