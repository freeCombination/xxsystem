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

import com.xx.system.dict.entity.Dictionary;

/**
 * 角色实体定义
 * 
 * @version V1.20,2013-11-25 下午2:27:50
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "T_ROLE")
public class Role implements java.io.Serializable
{
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields roleId : 角色ID
     */
    private int roleId;
    
    /**
     * @Fields roleName : 角色名称
     */
    private String roleName;
    
    /**
     * @Fields roleCode : 角色编码
     */
    private String roleCode;
    
    /** 
     * @Fields roleType : 角色类别 
     */ 
    private Dictionary roleType;
    
    /** @Fields roleTypeUUID : 角色类型字典数据UUID*/ 
    private String roleTypeUUID;
    
    /**
     * @Fields description : 描述
     */
    private String description;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1架构已删除2集成平台已删除
     */
    private int isDelete;
    
    /**
     * @Fields isDeleteAble : 1不允许删除；0可以删除
     */ 
     private int isDeleteAble; 
    
    /**
     * default constructor
     * <p>
     * Title:Role()
     * </p>
     * <p>
     * Description:无参构造方法
     * </p>
     */
    public Role()
    {
    }
    
    /**
     * minimal constructor
     * <p>
     * Title:Role(int roleId, String roleName, String roleCode)
     * </p>
     * <p>
     * Description:带参数的构造方法
     * </p>
     * 
     * @param roleId 主键
     * @param roleName 名称
     * @param roleCode 编码
     */
    public Role(int roleId, String roleName, String roleCode)
    {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleCode = roleCode;
    }
    
    /**
     * full constructor
     * <p>
     * Title:Role(int roleId, String roleName, String roleCode, String description)
     * </p>
     * <p>
     * Description:带参数的构造方法
     * </p>
     * 
     * @param roleId 主键
     * @param roleName 名称
     * @param roleCode 编码
     * @param description 描述
     */
    public Role(int roleId, String roleName, String roleCode, String description)
    {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.description = description;
    }
    
    /**
     * 主键
     * 
     * @Title getRoleId
     * @author wanglc
     * @date 2013-12-6
     * @return int
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID", nullable = false)
    public int getRoleId()
    {
        return this.roleId;
    }
    
    public void setRoleId(int roleId)
    {
        this.roleId = roleId;
    }
    
    /**
     * 角色名
     * 
     * @Title getRoleName
     * @author wanglc
     * @date 2013-12-6
     * @return String 角色名
     */
    @Column(name = "ROLE_NAME", nullable = false, length = 50)
    public String getRoleName()
    {
        return this.roleName;
    }
    
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    
    /**
     * 角色编码
     * 
     * @Title getRoleCode
     * @author wanglc
     * @date 2013-12-6
     * @return 角色编码
     */
    @Column(name = "ROLE_CODE",length = 50)
    public String getRoleCode()
    {
        return this.roleCode;
    }
    
    public void setRoleCode(String roleCode)
    {
        this.roleCode = roleCode;
    }
    
    /**
     * 描述
     * 
     * @Title getDescription
     * @author wanglc
     * @date 2013-12-6
     * @return 描述
     */
    @Column(name = "DESCRIPTION", length = 200)
    public String getDescription()
    {
        return this.description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * 是否删除 0 未删除 1已删除
     * 
     * @Title getIsDelete
     * @author wanglc
     * @date 2013-12-6
     * @return 是否删除 0 未删除 1已删除
     */
    @Column(name = "ISDELETE", nullable = false)
    public int getIsDelete()
    {
        return isDelete;
    }
    
    public void setIsDelete(int isDelete)
    {
        this.isDelete = isDelete;
    }

    /**
     * @return roleType
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ROLE_TYPE")
    public Dictionary getRoleType()
    {
        return roleType;
    }

    /**
     * @param roleType 要设置的 roleType
     */
    public void setRoleType(Dictionary roleType)
    {
        this.roleType = roleType;
    }

    /**
     * @return roleTypeUUID
     */
    @Column(name = "FK_ROLE_TYPE_UUID")
    public String getRoleTypeUUID()
    {
        return roleTypeUUID;
    }

    /**
     * @param roleTypeUUID 要设置的 roleTypeUUID
     */
    public void setRoleTypeUUID(String roleTypeUUID)
    {
        this.roleTypeUUID = roleTypeUUID;
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