package com.xx.system.deptgrade.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.deptgrade.service.IIndexManageService;
import com.xx.system.deptgrade.vo.IndexClassifyVo;
import com.xx.system.org.service.IOrgService;

/**
 * 指标逻辑接口实现
 * 
 * @version V1.20,2013-11-25 下午4:02:53
 * @see [相关类/方法]
 */
@Service("indexManageServiceImpl")
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListVo<IndexClassifyVo> getClassifyList(int start, int limit, IndexClassifyVo vo) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addClassify(IndexClassifyVo vo) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClassify(IndexClassifyVo vo) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String delClassifies(String ids) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lockUnLock(Integer classifyId) throws BusinessException {
		// TODO Auto-generated method stub
		
	}
}
