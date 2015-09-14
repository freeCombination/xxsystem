package com.xx.system.attach.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TAttach entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ATTACH")
public class Attach implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTACH_GROUP_ID", nullable = false)
	private AttachGroup attachGroup;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ATTACH_ID")
	private int attachId;
	
	/**
	 * 附件名称
	 */
	@Column(name = "ATTACH_NAME", nullable = false, length = 100)
	private String attachName;
	/**
	 * 附件类型
	 */
	@Column(name = "ATTACH_TYPE", length = 100)
	private String attachType;
	/**
	 * 附件路径
	 */
	@Column(name = "ATTACH_URL", nullable = false, length = 200)
	private String attachUrl;
	
	/**
	 * 附件描述
	 */
	@Column(name = "DESCRIPTION", length = 1000)
	private String description;
	
	/**
	 * 备注
	 */
	@Column(name = "REMARKS", length = 2000)
	private String remarks;
	
	/**
	 * 文件大小
	 */
	@Column(name = "FILE_SIZE", length = 50)
	private String fileSize;
	
	/**
	 * 下载次数
	 */
	@Column(name = "DOWNLOAD_TIME")
	private int downloadTime;
	
	/**
	 * 当前状态
	 */
	@Column(name = "STATUS", nullable = false)
	private int status;
	
	/**
	 * 上传时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMIT_DATE", nullable = false)
	private Date submitDate;
	
	// Constructors

	/** default constructor */
	public Attach() {
	}

	/** minimal constructor */
	public Attach(int attachId, AttachGroup attachGroup,
			String attachName, String attachUrl, int status) {
		this.attachId = attachId;
		this.attachGroup = attachGroup;
		this.attachName = attachName;
		this.attachUrl = attachUrl;
		this.status = status;
	}

	/** full constructor */
	public Attach(int attachId, AttachGroup attachGroup,
			String attachName, String attachUrl, String attachType,
			String description, int status, String remarks) {
		this.attachId = attachId;
		this.attachGroup = attachGroup;
		this.attachName = attachName;
		this.attachUrl = attachUrl;
		this.attachType = attachType;
		this.description = description;
		this.status = status;
		this.remarks = remarks;
	}

	// Property accessors
	public int getAttachId() {
		return this.attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

	
	public AttachGroup getAttachGroup() {
		return this.attachGroup;
	}

	public void setAttachGroup(AttachGroup attachGroup) {
		this.attachGroup = attachGroup;
	}

	
	public String getAttachName() {
		return this.attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getAttachType() {
		return this.attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getAttachUrl() {
		return attachUrl;
	}

	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

	public Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public int getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(int downloadTime) {
		this.downloadTime = downloadTime;
	}
}