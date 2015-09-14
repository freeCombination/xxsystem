package com.xx.system.common.exception;

/**
 * 异常处理基类
 * 
 * @version V1.20,2013-12-6 下午3:34:11
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class BaseException extends RuntimeException {
    
    /** @Fields serialVersionUID : serialVersionUID */
    private static final long serialVersionUID = -3653870580234213024L;
    
    /** @Fields messageKey : messageKey */
    protected String messageKey;
    
    /**
     * <p>
     * Title: BaseException
     * </p>
     * <p>
     * Description: BaseException
     * </p>
     */
    public BaseException() {
        super();
    }
    
    /**
     * <p>
     * Title: BaseException
     * </p>
     * <p>
     * Description: BaseException
     * </p>
     * 
     * @param s
     * @param throwable
     */
    public BaseException(String s, Throwable throwable) {
        super(s, throwable);
    }
    
    /**
     * <p>
     * Title: BaseException
     * </p>
     * <p>
     * Description: BaseException
     * </p>
     * 
     * @param throwable
     */
    public BaseException(Throwable throwable) {
        super(throwable);
    }
    
    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description: BaseException
     * </p>
     * 
     * @param messageKey
     */
    public BaseException(String messageKey) {
        super();
        this.messageKey = messageKey;
    }
    
    /**
     * 取得异常信息key
     * 
     * @Title getMessageKey
     * @author wanglc
     * @date 2013-12-6
     * @return
     */
    public String getMessageKey() {
        return messageKey;
    }
    
    /**
     * 设置异常信息key
     * 
     * @Title setMessageKey
     * @author wanglc
     * @date 2013-12-6
     * @param messageKey
     */
    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
    
}
