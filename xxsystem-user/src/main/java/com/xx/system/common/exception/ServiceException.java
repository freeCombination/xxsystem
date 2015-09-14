package com.xx.system.common.exception;

/**
 * 业务处理层异常处理类
 * 
 * @version V1.20,2013-12-6 下午2:46:32
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class ServiceException extends BaseException {
    
    /** @Fields serialVersionUID : serialVersionUID */
    private static final long serialVersionUID = -9006104640618533135L;
    
    /**
     * <p>
     * Title: ServiceException
     * </p>
     * <p>
     * Description: ServiceException
     * </p>
     * 
     * @param messageKey
     */
    public ServiceException(String messageKey) {
        super(messageKey);
    }
    
    /**
     * <p>
     * Title: ServiceException
     * </p>
     * <p>
     * Description: ServiceException
     * </p>
     * 
     * @param t
     */
    public ServiceException(Throwable t) {
        if (t instanceof BaseException) {
            super.setMessageKey(((BaseException)t).getMessageKey());
        }
        super.initCause(t);
    }
    
    /**
     * <p>
     * Title: ServiceException
     * </p>
     * <p>
     * Description: ServiceException
     * </p>
     * 
     * @param messageKey
     * @param t
     */
    public ServiceException(String messageKey, Throwable t) {
        super.setMessageKey(messageKey);
        super.initCause(t);
    }
    
    /**
     * <p>
     * Title: ServiceException
     * </p>
     * <p>
     * Description: 默认构造方法
     * </p>
     */
    public ServiceException() {
        
    }
    
    /**
     * getOrignalException
     * 
     * @Title getOrignalException
     * @author wanglc
     * @date 2013-12-6
     * @return
     */
    public Throwable getOrignalException() {
        Throwable t = this.getCause();
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }
    
    /**
     * getOrignalMessageKey
     * 
     * @Title getOrignalMessageKey
     * @author wanglc
     * @date 2013-12-6
     * @return
     */
    public String getOrignalMessageKey() {
        return this.getOrignalException().getClass().getSimpleName();
    }
}
