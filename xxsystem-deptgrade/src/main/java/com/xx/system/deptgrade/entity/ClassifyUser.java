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

import com.xx.system.user.entity.User;

/**
 * 考核指标分类与用户对应实体定义
 * 
 * @version V1.20,2013-11-25 下午3:37:13
 * @see [相关类/方法]
 * @since V1.20
 */
@Entity
@Table(name = "T_CLASSIFY_USER")
@BatchSize(size = 50)
public class ClassifyUser implements java.io.Serializable {

	private static final long serialVersionUID = -1445820631179733789L;

	/**
     * @Fields pkCfUserId : 主键
     */
    private int pkCfUserId;
    
    /**
     * 考核指标分类
     */
    private IndexClassify classify;
    
    /**
     * 用户
     */
    private User user;
    
    /**
     * @Fields hasSubmit : 是否已评分：0 否 1 是
     */
    private Integer hasSubmit;
    
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
    @Column(name = "PK_CFUSER_ID", nullable = false)
    public int getPkCfUserId() {
		return pkCfUserId;
	}

	public void setPkCfUserId(int pkCfUserId) {
		this.pkCfUserId = pkCfUserId;
	}
    
    @Column(name = "ISDELETE", nullable = false, precision = 22, scale = 0)
    public int getIsDelete() {
        return isDelete;
    }
    
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

	@Column(name = "HAS_SUBMIT", nullable = false, precision = 22, scale = 0)
	public Integer getHasSubmit() {
		return hasSubmit;
	}

	public void setHasSubmit(Integer hasSubmit) {
		this.hasSubmit = hasSubmit;
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
	
}