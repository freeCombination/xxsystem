package com.xx.grade.personal.vo;


/**
 * 评分结果vo
 * 
 * @author wujialing
 *
 */
public class PersonalGradeResultVo {
	
	/**
	 * 主键 id
	 */
	private Integer id;
	
	/**
	 * 个人评分id
	 */
	private Integer personalGradeId;
	
	/**
	 * 被评分人
	 */
	private String gradeUser ;
	
	/**
	 * 评分员工组织
	 */
	private String gradeOrg ;
	
	/**
	 * 评分人
	 */
	private String userName ;
	
	/**
	 * 评分人组织
	 */
	private String orgName ;
	
	/**
	 * 得分
	 */
	private double score ;
	
	/**
	 * 评分时间
	 */
	private String gradeDate ;
	
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
	 * 状态 0可修改 1提交（不可修改）
	 */
	private Integer state ;
	
	  /**
     * 岗位
     */
    private String responsibilities;
    
    /**
     * 出生日期
     */
    private String birthDay;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 学历（专业、毕业时间、学校）
     */
    private String educationBackground;
    
    /**
     * 政治面貌
     */
    private String politicsStatus ;
    
	/**
	 * 评分员工类型 0普通员工 1部门主任 2分管领导 3协管领导 4所长
	 */
	private Integer gradeUserType ;
    
	/**
	 * 领导评价 只有员工类型不为普通员工 评分页面才显示评价
	 */
	private String evaluation ;
	
	private String roleName ; //评分人角色
	
	private String indexTypeName ; //评分指标
	
	private String percentage ; //角色所占比例
	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIndexTypeName() {
		return indexTypeName;
	}

	public void setIndexTypeName(String indexTypeName) {
		this.indexTypeName = indexTypeName;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGradeUser() {
		return gradeUser;
	}

	public void setGradeUser(String gradeUser) {
		this.gradeUser = gradeUser;
	}

	public String getGradeOrg() {
		return gradeOrg;
	}

	public void setGradeOrg(String gradeOrg) {
		this.gradeOrg = gradeOrg;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getGradeDate() {
		return gradeDate;
	}

	public void setGradeDate(String gradeDate) {
		this.gradeDate = gradeDate;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEducationBackground() {
		return educationBackground;
	}

	public void setEducationBackground(String educationBackground) {
		this.educationBackground = educationBackground;
	}

	public String getPoliticsStatus() {
		return politicsStatus;
	}

	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}

	public Integer getPersonalGradeId() {
		return personalGradeId;
	}

	public void setPersonalGradeId(Integer personalGradeId) {
		this.personalGradeId = personalGradeId;
	}

	public Integer getGradeUserType() {
		return gradeUserType;
	}

	public void setGradeUserType(Integer gradeUserType) {
		this.gradeUserType = gradeUserType;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
}
