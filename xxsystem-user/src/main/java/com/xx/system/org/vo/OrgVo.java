package com.xx.system.org.vo;

import com.xx.system.org.entity.Organization;

/**
 * 组织Vo
 * 
 * @author dong.he
 * @version V1.20,2014年9月26日 上午11:06:51
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class OrgVo {
    /** @Fields orgId : 组织主键 */
    private int orgId;
    
    /** @Fields parentOrgId : 上级组织主键 */
    private int parentOrgId;
    
    /** @Fields parentOrgName : 上级组织名称 */
    private String parentOrgName;
    
    /** @Fields orgName : 组织名称 */
    private String orgName;
    
    /** @Fields orgCode : 组织编码 */
    private String orgCode;
    
    /** @Fields orgTypeValue : 组织类型值 */
    private String orgTypeValue;
    
    /** @Fields orgTypeName : 组织类型名称 */
    private String orgTypeName;
    
    /** @Fields orgFrom : 组织来源 */
    private int orgFrom;
    
    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     */
    public OrgVo() {
        
    }
    
    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param o
     */
    public OrgVo(Organization o) {
        this.orgCode = o.getOrgCode();
        this.orgFrom = o.getOrgFrom();
        this.orgId = o.getOrgId();
        this.orgName = o.getOrgName();
        this.parentOrgId =
            o.getOrganization() == null ? 0 : o.getOrganization().getOrgId();
        this.parentOrgName =
            o.getOrganization() == null ? "" : o.getOrganization().getOrgName();
        this.orgTypeValue = o.getOrgType().getDictionaryValue();
        this.orgTypeName = o.getOrgType().getDictionaryName();
    }
    
    /**
     * @return orgId
     */
    public int getOrgId() {
        return orgId;
    }
    
    /**
     * @param orgId 要设置的 orgId
     */
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
    
    /**
     * @return parentOrgId
     */
    public int getParentOrgId() {
        return parentOrgId;
    }
    
    /**
     * @param parentOrgId 要设置的 parentOrgId
     */
    public void setParentOrgId(int parentOrgId) {
        this.parentOrgId = parentOrgId;
    }
    
    /**
     * @return parentOrgName
     */
    public String getParentOrgName() {
        return parentOrgName;
    }
    
    /**
     * @param parentOrgName 要设置的 parentOrgName
     */
    public void setParentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
    }
    
    /**
     * @return orgName
     */
    public String getOrgName() {
        return orgName;
    }
    
    /**
     * @param orgName 要设置的 orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    /**
     * @return orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }
    
    /**
     * @param orgCode 要设置的 orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    /**
     * @return orgTypeValue
     */
    public String getOrgTypeValue() {
        return orgTypeValue;
    }
    
    /**
     * @param orgTypeValue 要设置的 orgTypeValue
     */
    public void setOrgTypeValue(String orgTypeValue) {
        this.orgTypeValue = orgTypeValue;
    }
    
    /**
     * @return orgTypeName
     */
    public String getOrgTypeName() {
        return orgTypeName;
    }
    
    /**
     * @param orgTypeName 要设置的 orgTypeName
     */
    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }
    
    /**
     * @return orgFrom
     */
    public int getOrgFrom() {
        return orgFrom;
    }
    
    /**
     * @param orgFrom 要设置的 orgFrom
     */
    public void setOrgFrom(int orgFrom) {
        this.orgFrom = orgFrom;
    }
    
}
