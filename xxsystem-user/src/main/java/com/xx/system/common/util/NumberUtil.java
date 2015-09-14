package com.xx.system.common.util;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 数字工具类
 * 
 */
public class NumberUtil
{
    
    private static final Logger LOG = Logger.getLogger(NumberUtil.class);
    
    /**
     * 数字转换为字符串
     * 
     * @param num 数字
     * @return 字符串,如果 num 为空, 返回空字符串
     */
    public static String num2Str(Object num)
    {
        String str = null;
        
        if (num == null)
        {
            str = "";
        }
        else
        {
            str = String.valueOf(num);
        }
        return str;
    }
    
    /**
     * 字符串转换为Integer
     * 
     * @param obj 字符串
     * @return Integer, str为null时返回0
     */
    public static Integer getInteger(Object obj)
    {
        return getInteger(obj, 0);
    }
    
    /**
     * 字符串转换为Integer
     * 
     * @param obj 字符串
     * @param def 默认值
     * @return Integer, 字符串为null时返回def
     */
    public static Integer getInteger(Object obj, int def)
    {
        String str = obj == null ? "" : obj.toString();
        
        Integer i = null;
        
        if (str.trim().length() == 0)
        {
            i = new Integer(def);
        }
        else
        {
            try
            {
                i = Integer.valueOf(str);
            }
            catch (Exception e)
            {
                LOG.error("字符串转换为Integer出错", e);
            }
        }
        
        return i == null ? new Integer(def) : i;
    }
    
    /**
     * 字符串转换为Long
     * 
     * @param obj 字符串
     * @return Long, str为null时返回0
     */
    public static Long getLong(Object obj)
    {
        return getLong(obj, 0);
    }
    
    /**
     * 字符串转换为Long
     * 
     * @param obj 字符串
     * @param def 默认值
     * @return Long, 字符串为null时返回def
     */
    public static Long getLong(Object obj, long def)
    {
        String str = obj == null ? "" : obj.toString();
        
        Long l = null;
        
        if (str.trim().length() == 0)
        {
            l = new Long(def);
        }
        else
        {
            try
            {
                l = Long.valueOf(str);
            }
            catch (Exception e)
            {
                LOG.error("字符串转换为Long出错", e);
            }
        }
        
        return l == null ? new Long(def) : l;
    }
    
    /**
     * 字符串转换为Integer
     * 
     * @param obj 字符串
     * @return Integer, str为null时返回0
     */
    public static int getIntegerValue(Object obj)
    {
        return getIntegerValue(obj, 0);
    }
    
    /**
     * 字符串转换为Integer
     * 
     * @param obj 字符串
     * @param def 默认值
     * @return Integer, 字符串为null时返回def
     */
    public static int getIntegerValue(Object obj, int def)
    {
        return getInteger(obj, def).intValue();
    }
    
    /**
     * 字符串转换为Long
     * 
     * @param obj 字符串
     * @return Long, str为null时返回0
     */
    public static long getLongValue(Object obj)
    {
        return getLongValue(obj, 0);
    }
    
    /**
     * 字符串转换为Long
     * 
     * @param obj 字符串
     * @param def 默认值
     * @return Long, 字符串为null时返回def
     */
    public static long getLongValue(Object obj, long def)
    {
        return getLong(obj, def).longValue();
    }
    
    /**
     * @Title getDouble
     * @author yzg
     * @Description:对象转换为Double
     * @date 2014-8-29
     * @param obj
     * @param def
     * @return
     */
    public static Double getDouble(Object obj, double def)
    {
        String str = obj == null ? "" : obj.toString();
        
        Double l = null;
        
        if (str.trim().length() == 0)
        {
            l = new Double(def);
        }
        else
        {
            try
            {
                l = Double.valueOf(str);
            }
            catch (Exception e)
            {
                LOG.error("对象转换为Double 出错", e);
            }
        }
        
        return l == null ? new Double(def) : l;
    }
    
    /**
     * 字符串转换为Double
     * 
     * @param obj 字符串
     * @return Double, str为null时返回0
     */
    public static Double getDouble(Object obj)
    {
        return getDouble(obj, 0);
    }
    
    /**
     * 字符串转换为Double
     * 
     * @param obj 字符串
     * @param def 字符串
     * @return Long, str为null时返回0
     */
    public static double getDoubleValue(Object obj, double def)
    {
        return getDouble(obj, def).doubleValue();
    }
    
    /**
     * 字符串转换为Double
     * 
     * @param obj 字符串
     * @return Double, str为null时返回0
     */
    public static double getDoubleValue(Object obj)
    {
        return getDouble(obj).doubleValue();
    }
    
    /**
     * 字符串转换为Integer
     * 
     * @param obj 字符串
     * @return Integer, str为null时返回0
     */
    public static int getINT(Object obj)
    {
        int temp = 0;
        temp = obj == null ? 0 : Integer.parseInt(obj.toString().trim());
        return temp;
    }
    
    /**
     * 字符串转换为Float
     * 
     * @param str
     * @return
     */
    public static Float getFloat(String str)
    {
        Float l = null;
        
        if (str.trim().length() == 0)
        {
            l = new Float(0);
        }
        else
        {
            try
            {
                l = Float.valueOf(str);
            }
            catch (Exception e)
            {
                LOG.error("get float by string error", e);
            }
        }
        
        return l == null ? new Float(0) : l;
    }
    
    /**
     * 对象转换为float
     * 
     * @param obj
     * @return
     */
    public static float getFloatValue(Object obj)
    {
        Float num = getFloat(StringUtil.getStr(obj));
        return num.floatValue();
    }
    
    /**
     * 去除小数后无用的0，例如3.00->3
     * 
     * @param num
     * @return
     */
    public static String remove0(String num)
    {
        num = StringUtil.getStr(num);
        if (isDigit(num))
        {
            int index = num.lastIndexOf('.');
            if (index > -1)
            {
                if (index == num.length() - 1)
                {
                    num = num.substring(0, index);
                }
                else
                {
                    String suffix = num.substring(index + 1);
                    for (int i = 0; i < suffix.length(); i++)
                    {
                        if ('0' != suffix.charAt(i))
                        {
                            return num;
                        }
                    }
                    num = num.substring(0, index);
                }
            }
        }
        return num;
    }
    
    /**
     * 判断当前值是否为整数
     * 
     * @param value
     * @return
     */
    public static boolean isInteger(Object value)
    {
        if (value == null)
        {
            return false;
        }
        String mstr = value.toString();
        Pattern pattern = Pattern.compile("^-?\\d+{1}");
        return pattern.matcher(mstr).matches();
    }
    
    /**
     * 判断当前值是否为数字(包括小数)
     * 
     * @param value
     * @return
     */
    public static boolean isDigit(Object value)
    {
        if (value == null)
        {
            return false;
        }
        String mstr = value.toString();
        Pattern pattern = Pattern.compile("^-?[0-9]*.?[0-9]*{1}");
        return pattern.matcher(mstr).matches();
    }
    
}