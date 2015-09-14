/**
 * @文件名 IActivitiFormDao.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程表单的持久层接口
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.bpm.entity.ActivitiForm;
import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.vo.ListVo;

/**
 * 
 * 流程表单逻辑层接口
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 上午9:07:02
 * @since V1.20
 * @depricated
 */

public interface IActivitiFormDao extends IBaseDao
{
    /**
     * 
     * @Title addActivitiForm
     * @author zhxh
     * @Description:添加表单地址
     * @date 2013-12-26
     * @param activitiForm
     */
    void addActivitiForm(ActivitiForm activitiForm);
    
    /**
     * 
     * @Title updateActivitiForm
     * @author zhxh
     * @Description:修改表单地址
     * @date 2013-12-26
     * @param activitiForm
     */
    void updateActivitiForm(ActivitiForm activitiForm);
    
    /**
     * 
     * @Title getActivitiForm
     * @author zhxh
     * @Description:查询表单 字段-值
     * @date 2013-12-26
     * @param map
     * @return List<ActivitiForm>
     */
    List<ActivitiForm> getActivitiForm(Map map);
    
    /**
     * 
     * @Title getActivitiFormByPage
     * @author zhxh
     * @Description:分页查询表单地址
     * @date 2013-12-26
     * @param map
     * @return ListVo<ActivitiForm>
     */
    ListVo<ActivitiForm> getActivitiFormByPage(Map map);
    
}
