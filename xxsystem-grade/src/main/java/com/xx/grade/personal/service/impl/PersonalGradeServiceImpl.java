package com.xx.grade.personal.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
			vo.setCompositeScores(String.valueOf(grade.getCompositeScores()));
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
	public ListVo<PersonalDutyVo> getPersonalDutyList(Map<String, String> paramMap) {
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
}
