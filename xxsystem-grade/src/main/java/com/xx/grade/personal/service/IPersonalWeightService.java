package com.xx.grade.personal.service;

import java.util.Map;

import com.xx.grade.personal.vo.PersonalWeightVo;
import com.xx.system.common.vo.ListVo;

/**
 * 个人评分权重维护接口服务
 * 
 * @author wujialing
 */
public interface IPersonalWeightService {

	/**
	 * 获取个人评分权重列表
	 * 
	 * @param paramMap
	 * @return
	 */
	ListVo<PersonalWeightVo> getPersonalWeightList(Map<String, String> paramMap);

	/**
	 * 添加个人评分权重
	 * 
	 * @param personalWeightVo
	 */
	void addPersonalWeigh(PersonalWeightVo personalWeightVo);

	/**
	 * 修改个人评分权重
	 * 
	 * @param personalWeightVo
	 */
	void updatePersonalWeigh(PersonalWeightVo personalWeightVo);

	/**
	 * 删除个人评分权重
	 * 
	 * @param ids
	 */
	void deletePersonalWeigh(String ids);

}
