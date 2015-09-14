package com.xx.system.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 群组管理实体
 * 
 * @version V1.40,2014年9月15日 上午8:52:00
 * @see [相关类/方法]
 * @since V1.40
 */
@Entity
@Table(name = "T_GROUP")
public class Group implements Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields id : ID
     */
    private Integer id;
    
    /**
     * @Fields id : 群组名称
     */
    private String groupName;
    
    /**
     * @Fields creteDate : 创建时间
     */
    private Date creteDate;
    
    /**
     * @Fields remark : 描述
     */
    private String remark;
    
    /**
     * 主键
     * 
     * @Title getId
     * @author tangh
     * @date 2014年9月15日
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "ID")
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
     * 群组名称
     * 
     * @Title groupName
     * @author tangh
     * @date 2014年9月15日
     * @return 群组名称
     */
    @Column(name = "GROUP_NAME", nullable = false, length = 100)
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
     * 创建时间
     * 
     * @Title creteDate
     * @author tangh
     * @date 2014年9月15日
     * @return 创建时间
     */
    @Column(name = "CREATE_DATE", nullable = false)
    public Date getCreteDate() {
        return creteDate;
    }
    
    /**
     * @param creteDate the creteDate to set
     */
    public void setCreteDate(Date creteDate) {
        this.creteDate = creteDate;
    }
    
    /**
     * 描述
     * 
     * @Title remark
     * @author tangh
     * @date 2014年9月15日
     * @return 描述
     */
    @Column(name = "REMARK", nullable = true, length = 2000)
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
