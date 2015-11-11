package com.xx.system.deptgrade.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.deptgrade.vo.DeptGradeDetailVo;
import com.xx.system.deptgrade.vo.GradeIndexVo;
import com.xx.system.deptgrade.vo.IndexClassifyVo;
import com.xx.system.deptgrade.vo.PercentageVo;
import com.xx.system.org.vo.OrgVo;
import com.xx.system.role.vo.RoleVo;
import com.xx.system.user.entity.User;
import com.xx.system.user.vo.UserVo;

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
    public List<IndexClassifyVo> getAllClassifies(String electYear, String participation)
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
    
    /**
     * 复制评分基础数据
     * 
     * @return
     * @throws Exception
     */
    public Map<String, String> copyBaseData() throws Exception;
    
    /************指标管理*************/
    
    /**
     * 根据指标分类id查询所有指标
     * 
     * @Title getAllIndex
     * @date 2013-11-25
     * @return List<GradeIndexVo>
     */
    public List<GradeIndexVo> getAllIndex(Integer cfId)
        throws BusinessException;
    
    /**
     * 检查指标编号的唯一性
     * 
     * @Title checkIndexNumber
     * @date 2013-11-25
     * @param number 指标编号
     * @return boolean 返回true表示不存在
     */
    public Map<String, Object> checkIndexNumber(String number)
        throws BusinessException;
    
    /**
     * 取得指标分页列表
     * 
     * @Title getIndexList
     * @date 2013-11-25
     * @param start 查询起始位置
     * @param limit 每页限制条数
     * @param indexVo 指标对象
     * @return ListVo<GradeIndexVo>
     */
    public ListVo<GradeIndexVo> getIndexList(int start, int limit, GradeIndexVo indexVo)
        throws BusinessException;
    
    /**
     * 添加指标
     * 
     * @Title addIndex
     * @date 2013-11-25
     * @param indexVo 指标对象
     */
    public void addIndex(GradeIndexVo indexVo, List<GradeIndexVo> indexLst)
        throws BusinessException;
    
    /**
     * 更新指标
     * 
     * @Title updateIndex
     * @date 2013-11-25
     * @param indexVo 指标对象
     */
    public void updateIndex(GradeIndexVo indexVo, List<GradeIndexVo> indexLst)
        throws BusinessException;
    
    /**
     * 批量删除指标
     * 
     * @param ids 指标ids
     * @return String msg
     * @throws BusinessException
     */
    public String delIndexes(String ids)
        throws BusinessException;
    
    /**
     * 根据一级指标id查询二级指标
     * 
     * @param index1Id 一级指标id
     * @return
     * @throws BusinessException
     */
    public List<GradeIndexVo> getIndex2ListByIndex1Id(Integer index1Id) throws BusinessException;
    
    /************权重管理*************/
    
    /**
     * 检查单据编号的唯一性
     * 
     * @Title checkreceiptsNum
     * @date 2013-11-25
     * @param number 单据编号
     * @return boolean 返回true表示不存在
     */
    public Map<String, Object> checkreceiptsNum(String number)
        throws BusinessException;
    
    /**
     * 根据指标分类id查询权重管理所需基础数据
     * 
     * @param cfId 指标分类id
     * @return
     * @throws BusinessException
     */
    public List<PercentageVo> getBaseListByCfId(Integer cfId) throws BusinessException;
    
    /**
     * 保存权重设置
     * 
     * @param voLst 权重详请情集合
     * @throws BusinessException
     */
    public void savePercentage(List<PercentageVo> voLst) throws BusinessException;
    
    /**
     * 获取所有角色
     * 
     * @return
     * @throws BusinessException
     */
    public List<RoleVo> getAllRole(String roleType) throws BusinessException;
    
    /************部门评分*************/
    
    /**
     * 查询指标分类用于部门评分
     * 
     * @Title getClassifyListForGrade
     * @date 2013-11-25
     * @param vo 指标分类对象
     * @return List<RespVo>
     */
    public List<IndexClassifyVo> getClassifyListForGrade(IndexClassifyVo vo, User currUsr)
        throws Exception;
    
    /**
     * 查询用于评分部门
     * 
     * @Title getOrgListForGrade
     * @date 2013-11-25
     * @param vo 指标分类对象
     * @return List<RespVo>
     */
    public List<OrgVo> getOrgListForGrade(IndexClassifyVo vo, User currUsr)
        throws Exception;
    
    /**
     * 根据指标分类id查询指标
     * 
     * @param cfId 指标分类id
     * @return
     * @throws Exception
     */
    public List<GradeIndexVo> getIndexListForGrade(Integer cfId, User currUsr) throws Exception;
    
    /**
     * 保存部门评分
     * 
     * @param defen 得分数据
     * @param currUsr 评分人
     * @throws Exception
     */
    public void saveDeptGrade(String defen, User currUsr) throws Exception;
    
    /**
     * 提交部门评分
     * 
     * @param cfIds
     * @param electYear 参评年份
     * @return
     * @throws Exception
     */
    public Map<String, String> submitDeptGrade(String cfIds, String electYear, User currUsr) throws Exception;
    
    
    /******************部门评分明细数据查询********************/
    
    /**
     * 查询部门评分明细
     * 
     * @param electYear 参评年份
     * @param canpDeptId 参评部门
     * @param gradeDeptId 评分部门
     * @return
     * @throws Exception
     */
    public ListVo<DeptGradeDetailVo> queryDeptGradeDetail(Integer start, Integer limit, 
    		String electYear, String canpDeptId, String gradeDeptId, String cfId) throws Exception;
    
    /******************部门评分汇总数据查询********************/
    
    /**
     * 查询部门评分汇总
     * 
     * @param electYear 参评年份
     * @param canpDeptId 参评部门
     * @return
     * @throws Exception
     */
    public ListVo<DeptGradeDetailVo> queryDeptGradeSummarizing(Integer start, Integer limit, 
    		String electYear, String canpDeptId, String cfId) throws Exception;
    
    /**
     * 显示评分用户
     * 
     * @param electYear 参评年份
     * @return
     * @throws Exception
     */
    public List<UserVo> showGradeUser(String electYear) throws Exception;
    
    /******************部门最终得分********************/
    
    /**
     * 查询部门最终得分
     * 
     * @param electYear 参评年份
     * @return
     * @throws Exception
     */
    public List<DeptGradeDetailVo> queryDeptFinalScore(String electYear) throws Exception;
    
    /**
     * 保存指标编辑得分和权重
     * 
     * @param cfId 指标分类id
     * @param orgId 部门id
     * @param score 得分
     * @param percentage 权重
     * @param flag 指标得分或权重标志
     * @throws Exception
     */
    public void saveEditScore(String cfId, String orgId, String score, String percentage, String flag) throws Exception;
    
    /**
     * 保存部门最终得分
     * 
     * @param orgId 部门id
     * @param finalScore 最终得分
     * @param electYear 参评年月
     * @throws Exception
     */
    public void saveFinalScore(String orgId, String finalScore, String electYear) throws Exception;
    
    /**
     * 保存指标得分小计或季度得分小计
     * 
     * @param orgId 部门id
     * @param sumScore 指标得分小计或季度得分小计
     * @param electYear 参评年月
     * @param flag 指标得分或季度得分小计标志
     * @throws Exception
     */
    public void saveSumScore(String orgId, String sumScore, String electYear, String flag) throws Exception;
    
    /**
     * 保存季度得分和权重
     * 
     * @param orgId 部门id
     * @param score 季度得分
     * @param percentage 季度得分权重
     * @param electYear 参评年月
     * @param flag 季度得分或权重标志
     * @throws Exception
     */
    public void saveJdEditScore(String orgId, String score, String percentage, String electYear, String flag) throws Exception;
}
