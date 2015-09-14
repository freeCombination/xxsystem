/**
 * @文件名 IActivitiDelegateDao.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程表单的持久层接口
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.bpm.entity.ActivitiDelegate;
import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.vo.ListVo;

/**
 * 
 * 委托授权逻辑层接口
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 上午9:07:02
 * @since V1.20
 * @depricated
 */

public interface IActivitiDelegateDao extends IBaseDao
{
    /**
     * 
     * @Title addDelegate
     * @author zhxh
     * @Description:添加委托
     * @date 2013-12-27
     * @param delegate
     */
    void addDelegate(ActivitiDelegate delegate);
    
    /**
     * 
     * @Title updateDelegate
     * @author zhxh
     * @Description: 更新托管信息
     * @date 2013-12-27
     * @param delegate
     */
    void updateDelegate(ActivitiDelegate delegate);
    
    /**
     * 
     * @Title getActivitiDelegate
     * @author zhxh
     * @Description:查询托管
     * @date 2013-12-27
     * @param paramMap
     * @return List<ActivitiDelegate>
     */
    List<ActivitiDelegate> getActivitiDelegate(Map paramMap);
    
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
     * @param paramMap
     * @date 2013-12-27
     * @return ListVo
     */
    ListVo getDelegateByPage(Map paramMap);
    
    /**
     * 
     * 
     * @author 张小虎
     * @param startTime
     * @param userId 托管人id
     * @Description:通过开始日期获得日期段的托管数据
     * @throws ServiceException
     * @return List<ActivitiDelegate>
     */
    List<ActivitiDelegate> getDelegateByUserIdStartTime(Date startTime,
        String userId);
    
}
