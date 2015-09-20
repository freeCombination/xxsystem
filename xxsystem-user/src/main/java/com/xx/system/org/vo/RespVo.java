package com.xx.system.org.vo;

import java.io.Serializable;

public class RespVo implements Serializable {

	private static final long serialVersionUID = -1716356894617833578L;

	/**
     * @Fields respId : 主键
     */
    private int respId;
    
    /**
     * 岗位编号
     */
    private String number;
    
    /**
     * 岗位名称
     */
    private String name;
    
    /**
     * @Fields orgName : 所属部门名称
     */
    private String orgName;
    
    /**
     * @Fields ogId : 所属部门ID
     */
    private Integer orgId;
    
    /**
     * 岗位级别
     */
    private String rank;
    
    /**
     * @Fields enable : 是否可用：0 是 1 否
     */
    private int enable;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;

	public int getRespId() {
		return respId;
	}

	public void setRespId(int respId) {
		this.respId = respId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
}
