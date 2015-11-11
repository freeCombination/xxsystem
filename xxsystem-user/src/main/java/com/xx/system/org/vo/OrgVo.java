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
    
    /**
     * @Fields deptLeader : 部门领导
     */
    private String deptLeader;
    
    /**
     * @Fields superiorLeader : 分管领导
     */
    private String superiorLeader;
    
    /**
     * @Fields otherSup : 所领导
     */
    private String otherSup;
    
    /**
     * @Fields superintendent : 所长
     */
    private String superintendent;
    
    /**
     * @Fields excellentCount : 优秀数量
     */
    private String excellentCount;
    
    /**
     * @Fields excellentCount : 优秀分数
     */
    private String excellentScore;
    
    /**
     * @Fields enable : 是否可用：0 是 1 否
     */
    private int enable;
    
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
        this.orgId = o.getOrgId();
        this.orgName = o.getOrgName();
        this.parentOrgId =
            o.getOrganization() == null ? 0 : o.getOrganization().getOrgId();
        this.parentOrgName =
            o.getOrganization() == null ? "" : o.getOrganization().getOrgName();
        this.orgTypeValue = o.getOrgType().getDictionaryValue();
        this.orgTypeName = o.getOrgType().getDictionaryName();
        
        this.deptLeader = o.getDeptHead() == null ? "" : o.getDeptHead().getRealname();
        this.superiorLeader = o.getBranchedLeader() == null ? "" : o.getBranchedLeader().getRealname();
        this.otherSup = o.getOtherSup() == null ? "" : o.getOtherSup().getRealname();
        this.superintendent = o.getSuperintendent() == null ? "" : o.getSuperintendent().getRealname();
        this.excellentCount = o.getExcellentCount() == null ? "" : o.getExcellentCount().toString();
        this.excellentScore = o.getExcellentScore() == null ? "" : o.getExcellentScore().toString();
        this.enable = o.getEnable();
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

	public String getDeptLeader() {
		return deptLeader;
	}

	public void setDeptLeader(String deptLeader) {
		this.deptLeader = deptLeader;
	}

	public String getSuperiorLeader() {
		return superiorLeader;
	}

	public void setSuperiorLeader(String superiorLeader) {
		this.superiorLeader = superiorLeader;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getOtherSup() {
		return otherSup;
	}

	public void setOtherSup(String otherSup) {
		this.otherSup = otherSup;
	}

	public String getSuperintendent() {
		return superintendent;
	}

	public void setSuperintendent(String superintendent) {
		this.superintendent = superintendent;
	}

	public String getExcellentCount() {
		return excellentCount;
	}

	public void setExcellentCount(String excellentCount) {
		this.excellentCount = excellentCount;
	}

	public String getExcellentScore() {
		return excellentScore;
	}

	public void setExcellentScore(String excellentScore) {
		this.excellentScore = excellentScore;
	}
}
