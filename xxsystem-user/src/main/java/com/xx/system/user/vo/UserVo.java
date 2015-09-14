package com.xx.system.user.vo;

import com.xx.system.org.entity.Organization;
import com.xx.system.user.entity.User;

/**
 * 用户Vo
 * 
 * @version V1.20,2013-11-25 下午2:08:39
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class UserVo {
    /**
     * @Fields roleMemberID : 角色成员id
     */
    public int roleMemberId;
    
    /**
     * @Fields userId : 用户ID
     */
    public int userId;
    
    /**
     * @Fields orgName : 部门名称
     */
    public String orgName;
    
    /**
     * @Fields unit : 小组
     */
    public String unit;
    
    /**
     * @Fields username : 登录名
     */
    private String username;
    
    /**
     * @Fields password : 密码
     */
    private String password;
    
    /**
     * @Fields realname : 真实姓名
     */
    private String realname;
    
    /**
     * @Fields gender : 性别
     */
    private String gender;
    
    /**
     * @Fields mobileNo1 : 手机1
     */
    private String mobileNo1;
    
    /**
     * @Fields mobileNo2 : 手机2
     */
    private String mobileNo2;
    
    /**
     * @Fields phoneNo : 固话
     */
    private String phoneNo;
    
    /**
     * @Fields idCard : 身份证号
     */
    private String idCard;
    
    /**
     * @Fields shortNo1 : 短号1
     */
    private String shortNo1;
    
    /**
     * @Fields shortNo2 : 短号2
     */
    private String shortNo2;
    
    /**
     * @Fields status : 状态
     */
    private int status;
    
    /**
     * @Fields disOrder : 排序
     */
    private int disOrder;
    
    /**
     * @Fields enable : 是否禁用：0未禁用，1禁用
     */
    private int enable;
    
    /**
     * @Fields typeValue : 用户类别值
     */
    private int typeValue;
    
    /**
     * @Fields typeText : 用户类别
     */
    private String typeText;
    
    /**
     * @Fields postValue : 职位值
     */
    private int postValue;
    
    /**
     * @Fields postText : 职位名称
     */
    private String postText;
    
    /**
     * @Fields postTitleValue : 职称值
     */
    private int postTitleValue;
    
    /**
     * @Fields postTitleText :职称名称
     */
    private String postTitleText;
    
    /**
     * @Fields jobValue1 : 职务值1
     */
    private int jobValue1;
    
    /**
     * @Fields jobText1 : 职务名称1
     */
    private String jobText1;
    
    /**
     * @Fields jobValue2 : 职务值2
     */
    private int jobValue2;
    
    /**
     * @Fields jobText2 : 职务名称2
     */
    private String jobText2;
    
    /**
     * @Fields jobLevelValue : 职级值
     */
    private int jobLevelValue;
    
    /**
     * @Fields jobLevelText : 职级名称
     */
    private String jobLevelText;
    
    /**
     * @Fields isDeletable : 是否允许删除
     */
    private int isDeletable;
    
    /**
     * @Fields birthPlace :出生地
     */
    private String birthPlace;
    
    /**
     * @Fields email : 邮箱
     */
    private String email;
    
    /**
     * @Fields teamValue : 班组值
     */
    private int teamValue;
    
    /**
     * @Fields teamText : 班组名称
     */
    private String teamText;
    
    /**
     * @Fields erpId : erp编号
     */
    private String erpId;
    
    /**
     * @Fields orgId : 组织ID
     */
    private int orgId;
    
    /**
     * @Fields birthDay : 出生日期
     */
    private String birthDay;
    
    /**
     * @Fields flag : 删除标志：0未删除，1已删除
     */
    private String flag;
    
    /**
     * <p>
     * Title: UserVo
     * </p>
     * <p>
     * Description: 默认构造方法
     * </p>
     */
    public UserVo() {
    }
    
    /**
     * <p>
     * Title: UserVo
     * </p>
     * <p>
     * Description: 带参数的构造方法
     * </p>
     * 
     * @param org 组织对象
     * @param user 用户对象
     */
    public UserVo(Organization org, User user) {
        /*this.postText =
            user.getPost() == null ? "" : user.getPost().getDictionaryName();
        this.postValue =
            user.getPost() == null ? Constant.ZORE : user.getPost()
                .getPkDictionaryId();
        this.postTitleValue =
            user.getPostTitle() == null ? Constant.ZORE : user.getPostTitle()
                .getPkDictionaryId();
        this.postTitleText =
            user.getPostTitle() == null ? "" : user.getPostTitle()
                .getDictionaryName();
        this.jobValue1 =
            user.getJob1() == null ? Constant.ZORE : user.getJob1()
                .getPkDictionaryId();
        this.jobText1 =
            user.getJob1() == null ? "" : user.getJob1().getDictionaryName();
        this.jobValue2 =
            user.getJob2() == null ? Constant.ZORE : user.getJob2()
                .getPkDictionaryId();
        this.jobText2 =
            user.getJob2() == null ? "" : user.getJob2().getDictionaryName();
        this.jobLevelValue =
            user.getJobLevel() == null ? Constant.ZORE : user.getJobLevel()
                .getPkDictionaryId();
        this.jobLevelText =
            user.getJobLevel() == null ? "" : user.getJobLevel()
                .getDictionaryName();
        this.isDeletable = user.getIsDeletAble();
        this.birthDay = user.getBirthDay() == null ? "" : user.getBirthDay();
        this.disOrder = user.getDisOrder() == null ? 0 : user.getDisOrder();
        this.enable = user.getEnable();
        this.gender = user.getGender() == null ? "" : user.getGender();
        this.email = user.getEmail() == null ? "" : user.getEmail();
        this.idCard = user.getIdCard() == null ? "" : user.getIdCard();
        this.mobileNo1 = user.getMobileNo1() == null ? "" : user.getMobileNo1();
        this.mobileNo2 = user.getMobileNo2() == null ? "" : user.getMobileNo2();
        this.phoneNo = user.getPhoneNo() == null ? "" : user.getPhoneNo();
        this.shortNo1 = user.getShortNo1() == null ? "" : user.getShortNo1();
        this.shortNo2 = user.getShortNo2() == null ? "" : user.getShortNo2();
        this.teamText =
            user.getTeam() == null ? "" : user.getTeam().getDictionaryName();
        this.teamValue =
            user.getTeam() == null ? Constant.ZORE : user.getTeam()
                .getPkDictionaryId();
        this.birthPlace =
            user.getBirthPlace() == null ? "" : user.getBirthPlace();
        this.password = user.getPassword() == null ? "" : user.getPassword();
        this.realname = user.getRealname() == null ? "" : user.getRealname();
        this.status = user.getStatus();
        this.typeValue =
            user.getType() == null ? Constant.ZORE : user.getType()
                .getPkDictionaryId();
        this.typeText =
            user.getType() == null ? "" : user.getType().getDictionaryName();
        this.userId = user.getUserId() == null ? 0 : user.getUserId();
        this.username = user.getUsername() == null ? "" : user.getUsername();
        this.erpId = user.getErpId() == null ? "0" : user.getErpId();
        this.isDeletable =
            user.getIsDeletAble() == null ? 0 : user.getIsDeletAble();
        
        // 属于多部门的情况，进行部门名称的拼接
        Set<OrgUser> ouSet = user.getOrgUsers();
        if (ouSet.size() <= 1) {
            this.orgName = ouSet.iterator().next().getOrganization().getOrgName();
        }
        else {
            Set<OrgUser> sort = new TreeSet<OrgUser>(new Comparator<OrgUser>() {
                @Override
                public int compare(OrgUser ou1, OrgUser ou2) {
                    return ou1.getOrganization().getOrgId() - ou2.getOrganization().getOrgId();
                }
            });
            
            Iterator<OrgUser> sortIt = ouSet.iterator();
            String orgs = "";
            while (sortIt.hasNext()) {
                sort.add(sortIt.next());
            }
            
            Iterator<OrgUser> it = sort.iterator();
            while (it.hasNext()) {
                OrgUser ou = it.next();
                if (ou.getIsDelete() == Constant.STATUS_NOT_DELETE) {
                    orgs = orgs + "," + ou.getOrganization().getOrgName();
                }
            }
            
            this.orgName = StringUtils.isBlank(orgs) ? "" : orgs.substring(1);
        }*/
        
        //this.orgId = org.getOrgId();
    }
    
    /**
     * @return userId
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * @param userId 要设置的 userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    /**
     * @return orgName
     */
    public String getOrgName() {
        return orgName;
    }
    
    /**
     * @param orgName 要设置的 orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    /**
     * @return unit
     */
    public String getUnit() {
        return unit;
    }
    
    /**
     * @param unit 要设置的 unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @param username 要设置的 username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * @param password 要设置的 password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return realname
     */
    public String getRealname() {
        return realname;
    }
    
    /**
     * @param realname 要设置的 realname
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }
    
    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * @param gender 要设置的 gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * @return mobileNo1
     */
    public String getMobileNo1() {
        return mobileNo1;
    }
    
    /**
     * @param mobileNo 要设置的 mobileNo
     */
    public void setMobileNo1(String mobileNo1) {
        this.mobileNo1 = mobileNo1;
    }
    
    /**
     * @return mobileNo2
     */
    public String getMobileNo2() {
        return mobileNo2;
    }
    
    /**
     * @param mobileNo2 要设置的 mobileNo2
     */
    public void setMobileNo2(String mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }
    
    /**
     * @return phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }
    
    /**
     * @param phoneNo 要设置的 phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    /**
     * @return idCard
     */
    public String getIdCard() {
        return idCard;
    }
    
    /**
     * @param idCard 要设置的 idCard
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    /**
     * @return shortNo1
     */
    public String getShortNo1() {
        return shortNo1;
    }
    
    /**
     * @param shortNo1 要设置的 shortNo1
     */
    public void setShortNo1(String shortNo1) {
        this.shortNo1 = shortNo1;
    }
    
    /**
     * @return shortNo2
     */
    public String getShortNo2() {
        return shortNo2;
    }
    
    /**
     * @param shortNo2 要设置的 shortNo2
     */
    public void setShortNo2(String shortNo2) {
        this.shortNo2 = shortNo2;
    }
    
    /**
     * @return status
     */
    public int getStatus() {
        return status;
    }
    
    /**
     * @param status 要设置的 status
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * @return disOrder
     */
    public int getDisOrder() {
        return disOrder;
    }
    
    /**
     * @param disOrder 要设置的 disOrder
     */
    public void setDisOrder(int disOrder) {
        this.disOrder = disOrder;
    }
    
    /**
     * @return enable
     */
    public int getEnable() {
        return enable;
    }
    
    /**
     * @param enable 要设置的 enable
     */
    public void setEnable(int enable) {
        this.enable = enable;
    }
    
    /**
     * @return typeValue
     */
    public int getTypeValue() {
        return typeValue;
    }
    
    /**
     * @param typeValue 要设置的 typeValue
     */
    public void setTypeValue(int typeValue) {
        this.typeValue = typeValue;
    }
    
    /**
     * @return typeText
     */
    public String getTypeText() {
        return typeText;
    }
    
    /**
     * @param typeText 要设置的 typeText
     */
    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }
    
    /**
     * @return postValue
     */
    public int getPostValue() {
        return postValue;
    }
    
    /**
     * @param postValue 要设置的 postValue
     */
    public void setPostValue(int postValue) {
        this.postValue = postValue;
    }
    
    /**
     * @return postText
     */
    public String getPostText() {
        return postText;
    }
    
    /**
     * @param postText 要设置的 postText
     */
    public void setPostText(String postText) {
        this.postText = postText;
    }
    
    /**
     * @return postTitleValue
     */
    public int getPostTitleValue() {
        return postTitleValue;
    }
    
    /**
     * @param postTitleValue 要设置的 postTitleValue
     */
    public void setPostTitleValue(int postTitleValue) {
        this.postTitleValue = postTitleValue;
    }
    
    /**
     * @return postTitleText
     */
    public String getPostTitleText() {
        return postTitleText;
    }
    
    /**
     * @param postTitleText 要设置的 postTitleText
     */
    public void setPostTitleText(String postTitleText) {
        this.postTitleText = postTitleText;
    }
    
    /**
     * @return jobValue1
     */
    public int getJobValue1() {
        return jobValue1;
    }
    
    /**
     * @param jobValue1 要设置的 jobValue1
     */
    public void setJobValue1(int jobValue1) {
        this.jobValue1 = jobValue1;
    }
    
    /**
     * @return jobText1
     */
    public String getJobText1() {
        return jobText1;
    }
    
    /**
     * @param jobText1 要设置的 jobText1
     */
    public void setJobText1(String jobText1) {
        this.jobText1 = jobText1;
    }
    
    /**
     * @return jobValue2
     */
    public int getJobValue2() {
        return jobValue2;
    }
    
    /**
     * @param jobValue2 要设置的 jobValue2
     */
    public void setJobValue2(int jobValue2) {
        this.jobValue2 = jobValue2;
    }
    
    /**
     * @return jobText2
     */
    public String getJobText2() {
        return jobText2;
    }
    
    /**
     * @param jobText2 要设置的 jobText2
     */
    public void setJobText2(String jobText2) {
        this.jobText2 = jobText2;
    }
    
    /**
     * @return jobLevelValue
     */
    public int getJobLevelValue() {
        return jobLevelValue;
    }
    
    /**
     * @param jobLevelValue 要设置的 jobLevelValue
     */
    public void setJobLevelValue(int jobLevelValue) {
        this.jobLevelValue = jobLevelValue;
    }
    
    /**
     * @return jobLevelText
     */
    public String getJobLevelText() {
        return jobLevelText;
    }
    
    /**
     * @param jobLevelText 要设置的 jobLevelText
     */
    public void setJobLevelText(String jobLevelText) {
        this.jobLevelText = jobLevelText;
    }
    
    /**
     * @return isDeletable
     */
    public int getIsDeletable() {
        return isDeletable;
    }
    
    /**
     * @param isDeletable 要设置的 isDeletable
     */
    public void setIsDeletable(int isDeletable) {
        this.isDeletable = isDeletable;
    }
    
    /**
     * @return birthPlace
     */
    public String getBirthPlace() {
        return birthPlace;
    }
    
    /**
     * @param birthPlace 要设置的 birthPlace
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
    
    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @param email 要设置的 email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return teamValue
     */
    public int getTeamValue() {
        return teamValue;
    }
    
    /**
     * @param teamValue 要设置的 teamValue
     */
    public void setTeamValue(int teamValue) {
        this.teamValue = teamValue;
    }
    
    /**
     * @return teamText
     */
    public String getTeamText() {
        return teamText;
    }
    
    /**
     * @param teamText 要设置的 teamText
     */
    public void setTeamText(String teamText) {
        this.teamText = teamText;
    }
    
    /**
     * @return erpId
     */
    public String getErpId() {
        return erpId;
    }
    
    /**
     * @param erpId 要设置的 erpId
     */
    public void setErpId(String erpId) {
        this.erpId = erpId;
    }
    
    /**
     * @return orgId
     */
    public int getOrgId() {
        return orgId;
    }
    
    /**
     * @param orgId 要设置的 orgId
     */
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
    
    /**
     * @return birthDay
     */
    public String getBirthDay() {
        return birthDay;
    }
    
    /**
     * @param birthDay 要设置的 birthDay
     */
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
    
    /**
     * @return flag
     */
    public String getFlag() {
        return flag;
    }
    
    /**
     * @param flag 要设置的 flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    /**
     * @return roleMemberID
     */
    public int getRoleMemberId() {
        return roleMemberId;
    }
    
    /**
     * @param roleMemberID 要设置的 roleMemberID
     */
    public void setRoleMemberId(int roleMemberId) {
        this.roleMemberId = roleMemberId;
    }
}
