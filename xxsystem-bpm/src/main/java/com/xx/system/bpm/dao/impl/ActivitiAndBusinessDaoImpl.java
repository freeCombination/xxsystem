/**
 * @文件名 ActivitiAndBusinessDaoImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程和业务持久层
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao.impl;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dqgb.sshframe.bpm.dao.IActivitiAndBusinessDao;
import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.bpm.entity.ActivitiDefineTemplate;
import com.dqgb.sshframe.bpm.entity.ActivitiProcessApproval;
import com.dqgb.sshframe.bpm.entity.ActivitiProcessInstance;
import com.dqgb.sshframe.common.constant.Constant;
import com.dqgb.sshframe.common.dao.impl.BaseDaoHibernateImpl;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.role.entity.Role;
import com.dqgb.sshframe.user.entity.User;
//import com.dqgb.sshframe.user.entity.UserRole;

/**
 * 数据库实现基类
 */
@Repository("activitiAndBusinessDao")
public class ActivitiAndBusinessDaoImpl extends 
BaseDaoHibernateImpl implements
    IActivitiAndBusinessDao
{
    
    /**
     * 添加事项预流程实例对应
     * 
     * @param List <ActivitiProcessInstance>
     * @throws ServiceException
     * @return void
     */
    @Override
    public void addActivitiProcessInstances(
        List<ActivitiProcessInstance> processInstances)
    {
        for (ActivitiProcessInstance p : processInstances)
        {
            this.addEntity(p);
        }
        
    }
    
    /**
     * 获取流程审批签名信息
     * 
     * @author 侯永超
     * @date 2011-5-24
     * @param processInstanceId @
     * @return ProcessApproval
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ActivitiProcessApproval> getProcessApprovalList(
        String processInstanceId)
    {
        StringBuffer hql = new StringBuffer();
        hql.append("select a from ActivitiProcessApproval a " +
        		" where a.processInstanceId = :processInstanceId " +
        		"  order by a.id asc");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("processInstanceId", processInstanceId);
        return (List<ActivitiProcessApproval>)this.queryEntitysByMap(
            hql.toString(),
            map);
        
    }
    
    /**
     * 分页查询流程实例
     * 
     * @param paramMap
     * @return ListVo
     */
    @Override
    public ListVo<Object[]> getProcessInstanceByPage(Map paramMap)
    {
        
        String processName = (String)paramMap.get("processName");
        String processCreateUserName = (String)paramMap.get("processCreateUserName");
        String processStartTime = (String)paramMap.get("processStartTime");
        String processStartEndTime = (String)paramMap.get("processStartEndTime");
        String processEndStartTime = (String)paramMap.get("processEndStartTime");
        String processEndTime = (String)paramMap.get("processEndTime");
        String processCreateUserId = (String)paramMap.get("processCreateUserId");
        if(processStartTime != null && !"".equals(processStartTime)){
        	processStartTime = processStartTime.substring(0, 10) + " 00:00:00";
        }
        if(processStartEndTime != null && !"".equals(processStartEndTime)){
        	processStartEndTime = processStartEndTime.substring(0, 10) + " 23:59:59";
        }
        if(processEndStartTime != null && !"".equals(processEndStartTime)){
        	processEndStartTime = processEndStartTime.substring(0, 10) + " 00:00:00";
        }
        if(processEndTime != null && !"".equals(processEndTime)){
        	processEndTime = processEndTime.substring(0, 10) + " 23:59:59";
        }
        int start = (Integer)paramMap.get("start");
        int limit = (Integer)paramMap.get("limit");
        StringBuffer sql = new StringBuffer();
        StringBuffer sqlTable = new StringBuffer();
        StringBuffer sqlWhere = new StringBuffer();
        StringBuffer sqlCount = new StringBuffer();
        ListVo listVo = new ListVo();
        sql.append("select  a.PROCESS_INSTANCE_ID,a.PROCESS_NAME,c.REALNAME," +
        		" c.USERNAME,to_char(b.START_TIME_,'yyyy-MM-dd hh24:mi:ss')," +
        		" to_char(b.END_TIME_,'yyyy-MM-dd hh24:mi:ss')," +
        		" b.END_ACT_ID_,DELETE_REASON_," +
        		" DURATION_,a.PROCESS_TYPE,a.PROCESS_CODE");
        sqlTable.append(" from SYS_ACTIVITI_PROCESS_INST a,t_USER c ,ACT_HI_PROCINST b");
        sqlWhere.append(" where a.PROCESS_INSTANCE_ID=b.PROC_INST_ID_ and" +
        		" a.FK_PROCESS_CREATER=c.USER_ID");
        sqlCount.append("select  count(*) ");
        if (StringUtils.isNotBlank(processName))
        {
            sqlWhere.append(" and a.PROCESS_NAME like '%")
                .append(processName.trim()).append("%'");
        }
        if (StringUtils.isNotBlank(processCreateUserId))
        {
            sqlWhere.append(" and b.START_USER_ID_ = '")
                .append(processCreateUserId.trim()).append("'");
        }
        if (StringUtils.isNotBlank(processCreateUserName))
        {
            sqlWhere.append(" and c.REALNAME like '%")
                .append(processCreateUserName.trim()).append("%'");
        }
        if (StringUtils.isNotBlank(processStartTime))
        {
            sqlWhere.append(" and b.START_TIME_ ")
                .append(">=").append("to_date('").append(processStartTime)
                .append("','yyyy-MM-dd hh24:mi:ss')");
            
        }
        if (StringUtils.isNotBlank(processStartEndTime))
        {
            sqlWhere.append(" and b.START_TIME_ ").append("<=")
                .append("to_date('").append(processStartEndTime)
                .append("','yyyy-MM-dd hh24:mi:ss')");
            
        }
        
        if (StringUtils.isNotBlank(processEndStartTime))
        {
            sqlWhere.append(" and b.END_TIME_ ").append(">=")
                .append("to_date('").append(processEndStartTime)
                .append("','yyyy-MM-dd hh24:mi:ss')");
        }

         if (StringUtils.isNotBlank(processEndTime))
        {
             sqlWhere.append(" and b.END_TIME_ ").append("<=")
                .append("to_date('").append(processEndTime)
                .append("','yyyy-MM-dd hh24:mi:ss')");
            
        }
         sql.append(sqlTable).append(sqlWhere)
        .append(" order by  b.END_TIME_ desc,b.START_TIME_ desc ");
         sqlCount.append(sqlTable).append(sqlWhere);
         List listCount = this.queryEntitysByNavtiveSql(sqlCount.toString());
        int count = (Integer.parseInt(listCount.get(0)+""));
        listVo.setTotalSize(count);
        List<Object[]>list = executeSqlByPage(sql.toString(),start,limit);
        listVo.setList(list);
        return listVo;
    }
    /**
     * 
    * @Title exeSqlByPage
    * @author zhxh
    * @Description: 执行sql的分页查询
    * @date 2014-1-7
    * @param tempSql
    * @param start
    * @param limit
    * @return List<Object[]>
     */
    private List<Object[]> executeSqlByPage(
         final String tempSql
        ,final int start,final int limit)
    {

        return this.getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session)
                    throws HibernateException, SQLException
                {
                    Query query = session.createSQLQuery(tempSql);
                    query.setFirstResult(start);
                    query.setMaxResults(limit);
                    return query.list();
                }
            });
    }
    @Override
    public void updateProcessInstance(final String xmlContent,
        final String deploymentId)
    {
        this.getHibernateTemplate().execute(new HibernateCallback()
        {
            @Override
            public Object doInHibernate(Session session)
                throws HibernateException, SQLException
            {
                Connection conn = session.connection();
                try
                {
                    String sql =
                        "select BYTES_  from ACT_GE_BYTEARRAY where DEPLOYMENT_ID_='"
                            + deploymentId + "' and GENERATED_=0 for update";
                    String flushBlob =
                        "update ACT_GE_BYTEARRAY set BYTES_=EMPTY_BLOB() " +
                        " where DEPLOYMENT_ID_='"
                            + deploymentId + "' and GENERATED_=0";
                    PreparedStatement ps = conn.prepareStatement(flushBlob);
                    ps.executeUpdate();
                    ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next())
                    {
                        oracle.sql.BLOB blob =
                            (oracle.sql.BLOB)rs.getBlob("BYTES_");
                        OutputStream outStream = blob.setBinaryStream(0L);
                        outStream.write(xmlContent.getBytes("UTF-8"),
                            0,
                            xmlContent.getBytes("UTF-8").length);
                        outStream.flush();
                        conn.commit();
                        outStream.close();
                        conn.close();
                    }
                    else
                    {
                        throw new ServiceException("保存失败");
                    }
                }
                catch (Exception e)
                {
                    conn.rollback();
                    conn.close();
                    throw new ServiceException("保存失败",e);
                }
                return null;
            }
        });
        
    }
    
    /**
     * 获取业务数据和流程实例关系
     * 
     * @return List<ProcessInstances>
     * @param ids 流程实例id，用逗号分开
     * 
     */
    @Override
    public List<ActivitiProcessInstance> getActivitiProcessInstance(
    
    String processInstanceIds)
    {
        String temp =
            "from ActivitiProcessInstance where" +
            " processInstanceId in("+ processInstanceIds + ")";
        List<ActivitiProcessInstance> pos =
            (List<ActivitiProcessInstance>)this.queryEntitys(temp);
        return pos;
        
    }
    
    /**
     * 获取用户列表，为流程在线设计器
     * 
     * @author 张小虎
     * @date 2011-3-25
     * @param paramMap 参数map
     * @throws ServiceException
     * @return ListVo<User>
     */
    @Override
    public ListVo<User> getUserListForFlow(Map<String, Object> paramMap)
    {
        
        StringBuffer hqlList = new StringBuffer();
        StringBuffer hqlCount = new StringBuffer();
        StringBuffer hqlWhere= new StringBuffer();
        StringBuffer idBuffer= new StringBuffer();
        Map queryMap = new HashMap();
        ListVo<User> userListVo = new ListVo<User>();
        List<User> userList = new ArrayList();
        int start = NumberUtils.toInt((String)paramMap.get("start"));
        int limit = NumberUtils.toInt((String)paramMap.get("limit"));
        String usersId = (String)paramMap.get("usersId");
        String userName = (String)paramMap.get("userName");
        hqlList.append("from User a where 1 = 1 ");
        hqlCount.append("select count(a.id) from User a where 1 = 1");
        hqlWhere.append(" and a.enable=:enable");
        hqlWhere.append(" and a.status=:status");
        queryMap.put("enable", Constant.ENABLE);
        queryMap.put("status", Constant.STATUS_NOT_DELETE);
        int count = 0;
        if (StringUtils.isNotBlank(userName))
        {
            hqlWhere.append(" and a.realname like :realname");
            queryMap.put("realname", "%"+userName.trim()+"%");
        }   
        /*if(StringUtils.isNotBlank(usersId))
        {
            String ids[] = usersId.split(",");
            for (String id : ids)
            {
                idBuffer.append("'").append(id).append("'").append(",");
            }
            List<User> userOtherList = this.queryEntitysByMap(
                hqlList.append(hqlWhere)
               .append(" and a.username in(")
               .append(idBuffer.substring(0, idBuffer.length() - 1))
               .append(")").toString(),queryMap);
            if(CollectionUtils.isEmpty(userOtherList))
            {
                hqlCount.append(hqlWhere);
                count = this.getTotalCount(hqlCount.toString(), queryMap);
                userList =  this.queryEntitysByPage(
                    start, limit, hqlList.toString(), queryMap); 
            }
            else{
                hqlWhere.append(" and a.username not in(")
                .append(idBuffer.substring(0, idBuffer.length() - 1))
                .append(")");
                userList = this.getUserListForTop(
                    hqlWhere.toString(),userOtherList, start, limit,
                    queryMap);
            }
        }
        else{
            hqlList.append(hqlWhere);
            userList =  this.queryEntitysByPage(
                start, limit, hqlList.toString(), queryMap);
        }*/
        hqlList.append(hqlWhere);
        userList =  this.queryEntitysByPage(
            start, limit, hqlList.toString(), queryMap);
        
        
        hqlCount.append(hqlWhere);
        count = this.getTotalCount(hqlCount.toString(), queryMap);
        userListVo.setTotalSize(count);
        userListVo.setList(userList);
        return userListVo;
    }
    /**
     * 
    * @Title getUserListForTop
    * @author zhxh
    * @Description:返回具有顶端用户的列表 
    * @date 2014-1-8
     */
    private List<User> getUserListForTop(String hqlWhere,
        List<User> userOthers,int start,int limit,Map paramMap){
        List<User> returnList =  new ArrayList();
        StringBuffer hqlList = new StringBuffer();
        hqlList.append("from User a where 1 = 1 ");
        List<User>tempUsers = new ArrayList();
        int size = userOthers.size();
        if(start + limit <= size)
        {
            returnList=
                userOthers.subList(start, limit + start);
            return returnList;
        }
        else if(start < size)
        {
            returnList = userOthers.subList(start, size);
            limit = limit - userOthers.size();
        }
        tempUsers = 
            (List<User>)this.findPageByQuery(
                start, limit,
                "from User a where 1=1 "+
                 hqlWhere.toString(), 
                paramMap);
        returnList.addAll(tempUsers);
       return returnList;
        
    }
    /**
     * 获取用户
     * 
     * @param loginNames
     * @return List<User>
     */
    @Override
    public List<User> getUserListByLoginNames(String loginNames)
    {
        Map paramMap = new HashMap();
        paramMap.put("enable", Constant.ENABLE);
        paramMap.put("status", Constant.STATUS_NOT_DELETE);
        String hql =
            "from User where enable=:enable and status=:status and username in("
                + loginNames + ")";
        return (List<User>)this.queryEntitysByMap("from User " +
        		" where enable=:enable and status=:status " +
        		" and username in("
            + loginNames + ")",
            paramMap);
    }
    
    /**
     * 获取角色
     * 
     * @param paramMap
     * @return ListVo<Role>
     */
    @Override
    public ListVo<Role> getRoleForBpmByPage(Map paramMap)
    {
        String roleName = (String)paramMap.get("roleName");
        ListVo listVo = new ListVo();
        StringBuffer hql = new StringBuffer("from Role where  isDelete=0");
        StringBuffer countHql = new StringBuffer("from Role where  isDelete=0");
        int start = Integer.valueOf((String)paramMap.get("start"));
        int limit = Integer.valueOf((String)paramMap.get("limit"));
        paramMap.clear();
        if (StringUtils.isNotBlank(roleName))
        {
            paramMap.put("roleName", "%" + roleName + "%");
            hql.append(" and roleName like :roleName");
            countHql.append(" and roleName like :roleName");
        }
        hql.append(" order by id desc");
        
        List<ActivitiDefineTemplate> datas =
            (List<ActivitiDefineTemplate>)this.queryEntitysByPage(start,
                limit,
                hql.toString(),
                paramMap);
        int count = this.queryTotalCount(countHql.toString(), paramMap);
        listVo.setTotalSize(count);
        listVo.setList(datas);
        return listVo;
    }
    
    /**
     * 通过角色Id以及bpmType获取角色单位下的人员
     * 
     * @param roleIds
     * @param bpmType
     * @return loginNames
     */
    @Override
    public List<User> getUserByRoleIdsAndCategoryId(
        String roleIds,
        int categoryId)
    {
        List<User> users = new ArrayList();
        ActivitiCategory category =
            (ActivitiCategory)this.queryEntityById(ActivitiCategory.class,
                categoryId);
        StringBuffer hql = new StringBuffer();
        Map paramMap = new HashMap();
        paramMap.put("isDelete", 0);
        paramMap.put("enable", Constant.ENABLE);
        paramMap.put("status", Constant.STATUS_NOT_DELETE);
        if (category != null)
        {
            if (StringUtils.isNotBlank(category.getCode()))
            {
                if (category.getCode().indexOf("_") == -1)
                {// 所有单位
                    hql.append("from UserRole a where a.role.isDelete=:isDelete ");
                    hql.append(" and a.user.enable=:enable ");
                    hql.append(" and a.user.status=:status ");
                    hql.append(" and a.role.id in(" + roleIds + ")");
                    /*List<UserRole> userRoles =
                        this.queryEntitysByMap(hql.toString(), paramMap);
                    for (UserRole userRole : userRoles)
                    {
                        User user = userRole.getUser();
                        users.add(user);
                    }*/
                    
                }
                else
                {
                    int pos = category.getCode().lastIndexOf("_");
                    String unitCode = category.getCode().substring(pos + 1);
                    paramMap.put("orgCode", unitCode);
                    hql.delete(0, hql.length());
                    hql.append("select a form UserRole c,User a inner join fetch a.orgUsers b");
                    hql.append(" where c.user.userId=a.userId");
                    hql.append(" and c.role.id in(" + roleIds + ")");
                    hql.append(" and c.organization.orgCode=:orgCode");
                    hql.append(" and a.user.enable=:enable ");
                    hql.append(" and a.user.status=:status ");
                    hql.append(" and a.role.isDelete=:isDelete ");
                    users = this.queryEntitysByMap(hql.toString(), paramMap);
                }
            }
        }
        
        return users;
    }
    
    @Override
    public void addActivitiProcessApproval(
        ActivitiProcessApproval processApproval)
    {
        this.addEntity(processApproval);
        
    }
    
    @Override
    public void deleteActivitiProcessApproval(String processInstanceIds)
    {
        this.executeHql("delete from ActivitiProcessApproval where id in("
            + processInstanceIds + ")");
        
    }

    
}
