package com.xx.system.common.util;

import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

/**
 * Double工具类
 * 
 * @version V1.20,2013-12-6 下午4:46:22
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public abstract class DoubleUtil extends StringUtils {
    
    /**
     * getNumberFormat
     * 
     * @Title getNumberFormat
     * @author wanglc
     * @date 2013-12-6
     * @return
     */
    public static NumberFormat getNumberFormat() {
        
        NumberFormat nbf = NumberFormat.getInstance();
        nbf.setMinimumFractionDigits(2);
        return nbf;
        
    }
    
}
