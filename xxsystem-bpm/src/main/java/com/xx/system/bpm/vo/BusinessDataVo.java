package com.dqgb.sshframe.bpm.vo;

import java.util.List;

public class BusinessDataVo
{
    /** 业务数据标题 */
    private String businessTitle;
    
    /** 业务描述 */
    private String businessDescription;
    
    /** 业务内容 */
    private String businessContent;
    
    /** 业务基础类型 **/
    private String businessBaseType;
    
    /**
     * 
     * @Title getBusinessBaseType
     * @author zhxh // * @Description: 获取业务基础类型
     * @date 2014-1-6
     * @return String
     */
    public String getBusinessBaseType()
    {
        return businessBaseType;
    }
    
    /**
     * 
     * @Title setBusinessBaseType
     * @author zhxh
     * @Description: 设置String
     * @date 2014-1-6
     * @param businessBaseType
     */
    public void setBusinessBaseType(String businessBaseType)
    {
        this.businessBaseType = businessBaseType;
    }
    
    /** 附件url */
    private String attachUrl;
    
    /** 当前任务的前一些人员的审批意见 */
    private List<String> opinions;
    
    /**
     * 审核状态
     * 
     */
    private int status;
    
    /**
     * 名称
     * 
     */
    private String statusName;
    
    /**
     * 
     * @Title getStatusName
     * @author zhxh
     * @Description:获取状态名称
     * @date 2014-1-6
     * @return String
     */
    public String getStatusName()
    {
        return statusName;
    }
    
    /**
     * 
     * @Title setStatusName
     * @author zhxh
     * @Description: 设置状态名称
     * @date 2014-1-6
     * @param statusName
     */
    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }
    
    /**
     * 
     * @Title getStatus
     * @author zhxh
     * @Description: 获取状态标志
     * @date 2014-1-6
     * @return int
     */
    public int getStatus()
    {
        return status;
    }
    
    /**
     * 
     * @Title setStatus
     * @author zhxh
     * @Description: 设置状态标志
     * @date 2014-1-6
     * @param status
     */
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    /**
     * 
     * @Title getOpinions
     * @author zhxh
     * @Description: 获取意见列表
     * @date 2014-1-6
     * @return List<String>
     */
    public List<String> getOpinions()
    {
        return opinions;
    }
    
    /**
     * 
     * @Title setOpinions
     * @author zhxh
     * @Description: 设置意见列表
     * @date 2014-1-6
     * @param opinions
     */
    public void setOpinions(List<String> opinions)
    {
        this.opinions = opinions;
    }
    
    /**
     * 
     * @Title getAttachUrl
     * @author zhxh
     * @Description:获取附件地址
     * @date 2014-1-6
     * @return String
     */
    public String getAttachUrl()
    {
        return attachUrl;
    }
    
    /**
     * 
     * @Title setAttachUrl
     * @author zhxh
     * @Description: 设置附件地址
     * @date 2014-1-6
     * @param attachUrl
     */
    public void setAttachUrl(String attachUrl)
    {
        this.attachUrl = attachUrl;
    }
    
    /**
     * 
     * @Title getBusinessTitle
     * @author zhxh
     * @Description:获取业务标题
     * @date 2014-1-6
     * @return String
     */
    public String getBusinessTitle()
    {
        return businessTitle;
    }
    
    /**
     * 
     * @Title setBusinessTitle
     * @author zhxh
     * @Description: 设置业务标题
     * @date 2014-1-6
     * @param businessTitle
     */
    public void setBusinessTitle(String businessTitle)
    {
        this.businessTitle = businessTitle;
    }
    
    /**
     * 
     * @Title getBusinessDescription
     * @author zhxh
     * @Description: 获取业务描述
     * @date 2014-1-6
     * @return String
     */
    public String getBusinessDescription()
    {
        return businessDescription;
    }
    
    /**
     * 
     * @Title setBusinessDescription
     * @author zhxh
     * @Description: 设置业务描述
     * @date 2014-1-6
     * @param businessDescription
     */
    public void setBusinessDescription(String businessDescription)
    {
        this.businessDescription = businessDescription;
    }
    
    /**
     * 
     * <p>
     * Title: BusinessDataVo
     * </p>
     * <p>
     * Description:构造器
     * </p>
     * 
     * @param businessTitle
     * @param businessDescription
     * @param businessContent
     */
    public BusinessDataVo(String businessTitle, String businessDescription,
        String businessContent)
    {
        super();
        this.businessTitle = businessTitle;
        this.businessDescription = businessDescription;
        this.businessContent = businessContent;
    }
    
    /**
     * 
     * @Title getBusinessContent
     * @author zhxh
     * @Description: 获取业务内容
     * @date 2014-1-6
     * @return String
     */
    public String getBusinessContent()
    {
        return businessContent;
    }
    
    /**
     * 
     * @Title setBusinessContent
     * @author zhxh
     * @Description:设置业务内容
     * @date 2014-1-6
     * @param businessContent
     */
    public void setBusinessContent(String businessContent)
    {
        this.businessContent = businessContent;
    }
    
    /**
     * 
     * <p>
     * Title: BusinessDataVo
     * </p>
     * <p>
     * Description:默认构造器
     * </p>
     */
    public BusinessDataVo()
    {
    }
}
