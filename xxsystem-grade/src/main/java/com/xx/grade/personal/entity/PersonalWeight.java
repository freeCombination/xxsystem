package com.xx.grade.personal.entity;

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

import com.xx.system.dict.entity.Dictionary;

/**
 * 个人评分权重主表 - 权重分类与指标类型确定唯一
 * 
 * @author wujialing
 *
 */
@Entity
@Table(name = "T_PERSONAL_WEIGHT")
public class PersonalWeight implements Serializable {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer id; 
	
	/**
	 * 权重分类 个人评权重，部门领导评分权重，领导评分权重
	 */
	private Dictionary classification ;
	
	/**
	 * 指标类型 部门绩效考核得分、工作计划完成情况、能力态度、领导能力、执行能力、工作业绩
	 */
	private Dictionary IndexType ;
	
    /**
     * 权重
     */
    private String percentage;
    
	/**
	 * 指标角色关系
	 */
	private Set<IndexTypeRoleWeight> indexTypeRoles ;
	
    /**
     * 备注
     */
	private String remark ;
	
	/**
	 * 是否参与评分（针对于部门指标不参与单独评分）
	 */
	private boolean isGrade ;

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
    @JoinColumn(name = "FK_CLASSIFICATION")
	public Dictionary getClassification() {
		return classification;
	}

	public void setClassification(Dictionary classification) {
		this.classification = classification;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_INDEX_TYPE")
	public Dictionary getIndexType() {
		return IndexType;
	}

	public void setIndexType(Dictionary indexType) {
		IndexType = indexType;
	}

	@Column(name = "PERCENTAGE")
	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "personalWeight")
	@OrderBy(clause="ID desc")
	public Set<IndexTypeRoleWeight> getIndexTypeRoles() {
		return indexTypeRoles;
	}

	public void setIndexTypeRoles(Set<IndexTypeRoleWeight> indexTypeRoles) {
		this.indexTypeRoles = indexTypeRoles;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "IS_GRADE")
	public boolean isGrade() {
		return isGrade;
	}

	public void setGrade(boolean isGrade) {
		this.isGrade = isGrade;
	}
	
	
}
