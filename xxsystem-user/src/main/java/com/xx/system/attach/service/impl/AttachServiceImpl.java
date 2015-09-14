package com.xx.system.attach.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xx.system.attach.entity.Attach;
import com.xx.system.attach.entity.AttachGroup;
import com.xx.system.attach.service.IAttachService;
import com.xx.system.attach.vo.AttachVo;
import com.xx.system.common.dao.IBaseDao;

@Service("attachService")
public class AttachServiceImpl implements IAttachService {
	

	@Autowired
	@Qualifier("baseDao")
	private IBaseDao baseDao;

	 public void setBaseDao(IBaseDao baseDao) {
	 	this.baseDao = baseDao;
	 }
	@Override
	public Attach saveAttach(Attach attach) {
		return (Attach) baseDao.save(attach);
	}

	@Override
	public Attach getAttach(Integer id) {
		return (Attach) baseDao.queryEntityById(Attach.class, id);
	}

	@Override
	public void removeAttach(Integer id) {
		baseDao.deleteEntityById(Attach.class, id);
	}

	@Override
	public void updateAttach(Attach attach){
		baseDao.update(attach);
	}
	
	@Override
	public List<AttachVo> getAttachsByGroupId(Integer attachGroupId){
		AttachGroup attachGroup = (AttachGroup) baseDao.queryEntityById(AttachGroup.class, attachGroupId);
		List<AttachVo> attachVoList = new ArrayList<AttachVo>();
		
		if(attachGroup != null){
			for(Attach a : attachGroup.getAttachs()){
				attachVoList.add(new AttachVo(a));
			}
		}
		return attachVoList;
	}

	@Override
	public List<Attach> getAttachsByIds(String attachIds){
		return baseDao.query("from Attach a where a.attachId in ("+attachIds+") order by a.submitDate desc",null,0,20);
	}

}
