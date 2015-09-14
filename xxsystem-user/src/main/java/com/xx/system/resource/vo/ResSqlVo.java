package com.xx.system.resource.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ResSqlVo {
    /**
     * @Fields resourceId : 资源ID
     */
    private BigDecimal resourceId;
    
    /**
     * @Fields resourceName : 资源名称
     */
    private String resourceName;
    
    /**
     * @Fields code : 资源编码
     */
    private String code;
    
    /**
     * @Fields resourceTypeName : 资源类型
     */
    private String resourceTypeName;
    
    /**
     * @Fields resourceTypeName : 资源类型
     */
    private String resourceTypeValue;
    
    /**
     * @Fields resourceTypeName : 资源类型
     */
    private int resourceTypeId;
    
    /**
     * @Fields urlpath : 资源URL
     */
    private String urlpath;
    
    /**
     * @Fields remarks : 资源备注
     */
    private String remarks;
    
    /**
     * @Fields disOrder : 资源排序
     */
    private String disOrder;
    
    /**
     * @Fields status : 资源状态：0未删除 1已删除
     */
    private int status;
    
    /**
     * @Fields createDate : 创建日期
     */
    private Date createDate;
    
    /**
     * @Fields parentResourceId : 上级资源ID
     */
    private int parentResourceId;
    
    /**
     * @Fields parentResourceName : 上级资源名称
     */
    private String parentResourceName;
    
    private BigDecimal ROWNUM_;
    
    /**
     * @return rOWNUM_
     */
    public BigDecimal getROWNUM_() {
        return ROWNUM_;
    }
    
    /**
     * @param rOWNUM_ 要设置的 rOWNUM_
     */
    public void setROWNUM_(BigDecimal rOWNUM_) {
        ROWNUM_ = rOWNUM_;
    }
    
    /**
     * @return resourceId
     */
    public BigDecimal getResourceId() {
        return resourceId;
    }
    
    /**
     * @param resourceId 要设置的 resourceId
     */
    public void setResourceId(BigDecimal resourceId) {
        this.resourceId = resourceId;
    }
    
    /**
     * @return resourceName
     */
    public String getResourceName() {
        return resourceName;
    }
    
    /**
     * @param resourceName 要设置的 resourceName
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    
    /**
     * @return code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param code 要设置的 code
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return resourceTypeName
     */
    public String getResourceTypeName() {
        return resourceTypeName;
    }
    
    /**
     * @param resourceTypeName 要设置的 resourceTypeName
     */
    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }
    
    /**
     * @return resourceTypeValue
     */
    public String getResourceTypeValue() {
        return resourceTypeValue;
    }
    
    /**
     * @param resourceTypeValue 要设置的 resourceTypeValue
     */
    public void setResourceTypeValue(String resourceTypeValue) {
        this.resourceTypeValue = resourceTypeValue;
    }
    
    /**
     * @return resourceTypeId
     */
    public int getResourceTypeId() {
        return resourceTypeId;
    }
    
    /**
     * @param resourceTypeId 要设置的 resourceTypeId
     */
    public void setResourceTypeId(int resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }
    
    /**
     * @return urlpath
     */
    public String getUrlpath() {
        return urlpath;
    }
    
    /**
     * @param urlpath 要设置的 urlpath
     */
    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath;
    }
    
    /**
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }
    
    /**
     * @param remarks 要设置的 remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    /**
     * @return disOrder
     */
    public String getDisOrder() {
        return disOrder;
    }
    
    /**
     * @param disOrder 要设置的 disOrder
     */
    public void setDisOrder(String disOrder) {
        this.disOrder = disOrder;
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
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * @param createDate 要设置的 createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * @return parentResourceId
     */
    public int getParentResourceId() {
        return parentResourceId;
    }
    
    /**
     * @param parentResourceId 要设置的 parentResourceId
     */
    public void setParentResourceId(int parentResourceId) {
        this.parentResourceId = parentResourceId;
    }
    
    /**
     * @return parentResourceName
     */
    public String getParentResourceName() {
        return parentResourceName;
    }
    
    /**
     * @param parentResourceName 要设置的 parentResourceName
     */
    public void setParentResourceName(String parentResourceName) {
        this.parentResourceName = parentResourceName;
    }
    
}
