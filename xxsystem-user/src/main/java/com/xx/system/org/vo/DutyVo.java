package com.xx.system.org.vo;

import java.io.Serializable;

public class DutyVo implements Serializable  {

	private static final long serialVersionUID = 6276767669405577936L;

	/**
     * @Fields dutyId : 主键
     */
    private Integer dutyId;
    
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
     * 岗位名称
     */
    private String respName;
    
    /**
     * 岗位ID
     */
    private Integer respId;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;

	public Integer getDutyId() {
		return dutyId;
	}

	public void setDutyId(Integer dutyId) {
		this.dutyId = dutyId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDutyContent() {
		return dutyContent;
	}

	public void setDutyContent(String dutyContent) {
		this.dutyContent = dutyContent;
	}

	public String getDutyType() {
		return dutyType;
	}

	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}

	public String getRespName() {
		return respName;
	}

	public void setRespName(String respName) {
		this.respName = respName;
	}

	public Integer getRespId() {
		return respId;
	}

	public void setRespId(Integer respId) {
		this.respId = respId;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
}
