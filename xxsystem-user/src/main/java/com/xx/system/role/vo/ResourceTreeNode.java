package com.xx.system.role.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装树结点
 */
public class ResourceTreeNode {
    
    /** 结点id */
    private int nodeId;
    
    /** 结点文本 */
    private String text;
    
    /** 是否叶子结点 */
    private boolean leaf;
    
    /** 选择框 */
    private boolean checked;
    
    private List<ResourceTreeNode> children = new ArrayList<ResourceTreeNode>();
    
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
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

	public List<ResourceTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceTreeNode> children) {
		this.children = children;
	}
    
    
    
}
