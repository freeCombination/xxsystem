package com.xx.system.log.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.ResponseVo;
import com.xx.system.common.vo.TreeNodeVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.log.service.ILogService;
import com.xx.system.log.vo.LogVo;

/**
 * 日志Action
 * 
 * @version V1.20,2013-11-25 下午3:14:17
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class LogAction extends BaseAction {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = -2579916878201800203L;
    
    /**
     * @Fields logService : 日志服务
     */
    @Resource
    private ILogService logService;
    
    /**
     * 日志首页
     * 
     * @Title toLogIndex
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String toLogIndex() {
        return SUCCESS;
    }
    
    /**
     * 获取日志列表
     * 
     * @Title getLog
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String getLog() {
        Map<String, String> params = RequestUtil.getParameterMap(getRequest());
        ListVo<LogVo> vo;
        ResponseVo rv = new ResponseVo();
        try {
            vo = logService.getLogList(params);
            rv.setList(vo.getList());
            rv.setTotalSize(vo.getTotalSize());
            rv.setSuccess(true);
            rv.setMsg("后台错误");
            JsonUtil.outJson(rv);
        }
        catch (BusinessException e) {
            this.excepAndLogHandle(LogAction.class, "获取日志列表", e, false);
        }
        return null;
    }
    
    /**
     * 删除日志
     * 
     * @Title delLogInfo
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @return String
     */
    public String delLogInfo() {
        Map<String, String> params = RequestUtil.getParameterMap(getRequest());
        String delResult;
        try {
            delResult = logService.delLog(params);
            JsonUtil.outJson(delResult);
        }
        catch (BusinessException e) {
            JsonUtil.outJson("{success:'false',msg:'删除日志失败'}");
            this.excepAndLogHandle(LogAction.class, "删除日志", e, false);
        }
        
        return null;
    }
    
    /**
     * 查询日志类型用以类型树
     * 
     * @Title getLogType
     * @author ls
     * @Description:
     * @date 2014-2-24
     * @return String
     */
    public String getLogType() {
        // 获取页面参数
        Map<String, String> params = RequestUtil.getParameterMap(getRequest());
        // 执行查询
        List<Dictionary> delResult =
            dictService.getDictByTypeCode(params.get("code"));
        // 封装类型树
        List<TreeNodeVo> result = new ArrayList<TreeNodeVo>();
        for (Dictionary dictionary : delResult) {
            TreeNodeVo tn = new TreeNodeVo();
            tn.setText(dictionary.getDictionaryName());
            tn.setDescription(dictionary.getDictionaryName());
            tn.setLeaf(true);
            tn.setNodeId(dictionary.getPkDictionaryId() + "");
            result.add(tn);
        }
        JsonUtil.outJsonArray(result);
        
        return null;
    }
    
    public void setLogService(ILogService logService) {
        this.logService = logService;
    }
    
}
