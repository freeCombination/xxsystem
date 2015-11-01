package com.xx.grade.personal.vo;

/**
 * 个人评分结果明细vo
 * 
 * @author wujialing
 *
 */
public class PersonalGradeResultDetailsVo {
	
	/**
	 * 主键 id
	 */
	private Integer id;
	
	/**
	 * 指标分类id
	 */
	private Integer indexTypeId;
	
	/**
	 * 指标分类名称
	 */
	private String indexTypeName;
	
	/**
	 * 得分
	 */
	private String score ;
	
	/**
	 * 角色id
	 */
	private Integer roleId ;
	
	/**
	 * 角色名称
	 */
	private String roleName ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIndexTypeId() {
		return indexTypeId;
	}

	public void setIndexTypeId(Integer indexTypeId) {
		this.indexTypeId = indexTypeId;
	}

	public String getIndexTypeName() {
		return indexTypeName;
	}

	public void setIndexTypeName(String indexTypeName) {
		this.indexTypeName = indexTypeName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
