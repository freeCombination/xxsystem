package com.xx.system.log.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xx.system.dict.entity.Dictionary;
import com.xx.system.user.entity.User;

/**
 * 日志实体类定义
 * 
 * @version V1.20,2013-11-25 下午3:16:36
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "T_LOG")
public class Log implements java.io.Serializable {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = -7031443788479281406L;
    
    /**
     * @Fields pkLogId : 日志主键
     */
    private Long pkLogId;
    
    /**
     * @Fields user : 操作人
     */
    private User user;
    
    /**
     * @Fields opDate : 操作日期
     */
    private Date opDate;
    
    /**
     * @Fields opContent : 操作内容
     */
    private String opContent;
    
    /**
     * @Fields ipUrl : IP地址和URL信息
     */
    private String ipUrl;
    
    /**
     * @Fields type : 日志类型：系统日志、错误日志等
     */
    private Dictionary type;
    
    /** @Fields logTypeUUID : 字典类型字典数据UUID */
    private String logTypeUUID;
    
    /**
     * @Title getPkLogId
     * @author wanglc
     * @Description: 主键
     * @date 2013-12-6
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_LOG_ID", nullable = false)
    public Long getPkLogId() {
        return this.pkLogId;
    }
    
    public void setPkLogId(Long pkLogId) {
        this.pkLogId = pkLogId;
    }
    
    /**
     * @Title getUser
     * @author wanglc
     * @Description: 外键：操作人
     * @date 2013-12-6
     * @return
     */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * @Title getOpDate
     * @author wanglc
     * @Description: 操作日期
     * @date 2013-12-6
     * @return
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OP_DATE", nullable = false)
    public Date getOpDate() {
        return this.opDate;
    }
    
    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
    
    /**
     * @Title getOpContent
     * @author wanglc
     * @Description: 操作内容
     * @date 2013-12-6
     * @return
     */
    @Column(name = "OP_CONTENT", length = 4000)
    public String getOpContent() {
        return this.opContent;
    }
    
    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }
    
    /**
     * @Title getIpUrl
     * @author wanglc
     * @Description: IP地址和URL信息
     * @date 2013-12-6
     * @return
     */
    @Column(name = "IP_URL", length = 50)
    public String getIpUrl() {
        return this.ipUrl;
    }
    
    public void setIpUrl(String ipUrl) {
        this.ipUrl = ipUrl;
    }
    
    /**
     * @Title getType
     * @author wanglc
     * @Description: 外键 日志类型
     * @date 2013-12-6
     * @return
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOG_TYPE")
    public Dictionary getType() {
        return type;
    }
    
    public void setType(Dictionary type) {
        this.type = type;
    }
    
    /**
     * @return logTypeUUID
     */
    @Column(name = "FK_LOG_TYPE_UUID")
    public String getLogTypeUUID() {
        return logTypeUUID;
    }
    
    /**
     * @param logTypeUUID 要设置的 logTypeUUID
     */
    public void setLogTypeUUID(String logTypeUUID) {
        this.logTypeUUID = logTypeUUID;
    }
    
    @Override
    public String toString() {
        return "LogBean [id=" + pkLogId + ", user=" + user + ", ip=" + ipUrl
            + ", type=" + type + ", content=" + opContent + ", time="
            + opContent + "]";
    }
    
}