package com.xx.system.org.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.ConnectionFactory;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.PagerVo;
import com.xx.system.common.vo.TreeNode;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.service.IDictService;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.service.IOrgService;
import com.xx.system.org.service.IOrgUserService;
import com.xx.system.org.vo.OrgVo;
import com.xx.system.user.util.HSSFUtils;


/**
 * 组织逻辑接口实现
 * 
 * @version V1.20,2013-11-25 下午4:02:53
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Service("organizationService")
@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public class OrgServiceImpl implements IOrgService {
    private final static int ORG_INT = 2;
    
    private final static int USER_INT = 1;
    
    private final static String ORG_ROOT_ID = "0";
    
    @Autowired
    @Qualifier("dictService")
    private IDictService dictService;
    
    @Resource
    public IOrgUserService orgUserService;
    
    
    public IOrgUserService getOrgUserService()
    {
        return orgUserService;
    }

    public void setOrgUserService(IOrgUserService orgUserService)
    {
        this.orgUserService = orgUserService;
    }

    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
    
    @javax.annotation.Resource
    private IOrgService organizationService;
    
    /**
     * @param organizationService 要设置的 organizationService
     */
    public void setOrganizationService(IOrgService organizationService) {
        this.organizationService = organizationService;
    }
    
    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;
    
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    /**
     * 添加组织
     * 
     * @Title addOrg
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param org 组织对象
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addOrg(Organization org)
        throws BusinessException {
        this.baseDao.save(org);
    }
    
    /**
     * 取得所有的组织列表,不包含禁用的和已删除的
     * 
     * @Title getAllOrg
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @return List<Organization>
     */
    @Override
    public List<Organization> getAllOrg()
        throws BusinessException {
        List<Organization> result = null;
        String hql =
            "from Organization o where o.enable=" + Constant.ENABLE
                + " and o.status=" + Constant.STATUS_NOT_DELETE
                + " order by o.disOrder";
        result = baseDao.queryEntitys(hql);
        return result;
    }
    
    /**
     * 根据组织ID获取所有的组织列表
     * 
     * @Title getAllOrg
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param orgId orgId 组织ID
     * @return List<Organization>
     */
    @Override
    public List<Organization> getAllOrg(int orgid)
        throws BusinessException {
        List<Organization> result = null;
        String tempHql = "from Organization where orgId=" + orgid;
        Organization org = (Organization)baseDao.queryEntitys(tempHql).get(0);
        
        String hql =
            "from Organization o where o.enable=" + Constant.ENABLE
                + " and o.status=" + Constant.STATUS_NOT_DELETE
                + " and o.disOrder like '" + org.getDisOrder() + "%'"
                + " order by o.disOrder";
        result = baseDao.queryEntitys(hql);
        return result;
    }
    
    /**
     * 根据父节点获取该节点下所有子节点组织
     * 
     * @Title getAllSonOrgByParentId
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param parentId 父节点组织
     * @param permissionOrgIds 权限范围内组织ID
     * @return List<Organization>
     */
    @Override
    public List<TreeNode> getAllSonOrgByParentId(Map<String, String> params)
        throws BusinessException {
        String permissionOrgIds = params.get("permissionOrgIds");
        String permissionFlag = params.get("permissionFlag");
        String parentId = params.get("parentId");
        
        ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        // 新建查询的hql语句
        StringBuffer sb =
            new StringBuffer("from Organization o where o.enable = ");
        sb.append(Constant.ENABLE);
        sb.append(" and o.status = ");
        sb.append(Constant.STATUS_NOT_DELETE);
        if (StringUtils.isNotBlank(permissionFlag)) {
            if (StringUtils.isNotBlank(permissionOrgIds)) {
                // 设置查询结果在权限范围内
                sb.append(" and o.orgId in (");
                sb.append(permissionOrgIds);
                sb.append(")");
            }
            else {
                sb.append(" and 1 = 0");
            }
        }
        
        sb.append(" and o.organization.orgId = ");
        sb.append(parentId);
        sb.append(" order by o.disOrder");
        
        List<Organization> result = baseDao.queryEntitys(sb.toString());
        for (Organization o : result) {
            TreeNode tn = new TreeNode();
            tn.setId(o.getOrgId());
            tn.setNodeId(o.getOrgId());
            tn.setText(o.getOrgName());
            tn.setLeaf(organizationService.isLeafOrg(o));
            treeNodeList.add(tn);
        }
        return treeNodeList;
    }
    
    /**
     * 获取部门树的根节点
     * 
     * @Title getRootOrg
     * @author wanglc
     * @Description:
     * @param param 权限范围和权限flag等参数
     * @date 2013-11-25
     * @return List<Organization>
     */
    @Override
    public List<TreeNode> getRootOrg(Map<String, String> params)
        throws BusinessException {
        String permissionOrgIds = params.get("permissionOrgIds");
        String permissionFlag = params.get("permissionFlag");
        
        ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        // 新建查询的hql语句
        StringBuffer sb =
            new StringBuffer("from Organization o where o.enable = ");
        sb.append(Constant.ENABLE);
        sb.append(" and o.status = ");
        sb.append(Constant.STATUS_NOT_DELETE);
        if (StringUtils.isNotBlank(permissionFlag)) {
            if (StringUtils.isNotBlank(permissionOrgIds)) {
                // 设置查询结果在权限范围内
                sb.append(" and o.orgId in (");
                sb.append(permissionOrgIds);
                sb.append(")");
                
                // 当根节点不在权限范围内时
                sb.append(" and (o.organization.orgId not in (");
                sb.append(permissionOrgIds);
                sb.append(") or (o.organization.orgId= 0 or o.organization is null))");
            }
            else {
                sb.append(" and 1 = 0");
            }
        }
        else {
            sb.append(" and (o.organization.orgId= 0 or o.organization is null)");
        }
        
        List<Organization> result = baseDao.queryEntitys(sb.toString());
        for (Organization o : result) {
            TreeNode tn = new TreeNode();
            tn.setId(o.getOrgId());
            tn.setNodeId(o.getOrgId());
            tn.setText(o.getOrgName());
            tn.setLeaf(organizationService.isLeafOrg(o));
            treeNodeList.add(tn);
        }
        return treeNodeList;
    }
    
    /**
     * 取得组织分页列表
     * 
     * @Title getOrgList
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param start 查询起始位置
     * @param limit 每页限制条数
     * @param org 组织对象
     * @return PagerVo<Organization>
     */
    @Override
    public PagerVo<Organization> getOrgList(int start, int limit,
        Organization org)
        throws BusinessException {
        List<Organization> orgList = null;
        int count = 0;
        if (org == null) {
            org = new Organization();
        }
        org.setEnable(Constant.ENABLE_ADN_DISABLE);
        org.setStatus(Constant.STATUS_NOT_DELETE);
        StringBuffer sb = new StringBuffer(" 1=1 ");
        Map<String, Object> values = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(org.getOrgName())) {
            sb.append(" and obj.orgName like ?");
            values.put("orgName", "'%" + org.getOrgName() + "%'");
        }
        if (StringUtils.isNotBlank(org.getOrgCode())) {
            sb.append(" and obj.orgCode = ?");
            values.put("orgCode", "'" + org.getOrgCode() + "'");
        }
        if (org.getOrgType().getPkDictionaryId() >= 0) {
            sb.append(" and obj.orgType.pkDictionaryId= ?");
            values.put("orgType", org.getOrgType());
        }
        if (org.getEnable() >= 0) {
            sb.append(" and obj.enable= ?");
            values.put("enable", org.getEnable());
        }
        if (org.getStatus() >= 0) {
            sb.append(" and obj.status= ?");
            values.put("status", org.getStatus());
        }
        
        orgList =
            baseDao.queryEntitysByPage(start, limit, sb.toString(), values);
        count = baseDao.getTotalCount(sb.toString(), new HashMap());
        return new PagerVo<Organization>(start, limit, count, orgList);
    }
    
    /**
     * 根据用户ID和菜单的编码取得用户在某个菜单下能够操作的组织列表，实现方式为先根据菜单取得拥有这个菜单的角色列表，再根据用户ID和角色列表筛选出组织列表
     * 
     * @Title getUserOrgs
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param userId 用户主键
     * @param menuCode 菜单编码
     * @return List<Organization>
     */
    /*@Override
    public List<Organization> getUserOrgs(int userId, String menuCode)
        throws BusinessException {
        String hql = "";
        hql =
            "from Organization org where org.enable="
                + Constant.ENABLE
                + " and org.status="
                + Constant.STATUS_NOT_DELETE
                + " and (org.orgId in "
                + "(select ur.organization.orgId from UserRole ur, RoleResource rr, Resource r "
                + "where ur.role.roleId=rr.role.roleId "
                + "and ur.user.userId="
                + userId
                + " and rr.resource.resourceId=r.resourceId "
                + " and r.code='"
                + menuCode
                + "') "
                + " or org.orgId in "
                + "(select t.organization.orgId from UserOrgResource t, Resource re  where  "
                + "t.user.userId=" + userId + "  "
                + "and t.resource.resourceId=re.resourceId " + "and re.code='"
                + menuCode + "')))" + " order by org.disOrder";
        return baseDao.queryEntitys(hql, new String[] {});
    }*/
    
    /**
     * 根据用户ID和菜单编码获取用户特殊资源树
     * 
     * @Title getUserOrgsTreeNodes
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param userId 用户ID
     * @param menuCode 菜单编码
     * @return List <ZTreeNodeVo>
     */
    /*@Override
    public List<ZTreeNodeVo> getUserOrgsTreeNodes(int userId, String menuCode)
        throws BusinessException {
        List<ZTreeNodeVo> treeNodeList = new ArrayList<ZTreeNodeVo>();
        List<User> userList = new ArrayList<User>();
        List<Organization> orgs = new ArrayList<Organization>();
        String hql =
            "from Organization org where org.enable="
                + Constant.ENABLE
                + " and org.status="
                + Constant.STATUS_NOT_DELETE
                + " and (org.orgId in "
                + "(select ur.organization.orgId from UserRole ur, RoleResource rr, Resource r "
                + "where ur.role.roleId=rr.role.roleId "
                + "and ur.user.userId="
                + userId
                + " and rr.resource.resourceId=r.resourceId "
                + " and r.code='"
                + menuCode
                + "') "
                + " or org.orgId in "
                + "(select t.organization.orgId from UserOrgResource t, Resource re  where  "
                + "t.user.userId=" + userId + "  "
                + "and t.resource.resourceId=re.resourceId " + "and re.code='"
                + menuCode + "')) order by org.disOrder";
        
        orgs = baseDao.queryEntitys(hql, new String[] {});
        ZTreeNodeVo tn;
        StringBuilder orgIds = new StringBuilder();
        orgIds.append("(");
        for (Organization o : orgs) {
            tn = new ZTreeNodeVo();
            tn.setId("org_" + o.getOrgId());
            tn.setPId(o.getOrganization() != null ? "org_"
                + o.getOrganization().getOrgId() : "0");
            tn.setChecked(false);
            tn.setType(ORG_INT);
            tn.setName(o.getOrgName());
            tn.setIsParent(true);
            treeNodeList.add(tn);
            if (orgIds.length() == 1)
                orgIds.append(o.getOrgId());
            else
                orgIds.append("," + o.getOrgId());
        }
        orgIds.append(")");
        if (orgIds.length() > 2) {
            hql =
                "from User user where user.enable="
                    + Constant.ENABLE
                    + " and user.status="
                    + Constant.STATUS_NOT_DELETE
                    + " and (user.organization.orgId in "
                    + orgIds.toString()
                    + " ) order by user.office desc, user.disOrder,user.username";
            
            userList = baseDao.query(hql, new String[] {});
            for (User u : userList) {
                tn = new ZTreeNodeVo();
                tn.setId("user_" + u.getUserId());
                tn.setChecked(false);
                tn.setType(USER_INT);
                tn.setName(u.getRealname());
                tn.setIsParent(false);
                treeNodeList.add(tn);
            }
        }
        return treeNodeList;
    }*/
    
    /**
     * 更新组织
     * 
     * @Title updateOrg
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param org 组织对象
     * @param orgName 组织名称
     * @param orgCode 组织编码
     * @param orgType 组织类型（字典数据）
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateOrg(Organization org, String orgName, String orgCode,
        Dictionary orgType)
        throws BusinessException {
        if (StringUtils.isNotBlank(orgCode)) {
            org.setOrgCode(orgCode);
        }
        if (StringUtils.isNotBlank(orgName)) {
            org.setOrgName(orgName);
        }
        if (orgType != null) {
            org.setOrgType(orgType);
        }
        baseDao.update(org);
    }
    
    /**
     * 调整组织顺序(prev拖到目标前面 inner拖到目标里面 next拖到目标后面)
     * 
     * @Title updateOrder
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @throws BusinessException
     * @param srcOrgId 被拖动的组织
     * @param targetOrgId 拖向的组织
     * @param position 拖到目标组织的相对位置
     * @return void
     * @throws SQLException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateOrder(int srcOrgId, int targetOrgId, String position)
        throws Exception {
        /**
         * 调用存储过程
         */
        Connection con = null;
        CallableStatement oracleCallableStatement = null;
        con = ConnectionFactory.getConnection();
        oracleCallableStatement =
            (CallableStatement)con.prepareCall("BEGIN P_UPDATE_ORDER(?,?,?); END;");
        oracleCallableStatement.setInt(1, srcOrgId);
        oracleCallableStatement.setInt(2, targetOrgId);
        oracleCallableStatement.setString(3, position);
        oracleCallableStatement.execute();
        if (oracleCallableStatement != null) {
            oracleCallableStatement.close();
        }
        if (con != null) {
            con.close();
        }
    }
    
    /**
     * 判断某个父组织下面给定名字子机构是否存在
     * 
     * @Title checkOrgName
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param Organization o 组织对象
     * @param orgName 组织名称
     * @return boolean 返回true表示不存在
     */
    @Override
    public boolean checkOrgName(Organization o, String orgName)
        throws BusinessException {
        int id = o.getOrgId();
        int parentId = 0;
        Organization po = o.getOrganization();
        if (StringUtils.isNotBlank(orgName)) {
            String sql =
                "SELECT COUNT(*) FROM Organization a WHERE a.status = "
                    + Constant.STATUS_NOT_DELETE + " and a.orgId != '" + id
                    + "'  AND a.orgName='" + orgName.replace("'", "''") + "'";
            if (po != null) {
                parentId = po.getOrgId();
                sql += " AND a.organization.orgId =" + parentId;
            }
            int count = baseDao.getTotalCount(sql, null); 
            if (count == 0) {
                return true;
            }
        }
        else {
            return true;
        }
        return false;
    }
    
    /**
     * 检查某个编码是否存在，数据表级别的唯一
     * 
     * @Title checkOrgCode
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param org 组织
     * @return boolean 返回true表示不存在
     */
    @Override
    public boolean checkOrgCode(Organization org)
        throws BusinessException {
        String code = org.getOrgCode();
        int orgId = org.getOrgId();
        if (StringUtils.isNotBlank(code)) {
            String sql =
                "SELECT COUNT(*) FROM Organization a WHERE a.status = "
                    + Constant.STATUS_NOT_DELETE + " and a.orgId != " + orgId
                    + " AND a.orgCode='" + code.replace("'", "''") + "'";
            int count = baseDao.getTotalCount(sql, null); 
            if (count == 0) {
                return true;
            }
        }
        else {
            return true;
        }
        return false;
    }
    
    /**
     * 根据ID取得组织
     * 
     * @Title getOrgById
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param id 组织主键
     * @return Organization
     */
    @Override
    public Organization getOrgById(int id)
        throws BusinessException {
        return (Organization)baseDao.queryEntityById(Organization.class, id);
    }
    
    /**
     * 添加的时候验证组织名称是否唯一
     * 
     * @Title validateAddOrgName
     * @author wanglc
     * @Description:
     * @date 2013-12-6
     * @param orgName
     * @return boolean 验证结果
     */
    public boolean validateAddOrgName(String orgName)
        throws BusinessException {
        String sql =
            "SELECT COUNT(*) FROM Organization a WHERE a.orgName='"
                + orgName.replace("'", "''") + "'";
        int flag =baseDao.getTotalCount(sql, null); 
            
        if (flag == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * @Title importOrg
     * @author wanglc
     * @Description: 上传EXCEL导入组织
     * @date 2013-11-25
     * @param attachUrl 附件URL
     * @return String
     * @throws Exception
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public String importOrg(String attachUrl)
        throws Exception {
        String message = "importSuccess";
        String[][] content = HSSFUtils.extractTextFromExcel(attachUrl);
        if (null == content) {
            message = "不是有效的Excel文件,请按照模版来定义！";
        }
        else {
            File attachFile = new File(attachUrl);
            FileUtils.forceDelete(attachFile);
            int col = content.length;
            StringBuffer sql[] = new StringBuffer[col - 1];
            List<String> sqlArray = new ArrayList<String>();
            StringBuffer sqlParam = new StringBuffer("");
            boolean sign = false;
            for (int i = 1; i < col; i++) {
                int j = i - 1;
                sql[j] = new StringBuffer("");
                sql[j].append("INSERT INTO T_ORGANIZATION(");
                // 组织名
                if (null != content[i][0]
                    && !StringUtils.isBlank(content[i][0].trim())
                    && !StringUtils.isEmpty(content[i][0].trim())
                    && !StringUtils.equals("null", content[i][0].trim())) {
                    String orgName = content[i][0].trim();
                    sign = this.validateAddOrgName(orgName);
                    if (sign) {
                        int o = i + 1;
                        message = "第【" + o + "】行,【1】列发生错误,原因:指定的组织名在数据库中已存在！";
                        break;
                    }
                    else {
                        sql[j].append("ORG_NAME");
                        sqlParam.append("'" + orgName + "'");
                    }
                }
                /**
                 * 组织编码
                 */
                if (null != content[i][1]
                    && !StringUtils.isBlank(content[i][1].trim())
                    && !StringUtils.isEmpty(content[i][1].trim())
                    && !StringUtils.equals("null", content[i][1].trim())) {
                    sql[j].append(",ORG_CODE");
                    sqlParam.append(",'" + content[i][1].trim() + "'");
                }
                
                /**
                 * 父组织
                 */
                if (null != content[i][2]
                    && !StringUtils.isBlank(content[i][2].trim())
                    && !StringUtils.isEmpty(content[i][2].trim())
                    && !StringUtils.equals("null", content[i][2].trim())) {
                    String orgName = content[i][2].trim();
                    String hql =
                        "FROM com.dqgb.org.entity.Organization o where o.orgName = ?";
                    List<Organization> list =
                        this.baseDao.query(hql, new String[] {orgName});
                    if (null != list && list.size() > 0) {
                        Organization org = list.get(0);
                        sql[j].append(",PARENT_ID");
                        sqlParam.append("," + org.getOrgId() + "");
                    }
                    else {
                        int o = i + 1;
                        message = "第【" + o + "】行,【3】列发生错误,原因:指定的组织名未找到！";
                        break;
                    }
                }
                else {
                    int o = i + 1;
                    message = "第【" + o + "】行,【3】列发生错误,原因:组织名不能空！";
                    break;
                }
                /**
                 * 组织类型
                 */
                if (null != content[i][3]
                    && !StringUtils.isBlank(content[i][3].trim())
                    && !StringUtils.isEmpty(content[i][3].trim())
                    && !StringUtils.equals("null", content[i][3].trim())) {
                    String officeString = content[i][3].trim();
                    int type = 0;
                    if (officeString.equals("公司")) {
                        type = 0;
                    }
                    else if (officeString.equals("小组")) {
                        type = 2;
                    }
                    else if (officeString.equals("部门")) {
                        type = 1;
                    }
                    else {
                        int o = i + 1;
                        message = "第【" + o + "】行,【4】列发生错误,原因:指定的组织类型不存在！";
                        break;
                    }
                    sql[j].append(",FK_ORGTYPE");
                    sqlParam.append("," + type + "");
                }
                
                sql[j].append(",STATUS");
                sqlParam.append("," + 0 + "");
                
                sql[j].append(",ENABLE");
                sqlParam.append("," + 0 + "");
                
                sql[j].append(",ORG_ID");
                int id = this.baseDao.getIdFromSequence("SEQ_ORGANIZATION");
                sqlParam.append("," + id + "");
                
                sql[j].append(") values(");
                sqlParam.append(")");
                sql[j].append(sqlParam.toString());
                sqlArray.add(sql[j].toString());
                sqlParam.delete(0, sqlParam.length());
            }
            if (message.equals("importSuccess")) {
                if (sqlArray.size() > 0) {
                    String[] sqlInsert = new String[sqlArray.size()];
                    for (int i = 0; i < sqlArray.size(); i++) {
                        sqlInsert[i] = (String)sqlArray.get(i);
                    }
                    if (sqlInsert.length > 0) {
                        baseDao.batchUpdate(sqlInsert);
                    }
                }
            }
        }
        return message;
    }
    
    /**
     * 根据组织编码获取组织对象
     * 
     * @Title getOrganizationByCode
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param code 组织编码
     * @return Organization
     */
    @Override
    public Organization getOrganizationByCode(String code)
        throws BusinessException {
        String hql =
            "from Organization o where o.enable = " + Constant.ENABLE
                + " and o.status = " + Constant.STATUS_NOT_DELETE + " and "
                + " o.orgCode = '" + code + "' order by o.disOrder";
        List<Organization> ul =
            (List<Organization>)this.baseDao.queryEntitys(hql);
        if (ul != null && ul.size() > 0) {
            return (Organization)ul.get(0);
        }
        return null;
    }
    
    /**
     * 获取当前用户的权限组织
     * 
     * @Title getMyOrganization
     * @author wanglc
     * @Description:
     * @date 2013-11-25
     * @param user 用户对象
     * @return List<Organization>
     */
    /*@Override
    public List<Organization> getMyOrganization(User user)
        throws BusinessException {
        List<Organization> orgList = new ArrayList<Organization>();
        String hql =
            "from UserOrgResource uor where uor.user.userId = '"
                + user.getUserId() + "'";
        List<UserOrgResource> uList =
            (List<UserOrgResource>)this.baseDao.queryEntitys(hql);
        for (UserOrgResource u : uList) {
            orgList.add(u.getOrganization());
        }
        return orgList;
    }*/
    
    /**
     * @Title getOrgCodeById
     * @author wanglc
     * @Description: 根据组织主键获取组织编码
     * @date 2013-11-25
     * @param departmentId 组织主键
     * @return String
     */
    @Override
    public String getOrgCodeById(String departmentId)
        throws BusinessException {
        String hql =
            "from Organization o where o.enable = 0 and o.status = 0 and o.orgId = '"
                + departmentId + "'";
        Organization org = (Organization)baseDao.query(hql).get(0);
        return org.getOrgCode();
    }
    
    /**
     * 删除本地组织数据
     * 
     * @Title deleteLocalOrgInfoLogic
     * @author wanglc
     * @Description:
     * @date 2013-12-16
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private void deleteLocalOrgInfoLogic(List<Organization> orgList) {
        for (Organization org : orgList) {
            // if (org.getOrgFrom() == 0)
            // 删除数据自动跳过架构
            // {
            // continue;
            // }
            if (org.getIsDeleteAble() != 1) {
                baseDao.delete(org.getOrgId() + "",
                    "Organization",
                    "status",
                    "orgId",
                    "2");
            }
        }
    }
    
    /**
     * 取本地数库中的当前组织
     * 
     * @Title getLocalOrgByCode
     * @author wanglc
     * @Description:
     * @date 2013-12-16
     * @param code
     * @return
     */
    private Organization getLocalOrgByCode(String code) {
        List<Organization> orgList = new ArrayList<Organization>();
        String hql =
            "from Organization o where o.orgCode = '" + code
                + "' and o.status = " + Constant.STATUS_NOT_DELETE;
        orgList = baseDao.queryEntitys(hql);
        if (orgList != null && orgList.size() > 0) {
            return orgList.get(0);
        }
        else {
            return null;
        }
    }
    
    public static List<Organization> getRootOrgForOther(IBaseDao baseDao) {
        String hql =
            "from Organization o where o.enable=" + Constant.ENABLE
                + " and o.status=" + Constant.STATUS_NOT_DELETE
                + " and (o.organization.orgId= 0 or o.organization is null)";
        List<Organization> result = baseDao.queryEntitys(hql);
        return result;
    }
    
    /**
     * @Title updateOrg
     * @author wanglc
     * @Description: 更新组织
     * @date 2014-2-10
     * @param org 组织对象
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateOrg(Organization org)
        throws BusinessException {
        baseDao.saveOrUpdate(org);
        
    }
    
    /**
     * 获取添加和修改用户时的树
     * 
     * @Title getUnitTreeListForModifyUser
     * @author yzg
     * @Description:
     * @date 2014-2-17
     * @param params
     * @return
     */
    @Override
    public ArrayList<TreeNode> getUnitTreeListForModifyUser(
        Map<String, String> params)
        throws BusinessException {
        ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        String parentId = StringUtil.getStr(params.get("parentId"));
        String userId = StringUtil.getStr(params.get("userId"));
        String hql =
            "from Organization o where o.enable=" + Constant.ENABLE
                + " and o.status=" + Constant.STATUS_NOT_DELETE;
        if ("0".equals(parentId)) {
            hql +=
                " and (o.organization.orgId= 0 or o.organization is null) order by o.disOrder";
        }
        else {
            hql +=
                " and o.organization.orgId = " + parentId
                    + " order by o.disOrder";
        }
        List<Organization> result = baseDao.queryEntitys(hql);
        
        for (Organization o : result) {
            TreeNode tn = new TreeNode();
            tn.setNodeId(o.getOrgId());
            tn.setText(o.getOrgName());
            tn.setLeaf(organizationService.isLeafOrg(o));
            if (!StringUtil.isEmpty(userId)) {
                hql =
                    "from OrgUser o where o.isDelete = "
                        + Constant.STATUS_NOT_DELETE + " and o.user.userId = "
                        + userId + " and o.organization.orgId = "
                        + o.getOrgId();
                List<OrgUser> orgUsers = baseDao.queryEntitys(hql);
                if (orgUsers != null && orgUsers.size() > 0) {
                    tn.setChecked(true);
                }
                else {
                    tn.setChecked(false);
                }
                tn.setExpanded(true);
            }
            else {
                tn.setExpanded(false);
            }
            treeNodeList.add(tn);
        }
        
        return treeNodeList;
    }
    
    @Override
    public ArrayList<TreeNode> getUnitTreeListForUpdateOrg(
        Map<String, String> params)
        throws BusinessException {
        ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        String parentId = StringUtil.getStr(params.get("parentId"));
        String objId = StringUtil.getStr(params.get("objId"));
        String curentOrgId = StringUtil.getStr(params.get("curentOrgId"));
        String hql =
            "from Organization o where o.enable=" + Constant.ENABLE
                + " and o.status=" + Constant.STATUS_NOT_DELETE;
        if (StringUtils.isNotBlank(curentOrgId))
        {
            hql += " and o.orgId <> "+curentOrgId ;
        }
        if ("0".equals(parentId)) {
            hql +=
                " and (o.organization.orgId= 0 or o.organization is null) order by o.disOrder";
        }
        else {
            hql +=
                " and o.organization.orgId = " + parentId
                    + " order by o.disOrder";
        }
        List<Organization> result = baseDao.queryEntitys(hql);
        
        for (Organization o : result) {
            TreeNode tn = new TreeNode();
            tn.setNodeId(o.getOrgId());
            tn.setText(o.getOrgName());
            tn.setLeaf(organizationService.isLeafOrg(o));
            if (!StringUtil.isEmpty(objId)) {
                if (objId.equals(String.valueOf(o.getOrgId()))) {
                    tn.setChecked(true);
                }
                else {
                    tn.setChecked(false);
                }
                tn.setExpanded(true);
            }
            else {
                tn.setExpanded(false);
            }
            treeNodeList.add(tn);
        }
        
        return treeNodeList;
    }
    
    /**
     * 取根节点部门信息,并带出当前部门当前角色下的人数
     * 
     * @Title getRootOrgForRoleSearch
     * @author wanglc
     * @Description:
     * @date 2014-2-18
     * @return
     */
    /*@Override
    public List<TreeNode> getRootOrgForRoleSearch(String currentRoleId)
        throws BusinessException {
        ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        String hql =
            "from Organization o where o.enable=" + Constant.ENABLE
                + " and o.status=" + Constant.STATUS_NOT_DELETE
                + " and (o.organization.orgId= 0 or o.organization is null)";
        List<Organization> result = baseDao.queryEntitys(hql);
        int userCount = 0;
        for (Organization o : result) {
            StringBuffer countSql = new StringBuffer(
                "select count(us.user_id) from sys_user us,sys_user_role ur ");
            countSql.append(" where us.user_id = ur.user_id ");
            countSql.append(" and us.status = ");
            countSql.append(Constant.STATUS_NOT_DELETE);
            countSql.append(" and ur.role_id = ");
            countSql.append(currentRoleId);
            countSql.append(
                " and ur.org_id in (Select a.org_id From t_organization a");
            countSql.append(" where enable = ");
            countSql.append(Constant.ENABLE);
            countSql.append(" and status = ");
            countSql.append(Constant.STATUS_NOT_DELETE);
            countSql.append(" Connect by nocycle prior org_id = parent_id ");
            countSql.append(" Start with a.org_id = ");
            countSql.append(o.getOrgId());
            countSql.append(")");
            
            
            userCount = baseDao.getTotalCountNativeQuery(countSql.toString(), null);
            TreeNode tn = new TreeNode();
            tn.setNodeId(o.getOrgId());
            tn.setText(o.getOrgName() + "(" + userCount + ")");
            tn.setLeaf(organizationService.isLeafOrg(o));
            treeNodeList.add(tn);
        }
        return treeNodeList;
    }*/
    
    /**
     * 获取子节点部门信息,并带出当前部门当前角色下的人数
     * 
     * @Title getAllSonOrgByParentIdForRoleSearch
     * @author wanglc
     * @Description:
     * @date 2014-2-18
     * @param parentId
     * @return
     */
    /*@Override
    public List<TreeNode> getAllSonOrgByParentIdForRoleSearch(
        String parentId, String currentRoleId)
        throws BusinessException {
        ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        String hql =
            "from Organization o where o.enable=" + Constant.ENABLE
                + " and o.status=" + Constant.STATUS_NOT_DELETE
                + " and o.organization.orgId = " + NumberUtils.toInt(parentId)
                + " order by o.disOrder";
        List<Organization> result = baseDao.queryEntitys(hql);
        
        int userCount = 0;
        for (Organization o : result) {
            String sql =
                "select count(ur.user_role_id) from sys_user_role ur where ur.role_id = "
                    + currentRoleId
                    + " and ur.org_id in( "
                    + "Select a.org_id From t_organization a  where enable = "
                    + Constant.ENABLE + " and status = "
                    + Constant.STATUS_NOT_DELETE
                    + " Connect by nocycle prior org_id =  parent_id "
                    + "Start with a.org_id = " + o.getOrgId() + ")";
            userCount = baseDao.getTotalCountNativeQuery(sql, null);
            TreeNode tn = new TreeNode();
            tn.setNodeId(o.getOrgId());
            tn.setText(o.getOrgName() + "(" + userCount + ")");
            
            tn.setLeaf(organizationService.isLeafOrg(o));
            
            treeNodeList.add(tn);
        }
        return treeNodeList;
    }*/
    
    /**
     * 获取组织列表
     * 
     * @Title getOrgList
     * @author wanglc
     * @Description:
     * @date 2014-2-25
     * @param map
     * @return
     */
    @Override
    public ListVo<OrgVo> getOrgList(Map<String, String> map)
        throws BusinessException {
        Map<String, Object> param = new HashMap<String, Object>();
        ListVo<OrgVo> orgVoObj = new ListVo<OrgVo>();
        List<OrgVo> orgVoList = new ArrayList<OrgVo>();
        //String orgName = (String)map.get("orgName");
        //String orgType = (String)map.get("orgType");
        String parentOrgId = (String)map.get("parentId");
        int start = NumberUtils.toInt(map.get("start"));
        int limit = NumberUtils.toInt(map.get("limit"));
        
        /*StringBuffer sql =
            new StringBuffer(
                "Select TO_NUMBER(a.org_Id) as \"orgId\" From t_organization a where a.status = "
                    + Constant.STATUS_NOT_DELETE
                    + " and a.enable = "
                    + Constant.ENABLE + " ");
        
        if (!StringUtils.isBlank(orgName)) {
            sql.append(" and a.org_name like '%" + orgName + "%'");
        }
        if (!StringUtils.isBlank(orgType)) {
            sql.append(" and a.org_type like '%" + orgType + "%'");
        }
        if (!StringUtils.isBlank(parentOrgId) && !"0".equals(parentOrgId)) {
            sql.append(" Connect by nocycle prior org_id =  parent_id Start with a.org_id = "
                + NumberUtils.toInt(parentOrgId));
        }
        sql.append(" order by a.parent_id, a.dis_Order desc");
        
        List<OrgSqlVo> result =
            baseDao.executeNativeSQLForBean(start,
                limit,
                sql.toString(),
                OrgSqlVo.class);
        int totalSize =
            baseDao.getCountByNativeSQL(sql.toString(), OrgSqlVo.class);
        for (OrgSqlVo o : result) {
            Organization org =
                (Organization)baseDao.queryEntityById(Organization.class,
                    o.getOrgId().intValue());
            
            OrgVo ov = new OrgVo(org);
            orgVoList.add(ov);
            
        }*/
        
        String orgIds = getOrgIdsByPermissionScope(parentOrgId, null);
        int totalSize = 0;
        if (StringUtils.isNotBlank(orgIds)) {
            StringBuffer hql = new StringBuffer(" from Organization o");
            hql.append(" where o.orgId in (");
            hql.append(orgIds);
            hql.append(")");
            hql.append(" and o.status = ");
            hql.append(Constant.STATUS_NOT_DELETE);
            hql.append(" and o.enable = ");
            hql.append(Constant.ENABLE);
            
            totalSize = baseDao.queryTotalCount(hql.toString(), 
                new HashMap<String, Object>());
            
            hql.append(" order by o.organization.orgId, o.disOrder, o.orgId");
            List<Organization> orgLst = baseDao.queryEntitysByPage(start, limit, 
                hql.toString(), new HashMap<String, Object>());
           
            if (!CollectionUtils.isEmpty(orgLst)) {
                for (Organization o : orgLst) {
                    OrgVo ov = new OrgVo(o);
                    orgVoList.add(ov);
                }
            }
        }
        
        orgVoObj.setList(orgVoList);
        orgVoObj.setTotalSize(totalSize);
        return orgVoObj;
    }
    
    /**
     * 校验组织属性值的唯一性
     * 
     * @Title validateOrgTypeproperties
     * @author wanglc
     * @Description:
     * @date 2014-2-25
     * @param paramsMap
     * @return
     */
    @Override
    public Map<String, Object> validateOrgProperties(
        Map<String, String> paramsMap)
        throws BusinessException {
        String key = paramsMap.get("key");
        String value = paramsMap.get("value");
        String orgId = paramsMap.get("orgId");
        String parentOrgId = paramsMap.get("parentOrgId");
        String validatorType = paramsMap.get("validatorType");
        Map<String, Object> vaildator = new HashMap<String, Object>();
        String valueField = "";
        switch (NumberUtils.toInt(key)) {
            case 0:
                valueField = "orgName";
                break;
            case 1:
                valueField = "orgCode";
                break;
            default:
                break;
        }
        String hql =
            "from Organization o where o." + valueField + " = '" + value
                + "' and o.status = " + Constant.STATUS_NOT_DELETE;
        if (validatorType != null && "update".equals(validatorType)) {
            hql += " and o.orgId <> " + orgId;
        }
        if (!"1".equals(key)) {
            // hql +=" and o.organization.orgId= "+parentOrgId;
        }
        int totleSize = baseDao.queryTotalCount(hql, new HashMap());
        if (totleSize > 0) {
            vaildator.put("success", true);
            vaildator.put("valid", false);
            vaildator.put("reason", "数据已存在");
        }
        else {
            vaildator.put("success", true);
            vaildator.put("valid", true);
            vaildator.put("reason", "");
        }
        return vaildator;
    }
    
    /**
     * 判断是否是叶子节点组织
     * 
     * @Title isLeafOrg
     * @author wanglc
     * @Description:
     * @date 2014-2-25
     * @return
     */
    @Override
    public boolean isLeafOrg(Organization o)
        throws BusinessException {
        String hql =
            "from Organization o where o.enable=" + Constant.ENABLE
                + " and o.status=" + Constant.STATUS_NOT_DELETE
                + " and o.organization.orgId = " + o.getOrgId();
        List<Organization> children = baseDao.queryEntitys(hql);
        if (children != null && children.size() > 0) {
            return false;
        }
        else {
            return true;
        }
    }
    
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
        throws BusinessException {
        String msg = "" ;
        StringBuffer isorgUserCounts = new StringBuffer() ;
        StringBuffer isexsistLeafOrg = new StringBuffer() ;
        for (String orgId : orgIds.split(",")) {
            Organization org = organizationService.getOrgById(NumberUtils.toInt(orgId));
            int orgUserCounts = orgUserService.getUserCountsByOrgIds(orgId);
            boolean exsistLeafOrg = organizationService.isLeafOrg(org);
            //如果该组织下有用户存在，返回该组织，不能进行删除
            if (orgUserCounts != 0)
            {
                isorgUserCounts.append(org.getOrgName()).append(",");
            }
            //如果该组织有下级组织，则进行判断，取出该组织下所有下级组织，对照orgIds，看是否存在下级组织不在orgIds中
            if (!isOrgHaveAll(orgId, orgIds)) {
                isexsistLeafOrg.append(org.getOrgName()).append(",");
            }
        }
        
        if (StringUtils.isNotEmpty(isorgUserCounts.toString()))
        {
            msg = "组织【"
                +isorgUserCounts.toString().substring(0, isorgUserCounts.toString().length() - 1)
                + "】或其子组织下有用户存在，不允许删除" ;
        }
        if (StringUtils.isEmpty(msg) && StringUtils.isNotEmpty(isexsistLeafOrg.toString()))
        {
            msg = "组织【"
                + isexsistLeafOrg.toString().substring(0, isexsistLeafOrg.toString().length() - 1)
                + "】下存在下级组织或删除数据未全选择其子组织，不允许删除"; 
        }
        return msg;
    }
    
    /**
     * 取出所有的下级组织id
     * 
     * @Title getAllLeafOrg
     * @author wujialing
     * @Description: 
     * @date 2015年4月30日
     * @param orgId
     * @return
     */
    public Boolean isOrgHaveAll(String orgId,String orgIds){
        boolean flag = true ;
        String sql = " select t.org_id from t_organization t where 1=1 and t.status = 0 " ;
        sql += "connect by prior t.org_id=t.parent_id start with t.org_id= " + orgId ;
        List<BigDecimal> list = baseDao.executeNativeQuery(sql);
        for (int i = 0; i < list.size(); i++)
        {
            BigDecimal id = (BigDecimal)list.get(i);
            //包含返回true
            if (!isContainsOrgs(id.toString(), orgIds))
            {
                flag = false ;
                break;
            }
        }
        return flag ;
    }
    
    /**
     * 
     * @Title isContainsOrgs
     * @author wujialing
     * @Description: 
     * @date 2015年4月30日
     * @param orgId
     * @param orgIds
     * @return
     */
    public Boolean isContainsOrgs(String orgId,String orgIds){
        boolean flag = false ;
        for (String tempId : orgIds.split(","))
        {
             if (orgId.equals(tempId))
            {
                 flag = true ;
                 break ;
            }
        }
        return flag ;
    }
    
    
    
    /**
     * 通过参数，查询当前组织是否已删除
     * 
     * @Title getOrgIsDelete
     * @author ndy
     * @Description:
     * @date 2014年3月17日
     * @param orgId
     * @return
     */
    @Override
    public int getOrgIsDelete(Map<String, String> map)
        throws BusinessException {
        // Organization org = this.baseDao.q
        
        return 0;
    }
    
    /**
     * 通过ID判断当前组织是否存在
     * 
     * @Title userIsExist
     * @author ndy
     * @date 2014年3月26日
     * @param orgId 组织ID
     * @param code 功能编码
     * @return
     */
    @Override
    public int orgIsExist(String orgId, String code)
        throws BusinessException {
        int size = 0;
        int status = 0;
        if (!code.equals("update")) {
            status = Constant.DISABLE;
        }
        String sql =
            "select count(*) from t_organization a where a.org_id in ("
                + orgId + ") and a.status =  " + status;
        size = this.baseDao.queryCountSQL(sql);
        return size;
    }

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
    @Override
    public String getOrgIdsByPermissionScope(String startOrgId,
        String permissionOrgIds)
        throws BusinessException {
        // 判断组织id是否为空
    	List<Organization> orgListResult = new ArrayList<Organization>();
    	if(!ORG_ROOT_ID.equals(startOrgId)){
    		getOrgIdsByHierarchicalQuery( Integer.parseInt(startOrgId),  orgListResult);
    	}
    	
    	StringBuffer hqBf = new StringBuffer();
    	for(Organization childorg : orgListResult){
    		hqBf.append(",").append(childorg.getOrgId());
    	}
    	StringBuffer sqlBuffer = new StringBuffer();
    	if(hqBf.length()>0){
            
            sqlBuffer.append("select a.org_id from T_ORGANIZATION a ");
            sqlBuffer.append(" where "+(StringUtils.isNotBlank(permissionOrgIds)
                ? "  a.org_id in(" + permissionOrgIds + ")" : "1=1 "));
            /*sqlBuffer.append(" start with a.org_id=" + startOrgId);*/
            sqlBuffer.append("and a.org_id in(").append(hqBf.substring(1)).append(")");
    	}else if(ORG_ROOT_ID.equals(startOrgId)){
    		sqlBuffer.append("select a.org_id from T_ORGANIZATION a ");
            sqlBuffer.append(" where "+(StringUtils.isNotBlank(permissionOrgIds)
                ? "  a.org_id in(" + permissionOrgIds + ")" : "1=1 "));
    	}else{
    		return null;
    	}
            // 根据父遍历子
            List<Object> objs =
                baseDao.executeNativeQuery(sqlBuffer.toString());
            
            StringBuffer queryIds = new StringBuffer();
            // 判断集合是否为空
            if (!CollectionUtils.isEmpty(objs)) {
                for (Object obj : objs) {
                    
                    queryIds.append(",").append(obj);
                }
            }
            
            return queryIds.toString().substring(1);
   
    }

    /**
     * 查询组织下的所有组织。
     * 
     * @Title getOrgIdsByHierarchicalQuery
     * @author hedaojun
     * @date 2014年10月16日
     * @param startOrgId 组织id
     * @param orgListResult 存放结果的LIST
     * @return List<Organization> 存放结果的LIST
     * @throws BusinessException
     */
    private List<Organization>  getOrgIdsByHierarchicalQuery(int startOrgId, List<Organization> orgListResult)
            throws BusinessException {
            // 判断组织id是否为空
                StringBuffer sqlBuffer = new StringBuffer();
                Organization org = this.getOrgById(startOrgId);
                orgListResult.add(org);
                if(org.getOrganizations()!=null && org.getOrganizations().size() != 0){
                	for(Organization childorg : org.getOrganizations()){
                		getOrgIdsByHierarchicalQuery(childorg.getOrgId(),orgListResult);
                	}
                }
              return orgListResult;
        }
    
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
    @Override
    public List<Organization> getOrgByIds(String orgIds)
        throws BusinessException {
        List<Organization> result = null;
        if (StringUtils.isNotBlank(orgIds)) {
            String hql =
                "from Organization o where o.enable=" + Constant.ENABLE
                + " and o.status=" + Constant.STATUS_NOT_DELETE
                + " and o.orgId in (" + orgIds + ")"
                + " order by o.disOrder";
            result = baseDao.queryEntitys(hql);
        }
        return result;
    }
    
}
