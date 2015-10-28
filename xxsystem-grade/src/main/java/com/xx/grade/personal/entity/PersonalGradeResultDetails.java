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
import com.xx.system.role.entity.Role;

/**
 * 评分明细 关联评分指标类型和评分结果
 * 
 * @author wujialing
 */
@Entity
@Table(name = "T_PERSONAL_GRADE_RESULT")
public class PersonalGradeResultDetails implements Serializable {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 个人评分结果
	 */
	private PersonalGradeResult personalGradeResult ;
	
	/**
	 * 指标类型 部门绩效考核得分、工作计划完成情况、能力态度、领导能力、执行能力、工作业绩
	 */
	private Dictionary IndexType ;
	
	/**
	 * 角色
	 */
	private Role role ;
	
	/**
	 * 得分
	 */
	private double score ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONAL_GRADE_RESULT_ID", nullable = true)
	public PersonalGradeResult getPersonalGradeResult() {
		return personalGradeResult;
	}

	public void setPersonalGradeResult(PersonalGradeResult personalGradeResult) {
		this.personalGradeResult = personalGradeResult;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_INDEX_TYPE")
	public Dictionary getIndexType() {
		return IndexType;
	}

	public void setIndexType(Dictionary indexType) {
		IndexType = indexType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ROLE_ID", nullable = true)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "score")
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
