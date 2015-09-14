package com.xx.system.attach.service;

import java.util.List;

import com.xx.system.attach.entity.Attach;
import com.xx.system.attach.vo.AttachVo;

public interface IAttachService {
	Attach saveAttach(Attach attach);
	
	Attach getAttach(Integer id);
	
	void removeAttach(Integer id);
	
	void updateAttach(Attach attach);
	
	/**
	 * 根据附件组ID取得所有附件
	 * @param attachGroupId
	 * @return
	 */
	List<AttachVo> getAttachsByGroupId(Integer attachGroupId);

	/**
	 * 根据多个ID取得附件列表
	 * @param attachIds   多个ID以逗号隔开
	 * @return
	 */
	List<Attach> getAttachsByIds(String attachIds);
}
