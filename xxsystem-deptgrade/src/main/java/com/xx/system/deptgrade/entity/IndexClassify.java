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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.xx.system.org.entity.Organization;

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
    private String number;
    
    /**
     * 考核指标分类名称
     */
    private String name;
    
    /**
     * @Fields orgs : 考核部门
     */
    private Set<Organization> orgs = new HashSet<Organization>(0);
    
    /**
     * @Fields 评分年份
     */
    private String electYear;
    
    /**
     * @Fields enable : 是否可用：0 是 1 否
     */
    private int enable;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
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
    
	@Column(name = "NUMBER", nullable = true, length = 50)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "indexClassify")
    @BatchSize(size = 50)
	public Set<Organization> getOrgs() {
		return orgs;
	}

	public void setOrgs(Set<Organization> orgs) {
		this.orgs = orgs;
	}

	@Column(name = "ELECTYEAR", nullable = true, length = 10)
	public String getElectYear() {
		return electYear;
	}

	public void setElectYear(String electYear) {
		this.electYear = electYear;
	}
}