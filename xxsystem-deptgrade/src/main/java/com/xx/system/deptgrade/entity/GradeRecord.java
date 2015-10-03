package com.xx.system.deptgrade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.xx.system.org.entity.Organization;
import com.xx.system.user.entity.User;

/**
 * 部门评分记录实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_GRADE_RECORD")
@BatchSize(size = 50)
public class GradeRecord implements java.io.Serializable {

	private static final long serialVersionUID = -1445820631179733789L;

	/**
     * @Fields pkGradeRecId : 主键
     */
    private int pkGradeRecId;
    
    /**
     * 考核指标分类
     */
    private IndexClassify classify;
    
    /**
     * 一级指标
     */
    private GradeIndex index1;
    
    /**
     * 二级指标
     */
    private GradeIndex index2;
    
    /**
     * 参评部门
     */
    private Organization org;
    
    /**
     * 评分用户
     */
    private User user;
    
    /**
     * @Fields score : 得分
     */
    private String score;
    
    /**
     * @Fields isDelete : 删除标志：0未删除 1已删除
     */
    private int isDelete;
    
    /**
     * @Title getPkCfUserId
     * @Description: 主键
     * @date 2013-12-6
     * @return 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_GRADEREC_ID", nullable = false)
    public int getPkGradeRecId() {
		return pkGradeRecId;
	}

	public void setPkGradeRecId(int pkGradeRecId) {
		this.pkGradeRecId = pkGradeRecId;
	}
    
    @Column(name = "ISDELETE", nullable = false, precision = 22, scale = 0)
    public int getIsDelete() {
        return isDelete;
    }
    
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

	@Column(name = "SCORE", nullable = false, precision = 22, scale = 0)
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CLASSIFY_ID", nullable = true)
	public IndexClassify getClassify() {
		return classify;
	}

	public void setClassify(IndexClassify classify) {
		this.classify = classify;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_USER_ID", nullable = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_INDEX1_ID", nullable = false)
	public GradeIndex getIndex1() {
		return index1;
	}

	public void setIndex1(GradeIndex index1) {
		this.index1 = index1;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_INDEX2_ID", nullable = true)
	public GradeIndex getIndex2() {
		return index2;
	}

	public void setIndex2(GradeIndex index2) {
		this.index2 = index2;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ORG_ID", nullable = false)
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
	
}