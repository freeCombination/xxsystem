package com.xx.system.log.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.log.entity.Log;
import com.xx.system.log.vo.LogVo;

/**
 * 日志接口定义
 * 
 * @version V1.20,2013-11-25 下午3:19:33
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public interface ILogService {
    
    /**
     * 添加日志
     * 
     * @Title addLog
     * @Description:
     * @date 2013-11-25
     * @param log 日志对象
     * @throws BusinessException
     */
    public void addLog(Log log)
        throws BusinessException;
    
    /**
     * 查询日志
     * 
     * @Title getLogList
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param params 参数Map
     * @return ListVo<Log> 日志列表集合
     */
    public ListVo<LogVo> getLogList(Map<String, String> params)
        throws BusinessException;
    
    /**
     * 删除日志
     * 
     * @Title delLog
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param params 参数Map
     * @return String 删除结果
     */
    public String delLog(Map<String, String> params)
        throws BusinessException;
    
    /**
     * 查询日志类型
     * 
     * @Title getLogType
     * @author ls
     * @Description:
     * @date 2014-2-24
     * @param params 字典code
     * @return List 字典数据集合
     */
    public List<Dictionary> getLogType(Map<String, String> params)
        throws BusinessException;
}
