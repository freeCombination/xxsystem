package com.xx.system.deptgrade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.deptgrade.entity.ClassifyUser;
import com.xx.system.deptgrade.entity.CopyRecord;
import com.xx.system.deptgrade.entity.FinalScore;
import com.xx.system.deptgrade.entity.GradeIndex;
import com.xx.system.deptgrade.entity.GradePercentage;
import com.xx.system.deptgrade.entity.GradeRecord;
import com.xx.system.deptgrade.entity.IndexClassify;
import com.xx.system.deptgrade.entity.OrgAndClassify;
import com.xx.system.deptgrade.service.IIndexManageService;
import com.xx.system.deptgrade.vo.DeptGradeDetailVo;
import com.xx.system.deptgrade.vo.GradeIndexVo;
import com.xx.system.deptgrade.vo.IndexClassifyVo;
import com.xx.system.deptgrade.vo.PercentageVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.service.IOrgService;
import com.xx.system.org.vo.OrgVo;
import com.xx.system.role.entity.Role;
import com.xx.system.role.entity.RoleMemberScope;
import com.xx.system.role.vo.RoleVo;
import com.xx.system.user.entity.User;
import com.xx.system.user.vo.UserVo;

import net.sf.json.JSONArray;

/**
 * 指标逻辑接口实现
 * 
 * @version V1.20,2013-11-25 下午4:02:53
 * @see [相关类/方法]
 */
@SuppressWarnings("unchecked")
@Service("indexManageService")
public class IndexManageServiceImpl implements IIndexManageService {
    
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    @Resource
    public IOrgService organizationService;

	@Override
	public List<IndexClassifyVo> getAllClassifies(String electYear, String participation) throws BusinessException {
		String cfHql = " from IndexClassify i where i.isDelete = 0 and i.enable = 0 ";
		if (StringUtil.isNotBlank(electYear)) {
			cfHql += " and i.electYear = '" + electYear + "'";
		}
		
		if (StringUtil.isNotBlank(participation) && "true".equals(participation)) {
			cfHql += " and i.isParticipation = 1";
		}
		
		cfHql += " order by i.name";
		
		List<IndexClassify> cfLst = (List<IndexClassify>)baseDao.queryEntitys(cfHql);
		
		IndexClassifyVo icvo = null;
		List<IndexClassifyVo> voLst = new ArrayList<IndexClassifyVo>();
		
		if (!CollectionUtils.isEmpty(cfLst)) {
			for (IndexClassify cf : cfLst) {
				icvo = new IndexClassifyVo();
				
				icvo.setClassifyId(cf.getPkClassifyId());
				icvo.setNumber(cf.getNumber());
				icvo.setName(cf.getName());
				
				voLst.add(icvo);
			}
		}
		
		return voLst;
	}

	@Override
	public Map<String, Object> checkNumber(String number) throws BusinessException {
		Map<String, Object> vaildator = new HashMap<String, Object>();
        
        int totleSize = 0;
        if (StringUtil.isNotBlank(number)) {
            StringBuffer countHql = new StringBuffer();
            countHql.append(" from IndexClassify r ");
            countHql.append(" where r.number = '");
            countHql.append(number + "'");
            
            totleSize = baseDao.queryTotalCount(countHql.toString(),
                new HashMap<String, Object>());
        }
        
        if (totleSize > 0) {
            vaildator.put("success", true);
            vaildator.put("valid", false);
            vaildator.put("reason", "该指标分类编号已被占用！");
        }
        else {
            vaildator.put("success", true);
            vaildator.put("valid", true);
            vaildator.put("reason", "");
        }
        return vaildator;
	}

	@Override
	public ListVo<IndexClassifyVo> getClassifyList(int start, int limit, IndexClassifyVo vo) throws BusinessException {
		ListVo<IndexClassifyVo> lstVo = new ListVo<IndexClassifyVo>();
		String cfHql = " from IndexClassify i where i.isDelete = 0";
		if (vo != null) {
			if (StringUtil.isNotBlank(vo.getNumber())) {
				cfHql += " and i.number like '%" + vo.getNumber() + "%'";
			}
			
			if (StringUtil.isNotBlank(vo.getName())) {
				cfHql += " and i.name like '%" + vo.getName() + "%'";
			}
			
			if (StringUtil.isNotBlank(vo.getElectYear())) {
				cfHql += " and i.electYear = '" + vo.getElectYear() + "'";
			}
		}
		
		int count = baseDao.queryTotalCount(cfHql, new HashMap<String, Object>());
		
		cfHql += " order by i.name";
		List<IndexClassify> cfLst = (List<IndexClassify>)baseDao.queryEntitysByPage(start, limit, 
				cfHql, new HashMap<String, Object>());
		
		IndexClassifyVo icvo = null;
		List<IndexClassifyVo> voLst = new ArrayList<IndexClassifyVo>();
		
		if (!CollectionUtils.isEmpty(cfLst)) {
			for (IndexClassify cf : cfLst) {
				icvo = new IndexClassifyVo();
				
				icvo.setClassifyId(cf.getPkClassifyId());
				icvo.setNumber(cf.getNumber());
				icvo.setName(cf.getName());
				
				String orgNames = "";
				String orgIds = "";
				if (cf.getOrgCfs() != null && cf.getOrgCfs().size() > 0) {
					Iterator<OrgAndClassify> ocIt = cf.getOrgCfs().iterator();
	                while (ocIt.hasNext()) {
	                	OrgAndClassify oc = ocIt.next();
	                	if (oc.getIsDelete() == Constant.STATUS_NOT_DELETE && 
	                            oc.getOrg().getStatus() == Constant.STATUS_NOT_DELETE) {
	                		orgNames += "," + oc.getOrg().getOrgName();
	                		orgIds += "," + oc.getOrg().getOrgId();
	                	}
	                }
					
				}
				
				if (StringUtil.isNotBlank(orgIds)) {
					icvo.setOrgIds(orgIds.substring(1));
					icvo.setOrgNames(orgNames.substring(1));
				}
				
				icvo.setElectYear(cf.getElectYear());
				//icvo.setHasSubmit(cf.getHasSubmit());
				icvo.setEnable(cf.getEnable());
				
				if (cf.getScoreType() != null) {
					icvo.setScoreTypeId(cf.getScoreType().getPkDictionaryId());
					icvo.setScoreTypeName(cf.getScoreType().getDictionaryName());
				}
				if (cf.getIsParticipation() != null && cf.getIsParticipation() == 1) {
					icvo.setParticipation("true");
				}
				else {
					icvo.setParticipation("false");
				}
				
				// 判断是否已产生评分记录，用于判断是否可以修改指标分类
				String grHql = " from GradeRecord gr where gr.isDelete = 0"
						+ " and gr.classify.pkClassifyId = " + cf.getPkClassifyId();
				int hasGrade = baseDao.queryTotalCount(grHql, new HashMap<String, Object>());
				if (hasGrade > 0) {
					icvo.setHasSubmit(1);
				}
				else {
					icvo.setHasSubmit(0);
				}
				
				voLst.add(icvo);
			}
		}
		lstVo.setList(voLst);
		lstVo.setTotalSize(count);
		
		return lstVo;
	}

	@Override
	public void addClassify(IndexClassifyVo vo) throws BusinessException {
		if (vo != null) {
			IndexClassify cf = new IndexClassify();
			cf.setNumber(vo.getNumber());
			cf.setName(vo.getName());
			cf.setElectYear(vo.getElectYear());
			cf.setHasSubmit(0);
			cf.setEnable(1);
			cf.setIsDelete(0);
			cf.setIsParticipation("true".equals(vo.getParticipation()) ? 1 : 0);
			
			// 查询字典
			if (vo.getScoreTypeId() != null && vo.getScoreTypeId() > 0) {
				Dictionary dict = (Dictionary)baseDao.queryEntityById(Dictionary.class, vo.getScoreTypeId());
				if (dict != null) {
					cf.setScoreType(dict);
				}
			}
			
			baseDao.save(cf);
			
			if (StringUtil.isNotBlank(vo.getOrgIds())) {
				String[] orgIds = vo.getOrgIds().split(",");
				OrgAndClassify oc = null;
				List<OrgAndClassify> orgCfs = new ArrayList<OrgAndClassify>();
				for (String id : orgIds) {
					oc = new OrgAndClassify();
					oc.setClassify(cf);
					oc.setIsDelete(0);
					
					Organization org = organizationService.getOrgById(Integer.valueOf(id));
					oc.setOrg(org);
					
					orgCfs.add(oc);
				}
				
				baseDao.saveAll(orgCfs);
			}
		}
	}

	@Override
	public void updateClassify(IndexClassifyVo vo) throws BusinessException {
		if (vo != null) {
			String cfHql = " from IndexClassify i where i.isDelete = 0 and i.enable = 1"
					+ " and i.pkClassifyId = " + vo.getClassifyId();
			List<IndexClassify> lst = (List<IndexClassify>)baseDao.queryEntitys(cfHql);
			IndexClassify cf = null;
			if (!CollectionUtils.isEmpty(lst)) {
				cf = lst.get(0);
				cf.setNumber(vo.getNumber());
				cf.setName(vo.getName());
				cf.setElectYear(vo.getElectYear());
				cf.setHasSubmit(0);
				cf.setEnable(1);
				cf.setIsDelete(0);
				
				cf.setIsParticipation("true".equals(vo.getParticipation()) ? 1 : 0);
				
				// 查询字典
				if (vo.getScoreTypeId() != null && vo.getScoreTypeId() > 0) {
					Dictionary dict = (Dictionary)baseDao.queryEntityById(Dictionary.class, vo.getScoreTypeId());
					if (dict != null) {
						cf.setScoreType(dict);
					}
				}
				
				baseDao.update(cf);
				
				// 删除后添加，实现更新的功能
				String delHql = " delete from OrgAndClassify oc where oc.classify.pkClassifyId = " + vo.getClassifyId();
				baseDao.executeHql(delHql);
				
				if (StringUtil.isNotBlank(vo.getOrgIds())) {
					String[] orgIds = vo.getOrgIds().split(",");
					OrgAndClassify oc = null;
					List<OrgAndClassify> orgCfs = new ArrayList<OrgAndClassify>();
					for (String id : orgIds) {
						oc = new OrgAndClassify();
						oc.setClassify(cf);
						oc.setIsDelete(0);
						
						Organization org = organizationService.getOrgById(Integer.valueOf(id));
						oc.setOrg(org);
						
						orgCfs.add(oc);
					}
					
					baseDao.saveAll(orgCfs);
				}
			}
		}
	}

	@Override
	public String delClassifies(String ids) throws BusinessException {
		if (StringUtil.isNotBlank(ids)) {
			// 先删除指标分类和部门的关联
			String delOc = " delete from OrgAndClassify o where o.classify.pkClassifyId in (" + ids + ")";
			baseDao.executeHql(delOc);
			
			// 然后删除指标分类
			String delCf = " delete from IndexClassify i where i.pkClassifyId in (" + ids + ")";
			baseDao.executeHql(delCf);
		}
		return "success";
	}

	@Override
	public void lockUnLock(Integer classifyId) throws BusinessException {
		if (classifyId != null && classifyId != 0) {
			IndexClassify cf = (IndexClassify)baseDao.queryEntityById(IndexClassify.class, classifyId);
			
			if (cf != null) {
				if (cf.getEnable() == 1) {
					cf.setEnable(0);
				}
				else {
					cf.setEnable(1);
				}
				
				baseDao.update(cf);
			}
		}
		
	}
	
	@Override
	public Map<String, String> copyBaseData() throws Exception {
		Map<String, String> message = new HashMap<String, String>();
		message.put("flag", "success");
		
		Calendar c = Calendar.getInstance();
		// 判断是否已经复制了
		String crHql = " from CopyRecord cr where cr.electYear = '" + c.get(Calendar.YEAR) + "'"
				+ " and cr.hasCopied = 1";
		int hasCopied = baseDao.queryTotalCount(crHql, new HashMap<String, Object>());
		if (hasCopied > 0) {
			message.put("flag", "hasCopied");
			message.put("msg", "本年度评分基础数据已复制，不能重复操作！");
			return message;
		}
		
		// 查询上一年基础数据
		c.add(Calendar.YEAR, -1);
		
		String cfHql = " from IndexClassify i where i.isDelete = 0 and i.enable = 0 "
			+ " and i.electYear = '" + c.get(Calendar.YEAR) + "'"
			+ " order by i.name";
		
		List<IndexClassify> cfLst = (List<IndexClassify>)baseDao.queryEntitys(cfHql);
		if (!CollectionUtils.isEmpty(cfLst)) {
			c.add(Calendar.YEAR, 1);
			
			IndexClassify classify = null;
			OrgAndClassify oc = null;
			for (IndexClassify cf : cfLst) {
				classify = new IndexClassify();
				// 复制指标分类
				classify.setNumber(cf.getNumber());
				classify.setName(cf.getName());
				classify.setElectYear(String.valueOf(c.get(Calendar.YEAR)));
				classify.setHasSubmit(0);
				classify.setEnable(0);
				classify.setIsDelete(0);
				
				classify.setIsParticipation(cf.getIsParticipation());
				classify.setScoreType(cf.getScoreType());
				
				baseDao.save(classify);
				
				// 复制指标关联的部门
				if (cf.getOrgCfs() != null && cf.getOrgCfs().size() > 0) {
					Iterator<OrgAndClassify> ocIt = cf.getOrgCfs().iterator();
	                while (ocIt.hasNext()) {
	                	oc = new OrgAndClassify();
	                	oc.setClassify(classify);
	                	oc.setOrg(ocIt.next().getOrg());
	                	oc.setIsDelete(0);
	                	
	                	baseDao.save(oc);
	                }
				}
				
				// 复制一级、二级指标
				String indexHql = " from GradeIndex g where g.isDelete = 0"
						+ " and g.classify.pkClassifyId =" + cf.getPkClassifyId()
						+ " and g.gradeIndex is null";
				
				List<GradeIndex> indexLst = (List<GradeIndex>)baseDao.queryEntitys(indexHql);
				
				if (!CollectionUtils.isEmpty(indexLst)) {
					GradeIndex index1 = null;
					GradeIndex index2 = null;
					for (GradeIndex index : indexLst) {
						index1 = new GradeIndex();
						index1.setNumber(index.getNumber());
						index1.setName(index.getName());
						index1.setClassify(classify);
						index1.setGrade(index.getGrade());
						index1.setRemark(index.getRemark());
						index1.setIsDelete(0);
						baseDao.save(index1);
						
						// 复制二级指标
						if (index.getIndexs() != null && index.getIndexs().size() > 0) {
							Iterator<GradeIndex> inxIt = index.getIndexs().iterator();
			                while (inxIt.hasNext()) {
			                	index2 = new GradeIndex();
			                	GradeIndex gi = inxIt.next();
			                	index2.setNumber(gi.getNumber());
			                	index2.setName(gi.getName());
			                	index2.setGrade(gi.getGrade());
			                	index2.setRemark(gi.getRemark());
			                	index2.setIsDelete(0);
			                	index2.setGradeIndex(index1);
			                	
			                	baseDao.save(index2);
			                }
						}
					}
				}
				
				// 复制权重
				String gpHql = " from GradePercentage g where isDelete = 0 "
						+ " and g.classify.pkClassifyId = " + cf.getPkClassifyId();
				List<GradePercentage> perLst = (List<GradePercentage>)baseDao.queryEntitys(gpHql);
				if (!CollectionUtils.isEmpty(perLst)) {
					GradePercentage percent = null;
					for (GradePercentage gp : perLst) {
						percent = new GradePercentage();
						percent.setClassify(classify);
						percent.setReceiptsNum(gp.getReceiptsNum());
						percent.setRole(gp.getRole());
						percent.setRemark(gp.getRemark());
						percent.setPercentage(gp.getPercentage());
						percent.setIsDelete(0);
						
						baseDao.save(percent);
					}
				}
			}
			
			// 保存本年度已经复制
			CopyRecord cr = new CopyRecord();
			cr.setElectYear(String.valueOf(c.get(Calendar.YEAR)));
			cr.setHasCopied(1);
			baseDao.save(cr);
		}
		
		return message;
	}

	/************指标管理*************/
	
	@Override
	public List<GradeIndexVo> getAllIndex(Integer cfId) throws BusinessException {
		
		return null;
	}

	@Override
	public Map<String, Object> checkIndexNumber(String number) throws BusinessException {
		Map<String, Object> vaildator = new HashMap<String, Object>();
        
        int totleSize = 0;
        if (StringUtil.isNotBlank(number)) {
            StringBuffer countHql = new StringBuffer();
            countHql.append(" from GradeIndex g ");
            countHql.append(" where g.number = '");
            countHql.append(number + "'");
            
            totleSize = baseDao.queryTotalCount(countHql.toString(),
                new HashMap<String, Object>());
        }
        
        if (totleSize > 0) {
            vaildator.put("success", true);
            vaildator.put("valid", false);
            vaildator.put("reason", "该指标编号已被占用！");
        }
        else {
            vaildator.put("success", true);
            vaildator.put("valid", true);
            vaildator.put("reason", "");
        }
        return vaildator;
	}

	@Override
	public ListVo<GradeIndexVo> getIndexList(int start, int limit, GradeIndexVo indexVo) throws BusinessException {
		ListVo<GradeIndexVo> lstVo = new ListVo<GradeIndexVo>();
		String indexHql = " from GradeIndex g where g.isDelete = 0";
		if (indexVo != null) {
			if (StringUtil.isNotBlank(indexVo.getNumber())) {
				indexHql += " and g.number like '%" + indexVo.getNumber() + "%'";
			}
			
			if (StringUtil.isNotBlank(indexVo.getName())) {
				indexHql += " and g.name like '%" + indexVo.getName() + "%'";
			}
			
			if (StringUtil.isNotBlank(indexVo.getElectYear())) {
				indexHql += " and g.classify.electYear = '" + indexVo.getElectYear() + "'";
			}
		}
		indexHql += " and g.classify is not null and g.gradeIndex is null";
		
		int count = baseDao.queryTotalCount(indexHql, new HashMap<String, Object>());
		
		indexHql += " order by g.name";
		List<GradeIndex> indexLst = (List<GradeIndex>)baseDao.queryEntitysByPage(start, limit, 
				indexHql, new HashMap<String, Object>());
		
		GradeIndexVo vo = null;
		List<GradeIndexVo> voLst = new ArrayList<GradeIndexVo>();
		
		if (!CollectionUtils.isEmpty(indexLst)) {
			for (GradeIndex index : indexLst) {
				vo = new GradeIndexVo();
				
				vo.setIndexId(index.getPkIndexId());
				vo.setNumber(index.getNumber());
				vo.setName(index.getName());
				vo.setClassifyName(index.getClassify().getName());
				vo.setClassifyId(index.getClassify().getPkClassifyId());
				vo.setGrade(index.getGrade());
				vo.setRemark(index.getRemark());
				
				voLst.add(vo);
			}
		}
		lstVo.setList(voLst);
		lstVo.setTotalSize(count);
		
		return lstVo;
	}

	@Override
	public void addIndex(GradeIndexVo indexVo, List<GradeIndexVo> indexLst) throws BusinessException {
		if (indexVo != null) {
			GradeIndex index = new GradeIndex();
			index.setNumber(indexVo.getNumber());
			index.setName(indexVo.getName());
			
			IndexClassify cf = (IndexClassify)baseDao.queryEntityById(IndexClassify.class, indexVo.getClassifyId());
			if (cf != null) {
				index.setClassify(cf);
			}
			
			index.setGrade(indexVo.getGrade());
			index.setRemark(indexVo.getRemark());
			index.setIsDelete(0);
			
			
			baseDao.save(index);
			
			if (!CollectionUtils.isEmpty(indexLst)) {
				GradeIndex index2 = null;
				List<GradeIndex> index2Lst = new ArrayList<GradeIndex>();
				for (GradeIndexVo vo : indexLst) {
					index2 = new GradeIndex();
					index2.setNumber(vo.getNumber());
					index2.setName(vo.getName());
					index2.setGrade(vo.getGrade());
					index2.setRemark(vo.getRemark());
					index2.setGradeIndex(index);
					index2.setIsDelete(0);
					
					index2Lst.add(index2);
				}
				baseDao.saveAll(index2Lst);
			}
		}
	}

	@Override
	public void updateIndex(GradeIndexVo indexVo, List<GradeIndexVo> indexLst) throws BusinessException {
		if (indexVo != null) {
			String indexHql = " from GradeIndex g where g.isDelete = 0"
					+ " and g.pkIndexId = " + indexVo.getIndexId();
			List<GradeIndex> lst = (List<GradeIndex>)baseDao.queryEntitys(indexHql);
			GradeIndex index = null;
			if (!CollectionUtils.isEmpty(lst)) {
				index = lst.get(0);
				
				index.setNumber(indexVo.getNumber());
				index.setName(indexVo.getName());
				
				IndexClassify cf = (IndexClassify)baseDao.queryEntityById(IndexClassify.class, indexVo.getClassifyId());
				if (cf != null) {
					index.setClassify(cf);
				}
				
				index.setGrade(indexVo.getGrade());
				index.setRemark(indexVo.getRemark());
				index.setIsDelete(0);
				
				baseDao.update(index);
				
				// 先删除后重新保存，实现更新的功能
				String delHql = " delete from GradeIndex g where g.gradeIndex.pkIndexId = " + indexVo.getIndexId();
				baseDao.executeHql(delHql);
				
				if (!CollectionUtils.isEmpty(indexLst)) {
					GradeIndex index2 = null;
					List<GradeIndex> index2Lst = new ArrayList<GradeIndex>();
					for (GradeIndexVo vo : indexLst) {
						index2 = new GradeIndex();
						index2.setNumber(vo.getNumber());
						index2.setName(vo.getName());
						index2.setGrade(vo.getGrade());
						index2.setRemark(vo.getRemark());
						index2.setGradeIndex(index);
						index2.setIsDelete(0);
						
						index2Lst.add(index2);
					}
					baseDao.saveAll(index2Lst);
				}
			}
		}
	}

	@Override
	public String delIndexes(String ids) throws BusinessException {
		if (StringUtil.isNotBlank(ids)) {
			// 先删除二级指标
			String delIndex2 = " delete from GradeIndex g where g.gradeIndex.pkIndexId in (" + ids + ")";
			baseDao.executeHql(delIndex2);
			
			// 然后删除一级指标
			String delIndex = " delete from GradeIndex g where g.pkIndexId in (" + ids + ")";
			baseDao.executeHql(delIndex);
		}
		return "success";
	}

	@Override
	public List<GradeIndexVo> getIndex2ListByIndex1Id(Integer index1Id) throws BusinessException {
		List<GradeIndexVo> lstVo = new ArrayList<GradeIndexVo>();
		if (index1Id != null && index1Id != 0) {
			String index2Hql = " from GradeIndex g where g.isDelete = 0 and g.gradeIndex.pkIndexId = " + index1Id;
			List<GradeIndex> index2Lst = (List<GradeIndex>)baseDao.queryEntitys(index2Hql);
			GradeIndexVo vo = null;
			if (!CollectionUtils.isEmpty(index2Lst)) {
				for (GradeIndex i : index2Lst) {
					vo = new GradeIndexVo();
					
					vo.setIndexId(i.getPkIndexId());
					vo.setNumber(i.getNumber());
					vo.setName(i.getName());
					vo.setGrade(i.getGrade());
					vo.setRemark(i.getRemark());
					
					lstVo.add(vo);
				}
			}
		}
		
		return lstVo;
	}

	/************权重管理*************/
	
	@Override
	public Map<String, Object> checkreceiptsNum(String number) throws BusinessException {
		
		return null;
	}

	@Override
	public List<PercentageVo> getBaseListByCfId(Integer cfId) throws BusinessException {
		List<PercentageVo> voLst = new ArrayList<PercentageVo>();
		
		if (cfId != null && cfId != 0) {
			// 查询指标类型对应的权重基础信息
			String gpHql = " from GradePercentage g where isDelete = 0 "
					+ " and g.classify.pkClassifyId = " + cfId
					+ " and g.classify.isParticipation = 1";
			List<GradePercentage> perLst = (List<GradePercentage>)baseDao.queryEntitys(gpHql);
			if (!CollectionUtils.isEmpty(perLst)) {
				PercentageVo vo = null;
				
				for (GradePercentage gp : perLst) {
					vo = new PercentageVo();
					vo.setPerId(gp.getPkPerId());
					vo.setReceiptsNum(gp.getReceiptsNum());
					if (gp.getRole() != null) {
						vo.setRoleId(gp.getRole().getRoleId());
						vo.setRoleName(gp.getRole().getRoleName());
					}
					vo.setPercentage(gp.getPercentage());
					vo.setRemark(gp.getRemark());
					voLst.add(vo);
				}
			}
		}
		
		return voLst;
	}

	@Override
	public void savePercentage(List<PercentageVo> voLst) throws BusinessException {
		if (!CollectionUtils.isEmpty(voLst)) {
			// 先删除所有该指标分类和角色对应的权重设置
			String delHql = " delete from GradePercentage gp where gp.classify.pkClassifyId = "
					+ voLst.get(0).getClassifyId();
			
			baseDao.executeHql(delHql);
			
			GradePercentage gp = null;
			List<GradePercentage> gpLst = new ArrayList<GradePercentage>();
			for (PercentageVo vo : voLst) {
				gp = new GradePercentage();
				
				// 根据指标分类ID查询指标
				IndexClassify cf = (IndexClassify)baseDao.queryEntityById(IndexClassify.class, vo.getClassifyId());
				gp.setClassify(cf);
				
				// 根据角色ID查询角色
				Role role = (Role)baseDao.queryEntityById(Role.class, vo.getRoleId());
				gp.setRole(role);
				
				gp.setRemark(vo.getRemark());
				gp.setReceiptsNum(vo.getReceiptsNum());
				gp.setPercentage(vo.getPercentage());
				gp.setIsDelete(0);
				
				gpLst.add(gp);
			}
			
			baseDao.saveAll(gpLst);
		}
	}

	@Override
	public List<RoleVo> getAllRole() throws BusinessException {
		String roleHql = " from Role r where r.isDelete = 0";
		List<Role> roleLst = (List<Role>)baseDao.queryEntitys(roleHql);
		
		List<RoleVo> voLst = new ArrayList<RoleVo>();
		if (!CollectionUtils.isEmpty(roleLst)) {
			for (Role role : roleLst) {
				RoleVo vo = new RoleVo();
				
				vo.setRoleId(role.getRoleId());
				vo.setRoleName(role.getRoleName());
				
				voLst.add(vo);
			}
		}
		return voLst;
	}

	/************部门评分*************/
	
	/**
     * 递归获取子部门
     * 
     * @Title recursionChildren
     * @author dong.he
     * @Description: 
     * @date 2014年12月23日
     * @param org
     */
    private void recursionChildren(Organization org, List<Organization> orgLst) 
        throws Exception {
        if (org != null && org.getOrganizations() != null) {
        	Iterator<Organization> children = org.getOrganizations().iterator();
    		while (children.hasNext()) {
    			Organization o = children.next();
    			orgLst.add(o);
    			
    			if (o.getOrganizations() != null) {
    				recursionChildren(o, orgLst);
    			}
    		}
        }
    }
    
    /**
     * 对部门进行排序
     * 
     * @Title orderOrg
     * @author dong.he
     * @date 2014年12月25日
     * @param orgLst
     */
    private void orderOrg(List<Organization> orgLst){
        Collections.sort(orgLst, new Comparator<Organization>() {
            public int compare(Organization o1, Organization o2) {
                if (o1.getDisOrder() - o2.getDisOrder() != 0) {
                    return o1.getDisOrder() - o2.getDisOrder();
                }
                return o1.getOrgId() - o2.getOrgId();
            }
        });
    }
	
    /**
     * 对指标进行排序
     * 
     * @Title orderIndex
     * @author dong.he
     * @date 2014年12月25日
     * @param inxLst
     */
    private void orderIndex(List<GradeIndex> inxLst){
        Collections.sort(inxLst, new Comparator<GradeIndex>() {
            public int compare(GradeIndex i1, GradeIndex i2) {
                return i1.getPkIndexId() - i2.getPkIndexId();
            }
        });
    }
    
	@Override
	public List<IndexClassifyVo> getClassifyListForGrade(IndexClassifyVo vo, User currUsr) throws Exception {
		String cfHql = " from IndexClassify i where i.isDelete = 0 and i.enable = 0 and i.isParticipation = 1";
		if (vo != null) {
			if (StringUtil.isNotBlank(vo.getElectYear())) {
				cfHql += " and i.electYear = '" + vo.getElectYear() + "'";
			}
		}
		
		// 获取当前登陆用户所在部门，进而获取该用户可评分的部门
		// 由于部门上也关联了部门主任、分管领导等用户信息，也可以把登录用户的id拿去匹配这些用户id，获取到的部门ids就可以限定指标分类的查询
		Iterator<OrgUser> ouIt = currUsr.getOrgUsers().iterator();
		String selfDept = "";
		String depts = "";
		while (ouIt.hasNext()) {
        	Organization org = ouIt.next().getOrganization();
        	selfDept += "," + org.getOrgId();
        	// 获取该部门的兄弟部门
        	if (org.getOrganization() != null) {
        		Iterator<Organization> orgIt = org.getOrganization().getOrganizations().iterator();
        		while (orgIt.hasNext()) {
        			Organization o = orgIt.next();
        			depts += "," + o.getOrgId();
        		}
        	}
        	
        	// 获取该部门的子部门
        	List<Organization> orgLst = new ArrayList<Organization>();
        	recursionChildren(org, orgLst);
        	if (!CollectionUtils.isEmpty(orgLst)) {
        		for (Organization o : orgLst) {
        			depts += "," + o.getOrgId();
        		}
        	}
        }
		
		// 由于分馆所领导可能管理多个部门，分馆所领导不能评分这些部门，也需要排除，可通过与部门关联的分馆所领导对比，对比上就排除该部门
		String fenguanHql = " from Organization o where o.status = 0"
				+ " and o.enable = 0"
				+ " and o.otherSup is not null"
				+ " and o.otherSup.userId = " + currUsr.getUserId();
		List<Organization> fenguanOrg = (List<Organization>)baseDao.queryEntitys(fenguanHql);
		if (!CollectionUtils.isEmpty(fenguanOrg)) {
			for (Organization org : fenguanOrg) {
				depts += "," + org.getOrgId();
			}
		}
		
		// 查询OrgAndClassify，限制IndexClassify
		String ocHql = " from OrgAndClassify oc where oc.isDelete = 0 and oc.classify.isParticipation = 1";
		/*
		 * 自己不能查看自己所在部门关联的指标分类（当一个指标只关联了自己所在的部门，注释掉表示可以查看，但不能评分）
		 */
		if (StringUtil.isNotBlank(selfDept)) {
			ocHql += " and oc.org.orgId not in (" + selfDept.substring(1) + ")";
		}
		
		if (StringUtil.isNotBlank(depts)) {
			ocHql += " and oc.org.orgId in (" + depts.substring(1) + ")";
		}
		
		if (StringUtil.isNotBlank(selfDept) || StringUtil.isNotBlank(depts)) {
			List<OrgAndClassify> ocLst = (List<OrgAndClassify>)baseDao.queryEntitys(ocHql);
			if (!CollectionUtils.isEmpty(ocLst)) {
				String cfIds = "";
				for (OrgAndClassify oc : ocLst) {
					cfIds += "," + oc.getClassify().getPkClassifyId();
        		}
				
				cfHql += " and i.pkClassifyId in (" + cfIds.substring(1) + ")";
			}
		}
		
		cfHql += " order by i.name";
		List<IndexClassify> cfLst = (List<IndexClassify>)baseDao.queryEntitys(cfHql);
		
		IndexClassifyVo icvo = null;
		List<IndexClassifyVo> voLst = new ArrayList<IndexClassifyVo>();
		
		if (!CollectionUtils.isEmpty(cfLst)) {
			for (IndexClassify cf : cfLst) {
				icvo = new IndexClassifyVo();
				
				icvo.setClassifyId(cf.getPkClassifyId());
				icvo.setNumber(cf.getNumber());
				icvo.setName(cf.getName());
				
				String orgNames = "";
				String orgIds = "";
				if (cf.getOrgCfs() != null && cf.getOrgCfs().size() > 0) {
					Iterator<OrgAndClassify> ocIt = cf.getOrgCfs().iterator();
	                while (ocIt.hasNext()) {
	                	OrgAndClassify oc = ocIt.next();
	                	if (oc.getIsDelete() == Constant.STATUS_NOT_DELETE && 
	                            oc.getOrg().getStatus() == Constant.STATUS_NOT_DELETE) {
	                		orgNames += "," + oc.getOrg().getOrgName();
	                		orgIds += "," + oc.getOrg().getOrgId();
	                	}
	                }
				}
				
				if (StringUtil.isNotBlank(orgIds)) {
					icvo.setOrgIds(orgIds.substring(1));
					icvo.setOrgNames(orgNames.substring(1));
				}
				
				icvo.setElectYear(cf.getElectYear());
				
				// 查询ClassifyUser，判断该用户对应的指标分类是否已经提交
				String cuHql = " from ClassifyUser cu where cu.isDelete = 0"
						+ " and cu.hasSubmit = 1 and cu.user.userId = " + currUsr.getUserId()
						+ " and cu.classify.pkClassifyId = " + cf.getPkClassifyId();
				
				int count = baseDao.queryTotalCount(cuHql, new HashMap<String, Object>());
				if (count > 0) {
					icvo.setHasSubmit(1);
				}
				else {
					icvo.setHasSubmit(0);
				}
				
				// 查询GradeRecord，判断当前用户是否对该指标进行了评分
				String grHql = " from GradeRecord gr where gr.isDelete = 0"
						+ " and gr.classify.pkClassifyId = " + cf.getPkClassifyId()
						+ " and gr.user.userId = " + currUsr.getUserId();
				
				int grCount = baseDao.queryTotalCount(grHql, new HashMap<String, Object>());
				if (grCount > 0) {
					icvo.setHasSaved(1);
				}
				else {
					icvo.setHasSaved(0);
				}
				
				icvo.setEnable(cf.getEnable());
				
				voLst.add(icvo);
			}
		}
		
		return voLst;
	}

	@Override
	public List<OrgVo> getOrgListForGrade(IndexClassifyVo vo, User currUsr) throws Exception {
		// 查询指标分类关联的所有部门
		List<OrgVo> voLst = new ArrayList<OrgVo>();
		if (vo != null) {
			IndexClassify ic = (IndexClassify)baseDao.queryEntityById(IndexClassify.class, vo.getClassifyId());
			if (ic != null && ic.getOrgCfs() != null && ic.getOrgCfs().size() > 0) {
				// 获取当前登陆用户所在部门，进而获取该用户可评分的部门（排除自己所在部门）
				Iterator<OrgUser> ouIt = currUsr.getOrgUsers().iterator();
				Map<Integer, Organization> orgMap = new HashMap<Integer, Organization>();
				while (ouIt.hasNext()) {
					Organization usrOrg = ouIt.next().getOrganization();
					orgMap.put(usrOrg.getOrgId(), usrOrg);
				}
				
				// 由于分馆所领导可能管理多个部门，分馆所领导不能评分这些部门，也需要排除，可通过与部门关联的分馆所领导对比，对比上就排除该部门
				String fenguanHql = " from Organization o where o.status = 0"
						+ " and o.enable = 0"
						+ " and o.otherSup is not null"
						+ " and o.otherSup.userId = " + currUsr.getUserId();
				List<Organization> fenguanOrg = (List<Organization>)baseDao.queryEntitys(fenguanHql);
				if (!CollectionUtils.isEmpty(fenguanOrg)) {
					for (Organization org : fenguanOrg) {
						orgMap.put(org.getOrgId(), org);
					}
				}
				
				Iterator<OrgAndClassify> ocIt = ic.getOrgCfs().iterator();
				List<Organization> orgLst = new ArrayList<Organization>();
				OrgVo o = null;
				while (ocIt.hasNext()) {
                	OrgAndClassify oc = ocIt.next();
                	if (oc.getIsDelete() == Constant.STATUS_NOT_DELETE && 
                        oc.getOrg().getStatus() == Constant.STATUS_NOT_DELETE &&
                        !orgMap.containsKey(oc.getOrg().getOrgId())) {
                		
                		orgLst.add(oc.getOrg());
                	}
                }
				
				// 排序
				orderOrg(orgLst);
				
				for (Organization org : orgLst) {
					o = new OrgVo();
            		
            		o.setOrgId(org.getOrgId());
            		o.setOrgName(org.getOrgName());
            		
            		voLst.add(o);
				}
			}
		}
		return voLst;
	}

	@Override
	public List<GradeIndexVo> getIndexListForGrade(Integer cfId, User currUsr) throws Exception {
		List<GradeIndexVo> voLst = new ArrayList<GradeIndexVo>();
		
		String indexHql = " from GradeIndex g where g.isDelete = 0";
		if (cfId != null && cfId != 0) {
			indexHql += " and g.classify.pkClassifyId = " + cfId;
			indexHql += " order by g.name";
			
			List<GradeIndex> indexLst = (List<GradeIndex>)baseDao.queryEntitys(indexHql);
			GradeIndexVo vo = null;
			if (!CollectionUtils.isEmpty(indexLst)) {
				for (GradeIndex index : indexLst) {
					// 查询得分：GradeRecord
					String grHql = " from GradeRecord gr where gr.isDelete = 0"
							+ " and gr.classify.isDelete = 0 and gr.classify.enable = 0"
							+ " and gr.classify.pkClassifyId = " + index.getClassify().getPkClassifyId()
							+ " and gr.index1.pkIndexId = " + index.getPkIndexId()
							+ " and gr.user.userId = " + currUsr.getUserId();
					// 得分字符串
					String scores = "";
					
					if (index.getIndexs() != null && index.getIndexs().size() > 0) {
						Iterator<GradeIndex> giIt = index.getIndexs().iterator();
						List<GradeIndex> giLst = new ArrayList<GradeIndex>();
						while (giIt.hasNext()) {
							giLst.add(giIt.next());
						}
						
						// 排序
						orderIndex(giLst);
						
						for (GradeIndex gi : giLst) {
							scores = "";
							
							vo = new GradeIndexVo();
							
							vo.setGradeIndex1Id(index.getPkIndexId());
							vo.setName(index.getName());
							vo.setGrade(index.getGrade());
							vo.setRemark(index.getRemark());
							
							vo.setIndexId(gi.getPkIndexId());
							vo.setNumber(gi.getNumber());
							vo.setGradeIndex2Name(gi.getName());
							vo.setGrade2(gi.getGrade());
							vo.setRemark2(gi.getRemark());
							
							// 查询得分：GradeRecord
							String grHql2 = grHql + " and gr.index2.pkIndexId = " + gi.getPkIndexId();
							
							List<GradeRecord> grLst = (List<GradeRecord>)baseDao.queryEntitys(grHql2);
							if (!CollectionUtils.isEmpty(grLst)) {
								for (GradeRecord rec : grLst) {
									scores += "|" + rec.getOrg().getOrgId() + ":" + rec.getScore();
								}
								
								vo.setGradeRecs(scores.substring(1));
							}
							
							voLst.add(vo);
						}
					}
					else{
						vo = new GradeIndexVo();
						
						vo.setIndexId(index.getPkIndexId());
						vo.setNumber(index.getNumber());
						vo.setName(index.getName());
						vo.setClassifyName(index.getClassify().getName());
						vo.setClassifyId(index.getClassify().getPkClassifyId());
						vo.setGrade(index.getGrade());
						vo.setRemark(index.getRemark());
						
						List<GradeRecord> grLst = (List<GradeRecord>)baseDao.queryEntitys(grHql);
						if (!CollectionUtils.isEmpty(grLst)) {
							for (GradeRecord rec : grLst) {
								scores += "|" + rec.getOrg().getOrgId() + ":" + rec.getScore();
							}
							
							vo.setGradeRecs(scores.substring(1));
						}
						voLst.add(vo);
					}
				}
			}
		}
		
		return voLst;
	}

	@Override
	public void saveDeptGrade(String defen, User currUsr) throws Exception {
		if (StringUtil.isNotBlank(defen)) {
			// 将json字符串转换成对象集合
			JSONArray json = JSONArray.fromObject(defen);
			List<GradeIndexVo> vos = (List<GradeIndexVo>)JSONArray.toCollection(json, GradeIndexVo.class);
			if (!CollectionUtils.isEmpty(vos)) {
				// 先删除后添加，实现修改功能
				String delHql = " delete from GradeRecord gr where "
						+ " gr.classify.pkClassifyId = " + vos.get(0).getClassifyId()
						+ " and gr.user.userId = " + currUsr.getUserId();
				baseDao.executeHql(delHql);
				
				List<GradeRecord> grLst = new ArrayList<GradeRecord>();
				GradeRecord gr = null;
				GradeRecord rec = null;
				for (GradeIndexVo vo : vos) {
					gr = new GradeRecord();
					
					// 查询指标分类
					if (vo.getClassifyId() != null && vo.getClassifyId() != 0) {
						IndexClassify cf = (IndexClassify)baseDao.queryEntityById(IndexClassify.class, vo.getClassifyId());
						if (cf != null) {
							gr.setClassify(cf);
						}
					}
					
					// 查询一级指标
					if (vo.getIndexId() != null && vo.getIndexId() != 0) {
						GradeIndex gi1 = (GradeIndex)baseDao.queryEntityById(GradeIndex.class, vo.getIndexId());
						
						// 查询二级指标对应的一级指标
						if (vo.getGradeIndex1Id() != null && vo.getGradeIndex1Id() != 0) {
							GradeIndex gi = (GradeIndex)baseDao.queryEntityById(GradeIndex.class, vo.getGradeIndex1Id());
							if (gi != null) {
								gr.setIndex1(gi);
							}
							
							if (gi1 != null) {
								gr.setIndex2(gi1);
							}
						}
						else {
							if (gi1 != null) {
								gr.setIndex1(gi1);
							}
						}
					}
					
					// 从评分字符串解析部门和对应的得分
					if (StringUtil.isNotBlank(vo.getGradeRecs())) {
						String[] scores = vo.getGradeRecs().split("\\|");
						for (String score : scores) {
							rec = new GradeRecord();
							
							rec.setClassify(gr.getClassify());
							rec.setIndex1(gr.getIndex1());
							rec.setIndex2(gr.getIndex2());
							rec.setUser(currUsr);
							
							// 评分人部门
							Iterator<OrgUser> ouIt = currUsr.getOrgUsers().iterator();
							if (ouIt.hasNext()) {
					        	rec.setUsrOrg(ouIt.next().getOrganization());
							}
							
							Organization org = (Organization)baseDao.queryEntityById(Organization.class, NumberUtils.toInt(score.split(":")[0]));
							rec.setOrg(org);
							
							rec.setScore(score.split(":")[1]);
							
							grLst.add(rec);
						}
					}
				}
				
				baseDao.saveAll(grLst);
			}
		}
	}

	@Override
	public Map<String, String> submitDeptGrade(String cfIds, String electYear, User currUsr) throws Exception {
		Map<String, String> msg = new HashMap<String, String>();
		
		// 提交部门评分
		if (StringUtil.isNotBlank(cfIds)) {
			// 判断是否已经提交
			String checkHql = " from ClassifyUser cu where "
					+ " cu.classify.pkClassifyId in (" + cfIds + ")"
					+ " and cu.user.userId = " + currUsr.getUserId()
					+ " and cu.hasSubmit = 1 and cu.isDelete = 0";
			int hasSubmit = baseDao.queryTotalCount(checkHql, new HashMap<String, Object>());
			if (hasSubmit > 0) {
				msg.put("flag", "notGrade");
				msg.put("msg", "该年度指标已经提交，不能重复提交！");
				return msg;
			}
			
			ClassifyUser cu = null;
			List<ClassifyUser> cuLst = new ArrayList<ClassifyUser>();
			
			String[] ids = cfIds.split(",");
			for (String id : ids) {
				// 查询指标分类
				IndexClassify cf = (IndexClassify)baseDao.queryEntityById(IndexClassify.class, NumberUtils.toInt(id));
				
				// 查询该指标分类是否有评分记录
				String cfHql = " from GradeRecord gr where "
						+ " gr.classify.pkClassifyId = " + id
						+ " and gr.user.userId = " + currUsr.getUserId();
				int count = baseDao.queryTotalCount(cfHql, new HashMap<String, Object>());
				if (count <= 0) {
					msg.put("flag", "notGrade");
					msg.put("msg", "指标：" + cf.getName() + " 还未进行评分！");
					return msg;
				}
				
				cu = new ClassifyUser();
				cu.setUser(currUsr);
				cu.setIsDelete(0);
				cu.setHasSubmit(1);
				
				if (cf != null) {
					cu.setClassify(cf);
				}
				
				cuLst.add(cu);
			}
			
			baseDao.saveAll(cuLst);
		}
		
		// 判断是否所有人都提交部门评分，如果都提交了则生成部门评分汇总
		if (StringUtil.isNotBlank(electYear)) {
			String gpHql = " from GradePercentage gp where gp.isDelete = 0"
					+ " and gp.classify.isDelete = 0 and gp.classify.enable = 0"
					+ " and gp.classify.isParticipation = 1"
					+ " and gp.classify.electYear = '" + electYear + "'";
			List<GradePercentage> gpLst = (List<GradePercentage>)baseDao.queryEntitys(gpHql);
			if (!CollectionUtils.isEmpty(gpLst)) {
				boolean allSubmit = true;
				for (GradePercentage gp : gpLst) {
					// 一旦判断到有一个没有提交就跳出循环，增强程序性能
					if (!allSubmit) {
						break;
					}
					
					// 获取该权重包含的指标分类所包含的所有部门
					List<Integer> orgIdLst = new ArrayList<Integer>();
					if (gp.getClassify().getOrgCfs() != null && gp.getClassify().getOrgCfs().size() > 0) {
						Iterator<OrgAndClassify> ocIt = gp.getClassify().getOrgCfs().iterator();
						while (ocIt.hasNext()) {
				        	Organization org = ocIt.next().getOrg();
				        	orgIdLst.add(org.getOrgId());
						}
					}
					
					// 查询角色下包含的用户（RoleMemberScope），进而通过ClassifyUser判断用户是否已提交部门评分
					String rmsHql = " from RoleMemberScope r where r.role.roleId = " + gp.getRole().getRoleId();
					List<RoleMemberScope> rmsLst = (List<RoleMemberScope>)baseDao.queryEntitys(rmsHql);
					if (!CollectionUtils.isEmpty(rmsLst)) {
						for (RoleMemberScope rms : rmsLst) {
							if (rms.getUser() != null) {
								// 查询ClassifyUser，判断是否提交
								String cuHql = " from ClassifyUser cu where cu.isDelete = 0 and cu.hasSubmit = 1"
										+ " and cu.classify.pkClassifyId = " + gp.getClassify().getPkClassifyId()
										+ " and cu.user.userId = " + rms.getUser().getUserId();
								boolean containOrg = false;
								Iterator<OrgUser> ouIt = rms.getUser().getOrgUsers().iterator();
								while (ouIt.hasNext()) {
									Organization usrOrg = ouIt.next().getOrganization();
									if (orgIdLst.contains(usrOrg.getOrgId())) {
										containOrg = true;
										break;
									}
								}
								
								int hasSubmit = baseDao.queryTotalCount(cuHql, new HashMap<String, Object>());
								if (!containOrg && hasSubmit <= 0) {
									allSubmit = false;
									break;
								}
							}
						}
					}
				}
				
				if (allSubmit) {
					String hql = " from OrgAndClassify oc where oc.isDelete = 0"
							+ " and oc.classify.enable = 0"
							+ " and oc.classify.isDelete = 0"
							+ " and oc.classify.isParticipation = 1";
					if (StringUtil.isNotBlank(electYear)) {
						hql += " and oc.classify.electYear = '" + electYear + "'";
					}
					
					List<OrgAndClassify> ocLst = (List<OrgAndClassify>)baseDao.queryEntitys(hql);
					if (!CollectionUtils.isEmpty(ocLst)) {
						for (OrgAndClassify oc : ocLst) {
							// 根据用户ID查询角色，进而根据指标分类ID和角色ID查询权重
							Integer classifyId = oc.getClassify().getPkClassifyId();
							Integer orgId = oc.getOrg().getOrgId();
							
							Float defen = 0f;
							// 根据classifyId查询该部门的评分包含哪些角色-->权重和该角色包含的userIds-->得到这部分人的权重和总分
							String gpHql1 = " from GradePercentage gp where gp.isDelete = 0"
									+ " and gp.classify.pkClassifyId = " + classifyId;
							List<GradePercentage> gpLst1 = (List<GradePercentage>)baseDao.queryEntitys(gpHql1);
							if (!CollectionUtils.isEmpty(gpLst1)) {
								for (GradePercentage gp : gpLst1) {
									// 查询角色关联的用户
									String rmHql = " from RoleMemberScope rm where rm.role.roleId = " + gp.getRole().getRoleId();
									List<RoleMemberScope> rmLst = (List<RoleMemberScope>)baseDao.queryEntitys(rmHql);
									if (!CollectionUtils.isEmpty(rmLst)) {
										String userIds = "";
										int containUsr = 0;
										for (RoleMemberScope member : rmLst) {
											userIds += "," + member.getUser().getUserId();
											
											Iterator<OrgUser> ouIt = member.getUser().getOrgUsers().iterator();
											while (ouIt.hasNext()) {
												Organization usrOrg = ouIt.next().getOrganization();
												if (usrOrg.getOrgId() == orgId) {
													containUsr++;
												}
											}
										}
										
										// 该角色对应的权重
										Float percentage = NumberUtils.toFloat(gp.getPercentage());
										
										// 查询该部门对应的该指标对应的每一个角色的所有用户的评分合计
										String sumSql = " SELECT SUM(CAST(gr.SCORE AS DECIMAL(5,2))) score"
												+ " FROM T_GRADE_RECORD gr "
												+ " WHERE gr.ISDELETE = 0"
												+ " and gr.FK_CLASSIFY_ID = "  + classifyId
												+ " and gr.FK_ORG_ID = " + orgId
												+ " and gr.FK_USER_ID in (" + userIds.substring(1) + ")";
										List<Object> sumLst = (List<Object>)baseDao.executeNativeQuery(sumSql);
										
										if (!CollectionUtils.isEmpty(sumLst) && sumLst.get(0) != null) {
											defen += ((BigDecimal)sumLst.get(0)).floatValue() / (rmLst.size() - containUsr) * percentage;
										}
									}
								}
							}
							
							String updateHql = " update OrgAndClassify oc set oc.score = '" + String.format("%.2f", defen)
							    + "' where oc.classify.pkClassifyId = " + classifyId
							    + " and oc.org.orgId = " + orgId + " and oc.isDelete = 0";
							baseDao.executeHql(updateHql);
						}
					}
				}
			}
		}
		
		msg.put("flag", "success");
		return msg;
	}

	/******************部门评分明细数据查询********************/
	
	@Override
	public ListVo<DeptGradeDetailVo> queryDeptGradeDetail(Integer start, Integer limit, String electYear,
			String canpDeptId, String gradeDeptId, String cfId) throws Exception {
		
		ListVo<DeptGradeDetailVo> listVo = new ListVo<DeptGradeDetailVo>();
		
		String dgdHql = " from GradeRecord gr where gr.isDelete = 0 and gr.classify.isParticipation = 1";
		if (StringUtil.isNotBlank(electYear)) {
			dgdHql += " and gr.classify.electYear = '" + electYear + "'";
		}
		
		if (StringUtil.isNotBlank(canpDeptId) && !"0".equals(canpDeptId)) {
			dgdHql += " and gr.org.orgId = " + canpDeptId;
		}
		
		if (StringUtil.isNotBlank(gradeDeptId) && !"0".equals(gradeDeptId)) {
			dgdHql += " and gr.usrOrg.orgId = " + gradeDeptId;
		}
		
		if (StringUtil.isNotBlank(cfId) && !"0".equals(cfId)) {
			dgdHql += " and gr.classify.pkClassifyId = " + cfId;
		}
		
		int count = baseDao.queryTotalCount(dgdHql, new HashMap<String, Object>());
		
		dgdHql += " order by gr.pkGradeRecId";
		List<GradeRecord> grLst = (List<GradeRecord>)baseDao.queryEntitysByPage(start, limit, dgdHql, new HashMap<String, Object>());
		if (!CollectionUtils.isEmpty(grLst)) {
			DeptGradeDetailVo vo = null;
			List<DeptGradeDetailVo> voLst = new ArrayList<DeptGradeDetailVo>();
			for (GradeRecord gr : grLst) {
				vo = new DeptGradeDetailVo();
				
				vo.setGradeDetailId(gr.getPkGradeRecId());
				vo.setClassifyName(gr.getClassify().getName());
				vo.setName(gr.getIndex1().getName());
				if (gr.getIndex2() != null) {
					vo.setGradeIndex2Name(gr.getIndex2().getName());
				}
				
				vo.setCanpDept(gr.getOrg().getOrgName());
				vo.setScore(gr.getScore());
				vo.setGradeUsr(gr.getUser().getRealname());
				vo.setGradeUsrDept(gr.getUsrOrg().getOrgName());
				
				voLst.add(vo);
			}
			
			listVo.setList(voLst);
			listVo.setTotalSize(count);
		}
		
		return listVo;
	}

	/******************部门评分汇总数据查询********************/
	
	@Override
	public ListVo<DeptGradeDetailVo> queryDeptGradeSummarizing(Integer start, Integer limit, String electYear,
			String canpDeptId, String cfId) throws Exception {
		
		ListVo<DeptGradeDetailVo> listVo = new ListVo<DeptGradeDetailVo>();
		
		String hql = " from OrgAndClassify oc where oc.isDelete = 0 and oc.classify.isParticipation = 1";
		if (StringUtil.isNotBlank(electYear)) {
			hql += " AND oc.classify.electYear = '" + electYear + "'";
		}
		
		if (StringUtil.isNotBlank(canpDeptId) && !"0".equals(canpDeptId)) {
			hql += " and oc.org.orgId = " + canpDeptId;
		}
		
		if (StringUtil.isNotBlank(cfId) && !"0".equals(cfId)) {
			hql += " and oc.classify.pkClassifyId = " + cfId;
		}
		
		int count = baseDao.queryTotalCount(hql, new HashMap<String, Object>());
		
		hql += " order by oc.classify.name";
		List<OrgAndClassify> ocLst = (List<OrgAndClassify>)baseDao.queryEntitysByPage(start, limit, hql, new HashMap<String, Object>());
		if (!CollectionUtils.isEmpty(ocLst)) {
			DeptGradeDetailVo vo = null;
			List<DeptGradeDetailVo> voLst = new ArrayList<DeptGradeDetailVo>();
			for (OrgAndClassify oc : ocLst) {
				vo = new DeptGradeDetailVo();
				
				vo.setGradeDetailId(oc.getPkOrgAndClassifyId());
				vo.setScore(oc.getScore());
				vo.setClassifyName(oc.getClassify().getName());
				vo.setCanpDept(oc.getOrg().getOrgName());
				
				voLst.add(vo);
			}
			
			listVo.setList(voLst);
			listVo.setTotalSize(count);
		}
		
		return listVo;
	}

	/**
     * 对评分用户进行排序
     * 
     * @Title orderGradeUser
     * @author dong.he
     * @date 2014年12月25日
     * @param inxLst
     */
    private void orderGradeUser(List<UserVo> guLst){
        Collections.sort(guLst, new Comparator<UserVo>() {
            public int compare(UserVo gu1, UserVo gu2) {
                return NumberUtils.toInt(gu1.getFlag()) - NumberUtils.toInt(gu2.getFlag());
            }
        });
    }
	
	@Override
	public List<UserVo> showGradeUser(String electYear) throws Exception {
		List<UserVo> uvLst = new ArrayList<UserVo>();
		
		// 获取所有评分人
		if (StringUtil.isNotBlank(electYear)) {
			String gpHql = " from GradePercentage gp where gp.isDelete = 0"
					+ " and gp.classify.isDelete = 0 and gp.classify.enable = 0 and gp.classify.isParticipation = 1"
					+ " and gp.classify.electYear = '" + electYear + "'";
			List<GradePercentage> gpLst = (List<GradePercentage>)baseDao.queryEntitys(gpHql);
			if (!CollectionUtils.isEmpty(gpLst)) {
				Map<Integer, UserVo> allUser = new HashMap<Integer, UserVo>();
				UserVo vo = null;
				for (GradePercentage gp : gpLst) {
					// 查询角色下包含的用户（RoleMemberScope），进而通过ClassifyUser判断用户是否已提交部门评分
					String rmsHql = " from RoleMemberScope r where r.role.roleId = " + gp.getRole().getRoleId();
					List<RoleMemberScope> rmsLst = (List<RoleMemberScope>)baseDao.queryEntitys(rmsHql);
					if (!CollectionUtils.isEmpty(rmsLst)) {
						for (RoleMemberScope rms : rmsLst) {
							if (rms.getUser() != null) {
								vo = new UserVo();
								
								vo.setUserId(rms.getUser().getUserId());
								vo.setRealname(rms.getUser().getRealname());
								
								Iterator<OrgUser> ouIt = rms.getUser().getOrgUsers().iterator();
								String orgName = "";
								List<Integer> selfOrgIds = new ArrayList<Integer>();
								while (ouIt.hasNext()) {
									Organization usrOrg = ouIt.next().getOrganization();
									orgName += "," + usrOrg.getOrgName();
									selfOrgIds.add(usrOrg.getOrgId());
								}
								if (StringUtil.isNotBlank(orgName)) {
									vo.setOrgName(orgName.substring(1));
								}
								vo.setFlag("0");
								
								// 查询ClassifyUser，判断是否提交
								String cuHql = " from ClassifyUser cu where cu.isDelete = 0 and cu.hasSubmit = 1"
										+ " and cu.classify.pkClassifyId = " + gp.getClassify().getPkClassifyId()
										+ " and cu.user.userId = " + rms.getUser().getUserId();
								
								int hasSubmit = baseDao.queryTotalCount(cuHql, new HashMap<String, Object>());
								
								// 获取该指标包含的所有部门（如果该指标只包含自己所在部门，默认已提交）
								if (gp.getClassify().getOrgCfs() != null && gp.getClassify().getOrgCfs().size() == 1) {
									Iterator<OrgAndClassify> ocIt = gp.getClassify().getOrgCfs().iterator();
									if (selfOrgIds.contains(ocIt.next().getOrg().getOrgId())) {
										vo.setFlag("1");
									}
								}
								
								if (hasSubmit > 0) {
									vo.setFlag("1");
								}
								
								allUser.put(rms.getUser().getUserId(), vo);
							}
						}
					}
				}
				
				if (allUser.size() > 0) {
					for (Entry<Integer, UserVo> entry : allUser.entrySet()) {
						uvLst.add(entry.getValue());
					}
					
					// 根据flag排序
					orderGradeUser(uvLst);
				}
			}
		}
		
		return uvLst;
	}

    /******************部门最终得分********************/
    
    /**
     * 查询部门最终得分
     * 
     * @param electYear 参评年份
     * @return
     * @throws Exception
     */
	@Override
	public List<DeptGradeDetailVo> queryDeptFinalScore(String electYear) throws Exception {
		List<DeptGradeDetailVo> voLst = new ArrayList<DeptGradeDetailVo>();
		
		// 首先查询所有部门
		String orgHql = " from Organization o where o.enable = 0"
				+ " and o.status = 0 and o.organization is not null"
				+ " order by o.disOrder, o.orgId";
		List<Organization> orgLst = (List<Organization>)baseDao.queryEntitys(orgHql);
		if (!CollectionUtils.isEmpty(orgLst)) {
			DeptGradeDetailVo vo = null;
			for (Organization org : orgLst) {
				// 查询指标分类与部门对应
				String ocHql = " from OrgAndClassify oc where oc.isDelete = 0"
						+ " and oc.org.orgId = " + org.getOrgId()
						+ " and oc.classify.scoreType.dictCode = 'INXSCORE'";
				
				String bdHql = " from OrgAndClassify oc where oc.isDelete = 0"
						+ " and oc.org.orgId = " + org.getOrgId()
						+ " and oc.classify.scoreType.dictCode = 'BUILDSCORE'";
				
				if (StringUtil.isNotBlank(electYear)) {
					ocHql += " and oc.classify.electYear = '" + electYear + "'";
					bdHql += " and oc.classify.electYear = '" + electYear + "'";
				}
				
				List<OrgAndClassify> ocLst = (List<OrgAndClassify>)baseDao.queryEntitys(ocHql);
				List<OrgAndClassify> bdLst = (List<OrgAndClassify>)baseDao.queryEntitys(bdHql);
				
				if (!CollectionUtils.isEmpty(ocLst)) {
					for (OrgAndClassify oc : ocLst) {
						vo = new DeptGradeDetailVo();
						
						//vo.setGradeDetailId(oc.getPkOrgAndClassifyId());
						vo.setCanpDept(oc.getOrg().getOrgName());
						vo.setCanpDeptId(oc.getOrg().getOrgId());
						vo.setClassifyName(oc.getClassify().getName());
						vo.setClassifyId(oc.getClassify().getPkClassifyId());
						vo.setScore(oc.getScore());
						
						if (StringUtil.isNotBlank(oc.getScore()) && !"0".equals(oc.getScore())) {
							vo.setIsParticipation(1);
						}
						else {
							vo.setIsParticipation(0);
						}
						
						vo.setPercentage(oc.getPercentage());
						if (!CollectionUtils.isEmpty(bdLst)) {
							vo.setBuildScore(bdLst.get(0).getScore());
						}
						
						// 查询部门最终得分
						String fsHql = " from FinalScore fs where fs.org.orgId = " + org.getOrgId();
						if (StringUtil.isNotBlank(electYear)) {
							fsHql += " and fs.electYear = '" + electYear + "'";
						}
						List<FinalScore> fsLst = (List<FinalScore>)baseDao.queryEntitys(fsHql);
						if (!CollectionUtils.isEmpty(fsLst)) {
							if (!"0".equals(fsLst.get(0).getSumScore())) {
								vo.setSumScore(fsLst.get(0).getSumScore());
							}
							vo.setFinalScore(fsLst.get(0).getScore());
						}
						
						voLst.add(vo);
					}
				}
				else {
					if (!CollectionUtils.isEmpty(bdLst)) {
						vo = new DeptGradeDetailVo();
						vo.setCanpDept(org.getOrgName());
						vo.setCanpDeptId(org.getOrgId());
						vo.setBuildScore(bdLst.get(0).getScore());
						vo.setIsParticipation(0);
						
						// 查询部门最终得分
						String fsHql = " from FinalScore fs where fs.org.orgId = " + org.getOrgId();
						if (StringUtil.isNotBlank(electYear)) {
							fsHql += " and fs.electYear = '" + electYear + "'";
						}
						List<FinalScore> fsLst = (List<FinalScore>)baseDao.queryEntitys(fsHql);
						if (!CollectionUtils.isEmpty(fsLst)) {
							if (!"0".equals(fsLst.get(0).getSumScore())) {
								vo.setSumScore(fsLst.get(0).getSumScore());
							}
							vo.setFinalScore(fsLst.get(0).getScore());
						}
						
						voLst.add(vo);
					}
				}
			}
		}
		
		return voLst;
	}

	@Override
	public void saveEditScore(String cfId, String orgId, String score, String percentage, String flag) throws Exception {
		if ("percentage".equals(flag)) {
			if (StringUtil.isNotBlank(cfId) && StringUtil.isNotBlank(orgId) && StringUtil.isNotBlank(percentage)) {
				String update = " update OrgAndClassify oc set oc.percentage = '" + percentage + "'"
						+ " where oc.isDelete = 0 and oc.classify.pkClassifyId = " + cfId
						+ " and oc.org.orgId = " + orgId;
				
				baseDao.executeHql(update);
			}
		}
		else {
			if (StringUtil.isNotBlank(cfId) && StringUtil.isNotBlank(orgId) && StringUtil.isNotBlank(score)) {
				String update = " update OrgAndClassify oc set oc.score = '" + score + "'"
						+ " where oc.isDelete = 0 and oc.classify.pkClassifyId = " + cfId
						+ " and oc.org.orgId = " + orgId;
				
				baseDao.executeHql(update);
			}
		}
	}

	@Override
	public void saveFinalScore(String orgId, String sumScore, String finalScore, String electYear) throws Exception {
		if (StringUtil.isNotBlank(orgId) && StringUtil.isNotBlank(sumScore) && StringUtil.isNotBlank(finalScore) && StringUtil.isNotBlank(electYear)) {
			String hql = " from FinalScore fs where fs.electYear = '" + electYear + "'"
					+ " and fs.org.orgId = " + orgId;
			List<FinalScore> fsLst = (List<FinalScore>)baseDao.queryEntitys(hql);
			FinalScore fs = null;
			if (!CollectionUtils.isEmpty(fsLst)) {
				fs = fsLst.get(0);
			}
			else {
				fs = new FinalScore();
				fs.setElectYear(electYear);
				Organization org = (Organization)baseDao.queryEntityById(Organization.class, NumberUtils.toInt(orgId));
				fs.setOrg(org);
			}
			
			fs.setSumScore(sumScore);
			fs.setScore(finalScore);
			
			baseDao.saveOrUpdate(fs);
		}
	}
}
