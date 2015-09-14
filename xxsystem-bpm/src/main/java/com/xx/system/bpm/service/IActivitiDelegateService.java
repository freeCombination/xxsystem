/**
 * @文件名 IActivitiDelegateService.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述  委托授权逻辑层接口
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.bpm.entity.ActivitiDelegate;
import com.dqgb.sshframe.bpm.vo.ActivitiDelegateVo;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.user.entity.User;

/**
 * 
 * 委托授权的逻辑层接口
 * 
 * @author zhxh
 * @version V1.20,2013-12-25 下午5:36:23
 * @since V1.20
 * 
 */
public interface IActivitiDelegateService {
	/**
	 * 
	 * @Title addDelegate
	 * @author zhxh
	 * @Description:添加委托
	 * @date 2013-12-27
	 * @param delegateVo
	 *            currentUser
	 */
	void addDelegate(ActivitiDelegateVo delegateVo, User currentUser);

	/**
	 * 
	 * @Title updateDelegate
	 * @author zhxh
	 * @Description:更新托管设置
	 * @date 2013-12-27
	 * @param delegateVo
	 */
	void updateDelegate(ActivitiDelegateVo delegateVo, User currentUser);

	/**
	 * 
	 * @Title deleteDelegate
	 * @author zhxh
	 * @Description:删除托管
	 * @date 2013-12-27
	 */
	void deleteDelegate(String ids);

	/**
	 * 
	 * @Title getDelegateByPage
	 * @author zhxh
	 * @Description:分页查询托管
	 * @date 2013-12-27
	 * @return ListVo
	 */
	ListVo getDelegateByPage(Map paramMap);

	/**
	 * 
	 * @author 张小虎
	 * @Description:通过开始日期获得日期段的托管数据
	 * @param startTime
	 * @param userId
	 *            托管人id
	 * @throws ServiceException
	 * @return List<ActivitiDelegate>
	 */
	List<ActivitiDelegate> getDelegateByUserIdStartTime(Date startTime,
			String userId) throws ServiceException;

	/**
	 * @Title uploadPhoto
	 * @author wlt
	 * @Description: 上传头像至Oracle 数据库
	 * @date 2014-8-14
	 * @param currentUser
	 * @param fin
	 * @throws IOException
	 */
	public void uploadPhoto(User currentUser, FileInputStream fin, String tag)
			throws IOException;

	/**
	 * @Title previewImg
	 * @author wlt
	 * @Description: 图片预览功能
	 * @date 2014-8-15
	 * @param currentUser
	 * @return
	 * @throws SQLException
	 */
	public InputStream previewImg(com.dqgb.sshframe.user.entity.User currentUser)
			throws SQLException;

	/**
	 * @Title queryUserInfoById
	 * @author wlt
	 * @Description: 根据员工ID查询员工信息（部门）
	 * @date 2014-8-15
	 * @param map
	 * @return
	 */
	Map<String, Object> queryUserInfoById(Map<String, String> map);

}
