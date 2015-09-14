package com.xx.system.attach.vo;

import java.util.Date;

import com.xx.system.attach.entity.Attach;

public class AttachVo implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	
	private int attachId;
	
	/**
	 * 附件名称
	 */
	private String attachName;
	/**
	 * 附件类型
	 */
	private String attachType;
	/**
	 * 附件路径
	 */
	private String attachUrl;
	
	/**
	 * 附件描述
	 */
	private String description;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 文件大小
	 */
	private String fileSize;
	
	/**
	 * 下载次数
	 */
	private int downloadTime;
	
	/**
	 * 当前状态
	 */
	private int status;
	
	/**
	 * 上传时间
	 */
	private Date submitDate;
	
	// Constructors

	/** default constructor */
	public AttachVo() {
	}
	
	public AttachVo(Attach attach) {
		this.attachId=attach.getAttachId();
		this.attachName=attach.getAttachName();
		this.attachType=attach.getAttachType();
		this.attachUrl=attach.getAttachUrl();
		this.description=attach.getDescription();
		this.downloadTime=attach.getDownloadTime();
		this.fileSize=attach.getFileSize();
		this.remarks=attach.getRemarks();
		this.submitDate=attach.getSubmitDate();
		this.status=attach.getStatus();
		
	}

	// Property accessors
	public int getAttachId() {
		return this.attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
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