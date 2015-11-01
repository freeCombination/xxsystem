package com.xx.grade.personal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xx.system.dict.entity.Dictionary;

/**
 * 指标评分分类与个人关联历史
 * 
 * @author wujialing
 */
@Entity
@Table(name = "T_PERSONAL_GRADE_DETAILS")
public class PersonalGradeDetails implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键 id
	 */
	private Integer id;
	
	/**
	 * 指标类型 部门绩效考核得分、工作计划完成情况、能力态度、领导能力、执行能力、工作业绩
	 */
	private Dictionary indexType ;
	
	/**
	 * 个人评分
	 */
	private PersonalGrade personalGrade ;
	
	/**
	 * 得分
	 */
	private Double score ;
	
    /**
     * 权重
     */
    private String percentage;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_INDEX_TYPE")
	public Dictionary getIndexType() {
		return indexType;
	}

	public void setIndexType(Dictionary indexType) {
		this.indexType = indexType;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONAL_GRADE_ID", nullable = true)
	public PersonalGrade getPersonalGrade() {
		return personalGrade;
	}

	public void setPersonalGrade(PersonalGrade personalGrade) {
		this.personalGrade = personalGrade;
	}

	@Column(name = "SCORE")
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "PERCENTAGE")
	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
}
