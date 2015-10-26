package com.xx.grade.personal.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人评分权重vo
 * 
 * @author wujialing
 *
 */
public class PersonalWeightVo {
	
	/**
	 * 主键ID
	 */
	private int id; 
	
	/**
	 *  权重分类id
	 */
	private int classificationId ;
	
	/**
	 *  权重分类名称
	 */
	private String classificationName ;
	
	/**
	 * 指标类型id
	 */
	private int indexTypeId ;
	
	/**
	 * 指标类型名称
	 */
	private String indexTypeName ;
	
    /**
     * 权重
     */
    private String percentage;
    
    /**
     * 备注
     */
	private String remark ;
	
	/**
	 * 个人评分角色关联明细
	 */
	private List<IndexTypeRoleWeightVo> rwList = new ArrayList<IndexTypeRoleWeightVo>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public int getIndexTypeId() {
		return indexTypeId;
	}

	public void setIndexTypeId(int indexTypeId) {
		this.indexTypeId = indexTypeId;
	}

	public String getIndexTypeName() {
		return indexTypeName;
	}

	public void setIndexTypeName(String indexTypeName) {
		this.indexTypeName = indexTypeName;
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

	public List<IndexTypeRoleWeightVo> getRwList() {
		return rwList;
	}

	public void setRwList(List<IndexTypeRoleWeightVo> rwList) {
		this.rwList = rwList;
	}
}
