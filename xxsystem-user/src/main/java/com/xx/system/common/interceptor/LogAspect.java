package com.xx.system.common.interceptor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.constant.Constant;
import com.xx.system.dict.service.IDictService;
import com.xx.system.log.service.ILogService;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
@Component("logAspect")
public class LogAspect extends BaseAction {
    
    @Autowired
    private ILogService logService;
    
    Logger logger = Logger.getLogger(LogAspect.class.getName());
    
    @Autowired
    private IDictService dictService;
    
    public void doSystemLog(JoinPoint point)
        throws Throwable {
        
        Method method = null;
        String methodName = point.getSignature().getName();
        if (!(methodName.startsWith("set") || methodName.startsWith("get") || methodName.startsWith("query"))
            && point.getArgs().length > 0) {
            Object[] param = point.getArgs();
            Class targetClass = point.getTarget().getClass();
            try {
                method = targetClass.getMethod(methodName, param[0].getClass());
            }
            catch (Exception e) {
                return;
            }
            if (method != null) {
                boolean hasAnnotation = method.isAnnotationPresent(Log.class);
                if (hasAnnotation) {
                    Log annotation = method.getAnnotation(Log.class);
                    try {
                        com.xx.system.log.entity.Log logInfo =
                            new com.xx.system.log.entity.Log();
                        logInfo.setIpUrl(this.getRequest().getRemoteAddr());
                        logInfo.setUser(getCurrentUser()); // user中获取username
                        logInfo.setOpDate(new Date());
                        logInfo.setType(dictService.getDictByTypeCode(Constant.LOGTYPE)
                            .get(0));
                        logInfo.setOpContent(getCurrentUser().getUsername()
                            + "执行【" + annotation.operationName() + "】的"
                            + annotation.operationType() + "操作,影响数据的ID集合为["
                            + getID(param[annotation.position() - 1]) + "]");
                        logger.info(logInfo.getOpContent());
                        this.logService.addLog(logInfo);
                    }
                    catch (Exception ex) {
                        logger.error("LogAspect文件中【doSystemLog】方法发生异常：" + ex);
                    }
                }
            }
        }
    }
    
    /**
     * 通过java反射来从传入的参数object里取出我们需要记录的id,name等属性， 此处我取出的是id
     * 
     * @author lishan
     * @date 2013-4-11
     * @param obj
     * @return
     * @return String
     */
    public String getID(Object obj) {
        String v = "";
        try {
            if (obj instanceof String) {
                return obj.toString();
            }
            String propertyId = "";
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method m : methods) {
                if (m.getAnnotation(javax.persistence.Id.class) != null) {
                    propertyId = m.invoke(obj, new Object[0]).toString();
                    break;
                }
            }
            if (!"".equals(propertyId)) {
                return propertyId;
            }
            String propertyName = "";
            Class fieldClass = obj.getClass();
            Field[] field = fieldClass.getDeclaredFields();
            
            for (Field f : field) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(javax.persistence.Id.class)) {
                    propertyName = f.getName();
                }
            }
            PropertyDescriptor pd = null;
            Method method = null;
            pd = new PropertyDescriptor(propertyName, obj.getClass());
            method = pd.getReadMethod();
            v = String.valueOf(method.invoke(obj));
        }
        catch (Exception e) {
            logger.error("LogAspect文件中【getID】方法发生异常：" + e);
        }
        return v;
    }
    
    /**
     * @return logService
     */
    public ILogService getLogService() {
        return logService;
    }
    
    /**
     * @param logService 要设置的 logService
     */
    public void setLogService(ILogService logService) {
        this.logService = logService;
    }
    
    /**
     * @return dictService
     */
    public IDictService getDictService() {
        return dictService;
    }
    
    /**
     * @param dictService 要设置的 dictService
     */
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
    
}
