package com.xx.grade.personal.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xx.grade.personal.entity.IndexTypeRoleWeight;
import com.xx.grade.personal.entity.PersonalDuty;
import com.xx.grade.personal.entity.PersonalGrade;
import com.xx.grade.personal.entity.PersonalGradeDetails;
import com.xx.grade.personal.entity.PersonalGradeResult;
import com.xx.grade.personal.entity.PersonalGradeResultDetails;
import com.xx.grade.personal.entity.PersonalWeight;
import com.xx.grade.personal.service.IPersonalGradeService;
import com.xx.grade.personal.service.IPersonalWeightService;
import com.xx.grade.personal.vo.PersonalDutyVo;
import com.xx.grade.personal.vo.PersonalGradeResultDetailsVo;
import com.xx.grade.personal.vo.PersonalGradeResultVo;
import com.xx.grade.personal.vo.PersonalGradeVo;
import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.DateUtil;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.service.IDictService;
import com.xx.system.org.entity.Duty;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.role.entity.Role;
import com.xx.system.user.entity.User;
import com.xx.system.user.util.HSSFUtils;


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
	
	@Autowired
	@Qualifier("dictService")
	private IDictService dictService ;
	
	@Autowired
	@Qualifier("personalWeightService")
	private IPersonalWeightService personalWeightService;

	@Override
	public ListVo<PersonalGradeVo> getPersonalGradeList(Map<String, String> paramMap) throws BusinessException {
		ListVo<PersonalGradeVo> result = new ListVo<PersonalGradeVo>();
		List<PersonalGradeVo> list = new ArrayList<PersonalGradeVo>(); 
		int totalSize = 0 ;
        int start = NumberUtils.toInt(paramMap.get("start"));
        int limit = NumberUtils.toInt(paramMap.get("limit"));
		//用户ID 用户自评只能看自己的数据 
		String userId = paramMap.get("userId");
		//年份
		String gradeYear = paramMap.get("gradeYear");
		//状态
		String status = paramMap.get("status");
		//人员姓名
		String inputGradeUser = paramMap.get("inputGradeUser");
		//部门
		String canpDeptQuery = paramMap.get("canpDeptQuery");
		
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		hql.append(" From PersonalGrade pg where 1=1 and pg.isDelete = 0 ");
		counthql.append(" select count(*) From PersonalGrade pg where 1=1 and pg.isDelete = 0 ");
		if (StringUtil.isNotEmpty(userId)) {
			hql.append(" and pg.user.userId = " + Integer.parseInt(userId));
			counthql.append(" and pg.user.userId = " + Integer.parseInt(userId));
		}
		if (StringUtil.isNotEmpty(gradeYear)) {
			hql.append(" and pg.gradeYear = '" + gradeYear+"'");
			counthql.append(" and pg.gradeYear = " + gradeYear+"'");
		}
		if (StringUtil.isNotEmpty(status)) {
			hql.append(" and pg.status = " + status);
			counthql.append(" and pg.status = " + status);
		}
		
		if (StringUtil.isNotEmpty(inputGradeUser)) {
			hql.append(" and pg.user.realname like '%"+inputGradeUser+"%'");
			counthql.append(" and pg.user.realname like '%"+inputGradeUser+"%'");
		}
		
		if (StringUtil.isNotEmpty(canpDeptQuery) && !"0".equals(canpDeptQuery)) {
			String userIds = getAllUserIdsByOrgId(canpDeptQuery);
			//找到该部门下所有人员，如果人员为空，则没有数据
			if (StringUtil.isNotEmpty(userIds)) {
				hql.append(" and pg.user.userId in ("+userIds+")");
				counthql.append(" and pg.user.userId in ("+userIds+")");
			}else{
				hql.append(" and 1=0 ");
				counthql.append(" and 1=0 ");
			}
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
			vo.setCompositeScores(String.valueOf(grade.getCompositeScores()));
		}
		vo.setGradeYear(grade.getGradeYear());
		vo.setProblem(grade.getProblem());
		vo.setStatus(grade.getStatus());
		if (grade.getUser() != null) {
			vo.setUserId(grade.getUser().getUserId());
			vo.setUserName(grade.getUser().getRealname());
			if (grade.getUser().getResponsibilities() != null) {
				vo.setResponsibilities(grade.getUser().getResponsibilities().getName());
			}
			if (grade.getUser().getOrgUsers() != null) {
				for (OrgUser orguser : grade.getUser().getOrgUsers()) {
					vo.setOrgName(orguser.getOrganization().getOrgName());
					break;
				}
			}
		}
		vo.setTotalPersonCount(getResultCounts(null, grade.getId()));
		vo.setCommitPersonCount(getResultCounts(1, grade.getId()));
		vo.setWorkPlan(grade.getWorkPlan());
	}
	
	/**
	 * 通过个人评分获取该评分总数(status传null获取全部，传1获取提交人数，传0获取未提交reshuffle)
	 * 
	 * @param status
	 * @param personalGradeId
	 * @return
	 */
	private int getResultCounts(Integer status,int personalGradeId){
		int count = 0 ;
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) from PersonalGradeResult r where r.personalGrade.id = "+personalGradeId);
		if (status != null) {
			hql.append(" and r.state = "+status);
		}
		count = baseDao.getTotalCount(hql.toString(), new HashMap<String, Object>());
		return count ;
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
	public ListVo<PersonalDutyVo> getPersonalDutyList(Map<String, String> paramMap) {
		ListVo<PersonalDutyVo> result = new ListVo<PersonalDutyVo>();
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

	@Override
	public String submitPersonalGrade(String ids) {
		try {
			if (StringUtil.isNotEmpty(ids)) {
				StringBuffer sql = new StringBuffer();
				sql.append(" update T_PERSONAL_GRADE t set t.STATUS = 1 ");
				sql.append(" where t.id in ('").append(ids).append("')");
				this.baseDao.executeNativeSQL(sql.toString());
			}
			return "{success:true,msg:'提交个人评分成功！'}";
		} catch (Exception e) {
			return "{success:false,msg:'提交个人评分失败！'}";
		}
	}

	@Override
	public HSSFWorkbook exportPersonalDuty(Map<String, String> dutyMap) {
		ListVo<PersonalDutyVo> result =	getPersonalDutyList(dutyMap);
		HSSFWorkbook wb = null;
		if (result.getTotalSize() > 0) {
			wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("个人职责明细");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = null;
			//创建头部
			cell = row.createCell(0);
			cell.setCellValue("主键");
			
			cell = row.createCell(1);
			cell.setCellValue("工作职责");
			
			cell = row.createCell(2);
			cell.setCellValue("完成情况");
			
			List<PersonalDutyVo> list = result.getList();
			for (int i = 0; i < list.size(); i++) {
				PersonalDutyVo vo = list.get(i);
				row = sheet.createRow(i + 1);
				//id
				cell = row.createCell(0);
				cell.setCellValue(String.valueOf(vo.getId()));
				
				//工作职责
				cell = row.createCell(1);
				cell.setCellValue(String.valueOf(vo.getWorkDuty()));
				
				//完成情况
				cell = row.createCell(2);
				cell.setCellValue(String.valueOf(vo.getCompletion()));
			}
		}
		return wb;
	}

	@Override
	public String uploadPersonalDutyExcel(String fileUrl,
			Map<String, String> paramsMap) {
	       // 年月
        String reportDate = paramsMap.get("reportDate");
        // 标示
        String message = "importSuccess";
        String[][] content = null;
        try {
            content = HSSFUtils.extractTextFromExcel(fileUrl);
        }
        catch (Exception e) {
        	message = "解析excel出错！";
        }
        
        if (null == content) {
            message = "不是有效的Excel文件,请按照模版来定义！";
        }
        else {
            File attachFile = new File(fileUrl);
            attachFile.delete();
            int col = content.length;
            List<PersonalDuty> duties = new ArrayList<PersonalDuty>();
            for (int i = 1; i < col; i++) {
            	String id = content[i][0];
            	PersonalDuty duty = (PersonalDuty)this.baseDao.queryEntityById(PersonalDuty.class, Integer.parseInt(id));
            	if (duty != null) {
                	String workDuty = content[i][1];
                	String completion = content[i][2];
                	duty.setCompletion(completion);
				}
            }
            this.baseDao.saveOrUpdate(duties);
        }
        return message ;
	}

	/**
	 * 生成个人评分结果表 20151028改版
	 */
	public void generatePersonalGradeResult(String ids, User curUser) {
		if (StringUtil.isNotEmpty(ids) && curUser != null) {
			StringBuffer hql = new StringBuffer();
			hql.append(" From PersonalGrade pg where pg.status = 1 ");
			hql.append(" and pg.id in ('"+ids+"')");
			List<PersonalGrade> grades = baseDao.queryEntitys(hql.toString());
			
			//获取当前登录人部门
			Set<OrgUser> currentOrgs = curUser.getOrgUsers();
			Organization currentOrg = null ;
			for (OrgUser orgUser : currentOrgs) {
				if (orgUser.getOrganization() != null) {
					currentOrg = orgUser.getOrganization();
					break;
				}
			}
			
			for (PersonalGrade grade : grades) {
				Dictionary classification = grade.getClassification();
				//获取该权重分类下的所有权重维护
				List<PersonalWeight> pws = personalWeightService.getPersonalWeightByClassification(classification.getPkDictionaryId());
				
				//生成指标评分明细历史
				for (PersonalWeight pw : pws) {
					PersonalGradeDetails gradeDetail = getGradeDetailsByGrade(grade,pw.getIndexType());
					if (gradeDetail == null) {
						gradeDetail = new PersonalGradeDetails();
						gradeDetail.setPersonalGrade(grade);
						gradeDetail.setIndexType(pw.getIndexType());
						gradeDetail.setPercentage(pw.getPercentage());
						gradeDetail.setGrade(pw.isGrade());
						baseDao.save(gradeDetail);
					}
				}
				
				for (PersonalWeight pw : pws) {
					//对于参与评分的指标
					if (pw.isGrade()) {
						//获取该指标下所有角色权重
						Set<IndexTypeRoleWeight> rws = pw.getIndexTypeRoles();
						Iterator<IndexTypeRoleWeight> it = rws.iterator();
						while (it.hasNext()) {
							IndexTypeRoleWeight rw  = it.next();
							Role role = rw.getRole();
							List<User> users = getUsersExcludeSelf(role,grade.getUser().getUserId(),currentOrg);
							for (User user : users) {
								PersonalGradeResult result = getResultByUserAndGrade(user,grade);
								if (result == null) {
									result = new PersonalGradeResult();
									result.setPersonalGrade(grade);
									result.setGradeUser(user);
									result.setState(0);
									result.setGradeUserType(0);
									baseDao.save(result);
								}
								PersonalGradeResultDetails detail = getDetailsByRoleAndIndexAndResult(role,pw.getIndexType(),result);
								if (detail == null) {
									detail = new PersonalGradeResultDetails();
									detail.setPersonalGradeResult(result);
									detail.setIndexType(pw.getIndexType());
									detail.setRole(role);
									detail.setPercentage(rw.getPercentage());
									baseDao.save(detail);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取个人评分指标历史
	 * 
	 * @param grade
	 * @param indexType
	 * @return
	 */
	private PersonalGradeDetails getGradeDetailsByGrade(PersonalGrade grade, Dictionary indexType) {
		PersonalGradeDetails gradeDetail = null ;
		StringBuffer hql = new StringBuffer();
		hql.append(" From PersonalGradeDetails pd where pd.personalGrade.id="+grade.getId());
		hql.append(" and pd.indexType.pkDictionaryId="+indexType.getPkDictionaryId());
		List<PersonalGradeDetails> gradeDetails = baseDao.queryEntitys(hql.toString());
		if (gradeDetails != null && gradeDetails.size()>0) {
			gradeDetail = gradeDetails.get(0);
		}
		return gradeDetail;
	}

	/**
	 * 通过角色 指标 结果表查找详情明细表
	 * 
	 * @param role
	 * @param indexType
	 * @param result
	 * @return
	 */
	private PersonalGradeResultDetails getDetailsByRoleAndIndexAndResult(Role role, Dictionary indexType,
			PersonalGradeResult result) {
		PersonalGradeResultDetails detail = null ;
		StringBuffer hql = new StringBuffer();
		hql.append(" From PersonalGradeResultDetails d where d.personalGradeResult.id ="+result.getId()
			+" and d.indexType.pkDictionaryId="+indexType.getPkDictionaryId()+" and d.role.roleId="+role.getRoleId());
		List<PersonalGradeResultDetails> details = baseDao.queryEntitys(hql.toString());
		if (details != null && details.size() >0) {
			detail = details.get(0);
		}
		return detail;
	}

	/**
	 * 通过个人得分与评分人找结果
	 * 
	 * @param user
	 * @param grade
	 * @return
	 */
	private PersonalGradeResult getResultByUserAndGrade(User user, PersonalGrade grade) {
		PersonalGradeResult result = null ;
		StringBuffer hql = new StringBuffer();
		hql.append(" From PersonalGradeResult r where r.personalGrade.id ="+grade.getId()+" and r.gradeUser.userId="+user.getUserId());
		List<PersonalGradeResult> results = baseDao.queryEntitys(hql.toString());
		if (results != null && results.size() >0) {
			result = results.get(0);
		}
		return result;
	}

	/**
	 * 查找角色对应的人员，极为重要的方法，此方法决定了评分人
	 * 
	 * @param role
	 * @param userId
	 * @param currentOrg
	 * @return
	 */
	private List<User> getUsersExcludeSelf(Role role, Integer userId, Organization currentOrg) {
		List<User> users = null ;
		StringBuffer hql = new StringBuffer();
		//如果是一般员工角色，则需要排除自己
		if (role.getRoleName().equals("一般员工")) {
			hql.append(" select rs.user From RoleMemberScope rs where rs.role.roleId ="+role.getRoleId()+" and rs.user.userId <> " + userId);
		}//如果是与部门挂钩的角色，则需要找到对应角色范围属于该部门的员工
		else if(role.getRoleName().equals("部门主任")
				|| role.getRoleName().equals("部门副主任")
				|| role.getRoleName().equals("分管领导")
				|| role.getRoleName().equals("分管副所长")
				|| role.getRoleName().equals("部门负责人")){
			hql.append(" select sm.roleMemberScope.user From ScopeMember sm where sm.org.orgId = "+currentOrg.getOrgId()+" and sm.roleMemberScope.role.roleId = "+role.getRoleId());
		}//其他角色，直接取对于角色下的所有人
		else
		{
			hql.append(" select rs.user From RoleMemberScope rs where rs.role.roleId ="+role.getRoleId());
		}
		users = baseDao.queryEntitys(hql.toString());
		return users;
	}

	/**
	 * 生成个人评分结果表（已停用）
	 * 
	 * @param ids
	 * @param curUser
	 */
	public void generatePersonalGradeResultOld(String ids, User curUser) {
		if (StringUtil.isNotEmpty(ids) && curUser != null) {
			StringBuffer hql = new StringBuffer();
			hql.append(" From PersonalGrade pg where pg.status = 1 ");
			hql.append(" and pg.id in ('"+ids+"')");
			List<PersonalGrade> grades = baseDao.queryEntitys(hql.toString());
			
			//获取当前登录人部门
			Set<OrgUser> currentOrgs = curUser.getOrgUsers();
			Organization currentOrg = null ;
			for (OrgUser orgUser : currentOrgs) {
				if (orgUser.getOrganization() != null) {
					currentOrg = orgUser.getOrganization();
					break;
				}
			}
			
			//获取该人员组织下所有人员（排除领导和自己）
			List<User> resultUser = getResultUserByCurrentOrg(curUser);
			List<PersonalGradeResult> gradeResults = new ArrayList<PersonalGradeResult>();
			for (PersonalGrade grade : grades) {
				//如果是部门领导（主任），则评分人为其他三位领导，否则为部门其他人和四位领导评分
				if (currentOrg != null 
						&& currentOrg.getDeptHead() != null
						&& currentOrg.getDeptHead().getUserId() == curUser.getUserId()) {
					
				}else{
					for (User user : resultUser) {
						PersonalGradeResult result = new PersonalGradeResult();
						result.setPersonalGrade(grade);
						result.setGradeUser(user);
						result.setState(0);
						result.setGradeUserType(0);
						gradeResults.add(result);
					}
					//添加部门领导 分管领导 协管领导 所长
					//添加部门领导
					if (currentOrg.getDeptHead() != null) {
						PersonalGradeResult result = new PersonalGradeResult();
						result.setPersonalGrade(grade);
						result.setGradeUser(currentOrg.getDeptHead());
						result.setState(0);
						result.setGradeUserType(1);
						gradeResults.add(result);
					}
				}
				//分管领导
				if (currentOrg.getBranchedLeader() != null) {
					PersonalGradeResult result = new PersonalGradeResult();
					result.setPersonalGrade(grade);
					result.setGradeUser(currentOrg.getBranchedLeader());
					result.setState(0);
					result.setGradeUserType(2);
					gradeResults.add(result);
				}
				//协管领导
				if (currentOrg.getOtherSup() != null) {
					PersonalGradeResult result = new PersonalGradeResult();
					result.setPersonalGrade(grade);
					result.setGradeUser(currentOrg.getOtherSup());
					result.setState(0);
					result.setGradeUserType(3);
					gradeResults.add(result);
				}
				//所领导
				if (currentOrg.getSuperintendent() != null) {
					PersonalGradeResult result = new PersonalGradeResult();
					result.setPersonalGrade(grade);
					result.setGradeUser(currentOrg.getSuperintendent());
					result.setState(0);
					result.setGradeUserType(4);
					gradeResults.add(result);
				}
			}
			//如果有数据 批量保存
			if (gradeResults != null && gradeResults.size()>0) {
				baseDao.saveOrUpdate(gradeResults);
			}
		}
	}

	/**
	 * 获取当前组织下所有人员及所有上级组织领导
	 * 
	 * @param currentOrg
	 * @param curUser
	 * @return
	 */
	private List<User> getResultUserByCurrentOrg(User curUser) {
		String userId = "" ;
		//获取当前组织下所有人员
		
		Set<OrgUser> currentOrgs = curUser.getOrgUsers();
		Organization currentOrg = null ;
		for (OrgUser orgUser : currentOrgs) {
			if (orgUser.getOrganization() != null) {
				currentOrg = orgUser.getOrganization();
				break;
			}
		}
		
		StringBuffer OrgUserhql = new StringBuffer();
		OrgUserhql.append(" From OrgUser ou where ou.isDelete = 0 and ou.organization.orgId ="+currentOrg.getOrgId());
		OrgUserhql.append(" and ou.user.userId <> "+curUser.getUserId());
		//排除领导
		if (currentOrg.getDeptHead() != null) {
			OrgUserhql.append(" and ou.user.userId <> "+currentOrg.getDeptHead().getUserId());
		}
		if (currentOrg.getBranchedLeader() != null) {
			OrgUserhql.append(" and ou.user.userId <> "+currentOrg.getBranchedLeader().getUserId());
		}
		if (currentOrg.getOtherSup() != null) {
			OrgUserhql.append(" and ou.user.userId <> "+currentOrg.getOtherSup().getUserId());
		}
		if (currentOrg.getSuperintendent() != null) {
			OrgUserhql.append(" and ou.user.userId <> "+currentOrg.getSuperintendent().getUserId());
		}
		List<OrgUser> orgUsers2 = baseDao.queryEntitys(OrgUserhql.toString());
		for (OrgUser orgUser : orgUsers2) {
			if (orgUser.getUser() !=null 
					&& orgUser.getUser().getEnable() == 1) {
				userId += ","+orgUser.getUser().getUserId();
			}
		}
		if (StringUtil.isNotEmpty(userId)) {
			userId = userId.substring(1, userId.length());
		}
		//获取所有上级组织领导 TODO
		StringBuffer hql = new StringBuffer();
		hql.append(" From User u where u.userId in ("+userId+")");
		List<User> users = baseDao.queryEntitys(hql.toString());
		return users;
	}
	
	/**
	 * 获取部门下所有的人员id集合
	 * 
	 * @param orgId
	 * @return
	 */
	private String getAllUserIdsByOrgId(String orgId){
		String userId = "" ;
		StringBuffer OrgUserhql = new StringBuffer();
		OrgUserhql.append(" From OrgUser ou where ou.isDelete = 0 and ou.organization.orgId ='"+orgId+"'");
		List<OrgUser> orgUsers2 = baseDao.queryEntitys(OrgUserhql.toString());
		for (OrgUser orgUser : orgUsers2) {
			if (orgUser.getUser() !=null 
					&& orgUser.getUser().getEnable() == 1) {
				userId += ","+orgUser.getUser().getUserId();
			}
		}
		if (StringUtil.isNotEmpty(userId)) {
			userId = userId.substring(1, userId.length());
		}
		return userId ;
	}

	@Override
	public ListVo<PersonalGradeResultVo> getPersonalGradeResultList(
			Map<String, String> paramMap) {
		ListVo<PersonalGradeResultVo> result = new ListVo<PersonalGradeResultVo>();
		List<PersonalGradeResultVo> list = new ArrayList<PersonalGradeResultVo>(); 
		int totalSize = 0 ;
        int start = NumberUtils.toInt(paramMap.get("start"));
        int limit = NumberUtils.toInt(paramMap.get("limit"));
		//用户ID 用户自评只能看自己的数据 
		String userId = paramMap.get("userId");
		String state = paramMap.get("state");
		//个人评分 对应的
		String personalGradeId = paramMap.get("personalGradeId");
		String inputGradeUser = paramMap.get("inputGradeUser");
		String inputUserName = paramMap.get("inputUserName");
		String canpDeptQuery = paramMap.get("canpDeptQuery");
		//标题
		String inputTitle = paramMap.get("inputTitle");
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		hql.append(" From PersonalGradeResult pgr where 1=1");
		counthql.append(" select count(*) From PersonalGradeResult pgr where 1=1 ");
		if (StringUtil.isNotEmpty(userId)) {
			hql.append(" and pgr.gradeUser.userId = " + Integer.parseInt(userId));
			counthql.append(" and pgr.gradeUser.userId = " + Integer.parseInt(userId));
		}
		
		if (StringUtil.isNotEmpty(state)) {
			hql.append(" and pgr.state = " + Integer.parseInt(state));
			counthql.append(" and pgr.state = " + Integer.parseInt(state));
		}
		
		if (StringUtil.isNotEmpty(inputGradeUser)) {
			hql.append(" and pgr.personalGrade.user.realname like '%"+inputGradeUser+"%'");
			counthql.append(" and pgr.personalGrade.user.realname like '%"+inputGradeUser+"%'");
		}
		
		//标题
		if (StringUtil.isNotEmpty(inputTitle)) {
			hql.append(" and pgr.personalGrade.title like '%"+inputTitle+"%'");
			counthql.append(" and pgr.personalGrade.title like '%"+inputTitle+"%'");
		}
		
		if (StringUtil.isNotEmpty(inputUserName)) {
			hql.append(" and pgr.gradeUser.realname like '%"+inputUserName+"%'");
			counthql.append(" and pgr.gradeUser.realname like '%"+inputUserName+"%'");
		}
		
		if (StringUtil.isNotEmpty(personalGradeId)) {
			hql.append(" and pgr.personalGrade.id = '"+personalGradeId+"'");
			counthql.append(" and pgr.personalGrade.id = '"+personalGradeId+"'");
		}
		
		if (StringUtil.isNotEmpty(canpDeptQuery) && !"0".equals(canpDeptQuery)) {
			String userIds = getAllUserIdsByOrgId(canpDeptQuery);
			//找到该部门下所有人员，如果人员为空，则没有数据
			if (StringUtil.isNotEmpty(userIds)) {
				hql.append(" and pgr.personalGrade.user.userId in ("+userIds+")");
				counthql.append(" and pgr.personalGrade.user.userId in ("+userIds+")");
			}else{
				hql.append(" and 1=0 ");
				counthql.append(" and 1=0 ");
			}
		}
		//评分状态排序 满足点击评分人员列表需求
		hql.append(" order by pgr.state");
		
		totalSize =  baseDao.getTotalCount(counthql.toString(), new HashMap<String, Object>());
		List<PersonalGradeResult> personalGradeResults =  (List<PersonalGradeResult>)baseDao.queryEntitysByPage(start, limit, hql.toString(),new HashMap<String, Object>());
		for (PersonalGradeResult gradeResult : personalGradeResults) {
			PersonalGradeResultVo vo = new PersonalGradeResultVo();
			buildResultEntityToVo(gradeResult,vo);
			list.add(vo);
		}
		result.setList(list);
		result.setTotalSize(totalSize);
		return result;
	}

	private void buildResultEntityToVo(PersonalGradeResult gradeResult,
			PersonalGradeResultVo vo) {
		vo.setId(gradeResult.getId());
		vo.setUserName(gradeResult.getGradeUser().getRealname());
		if (gradeResult.getGradeDate() != null) {
			vo.setGradeDate(DateUtil.dateToString(gradeResult.getGradeDate(), "yyyy-MM-dd HH:mm:ss"));
		}
		vo.setState(gradeResult.getState());
		vo.setGradeUserType(getGradeUserTypeByResult(gradeResult));
		vo.setEvaluation(gradeResult.getEvaluation());
		vo.setEvaluation1(gradeResult.getEvaluation1());
		vo.setEvaluation2(gradeResult.getEvaluation3());
		vo.setEvaluation3(gradeResult.getEvaluation3());
		if (gradeResult.getPersonalGrade() != null) {
			PersonalGrade grade = gradeResult.getPersonalGrade();
			vo.setGradeYear(grade.getGradeYear());
			vo.setPersonalGradeId(grade.getId());
			if (grade.getUser() != null) {
				User user = grade.getUser();
				vo.setGradeUser(user.getRealname());
				if (user.getResponsibilities() != null) {
					vo.setResponsibilities(user.getResponsibilities().getName());
				}
				vo.setBirthDay(user.getBirthDay());
				vo.setGender(user.getGender());
				vo.setPoliticsStatus(user.getPoliticsStatus());
				vo.setEducationBackground(user.getEducationBackground());
				if (user.getOrgUsers() != null) {
					for (OrgUser orguser : user.getOrgUsers()) {
						vo.setGradeOrg(orguser.getOrganization().getOrgName());
						break;
					}
				}
			}
			vo.setTitle(grade.getTitle());
			vo.setProblem(grade.getProblem());
			vo.setWorkPlan(grade.getWorkPlan());
		}
	}

	/**
	 * 获取当前评分人是否具有领导职位
	 */
	private String getGradeUserTypeByResult(PersonalGradeResult gradeResult) {
		String result = "" ;
		String isBmld = "false" ;
		String isfgld = "false" ;
		String isqtsld = "false" ;
		String issld = "false" ;
		Set<PersonalGradeResultDetails> details = gradeResult.getDetails();
		Iterator<PersonalGradeResultDetails> it = details.iterator();
		while (it.hasNext()) {
			PersonalGradeResultDetails detail = it.next();
			if ("部门主任".equals(detail.getRole().getRoleName())) {
				isBmld = "true" ;
			}
			if ("分管领导".equals(detail.getRole().getRoleName())) {
				isfgld = "true" ;
			}
			if ("分管副所长".equals(detail.getRole().getRoleName())) {
				isqtsld = "true" ;
			}
			if ("所长".equals(detail.getRole().getRoleName())) {
				issld = "true" ;
			}
		}
		result = isBmld + "," + isfgld + "," + isqtsld + "," + issld ;
		return result;
	}

	@Override
	public PersonalGradeResultVo getPersonalGradeResultById(int id) {
		PersonalGradeResult result = (PersonalGradeResult)baseDao.queryEntityById(PersonalGradeResult.class, id);
		PersonalGradeResultVo vo = new PersonalGradeResultVo();
		buildResultEntityToVo(result, vo);
		return vo;
	}

	@Override
	public PersonalGradeResult getPersonalGradeResultEntityById(int id) {
		PersonalGradeResult result = (PersonalGradeResult)baseDao.queryEntityById(PersonalGradeResult.class, id);
		return result ;
	}

	@Override
	public void editPersonalGradeResult(PersonalGradeResult result) {
		baseDao.updateEntity(result);
	}

	@Override
	public String submitPersonalGradeResult(String ids) {
		try {
			if (StringUtil.isNotEmpty(ids)) {
				String[] idsArr = ids.split(",");
				for (String id : idsArr) {
					PersonalGradeResult result = (PersonalGradeResult)baseDao.queryEntityById(PersonalGradeResult.class, Integer.parseInt(id));
					if (result != null) {
						result.setState(1);
						result.setGradeDate(new Date());
						baseDao.saveOrUpdate(result);
						generateCompositeScoresNew(result);
					}
				}
			}
			return "{success:true,msg:'提交成功！'}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{success:false,msg:'提交失败！'}";
		}
	}

	/**
	 * 生成个人评分综合评分，如果该职工已被所有人评完，则生成总得分；
	 * 生成规则：评分人大于3人时，去掉最高最低，然后取平均分
	 * 
	 * @param result
	 */
	private void generateCompositeScores(PersonalGradeResult result) {
		PersonalGrade grade = result.getPersonalGrade();
		if (grade != null) {
			StringBuffer hql = new StringBuffer();
			StringBuffer hqlCount = new StringBuffer();
			hql.append(" From PersonalGradeResult r where r.state=1 and r.personalGrade.id="+grade.getId());
			hql.append(" order by r.score");
			hqlCount.append(" select count(*) from PersonalGradeResult r where r.personalGrade.id= "+grade.getId());
			int totalSize = baseDao.getTotalCount(hqlCount.toString(), new HashMap<String, Object>());
			List<PersonalGradeResult> results = baseDao.queryEntitys(hql.toString());
			//判断是否已经提交完成
			if (results != null && results.size() == totalSize) {
				//如果小于3，直接求平均分
				double totalScore = 0 ;
				int userCount = 0 ;
				if (totalSize < 3) {
					for (int i = 0; i < results.size(); i++) {
						PersonalGradeResult gradeResult = results.get(i);
						totalScore += gradeResult.getScore();
					}
					userCount = totalSize ;
				}
				//如果大于等于3，则需去掉两头
				else{
					for (int i = 1; i < results.size()-1; i++) {
						PersonalGradeResult gradeResult = results.get(i);
						totalScore += gradeResult.getScore();
					}
					userCount = totalSize - 2 ;
				}
				int scale = 2; //小数点精度
				BigDecimal a = new BigDecimal(String.valueOf(totalScore));
				BigDecimal b = new BigDecimal(String.valueOf(userCount));
				BigDecimal r = a.divide(b, scale, BigDecimal.ROUND_HALF_UP);
				grade.setCompositeScores(r.doubleValue());
				grade.setStatus(2);
				baseDao.saveOrUpdate(grade);
			}
		}
	}
	
	/**
	 * 生成个人评分综合评分 新版
	 * 
	 * @param result
	 */
	private void generateCompositeScoresNew(PersonalGradeResult result) {
		PersonalGrade grade = result.getPersonalGrade();
		if (grade != null) {
			StringBuffer hql = new StringBuffer();
			StringBuffer hqlCount = new StringBuffer();
			hql.append(" From PersonalGradeResult r where r.state=1 and r.personalGrade.id="+grade.getId());
			hqlCount.append(" select count(*) from PersonalGradeResult r where r.personalGrade.id= "+grade.getId());
			int totalSize = baseDao.getTotalCount(hqlCount.toString(), new HashMap<String, Object>());
			List<PersonalGradeResult> results = baseDao.queryEntitys(hql.toString());
			//判断是否已经提交完成
			if (results != null && results.size() == totalSize) {
				Double totalScore = 0d ;
				Set<PersonalGradeDetails> gradeDetails = grade.getDetails();
				Iterator<PersonalGradeDetails> it = gradeDetails.iterator();
				while (it.hasNext()) {
					PersonalGradeDetails gradeDetail = it.next();
					Double indexTypeTotal = getIndexTypeTotal(grade,gradeDetail);
					if (indexTypeTotal != null && StringUtil.isNotEmpty(gradeDetail.getPercentage())) {
						totalScore += (new BigDecimal(indexTypeTotal).multiply(new BigDecimal(gradeDetail.getPercentage()))).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					gradeDetail.setScore(indexTypeTotal);
					baseDao.saveOrUpdate(gradeDetail);
				}
				grade.setCompositeScores(totalScore);
				grade.setStatus(2);
				baseDao.saveOrUpdate(grade);
			}
		}
	}

	private Double getIndexTypeTotal(PersonalGrade grade, PersonalGradeDetails gradeDetail) {
		StringBuffer sql = new StringBuffer();
		Double result = 0d ;
		sql.append(" select d.FK_ROLE_ID ,d.FK_INDEX_TYPE ,d.PERCENTAGE, ");
		sql.append(" CASE count(d.ID) > 3 WHEN TRUE then (SUM(d.score)-MAX(d.score)-MIN(d.score))/(count(d.ID)-2) ELSE SUM(d.score)/count(d.ID) END as roleScore ");
		sql.append(" from T_PERSONAL_GRADE_RESULT_DETAILS d ");
		sql.append(" INNER JOIN T_PERSONAL_GRADE_RESULT r on r.ID = d.PERSONAL_GRADE_RESULT_ID ");
		sql.append(" INNER JOIN T_PERSONAL_GRADE g on g.ID = r.PERSONAL_GRADE_ID ");
		sql.append(" where 1=1  ");
		sql.append(" AND d.FK_INDEX_TYPE = "+gradeDetail.getIndexType().getPkDictionaryId());
		sql.append(" and g.ID = "+grade.getId());
		sql.append(" GROUP BY d.FK_ROLE_ID ,d.FK_INDEX_TYPE,d.PERCENTAGE ");
		List<Map> maps = baseDao.querySQLForMap(sql.toString());
		for (Map map : maps) {
			String percentage = (String) map.get("PERCENTAGE");
			Double roleScore = (Double) map.get("roleScore");
			if (StringUtil.isNotEmpty(percentage) && roleScore != null) {
				result += (new BigDecimal(percentage).multiply(new BigDecimal(roleScore))).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).doubleValue();
			}
		}
		System.err.println(result);
		//如果是不参与个人评分的就是组织 TODO
		if (!gradeDetail.isGrade()) {
			result = 80d ;
		}
		return result;
	}

	@Override
	public String generatePersonalGrade(String gradeYear, User currentUser) {
		String result = "{success:true,msg:'生成个人评分成功！'}";
		try {
			StringBuffer hql = new StringBuffer();
			hql.append(" From PersonalGrade where gradeYear = '"+gradeYear+"'");
			hql.append(" and user.userId="+currentUser.getUserId());
			List<PersonalGrade> grades = baseDao.queryEntitys(hql.toString());
			if (grades != null && grades.size()>0) {
				result = "{success:false,msg:'生成个人评分失败，已存在数据！'}";
			}else{
				//查找当前人个人评分角色
				List<Role> roles = getRoleListByCurrentUser(currentUser);
				if (roles != null) {
					//获取个人评分人所属类型
					Dictionary classification  = getClassification(roles);
					if (classification != null) {
						PersonalGrade grade = new PersonalGrade();
						grade.setTitle(currentUser.getRealname()+gradeYear+"年个人评分表");
						grade.setUser(currentUser);
						grade.setIsDelete(0);
						grade.setStatus(0);
						grade.setGradeYear(gradeYear);
						grade.setClassification(classification);
						baseDao.save(grade);
						//生成职责表
						if (currentUser.getResponsibilities() != null) {
							StringBuffer hqlDuty = new StringBuffer();
							hqlDuty.append(" From Duty where responsibilities.pkRespId = "+currentUser.getResponsibilities().getPkRespId());
							List<Duty> duties = baseDao.queryEntitys(hqlDuty.toString());
							for (Duty duty : duties) {
								PersonalDuty personalDuty = new PersonalDuty();
								personalDuty.setWorkDuty(duty.getDutyContent());
								personalDuty.setPersonalGrade(grade);
								baseDao.save(personalDuty);
							}
						}
					}else{
						result = "{success:false,msg:'生成个人评分失败，未配置个人评分相关角色！'}";
					}
				}else{
					result = "{success:false,msg:'该用户未配置个人评分角色，请联系管理员！'}";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,msg:'生成个人评分失败！'}";
		}
		return result;
	}

	/**
	 * 
	 * @param roles
	 * @return
	 */
	private Dictionary getClassification(List<Role> roles) {
		Dictionary classification = null ;
		boolean isYbyg = false ;//一般人员
		boolean isBmzr = false ;//部门主任
		boolean isLd = false ;//领导
		//此处先通过名称判断
		for (Role role : roles) {
			if (role.getRoleName().equals("一般员工")) {
				isYbyg = true ;
			}
			if (role.getRoleName().equals("部门主任") || role.getRoleName().equals("部门副主任")) {
				isBmzr = true ;
			}
			if (role.getRoleName().equals("总工") 
					|| role.getRoleName().equals("副总工")
					|| role.getRoleName().equals("质检中心总工")) {
				isLd = true ;
			}
		}
		if (isLd) {
			classification = dictService.getDictByTypeAndValue(Constant.GRADE_QZFL,Constant.QZFL_GSLD);
		}else if(isBmzr){
			classification = dictService.getDictByTypeAndValue(Constant.GRADE_QZFL,Constant.QZFL_BMLD);
		}else if(isYbyg){
			classification = dictService.getDictByTypeAndValue(Constant.GRADE_QZFL,Constant.QZFL_YBYG);
		}
		return classification;
	}

	/**
	 * 获取当前登录人个人评分角色列表
	 * 
	 * @param currentUser
	 * @return
	 */
	private List<Role> getRoleListByCurrentUser(User currentUser) {
		StringBuffer hql = new StringBuffer();
		String roleIds = getRoleIdsByCurrentUser(currentUser);
		hql.append(" From Role r where isDelete = 0 and r.roleType.dictCode ='"+Constant.ROLE_GRPF+"' and r.roleId in ("+roleIds+")");
		List<Role> result = baseDao.queryEntitys(hql.toString());
		return result;
	}

	/**
	 * 获取用户所有角色id集合
	 * 
	 * @param currentUser
	 * @return
	 */
	private String getRoleIdsByCurrentUser(User currentUser) {
		String ids = "" ;
		StringBuffer sql = new StringBuffer();
		sql.append(" select DISTINCT t.ROLE_ID  from T_ROLE_MEMBER_SCOPE t where t.USER_ID = "+currentUser.getUserId());
		List<Map> roleIds = baseDao.querySQLForMap(sql.toString());
		for (Map map : roleIds) {
			ids += ",'" + map.get("ROLE_ID") + "'";
		}
		if (StringUtil.isNotEmpty(ids)) {
			ids = ids.substring(1,ids.length());
		}
		return ids;
	}

	@Override
	public HSSFWorkbook exportPersonalGradeAll(Map<String, String> paramMap,File file) {
		HSSFWorkbook wb = null;
		try {
			String personalGradeId = paramMap.get("personalGradeId");
			PersonalGrade grade = (PersonalGrade)baseDao.queryEntityById(PersonalGrade.class, Integer.parseInt(personalGradeId));
			//如果为空不进行导出
			if (grade == null) {
				return null;
			}
			//获取职责 职责需要插入到中间行
			Set<PersonalDuty> personalDutys = grade.getPersonalDutys();
			//获取评价
			Map<String, String> evaluationMaps = getEvaluationMaps(grade);
			//分别为部门主任，分管领导，其他所领导，所领导评价
			String evaluation = evaluationMaps.get("evaluation") ;
			String evaluation1 = evaluationMaps.get("evaluation1") ;
			String evaluation2 = evaluationMaps.get("evaluation2") ;
			String evaluation3 = evaluationMaps.get("evaluation3") ;
			
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
	        //读取excel模板  
	        wb = new HSSFWorkbook(fs);
	        HSSFSheet aSheet = wb.getSheetAt(0);
	        
	        //插入职员个人信息
	        HSSFRow row1 = aSheet.getRow(1);
	        HSSFRow row2 = aSheet.getRow(2);
	        HSSFRow row3 = aSheet.getRow(3);
	        HSSFCell cell11 = row1.getCell(1);
	        HSSFCell cell13 = row1.getCell(3);
	        HSSFCell cell15 = row1.getCell(5);
	        
	        HSSFCell cell21 = row2.getCell(1);
	        HSSFCell cell23 = row2.getCell(3);
	        HSSFCell cell25 = row2.getCell(5);
	        
	        HSSFCell cell31 = row3.getCell(1);
	        HSSFCell cell33 = row3.getCell(3);
	        
	        if (grade.getUser() != null) {
		        cell11.setCellValue(grade.getUser().getRealname());
		        cell13.setCellValue(grade.getUser().getGender());
		        cell15.setCellValue(grade.getUser().getBirthDay());
		        cell21.setCellValue(grade.getUser().getPoliticsStatus());
		        cell23.setCellValue(grade.getUser().getEducationBackground());
		        cell25.setCellValue(grade.getUser().getJobStartDate());
				if (grade.getUser().getResponsibilities() != null) {
					cell31.setCellValue(grade.getUser().getResponsibilities().getName());
				}
				//现任岗位时间
		        cell33.setCellValue(grade.getUser().getOfficeHoldingDate());
			}
	        
	        HSSFCellStyle cellStyle = wb.createCellStyle();     
	        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
	        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
	        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
	        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
	        
	        //获取单元格格式
	        HSSFCellStyle style1=aSheet.getRow(5).getCell(0).getCellStyle();
	        HSSFCellStyle style2=aSheet.getRow(5).getCell(1).getCellStyle();
	        int newRow=6; //从第几行开始插入
	        int rows = personalDutys.size();//设定插入几行 
	        if (personalDutys != null && personalDutys.size() > 0) {
		        aSheet.shiftRows(newRow, aSheet.getLastRowNum(), rows,true ,true); 
		        int rowSize = 0 ;
		        for (PersonalDuty duty : personalDutys) {
		        	HSSFRow sourceRow =aSheet.getRow(newRow+rowSize); 
			        sourceRow.setHeight((short)400);
			        //合并 单元格 操作* 第一个参数 0 表示 起始 行* 第二个参数 a表示 起始 列* 第三个参数 0 表示结束行* 第四个参数 b表示结束列
			        aSheet.addMergedRegion(new Region(newRow+rowSize,(short) 0, newRow+rowSize,(short) 1)); // 
			        HSSFCell cew2 =sourceRow.createCell((short) 0); 
			        cew2.setCellValue(duty.getWorkDuty()); 
			        cew2.setCellStyle(style1);

			        aSheet.addMergedRegion(new Region(newRow+rowSize,(short) 2, newRow+rowSize,(short) 5)); // 
			        HSSFCell cew3 =sourceRow.createCell((short) 2); 
			        cew3.setCellValue(duty.getCompletion()); 
			        cew3.setCellStyle(style2);
		        	rowSize++ ;
				}
			}
	        
	        //写入其他信息
	        HSSFRow row4 = aSheet.getRow(newRow+personalDutys.size()+1);
	        HSSFCell cell41 = row4.getCell(0);
	        cell41.setCellType(HSSFCellStyle.ALIGN_RIGHT);
	        cell41.setCellValue(grade.getProblem());
	        
	        HSSFRow row5 = aSheet.getRow(newRow+personalDutys.size()+3);
	        HSSFCell cell51 = row5.getCell(0);
	        cell51.setCellType(HSSFCellStyle.ALIGN_RIGHT);
	        cell51.setCellValue(grade.getWorkPlan());
	        
	        HSSFRow row6 = aSheet.getRow(newRow+personalDutys.size()+4);
	        HSSFCell cell61 = row6.getCell(1);
	        cell61.setCellType(HSSFCellStyle.ALIGN_RIGHT);
	        cell61.setCellValue(evaluation);
	        
	        HSSFRow row7 = aSheet.getRow(newRow+personalDutys.size()+7);
	        HSSFCell cell71 = row7.getCell(1);
	        cell71.setCellType(HSSFCellStyle.ALIGN_RIGHT);
	        cell71.setCellValue(evaluation1);
	        
	        HSSFRow row8 = aSheet.getRow(newRow+personalDutys.size()+10);
	        HSSFCell cell81 = row8.getCell(1);
	        cell81.setCellType(HSSFCellStyle.ALIGN_RIGHT);
	        cell81.setCellValue(evaluation2);
	        
	        HSSFRow row9 = aSheet.getRow(newRow+personalDutys.size()+13);
	        HSSFCell cell91 = row9.getCell(1);
	        cell91.setCellType(HSSFCellStyle.ALIGN_RIGHT);
	        cell91.setCellValue(evaluation3);
	        
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return wb;
	}

	/**
	 * 获取评价集合
	 * 
	 * @param grade
	 * @return
	 */
	private Map<String, String> getEvaluationMaps(PersonalGrade grade) {
		Map<String, String> result = new HashMap<String, String>();
		String evaluation = "" ;
		String evaluation1 = "" ;
		String evaluation2 = "" ;
		String evaluation3 = "" ;
		Set<PersonalGradeResult> gradeResults = grade.getResult();
		for (PersonalGradeResult personalGradeResult : gradeResults) {
			if (StringUtil.isNotEmpty(personalGradeResult.getEvaluation())) {
				evaluation +=  personalGradeResult.getEvaluation() + "(" + personalGradeResult.getGradeUser().getRealname() + ");" ;
			}
			if (StringUtil.isNotEmpty(personalGradeResult.getEvaluation1())) {
				evaluation1 +=  personalGradeResult.getEvaluation1() + "(" + personalGradeResult.getGradeUser().getRealname() + ");" ;
			}
			if (StringUtil.isNotEmpty(personalGradeResult.getEvaluation2())) {
				evaluation2 +=  personalGradeResult.getEvaluation2() + "(" + personalGradeResult.getGradeUser().getRealname() + ");" ;
			}
			if (StringUtil.isNotEmpty(personalGradeResult.getEvaluation3())) {
				evaluation3 +=  personalGradeResult.getEvaluation3() + "(" + personalGradeResult.getGradeUser().getRealname() + ");" ;
			}
		}
		result.put("evaluation", evaluation);
		result.put("evaluation1", evaluation1);
		result.put("evaluation2", evaluation2);
		result.put("evaluation3", evaluation3);
		return result;
	}

	@Override
	public ListVo<PersonalGradeResultDetailsVo> getPersonalResultDetailsList(Map<String, String> paramMap) {
		ListVo<PersonalGradeResultDetailsVo> result = new ListVo<PersonalGradeResultDetailsVo>();
		List<PersonalGradeResultDetailsVo> list = new ArrayList<PersonalGradeResultDetailsVo>(); 
		String personalGradeResultId = paramMap.get("personalGradeResultId");
		StringBuffer hql = new StringBuffer();
		hql.append(" From PersonalGradeResultDetails d where 1=1  ");
		if (StringUtil.isNotEmpty(personalGradeResultId)) {
			hql.append(" and d.personalGradeResult.id = " + Integer.parseInt(personalGradeResultId));
		}else{
			hql.append(" and 1= 0");
		}
		List<PersonalGradeResultDetails> detailsList =  (List<PersonalGradeResultDetails>)baseDao.queryEntitys(hql.toString());
		for (PersonalGradeResultDetails detail : detailsList) {
			PersonalGradeResultDetailsVo vo = new PersonalGradeResultDetailsVo();
			buildDutyDetailsToVo(detail,vo);
			list.add(vo);
		}
		result.setList(list);
		result.setTotalSize(list.size());
		return result;
	}

	/**
	 * 转化个人评分结果明细与vo
	 * 
	 * @param detail
	 * @param vo
	 */
	private void buildDutyDetailsToVo(PersonalGradeResultDetails detail, PersonalGradeResultDetailsVo vo) {
		vo.setId(detail.getId());
		vo.setScore(detail.getScore()==null ? "" : String.valueOf(detail.getScore()));
		if (detail.getIndexType() != null) {
			vo.setIndexTypeId(detail.getIndexType().getPkDictionaryId());
			vo.setIndexTypeName(detail.getIndexType().getDictionaryName());
		}
		if (detail.getRole() != null) {
			vo.setRoleId(detail.getRole().getRoleId());
			vo.setRoleName(detail.getRole().getRoleName());
		}
	}

	@Override
	public PersonalGradeResultDetails getPersonalGradeResultDetailsById(int id) {
		PersonalGradeResultDetails detail = (PersonalGradeResultDetails)baseDao.queryEntityById(PersonalGradeResultDetails.class, id);
		return detail;
	}

	@Override
	public void updatePersonalGradeResultDetails(PersonalGradeResultDetails detail) {
		baseDao.update(detail);
	}

	@Override
	public ListVo<PersonalGradeResultVo> getPersonalGradeResultDetailsList(
			Map<String, String> paramMap) {
		ListVo<PersonalGradeResultVo> result = new ListVo<PersonalGradeResultVo>();
		List<PersonalGradeResultVo> list = new ArrayList<PersonalGradeResultVo>(); 
		int totalSize = 0 ;
        int start = NumberUtils.toInt(paramMap.get("start"));
        int limit = NumberUtils.toInt(paramMap.get("limit"));
		//用户ID 用户自评只能看自己的数据 
		String userId = paramMap.get("userId");
		String state = paramMap.get("state");
		//个人评分 对应的
		String personalGradeId = paramMap.get("personalGradeId");
		String inputGradeUser = paramMap.get("inputGradeUser");
		String inputUserName = paramMap.get("inputUserName");
		String canpDeptQuery = paramMap.get("canpDeptQuery");
		//标题
		String inputTitle = paramMap.get("inputTitle");
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		hql.append(" From PersonalGradeResultDetails pgr where 1=1");
		counthql.append(" select count(*) From PersonalGradeResultDetails pgr where 1=1 ");
		if (StringUtil.isNotEmpty(userId)) {
			hql.append(" and pgr.personalGradeResult.gradeUser.userId = " + Integer.parseInt(userId));
			counthql.append(" and pgr.personalGradeResult.gradeUser.userId = " + Integer.parseInt(userId));
		}
		
		if (StringUtil.isNotEmpty(state)) {
			hql.append(" and pgr.personalGradeResult.state = " + Integer.parseInt(state));
			counthql.append(" and pgr.personalGradeResult.state = " + Integer.parseInt(state));
		}
		
		if (StringUtil.isNotEmpty(inputGradeUser)) {
			hql.append(" and pgr.personalGradeResult.personalGrade.user.realname like '%"+inputGradeUser+"%'");
			counthql.append(" and pgr.personalGradeResult.personalGrade.user.realname like '%"+inputGradeUser+"%'");
		}
		
		//标题
		if (StringUtil.isNotEmpty(inputTitle)) {
			hql.append(" and pgr.personalGradeResult.personalGrade.title like '%"+inputTitle+"%'");
			counthql.append(" and pgr.personalGradeResult.personalGrade.title like '%"+inputTitle+"%'");
		}
		
		if (StringUtil.isNotEmpty(inputUserName)) {
			hql.append(" and pgr.personalGradeResult.gradeUser.realname like '%"+inputUserName+"%'");
			counthql.append(" and pgr.personalGradeResult.gradeUser.realname like '%"+inputUserName+"%'");
		}
		
		if (StringUtil.isNotEmpty(personalGradeId)) {
			hql.append(" and pgr.personalGradeResult.personalGrade.id = '"+personalGradeId+"'");
			counthql.append(" and pgr.personalGradeResult.personalGrade.id = '"+personalGradeId+"'");
		}
		
		if (StringUtil.isNotEmpty(canpDeptQuery) && !"0".equals(canpDeptQuery)) {
			String userIds = getAllUserIdsByOrgId(canpDeptQuery);
			//找到该部门下所有人员，如果人员为空，则没有数据
			if (StringUtil.isNotEmpty(userIds)) {
				hql.append(" and pgr.personalGradeResult.personalGrade.user.userId in ("+userIds+")");
				counthql.append(" and pgr.personalGradeResult.personalGrade.user.userId in ("+userIds+")");
			}else{
				hql.append(" and 1=0 ");
				counthql.append(" and 1=0 ");
			}
		}
		//评分状态排序 满足点击评分人员列表需求
		hql.append(" order by pgr.personalGradeResult.personalGrade.id,pgr.indexType.pkDictionaryId");
		
		totalSize =  baseDao.getTotalCount(counthql.toString(), new HashMap<String, Object>());
		List<PersonalGradeResultDetails> personalGradeResults =  (List<PersonalGradeResultDetails>)baseDao.queryEntitysByPage(start, limit, hql.toString(),new HashMap<String, Object>());
		for (PersonalGradeResultDetails gradeResultDetail : personalGradeResults) {
			PersonalGradeResultVo vo = new PersonalGradeResultVo();
			buildResultDetailsEntityToVo(gradeResultDetail,vo);
			list.add(vo);
		}
		result.setList(list);
		result.setTotalSize(totalSize);
		return result;
	}

	private void buildResultDetailsEntityToVo(
			PersonalGradeResultDetails gradeResultDetail,
			PersonalGradeResultVo vo) {
		vo.setScore(gradeResultDetail.getScore());
		vo.setPercentage(gradeResultDetail.getPercentage());
		vo.setDetailsId(gradeResultDetail.getId());
		if (gradeResultDetail.getIndexType() != null) {
			vo.setIndexTypeName(gradeResultDetail.getIndexType().getDictionaryName());
		}
		if (gradeResultDetail.getRole() != null) {
			vo.setRoleName(gradeResultDetail.getRole().getRoleName());
		}
		buildResultEntityToVo(gradeResultDetail.getPersonalGradeResult(), vo);
		vo.setId(gradeResultDetail.getId());
	}
}
