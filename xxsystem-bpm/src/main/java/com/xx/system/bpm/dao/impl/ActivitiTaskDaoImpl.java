/**
 * @文件名 ActivitiTaskDaoImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程任务持久层实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.dqgb.sshframe.bpm.dao.IActivitiTaskDao;
import com.dqgb.sshframe.bpm.entity.ActivitiTaskRole;
import com.dqgb.sshframe.bpm.vo.TaskVo;
import com.dqgb.sshframe.common.constant.Constant;
import com.dqgb.sshframe.common.dao.impl.BaseDaoHibernateImpl;
import com.dqgb.sshframe.common.util.DateUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.user.entity.User;

/**
 * 
 * 流程任务持久层实现
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 下午2:16:42
 * @since V1.20
 * @depricated
 */
@Repository("activitiTaskDao")
public class ActivitiTaskDaoImpl extends BaseDaoHibernateImpl implements
    IActivitiTaskDao {
    
    @Override
    public ListVo getDoneTaskListByPage(Map paramMap) {
        ListVo listVo = new ListVo();
        User u = (User)paramMap.get("user");
        String processName = (String)paramMap.get("processName");
        String taskOwnerName = (String)paramMap.get("taskOwnerName");
        String taskStartTime = (String)paramMap.get("taskStartTime");
        String taskEndTime = (String)paramMap.get("taskEndTime");
        String taskStartEndTime = (String)paramMap.get("taskStartEndTime");
        String taskEndStartTime = (String)paramMap.get("taskEndStartTime");
        String taskCompeletUser = (String)paramMap.get("taskCompeletUser");
        String processCode = (String)paramMap.get("processCode");
        StringBuffer sqlTable = new StringBuffer();
        StringBuffer sqlWhere = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        StringBuffer sqlCount = new StringBuffer();
        sql.append("select  a.ID_,a.TASK_DEF_KEY_,a.PROC_INST_ID_,a.FORM_URL_,"
            + " a.NAME_,to_char(a.START_TIME_,'yyyy-MM-dd hh24:mi:ss'),"
            + " to_char(a.END_TIME_,'yyyy-MM-dd hh24:mi:ss'),");
        sql.append(" a.DURATION_,a.DELETE_REASON_,b.PROCESS_NAME,a.ASSIGNEE_,"
            + " c.REALNAME  u1,d.REALNAME  u2,a.OWNER_,b.PROCESS_TYPE,b.PROCESS_CODE,"
            + " b.FK_ACTIVITI_CATEGORY_ID,b.BUSINESS_ID,a.DESCRIPTION_ ");
        sqlCount.append("select count(*) ");
        if (StringUtils.isNotBlank(taskOwnerName)) {
            sqlTable.append(" from ACT_HI_TASKINST a ,t_USER c ,"
                + " t_USER d,SYS_ACTIVITI_PROCESS_INST b");
            sqlWhere.append(" where a.PROC_INST_ID_=b.PROCESS_INSTANCE_ID  "
                + " and a.OWNER_=d.USERNAME and a.ASSIGNEE_=c.USERNAME and d.STATUS="
                + Constant.STATUS_NOT_DELETE + " ");
        } else {
            sqlTable.append(" from  t_USER c ,SYS_ACTIVITI_PROCESS_INST b,"
                + "  ACT_HI_TASKINST a left outer JOIN t_USER d on a.OWNER_=d.USERNAME  "
                + "  and  d.STATUS=" + Constant.STATUS_NOT_DELETE + " ");
            sqlWhere.append(" where   a.ASSIGNEE_=c.USERNAME and "
                + " a.PROC_INST_ID_=b.PROCESS_INSTANCE_ID  ");
        }
        sqlWhere.append(" and (a.ASSIGNEE_='")
            .append(u.getUsername())
            .append("'")
            .append(" or a.OWNER_='")
            .append(u.getUsername())
            .append("')")
            .append(" and c.STATUS=" + Constant.STATUS_NOT_DELETE)
            .append(" and a.END_TIME_ is not null ");
        if (StringUtils.isNotBlank(processName)) {
            sqlWhere.append(" and b.PROCESS_NAME like '%")
                .append(processName.trim())
                .append("%'");
        }
        if (StringUtils.isNotBlank(processCode)) {
            sqlWhere.append(" and b.PROCESS_CODE = '")
                .append(processCode)
                .append("'");
        }
        if (StringUtils.isNotBlank(taskOwnerName)) {
            sqlWhere.append(" and d.REALNAME like '%")
                .append(taskOwnerName)
                .append("%'");
        }
        if (StringUtils.isNotBlank(taskStartTime)) {
            sqlWhere.append(" and a.START_TIME_ ")
                .append(">=")
                .append("to_date('")
                .append(taskStartTime)
                .append("','yyyy-MM-dd hh24:mi:ss')");
        }
        if (StringUtils.isNotBlank(taskStartEndTime)) {
            sqlWhere.append(" and a.START_TIME_ ")
                .append("<")
                .append("to_date('")
                .append(taskStartEndTime)
                .append("','yyyy-MM-dd hh24:mi:ss')");
        }
        if (StringUtils.isNotBlank(taskEndStartTime)) {
            sqlWhere.append(" and a.END_TIME_ ")
                .append(">=")
                .append("to_date('")
                .append(taskEndStartTime)
                .append("','yyyy-MM-dd hh24:mi:ss')");
        }
        if (StringUtils.isNotBlank(taskEndTime)) {
            sqlWhere.append(" and a.END_TIME_ ")
                .append("<")
                .append("to_date('")
                .append(taskEndTime)
                .append("','yyyy-MM-dd hh24:mi:ss')");
        }
        if (StringUtils.isNotBlank(taskCompeletUser)) {
            sqlWhere.append(" and c.REALNAME like '%")
                .append(taskCompeletUser)
                .append("%'");
        }
        sql.append(sqlTable)
            .append(sqlWhere)
            .append(" order by a.END_TIME_ desc,a.START_TIME_ desc");
        sqlCount.append(sqlTable).append(sqlWhere);
        /*
         * List listCount = this.queryEntitysByNavtiveSql(sqlCount.toString()); int count =
         * ((BigDecimal)listCount.get(0)).intValue();
         */
        List listCount = this.queryEntitysByNavtiveSql(sqlCount.toString());
        int count = Integer.parseInt(listCount.get(0) + "");
        
        listVo.setTotalSize(count);
        final int limit = (Integer)paramMap.get("limit");
        final int start = (Integer)paramMap.get("start");
        List<Object[]> list = findPageBySql(sql.toString(), start, limit);
        listVo.setList(list);
        return listVo;
    }
    
    /**
     * 获取有效授权信息
     * 
     * @Title getEntrustByBeUser
     * @author qiubo
     * @Description:
     * @date 2014-8-26
     * @param userId
     * @return
     */
    private String getEntrustByBeUser(Integer userId) {
        String sql =
            " select FK_USER_ID from TB_ENTRUST t where t.FK_BE_USER_ID="
                + userId + " and t.SIGN=0 and t.START_TIME<=to_date('"
                + DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss")
                + "','yyyy-MM-dd hh24:mi:ss')" + " and t.END_TIME >=to_date('"
                + DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss")
                + "','yyyy-MM-dd hh24:mi:ss')";
        List<Map> mapList = this.querySQLForMap(sql);
        if (mapList != null && mapList.size() > 0) {
            StringBuffer restr = new StringBuffer();
            for (Map<String, Object> map : mapList) {
                restr.append(map.get("FK_USER_ID").toString()).append(",");
            }
            return restr.substring(0, restr.length() - 1);
        } else {
            return null;
        }
        
    }
    
    /**
     * 分页查询待办理任务
     * 
     * @author 张小虎
     * @param paramMap
     * @return ListVo
     */
    @Override
    public ListVo<Object[]> getToDoTaskListByPage(Map paramMap) {
        ListVo<Object[]> listVo = new ListVo<Object[]>();
        StringBuffer sql = new StringBuffer();
        StringBuffer sqlCount = new StringBuffer();
        User user = (User)paramMap.get("user");
        // String entrustByBeUser =getEntrustByBeUser(u.getUserId());
        String processName = (String)paramMap.get("processName");
        String taskOwnerName = (String)paramMap.get("taskOwnerName");
        String taskDefKey = (String)paramMap.get("taskDefKey");
        String taskStartTime = (String)paramMap.get("taskStartTime");
        String categoryId = (String)paramMap.get("categoryId");
        String taskStartEndTime = (String)paramMap.get("taskStartEndTime");
        String processCode = (String)paramMap.get("processCode");
        String processInstanceId = (String)paramMap.get("processInstanceId");
        StringBuffer sqlTabletestwhere = new StringBuffer();
        if (user !=null && StringUtils.isNotBlank(user.getUsername())) {
            sqlTabletestwhere.append(" and CAL.ASSIGNEE_ = '")
                .append(user.getUsername().trim())
                .append("'");
        }
        if (StringUtils.isNotBlank(processInstanceId)) {
        	sqlTabletestwhere.append(" and CAL.PROC_INST_ID_ = '")
        	.append(processInstanceId.trim())
        	.append("'");
        }
        if (StringUtils.isNotBlank(processName)) {
        	sqlTabletestwhere.append(" and CAL.PROCESS_NAME like '%")
        	.append(processName.trim())
        	.append("%'");
        }
        if (StringUtils.isNotBlank(processCode)) {
            sqlTabletestwhere.append(" and CAL.PROCESS_CODE = '")
                .append(processCode)
                .append("'");
        }
        if (StringUtils.isNotBlank(taskStartTime)) {
            sqlTabletestwhere.append(" and CAL.CREATE_TIME_ ")
                .append(">=")
                .append("'")
                .append(taskStartTime)
                .append("'");
        }
        
        if (StringUtils.isNotBlank(taskStartEndTime)) {
            sqlTabletestwhere.append(" and CAL.CREATE_TIME_ ")
                .append("<=")
                .append("'")
                .append(taskStartEndTime)
                .append("'");
            
        }
        if (StringUtils.isNotBlank(taskOwnerName)) {
            sqlTabletestwhere.append(" and CAL.U1 like ")
                .append("'%")
                .append(taskOwnerName.trim())
                .append("%'");
        }
        if (StringUtils.isNotBlank(taskDefKey)) {
        	sqlTabletestwhere.append(" and CAL.TASK_DEF_KEY_ = ")
        	.append("'")
        	.append(taskDefKey.trim())
        	.append("'");
        }
        if (StringUtils.isNotBlank(categoryId)) {
        	sqlTabletestwhere.append(" and CAL.FK_ACTIVITI_CATEGORY_ID ="+categoryId);
        }
        
        StringBuffer sqlTabletest = new StringBuffer();
        sqlTabletest.append("select distinct CAL.ID_,CAL.TASK_DEF_KEY_,CAL.PROC_INST_ID_,CAL.FORM_URL_,"
            + " CAL.NAME_,CAL.CREATE_TIME_,CAL.PROCESS_NAME,CAL.ASSIGNEE_,CAL.u1,CAL.u2,"
            + " CAL.OWNER_,CAL.EXECUTION_ID_,CAL.PROCESS_TYPE,CAL.PROCESS_CODE,CAL.FK_ACTIVITI_CATEGORY_ID,"
            + " CAL.BUSINESS_ID,CAL.USER_ID"
            + " from ( select a.ID_,a.TASK_DEF_KEY_,a.PROC_INST_ID_,a.FORM_URL_,a.NAME_,"
            + " to_char(a.CREATE_TIME_,'yyyy-MM-dd hh24:mi:ss') CREATE_TIME_,"
            + " b.PROCESS_NAME,to_char(a.ASSIGNEE_) ASSIGNEE_,c.REALNAME u1,"
            + " d.REALNAME u2,a.OWNER_,a.EXECUTION_ID_,b.PROCESS_TYPE,"
            + " b.PROCESS_CODE,b.FK_ACTIVITI_CATEGORY_ID,"
            + " b.BUSINESS_ID,c.USER_ID"
            + " from t_USER c, SYS_ACTIVITI_PROCESS_INST b, ACT_RU_TASK a"
            + " left outer JOIN t_USER d"
            + " on a.OWNER_ = d.USERNAME"
            + " and d.STATUS = 0"
            + " where a.ASSIGNEE_ = c.USERNAME"
            + " and a.PROC_INST_ID_ = b.PROCESS_INSTANCE_ID"
            + " and c.STATUS = 0"
            + " union"
            + " select a.ID_,a.TASK_DEF_KEY_,a.PROC_INST_ID_,a.FORM_URL_,a.NAME_,"
            + " to_char(a.CREATE_TIME_,'yyyy-MM-dd hh24:mi:ss') CREATE_TIME_,"
            + " b.PROCESS_NAME,g.username,g.realname u1,'' u2,"
            + " a.OWNER_,a.EXECUTION_ID_,b.PROCESS_TYPE,b.PROCESS_CODE,b.FK_ACTIVITI_CATEGORY_ID,"
            + " b.BUSINESS_ID,G.USER_ID"
            + " from ACT_RU_TASK               a,"
            + " SYS_ACTIVITI_PROCESS_INST b,"
            + " SYS_ACTIVITI_CATEGORY     c,"
            + " t_ROLE                  e,"
            + " t_role_member_scope     f,"
            + " t_USER                  g,"
            + " SYS_ACTIVITI_ROLE         h,"
            + " t_org_user                ou"
            + " where a.ASSIGNEE_ is null"
            + " and b.FK_ACTIVITI_CATEGORY_ID = c.PK_ACTIVITI_CATEGORY_ID"
            + " and b.PROCESS_INSTANCE_ID = a.PROC_INST_ID_"
            + " and e.ROLE_ID = f.ROLE_ID"
            + " and h.TASKID = a.ID_"
            + " and f.USER_ID = g.USER_ID "
            + " and h.fk_role_id=e.role_id "
            + " and g.user_id=ou.fk_user_id"
            + " and (b.business_org=ou.fk_org_id " //能审核自己部门的数据
            + " or b.business_org in (select sm.org_id from t_role_member_scope rms ,t_scope_member sm " //能审有权限的部门的数据。
            + " where rms.id=sm.role_member_id and rms.role_id=e.role_id and rms.user_id=g.user_id) "
            + " or exists (select sm.user_id from t_role_member_scope rms, t_scope_member sm " //能审有权限的流程发起人的数据
            + " where rms.id = sm.role_member_id and rms.role_id = e.role_id and rms.user_id = g.user_id and sm.user_id =b.fk_process_creater) )"
            + " and g.STATUS = 0  ) CAL where 1=1 ");
            
        sqlTabletest.append(sqlTabletestwhere);
        
        StringBuffer sqlcou = new StringBuffer();
        sqlcou.append("select count(1) from ( ")
            .append(sqlTabletest)
            .append(" )");
        sqlTabletest.append(" order by 6 desc, 1   ");
        // List listCount = this.queryEntitysByNavtiveSql(sqlCount.toString());
        List listCount = this.queryEntitysByNavtiveSql(sqlcou.toString());
        int count = Integer.parseInt(listCount.get(0) + "");
        listVo.setTotalSize(count);
        int pageSize = (Integer)paramMap.get("limit");
        int start = (Integer)paramMap.get("start");
        List<Object[]> list;
        try {
            list = findPageBySql(sqlTabletest.toString(), start, pageSize);
            listVo.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listVo;
    }
    
    /**
     * 
     * @Title findPageBySql
     * @author zhxh
     * @Description:纯sql分页查询
     * @date 2013-12-28
     * @return
     */
    public List<Object[]> findPageBySql(final String sql, final int start,
        final int limit) {
        return this.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                query.setFirstResult(start);
                query.setMaxResults(limit);
                return query.list();
            }
        });
    }
    
    /**
     * 
     * @Title getRoleTaskSql
     * @author zhxh
     * @Description:任务是角色的sql
     * @date 2013-12-30
     * @param u 用户
     * @param sqlSelect 选择的sql
     * @param sqlConditionWhere 用户查询条件sql
     * @return String
     */
    private String getRoleTaskSql(User u, StringBuffer sqlSelect,
        StringBuffer sqlConditionWhere) {
        StringBuffer sqlRole = new StringBuffer();
        sqlRole.append(" union ");
        sqlRole.append(sqlSelect);
        sqlRole.append(" from ACT_RU_TASK a,SYS_ACTIVITI_PROCESS_INST b,SYS_ACTIVITI_CATEGORY c");
        sqlRole.append(" where a.ASSIGNEE_ is null");
        sqlRole.append(" and b.FK_ACTIVITI_CATEGORY_ID=c.PK_ACTIVITI_CATEGORY_ID");
        sqlRole.append(" and b.PROCESS_INSTANCE_ID=a.PROC_INST_ID_");
        sqlRole.append(sqlConditionWhere);
        sqlRole.append(" and exists(select 1 from t_ROLE e,t_role_member_scope f,t_USER g,"
            + "  SYS_ACTIVITI_ROLE h,t_ORGANIZATION i");
        sqlRole.append(" where e.ROLE_ID=f.ROLE_ID  ");
        sqlRole.append(" and h.TASKID=a.ID_");
        sqlRole.append(" and e.ROLE_ID=h.FK_ROLE_ID");
        sqlRole.append(" and f.USER_ID=g.USER_ID");
        sqlRole.append(" and f.ORG_ID=i.ORG_ID");
        sqlRole.append(" and ( (INSTR(c.category_code,'_')>0 ");
        sqlRole.append(" and  i.ORG_CODE=substr(c.category_code,INSTR(c.category_code,'_')+1 ) ) "
            + " or (INSTR(c.category_code,'_')<=0)) ");
        sqlRole.append(" and g.USERNAME='" + u.getUsername()).append("' ");
        sqlRole.append(" and g.STATUS=" + Constant.STATUS_NOT_DELETE)
            .append(")");
        return sqlRole.toString();
    }
    
    /**
     * 
     * @Title updateHistoryTask
     * @author zhxh
     * @Description: 更新历史任务数据
     * @date 2013-12-25
     * @param vo
     */
    @Override
    public void updateHistoryTask(TaskVo vo) {
        String sql =
            "update ACT_HI_TASKINST set NAME_=?,FORM_URL_=?,ASSIGNEE_=?"
                + " where ID_=?";
        String[] paramValues =
            new String[] {vo.getName(), vo.getFormUrl(), vo.getAssignee(),
                vo.getTaskId()};
        this.executeNativeSQL(sql, paramValues);
    }
    
    @Override
    public void addActivitiTaskRole(List<ActivitiTaskRole> activitiTaskRoles) {
        this.saveAll(activitiTaskRoles);
    }
    
    @Override
    public List<ActivitiTaskRole> getActivitiTaskRole(Map paramMap) {
        StringBuffer hql = new StringBuffer();
        hql.append("from ActivitiTaskRole where 1=1");
        Map conditionMap = new HashMap();
        this.getHqlCondition(hql, paramMap, conditionMap);
        return this.queryEntitysByMap(hql.toString(), conditionMap);
    }
    
}
