package com.xx.system.role.entity;

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
import com.xx.system.user.entity.Group;
import com.xx.system.user.entity.User;

/**
 * 角色成员范围实体定义
 * 
 * @version V1.20,2013-11-25 下午2:27:50
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */

@Entity
@Table(name = "T_SCOPE_MEMBER")
public class ScopeMember implements java.io.Serializable {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields id : 主键id
     */
    private int id;
    
    /**
     * @Fields roleMemberId : 权限范围id
     */
    private RoleMemberScope roleMemberScope;
    
    /**
     * @Fields orgId :组织
     */
    private  Organization org;
    
    /**
     * @Fields orgId :用户
     */
    private  User user;
    
    /**
     * @Fields orgId :群组
     */
    private  Group group;
    
   
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * 组织
     * 
     * @Title getOrg
     * @author dong.he
     * @date 2014-8-22
     * @return 组织id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID", nullable = true)
    public Organization getOrg() {
        return org;
    }
    
    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * 所属组织
     * 
     * @Title getRoleMemberScope
     * @author dong.he
     * @date 2014-8-22
     * @return 组织id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_MEMBER_ID", nullable = true)
    public RoleMemberScope getRoleMemberScope() {
        return roleMemberScope;
    }

    public void setRoleMemberScope(RoleMemberScope roleMemberScope) {
        this.roleMemberScope = roleMemberScope;
    }

    /**
     * 用户
     * 
     * @Title getOrg
     * @author 
     * @date 2015-3-31
     * @return 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	 /**
     * 组织
     * 
     * @Title getOrg
     * @author dong.he
     * @date 2014-8-22
     * @return 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", nullable = true)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}

