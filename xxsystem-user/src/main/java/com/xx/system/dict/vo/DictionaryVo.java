package com.xx.system.dict.vo;

/**
 * 字典包装类
 * 
 * @version 版本号,2014-8-22
 * @see [相关类/方法]
 * @since 1.0
 */
public class DictionaryVo {
    /**
     * 主键ID
     */
    private Integer pkDictionaryId;
    
    /**
     * 字典名称
     */
    private String dictionaryName;
    
    /**
     * 等级排序
     */
    private Integer levelOrder;
    
    /**
     * 字典类别ID
     */
    private Integer dictionaryTypeId;
    
    /**
     * 字典类型名称
     */
    private String dictionaryTypeName;
    
    /**
     * 字典值
     */
    private String dictionaryValue;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 字典CODE
     */
    private String dictionaryCode;
    
    /**
     * 标志
     */
    private String flag;
    
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
     * @return the dictionaryTypeId
     */
    public Integer getDictionaryTypeId() {
        return dictionaryTypeId;
    }
    
    /**
     * @param dictionaryTypeId the dictionaryTypeId to set
     */
    public void setDictionaryTypeId(Integer dictionaryTypeId) {
        this.dictionaryTypeId = dictionaryTypeId;
    }
    
    /**
     * @return the dictionaryTypeName
     */
    public String getDictionaryTypeName() {
        return dictionaryTypeName;
    }
    
    /**
     * @param dictionaryTypeName the dictionaryTypeName to set
     */
    public void setDictionaryTypeName(String dictionaryTypeName) {
        this.dictionaryTypeName = dictionaryTypeName;
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
     * @return the dictionaryCode
     */
    public String getDictionaryCode() {
        return dictionaryCode;
    }
    
    /**
     * @param dictionaryCode the dictionaryCode to set
     */
    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }
    
    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }
    
    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
}
