package com.xx.grade.personal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xx.grade.personal.entity.PersonalDuty;
import com.xx.grade.personal.entity.PersonalGrade;
import com.xx.grade.personal.service.IPersonalGradeService;
import com.xx.grade.personal.vo.PersonalDutyVo;
import com.xx.grade.personal.vo.PersonalGradeVo;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;


/**
 * 个人评分服务实现类
 * 
 * @author wujialing
 *
 */
@Service("personalGradeService")
public class PersonalGradeServiceImpl implements IPersonalGradeService {
	
	@Autowired
	@Qualifier("baseDao")
	private IBaseDao baseDao ;

	@Override
	public ListVo<PersonalGradeVo> getPersonalGradeList(Map<String, String> paramMap) throws BusinessException {
		ListVo<PersonalGradeVo> result = new ListVo();
		List<PersonalGradeVo> list = new ArrayList<PersonalGradeVo>(); 
		int totalSize = 0 ;
        int start = NumberUtils.toInt(paramMap.get("start"));
        int limit = NumberUtils.toInt(paramMap.get("limit"));
		//用户ID 用户自评只能看自己的数据 
		String userId = paramMap.get("userId");
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		hql.append(" From PersonalGrade pg where 1=1 and pg.isDelete = 0 ");
		counthql.append(" select count(*) From PersonalGrade pg where 1=1 and pg.isDelete = 0 ");
		if (StringUtil.isNotEmpty(userId)) {
			hql.append(" and pg.user.userId = " + Integer.parseInt(userId));
			counthql.append(" and pg.user.userId = " + Integer.parseInt(userId));
		}
		totalSize =  baseDao.getTotalCount(counthql.toString(), new HashMap<String, Object>());
		List<PersonalGrade> personalGradeLists =  (List<PersonalGrade>)baseDao.queryEntitysByPage(start, limit, hql.toString(),new HashMap<String, Object>());
		for (PersonalGrade grade : personalGradeLists) {
			PersonalGradeVo vo = new PersonalGradeVo();
			buildEntityToVo(grade,vo);
			list.add(vo);
		}
		result.setList(list);
		result.setTotalSize(totalSize);
		return result;
	}

	/**
	 * 个人评分实体转vo
	 * 
	 * @param grade
	 * @param vo
	 */
	private void buildEntityToVo(PersonalGrade grade, PersonalGradeVo vo) {
		vo.setId(grade.getId());
		vo.setTitle(grade.getTitle());
		if (grade.getCompositeScores() != null) {
			vo.setCompositeScores(grade.getCompositeScores());
		}
		vo.setGradeYear(grade.getGradeYear());
		vo.setProblem(grade.getProblem());
		vo.setStatus(grade.getStatus());
		if (grade.getUser() != null) {
			vo.setUserId(grade.getUser().getUserId());
			vo.setUserName(grade.getUser().getRealname());
		}
		vo.setWorkPlan(grade.getWorkPlan());
	}

	@Override
	public PersonalGradeVo getPersonalGradeById(int id) throws BusinessException {
		PersonalGrade grade = (PersonalGrade)baseDao.queryEntityById(PersonalGrade.class, id);
		PersonalGradeVo vo = new PersonalGradeVo();
		buildEntityToVo(grade, vo);
		return vo;
	}

	@Override
	public void editPersonalGrade(PersonalGrade grade) throws BusinessException {
		baseDao.updateEntity(grade);
	}

	@Override
	public PersonalGrade getPersonalGradeEntityById(int id) throws BusinessException {
		PersonalGrade grade = (PersonalGrade)baseDao.queryEntityById(PersonalGrade.class, id);
		return grade ;
	}

	@Override
	public ListVo<PersonalDutyVo> getPersonalDutyList(Map<String, String> paramMap) throws BusinessException {
		ListVo<PersonalDutyVo> result = new ListVo();
		List<PersonalDutyVo> list = new ArrayList<PersonalDutyVo>(); 
		//用户ID 用户自评只能看自己的数据 
		String personalGradeId = paramMap.get("personalGradeId");
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		hql.append(" From PersonalDuty pg where 1=1  ");
		if (StringUtil.isNotEmpty(personalGradeId)) {
			hql.append(" and pg.personalGrade.id = " + Integer.parseInt(personalGradeId));
		}else{
			
		}
		List<PersonalDuty> personalDutyLists =  (List<PersonalDuty>)baseDao.queryEntitys(hql.toString());
		for (PersonalDuty duty : personalDutyLists) {
			PersonalDutyVo vo = new PersonalDutyVo();
			buildDutyEntityToVo(duty,vo);
			list.add(vo);
		}
		result.setList(list);
		result.setTotalSize(list.size());
		return result;
	}

	/**
	 * 用户自评职责明细实体转换
	 * @param duty
	 * @param vo
	 */
	private void buildDutyEntityToVo(PersonalDuty duty, PersonalDutyVo vo) {
		vo.setId(duty.getId());
		vo.setWorkDuty(duty.getWorkDuty());
		vo.setCompletion(duty.getCompletion());
	}

	@Override
	public PersonalDuty getPersonalDutyBy(int id)
			throws BusinessException {
		PersonalDuty duty = (PersonalDuty)this.baseDao.queryEntityById(PersonalDuty.class, id);
		return duty;
	}

	@Override
	public void updatePersonalDuty(PersonalDuty duty) throws BusinessException {
		this.baseDao.saveOrUpdate(duty);
	}
}
