/**
 * @文件名 ActivitiCategoryDaoImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板种类持久层实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dqgb.sshframe.bpm.dao.IActivitiCategoryDao;
import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.common.dao.impl.BaseDaoHibernateImpl;

/**
 * 
 * 流程种类持久层实现
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 下午1:51:37
 * @since V1.20
 */
@Repository("activitiCategoryDao")
public class ActivitiCategoryDaoImpl extends BaseDaoHibernateImpl implements
    IActivitiCategoryDao
{
    
    @Override
    public List<ActivitiCategory> getActivitiCategory(Map paramMap)
    {
        StringBuffer hql = new StringBuffer("from ActivitiCategory where 1=1 ");
        if (paramMap != null)
        {
            Iterator<?> it = paramMap.keySet().iterator();
            while (it.hasNext())
            {
                Object key = it.next();
                hql.append(" and ")
                    .append(key.toString())
                    .append("=")
                    .append(":")
                    .append(key.toString());
            }
        }
        return (List<ActivitiCategory>)this.queryEntitysByMap(hql.toString(),
            paramMap);
        
    }
    
    @Override
    public void addActivitiCategory(ActivitiCategory category)
    {
        this.addEntity(category);
        
    }
    
    @Override
    public List<ActivitiCategory> getLevelCategory(int id)
    {
        List<ActivitiCategory> result = new ArrayList();
        StringBuffer hql = new StringBuffer();
        ActivitiCategory c =
            (ActivitiCategory)this.queryEntityById(ActivitiCategory.class, id);
        while (c != null && c.getParentId() != 0)
        {
            ActivitiCategory temp =
                (ActivitiCategory)this.queryEntityById(ActivitiCategory.class,
                    c.getParentId());
            if (temp != null)
            {
                result.add(temp);
            }
            c = temp;
        }
        return result;
    }
    
    @Override
    public void deleteActivitiCategory(String ids)
    {
        StringBuffer childrenIds = new StringBuffer();
        String currentIds = "";
        List<ActivitiCategory> datas =
            (List<ActivitiCategory>)this.queryEntitys("from  ActivitiCategory " +
            		"  where isDelete=0 and parentId in("
                      + ids + ")");
        if (datas.size() > 0)
        {
            for (ActivitiCategory c : datas)
            {
                childrenIds.append(c.getCategoryId()).append(",");
            }
            currentIds = childrenIds.substring(0, childrenIds.length() - 1);
        }
        while (datas != null && datas.size() > 0)
        {
            datas.clear();
            datas =
                (List<ActivitiCategory>)this.queryEntitys("from  ActivitiCategory " +
                		"  where isDelete=0 and parentId in("
                    + currentIds + ")");
            if (datas.size() > 0)
            {
                for (ActivitiCategory c : datas)
                {
                    childrenIds.append(c.getCategoryId()).append(",");
                    currentIds = c.getCategoryId() + ",";
                }
                currentIds = currentIds.substring(0, currentIds.length() - 1);
            }
        }
        childrenIds.append(ids);
        this.executeHql("delete from  ActivitiCategory  where id in("
            + childrenIds.toString() + ")");
        
    }
    
    @Override
    public void updateActivitiCategory(ActivitiCategory category)
    {
        this.updateEntity(category);
    }
    
    @Override
    public List<ActivitiCategory> getCategoryById(String ids)
    {
        Map map = new HashMap();
        map.put("isDelete", 0);
        List<ActivitiCategory> categories =
            (List<ActivitiCategory>)this.queryEntitysByMap("from ActivitiCategory " +
            		"  where isDelete=:isDelete and id in("
                + ids + ")",
                map);
        return categories;
    }
}
