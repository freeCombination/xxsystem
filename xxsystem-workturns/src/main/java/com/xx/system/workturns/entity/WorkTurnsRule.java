/**
 * 文件名： WorkTurnsRule.java
 * 版权：Copyright 2009-2013 版权所有：大庆金桥信息技术工程有限公司成都分公司
 * 描述：倒班规则实体类
 * 修改人： tangh
 * 修改时间： 2014年9月10日
 * 修改内容：新增
 */
package com.dqgb.sshframe.workturns.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dqgb.sshframe.org.entity.Organization;
import com.dqgb.sshframe.user.entity.User;

/**
 * WorkTurnsRule（倒班规则）实体定义
 * 
 * @author tangh
 * @version V1.40,2014年9月10日 下午4:40:11
 * @see [相关类/方法]
 * @since V1.4
 */
@Entity
@Table(name = "T_WORK_TURNS_RULE")
public class WorkTurnsRule implements java.io.Serializable
{
    
    /**
     * @Fields serialVersionUID : 对象序列化ID
     */
    private static final long serialVersionUID = -5084314068001115417L;
    
    /**
     * @Fields id : 倒班规则ID
     */
    private Integer id;
    
    /**
     * @Fields name : 倒班规则名称
     */
    private String name;
    
    /**
     * @Fields user : 制定人
     */
    private User user;
    
    /**
     * @Fields makeDate : 制定时间
     */
    private Date makeDate;
    
    /**
     * @Fields cycleDays : 倒班周期天数，由倒班规则制定人动态设定。
     */
    private Integer cycleDays;
    
    /**
     * @Fields remarks : 对象序列化ID
     */
    private String remarks;
    
    /**
     * @Fields org : 对应部门
     */
    private Organization org;
    
    public WorkTurnsRule()
    {
        
    }
    
    public WorkTurnsRule(Integer id)
    {
        this.id = id;
    }
    
    public WorkTurnsRule(String name, User user, Date makeDate,
        Integer cycleDays)
    {
        this.name = name;
        this.user = user;
        this.makeDate = makeDate;
        this.cycleDays = cycleDays;
    }
    
    /**
     * @Title getId
     * @author dong.he
     * @Description: 主键
     * @date 2014-8-22
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public Integer getId()
    {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    /**
     * @Title getName
     * @author dong.he
     * @Description: 倒班规则名称
     * @date 2014-8-22
     * @return 倒班规则名称
     */
    @Column(name = "RULE_NAME")
    public String getName()
    {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @Title getUser
     * @author dong.he
     * @Description: 创建人
     * @date 2014-8-22
     * @return 创建人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAKE_USER_ID")
    public User getUser()
    {
        return user;
    }
    
    /**
     * @param user the user to set
     */
    public void setUser(User user)
    {
        this.user = user;
    }
    
    /**
     * @Title getMakeDate
     * @author dong.he
     * @Description: 创建时间
     * @date 2014-8-22
     * @return 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAKE_TIME", nullable = false, length = 7)
    public Date getMakeDate()
    {
        return makeDate;
    }
    
    /**
     * @param makeDate the makeDate to set
     */
    public void setMakeDate(Date makeDate)
    {
        this.makeDate = makeDate;
    }
    
    /**
     * @Title getCycleDays
     * @author dong.he
     * @Description: 循环天数
     * @date 2014-8-22
     * @return 循环天数
     */
    @Column(name = "CYCLE_DAYS", nullable = true)
    public Integer getCycleDays()
    {
        return cycleDays;
    }
    
    /**
     * @param cycleDays the cycleDays to set
     */
    public void setCycleDays(Integer cycleDays)
    {
        this.cycleDays = cycleDays;
    }
    
    /**
     * @Title getRemarks
     * @author dong.he
     * @Description: 备注
     * @date 2014-8-22
     * @return 备注
     */
    @Column(name = "REMARKS")
    public String getRemarks()
    {
        return remarks;
    }
    
    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    /**
     * @Title getOrg
     * @author dong.he
     * @Description: 所属部门
     * @date 2014-8-22
     * @return 所属部门
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID")
    public Organization getOrg()
    {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(Organization org)
    {
        this.org = org;
    }
    
}