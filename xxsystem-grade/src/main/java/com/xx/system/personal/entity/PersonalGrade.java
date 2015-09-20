package com.xx.system.personal.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.OrderBy;

import com.xx.system.org.entity.Organization;
import com.xx.system.user.entity.User;

/**
 * 个人评分实体
 * 
 * @author wujialing
 */
@Entity
@Table(name = "T_PERSONAL_GRADE")
public class PersonalGrade implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键 id
	 */
	private Integer id;
	
	/**
	 * 员工
	 */
	private User user;
	
	/**
	 * 组织
	 */
	private Organization org ;
	
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
	private Double compositeScores ;
	
	/**
	 * 状态（0:创建（可修改），1:提交（不可修改，他人进行评分），2:归档（评分完毕，不可修改））
	 */
	private Integer status ;
	
	/**
	 * 删除标志 0未删除 1删除
	 */
	private Integer isDelete ;
	
	/**
	 * 个人职责列表
	 */
	private Set<PersonalDuty> personalDutys ;
	
	/**
	 * 个人评分结果列表
	 */
	private Set<PersonalGradeResult> result ;

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
    @JoinColumn(name = "USER_ID", nullable = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID", nullable = true)
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	@Column(name = "GRADE_YEAR", length = 4)
	public String getGradeYear() {
		return gradeYear;
	}

	public void setGradeYear(String gradeYear) {
		this.gradeYear = gradeYear;
	}

	@Column(name = "PROBLEM", length = 500)
	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	@Column(name = "WORK_PLAN", length = 500)
	public String getWorkPlan() {
		return workPlan;
	}

	public void setWorkPlan(String workPlan) {
		this.workPlan = workPlan;
	}

	@Column(name = "COMPOSITE_SCORES")
	public Double getCompositeScores() {
		return compositeScores;
	}

	public void setCompositeScores(Double compositeScores) {
		this.compositeScores = compositeScores;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "ISDELETE")
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "personalGrade")
	@OrderBy(clause="ID desc")
	public Set<PersonalDuty> getPersonalDutys() {
		return personalDutys;
	}

	public void setPersonalDutys(Set<PersonalDuty> personalDutys) {
		this.personalDutys = personalDutys;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "personalGrade")
	@OrderBy(clause="GRADE_DATE desc")
	public Set<PersonalGradeResult> getResult() {
		return result;
	}

	public void setResult(Set<PersonalGradeResult> result) {
		this.result = result;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
