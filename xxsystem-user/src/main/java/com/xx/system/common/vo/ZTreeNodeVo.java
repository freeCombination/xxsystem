package com.xx.system.common.vo;

import org.apache.struts2.json.annotations.JSON;

/**
 * zTree 封装树结点
 */
public class ZTreeNodeVo {
    
    /** 结点id */
    private String id;
    
    /** 父结点id */
    private String pId;
    
    /** 结点文本 */
    private String name;
    
    /** 结点类型 */
    private int type;
    
    /** 提示信息 */
    private String title;
    
    /** 选择框 */
    private boolean checked;
    
    private boolean isParent;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @JSON(name = "pId")
    public String getPId() {
        return pId;
    }
    
    public void setPId(String pId) {
        this.pId = pId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public boolean getIsParent() {
        return isParent;
    }
    
    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }
    
}
