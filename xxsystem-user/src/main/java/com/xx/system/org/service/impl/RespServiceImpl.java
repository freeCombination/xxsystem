package com.xx.system.org.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.org.entity.Duty;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.entity.Responsibilities;
import com.xx.system.org.service.IOrgService;
import com.xx.system.org.service.IRespService;
import com.xx.system.org.vo.DutyVo;
import com.xx.system.org.vo.RespVo;

/**
 * 岗位逻辑接口实现
 * 
 * @version V1.20,2013-11-25 下午4:02:53
 * @see [相关类/方法]
 */
@Service("respService")
public class RespServiceImpl implements IRespService {
    
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    @Resource
    public IOrgService organizationService;

	@Override
	public List<RespVo> getAllResp(Integer orgId) throws BusinessException {
		List<RespVo> voLst = new ArrayList<RespVo>();
		
		String respHql = " from Responsibilities r where r.isDelete = 0 and r.enable = 0";
		if (orgId != null && orgId != 0) {
			respHql += " and r.organization.orgId = " + orgId;
		}
		
		List<Responsibilities> respLst = (List<Responsibilities>)baseDao.queryEntitys(respHql);
		if (!CollectionUtils.isEmpty(respLst)) {
			RespVo vo = null;
			for (Responsibilities r : respLst) {
				vo = new RespVo();
				
				vo.setRespId(r.getPkRespId());
				vo.setName(r.getName());
				
				voLst.add(vo);
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
            countHql.append(" from Responsibilities r ");
            countHql.append(" where r.number = '");
            countHql.append(number + "'");
            
            totleSize = baseDao.queryTotalCount(countHql.toString(),
                new HashMap<String, Object>());
        }
        
        if (totleSize > 0) {
            vaildator.put("success", true);
            vaildator.put("valid", false);
            vaildator.put("reason", "该岗位编号已被占用！");
        }
        else {
            vaildator.put("success", true);
            vaildator.put("valid", true);
            vaildator.put("reason", "");
        }
        return vaildator;
	}

	@Override
	public ListVo<RespVo> getRespList(int start, int limit, RespVo respVo) throws BusinessException {
		ListVo<RespVo> lstVo = new ListVo<RespVo>();
		String respHql = " from Responsibilities r where r.isDelete = 0";
		if (respVo != null) {
			if (StringUtil.isNotBlank(respVo.getNumber())) {
				respHql += " and r.number like '%" + respVo.getNumber() + "%'";
			}
			
			if (StringUtil.isNotBlank(respVo.getName())) {
				respHql += " and r.name like '%" + respVo.getName() + "%'";
			}
			
			if (respVo.getOrgId() != null && respVo.getOrgId() != 0) {
				respHql += " and r.organization.orgId = " + respVo.getOrgId();
			}
		}
		
		int count = baseDao.queryTotalCount(respHql, new HashMap<String, Object>());
		
		respHql += " order by r.organization.orgName, r.name";
		List<Responsibilities> respLst = (List<Responsibilities>)baseDao.queryEntitysByPage(start, limit, 
				respHql, new HashMap<String, Object>());
		
		RespVo vo = null;
		List<RespVo> voLst = new ArrayList<RespVo>();
		
		if (!CollectionUtils.isEmpty(respLst)) {
			for (Responsibilities resp : respLst) {
				vo = new RespVo();
				
				vo.setRespId(resp.getPkRespId());
				vo.setNumber(resp.getNumber());
				vo.setName(resp.getName());
				vo.setOrgId(resp.getOrganization().getOrgId());
				vo.setOrgName(resp.getOrganization().getOrgName());
				vo.setRank(resp.getRank());
				vo.setEnable(resp.getEnable());
				
				voLst.add(vo);
			}
		}
		lstVo.setList(voLst);
		lstVo.setTotalSize(count);
		
		return lstVo;
	}

	@Override
	public void addResp(RespVo respVo, List<DutyVo> dvoLst) throws BusinessException {
		if (respVo != null) {
			Responsibilities resp = new Responsibilities();
			resp.setNumber(respVo.getNumber());
			resp.setName(respVo.getName());
			if (respVo.getRank() != null) {
				resp.setRank(respVo.getRank());
			}
			resp.setEnable(1);
			resp.setIsDelete(0);
			
			Organization org = organizationService.getOrgById(respVo.getOrgId());
			resp.setOrganization(org);
			
			baseDao.save(resp);
			
			if (!CollectionUtils.isEmpty(dvoLst)) {
				Duty duty = null;
				List<Duty> dutyLst = new ArrayList<Duty>();
				for (DutyVo vo : dvoLst) {
					duty = new Duty();
					
					duty.setNumber(vo.getNumber());
					duty.setDutyContent(vo.getDutyContent());
					duty.setDutyType(vo.getDutyType());
					duty.setResponsibilities(resp);
					duty.setIsDelete(0);
					
					dutyLst.add(duty);
				}
				baseDao.saveAll(dutyLst);
			}
		}
	}

	@Override
	public void updateResp(RespVo respVo, List<DutyVo> dvoLst) throws BusinessException {
		if (respVo != null) {
			String respHql = " from Responsibilities r where r.isDelete = 0 and r.enable = 1"
					+ " and r.pkRespId = " + respVo.getRespId();
			List<Responsibilities> lst = (List<Responsibilities>)baseDao.queryEntitys(respHql);
			Responsibilities resp = null;
			if (!CollectionUtils.isEmpty(lst)) {
				resp = lst.get(0);
				resp.setNumber(respVo.getNumber());
				resp.setName(respVo.getName());
				if (respVo.getRank() != null) {
					resp.setRank(respVo.getRank());
				}
				resp.setEnable(1);
				resp.setIsDelete(0);
				
				Organization org = organizationService.getOrgById(respVo.getOrgId());
				resp.setOrganization(org);
				
				baseDao.update(resp);
				
				// 保存岗位职责前删除关联的所有职责，实现更新的功能
				String delHql = " delete from Duty d where d.responsibilities.pkRespId = " + respVo.getRespId();
				baseDao.executeHql(delHql);
				
				if (!CollectionUtils.isEmpty(dvoLst)) {
					Duty duty = null;
					List<Duty> dutyLst = new ArrayList<Duty>();
					for (DutyVo vo : dvoLst) {
						duty = new Duty();
						
						duty.setNumber(vo.getNumber());
						duty.setDutyContent(vo.getDutyContent());
						duty.setDutyType(vo.getDutyType());
						duty.setResponsibilities(resp);
						duty.setIsDelete(0);
						
						dutyLst.add(duty);
					}
					baseDao.saveAll(dutyLst);
				}
			}
		}
	}

	@Override
	public RespVo getRespById(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delResps(String ids) throws BusinessException {
		if (StringUtil.isNotBlank(ids)) {
			// 先删除岗位职责
			String delDuty = " delete from Duty d where d.responsibilities.pkRespId in (" + ids + ")";
			baseDao.executeHql(delDuty);
			
			// 然后删除岗位
			String delResp = " delete from Responsibilities r where r.pkRespId in (" + ids + ")";
			baseDao.executeHql(delResp);
		}
		return "success";
	}

	@Override
	public List<DutyVo> getDutyListByRespId(Integer respId) throws BusinessException {
		List<DutyVo> lstVo = new ArrayList<DutyVo>();
		if (respId != null && respId != 0) {
			String dutyHql = " from Duty d where d.isDelete = 0 and d.responsibilities.pkRespId = " + respId;
			List<Duty> dutyLst = (List<Duty>)baseDao.queryEntitys(dutyHql);
			DutyVo vo = null;
			if (!CollectionUtils.isEmpty(dutyLst)) {
				for (Duty d : dutyLst) {
					vo = new DutyVo();
					
					vo.setDutyId(d.getPkDutyId());
					vo.setDutyContent(d.getDutyContent());
					vo.setDutyType(d.getDutyType());
					vo.setNumber(d.getNumber());
					vo.setRespId(d.getResponsibilities().getPkRespId());
					vo.setRespName(d.getResponsibilities().getName());
					
					lstVo.add(vo);
				}
			}
		}
		
		return lstVo;
	}

	@Override
	public void lockUnLock(Integer respId) throws BusinessException {
		if (respId != null && respId != 0) {
			Responsibilities resp = (Responsibilities)baseDao.queryEntityById(Responsibilities.class, respId);
			
			if (resp != null) {
				if (resp.getEnable() == 1) {
					resp.setEnable(0);
				}
				else {
					resp.setEnable(1);
				}
				
				baseDao.update(resp);
			}
		}
	}
}
