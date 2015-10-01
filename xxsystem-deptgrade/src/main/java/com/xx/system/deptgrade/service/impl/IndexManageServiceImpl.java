package com.xx.system.deptgrade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.deptgrade.entity.GradeIndex;
import com.xx.system.deptgrade.entity.GradePercentage;
import com.xx.system.deptgrade.entity.IndexClassify;
import com.xx.system.deptgrade.entity.OrgAndClassify;
import com.xx.system.deptgrade.service.IIndexManageService;
import com.xx.system.deptgrade.vo.GradeIndexVo;
import com.xx.system.deptgrade.vo.IndexClassifyVo;
import com.xx.system.deptgrade.vo.PercentageVo;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.service.IOrgService;
import com.xx.system.role.entity.Role;
import com.xx.system.role.vo.RoleVo;

/**
 * 指标逻辑接口实现
 * 
 * @version V1.20,2013-11-25 下午4:02:53
 * @see [相关类/方法]
 */
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
	public List<IndexClassifyVo> getAllClassifies() throws BusinessException {
		String cfHql = " from IndexClassify i where i.isDelete = 0 and i.enable = 0 order by i.name";
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
				icvo.setHasSubmit(cf.getHasSubmit());
				icvo.setEnable(cf.getEnable());
				
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

	/************指标管理*************/
	
	@Override
	public List<GradeIndexVo> getAllIndex(Integer cfId) throws BusinessException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PercentageVo> getBaseListByCfId(Integer cfId) throws BusinessException {
		List<PercentageVo> voLst = new ArrayList<PercentageVo>();
		
		if (cfId != null && cfId != 0) {
			// 查询指标类型对应的权重基础信息
			String gpHql = " from GradePercentage g where isDelete = 0 "
					+ " and g.classify.pkClassifyId = " + cfId;
			List<GradePercentage> perLst = (List<GradePercentage>)baseDao.queryEntitys(gpHql);
			if (!CollectionUtils.isEmpty(perLst)) {
				PercentageVo vo = null;
				
				for (GradePercentage gp : perLst) {
					vo = new PercentageVo();
					vo.setPerId(gp.getPkPerId());
					vo.setReceiptsNum(gp.getReceiptsNum());
					vo.setRoleId(gp.getRole().getRoleId());
					vo.setRoleName(gp.getRole().getRoleName());
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
}
