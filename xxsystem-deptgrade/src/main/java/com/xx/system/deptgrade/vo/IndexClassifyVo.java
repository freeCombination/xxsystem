package com.xx.system.deptgrade.vo;

/**
 * 考核指标分类实体VO定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
public class IndexClassifyVo implements java.io.Serializable {

	private static final long serialVersionUID = -3345121268560793197L;

	/**
     * @Fields classifyId : 主键
     */
    private int classifyId;
    
    /**
     * 考核指标分类编号
     */
    private String number;
    
    /**
     * 考核指标分类名称
     */
    private String name;
    
    /**
     * @Fields orgNames : 考核部门
     */
    private String orgNames;
    
    /**
     * @Fields orgIds : 考核部门ids
     */
    private String orgIds;
    
    /**
     * @Fields 评分年份
     */
    private String electYear;
    
    /**
     * @Fields hasSubmit : 是否已评分：0 否 1 是
     */
    private Integer hasSubmit;
    
    /**
     * @Fields enable : 是否可用：0 是 1 否
     */
    private int enable;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
    /**
     * @Fields scoreTypeId : 汇总得分分类ID
     */
    private Integer scoreTypeId;
    
    /**
     * @Fields scoreTypeName : 汇总得分分类
     */
    private String scoreTypeName;
    
    /**
     * 是否参与评分
     */
    private String participation;

	public int getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(int classifyId) {
		this.classifyId = classifyId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgNames() {
		return orgNames;
	}

	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public String getElectYear() {
		return electYear;
	}

	public void setElectYear(String electYear) {
		this.electYear = electYear;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getHasSubmit() {
		return hasSubmit;
	}

	public void setHasSubmit(Integer hasSubmit) {
		this.hasSubmit = hasSubmit;
	}

	public Integer getScoreTypeId() {
		return scoreTypeId;
	}

	public void setScoreTypeId(Integer scoreTypeId) {
		this.scoreTypeId = scoreTypeId;
	}

	public String getScoreTypeName() {
		return scoreTypeName;
	}

	public void setScoreTypeName(String scoreTypeName) {
		this.scoreTypeName = scoreTypeName;
	}

	public String getParticipation() {
		return participation;
	}

	public void setParticipation(String participation) {
		this.participation = participation;
	}

}