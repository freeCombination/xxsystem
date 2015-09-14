package com.xx.system.attach.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xx.system.attach.entity.AttachGroup;
import com.xx.system.attach.service.IAttachGroupService;
import com.xx.system.common.dao.IBaseDao;

@Service("attachGroupService")
public class AttachGroupServiceImpl implements IAttachGroupService {
	
	 @Autowired
	 @Qualifier("baseDao")
	 private IBaseDao baseDao;

	 public void setBaseDao(IBaseDao baseDao) {
	        this.baseDao = baseDao;
	    }


	@Override
	public AttachGroup saveAttachGroup(AttachGroup attachGroup) {
		return (AttachGroup) baseDao.save(attachGroup);
	}

	@Override
	public AttachGroup getAttachGroup(Integer id) {
		return (AttachGroup) baseDao.queryEntityById(AttachGroup.class,id);
	}

	@Override
	public void removeAttachGroup(Integer id) {
		baseDao.deleteEntityById(AttachGroup.class, id);
	}

}
