package com.xx.system.deptgrade.entity;

import java.util.HashSet;
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

import org.hibernate.annotations.BatchSize;

import com.xx.system.dict.entity.Dictionary;

/**
 * 考核指标分类实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_INDEXCLASSIFY")
@BatchSize(size = 50)
public class IndexClassify implements java.io.Serializable {
	private static final long serialVersionUID = -3109594331047803529L;

	/**
     * @Fields pkClassifyId : 主键
     */
    private int pkClassifyId;
    
    /**
     * 考核指标分类编号
     */
    private Integer number;
    
    /**
     * 考核指标分类名称
     */
    private String name;
    
    /**
     * @Fields orgs : 考核部门
     */
    private Set<OrgAndClassify> orgCfs = new HashSet<OrgAndClassify>(0);
    
    /**
     * @Fields 评分年份
     */
    private String electYear;
    
    /**
     * @Fields hasSubmit : 是否已评分：0 否 1 是
     */
    private Integer hasSubmit;
    
    /**
     * @Fields enable : 是否可用：0 是 1 否
     */
    private int enable;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
    /**
     * @Fields scoreTypeId : 汇总得分分类ID
     */
    private Dictionary scoreType;
    
    /**
     * 是否参与评分
     */
    private Integer isParticipation;
    
    /**
     * 不参与该指标评分的用户
     */
    private String noParticipationUsr;
    
    /**
     * @Title getPkClassifyId
     * @Description: 主键
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_CLASSIFY_ID", nullable = false)
    public int getPkClassifyId() {
		return pkClassifyId;
	}

	public void setPkClassifyId(int pkClassifyId) {
		this.pkClassifyId = pkClassifyId;
	}
    
	@Column(name = "NUMBER")
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    @Column(name = "ISDELETE", nullable = false, precision = 22, scale = 0)
    public int getIsDelete() {
        return isDelete;
    }
    
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
    
	@Column(name = "ENABLE", nullable = false, precision = 22, scale = 0)
	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	@Column(name = "ELECTYEAR", nullable = true, length = 10)
	public String getElectYear() {
		return electYear;
	}

	public void setElectYear(String electYear) {
		this.electYear = electYear;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "classify")
    @BatchSize(size = 50)
	public Set<OrgAndClassify> getOrgCfs() {
		return orgCfs;
	}

	public void setOrgCfs(Set<OrgAndClassify> orgCfs) {
		this.orgCfs = orgCfs;
	}

	@Column(name = "HAS_SUBMIT", nullable = false, precision = 22, scale = 0)
	public Integer getHasSubmit() {
		return hasSubmit;
	}

	public void setHasSubmit(Integer hasSubmit) {
		this.hasSubmit = hasSubmit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SCORETYPE")
	public Dictionary getScoreType() {
		return scoreType;
	}

	public void setScoreType(Dictionary scoreType) {
		this.scoreType = scoreType;
	}

	@Column(name = "IS_PARTICIPATION", nullable = false, precision = 22, scale = 0)
	public Integer getIsParticipation() {
		return isParticipation;
	}

	public void setIsParticipation(Integer isParticipation) {
		this.isParticipation = isParticipation;
	}

	@Column(name = "NO_PARTICIPATION_USR")
	public String getNoParticipationUsr() {
		return noParticipationUsr;
	}

	public void setNoParticipationUsr(String noParticipationUsr) {
		this.noParticipationUsr = noParticipationUsr;
	}
	
}