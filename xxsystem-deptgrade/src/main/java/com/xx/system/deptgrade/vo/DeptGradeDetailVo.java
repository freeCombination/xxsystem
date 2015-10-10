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
     * 得分
     */
    private String score;
    
	/**
	 * 评分用户
	 */
    private String gradeUsr;
    
    /**
     * 评分人所在部门
     */
    private String gradeUsrDept;

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
    
}