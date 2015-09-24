package com.xx.system.deptgrade.action;

import javax.annotation.Resource;

import com.xx.system.common.action.BaseAction;
import com.xx.system.deptgrade.service.IIndexManageService;
import com.xx.system.deptgrade.vo.IndexClassifyVo;

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
    public IIndexManageService indexManageServiceImpl;
	
	/**
     * @Fields classifyVo : 指标分类对象vo
     */
    private IndexClassifyVo classifyVo;

	public IndexClassifyVo getClassifyVo() {
		return classifyVo;
	}

	public void setClassifyVo(IndexClassifyVo classifyVo) {
		this.classifyVo = classifyVo;
	}

	
}
