package com.xx.grade.personal.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xx.grade.personal.entity.PersonalDuty;
import com.xx.grade.personal.entity.PersonalGrade;
import com.xx.grade.personal.vo.PersonalDutyVo;
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

	/**
	 * 获取个人评分实体
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public PersonalGradeVo getPersonalGradeById(int id) 
			throws BusinessException;
	
	/**
	 * 获取个人评分实体
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public PersonalGrade getPersonalGradeEntityById(int id) 
			throws BusinessException;

	/**
	 * 修改个人评分
	 * 
	 * @param grade
	 * @throws BusinessException
	 */
	public void editPersonalGrade(PersonalGrade grade)
			throws BusinessException;

	/**
	 * 获取职责明细列表
	 * 
	 * @param paramMap
	 * @return
	 * @throws BusinessException
	 */
	public ListVo<PersonalDutyVo> getPersonalDutyList(Map<String, String> paramMap);

	/**
	 * 获取职责明细实体
	 * 
	 * @param parseInt
	 * @return
	 * @throws BusinessException
	 */
	public PersonalDuty getPersonalDutyBy(int parseInt)
			throws BusinessException;

	/**
	 * 修改职责明细
	 * 
	 * @param duty
	 * @throws BusinessException
	 */
	public void updatePersonalDuty(PersonalDuty duty)
			throws BusinessException;

	/**
	 * 提交个人评分
	 * 
	 * @param ids
	 * @return
	 */
	public String submitPersonalGrade(String ids);

	/**
	 * 导出工作职责
	 * 
	 * @param dutyMap
	 * @return
	 */
	public HSSFWorkbook exportPersonalDuty(Map<String, String> dutyMap);

	/**
	 * 导入工作职责
	 * 
	 * @param fileUrl
	 * @param paramsMap
	 * @return
	 */
	public String uploadPersonalDutyExcel(String fileUrl,
			Map<String, String> paramsMap);
	
}
