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
import com.xx.system.org.entity.Responsibilities;

/**
 * 权重实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_GRADEPERCENTAGE")
@BatchSize(size = 50)
public class GradePercentage implements java.io.Serializable {

	private static final long serialVersionUID = -2745797740192300789L;

	/**
     * @Fields pkPerId : 主键
     */
    private int pkPerId;
    
    /**
     * @Fields classify : 指标分类
     */
    private IndexClassify classify;
    
    /**
     * 单据编号
     */
    private String receiptsNum;
    
    /**
     * @Fields classify : 部门
     */
    private Organization org;
    
    /**
     * 岗位
     */
    private Responsibilities resp;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 权重
     */
    private String percentage;
    
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
    @Column(name = "PK_PER_ID", nullable = false)
    public int getPkPerId() {
		return pkPerId;
	}

	public void setPkPerId(int pkPerId) {
		this.pkPerId = pkPerId;
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

	@Column(name = "REMARK", nullable = true, length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "RECEIPTSNUM", nullable = true, length = 100)
	public String getReceiptsNum() {
		return receiptsNum;
	}

	public void setReceiptsNum(String receiptsNum) {
		this.receiptsNum = receiptsNum;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ORG_ID", nullable = true)
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_RESP_ID", nullable = true)
	public Responsibilities getResp() {
		return resp;
	}

	public void setResp(Responsibilities resp) {
		this.resp = resp;
	}

	@Column(name = "PERCENTAGE", nullable = true, length = 20)
	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
}