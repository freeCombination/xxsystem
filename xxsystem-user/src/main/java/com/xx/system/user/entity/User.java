package com.xx.system.user.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.xx.system.dict.entity.Dictionary;
import com.xx.system.org.entity.OrgUser;
import com.xx.system.org.entity.Responsibilities;

/**
 * 用户 实体类
 * 
 * @version V1.20,2013-12-6 下午3:30:06
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Entity
@Table(name = "T_USER")
@BatchSize(size = 50)
public class User implements java.io.Serializable {
    
    /** @Fields serialVersionUID : serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    private Integer userId;
    
    /**
     * 身份证号
     */
    private String idCard;
    
    /** 组织 */
    private Set<OrgUser> orgUsers;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 姓名
     */
    private String realname;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 手机号码1
     */
    private String mobileNo1;
    
    /**
     * 手机号码2
     */
    private String mobileNo2;
    
    /**
     * 座机号码
     */
    private String phoneNo;
    
    /**
     * 集团短号1
     */
    private String shortNo1;
    
    /**
     * 集团短号2
     */
    private String shortNo2;
    
    /**
     * 出生地
     */
    private String birthPlace;
    
    /**
     * 电子邮件
     */
    private String email;
    
    /**
     * 状态
     */
    private int status;
    
    /**
     * 排序
     */
    private Integer disOrder;
    
    /**
     * 是否禁用
     */
    private int enable;
    
    /**
     * 上次登录时间
     */
    private long userOnline;
    
    /**
     * 类型：本地、中邮用户
     */
    private Dictionary type;
    
    /** @Fields userTypeUUID :用户类型字典数据UUID */
    private String userTypeUUID;
    
    /**
     * 班组
     */
    private Dictionary team;
    
    /** @Fields teamUUID : 班组UUID */
    private String teamUUID;
    
    /**
     * 卡号
     */
    private String cardCode;
    
    /**
     * ERPID
     */
    private String erpId;
    
    /**
     * 电子签名
     */
    private String eSignature;
    
    /**
     * 个人头像
     */
    private String personImage;
    
    /**
     * 出生日期
     */
    private String birthDay;
    
    /**
     * 职称
     */
    private Dictionary postTitle;
    
    /** @Fields postTitleUUID : 职称UUID */
    private String postTitleUUID;
    
    /**
     * 职位
     */
    private Dictionary post;
    
    /** @Fields postUUID : 职位UUID */
    private String postUUID;
    
    /**
     * 职务1
     */
    private Dictionary job1;
    
    /** @Fields job1UUID : 职务1UUID */
    private String job1UUID;
    
    /**
     * 职务2
     */
    private Dictionary job2;
    
    /** @Fields job2UUID : 职务2UUID */
    private String job2UUID;
    
    /**
     * 职级
     */
    private Dictionary jobLevel;
    
    /** @Fields jobLevelUUID : 职级UUID */
    private String jobLevelUUID;
    
    /**
     * 是否允许删除
     */
    private Integer isDeletAble;
    
    /**
     * 岗位
     */
    private Responsibilities responsibilities;
    
	/**
     * <p>
     * Title: 用户构造方法
     * </p>
     * <p>
     * Description: 构造方法
     * </p>
     */
    public User() {
    }
    
    /**
     * <p>
     * Title: 带参数的用户构造方法
     * </p>
     * <p>
     * Description: 带参数的构造方法
     * </p>
     * 
     * @param userId 用户ID
     */
    public User(Integer userId) {
        this.userId = userId;
    }
    
    /**
     * 
     * <p>
     * Title:User(int userId, String username, String password, String realname, int status, Integer
     * disOrder, Dictionary type, int enable, int isDeletAble)
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param userId
     * @param username
     * @param password
     * @param realname
     * @param status
     * @param disOrder
     * @param type
     * @param enable
     * @param isDeletAble
     */
    public User(int userId, String username, String password, String realname,
        int status, Integer disOrder, Dictionary type, int enable,
        int isDeletAble) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.status = status;
        this.disOrder = disOrder;
        this.type = type;
        this.enable = enable;
        this.isDeletAble = isDeletAble;
    }
    
    /**
     * 主键
     * 
     * @Title getUserId
     * @author wanglc
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "USER_ID")
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    /**
     * 用户名
     * 
     * @Title getUsername
     * @author wanglc
     * @date 2013-12-6
     * @return 用户名
     */
    @Column(name = "USERNAME", length = 50)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * 密码
     * 
     * @Title getPassword
     * @author wanglc
     * @date 2013-12-6
     * @return 密码
     */
    @Column(name = "PASSWORD", length = 50)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 真实姓名
     * 
     * @Title getRealname
     * @author wanglc
     * @date 2013-12-6
     * @return 真实姓名
     */
    @Column(name = "REALNAME", length = 50)
    public String getRealname() {
        return this.realname;
    }
    
    public void setRealname(String realname) {
        this.realname = realname;
    }
    
    /**
     * 性别
     * 
     * @Title getGender
     * @author wanglc
     * @date 2013-12-6
     * @return 性别
     */
    @Column(name = "GENDER", length = 3)
    public String getGender() {
        return this.gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * 出生日期
     * 
     * @Title getBirthDay
     * @author wanglc
     * @date 2013-12-6
     * @return 出生日期
     */
    @Column(name = "BIRTHDAY", length = 50)
    public String getBirthDay() {
        return this.birthDay;
    }
    
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
    
    /**
     * 手机号1
     * 
     * @Title getMobileNo1
     * @author yzg
     * @date 2014-2-13
     * @return 手机号1
     */
    @Column(name = "MOBILE_PHONE1", length = 20)
    public String getMobileNo1() {
        return mobileNo1;
    }
    
    public void setMobileNo1(String mobileNo1) {
        this.mobileNo1 = mobileNo1;
    }
    
    /**
     * 手机号2
     * 
     * @Title getMobileNo2
     * @author yzg
     * @date 2014-2-13
     * @return 手机号2
     */
    @Column(name = "MOBILE_PHONE2", length = 20)
    public String getMobileNo2() {
        return mobileNo2;
    }
    
    public void setMobileNo2(String mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }
    
    /**
     * 短号1
     * 
     * @Title getShortNo1
     * @author yzg
     * @date 2014-2-13
     * @return 短号1
     */
    @Column(name = "SHORT_NO1", length = 20)
    public String getShortNo1() {
        return shortNo1;
    }
    
    public void setShortNo1(String shortNo1) {
        this.shortNo1 = shortNo1;
    }
    
    /**
     * 短号2
     * 
     * @Title getShortNo2
     * @author yzg
     * @date 2014-2-13
     * @return 短号2
     */
    @Column(name = "SHORT_NO2", length = 20)
    public String getShortNo2() {
        return shortNo2;
    }
    
    public void setShortNo2(String shortNo2) {
        this.shortNo2 = shortNo2;
    }
    
    /**
     * 固话
     * 
     * @Title getPhoneNo
     * @author wanglc
     * @date 2013-12-6
     * @return 固话
     */
    @Column(name = "PHONE_NO", length = 20)
    public String getPhoneNo() {
        return phoneNo;
    }
    
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    /**
     * 状态：0可用 1不可用
     * 
     * @Title getStatus
     * @author wanglc
     * @date 2013-12-6
     * @return 状态：0可用 1不可用
     */
    @Column(name = "STATUS", precision = 22, scale = 0)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * 排序
     * 
     * @Title getDisOrder
     * @author wanglc
     * @date 2013-12-6
     * @return 排序
     */
    @Column(name = "DIS_ORDER")
    public Integer getDisOrder() {
        return this.disOrder;
    }
    
    public void setDisOrder(Integer disOrder) {
        this.disOrder = disOrder;
    }
    
    /**
     * 状态：0未禁用 1已禁用
     * 
     * @Title getEnable
     * @author wanglc
     * @date 2013-12-6
     * @return 状态：0未禁用 1已禁用
     */
    @Column(name = "ISENABLE")
    public int getEnable() {
        return this.enable;
    }
    
    public void setEnable(int enable) {
        this.enable = enable;
    }
    
    /**
     * 类型
     * 
     * @Title getType
     * @author wanglc
     * @date 2013-12-6
     * @return 类型
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_TYPE")
    public Dictionary getType() {
        return type;
    }
    
    public void setType(Dictionary type) {
        this.type = type;
    }
    
    /**
     * 在线
     * 
     * @Title getUserOnline
     * @author wanglc
     * @date 2013-12-6
     * @return
     */
    @Column(name = "USER_ONLINE")
    public long getUserOnline() {
        return userOnline;
    }
    
    public void setUserOnline(long userOnline) {
        this.userOnline = userOnline;
    }
    
    /**
     * 出生地
     * 
     * @Title getBirthPlace
     * @author wanglc
     * @date 2013-12-6
     * @return 出生地
     */
    @Column(name = "BIRTH_PLACE")
    public String getBirthPlace() {
        return birthPlace;
    }
    
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
    
    /**
     * 邮箱
     * 
     * @Title getEmail
     * @author wanglc
     * @date 2013-12-6
     * @return 邮箱
     */
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * 班组
     * 
     * @Title getTeam
     * @author wanglc
     * @date 2013-12-6
     * @return 班组
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_TEAM")
    public Dictionary getTeam() {
        return team;
    }
    
    public void setTeam(Dictionary team) {
        this.team = team;
    }
    
    /**
     * 卡号
     * 
     * @Title getCardCode
     * @author wanglc
     * @date 2013-12-6
     * @return 卡号
     */
    @Column(name = "CARD_CODE")
    public String getCardCode() {
        return cardCode;
    }
    
    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }
    
    /**
     * ERP编号
     * 
     * @Title getErpId
     * @author wanglc
     * @date 2013-12-6
     * @return ERP编号
     */
    @Column(name = "ERP_ID")
    public String getErpId() {
        return erpId;
    }
    
    public void setErpId(String erpId) {
        this.erpId = erpId;
    }
    
    /**
     * 电子签名
     * 
     * @Title geteSignature
     * @author wanglc
     * @date 2013-12-6
     * @return 电子签名
     */
    @Column(name = "ELECTRONIC_SIGNATURE")
    public String geteSignature() {
        return eSignature;
    }
    
    public void seteSignature(String eSignature) {
        this.eSignature = eSignature;
    }
    
    /**
     * 个人头像
     * 
     * @Title getPersonImage
     * @author wanglc
     * @date 2013-12-6
     * @return 个人头像
     */
    @Column(name = "PERSONAL_IMAGE ")
    public String getPersonImage() {
        return personImage;
    }
    
    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }
    
    /**
     * 身份证号
     * 
     * @Title getIdCard
     * @author wanglc
     * @date 2013-12-6
     * @return 身份证号
     */
    @Column(name = "ID_CARD")
    public String getIdCard() {
        return idCard;
    }
    
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    /**
     * 是否允许删除
     * 
     * @Title getIsDeletAble
     * @author wanglc
     * @date 2013-12-6
     * @return 是否允许删除
     */
    @Column(name = "ISDELETABLE")
    public Integer getIsDeletAble() {
        return isDeletAble;
    }
    
    public void setIsDeletAble(Integer isDeletAble) {
        this.isDeletAble = isDeletAble;
    }
    
    /**
     * 职称
     * 
     * @Title getPostTitle
     * @author wanglc
     * @date 2013-12-6
     * @return 职称
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_POSTTITLE")
    public Dictionary getPostTitle() {
        return postTitle;
    }
    
    public void setPostTitle(Dictionary postTitle) {
        this.postTitle = postTitle;
    }
    
    /**
     * 职务
     * 
     * @Title getPost
     * @author wanglc
     * @date 2013-12-6
     * @return 职务
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_POST")
    public Dictionary getPost() {
        return post;
    }
    
    public void setPost(Dictionary post) {
        this.post = post;
    }
    
    /**
     * 职位1
     * 
     * @Title getJob1
     * @author yzg
     * @date 2014-2-13
     * @return 职位1
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_JOB1")
    public Dictionary getJob1() {
        return job1;
    }
    
    public void setJob1(Dictionary job1) {
        this.job1 = job1;
    }
    
    /**
     * 职位2
     * 
     * @Title getJob2
     * @author yzg
     * @date 2014-2-13
     * @return 职位2
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_JOB2")
    public Dictionary getJob2() {
        return job2;
    }
    
    public void setJob2(Dictionary job2) {
        this.job2 = job2;
    }
    
    /**
     * 职级
     * 
     * @Title getJobLevel
     * @author wanglc
     * @date 2013-12-6
     * @return 职级
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_JOBLEVEL")
    public Dictionary getJobLevel() {
        return jobLevel;
    }
    
    public void setJobLevel(Dictionary jobLevel) {
        this.jobLevel = jobLevel;
    }
    
    /**
     * 用户组织
     * 
     * @Title getOrgUsers
     * @author wanglc
     * @date 2013-12-6
     * @return 用户组织
     */
    @OneToMany(mappedBy = "user")
    @BatchSize(size = 50)
    public Set<OrgUser> getOrgUsers() {
        return orgUsers;
    }
    
    public void setOrgUsers(Set<OrgUser> orgUsers) {
        this.orgUsers = orgUsers;
    }
    
    /**
     * @return userTypeUUID
     */
    @Column(name = "FK_USERTYPE_UUID")
    public String getUserTypeUUID() {
        return userTypeUUID;
    }
    
    /**
     * @param userTypeUUID 要设置的 userTypeUUID
     */
    public void setUserTypeUUID(String userTypeUUID) {
        this.userTypeUUID = userTypeUUID;
    }
    
    /**
     * @return teamUUID
     */
    @Column(name = "FK_TEAM_UUID")
    public String getTeamUUID() {
        return teamUUID;
    }
    
    /**
     * @param teamUUID 要设置的 teamUUID
     */
    public void setTeamUUID(String teamUUID) {
        this.teamUUID = teamUUID;
    }
    
    /**
     * @return postTitleUUID
     */
    @Column(name = "FK_POSTTITLE_UUID")
    public String getPostTitleUUID() {
        return postTitleUUID;
    }
    
    /**
     * @param postTitleUUID 要设置的 postTitleUUID
     */
    public void setPostTitleUUID(String postTitleUUID) {
        this.postTitleUUID = postTitleUUID;
    }
    
    /**
     * @return postUUID
     */
    @Column(name = "FK_POST_UUID")
    public String getPostUUID() {
        return postUUID;
    }
    
    /**
     * @param postUUID 要设置的 postUUID
     */
    public void setPostUUID(String postUUID) {
        this.postUUID = postUUID;
    }
    
    /**
     * @return job1UUID
     */
    @Column(name = "FK_JOB1_UUID")
    public String getJob1UUID() {
        return job1UUID;
    }
    
    /**
     * @param job1uuid 要设置的 job1UUID
     */
    public void setJob1UUID(String job1uuid) {
        job1UUID = job1uuid;
    }
    
    /**
     * @return job2UUID
     */
    @Column(name = "FK_JOB2_UUID")
    public String getJob2UUID() {
        return job2UUID;
    }
    
    /**
     * @param job2uuid 要设置的 job2UUID
     */
    public void setJob2UUID(String job2uuid) {
        job2UUID = job2uuid;
    }
    
    /**
     * @return jobLevelUUID
     */
    @Column(name = "FK_JOBLEVEL_UUID")
    public String getJobLevelUUID() {
        return jobLevelUUID;
    }
    
    /**
     * @param jobLevelUUID 要设置的 jobLevelUUID
     */
    public void setJobLevelUUID(String jobLevelUUID) {
        this.jobLevelUUID = jobLevelUUID;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_RESP_ID")
    public Responsibilities getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(Responsibilities responsibilities) {
		this.responsibilities = responsibilities;
	}
}