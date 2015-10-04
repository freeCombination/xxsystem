package com.xx.system.deptgrade.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.deptgrade.service.IIndexManageService;
import com.xx.system.deptgrade.vo.DeptGradeDetailVo;
import com.xx.system.deptgrade.vo.GradeIndexVo;
import com.xx.system.deptgrade.vo.IndexClassifyVo;
import com.xx.system.deptgrade.vo.PercentageVo;
import com.xx.system.org.vo.OrgVo;

/**
 * 考核指标管理Action
 * 
 * @version V1.40,2014年8月25日 上午11:49:31
 * @see [相关类/方法]
 * @since V1.40
 */
public class IndexManageAction extends BaseAction {

	private static final long serialVersionUID = -1086178041922316590L;

	@Resource
    public IIndexManageService indexManageService;
	
	/**
     * @Fields classifyVo : 指标分类对象vo
     */
    private IndexClassifyVo classifyVo;
    
    /**
     * 指标对象vo
     */
    private GradeIndexVo indexVo;
    
    private List<GradeIndexVo> index2Lst = new ArrayList<GradeIndexVo>();
    
    private List<PercentageVo> perLst = new ArrayList<PercentageVo>();

	public IndexClassifyVo getClassifyVo() {
		return classifyVo;
	}

	public void setClassifyVo(IndexClassifyVo classifyVo) {
		this.classifyVo = classifyVo;
	}

	public GradeIndexVo getIndexVo() {
		return indexVo;
	}

	public void setIndexVo(GradeIndexVo indexVo) {
		this.indexVo = indexVo;
	}

	public List<GradeIndexVo> getIndex2Lst() {
		return index2Lst;
	}

	public void setIndex2Lst(List<GradeIndexVo> index2Lst) {
		this.index2Lst = index2Lst;
	}
	
	public List<PercentageVo> getPerLst() {
		return perLst;
	}

	public void setPerLst(List<PercentageVo> perLst) {
		this.perLst = perLst;
	}

	/**
	 * 分页查询指标分类
	 */
	public String getClassifyList() {
		try {
			ListVo<IndexClassifyVo> lv = indexManageService.getClassifyList(getStart(), getLimit(), classifyVo);
			JsonUtil.outJson(lv);
		} catch (BusinessException e) {
			this.excepAndLogHandle(IndexManageAction.class, "分页查询指标分类", e, false);
		}
		
		return null;
	}
	
	/**
	 * 添加指标分类
	 */
	public String addClassify() {
		String msg = "{success:'false',msg:'指标分类添加失败'}";
		try {
			indexManageService.addClassify(classifyVo);
			msg = "{success:'true',msg:'指标分类添加成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "指标分类添加", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
	}
	
	/**
	 * 修改指标分类
	 */
	public String updateClassify() {
		String msg = "{success:'false',msg:'指标分类修改失败'}";
		try {
			indexManageService.updateClassify(classifyVo);
			msg = "{success:'true',msg:'指标分类修改成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "修改指标分类", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
	}
	
	/**
	 * 检查指标分类编号的唯一性
	 */
	public String checkNumber() {
		String number = getRequest().getParameter("value");
		try {
			Map<String, Object> vaildator = indexManageService.checkNumber(number);
            JsonUtil.outJson(vaildator);
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "检查指标分类编号的唯一性", e, false);
		}
		return null;
	}
	
	/**
	 * 删除指标分类
	 */
	public String delClassifies() {
		String msg = "{success:'false',msg:'删除指标分类失败'}";
		try {
			String ids = getRequest().getParameter("ids");
			indexManageService.delClassifies(ids);
			msg = "{success:'true',msg:'删除指标分类成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "删除指标分类", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
	}
	
	/**
	 * 锁定和解锁指标分类
	 * @return
	 */
	public String lockUnLock() {
		String msg = "{success:'false',msg:'锁定指标分类失败'}";
		String msg1 = "{success:'false',msg:'解锁指标分类失败'}";
		
		String id = getRequest().getParameter("classify");
		String enable = getRequest().getParameter("enable");
		Integer respId = StringUtil.isNotBlank(id) ? Integer.valueOf(id) : 0;
		Integer en = StringUtil.isNotBlank(enable) ? Integer.valueOf(enable) : 0;
		try {
			indexManageService.lockUnLock(respId);
			msg = "{success:'true',msg:'锁定指标分类成功'}";
			msg1 = "{success:'true',msg:'解锁指标分类成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "锁定和解锁指标分类", e, false);
		}
		
		if (en == 1) {
			JsonUtil.outJson(msg);
		}
		else {
			JsonUtil.outJson(msg1);
		}
		return null;
	}
	
	/**
	 * 查询所有指标分类
	 */
	public String getAllClassifies() {
		try {
			JsonUtil.outJsonArray(indexManageService.getAllClassifies());
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "查询所有指标分类", e, false);
		}
		return null;
	}
	
    /************指标管理*************/
    
    /**
     * 根据指标分类id查询所有指标
     */
    public String getAllIndex() {
    	
    	return null;
    }
    
    /**
     * 检查指标编号的唯一性
     */
    public String checkIndexNumber() {
    	String number = getRequest().getParameter("value");
		try {
			Map<String, Object> vaildator = indexManageService.checkIndexNumber(number);
            JsonUtil.outJson(vaildator);
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "检查指标编号的唯一性", e, false);
		}
		return null;
    }
    
    /**
     * 分页查询指标
     */
    public String getIndexList() {
    	try {
			ListVo<GradeIndexVo> lv = indexManageService.getIndexList(getStart(), getLimit(), indexVo);
			JsonUtil.outJson(lv);
		} catch (BusinessException e) {
			this.excepAndLogHandle(IndexManageAction.class, "分页查询指标", e, false);
		}
		return null;
    }
    
    /**
     * 添加指标
     */
    public String addIndex() {
    	String msg = "{success:'false',msg:'指标添加失败'}";
		try {
			indexManageService.addIndex(indexVo, index2Lst);
			msg = "{success:'true',msg:'指标添加成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "指标添加", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
    }
    
    /**
     * 更新指标
     */
    public String updateIndex() {
    	String msg = "{success:'false',msg:'指标修改失败'}";
		try {
			indexManageService.updateIndex(indexVo, index2Lst);
			msg = "{success:'true',msg:'指标修改成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "修改指标", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
    }
    
    /**
     * 批量删除指标
     */
    public String delIndexes() {
    	String msg = "{success:'false',msg:'删除指标失败'}";
		try {
			String ids = getRequest().getParameter("ids");
			indexManageService.delIndexes(ids);
			msg = "{success:'true',msg:'删除指标成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "删除指标", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
    }
    
    /**
     * 根据一级指标id查询二级指标
     */
    public String getIndex2ListByIndex1Id() {
    	try {
			String id = getRequest().getParameter("index1Id");
			Integer index1Id = StringUtil.isNotBlank(id) ? Integer.valueOf(id) : 0;
			JsonUtil.outJsonArray(indexManageService.getIndex2ListByIndex1Id(index1Id));
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "根据一级指标id查询二级指标", e, false);
		}
		return null;
    }
    
    /************权重管理*************/
    
    /**
     * 检查单据编号的唯一性
     */
    public String checkreceiptsNum() {
    	String number = getRequest().getParameter("value");
		try {
			Map<String, Object> vaildator = indexManageService.checkreceiptsNum(number);
            JsonUtil.outJson(vaildator);
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "检查单据编号的唯一性", e, false);
		}
		return null;
    }
    
    /**
     * 根据指标分类id查询权重管理所需基础数据
     */
    public String getBaseListByCfId() {
    	try {
			String id = getRequest().getParameter("cfId");
			Integer cfId = StringUtil.isNotBlank(id) ? Integer.valueOf(id) : 0;
			JsonUtil.outJsonArray(indexManageService.getBaseListByCfId(cfId));
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "根据指标分类id查询权重管理所需基础数据", e, false);
		}
		return null;
    }
    
    /**
     * 保存权重设置
     */
    public String savePercentage() {
    	String msg = "{success:'false',msg:'保存权重设置失败'}";
		try {
			indexManageService.savePercentage(perLst);
			msg = "{success:'true',msg:'保存权重设置成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "保存权重设置", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
    }
    
    /**
     * 获取所有角色
     */
    public String getAllRole() {
		try {
			JsonUtil.outJsonArray(indexManageService.getAllRole());
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "获取所有角色", e, false);
		}
		return null;
    }
    
    /************部门评分*************/
    
    /**
	 * 查询指标分类用于部门评分
	 */
	public String getClassifyListForGrade() {
		try {
			List<IndexClassifyVo> lst = indexManageService.getClassifyListForGrade(classifyVo, getCurrentUser());
			JsonUtil.outJsonArray(lst);
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "查询指标分类用于部门评分", e, false);
		}
		
		return null;
	}
	
	/**
	 * 查询用于评分部门
	 */
	public String getOrgListForGrade() {
		try {
			List<OrgVo> lst = indexManageService.getOrgListForGrade(classifyVo, getCurrentUser());
			JsonUtil.outJsonArray(lst);
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "查询用于评分部门", e, false);
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
     * 根据指标分类id查询指标
     */
    public String getIndexListForGrade() {
    	try {
			String id = getRequest().getParameter("cfId");
			Integer cfId = StringUtil.isNotBlank(id) ? Integer.valueOf(id) : 0;
			JsonUtil.outJsonArray(indexManageService.getIndexListForGrade(cfId, getCurrentUser()));
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "根据指标分类id查询指标", e, false);
		}
		return null;
    }
    
    /**
     * 保存部门评分
     */
    public String saveDeptGrade() {
    	String msg = "{success:'false',msg:'保存部门评分失败'}";
		try {
			String defen = getRequest().getParameter("defen");
			indexManageService.saveDeptGrade(defen, getCurrentUser());
			msg = "{success:'true',msg:'保存部门评分成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "保存部门评分", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
    }
    
    /**
     * 提交部门评分
     */
    public String submitDeptGrade() {
    	String msg = "{success:'false',msg:'提交部门评分失败'}";
		try {
			String cfIds = getRequest().getParameter("cfIds");
			Map<String, String> rtn = indexManageService.submitDeptGrade(cfIds, getCurrentUser());
			if ("notGrade".equals(rtn.get("flag"))) {
				msg = "{success:'false',msg:'" + rtn.get("msg") + "'}";
			}
			else {
				msg = "{success:'true',msg:'提交部门评分成功'}";
			}
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "提交部门评分", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
    }
    
    /******************部门评分明细数据查询********************/
    
    /**
	 * 查询部门评分明细
	 */
	public String queryDeptGradeDetail() {
		try {
			String electYear = getRequest().getParameter("electYear");
			String canpDeptId = getRequest().getParameter("canpDeptId");
			String gradeUsrDeptId = getRequest().getParameter("gradeUsrDeptId");
			String cfId = getRequest().getParameter("cfId");
			
			ListVo<DeptGradeDetailVo> lv = indexManageService.queryDeptGradeDetail(getStart(), getLimit(), electYear, 
					canpDeptId, gradeUsrDeptId, cfId);
			JsonUtil.outJson(lv);
		} catch (Exception e) {
			this.excepAndLogHandle(IndexManageAction.class, "查询部门评分明细", e, false);
		}
		
		return null;
	}
}
