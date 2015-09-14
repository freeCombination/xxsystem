package com.xx.system.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.role.entity.RoleMemberScope;
import com.xx.system.role.service.IRoleService;
import com.xx.system.user.entity.Group;
import com.xx.system.user.entity.GroupMember;
import com.xx.system.user.service.IGroupService;
import com.xx.system.user.vo.GroupVo;

/**
 * 群组管理服务接口实现类
 * 
 * @version V1.40,2014年9月15日 上午8:52:00
 * @see [相关类/方法]
 * @since V1.40
 */
@Service("groupService")
@Transactional(readOnly = false)
@SuppressWarnings("unchecked")
public class GroupServiceImpl implements IGroupService {
    
    /**
     * @Fields baseDao : 数据库操作基础方法
     */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    /**
     * @Fields roleService : 角色服务
     */
    @Autowired
    @Qualifier("roleService")
    private IRoleService roleService;
    
    /**
     * 保存群组
     * 
     * @Title addGroup
     * @author tangh
     * @date 2014年9月15日
     * @param groupVo 群组VO对象
     * @return 字符串
     * @throws BusinessException
     */
    @Override
    public String addGroup(GroupVo groupVo)
        throws BusinessException {
        String result = "error";
        try {
            // 判断群组VO是否为空
            if (groupVo != null) {
                // 创建对象并封装参数
                Group group = new Group();
                group.setGroupName(groupVo.getGroupName());
                group.setCreteDate(new Date());
                if (StringUtils.isNotBlank(groupVo.getRemark().trim())) {
                    group.setRemark(groupVo.getRemark().trim());
                }
                baseDao.save(group);
                result = "success";
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }
    
    /**
     * 删除群组
     * 
     * @Title delGroup
     * @author tangh
     * @date 2014年9月15日
     * @param id 群组ID
     * @return 字符串
     * @throws BusinessException
     */
    @Override
    public String delGroup(String ids)
        throws BusinessException {
        String result = "error";
        try {
            // 判断id是否为空
            if (StringUtils.isNotBlank(ids)) {
                String[] arr = ids.split(",");
                for (String str : arr) {
                    List<RoleMemberScope> roleMemberScopes =
                        roleService.getRoleMemberScopeByMemberId(str,
                            Constant.GROUP);
                    if (roleMemberScopes != null && roleMemberScopes.size() > 0) {
                        return result = "该群组已被分配权限，请删除权限再删除群组";
                    }
                    /*
                     * int existGm = getGMByGroId(NumberUtils.toInt(str)); if(existGm != 0){ return
                     * result = "该群组数据下的相关子信息会一并删除！"; }
                     */
                }
                
                StringBuffer gbbuffer = new StringBuffer();
                gbbuffer.append("delete from GroupMember o where o.group.id in ("
                    + ids + ")");
                baseDao.executeHql(gbbuffer.toString());
                
                StringBuffer buffer = new StringBuffer();
                buffer.append("delete from Group o where o.id in (" + ids + ")");
                baseDao.executeHql(buffer.toString());
                result = "success";
            }
            
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据群组主键ID查询是否有子记录
     * 
     * @Title getGMByGroId
     * @author lizhengc
     * @date 2014年9月2日
     * @param id 群组id
     * @return GroupMember 群组成员实体
     * @throws BusinessException
     */
    @Override
    public GroupMember getGMByGroId(String id)
        throws BusinessException {
        GroupMember gm = null;
        if (StringUtils.isNotBlank(id)) {
            String hql =
                "from GroupMember gm where  gm.group.id="
                    + id
                    + " and gm.user.status = 0 and gm.user.userId is not null and gm.org.orgId is null";
            List<GroupMember> gmsUser = baseDao.queryEntitys(hql);
            
            String hql1 =
                "from GroupMember gm where  gm.group.id="
                    + id
                    + " and gm.org.status = 0  and gm.org.orgId is not null and gm.user.userId is null";
            List<GroupMember> gmsOrg = baseDao.queryEntitys(hql1);
            
            if (gmsUser.size() > 0) {
                gm = gmsUser.get(0);
            }
            else if (gmsOrg.size() > 0) {
                gm = gmsOrg.get(0);
            }
        }
        return gm;
    }
    
    /**
     * 修改群组
     * 
     * @Title updateGroup
     * @author tangh
     * @date 2014年9月15日
     * @param groupVo 群组VO对象
     * @return 字符串
     * @throws BusinessException
     */
    @Override
    public String updateGroup(GroupVo groupVo)
        throws BusinessException {
        String result = "error";
        try {
            // 判断群组VO是否为空
            if (groupVo != null) {
                // 创建对象并封装参数
                Group group = new Group();
                group.setId(groupVo.getId());
                group.setGroupName(groupVo.getGroupName());
                // 存储当前时间并转换成字符串
                group.setCreteDate(new Date());
                if (StringUtils.isNotBlank(groupVo.getRemark().trim())) {
                    group.setRemark(groupVo.getRemark().trim());
                }
                baseDao.update(group);
                result = "success";
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据群组Id查询群组
     * 
     * @Title getGroupById
     * @author tangh
     * @date 2014年9月15日
     * @param id 群组ID
     * @return 群组对象
     * @throws BusinessException
     */
    @Override
    public Group getGroupById(Integer id)
        throws BusinessException {
        Group group = null;
        try {
            // 判断群组ID是否为空
            if (StringUtils.isNotBlank(String.valueOf(id))) {
                group = (Group)baseDao.queryEntityById(Group.class, id);
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return group;
    }
    
    /**
     * 查询群组
     * 
     * @Title getGroup
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap
     * @return ListVo<GroupVo> 群组集合
     * @throws BusinessException
     */
    @Override
    public ListVo<GroupVo> getGroup(Map<String, String> paramMap)
        throws BusinessException {
        ListVo<GroupVo> listVo = new ListVo<GroupVo>();
        List<Group> list = new ArrayList<Group>();
        int totalSize = 0;
        
        // 获取参数
        String start = paramMap.get("start");
        String limit = paramMap.get("limit");
        String groupName = paramMap.get("groupName");
        String flag = paramMap.get("flag");
        String roleId = paramMap.get("roleId");
        StringBuffer buffer = new StringBuffer();
        if ("QxGroup".equals(flag) || StringUtils.isNotBlank(flag)) {
            
            StringBuffer hql1 = new StringBuffer();
            hql1.append(" from RoleMemberScope rms where 1=1 and rms.group.id is not null  ");
            hql1.append(" and rms.user.userId is null ");
            hql1.append(" and rms.role.roleId = " + roleId + " ");
            
            List<RoleMemberScope> gList = baseDao.queryEntitys(hql1.toString());
            
            // 判断gList是否为空
            if (gList != null && gList.size() > 0) {
                // 遍历群组list集合
                for (int i = 0; i < gList.size(); i++) {
                    // 判断是否为最后一个元素，不是就添加，分割
                    if (i != gList.size() - 1) {
                        buffer.append(gList.get(i).getGroup().getId() + ",");
                    }
                    else {
                        buffer.append(gList.get(i).getGroup().getId());
                    }
                }
            }
        }
        // 查询hql语句
        StringBuffer hql = new StringBuffer();
        hql.append("from Group o where 1=1 ");
        
        // 判断buffer是否为空，筛选群组的id
        if (StringUtils.isNotBlank(buffer.toString())) {
            hql.append(" and o.id not in (" + buffer.toString() + ") ");
        }
        
        // 判断班组名称是否为空
        if (StringUtils.isNotBlank(groupName)) {
            hql.append(" and o.groupName like '%" + groupName + "%' ");
        }
        
        hql.append(" order by o.id");
        // 统计中条数
        totalSize =
            baseDao.queryTotalCount(hql.toString(),
                new HashMap<String, Object>());
        try {
            list =
                (List<Group>)baseDao.queryEntitysByPage(Integer.parseInt(start),
                    Integer.parseInt(limit),
                    hql.toString(),
                    new HashMap<String, Object>());
            // 用于存储群组VO的集合
            List<GroupVo> groupVoList = new ArrayList<GroupVo>();
            GroupVo groupVo = null;
            // 循环遍历结果集，封装对象并添加群组VO集合
            for (Group group : list) {
                // 封装对象
                groupVo = new GroupVo();
                groupVo.setId(group.getId());
                groupVo.setGroupName(group.getGroupName());
                groupVo.setRemark(group.getRemark());
                groupVoList.add(groupVo);
            }
            listVo.setList(groupVoList);
            listVo.setTotalSize(totalSize);
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return listVo;
    }
    
    /**
     * 验证群组名字
     * 
     * @Title validateGroupName
     * @author tangh
     * @date 2014年9月15日
     * @param paramMap 参数
     * @return Integer 整形
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> validateGroupName(Map<String, String> paramMap)
        throws BusinessException {
        Map<String, Object> validate = new HashMap<String, Object>();
        // 获取参数
        String groupName = paramMap.get("groupName");
        int totleSize = 0;
        
        // 判断参数是否为空
        if (StringUtils.isNotBlank(groupName)) {
            // 根据班组名称判断该班组名称是否已经使用
            StringBuffer hql = new StringBuffer();
            hql.append("from Group o where 1=1 ");
            hql.append(" and o.groupName='" + groupName + "'");

            // 总条数
            totleSize =
                baseDao.queryTotalCount(hql.toString(),
                    new HashMap<String, Object>());
        }
        
        if (totleSize > 0) {
            validate.put("success", true);
            validate.put("valid", false);
            validate.put("reason", "数据已存在");
        }
        else {
            validate.put("success", true);
            validate.put("valid", true);
            validate.put("reason", "");
        }
        return validate;
    }
    
    /**
     * 根据群组成员ID获取对应的群组 如果status为true的时候，代表是通过组织id来获取对应的群组id; 如果status为false的时候，代表是通过用户id来获取对应的群组id;
     * 
     * @Title getGroupByMemberId
     * @author liukang-wb
     * @Description:
     * @date 2014年9月15日
     * @param memberId
     * @return
     */
    @Override
    public List<GroupMember> getGroupByMemberId(String memberIds, String type)
        throws BusinessException {
        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        String hql = "from GroupMember gm where 1=1";
        // memberId不为空
        if (!"".equals(memberIds) && memberIds.length() != 0) {
            if (type.equals(Constant.ORG)) {
                hql += "and  gm.org.orgId in (" + memberIds + ")";
            }
            else if (type.equals(Constant.USER)) {
                hql += "and  gm.user.userId in (" + memberIds + ")";
            }
            groupMembers = baseDao.queryEntitys(hql);
        }
        return groupMembers;
    }
    
}
