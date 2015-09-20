package com.xx.system.org.entity;

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

/**
 * 岗位职责实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_DUTY")
@BatchSize(size = 50)
public class Duty implements java.io.Serializable {

	private static final long serialVersionUID = -3403146357291911409L;

	/**
     * @Fields pkDutyId : 主键
     */
    private Integer pkDutyId;
    
    /**
     * 职责编号
     */
    private String number;
    
    /**
     * 职责内容
     */
    private String dutyContent;
    
    /**
     * 职责类型
     */
    private String dutyType;
    
    /**
     * 岗位
     */
    private Responsibilities responsibilities;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
    /**
     * @Title getPkDutyId
     * @Description: 主键
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_DUTY_ID", nullable = false)
    public Integer getPkDutyId() {
		return pkDutyId;
	}

	public void setPkDutyId(Integer pkDutyId) {
		this.pkDutyId = pkDutyId;
	}
    
	@Column(name = "NUMBER", nullable = true, length = 50)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
    
	@Column(name = "DUTY_CONTENT", nullable = false, length = 2000)
	public String getDutyContent() {
		return dutyContent;
	}

	public void setDutyContent(String dutyContent) {
		this.dutyContent = dutyContent;
	}

	@Column(name = "DUTY_TYPE", nullable = false, length = 200)
	public String getDutyType() {
		return dutyType;
	}

	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_RESP_ID", nullable = false)
	public Responsibilities getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(Responsibilities responsibilities) {
		this.responsibilities = responsibilities;
	}
	
    @Column(name = "ISDELETE", nullable = false, precision = 22, scale = 0)
    public int getIsDelete() {
        return isDelete;
    }
    
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}