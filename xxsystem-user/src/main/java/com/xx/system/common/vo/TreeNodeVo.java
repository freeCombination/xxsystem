package com.xx.system.common.vo;

/**
 * 封装树节点
 * 
 * @version V1.20,2013-12-6 下午3:07:26
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class TreeNodeVo {
    
    /** 结点id */
    private String nodeId;
    
    private String id;
    
    /** 真实的id */
    private String realId;
    
    /** 结点文本 */
    private String text;
    
    /** 是否叶子结点 */
    private boolean leaf;
    
    /** 层级 */
    private int number;
    
    /** 结点类型 */
    private String type;
    
    /** 排序 */
    private int sort;
    
    /** 父节点id */
    private String parentId;
    
    /** 描述 */
    private String description;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    
    public String getRealId() {
        return realId;
    }
    
    public void setRealId(String realId) {
        this.realId = realId;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public boolean isLeaf() {
        return leaf;
    }
    
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public int getSort() {
        return sort;
    }
    
    public void setSort(int sort) {
        this.sort = sort;
    }
    
    public String getParentId() {
        return parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
