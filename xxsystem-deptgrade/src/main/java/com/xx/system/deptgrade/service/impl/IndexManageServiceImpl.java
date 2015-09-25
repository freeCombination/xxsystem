package com.xx.system.deptgrade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.deptgrade.entity.IndexClassify;
import com.xx.system.deptgrade.entity.OrgAndClassify;
import com.xx.system.deptgrade.service.IIndexManageService;
import com.xx.system.deptgrade.vo.IndexClassifyVo;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.entity.Responsibilities;
import com.xx.system.org.service.IOrgService;

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
		// TODO Auto-generated method stub
		return null;
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
		}
		
		int count = baseDao.getTotalCount(cfHql, new HashMap<String, Object>());
		
		cfHql += " order by i.name";
		List<IndexClassify> respLst = (List<IndexClassify>)baseDao.queryEntitysByPage(start, limit, 
				cfHql, new HashMap<String, Object>());
		
		IndexClassifyVo icvo = null;
		List<IndexClassifyVo> voLst = new ArrayList<IndexClassifyVo>();
		
		if (!CollectionUtils.isEmpty(respLst)) {
			for (IndexClassify cf : respLst) {
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
}
