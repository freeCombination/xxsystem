package com.xx.system.deptgrade.vo;

/**
 * 部门评分实体VO定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
public class DeptGradeDetailVo implements java.io.Serializable {

	private static final long serialVersionUID = 5313210029238183684L;

	/**
     * @Fields gradeDetailId : 主键
     */
    private Integer gradeDetailId;
    
    /**
     * @Fields classify : 考核分类
     */
    private String classifyName;
    
    /**
     * 考核分类ID
     */
    private Integer classifyId;
    
    /**
     * 一级指标名称
     */
    private String name;
    
    /**
     * 二级指标
     */
    private String gradeIndex2Name;
    
    /**
     * @Fields 评分年份
     */
    private String electYear;
    
    /**
     * 被评分部门
     */
    private String canpDept;
    
    /**
     * 被评分部门ID
     */
    private Integer canpDeptId;
    
    /**
     * 得分
     */
    private String score;
    
    /**
     * 权重
     */
    private String percentage;
    
    /**
     * 加分项
     */
    private String plusedScore;
    
    /**
     * 指标得分小计
     */
    private String sumScore;
    
    /**
     * 部门建设得分
     */
    private String buildScore;
    
    /**
     * 总分
     */
    private String finalScore;
    
    /**
     * *******季度得分相关属性*********
     */
    
    /**
     * 季度得分
     */
    private String jdScore;
    
    /**
     * 季度权重
     */
    private String jdPercentage;
    
    /**
     * 季度得分小计
     */
    private String jdSumScore;
    
    /**
     * 是否参与评分
     */
    private Integer isParticipation;
    
	/**
	 * 评分用户
	 */
    private String gradeUsr;
    
    /**
     * 评分人所在部门
     */
    private String gradeUsrDept;
    
    
    /**
     * 党建得分
     */
    private String partyScore;
    /**
     * 综合治理得分
     */
    private String zhzlScore;
    /**
     * 保密得分
     */
    private String secScore;
    /**
     * 例会得分
     */
    private String lhScore;

	public Integer getGradeDetailId() {
		return gradeDetailId;
	}

	public void setGradeDetailId(Integer gradeDetailId) {
		this.gradeDetailId = gradeDetailId;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGradeIndex2Name() {
		return gradeIndex2Name;
	}

	public void setGradeIndex2Name(String gradeIndex2Name) {
		this.gradeIndex2Name = gradeIndex2Name;
	}

	public String getElectYear() {
		return electYear;
	}

	public void setElectYear(String electYear) {
		this.electYear = electYear;
	}

	public String getCanpDept() {
		return canpDept;
	}

	public void setCanpDept(String canpDept) {
		this.canpDept = canpDept;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getGradeUsr() {
		return gradeUsr;
	}

	public void setGradeUsr(String gradeUsr) {
		this.gradeUsr = gradeUsr;
	}

	public String getGradeUsrDept() {
		return gradeUsrDept;
	}

	public void setGradeUsrDept(String gradeUsrDept) {
		this.gradeUsrDept = gradeUsrDept;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getSumScore() {
		return sumScore;
	}

	public void setSumScore(String sumScore) {
		this.sumScore = sumScore;
	}

	public String getBuildScore() {
		return buildScore;
	}

	public void setBuildScore(String buildScore) {
		this.buildScore = buildScore;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}

	public Integer getIsParticipation() {
		return isParticipation;
	}

	public void setIsParticipation(Integer isParticipation) {
		this.isParticipation = isParticipation;
	}

	public Integer getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(Integer classifyId) {
		this.classifyId = classifyId;
	}

	public Integer getCanpDeptId() {
		return canpDeptId;
	}

	public void setCanpDeptId(Integer canpDeptId) {
		this.canpDeptId = canpDeptId;
	}

	public String getJdScore() {
		return jdScore;
	}

	public void setJdScore(String jdScore) {
		this.jdScore = jdScore;
	}

	public String getJdPercentage() {
		return jdPercentage;
	}

	public void setJdPercentage(String jdPercentage) {
		this.jdPercentage = jdPercentage;
	}

	public String getJdSumScore() {
		return jdSumScore;
	}

	public void setJdSumScore(String jdSumScore) {
		this.jdSumScore = jdSumScore;
	}

	public String getPlusedScore() {
		return plusedScore;
	}

	public void setPlusedScore(String plusedScore) {
		this.plusedScore = plusedScore;
	}

	public String getPartyScore() {
		return partyScore;
	}

	public void setPartyScore(String partyScore) {
		this.partyScore = partyScore;
	}

	public String getZhzlScore() {
		return zhzlScore;
	}

	public void setZhzlScore(String zhzlScore) {
		this.zhzlScore = zhzlScore;
	}

	public String getSecScore() {
		return secScore;
	}

	public void setSecScore(String secScore) {
		this.secScore = secScore;
	}

	public String getLhScore() {
		return lhScore;
	}

	public void setLhScore(String lhScore) {
		this.lhScore = lhScore;
	}
	
}