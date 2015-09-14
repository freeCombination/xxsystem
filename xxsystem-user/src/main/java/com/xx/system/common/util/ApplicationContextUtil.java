package com.xx.system.common.util;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;

import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContext工具类
 * 
 * @version V1.20,2013-12-6 下午2:48:38
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings({"rawtypes", "static-access", "unchecked"})
public class ApplicationContextUtil implements ApplicationContextAware {
    
    /** @Fields context : context */
    private static ApplicationContext context;// 声明一个静态变量保存
    
    /**
     * <p>
     * Title setApplicationContext
     * </p>
     * <p>
     * Author wanglc
     * </p>
     * <p>
     * Description 设置ApplicationContext
     * </p>
     * 
     * @param contex
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext contex)
        
        throws BeansException {
        this.context = contex;
    }
    
    /**
     * 获取ApplicationContext
     * 
     * @Title getContext
     * @author wanglc
     * @date 2013-12-6
     * @return
     */
    public static ApplicationContext getContext() {
        return context;
    }
    
    /**
     * 获取Bean
     * 
     * @Title getBean
     * @author wanglc
     * @date 2013-12-6
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }
    
    /**
     * 获取Bean
     * 
     * @Title getBean
     * @author wanglc
     * @date 2013-12-6
     * @param name
     * @return
     */
    public static Object getBean(Class name) {
        return context.getBean(name);
    }
}