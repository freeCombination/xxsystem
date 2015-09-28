package com.xx.system.deptgrade.vo;

/**
 * 考核指标实体VO定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
public class GradeIndexVo implements java.io.Serializable {

	private static final long serialVersionUID = 5234096313623999728L;

	/**
     * @Fields indexId : 主键
     */
    private int indexId;
    
    /**
     * 考核指标编号
     */
    private String number;
    
    /**
     * 考核指标名称
     */
    private String name;
    
    /**
     * @Fields classify : 考核分类
     */
    private String classifyName;
    
    /**
     * @Fields classify : 考核分类ID
     */
    private Integer classifyId;
    
    /**
     * 考核分值
     */
    private String grade;
    
    /**
     * 考核说明
     */
    private String remark;
    
    /**
     * @Fields 评分年份
     */
    private String electYear;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
    /**
     * 一级指标
     */
    private String gradeIndexName;
    
    /**
     * 一级指标ID
     */
    private Integer gradeIndexId;

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
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

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public Integer getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(Integer classifyId) {
		this.classifyId = classifyId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getGradeIndexName() {
		return gradeIndexName;
	}

	public void setGradeIndexName(String gradeIndexName) {
		this.gradeIndexName = gradeIndexName;
	}

	public Integer getGradeIndexId() {
		return gradeIndexId;
	}

	public void setGradeIndexId(Integer gradeIndexId) {
		this.gradeIndexId = gradeIndexId;
	}

	public String getElectYear() {
		return electYear;
	}

	public void setElectYear(String electYear) {
		this.electYear = electYear;
	}
}