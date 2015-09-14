package com.xx.system.common.sso;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * 中油邮箱登陆相关
 * 
 * @version V1.20,2013-12-6 下午2:41:35
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public class LoginYu {
    /** @Fields env : env */
    public Hashtable env = null;
    
    /** @Fields ctx : ctx */
    public LdapContext ctx = null;
    
    /** @Fields connCtls : connCtls */
    public Control[] connCtls = null;
    
    /**
     * <p>
     * Title: LoginYu
     * </p>
     * <p>
     * Description: 中油登录
     * </p>
     * 
     * @param ldapurl
     */
    public LoginYu(String ldapurl) {
        env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, ldapurl);
        connCtls = new Control[] {new FastBindConnectionControl()};
        try {
            ctx = new InitialLdapContext(env, connCtls);
        }
        catch (NamingException e) {
            
        }
    }
    
    /**
     * Authenticate
     * 
     * @Title Authenticate
     * @author wanglc
     * @date 2013-12-6
     * @param username
     * @param password
     * @return
     */
    public boolean Authenticate(String username, String password) {
        try {
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, username);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            ctx.reconnect(connCtls);
            return true;
        }
        catch (AuthenticationException e) {
            return false;
        }
        catch (NamingException e) {
            return false;
        }
    }
    
    /**
     * finito
     * 
     * @Title finito
     * @author wanglc
     * @date 2013-12-6
     */
    public void finito() {
        try {
            ctx.close();
        }
        catch (NamingException e) {
        }
    }
    
    /**
     * connectPtr
     * 
     * @Title connectPtr
     * @author wanglc
     * @date 2013-12-6
     * @param ldapurl
     * @param username
     * @param pw
     * @return
     */
    public boolean connectPtr(String ldapurl, String username, String pw) {
        // String ldapurl = "ldap://10.33.17.1:389";
        boolean IsAuthenticated = true;
        LoginYu ctx = new LoginYu(ldapurl);
        // IsAuthenticated = ctx.Authenticate("ptr\\luanchao-ds", "luanchao");
        IsAuthenticated = ctx.Authenticate("ptr\\" + username, pw);
        ctx.finito();
        return IsAuthenticated;
    }
    
    /**
     * TEST
     * 
     * @Title main
     * @author wanglc
     * @date 2013-12-6
     * @param tt
     */
    public static void main(String[] tt) {
        String ldapurl = "ldap://10.33.17.1:389";
        boolean IsAuthenticated = false;
        LoginYu ctx = new LoginYu(ldapurl);
        IsAuthenticated = ctx.Authenticate("ptr\\liyan5-ds", "liyan5-ds");
        // IsAuthenticated = ctx.Authenticate(username, pw);
        ctx.finito();
        // return IsAuthenticated;
    }
}
