package com.xx.system.deptgrade.vo;

/**
 * 权重实体VO定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
public class PercentageVo implements java.io.Serializable {

	private static final long serialVersionUID = 38250158073450898L;

	/**
     * @Fields perId : 主键
     */
    private Integer perId;
    
    /**
     * 指标分类名称
     */
    private String classifyName;
    
    /**
     * 考核指标ID
     */
    private Integer classifyId;
    
    /**
     * 单据编号
     */
    private String receiptsNum;
    
    /**
     * @Fields orgName : 部门名称
     */
    private String orgName;
    
    /**
     * @Fields orgId : 部门ID
     */
    private Integer orgId;
    
    /**
     * 岗位名称
     */
    private String respName;
    
    /**
     * 岗位ID
     */
    private Integer respId;
    
    /**
     * 权重
     */
    private String percentage;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;

	public Integer getPerId() {
		return perId;
	}

	public void setPerId(Integer perId) {
		this.perId = perId;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public Integer getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(Integer classifyId) {
		this.classifyId = classifyId;
	}

	public String getReceiptsNum() {
		return receiptsNum;
	}

	public void setReceiptsNum(String receiptsNum) {
		this.receiptsNum = receiptsNum;
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

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

}