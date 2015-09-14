package com.xx.system.common.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.xx.system.common.constant.Constant;
import com.xx.system.common.constant.SystemConstant;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.PagerVo;
import com.xx.system.dict.service.IDictService;
import com.xx.system.log.entity.Log;
import com.xx.system.log.service.ILogService;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.user.entity.User;

/**
 * 公用Action
 * 
 * @version V1.20,2013-12-6 下午2:32:47
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class BaseAction extends ActionSupport {
    /** @Fields serialVersionUID : serialVersionUID */
    private static final long serialVersionUID = -4313906072406350263L;
    
    /**
     * @Fields logger : 日志处理工具类
     */
    Logger logger = Logger.getLogger(BaseAction.class.getName());
    
    /** @Fields output : output */
    public Map<String, Object> output = new HashMap<String, Object>();
    
    /** @Fields start : ext分页参数，起始记录位置 */
    protected int start = 0;
    
    /** @Fields limit : ext分页参数，将取记录条数 */
    protected int limit = 10;
    
    /** @Fields pager : 分页VO */
    private PagerVo<?> pager;
    
    /** @Fields pageNum : 当前第几页 */
    private int pageNum = 1;
    
    /** @Fields pageSize : 每页显示多少行 */
    private int pageSize = 99;
    
    /** @Fields menuCode : 菜单编码 */
    protected String menuCode;
    
    /**
     * @Fields dictService : 字典相关处理工具类
     */
    @Autowired
    protected IDictService dictService;
    
    /**
     * @Fields logService : 日志处理工具类
     */
    @Autowired
    private ILogService logService;
    
    /**
     * request请求
     * 
     * @Title getRequest
     * @author wanglc
     * @date 2013-12-5
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }
    
    /**
     * response响应
     * 
     * @Title getResponse
     * @author wanglc
     * @Description:
     * @date 2013-12-5
     * @return HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
    
    /**
     * 获取session
     * 
     * @Title getSession
     * @author wanglc
     * @Description:
     * @date 2013-12-5
     * @return HttpSession
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }
    
    /**
     * 设置session
     * 
     * @Title setSession
     * @author wanglc
     * @Description:
     * @date 2013-12-5
     * @param key session Key
     * @param o session 值
     */
    protected void setSession(String key, Object o) {
        try {
            getSession().setAttribute(key, o);
        }
        catch (Exception e) {
            log4j(getClass(), e);
        }
    }
    
    /**
     * 添加提示消息
     * 
     * @Title addMessage
     * @author wanglc
     * @Description:
     * @date 2013-12-5
     * @param messages 消息
     */
    public void addMessage(String messages) {
        try {
            this.addActionMessage(this.getText(messages));
        }
        catch (Exception e) {
            log4j(getClass(), e);
        }
    }
    
    /**
     * 添加错误提示消息
     * 
     * @Title addError
     * @author wanglc
     * @Description:
     * @date 2013-12-5
     * @param errorMessages 消息
     */
    public void addError(String errorMessages) {
        try {
            this.addActionError(this.getText(errorMessages));
        }
        catch (Exception e) {
            log4j(getClass(), e);
        }
    }
    
    /**
     * 添加输入框错误提示消息
     * 
     * @Title addFieldErrorMessages
     * @author wanglc
     * @date 2013-12-5
     * @param fieldName
     * @param fieldErrorMessages 消息
     */
    public void addFieldErrorMessages(String fieldName,
        String fieldErrorMessages) {
        try {
            this.addFieldError(fieldName, this.getText(fieldErrorMessages));
        }
        catch (Exception e) {
            log4j(getClass(), e);
        }
    }
    
    /**
     * 获取当前用户
     * 
     * @Title getCurrentUser
     * @author wanglc
     * @date 2013-12-5
     * @return User 用户对象
     */
    public User getCurrentUser() {
        return (User)this.getSession()
            .getAttribute(SystemConstant.CURRENT_USER);
    }
    
    /**
     * 获取当前用户部门
     * 
     * @Title getCurrentUser
     * @author hedaojun
     * @date 2013-12-5
     * @return User 用户对象
     */
    public Organization getCurrentOrg() {
    	User user = this.getCurrentUser();
    	Set<OrgUser>  orgSet = user.getOrgUsers();
    	if(orgSet!=null && orgSet.size()>0){
    		return orgSet.iterator().next().getOrganization();
    	}
    	return null;
    }
    
    /**
     * 添加log4j日志
     * 
     * @Title log4j
     * @author wanglc
     * @Description:
     * @date 2013-12-5
     * @param clazz
     * @param message 日志内容
     */
    public void log4j(Class<?> clazz, Object message) {
        Logger.getLogger(clazz).error(message);
    }
    
    /**
     * @return output
     */
    public Map<String, Object> getOutput() {
        return output;
    }
    
    /**
     * @param output 要设置的 output
     */
    public void setOutput(Map<String, Object> output) {
        this.output = output;
    }
    
    /**
     * @return start
     */
    public int getStart() {
        return start;
    }
    
    /**
     * @param start 要设置的 start
     */
    public void setStart(int start) {
        this.start = start;
    }
    
    /**
     * @return limit
     */
    public int getLimit() {
        return limit;
    }
    
    /**
     * @param limit 要设置的 limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    /**
     * @return pager
     */
    public PagerVo<?> getPager() {
        return pager;
    }
    
    /**
     * @param pager 要设置的 pager
     */
    public void setPager(PagerVo<?> pager) {
        this.pager = pager;
    }
    
    /**
     * @return pageNum
     */
    public int getPageNum() {
        return pageNum;
    }
    
    /**
     * @param pageNum 要设置的 pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    
    /**
     * @return pageSize
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * @param pageSize 要设置的 pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * @return menuCode
     */
    public String getMenuCode() {
        return menuCode;
    }
    
    /**
     * @param menuCode 要设置的 menuCode
     */
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    
    /**
     * 异常和日志处理
     * 
     * @Title excepHandle
     * @author dong.he
     * @date 2014年8月25日
     * @param cls 发生异常的类
     * @param msg 发生异常的方法
     * @param ex 异常
     * @param flag true代表操作日志，false代表异常日志
     */
    public void excepAndLogHandle(Class<?> cls, String msg, Exception ex,
        boolean flag) {
        logger = Logger.getLogger(cls.getName());
        Log log = new Log();
        log.setOpDate(new Date());
        log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
        // flag = true正常的日志信息
        if (flag) {
            logger.info(getCurrentUser().getUsername() + " 执行【" + msg
                + "】功能，正常！");
            log.setOpContent(getCurrentUser().getUsername() + " 执行【" + msg
                + "】功能，正常！");
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(0));
        }
        // flag = false 异常日志信息
        else {
            logger.error(getCurrentUser().getUsername() + " 执行【" + msg
                + "】功能，发生异常：" + ex);
            log.setOpContent(getCurrentUser().getUsername() + " 执行【" + msg
                + "】功能，发生异常：" + ex);
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
        }
        log.setUser(getCurrentUser());
        try {
            logService.addLog(log);
        }
        catch (BusinessException e) {
            logger.error(" 执行【异常和日志处理】功能，发生异常：" + e);
        }
    }
}
