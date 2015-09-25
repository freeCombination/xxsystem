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
 * 考核指标分类实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_ORGANDCLASSIFY")
@BatchSize(size = 50)
public class OrgAndClassify implements java.io.Serializable {
	private static final long serialVersionUID = -3109594331047803529L;

	/**
     * @Fields pkOrgAndClassifyId : 主键
     */
    private int pkOrgAndClassifyId;
    
    /**
     * 考核指标分
     */
    private IndexClassify classify;
    
    /**
     * @Fields orgs : 考核部门
     */
    private Organization org;
    
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
    @Column(name = "PK_ORGANDCLASSIFY_ID", nullable = false)
    public int getPkOrgAndClassifyId() {
		return pkOrgAndClassifyId;
	}

	public void setPkOrgAndClassifyId(int pkOrgAndClassifyId) {
		this.pkOrgAndClassifyId = pkOrgAndClassifyId;
	}
    
    @Column(name = "ISDELETE", nullable = false, precision = 22, scale = 0)
    public int getIsDelete() {
        return isDelete;
    }
    
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLASSIFY_ID", nullable = false)
	public IndexClassify getClassify() {
		return classify;
	}

	public void setClassify(IndexClassify classify) {
		this.classify = classify;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ORG_ID", nullable = false)
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
}