/**
 * @文件名 ActivitiDelegateDaoImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 委托授权持久层实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.dqgb.sshframe.bpm.dao.IActivitiDelegateDao;
import com.dqgb.sshframe.bpm.entity.ActivitiDelegate;
import com.dqgb.sshframe.bpm.vo.ActivitiDelegateVo;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.dao.impl.BaseDaoHibernateImpl;
import com.dqgb.sshframe.common.util.DateUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.user.entity.User;

/**
 * 
 * 委托授权持久层实现,查询，修改，删除，添加
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 下午2:16:42
 * @since V1.20
 * @depricated
 */
@Repository("activitiDelegateDao")
public class ActivitiDelegateDaoImpl extends BaseDaoHibernateImpl implements
    IActivitiDelegateDao
{
    @Override
    public void addDelegate(ActivitiDelegate delegate)
    {
        this.addEntity(delegate);
        
    }
    
    @Override
    public void updateDelegate(ActivitiDelegate delegate)
    {
        this.updateEntity(delegate);
        
    }
    
    @Override
    public List<ActivitiDelegate> getActivitiDelegate(Map paramMap)
    {
        StringBuffer hql = new StringBuffer();
        hql.append("from ActivitiDelegate where 1=1 ");
        Map conditionMap = new HashMap();
        this.getHqlCondition(hql, paramMap, conditionMap);
        return this.queryEntitysByMap(hql.toString(), conditionMap);
    }
    
    @Override
    public void deleteDelegate(String ids)
    {
        String hql =
            "update ActivitiDelegate a set isDelete=1 where id in(" + ids + ")";
        this.executeHql(hql);
        
    }
    
    @Override
    public ListVo getDelegateByPage(Map paramMap)
    {
        ListVo listVo = new ListVo();
        List datas = new ArrayList();
        User u = (User)paramMap.get("user");
        Map queryMap = new HashMap();
        queryMap.put("isDelete", 0);
        queryMap.put("username", u.getUsername());
        StringBuffer hql = new StringBuffer();
        StringBuffer hqlWhere = new StringBuffer();
        StringBuffer hqlCount = new StringBuffer();
        hql.append("from ActivitiDelegate a ");
        hqlCount.append("select count(*) from ActivitiDelegate a ");
        hqlWhere.append("where a.owner.username=:username and a.isDelete=:isDelete");
        int start = Integer.valueOf((String)paramMap.get("start"));
        int limit = Integer.valueOf((String)paramMap.get("limit"));
        String startTime = (String)paramMap.get("startTime");
        String startEndTime = (String)paramMap.get("startEndTime");
        String endStartTime = (String)paramMap.get("endStartTime");
        String endTime = (String)paramMap.get("endTime");
        String statusFlag = (String)paramMap.get("statusFlag");
        String delegateUserName = (String)paramMap.get("delegateUserName");
        if (StringUtils.isNotBlank(startTime))
        {
        	/*sqlWhere.append(" and b.START_TIME_ ").append("<")
            .append("DATE_FORMAT('").append(processStartEndTime)
            .append("','%Y-%m-%d %H:%i:%S')");
        	*/
        	
            hqlWhere.append(" and a.delegateStartTime ")
                .append(">=").append("DATE_FORMAT('").append(startTime)
                .append("','%Y-%m-%d %H:%i:%S')");
        }
        if (StringUtils.isNotBlank(startEndTime))
        {
            hqlWhere.append(" and a.delegateStartTime ")
                .append("<").append("DATE_FORMAT('").append(startEndTime)
                .append("','%Y-%m-%d %H:%i:%S')");
        }
        if (StringUtils.isNotBlank(endStartTime))
        {
            hqlWhere.append(" and a.delegateEndTime ")
                .append(">=").append("DATE_FORMAT('").append(endStartTime)
                .append("','%Y-%m-%d %H:%i:%S')");
        }
        
        if (StringUtils.isNotBlank(endTime))
        {
            hqlWhere.append(" and a.delegateEndTime ")
                .append("<").append("DATE_FORMAT('").append(endTime)
                .append("','%Y-%m-%d %H:%i:%S')");
        }
        if (StringUtils.isNotBlank(delegateUserName))
        {
            hqlWhere.append(" and a.delegateUser.realname like :delegateUserName");
            queryMap.put("delegateUserName", "%"+delegateUserName+"%");
        }
        if (StringUtils.isNotBlank(statusFlag))
        {
            hqlWhere.append(" and a.delegateStatus =:delegateStatus");
            queryMap.put("delegateStatus", Integer.valueOf(statusFlag)-1);
        }
        hql.append(hqlWhere)
        .append(" order by  a.delegateStatus asc,a.delegateEndTime asc");
        hqlCount.append(hqlWhere);
        int count = this.queryTotalCount(hqlCount.toString(),queryMap);
        listVo.setTotalSize(count);
        List<ActivitiDelegate> activitiDelegates =
            (List<ActivitiDelegate>)this.queryEntitysByPage(start,
                limit,hql.toString(),queryMap);
        this.updateDelegateStatus(activitiDelegates, datas,u);
        listVo.setList(datas);
        return listVo;
    }
    /**
     * 
    * @Title updateDelegateStatus
    * @author zhxh
    * @Description: 更新状态
    * @date 2014-1-8
     */
    private void updateDelegateStatus(
        List<ActivitiDelegate> activitiDelegates,
        List<ActivitiDelegateVo> activitiDelegateVos,
        User currentUser)
    {

        Date temp = new Date();
        temp = DateUtil.formatDate(temp, SystemConstant.DATE_PATTEN_LONG);
        StringBuffer idsBuffer = new StringBuffer();
        for (ActivitiDelegate a : activitiDelegates)
        {
            ActivitiDelegateVo vo = new ActivitiDelegateVo();
            vo.setDelegateStatus(a.getDelegateStatus());
            if (a.getDelegateStatus() == 0)
            {
                    if (a.getDelegateEndTime().compareTo(temp) < 0)
                    {
                        idsBuffer.append(a.getId()).append(",");
                        vo.setDelegateStatus(1);
                    }
            }
            vo.setDelegateEndTime(DateUtil.dateToString(
                    a.getDelegateEndTime(),
                    SystemConstant.DATE_PATTEN_LONG));
            vo.setDelegateStartTime(DateUtil.dateToString(
                    a.getDelegateStartTime(),
                    SystemConstant.DATE_PATTEN_LONG));
            vo.setOwnerId(currentUser.getUsername());
            vo.setOwnerName(currentUser.getUsername());
            if (a.getDelegateUser() != null)
            {
                vo.setDelegateUserId(a.getDelegateUser().getUsername());
                vo.setDelegateUserName(a.getDelegateUser().getUsername());
            }
            vo.setId(a.getId());
            activitiDelegateVos.add(vo);
        }
        if (idsBuffer.length() > 0)
        {
            this.executeHql("update ActivitiDelegate " +
                    " set delegateStatus=1 where id in("
                + idsBuffer.substring(0, idsBuffer.length() - 1) + ")");
        }
    }
    @Override
    public List<ActivitiDelegate> getDelegateByUserIdStartTime(
        Date startTime,String userId)
    {
        StringBuffer hql = new StringBuffer();
        String startDate =
            DateUtil.dateToString(startTime, SystemConstant.DATE_PATTEN_LONG);
        hql.append("from ActivitiDelegate a ");
       hql.append(" where a.delegateStartTime<= ")
            .append("TO_DATE('").append(startDate)
            .append("','YYYY-MM-dd HH24:Mi:SS')");
        hql.append(" and a.delegateEndTime>=")
            .append("TO_DATE('").append(startDate)
            .append("','YYYY-MM-dd HH24:Mi:SS')");
        hql.append(" and a.owner.username='" + userId + "'");
        hql.append(" and a.isDelete=0 and a.delegateStatus=0");
        return (List<ActivitiDelegate>)this.queryEntitys(hql.toString());
    }
    
}
