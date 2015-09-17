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

import com.xx.system.dict.entity.Dictionary;

/**
 * Organization实体定义
 * 
 * @version V1.20,2013-11-25 下午3:33:52
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "T_ORGANIZATION")
@BatchSize(size = 50)
public class Organization implements java.io.Serializable {
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields orgId : 组织ID
     */
    private int orgId;
    
    /**
     * @Fields organization : 上级组织
     */
    private Organization organization;
    
    /**
     * @Fields orgName : 组织名称
     */
    private String orgName;
    
    /**
     * @Fields orgCode : 组织编码
     */
    private String orgCode;
    
    /**
     * @Fields orgType : 组织类型(字典数据) 0：公司；1：部门；2：小组；3：装置
     */
    private Dictionary orgType;
    
    /**
     * @Fields disOrder : 组织排序
     */
    private int disOrder;
    
    /**
     * @Fields enable : 是否可用：0 是 1 否
     */
    private int enable;
    
    /**
     * @Fields status : 删除标志：0未删除 1已删除
     */
    private int status;
    
    /**
     * @Fields orgUsers : 组织用户
     */
    private Set<OrgUser> orgUsers = new HashSet<OrgUser>(0);
    
    /**
     * @Fields organizations : 上级组织
     */
    private Set<Organization> organizations = new HashSet<Organization>(0);
    
    /**
     * <p>
     * Title: Organization()
     * </p>
     * <p>
     * Description: 默认构造方法
     * </p>
     */
    public Organization() {
    }
    
    /**
     * <p>
     * Title: Organization(int orgId)
     * </p>
     * <p>
     * Description: 带参数的构造方法
     * </p>
     * 
     * @param orgId
     */
    public Organization(int orgId) {
        this.orgId = orgId;
    }
    
    /**
     * <p>
     * Title: Organization(int orgId, String orgName, String orgCode, int orgType, int disOrder, int
     * enable, int status)
     * </p>
     * <p>
     * Description: 带参数的构造方法
     * </p>
     * 
     * @param orgId
     * @param orgName
     * @param orgCode
     * @param orgType
     * @param disOrder
     * @param enable
     * @param status
     */
    public Organization(int orgId, String orgName, String orgCode,
        Dictionary orgType, int disOrder, int enable, int status) {
        this.orgId = orgId;
        this.orgName = orgName;
        this.orgCode = orgCode;
        this.orgType = orgType;
        this.disOrder = disOrder;
        this.enable = enable;
        this.status = status;
    }
    
    /**
     * <p>
     * Title: Organization(int orgId, Organization organization, String orgName, String orgCode, int
     * orgType, int disOrder, int enable, int status, Set<OrgUser> orgUsers)
     * </p>
     * <p>
     * Description: 带参数的构造方法
     * </p>
     * 
     * @param orgId
     * @param organization
     * @param orgName
     * @param orgCode
     * @param orgType
     * @param disOrder
     * @param enable
     * @param status
     * @param orgUsers
     */
    public Organization(int orgId, Organization organization, String orgName,
        String orgCode, Dictionary orgType, int disOrder, int enable,
        int status, Set<OrgUser> orgUsers) {
        this.orgId = orgId;
        this.organization = organization;
        this.orgName = orgName;
        this.orgCode = orgCode;
        this.orgType = orgType;
        this.disOrder = disOrder;
        this.enable = enable;
        this.status = status;
        this.orgUsers = orgUsers;
    }
    
    /**
     * @Title getOrgId
     * @Description: 主键
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORG_ID", nullable = false)
    public int getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
    
    /**
     * @Title getOrganization
     * @Description: 上级组织
     * @date 2013-12-6
     * @return 上级组织
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    public Organization getOrganization() {
        return this.organization;
    }
    
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    
    /**
     * @Title getOrgName
     * @Description: 组织名称
     * @date 2013-12-6
     * @return 组织名称
     */
    @Column(name = "ORG_NAME", nullable = false, length = 100)
    public String getOrgName() {
        return this.orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    /**
     * @Title getOrgCode
     * @Description: 组织编码
     * @date 2013-12-6
     * @return 组织编码
     */
    @Column(name = "ORG_CODE", nullable = true, length = 50)
    public String getOrgCode() {
        return this.orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    /**
     * @Title getOrgType
     * @Description: 组织类型
     * @date 2013-12-6
     * @return 组织类型
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ORGTYPE")
    public Dictionary getOrgType() {
        return this.orgType;
    }
    
    public void setOrgType(Dictionary orgType) {
        this.orgType = orgType;
    }
    
    /**
     * @Title getDisOrder
     * @Description: 排序
     * @date 2013-12-6
     * @return 排序
     */
    @Column(name = "DIS_ORDER", length = 50)
    public int getDisOrder() {
        return this.disOrder;
    }
    
    public void setDisOrder(int disOrder) {
        this.disOrder = disOrder;
    }
    
    /**
     * @Title getEnable
     * @Description: 标志位：0可用 1不可用
     * @date 2013-12-6
     * @return 标志位：0可用 1不可用
     */
    @Column(name = "ENABLE", nullable = false, precision = 22, scale = 0)
    public int getEnable() {
        return this.enable;
    }
    
    public void setEnable(int enable) {
        this.enable = enable;
    }
    
    /**
     * @Title getStatus
     * @Description: 标志位：0未删除 1删除
     * @date 2013-12-6
     * @return 标志位：0未删除 1删除
     */
    @Column(name = "STATUS", nullable = false, precision = 22, scale = 0)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * @Title getOrgUsers
     * @Description: 组织用户关系
     * @date 2013-12-6
     * @return 组织用户关系
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    @BatchSize(size = 50)
    public Set<OrgUser> getOrgUsers() {
        return orgUsers;
    }
    
    public void setOrgUsers(Set<OrgUser> orgUsers) {
        this.orgUsers = orgUsers;
    }
    
    /**
     * @Title getOrganizations
     * @Description: 上级组织
     * @date 2013-12-6
     * @return
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    public Set<Organization> getOrganizations() {
        return this.organizations;
    }
    
    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }
}