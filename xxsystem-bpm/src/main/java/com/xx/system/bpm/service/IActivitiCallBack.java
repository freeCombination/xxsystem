package com.dqgb.sshframe.bpm.service;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

/**
 * 
 * 回调接口
 * 
 * @author zhxh
 * @version V1.20,2014-1-6 下午2:06:03
 * @since V1.20
 * @depricated
 */
public interface IActivitiCallBack
{
    /**
     * 
     * @Title doActiviti
     * @author zhxh
     * @Description: 回调接口
     * @date 2014-1-6
     * @param taskService
     * @param runtimeService
     * @param repositoryService
     * @param historyService
     * @param identityService
     * @return Object
     */
    public Object doActiviti(TaskService taskService,
        RuntimeService runtimeService, RepositoryService repositoryService,
        HistoryService historyService, IdentityService identityService);
}
