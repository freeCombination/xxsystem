package com.xx.system.common.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

/**
 * MD5工具类
 * 
 * @version V1.20,2013-12-6 下午4:51:34
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings("unused")
public abstract class MD5Util {
    private static final Logger log = Logger.getLogger(MD5Util.class);
    
    /** @Fields hexDigits : 16进制数 */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
        "6", "7", "a", "b", "c", "d", "e", "f", "8", "9"};
    
    /**
     * 转换字节数组为16进制字串
     * 
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
    
    /**
     * byteToHexString
     * 
     * @Title byteToHexString
     * @author wanglc
     * @Description:
     * @date 2013-12-6
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    
    /**
     * 加密
     * 
     * @Title encode
     * @author wanglc
     * @Description:
     * @date 2013-12-6
     * @param originString
     * @return
     */
    /*
     * public static String encode(String originString) { String resultString = null; try {
     * MessageDigest md = MessageDigest.getInstance("MD5"); originString += "07"; resultString =
     * byteArrayToHexString(md.digest(originString.getBytes())); } catch (Exception ex) {
     * ex.printStackTrace(); } return resultString; }
     */
    
    /**
     * MD5加密
     * 
     * @Title encode
     * @author wanglc
     * @Description:
     * @date 2014-2-13
     * @param plan
     * @return
     */
    public static String encode(String plan) {
        String ret;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plan.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            ret = buf.toString();
        }
        catch (Exception e) {
            log.error(" ExcelUtil文件中【导出excel】方法发生异常：" + e);
            ret = "错误" + e.getMessage();
        }
        return ret;
    }
    
    public static void main(String[] args) {
        
        System.out.println(MD5Util.encode("123"));
    }
}
