package com.xx.system.common.vo;

/**
 * 封装树结点
 */
public class TreeNode {
    
    /** 结点id */
    private int nodeId;
    
    private int id;
    
    /** 结点文本 */
    private String text;
    
    /** 是否叶子结点 */
    private boolean leaf;
    
    /** 是否展开 */
    private boolean expanded;
    
    /** 层级 */
    private int number;
    
    /** 结点类型 */
    private String type;
    
    /** 排序 */
    private int sort;
    
    /** 父节点id */
    private int parentId;
    
    /** 描述 */
    private String description;
    
    /** 选择框 */
    private boolean checked;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
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
    
    public int getSort() {
        return sort;
    }
    
    public void setSort(int sort) {
        this.sort = sort;
    }
    
    public int getParentId() {
        return parentId;
    }
    
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public boolean isExpanded() {
        return expanded;
    }
    
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    
}
