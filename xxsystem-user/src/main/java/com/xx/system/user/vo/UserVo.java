package com.xx.system.user.vo;

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
     * @Fields mobileNo1 : 手机
     */
    private String mobileNo;
    
    /**
     * @Fields phoneNo : 固话
     */
    private String phoneNo;
    
    /**
     * @Fields idCard : 身份证号
     */
    private String idCard;
    
    /**
     * @Fields shortNo1 : 短号
     */
    private String shortNo;
    
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
     * @Fields birthPlace :出生地
     */
    private String birthPlace;
    
    /**
     * @Fields email : 邮箱
     */
    private String email;
    
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
     * 岗位
     */
    private String respName;
    
    /**
     * 岗位ID
     */
    private Integer respId;
    
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
     * @return mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }
    
    /**
     * @param mobileNo 要设置的 mobileNo
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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
     * @return shortNo
     */
    public String getShortNo() {
        return shortNo;
    }
    
    /**
     * @param shortNo1 要设置的 shortNo1
     */
    public void setShortNo(String shortNo) {
        this.shortNo = shortNo;
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

	public String getRespName() {
		return respName;
	}

	public void setRespName(String respName) {
		this.respName = respName;
	}

	public Integer getRespId() {
		return respId;
	}

	public void setRespId(Integer respId) {
		this.respId = respId;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPartyDate() {
		return partyDate;
	}

	public void setPartyDate(String partyDate) {
		this.partyDate = partyDate;
	}

	public String getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(String jobStartDate) {
		this.jobStartDate = jobStartDate;
	}

	public String getOfficeHoldingDate() {
		return officeHoldingDate;
	}

	public void setOfficeHoldingDate(String officeHoldingDate) {
		this.officeHoldingDate = officeHoldingDate;
	}

	public String getEducationBackground() {
		return educationBackground;
	}

	public void setEducationBackground(String educationBackground) {
		this.educationBackground = educationBackground;
	}

	public String getTechnicaTitles() {
		return technicaTitles;
	}

	public void setTechnicaTitles(String technicaTitles) {
		this.technicaTitles = technicaTitles;
	}

	public String getComeDate() {
		return comeDate;
	}

	public void setComeDate(String comeDate) {
		this.comeDate = comeDate;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public String getEmploymentInfo() {
		return employmentInfo;
	}

	public void setEmploymentInfo(String employmentInfo) {
		this.employmentInfo = employmentInfo;
	}

	public String getPostWage() {
		return postWage;
	}

	public void setPostWage(String postWage) {
		this.postWage = postWage;
	}

	public String getTrainInfo() {
		return trainInfo;
	}

	public void setTrainInfo(String trainInfo) {
		this.trainInfo = trainInfo;
	}

	public String getPoliticsStatus() {
		return politicsStatus;
	}

	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}
}
