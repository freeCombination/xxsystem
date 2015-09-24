package com.xx.grade.personal.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xx.grade.personal.entity.PersonalDuty;
import com.xx.grade.personal.entity.PersonalGrade;
import com.xx.grade.personal.service.IPersonalGradeService;
import com.xx.grade.personal.vo.PersonalDutyVo;
import com.xx.grade.personal.vo.PersonalGradeVo;
import com.xx.system.common.action.BaseAction;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.ResponseVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.role.action.RoleAction;
import com.xx.system.role.entity.Role;
import com.xx.system.user.entity.User;

/**
 * 个人评分action
 * 
 * @author wujialing
 */
public class PersonalGradeAction extends BaseAction {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("personalGradeService")
	private IPersonalGradeService personalGradeService;

	/**
	 * @Fields id : ID
	 */
	private int id;

	/**
	 * 评分实体
	 */
	private PersonalGrade grade;

	/**
	 * 获取用户自评页面列表
	 * 
	 * @return
	 */
	public String getPersonalGradeForUserSelfList() {
		try {
			Map<String, String> paramMap = RequestUtil.getParameterMap(getRequest());
			User user = getCurrentUser();
			if (user != null) {
				paramMap.put("userId", user.getUserId().toString());
			}
			ListVo<PersonalGradeVo> personalGradeList = this.personalGradeService.getPersonalGradeList(paramMap);
			JsonUtil.outJson(personalGradeList);
		} catch (Exception e) {
			this.excepAndLogHandle(PersonalGradeAction.class, "获取用户自评列表失败", e, false);
		}
		return null;
	}
	
	/**
	 * 获取用户自评职责明细
	 * 
	 * @return
	 */
	public String getPersonalDutyList() {
		try {
			Map<String, String> paramMap = RequestUtil.getParameterMap(getRequest());
			ListVo<PersonalDutyVo> personalDutyList = this.personalGradeService.getPersonalDutyList(paramMap);
			JsonUtil.outJson(personalDutyList);
		} catch (Exception e) {
			this.excepAndLogHandle(PersonalGradeAction.class, "获取用户自评职责明细列表失败", e, false);
		}
		return null;
	}
	

	/**
	 * 获取个人评分实体
	 * 
	 * @return
	 */
	public String getPersonalGradeById() {
		try {
			ResponseVo rv = new ResponseVo();
			PersonalGradeVo vo = this.personalGradeService.getPersonalGradeById(id);
			rv.setData(vo);
			JsonUtil.outJson(rv);
		} catch (Exception e) {
			this.excepAndLogHandle(PersonalGradeAction.class, "根据ID获取个人评分", e, false);
		}
		return null;
	}

	/**
	 * 编辑个人评分
	 * 
	 * @return
	 */
	public String editPersonalGrade() {
		try {
			grade = parseGradeFormRequest();
			this.personalGradeService.editPersonalGrade(grade);
			JsonUtil.outJson("{success:true,msg:'修改个人评分成功！'}");
			this.excepAndLogHandle(PersonalGradeAction.class, "修改个人评分信息", null, true);
		} catch (Exception e) {
			JsonUtil.outJson("{success:false,msg:'修改个人评分失败！'}");
			this.excepAndLogHandle(PersonalGradeAction.class, "修改个人评分信息", e, false);
			return null;
		}
		return null;
	}
	
	/**
	 * 更改职责明细
	 * 
	 * @return
	 */
	public String updatePersonalDuty() {
		try {
			Map<String, String> dutyMap = RequestUtil.getParameterMap(super.getRequest());
			String id = dutyMap.get("id");
			String completion = dutyMap.get("completion");
			PersonalDuty duty = this.personalGradeService.getPersonalDutyBy(Integer.parseInt(id));
			duty.setCompletion(completion);
			this.personalGradeService.updatePersonalDuty(duty);
			JsonUtil.outJson("{success:true,msg:'修改个人评分职责明细成功！'}");
			this.excepAndLogHandle(PersonalGradeAction.class, "修改个人评分职责明细信息", null, true);
		} catch (Exception e) {
			JsonUtil.outJson("{success:false,msg:'修改个人评分职责明细信息失败！'}");
			this.excepAndLogHandle(PersonalGradeAction.class, "修改个人评分职责明细信息", e, false);
			return null;
		}
		return null;
	}
	

	/**
	 * 组装个人评分实体
	 * 
	 * @return
	 */
	private PersonalGrade parseGradeFormRequest() {
		PersonalGrade grade = null ;
		try {
			Map<String, String> gradeMap = RequestUtil.getParameterMap(super.getRequest());
			if (gradeMap.get("id") != null && StringUtil.isNotBlank(gradeMap.get("id"))) {
				grade = this.personalGradeService.getPersonalGradeEntityById(Integer.parseInt(gradeMap.get("id")));
			}
			if (null != grade) {
				grade.setWorkPlan(gradeMap.get("workPlan"));
				grade.setProblem(gradeMap.get("problem"));
			}else{
				JsonUtil.outJson("{success:'false',msg:'编辑个人评分失败，未找到该数据！'}");
			}
		} catch (Exception e) {
			JsonUtil.outJson("{success:'false',msg:'编辑个人评分失败！'}");
			this.excepAndLogHandle(PersonalGradeAction.class, "编辑个人评分失败", e, false);
		}
		return grade;
	}

	/**
	 * get && set
	 * 
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PersonalGrade getGrade() {
		return grade;
	}

	public void setGrade(PersonalGrade grade) {
		this.grade = grade;
	}

}
