/**
 * @文件名 ActivitiDefineTemplateDaoImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板持久层实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dqgb.sshframe.bpm.dao.IActivitiDefineTemplateDao;
import com.dqgb.sshframe.bpm.entity.ActivitiDefineTemplate;
import com.dqgb.sshframe.common.dao.impl.BaseDaoHibernateImpl;
import com.dqgb.sshframe.common.vo.ListVo;

/**
 * 
 * 流程模板持久层实现,查询，修改，删除，添加
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 下午2:16:42
 * @since V1.20
 */
@Repository("activitiDefineTemplateDao")
public class ActivitiDefineTemplateDaoImpl extends BaseDaoHibernateImpl
    implements IActivitiDefineTemplateDao
{
    
    @Override
    public void addActivitiDefineTemplate(
        ActivitiDefineTemplate activitiDefineTemplate)
    {
        this.addEntity(activitiDefineTemplate);
    }
    
    @Override
    public List<ActivitiDefineTemplate> getActivitiDefineTemplate(Map map)
    {
        StringBuffer hql =
            new StringBuffer("from ActivitiDefineTemplate where 1=1");
        Map conditionMap = new HashMap();
        this.getHqlCondition(hql, map, conditionMap);
        List list = this.queryEntitysByMap(hql.toString(), conditionMap);
        return list;
    }
    
    @Override
    public void updateActivitiDefineTemplate(
        ActivitiDefineTemplate activitiDefineTemplate)
    {
        this.updateEntity(activitiDefineTemplate);
        
    }
    
    @Override
    public void deleteActivitiDefineTemplate(String ids)
    {
        String hql = "delete ActivitiDefineTemplate  where id in(" + ids + ")";
        this.executeHql(hql);
        
    }
    
    @Override
    public ListVo getActivitiDefineTemplateByPage(Map map)
    {
        ListVo listVo = new ListVo();
        StringBuffer hql =
            new StringBuffer("from ActivitiDefineTemplate where  isDelete=0");
        StringBuffer countHql =
            new StringBuffer("from ActivitiDefineTemplate where  isDelete=0");
        int start = (Integer)map.get("start");
        int limit = (Integer)map.get("limit");
        String categoryId = (String)map.get("categoryId");
        String templateName = (String)map.get("templateName");
        
        map.clear();
        
        if (StringUtils.isNotBlank(templateName))
        {
            hql.append(" and name like '%").append(templateName).append("%'");
            countHql.append(" and name like '%")
                .append(templateName)
                .append("%'");
        }
        if (StringUtils.isNotBlank(categoryId))
        {
            map.put("categoryId", Integer.valueOf(categoryId));
            hql.append(" and category.categoryId=:categoryId");
            countHql.append(" and category.categoryId=:categoryId");
        }
        
        List<ActivitiDefineTemplate> datas =
            (List<ActivitiDefineTemplate>)this.queryEntitysByPage(start,
                limit,
                hql.toString(),
                map);
        int count = this.queryTotalCount(countHql.toString(), map);
        listVo.setTotalSize(count);
        listVo.setList(datas);
        
        return listVo;
    }
    
}
