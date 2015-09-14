package com.xx.system.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.constant.Constant;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.dict.service.IDictService;
import com.xx.system.log.entity.Log;
import com.xx.system.log.service.ILogService;
import com.xx.system.org.entity.Organization;
import com.xx.system.resource.action.ResourceAction;
import com.xx.system.resource.vo.ResMenuVo;
import com.xx.system.resource.vo.ResourceVo;
import com.xx.system.user.entity.User;
import com.xx.system.user.service.IUserService;

/**
 * 登录Action 系统登录相关方法
 * 
 * @version V1.20,2013-11-25 上午11:29:42
 * @see [相关类/方法]
 * @since V1.20
 * 
 */
@Service("loginAction")
public class LoginAction extends BaseAction {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = -2048758015985728056L;
    
    /**
     * @Fields logger : 日志
     */
    static Logger logger = Logger.getLogger(ResourceAction.class.getName());
    
    /**
     * @Fields userResource : 用户资源对象
     */
    List<Map<String, Object>> userResource =
        new ArrayList<Map<String, Object>>();
    
    /**
     * @Fields userService : 用户服务
     */
    @Autowired(required = true)
    @Qualifier("userService")
    private IUserService userService;
    
    /**
     * @Fields username : 用户名
     */
    private String username;
    
    /**
     * @Fields password : 密码
     */
    private String password;
    
    /**
     * @Fields url : 跳转地址
     */
    private String url;
    
    /**
     * @Fields user :用户对象
     */
    private User user;
    
    /**
     * @Fields SSOTOKEN : SSOTOKEN
     */
    private String SSOTOKEN;
    
    /**
     * 菜单
     */
    private List<ResMenuVo> menuVos;
    
    /**
     * @Fields roleResourceService : 字典服务
     */
    @Autowired(required = true)
    @Qualifier("dictService")
    public IDictService dictService;
    
    /**
     * @Fields logService : 日志服务
     */
    @Autowired(required = true)
    @Qualifier("logService")
    public ILogService logService;
    
    /**
     * 用户登录
     * 
     * @Title login
     * @date 2013-11-25
     * @return null
     */
    @SuppressWarnings("unchecked")
    public String login() {
        try {
        	String flag = "false";
        	if( StringUtils.isNotBlank(SSOTOKEN) ){
        		
        	}
        	Map<String, String> param =  RequestUtil.getParameterMap(getRequest());
        	if(StringUtils.isBlank(username)){
        		JsonUtil.outJson("{success:false,msg:'0'}");
                return null;
        	}
            User user = userService.getUserByUsername(username);
            if (user == null) {
                // 0表示用户不存在
                JsonUtil.outJson("{success:false,msg:'0'}");
                return null;
            }
            if (user.getEnable() == Constant.DISABLE) {
                // 用户已被禁用
                JsonUtil.outJson("{success:false,msg:'1'}");
                return null;
            }
            Map<String, String> loginMap=null;
            if( StringUtils.isNotBlank(SSOTOKEN) && "true".equals(flag)){
            	
            }else{
            	loginMap = userService.userLogin(param, user);
                flag = loginMap.get("flag");
            }
            
            if ("true".equals(flag)) {
                
                // 得到该用户的资源及部门权限
                Map<String, Object> params = userService.getResAndOrg(user);
                HttpSession session = getSession();
                session.setAttribute(Constant.CURRENT_USER, user);
                session.setAttribute("currentUserName", user.getRealname());
                getRequest().setAttribute("currentUserName", user.getRealname());
                // 资源放入session
                session.setAttribute("userPermission",
                    (List<ResourceVo>)params.get("userPermission"));
                // 组织权限放入session
                setOrgsInSession((Set<Organization>)params.get("orgPermission"),  "orgPermission");
                
                session.setAttribute("menuList",  (List<ResMenuVo>)params.get("menuList"));
            }
            else {
                // 登录失败
                String errorMsg = loginMap!=null? loginMap.get("msg"):"";
                addActionMessage(errorMsg);
                // 获取用户资源失败
                JsonUtil.outJson("{success:false,msg:'" + errorMsg + "'}");
                return null;
            }
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'3'}");
            excepHandle(e, user, " 登录失败，发生异常：");
            return null;
        }
        JsonUtil.outJson("{success:true}");
        this.excepAndLogHandle(LoginAction.class, "系统登录", null, true);
        return null;
    }
    
	/**
     * 获取菜单
     * 
     * @Title getMenu
     * @author dong.he
     * @date 2014年9月29日
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getMenu() {
        menuVos = (List<ResMenuVo>)getSession().getAttribute("menuList");
        return "success";
    }
    
    /**
     * 登录异常处理方法(仅限于登录使用)
     * 
     * @Title excepHandle
     * @author liukang-wb
     * @date 2014年9月18日
     * @param ex
     * @param user
     * @param msg
     */
    private void excepHandle(Exception ex, User user, String msg) {
        logger = Logger.getLogger(LoginAction.class.getName());
        Log log = new Log();
        log.setOpDate(new Date());
        log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
        logger.error(user.getRealname() + msg + ex);
        log.setOpContent(user.getRealname() + msg + ex);
        log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
        log.setUser(user);
        try {
            logService.addLog(log);
        }
        catch (Exception e) {
            this.excepAndLogHandle(LoginAction.class, "登录日志添加", e, false);
        }
    }
    
    /**
     * 设置session
     * 
     * @Title setOrgsInSession
     * @author wanglc
     * @date 2013-11-25
     * @param orgs 组织
     * @param key 在session 中的KEY值
     */
    private void setOrgsInSession(Set<Organization> orgs, String key) {
        if (orgs == null || orgs.size() == 0) {
            setSession(key, null);
            return;
        }
        StringBuffer sb = new StringBuffer();
        for (Organization organization : orgs) {
            sb.append(",");
            sb.append(organization.getOrgId());
        }
        setSession(key, sb.substring(1).toString());
    }
    
    /**
     * 用户注销
     * 
     * @Title logout
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String logout() {
        try {
            Log log = new Log();
            log.setOpDate(new Date());
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent(getCurrentUser().getUsername() + ",成功注销系统");
            log.setUser(getCurrentUser());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(0));
            
            super.getSession().removeAttribute(Constant.CURRENT_USER);
            super.getSession().removeAttribute("currentUserName");
            super.getSession().removeAttribute("userPermission");
            super.getSession().removeAttribute("orgPermission");
            super.getSession().removeAttribute("menuList");
            logService.addLog(log);
            JsonUtil.outJson("{success:true,msg:''}");
        }
        catch (Exception e) {
            // this.excepAndLogHandle(LoginAction.class, "注销错误", e, false);
            excepHandle(e, user, "注销失败，发生异常");
            JsonUtil.outJson("{success:false,msg:''}");
            return null;
        }
        return null;
    }
    
    /**
     * 页面跳转
     * 
     * @Title toIndex
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String toIndex() {
        return SUCCESS;
    }
    
    /**
     * userService set方法
     * 
     * @Title setUserService
     * @author wanglc
     * @date 2013-12-6
     * @param userService
     */
    
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    /**
     * @return logger
     */
    public static Logger getLogger() {
        return logger;
    }
    
    /**
     * @param logger 要设置的 logger
     */
    public static void setLogger(Logger logger) {
        LoginAction.logger = logger;
    }
    
    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @param username 要设置的 username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * @param password 要设置的 password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * @param url 要设置的 url
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * @return user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * @param user 要设置的 user
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * @return sSOTOKEN
     */
    public String getSSOTOKEN() {
        return SSOTOKEN;
    }
    
    /**
     * @param sSOTOKEN 要设置的 sSOTOKEN
     */
    public void setSSOTOKEN(String sSOTOKEN) {
        SSOTOKEN = sSOTOKEN;
    }
    
    /**
     * @return userService
     */
    public IUserService getUserService() {
        return userService;
    }
    
    /**
     * @return userResource
     */
    public List<Map<String, Object>> getUserResource() {
        return userResource;
    }
    
    /**
     * userResource set方法
     * 
     * @Title setUserResource
     * @author wanglc
     * @date 2013-12-6
     * @param userResource
     */
    public void setUserResource(List<Map<String, Object>> userResource) {
        this.userResource = userResource;
    }
    
    /**
     * @return dictService
     */
    public IDictService getDictService() {
        return dictService;
    }
    
    /**
     * @param dictService 要设置的 dictService
     */
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
    
    /**
     * @return logService
     */
    public ILogService getLogService() {
        return logService;
    }
    
    /**
     * @param logService 要设置的 logService
     */
    public void setLogService(ILogService logService) {
        this.logService = logService;
    }
    
    public List<ResMenuVo> getMenuVos() {
        return menuVos;
    }
    
    public void setMenuVos(List<ResMenuVo> menuVos) {
        this.menuVos = menuVos;
    }
}
