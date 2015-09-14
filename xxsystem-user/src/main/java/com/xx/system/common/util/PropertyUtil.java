package com.xx.system.common.util;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 属性文件工具类
 * 
 * @version V1.20,2013-12-6 下午4:55:26
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class PropertyUtil {
    private static Logger logger = Logger.getLogger(PropertyUtil.class);
    
    private static Properties props = new Properties();
    
    static {
        try {
            /*
             * InputStream in = new BufferedInputStream (new FileInputStream(
             * "D:/sunbridgeworkspace/sshFrame/web/src/main/resources/application.properties"));
             * props.load(in);
             */
            props.load(PropertyUtil.class.getResourceAsStream("/application.properties"));
        }
        catch (IOException e) {
            logger.error("获取资源文件错误。" + e.getMessage());
        }
    }
    
    /**
     * 根据属性名去属性值
     * 
     * @Title get
     * @author wanglc
     * @Description:
     * @date 2013-12-6
     * @param propertyName
     * @return
     */
    public static String get(String propertyName) {
        return props.getProperty(propertyName);
    }
    
    /*
     * public static void main(String[] args) { System.out.println(PropertyUtil.get("jdbc.url")); }
     */
}
