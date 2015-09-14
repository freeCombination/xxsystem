package com.xx.system.dict.entity;

/**
 * 接收数据是否存在表中的数据
 * 
 * @version V1.20,2014年3月25日 上午9:25:03
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class IsExistVo {
    
    /**
     * @Fields tableName : 表名
     */
    String TABLE_NAME;
    
    /**
     * @Fields tableField : 字段名
     */
    String TABLE_FIELD;
    
    /**
     * @return tABLE_NAME
     */
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }
    
    /**
     * @param tABLE_NAME 要设置的 tABLE_NAME
     */
    public void setTABLE_NAME(String tABLE_NAME) {
        TABLE_NAME = tABLE_NAME;
    }
    
    /**
     * @return tABLE_FIELD
     */
    public String getTABLE_FIELD() {
        return TABLE_FIELD;
    }
    
    /**
     * @param tABLE_FIELD 要设置的 tABLE_FIELD
     */
    public void setTABLE_FIELD(String tABLE_FIELD) {
        TABLE_FIELD = tABLE_FIELD;
    }
    
}
