package com.xx.system.user.vo;

/**
 * 字典Vo 字典Vo详细定义
 * 
 * @version V1.20,2013-11-25 下午2:06:40
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class DictionaryVo {
    
    /**
     * @Fields dictionaryId : 字典ID
     */
    private int dictionaryId;
    
    /**
     * @Fields dictionaryName : 字典名称
     */
    private String dictionaryName;
    
    /**
     * @Fields dictionaryType : 字典类型
     */
    private int dictionaryType;
    
    public int getDictionaryId() {
        return dictionaryId;
    }
    
    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }
    
    public String getDictionaryName() {
        return dictionaryName;
    }
    
    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }
    
    public int getDictionaryType() {
        return dictionaryType;
    }
    
    public void setDictionaryType(int dictionaryType) {
        this.dictionaryType = dictionaryType;
    }
    
}
