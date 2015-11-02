package com.xx.grade.personal.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OrderBy;

import com.xx.system.org.entity.Organization;
import com.xx.system.user.entity.User;

/**
 * 个人评分结果
 * 
 * @author wujialing
 *
 */
@Entity
@Table(name = "T_PERSONAL_GRADE_RESULT")
public class PersonalGradeResult implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 个人评分
	 */
	private PersonalGrade personalGrade ;
	
	/**
	 * 评分员工
	 */
	private User gradeUser;
	
	/**
	 * 评分员工组织
	 */
	private Organization gradeOrg ;
	
	/**
	 * 得分
	 */
	private double score ;
	
	/**
	 * 评分时间
	 */
	private Date gradeDate ;
	
	/**
	 * 状态 0可修改 1提交（不可修改）
	 */
	private Integer state ;
	
	/**
	 * 评分员工类型 0普通员工 1部门主任 2分管领导 3协管领导 4所长
	 */
	private Integer gradeUserType ;
	
	/**
	 * 领导评价 只有员工类型不为普通员工 评分页面才显示评价（部门意见）
	 */
	private String evaluation ;
	
	/**
	 * 领导评价 只有员工类型不为普通员工 评分页面才显示评价（分管领导意见）
	 */
	private String evaluation1 ;
	
	/**
	 * 领导评价 只有员工类型不为普通员工 评分页面才显示评价（其他所领导意见）
	 */
	private String evaluation2 ;
	
	/**
	 * 领导评价 只有员工类型不为普通员工 评分页面才显示评价（所领导意见）
	 */
	private String evaluation3 ;
	
	/**
	 * 个人评分结果详情
	 */
	private Set<PersonalGradeResultDetails> details ;

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
    @JoinColumn(name = "PERSONAL_GRADE_ID", nullable = true)
	public PersonalGrade getPersonalGrade() {
		return personalGrade;
	}

	public void setPersonalGrade(PersonalGrade personalGrade) {
		this.personalGrade = personalGrade;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRADE_USER_ID", nullable = true)
	public User getGradeUser() {
		return gradeUser;
	}

	public void setGradeUser(User gradeUser) {
		this.gradeUser = gradeUser;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRADE_ORG_ID", nullable = true)
	public Organization getGradeOrg() {
		return gradeOrg;
	}

	public void setGradeOrg(Organization gradeOrg) {
		this.gradeOrg = gradeOrg;
	}

	@Column(name = "score")
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GRADE_DATE")
	public Date getGradeDate() {
		return gradeDate;
	}

	public void setGradeDate(Date gradeDate) {
		this.gradeDate = gradeDate;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "GRADEUSER_TYPE")
	public Integer getGradeUserType() {
		return gradeUserType;
	}

	public void setGradeUserType(Integer gradeUserType) {
		this.gradeUserType = gradeUserType;
	}

	@Column(name = "evaluation")
	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	
	@Column(name = "evaluation1")
	public String getEvaluation1() {
		return evaluation1;
	}

	public void setEvaluation1(String evaluation1) {
		this.evaluation1 = evaluation1;
	}

	@Column(name = "evaluation2")
	public String getEvaluation2() {
		return evaluation2;
	}

	public void setEvaluation2(String evaluation2) {
		this.evaluation2 = evaluation2;
	}

	@Column(name = "evaluation3")
	public String getEvaluation3() {
		return evaluation3;
	}

	public void setEvaluation3(String evaluation3) {
		this.evaluation3 = evaluation3;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "personalGradeResult")
	@OrderBy(clause="ID desc")
	public Set<PersonalGradeResultDetails> getDetails() {
		return details;
	}

	public void setDetails(Set<PersonalGradeResultDetails> details) {
		this.details = details;
	}
}
