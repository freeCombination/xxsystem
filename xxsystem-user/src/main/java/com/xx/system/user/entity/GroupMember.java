package com.xx.system.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xx.system.org.entity.Organization;

/**
 * 群组成员实体
 * 
 * @version V1.40,2014年9月15日 上午8:52:00
 * @see [相关类/方法]
 * @since V1.40
 */
@Entity
@Table(name = "T_GROUP_MEMBER")
public class GroupMember implements Serializable {
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields id : ID
     */
    private Integer id;
    
    /**
     * @Fields user : 用户
     */
    private User user;
    
    /**
     * @Fields org : 群组所属组织
     */
    private Organization org;
    
    /**
     * @Fields group : 群组ID
     */
    private Group group;
    
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
     * 成员
     * 
     * @Title getUser
     * @author tangh
     * @date 2014年9月15日
     * @return 成员
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = true)
    public User getUser() {
        return user;
    }
    
    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * 班组所属组织
     * 
     * @Title getOrg
     * @author tangh
     * @date 2014年9月15日
     * @return 班组所属组织
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID", nullable = true)
    public Organization getOrg() {
        return org;
    }
    
    /**
     * @param org the org to set
     */
    public void setOrg(Organization org) {
        this.org = org;
    }
    
    /**
     * 所属群组
     * 
     * @Title getGroup
     * @author tangh
     * @date 2014年9月15日
     * @return 所属群组
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    public Group getGroup() {
        return group;
    }
    
    /**
     * @param group the group to set
     */
    public void setGroup(Group group) {
        this.group = group;
    }
    
}
