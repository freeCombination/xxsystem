/**
 * @文件名 IActivitiTaskDao.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程任务的持久层接口
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.bpm.entity.ActivitiTaskRole;
import com.dqgb.sshframe.bpm.vo.TaskVo;
import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.vo.ListVo;

/**
 * 
 * 流程任务逻辑层接口
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 上午9:07:02
 * @since V1.20
 * @depricated
 */

public interface IActivitiTaskDao extends IBaseDao
{
    /**
     * 
     * @Title getDoneTaskListByPage
     * @author zhxh
     * @Description:分页查询已处理任务
     * @date 2013-12-27
     * @param paramMap
     * @return ListVo
     */
    ListVo<Object[]> getDoneTaskListByPage(Map paramMap);
    
    /**
     * 
     * @Title getToDoTaskListByPage
     * @author zhxh
     * @Description:分页查询待办理任务
     * @date 2013-12-27
     * @param paramMap
     * @return ListVo
     */
    ListVo<Object[]> getToDoTaskListByPage(Map paramMap);
    
    /**
     * 
     * @Title updateHistoryTask
     * @author zhxh
     * @Description:更新历史任务
     * @date 2013-12-30
     * @param vo
     */
    void updateHistoryTask(TaskVo vo);
    
    /**
     * 
     * @Title addActivitiTaskRole
     * @author zhxh
     * @Description:保存任务角色管理
     * @date 2013-12-30
     * @param activitiTaskRoles
     */
    void addActivitiTaskRole(List<ActivitiTaskRole> activitiTaskRoles);
    
    /**
     * 
     * @Title getActivitiTaskRole
     * @author zhxh
     * @Description:获取任务角色
     * @date 2013-12-27
     * @param paramMap
     * @return List<ActivitiTaskRole>
     */
    List<ActivitiTaskRole> getActivitiTaskRole(Map paramMap);
    
}
