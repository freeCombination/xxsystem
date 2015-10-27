package com.xx.system.user.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.constant.Constant;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.FileUtil;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.MD5Util;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.ResponseVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.service.IDictService;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Organization;
import com.xx.system.org.service.IOrgService;
import com.xx.system.org.service.IOrgUserService;
import com.xx.system.role.entity.Role;
import com.xx.system.user.entity.User;
import com.xx.system.user.service.IUserService;
import com.xx.system.user.vo.DictionaryVo;
import com.xx.system.user.vo.UserVo;

/**
 * 用户Action 所有操作用户数据相关的Action方法
 * 
 * @version V1.20,2013-11-25 上午11:28:53
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class UserAction extends BaseAction {
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = -5433084327100824821L;
    
    /**
     * @Fields logger : 日志
     */
    static Logger logger = Logger.getLogger(UserAction.class.getName());
    
    /**
     * @Fields filename :
     */
    private String filename;
    
    /**
     * @Fields uploadAttach :
     */
    private File uploadAttach;
    
    /**
     * @Fields downloadType :
     */
    private String downloadType;
    
    /**
     * @Fields userService :
     */
    @Resource
    private IUserService userService;
    
    /**
     * @Fields userRoleService :
     */
    /*@Resource
    private IUserRoleService userRoleService;*/
    
    /**
     * @Fields orgUserService :
     */
    @Resource
    private IOrgUserService orgUserService;
    
    /**
     * @Fields orgService :
     */
    @Resource
    private IOrgService orgService;
    
    /**
     * @Fields dictService :
     */
    @Resource
    private IDictService dictService;
    
    /**
     * @Fields user :
     */
    private User user;
    
    /**
     * @Fields userRole :
     */
    //private UserRole userRole;
    
    /**
     * @Fields id :
     */
    private int id;
    
    /**
     * @Fields userRoles :
     */
    //private List<UserRole> userRoles;
    
    /**
     * @Fields role :
     */
    private Role role;
    
    /**
     * @Fields org :
     */
    private Organization org;
    
    /**
     * @Fields roleIds :
     */
    private String roleIds;
    
    /**
     * @Fields result :
     */
    private String result;
    
    /**
     * @Fields users :
     */
    private List<User> users;
    
    /**
     * @Fields nowStatus : 状态
     */
    private int nowStatus;
    
    /**
     * @Fields nowEnable : 启用/锁定状态
     */
    private int nowEnable;
    
    /**
     * 跳转至用户管理页面
     * 
     * @Title getUserManage
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String getUserManage() {
        return SUCCESS;
    }
    
    /**
     * 获取字典数据的下拉框选项
     * 
     * @Title getSelectionsByType
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String getSelectionsByType() {
        String code = RequestUtil.getString(getRequest(), "dictTypeCode");
        // if (!(Constant.POST.equals(code) || Constant.POSTTITLE.equals(code)
        // || Constant.JOB.equals(code) || Constant.JOBLEVEL.equals(code)
        // || Constant.RESOURCETYPE.equals(code)
        // || Constant.ORGTYPE.equals(code) || Constant.TEAM.equals(code) ||
        // Constant.USERTYPE.equals(code)))
        // {
        // return null;
        // }
        try {
            List<Dictionary> list = dictService.getDictByTypeCode(code);
            if (list != null && list.size() > 0) {
                ListVo<DictionaryVo> vo = new ListVo<DictionaryVo>();
                List<DictionaryVo> dictList = new ArrayList<DictionaryVo>();
                for (int i = 0; i < list.size(); i++) {
                    DictionaryVo dvo = new DictionaryVo();
                    dvo.setDictionaryId(list.get(i).getPkDictionaryId());
                    dvo.setDictionaryName(list.get(i).getDictionaryName());
                    dvo.setDictCode(list.get(i).getDictCode());
                    dvo.setDictionaryValue(list.get(i).getDictionaryValue());
                    /*if (list.get(i).getDictionaryType() != null) {
                        dvo.setDictionaryType(list.get(i)
                            .getDictionaryType()
                            .getId());
                    }*/
                    dictList.add(dvo);
                }
                vo.setList(dictList);
                vo.setTotalSize(2);
                JsonUtil.outJson(vo);
            }
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class, "获取字典数据的下拉框选项", e, false);
        }
        return null;
    }
    
    public String[] getSelectionsByTypeForUserTemp() {
        String[] result;
        try {
            List<Dictionary> list = dictService.getDictByTypeCode("USERTYPE");
            if (list != null && list.size() > 0) {
                result = new String[list.size()] ;
                for (int i = 0; i < list.size(); i++) {
                    result[i] = list.get(i).getDictionaryName() ;
                }
                return result;
            }
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class, "获取字典数据的下拉框选项", e, false);
        }
        return null;
    }
    
    /**
     * 取得用户列表
     * 
     * @Title getUserList
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String getUserList() {
        try {
            Map<String, String> params =
                RequestUtil.getParameterMap(getRequest());
            ListVo<UserVo> userVoListVo = this.userService.getUserList(params);
            
            JsonUtil.outJson(userVoListVo);
            
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'获取用户列表失败！'}");
            this.excepAndLogHandle(UserAction.class, "取得用户列表", e, false);
            return LOGIN;
        }
        return null;
    }
    
    /**
     * 根据角色获取用户列表
     * 
     * @Title getUserListByRole
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String getUserListByRole() {
        try {
            Map<String, String> params =
                RequestUtil.getParameterMap(getRequest());
            //int roleId = RequestUtil.getInt(getRequest(), "roleId");
            ListVo<UserVo> userVoListVo = this.userService.getUserList(params);
            /*List<UserRole> userRoleList =
                userRoleService.getUserRoleListByRoleId(String.valueOf(roleId));
            List<Integer> userIdList = new ArrayList<Integer>();
            if (userRoleList != null && userRoleList.size() > 0) {
                for (UserRole ur : userRoleList) {
                    userIdList.add(ur.getUser().getUserId());
                }
            }
            List<UserVo> uvos = userVoListVo.getList();
            for (UserVo uvo : uvos) {
                if (userIdList.contains(uvo.getUserId())) {
                    uvo.setFlag("1");
                }
            }*/
            JsonUtil.outJson(userVoListVo);
            
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'获取用户列表失败！'}");
            this.excepAndLogHandle(UserAction.class, "根据角色获取用户列表", e, false);
            return LOGIN;
        }
        return null;
    }
    
    /**
     * 新增用户
     * 
     * @Title addUser
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String addUser() {
        
        try {
            String orgIds = getRequest().getParameter("orgIds");
            user.setStatus(Constant.STATUS_NOT_DELETE);
            user.setEnable(Constant.ENABLE);
            
            if (user.getDisOrder() == null || user.getDisOrder() == 0) {
                user.setDisOrder(0);
            }
            
            user.setPassword(MD5Util.encode(user.getPassword()));
            userService.addUpdateUser(user);
            String[] ids = orgIds.split(",");
            for (String id : ids) {
                Organization o = orgService.getOrgById(NumberUtils.toInt(id));
                OrgUser ou = new OrgUser();
                ou.setIsDelete(Constant.STATUS_NOT_DELETE);
                ou.setOrganization(o);
                ou.setUser(user);
                orgUserService.addOrgUser(ou);
            }
            
            JsonUtil.outJson("{success:true,msg:'添加用户成功!'}");
            this.excepAndLogHandle(UserAction.class, "新增用户", null, true);
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class, "新增用户", e, false);
            JsonUtil.outJson("{success:false,msg:'添加用户失败!'}");
        }
        return null;
    }
    
    /**
     * 取得在线用户
     * 
     * @Title getOnlineUsers
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String getOnlineUsers() {
        try {
            users =
                userService.getOnlineUser(super.getCurrentUser().getUserId());
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class, "取得在线用户", e, false);
        }
        return SUCCESS;
    }
    
    /**
     * 根据ID查询用户
     * 
     * @Title getUserById
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String getUserById() {
        try {
            if (id != 0) {
                user = userService.getUserById(id);
            }
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class, "根据ID查询用户", e, false);
            return ERROR;
        }
        return SUCCESS;
    }
    
    /**
     * 获取修改数据
     * 
     * @Title getUserByIdForUpdate
     * @author wujialing
     * @Description: 
     * @date 2015年7月9日
     */
    public void getUserByIdForUpdate() {
        try {
            if (id != 0) {
                ResponseVo vo = new ResponseVo();
                Map<String, Object> data = userService.getUserByIdForUpdate(id);
                vo.setData(data);
                JsonUtil.outJson(vo);
            }
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class, "获取修改数据", e, false);
        }
    }
    
    
    
    
    /**
     * 修改用户信息
     * 
     * @Title editUser
     * @date 2013-11-25
     * @return
     */
    public String editUser() {
        try {
            User oldUser = userService.getUserById(user.getUserId());

            if (StringUtils.isNotBlank(user.getGender())) {
                oldUser.setGender(user.getGender());
            }
            if (StringUtils.isNotBlank(user.getRealname())) {
                oldUser.setRealname(user.getRealname());
            }
            oldUser.setMobileNo(user.getMobileNo());
            oldUser.setPhoneNo(user.getPhoneNo());
            oldUser.setShortNo(user.getShortNo());
            oldUser.setEmail(user.getEmail());
            oldUser.setErpId(user.getErpId());
            oldUser.setBirthDay(user.getBirthDay());
            oldUser.setDisOrder(user.getDisOrder());
            oldUser.setBirthPlace(user.getBirthPlace());
            oldUser.setIdCard(user.getIdCard());
            
            if (user.getResponsibilities() != null) {
            	oldUser.setResponsibilities(user.getResponsibilities());
            }
            
            oldUser.setNationality(user.getNationality());
            oldUser.setPartyDate(user.getPartyDate());
            oldUser.setJobStartDate(user.getJobStartDate());
            oldUser.setOfficeHoldingDate(user.getOfficeHoldingDate());
            oldUser.setEducationBackground(user.getEducationBackground());
            oldUser.setTechnicaTitles(user.getTechnicaTitles());
            oldUser.setComeDate(user.getComeDate());
            oldUser.setSkill(user.getSkill());
            oldUser.setPerformance(user.getPerformance());
            oldUser.setEmploymentInfo(user.getEmploymentInfo());
            oldUser.setPostWage(user.getPostWage());
            oldUser.setTrainInfo(user.getTrainInfo());
            oldUser.setPoliticsStatus(user.getPoliticsStatus());
            
            userService.addUpdateUser(oldUser);
            
            String orgIds = getRequest().getParameter("orgIds");
            String[] ids = orgIds.split(",");
            // 待增加/更新的用户组织关系
            List<String> orgIdList = Arrays.asList(ids);
            // 待删除的用户组织关系
            List<OrgUser> ouListForDelete = new ArrayList<OrgUser>();
            // 原有组织
            List<OrgUser> ouList =
                orgUserService.getOrgUserByUserId(user.getUserId());
            
            for (OrgUser orgUser : ouList) {
                if (!orgIdList.contains(StringUtil.getStr(orgUser.getOrganization()
                    .getOrgId()))) {
                    ouListForDelete.add(orgUser);
                }
            }
            orgUserService.deleteOrgUsers(ouListForDelete);
            for (String id : orgIdList) {
                Organization o = orgService.getOrgById(NumberUtils.toInt(id));
                List<OrgUser> existOrgUser =
                    orgUserService.getOrgUserByOrgAndUser(o, oldUser);
                // 如果该用户组织关系已经存在，则跳过，否则新增/修改
                if (existOrgUser != null && existOrgUser.size() > 0
                    && existOrgUser.get(0) != null) {
                    continue;
                } else {
                    OrgUser ou = new OrgUser();
                    ou.setOrganization(o);
                    ou.setUser(user);
                    ou.setIsDelete(Constant.STATUS_NOT_DELETE);
                    orgUserService.addUpdateUser(ou);
                }
                
            }
            JsonUtil.outJson("{success:true,msg:'修改用户成功！'}");
            this.excepAndLogHandle(UserAction.class, "修改用户信息", null, true);
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'修改用户失败！'}");
            this.excepAndLogHandle(UserAction.class, "修改用户信息", e, false);
        }
        return null;
    }
    
    /**
     * 修改用户登录密码
     * 
     * @Title updatePassword
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String updatePassword() {
        try {
            User updateUser =
                this.userService.getUserById(((User)super.getSession()
                    .getAttribute(Constant.CURRENT_USER)).getUserId());
            String oPwd = updateUser.getPassword();
            String oldPwd = this.getRequest().getParameter("oldPassword");
            oldPwd = MD5Util.encode(oldPwd);
            String newPassword = this.getRequest().getParameter("newPassword");
            newPassword = MD5Util.encode(newPassword);
            if (!oPwd.equals(oldPwd)) {
                this.output.put("success", false);
                this.output.put("msg", "输入的原密码错误");
            } else {
                updateUser.setPassword(newPassword);
                this.userService.updateUserEnable(updateUser);
                JsonUtil.outJson("{success:true,msg:'密码修改成功'}");
                this.excepAndLogHandle(UserAction.class, "修改用户登录密码", null, true);
            }
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'密码修改失败'}");
            this.excepAndLogHandle(UserAction.class, "修改用户登录密码", e, false);
        }
        return null;
    }
    
    /**
     * 修改密码首页跳转
     * 
     * @Title modifyPasswordIndex
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String modifyPasswordIndex() {
        return SUCCESS;
    }
    
    /**
     * 验证输入的密码是否跟登录密码一致
     * 
     * @Title validatePassword
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String validatePassword() {
        Map<String, Object> vaildator = new HashMap<String, Object>();
        User user =
            (User)super.getSession().getAttribute(Constant.CURRENT_USER);
        String validatePassword = this.getRequest().getParameter("oldPassword");
        if (!StringUtil.isEmpty(validatePassword)) {
            if (!user.getPassword().equals(MD5Util.encode(validatePassword))) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "输入原密码错误");
            } else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
        } else {
            vaildator.put("success", true);
            vaildator.put("valid", true);
            vaildator.put("reason", "");
        }
        JsonUtil.outJson(vaildator);
        return null;
    }
    
    /**
     * 重置密码
     * 
     * @Title resetUserPassword
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String resetUserPassword() {
        String userIds = this.getRequest().getParameter("userIds");
        try {
            if (!StringUtil.isEmpty(userIds)) {
                String[] userIdArr = userIds.split(",");
                if (userIdArr.length > 0) {
                    for (String id : userIdArr) {
                        User u = userService.getUserById(Integer.parseInt(id));
                        u.setPassword(MD5Util.encode(Constant.DEFAULT_PASSWORD));
                        userService.resetUserPassword(u);
                    }
                }
            }
            JsonUtil.outJson("{success:true,msg:'密码重置成功'}");
            this.excepAndLogHandle(UserAction.class, "重置密码", null, true);
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'密码重置失败！'}");
            this.excepAndLogHandle(UserAction.class, "重置密码", e, false);
        }
        return null;
    }
    
    /**
     * 批量删除用户
     * 
     * @Title deleteUser
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    public String deleteUser() {
        try {
            userService.batchDeleteUser(this.getRequest()
                .getParameter("userIds"));
            JsonUtil.outJson("{success:true,msg:'删除用户成功！'}");
            this.excepAndLogHandle(UserAction.class, "批量删除用户", null, true);
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'删除用户失败！'}");
            this.excepAndLogHandle(UserAction.class, "批量删除用户", e, false);
        }
        return null;
    }
    
    /**
     * 登录跳转
     * 
     * @Title toAdmin
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String toAdmin() {
        return SUCCESS;
    }
    
    /**
     * 为用户分配角色
     * 
     * @Title addUserRole
     * @author wanglc
     * @date 2013-11-25
     * @return String
     */
    /*public String addUserRole() {
        try {
            String userIds = this.getRequest().getParameter("userIds");
            String roleId = this.getRequest().getParameter("roleId");
            String orgId = this.getRequest().getParameter("orgId");
            String[] ids = userIds.split(",");
            for (String userId : ids) {
                userService.addUserRoles(NumberUtils.toInt(userId),
                    roleId,
                    NumberUtils.toInt(orgId));
            }
            JsonUtil.outJson("{success:true,msg:'添加用户角色关系成功'}");
            this.excepAndLogHandle(UserAction.class, "为用户分配角色", null, true);
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'添加用户角色关系失败'}");
            this.excepAndLogHandle(UserAction.class, "为用户分配角色", e, false);
        }
        return null;
    }*/
    
    /**
     * 为用户分配资源
     * 
     * @Title addUserResource
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    /*public String addUserResource() {
        try {
            String userId = this.getRequest().getParameter("userId");
            String resourceId = this.getRequest().getParameter("resourceId");
            String orgId = this.getRequest().getParameter("orgId");
            userService.addUserResources(NumberUtils.toInt(userId),
                resourceId,
                NumberUtils.toInt(orgId));
            JsonUtil.outJson("{success:true,msg:'添加用户权限成功'}");
            this.excepAndLogHandle(UserAction.class, "为用户分配资源", null, true);
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'添加用户资源失败'}");
            this.excepAndLogHandle(UserAction.class, "为用户分配资源", e, false);
        }
        return null;
    }*/
    
    /**
     * 删除用户角色
     * 
     * @Title deleteUserRole
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String deleteUserRole() {
        try {
            String userIds = this.getRequest().getParameter("userIds");
            String roleId = this.getRequest().getParameter("roleId");
            String orgId = this.getRequest().getParameter("orgId");
            if (!"".equals(userIds)) {
                for (String userId : userIds.split(",")) {
                    userService.deleteUserRoles(NumberUtils.toInt(userId),
                        NumberUtils.toInt(roleId),
                        NumberUtils.toInt(orgId));
                }
                JsonUtil.outJson("{success:true,msg:'删除用户角色关系成功'}");
                this.excepAndLogHandle(UserAction.class, "删除用户角色", null, true);
            }
        } catch (Exception e) {
            JsonUtil.outJson("{success:true,msg:'删除用户角色关系失败'}");
            this.excepAndLogHandle(UserAction.class, "删除用户角色", e, false);
        }
        return null;
    }
    
    /**
     * 删除用户资源
     * 
     * @Title deleteUserResource
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    /*public String deleteUserResource() {
        try {
            String userId = this.getRequest().getParameter("userId");
            String resourceId = this.getRequest().getParameter("resourceId");
            String orgId = this.getRequest().getParameter("orgId");
            boolean msg = true;
            if (StringUtils.isNotBlank(resourceId)
                && resourceId.split(",").length > 0
                && StringUtils.isNotBlank(userId)
                && StringUtils.isNotBlank(orgId)) {
                for (String resId : resourceId.split(",")) {
                    msg =
                        userService.deleteUserResource(NumberUtils.toInt(userId),
                            NumberUtils.toInt(resId),
                            NumberUtils.toInt(orgId));
                    if (!msg) {
                        break;
                    }
                }
            }
            if (!msg) {
                JsonUtil.outJson("{success:false,msg:'该资源属于角色，不能删除！'}");
            } else {
                JsonUtil.outJson("{success:true,msg:'删除用户资源成功'}");
                this.excepAndLogHandle(UserAction.class, "删除用户资源", null, true);
            }
        } catch (Exception e) {
            JsonUtil.outJson("{success:true,msg:'删除用户资源失败'}");
            this.excepAndLogHandle(UserAction.class, "删除用户资源", e, false);
        }
        return null;
    }*/
    
    /**
     * 查询用户在特定组织下的角色集合
     * 
     * @Title queryUserRoleUnderOrg
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    /*public String queryUserRoleUnderOrg() {
        String userId = this.getRequest().getParameter("userId");
        String orgId = this.getRequest().getParameter("orgId");
        List<UserRole> list;
        try {
            list =
                userService.getUserRoles(NumberUtils.toInt(userId),
                    NumberUtils.toInt(orgId));
            Integer[] roleIds = new Integer[list.size()];
            for (int i = 0; i < list.size(); i++) {
                roleIds[i] = list.get(i).getRole().getRoleId();
            }
            JsonUtil.outJsonArray(roleIds);
        } catch (BusinessException e) {
            this.excepAndLogHandle(UserAction.class,
                "查询用户在特定组织下的角色集合",
                e,
                false);
        }
        
        return null;
    }*/
    
    /**
     * 查询用户在特定组织下的资源集合
     * 
     * @Title queryUserResources
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    /*public String queryUserResources() {
        @SuppressWarnings("rawtypes")
        List list = new ArrayList();
        String userId = this.getRequest().getParameter("userId");
        String orgId = this.getRequest().getParameter("orgId");
        
        // 用户特殊资源主键数组
        Integer[] userOrgResources;
        try {
            userOrgResources =
                userService.getUserResources(NumberUtils.toInt(userId),
                    NumberUtils.toInt(orgId));
            // 用户所对应的角色资源主键数组
            Integer[] roleResources =
                userService.getRoleResource(NumberUtils.toInt(userId),
                    NumberUtils.toInt(orgId));
            
            list.add(userOrgResources);
            list.add(roleResources);
            
            JsonUtil.outJsonArray(list);
        } catch (BusinessException e) {
            this.excepAndLogHandle(UserAction.class,
                "查询用户在特定组织下的资源集合",
                e,
                false);
        }
        return null;
    }*/
    
    /**
     * 修改用户启用/锁定
     * 
     * @Title updateUserEnable
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String updateUserEnable() {
        try {
            String userId = this.getRequest().getParameter("userId");
            
            User updateUser =
                this.userService.getUserById(NumberUtils.toInt(userId));
            
            if (updateUser.getEnable() == 1) {
                updateUser.setEnable(0);
                this.userService.updateUserEnable(updateUser);
                JsonUtil.outJson("{success:true,msg:'禁用已解除'}");
                this.excepAndLogHandle(UserAction.class, "禁用已解除", null, true);
            } else {
                updateUser.setEnable(1);
                this.userService.updateUserEnable(updateUser);
                JsonUtil.outJson("{success:true,msg:'禁用已设定'}");
                this.excepAndLogHandle(UserAction.class, "禁用已设定", null, true);
            }
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'禁用用户失败'}");
            this.excepAndLogHandle(UserAction.class, "修改用户启用/锁定", e, false);
        }
        return null;
    }
    
    /**
     * 添加用户，验证输入的用户名唯一性
     * 
     * @Title validateAddUserName
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String validateAddUserName() {
        Map<String, Object> vaildator = new HashMap<String, Object>();
        String userName = this.getRequest().getParameter("value");
        int userNameSize;
        try {
            userNameSize = this.userService.validateAddUserName(userName);
            
            if (userNameSize != 0) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "用户名已经存在！");
            } else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            JsonUtil.outJson(vaildator);
        } catch (BusinessException e) {
            this.excepAndLogHandle(UserAction.class,
                "(添加用户)验证输入的用户名唯一性",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 修改用户，验证输入的用户名唯一性
     * 
     * @Title validateUpdateUserName
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String validateUpdateUserName() {
        HttpServletRequest request = getRequest();
        String userName = request.getParameter("value");
        String userId = request.getParameter("userId");
        Map<String, Object> vaildator = new HashMap<String, Object>();
        
        int userNameSize;
        try {
            userNameSize =
                this.userService.validateUpdateUerName(userName, userId);
            if (userNameSize != 0) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "用户名已经存在！");
            } else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            JsonUtil.outJson(vaildator);
        } catch (BusinessException e) {
            this.excepAndLogHandle(UserAction.class,
                "(修改用户)验证输入的用户名唯一性",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 下载用户导入/用户角色关系导入模版
     * 
     * @Title getExcelTemplateStream
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public InputStream getExcelTemplateStream() {
        HttpServletRequest request = this.getRequest();
        ServletContext servletContext =
            request.getSession().getServletContext();
        InputStream in = null;
        try {
            if ("user".equals(downloadType)) {
                //修改模板中用户类比
                String[] textlist = getSelectionsByTypeForUserTemp();
                if (null != textlist && textlist.length != 0)
                {
                  in =  userService.updateTempleUser(servletContext.getRealPath("/template/userTemplate.xls"), textlist);
                }else{
                    in =
                        new FileInputStream(
                            servletContext.getRealPath("/template/userTemplate.xls"));
                }
                filename = new String("用户导入模版.xls".getBytes(), "ISO8859-1");
            } else {
                in =
                    new FileInputStream(
                        servletContext.getRealPath("/template/userRoleTemplate.xls"));
                filename = new String("用户角色关系导入模版.xls".getBytes(), "ISO8859-1");
            }
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class,
                "下载用户导入/用户角色关系导入模版",
                e,
                false);
        }
        return in;
    }
    
    /**
     * 通过上传EXCEl文件，导入用户
     * 
     * @Title uploadExcelToBacthImportUser
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String uploadExcelToBacthImportUser() {
        try {
            HttpServletRequest request = this.getRequest();
            ServletContext servletContext =
                request.getSession().getServletContext();
            String fileUrl =
                FileUtil.upload(uploadAttach, filename, "upload/importUser");
            String message =
                userService.importUser(servletContext.getRealPath("/")
                    + fileUrl);
            
            this.excepAndLogHandle(UserAction.class,
                "通过上传EXCEl文件，导入用户", null, true);
            JsonUtil.outJson("{success:true,msg:'" + message + "'}");
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class,
                "通过上传EXCEl文件，导入用户",
                e,
                false);
        }
        return null;
    }
    
    /**
     * 通过上传EXCEl文件，导入用户与角色关系
     * 
     * @Title uploadExcelToBacthImportUserRole
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    /*public String uploadExcelToBacthImportUserRole() {
        try {
            HttpServletRequest request = this.getRequest();
            ServletContext servletContext =
                request.getSession().getServletContext();
            String fileUrl =
                FileUtil.upload(uploadAttach, filename, "upload/importUserRole");
            String message =
                userService.importUserRole(servletContext.getRealPath("/")
                    + fileUrl);
            JsonUtil.outJson("{success:true,msg:'" + message + "'}");
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class,
                "通过上传EXCEl文件，导入用户与角色关系",
                e,
                false);
        }
        return null;
    }*/
    
    /**
     * 用户信息导出到EXCEL
     * 
     * @Title getExportAllUsersInputStream
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public InputStream getExportAllUsersInputStream() {
        List<User> list;
        InputStream inputStream = null;
        try {
            list = userService.getAllUser();
            List<OrgUser> ouList = new ArrayList<OrgUser>();
            for (User u : list) {
                List<OrgUser> ous =
                    (List<OrgUser>)orgUserService.getOrgUserByUserId(u.getUserId());
                if (ous != null && ous.size() > 0) {
                    ouList.addAll(ous);
                }
            }
            inputStream = userService.exportAllUsers(list, ouList);
            filename = new String("用户信息导出.xls".getBytes(), "ISO8859-1");
            this.excepAndLogHandle(UserAction.class, "用户信息导出到EXCEL", null, true);
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class, "用户信息导出到EXCEL", e, false);
        }
        return inputStream;
    }
    
    /**
     * 校验用户名是否存在
     * 
     * @Title validateUserName
     * @author yzg
     * @date 2014-2-11
     */
    public void validateUserName() {
        String userName = RequestUtil.getString(getRequest(), "userName");
        User user;
        try {
            user = userService.getUserByUsername(userName);
            if (user != null) {
                JsonUtil.outJson("{exist:true,msg:'登录名已存在！'}");
            } else {
                JsonUtil.outJson("{exist:false,msg:'该登录名尚未被使用'}");
            }
        } catch (BusinessException e) {
            this.excepAndLogHandle(UserAction.class, "校验用户名是否存在", e, false);
        }
    }
    
    /**
     * 获取指定用户的所有部门名称及ID，逗号分隔
     * 
     * @Title getUserOrgs
     * @author yzg
     * @date 2014-2-19
     */
    public void getUserOrgs() {
        String userId = RequestUtil.getString(getRequest(), "userId");
        String result;
        try {
            result = userService.getUserOrgs(userId);
            JsonUtil.outJson(result);
        } catch (BusinessException e) {
            this.excepAndLogHandle(UserAction.class,
                "获取指定用户的所有部门名称及ID",
                e,
                false);
        }
        
    }
    
    /**
     * 通过ID和操作编码判断当前用户是否存在
     * 
     * @Title userIsExist
     * @author ndy
     * @date 2014年3月26日
     * @return
     */
    public String userIsExist() {
        String userId = RequestUtil.getString(getRequest(), "userId");
        String code = RequestUtil.getString(getRequest(), "code");
        try {
            int size = this.userService.userIsExist(userId, code);
            if (code.equals("delete")) {
                if (size != 0) {
                    JsonUtil.outJson("{success:true,msg:'选择的数据已删除，列表已刷新。'}");
                } else {
                    JsonUtil.outJson("{success:false,msg:'用户存在'}");
                }
            } else {
                if (size != 0) {
                    JsonUtil.outJson("{success:true,msg:'用户存在'}");
                } else {
                    JsonUtil.outJson("{success:false,msg:'选择的数据已删除，列表已刷新。'}");
                }
            }
        } catch (Exception e) {
            this.excepAndLogHandle(UserAction.class,
                "通过ID和操作编码判断当前用户是否存在",
                e,
                false);
        }
        return null;
    }
    
    /**
     * <p>
     * Title execute
     * </p>
     * <p>
     * Author wanglc
     * </p>
     * <p>
     * Description ActionSupport#execute()
     * </p>
     * 
     * @return
     * @throws Exception
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute()
        throws Exception {
        return SUCCESS;
    }
    
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    /*public List<UserRole> getUserRoles() {
        return userRoles;
    }
    
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }*/
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public Organization getOrg() {
        return org;
    }
    
    public void setOrg(Organization org) {
        this.org = org;
    }
    
    public String getRoleIds() {
        return roleIds;
    }
    
    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public int getNowStatus() {
        return nowStatus;
    }
    
    public void setNowStatus(int nowStatus) {
        this.nowStatus = nowStatus;
    }
    
    public int getNowEnable() {
        return nowEnable;
    }
    
    public void setNowEnable(int nowEnable) {
        this.nowEnable = nowEnable;
    }
    
    /*public UserRole getUserRole() {
        return userRole;
    }
    
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }*/
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public File getUploadAttach() {
        return uploadAttach;
    }
    
    public void setUploadAttach(File uploadAttach) {
        this.uploadAttach = uploadAttach;
    }
    
    public String getDownloadType() {
        return downloadType;
    }
    
    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }
    
    /*public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }*/
    
    public IOrgUserService getOrgUserService() {
        return orgUserService;
    }
    
    public void setOrgUserService(IOrgUserService orgUserService) {
        this.orgUserService = orgUserService;
    }
    
    public void setOrgService(IOrgService orgService) {
        this.orgService = orgService;
    }
    
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
    
}
