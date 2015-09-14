package com.xx.system.attach.service;

import com.xx.system.attach.entity.AttachGroup;

public interface IAttachGroupService {
	AttachGroup saveAttachGroup(AttachGroup attachGroup);
	
	AttachGroup getAttachGroup(Integer id);
	
	void removeAttachGroup(Integer id);
	
}
