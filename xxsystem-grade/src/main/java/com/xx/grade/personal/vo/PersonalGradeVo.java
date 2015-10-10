package com.xx.grade.personal.vo;

/**
 * 个人评分vo
 * 
 * @author wujialing
 *
 */
public class PersonalGradeVo {
	
	/**
	 * 主键 id
	 */
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String userName ;
	
	/**
     * 部门
     */
	private String orgName ;
	
	/**
     * 岗位
     */
    private String responsibilities;
	
	/**
	 * 用户ID
	 */
	private Integer userId ;
	
	/**
	 * 评分年份
	 */
	private String gradeYear ;
	
	/**
	 * 标题
	 */
	private String title ;
	
	/**
	 * 存在问题
	 */
	private String problem ;
	
	/**
	 * 工作计划
	 */
	private String workPlan ;
	
	/**
	 * 综合得分
	 */
	private String compositeScores ;
	
	/**
	 * 状态（0:创建（可修改），1:提交（不可修改，他人进行评分），2:归档（评分完毕，不可修改））
	 */
	private Integer status ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getGradeYear() {
		return gradeYear;
	}

	public void setGradeYear(String gradeYear) {
		this.gradeYear = gradeYear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getWorkPlan() {
		return workPlan;
	}

	public void setWorkPlan(String workPlan) {
		this.workPlan = workPlan;
	}

	public String getCompositeScores() {
		return compositeScores;
	}

	public void setCompositeScores(String compositeScores) {
		this.compositeScores = compositeScores;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}
	
}
