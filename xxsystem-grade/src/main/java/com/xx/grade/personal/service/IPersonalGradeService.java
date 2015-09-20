package com.xx.grade.personal.service;

import java.util.Map;

import com.xx.grade.personal.vo.PersonalGradeVo;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;

/**
 * 个人评分服务
 * 
 * @author wujialing
 */
public interface IPersonalGradeService {
	
	/**
	 * 获取个人评分列表
	 * 
	 * @param paramMap
	 * @return
	 * @throws BusinessException
	 */
	public ListVo<PersonalGradeVo> getPersonalGradeList(Map<String, String> paramMap)
            throws BusinessException;
	
}
