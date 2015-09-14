package com.xx.system.common.exception;

/**
 * 业务逻辑异常，必须捕获并处理。通常使用在业务逻辑错误，如用户不合法、输入数据有误等等。 此类异常捕获后需要抛给action层，action根据错误类型返回信息提示给用户。
 * 
 * @version V1.20,2013-12-6 下午2:45:01
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class BusinessException extends Exception {
    /** @Fields serialVersionUID : serialVersionUID */
    private static final long serialVersionUID = 5716830600055176984L;
    
    /** @Fields message : message */
    private String message;
    
    /**
     * <p>
     * Title: BusinessException
     * </p>
     * <p>
     * Description: BusinessException
     * </p>
     * 
     * @param message
     */
    public BusinessException(String message) {
        
        this.message = message;
    }
    
    /**
     * <p>
     * Title: BusinessException
     * </p>
     * <p>
     * Description: BusinessException
     * </p>
     * 
     * @param e
     */
    public BusinessException(Exception e) {
        super(e);
    }
    
    /**
     * <p>
     * Title: BusinessException
     * </p>
     * <p>
     * Description: BusinessException
     * </p>
     * 
     * @param message
     * @param cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
    
    /**
     * <p>
     * Title getMessage
     * </p>
     * <p>
     * Author wanglc
     * </p>
     * <p>
     * Description getMessage
     * </p>
     * 
     * @return
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        String stackMsg = super.getMessage();
        return message + "\r\n" + stackMsg;
    }
    
    /**
     * getDefaultMessage
     * 
     * @Title getDefaultMessage
     * @author wanglc
     * @date 2013-12-6
     * @return
     */
    public String getDefaultMessage() {
        return message;
    }
    
}
