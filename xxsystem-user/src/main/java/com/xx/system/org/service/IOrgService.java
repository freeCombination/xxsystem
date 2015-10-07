package com.xx.system.org.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.PagerVo;
import com.xx.system.common.vo.TreeNode;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.vo.OrgVo;

/**
 * 定义组织逻辑接口
 * 
 * @version V1.20,2013-11-25 下午3:47:10
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public interface IOrgService {
    /**
     * 取得所有的组织列表,不包含禁用的和已删除的
     * 
     * @Title getAllOrg
     * @author wanglc
     * @date 2013-11-25
     * @return List<Organization>
     */
    public List<Organization> getAllOrg()
        throws BusinessException;
    
    /**
     * 根据组织ID获取所有的组织列表
     * 
     * @Title getAllOrg
     * @author wanglc
     * @date 2013-11-25
     * @param orgId orgId 组织ID
     * @return List<Organization>
     */
    public List<Organization> getAllOrg(int orgId)
        throws BusinessException;
    
    /**
     * 根据父节点获取该节点下所有子节点组织
     * 
     * @Title getAllSonOrgByParentId
     * @author wanglc
     * @date 2013-11-25
     * @param params 父节点ID、权限范围和权限flag等参数
     * @return List<Organization>
     */
    public List<TreeNode> getAllSonOrgByParentId(Map<String, String> params)
        throws BusinessException;
    
    /**
     * 获取部门树的根节点
     * 
     * @Title getRootOrg
     * @author wanglc
     * @param params 权限范围和权限flag等参数
     * @date 2013-11-25
     * @return List<Organization>
     */
    public List<TreeNode> getRootOrg(Map<String, String> params)
        throws BusinessException;
    
    /**
     * 判断某个父组织下面给定名字子机构是否存在
     * 
     * @Title checkOrgName
     * @author wanglc
     * @date 2013-11-25
     * @param Organization o 组织对象
     * @param orgName 组织名称
     * @return boolean 返回true表示不存在
     */
    public boolean checkOrgName(Organization o, String orgName)
        throws BusinessException;
    
    /**
     * 检查某个编码是否存在，数据表级别的唯一
     * 
     * @Title checkOrgCode
     * @author wanglc
     * @date 2013-11-25
     * @param org 组织
     * @return boolean 返回true表示不存在
     */
    public boolean checkOrgCode(Organization org)
        throws BusinessException;
    
    /**
     * 根据用户ID和菜单的编码取得用户在某个菜单下能够操作的组织列表，实现方式为先根据菜单取得拥有这个菜单的角色列表，再根据用户ID和角色列表筛选出组织列表
     * 
     * @Title getUserOrgs
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户主键
     * @param menuCode 菜单编码
     * @return List<Organization>
     */
    /*
     * public List<Organization> getUserOrgs(int userId, String menuCode) throws BusinessException;
     */
    
    /**
     * 根据用户ID和菜单编码获取用户特殊资源树
     * 
     * @Title getUserOrgsTreeNodes
     * @author wanglc
     * @date 2013-11-25
     * @param userId 用户ID
     * @param menuCode 菜单编码
     * @return List <ZTreeNodeVo>
     */
    /*
     * public List<ZTreeNodeVo> getUserOrgsTreeNodes(int userId, String menuCode) throws
     * BusinessException;
     */
    
    /**
     * 取得组织分页列表
     * 
     * @Title getOrgList
     * @author wanglc
     * @date 2013-11-25
     * @param start 查询起始位置
     * @param limit 每页限制条数
     * @param org 组织对象
     * @return PagerVo<Organization>
     */
    public PagerVo<Organization> getOrgList(int start, int limit,
        Organization org)
        throws BusinessException;
    
    /**
     * 添加组织
     * 
     * @Title addOrg
     * @author wanglc
     * @date 2013-11-25
     * @param org 组织对象
     */
    public void addOrg(Organization org)
        throws BusinessException;
    
    /**
     * 更新组织
     * 
     * @Title updateOrg
     * @author wanglc
     * @date 2013-11-25
     * @param org 组织对象
     * @param orgName 组织名称
     * @param orgCode 组织编码
     * @param orgType 组织类型（字典数据）
     */
    public void updateOrg(Organization org, String orgName, String orgCode,
        Dictionary orgType)
        throws BusinessException;
    
    /**
     * 调整组织顺序(prev拖到目标前面 inner拖到目标里面 next拖到目标后面)
     * 
     * @Title updateOrder
     * @author wanglc
     * @date 2013-11-25
     * @throws BusinessException
     * @param srcOrgId 被拖动的组织
     * @param targetOrgId 拖向的组织
     * @param position 拖到目标组织的相对位置
     * @return void
     * @throws Exception
     */
    public void updateOrder(int srcOrgId, int targetOrgId, String position)
        throws Exception;
    
    /**
     * 根据ID取得组织
     * 
     * @Title getOrgById
     * @author wanglc
     * @date 2013-11-25
     * @param id 组织主键
     * @return Organization
     */
    public Organization getOrgById(int id)
        throws BusinessException;
    
    /**
     * 上传EXCEL导入组织
     * 
     * @Title importOrg
     * @author wanglc
     * @date 2013-11-25
     * @param attachUrl 附件URL
     * @return String
     * @throws Exception
     */
    public String importOrg(String attachUrl)
        throws Exception;
    
    /**
     * 根据组织编码获取组织对象
     * 
     * @Title getOrganizationByCode
     * @author wanglc
     * @date 2013-11-25
     * @param code 组织编码
     * @return Organization
     */
    public Organization getOrganizationByCode(String code)
        throws BusinessException;
    
    /**
     * 获取当前用户的权限组织
     * 
     * @Title getMyOrganization
     * @author wanglc
     * @date 2013-11-25
     * @param user 用户对象
     * @return List<Organization>
     */
    /*
     * public List<Organization> getMyOrganization(User user) throws BusinessException;
     */
    
    /**
     * 根据组织主键获取组织编码
     * 
     * @Title getOrgCodeById
     * @author wanglc
     * @date 2013-11-25
     * @param departmentId 组织主键
     * @return String
     */
    public String getOrgCodeById(String departmentId)
        throws BusinessException;
    
    /**
     * 更新组织
     * 
     * @Title updateOrg
     * @author wanglc
     * @Description: 更新组织
     * @date 2014-2-10
     * @param org 组织对象
     */
    public void updateOrg(Organization org)
        throws BusinessException;
    
    /**
     * 获取添加和修改用户时的树
     * 
     * @Title getUnitTreeListForModifyUser
     * @author yzg
     * @date 2014-2-17
     * @param params 参数列表
     * @return
     */
    public List<TreeNode> getUnitTreeListForModifyUser(
        Map<String, String> params)
        throws BusinessException;
    
    /**
     * 修改组织上级部门
     * 
     * @Title getUnitTreeListForUpdateOrg
     * @author wujialing
     * @Description: 
     * @date 2015年4月23日
     * @param params
     * @return
     * @throws BusinessException
     */
    public List<TreeNode> getUnitTreeListForUpdateOrg(
        Map<String, String> params)
        throws BusinessException;
    
    /**
     * 取根节点部门信息,并带出当前部门当前角色下的人数
     * 
     * @Title getRootOrgForRoleSearch
     * @author wanglc
     * @date 2014-2-18
     * @return List<TreeNode>
     */
    /*
     * public List<TreeNode> getRootOrgForRoleSearch(String currentRoleId) throws BusinessException;
     */
    
    /**
     * 获取子节点部门信息,并带出当前部门当前角色下的人数
     * 
     * @Title getAllSonOrgByParentIdForRoleSearch
     * @author wanglc
     * @date 2014-2-18
     * @param parentId 组织ID
     * @return List<TreeNode> 子节点部门信息
     */
    /*
     * public List<TreeNode> getAllSonOrgByParentIdForRoleSearch( String parentId, String
     * currentRoleId) throws BusinessException;
     */
    
    /**
     * 获取组织列表
     * 
     * @Title getOrgList
     * @author wanglc
     * @date 2014-2-25
     * @param map 组织名称、类型等参数列表
     * @return ListVo<OrgVo>
     */
    public ListVo<OrgVo> getOrgList(Map<String, String> map)
        throws BusinessException;
    
    /**
     * 校验组织属性值的唯一性
     * 
     * @Title validateOrgTypeproperties
     * @author wanglc
     * @date 2014-2-25
     * @param paramsMap 参数列表
     * @return Map<String, Object> 校验结果
     */
    public Map<String, Object> validateOrgProperties(
        Map<String, String> paramsMap)
        throws BusinessException;
    
    /**
     * 判断是否是叶子节点组织
     * 
     * @Title isLeafOrg
     * @author wanglc
     * @param org 组织对象
     * @date 2014-2-25
     * @return boolean
     */
    public boolean isLeafOrg(Organization org)
        throws BusinessException;
    
    /**
     * 通过参数，查询当前组织是否已删除
     * 
     * @Title getOrgIsDelete
     * @author ndy
     * @Description:
     * @date 2014年3月17日
     * @param orgId 组织ID
     * @return 小于等于0表示已删除
     */
    int getOrgIsDelete(Map<String, String> map)
        throws BusinessException;
    
    /**
     * 通过ID判断当前组织是否存在
     * 
     * @Title userIsExist
     * @author ndy
     * @date 2014年3月26日
     * @param orgId 组织ID
     * @param code 功能编码
     * @return 小于等于0表示不存在
     */
    int orgIsExist(String orgId, String code)
        throws BusinessException;
    
    /**
     * 通过组织id和权限范围ids递归查询所有组织id
     * 
     * @Title getOrgIdsByPermissionScope
     * @author dong.he
     * @date 2014年9月26日
     * @param startOrgId 组织id
     * @param permissionOrgIds 权限范围内组织ids
     * @return String 组织IDS
     * @throws BusinessException
     */
    public String getOrgIdsByPermissionScope(String startOrgId,
        String permissionOrgIds)
        throws BusinessException;
    
    /**
     * 根据orgIds查询组织集合
     * 
     * @Title getOrgByIds
     * @author dong.he
     * @date 2014年9月30日
     * @param orgIds 组织ids
     * @return 组织对象集合
     * @throws BusinessException
     */
    public List<Organization> getOrgByIds(String orgIds)
        throws BusinessException;
    
    /**
     * 当传入多个组织的时候，判断这些组织是否满足删除条件，唯一能够删除的条件是：
     * 所选组织包含父及其所有的儿子 ，实现思路：遍历所有选中组织，取其全部子组织，看是否全部被包含在被选的组织中
     * 
     * @Title isCandelOrgIds
     * @author wujialing
     * @Description: 
     * @date 2015年4月30日
     * @param orgIds
     * @return
     * @throws BusinessException
     */
    public String isCandelOrgIds(String orgIds)
        throws BusinessException;
    
    /**
     * 锁定和解锁组织
     * 
     * @param orgId
     * @throws BusinessException
     */
    public void lockupOrg(Integer orgId) throws BusinessException;
}
