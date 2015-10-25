package com.xx.system.deptgrade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.xx.system.org.entity.Organization;

/**
 * 部门最终得分实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_FINALSCORE")
@BatchSize(size = 50)
public class FinalScore implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * @Fields pkFinalScoreId : 主键
     */
    private Integer pkFinalScoreId;
    
    /**
     * @Fields 评分年份
     */
    private String electYear;
    
    /**
     * @Fields orgs : 考核部门
     */
    private Organization org;
    
    /**
     * 指标得分小计
     */
    private String sumScore;
    
    /**
     * 最终得分
     */
    private String score;
    
    /**
     * @Title getPkFinalScoreId
     * @Description: 主键
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_FINALSCORE_ID", nullable = false)
    public Integer getPkFinalScoreId() {
		return pkFinalScoreId;
	}

	public void setPkFinalScoreId(Integer pkFinalScoreId) {
		this.pkFinalScoreId = pkFinalScoreId;
	}

	@Column(name = "ELECTYEAR", nullable = false, length = 10)
	public String getElectYear() {
		return electYear;
	}

	public void setElectYear(String electYear) {
		this.electYear = electYear;
	}

	@Column(name = "SCORE", nullable = true)
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ORG_ID", nullable = false)
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	@Column(name = "SUM_SCORE", nullable = true)
	public String getSumScore() {
		return sumScore;
	}

	public void setSumScore(String sumScore) {
		this.sumScore = sumScore;
	}
}