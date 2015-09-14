package com.dqgb.sshframe.bpm.vo;

/**
 * 
 * 托管Vo
 * 
 * @author zhxh
 * @version V1.20,2014-1-6 上午9:44:54
 * @since V1.20
 * @depricated
 */
public class ActivitiDelegateVo
{
    /** 主键 */
    private Integer id;
    
    /** 被委托人登录名 */
    private String delegateUserId;
    
    /** 委托开始时间 */
    private String delegateStartTime;
    
    /** 委托结束时间 */
    private String delegateEndTime;
    
    /**
     * 
     * <p>
     * Title:构造器
     * </p>
     * <p>
     * Description: 默认构造器
     * </p>
     */
    public ActivitiDelegateVo()
    {
    }
    
    /**
     * 
     * <p>
     * Title:构造器
     * </p>
     * <p>
     * Description:带有参数构造器
     * </p>
     * 
     * @param id
     * @param delegateUserId
     * @param delegateStartTime
     * @param delegateEndTime
     * @param delegateStatus
     * @param ownerId
     * @param ownerName
     * @param delegateUserName
     */
    public ActivitiDelegateVo(Integer id, String delegateUserId,
        String delegateStartTime, String delegateEndTime, int delegateStatus,
        String ownerId, String ownerName, String delegateUserName)
    {
        super();
        this.id = id;
        this.delegateUserId = delegateUserId;
        this.delegateStartTime = delegateStartTime;
        this.delegateEndTime = delegateEndTime;
        this.delegateStatus = delegateStatus;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.delegateUserName = delegateUserName;
    }
    
    /**
     * 
     * @Title getId
     * @author zhxh
     * @Description:获取主键
     * @date 2014-1-6
     * @return Integer
     */
    public Integer getId()
    {
        return id;
    }
    
    /**
     * 
     * @Title setId
     * @author zhxh
     * @Description:设置主键
     * @date 2014-1-6
     * @param id
     */
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    /**
     * 
     * @Title getDelegateUserId
     * @author zhxh
     * @Description: 获取被委托人登录名
     * @date 2014-1-6
     * @return String
     */
    public String getDelegateUserId()
    {
        return delegateUserId;
    }
    
    /**
     * 
     * @Title setDelegateUserId
     * @author zhxh
     * @Description: 设置被委托人登录名
     * @date 2014-1-6
     * @param delegateUserId
     */
    public void setDelegateUserId(String delegateUserId)
    {
        this.delegateUserId = delegateUserId;
    }
    
    /**
     * 
     * @Title getDelegateStartTime
     * @author zhxh
     * @Description: 获取托管时间
     * @date 2014-1-6
     * @return String
     */
    public String getDelegateStartTime()
    {
        return delegateStartTime;
    }
    
    /**
     * 
     * @Title setDelegateStartTime
     * @author zhxh
     * @Description:设置托管开始时间
     * @date 2014-1-6
     * @param delegateStartTime
     */
    public void setDelegateStartTime(String delegateStartTime)
    {
        this.delegateStartTime = delegateStartTime;
    }
    
    /**
     * 
     * @Title getDelegateEndTime
     * @author zhxh
     * @Description: 获取托管结束时间
     * @date 2014-1-6
     * @return String
     */
    public String getDelegateEndTime()
    {
        return delegateEndTime;
    }
    
    /**
     * 
     * @Title setDelegateEndTime
     * @author zhxh
     * @Description: 设置托管结束时间
     * @date 2014-1-6
     * @param delegateEndTime
     */
    public void setDelegateEndTime(String delegateEndTime)
    {
        this.delegateEndTime = delegateEndTime;
    }
    
    /**
     * 
     * @Title getDelegateStatus
     * @author zhxh
     * @Description: 委托状态
     * @date 2014-1-6
     * @return int
     */
    public int getDelegateStatus()
    {
        return delegateStatus;
    }
    
    /**
     * 
     * @Title setDelegateStatus
     * @author zhxh
     * @Description:设置委托状态
     * @date 2014-1-6
     * @param delegateStatus
     */
    public void setDelegateStatus(int delegateStatus)
    {
        this.delegateStatus = delegateStatus;
    }
    
    /**
     * 
     * @Title getOwnerId
     * @author zhxh
     * @Description: 获取委托人登录名
     * @date 2014-1-6
     * @return String
     */
    public String getOwnerId()
    {
        return ownerId;
    }
    
    /**
     * 
     * @Title setOwnerId
     * @author zhxh
     * @Description:设置委托人登录名
     * @date 2014-1-6
     * @param ownerId
     */
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
    /**
     * 
     * @Title getOwnerName
     * @author zhxh
     * @Description: 获取委托人姓名
     * @date 2014-1-6
     * @return String
     */
    public String getOwnerName()
    {
        return ownerName;
    }
    
    /**
     * 
     * @Title setOwnerName
     * @author zhxh
     * @Description: 设置委托人姓名
     * @date 2014-1-6
     * @param ownerName
     */
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    /**
     * 
     * @Title getDelegateUserName
     * @author zhxh
     * @Description: 获取被委托人姓名
     * @date 2014-1-6
     * @return String
     */
    public String getDelegateUserName()
    {
        return delegateUserName;
    }
    
    /**
     * 
     * @Title setDelegateUserName
     * @author zhxh
     * @Description:设置被委托人姓名
     * @date 2014-1-6
     * @param delegateUserName
     */
    public void setDelegateUserName(String delegateUserName)
    {
        this.delegateUserName = delegateUserName;
    }
    
    /**
     * 委托状态
     */
    private int delegateStatus;
    
    /** 委托人登录名 */
    private String ownerId;
    
    /** 委托人姓名 */
    private String ownerName;
    
    /** 被委托人姓名 */
    private String delegateUserName;
    
}
