package com.xx.system.common.util;

import javax.naming.ldap.Control;

/**
 * FastBindConnectionControl
 * 
 * @author wanglc
 * @version V1.20,2013-12-6 下午4:57:26
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
class FastBindConnectionControl implements Control {
    /** @Fields serialVersionUID : serialVersionUID */
    private static final long serialVersionUID = -3865293681938961671L;
    
    /**
     * <p>
     * Title getEncodedValue
     * </p>
     * <p>
     * Author wanglc
     * </p>
     * <p>
     * Description getEncodedValue
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
     * Description getID
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
     * Description isCritical
     * </p>
     * 
     * @return
     * @see javax.naming.ldap.Control#isCritical()
     */
    public boolean isCritical() {
        return true;
    }
}
