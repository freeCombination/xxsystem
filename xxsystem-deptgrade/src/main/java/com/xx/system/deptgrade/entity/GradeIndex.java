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

/**
 * 考核指标实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_GRADEINDEX")
@BatchSize(size = 50)
public class GradeIndex implements java.io.Serializable {
	private static final long serialVersionUID = -4950154099670327838L;

	/**
     * @Fields pkIndexId : 主键
     */
    private int pkIndexId;
    
    /**
     * 考核指标编号
     */
    private String number;
    
    /**
     * 考核指标名称
     */
    private String name;
    
    /**
     * @Fields classify : 考核分类
     */
    private IndexClassify classify;
    
    /**
     * 考核分值
     */
    private String grade;
    
    /**
     * 考核说明
     */
    private String remark;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
    /**
     * 一级指标
     */
    private GradeIndex gradeIndex;
    
    /**
     * 二级指标
     */
    private Set<GradeIndex> indexs = new HashSet<GradeIndex>(0);
    
    /**
     * @Title getPkClassifyId
     * @Description: 主键
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_INDEX_ID", nullable = false)
    public int getPkIndexId() {
		return pkIndexId;
	}

	public void setPkIndexId(int pkIndexId) {
		this.pkIndexId = pkIndexId;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLASSIFY_ID", nullable = true)
	public IndexClassify getClassify() {
		return classify;
	}

	public void setClassify(IndexClassify classify) {
		this.classify = classify;
	}

	@Column(name = "GRADE", nullable = true, length = 20)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name = "REMARK", nullable = true, length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PARENTINDEX_ID", nullable = true)
	public GradeIndex getGradeIndex() {
		return gradeIndex;
	}

	public void setGradeIndex(GradeIndex gradeIndex) {
		this.gradeIndex = gradeIndex;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gradeIndex")
    @BatchSize(size = 50)
	public Set<GradeIndex> getIndexs() {
		return indexs;
	}

	public void setIndexs(Set<GradeIndex> indexs) {
		this.indexs = indexs;
	}
}