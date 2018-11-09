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
     * 加分项
     */
    private String plusedScore;
    
    /**
     * 指标得分小计
     */
    private String sumScore;
    
    /**
     * 最终得分
     */
    private String score;
    
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
     * 党建
     */
    private String partyScore;
    
    /**
     * 综合治理
     */
    private String zhzlScore;
    
    /**
     * 保密
     */
    private String secScore;
    
    /**
     * 例会
     */
    private String lhScore;
    
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

	@Column(name = "JD_SCORE", nullable = true)
	public String getJdScore() {
		return jdScore;
	}

	public void setJdScore(String jdScore) {
		this.jdScore = jdScore;
	}

	@Column(name = "JD_PERCENTAGE", nullable = true)
	public String getJdPercentage() {
		return jdPercentage;
	}

	public void setJdPercentage(String jdPercentage) {
		this.jdPercentage = jdPercentage;
	}

	@Column(name = "JD_SUM_SCORE", nullable = true)
	public String getJdSumScore() {
		return jdSumScore;
	}

	public void setJdSumScore(String jdSumScore) {
		this.jdSumScore = jdSumScore;
	}

	@Column(name = "PLUSED_SCORE", nullable = true)
	public String getPlusedScore() {
		return plusedScore;
	}

	public void setPlusedScore(String plusedScore) {
		this.plusedScore = plusedScore;
	}

	@Column(name = "PARTY_SCORE", nullable = true)
	public String getPartyScore() {
		return partyScore;
	}

	public void setPartyScore(String partyScore) {
		this.partyScore = partyScore;
	}

	@Column(name = "ZHZL_SCORE", nullable = true)
	public String getZhzlScore() {
		return zhzlScore;
	}

	public void setZhzlScore(String zhzlScore) {
		this.zhzlScore = zhzlScore;
	}

	@Column(name = "SEC_SCORE", nullable = true)
	public String getSecScore() {
		return secScore;
	}

	public void setSecScore(String secScore) {
		this.secScore = secScore;
	}

	@Column(name = "LH_SCORE", nullable = true)
	public String getLhScore() {
		return lhScore;
	}

	public void setLhScore(String lhScore) {
		this.lhScore = lhScore;
	}
	
}