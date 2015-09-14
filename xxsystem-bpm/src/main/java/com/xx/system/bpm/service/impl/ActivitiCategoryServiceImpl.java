/**
 * @文件名 ActivitiCategoryServiceImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板种类（类型）的逻辑层实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dqgb.sshframe.bpm.dao.IActivitiCategoryDao;
import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.bpm.service.IActivitiCategoryService;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.exception.ServiceException;

@Transactional(readOnly = true)
@Service("activitiCategoryService")
public class ActivitiCategoryServiceImpl implements IActivitiCategoryService
{
    @Autowired
    @Qualifier("activitiCategoryDao")
    /**流程种类持久层接口*/
    private IActivitiCategoryDao activitiCategoryDao;
    
    @Override
    public List<ActivitiCategory> getActivitiCategory(Map paramMap)
        throws ServiceException
    {
        try
        {
            return activitiCategoryDao.getActivitiCategory(paramMap);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(),e);
        }
        
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addActivitiCategory(ActivitiCategory category)
        throws ServiceException
    {
        try
        {
            Map paramMap = new HashMap();
            paramMap.put("code", category.getCode());
            paramMap.put("isDelete", 0);
            List list = this.getActivitiCategory(paramMap);
            if (list.size() > 0)
            {
                throw new ServiceException("类型编码已经存在！");
            }
            if (category.getParentId() != 0)
            {
                paramMap.clear();
                paramMap.put("categoryId", category.getParentId());
                paramMap.put("isDelete", 0);
                list = this.getActivitiCategory(paramMap);
                if (list.size() < 1)
                {
                    throw new ServiceException("父级类型已经不存在！");
                }
            }
            this.activitiCategoryDao.addActivitiCategory(category);
            this.createBpmFolder(category);
        }
        catch (ServiceException e)
        {
            throw new ServiceException(e.getMessageKey(),e);
        }
        catch (Exception e)
        {
            throw new ServiceException("保存失败!",e);
        }
        
    }
    
    @Override
    public List<ActivitiCategory> getLevelActivitiCategory(int id)
        throws ServiceException
    {
        try
        {
            return activitiCategoryDao.getLevelCategory(id);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteActivitiCategory(String ids)
        throws ServiceException
    {
        try
        {
            activitiCategoryDao.deleteActivitiCategory(ids);
        }
        catch (Exception e)
        {
            if (e.getCause() instanceof ConstraintViolationException)
            {
                throw new ServiceException("请检查是否存在比如表单等业务数据关联了此种类以及其子种类!",e);
            }
            throw new ServiceException("删除失败，请稍后重试!",e);
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateActivitiCategory(ActivitiCategory category)
        throws ServiceException
    {
        try
        {
            List list =
                this.activitiCategoryDao.queryEntitys(
                    "from ActivitiCategory " +
                		" where id=? and isDelete=0",
                    new Object[] {category.getCategoryId()});
            if (list.size() < 1)
            {
                throw new ServiceException("数据不存在");
            }
            ActivitiCategory tempCategory = (ActivitiCategory)list.get(0);
            
            if (!category.getCode().equals(tempCategory.getCode()))
            {// 修改了code
                Map paramMap = new HashMap();
                paramMap.put("code", category.getCode());
                List<ActivitiCategory> temps =
                    this.activitiCategoryDao.queryEntitysByMap(
                        "from ActivitiCategory where id!="
                        + tempCategory.getCategoryId()
                        + " and code=:code and isDelete=0",
                        paramMap);
                if (temps.size() > 0)
                {
                    throw new ServiceException("系统已经存在修改的编码！");
                }
                this.createBpmFolder(category);
            }
            
            tempCategory.setName(category.getName());
            tempCategory.setParentId(category.getParentId());
            tempCategory.setCode(category.getCode());
            tempCategory.setSort(category.getSort());
            tempCategory.setDescription(category.getDescription());
            activitiCategoryDao.updateActivitiCategory(tempCategory);
            
        }
        catch (ServiceException e)
        {
            throw new ServiceException(e.getMessageKey(),e);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }
    
    /**
     * 获取任务最上层的种类数据
     */
    @Override
    public ActivitiCategory getActivitiTopCategory(Integer categoryId)
    {
    	int pk_activiti_category_id = 0;
    	int firstId = categoryId;
    	do{
    		String hql = "from ActivitiCategory a where a.isDelete = 0 and a.categoryId = "+firstId;
    		List<ActivitiCategory> list = this.activitiCategoryDao.queryEntitys(hql);
    		//如果list.size>0并且父亲ID = 0，说明已经是跟节点
    		if(list.size()>0){
    			if(list.get(0).getParentId()==0){
        			pk_activiti_category_id = list.get(0).getCategoryId();
        		}else{
        			firstId = list.get(0).getParentId();
        		}
    		}else{
    			break;
    		} 
    	}while(pk_activiti_category_id != 0);
    	if(pk_activiti_category_id!=0){
    		Map paramMap = new HashMap();
            paramMap.put("isDelete", 0);
            paramMap.put("categoryId", pk_activiti_category_id);
            List<ActivitiCategory> categories =
                this.activitiCategoryDao.getActivitiCategory(paramMap);
            if (categories.size() > 0)
            {
                return categories.get(0);
            }
    	}
		return null;
    }
    
    @Override
    public List<Map> getBPMType(Map map)
        throws ServiceException
    {
        List<ActivitiCategory> datas = new ArrayList();
        List<Map> treeNodes = new ArrayList();
        Map tempMap = new HashMap();
        try
        {
            String checkedFlag = (String)map.get("checkedFlag");
            map.remove("checkedFlag");
            map.put("isDelete", 0);
            datas = this.activitiCategoryDao.getActivitiCategory(map);
            for (ActivitiCategory c : datas)
            {
                Map treeNode = new HashMap();
                treeNode.put("nodeId", c.getCategoryId());
                treeNode.put("parentId", c.getParentId());
                treeNode.put("text", c.getName());
                treeNode.put("type", c.getCode());
                treeNode.put("sort", c.getSort());
                treeNode.put("description", c.getDescription());
                tempMap.put("isDelete", 0);
                tempMap.put("parentId", c.getCategoryId());
                int count =
                    this.activitiCategoryDao.
                    queryTotalCount("from ActivitiCategory " +
                    		" where isDelete=:isDelete " +
                    		"  and parentId=:parentId",
                        tempMap);
                if (count <= 0)
                {
                    treeNode.put("leaf", true);
                }
                else
                {
                    treeNode.put("leaf", false);
                }
                if ("true".equals(checkedFlag))
                {
                    treeNode.put("checked", false);
                }
                
                treeNodes.add(treeNode);
                
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
        return treeNodes;
    }
    
    @Override
    public List<Map> getBPMTypeByCheck(Map map)
        throws ServiceException
    {
        List<ActivitiCategory> datas = new ArrayList();
        List<Map> treeNodes = new ArrayList();
        Map tempMap = new HashMap();
        try
        {
            String checkedFlag = (String)map.get("checkedFlag");
            map.remove("checkedFlag");
            map.put("isDelete", 0);
            datas = this.activitiCategoryDao.getActivitiCategory(map);
            for (ActivitiCategory c : datas)
            {
                Map treeNode = new HashMap();
                treeNode.put("nodeId", c.getCategoryId());
                treeNode.put("parentId", c.getParentId());
                treeNode.put("text", c.getName());
                treeNode.put("type", c.getCode());
                treeNode.put("sort", c.getSort());
                treeNode.put("description", c.getDescription());
                tempMap.put("isDelete", 0);
                tempMap.put("parentId", c.getCategoryId());
                int count =
                    this.activitiCategoryDao.
                    queryTotalCount("from ActivitiCategory " +
                            " where isDelete=:isDelete " +
                            "  and parentId=:parentId",
                        tempMap);
                if (count <= 0)
                {
                    treeNode.put("leaf", true);
                }
                else
                {
                    treeNode.put("leaf", false);
                }
                if ("true".equals(checkedFlag))
                {
                    treeNode.put("checked", false);
                }
                
                List<ActivitiCategory> pActivitiCategory = (List<ActivitiCategory>)this.activitiCategoryDao.getCategoryById(c.getParentId() + "");
                if(pActivitiCategory != null && pActivitiCategory.size() > 0){
                    treeNode.put("parentName", pActivitiCategory.get(0).getName());
                }
                treeNodes.add(treeNode);
                
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
        return treeNodes;
    }
    
    @Override
    public List<ActivitiCategory> getActivitiCategoryById(String ids)
    {
        
        return this.activitiCategoryDao.getCategoryById(ids);
        
    }
    

    @Override
    public Map createBpmFolder(ActivitiCategory category)
    {
        Map map = new HashMap();//相对路径，绝对路径
        
        List<ActivitiCategory> datas =
            this.getLevelActivitiCategory(Integer.valueOf(category.getCategoryId()));
       
        StringBuffer path = new StringBuffer();
        if (datas.size() > 0)
        {
            for (int i = datas.size() - 1; i >= 0; i--)
            {
                path.append(datas.get(i).getCode()).append("/");
            }
            
        }
        path.append(category.getCode());
        File pageTemp =
            new File(ServletActionContext.getServletContext()
                .getRealPath("/")
                + SystemConstant.PAGE_FOLDER_ACTIVITI
                + File.separator + path.toString());
        if (!pageTemp.exists() || !pageTemp.isDirectory())
        {
            pageTemp.mkdirs();
        }
        String floder =
            ServletActionContext.getServletContext().getRealPath("/")
                + SystemConstant.UPLOAD_FOLDER_ACTIVITI + File.separator
                + path.toString();
        File file = new File(floder);
        if (!file.exists() || !file.isDirectory())
        {
            file.mkdirs();
        }
        map.put("relativeUrl", path.toString());
        map.put("absoluteUrl", floder);
        return map;
    }
}
