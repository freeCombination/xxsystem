package com.xx.system.personal.entity;

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

/**
 * 个人职责明细
 * 
 * @author wujialing
 *
 */
@Entity
@Table(name = "T_PERSONAL_DUTY")
public class PersonalDuty implements Serializable {

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
	 * 工作职责
	 */
	private String workDuty ;
	
	/**
	 * 完成情况
	 */
	private String completion ;

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

	@Column(name = "WORK_DUTY", length = 500)
	public String getWorkDuty() {
		return workDuty;
	}

	public void setWorkDuty(String workDuty) {
		this.workDuty = workDuty;
	}

	@Column(name = "COMPLETION", length = 500)
	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

}
