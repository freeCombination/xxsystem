package com.xx.system.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.user.action.LoginAction;

/**
 * 系统拦截器，拦截所有的方法
 * 
 * @author ndy
 * @version V1.20,2014年2月13日 上午10:38:57
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class MethodInterceptor extends AbstractInterceptor {
    
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = -2163314983372693453L;
    
    @Autowired(required = true)
    @Qualifier("loginAction")
    LoginAction loginAction;
    
    /**
     * <p>
     * Title intercept
     * </p>
     * <p>
     * Author ndy
     * </p>
     * <p>
     * Description
     * </p>
     * 
     * @param arg0
     * @throws Exception
     * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
     */
    @Override
    public String intercept(ActionInvocation arg0)
        throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ssotoken = request.getParameter("SSOTOKEN");
        HttpSession session = request.getSession();
		if (session.getAttribute("currentUserName") == null && StringUtils.isNotBlank(ssotoken) ) {
			loginAction.setSSOTOKEN(ssotoken);
			loginAction.login();
		}
        if (session.getAttribute("currentUserName") == null) {
            String requestType = request.getHeader("X-Requested-With");
            if(StringUtils.isNotBlank(requestType) && requestType.equalsIgnoreCase("XMLHttpRequest")){
                HttpServletResponse response = ServletActionContext.getResponse();
                JsonUtil.outJson("{'sessionstatus':'true'}");
                response.setHeader("sessionstatus", "timeout");
                return null;
            }
            else {
                String url = RequestUtil.getProjectURL(request);
                String str =
                    "<script type=\"text/javascript\">top.window.location.href='"
                        + url + "';</script>";// 页面接收
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setContentType("text/html; charset=utf-8");
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                response.getWriter().write(str);
                response.getWriter().flush();
                response.getWriter().close();
                return null;
            }
        }
        else {
            return arg0.invoke();
        }
    }
}
