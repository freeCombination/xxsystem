package com.xx.grade.personal.vo;

/**
 * 个人评分职责明细vo
 * 
 * @author wujialing
 *
 */
public class PersonalDutyVo {
	
	/**
	 * 主键 id
	 */
	private Integer id;
	
	/**
	 * 工作职责
	 */
	private String workDuty ;
	
	/**
	 * 完成情况
	 */
	private String completion ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorkDuty() {
		return workDuty;
	}

	public void setWorkDuty(String workDuty) {
		this.workDuty = workDuty;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

}
