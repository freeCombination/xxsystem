/**
 * 文件名： WorkTeamServiceImpl.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述： 班组管理逻辑接口实现类
 * 修改人： tangh
 * 修改时间： 2014年8月22日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dqgb.sshframe.common.constant.Constant;
import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.util.DateUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.org.entity.OrgUser;
import com.dqgb.sshframe.org.entity.Organization;
import com.dqgb.sshframe.org.service.IOrgService;
import com.dqgb.sshframe.user.entity.User;
import com.dqgb.sshframe.user.vo.UserVo;
import com.dqgb.sshframe.workturns.entity.WorkTeam;
import com.dqgb.sshframe.workturns.entity.WorkTeamMember;
import com.dqgb.sshframe.workturns.service.IWorkTeamService;
import com.dqgb.sshframe.workturns.vo.WorkTeamVo;

/**
 * 定义班组管理逻辑接口实现类
 * 
 * @author tangh
 * @version V1.40,2014年9月5日 下午4:16:50
 * @see [相关类/方法]
 * @since V1.40
 */
@Service("workTeamService")
@Transactional(readOnly = false)
@SuppressWarnings("unchecked")
public class WorkTeamServiceImpl implements IWorkTeamService {
    /**
     * @Fields baseDao : 数据库操作基础方法
     */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    @Autowired
    @Qualifier("organizationService")
    private IOrgService orgService;
    
    /**
     * 添加工作组
     * 
     * @Title addToWorkTeam
     * @author tangh
     * @date 2014年9月3日
     * @param workTeamVo 班组VO对象
     * @return 整形
     * @throws BusinessException
     */
    @Override
    public int addToWorkTeam(WorkTeamVo workTeamVo)
        throws BusinessException {
        try {
            // 封装参数
            Organization org = new Organization(workTeamVo.getOrgId());
            WorkTeam workTeam = new WorkTeam();
            workTeam.setTeamName(workTeamVo.getWorkTeamName());
            workTeam.setOrg(org);
            if(StringUtils.isNotBlank(workTeamVo.getRemark().trim())){
                workTeam.setRemark(workTeamVo.getRemark().trim());
            }
            workTeam.setCreateDate(new Date());
            WorkTeam team = (WorkTeam)baseDao.save(workTeam);
            // 判断班组是否为空，不为空返回班组id
            if (team != null) {
                return team.getId();
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return 0;
    }
    
    /**
     * 获取班组成员
     * 
     * @Title getWorkTeamMember
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 查询参数
     * @return ListVo<UserVo> 用户集合
     * @throws BusinessException
     */
    @Override
    public ListVo<UserVo> getWorkTeamMember(Map<String, String> param)
        throws BusinessException {
        ListVo<UserVo> lstVo = new ListVo<UserVo>();
        // 获取参数
        String start = (String)param.get("start");
        String limit = (String)param.get("limit");
        String teamId = (String)param.get("teamId");
        
        // 判断版主ID是否等于0
        if (NumberUtils.toInt(teamId) != 0) {
            StringBuffer hql = new StringBuffer();
            hql.append("from WorkTeamMember o where o.workTeam.id=" + teamId);
            hql.append(" and o.members.status = ");
            hql.append(Constant.STATUS_NOT_DELETE);
            hql.append(" order by o.members.disOrder, o.members.id");
            
            // 总条数
            int count =
                baseDao.queryTotalCount(hql.toString(),
                    new HashMap<String, Object>());
            
            // 查询成员
            List<WorkTeamMember> wtmList =
                (List<WorkTeamMember>)baseDao.queryEntitysByPage(NumberUtils.toInt(start),
                    NumberUtils.toInt(limit),
                    hql.toString(),
                    new HashMap<String, Object>());
            
            // 申明用户VO集合，用于存储用户
            List<UserVo> userVoLst = new ArrayList<UserVo>();
            UserVo userVo = null;
            // 判断班组成员是否是空，不为空就进行实体转换
            if (!CollectionUtils.isEmpty(wtmList)) {
                // 循环讲实体对象转换成VO对象
                for (WorkTeamMember wtm : wtmList) {
                    userVo = new UserVo();
                    userVo.setUserId(wtm.getMembers().getUserId());
                    userVo.setRealname(wtm.getMembers().getRealname());
                    userVo.setMobileNo1(wtm.getMembers().getMobileNo1());
                    userVo.setErpId(wtm.getMembers().getErpId());
                    userVoLst.add(userVo);
                }
            }
            lstVo.setList(userVoLst);
            lstVo.setTotalSize(count);
        }
        return lstVo;
    }
    
    /**
     * 获取组织成员
     * 
     * @Title getDeptMember
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 查询参数
     * @return ListVo<UserVo> 用户集合
     * @throws BusinessException
     */
    @Override
    public ListVo<UserVo> getDeptMember(Map<String, String> param)
        throws BusinessException {
        ListVo<UserVo> listVo = new ListVo<UserVo>();
        // 获取参数列表
        String start = (String)param.get("start");
        String limit = (String)param.get("limit");
        String deptId = (String)param.get("orgId");
        String teamId = (String)param.get("teamId");
        
        List<WorkTeamMember> wtmList = null;
        if (NumberUtils.toInt(teamId) != 0) {
            StringBuffer hql = new StringBuffer();
            hql.append("from WorkTeamMember o where o.workTeam.id=" + teamId);
            hql.append(" and o.members is not null");
            hql.append(" and o.members.status = ");
            hql.append(Constant.STATUS_NOT_DELETE);
            
            // 查询成员
            wtmList = (List<WorkTeamMember>)baseDao.queryEntitys(hql.toString());
        }
        
        StringBuffer buffer = new StringBuffer();
        // 判断uList是否为空
        if (wtmList != null && wtmList.size() > 0) {
            // 循环遍历集合，组装查询条件
            for (int i = 0; i < wtmList.size(); i++) {
                // 判断是否为最后一个元素，不是就添加，分割
                if (i != wtmList.size() - 1) {
                    buffer.append(wtmList.get(i).getMembers().getUserId() + ",");
                }
                else {
                    buffer.append(wtmList.get(i).getMembers().getUserId());
                }
            }
        }
        
        // 根据deptId查询该部门下的所有用户
        StringBuffer orgUsrHql = new StringBuffer();
        orgUsrHql.append("from OrgUser ou where ou.user.status = 0 ");
        orgUsrHql.append(" and ou.organization.orgId =" + deptId);
        
        // 判断buffer是否为空，不为空就添加查询条件
        if (StringUtils.isNotBlank(buffer.toString())) {
            orgUsrHql.append(" and ou.user.userId not in (" + buffer.toString()
                + ")");
        }
        orgUsrHql.append(" and ou.isDelete = ");
        orgUsrHql.append(Constant.STATUS_NOT_DELETE);
        
        // 总条数
        int count =
            baseDao.queryTotalCount(orgUsrHql.toString(),
                new HashMap<String, Object>());
        
        List<OrgUser> ouLst =
            (List<OrgUser>)baseDao.queryEntitysByPage(NumberUtils.toInt(start),
                NumberUtils.toInt(limit),
                orgUsrHql.toString(),
                new HashMap<String, Object>());
        // 判断集合(ouLst)是否为空
        if (!CollectionUtils.isEmpty(ouLst)) {
            List<UserVo> userVoLst = new ArrayList<UserVo>();
            UserVo userVo = null;
            // 循环封装用户VO对象并添加到集合中
            for (OrgUser orgUser : ouLst) {
                // 实体之间相互转换
                userVo = new UserVo();
                userVo.setUserId(orgUser.getUser().getUserId());
                userVo.setRealname(orgUser.getUser().getRealname());
                userVo.setMobileNo1(orgUser.getUser().getMobileNo1());
                userVo.setErpId(orgUser.getUser().getErpId());
                userVoLst.add(userVo);
            }
            listVo.setList(userVoLst);
            listVo.setTotalSize(count);
        }
        return listVo;
    }
    
    /**
     * 添加班组成员
     * 
     * @Title addWorkTeamMember
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 参数
     * @return String 字符串
     * @throws BusinessException
     */
    @Override
    public String addWorkTeamMember(Map<String, String> param)
        throws BusinessException {
        // 获取参数
        String ids = (String)param.get("ids");
        String teamId = (String)param.get("teamId");
        try {
            // 判断参数是否为空
            if (StringUtils.isNotBlank(teamId) && StringUtils.isNotBlank(ids)) {
                String[] strs = ids.split(",");
                // 循环遍历数组，获取用户并保存
                for (String str : strs) {
                    // 实体转换
                    User user = new User(NumberUtils.toInt(str));
                    WorkTeam workTeam = new WorkTeam();
                    workTeam.setId(NumberUtils.toInt(teamId));
                    WorkTeamMember workTeamMember = new WorkTeamMember();
                    workTeamMember.setMembers(user);
                    workTeamMember.setWorkTeam(workTeam);
                    baseDao.save(workTeamMember);
                }
                return "success";
            }
            
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }
    
    /**
     * 删除班组成员
     * 
     * @Title delWorkTeamMember
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 参数
     * @return String 字符串
     * @throws BusinessException
     */
    @Override
    public String delWorkTeamMember(Map<String, String> param)
        throws BusinessException {
        // 获取传递的参数
        String delMemberIds = (String)param.get("delMemberIds");
        String teamId = (String)param.get("teamId");
        String result = "deleted";
        try {
            // 根据teamId排除已在班组的用户
            WorkTeam workTeam =
                (WorkTeam)baseDao.queryEntityById(WorkTeam.class,
                    NumberUtils.toInt(teamId));
            // 判断改记录是否被删除
            if (workTeam == null) {
                return result;
            }
            // 判断班组人员id是否为空，不为空就循环删除对象
            if (StringUtils.isNotBlank(delMemberIds)) {
                StringBuffer hql =
                    new StringBuffer("delete from WorkTeamMember o where ");
                hql.append(" o.workTeam.id = ");
                hql.append(teamId);
                hql.append(" and o.members.userId in (");
                hql.append(delMemberIds);
                hql.append(")");
                
                baseDao.executeHql(hql.toString());
                result = "success";
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }
    
    /**
     * 删除班组
     * 
     * @Title delWorkTeam
     * @author tangh
     * @date 2014年9月3日
     * @param teamId 班组id
     * @return String 字符串
     * @throws BusinessException
     */
    @Override
    public String delWorkTeam(String ids)
        throws BusinessException {
        String result = "error";
        try {
            // 判断参数是否存在
            if (StringUtils.isNotBlank(ids)) {
                String[] str = ids.split(",");
                for (String id : str) {
                    // 根据id查询对象
                    WorkTeam workTeam =
                        (WorkTeam)baseDao.queryEntityById(WorkTeam.class,
                            NumberUtils.toInt(id));
                    if (workTeam != null) {
                        // 查询倒班规则详细
                        StringBuffer rwtHql =
                            new StringBuffer(
                                "from RuleWorkTeamMap o where o.workTeam.id="
                                    + NumberUtils.toInt(id));
                        int rwtCount =
                            baseDao.queryTotalCount(rwtHql.toString(),
                                new HashMap<String, Object>());
                        // 判断班组是否在倒班规则中存在
                        if (rwtCount > 0) {
                            return result = "班组正在倒班规则中使用，不能删除";
                        }
                        
                        // 查询倒班计划详细
                        StringBuffer sppHql =
                            new StringBuffer(
                                "from WorkRulePlan o where o.workTeam.id="
                                    + NumberUtils.toInt(id));
                        int sppCount =
                            baseDao.queryTotalCount(sppHql.toString(),
                                new HashMap<String, Object>());
                        // 判断班组是否在倒班计划中存在
                        if (sppCount > 0) {
                            return result = "班组正在倒班计划中使用，不能删除";
                        }
                        
                        StringBuffer hql =
                            new StringBuffer("delete from WorkTeam wt ");
                        hql.append(" where wt.id =" + id);
                        baseDao.executeHql(hql.toString());
                    }
                }
                result = "success";
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }
    
    /**
     * 修改班组信息
     * 
     * @Title updateWorkTeam
     * @author tangh
     * @date 2014年9月3日
     * @param workTeamVo 班组VO对象
     * @param flag 标识符
     * @return String 字符串
     * @throws BusinessException
     */
    @Override
    public String updateWorkTeam(WorkTeamVo workTeamVo, String flag)
        throws BusinessException {
        String result = "error";
        // 判断对象是否为空
        if (workTeamVo != null && StringUtils.isNotBlank(flag)) {
            WorkTeam workTeam = getWorkTeamById(workTeamVo.getWorkTeamId());
            // 判断该班组是否存在
            if (workTeam == null) {
                return result;
            }
            else {
                // 判断组织id是否改变过，组织id改变就删除该组织下的人员
                if (Integer.parseInt(flag) != workTeamVo.getOrgId()) {
                    this.delWorkTeamMemberAll(String.valueOf(workTeamVo.getWorkTeamId()));
                }
                // 封装参数
                Organization org = new Organization(workTeamVo.getOrgId());
                workTeam.setTeamName(workTeamVo.getWorkTeamName());
                workTeam.setOrg(org);
                if(StringUtils.isNotBlank(workTeamVo.getRemark().trim())){
                    workTeam.setRemark(workTeamVo.getRemark().trim());
                }
                result = "success";
            }
        }
        return result;
    }
    
    /**
     * 获得所有的工作组
     * 
     * @Title getAllWorkTeam
     * @author tangh
     * @date 2014年9月3日
     * @param paramMap 查询参宿
     * @return ListVo<WorkTeamVo> 班组集合
     * @throws BusinessException
     */
    @Override
    public ListVo<WorkTeamVo> getAllWorkTeam(Map<String, String> param)
        throws BusinessException {
        ListVo<WorkTeamVo> returnList = new ListVo<WorkTeamVo>();
        
        // 获取参数
        String start = (String)param.get("start");
        String limit = (String)param.get("limit");
        String orgId = (String)param.get("orgId");
        String workTeamName = (String)param.get("workTeamName");
        String ids = (String)param.get("orgIds");
        
        // 查询orgId下所有部门
        StringBuffer teamHql = new StringBuffer();
        teamHql.append("from WorkTeam wt where 1 = 1");
        // 判断班组名称是否为空
        if (StringUtils.isNotBlank(workTeamName)) {
            teamHql.append(" and wt.teamName like '%" + workTeamName + "%'");
        }
        // 判断组织id是否为空
        if (StringUtils.isNotBlank(orgId)) {
            String permissionIds = orgService.getOrgIdsByPermissionScope(orgId, ids);
            // 判断集合是否为空
            if (StringUtils.isNotBlank(permissionIds)) {
                teamHql.append(" and wt.org.orgId in (")
                    .append(permissionIds)
                    .append(")");
            }
        }
        else if (StringUtils.isNotBlank(orgId)) {
            teamHql.append(" and wt.org.orgId=" + orgId);
        }
        
        int count =
            baseDao.queryTotalCount(teamHql.toString(),
                new HashMap<String, Object>());
        
        // 执行查询
        List<WorkTeam> teamList =
            (List<WorkTeam>)baseDao.queryEntitysByPage(NumberUtils.toInt(start),
                NumberUtils.toInt(limit),
                teamHql.toString(),
                new HashMap<String, Object>());
        
        // entity转换成vo
        List<WorkTeamVo> userVoLst = new ArrayList<WorkTeamVo>();
        if (!CollectionUtils.isEmpty(teamList)) {
            WorkTeamVo wtvo = null;
            // 循环遍历结果集并封装成用户VO添加到集合中
            for (WorkTeam team : teamList) {
                wtvo = new WorkTeamVo();
                wtvo.setWorkTeamId(team.getId());
                wtvo.setWorkTeamName(team.getTeamName());
                wtvo.setOrgId(team.getOrg().getOrgId());
                wtvo.setOrgName(team.getOrg().getOrgName());
                wtvo.setCreateDate(DateUtil.dateToString(team.getCreateDate(),
                    "yyyy-MM-dd HH:mm:ss"));
                wtvo.setRemark(team.getRemark());
                userVoLst.add(wtvo);
            }
            returnList.setList(userVoLst);
            returnList.setTotalSize(count);
        }
        return returnList;
    }
    
    /**
     * 验证工作组名字
     * 
     * @Title validateWorkTeamName
     * @author tangh
     * @date 2014年9月3日
     * @param teamName 班组名称
     * @param orgId 组织id
     * @param teamId 班组id
     * @return Integer 整形
     * @throws BusinessException
     */
    @Override
    public int validateWorkTeamName(Map<String, String> paramMap)
        throws BusinessException {
        // 获取参数列表
        String teamName = paramMap.get("value");
        String orgId = paramMap.get("orgId");
        String teamId = paramMap.get("teamId");
        // 根据班组名称判断该班组名称是否已经使用
        StringBuffer hql = new StringBuffer();
        hql.append("from WorkTeam wt where 1 = 1");
        hql.append(" and wt.org.orgId=" + orgId);
        // 判断版主名称是否为空
        if (StringUtils.isNotBlank(teamName)) {
            hql.append(" and wt.teamName='" + teamName + "'");
        }
        // 班组Id不为空就拼接查询条件
        if (StringUtils.isNotBlank(teamId) && !"0".equals(teamId)) {
            hql.append(" and wt.id <>" + teamId);
        }
        // 总条数
        int count =
            baseDao.queryTotalCount(hql.toString(),
                new HashMap<String, Object>());
        return count;
    }
    
    /**
     * 根据班组ID获取班组信息
     * 
     * @Title getWorkTeamById
     * @author tangh
     * @date 2014年9月3日
     * @param id 班组id
     * @return WorkTeam 班组对象
     * @throws BusinessException
     */
    @Override
    public WorkTeam getWorkTeamById(int id)
        throws BusinessException {
        // 根据teamId排除已在班组的用户
        WorkTeam workTeam =
            (WorkTeam)baseDao.queryEntityById(WorkTeam.class, id);
        return workTeam;
    }
    
    /**
     * 删除班组下所有的成员
     * 
     * @Title delWorkTeamMemberAll
     * @author tangh
     * @date 2014年9月4日
     * @param teamId 班组id
     * @return 无
     * @throws BusinessException
     */
    @Override
    public void delWorkTeamMemberAll(String teamId)
        throws BusinessException {
        try {
            if (StringUtils.isNotBlank(teamId)) {
                String delHql = "delete from WorkTeamMember wtm where "
                    + " wtm.workTeam.id = " + teamId;
                baseDao.executeHql(delHql);
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
    /**
     * 通过组织ID获取班组
     * 
     * @Title getAllWorkTeamByOrgId
     * @author dong.he
     * @date 2014年9月5日
     * @param orgId 组织id
     * @return 班组集合
     * @throws BusinessException
     */
    @Override
    public List<WorkTeam> getAllWorkTeamByOrgId(String orgId)
        throws BusinessException {
        // 新建查询班组的hql语句
        StringBuffer hql = new StringBuffer();
        hql.append("from WorkTeam wt where 1 = 1");
        hql.append(" and wt.org.orgId=" + orgId);
        hql.append(" order by wt.id");
        
        return (List<WorkTeam>)baseDao.queryEntitys(hql.toString());
    }
    
}
