package com.xx.system.log.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.DateUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.log.entity.Log;
import com.xx.system.log.service.ILogService;
import com.xx.system.log.vo.LogVo;

/**
 * 详细实现日志接口定义的方法
 * 
 * @version V1.20,2013-11-25 下午3:18:46
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Service("logService")
public class LogServiceImpl implements ILogService {
    
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    /**
     * 添加日志
     * 
     * @Title addLog
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param log 日志对象
     * @throws BusinessException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addLog(Log log)
        throws BusinessException {
        // 取得字典类型的uuid
        log.setLogTypeUUID(log.getType().getFkDictTypeUUID());
        // 执行保存
        this.baseDao.save(log);
    }
    
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
    @Override
    public ListVo<LogVo> getLogList(Map<String, String> params)
        throws BusinessException {
        // 获取页面参数
        String startTime = params.get("startDate");
        String endTime = params.get("endDate");
        String type = params.get("type");
        String logContent = params.get("logContent");
        int start = NumberUtils.toInt(params.get("start"));
        int limit = NumberUtils.toInt(params.get("limit"));
        ListVo<LogVo> vo = new ListVo<LogVo>();
        List<LogVo> voList = new ArrayList<LogVo>();
        Map pram = new HashMap();
        // 新建查询HQL语句
        StringBuffer logHql = new StringBuffer("from Log l ");
        logHql.append(" where l.type.pkDictionaryId = ");
        logHql.append(type);
        logHql.append(StringUtils.isNotBlank(logContent) ? 
            " and l. opContent like '%" + logContent + "%'" : "");
        if(StringUtils.isNotBlank(startTime)){
        	pram.put("startTime", DateUtil.string2date(startTime));
        	logHql.append(StringUtils.isNotBlank(startTime) ? 
        	            " and l.opDate >= :startTime" : "");
        }
        if(StringUtils.isNotBlank(endTime)){
        	Date d = new Date(DateUtil.string2date(endTime).getTime()+24*60*60*1000);
        	pram.put("endTime", d);
        	logHql.append(StringUtils.isNotBlank(endTime) ? 
                    " and l.opDate<:endTime" : "");
        }
        
        // 查询总条数
        int totalCount = baseDao.queryTotalCount(logHql.toString(), pram);
        
        logHql.append(" order by l.opDate desc ");
        // queryEntitysByPage(start, limit, hql, new HashMap());
        // 分页查询
        List<Log> logList =
            (List<Log>)baseDao.findPageByQuery(start,
                limit,
                logHql.toString(),
                pram);
        
        // entity转换为vo
        if (logList != null && logList.size() > 0) {
            for (Log l : logList) {
                LogVo lv = new LogVo(l);
                voList.add(lv);
            }
        }
        // 设置返回结果
        vo.setList(voList);
        vo.setTotalSize(totalCount);
        return vo;
    }
    
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public String delLog(Map<String, String> params)
        throws BusinessException {
        String ids = params.get("ids");
        if (ids == null || "".equals(ids)) {
            return "{success:'false',msg:'删除日志失败'}";
        }
        else {
            for (String logId : ids.split(",")) {
                baseDao.deleteEntityById(Log.class,
                    NumberUtils.toLong(logId));
            }
        }
        return "{success:'true',msg:'删除日志成功'}";
    }
    
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
    @Override
    public List<Dictionary> getLogType(Map<String, String> params)
        throws BusinessException {
        // 查询字典类型用以封装类型树
        return baseDao.queryEntitys(" from  Dictionary d  where d.dictionaryType.typeCode='"
            + params.get("code") + "'");
    }
}
