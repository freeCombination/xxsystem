package com.xx.system.common.sso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * FastBindConnectionControl
 * 
 * @version V1.20,2013-12-6 下午2:35:52
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
class FastBindConnectionControl implements Control {
    /** @Fields serialVersionUID : serialVersionUID */
    private static final long serialVersionUID = 7015102277059733721L;
    
    /**
     * <p>
     * Title getEncodedValue
     * </p>
     * <p>
     * Author wanglc
     * </p>
     * <p>
     * Description
     * </p>
     * 
     * @return
     * @see javax.naming.ldap.Control#getEncodedValue()
     */
    public byte[] getEncodedValue() {
        return null;
    }
    
    /**
     * <p>
     * Title getID
     * </p>
     * <p>
     * Author wanglc
     * </p>
     * <p>
     * Description
     * </p>
     * 
     * @return
     * @see javax.naming.ldap.Control#getID()
     */
    public String getID() {
        return "1.2.840.113556.1.4.1781";
    }
    
    /**
     * <p>
     * Title isCritical
     * </p>
     * <p>
     * Author wanglc
     * </p>
     * <p>
     * Description
     * </p>
     * 
     * @return
     * @see javax.naming.ldap.Control#isCritical()
     */
    public boolean isCritical() {
        return true;
    }
}

/**
 * 中油邮箱登陆相关
 * 
 * @author wanglc
 * @version V1.20,2013-12-6 下午2:37:22
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public class LDAPFastBind {
    
    /** @Fields env : env */
    public Hashtable env = null;
    
    /** @Fields ctx : ctx */
    public LdapContext ctx = null;
    
    /** @Fields connCtls :connCtls */
    public Control[] connCtls = null;
    
    /**
     * <p>
     * Title: LDAPFastBind
     * </p>
     * <p>
     * Description: LDAPFastBind
     * </p>
     * 
     * @param ldapurl
     */
    public LDAPFastBind(String ldapurl) {
        env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, ldapurl);
        
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        
        String keystore =
            "J:/Program Files/Java/jdk1.6.0_17/jre/lib/security/cacerts";
        System.setProperty("javax.net.ssl.trustStore", keystore);
        
        connCtls = new Control[] {new FastBindConnectionControl()};
        
        try {
            ctx = new InitialLdapContext(env, connCtls);
            
        }
        catch (NamingException e) {
        }
    }
    
    /**
     * LDAPFastBind
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
     * LDAPFastBind
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
     * printUserAccountControl
     * 
     * @Title printUserAccountControl
     * @author wanglc
     * @date 2013-12-6
     */
    public void printUserAccountControl() {
        try {
            
            // Create the search controls
            SearchControls searchCtls = new SearchControls();
            
            // Specify the search scope
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            
            // specify the LDAP search filter
            // String searchFilter = "(&(objectClass=user)(CN=test))";
            // String searchFilter = "(&(objectClass=group))";
            String searchFilter = "(&(objectClass=user)(CN=peter lee))";
            
            // Specify the Base for the search
            String searchBase = "DC=joeyta,DC=local";
            
            // initialize counter to total the group members
            int totalResults = 0;
            
            // Specify the attributes to return
            String returnedAtts[] = {"givenName", "mail"};
            searchCtls.setReturningAttributes(returnedAtts);
            
            // Search for objects using the filter
            NamingEnumeration answer =
                ctx.search(searchBase, searchFilter, searchCtls);
            
            // Loop through the search results
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult)answer.next();
                
                // Print out the groups
                
                Attributes attrs = sr.getAttributes();
                if (attrs != null) {
                    
                    try {
                        for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
                            Attribute attr = (Attribute)ae.next();
                            for (NamingEnumeration e = attr.getAll(); e.hasMore(); totalResults++) {
                            }
                            
                        }
                        
                    }
                    catch (NamingException e) {
                        System.err.println("Problem listing membership: " + e);
                    }
                    
                }
            }
            
        }
        
        catch (NamingException e) {
            System.err.println("Problem searching directory: " + e);
        }
        
    }
    
    /**
     * adminChangePassword
     * 
     * @Title adminChangePassword
     * @author wanglc
     * @date 2013-12-6
     * @param sUserName
     * @param sNewPassword
     * @return
     */
    public boolean adminChangePassword(String sUserName, String sNewPassword) {
        try {
            // set password is a ldap modfy operation
            ModificationItem[] mods = new ModificationItem[1];
            
            // Replace the "unicdodePwd" attribute with a new value
            // Password must be both Unicode and a quoted string
            String newQuotedPassword = "\"" + sNewPassword + "\"";
            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
            
            mods[0] =
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("unicodePwd", newUnicodePassword));
            
            // Perform the update
            ctx.modifyAttributes(sUserName, mods);
            
            return true;
        }
        catch (NamingException e) {
        }
        catch (UnsupportedEncodingException e) {
        }
        return false;
    }
    
    /**
     * userChangePassword
     * 
     * @Title userChangePassword
     * @author wanglc
     * @date 2013-12-6
     * @param sUserName
     * @param sOldPassword
     * @param sNewPassword
     * @return
     */
    public boolean userChangePassword(String sUserName, String sOldPassword,
        String sNewPassword) {
        try {
            // StartTlsResponse tls =
            // (StartTlsResponse)ctx.extendedOperation(new StartTlsRequest());
            // tls.negotiate();
            
            // change password is a single ldap modify operation
            // that deletes the old password and adds the new password
            ModificationItem[] mods = new ModificationItem[2];
            
            // Firstly delete the "unicdodePwd" attribute, using the old
            // password
            // Then add the new password,Passwords must be both Unicode and a
            // quoted string
            String oldQuotedPassword = "\"" + sOldPassword + "\"";
            byte[] oldUnicodePassword = oldQuotedPassword.getBytes("UTF-16LE");
            String newQuotedPassword = "\"" + sNewPassword + "\"";
            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
            
            mods[0] =
                new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
                    new BasicAttribute("unicodePwd", oldUnicodePassword));
            mods[1] =
                new ModificationItem(DirContext.ADD_ATTRIBUTE,
                    new BasicAttribute("unicodePwd", newUnicodePassword));
            
            // Perform the update
            ctx.modifyAttributes(sUserName, mods);
            
            // tls.close();
            return true;
            
        }
        catch (NamingException e) {
            System.err.println("Problem changing password: " + e);
        }
        catch (UnsupportedEncodingException e) {
            System.err.println("Problem encoding password: " + e);
        }
        catch (Exception e) {
            System.err.println("Problem: " + e);
        }
        return false;
    }
    
    /**
     * createNewUser
     * 
     * @Title createNewUser
     * @author wanglc
     * @date 2013-12-6
     * @param sGroupName
     * @param sUserName
     * @return
     */
    public boolean createNewUser(String sGroupName, String sUserName) {
        try {
            // Create attributes to be associated with the new user
            Attributes attrs = new BasicAttributes(true);
            
            // These are the mandatory attributes for a user object
            // Note that Win2K3 will automagically create a random
            // samAccountName if it is not present. (Win2K does not)
            attrs.put("objectClass", "user");
            attrs.put("sAMAccountName", "AlanT");
            attrs.put("cn", "Alan Tang");
            
            // These are some optional (but useful) attributes
            attrs.put("givenName", "Alan");
            attrs.put("sn", "Tang");
            attrs.put("displayName", "Alan Tang");
            attrs.put("description", "Engineer");
            attrs.put("userPrincipalName", "alan-AT-joeyta.local");
            attrs.put("mail", "alang-AT-mail.joeyta-DOT-local");
            attrs.put("telephoneNumber", "123 456 789");
            
            // some useful constants from lmaccess.h
            int UF_ACCOUNTDISABLE = 0x0002;
            int UF_PASSWD_NOTREQD = 0x0020;
            int UF_NORMAL_ACCOUNT = 0x0200;
            int UF_PASSWORD_EXPIRED = 0x800000;
            
            attrs.put("userAccountControl",
                Integer.toString(UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD
                    + UF_PASSWORD_EXPIRED + UF_ACCOUNTDISABLE));
            
            ModificationItem[] mods = new ModificationItem[2];
            String newQuotedPassword = "\"P-AT-ssw0rd\"";
            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
            
            mods[0] =
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("unicodePwd", newUnicodePassword));
            mods[1] =
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("userAccountControl",
                        Integer.toString(UF_NORMAL_ACCOUNT
                            + UF_PASSWORD_EXPIRED)));
            
            ctx.modifyAttributes(sUserName, mods);
            
            try {
                ModificationItem member[] = new ModificationItem[1];
                member[0] =
                    new ModificationItem(DirContext.ADD_ATTRIBUTE,
                        new BasicAttribute("member", sUserName));
                
                ctx.modifyAttributes(sGroupName, member);
                
            }
            catch (NamingException e) {
                System.err.println("Problem adding user to group: " + e);
            }
            // Could have put tls.close() prior to the group modification
            // but it seems to screw up the connection or context
            
            return true;
            
        }
        catch (NamingException e) {
            System.err.println("Problem creating object: " + e);
        }
        
        catch (IOException e) {
            System.err.println("Problem creating object: " + e);
        }
        return false;
    }
    
    /**
     * addUserToGroup
     * 
     * @Title addUserToGroup
     * @author wanglc
     * @date 2013-12-6
     * @param ctx
     * @param userDN
     * @param groupDN
     * @return
     */
    public boolean addUserToGroup(LdapContext ctx, String userDN, String groupDN) {
        try {
            ModificationItem[] mods = new ModificationItem[1];
            mods[0] =
                new ModificationItem(DirContext.ADD_ATTRIBUTE,
                    new BasicAttribute("member", userDN));
            ctx.modifyAttributes(groupDN, mods);
            return true;
        }
        catch (NamingException ne) {
            System.err.println("Problem add user to group: " + ne);
        }
        return false;
    }
}
