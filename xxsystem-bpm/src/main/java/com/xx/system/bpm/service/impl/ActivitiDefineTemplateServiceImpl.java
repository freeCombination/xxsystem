/**
 * @文件名 ActivitiDefineTemplateServiceImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板种类（类型）的逻辑层实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dqgb.sshframe.bpm.dao.IActivitiDefineTemplateDao;
import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.bpm.entity.ActivitiDefineTemplate;
import com.dqgb.sshframe.bpm.service.IActivitiAndBusinessService;
import com.dqgb.sshframe.bpm.service.IActivitiCategoryService;
import com.dqgb.sshframe.bpm.service.IActivitiDefineTemplateService;
import com.dqgb.sshframe.bpm.vo.ActivitiDefineTemplateVo;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.util.XMLUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.dict.service.IDictService;
import com.dqgb.sshframe.log.service.ILogService;

@Transactional(readOnly = true)
@Service("activitiDefineTemplateService")
public class ActivitiDefineTemplateServiceImpl implements
    IActivitiDefineTemplateService
{
    /**
     * @Fields baseDao : 数据库操作基础方法
     */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    /** 流程模板持久层接口 */
    @Autowired
    @Qualifier("activitiDefineTemplateDao")
    private IActivitiDefineTemplateDao activitiDefineTemplateDao;
    
    /** 流程种类服务层接口 */
    @Autowired
    @Qualifier("activitiCategoryService")
    private IActivitiCategoryService activitiCategoryService;
    
    /** 流程业务服务层接口 */
    @Autowired
    @Qualifier("activitiAndBusinessService")
    private IActivitiAndBusinessService activitiAndBusinessService;
    
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
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addActivitiDefineTemplate(String xmlContent)
        throws ServiceException
    {
        try
        {
            ActivitiDefineTemplate activitiDefineTemplate =
                validAndSaveTemplateXml(xmlContent);
            this.activitiDefineTemplateDao.addActivitiDefineTemplate(activitiDefineTemplate);
        }
        catch (ServiceException e)
        {
            throw new ServiceException(e.getMessageKey(),e);
        }
        catch (Exception e)
        {
            throw new ServiceException("保存失败！",e);
        }
    }
    
    @Override
    public List<ActivitiDefineTemplate> getActivitiDefineTemplate(Map map)
        throws ServiceException
    {
        try
        {
            return this.activitiDefineTemplateDao.getActivitiDefineTemplate(map);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateActivitiDefineTemplate(String xmlContent)
    {
        ActivitiDefineTemplate activitiDefineTemplate =
            validAndSaveTemplateXml(xmlContent);
        ActivitiDefineTemplate activitiDefineTemplateUpdate = null;
        Map map = new HashMap();
        map.put("processDefineKey",
            activitiDefineTemplate.getProcessDefineKey());
        map.put("isDelete", 0);
        List<ActivitiDefineTemplate> templates =
            this.getActivitiDefineTemplate(map);
        if (templates.size() > 0)
        {
            activitiDefineTemplateUpdate = templates.get(0);
        }
        if (activitiDefineTemplateUpdate == null)
        {
            throw new ServiceException("不存在模板数据");
        }
        activitiDefineTemplateUpdate.setName(activitiDefineTemplate.getName());
        activitiDefineTemplateUpdate.setRealName(activitiDefineTemplate.getRealName());
        activitiDefineTemplateUpdate.setProcessDefineKey(activitiDefineTemplate.getProcessDefineKey());
        activitiDefineTemplateUpdate.setCategory(activitiDefineTemplate.getCategory());
        activitiDefineTemplateUpdate.setUrl(activitiDefineTemplate.getUrl());
        this.activitiDefineTemplateDao.updateActivitiDefineTemplate(activitiDefineTemplateUpdate);
        
    }
    
    /**
     * 
     * @Title validAndSaveTemplateXml
     * @author zhxh
     * @Description:校验并且保存xml数据
     * @date 2013-12-27
     * @return ActivitiDefineTemplate
     * @throws ServiceException
     */
    private ActivitiDefineTemplate validAndSaveTemplateXml(String xmlContent)
    {
        try
        {
            StringReader reader = new StringReader(xmlContent);
            if (reader == null)
            {
                throw new ServiceException("保存失败！没有获取到xml值");
            }
            Document document = new SAXReader().read(reader);
            Element root = document.getRootElement();
            List<Element> resultList = new ArrayList<Element>();
            if ("process".equals(root.getQName().getName().trim()))
            {
                resultList.add(root);
            }
            XMLUtil.getElementList(resultList, root, "process");
            if (resultList.size() < 1)
            {
                throw new ServiceException("保存失败！没有获取到xml中process节点");
            }
            String bpmType = resultList.get(0).attributeValue("bpmType");
            String name = resultList.get(0).attributeValue("name");
            String processDefineKey = resultList.get(0).attributeValue("id");
            if (StringUtils.isBlank(bpmType))
            {
                throw new ServiceException("保存失败！没有获取到xml中process节点中的bpmType值");
            }
            String realName = UUID.randomUUID().toString();
            String fileName = realName + ".bpmn20.xml";
            Map map = new HashMap();
            map.put("categoryId", Integer.valueOf(bpmType));
            map.put("isDelete", 0);
            List<ActivitiCategory> list =
                this.activitiCategoryService.getActivitiCategory(map);
            if (list.size() < 1)
            {
                throw new ServiceException("模版种类不存在，请确认！");
            }
            String fileAbsoluteUrl = "";
            map =  this.activitiCategoryService.createBpmFolder(list.get(0));
            fileAbsoluteUrl = (String)map.get("absoluteUrl")
                + File.separator + fileName;
            fileAbsoluteUrl = fileAbsoluteUrl.replaceAll("/", "\\\\");
            XMLUtil.writeXml(document, fileAbsoluteUrl);
            String deploymentId =
                activitiAndBusinessService.deployProcessDefinitionByXml(fileAbsoluteUrl);
            activitiAndBusinessService.deleteDeployMent(deploymentId, true);
            ActivitiDefineTemplate activitiDefineTemplate =
                new ActivitiDefineTemplate();
            activitiDefineTemplate.setName(name);
            activitiDefineTemplate.setRealName(realName);
            activitiDefineTemplate.setProcessDefineKey(processDefineKey);
            activitiDefineTemplate.setCategory(list.get(0));
            activitiDefineTemplate.setUrl((SystemConstant.UPLOAD_FOLDER_ACTIVITI
                + File.separator + (String)map.get("relativeUrl")
                + File.separator + fileName).replaceAll("/",
                "\\\\"));
            return activitiDefineTemplate;
        }
        catch (DocumentException e)
        {
            throw new ServiceException("读取报文失败！",e);
        }
        catch (IOException e)
        {
            throw new ServiceException("写报文失败！",e);
        }
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteActivitiDefineTemplate(String ids)
        throws ServiceException
    {
        try
        {
            
            this.activitiDefineTemplateDao.deleteActivitiDefineTemplate(ids);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }
    
    @Override
    public ListVo getActivitiDefineTemplateByPage(Map map)
        throws ServiceException
    {
        List<ActivitiDefineTemplateVo> vos = new ArrayList();
        BeanCopier copier =
            BeanCopier.create(ActivitiDefineTemplate.class,
                ActivitiDefineTemplateVo.class,
                false);
        try
        {
            Map tempMap = new HashMap();
            ListVo listVo =
                this.activitiDefineTemplateDao.getActivitiDefineTemplateByPage(map);
            List<ActivitiDefineTemplate> datas = listVo.getList();
            for (ActivitiDefineTemplate a : datas)
            {
                ActivitiDefineTemplateVo vo = new ActivitiDefineTemplateVo();
                copier.copy(a, vo, null);
                if (a.getCategory() != null
                    && a.getCategory().getIsDelete() == 0)
                {
                    map.put("categoryId", a.getCategory().getCategoryId());
                    map.put("isDelete", 0);
                    List<ActivitiCategory> temp =
                        this.activitiCategoryService.getActivitiCategory(map);
                    if (temp.size() > 0 && temp.get(0).getIsDelete() == 0)
                    {
                        vo.setCategoryName(temp.get(0).getName());
                    }
                    
                }
                vos.add(vo);
            }
            listVo.setList(vos);
            return listVo;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
        
    }
    
    
    /**
     * 判断是否存在活动的流程实例
     *
     * @Title isProcessExist
     * @author 王龙涛
     * @Description: 
     * @date 2014-11-27
     * @param params 
     * @return
     */ 
    public Map<String, Object> isProcessExist(Map<String, String> params){
        
        Map<String, Object> result = new HashMap<String, Object>();
        String templateCode = params.get("processDefineKey")+":"; //流程模板编码
        String sql ="select * from  ACT_HI_TASKINST where PROC_DEF_ID_ like '"+templateCode+"%' and DELETE_REASON_ is null";
        List results =baseDao.querySQLForList(sql);
        
        if(results!=null&&results.size()>0){
            result.put("exists", true);
        }else{
            result.put("exists", false);
        }
        
        return  result;
    }

    public IBaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
}
