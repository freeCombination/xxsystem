/**
 * @文件名 ActivitiFormDaoImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程表单持久层实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Repository;

import com.dqgb.sshframe.bpm.dao.IActivitiFormDao;
import com.dqgb.sshframe.bpm.entity.ActivitiForm;
import com.dqgb.sshframe.common.dao.impl.BaseDaoHibernateImpl;
import com.dqgb.sshframe.common.vo.ListVo;

/**
 * 
 * 流程表单持久层实现,查询，修改，删除，添加
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 下午2:16:42
 * @since V1.20
 * @depricated
 */
@Repository("activitiFormDao")
public class ActivitiFormDaoImpl extends BaseDaoHibernateImpl 
                      implements IActivitiFormDao
{
    @Override
    public void addActivitiForm(ActivitiForm activitiForm)
    {
        this.addEntity(activitiForm);
        
    }
    
    @Override
    public void updateActivitiForm(ActivitiForm activitiForm)
    {
        this.updateEntity(activitiForm);
        
    }
    
    @Override
    public List<ActivitiForm> getActivitiForm(Map map)
    {
        Map conditionMap = new HashMap();
        StringBuffer hql = new StringBuffer("from ActivitiForm where 1=1");
        this.getHqlCondition(hql, map, conditionMap);
        return (List<ActivitiForm>)this.queryEntitysByMap(hql.toString(),
            conditionMap);
    }
    
    @Override
    public ListVo<ActivitiForm> getActivitiFormByPage(Map map)
    {
        ListVo<ActivitiForm> listVo = new ListVo<ActivitiForm>();
        String formName = (String)map.get("formName");
        String categoryId = (String)map.get("categoryId");
        String adaptationNode = (String)map.get("adaptationNode");
        String limit = (String)map.get("limit");
        String start = (String)map.get("start");
        StringBuffer hqlCount = new StringBuffer();
        StringBuffer hql = new StringBuffer();
        map.clear();
        if (StringUtils.isNotBlank(categoryId))
        {
            hqlCount.append("select count(*) from ActivitiForm a " +
            		"  inner join  a.categories b where 1=1 and b.isDelete=0 and a.isDelete=0");
            hql.append("from ActivitiForm a inner join  " +
            		" a.categories b where 1=1 and b.isDelete=0 and a.isDelete=0");
        }
        else
        {
            
            hqlCount.append("select count(*) from ActivitiForm a " +
            		" where  isDelete=0");
            hql.append("from ActivitiForm a where  a.isDelete=0");
        }
        if (StringUtils.isNotBlank(categoryId))
        {
            map.put("categoryId", Integer.valueOf(categoryId));
            hql.append(" and b.categoryId=").append(":categoryId");
            hqlCount.append(" and b.categoryId=").append(":categoryId");
            
        }
        
        if (StringUtils.isNotBlank(formName))
        {
            map.put("formName", "%" + formName + "%");
            hql.append(" and a.formName like ").append(":formName");
            hqlCount.append(" and a.formName like ").append(":formName");
            
        }
        
        if (StringUtils.isNotBlank(adaptationNode))
        {
            map.put("adaptationNode", "%," + adaptationNode + ",%");
            hql.append(" and a.adaptationNode like ").append(":adaptationNode");
            hqlCount.append(" and a.adaptationNode like ")
                .append(":adaptationNode");
            
        }
        hql.append(" order by a.id desc");
        int totalSize = this.queryTotalCount(hqlCount.toString(), map);
        listVo.setTotalSize(totalSize);
        List<ActivitiForm> forms = new ArrayList<ActivitiForm>();
        if (StringUtils.isNotBlank(categoryId))
        {
            List<Object[]> objs =
                (List<Object[]>)this.queryEntitysByPage(
                    NumberUtils.toInt(start),
                    NumberUtils.toInt(limit),
                    hql.toString(),
                    map);
            for (Object[] obj : objs)
            {
                ActivitiForm form = (ActivitiForm)obj[0];
                forms.add(form);
            }
        }
        else
        {
            forms =
                (List<ActivitiForm>)this.queryEntitysByPage(
                    NumberUtils.toInt(start),
                    NumberUtils.toInt(limit),
                    hql.toString(),
                    map);
            
        }
        listVo.setList(forms);
        return listVo;
    }
}
