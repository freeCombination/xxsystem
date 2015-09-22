package com.xx.system.org.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.exception.ServiceException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.service.IOrgUserService;
import com.xx.system.user.entity.User;

/**
 * 用户组织业务逻辑实现
 * 
 * @version V1.20,2013-11-25 下午4:06:14
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings("unchecked")
@Service("orgUserService")
public class OrgUserServiceImpl implements IOrgUserService {
    
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    /**
     * 添加用户组织
     * 
     * @Title addOrgUser
     * @author wanglc
     * @date 2013-11-25
     * @param orgUser 组织用户对象
     * @throws BusinessException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    // @Log(operationType = "添加", operationName = "用户组织关系数据")
    @Override
    public void addOrgUser(OrgUser orgUser)
        throws BusinessException {
        this.baseDao.saveOrUpdate(orgUser);
    }
    
    /**
     * 修改组织用户关系
     * 
     * @Title modifyOrgUser
     * @author wanglc
     * @date 2013-11-25
     * @param orgUser 用户组织对象
     * @throws BusinessException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    // @Log(operationType = "修改", operationName = "用户组织关系数据")
    @Override
    public void modifyOrgUser(OrgUser orgUser)
        throws BusinessException {
        this.baseDao.saveOrUpdate(orgUser);
    }
    
    /**
     * 删除组织用户关系
     * 
     * @Title deleteOrgUsers
     * @author wanglc
     * @date 2013-11-25
     * @param orgUserList 组织用户列表
     * @throws BusinessException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    // @Log(operationType = "删除", operationName = "用户组织关系数据")
    public void deleteOrgUsers(List<OrgUser> orgUserList)
        throws BusinessException {
        if (orgUserList != null && orgUserList.size() > 0) {
            StringBuffer orgUserIds = new StringBuffer();
            for (int i = 0; i < orgUserList.size(); i++) {
                OrgUser ou = orgUserList.get(i);
                orgUserIds.append(ou.getPkOrgUserId());
                if (i != orgUserList.size() - 1) {
                    orgUserIds.append(",");
                }
            }
            baseDao.executeHql("delete from OrgUser o where o.pkOrgUserId in( "+orgUserIds.toString()+")");
           /* this.baseDao.delete(orgUserIds.toString(),
                "OrgUser",
                "isDelete",
                "pkOrgUserId",
                "1");*/
        }
    }
    
    /**
     * 根据主键获取组织用户对象
     * 
     * @Title getOrgUserById
     * @author wanglc
     * @date 2013-11-25
     * @param orgUserId 组织用户主键
     * @return OrgUser 用户组织关系对象
     * @throws BusinessException
     */
    @Override
    public OrgUser getOrgUserById(int orgUserId)
        throws BusinessException {
        String hql =
            "from OrgUser ou where ou.isDelete = " + Constant.STATUS_NOT_DELETE
                + " and ou.pkOrgUserId = " + orgUserId;
        List<OrgUser> ouList = this.baseDao.queryEntitys(hql);
        if (ouList.size() == 1) {
            return ouList.get(0);
        }
        else {
            return null;
        }
    }
    
    /**
     * 获取当前用户的组织用户关系列表
     * 
     * @Title getOrgUserByUserId
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @return List<OrgUser>
     * @throws BusinessException
     */
    @Override
    public List<OrgUser> getOrgUserByUserId(int userId)
        throws BusinessException {
        String hql =
            "from OrgUser ou where ou.isDelete =" + Constant.STATUS_NOT_DELETE
                + " and ou.user.userId = " + userId;
        List<OrgUser> ouList = this.baseDao.queryEntitys(hql);
        if (ouList.size() > 0) {
            return ouList;
        }
        else {
            return null;
        }
    }
    
    /**
     * 删除当前用户的组织用户关系
     * 
     * @Title deleteOrgUserByUser
     * @author wanglc
     * @date 2013-11-25
     * @param user 用户对象
     * @throws BusinessException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    //@Log(operationType = "删除", operationName = "根据用户对象删除对应用户组织关系数据")
    public void deleteOrgUserByUser(User user)
        throws BusinessException {
        this.baseDao.delete("1",
            "OrgUser",
            "isDelete",
            user.getUserId() + "",
            Constant.STATUS_DELETE_BYXTJG + "");
    }
    
    /**
     * 删除当前组织的组织用户关系
     * 
     * @Title deleteOrgUserByOrg
     * @author wanglc
     * @date 2013-11-25
     * @param org 组织对象
     * @throws BusinessException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteOrgUserByOrg(Organization org)
        throws BusinessException {
        this.baseDao.delete("1",
            "OrgUser",
            "isDelete",
            org.getOrgId() + "",
            Constant.STATUS_DELETE_BYXTJG + "");
    }
    
    /**
     * 获取当前组织的组织用户关系
     * 
     * @Title getOrgUserByOrg
     * @author wanglc
     * @date 2013-11-25
     * @param org 组织对象
     * @return List<OrgUser> 用户组织关系数据集合
     * @throws BusinessException
     */
    @Override
    public List<OrgUser> getOrgUserByOrg(Organization org)
        throws BusinessException {
        String hql =
            "from OrgUser ou where ou.isDelete = " + Constant.STATUS_NOT_DELETE
                + " and ou.organization.orgId = " + org.getOrgId();
        List<OrgUser> ouList = this.baseDao.queryEntitys(hql);
        if (ouList.size() > 0) {
            return ouList;
        }
        else {
            return null;
        }
    }
    
    /**
     * 根据组织和用户查询用户组织关系
     * 
     * @Title getOrgUserByOrgAndUser
     * @author yzg
     * @date 2014-2-10
     * @param org 组织对象
     * @param user 用户对象
     * @return List<OrgUser> 用户组织关系数据集合
     * @throws BusinessException
     */
    @Override
    public List<OrgUser> getOrgUserByOrgAndUser(Organization org, User user)
        throws BusinessException {
        String hql =
            "from OrgUser ou where ou.isDelete = " + Constant.STATUS_NOT_DELETE
                + " and ou.organization.orgId = " + org.getOrgId()
                + " and ou.user.userId = " + user.getUserId();
        List<OrgUser> ouList = this.baseDao.queryEntitys(hql);
        if (ouList.size() > 0) {
            return ouList;
        }
        else {
            return null;
        }
    }
    
    /**
     * 查询用户组织
     * 
     * @Title getOrgUserListByPage
     * @author wanglc
     * @date 2013-11-25
     * @param start 查询开始
     * @param limit 每页限制条数
     * @throws ServiceException
     * @return ListVo<OrgUser> 用户组织列表集合
     */
    @Override
    public ListVo<OrgUser> getOrgUserListByPage(int start, int limit)
        throws BusinessException {
        ListVo<OrgUser> orgUserListVo = new ListVo<OrgUser>();
        String hql =
            "from OrgUser ou where ou.isDelete =  "
                + Constant.STATUS_NOT_DELETE;
        List<OrgUser> ouList =
            this.baseDao.query(hql, new Object[] {}, start, limit);
        String totalCountSQL =
            "select count(1） from T_ORG_USER where ISDELETE = "
                + Constant.STATUS_NOT_DELETE;
        int totalCount = this.baseDao.queryCountSQL(totalCountSQL);
        if (ouList.size() > 0) {
            orgUserListVo.setList(ouList);
        }
        else if (totalCount > 0) {
            orgUserListVo.setTotalSize(totalCount);
        }
        else {
            return null;
        }
        return orgUserListVo;
    }
    
    /**
     * 新增/更新用户组织
     * 
     * @Title addUpdateUser
     * @author wanglc
     * @date 2013-11-25
     * @param ou 用户组织对象
     * @throws BusinessException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    //@Log(operationType = "修改", operationName = "用户组织关系数据")
    @Override
    public void addUpdateUser(OrgUser ou)
        throws BusinessException {
        if (ou != null) {
            baseDao.saveOrUpdate(ou);
        }
    }
    
    /**
     * @Title getOrgUserList
     * @author wanglc
     * @Description: 取所有的组织用户关系
     * @date 2013-12-18
     * @param baseDao
     * @return
     */
    public static List<OrgUser> getOrgUserList(IBaseDao baseDao) {
        String hql =
            "from OrgUser ou where ou.isDelete = " + Constant.STATUS_NOT_DELETE;
        List<OrgUser> orgUserList = baseDao.queryEntitys(hql);
        return orgUserList;
    }
    
    /**
     * 根据用户主键获取当前用户所属组织
     * 
     * @Title getLocalOrganizationByUserId
     * @author wanglc
     * @date 2014-2-21
     * @param userId 用户ID
     * @return List<Organization> 组织集合
     */
    @Override
    public List<Organization> getLocalOrganizationByUserId(int userId) {
        String hql =
            "from OrgUser ou where ou.user.userId = " + userId
                + " and ou.isDelete = " + Constant.STATUS_NOT_DELETE;
        List<OrgUser> orgUserList = baseDao.queryEntitys(hql);
        if (orgUserList != null && orgUserList.size() > 0) {
            List<Organization> orgList = new ArrayList<Organization>();
            for (OrgUser ou : orgUserList) {
                Organization org =
                    (Organization)baseDao.queryEntityById(Organization.class,
                        ou.getOrganization().getOrgId());
                orgList.add(org);
            }
            return orgList;
        }
        else {
            return null;
        }
    }
    
    /**
     * @Title deleteLocalOrgUser
     * @author wanglc
     * @Description: 删除本地组织用户，同步时调用
     * @date 2013-12-23
     * @param baseDao
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    //@Log(operationType = "删除", operationName = "用户组织关系数据")
    @Override
    public void deleteLocalOrgUser() {
        String hql =
            "from OrgUser ou where ou.organization.orgFrom <> 0";
        List<OrgUser> ouList = baseDao.queryEntitys(hql);
        baseDao.deleteEntities(ouList);
    }
    
    /**
     * 获取当前组织下的人数
     * 
     * @Title getUserCountsByOrgId
     * @author wanglc
     * @date 2014-2-11
     * @return int 当前组织下的人数
     * @throws BusinessException
     */
    @Override
    public int getUserCountsByOrgIds(String ids) {
        String hql =
            "from OrgUser ou where ou.organization.orgId in ("
                + ids
                + ") and ou.organization.orgCode is not null and ou.user.status = "
                + Constant.STATUS_NOT_DELETE + 
//                " and ou.user.enable = "
//                + Constant.ENABLE +
                " and ou.isDelete = "
                + Constant.STATUS_NOT_DELETE + "";
        return baseDao.queryTotalCount(hql, new HashMap<String, Object>());
    }
}
