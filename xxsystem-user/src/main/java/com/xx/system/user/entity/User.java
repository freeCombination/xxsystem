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
     * 手机号码
     */
    private String mobileNo;
    
    /**
     * 座机号码
     */
    private String phoneNo;
    
    /**
     * 集团短号
     */
    private String shortNo;
    
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
     * 岗位
     */
    private Responsibilities responsibilities;
    
    /**
     * 现任岗位时间
     */
    private String respChangeDate;
    
    /**
     * 民族
     */
    private String nationality;
    
    /**
     * 入党团时间
     */
    private String partyDate;
    
    /**
     * 参加工作时间
     */
    private String jobStartDate;
    
    /**
     * 党政职务及任职时间
     */
    private String officeHoldingDate;
    
    /**
     * 学历（专业、毕业时间、学校）
     */
    private String educationBackground;
    
    /**
     * 现技术职称及确定时间
     */
    private String technicaTitles;
    
    /**
     * 进所时间
     */
    private String comeDate;
    
    /**
     * 技能
     */
    private String skill;
    
    /**
     * 业绩
     */
    private String performance;
    
    /**
     * 用工信息
     */
    private String employmentInfo;
    
    /**
     * 岗位工资
     */
    private String postWage;
    
    /**
     * 培训情况
     */
    private String trainInfo;
    
    /**
     * 政治面貌
     */
    private String politicsStatus;
    
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
     * disOrder, Dictionary type, int enable)
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
     */
    public User(int userId, String username, String password, String realname,
        int status, Integer disOrder, Dictionary type, int enable) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.status = status;
        this.disOrder = disOrder;
        this.enable = enable;
    }
    
    /**
     * 主键
     * 
     * @Title getUserId
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
     * 手机号
     * 
     * @Title getMobileNo
     * @date 2014-2-13
     * @return 手机号
     */
    @Column(name = "MOBILE_PHONE", length = 20)
    public String getMobileNo() {
        return mobileNo;
    }
    
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    
    /**
     * 短号
     * 
     * @Title getShortNo
     * @date 2014-2-13
     * @return 短号
     */
    @Column(name = "SHORT_NO", length = 20)
    public String getShortNo() {
        return shortNo;
    }
    
    public void setShortNo(String shortNo) {
        this.shortNo = shortNo;
    }
    
    /**
     * 固话
     * 
     * @Title getPhoneNo
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
     * 在线
     * 
     * @Title getUserOnline
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
     * 卡号
     * 
     * @Title getCardCode
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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_RESP_ID")
    public Responsibilities getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(Responsibilities responsibilities) {
		this.responsibilities = responsibilities;
	}
	
	@Column(name = "NATIONALITY")
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "PARTY_DATE")
	public String getPartyDate() {
		return partyDate;
	}

	public void setPartyDate(String partyDate) {
		this.partyDate = partyDate;
	}

	@Column(name = "JOB_START_DATE")
	public String getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(String jobStartDate) {
		this.jobStartDate = jobStartDate;
	}

	@Column(name = "OFFICE_HOLDING_DATE")
	public String getOfficeHoldingDate() {
		return officeHoldingDate;
	}

	public void setOfficeHoldingDate(String officeHoldingDate) {
		this.officeHoldingDate = officeHoldingDate;
	}

	@Column(name = "EDUCATION_BACKGROUND")
	public String getEducationBackground() {
		return educationBackground;
	}

	public void setEducationBackground(String educationBackground) {
		this.educationBackground = educationBackground;
	}

	@Column(name = "TECHNICA_TITLES")
	public String getTechnicaTitles() {
		return technicaTitles;
	}

	public void setTechnicaTitles(String technicaTitles) {
		this.technicaTitles = technicaTitles;
	}

	@Column(name = "COME_DATE")
	public String getComeDate() {
		return comeDate;
	}

	public void setComeDate(String comeDate) {
		this.comeDate = comeDate;
	}

	@Column(name = "SKILL")
	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@Column(name = "PERFORMANCE")
	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	@Column(name = "EMPLOYMENT_INFO")
	public String getEmploymentInfo() {
		return employmentInfo;
	}

	public void setEmploymentInfo(String employmentInfo) {
		this.employmentInfo = employmentInfo;
	}

	@Column(name = "POST_WAGE")
	public String getPostWage() {
		return postWage;
	}

	public void setPostWage(String postWage) {
		this.postWage = postWage;
	}

	@Column(name = "TRAIN_INFO")
	public String getTrainInfo() {
		return trainInfo;
	}

	public void setTrainInfo(String trainInfo) {
		this.trainInfo = trainInfo;
	}

	@Column(name = "POLITICS_STATUS")
	public String getPoliticsStatus() {
		return politicsStatus;
	}

	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}

	@Column(name = "RESP_CHANGE_DATE", length = 50)
	public String getRespChangeDate() {
		return respChangeDate;
	}

	public void setRespChangeDate(String respChangeDate) {
		this.respChangeDate = respChangeDate;
	}
}