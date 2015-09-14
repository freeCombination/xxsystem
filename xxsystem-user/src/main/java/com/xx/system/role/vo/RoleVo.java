package com.xx.system.role.vo;

import java.util.List;

import com.xx.system.role.entity.Role;

/**
 * 角色Vo
 * 
 * @version V1.20,2013-12-6 上午9:14:53
 * @see [相关类/方法]   
 * @since V1.20
 * @depricated
 */
public class RoleVo
{
    
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
     * @Fields description : 描述
     */
    private String description;
    
    /**
     * @Fields roleTypeName : 角色类型名称
     */
    private String roleTypeName;
    
    /**
     * @Fields roleTypeName : 角色类型值
     */
    private String roleTypeValue;
    
    /**
     * @Fields roleTypeName : 角色类型ID
     */
    private int roleTypeId;
    
    /**
     * 角色所对应的资源ID
     */
    private List<Integer> roleResourceIds;
    
    public RoleVo()
    {
        
    }
    
    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param role
     */
    public RoleVo(Role role)
    {
        this.description = role.getDescription();
        this.roleCode = role.getRoleCode();
        this.roleName = role.getRoleName();
        this.roleId = role.getRoleId();
        this.roleTypeId = role.getRoleType().getPkDictionaryId();
        this.roleTypeValue = role.getRoleType().getDictionaryValue();
        this.roleTypeName = role.getRoleType().getDictionaryName();
        
    }
    
    public int getRoleId()
    {
        return roleId;
    }
    
    public void setRoleId(int roleId)
    {
        this.roleId = roleId;
    }
    
    public String getRoleName()
    {
        return roleName;
    }
    
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    
    public String getRoleCode()
    {
        return roleCode;
    }
    
    public void setRoleCode(String roleCode)
    {
        this.roleCode = roleCode;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<Integer> getRoleResourceIds()
    {
        return roleResourceIds;
    }
    
    public void setRoleResourceIds(List<Integer> roleResourceIds)
    {
        this.roleResourceIds = roleResourceIds;
    }
    
    /**
     * @return roleTypeName
     */
    public String getRoleTypeName()
    {
        return roleTypeName;
    }
    
    /**
     * @param roleTypeName 要设置的 roleTypeName
     */
    public void setRoleTypeName(String roleTypeName)
    {
        this.roleTypeName = roleTypeName;
    }
    
    /**
     * @return roleTypeValue
     */
    public String getRoleTypeValue()
    {
        return roleTypeValue;
    }
    
    /**
     * @param roleTypeValue 要设置的 roleTypeValue
     */
    public void setRoleTypeValue(String roleTypeValue)
    {
        this.roleTypeValue = roleTypeValue;
    }
    
    /**
     * @return roleTypeId
     */
    public int getRoleTypeId()
    {
        return roleTypeId;
    }
    
    /**
     * @param roleTypeId 要设置的 roleTypeId
     */
    public void setRoleTypeId(int roleTypeId)
    {
        this.roleTypeId = roleTypeId;
    }
    
}
