package com.xx.system.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 单位格式化工具类
 * 
 * @version V1.20,2013-12-6 下午4:41:41
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class FormatUnitsUtil {
    
    /**
     * formatUnits
     * 
     * @Title formatUnits
     * @author wanglc
     * @date 2013-12-6
     * @param baseNumber 要转换的值
     * @param unitDivisors 单位数组
     * @param unitLabels 单位量词
     * @param singleFractional true时 保留小数点后两位，保证整数部分大于1。比如：1.5小时; false时，比如：1小时30分
     * @return
     */
    public static String formatUnits(long baseNumber, int[] unitDivisors,
        String[] unitLabels, boolean singleFractional) {
        int unitDivisor;
        String unitLabel;
        BigDecimal unit = null;
        
        if (baseNumber == 0) {
            return "0 " + unitLabels[unitLabels.length - 1];
        }
        
        // 以小数方式显示
        if (singleFractional) {
            unit = new BigDecimal(baseNumber);
            unitLabel =
                unitLabels.length >= unitDivisors.length ? unitLabels[unitDivisors.length - 1]
                    : "";
            for (int i = 0; i < unitDivisors.length; i++) {
                if (baseNumber >= unitDivisors[i]) {
                    unit =
                        new BigDecimal((double)baseNumber
                            / (double)unitDivisors[i]).setScale(2,
                            BigDecimal.ROUND_HALF_UP);
                    unitLabel =
                        unitLabels.length >= i ? " " + unitLabels[i] : "";
                    break;
                }
            }
            return removeTailZero(unit) + unitLabel;
        }
        else {
            List<String> formattedStrings = new ArrayList<String>();
            long remainder = baseNumber;
            
            for (int i = 0; i < unitDivisors.length; i++) {
                unitDivisor = unitDivisors[i];
                unitLabel = unitLabels.length > i ? " " + unitLabels[i] : "";
                
                unit = new BigDecimal(remainder / unitDivisor);
                if (i < unitDivisors.length - 1) {
                    unit = new BigDecimal(Math.floor(unit.doubleValue()));
                }
                else {
                    unit = unit.setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                if (unit.doubleValue() > 0) {
                    remainder = remainder % unitDivisor;
                    formattedStrings.add(removeTailZero(unit) + unitLabel);
                }
            }
            String formattedStr = "";
            for (String s : formattedStrings) {
                formattedStr += s + " ";
            }
            return formattedStr;
        }
    }
    
    /**
     * 去小数点后多余的0
     * 
     * @Title removeTailZero
     * @author wanglc
     * @date 2013-12-6
     * @param b
     * @return
     */
    public static String removeTailZero(BigDecimal b) {
        String s = b.toString();
        if (s.indexOf('.') <= 0)
            return s;
        int i, len = s.length();
        for (i = 0; i < len; i++)
            if (s.charAt(len - 1 - i) != '0')
                break;
        if (s.charAt(len - i - 1) == '.')
            return s.substring(0, len - i - 1);
        return s.substring(0, len - i);
    }
    
    /**
     * 格式化文件大小
     * 
     * @Title formatBytes
     * @author wanglc
     * @date 2013-12-6
     * @param baseNumber 文件大小 单位为byte
     * @return
     */
    public static String formatBytes(long baseNumber) {
        int[] sizeUnits = {1073741824, 1048576, 1024, 1};
        String[] sizeUnitLabels = {"GB", "MB", "KB", "B"};
        return formatUnits(baseNumber, sizeUnits, sizeUnitLabels, true);
    }
    
    /**
     * 格式化时间
     * 
     * @Title formatTime
     * @author wanglc
     * @date 2013-12-6
     * @param baseNumber 时间 单位为byte
     * @return
     */
    public static String formatTime(long baseNumber) {
        int[] sizeUnits = {86400, 3600, 60, 1};
        String[] sizeUnitLabels = {"天", "小时", "分钟", "秒"};
        return formatUnits(baseNumber, sizeUnits, sizeUnitLabels, false);
    }
    
}
