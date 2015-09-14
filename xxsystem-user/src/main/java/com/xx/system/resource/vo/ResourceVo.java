package com.xx.system.resource.vo;

import java.util.Date;

import com.xx.system.resource.entity.Resource;

/**
 * Resource类的Vo类
 * 
 * @version V1.20,2013-11-25 下午3:09:16
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class ResourceVo {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields resourceId : 资源ID
     */
    private int resourceId;
    
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
    private Integer disOrder;
    
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
    
    /**
     * <p>
     * Title: ResourceVo()
     * </p>
     * <p>
     * Description: 默认构造方法
     * </p>
     */
    public ResourceVo() {
        
    }
    
    /**
     * <p>
     * Title: ResourceVo(Resource r)
     * </p>
     * <p>
     * Description: 带参数的构造方法
     * </p>
     * 
     * @param r
     */
    public ResourceVo(Resource r) {
        int parentResourceId = 0;
        String parentResourceName = "";
        if (r.getResource() != null) {
            parentResourceId = r.getResource().getResourceId();
            parentResourceName = r.getResource().getResourceName();
        }
        this.resourceTypeId = r.getResourceType().getPkDictionaryId();
        this.parentResourceId = parentResourceId;
        this.parentResourceName = parentResourceName;
        this.resourceId = r.getResourceId();
        this.code = r.getCode();
        this.createDate = r.getCreateDate();
        this.disOrder = r.getDisOrder();
        this.remarks = r.getRemarks();
        this.resourceName = r.getResourceName();
        this.resourceTypeName = r.getResourceType().getDictionaryName();
        this.resourceTypeValue = r.getResourceType().getDictionaryValue();
        this.status = r.getStatus();
        this.urlpath = r.getUrlpath();
    }
    
    public int getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
    
    public String getResourceName() {
        return resourceName;
    }
    
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    
    public String getCode() {
        return code;
    }
    
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
    
    public String getUrlpath() {
        return urlpath;
    }
    
    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Integer getDisOrder() {
        return disOrder;
    }
    
    public void setDisOrder(Integer disOrder) {
        this.disOrder = disOrder;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public int getParentResourceId() {
        return parentResourceId;
    }
    
    public void setParentResourceId(int parentResourceId) {
        this.parentResourceId = parentResourceId;
    }
    
    public String getParentResourceName() {
        return parentResourceName;
    }
    
    public void setParentResourceName(String parentResourceName) {
        this.parentResourceName = parentResourceName;
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
    
}
