/**
 * @文件名 ActivitiCategoryServiceImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述  委托授权的逻辑层实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dqgb.sshframe.bpm.dao.IActivitiDelegateDao;
import com.dqgb.sshframe.bpm.entity.ActivitiDelegate;
import com.dqgb.sshframe.bpm.service.IActivitiDelegateService;
import com.dqgb.sshframe.bpm.vo.ActivitiDelegateVo;
import com.dqgb.sshframe.common.constant.SystemConstant;
import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.util.DateUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.org.entity.OrgUser;
import com.dqgb.sshframe.user.entity.User;
import com.dqgb.sshframe.user.service.IUserService;

/**
 * 
 * 委托授权的增删改查
 * 
 * @author zhxh
 * @version V1.20,2013-12-27 上午10:41:30
 * @since V1.20
 * @depricated
 */
@Transactional(readOnly = true)
@Service("activitiDelegateService")
public class ActivitiDelegateServiceImpl implements IActivitiDelegateService
{
    /** 委托持久层接口 */
    @Autowired
    @Qualifier("activitiDelegateDao")
    private IActivitiDelegateDao activitiDelegateDao;
    
    /** @Fields baseDao : 基础DAO */
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    /** 用户service接口 */
    @Autowired
    @Qualifier("userService")
    private IUserService userService;
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addDelegate(ActivitiDelegateVo delegateVo, User currentUser)
    {
        try
        {
            ActivitiDelegate delegate = validDelegate(delegateVo, currentUser);
            this.activitiDelegateDao.addDelegate(delegate);
        }
        catch (ServiceException e)
        {
            throw new ServiceException(e.getMessageKey(),e);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateDelegate(ActivitiDelegateVo delegateVo, User currentUser)
        throws ServiceException
    {
        try
        {
            Map paramMap = new HashMap();
            paramMap.put("isDelete", 0);
            paramMap.put("id", delegateVo.getId());
            List<ActivitiDelegate> delegates =
                this.activitiDelegateDao.getActivitiDelegate(paramMap);
            if (CollectionUtils.isEmpty(delegates))
            {
                throw new ServiceException("托管数据已经不存在!");
            }
            ActivitiDelegate updateDelegate = null;
            if (delegateVo.getDelegateStatus() == 0)
            {
                ActivitiDelegate delegate =
                    validDelegate(delegateVo, currentUser);
                updateDelegate = delegates.get(0);
                updateDelegate.setDelegateEndTime(delegate.getDelegateEndTime());
                updateDelegate.setDelegateStartTime(delegate.getDelegateStartTime());
                updateDelegate.setDelegateStatus(delegate.getDelegateStatus());
                updateDelegate.setDelegateUser(delegate.getDelegateUser());
            }
            else
            {
                updateDelegate = delegates.get(0);
                updateDelegate.setDelegateEndTime(DateUtil.stringToDate(delegateVo.getDelegateEndTime(),
                    SystemConstant.DATE_PATTEN_LONG));
                updateDelegate.setDelegateStartTime(DateUtil.stringToDate(delegateVo.getDelegateStartTime(),
                    SystemConstant.DATE_PATTEN_LONG));
                updateDelegate.setDelegateStatus(delegateVo.getDelegateStatus());
                List<User> delegateUsers =
                    this.userService.getUserListByLoginNames(delegateVo.getDelegateUserId());
                if (delegateUsers.size() < 1)
                {
                    throw new ServiceException("没有找到被委托人员");
                }
                updateDelegate.setDelegateUser(delegateUsers.get(0));
            }
            this.activitiDelegateDao.updateDelegate(updateDelegate);
            
        }
        catch (ServiceException e)
        {
            throw new ServiceException(e.getMessageKey(),e);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(),e);
        }
        
    }
    
    /**
     * 
     * @Title validDelegate
     * @author zhxh
     * @Description:校验添加和修改的数据是否正确
     * @date 2013-12-27
     * @param delegateVo
     */
    private ActivitiDelegate validDelegate(ActivitiDelegateVo delegateVo,
        User currentUser)
    {
        ActivitiDelegate d = new ActivitiDelegate();
        try {
			d.setDelegateStartTime(DateUtil.stringToDate(delegateVo.getDelegateStartTime(),
			    SystemConstant.DATE_PATTEN_LONG));
			d.setDelegateEndTime(DateUtil.stringToDate(delegateVo.getDelegateEndTime(),
			    SystemConstant.DATE_PATTEN_LONG));
			List<User> delegateUsers =
			    this.userService.getUserListByLoginNames(delegateVo.getDelegateUserId());
			if (delegateUsers.size() < 1)
			{
			    throw new ServiceException("没有找到被委托人员");
			}
			String startTime = delegateVo.getDelegateStartTime();
			String endTime = delegateVo.getDelegateEndTime();
			d.setDelegateUser(delegateUsers.get(0));
			d.setOwner(currentUser);
			d.setDelegateStatus(delegateVo.getDelegateStatus());
			d.setIsDelete(0);
			String extendSql = "";
			Map map = new HashMap();
			if (delegateVo.getId() != null && delegateVo.getId() != 0)
			{
			    extendSql = " and a.id!=:delegateId";
			    map.put("delegateId", delegateVo.getId());
			}
			this.checkDeleteDelegateIncludeA(delegateVo, 
			    currentUser, extendSql, map);
			this.checkDeleteDelegateIncludeB(delegateVo, currentUser, 
			    extendSql, map);
			this.checkDeleteDelegateStartTime(delegateVo, currentUser,
			    extendSql, map);
		} catch (BusinessException e) {
			throw new ServiceException("校验添加和修改数据失败！",e);
		}
        return d;
    }
    /**
     * 
    * @Title checkDeleteDelegateIncludeA
    * @author zhxh
    * @Description:有效数据的时间段包含了该时间段 
    * @date 2014-1-6
     */
    private void checkDeleteDelegateIncludeA(
        ActivitiDelegateVo delegateVo,User currentUser,
        String extendSql,Map conditionMap)
    {

        StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from ActivitiDelegate a ");
        hql.append(" where a.owner.username='" + currentUser.getUsername()
            + "'");
        hql.append(" and a.delegateStartTime<= ")
            .append("to_date('")
            .append(delegateVo.getDelegateStartTime())
            .append("','yyyy-MM-dd hh24:mi:ss')");
        hql.append(" and a.delegateEndTime>=")
            .append("to_date('")
            .append(delegateVo.getDelegateEndTime())
            .append("','yyyy-MM-dd hh24:mi:ss')");
        hql.append(" and a.isDelete=0 and a.delegateStatus=0  ");
        hql.append(extendSql);

        int count =
            this.activitiDelegateDao.queryTotalCount(hql.toString(), conditionMap);
        if (count > 0)
        {
            throw new ServiceException("您的有效数据的时间段包含了该时间段！");
        }
    }
    
    /**
     * 
    * @Title checkDeleteDelegateIncludeB
    * @author zhxh
    * @Description:该时间段包含了您的有效数据的时间段
    * @date 2014-1-6
     */
    private void checkDeleteDelegateIncludeB(
        ActivitiDelegateVo delegateVo,User currentUser,
        String extendSql,Map conditionMap)
    {

        StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from ActivitiDelegate a ");
        hql.append(" where a.owner.username='" + currentUser.getUsername()
            + "'");
        hql.append(" and a.delegateStartTime>= ")
            .append("to_date('")
            .append(delegateVo.getDelegateStartTime())
            .append("','yyyy-MM-dd hh24:mi:ss')");
        hql.append(" and a.delegateEndTime<=")
            .append("to_date('")
            .append(delegateVo.getDelegateEndTime())
            .append("','yyyy-MM-dd hh24:mi:ss')");
        hql.append(" and a.isDelete=0 and a.delegateStatus=0  ");
        hql.append(extendSql);
        int count = this.activitiDelegateDao.queryTotalCount(hql.toString(), conditionMap);
        if (count > 0)
        {
            throw new ServiceException("该时间段包含了您的有效数据的时间段！");
        }
    }
    

    /**
     * 
    * @Title checkDeleteDelegateStartTime
    * @author zhxh
    * @Description: 开始时间在您的有效数据的时间段内
    * @date 2014-1-6
     */
    private void checkDeleteDelegateStartTime(
        ActivitiDelegateVo delegateVo,User currentUser,
        String extendSql,Map conditionMap)
    {

        StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from ActivitiDelegate a ");
        hql.append(" where a.owner.username='" + currentUser.getUsername()
            + "'");
        hql.append("  and a.delegateStartTime<= ")
            .append("to_date('")
            .append(delegateVo.getDelegateStartTime())
            .append("','yyyy-MM-dd hh24:mi:ss')");
        hql.append("  and a.delegateEndTime>=")
            .append("to_date('")
            .append(delegateVo.getDelegateStartTime())
            .append("','yyyy-MM-dd hh24:mi:ss')");
        hql.append(" and a.isDelete=0 and a.delegateStatus=0 ");
        hql.append(extendSql);
        int count = this.activitiDelegateDao.queryTotalCount(hql.toString(), conditionMap);
        if (count > 0)
        {
            throw new ServiceException("开始时间在您的有效数据的时间段内！");
        }
    }
    

    /**
     * 
    * @Title checkDeleteDelegateEndTime
    * @author zhxh
    * @Description: 结束时间在您的有效数据的时间段内
    * @date 2014-1-6
     */
    private void checkDeleteDelegateEndTime(
        ActivitiDelegateVo delegateVo,User currentUser,
        String extendSql,Map conditionMap)
    {

        StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from ActivitiDelegate a ");
        hql.append(" where a.owner.username='" + currentUser.getUsername()
            + "'");
        hql.append(" and a.delegateStartTime<= ")
            .append("to_date('")
            .append(delegateVo.getDelegateEndTime())
            .append("','yyyy-MM-dd hh24:mi:ss')");
        hql.append(" and a.delegateEndTime>=")
            .append("to_date('")
            .append(delegateVo.getDelegateEndTime())
            .append("','yyyy-MM-dd hh24:mi:ss')");
        hql.append(" and a.isDelete=0 and a.delegateStatus=0");
        hql.append(extendSql);
        int count = this.activitiDelegateDao.queryTotalCount(hql.toString(), conditionMap);
        if (count > 0)
        {
            throw new ServiceException("结束时间在您的有效数据的时间段内！");
        }
    }
    @Override
    public void deleteDelegate(String ids)
    {
        this.activitiDelegateDao.deleteDelegate(ids);
        
    }
    
    @Override
    public ListVo getDelegateByPage(Map paramMap)
    {
        ListVo listVo = this.activitiDelegateDao.getDelegateByPage(paramMap);
        return listVo;
    }
    
    @Override
    public List<ActivitiDelegate> getDelegateByUserIdStartTime(Date startTime,
        String userId)
        throws ServiceException
    {
        try
        {
            return this.activitiDelegateDao.getDelegateByUserIdStartTime(startTime,
                userId);
        }
        catch (ServiceException e)
        {
            throw new ServiceException(e.getMessageKey(),e);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    
    public void uploadPhoto(com.dqgb.sshframe.user.entity.User currentUser,
            FileInputStream fin, String tag) throws IOException
        {
            
            if (currentUser.getPersonImage() == null)
            {
                String hqltemp =
                    " from User where userId = " + currentUser.getUserId();
                baseDao.queryEntitys(hqltemp);
                //currentUser.setPersonImage(Hibernate.createBlob(new byte[1]));
                baseDao.getHibernateTemp().clear();
                
                baseDao.updateEntity(currentUser);
            }
            if (currentUser.geteSignature() == null)
            {
                String hqltemp =
                    " from User where userId = " + currentUser.getUserId();
                baseDao.queryEntitys(hqltemp);
                //currentUser.setPersonImage(Hibernate.createBlob(new byte[1]));
                baseDao.getHibernateTemp().clear();
                
                baseDao.updateEntity(currentUser);
            }
            
            String hqltemp = " from User where userId = " + currentUser.getUserId();
            baseDao.queryEntitys(hqltemp);
           
                baseDao.getHibernateTemp().clear();
                
                if ("photo".equals(tag))
                {
                    //currentUser.setPersonImage(Hibernate.createBlob(fin));
                    baseDao.getHibernateTemp().flush();
                }
                else
                {
                    //currentUser.seteSignature((Hibernate.createBlob(fin)));
                    currentUser.setPersonImage(currentUser.getPersonImage());
                    baseDao.getHibernateTemp().flush();
                }
                
            baseDao.updateEntity(currentUser);
            // baseDao.getHibernateTemp().flush();
            // baseDao.getHibernateTemp().clear();
            
        }
    
    public InputStream previewImg(com.dqgb.sshframe.user.entity.User currentUser) throws SQLException
    {
        String hql2 = " from User where userId = " + currentUser.getUserId();
        List<com.dqgb.sshframe.user.entity.User> userResult2 =
            baseDao.queryEntitys(hql2);
        baseDao.getHibernateTemp().flush();
        baseDao.getHibernateTemp().clear();
        InputStream inputStream = null;
        //Blob blob = userResult2.get(0).getPersonImage();
        
            //inputStream = blob.getBinaryStream();
       
        return inputStream;
        
    }
    
    public Map<String, Object> queryUserInfoById(Map<String, String> params){
    	String userId = params.get("userId");

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
        
		String hql ="from OrgUser where user.userId ="+userId;
		List<OrgUser> orgUsers =baseDao.queryEntitys(hql);
		StringBuffer orgNames = new StringBuffer("");
		for(OrgUser ou:orgUsers){
			orgNames.append(
			ou.getOrganization().getOrgName());
		}
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("orgName",orgNames.toString());
		result.put("data", data);

		return result;
    }

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
    
    
}
