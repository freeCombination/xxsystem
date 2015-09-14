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

import com.xx.system.user.entity.Group;
import com.xx.system.user.entity.User;

/**
 * 角色成员范围实体定义
 * 
 * @author wanglc
 * @version V1.20,2013-11-25 下午2:27:50
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */

@Entity
@Table(name = "T_ROLE_MEMBER_SCOPE")
public class RoleMemberScope implements java.io.Serializable {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields id : 角色成员范围id
     */
    private int id;
    
    /**
     * @Fields roleId : 角色id
     */
    private Role role;
    
    /**
     * @Fields userId : 用户id
     */
    private User user;
    
    /**
     * @Fields groupId : 群组id
     */
    private Group group;
    
    
    /**
     * 主键
     * 
     * @Title getId
     * @author liukang-wb
     * @date 2014年9月17日
     * @return
     */
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
     * 角色
     * 
     * @Title getRole
     * @author liukang-wb
     * @date 2014年9月16日
     * @return
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    /**
     * 用户
     * 
     * @Title getUser
     * @author liukang-wb
     * @date 2014年9月16日
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
     * 群组
     * 
     * @Title getGroup
     * @author liukang-wb
     * @date 2014年9月16日
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
