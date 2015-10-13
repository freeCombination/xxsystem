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
    private Integer indexId;
    
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
     * 考核分值2
     */
    private String grade2;
    
    /**
     * 考核说明
     */
    private String remark;
    
    /**
     * 考核说明2
     */
    private String remark2;
    
    /**
     * @Fields 评分年份
     */
    private String electYear;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
    /**
     * 二级指标
     */
    private String gradeIndex2Name;
    
    /**
     * 一级指标ID
     */
    private Integer gradeIndex1Id;
    
    /**
     * 部门得分数据字符串
     */
    private String gradeRecs;

	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
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

	public String getGradeIndex2Name() {
		return gradeIndex2Name;
	}

	public void setGradeIndex2Name(String gradeIndex2Name) {
		this.gradeIndex2Name = gradeIndex2Name;
	}

	public Integer getGradeIndex1Id() {
		return gradeIndex1Id;
	}

	public void setGradeIndex1Id(Integer gradeIndex1Id) {
		this.gradeIndex1Id = gradeIndex1Id;
	}

	public String getElectYear() {
		return electYear;
	}

	public void setElectYear(String electYear) {
		this.electYear = electYear;
	}

	public String getGrade2() {
		return grade2;
	}

	public void setGrade2(String grade2) {
		this.grade2 = grade2;
	}

	public String getGradeRecs() {
		return gradeRecs;
	}

	public void setGradeRecs(String gradeRecs) {
		this.gradeRecs = gradeRecs;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
}