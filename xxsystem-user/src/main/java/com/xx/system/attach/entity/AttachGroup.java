package com.xx.system.attach.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OrderBy;

/**
 * TAttachGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ATTACH_GROUP")
public class AttachGroup implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	
	/**
	 * 上传时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMIT_DATE", nullable = false)
	private Date submitDate;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ATTACH_GROUP_ID")
	private int attachGroupId;
	/**
	 * 备注
	 */
	@Column(name = "REMARKS", length = 2000)
	private String remarks;
	/**
	 * 当前状态
	 */
	@Column(name = "STATUS", nullable = false)
	private int status;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "attachGroup")
	@OrderBy(clause="SUBMIT_DATE desc")
	private List<Attach> attachs;
	// Constructors

	/** default constructor */
	public AttachGroup() {
	}

	/** minimal constructor */
	public AttachGroup(int attachGroupId, 
			Date submitDate, int status) {
		this.attachGroupId = attachGroupId;
		this.submitDate = submitDate;
		this.status = status;
	}

	/** full constructor */
	public AttachGroup(int attachGroupId, Date submitDate, int status, String remarks,List<Attach> attachs) {
		this.attachGroupId = attachGroupId;
		this.submitDate = submitDate;
		this.status = status;
		this.remarks = remarks;
		this.attachs = attachs;
	}

	public int getAttachGroupId() {
		return this.attachGroupId;
	}

	public void setAttachGroupId(int attachGroupId) {
		this.attachGroupId = attachGroupId;
	}

	public Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public List<Attach> getAttachs() {
		return this.attachs;
	}

	public void setAttachs(List<Attach> attachs) {
		this.attachs = attachs;
	}

}