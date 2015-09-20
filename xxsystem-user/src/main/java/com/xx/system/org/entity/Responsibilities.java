package com.xx.system.org.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.xx.system.user.entity.User;

/**
 * 岗位实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_RESPONSIBILITIES")
@BatchSize(size = 50)
public class Responsibilities implements java.io.Serializable {
    
	private static final long serialVersionUID = -4897082516701301833L;

	/**
     * @Fields pkRespId : 主键
     */
    private int pkRespId;
    
    /**
     * 岗位编号
     */
    private String number;
    
    /**
     * 岗位名称
     */
    private String name;
    
    /**
     * @Fields organization : 所属部门
     */
    private Organization organization;
    
    /**
     * 岗位级别
     */
    private String rank;
    
    /**
     * @Fields user : 用户对象
     */
    private Set<User> users = new HashSet<User>(0);
    
    /**
     * @Fields enable : 是否可用：0 是 1 否
     */
    private int enable;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
    /**
     * @Title getPkOrgUserId
     * @Description: 主键
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_RESP_ID", nullable = false)
    public int getPkRespId() {
		return pkRespId;
	}

	public void setPkRespId(int pkRespId) {
		this.pkRespId = pkRespId;
	}
    
	@Column(name = "NUMBER", nullable = true, length = 50)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    /**
     * @Title getOrganization
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
    
    @Column(name = "ISDELETE", nullable = false, precision = 22, scale = 0)
    public int getIsDelete() {
        return isDelete;
    }
    
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
    
    @Column(name = "RANK", nullable = false, length = 100)
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Column(name = "ENABLE", nullable = false, precision = 22, scale = 0)
	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "responsibilities")
    @BatchSize(size = 50)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}