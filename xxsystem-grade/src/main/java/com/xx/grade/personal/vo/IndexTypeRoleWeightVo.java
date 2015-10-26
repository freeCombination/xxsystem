package com.xx.grade.personal.vo;

/**
 * 指标与角色关系vo
 * 
 * @author wujialing
 *
 */
public class IndexTypeRoleWeightVo {
	
	/**
	 * 主键ID
	 */
	private int id; 
	
	/**
	 * 角色id
	 */
	private int roleId ;
	
	/**
	 * 角色名称
	 */
	private String roleName ;
	
    /**
     * 权重
     */
    private String percentage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
}
