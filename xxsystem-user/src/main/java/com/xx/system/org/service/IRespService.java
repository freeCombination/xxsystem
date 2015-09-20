package com.xx.system.org.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.org.vo.DutyVo;
import com.xx.system.org.vo.RespVo;

/**
 * 定义岗位逻辑接口
 * 
 * @version V1.20,2013-11-25 下午3:47:10
 * @see [相关类/方法]
 * @since V1.20
 */
public interface IRespService {
    /**
     * 根据部门id查询所有岗位
     * 
     * @Title getAllResp
     * @date 2013-11-25
     * @return List<RespVo>
     */
    public List<RespVo> getAllResp(Integer orgId)
        throws BusinessException;
    
    /**
     * 检查岗位编号的唯一性
     * 
     * @Title checkNumber
     * @date 2013-11-25
     * @param number 岗位编号
     * @return boolean 返回true表示不存在
     */
    public Map<String, Object> checkNumber(String number)
        throws BusinessException;
    
    /**
     * 取得岗位分页列表
     * 
     * @Title getRespList
     * @date 2013-11-25
     * @param start 查询起始位置
     * @param limit 每页限制条数
     * @param respVo 岗位对象
     * @return PagerVo<RespVo>
     */
    public ListVo<RespVo> getRespList(int start, int limit, RespVo respVo)
        throws BusinessException;
    
    /**
     * 添加岗位
     * 
     * @Title addResp
     * @date 2013-11-25
     * @param respVo 岗位对象
     */
    public void addResp(RespVo respVo, List<DutyVo> dvoLst)
        throws BusinessException;
    
    /**
     * 更新岗位
     * 
     * @Title updateResp
     * @date 2013-11-25
     * @param respVo 岗位对象
     */
    public void updateResp(RespVo respVo)
        throws BusinessException;
    
    /**
     * 根据ID取得岗位
     * 
     * @Title getRespById
     * @date 2013-11-25
     * @param id 岗位主键
     * @return RespVo
     */
    public RespVo getRespById(int id)
        throws BusinessException;
    
    /**
     * 批量删除岗位
     * 
     * @param ids 岗位ids
     * @return String msg
     * @throws BusinessException
     */
    public String delResps(String ids)
        throws BusinessException;
    
    /**
     * 根据岗位id查询职责
     * 
     * @param respId
     * @return
     * @throws BusinessException
     */
    public List<DutyVo> getDutyListByRespId(Integer respId) throws BusinessException;
}
