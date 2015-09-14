package com.xx.system.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.xx.system.user.entity.User;

/**
 * 组织用户实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "T_ORG_USER")
@BatchSize(size = 50)
public class OrgUser implements java.io.Serializable {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 6577547554649980010L;
    
    /**
     * @Fields pkOrgUserId : 主键
     */
    private int pkOrgUserId;
    
    /**
     * @Fields organization : 组织对象
     */
    private Organization organization;
    
    /**
     * @Fields user : 用户对象
     */
    private User user;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
    /**
     * <p>
     * Title: OrgUser()
     * </p>
     * <p>
     * Description: 默认构造方法
     * </p>
     */
    public OrgUser() {
    }
    
    /**
     * <p>
     * Title:OrgUser(int pkOrgUserId, Organization organization, User user, int isDelete)
     * </p>
     * <p>
     * Description: 带参数的构造方法
     * </p>
     * 
     * @param pkOrgUserId
     * @param organization
     * @param user
     * @param isDelete
     */
    public OrgUser(int pkOrgUserId, Organization organization, User user,
        int isDelete) {
        this.pkOrgUserId = pkOrgUserId;
        this.organization = organization;
        this.user = user;
        this.isDelete = isDelete;
    }
    
    /**
     * @Title getPkOrgUserId
     * @author wanglc
     * @Description: 主键
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_ORG_USER_ID", nullable = false)
    public int getPkOrgUserId() {
        return this.pkOrgUserId;
    }
    
    public void setPkOrgUserId(int pkOrgUserId) {
        this.pkOrgUserId = pkOrgUserId;
    }
    
    /**
     * @Title getOrganization
     * @author wanglc
     * @Description: 组织对象
     * @date 2013-12-6
     * @return 组织对象
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ORG_ID", nullable = false)
    public Organization getOrganization() {
        return this.organization;
    }
    
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    
    /**
     * @Title getUser
     * @author wanglc
     * @Description: 用户对象
     * @date 2013-12-6
     * @return 用户对象
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USER_ID", nullable = false)
    public User getUser() {
        return this.user;
    }
    
    public int getIsDelete() {
        return isDelete;
    }
    
    /**
     * @Title setIsDelete
     * @author wanglc
     * @Description: 删除标志：0未删除 1已删除
     * @date 2013-12-6
     * @param isDelete
     */
    @Column(name = "ISDELETE", nullable = false)
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}