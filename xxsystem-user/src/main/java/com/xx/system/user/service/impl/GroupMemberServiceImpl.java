package com.xx.system.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.service.IOrgService;
import com.xx.system.user.entity.Group;
import com.xx.system.user.entity.GroupMember;
import com.xx.system.user.entity.User;
import com.xx.system.user.service.IGroupMemberService;
import com.xx.system.user.service.IUserService;
import com.xx.system.user.vo.GroupMemberVo;
import com.xx.system.user.vo.UserVo;

/**
 * 群组成员服务接口实现类
 * 
 * @version V1.40,2014年9月15日 上午8:52:00
 * @see [相关类/方法]
 * @since V1.40
 */
@Service("groupMemberService")
@Transactional(readOnly = false)
@SuppressWarnings("unchecked")
public class GroupMemberServiceImpl implements IGroupMemberService {
    
    /**
     * @Fields baseDao : 数据库操作基础方法
     */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    @Autowired
    @Qualifier("organizationService")
    private IOrgService orgService;
    @Autowired
    @Qualifier("userService")
    private IUserService userService;
    
    /**
     * 添加群组成员
     * 
     * @Title addGroupMember
     * @author tangh
     * @date 2014年9月15日
     * @param param 参数
     * @return 字符串
     * @throws BusinessException
     */
    @Override
    public String addGroupMember(Map<String, String> param)
        throws BusinessException {
        String result = "error";
        // 获取参数
        String userIds = (String)param.get("userIds");
        String groupId = (String)param.get("groupId");
        // String orgId = (String)param.get("orgId");
        try {
            // 判断群组id和用户id是否为空
            if (StringUtils.isNotBlank(groupId)
                && StringUtils.isNotBlank(userIds)) {
                // 拆分字符串
                String[] strs = userIds.split(",");
                // 遍历数组并添加用户到该群组
                for (String userId : strs) {
                    GroupMember groupMember = new GroupMember();
                    boolean ss = isExitUserId(userId, groupId);
                    if (ss) {// 说明此用户还没有存在
                        User user = new User();
                        user.setUserId(Integer.parseInt(userId));
                        groupMember.setUser(user);
                        // 设置群组
                        Group group = new Group();
                        group.setId(Integer.parseInt(groupId));
                        groupMember.setGroup(group);
                        baseDao.save(groupMember);
                        result = "success";
                    }
                    else {
                        String hql1 = " delete from GroupMember gm where gm.user.userId = " + userId
                               + " and gm.group.id = " + groupId;
                        baseDao.executeHql(hql1);
                        
                        User user = new User();
                        user.setUserId(Integer.parseInt(userId));
                        groupMember.setUser(user);
                        // 设置群组
                        Group group = new Group();
                        group.setId(Integer.parseInt(groupId));
                        groupMember.setGroup(group);
                        baseDao.save(groupMember);
                        result = "success";
                        //result = "exist";
                    }
                }
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }
    
    /**
     * 添加群组成员时查询该用户是否已经存在
     * 
     * @Title isExitUserId
     * @author tangh
     * @date 2014年9月15日
     * @param userId 用户id
     * @param groupId 群组id
     * @return boolean
     * @throws BusinessException
     */
    @Override
    public boolean isExitUserId(String userId, String groupId)
        throws BusinessException {
        String hql =
            "from GroupMember gm where gm.user.userId = " + userId
                + " and gm.group.id = " + groupId;
        List<GroupMember> gm = baseDao.queryEntitys(hql);
        if (CollectionUtils.isEmpty(gm) || (gm != null && gm.size() <= 0)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * 删除群组成员
     * 
     * @Title delGroupMember
     * @author tangh
     * @date 2014年9月15日
     * @param paramsMap 参数
     * @return 字符串
     * @throws BusinessException
     */
    @Override
    public String delGroupMember(Map<String, String> paramsMap)
        throws BusinessException {
        String result = "error";
        String userIds = paramsMap.get("userIds");
        String groupId = paramsMap.get("groupId");
        try {
            // 判断用户id是否为空
            if (StringUtils.isNotBlank(userIds)) {
                StringBuffer hql = new StringBuffer();
                hql.append("delete from GroupMember o where 1=1 ");
                hql.append(" and o.group.id=" + groupId);
                hql.append(" and o.user.userId in (" + userIds + ")");
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
     * 查询群组成员
     * 
     * @Title getGroupMember
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap
     * @return ListVo<GroupMember> 群组成员集合
     * @throws BusinessException
     */
    @Override
    public ListVo<GroupMemberVo> getGroupMember(Map<String, String> paramMap)
        throws BusinessException {
        ListVo<GroupMemberVo> listVo = new ListVo<GroupMemberVo>();
        int totalSize = 0;
        
        // 获取参数
        String start = paramMap.get("start");
        String limit = paramMap.get("limit");
        String realname = paramMap.get("realname");
        String groupId = paramMap.get("groupId");
        
        // 查询hql语句
        StringBuffer hql = new StringBuffer();
        hql.append("from GroupMember o where 1=1 ");
        // 判断群组id是否为空
        if (StringUtils.isNotBlank(groupId)) {
            hql.append(" and o.group.id=" + groupId);
        }else{
            hql.append(" and o.group.id is null ");
        }
        // 判断班组名称是否为空
        if (StringUtils.isNotBlank(realname)) {
            hql.append(" and o.user.realname like '%" + realname + "%' ");
        }
        
        hql.append(" and o.user is not null and o.user.status = 0");
        
        try {
            // 统计中条数
            totalSize =
                baseDao.queryTotalCount(hql.toString(),
                    new HashMap<String, Object>());
            List<GroupMember> list = baseDao.queryEntitysByPage(NumberUtils.toInt(start),
                NumberUtils.toInt(limit),
                hql.toString(),
                new HashMap<String, Object>());
            
            // 用于存储群组用户VO的集合
            List<GroupMemberVo> memberVos = new ArrayList<GroupMemberVo>();
            GroupMemberVo groupMemberVo = null;
            // 循环遍历结果集，封装对象并添加群组成员VO集合
            for (GroupMember groupMember : list) {
                // 创建VO对象并封装数据
                groupMemberVo = new GroupMemberVo();
                groupMemberVo.setId(groupMember.getId());
                // 判断用户是否为空
                if (groupMember.getUser() != null) {
                    groupMemberVo.setUserId(groupMember.getUser().getUserId());
                    groupMemberVo.setRealname(groupMember.getUser()
                        .getRealname());
                    groupMemberVo.setEmail(groupMember.getUser().getEmail());
                    groupMemberVo.setErpId(groupMember.getUser().getErpId());
                    
                    Iterator<OrgUser> it = groupMember.getUser().getOrgUsers().iterator();
                    String orgIds = "";
                    while (it.hasNext()) {
                        OrgUser ou = it.next();
                        if(ou.getIsDelete() == Constant.STATUS_NOT_DELETE){
                            orgIds = orgIds + "," + ou.getOrganization().getOrgId();
                        }
                    }
                    List<Organization> orgLst =
                        orgService.getOrgByIds(orgIds.substring(1));
                    
                    String orgNames = "";
                    if (!CollectionUtils.isEmpty(orgLst)) {
                        for (Organization organization : orgLst) {
                            orgNames = orgNames + "," + organization.getOrgName();
                        }
                        orgNames = orgNames.substring(1);
                    }
                    
                    groupMemberVo.setOrgName(orgNames);
                }
                
                memberVos.add(groupMemberVo);
            }
            listVo.setList(memberVos);
            listVo.setTotalSize(totalSize);
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return listVo;
    }
    
    /**
     * 查询属于该群组的部门
     * 
     * @Title getDept
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap
     * @return ListVo<GroupMember> 群组成员集合
     * @throws BusinessException
     */
    public ListVo<GroupMemberVo> getDept(Map<String, String> paramMap)
        throws BusinessException {
        ListVo<GroupMemberVo> listVo = new ListVo<GroupMemberVo>();
        List<GroupMember> list = new ArrayList<GroupMember>();
        int totalSize = 0;
        
        // 获取参数
        String start = paramMap.get("start");
        String limit = paramMap.get("limit");
        String groupId = paramMap.get("groupId");
        String orgName = paramMap.get("orgName");
        
        // 查询hql语句
        StringBuffer hql = new StringBuffer();
        hql.append("from GroupMember o where 1=1 ");
        // 判断群组id是否为空
        if (StringUtils.isNotBlank(groupId)) {
            hql.append(" and o.group.id=" + groupId);
        }
        
        // 判断组织是否为空
        if (StringUtils.isNotBlank(orgName)) {
            hql.append(" and o.org.orgName like '%" + orgName + "%'");
        }
        hql.append(" and o.user is null");
        
        try {
            // 统计中条数
            totalSize =
                baseDao.queryTotalCount(hql.toString(),
                    new HashMap<String, Object>());
            list =
                (List<GroupMember>)baseDao.queryEntitysByPage(Integer.parseInt(start),
                    Integer.parseInt(limit),
                    hql.toString(),
                    new HashMap<String, Object>());
            // 用于存储群组用户VO的集合
            List<GroupMemberVo> memberVos = new ArrayList<GroupMemberVo>();
            GroupMemberVo groupMemberVo = null;
            // 循环遍历结果集，封装对象并添加群组成员VO集合
            for (GroupMember groupMember : list) {
                // 创建VO对象并封装数据
                groupMemberVo = new GroupMemberVo();
                groupMemberVo.setId(groupMember.getId());
                // 判断用户是否为空
                if (groupMember.getUser() != null) {
                    groupMemberVo.setUserId(groupMember.getUser().getUserId());
                    groupMemberVo.setRealname(groupMember.getUser()
                        .getRealname());
                    groupMemberVo.setEmail(groupMember.getUser().getEmail());
                    groupMemberVo.setErpId(groupMember.getUser().getErpId());
                }
                // 判断组织是否为空
                if (groupMember.getOrg() != null) {
                    groupMemberVo.setOrgName(groupMember.getOrg().getOrgName());
                    groupMemberVo.setOrgCode(groupMember.getOrg().getOrgCode());
                    // groupMemberVo.setOrgType(groupMember.getOrg().getOrgType().getDictionaryType().getTypeName());
                }
                
                memberVos.add(groupMemberVo);
            }
            listVo.setList(memberVos);
            listVo.setTotalSize(totalSize);
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return listVo;
    }
    
    /**
     * 为该群组添加部门
     * 
     * @Title addDept
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap 参数
     * @return String 字符串
     * @throws BusinessException
     */
    @Override
    public String addDept(Map<String, String> paramMap)
        throws BusinessException {
        String result = "error";
        // 获取参数
        String orgId = paramMap.get("orgIds");
        String groupId = paramMap.get("groupId");
        try {
            // 判断组织ID和群组ID是否为空
            if (StringUtils.isNotBlank(orgId)) {
                GroupMember groupMember = new GroupMember();
                // 设置部门
                Organization org = new Organization();
                org.setOrgId(Integer.parseInt(orgId));
                groupMember.setOrg(org);
                
                // 设置群组
                Group group = new Group();
                group.setId(Integer.parseInt(groupId));
                groupMember.setGroup(group);
                
                baseDao.save(groupMember);
                result = "success";
            }
            
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }
    
    /**
     * 删除该群组中的部门
     * 
     * @Title delDept
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap 参数
     * @return String 字符串
     * @throws BusinessException
     */
    @Override
    public String delDept(Map<String, String> paramMap)
        throws BusinessException {
        String result = "error";
        String groupId = paramMap.get("groupId");
        String ids = paramMap.get("orgIds");
        try {
            // 判断用户id是否为空
            if (StringUtils.isNotBlank(ids)) {
                StringBuffer hql = new StringBuffer();
                hql.append("delete from GroupMember o where 1=1 ");
                hql.append(" and o.group.id=" + groupId);
                hql.append(" and o.org.orgId=" + ids);
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
     * 根据群组ID查询该群组下的所有组织
     * 
     * @Title getDeptByGroupId
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap
     * @return ListVo<GroupMember> 群组部门集合
     * @throws BusinessException
     */
    @Override
    public List<GroupMember> getDeptByGroupId(Map<String, String> paramMap)
        throws BusinessException {
        List<GroupMember> list = new ArrayList<GroupMember>();
        String groupId = paramMap.get("groupId");
        if (StringUtils.isNotBlank(groupId)) {
            // 获取参数
            StringBuffer hql =
                new StringBuffer("from GroupMember o where 1=1 ");
            hql.append(" and o.group.id=" + groupId);
            hql.append(" and o.org.orgId is not null");
            list = baseDao.queryEntitys(hql.toString());
        }
        return list;
    }
    
    /**
     * 获取用户列表
     * 
     * @Title getUserList
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap 请求参数Map
     * @return ListVo<UserVo>
     */
    @Override
    public ListVo<UserVo> getUserList(Map<String, String> paramMap) 
        throws BusinessException{
        // 获取参数
        int start = NumberUtils.toInt(paramMap.get("start"));
        int limit = NumberUtils.toInt(paramMap.get("limit"));
        String orgId = paramMap.get("orgId");
        String userName = paramMap.get("userName");
        String groupId = paramMap.get("groupId");
        
        String orgIds = orgService.getOrgIdsByPermissionScope(orgId,
            null);
        
        // 用户存储hql语句
        StringBuffer hql = new StringBuffer();
        hql.append("from OrgUser ou where 1=1");
        hql.append(" and ou.isDelete=" + Constant.STATUS_NOT_DELETE);
        hql.append(" and ou.user.status=" + Constant.STATUS_NOT_DELETE);
        hql.append(" and ou.organization.status=" + Constant.STATUS_NOT_DELETE);
        
        // 判断组织id是否为空
        if (StringUtils.isNotBlank(orgIds)) {
            hql.append(" and ou.organization.orgId in (" + orgIds + ")");
        }
        // 判断用户名是否为空
        if (StringUtils.isNotBlank(userName)) {
            hql.append(" and ou.user.realname like '%" + userName + "%'");
        }
        
        StringBuffer groupHql = new StringBuffer();
        groupHql.append(" from GroupMember o where 1=1 and o.user.userId is not null ");
        groupHql.append(" and o.group.id=" + groupId);
        groupHql.append(" and o.user.status = 0");
        
        List<GroupMember> list = baseDao.queryEntitys(groupHql.toString());
        
        StringBuffer buffer = new StringBuffer();
        // 判断集合是否为空
        if (list != null && list.size() > 0) {
            // 循环遍历集合，组装查询条件
            for (int i = 0; i < list.size(); i++) {
                // 判断是否为最后一个元素，不是就添加，分割
                if (i != list.size() - 1) {
                    buffer.append(list.get(i).getUser().getUserId() + ",");
                }
                else {
                    buffer.append(list.get(i).getUser().getUserId());
                }
            }
            // 封装查询条件
            hql.append(" and ou.user.userId not in (" + buffer.toString()
                + ")");
         }
            
//        hql.append(" order by ou.organization.orgId");
        
        // 查询用户集合
        List<OrgUser> ouList =
            baseDao.queryEntitys(hql.toString());
        
        String userIds = "";
        if (!CollectionUtils.isEmpty(ouList)) {
            for (OrgUser ou : ouList) {
                if (ou.getUser() != null) {
                    userIds = userIds + "," + ou.getUser().getUserId();
                }
            }
        }
        
        ListVo<UserVo> userVoListVo = new ListVo<UserVo>();
        List<UserVo> userVoList = new ArrayList<UserVo>();
        if (StringUtils.isNotBlank(userIds)) {
            StringBuffer userHql = new StringBuffer(" from User u");
            userHql.append(" where u.userId in (");
            userHql.append(userIds.substring(1));
            userHql.append(")");
            userHql.append(" and u.status = 0");
            
            int totalSize =
                baseDao.queryTotalCount(userHql.toString(),
                    new HashMap<String, Object>());
            
            userHql.append(" order by u.disOrder, u.userId");
            List<User> userList =
                (List<User>)baseDao.queryEntitysByPage(start,
                    limit,
                    userHql.toString(),
                    new HashMap<String, Object>());
            
            for (User u : userList) {
                UserVo uv = this.userService.convertVo(u);
                userVoList.add(uv);
            }
            userVoListVo.setList(userVoList);
            userVoListVo.setTotalSize(totalSize);
        }
        return userVoListVo;
    }
    
    /**
     * 查询当前组织的一级菜单
     * 
     * @Title getFirstLevelOrg
     * @author tangh
     * @date 2014年9月15日
     * @return List<Organization> 获取组织
     * @throws BusinessException
     */
    @Override
    public List<Organization> getFirstLevelOrg()
        throws BusinessException {
        StringBuffer hql = new StringBuffer();
        hql.append("from Organization o where o.organization is null and o.status="
            + Constant.STATUS_NOT_DELETE);
        hql.append(" order by o.disOrder");
        return baseDao.queryEntitys(hql.toString());
    }
    
    /**
     * 根据父节点取所有子节点
     * 
     * @Title getAllSonOrgByParentId
     * @author tangh
     * @date 2014年9月15日
     * @param parentId 父节点ID
     * @return List<Organization>
     * @throws BusinessException
     */
    @Override
    public List<Organization> getAllSonOrgByParentId(String parentId)
        throws BusinessException {
        StringBuffer hql = new StringBuffer();
        hql.append("from Organization o where o.status="
            + Constant.STATUS_NOT_DELETE);
        hql.append(" and o.organization.status=" + Constant.STATUS_NOT_DELETE);
        hql.append(" and o.organization.orgId=" + NumberUtils.toInt(parentId));
        hql.append(" order by o.disOrder Asc");
        List<Organization> list = baseDao.queryEntitys(hql.toString());
        return list;
    }
    
}
