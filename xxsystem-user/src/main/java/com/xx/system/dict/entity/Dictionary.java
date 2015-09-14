package com.xx.system.dict.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 字典实体类
 * 
 * @version V1.40,2014年9月10日 下午3:19:17
 * @see [相关类/方法]
 * @since V1.40
 */
@Entity
@Table(name = "T_DICTIONARY")
@SuppressWarnings("serial")
public class Dictionary implements java.io.Serializable {
    
    /** 主键 */
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "PK_ID", nullable = false)
    private Integer pkDictionaryId;
    
    /** 字典类型 */
    /*@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DICTTYPE_ID", insertable = true, updatable = false)
    private DictionaryType dictionaryType;*/
    
    public Dictionary(Integer pkDictionaryId) {
		super();
		this.pkDictionaryId = pkDictionaryId;
	}

    public Dictionary() {
		super();
	}
    
	/** 字典名称 */
    @Column(name = "DICTIONARY_NAME", length = 48)
    private String dictionaryName;
    
    /** 等级或排序 */
    @Column(name = "LEVEL_ORDER")
    private Integer levelOrder;
    
    /** 字典值 */
    @Column(name = "DICTIONARY_VALUE", length = 480)
    private String dictionaryValue;
    
    /** 删除标志：0未删除 1已删除 */
    @Column(name = "STATUS", length = 1)
    private String status;
    
    /** 字典CODE */
    @Column(name = "DICT_CODE")
    private String dictCode;
    
    /** 字典数据类型 */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID", insertable = true, updatable = true)
    private Dictionary dictionary;
    
    /** 字典类型数据随机数 */
    @Column(name = "FK_DICT_TYPE_UUID")
    private String fkDictTypeUUID;
    
    /** @Fields dictUUID : 字典数据UUID */
    @Column(name = "DICT_UUID")
    private String dictUUID;
    
    /**
     * @return the pkDictionaryId
     */
    public Integer getPkDictionaryId() {
        return pkDictionaryId;
    }
    
    /**
     * @param pkDictionaryId the pkDictionaryId to set
     */
    public void setPkDictionaryId(Integer pkDictionaryId) {
        this.pkDictionaryId = pkDictionaryId;
    }
    
    /**
     * @return the dictionaryType
     */
    /*public DictionaryType getDictionaryType() {
        return dictionaryType;
    }*/
    
    /**
     * @param dictionaryType the dictionaryType to set
     */
    /*public void setDictionaryType(DictionaryType dictionaryType) {
        this.dictionaryType = dictionaryType;
    }*/
    
    /**
     * @return the dictionaryName
     */
    public String getDictionaryName() {
        return dictionaryName;
    }
    
    /**
     * @param dictionaryName the dictionaryName to set
     */
    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }
    
    /**
     * @return the levelOrder
     */
    public Integer getLevelOrder() {
        return levelOrder;
    }
    
    /**
     * @param levelOrder the levelOrder to set
     */
    public void setLevelOrder(Integer levelOrder) {
        this.levelOrder = levelOrder;
    }
    
    /**
     * @return the dictionaryValue
     */
    public String getDictionaryValue() {
        return dictionaryValue;
    }
    
    /**
     * @param dictionaryValue the dictionaryValue to set
     */
    public void setDictionaryValue(String dictionaryValue) {
        this.dictionaryValue = dictionaryValue;
    }
    
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * @return the dictCode
     */
    public String getDictCode() {
        return dictCode;
    }
    
    /**
     * @param dictCode the dictCode to set
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }
    
    /**
     * @return the dictionary
     */
    public Dictionary getDictionary() {
        return dictionary;
    }
    
    /**
     * @param dictionary the dictionary to set
     */
    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
    
    /**
     * @return the fkDictTypeUUID
     */
    public String getFkDictTypeUUID() {
        return fkDictTypeUUID;
    }
    
    /**
     * @param fkDictTypeUUID the fkDictTypeUUID to set
     */
    public void setFkDictTypeUUID(String fkDictTypeUUID) {
        this.fkDictTypeUUID = fkDictTypeUUID;
    }
    
    /**
     * @return the dictUUID
     */
    public String getDictUUID() {
        return dictUUID;
    }
    
    /**
     * @param dictUUID the dictUUID to set
     */
    public void setDictUUID(String dictUUID) {
        this.dictUUID = dictUUID;
    }
    
}