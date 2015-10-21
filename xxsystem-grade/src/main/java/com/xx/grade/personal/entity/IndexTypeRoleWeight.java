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

import com.xx.system.role.entity.Role;

/**
 * 指标角色权重-一个指标关联一个角色
 * 
 * @author wujialing
 *
 */
@Entity
@Table(name = "T_INDEXTYPEROLE_WEIGHT")
public class IndexTypeRoleWeight implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer id; 
	
	/**
	 * 角色
	 */
	private Role role ;
	
	/**
	 * 个人权重
	 */
	private PersonalWeight personalWeight ;
	
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONAL_WEIGHT_ID", nullable = true)
	public PersonalWeight getPersonalWeight() {
		return personalWeight;
	}

	public void setPersonalWeight(PersonalWeight personalWeight) {
		this.personalWeight = personalWeight;
	}

	@Column(name = "COMPOSITE_SCORES")
	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ROLE_ID", nullable = true)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
