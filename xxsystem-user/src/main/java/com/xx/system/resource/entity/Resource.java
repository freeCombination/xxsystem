package com.xx.system.resource.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;

import com.xx.system.dict.entity.Dictionary;
import com.xx.system.role.entity.RoleResource;

/**
 * 资源实体类定义
 * 
 * @version V1.20,2013-11-25 下午2:55:24
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "T_RESOURCE")
@BatchSize(size=50)
public class Resource implements java.io.Serializable {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields resourceId : 资源ID
     */
    private Integer resourceId;
    
    /**
     * @Fields resource : 父类资源
     */
    private Resource resource;
    
    /**
     * @Fields resourceName : 资源名称
     */
    private String resourceName;
    
    /**
     * @Fields code : 资源编码
     */
    private String code;
    
    /**
     * @Fields resourceType : 资源类型
     */
    private Dictionary resourceType;
    
    /**
     * @Fields resourceTypeDictUUID : 资源类型字典数据UUID
     */
    private String resourceTypeDictUUID;
    
    /**
     * @Fields resourceFrom : 资源来源 0：架构自行添加 1：集成平台同步
     */
    private int resourceFrom;
    
    /**
     * @Fields urlpath : 资源URL
     */
    private String urlpath;
    
    /**
     * @Fields remarks : 资源备注
     */
    private String remarks;
    
    /**
     * @Fields disOrder : 资源排序
     */
    private Integer disOrder;
    
    /**
     * @Fields status : 资源状态
     */
    private int status;
    
    /**
     * @Fields createDate : 创建时间
     */
    private Date createDate;
    
    /**
     * @Fields isDeleteAble : 1不允许删除；0可以删除
     */
    private int isDeleteAble;
    
    /**
     * @Fields roleResources : 角色资源
     */
    @BatchSize(size=20)
    private Set<RoleResource> roleResources = new HashSet<RoleResource>(0);
    
    /**
     * @Fields resources : 父类资源
     */
    @BatchSize(size=20)
    private Set<Resource> resources = new HashSet<Resource>(0);
    
    /**
     * <p>
     * Title: Resource()
     * </p>
     * <p>
     * Description: 无参数构造方法
     * </p>
     */
    public Resource() {
    }
    
    /**
     * <p>
     * Title:Resource(int resourceId, String resourceName, String resourceType, int status, Date
     * createDate)
     * </p>
     * <p>
     * Description: 带参数的构造方法
     * </p>
     * 
     * @param resourceId 主键
     * @param resourceName 名称
     * @param resourceType 类型
     * @param status 状态
     * @param createDate 创建日期
     */
    public Resource(int resourceId, String resourceName,
        Dictionary resourceType, int status, Date createDate) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.status = status;
        this.createDate = createDate;
    }
    
    /**
     * <p>
     * Title: Resource(int resourceId, Resource resource, String resourceName, String code, String
     * resourceType, String urlpath, String remarks, String disOrder, int status, Set<RoleResource>
     * roleResources, Set<Resource> resources, Date createDate)
     * </p>
     * <p>
     * Description: 完全构造方法
     * </p>
     * 
     * @param resourceId 主键
     * @param resource 外键 父级
     * @param resourceName 名称
     * @param code 编码
     * @param resourceType 类型
     * @param urlpath URL
     * @param remarks 备注
     * @param disOrder 排序
     * @param status 状态
     * @param roleResources 角色资源关系
     * @param resources 资源
     * @param createDate 创建日期
     */
    public Resource(int resourceId, Resource resource, String resourceName,
        String code, Dictionary resourceType, String urlpath, String remarks,
        Integer disOrder, int status, Set<RoleResource> roleResources,
        Set<Resource> resources, Date createDate, int resourceFrom) {
        this.resourceId = resourceId;
        this.resource = resource;
        this.resourceName = resourceName;
        this.code = code;
        this.resourceType = resourceType;
        this.urlpath = urlpath;
        this.remarks = remarks;
        this.disOrder = disOrder;
        this.status = status;
        this.roleResources = roleResources;
        this.resources = resources;
        this.createDate = createDate;
        this.resourceFrom = resourceFrom;
    }
    
    /**
     * 主键
     * 
     * @Title getResourceId
     * @author wanglc
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESOURCE_ID", nullable = false)
    public Integer getResourceId() {
        return this.resourceId;
    }
    
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
    
    /**
     * 父级
     * 
     * @Title getResource
     * @author wanglc
     * @date 2013-12-6
     * @return 父级
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    public Resource getResource() {
        return this.resource;
    }
    
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    /**
     * 名称
     * 
     * @Title getResourceName
     * @author wanglc
     * @date 2013-12-6
     * @return 名称
     */
    @Column(name = "RESOURCE_NAME", nullable = false, length = 100)
    public String getResourceName() {
        return this.resourceName;
    }
    
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    
    /**
     * 编码
     * 
     * @Title getCode
     * @author wanglc
     * @date 2013-12-6
     * @return 编码
     */
    @Column(name = "CODE", length = 100)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 类型
     * 
     * @Title getResourceType
     * @author wanglc
     * @date 2013-12-6
     * @return 类型
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESOURCE_TYPE")
    public Dictionary getResourceType() {
        return this.resourceType;
    }
    
    public void setResourceType(Dictionary resourceType) {
        this.resourceType = resourceType;
    }
    
    /**
     * URL
     * 
     * @Title getUrlpath
     * @author wanglc
     * @date 2013-12-6
     * @return URL
     */
    @Column(name = "URLPATH", length = 200)
    public String getUrlpath() {
        return this.urlpath;
    }
    
    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath;
    }
    
    /**
     * 备注
     * 
     * @Title getRemarks
     * @author wanglc
     * @date 2013-12-6
     * @return 备注
     */
    @Column(name = "REMARKS", length = 200)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    /**
     * 排序
     * 
     * @Title getDisOrder
     * @author wanglc
     * @date 2013-12-6
     * @return 排序
     */
    @Column(name = "DIS_ORDER")
    public Integer getDisOrder() {
        return this.disOrder;
    }
    
    public void setDisOrder(Integer disOrder) {
        this.disOrder = disOrder;
    }
    
    /**
     * 状态
     * 
     * @Title getStatus
     * @author wanglc
     * @date 2013-12-6
     * @return 状态
     */
    @Column(name = "STATUS", nullable = false, precision = 22, scale = 0)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * 创建日期
     * 
     * @Title getCreateDate
     * @author wanglc
     * @date 2013-12-6
     * @return 创建日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", nullable = false, length = 7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * 角色资源关系
     * 
     * @Title getRoleResources
     * @author wanglc
     * @date 2013-12-6
     * @return 角色资源关系
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
    public Set<RoleResource> getRoleResources() {
        return this.roleResources;
    }
    
    public void setRoleResources(Set<RoleResource> roleResources) {
        this.roleResources = roleResources;
    }
    
    /**
     * 资源Set
     * 
     * @Title getResources
     * @author wanglc
     * @date 2013-12-6
     * @return 资源Set
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
    public Set<Resource> getResources() {
        return this.resources;
    }
    
    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
    
    /**
     * @return resourceFrom
     */
    @Column(name = "RESOURCE_FROM")
    public int getResourceFrom() {
        return resourceFrom;
    }
    
    /**
     * @param resourceFrom 要设置的 resourceFrom
     */
    public void setResourceFrom(int resourceFrom) {
        this.resourceFrom = resourceFrom;
    }
    
    /**
     * @return resourceTypeDictUUID
     */
    @Column(name = "RESOURCE_TYPE_UUID")
    public String getResourceTypeDictUUID() {
        return resourceTypeDictUUID;
    }
    
    /**
     * @param resourceTypeDictUUID 要设置的 resourceTypeDictUUID
     */
    public void setResourceTypeDictUUID(String resourceTypeDictUUID) {
        this.resourceTypeDictUUID = resourceTypeDictUUID;
    }
    
    /**
     * @return isDeleteAble
     */
    public int getIsDeleteAble() {
        return isDeleteAble;
    }
    
    /**
     * @param isDeleteAble 要设置的 isDeleteAble
     */
    public void setIsDeleteAble(int isDeleteAble) {
        this.isDeleteAble = isDeleteAble;
    }
    
}