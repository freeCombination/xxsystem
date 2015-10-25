package com.xx.system.deptgrade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

/**
 * 复制评分基础数据记录实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_COPYRECORD")
@BatchSize(size = 50)
public class CopyRecord implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * @Fields pkCopyRecordId : 主键
     */
    private Integer pkCopyRecordId;
    
    /**
     * @Fields 评分年份
     */
    private String electYear;
    
    /**
     * @Fields hasCopied : 是否已复制：0 否 1 是
     */
    private Integer hasCopied;
    
    
    /**
     * @Title getPkCopyRecordId
     * @Description: 主键
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_COPYRECORD_ID", nullable = false)
    public Integer getPkCopyRecordId() {
		return pkCopyRecordId;
	}

	public void setPkCopyRecordId(Integer pkCopyRecordId) {
		this.pkCopyRecordId = pkCopyRecordId;
	}

	@Column(name = "ELECTYEAR", nullable = false, length = 10)
	public String getElectYear() {
		return electYear;
	}

	public void setElectYear(String electYear) {
		this.electYear = electYear;
	}

	@Column(name = "HAS_COPIED", nullable = false, precision = 22, scale = 0)
	public Integer getHasCopied() {
		return hasCopied;
	}

	public void setHasCopied(Integer hasCopied) {
		this.hasCopied = hasCopied;
	}
}