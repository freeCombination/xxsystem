package com.xx.system.common.util;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * DomainValidation
 * 
 * @version V1.20,2013-12-6 下午3:56:36
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DomainValidation {
    /** @Fields env :env */
    public Hashtable env = null;
    
    /** @Fields ctx : ctx */
    public LdapContext ctx = null;
    
    /** @Fields connCtls : connCtls */
    public Control[] connCtls = null;
    
    /**
     * <p>
     * Title: DomainValidation
     * </p>
     * <p>
     * Description: DomainValidation
     * </p>
     * 
     * @param ldapurl
     */
    public DomainValidation(String ldapurl) {
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
    
}
