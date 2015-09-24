package com.xx.system.deptgrade.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.deptgrade.vo.IndexClassifyVo;

/**
 * 指标管理逻辑接口
 * 
 * @version V1.20,2013-11-25 下午3:47:10
 * @see [相关类/方法]
 * @since V1.20
 */
public interface IIndexManageService {
    /**
     * 查询所有指标分类
     * 
     * @Title getAllClassifies
     * @date 2013-11-25
     * @return List<IndexClassifyVo>
     */
    public List<IndexClassifyVo> getAllClassifies()
        throws BusinessException;
    
    /**
     * 检查指标分类编号的唯一性
     * 
     * @Title checkNumber
     * @date 2013-11-25
     * @param number 岗位编号
     * @return boolean 返回true表示不存在
     */
    public Map<String, Object> checkNumber(String number)
        throws BusinessException;
    
    /**
     * 取得指标分类分页列表
     * 
     * @Title getClassifyList
     * @date 2013-11-25
     * @param start 查询起始位置
     * @param limit 每页限制条数
     * @param vo 指标分类对象
     * @return ListVo<RespVo>
     */
    public ListVo<IndexClassifyVo> getClassifyList(int start, int limit, IndexClassifyVo vo)
        throws BusinessException;
    
    /**
     * 添加标分类
     * 
     * @Title addClassify
     * @date 2013-11-25
     * @param vo 指标分类对象VO
     */
    public void addClassify(IndexClassifyVo vo)
        throws BusinessException;
    
    /**
     * 更新标分类
     * 
     * @Title updateClassify
     * @date 2013-11-25
     * @param vo 指标分类对象VO
     */
    public void updateClassify(IndexClassifyVo vo)
        throws BusinessException;
    
    /**
     * 批量删除指标分类
     * 
     * @param ids 指标分类ids
     * @return String msg
     * @throws BusinessException
     */
    public String delClassifies(String ids)
        throws BusinessException;
    
    /**
     * 锁定解锁指标分类
     * 
     * @param classifyId 指标分类ID
     * @throws BusinessException
     */
    public void lockUnLock(Integer classifyId) throws BusinessException;
}