package com.xx.system.common.util;

import java.util.UUID;

/**
 * 生成UUID类
 * 
 * @version V1.20,2013-12-6 下午2:48:38
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class UUIDUtil {
    /**
     * 获取不重复随机数
     * 
     * @Title getRandom
     * @author ndy
     * @date 2014年3月24日
     * @param name 具体业务名称，如Dictionary等
     */
    public static String getUUID(String name) {
        UUID uuid = UUID.randomUUID();
        return name + "-" + uuid;
    }
}