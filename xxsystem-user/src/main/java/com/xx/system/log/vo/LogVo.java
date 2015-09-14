package com.xx.system.log.vo;

import com.xx.system.common.util.DateUtil;
import com.xx.system.log.entity.Log;

/**
 * Log 类的Vo类
 * 
 * @version V1.20,2013-11-25 下午3:21:15
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class LogVo {
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields pkLogId : 日志主键
     */
    private Long pkLogId;
    
    /**
     * @Fields userId : 操作人ID
     */
    private int userId;
    
    /**
     * @Fields userName : 操作人名称
     */
    private String userName;
    
    /**
     * @Fields opDate : 操作日期
     */
    private String opDate;
    
    /**
     * @Fields opContent : 操作内容
     */
    private String opContent;
    
    /**
     * @Fields ipUrl : IP地址和日志URL
     */
    private String ipUrl;
    
    /**
     * @Fields logTypeId : 日志类型值
     */
    private int logTypeId;
    
    /**
     * @Fields logTypeText : 日志类型名称
     */
    private String logTypeText;
    
    /**
     * <p>
     * Title: LogVo(Log log)
     * </p>
     * <p>
     * Description: 构造方法
     * </p>
     * 
     * @param log
     */
    public LogVo(Log log) {
        this.ipUrl = log.getIpUrl();
        this.logTypeId = log.getType().getPkDictionaryId();
        this.logTypeText = log.getType().getDictionaryName();
        this.opContent = log.getOpContent();
        this.opDate = DateUtil.dateToString(log.getOpDate(), "yyyy-MM-dd : HH:mm:ss");
        this.pkLogId = log.getPkLogId();
        this.userId = log.getUser().getUserId();
        this.userName = log.getUser().getUsername();
    }
    
    public Long getPkLogId() {
        return pkLogId;
    }
    
    public void setPkLogId(Long pkLogId) {
        this.pkLogId = pkLogId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getOpDate() {
        return opDate;
    }
    
    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }
    
    public String getOpContent() {
        return opContent;
    }
    
    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }
    
    public String getIpUrl() {
        return ipUrl;
    }
    
    public void setIpUrl(String ipUrl) {
        this.ipUrl = ipUrl;
    }
    
    public int getLogTypeId() {
        return logTypeId;
    }
    
    public void setLogTypeId(int logTypeId) {
        this.logTypeId = logTypeId;
    }
    
    public String getLogTypeText() {
        return logTypeText;
    }
    
    public void setLogTypeText(String logTypeText) {
        this.logTypeText = logTypeText;
    }
}
