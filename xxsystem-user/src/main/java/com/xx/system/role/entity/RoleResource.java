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

import com.xx.system.resource.entity.Resource;

/**
 * 角色资源实体定义
 * 
 * @author wanglc
 * @version V1.20,2013-11-25 下午2:29:48
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "T_ROLE_RESOURCE")
public class RoleResource implements java.io.Serializable
{
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields roleResourceId : 角色资源ID
     */
    private int roleResourceId;
    
    /**
     * @Fields role : 角色对象
     */
    private Role role;
    
    /**
     * @Fields resource : 资源对象
     */
    private Resource resource;
    
    /**
     * <p>
     * Title:RoleResource()
     * </p>
     * <p>
     * Description: 无参构造方法
     * </p>
     */
    public RoleResource()
    {
    }
    
    /**
     * <p>
     * Title:RoleResource(int roleResourceId, Role role, Resource resource)
     * </p>
     * <p>
     * Description:带参构造方法
     * </p>
     * 
     * @param roleResourceId 角色资源ID
     * @param role 角色对象
     * @param resource 资源对象
     */
    public RoleResource(int roleResourceId, Role role, Resource resource)
    {
        this.roleResourceId = roleResourceId;
        this.role = role;
        this.resource = resource;
    }
    
    /**
     * 主键
     * 
     * @Title getRoleResourceId
     * @author wanglc
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_RESOURCE_ID", nullable = false)
    public int getRoleResourceId()
    {
        return this.roleResourceId;
    }
    
    public void setRoleResourceId(int roleResourceId)
    {
        this.roleResourceId = roleResourceId;
    }
    
    /**
     * 外键 角色
     * 
     * @Title getRole
     * @author wanglc
     * @date 2013-12-6
     * @return 外键 角色
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public Role getRole()
    {
        return this.role;
    }
    
    public void setRole(Role role)
    {
        this.role = role;
    }
    
    /**
     * 外键 资源
     * 
     * @Title getResource
     * @author wanglc
     * @date 2013-12-6
     * @return 外键 资源
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESOURCE_ID", nullable = false)
    public Resource getResource()
    {
        return this.resource;
    }
    
    public void setResource(Resource resource)
    {
        this.resource = resource;
    }
    
}