package com.xx.system.user.vo;

/**
 * 群组管理VO
 * 
 * @version V1.40,2014年9月15日 上午8:52:00
 * @see [相关类/方法]
 * @since V1.40
 */
public class GroupVo {
    /**
     * @Fields id : ID
     */
    private Integer id;
    
    /**
     * @Fields id : 角色成员id
     */
    private Integer roleMemberId;
    
    /**
     * @Fields groupName : 群组名称
     */
    private String groupName;
    
    /**
     * @Fields groupName : 创建时间
     */
    private String creteDate;
    
    /**
     * @Fields remark : 描述
     */
    private String remark;
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }
    
    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    /**
     * @return the creteDate
     */
    public String getCreteDate() {
        return creteDate;
    }
    
    /**
     * @param creteDate the creteDate to set
     */
    public void setCreteDate(String creteDate) {
        this.creteDate = creteDate;
    }
    
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return the roleMemberId
     */
    public Integer getRoleMemberId() {
        return roleMemberId;
    }
    
    /**
     * @param roleMemberId the roleMemberId to set
     */
    public void setRoleMemberId(Integer roleMemberId) {
        this.roleMemberId = roleMemberId;
    }
    
}
